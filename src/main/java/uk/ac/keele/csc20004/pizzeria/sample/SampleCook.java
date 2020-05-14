/* **********************
 * CSC-20004 COURSEWORK *
 * Due date: 7 May 2020 *
 * **********************/ 

package uk.ac.keele.csc20004.pizzeria.sample;

import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.keele.csc20004.pizzeria.Cook;
import uk.ac.keele.csc20004.pizzeria.Ingredient;
import uk.ac.keele.csc20004.pizzeria.Order;
import uk.ac.keele.csc20004.pizzeria.Pizza;
import uk.ac.keele.csc20004.pizzeria.Pizzeria;

/** An implementation of the interface Cook. This is provided only as an
 * example and it is not part of the coursework.
 * This is an extremely simplified version of a Cook that shows you a possible
 * way of simulating the preparation of an order through the sleep() method.
 * 
 * @author Marco Ortolani
 */
public class SampleCook implements Cook {
    private final String name;
    private final Pizzeria pizzeria;
    
    /** A bare constructor, just to initialise the internal variables.
     * 
     * @param n the name of the cook (just used for printing purposes)
     * @param p the pizzeria the cook belongs to
     */
    public SampleCook(String n, Pizzeria p) {
        name = n;
        pizzeria = p;
    }

    /** Simulate the cooking of a pizza by sleeping for the specified amount
     * of time. It is a private function as a Cook works on Orders not on single 
     * Pizzas
     * 
     * @param p the pizza to be cooked
     */
    private void cookPizza(Pizza p) {
        System.out.println(name + ": begins cooking pizza " + p.toString());
        
        try {
            Thread.sleep(p.getPrepTime());
        } catch (InterruptedException ex) {
            Logger.getLogger(SampleCook.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(name + ": pizza " + p.toString() + " is ready");
    }
    
    /** Simulate the preparation of an order by a double iteration: through the list
     * of the pizzas in the order, and then, for each pizza, through its ingredients.
     * This method simulates the fetching of ingredients (just stub methods in
     * SamplePizzeria) and calls cookPizza()
     * 
     * @param o the order to be processed
     */
    @Override
    public void prepareOrder(Order o) {
        for (Pizza p : o) {
            for (Ingredient i : p.getIngredients()) {
                System.out.println(name + ": trying to fetch " + i);
                if (i.isSauce()) pizzeria.fetchSauce();
                if (i.isCheese()) pizzeria.fetchCheese();
                if (i.isHam()) pizzeria.fetchHam();
                if (i.isVeggies()) pizzeria.fetchVeggies();
                if (i.isPineapple()) pizzeria.fetchPineapple();
                System.out.println(name + ": fetched " + i);
            }

            cookPizza(p);
        }
        
        pizzeria.deliverOrder(o);
    }
}
