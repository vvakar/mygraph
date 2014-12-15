package vvakar.tree;

/**
 * {@inheritDoc}
 */
public class SumSegmentTree extends AbstractSegmentTree {

    public SumSegmentTree(int nonEmptyPositions[]) {
        super(nonEmptyPositions);
    }

    /**
     * {@inheritDoc}
     */
    public void update(int key, long val) {
        Node node = leafs.get(key);
        long originalValue = node.value;
        node.value = val;
        propagateUp(node.parent, val - originalValue);
    }

    private void propagateUp(Node node, long diff) {
        if (node == null) return;
        node.value += diff;
        propagateUp(node.parent, diff);
    }

    @Override
    protected long aggregateQueryResults(long candidateLeft, long candidateRight) {
        return candidateLeft + candidateRight;
    }
}

