public class Main {
    public static void main(String[] args) {
        GraphLink<String> grafo = new GraphLink<>();

        // Insertar vértices
        grafo.insertVertex("A");
        grafo.insertVertex("B");
        grafo.insertVertex("C");
        grafo.insertVertex("D");
        grafo.insertVertex("E");

        // Insertar aristas no dirigidas con pesos
        grafo.insertEdgeNoDirigido("A", "B", 5);
        grafo.insertEdgeNoDirigido("A", "C", 3);
        grafo.insertEdgeNoDirigido("B", "D", 2);
        grafo.insertEdgeNoDirigido("C", "D", 4);
        grafo.insertEdgeNoDirigido("D", "E", 1);

        // Mostrar el grafo
        System.out.println("Grafo:");
        System.out.println(grafo);

        // Realizar recorrido DFS
        grafo.dfs("A");
    }
}
