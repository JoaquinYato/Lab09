public class Vertex <E> implements Comparable<Vertex<E>> {
    private E data;
    protected ListLinked<Edge<E>> listAdj;

    public Vertex(E data){
        this.data = data;
        listAdj = new ListLinked<Edge<E>>();
    }

    public ListLinked<Edge<E>> getListAdj(){return listAdj;}

    public E getData(){return data;}

    @Override
    public int compareTo(Vertex<E> o) {
        if (data instanceof Comparable) {
            return ((Comparable<E>) data).compareTo(o.getData());
        }
        return 0;
    }

    public boolean equals(Object o){
        if(o instanceof Vertex<?>){
            Vertex<E> v = (Vertex)o;
            return this.data.equals(v.data);
        }
        return false;
    }

    public String toString(){
        return this.data+"--->"+this.listAdj.toString()+"\n";
    }
}
