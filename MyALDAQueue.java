import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyALDAQueue<E> implements ALDAQueue<E> {

    private Node<E> head;
    private Node<E> tail;
    private int capacity;
    private int size;

    private static class Node<T> {
        private T data;
        private Node<T> next;
    
        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
    
    public MyALDAQueue(int capacity){
        if(capacity > 0){
            this.capacity = capacity;
            this.head = null;
            this.tail = null;
            this.size = 0;
        }else{
            throw new IllegalArgumentException();
        }
    }


    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head;
    
            @Override
            public boolean hasNext() {
                return current != null;
            }
    
            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E data = current.data;
                current = current.next;
                return data; 
            }
        };
    }

    @Override
    public void add(E element) {
        if(isFull()){
            throw new IllegalStateException();
        }else{
            if(element != null){
                Node<E> newNode = new Node<E>(element);
                if(isEmpty()){
                    head = newNode;
                    tail = newNode;
                }else{
                    tail.next = newNode;
                    tail = newNode;
                }
                size++;
            }else{
                throw new NullPointerException();
            }
        }

    }

    @Override
    public void addAll(Collection<? extends E> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        for (E element : c) {
            add(element);
        }
    }

    @Override
    public E remove() {
        if(!isEmpty()){
            E removedElement = head.data;
            head = head.next;
            size--;
            if (isEmpty()) {
                tail = null;
            }
            return removedElement;
        }else{
            throw new NoSuchElementException();
        }
    }

    @Override
    public E peek() {
        if(isEmpty()){
            return null;
        }
        return head.data;
    }

    @Override
    public void clear() {
        head = null; 
        tail = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size >= capacity;
    }

    @Override
    public int totalCapacity() {
        return capacity;
    }

    @Override
    public int currentCapacity() {
        return capacity - size;
    }

    @Override
    public int discriminate(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        
        int count = 0;
        Node<E> current = head;
        Node<E> previous = null;
        int elementsToMove = size;
        
        while (elementsToMove > 0) {
            if (current.data.equals(e)) {
                count++;
                if (current == head) {
                    head = current.next;
                } else {
                    previous.next = current.next;
                }
                if (current == tail) {
                    tail = previous;
                }
                Node<E> temp = current;
                current = current.next;
                temp.next = null;
                if (tail != null){
                    tail.next = temp; 
                } else {
                    head = temp;
                }
                tail = temp;
            } else {
                previous = current;
                current = current.next;
            }
            elementsToMove--;
        }
        return count;

    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    } 
}
