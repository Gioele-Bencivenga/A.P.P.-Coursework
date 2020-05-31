package uk.ac.keele.csc20004.pizzeria.task1;

import java.util.LinkedList;
import java.util.Queue;
import uk.ac.keele.csc20004.pizzeria.Order;
import static uk.ac.keele.csc20004.pizzeria.task1.DeliveryChain.MAX_DELIVERY_WAITS;

/**
 * A spin on the Buffer class seen in the practicals. Basically a queue
 * implemented with a linked list.
 *
 * @author Marco Ortolani, Repurposed by Gioele Bencivenga.
 */
public class OrdersList {

    protected Queue<Order> list;
    protected int size;

    /**
     * Whether the chain has reached maximum capacity or not.
     */
    public boolean isFull;

    public OrdersList(int _size) {
        list = new LinkedList<>();
        size = _size;

        isFull = false;
    }

    /**
     * Inserts the specified element into this queue.
     *
     * @param _order the order to add to the queue
     */
    public synchronized void add(Order _order) throws InterruptedException {
        while (getSize() == size) {
            isFull = true;
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
        while (list.isEmpty()) {
            wait();
        }

        while (isFull) { // if the chain is full and we poll then the chain won't be full anymores
            isFull = false;
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
