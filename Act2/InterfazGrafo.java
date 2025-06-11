import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class InterfazGrafo extends JFrame {
    private GraphLink<String> grafo;
    private boolean esDirigido;
    private boolean esPonderado;
    private DibujoPanel panel;

    private ArrayList<String> vertices;
    private ArrayList<AristaGrafica> aristas;

    public InterfazGrafo() {
        // Preguntar si es dirigido y/o ponderado
        esDirigido = JOptionPane.showConfirmDialog(null, "¿El grafo es dirigido?", "Tipo de grafo", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
        esPonderado = JOptionPane.showConfirmDialog(null, "¿El grafo es ponderado?", "Tipo de grafo", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

        grafo = new GraphLink<>();
        vertices = new ArrayList<>();
        aristas = new ArrayList<>();

        setTitle("Editor de Grafo");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        panel = new DibujoPanel();
        add(panel, BorderLayout.CENTER);

        JPanel botones = new JPanel();

        JButton btnAgregarVertice = new JButton("Agregar vértice");
        btnAgregarVertice.addActionListener(e -> agregarVertice());

        JButton btnAgregarArista = new JButton("Agregar arista");
        btnAgregarArista.addActionListener(e -> agregarArista());

        botones.add(btnAgregarVertice);
        botones.add(btnAgregarArista);

        add(botones, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void agregarVertice() {
        String nombre = JOptionPane.showInputDialog(this, "Ingrese nombre del vértice:");
        if (nombre != null && !nombre.isEmpty() && !vertices.contains(nombre)) {
            grafo.insertVertex(nombre);
            vertices.add(nombre);
            panel.repaint();
        }
    }

    private void agregarArista() {
        if (vertices.size() < 2) {
            JOptionPane.showMessageDialog(this, "Agrega al menos dos vértices.");
            return;
        }

        String origen = (String) JOptionPane.showInputDialog(this, "Origen:", "Vértices", JOptionPane.PLAIN_MESSAGE, null, vertices.toArray(), null);
        String destino = (String) JOptionPane.showInputDialog(this, "Destino:", "Vértices", JOptionPane.PLAIN_MESSAGE, null, vertices.toArray(), null);

        int peso = 1;
        if (esPonderado) {
            try {
                peso = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el peso de la arista:"));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Peso inválido. Se usará 1.");
            }
        }

        if (esDirigido) {
            grafo.insertEdgePon(origen, destino, peso);
        } else {
            grafo.insertEdgeNoDirigido(origen, destino, peso);
        }

        aristas.add(new AristaGrafica(origen, destino, peso));
        panel.repaint();
    }

    class DibujoPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int radio = 30;
            int centroX = getWidth() / 2;
            int centroY = getHeight() / 2;
            int r = Math.min(centroX, centroY) - 60;
            double angulo = 2 * Math.PI / vertices.size();

            // Posiciones de los vértices
            Point[] posiciones = new Point[vertices.size()];
            for (int i = 0; i < vertices.size(); i++) {
                int x = (int) (centroX + r * Math.cos(i * angulo));
                int y = (int) (centroY + r * Math.sin(i * angulo));
                posiciones[i] = new Point(x, y);
            }

            // Dibujar aristas
            for (AristaGrafica a : aristas) {
                int i = vertices.indexOf(a.origen);
                int j = vertices.indexOf(a.destino);

                Point p1 = posiciones[i];
                Point p2 = posiciones[j];

                g.setColor(Color.BLACK);
                drawArrowLine(g, p1.x, p1.y, p2.x, p2.y, esDirigido ? 10 : 0, 6);

                if (esPonderado) {
                    int midX = (p1.x + p2.x) / 2;
                    int midY = (p1.y + p2.y) / 2;
                    g.setColor(Color.RED);
                    g.drawString(String.valueOf(a.peso), midX, midY);
                }
            }

            // Dibujar vértices
            for (int i = 0; i < vertices.size(); i++) {
                Point p = posiciones[i];
                g.setColor(Color.YELLOW);
                g.fillOval(p.x - radio / 2, p.y - radio / 2, radio, radio);
                g.setColor(Color.BLACK);
                g.drawOval(p.x - radio / 2, p.y - radio / 2, radio, radio);
                g.drawString(vertices.get(i), p.x - 10, p.y + 4);
            }
        }

        private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
            if (!esDirigido) {
                g.drawLine(x1, y1, x2, y2);
                return;
            }

            int dx = x2 - x1, dy = y2 - y1;
            double D = Math.sqrt(dx * dx + dy * dy);
            double xm = D - d, xn = xm, ym = h, yn = -h, x;
            double sin = dy / D, cos = dx / D;

            x = xm * cos - ym * sin + x1;
            ym = xm * sin + ym * cos + y1;
            xm = x;

            x = xn * cos - yn * sin + x1;
            yn = xn * sin + yn * cos + y1;
            xn = x;

            int[] xpoints = {x2, (int) xm, (int) xn};
            int[] ypoints = {y2, (int) ym, (int) yn};

            g.drawLine(x1, y1, x2, y2);
            g.fillPolygon(xpoints, ypoints, 3);
        }
    }

    static class AristaGrafica {
        String origen, destino;
        int peso;

        AristaGrafica(String o, String d, int p) {
            origen = o;
            destino = d;
            peso = p;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfazGrafo::new);
    }
}
