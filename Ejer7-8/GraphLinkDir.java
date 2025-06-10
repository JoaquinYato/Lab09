public class GraphLinkDir<E> {
    protected ListLinked<VertexDir<E>> listVertex;

    public GraphLinkDir() {
        listVertex = new ListLinked<VertexDir<E>>();
    }

    public void insertVertex(E data) {
        if (searchVertexObject(data) == null) {
            listVertex.add(new VertexDir<E>(data));
        }
    }

    public void insertEdge(E verOri, E verDes) {
        VertexDir<E> vOri = searchVertexObject(verOri);
        VertexDir<E> vDes = searchVertexObject(verDes);
        if (vOri == null || vDes == null) return;
        if (!vOri.listAdj.contains(new EdgeDir<E>(vDes))) {
            vOri.listAdj.add(new EdgeDir<E>(vDes));
        }
    }

    public VertexDir<E> searchVertexObject(E data) {
        for (VertexDir<E> v : listVertex) {
            if (v.getData().equals(data)) return v;
        }
        return null;
    }

    // Grado de salida: cantidad de aristas que salen de un nodo
    public int outDegree(E vertexData) {
        VertexDir<E> vertex = searchVertexObject(vertexData);
        if (vertex == null) return -1;
        return vertex.listAdj.size();
    }

    // Grado de entrada: cantidad de aristas que llegan a un nodo
    public int inDegree(E vertexData) {
        VertexDir<E> vertex = searchVertexObject(vertexData);
        if (vertex == null) return -1;
        int count = 0;
        for (VertexDir<E> v : listVertex) {
            if (v.listAdj.contains(new EdgeDir<E>(vertex))) count++;
        }
        return count;
    }

    // Es camino dirigido: un nodo con inDegree 0 y outDegree 1 (inicio), uno con inDegree 1 y outDegree 0 (fin), el resto inDegree 1 y outDegree 1
    public boolean isDirectedPath() {
        int start = 0, end = 0, middle = 0;
        for (VertexDir<E> v : listVertex) {
            int in = inDegree(v.getData());
            int out = outDegree(v.getData());
            if (in == 0 && out == 1) start++;
            else if (in == 1 && out == 0) end++;
            else if (in == 1 && out == 1) middle++;
            else return false;
        }
        return start == 1 && end == 1 && (start + end + middle == listVertex.size());
    }

    // Es ciclo dirigido: todos los nodos con inDegree 1 y outDegree 1
    public boolean isDirectedCycle() {
        for (VertexDir<E> v : listVertex) {
            if (inDegree(v.getData()) != 1 || outDegree(v.getData()) != 1) return false;
        }
        return true;
    }

    // Es rueda dirigida: un nodo con outDegree n-1 y inDegree 0 (centro), n-1 nodos con inDegree 1, outDegree 1 (periféricos)
    public boolean isDirectedWheel() {
        int n = listVertex.size();
        int center = 0, periphery = 0;
        for (VertexDir<E> v : listVertex) {
            int in = inDegree(v.getData());
            int out = outDegree(v.getData());
            if (in == 0 && out == n - 1) center++;
            else if (in == 1 && out == 1) periphery++;
            else return false;
        }
        return center == 1 && periphery == n - 1;
    }

    @Override
    public String toString() {
        return this.listVertex.toString();
    }
}

