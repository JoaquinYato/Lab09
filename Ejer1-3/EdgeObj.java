public class EdgeObj<V,E> implements Comparable<EdgeObj<V, E>> {
    protected E info;
    protected VertexObj<V,E> endVertex1;
    protected VertexObj<V,E> endVertex2;
    protected int position;

    public EdgeObj(VertexObj<V,E> vert1, VertexObj<V,E> vert2, E info, int position) {
        this.endVertex1 = vert1;
        this.endVertex2 = vert2;
        this.info = info;
        this.position = position;
    }

    public VertexObj<V,E> getVertex1() {
        return endVertex1;
    }

    public VertexObj<V,E> getVertex2() {
        return endVertex2;
    }

    public boolean connects(VertexObj<V,E> v1, VertexObj<V,E> v2) {
        return (endVertex1.equals(v1) && endVertex2.equals(v2)) ||
                (endVertex1.equals(v2) && endVertex2.equals(v1));
    }

    @Override
    public int compareTo(EdgeObj<V, E> other) {
        return Integer.compare(this.position, other.position);
    }

}
