public class GraphListEdge<V, E> {
    private ListLinked<VertexObj<V, E>> secVertex;
    private ListLinked<EdgeObj<V, E>> secEdge;

    public GraphListEdge() {
        this.secVertex = new ListLinked<>();
        this.secEdge = new ListLinked<>();
    }

    public void insertVertex(V data) {
        if (!searchVertex(data)) {
            VertexObj<V, E> v = new VertexObj<>(data, secVertex.size());
            secVertex.agregar(v);
        }
    }

    public boolean searchVertex(V data) {
        ListLinked<VertexObj<V, E>>.Nodo nodo = secVertex.getPrimero();
        while (nodo != null) {
            VertexObj<V, E> v = secVertex.getDato(nodo);
            if (v.getInfo().equals(data)) return true;
            nodo = secVertex.getSiguiente(nodo);
        }
        return false;
    }

    private VertexObj<V, E> getVertex(V data) {
        ListLinked<VertexObj<V, E>>.Nodo nodo = secVertex.getPrimero();
        while (nodo != null) {
            VertexObj<V, E> v = secVertex.getDato(nodo);
            if (v.getInfo().equals(data)) return v;
            nodo = secVertex.getSiguiente(nodo);
        }
        return null;
    }

    public void insertEdge(V v1, V v2, E info) {
        if (!searchEdge(v1, v2)) {
            VertexObj<V, E> vert1 = getVertex(v1);
            VertexObj<V, E> vert2 = getVertex(v2);
            if (vert1 != null && vert2 != null) {
                EdgeObj<V, E> edge = new EdgeObj<>(vert1, vert2, info, secEdge.size());
                secEdge.agregar(edge);
            }
        }
    }

    public boolean searchEdge(V v1, V v2) {
        VertexObj<V, E> vert1 = getVertex(v1);
        VertexObj<V, E> vert2 = getVertex(v2);
        if (vert1 == null || vert2 == null) return false;

        ListLinked<EdgeObj<V, E>>.Nodo nodo = secEdge.getPrimero();
        while (nodo != null) {
            EdgeObj<V, E> edge = secEdge.getDato(nodo);
            if (edge.connects(vert1, vert2)) return true;
            nodo = secEdge.getSiguiente(nodo);
        }
        return false;
    }

    public void bfs(V origen) {
        VertexObj<V, E> start = getVertex(origen);
        if (start == null) {
            System.out.println("Vértice no encontrado.");
            return;
        }

        ListLinked<VertexObj<V, E>> visitados = new ListLinked<>();
        Queue<VertexObj<V, E>> cola = new Queue<>();

        cola.enqueue(start);
        visitados.agregar(start);
        System.out.print("BFS desde " + origen + ": ");

        while (!cola.isEmpty()) {
            VertexObj<V, E> actual = cola.dequeue();
            System.out.print(actual.getInfo() + " ");

            ListLinked<VertexObj<V, E>> vecinos = getVecinos(actual);
            ListLinked<VertexObj<V, E>>.Nodo nodo = vecinos.getPrimero();
            while (nodo != null) {
                VertexObj<V, E> vecino = vecinos.getDato(nodo);
                if (!visitados.buscar(vecino)) {
                    cola.enqueue(vecino);
                    visitados.agregar(vecino);
                }
                nodo = vecinos.getSiguiente(nodo);
            }
        }
        System.out.println();
    }

    private ListLinked<VertexObj<V, E>> getVecinos(VertexObj<V, E> v) {
        ListLinked<VertexObj<V, E>> vecinos = new ListLinked<>();
        ListLinked<EdgeObj<V, E>>.Nodo nodo = secEdge.getPrimero();
        while (nodo != null) {
            EdgeObj<V, E> edge = secEdge.getDato(nodo);
            if (edge.getVertex1().equals(v)) {
                vecinos.agregar(edge.getVertex2());
            } else if (edge.getVertex2().equals(v)) {
                vecinos.agregar(edge.getVertex1());
            }
            nodo = secEdge.getSiguiente(nodo);
        }
        return vecinos;
    }
}
