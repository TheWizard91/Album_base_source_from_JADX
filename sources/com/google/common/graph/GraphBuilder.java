package com.google.common.graph;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.graph.ImmutableGraph;

public final class GraphBuilder<N> extends AbstractGraphBuilder<N> {
    private GraphBuilder(boolean directed) {
        super(directed);
    }

    public static GraphBuilder<Object> directed() {
        return new GraphBuilder<>(true);
    }

    public static GraphBuilder<Object> undirected() {
        return new GraphBuilder<>(false);
    }

    public static <N> GraphBuilder<N> from(Graph<N> graph) {
        return new GraphBuilder(graph.isDirected()).allowsSelfLoops(graph.allowsSelfLoops()).nodeOrder(graph.nodeOrder());
    }

    public <N1 extends N> ImmutableGraph.Builder<N1> immutable() {
        return new ImmutableGraph.Builder<>(cast());
    }

    public GraphBuilder<N> allowsSelfLoops(boolean allowsSelfLoops) {
        this.allowsSelfLoops = allowsSelfLoops;
        return this;
    }

    public GraphBuilder<N> expectedNodeCount(int expectedNodeCount) {
        this.expectedNodeCount = Optional.m1108of(Integer.valueOf(Graphs.checkNonNegative(expectedNodeCount)));
        return this;
    }

    public <N1 extends N> GraphBuilder<N1> nodeOrder(ElementOrder<N1> nodeOrder) {
        GraphBuilder<N1> newBuilder = cast();
        newBuilder.nodeOrder = (ElementOrder) Preconditions.checkNotNull(nodeOrder);
        return newBuilder;
    }

    public <N1 extends N> MutableGraph<N1> build() {
        return new ConfigurableMutableGraph(this);
    }

    private <N1 extends N> GraphBuilder<N1> cast() {
        return this;
    }
}
