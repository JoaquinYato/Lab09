// Clase que representa un grafo dirigido usando listas enlazadas propias para vértices y aristas
public class Graph {
    // Lista de vértices (sin duplicados)
    private LinkedList<Integer> vertices;
    // Lista de aristas (cada arista es un objeto GraphLink)
    private GraphListEdge aristas;

    // Constructor: inicializa las listas de vértices y aristas
    public Graph() {
        vertices = new LinkedList<>();
        aristas = new GraphListEdge();
    }

    // Agrega un vértice al grafo si no existe aún
    public void agregarVertice(int v) {
        if (!vertices.contains(v)) vertices.add(v);
    }

    // Agrega una arista dirigida (origen -> destino) y asegura que ambos vértices existan
    public void agregarArista(int origen, int destino) {
        agregarVertice(origen);
        agregarVertice(destino);
        aristas.agregarArista(new GraphLink(origen, destino));
    }

    // Devuelve la lista de vértices del grafo
    public LinkedList<Integer> obtenerVertices() {
        return vertices;
    }

    // Devuelve la lista de aristas del grafo
    public LinkedList<GraphLink> obtenerAristas() {
        return aristas.obtenerAristas();
    }

    // Verifica si existe una arista dirigida entre dos vértices
    public boolean existeArista(int origen, int destino) {
        return aristas.contieneArista(new GraphLink(origen, destino));
    }

    // Devuelve los vecinos (destinos alcanzables directamente) de un vértice
    public LinkedList<Integer> vecinos(int v) {
        LinkedList<Integer> lista = new LinkedList<>();
        LinkedList<GraphLink> todas = aristas.obtenerAristas();
        LinkedList.Node<GraphLink> temp = todas.head;
        while (temp != null) {
            if (temp.data.origen == v) lista.add(temp.data.destino);
            temp = temp.next;
        }
        return lista;
    }

    // ----------- PROPIEDADES DEL GRAFO ------------

    // Verifica si el grafo es fuertemente conexo (todos los vértices son alcanzables desde cualquier otro)
    public boolean esConexo() {
        LinkedList.Node<Integer> temp = vertices.head;
        int totalVertices = vertices.size();
        while (temp != null) {
            LinkedList<Integer> visitados = new LinkedList<>();
            dfs(temp.data, visitados);
            if (visitados.size() != totalVertices) return false;
            temp = temp.next;
        }
        return true;
    }

    // Búsqueda en profundidad (DFS) para marcar vértices alcanzados
    private void dfs(int actual, LinkedList<Integer> visitados) {
        if (!visitados.contains(actual)) {
            visitados.add(actual);
            LinkedList<Integer> vecinosList = vecinos(actual);
            LinkedList.Node<Integer> temp = vecinosList.head;
            while (temp != null) {
                dfs(temp.data, visitados);
                temp = temp.next;
            }
        }
    }

    // Devuelve el complemento del grafo (mismas conexiones posibles, excepto las que ya existen)
    public Graph complemento() {
        Graph comp = new Graph();
        // Copia todos los vértices
        LinkedList.Node<Integer> tempU = vertices.head;
        while (tempU != null) {
            comp.agregarVertice(tempU.data);
            tempU = tempU.next;
        }
        // Agrega aristas que no existen en el grafo original
        tempU = vertices.head;
        while (tempU != null) {
            LinkedList.Node<Integer> tempV = vertices.head;
            while (tempV != null) {
                if (tempU.data != tempV.data && !existeArista(tempU.data, tempV.data)) {
                    comp.agregarArista(tempU.data, tempV.data);
                }
                tempV = tempV.next;
            }
            tempU = tempU.next;
        }
        return comp;
    }

    // Verifica si el grafo es isomorfo a otro (misma estructura bajo renombramiento de vértices)
    public boolean esIsomorfo(Graph otro) {
        if (vertices.size() != otro.vertices.size() || aristas.obtenerAristas().size() != otro.aristas.obtenerAristas().size())
            return false;

        LinkedList<Integer> listaV1 = copiarLista(vertices);
        LinkedList<Integer> listaV2 = copiarLista(otro.vertices);

        return permutacionesIsomorfas(listaV1, listaV2, otro);
    }

    // Copia una lista enlazada de enteros
    private LinkedList<Integer> copiarLista(LinkedList<Integer> original) {
        LinkedList<Integer> copia = new LinkedList<>();
        LinkedList.Node<Integer> temp = original.head;
        while (temp != null) {
            copia.add(temp.data);
            temp = temp.next;
        }
        return copia;
    }

    // Genera todas las permutaciones posibles de vértices para verificar isomorfismo (fuerza bruta)
    private boolean permutacionesIsomorfas(LinkedList<Integer> v1, LinkedList<Integer> v2, Graph otro) {
        int n = v1.size();
        boolean[] usados = new boolean[n];
        int[] mapeo = new int[n];
        return permutacionesIsomorfasHelper(v1, v2, usados, mapeo, otro, 0);
    }

    // Ayudante recursivo para probar todas las permutaciones de isomorfismo
    private boolean permutacionesIsomorfasHelper(LinkedList<Integer> v1, LinkedList<Integer> v2, boolean[] usados, int[] mapeo, Graph otro, int idx) {
        int n = v1.size();
        if (idx == n) {
            // Valida que la correspondencia de vértices conserva las aristas
            LinkedList.Node<GraphLink> temp = aristas.obtenerAristas().head;
            while (temp != null) {
                int origen = temp.data.origen;
                int destino = temp.data.destino;
                int origenMap = -1, destinoMap = -1;
                for (int i = 0; i < n; i++) {
                    if (v1.get(i) == origen) origenMap = v2.get(mapeo[i]);
                    if (v1.get(i) == destino) destinoMap = v2.get(mapeo[i]);
                }
                if (!otro.existeArista(origenMap, destinoMap)) return false;
                temp = temp.next;
            }
            return true;
        }
        for (int i = 0; i < n; i++) {
            if (!usados[i]) {
                usados[i] = true;
                mapeo[idx] = i;
                if (permutacionesIsomorfasHelper(v1, v2, usados, mapeo, otro, idx + 1)) return true;
                usados[i] = false;
            }
        }
        return false;
    }

    // Verifica si el grafo es auto-complementario (isomorfo a su complemento)
    public boolean esAutoComplementario() {
        Graph comp = complemento();
        return esIsomorfo(comp);
    }

    // Verifica si el grafo es plano usando la desigualdad de Euler (solo para grafos pequeños y simples)
    public boolean esPlano() {
        int n = vertices.size();
        int m = aristas.obtenerAristas().size();
        if (n < 5) return true;
        return m <= 3 * n - 6;
    }
}
