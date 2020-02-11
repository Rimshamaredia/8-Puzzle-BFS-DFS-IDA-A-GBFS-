import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;


public class MyQueue<Item> implements Iterable<Item> {
    private Node<Item> first;    // beginning of queue
    private Node<Item> last;     // end of queue
    private int num;               // number of elements on queue

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    
    public MyQueue() {
        first = null;
        last = null;
        num = 0;
    }

    public void clear() {
        first = null;
        last = null;
         num= 0;
    }

    
    public boolean isEmpty() {
        return first == null;
    }

   
    public int size() {
        return num;
    }

    
    public Item peek() {
        
        return first.item;
    }

   
    public void enqueue(Item item) {

        Node<Item> old = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else old.next = last;
        num++;
    }

    
    public Item dequeue() {
        
        Item item = first.item;
        first = first.next;
        num--;
        if (isEmpty()) last = null;   // to avoid loitering
        return item;
    }


  
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }



   
    public void addQueue(MyQueue<Item> queue) {
        if (!queue.isEmpty()) {


            Node<Item> oldFirst = first;

            if (isEmpty()) {
                first = queue.first;
                last = queue.last;
            } else {
                first = queue.first;
                queue.last.next = oldFirst;
            }

            num = num + queue.size();
        }

    }

}
