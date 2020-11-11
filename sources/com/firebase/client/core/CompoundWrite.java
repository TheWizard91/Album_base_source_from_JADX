package com.firebase.client.core;

import com.firebase.client.core.utilities.ImmutableTree;
import com.firebase.client.snapshot.ChildKey;
import com.firebase.client.snapshot.NamedNode;
import com.firebase.client.snapshot.Node;
import com.firebase.client.snapshot.NodeUtilities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CompoundWrite implements Iterable<Map.Entry<Path, Node>> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final CompoundWrite EMPTY = new CompoundWrite(new ImmutableTree(null));
    private final ImmutableTree<Node> writeTree;

    private CompoundWrite(ImmutableTree<Node> writeTree2) {
        this.writeTree = writeTree2;
    }

    public static CompoundWrite emptyWrite() {
        return EMPTY;
    }

    public static CompoundWrite fromValue(Map<String, Object> merge) {
        ImmutableTree<Node> writeTree2 = ImmutableTree.emptyInstance();
        for (Map.Entry<String, Object> entry : merge.entrySet()) {
            writeTree2 = writeTree2.setTree(new Path(entry.getKey()), new ImmutableTree<>(NodeUtilities.NodeFromJSON(entry.getValue())));
        }
        return new CompoundWrite(writeTree2);
    }

    public static CompoundWrite fromChildMerge(Map<ChildKey, Node> merge) {
        ImmutableTree<Node> writeTree2 = ImmutableTree.emptyInstance();
        for (Map.Entry<ChildKey, Node> entry : merge.entrySet()) {
            writeTree2 = writeTree2.setTree(new Path(entry.getKey()), new ImmutableTree<>(entry.getValue()));
        }
        return new CompoundWrite(writeTree2);
    }

    public static CompoundWrite fromPathMerge(Map<Path, Node> merge) {
        ImmutableTree<Node> writeTree2 = ImmutableTree.emptyInstance();
        for (Map.Entry<Path, Node> entry : merge.entrySet()) {
            writeTree2 = writeTree2.setTree(entry.getKey(), new ImmutableTree<>(entry.getValue()));
        }
        return new CompoundWrite(writeTree2);
    }

    public CompoundWrite addWrite(Path path, Node node) {
        if (path.isEmpty()) {
            return new CompoundWrite(new ImmutableTree(node));
        }
        Path rootMostPath = this.writeTree.findRootMostPathWithValue(path);
        if (rootMostPath != null) {
            Path relativePath = Path.getRelative(rootMostPath, path);
            Node value = this.writeTree.get(rootMostPath);
            ChildKey back = relativePath.getBack();
            if (back != null && back.isPriorityChildName() && value.getChild(relativePath.getParent()).isEmpty()) {
                return this;
            }
            return new CompoundWrite(this.writeTree.set(rootMostPath, value.updateChild(relativePath, node)));
        }
        return new CompoundWrite(this.writeTree.setTree(path, new ImmutableTree<>(node)));
    }

    public CompoundWrite addWrite(ChildKey key, Node node) {
        return addWrite(new Path(key), node);
    }

    public CompoundWrite addWrites(final Path path, CompoundWrite updates) {
        return (CompoundWrite) updates.writeTree.fold(this, new ImmutableTree.TreeVisitor<Node, CompoundWrite>() {
            public CompoundWrite onNodeValue(Path relativePath, Node value, CompoundWrite accum) {
                return accum.addWrite(path.child(relativePath), value);
            }
        });
    }

    public CompoundWrite removeWrite(Path path) {
        if (path.isEmpty()) {
            return EMPTY;
        }
        return new CompoundWrite(this.writeTree.setTree(path, ImmutableTree.emptyInstance()));
    }

    public boolean hasCompleteWrite(Path path) {
        return getCompleteNode(path) != null;
    }

    public Node rootWrite() {
        return this.writeTree.getValue();
    }

    public Node getCompleteNode(Path path) {
        Path rootMost = this.writeTree.findRootMostPathWithValue(path);
        if (rootMost != null) {
            return this.writeTree.get(rootMost).getChild(Path.getRelative(rootMost, path));
        }
        return null;
    }

    public List<NamedNode> getCompleteChildren() {
        List<NamedNode> children = new ArrayList<>();
        if (this.writeTree.getValue() != null) {
            for (NamedNode entry : this.writeTree.getValue()) {
                children.add(new NamedNode(entry.getName(), entry.getNode()));
            }
        } else {
            Iterator i$ = this.writeTree.getChildren().iterator();
            while (i$.hasNext()) {
                Map.Entry<ChildKey, ImmutableTree<Node>> entry2 = i$.next();
                ImmutableTree<Node> childTree = entry2.getValue();
                if (childTree.getValue() != null) {
                    children.add(new NamedNode(entry2.getKey(), childTree.getValue()));
                }
            }
        }
        return children;
    }

    public CompoundWrite childCompoundWrite(Path path) {
        if (path.isEmpty()) {
            return this;
        }
        Node shadowingNode = getCompleteNode(path);
        if (shadowingNode != null) {
            return new CompoundWrite(new ImmutableTree(shadowingNode));
        }
        return new CompoundWrite(this.writeTree.subtree(path));
    }

    public Map<ChildKey, CompoundWrite> childCompoundWrites() {
        Map<ChildKey, CompoundWrite> children = new HashMap<>();
        Iterator i$ = this.writeTree.getChildren().iterator();
        while (i$.hasNext()) {
            Map.Entry<ChildKey, ImmutableTree<Node>> entries = i$.next();
            children.put(entries.getKey(), new CompoundWrite(entries.getValue()));
        }
        return children;
    }

    public boolean isEmpty() {
        return this.writeTree.isEmpty();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: com.firebase.client.snapshot.Node} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.firebase.client.snapshot.Node applySubtreeWrite(com.firebase.client.core.Path r8, com.firebase.client.core.utilities.ImmutableTree<com.firebase.client.snapshot.Node> r9, com.firebase.client.snapshot.Node r10) {
        /*
            r7 = this;
            java.lang.Object r0 = r9.getValue()
            if (r0 == 0) goto L_0x0011
            java.lang.Object r0 = r9.getValue()
            com.firebase.client.snapshot.Node r0 = (com.firebase.client.snapshot.Node) r0
            com.firebase.client.snapshot.Node r0 = r10.updateChild(r8, r0)
            return r0
        L_0x0011:
            r0 = 0
            com.firebase.client.collection.ImmutableSortedMap r1 = r9.getChildren()
            java.util.Iterator r1 = r1.iterator()
        L_0x001a:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0057
            java.lang.Object r2 = r1.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            java.lang.Object r3 = r2.getValue()
            com.firebase.client.core.utilities.ImmutableTree r3 = (com.firebase.client.core.utilities.ImmutableTree) r3
            java.lang.Object r4 = r2.getKey()
            com.firebase.client.snapshot.ChildKey r4 = (com.firebase.client.snapshot.ChildKey) r4
            boolean r5 = r4.isPriorityChildName()
            if (r5 == 0) goto L_0x004e
            java.lang.Object r5 = r3.getValue()
            if (r5 == 0) goto L_0x0046
            java.lang.Object r5 = r3.getValue()
            r0 = r5
            com.firebase.client.snapshot.Node r0 = (com.firebase.client.snapshot.Node) r0
            goto L_0x0056
        L_0x0046:
            java.lang.AssertionError r5 = new java.lang.AssertionError
            java.lang.String r6 = "Priority writes must always be leaf nodes"
            r5.<init>(r6)
            throw r5
        L_0x004e:
            com.firebase.client.core.Path r5 = r8.child((com.firebase.client.snapshot.ChildKey) r4)
            com.firebase.client.snapshot.Node r10 = r7.applySubtreeWrite(r5, r3, r10)
        L_0x0056:
            goto L_0x001a
        L_0x0057:
            com.firebase.client.snapshot.Node r1 = r10.getChild(r8)
            boolean r1 = r1.isEmpty()
            if (r1 != 0) goto L_0x006f
            if (r0 == 0) goto L_0x006f
            com.firebase.client.snapshot.ChildKey r1 = com.firebase.client.snapshot.ChildKey.getPriorityKey()
            com.firebase.client.core.Path r1 = r8.child((com.firebase.client.snapshot.ChildKey) r1)
            com.firebase.client.snapshot.Node r10 = r10.updateChild(r1, r0)
        L_0x006f:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.client.core.CompoundWrite.applySubtreeWrite(com.firebase.client.core.Path, com.firebase.client.core.utilities.ImmutableTree, com.firebase.client.snapshot.Node):com.firebase.client.snapshot.Node");
    }

    public Node apply(Node node) {
        return applySubtreeWrite(Path.getEmptyPath(), this.writeTree, node);
    }

    public Map<String, Object> getValue(final boolean exportFormat) {
        final Map<String, Object> writes = new HashMap<>();
        this.writeTree.foreach(new ImmutableTree.TreeVisitor<Node, Void>() {
            public Void onNodeValue(Path relativePath, Node value, Void accum) {
                writes.put(relativePath.wireFormat(), value.getValue(exportFormat));
                return null;
            }
        });
        return writes;
    }

    public Iterator<Map.Entry<Path, Node>> iterator() {
        return this.writeTree.iterator();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || o.getClass() != getClass()) {
            return false;
        }
        return ((CompoundWrite) o).getValue(true).equals(getValue(true));
    }

    public int hashCode() {
        return getValue(true).hashCode();
    }

    public String toString() {
        return "CompoundWrite{" + getValue(true).toString() + "}";
    }
}
