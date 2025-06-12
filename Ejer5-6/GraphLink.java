// GraphLink.java
public class GraphLink<E> {
    protected ListLinked<Vertex<E>> listVertex = new ListLinked<>();

    public void insertVertex(E var1) {
        if (this.searchVertexObject(var1) == null) {
            this.listVertex.add(new Vertex<E>(var1));
        }
    }

    public void insertEdge(E var1, E var2) {
        Vertex<E> var3 = this.searchVertexObject(var1);
        Vertex<E> var4 = this.searchVertexObject(var2);
        if (var3 != null && var4 != null) {
            if (!var3.listAdj.contains(new Edge<E>(var4))) {
                var3.listAdj.add(new Edge<E>(var4));
            }
            if (!var4.listAdj.contains(new Edge<E>(var3))) {
                var4.listAdj.add(new Edge<E>(var3));
            }
        }
    }

    public Vertex<E> searchVertexObject(E var1) {
        ListLinked.SimpleIterator<Vertex<E>> it = listVertex.simpleIterator();
        while (it.hasNext()) {
            Vertex<E> var3 = it.next();
            if (var3.getData().equals(var1)) {
                return var3;
            }
        }
        return null;
    }

    public boolean searchVertex(E var1) {
        return this.searchVertexObject(var1) != null;
    }

    public boolean searchEdge(E var1, E var2) {
        Vertex<E> var3 = this.searchVertexObject(var1);
        Vertex<E> var4 = this.searchVertexObject(var2);
        return var3 != null && var4 != null ? var3.listAdj.contains(new Edge<E>(var4)) : false;
    }

    public int degree(E var1) {
        Vertex<E> var2 = this.searchVertexObject(var1);
        return var2 == null ? -1 : var2.listAdj.size();
    }

    public boolean isPath() {
        int var1 = 0;
        int var2 = 0;
        ListLinked.SimpleIterator<Vertex<E>> it = listVertex.simpleIterator();
        while (it.hasNext()) {
            Vertex<E> var4 = it.next();
            int var5 = var4.listAdj.size();
            if (var5 == 1) {
                ++var1;
            } else if (var5 == 2) {
                ++var2;
            } else if (var5 > 2) {
                return false;
            }
        }
        return var1 == 2 && var1 + var2 == listVertex.size();
    }

    public boolean isCycle() {
        ListLinked.SimpleIterator<Vertex<E>> it = listVertex.simpleIterator();
        while (it.hasNext()) {
            Vertex<E> var2 = it.next();
            if (var2.listAdj.size() != 2) {
                return false;
            }
        }
        return true;
    }

    public boolean isWheel() {
        int var1 = listVertex.size();
        int var2 = 0;
        int var3 = 0;
        ListLinked.SimpleIterator<Vertex<E>> it = listVertex.simpleIterator();
        while (it.hasNext()) {
            Vertex<E> var5 = it.next();
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

    public boolean isComplete() {
        int var1 = listVertex.size();
        ListLinked.SimpleIterator<Vertex<E>> it = listVertex.simpleIterator();
        while (it.hasNext()) {
            Vertex<E> var3 = it.next();
            if (var3.listAdj.size() != var1 - 1) {
                return false;
            }
        }
        return true;
    }

    public void printFormal() {
        System.out.print("Vértices: { ");
        ListLinked.SimpleIterator<Vertex<E>> it = listVertex.simpleIterator();
        while (it.hasNext()) {
            Vertex<E> v = it.next();
            System.out.print(v.getData() + " ");
        }
        System.out.print("}\nAristas: { ");
        it = listVertex.simpleIterator();
        while (it.hasNext()) {
            Vertex<E> v = it.next();
            ListLinked.SimpleIterator<Edge<E>> itAdj = v.listAdj.simpleIterator();
            while (itAdj.hasNext()) {
                Edge<E> e = itAdj.next();
                if (v.getData().toString().compareTo(e.getRefDest().getData().toString()) < 0) {
                    System.out.print("{" + v.getData() + "," + e.getRefDest().getData() + "} ");
                }
            }
        }
        System.out.println("}");
    }

    public void printAdjacencyList() {
        ListLinked.SimpleIterator<Vertex<E>> it = listVertex.simpleIterator();
        while (it.hasNext()) {
            Vertex<E> v = it.next();
            System.out.print(v.getData() + ": ");
            ListLinked.SimpleIterator<Edge<E>> itAdj = v.listAdj.simpleIterator();
            while (itAdj.hasNext()) {
                Edge<E> e = itAdj.next();
                System.out.print(e.getRefDest().getData() + " ");
            }
            System.out.println();
        }
    }

    public void printAdjacencyMatrix() {
        int n = listVertex.size();
        Object[] vertices = new Object[n];
        int idx = 0;
        ListLinked.SimpleIterator<Vertex<E>> it = listVertex.simpleIterator();
        while (it.hasNext()) {
            Vertex<E> v = it.next();
            vertices[idx++] = v.getData();
        }
        System.out.print("   ");
        for (Object v : vertices) System.out.print(v + " ");
        System.out.println();
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
