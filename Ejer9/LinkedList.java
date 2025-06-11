public class LinkedList<T> {
    Node<T> head;
    private int size;

    public static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) { this.data = data; }
    }

    public LinkedList() {
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
        Node<T> temp = head;
        while (temp != null) {
            if (temp.data.equals(data)) return true;
            temp = temp.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public T get(int index) {
        Node<T> temp = head;
        int i = 0;
        while (temp != null) {
            if (i == index) return temp.data;
            temp = temp.next;
            i++;
        }
        throw new IndexOutOfBoundsException();
    }
}
