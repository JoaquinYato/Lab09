public class GraphLink<E> {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected ListLinked<Vertex<E>> listVertex = new ListLinked();


    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void insertVertex(E var1) {
        if (this.searchVertexObject(var1) == null) {
            this.listVertex.add(new Vertex(var1));
        }

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void insertEdge(E var1, E var2) {
        Vertex var3 = this.searchVertexObject(var1);
        Vertex var4 = this.searchVertexObject(var2);
        if (var3 != null && var4 != null) {
            if (!var3.listAdj.contains(new Edge(var4))) {
                var3.listAdj.add(new Edge(var4));
            }

            if (!var4.listAdj.contains(new Edge(var3))) {
                var4.listAdj.add(new Edge(var3));
            }

        }
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Vertex<E> searchVertexObject(E var1) {
        for(Vertex var3 : this.listVertex) {
            if (var3.getData().equals(var1)) {
                return var3;
            }
        }

        return null;
    }

    public boolean searchVertex(E var1) {
        return this.searchVertexObject(var1) != null;
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public boolean searchEdge(E var1, E var2) {
        Vertex var3 = this.searchVertexObject(var1);
        Vertex var4 = this.searchVertexObject(var2);
        return var3 != null && var4 != null ? var3.listAdj.contains(new Edge(var4)) : false;
    }
    @SuppressWarnings("rawtypes")
    public int degree(E var1) {
        Vertex var2 = this.searchVertexObject(var1);
        return var2 == null ? -1 : var2.listAdj.size();
    }
    @SuppressWarnings("rawtypes")
    public boolean isPath() {
        int var1 = 0;
        int var2 = 0;

        for(Vertex var4 : this.listVertex) {
            int var5 = var4.listAdj.size();
            if (var5 == 1) {
                ++var1;
            } else if (var5 == 2) {
                ++var2;
            } else if (var5 > 2) {
                return false;
            }
        }

        return var1 == 2 && var1 + var2 == this.listVertex.size();
    }

    @SuppressWarnings("rawtypes")
    public boolean isCycle() {
        for(Vertex var2 : this.listVertex) {
            if (var2.listAdj.size() != 2) {
                return false;
            }
        }

        return true;
    }

    @SuppressWarnings("rawtypes")
    public boolean isWheel() {
        int var1 = this.listVertex.size();
        int var2 = 0;
        int var3 = 0;

        for(Vertex var5 : this.listVertex) {
            int var6 = var5.listAdj.size();
            if (var6 == var1 - 1) {
                ++var2;
            } else if (var6 == 3) {
                ++var3;
            } else if (var6 != 3 && var6 != var1 - 1) {
                return false;
            }
        }

        return var2 == 1 && var3 == var1 - 1;
    }

    @SuppressWarnings("rawtypes")
    public boolean isComplete() {
        int var1 = this.listVertex.size();

        for(Vertex var3 : this.listVertex) {
            if (var3.listAdj.size() != var1 - 1) {
                return false;
            }
        }

        return true;
    }

    // Ejercicio 6

    public void printFormal() {
        System.out.print("Vértices: { ");
        for (Vertex<E> v : listVertex) {
            System.out.print(v.getData() + " ");
        }
        System.out.print("}\nAristas: { ");
        for (Vertex<E> v : listVertex) {
            for (Edge<E> e : v.listAdj) {
                if (v.getData().toString().compareTo(e.getRefDest().getData().toString()) < 0) {
                    System.out.print("{" + v.getData() + "," + e.getRefDest().getData() + "} ");
                }
            }
        }
        System.out.println("}");
    }

    public void printAdjacencyList() {
        for (Vertex<E> v : listVertex) {
            System.out.print(v.getData() + ": ");
            for (Edge<E> e : v.listAdj) {
                System.out.print(e.getRefDest().getData() + " ");
            }
            System.out.println();
        }
    }

    @SuppressWarnings("unchecked")
    public void printAdjacencyMatrix() {
        int n = listVertex.size();
        Object[] vertices = new Object[n];
        int idx = 0;
        for (Vertex<E> v : listVertex) {
            vertices[idx++] = v.getData();
        }
// Encabezado
        System.out.print("   ");
        for (Object v : vertices) System.out.print(v + " ");
        System.out.println();
// Matriz
        for (int i = 0; i < n; i++) {
            System.out.print(vertices[i] + ": ");
            for (int j = 0; j < n; j++) {
                Vertex<E> vi = searchVertexObject((E)vertices[i]);
                Vertex<E> vj = searchVertexObject((E)vertices[j]);
                System.out.print(vi.listAdj.contains(new Edge<E>(vj)) ? "1 " : "0 ");
            }
            System.out.println();
        }
    }

    public String toString() {
        return this.listVertex.toString();
    }

}

