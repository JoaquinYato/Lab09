public class GraphListEdge {
    private LinkedList<GraphLink> aristas;

    public GraphListEdge() {
        aristas = new LinkedList<>();
    }

    public void agregarArista(GraphLink arista) {
        aristas.add(arista);
    }

    public LinkedList<GraphLink> obtenerAristas() {
        return aristas;
    }

    public boolean contieneArista(GraphLink arista) {
        return aristas.contains(arista);
    }
}
