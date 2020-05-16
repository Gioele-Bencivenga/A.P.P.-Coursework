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

    private Buffer orders;

    private MyCook cook1;

    public BellaNapoli() {
        orders = new Buffer(MAX_ORDERS);
        cook1 = new MyCook("Giorgio", this);
    }

    public static void main(String[] args) {
        // Provide a main that shows how your implementation of the pizzeria works
    }

    /**
     * Helper function to create a random order. Feel free to reuse it in your
     * code.
     *
     * @return a randomly created order (a list of pizzas of random types)(
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
    public void placeOrder(Order _order) {
        // check and see if I need to use a thread to do this stuff
        try {
            orders.add(_order);
        } catch (InterruptedException _exception) {
            System.err.println(_exception);
        }

    }

    /**
     * Fetch an order from the default order queue. By default, this will be the
     * "eat-in" queue, or the only one available, depending on the scenario.
     *
     * @return the "next" order in the queue; in first-come-first-served
     * ordering.
     */
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
     * Place the order in the default "chain" for delivery. By default, this will
     * be the "eat-in" chain, or the only one available, depending on the
     * scenario. This method will likely be called by a Cook.
     *
     * @param o the Order to be delivered
     */
    public void deliverOrder(Order o) {

    }

    /**
     * Get the number of orders still to be processed in the default order
     * queue. By default, this will be the "eat-in" queue, or the only one
     * available, depending on the scenario.
     *
     * @return the number of waiting orders
     */
    public int getNumOfWaitingOrders() {
        return orders.getSize();
    }

    /**
     * Take one instance of an ingredient from the corresponding shelf.
     *
     * @return an Ingredient of type sauce
     */
    public Ingredient fetchSauce() {
        return Ingredient.createSauce();
    }

    /**
     * Take one instance of an ingredient from the corresponding shelf.
     *
     * @return an Ingredient of type ham
     */
    public Ingredient fetchHam() {
        return Ingredient.createHam();
    }

    /**
     * Take one instance of an ingredient from the corresponding shelf.
     *
     * @return an Ingredient of type veggies
     */
    public Ingredient fetchVeggies() {
        return Ingredient.createVeggies();
    }

    /**
     * Take one instance of an ingredient from the corresponding shelf.
     *
     * @return an Ingredient of type cheese
     */
    public Ingredient fetchCheese() {
        return Ingredient.createCheese();
    }

    /**
     * Take one instance of an ingredient from the corresponding shelf.
     *
     * @return an Ingredient of type pineapple
     */
    public Ingredient fetchPineapple() {
        return Ingredient.createPineapple();
    }

    /**
     * Puts one instance of an ingredient in the corresponding shelf. In this
     * case, in the shelf for sauce
     */
    public void refillSauce() {

    }

    /**
     * Puts one instance of an ingredient in the corresponding shelf. In this
     * case, in the shelf for ham
     */
    public void refillHam() {

    }

    /**
     * Puts one instance of an ingredient in the corresponding shelf. In this
     * case, in the shelf for veggies
     */
    public void refillVeggies() {

    }

    /**
     * Puts one instance of an ingredient in the corresponding shelf. In this
     * case, in the shelf for cheese
     */
    public void refillCheese() {

    }

    /**
     * Puts one instance of an ingredient in the corresponding shelf. In this
     * case, in the shelf for pineapple
     */
    public void refillPineapple() {

    }

    /**
     * Helper method to get the number of ingredients of type sauce are
     * currently present in the corresponding shelf.
     *
     * @return the number of sauce items in the corresponding shelf
     */
    public int getSauceStorageLevel() {
        return 0;
    }

    /**
     * Helper method to get the number of ingredients of type ham are currently
     * present in the corresponding shelf.
     *
     * @return the number of ham items in the corresponding shelf
     */
    public int getHamStorageLevel() {
        return 0;
    }

    /**
     * Helper method to get the number of ingredients of type veggies are
     * currently present in the corresponding shelf.
     *
     * @return the number of veggies items in the corresponding shelf
     */
    public int getVeggiesStorageLevel() {
        return 0;
    }

    /**
     * Helper method to get the number of ingredients of type cheese are
     * currently present in the corresponding shelf.
     *
     * @return the number of cheese items in the corresponding shelf
     */
    public int getCheeseStorageLevel() {
        return 0;
    }

    /**
     * Helper method to get the number of ingredients of type pineapple are
     * currently present in the corresponding shelf.
     *
     * @return the number of pineapple items in the corresponding shelf
     */
    public int getPineappleStorageLevel() {
        return 0;
    }
}
