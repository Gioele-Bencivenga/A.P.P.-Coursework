/* **********************
 * CSC-20004 COURSEWORK *
 * Due date: 7 May 2020 *
 * **********************/
package uk.ac.keele.csc20004.pizzeria.task3;

import java.util.ArrayList;
import java.util.Random;
import uk.ac.keele.csc20004.pizzeria.*;
import uk.ac.keele.csc20004.pizzeria.task1.*;
import uk.ac.keele.csc20004.pizzeria.task2.MyPizzeria;

/**
 * Code relative to Task3.
 *
 * Q: what are the potential issues due to concurrent access to the shelves, the
 * order queues and the delivery chain;
 *
 * A: shelves, order lists and delivery chains are all implemented as
 * synchronized queues. If they weren't synchronised potential problems would
 * arise when two threads would access the queue at the same time. Examples
 * include shelves exceeding their maximum storage amount, orders getting
 * prepared multiple times by more than one cook, consumers consuming the same
 * duplicated order that only appeared once in the delivery chain and so on.
 *
 * Q: how you made sure that your solution is not causing deadlock/livelock;
 *
 * A: my solution doesn't cause deadlock because it doesn't present any case of
 * "hold and wait" when a thread is holding a resource and at the same time
 * waiting for another. The hold and wait condition is mandatory for deadlock to
 * occur, among other conditions. Additionally, when a lock is acquired it is
 * shortly after released so that others may use it, all of the synchronised
 * blocks are kept as short and simple as possible. my solution doesn't cause
 * livelock since threads do not communicate with each other (apart from
 * notifying when a lock has been released) they cannot get stuck in a endless
 * cycle of responding to each other.
 *
 * Q: whether starvation may occur, and where; and what is your approach to deal
 * with it.
 *
 * A: starvation is mostly avoided by having short synchronised blocks. It may
 * still happen for short periods of time when refilling a shelf or getting an
 * order, but in those cases it will have happened because another thread is
 * already working on it and will be done shortly.
 *
 * @author Gioele Bencivenga
 */
public class PizzeriaItalia implements MyPizzeria {

    /**
     * the maximum number of orders that may be accepted in each of the "order
     * queues". Further orders may be put on hold or just rejected.
     */
    public static final int MAX_ORDERS = 50;

    // lists of the orders waiting to be processed
    private OrdersList eatInOrders;
    private OrdersList takeAwayOrders;

    // delivery chains for finished orders that need to be consumed
    private DeliveryChain eatInChain;
    private DeliveryChain takeAwayChain;

    // ingredient shelves
    private StorageShelf sauceShelf;
    private StorageShelf cheeseShelf;
    private StorageShelf veggiesShelf;
    private StorageShelf hamShelf;
    private StorageShelf pineappleShelf;

    // cooks
    private Thread cook1;
    private Thread cook2;
    private Thread cook3;
    // suppliers
    private Thread supplier1;
    private Thread supplier2;
    private Thread supplier3;
    private Thread supplier4;
    private Thread supplier5;
    // consumers
    private Thread eatInConsumer;
    private Thread takeAwayConsumer;

    public PizzeriaItalia() {
        // orders lists
        eatInOrders = new OrdersList(MAX_ORDERS);
        takeAwayOrders = new OrdersList(MAX_ORDERS);

        // chains
        eatInChain = new DeliveryChain();
        takeAwayChain = new DeliveryChain();

        // shelves
        sauceShelf = new StorageShelf(StorageShelf.MAX_INGREDIENTS, 0);
        cheeseShelf = new StorageShelf(StorageShelf.MAX_INGREDIENTS, 1);
        veggiesShelf = new StorageShelf(StorageShelf.MAX_INGREDIENTS, 2);
        hamShelf = new StorageShelf(StorageShelf.MAX_INGREDIENTS, 3);
        pineappleShelf = new StorageShelf(StorageShelf.MAX_INGREDIENTS, 4);

        // people
        cook1 = new Thread(new MyCook("COOK_1", this, 0));
        cook2 = new Thread(new MyCook("COOK_2", this, 0));
        cook3 = new Thread(new MyCook("COOK_3", this, 0));
        supplier1 = new Thread(new Supplier("SUPPLIER_1", this));
        supplier2 = new Thread(new Supplier("SUPPLIER_2", this));
        supplier3 = new Thread(new Supplier("SUPPLIER_3", this));
        supplier4 = new Thread(new Supplier("SUPPLIER_4", this));
        supplier5 = new Thread(new Supplier("SUPPLIER_5", this));
        eatInConsumer = new Thread(new OrderConsumer("CONSUMER_EATIN", eatInChain));
        takeAwayConsumer = new Thread(new OrderConsumer("CONSUMER_TAKEAWAY", takeAwayChain));
    }

    public static void main(String[] args) {
        PizzeriaItalia pizzeria = new PizzeriaItalia();

        pizzeria.run();
    }

    /**
     * This method simulates the actual work of the pizzeria.
     *
     * for now only a number of orders are placed, cooks, suppliers and
     * consumers will check on their own what to do and do it in their `run()`
     * method.
     */
    public void run() {
        // starting the pizzeria workers and consumers so they can start fulfilling their role
        supplier1.start();
        supplier2.start();
        supplier3.start();
        supplier4.start();
        supplier5.start();
        cook1.start();
        cook2.start();
        cook3.start();
        eatInConsumer.start();
        takeAwayConsumer.start();

        int nOfOrders = 5; // the number of random orders we want to create

        // creating and placing the orders in the chains
        for (int i = 0; i < nOfOrders; i++) {
            Order eatInOrder = createRandomOrder();
            Order takeAwayOrder = createRandomOrder();
            placeOrder(eatInOrder);
            placeTakeAwayOrder(takeAwayOrder);
        }
    }

    /**
     * Helper function to create a random order. Feel free to reuse it in your
     * code.
     *
     * @return a randomly created order (a list of pizzas of random types)
     */
    private Order createRandomOrder() {
        Random random = new Random();

        ArrayList<Pizza> p = new ArrayList<>();

        // create at most 5 random pizzas
        int quantity = random.nextInt(5) + 1;
        for (int i = 0; i < quantity; i++) {
            int type = random.nextInt(Pizza.PIZZA_TYPES);
            switch (type) {
                case Pizza.MARGHERITA:
                    p.add(Pizza.createMargherita());
                    break;
                case Pizza.ROMANA:
                    p.add(Pizza.createRomana());
                    break;
                case Pizza.VEGETARIAN:
                    p.add(Pizza.createVegetarian());
                    break;
                default:
                    p.add(Pizza.createHawaiian());
                    break;
            }
        }

        return new Order(p);
    }

    /**
     * Accept an order and store it in the default order queue. By default, this
     * will be the "eat-in" queue, or the only one available, depending on the
     * scenario.
     *
     * @param o the Order to be accepted
     */
    @Override
    public void placeOrder(Order o) {
        try {
            eatInOrders.add(o);
        } catch (InterruptedException _exception) {
            System.err.println(_exception);
        }
        System.out.println("Order " + o + " arrived in the EAT IN queue!");
    }

    /**
     * Accept an order and store it in the take away order queue. Just like
     * `placeOrder` but for takeAways.
     *
     * @param o the Order to be accepted
     */
    @Override
    public void placeTakeAwayOrder(Order o) {
        try {
            takeAwayOrders.add(o);
        } catch (InterruptedException _exception) {
            System.err.println(_exception);
        }
        System.out.println("Order " + o + " arrived in the TAKE AWAY queue!");
    }

    /**
     * Fetch an order from the default order queue. By default, this will be the
     * "eat-in" queue, or the only one available, depending on the scenario.
     *
     * @return the "next" order in the queue; in first-come-first-served
     * ordering.
     */
    @Override
    public Order getNextOrder() {
        Order nextOrder = null;
        try {
            nextOrder = eatInOrders.poll();
        } catch (InterruptedException _exception) {
            System.err.println(_exception);
        }

        return nextOrder;
    }

    /**
     * Fetch an order from the Take Away order queue. Just like `getNextOrder`
     * but for takeAways.
     *
     * @return the "next" order in the queue; in first-come-first-served
     * ordering.
     */
    @Override
    public Order getNextTakeAwayOrder() {
        Order nextOrder = null;
        try {
            nextOrder = takeAwayOrders.poll();
        } catch (InterruptedException _exception) {
            System.err.println(_exception);
        }

        return nextOrder;
    }

    /**
     * Place the order in the default "chain" for delivery. By default, this
     * will be the "eat-in" chain, or the only one available, depending on the
     * scenario. This method will likely be called by a Cook.
     *
     * @param o the Order to be delivered
     */
    @Override
    public void deliverOrder(Order o) {
        try {
            eatInChain.add(o);
        } catch (InterruptedException _exception) {
            System.err.println(_exception);
        }
        System.out.println("Order " + o + " added to EAT IN delivery queue.");
    }

    /**
     * Place the order in the Take Away "chain" for delivery. Just like
     * `deliverOrder` but for takeAways.
     *
     * @param o the Order to be delivered
     */
    @Override
    public void deliverTakeAwayOrder(Order o) {
        try {
            takeAwayChain.add(o);
        } catch (InterruptedException _exception) {
            System.err.println(_exception);
        }
        System.out.println("Order " + o + " added to TAKE AWAY delivery queue.");
    }

    /**
     * Get the number of orders still to be processed in the default order
     * queue. By default, this will be the "eat-in" queue, or the only one
     * available, depending on the scenario.
     *
     * @return the number of waiting orders
     */
    @Override
    public int getNumOfWaitingOrders() {
        return eatInOrders.getSize();
    }

    /**
     * Get the number of orders still to be processed in the take away order
     * queue. Just like `getNumOfWaitingOrders` but for takeAways.
     *
     * @return the number of waiting orders
     */
    @Override
    public int getNumOfWaitingTakeAwayOrders() {
        return takeAwayOrders.getSize();
    }

    /**
     * Take one instance of an ingredient from the corresponding shelf.
     *
     * @return an Ingredient of type sauce
     */
    @Override
    public Ingredient fetchSauce() {
        try {
            return sauceShelf.poll();
        } catch (InterruptedException _exception) {
            System.err.println(_exception);
        }
        return null; // if we hit the first return statement this won't be reached
    }

    /**
     * Take one instance of an ingredient from the corresponding shelf.
     *
     * @return an Ingredient of type ham
     */
    @Override
    public Ingredient fetchHam() {
        try {
            return hamShelf.poll();
        } catch (InterruptedException _exception) {
            System.err.println(_exception);
        }
        return null;
    }

    /**
     * Take one instance of an ingredient from the corresponding shelf.
     *
     * @return an Ingredient of type veggies
     */
    @Override
    public Ingredient fetchVeggies() {
        try {
            return veggiesShelf.poll();
        } catch (InterruptedException _exception) {
            System.err.println(_exception);
        }
        return null;
    }

    /**
     * Take one instance of an ingredient from the corresponding shelf.
     *
     * @return an Ingredient of type cheese
     */
    @Override
    public Ingredient fetchCheese() {
        try {
            return cheeseShelf.poll();
        } catch (InterruptedException _exception) {
            System.err.println(_exception);
        }
        return null;
    }

    /**
     * Take one instance of an ingredient from the corresponding shelf.
     *
     * @return an Ingredient of type pineapple
     */
    @Override
    public Ingredient fetchPineapple() {
        try {
            return pineappleShelf.poll();
        } catch (InterruptedException _exception) {
            System.err.println(_exception);
        }
        return null;
    }

    /**
     * Puts one instance of an ingredient in the corresponding shelf. In this
     * case, in the shelf for sauce
     */
    @Override
    public void refillSauce() {
        try {
            sauceShelf.add(Ingredient.createSauce());
        } catch (InterruptedException _exception) {
            System.err.println(_exception);
        }
    }

    /**
     * Puts one instance of an ingredient in the corresponding shelf. In this
     * case, in the shelf for ham
     */
    @Override
    public void refillHam() {
        try {
            hamShelf.add(Ingredient.createHam());
        } catch (InterruptedException _exception) {
            System.err.println(_exception);
        }
    }

    /**
     * Puts one instance of an ingredient in the corresponding shelf. In this
     * case, in the shelf for veggies
     */
    @Override
    public void refillVeggies() {
        try {
            veggiesShelf.add(Ingredient.createVeggies());
        } catch (InterruptedException _exception) {
            System.err.println(_exception);
        }
    }

    /**
     * Puts one instance of an ingredient in the corresponding shelf. In this
     * case, in the shelf for cheese
     */
    @Override
    public void refillCheese() {
        try {
            cheeseShelf.add(Ingredient.createCheese());
        } catch (InterruptedException _exception) {
            System.err.println(_exception);
        }
    }

    /**
     * Puts one instance of an ingredient in the corresponding shelf. In this
     * case, in the shelf for pineapple
     */
    @Override
    public void refillPineapple() {
        try {
            pineappleShelf.add(Ingredient.createPineapple());
        } catch (InterruptedException _exception) {
            System.err.println(_exception);
        }
    }

    /**
     * Helper method to get the number of ingredients of type sauce are
     * currently present in the corresponding shelf.
     *
     * @return the number of sauce items in the corresponding shelf
     */
    @Override
    public int getSauceStorageLevel() {
        return sauceShelf.getSize();
    }

    /**
     * Helper method to get the number of ingredients of type ham are currently
     * present in the corresponding shelf.
     *
     * @return the number of ham items in the corresponding shelf
     */
    @Override
    public int getHamStorageLevel() {
        return hamShelf.getSize();
    }

    /**
     * Helper method to get the number of ingredients of type veggies are
     * currently present in the corresponding shelf.
     *
     * @return the number of veggies items in the corresponding shelf
     */
    @Override
    public int getVeggiesStorageLevel() {
        return veggiesShelf.getSize();
    }

    /**
     * Helper method to get the number of ingredients of type cheese are
     * currently present in the corresponding shelf.
     *
     * @return the number of cheese items in the corresponding shelf
     */
    @Override
    public int getCheeseStorageLevel() {
        return cheeseShelf.getSize();
    }

    /**
     * Helper method to get the number of ingredients of type pineapple are
     * currently present in the corresponding shelf.
     *
     * @return the number of pineapple items in the corresponding shelf
     */
    @Override
    public int getPineappleStorageLevel() {
        return pineappleShelf.getSize();
    }
}
