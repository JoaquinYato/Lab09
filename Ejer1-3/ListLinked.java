public class ListLinked<T extends Comparable<T>> {
    public class Nodo {
        public T dato;
        public Nodo siguiente;

        public Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }


    }
    private Nodo primero;
    private int numElem;

    public ListLinked() {
        primero = null;
        numElem = 0;
    }

    public Nodo getPrimero(){return primero;}

    public Nodo getSiguiente(Nodo actual){return actual.siguiente;}

    public T getDato(Nodo actual){return actual.dato;}

    public void setDato(Nodo n, T data){n.dato=data;}

    public void agregar(T data) {
        Nodo nuevo = new Nodo(data);
        if (primero == null) {
            primero = nuevo;
        } else {
            Nodo actual = primero;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        numElem++;
    }

    public void insertarFirst(T data) {
        Nodo nuevo = new Nodo(data);
        nuevo.siguiente = primero;
        primero = nuevo;
        numElem++;
    }

    public void insercionIntermedia(T data) {
        Nodo nuevo = new Nodo(data);
        if (primero == null || data.compareTo(primero.dato) < 0) {
            nuevo.siguiente = primero;
            primero = nuevo;
        } else {
            Nodo actual = primero;
            while (actual.siguiente != null && data.compareTo(actual.siguiente.dato) > 0) {
                actual = actual.siguiente;
            }
            nuevo.siguiente = actual.siguiente;
            actual.siguiente = nuevo;
        }
        numElem++;
    }

    public boolean removeNode(T data) {
        if (primero == null) {
            return false;
        }
        if (primero.dato.equals(data)) {
            primero = primero.siguiente;
            numElem--;
            return true;
        } else {
            Nodo actual = primero;
            while (actual.siguiente != null && !actual.siguiente.dato.equals(data)) {
                actual = actual.siguiente;
            }
            if (actual.siguiente == null) {
                return false;
            } else {
                actual.siguiente = actual.siguiente.siguiente;
                numElem--;
                return true;
            }
        }
    }

    public int length() {
        return numElem;
    }

    public void destroyList() {
        primero = null;
        numElem = 0;
    }

    public boolean inEmptyList() {
        return primero == null;
    }

    public boolean compararListas(ListLinked<T> otraLista) {
        Nodo actual1 = this.primero;
        Nodo actual2 = otraLista.primero;

        while (actual1 != null && actual2 != null) {
            if (!actual1.dato.equals(actual2.dato)) {
                return false;
            }
            actual1 = actual1.siguiente;
            actual2 = actual2.siguiente;
        }

        return actual1 == null && actual2 == null;
    }

    public void concatenar(ListLinked<T> otraLista) {
        if (otraLista.primero == null) {
            return;
        }

        if (this.primero == null) {
            this.primero = otraLista.primero;
        } else {
            Nodo actual = this.primero;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = otraLista.primero;
        }
        this.numElem += otraLista.numElem;
    }

    public void ordenar() {
        if (primero == null || primero.siguiente == null) {
            return;
        }
        Nodo actual = primero.siguiente;
        while (actual != null) {
            Nodo temp = primero;
            while (temp != actual) {
                if (actual.dato.compareTo(temp.dato) < 0) {
                    T prev = temp.dato;
                    temp.dato = actual.dato;
                    actual.dato = prev;
                }
                temp = temp.siguiente;
            }
            actual = actual.siguiente;
        }
    }

    public void ordenarInsertion() {
        if (primero == null || primero.siguiente == null) {
            return;
        }

        ListLinked<T> listaOrdenada = new ListLinked<>();

        Nodo actual = primero;

        while (actual != null) {
            listaOrdenada.insercionIntermedia(actual.dato);
            actual = actual.siguiente;
        }
        this.primero = listaOrdenada.primero;
        this.numElem = listaOrdenada.numElem;
    }

    public void mostrar() {
        Nodo actual = primero;
        while (actual != null) {
            System.out.println(actual.dato);
            actual = actual.siguiente;
        }
    }

    public void eliminarGrandes(int n){
        if (inEmptyList()){
            return;
        }
        for (int i = 0; i<n; i++){
            Nodo actual = primero;
            Nodo maxNodo = primero;
            Nodo maxAnterior = null;

            while(actual != null && actual.siguiente != null){
                if(actual.siguiente.dato.compareTo(maxNodo.dato) > 0){
                    maxAnterior = actual;
                    maxNodo=actual.siguiente;
                }
                actual=actual.siguiente;
            }

            if(maxNodo == primero){
                primero = primero.siguiente;
            }else if(maxAnterior != null){
                maxAnterior.siguiente = maxNodo.siguiente;
            }
        }
    }

    public boolean buscar (T dato){
        Nodo actual = primero;
        while (actual != null){
            if(actual.dato.equals(dato)) return true;
            actual = actual.siguiente;
        }
        return false;
    }
}

