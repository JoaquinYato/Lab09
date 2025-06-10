import java.util.ArrayList;

public class GraphListEdge<V> {
    ArrayList<VertexDir<V>> secVertex;
    ArrayList<EdgeDir<V>> secEdge;

    public GraphListEdge() {
        this.secVertex = new ArrayList<>();
        this.secEdge = new ArrayList<>();
    }

    public void insertVertex(V data) {
        if (searchVertexObject(data) == null) {
            secVertex.add(new VertexDir<V>(data));
        }
    }

    public void insertEdge(V vOri, V vDes) {
        VertexDir<V> ori = searchVertexObject(vOri);
        VertexDir<V> des = searchVertexObject(vDes);
        if (ori == null || des == null) return;
        EdgeDir<V> edge = new EdgeDir<>(des);
        if (!ori.listAdj.contains(edge)) {
            ori.listAdj.add(edge);
            secEdge.add(edge);
        }
    }

    public VertexDir<V> searchVertexObject(V data) {
        for (VertexDir<V> v : secVertex) {
            if (v.getData().equals(data)) return v;
        }
        return null;
    }

    public int outDegree(V vertexData) {
        VertexDir<V> vertex = searchVertexObject(vertexData);
        if (vertex == null) return -1;
        return vertex.listAdj.size();
    }

    public int inDegree(V vertexData) {
        VertexDir<V> vertex = searchVertexObject(vertexData);
        if (vertex == null) return -1;
        int count = 0;
        for (VertexDir<V> v : secVertex) {
            if (v.listAdj.contains(new EdgeDir<V>(vertex))) count++;
        }
        return count;
    }

    public boolean isDirectedPath() {
        int start = 0, end = 0, middle = 0;
        for (VertexDir<V> v : secVertex) {
            int in = inDegree(v.getData());
            int out = outDegree(v.getData());
            if (in == 0 && out == 1) start++;
            else if (in == 1 && out == 0) end++;
            else if (in == 1 && out == 1) middle++;
            else return false;
        }
        return start == 1 && end == 1 && (start + end + middle == secVertex.size());
    }

    public boolean isDirectedCycle() {
        for (VertexDir<V> v : secVertex) {
            if (inDegree(v.getData()) != 1 || outDegree(v.getData()) != 1) return false;
        }
        return true;
    }

    public boolean isDirectedWheel() {
        int n = secVertex.size();
        int center = 0, periphery = 0;
        for (VertexDir<V> v : secVertex) {
            int in = inDegree(v.getData());
            int out = outDegree(v.getData());
            if (in == 0 && out == n - 1) center++;
            else if (in == 1 && out == 1) periphery++;
            else return false;
        }
        return center == 1 && periphery == n - 1;
    }

    @Override
    public String toString() {
        return secVertex.toString();
    }

    public void printFormal() {
        System.out.print("Vértices: { ");
        for (VertexDir<V> v : secVertex) {
            System.out.print(v.getData() + " ");
        }
        System.out.print("}\nAristas: { ");
        for (EdgeDir<V> e : secEdge) {
            System.out.print(e.toString() + " ");
        }
        System.out.println("}");
    }
    public void printAdjacencyList() {
        for (VertexDir<V> v : secVertex) {
            System.out.print(v.getData() + ": ");
            for (EdgeDir<V> e : v.listAdj) {
                System.out.print(e.getRefDest().getData() + " ");
            }
            System.out.println();
        }
    }

    public void printAdjacencyMatrix() {
        int n = secVertex.size();
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            VertexDir<V> v = secVertex.get(i);
            for (EdgeDir<V> e : v.listAdj) {
                int j = secVertex.indexOf(e.getRefDest());
                matrix[i][j] = 1;
            }
        }
        System.out.print("  ");
        for (VertexDir<V> v : secVertex) {
            System.out.print(v.getData() + " ");
        }
        System.out.println();
        for (int i = 0; i < n; i++) {
            System.out.print(secVertex.get(i).getData() + " ");
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }       
}
