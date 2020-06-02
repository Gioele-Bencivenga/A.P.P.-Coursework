/* **********************
 * CSC-20004 COURSEWORK *
 * Due date: 7 May 2020 *
 * **********************/
package uk.ac.keele.csc20004.pizzeria.task2;

import java.util.ArrayList;
import java.util.Random;
import uk.ac.keele.csc20004.pizzeria.*;
import uk.ac.keele.csc20004.pizzeria.task1.*;

/**
 * Code relative to Task2.
 *
 * A potential issue with concurrent access to the shelves is the cooks
 * accessing the shelves at the same time. By having each shelf as a queue that
 * has synchronized `add()` and `poll()` methods it is not possible for two
 * threads to access a shelf at the same time as another thread. Additionally,
 * thanks to the `notifyAll()` call at the end of the methods a cook accessing a
 * shelf is able to wake up the other threads that are waiting as soon as it's
 * done.
 *
 * @author Gioele Bencivenga
 */
public class PizzaExpress implements MyPizzeria {

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

    // workers and consumers (people in the pizzeria)
    private Thread eatInCook;
    private Thread takeAwayCook;
    private Thread supplier;
    private Thread eatInConsumer;
    private Thread takeAwayConsumer;

    public PizzaExpress() {
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
        eatInCook = new Thread(new MyCook("COOK_EATIN", this, 1));
        takeAwayCook = new Thread(new MyCook("COOK_TAKEAWAY", this, 2));
        supplier = new Thread(new Supplier("SUPPLIER", this));
        eatInConsumer = new Thread(new OrderConsumer("CONSUMER_EATIN", eatInChain));
        takeAwayConsumer = new Thread(new OrderConsumer("CONSUMER_TAKEAWAY", takeAwayChain));
    }

    public static void main(String[] args) {
        PizzaExpress pizzeria = new PizzaExpress();

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
        supplier.start();
        eatInCook.start();
        takeAwayCook.start();
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
