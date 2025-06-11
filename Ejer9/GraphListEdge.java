import java.util.*;

public class GraphListEdge {
    private List<GraphLink> aristas;

    public GraphListEdge() {
        aristas = new ArrayList<>();
    }

    public void agregarArista(GraphLink arista) {
        aristas.add(arista);
    }

    public List<GraphLink> obtenerAristas() {
        return aristas;
    }

    public boolean contieneArista(GraphLink arista) {
        return aristas.contains(arista);
    }
}
