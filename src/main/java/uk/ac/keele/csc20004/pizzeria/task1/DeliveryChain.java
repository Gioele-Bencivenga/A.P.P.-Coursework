/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.keele.csc20004.pizzeria.task1;

import uk.ac.keele.csc20004.pizzeria.Order;

/**
 * A spin on the OrdersList class seen in the practicals.
 *
 * Basically a queue implemented with a linked list that after some time deletes
 * the first element to simulate the order being consumed.
 *
 * @author Marco Ortolani, Repurposed by Gioele Bencivenga.
 */
public class DeliveryChain extends OrdersList {

    /**
     * The maximum time that an order can be stored in the chain for.
     */
    public final int MAX_DELIVERY_TIME = 15000; 

    /**
     * The maximum number of orders that can be in the chain at a time.
     *
     * If the chain is full no pizzas get prepared.
     */
    public static final int MAX_DELIVERY_WAITS = 5;

    /**
     * Instantiates the DeliveryChain with a size of `MAX_DELIVERY_WAITS`.
     */
    public DeliveryChain() {
        super(MAX_DELIVERY_WAITS);
    }

    @Override
    public synchronized void add(Order _order) throws InterruptedException {
        super.add(_order);
    }

    @Override
    public synchronized Order poll() throws InterruptedException {
        return super.poll();
    }
}
