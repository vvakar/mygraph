package vvakar.tree;

/**
 * {@inheritDoc}
 */
public class MinMaxSegmentTree extends AbstractSegmentTree {

    public MinMaxSegmentTree(int nonEmptyPositions[]) {
        super(nonEmptyPositions);
    }

    /**
     * {@inheritDoc}
     */
    public void update(int key, long val) {
        Node node = leafs.get(key);
        node.value = val;
        propagateUp(node.parent);
    }

    private void propagateUp(Node node) {
        if (node == null) return;
        node.value = aggregateQueryResults(node.left.value, node.right.value);
        propagateUp(node.parent);
    }

    @Override
    protected long aggregateQueryResults(long candidateLeft, long candidateRight) {
        return Math.max(candidateLeft, candidateRight);
    }
}

