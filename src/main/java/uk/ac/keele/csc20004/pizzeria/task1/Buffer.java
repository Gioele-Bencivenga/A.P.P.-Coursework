/* ***********************
 * CSC-20004             *
 * Producer/Consumer     *
 * 19 Mar 2020           *
 * ***********************/
package uk.ac.keele.csc20004.pizzeria.task1;

import java.util.LinkedList;
import java.util.Queue;
import uk.ac.keele.csc20004.pizzeria.Order;

public class Buffer {

    private Queue<Order> list;
    private int size;

    public Buffer(int _size) {
        this.list = new LinkedList<>();
        this.size = _size;
    }

    /**
     * Inserts the specified element into this queue.
     *
     * @param _order the order to add to the queue
     */
    public void add(Order _order) throws InterruptedException {
        synchronized (this) {
            while (list.size() >= size) {
                wait();
            }
            list.add(_order);
            notify();
        }
    }

    /**
     * Retrieves and removes the head of this queue, or returns null if this
     * queue is empty.
     *
     * @return the head of the queue or null if the queue is empty
     */
    public Order poll() throws InterruptedException {
        synchronized (this) {
            while (list.size() == 0) {
                wait();
            }
            Order order = list.poll();
            notify();
            return order;
        }
    }
    
    /**
     * Returns the number of elements in this collection.
     *
     * @return the number of elements in this collection
     */
    public int getSize(){
        return list.size();
    }
}
