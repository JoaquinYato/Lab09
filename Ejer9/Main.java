public class Main {
    public static void main(String[] args) {
        Graph grafo1 = new Graph();
        grafo1.agregarArista(1, 2);
        grafo1.agregarArista(2, 3);
        grafo1.agregarArista(3, 4);
        grafo1.agregarArista(4, 1);

        Graph grafo2 = new Graph();
        grafo2.agregarArista(5, 6);
        grafo2.agregarArista(6, 7);
        grafo2.agregarArista(7, 8);
        grafo2.agregarArista(8, 5);

        System.out.println("Grafo 1 es conexo: " + grafo1.esConexo());
        System.out.println("Grafo 1 es plano: " + grafo1.esPlano());
        System.out.println("Grafo 1 es auto-complementario: " + grafo1.esAutoComplementario());
        System.out.println("Grafo 1 y Grafo 2 son isomorfos: " + grafo1.esIsomorfo(grafo2));
    }
}
