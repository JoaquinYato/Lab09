import model.*;

public class Main {
    public static void main(String[] args) {
        Grafo grafo = new Grafo();

        grafo.agregarVertice(1);
        grafo.agregarVertice(2);
        grafo.agregarVertice(3);
        grafo.agregarVertice(4);

        grafo.agregarArista(1, 2);
        grafo.agregarArista(1, 3);
        grafo.agregarArista(2, 4);

        grafo.mostrarGrafo();
    }
}
