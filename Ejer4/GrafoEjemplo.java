package org.example;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.*;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class GrafoEjemplo extends JFrame {

    public GrafoEjemplo() {
        setTitle("Visualización de Varios Grafos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2)); // 2 filas x 2 columnas

        add(crearPanelGrafoNoDirigido());
        add(crearPanelGrafoDirigido());
        add(crearPanelGrafoNoDirigidoPonderado());
        add(crearPanelGrafoDirigidoPonderado());

        setSize(1000, 800);
        setVisible(true);
    }

    private JPanel crearPanelGrafoNoDirigido() {
        Graph<String, DefaultEdge> grafo = new SimpleGraph<>(DefaultEdge.class);
        grafo.addVertex("A");
        grafo.addVertex("B");
        grafo.addVertex("C");
        grafo.addEdge("A", "B");
        grafo.addEdge("B", "C");

        JGraphXAdapter<String, DefaultEdge> adapter = new JGraphXAdapter<>(grafo);
        quitarFlechas(adapter);

        return crearPanelConTitulo("No dirigido (no ponderado)", adapter);
    }

    private JPanel crearPanelGrafoDirigido() {
        Graph<String, DefaultEdge> grafo = new DefaultDirectedGraph<>(DefaultEdge.class);
        grafo.addVertex("1");
        grafo.addVertex("2");
        grafo.addVertex("3");
        grafo.addEdge("1", "2");
        grafo.addEdge("2", "3");

        return crearPanelConTitulo("Dirigido (no ponderado)", new JGraphXAdapter<>(grafo));
    }

    private JPanel crearPanelGrafoNoDirigidoPonderado() {
        Graph<String, DefaultWeightedEdge> grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        grafo.addVertex("X");
        grafo.addVertex("Y");
        grafo.addVertex("Z");
        DefaultWeightedEdge e1 = grafo.addEdge("X", "Y");
        grafo.setEdgeWeight(e1, 2.5);
        DefaultWeightedEdge e2 = grafo.addEdge("Y", "Z");
        grafo.setEdgeWeight(e2, 3.0);

        JGraphXAdapter<String, DefaultWeightedEdge> adapter = new JGraphXAdapter<>(grafo);
        quitarFlechas(adapter);
        mostrarPesos(grafo, adapter);

        return crearPanelConTitulo("No dirigido (ponderado)", adapter);
    }

    private JPanel crearPanelGrafoDirigidoPonderado() {
        Graph<String, DefaultWeightedEdge> grafo = new DirectedWeightedMultigraph<>(DefaultWeightedEdge.class);
        grafo.addVertex("U");
        grafo.addVertex("V");
        grafo.addVertex("W");
        DefaultWeightedEdge e1 = grafo.addEdge("U", "V");
        grafo.setEdgeWeight(e1, 1.0);
        DefaultWeightedEdge e2 = grafo.addEdge("V", "W");
        grafo.setEdgeWeight(e2, 4.2);

        JGraphXAdapter<String, DefaultWeightedEdge> adapter = new JGraphXAdapter<>(grafo);
        mostrarPesos(grafo, adapter);

        return crearPanelConTitulo("Dirigido (ponderado)", adapter);
    }

    private <V, E> JPanel crearPanelConTitulo(String titulo, JGraphXAdapter<V, E> adapter) {
        mxCircleLayout layout = new mxCircleLayout(adapter);
        layout.execute(adapter.getDefaultParent());

        mxGraphComponent graphComponent = new mxGraphComponent(adapter);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(titulo, SwingConstants.CENTER), BorderLayout.NORTH);
        panel.add(graphComponent, BorderLayout.CENTER);
        return panel;
    }

    private void quitarFlechas(JGraphXAdapter<String, ?> adapter) {
        for (Object cellObj : adapter.getChildEdges(adapter.getDefaultParent())) {
            if (cellObj instanceof mxCell) {
                mxCell edge = (mxCell) cellObj;
                adapter.setCellStyle("endArrow=none", new Object[]{edge});
            }
        }
    }

    private void mostrarPesos(Graph<String, DefaultWeightedEdge> grafo, JGraphXAdapter<String, DefaultWeightedEdge> adapter) {
        Map<DefaultWeightedEdge, mxICell> edgeToCellMap = adapter.getEdgeToCellMap();
        for (DefaultWeightedEdge edge : grafo.edgeSet()) {
            double peso = grafo.getEdgeWeight(edge);
            mxICell cell = edgeToCellMap.get(edge);
            if (cell instanceof mxCell) {
                mxCell edgeCell = (mxCell) cell;
                edgeCell.setValue(String.valueOf(peso));
                adapter.getModel().setValue(edgeCell, String.valueOf(peso));
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GrafoEjemplo::new);
    }
}
