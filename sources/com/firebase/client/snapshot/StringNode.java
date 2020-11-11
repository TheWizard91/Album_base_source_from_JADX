package com.firebase.client.snapshot;

import com.firebase.client.snapshot.LeafNode;
import com.firebase.client.snapshot.Node;
import com.firebase.client.utilities.Utilities;

public class StringNode extends LeafNode<StringNode> {
    private final String value;

    public StringNode(String value2, Node priority) {
        super(priority);
        this.value = value2;
    }

    public Object getValue() {
        return this.value;
    }

    /* renamed from: com.firebase.client.snapshot.StringNode$1 */
    static /* synthetic */ class C10121 {
        static final /* synthetic */ int[] $SwitchMap$com$firebase$client$snapshot$Node$HashVersion;

        static {
            int[] iArr = new int[Node.HashVersion.values().length];
            $SwitchMap$com$firebase$client$snapshot$Node$HashVersion = iArr;
            try {
                iArr[Node.HashVersion.V1.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$firebase$client$snapshot$Node$HashVersion[Node.HashVersion.V2.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public String getHashRepresentation(Node.HashVersion version) {
        int i = C10121.$SwitchMap$com$firebase$client$snapshot$Node$HashVersion[version.ordinal()];
        if (i == 1) {
            return getPriorityHash(version) + "string:" + this.value;
        }
        if (i == 2) {
            return getPriorityHash(version) + "string:" + Utilities.stringHashV2Representation(this.value);
        }
        throw new IllegalArgumentException("Invalid hash version for string node: " + version);
    }

    public StringNode updatePriority(Node priority) {
        return new StringNode(this.value, priority);
    }

    /* access modifiers changed from: protected */
    public LeafNode.LeafType getLeafType() {
        return LeafNode.LeafType.String;
    }

    /* access modifiers changed from: protected */
    public int compareLeafValues(StringNode other) {
        return this.value.compareTo(other.value);
    }

    public boolean equals(Object other) {
        if (!(other instanceof StringNode)) {
            return false;
        }
        StringNode otherStringNode = (StringNode) other;
        if (!this.value.equals(otherStringNode.value) || !this.priority.equals(otherStringNode.priority)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.value.hashCode() + this.priority.hashCode();
    }
}
