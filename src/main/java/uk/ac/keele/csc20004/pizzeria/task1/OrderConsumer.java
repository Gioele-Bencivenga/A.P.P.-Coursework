/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.keele.csc20004.pizzeria.task1;

import java.util.Random;
import java.util.TimerTask;
import uk.ac.keele.csc20004.pizzeria.Pizzeria;

/**
 * A class that simulates the consumption of orders.
 *
 * One order (from the head of the deliveryChain) gets consumed every N seconds
 * (scheduled by the Pizzeria).
 *
 * @author Gioele Bencivenga
 */
public class OrderConsumer extends Thread {

    /**
     * The Consumer's name.
     */
    protected final String name;

    /**
     * The chain this Consumer will consume.
     */
    protected DeliveryChain chain;

    public OrderConsumer(String _name, DeliveryChain _chain) {
        name = _name;
        chain = _chain;
    }

    /**
     * Simulates the consuming of a consumer.
     *
     * The consumer polls the head of the chain he is consuming every x seconds,
     * as long as the chain contains 1 or more elements.
     */
    @Override
    public void run() {
        while (true) {
            //while (chain.getSize() > 0) {
                try {
                    Thread.sleep(new Random().nextInt(chain.MAX_DELIVERY_TIME));
                    System.out.println(name + ": Consumed order " + chain.poll());
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
            //}
        }
    }
}
