import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JGraphTExample extends JFrame {
    private Graph<String, DefaultEdge> graph;
    private JTextArea displayArea;
    private JTextField vertexInput1, vertexInput2;

    public JGraphTExample() {
        super("Ejemplo de Grafo con JGraphT y Swing");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Creamos un grafo no dirigido simple con vertices String y aristas DefaultEdge
        graph = new SimpleGraph<>(DefaultEdge.class);

        // Panel para botones y entradas
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        vertexInput1 = new JTextField(5);
        vertexInput2 = new JTextField(5);

        JButton addVertexButton = new JButton("Agregar Nodo");
        JButton addEdgeButton = new JButton("Agregar Arista");
        JButton showGraphButton = new JButton("Mostrar Grafo");

        controlPanel.add(new JLabel("Nodo:"));
        controlPanel.add(vertexInput1);
        controlPanel.add(addVertexButton);

        controlPanel.add(new JLabel("Desde:"));
        controlPanel.add(vertexInput1);
        controlPanel.add(new JLabel("Hasta:"));
        controlPanel.add(vertexInput2);
        controlPanel.add(addEdgeButton);

        controlPanel.add(showGraphButton);

        add(controlPanel, BorderLayout.NORTH);

        // Área de texto para mostrar grafo
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // Acción botón agregar nodo
        addVertexButton.addActionListener(e -> {
            String vertex = vertexInput1.getText().trim();
            if (!vertex.isEmpty()) {
                if (graph.addVertex(vertex)) {
                    displayArea.append("Nodo '" + vertex + "' agregado.\n");
                } else {
                    displayArea.append("Nodo '" + vertex + "' ya existe.\n");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Introduce un nombre válido para el nodo.");
            }
        });

        // Acción botón agregar arista
        addEdgeButton.addActionListener(e -> {
            String v1 = vertexInput1.getText().trim();
            String v2 = vertexInput2.getText().trim();
            if (v1.isEmpty() || v2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Introduce ambos nodos para la arista.");
                return;
            }
            if (!graph.containsVertex(v1) || !graph.containsVertex(v2)) {
                displayArea.append("Uno o ambos nodos no existen.\n");
                return;
            }
            if (graph.addEdge(v1, v2) != null) {
                displayArea.append("Arista entre '" + v1 + "' y '" + v2 + "' agregada.\n");
            } else {
                displayArea.append("La arista ya existe.\n");
            }
        });

        // Acción botón mostrar grafo
        showGraphButton.addActionListener(e -> mostrarGrafo());

    }

    private void mostrarGrafo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Grafo actual:\n");
        for (String vertex : graph.vertexSet()) {
            sb.append("Nodo ").append(vertex).append(" conectado a: ");
            for (DefaultEdge edge : graph.edgesOf(vertex)) {
                String source = graph.getEdgeSource(edge);
                String target = graph.getEdgeTarget(edge);
                String connectedVertex = source.equals(vertex) ? target : source;
                sb.append(connectedVertex).append(" ");
            }
            sb.append("\n");
        }
        displayArea.append(sb.toString());
    }

    public static void main(String[] args) {
        // Para que se vea con el estilo nativo del SO
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            JGraphTExample frame = new JGraphTExample();
            frame.setVisible(true);
        });
    }
}
