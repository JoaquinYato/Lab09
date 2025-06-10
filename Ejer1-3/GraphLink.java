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

    public void insertEdgeNoDirNoPon(E verOri, E verDes){
        insertEdgePon (verOri, verDes, -1);
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

    public boolean removeEdgeNoDir(E v1, E v2) {
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




    //EJERCICIO 1.1
    public void bfs(E v) {
        Vertex<E> start = obtenerVertice(v);
        if (start == null) {
            System.out.println("Vértice no encontrado.");
            return;
        }

        Queue<Vertex<E>> queue = new Queue<>();
        ListLinked<E> visitados = new ListLinked<>();

        queue.enqueue(start);
        visitados.agregar(start.getData());

        System.out.print("BFS desde " + v + ": ");

        while (!queue.isEmpty()) {
            Vertex<E> actual = queue.dequeue();
            System.out.print(actual.getData() + " ");

            ListLinked<Edge<E>> adyacentes = actual.getListAdj();
            ListLinked<Edge<E>>.Nodo nodoArista = adyacentes.getPrimero();

            while (nodoArista != null) {
                Edge<E> arista = adyacentes.getDato(nodoArista);
                Vertex<E> destino = arista.getDestino();
                if (!visitados.buscar(destino.getData())) {
                    queue.enqueue(destino);
                    visitados.agregar(destino.getData());
                }
                nodoArista = adyacentes.getSiguiente(nodoArista);
            }
        }
        System.out.println();
    }

    //EJERCICIO 1.2
    public ListLinked<E> bfsPath(E v, E z) {
        Vertex<E> start = obtenerVertice(v);
        Vertex<E> end = obtenerVertice(z);
        ListLinked<E> camino = new ListLinked<>();

        if (start == null || end == null) {
            return camino; // vacío
        }

        Queue<Vertex<E>> queue = new Queue<>();
        ListLinked<E> visitados = new ListLinked<>();

        // Mapa simple de predecesores para reconstruir camino
        // Usaremos dos listas enlazadas paralelas para simular un mapa:
        ListLinked<Vertex<E>> predecesoresVertices = new ListLinked<>();
        ListLinked<Vertex<E>> verticesVisitados = new ListLinked<>();

        queue.enqueue(start);
        visitados.agregar(start.getData());
        verticesVisitados.agregar(start);

        predecesoresVertices.agregar(null); // start no tiene predecesor

        boolean encontrado = false;

        while (!queue.isEmpty() && !encontrado) {
            Vertex<E> actual = queue.dequeue();

            if (actual.equals(end)) {
                encontrado = true;
                break;
            }

            ListLinked<Edge<E>> adyacentes = actual.getListAdj();
            ListLinked<Edge<E>>.Nodo nodoArista = adyacentes.getPrimero();

            while (nodoArista != null) {
                Edge<E> arista = adyacentes.getDato(nodoArista);
                Vertex<E> destino = arista.getDestino();

                if (!visitados.buscar(destino.getData())) {
                    queue.enqueue(destino);
                    visitados.agregar(destino.getData());

                    // Agregar destino y su predecesor actual
                    verticesVisitados.agregar(destino);
                    predecesoresVertices.agregar(actual);
                }
                nodoArista = adyacentes.getSiguiente(nodoArista);
            }
        }

        if (!encontrado) {
            return camino; // camino vacío
        }

        // Reconstruir camino desde end hasta start usando predecesores
        Vertex<E> paso = end;
        while (paso != null) {
            camino.insertarFirst(paso.getData()); // insertar al inicio
            // Buscar predecesor de paso
            int idx = -1;
            int i = 0;
            ListLinked<Vertex<E>>.Nodo n = verticesVisitados.getPrimero();
            while (n != null) {
                if (verticesVisitados.getDato(n).equals(paso)) {
                    idx = i;
                    break;
                }
                n = verticesVisitados.getSiguiente(n);
                i++;
            }
            if (idx == -1) break; // no encontrado (no debería pasar)
            // obtener predecesor correspondiente
            ListLinked<Vertex<E>>.Nodo p = predecesoresVertices.getPrimero();
            for (int j = 0; j < idx; j++) {
                p = predecesoresVertices.getSiguiente(p);
            }
            paso = predecesoresVertices.getDato(p);
        }

        return camino;
    }

    //EJERCICIO 2.2
    public ListLinked<E> shortPath(E v, E z) {
        Vertex<E> start = obtenerVertice(v);
        Vertex<E> end = obtenerVertice(z);
        ListLinked<E> camino = new ListLinked<>();

        if (start == null || end == null) return camino;

        Queue<Vertex<E>> queue = new Queue<>();
        ListLinked<E> visitados = new ListLinked<>();
        ListLinked<Vertex<E>> verticesVisitados = new ListLinked<>();
        ListLinked<Vertex<E>> predecesoresVertices = new ListLinked<>();

        queue.enqueue(start);
        visitados.agregar(start.getData());
        verticesVisitados.agregar(start);
        predecesoresVertices.agregar(null);

        boolean encontrado = false;

        while (!queue.isEmpty() && !encontrado) {
            Vertex<E> actual = queue.dequeue();

            if (actual.equals(end)) {
                encontrado = true;
                break;
            }

            ListLinked<Edge<E>> adyacentes = actual.getListAdj();
            ListLinked<Edge<E>>.Nodo nodoArista = adyacentes.getPrimero();

            while (nodoArista != null) {
                Edge<E> arista = adyacentes.getDato(nodoArista);
                Vertex<E> destino = arista.getDestino();

                if (!visitados.buscar(destino.getData())) {
                    queue.enqueue(destino);
                    visitados.agregar(destino.getData());
                    verticesVisitados.agregar(destino);
                    predecesoresVertices.agregar(actual);
                }
                nodoArista = adyacentes.getSiguiente(nodoArista);
            }
        }

        if (!encontrado) return camino;

        Vertex<E> paso = end;
        while (paso != null) {
            camino.insertarFirst(paso.getData());

            int idx = -1;
            int i = 0;
            ListLinked<Vertex<E>>.Nodo n = verticesVisitados.getPrimero();
            while (n != null) {
                if (verticesVisitados.getDato(n).equals(paso)) {
                    idx = i;
                    break;
                }
                n = verticesVisitados.getSiguiente(n);
                i++;
            }
            if (idx == -1) break;

            ListLinked<Vertex<E>>.Nodo p = predecesoresVertices.getPrimero();
            for (int j = 0; j < idx; j++) {
                p = predecesoresVertices.getSiguiente(p);
            }
            paso = predecesoresVertices.getDato(p);
        }

        return camino;
    }

    //EJERCICIO 2.3
    public boolean isConexo() {
        if (listVertex.inEmptyList()) return true;

        ListLinked<Vertex<E>>.Nodo nodo = listVertex.getPrimero();
        Vertex<E> inicio = listVertex.getDato(nodo);

        ListLinked<E> visitados = new ListLinked<>();
        Queue<Vertex<E>> queue = new Queue<>();

        queue.enqueue(inicio);
        visitados.agregar(inicio.getData());

        while (!queue.isEmpty()) {
            Vertex<E> actual = queue.dequeue();
            ListLinked<Edge<E>> adyacentes = actual.getListAdj();
            ListLinked<Edge<E>>.Nodo nodoArista = adyacentes.getPrimero();

            while (nodoArista != null) {
                Edge<E> arista = adyacentes.getDato(nodoArista);
                Vertex<E> destino = arista.getDestino();

                if (!visitados.buscar(destino.getData())) {
                    queue.enqueue(destino);
                    visitados.agregar(destino.getData());
                }
                nodoArista = adyacentes.getSiguiente(nodoArista);
            }
        }

        // Comparar cantidad de visitados con cantidad de vértices totales
        return visitados.length() == listVertex.length();
    }
    //EJERCICIO 2.4
    public Stack<E> Dijkstra(E v, E w) {
        Vertex<E> start = obtenerVertice(v);
        Vertex<E> end = obtenerVertice(w);
        Stack<E> ruta = new Stack<>();

        if (start == null || end == null) return ruta;

        ListLinked<Vertex<E>> vertices = listVertex; // todos los vértices
        int n = vertices.length();

        // Listas para distancias y predecesores en paralelo a vertices
        ListLinked<Integer> distancias = new ListLinked<>();
        ListLinked<Vertex<E>> predecesores = new ListLinked<>();
        ListLinked<Vertex<E>> noVisitados = new ListLinked<>();

        // Inicializar distancias infinitas y predecesores null
        ListLinked<Vertex<E>>.Nodo nodoVert = vertices.getPrimero();
        while (nodoVert != null) {
            distancias.agregar(Integer.MAX_VALUE);
            predecesores.agregar(null);
            Vertex<E> vert = vertices.getDato(nodoVert);
            noVisitados.agregar(vert);
            nodoVert = vertices.getSiguiente(nodoVert);
        }

        // Distancia al origen 0
        int indexStart = indexOf(vertices, start);
        setDistancia(distancias, indexStart, 0);

        while (!noVisitados.inEmptyList()) {
            // Obtener vértice con distancia mínima
            int minDist = Integer.MAX_VALUE;
            Vertex<E> u = null;
            ListLinked<Vertex<E>>.Nodo nodoNV = noVisitados.getPrimero();
            int pos = 0;
            int minPos = -1;
            while (nodoNV != null) {
                Vertex<E> vert = noVisitados.getDato(nodoNV);
                int idx = indexOf(vertices, vert);
                int dist = getDistancia(distancias, idx);
                if (dist < minDist) {
                    minDist = dist;
                    u = vert;
                    minPos = pos;
                }
                nodoNV = noVisitados.getSiguiente(nodoNV);
                pos++;
            }

            if (u == null) break; // no queda nada

            // Remover u de noVisitados
            removeAt(noVisitados, minPos);

            if (u.equals(end)) break; // llegamos

            int idxU = indexOf(vertices, u);

            // Actualizar distancias vecinos
            ListLinked<Edge<E>> adyacentes = u.getListAdj();
            ListLinked<Edge<E>>.Nodo nodoArista = adyacentes.getPrimero();
            while (nodoArista != null) {
                Edge<E> arista = adyacentes.getDato(nodoArista);
                Vertex<E> vAdy = arista.getDestino();
                int idxV = indexOf(vertices, vAdy);

                // Solo si vAdy sigue en noVisitados
                if (contains(noVisitados, vAdy)) {
                    int alt = minDist + arista.getPeso();
                    int distV = getDistancia(distancias, idxV);
                    if (alt < distV) {
                        setDistancia(distancias, idxV, alt);
                        setPredecesor(predecesores, idxV, u);
                    }
                }
                nodoArista = adyacentes.getSiguiente(nodoArista);
            }
        }

        // Reconstruir ruta desde end hacia start
        Vertex<E> paso = end;
        while (paso != null) {
            ruta.push(paso.getData());
            int idx = indexOf(vertices, paso);
            paso = getPredecesor(predecesores, idx);
        }

        return ruta;
    }

    //METODOS AUXILIARES EJERCICIO 2.4

    private void setDistancia(ListLinked<Integer> distancias, int idx, int valor) {
        ListLinked<Integer>.Nodo nodo = distancias.getPrimero();
        for (int i = 0; i < idx; i++) nodo = distancias.getSiguiente(nodo);
        distancias.setDato(nodo, valor);
    }

    private int indexOf(ListLinked<Vertex<E>> list, Vertex<E> v) {
        int i = 0;
        ListLinked<Vertex<E>>.Nodo nodo = list.getPrimero();
        while (nodo != null) {
            if (list.getDato(nodo).equals(v)) return i;
            nodo = list.getSiguiente(nodo);
            i++;
        }
        return -1;
    }
    private int getDistancia(ListLinked<Integer> distancias, int idx) {
        if (idx < 0 || idx >= distancias.length()) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        ListLinked<Integer>.Nodo nodo = distancias.getPrimero();
        for (int i = 0; i < idx; i++) nodo = distancias.getSiguiente(nodo);
        return distancias.getDato(nodo);
    }
    private Vertex<E> getPredecesor(ListLinked<Vertex<E>> predecesores, int idx) {
        ListLinked<Vertex<E>>.Nodo nodo = predecesores.getPrimero();
        for (int i = 0; i < idx; i++) nodo = predecesores.getSiguiente(nodo);
        return predecesores.getDato(nodo);
    }
    private void setPredecesor(ListLinked<Vertex<E>> predecesores, int idx, Vertex<E> v) {
        ListLinked<Vertex<E>>.Nodo nodo = predecesores.getPrimero();
        for (int i = 0; i < idx; i++) nodo = predecesores.getSiguiente(nodo);
        predecesores.setDato(nodo, v);
    }
    private boolean contains(ListLinked<Vertex<E>> list, Vertex<E> v) {
        ListLinked<Vertex<E>>.Nodo nodo = list.getPrimero();
        while (nodo != null) {
            if (list.getDato(nodo).equals(v)) return true;
            nodo = list.getSiguiente(nodo);
        }
        return false;
    }
    private void removeAt(ListLinked<Vertex<E>> list, int index) {
        if (index < 0 || index >= list.length()) return;

        // Recorremos hasta el nodo en la posición `index`
        ListLinked<Vertex<E>>.Nodo nodo = list.getPrimero();
        for (int i = 0; i < index; i++) {
            nodo = list.getSiguiente(nodo);
        }

        // Eliminamos por dato
        list.removeNode(list.getDato(nodo));
    }


}
