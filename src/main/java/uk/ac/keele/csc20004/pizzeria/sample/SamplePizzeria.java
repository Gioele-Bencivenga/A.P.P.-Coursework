/* **********************
 * CSC-20004 COURSEWORK *
 * Due date: 7 May 2020 *
 * **********************/ 

package uk.ac.keele.csc20004.pizzeria.sample;

import uk.ac.keele.csc20004.pizzeria.Cook;
import java.util.ArrayList;
import java.util.Random;
import uk.ac.keele.csc20004.pizzeria.Ingredient;
import uk.ac.keele.csc20004.pizzeria.Order;
import uk.ac.keele.csc20004.pizzeria.Pizza;
import uk.ac.keele.csc20004.pizzeria.Pizzeria;

/** An implementation of the interface Pizzeria. This is provided only as an
 * example and it is not part of the coursework.
 * This is an extremely simplified version of a Pizzeria, which eternally loops 
 * through the phases:
 * - place a random order
 * - prepare the order
 *
 * @author Marco Ortolani
 */
public class SamplePizzeria implements Pizzeria {
    // a "list" of orders (in this implementation, it is just one)
    private Order orders;
    
    // the only cook in the pizzeria
    private final Cook cook;
    
    /**  A constructor that simply takes care of adding one single cook (an instance
     * of SampleCook) to this pizzeria.
     * 
     */
    public SamplePizzeria() {
        cook = new SampleCook("Mario", this);
    }
    
    /** Helper function to create a random order. Feel free to reuse it in your 
     * code.
     * 
     * @return a randomly created order (a list of pizzas of random types)(
     */
    private Order createRandomOrder() {
        Random random = new Random();

        ArrayList<Pizza> p = new ArrayList<>();
        
        // create at most 5 random pizzas
        int quantity = random.nextInt(5)+1;
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


    /** Simple implementation of placeOrder(); just replaces the internal
     * private var storing a single order.
     * 
     * @param o the Order to be "put in the queue"
     */
    @Override
    public void placeOrder(Order o) {
        orders = o;
        System.out.println("Order " + o + " arrived!");
    }

    /** Just a placeholder; this method is not used in this implementation
     * 
     * @return just throws an exception as it is not supposed to be used.
     */
    @Override
    public Order getNextOrder() {
        // just a placeholder; this method is not used in this implementation
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    /** Simulates order delivery by printing out a message.
     * 
     * @param o the order that is ready for delivery
     */
    @Override
    public void deliverOrder(Order o) {
        System.out.println("Order " + o + " delivered!");
    }

    /** Overridden method from superclass. An infinite storage is simulated by 
     * returning a new instance of an Ingredient every time.
     * No shelves are provided in this implementation.
     * 
     * @return a sauce Ingredient
     */
    @Override
    public Ingredient fetchSauce() {
        return Ingredient.createSauce();
    }

    /** Overridden method from superclass. An infinite storage is simulated by 
     * returning a new instance of an Ingredient every time.
     * No shelves are provided in this implementation.
     * 
     * @return a ham Ingredient
     */
    @Override
    public Ingredient fetchHam() {
        return Ingredient.createHam();
    }

    /** Overridden method from superclass. An infinite storage is simulated by 
     * returning a new instance of an Ingredient every time.
     * No shelves are provided in this implementation.
     * 
     * @return a veggies Ingredient
     */
    @Override
    public Ingredient fetchVeggies() {
        return Ingredient.createVeggies();
    }

    /** Overridden method from superclass. An infinite storage is simulated by 
     * returning a new instance of an Ingredient every time.
     * No shelves are provided in this implementation.
     * 
     * @return a cheese Ingredient
     */
    @Override
    public Ingredient fetchCheese() {
        return Ingredient.createCheese();
    }

    /** Overridden method from superclass. An infinite storage is simulated by 
     * returning a new instance of an Ingredient every time.
     * No shelves are provided in this implementation.
     * 
     * @return a pineapple Ingredient
     */
    @Override
    public Ingredient fetchPineapple() {
        return Ingredient.createPineapple();
    }

    /** A placeholder method: no shelves are provided in this implementation.
     * 
     */
    @Override
    public void refillSauce() {
        // do nothing
    }

    /** A placeholder method: no shelves are provided in this implementation.
     * 
     */
    @Override
    public void refillHam() {
        // do nothing
    }

    /** A placeholder method: no shelves are provided in this implementation.
     * 
     */
    @Override
    public void refillVeggies() {
        // do nothing
    }

    /** A placeholder method: no shelves are provided in this implementation.
     * 
     */
    @Override
    public void refillCheese() {
        // do nothing
    }

    /** A placeholder method: no shelves are provided in this implementation.
     * 
     */
    @Override
    public void refillPineapple() {
        // do nothing
    }
    
    /** This method simulates the actual work of the pizzeria: an eternal loop 
     * through the phases:
     * - place a random order
     * - ask the cook to prepare the order
     */
    public void run() {
        while(true) {
            Order o = createRandomOrder();
            this.placeOrder(o);
            
            cook.prepareOrder(o);
        }
    }
    
    /** Just a placeholder; this method is not used in this implementation
     * 
     * @return always returns 0
     */
    @Override
    public int getNumOfWaitingOrders() {
        return 0;
    }

    /** A placeholder method: no shelves are provided in this implementation.
     * 
     * @return always return 1 to simulate a "never empty" shelf
     */
    @Override
    public int getSauceStorageLevel() {
        return 1;
    }

    /** A placeholder method: no shelves are provided in this implementation.
     * 
     * @return always return 1 to simulate a "never empty" shelf
     */
    @Override
    public int getHamStorageLevel() {
        return 1;
    }

    /** A placeholder method: no shelves are provided in this implementation.
     * 
     * @return always return 1 to simulate a "never empty" shelf
     */
    @Override
    public int getVeggiesStorageLevel() {
        return 1;
    }

    /** A placeholder method: no shelves are provided in this implementation.
     * 
     * @return always return 1 to simulate a "never empty" shelf
     */
    @Override
    public int getCheeseStorageLevel() {
        return 1;
    }

    /** A placeholder method: no shelves are provided in this implementation.
     * 
     * @return always return 1 to simulate a "never empty" shelf
     */
    @Override
    public int getPineappleStorageLevel() {
        return 1;
    }

    /** The main method; you can run this to have an idea of the expected output of
     * the coursework.
     * 
     * @param args 
     */
    public static void main(String[] args) {
        SamplePizzeria p = new SamplePizzeria();
        
        p.run();
    }


}
