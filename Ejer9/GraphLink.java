public class GraphLink {
    public int origen;
    public int destino;

    public GraphLink(int origen, int destino) {
        this.origen = origen;
        this.destino = destino;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof GraphLink)) return false;
        GraphLink other = (GraphLink) obj;
        return origen == other.origen && destino == other.destino;
    }

    @Override
    public int hashCode() {
        return 31 * origen + destino;
    }
}
