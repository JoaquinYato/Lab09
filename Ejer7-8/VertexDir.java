
public class VertexDir<E> {
    private E data;
    protected ListLinked<EdgeDir<E>> listAdj;

    public VertexDir(E data) {
        this.data = data;
        listAdj = new ListLinked<EdgeDir<E>>();
    }

    public E getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof VertexDir<?>) {
            VertexDir<E> v = (VertexDir<E>) o;
            return this.data.equals(v.data);
        }
        return false;
    }

    @Override
    public String toString() {
        return this.data + " --> " + this.listAdj.toString() + "\n";
    }
}
