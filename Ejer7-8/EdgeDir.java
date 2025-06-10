public class EdgeDir<E> {
    private VertexDir<E> refDest;

    public EdgeDir(VertexDir<E> refDest) {
        this.refDest = refDest;
    }

    public VertexDir<E> getRefDest() {
        return refDest;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof EdgeDir<?>) {
            EdgeDir<E> e = (EdgeDir<E>) o;
            return this.refDest.equals(e.refDest);
        }
        return false;
    }

    @Override
    public String toString() {
        return refDest.getData() + ", ";
    }
}
