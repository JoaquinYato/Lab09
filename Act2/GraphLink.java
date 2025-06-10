public class GraphLink<E extends Comparable<E>>  {
    protected ListLinked<Vertex<E>> listVertex;

    public GraphLink() {
        listVertex = new ListLinked<Vertex<E>>();
    }

    public void insertVertex(E data) {
        if (data == null) return;
        if(!searchVertex(data)) {
            Vertex<E> nuevo = new Vertex(data);
            listVertex.agregar(nuevo);
        }
    }

    public boolean searchVertex(E data){
        ListLinked<Vertex<E>>.Nodo actual = listVertex.getPrimero();
        while (actual!= null){
            Vertex<E> vertice = listVertex.getDato(actual);
            if (vertice.getData().equals(data)) return true;
            actual = listVertex.getSiguiente(actual);
        }
        return false;
    }

    public Vertex<E> obtenerVertice(E data){
        ListLinked<Vertex<E>>.Nodo actual = listVertex.getPrimero();
        while (actual != null){
            Vertex<E> vertice = listVertex.getDato(actual);
            if (vertice.getData().equals(data)) return vertice;
            actual = listVertex.getSiguiente(actual);
        }
        return null;
    }

    public void insertEdgeNoPon(E verOri, E verDes){
        insertEdgePon (verOri, verDes, -1);
    }

    public void insertEdgePon(E verOri, E verDes, int peso) {
        Vertex<E> origen = obtenerVertice(verOri);
        Vertex<E> destino = obtenerVertice(verDes);
        if (origen != null && destino != null){
            Edge<E> arista = new Edge<>(destino, peso);
            origen.getListAdj().agregar(arista);
        }
    }

    public void insertEdgeNoDirNoPon(E verOri, E verDes){insertEdgePon (verOri, verDes, -1);
    }

    public void insertEdgeNoDirigido(E verOri, E verDes, int peso) {
        Vertex<E> origen = obtenerVertice(verOri);
        Vertex<E> destino = obtenerVertice(verDes);
        if (origen != null && destino != null && !searchEdge(verOri, verDes)) {
            origen.getListAdj().agregar(new Edge<>(destino, peso));
            destino.getListAdj().agregar(new Edge<>(origen, peso));
        }
    }

    public boolean searchEdge (E v, E z){
        Vertex<E> origen = obtenerVertice(v);
        Vertex<E> destino = obtenerVertice(z);

        if(origen == null || destino == null) return false;

        ListLinked<Edge<E>> list = origen.getListAdj();
        ListLinked<Edge<E>>.Nodo actual = list.getPrimero();

        while(actual != null){
            Edge<E> arista = list.getDato(actual);
            if(arista.getDestino().getData().equals(z)) return true;
            actual = list.getSiguiente(actual);
        }
        return false;
    }

    public boolean removeEdge(E v, E z) {
        Vertex<E> origen = obtenerVertice(v);
        Vertex<E> destino = obtenerVertice(z);

        if (origen == null || destino == null) return false;

        ListLinked<Edge<E>> lista = origen.getListAdj();
        ListLinked<Edge<E>>.Nodo nodo = lista.getPrimero();
        while (nodo != null) {
            Edge<E> arista = lista.getDato(nodo);
            if (arista.getDestino().equals(destino)) {
                lista.removeNode(arista);
                return true;
            }
            nodo = lista.getSiguiente(nodo);
        }
        return false;
    }

    public boolean removeEdgeNoDir(E v, E z) {
        Vertex<E> origen = obtenerVertice(v);
        Vertex<E> destino = obtenerVertice(z);

        if (origen == null || destino == null) return false;

        boolean eliminado = false;

        ListLinked<Edge<E>> listaOrigen = origen.getListAdj();
        ListLinked<Edge<E>>.Nodo nodo = listaOrigen.getPrimero();
        while (nodo != null) {
            Edge<E> arista = listaOrigen.getDato(nodo);
            if (arista.getDestino().equals(destino)) {
                listaOrigen.removeNode(arista);
                eliminado = true;
                break;
            }
            nodo = listaOrigen.getSiguiente(nodo);
        }

        ListLinked<Edge<E>> listaDestino = destino.getListAdj();
        nodo = listaDestino.getPrimero();
        while (nodo != null) {
            Edge<E> arista = listaDestino.getDato(nodo);
            if (arista.getDestino().equals(origen)) {
                listaDestino.removeNode(arista);
                eliminado = true;
                break;
            }
            nodo = listaDestino.getSiguiente(nodo);
        }

        return eliminado;
    }

    public boolean removeVertex(E v) {
        if (v == null || !searchVertex(v)) return false;

        Vertex<E> origen = obtenerVertice(v);

        ListLinked<Edge<E>> adyacentes = origen.getListAdj();
        ListLinked<Edge<E>>.Nodo nodoArista = adyacentes.getPrimero();
        while (nodoArista != null) {
            Edge<E> arista = adyacentes.getDato(nodoArista);
            E destino = arista.getDestino().getData();
            removeEdge(origen.getData(), destino);
            nodoArista = adyacentes.getPrimero();
        }

        ListLinked<Vertex<E>>.Nodo nodoVertice = listVertex.getPrimero();
        while (nodoVertice != null) {
            Vertex<E> actual = listVertex.getDato(nodoVertice);
            if (!actual.equals(origen)) {
                ListLinked<Edge<E>> lista = actual.getListAdj();
                ListLinked<Edge<E>>.Nodo nodo = lista.getPrimero();
                while (nodo != null) {
                    Edge<E> arista = lista.getDato(nodo);
                    if (arista.getDestino().equals(origen)) {
                        removeEdge(actual.getData(), origen.getData());
                        break;
                    }
                    nodo = lista.getSiguiente(nodo);
                }
            }
            nodoVertice = listVertex.getSiguiente(nodoVertice);
        }
        listVertex.removeNode(origen);
        return true;
    }

    public boolean removeVertexNoDir(E v){
        if (v == null || !searchVertex(v)) return false;

        Vertex<E> origen = obtenerVertice(v);

        ListLinked<Edge<E>> adyacentes = origen.getListAdj();
        ListLinked<Edge<E>>.Nodo nodoArista = adyacentes.getPrimero();

        while (nodoArista != null){
            Edge<E> arista = adyacentes.getDato(nodoArista);
            E destino = arista.getDestino().getData();
            removeEdgeNoDir(origen.getData(), destino);
            nodoArista = adyacentes.getPrimero();
        }
        listVertex.removeNode(origen);
        return true;
    }

    public void dfs(E v) {
        Vertex<E> origen = obtenerVertice(v);
        if (origen == null) {
            System.out.println("Vertice no encontrado.");
            return;
        }

        ListLinked<E> visitados = new ListLinked<>();
        System.out.print("DFS desde " + v + ": ");
        dfsRecursivo(origen, visitados);
        System.out.println();
    }

    private void dfsRecursivo(Vertex<E> actual, ListLinked<E> visitados) {
        System.out.print(actual.getData() + " ");
        visitados.agregar(actual.getData());

        ListLinked<Edge<E>> adyacentes = actual.getListAdj();
        ListLinked<Edge<E>>.Nodo nodoArista = adyacentes.getPrimero();

        while (nodoArista != null) {
            Edge<E> arista = adyacentes.getDato(nodoArista);
            Vertex<E> destino = arista.getDestino();
            if (!visitados.buscar(destino.getData())) {
                dfsRecursivo(destino, visitados);
            }
            nodoArista = adyacentes.getSiguiente(nodoArista);
        }
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListLinked<Vertex<E>>.Nodo nodo = listVertex.getPrimero();
        while (nodo != null) {
            Vertex<E> vertice = listVertex.getDato(nodo);
            sb.append(vertice.getData()).append(" -> ");
            ListLinked<Edge<E>> adyacentes = vertice.getListAdj();
            ListLinked<Edge<E>>.Nodo nodoArista = adyacentes.getPrimero();
            while (nodoArista != null) {
                Edge<E> arista = adyacentes.getDato(nodoArista);
                sb.append(arista.getDestino().getData())
                        .append(" (").append(arista.getPeso()).append(")").append(" -> ");
                nodoArista = adyacentes.getSiguiente(nodoArista);
            }
            sb.append("null\n");
            nodo = listVertex.getSiguiente(nodo);
        }
        return sb.toString();
    }
}
