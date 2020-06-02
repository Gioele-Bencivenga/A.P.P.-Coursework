/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.keele.csc20004.pizzeria.task2;

import uk.ac.keele.csc20004.pizzeria.Ingredient;
import uk.ac.keele.csc20004.pizzeria.Order;
import uk.ac.keele.csc20004.pizzeria.Order;
import uk.ac.keele.csc20004.pizzeria.Pizzeria;

/**
 * A slightly less basic interface for a pizzeria. See
 * {@link uk.ac.keele.csc20004.pizzeria.task2.PizzaExpress} for a sample
 * implementation. The only additional methods in this pizzeria are regarding
 * takeaway orders since the basic pizzeria implementation didn't have any.
 *
 * @author Marco Ortolani extended by Gioele Bencivenga
 */
public interface MyPizzeria extends Pizzeria {

    /**
     * Accept an order and store it in the take away order queue. Just like
     * `placeOrder` but for takeAways.
     *
     * @param o the Order to be accepted
     */
    public void placeTakeAwayOrder(Order o);

    /**
     * Fetch an order from the Take Away order queue. Just like `getNextOrder`
     * but for takeAways.
     *
     * @return the "next" order in the queue; in first-come-first-served
     * ordering.
     */
    public Order getNextTakeAwayOrder();

    /**
     * Place the order in the Take Away "chain" for delivery. Just like
     * `deliverOrder` but for takeAways.
     *
     * @param o the Order to be delivered
     */
    public void deliverTakeAwayOrder(Order o);

    /**
     * Get the number of orders still to be processed in the take away order
     * queue. Just like `getNumOfWaitingOrders` but for takeAways.
     *
     * @return the number of waiting orders
     */
    public int getNumOfWaitingTakeAwayOrders();
}
