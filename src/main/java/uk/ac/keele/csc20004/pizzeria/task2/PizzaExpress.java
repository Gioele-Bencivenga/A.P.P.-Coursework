/* **********************
 * CSC-20004 COURSEWORK *
 * Due date: 7 May 2020 *
 * **********************/ 

package uk.ac.keele.csc20004.pizzeria.task2;

import uk.ac.keele.csc20004.pizzeria.Ingredient;
import uk.ac.keele.csc20004.pizzeria.Order;
import uk.ac.keele.csc20004.pizzeria.Pizzeria;

/** Complete the code relative to Task2 here.
 * Feel free to add more classes/interfaces if necessary.
 * 
 * Replace this comment with your own.
 * 
 * Include description of your approach to resource contention (the cooks
 * are competing for access to the shared shelves).
 * Provide a couple of (short) sentences to discuss:
 * - what are the potential issues due to concurrent access to the shelves
 * - how you managed the contention
 *
 * @author 18016286
 */
public class PizzaExpress implements Pizzeria {
    /** the maximum number of orders that may be accepted in each of the "order 
     * queues". Further orders may be put on hold or just rejected. 
     */
    public static final int MAX_ORDERS = 50;
    
    // YOUR CODE HERE
    
    public static void main(String[] args) {
        // Provide a main that shows how your implementation of the pizzeria works
    }
    
    /** Accept an order and store it in the default order queue.
     * By default, this will be the "eat-in" queue, or the only one available, 
     * depending on the scenario.
     * 
     * @param o the Order to be accepted
     */
    public void placeOrder(Order o){
        // add this order to the list of orders (list to be created)
    }
    
    /** Fetch an order from the default order queue.
     * By default, this will be the "eat-in" queue, or the only one available, 
     * depending on the scenario.
     * 
     * @return the "next" order in the queue; in first-come-first-served ordering.
     */
    public Order getNextOrder(){
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    /** Place the order in the default "chain" for delivery
     * By default, this will be the "eat-in" chain, or the only one available, 
     * depending on the scenario. This method will likely be called by a Cook.
     * 
     * @param o the Order to be delivered
     */
    public void deliverOrder(Order o){
        
    }
    
    /** Get the number of orders still to be processed in the default order queue.
     * By default, this will be the "eat-in" queue, or the only one available, 
     * depending on the scenario.
     * 
     * @return the number of waiting orders
     */
    public int getNumOfWaitingOrders(){
        return 0;
    }
    
    /** Take one instance of an ingredient from the corresponding shelf.
     * 
     * @return an Ingredient of type sauce
     */
    public Ingredient fetchSauce(){
        return Ingredient.createSauce();
    }

    /** Take one instance of an ingredient from the corresponding shelf.
     * 
     * @return an Ingredient of type ham
     */
    public Ingredient fetchHam(){
        return Ingredient.createHam();
    }
    
    /** Take one instance of an ingredient from the corresponding shelf.
     * 
     * @return an Ingredient of type veggies
     */
    public Ingredient fetchVeggies(){
        return Ingredient.createVeggies();
    }

    /** Take one instance of an ingredient from the corresponding shelf.
     * 
     * @return an Ingredient of type cheese
     */
    public Ingredient fetchCheese(){
        return Ingredient.createCheese();
    }
    
    /** Take one instance of an ingredient from the corresponding shelf.
     * 
     * @return an Ingredient of type pineapple
     */
    public Ingredient fetchPineapple(){
        return Ingredient.createPineapple();
    }
    
    /** Puts one instance of an ingredient in the corresponding shelf.
     * In this case, in the shelf for sauce
     */
    public void refillSauce(){
        
    }

    /** Puts one instance of an ingredient in the corresponding shelf.
     * In this case, in the shelf for ham
     */
    public void refillHam(){
        
    }
    
    /** Puts one instance of an ingredient in the corresponding shelf.
     * In this case, in the shelf for veggies
     */
    public void refillVeggies(){
        
    }
    
    /** Puts one instance of an ingredient in the corresponding shelf.
     * In this case, in the shelf for cheese
     */
    public void refillCheese(){
        
    }
    
    /** Puts one instance of an ingredient in the corresponding shelf.
     * In this case, in the shelf for pineapple
     */
    public void refillPineapple(){
        
    }
    
    /** Helper method to get the number of ingredients of type sauce are
     * currently present in the corresponding shelf.
     * 
     * @return the number of sauce items in the corresponding shelf 
     */
    public int getSauceStorageLevel(){
        return 0;
    }

    /** Helper method to get the number of ingredients of type ham are
     * currently present in the corresponding shelf.
     * 
     * @return the number of ham items in the corresponding shelf 
     */
    public int getHamStorageLevel(){
        return 0;
    }
    
    /** Helper method to get the number of ingredients of type veggies are
     * currently present in the corresponding shelf.
     * 
     * @return the number of veggies items in the corresponding shelf 
     */
    public int getVeggiesStorageLevel(){
        return 0;
    }

    /** Helper method to get the number of ingredients of type cheese are
     * currently present in the corresponding shelf.
     * 
     * @return the number of cheese items in the corresponding shelf 
     */
    public int getCheeseStorageLevel(){
        return 0;
    }

    /** Helper method to get the number of ingredients of type pineapple are
     * currently present in the corresponding shelf.
     * 
     * @return the number of pineapple items in the corresponding shelf 
     */
    public int getPineappleStorageLevel(){
        return 0;
    }
}
