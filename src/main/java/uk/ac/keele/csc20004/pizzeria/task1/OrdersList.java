package uk.ac.keele.csc20004.pizzeria.task1;

import java.util.LinkedList;
import java.util.Queue;
import uk.ac.keele.csc20004.pizzeria.Order;

/**
 * A spin on the Buffer class seen in the practicals. Basically a queue
 * implemented with a linked list.
 *
 * @author Marco Ortolani, Repurposed by 18016286.
 */
public class OrdersList {

    private Queue<Order> list;
    private int size;

    public OrdersList(int _size) {
        this.list = new LinkedList<>();
        this.size = _size;
    }

    /**
     * Inserts the specified element into this queue.
     *
     * @param _order the order to add to the queue
     */
    public synchronized void add(Order _order) throws InterruptedException {
        while (list.size() >= size) {
            wait();
        }
        list.add(_order);
        notifyAll();
    }

    /**
     * Retrieves and removes the head of this queue, or returns null if this
     * queue is empty.
     *
     * @return the head of the queue or null if the queue is empty
     */
    public synchronized Order poll() throws InterruptedException {
        while (list.size() == 0) {
            wait();
        }
        Order order = list.poll();
        notifyAll();
        return order;
    }

    /**
     * Returns the number of elements in this collection.
     *
     * @return the number of elements in this collection
     */
    public int getSize() {
        return list.size();
    }
}
