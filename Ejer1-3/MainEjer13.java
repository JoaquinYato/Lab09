public class MainEjer13 {
    public static void main(String[] args) {
        GraphLink<String> grafo = new GraphLink<>();

        // Agregar vértices
        grafo.insertVertex("A");
        grafo.insertVertex("B");
        grafo.insertVertex("C");
        grafo.insertVertex("D");

        // Agregar aristas
        grafo.insertEdgePon("A", "B", 5);
        grafo.insertEdgePon("A", "C", 3);
        grafo.insertEdgePon("B", "D", 2);
        grafo.insertEdgePon("C", "D", 4);

        // Mostrar grafo
        System.out.println("Grafo inicial:");
        System.out.println(grafo);

        // Buscar aristas
        System.out.println("¿Existe arista entre A y C? " + grafo.searchEdge("A", "C"));
        System.out.println("¿Existe arista entre B y C? " + grafo.searchEdge("B", "C"));

        // Eliminar arista
        grafo.removeEdge("A", "C");
        System.out.println("\nDespués de eliminar la arista A -> C:");
        System.out.println(grafo);

        // Eliminar vértice
        grafo.removeVertex("D");
        System.out.println("\nDespués de eliminar el vértice D:");
        System.out.println(grafo);

        // DFS desde un vértice
        System.out.println("\nRecorrido DFS desde A:");
        grafo.dfs("A");
    }
}
