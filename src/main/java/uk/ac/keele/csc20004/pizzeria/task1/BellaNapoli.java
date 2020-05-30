/* **********************
 * CSC-20004 COURSEWORK *
 * Due date: 7 May 2020 *
 * **********************/
package uk.ac.keele.csc20004.pizzeria.task1;

import java.util.ArrayList;
import java.util.Random;
import uk.ac.keele.csc20004.pizzeria.*;

/**
 * Complete the code relative to Task1 here. Feel free to add more
 * classes/interfaces if necessary.
 *
 * I looked at ArrayBlockingQueue and ConcurrentLinkedQueue as the data
 * structure for holding the orders, however I then decided to use something
 * we've already seen in the lab practicals: the Buffer class provided by Marco.
 *
 * @author 18016286
 */
public class BellaNapoli implements Pizzeria {

    private OrdersList orders; // list of orders waiting to be processed
    private OrdersList deliveryChain; // completed orders that will need to be brought to the tables by the servers

    private ArrayList<StorageShelf> shelfs;
    private StorageShelf sauceShelf;
    private StorageShelf cheeseShelf;
    private StorageShelf veggiesShelf;
    private StorageShelf hamShelf;
    private StorageShelf pineappleShelf;

    private Thread cook;
    private Thread supplier;

    public BellaNapoli() {
        orders = new OrdersList(MAX_ORDERS);
        deliveryChain = new OrdersList(MAX_ORDERS);

        sauceShelf = new StorageShelf(StorageShelf.MAX_INGREDIENTS, 0);
        cheeseShelf = new StorageShelf(StorageShelf.MAX_INGREDIENTS, 1);
        veggiesShelf = new StorageShelf(StorageShelf.MAX_INGREDIENTS, 2);
        hamShelf = new StorageShelf(StorageShelf.MAX_INGREDIENTS, 3);
        pineappleShelf = new StorageShelf(StorageShelf.MAX_INGREDIENTS, 4);

        shelfs.add(sauceShelf);
        shelfs.add(cheeseShelf);
        shelfs.add(veggiesShelf);
        shelfs.add(hamShelf);
        shelfs.add(pineappleShelf);

        cook = new Thread(new MyCook("Pierluigi", this));
        supplier = new Thread(new Supplier("Michele", this));
    }

    public static void main(String[] args) {
        BellaNapoli bellaNapoli = new BellaNapoli();

        bellaNapoli.run();

        bellaNapoli.cook.start();
    }

    /**
     * This method simulates the actual work of the pizzeria. - a number of
     * random orders are placed - the cook keeps preparing orders as long as
     * there are orders to be prepared
     */
    public void run() {
        // we create N random orders and add them to the order list 
        for (int i = 0; i < 5; i++) {
            Order order = createRandomOrder();
            placeOrder(order);
        }

    }

    /**
     * Helper function to refill a random shelf. Only one ingredient is added to
     * the shelf at one time.
     *
     * @return a randomly created order (a list of pizzas of random types)(
     */
    private void refillRandomShelf() {
        Random random = new Random();
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
     * @param _order the Order to be accepted
     */
    @Override
    public void placeOrder(Order _order) {
        try {
            orders.add(_order);
        } catch (InterruptedException _exception) {
            System.err.println(_exception);
        }
        System.out.println("Order " + _order + " arrived!");
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
            nextOrder = orders.poll();
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
     * @param _order the Order to be delivered
     */
    @Override
    public void deliverOrder(Order _order) {
        try {
            deliveryChain.add(_order);
        } catch (InterruptedException _exception) {
            System.err.println(_exception);
        }
        System.out.println("Order " + _order + " added to delivery queue.");
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
        return orders.getSize();
    }

    /**
     * Get this pizzeria's shelves.
     *
     * @return an ArrayList<StorageShelf>
     */
    public ArrayList<StorageShelf> getShelfs() {
        return shelfs;
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
