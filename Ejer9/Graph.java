import java.util.*;

public class Graph {
    private Set<Integer> vertices;
    private GraphListEdge aristas;

    public Graph() {
        vertices = new HashSet<>();
        aristas = new GraphListEdge();
    }

    public void agregarVertice(int v) {
        vertices.add(v);
    }

    public void agregarArista(int origen, int destino) {
        vertices.add(origen);
        vertices.add(destino);
        aristas.agregarArista(new GraphLink(origen, destino));
    }

    public Set<Integer> obtenerVertices() {
        return vertices;
    }

    public List<GraphLink> obtenerAristas() {
        return aristas.obtenerAristas();
    }

    public boolean existeArista(int origen, int destino) {
        return aristas.contieneArista(new GraphLink(origen, destino));
    }

    public List<Integer> vecinos(int v) {
        List<Integer> lista = new ArrayList<>();
        for (GraphLink arista : aristas.obtenerAristas()) {
            if (arista.origen == v) lista.add(arista.destino);
        }
        return lista;
    }

    // ----------- PROPIEDADES ------------

    // Conexo (fuertemente conexo para dirigido)
    public boolean esConexo() {
        for (int v : vertices) {
            Set<Integer> visitados = new HashSet<>();
            dfs(v, visitados);
            if (visitados.size() != vertices.size()) return false;
        }
        return true;
    }

    private void dfs(int actual, Set<Integer> visitados) {
        visitados.add(actual);
        for (int vecino : vecinos(actual)) {
            if (!visitados.contains(vecino)) {
                dfs(vecino, visitados);
            }
        }
    }

    // Complemento
    public Graph complemento() {
        Graph comp = new Graph();
        for (int v : vertices) comp.agregarVertice(v);
        for (int u : vertices) {
            for (int v : vertices) {
                if (u != v && !existeArista(u, v)) {
                    comp.agregarArista(u, v);
                }
            }
        }
        return comp;
    }

    // Isomorfismo (solo para grafos pequeños, fuerza bruta)
    public boolean esIsomorfo(Graph otro) {
        if (vertices.size() != otro.vertices.size() || aristas.obtenerAristas().size() != otro.aristas.obtenerAristas().size())
            return false;

        List<Integer> listaV1 = new ArrayList<>(vertices);
        List<Integer> listaV2 = new ArrayList<>(otro.vertices);

        return permutacionesIsomorfas(listaV1, listaV2, otro);
    }

    private boolean permutacionesIsomorfas(List<Integer> v1, List<Integer> v2, Graph otro) {
        return permutacionesIsomorfasHelper(v1, v2, new boolean[v2.size()], new HashMap<>(), otro, 0);
    }

    private boolean permutacionesIsomorfasHelper(List<Integer> v1, List<Integer> v2, boolean[] usados, Map<Integer, Integer> mapeo, Graph otro, int idx) {
        if (idx == v1.size()) {
            // Verificar si la correspondencia es válida
            for (GraphLink arista : aristas.obtenerAristas()) {
                int origenMap = mapeo.get(arista.origen);
                int destinoMap = mapeo.get(arista.destino);
                if (!otro.existeArista(origenMap, destinoMap)) return false;
            }
            return true;
        }
        for (int i = 0; i < v2.size(); i++) {
            if (!usados[i]) {
                usados[i] = true;
                mapeo.put(v1.get(idx), v2.get(i));
                if (permutacionesIsomorfasHelper(v1, v2, usados, mapeo, otro, idx + 1)) return true;
                usados[i] = false;
                mapeo.remove(v1.get(idx));
            }
        }
        return false;
    }

    // Auto-complementario
    public boolean esAutoComplementario() {
        Graph comp = complemento();
        return esIsomorfo(comp);
    }

    // Plano (heurístico simple para grafos pequeños)
    public boolean esPlano() {
        // Para grafos pequeños, usar el teorema de Kuratowski o la desigualdad de Euler para grafos simples conectados
        // |E| <= 3|V| - 6 para grafos simples, conectados y sin lazos múltiples, con |V| >= 3
        int n = vertices.size();
        int m = aristas.obtenerAristas().size();
        if (n < 5) return true; // Todos los grafos con menos de 5 vértices son planos
        return m <= 3 * n - 6;
    }
}
