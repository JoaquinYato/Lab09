public class Main78 {
    public static void main(String[] args) {
// Prueba con lista de adyacencia dirigida
        GraphLinkDir<String> g = new GraphLinkDir<>();
        g.insertVertex("A");
        g.insertVertex("B");
        g.insertVertex("C");
        g.insertVertex("D");
        g.insertEdge("A", "B");
        g.insertEdge("B", "C");
        g.insertEdge("C", "B");
        g.insertEdge("D", "B");

        System.out.println("Lista de adyacencia dirigida:");
        System.out.println(g);
        System.out.println("Grado de entrada de B: " + g.inDegree("B"));
        System.out.println("Grado de salida de B: " + g.outDegree("B"));
        System.out.println("¿Es camino dirigido? " + g.isDirectedPath());
        System.out.println("¿Es ciclo dirigido? " + g.isDirectedCycle());
        System.out.println("¿Es rueda dirigida? " + g.isDirectedWheel());

        // Prueba con lista de aristas dirigida
        GraphListEdge<String> g2 = new GraphListEdge<>();
        g2.insertVertex("A");
        g2.insertVertex("B");
        g2.insertVertex("C");
        g2.insertVertex("D");
        g2.insertEdge("A", "B");
        g2.insertEdge("B", "C");
        g2.insertEdge("C", "D");
        g2.insertEdge("D", "B"); // Descomentar para probar con un ciclo

        System.out.println("\nLista de aristas dirigida:");
        System.out.println(g2);
        System.out.println("Grado de entrada de B: " + g2.inDegree("B"));
        System.out.println("Grado de salida de B: " + g2.outDegree("B"));
        System.out.println("¿Es camino dirigido? " + g2.isDirectedPath());
        System.out.println("¿Es ciclo dirigido? " + g2.isDirectedCycle());
        System.out.println("¿Es rueda dirigida? " + g2.isDirectedWheel());

        System.out.println("\nRepresentación formal:");
        g2.printFormal();
        System.out.println("\nLista de adyacencias:");
        g2.printAdjacencyList();
        System.out.println("\nMatriz de adyacencia:");
        g2.printAdjacencyMatrix();
    }
}
