public class VertexObj<V,E> implements Comparable<VertexObj<V, E>> {
    protected V info;
    protected int position;

    public VertexObj(V info, int position) {
        this.info = info;
        this.position = position;
    }

    public V getInfo() {
        return info;
    }

    public boolean equals(Object o) {
        if (o instanceof VertexObj) {
            VertexObj<?, ?> v = (VertexObj<?, ?>) o;
            return this.info.equals(v.info);
        }
        return false;
    }

    @Override
    public int compareTo(VertexObj<V, E> other) {
        return Integer.compare(this.position, other.position);
    }

}
