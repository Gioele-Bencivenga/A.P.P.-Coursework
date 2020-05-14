/* **********************
 * CSC-20004 COURSEWORK *
 * Due date: 7 May 2020 *
 * **********************/ 

package uk.ac.keele.csc20004.pizzeria;

/** A very basic interface for a pizzeria.
 *  See {@link uk.ac.keele.csc20004.pizzeria.sample.SamplePizzeria} for a 
 *  sample implementation.
 *
 * @author Marco Ortolani
 */
public interface Pizzeria {
    /** the maximum number of orders that may be accepted in each of the "order 
     * queues". Further orders may be put on hold or just rejected. 
     */
    public static final int MAX_ORDERS = 50;
    
    /** Accept an order and store it in the default order queue.
     * By default, this will be the "eat-in" queue, or the only one available, 
     * depending on the scenario.
     * 
     * @param o the Order to be accepted
     */
    public void placeOrder(Order o);
    
    /** Fetch an order from the default order queue.
     * By default, this will be the "eat-in" queue, or the only one available, 
     * depending on the scenario.
     * 
     * @return the "next" order in the queue; in first-come-first-served ordering.
     */
    public Order getNextOrder();    
    
    /** Place the order in the default "chain" for delivery
     * By default, this will be the "eat-in" chain, or the only one available, 
     * depending on the scenario. This method will likely be called by a Cook.
     * 
     * @param o the Order to be delivered
     */
    public void deliverOrder(Order o);
    
    /** Get the number of orders still to be processed in the default order queue.
     * By default, this will be the "eat-in" queue, or the only one available, 
     * depending on the scenario.
     * 
     * @return the number of waiting orders
     */
    public int getNumOfWaitingOrders();
    
    /** Take one instance of an ingredient from the corresponding shelf.
     * 
     * @return an Ingredient of type sauce
     */
    public Ingredient fetchSauce();

    /** Take one instance of an ingredient from the corresponding shelf.
     * 
     * @return an Ingredient of type ham
     */
    public Ingredient fetchHam();
    
    /** Take one instance of an ingredient from the corresponding shelf.
     * 
     * @return an Ingredient of type veggies
     */
    public Ingredient fetchVeggies();

    /** Take one instance of an ingredient from the corresponding shelf.
     * 
     * @return an Ingredient of type cheese
     */
    public Ingredient fetchCheese();
    
    /** Take one instance of an ingredient from the corresponding shelf.
     * 
     * @return an Ingredient of type pineapple
     */
    public Ingredient fetchPineapple();
    
    /** Puts one instance of an ingredient in the corresponding shelf.
     * In this case, in the shelf for sauce
     */
    public void refillSauce();

    /** Puts one instance of an ingredient in the corresponding shelf.
     * In this case, in the shelf for ham
     */
    public void refillHam();
    
    /** Puts one instance of an ingredient in the corresponding shelf.
     * In this case, in the shelf for veggies
     */
    public void refillVeggies();
    
    /** Puts one instance of an ingredient in the corresponding shelf.
     * In this case, in the shelf for cheese
     */
    public void refillCheese();
    
    /** Puts one instance of an ingredient in the corresponding shelf.
     * In this case, in the shelf for pineapple
     */
    public void refillPineapple();
    
    /** Helper method to get the number of ingredients of type sauce are
     * currently present in the corresponding shelf.
     * 
     * @return the number of sauce items in the corresponding shelf 
     */
    public int getSauceStorageLevel();

    /** Helper method to get the number of ingredients of type ham are
     * currently present in the corresponding shelf.
     * 
     * @return the number of ham items in the corresponding shelf 
     */
    public int getHamStorageLevel();
    
    /** Helper method to get the number of ingredients of type veggies are
     * currently present in the corresponding shelf.
     * 
     * @return the number of veggies items in the corresponding shelf 
     */
    public int getVeggiesStorageLevel();

    /** Helper method to get the number of ingredients of type cheese are
     * currently present in the corresponding shelf.
     * 
     * @return the number of cheese items in the corresponding shelf 
     */
    public int getCheeseStorageLevel();

    /** Helper method to get the number of ingredients of type pineapple are
     * currently present in the corresponding shelf.
     * 
     * @return the number of pineapple items in the corresponding shelf 
     */
    public int getPineappleStorageLevel();
    
}
