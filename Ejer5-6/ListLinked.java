// ListLinked.java
public class ListLinked<T> {
    private Node<T> head;
    private int size;

    protected static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) { this.data = data; }
    }

    // Iterador público y estático
    public static class SimpleIterator<T> {
        private Node<T> current;

        public SimpleIterator(Node<T> head) {
            this.current = head;
        }

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            if (!hasNext()) {
                throw new RuntimeException("No more elements");
            }
            T data = current.data;
            current = current.next;
            return data;
        }
    }

    public ListLinked() {
        head = null;
        size = 0;
    }

    public void add(T data) {
        Node<T> newNode = new Node<T>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> temp = head;
            while (temp.next != null) temp = temp.next;
            temp.next = newNode;
        }
        size++;
    }

    public boolean contains(T data) {
        SimpleIterator<T> it = simpleIterator();
        while (it.hasNext()) {
            T item = it.next();
            if (item.equals(data)) return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public SimpleIterator<T> simpleIterator() {
        return new SimpleIterator<T>(head);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        SimpleIterator<T> it = simpleIterator();
        while (it.hasNext()) {
            sb.append(it.next());
        }
        return sb.toString();
    }
}
