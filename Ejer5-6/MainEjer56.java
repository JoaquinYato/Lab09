public class MainEjer56 {
    public static void main(String[] args) {
        // Crear un grafo no dirigido de ejemplo
        GraphLink<String> grafo = new GraphLink<>();

        // Insertar vértices
        grafo.insertVertex("A");
        grafo.insertVertex("B");
        grafo.insertVertex("C");
        grafo.insertVertex("D");

        // Insertar aristas (puedes modificar para probar diferentes tipos de grafos)
        grafo.insertEdge("A", "B");
        grafo.insertEdge("B", "C");
        grafo.insertEdge("C", "D");
        //grafo.insertEdge("D", "A"); // Descomenta para formar un ciclo

        // Mostrar el grafo
        System.out.println("Grafo:");
        System.out.println(grafo);

        // Probar métodos del ejercicio 5
        System.out.println("Grado de B: " + grafo.degree("B"));

        System.out.println("¿Es camino? " + grafo.isPath());
        System.out.println("¿Es ciclo? " + grafo.isCycle());
        System.out.println("¿Es rueda? " + grafo.isWheel());
        System.out.println("¿Es completo? " + grafo.isComplete());

        // Ejercicio 6

        System.out.println("\nRepresentación formal:");
        grafo.printFormal();

        System.out.println("\nLista de adyacencias:");
        grafo.printAdjacencyList();

        System.out.println("\nMatriz de adyacencia:");
        grafo.printAdjacencyMatrix();

    }
}
