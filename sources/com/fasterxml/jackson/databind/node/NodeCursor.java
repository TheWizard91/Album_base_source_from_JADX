package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Iterator;
import java.util.Map;

abstract class NodeCursor extends JsonStreamContext {
    protected String _currentName;
    protected final NodeCursor _parent;

    public abstract boolean currentHasChildren();

    public abstract JsonNode currentNode();

    public abstract JsonToken endToken();

    public abstract JsonToken nextToken();

    public abstract JsonToken nextValue();

    public NodeCursor(int i, NodeCursor nodeCursor) {
        this._type = i;
        this._index = -1;
        this._parent = nodeCursor;
    }

    public final NodeCursor getParent() {
        return this._parent;
    }

    public final String getCurrentName() {
        return this._currentName;
    }

    public void overrideCurrentName(String str) {
        this._currentName = str;
    }

    public final NodeCursor iterateChildren() {
        JsonNode currentNode = currentNode();
        if (currentNode == null) {
            throw new IllegalStateException("No current node");
        } else if (currentNode.isArray()) {
            return new Array(currentNode, this);
        } else {
            if (currentNode.isObject()) {
                return new Object(currentNode, this);
            }
            throw new IllegalStateException("Current node of type " + currentNode.getClass().getName());
        }
    }

    protected static final class RootValue extends NodeCursor {
        protected boolean _done = false;
        protected JsonNode _node;

        public /* bridge */ /* synthetic */ JsonStreamContext getParent() {
            return NodeCursor.super.getParent();
        }

        public RootValue(JsonNode jsonNode, NodeCursor nodeCursor) {
            super(0, nodeCursor);
            this._node = jsonNode;
        }

        public void overrideCurrentName(String str) {
        }

        public JsonToken nextToken() {
            if (!this._done) {
                this._done = true;
                return this._node.asToken();
            }
            this._node = null;
            return null;
        }

        public JsonToken nextValue() {
            return nextToken();
        }

        public JsonToken endToken() {
            return null;
        }

        public JsonNode currentNode() {
            return this._node;
        }

        public boolean currentHasChildren() {
            return false;
        }
    }

    protected static final class Array extends NodeCursor {
        protected Iterator<JsonNode> _contents;
        protected JsonNode _currentNode;

        public /* bridge */ /* synthetic */ JsonStreamContext getParent() {
            return NodeCursor.super.getParent();
        }

        public Array(JsonNode jsonNode, NodeCursor nodeCursor) {
            super(1, nodeCursor);
            this._contents = jsonNode.elements();
        }

        public JsonToken nextToken() {
            if (!this._contents.hasNext()) {
                this._currentNode = null;
                return null;
            }
            JsonNode next = this._contents.next();
            this._currentNode = next;
            return next.asToken();
        }

        public JsonToken nextValue() {
            return nextToken();
        }

        public JsonToken endToken() {
            return JsonToken.END_ARRAY;
        }

        public JsonNode currentNode() {
            return this._currentNode;
        }

        public boolean currentHasChildren() {
            return ((ContainerNode) currentNode()).size() > 0;
        }
    }

    protected static final class Object extends NodeCursor {
        protected Iterator<Map.Entry<String, JsonNode>> _contents;
        protected Map.Entry<String, JsonNode> _current;
        protected boolean _needEntry = true;

        public /* bridge */ /* synthetic */ JsonStreamContext getParent() {
            return NodeCursor.super.getParent();
        }

        public Object(JsonNode jsonNode, NodeCursor nodeCursor) {
            super(2, nodeCursor);
            this._contents = ((ObjectNode) jsonNode).fields();
        }

        public JsonToken nextToken() {
            if (this._needEntry) {
                String str = null;
                if (!this._contents.hasNext()) {
                    this._currentName = null;
                    this._current = null;
                    return null;
                }
                this._needEntry = false;
                Map.Entry<String, JsonNode> next = this._contents.next();
                this._current = next;
                if (next != null) {
                    str = next.getKey();
                }
                this._currentName = str;
                return JsonToken.FIELD_NAME;
            }
            this._needEntry = true;
            return this._current.getValue().asToken();
        }

        public JsonToken nextValue() {
            JsonToken nextToken = nextToken();
            if (nextToken == JsonToken.FIELD_NAME) {
                return nextToken();
            }
            return nextToken;
        }

        public JsonToken endToken() {
            return JsonToken.END_OBJECT;
        }

        public JsonNode currentNode() {
            Map.Entry<String, JsonNode> entry = this._current;
            if (entry == null) {
                return null;
            }
            return entry.getValue();
        }

        public boolean currentHasChildren() {
            return ((ContainerNode) currentNode()).size() > 0;
        }
    }
}
