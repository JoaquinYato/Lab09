package model;

import java.util.*;

public class Grafo {
    private Map<Integer, List<Integer>> adyacencias;

    public Grafo() {
        adyacencias = new HashMap<>();
    }

    public void agregarVertice(int vertice) {
        adyacencias.putIfAbsent(vertice, new ArrayList<>());
    }

    public void agregarArista(int origen, int destino) {
        adyacencias.putIfAbsent(origen, new ArrayList<>());
        adyacencias.putIfAbsent(destino, new ArrayList<>());
        adyacencias.get(origen).add(destino);
        adyacencias.get(destino).add(origen); // Si el grafo es dirigido, elimina esta línea
    }

    public List<Integer> obtenerVecinos(int vertice) {
        return adyacencias.getOrDefault(vertice, new ArrayList<>());
    }

    public void mostrarGrafo() {
        for (int vertice : adyacencias.keySet()) {
            System.out.println(vertice + " -> " + adyacencias.get(vertice));
        }
    }
}
