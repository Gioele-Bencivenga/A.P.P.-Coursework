/* **********************
 * CSC-20004 COURSEWORK *
 * Due date: 7 May 2020 *
 * **********************/ 

package uk.ac.keele.csc20004.pizzeria.task1;

import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.keele.csc20004.pizzeria.*;

/** An implementation of the interface Cook. 
 * Taken from SampleCook.java and modified.
 * 
 * @author Marco Ortolani
 * Repurposed by Gioele Bencivenga
 */
public class MyCook implements Cook {
    private final String name;
    private final Pizzeria pizzeria;
    
    /** A bare constructor, just to initialise the internal variables.
     * 
     * @param _name the name of the cook (just used for printing purposes)
     * @param _pizzeria the pizzeria the cook belongs to
     */
    public MyCook(String _name, Pizzeria _pizzeria) {
        name = _name;
        pizzeria = _pizzeria;
    }

    /** Simulate the cooking of a pizza by sleeping for the specified amount
     * of time. It is a private function as a Cook works on Orders not on single 
     * Pizzas
     * 
     * @param _pizza the pizza to be cooked
     */
    private void cookPizza(Pizza _pizza) {
        System.out.println(name + ": begins cooking pizza " + _pizza.toString());
        
        try {
            Thread.sleep(_pizza.getPrepTime());
        } catch (InterruptedException ex) {
            Logger.getLogger(MyCook.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(name + ": pizza " + _pizza.toString() + " is ready");
    }
    
    /** Simulate the preparation of an order by a double iteration: through the list
     * of the pizzas in the order, and then, for each pizza, through its ingredients.
     * This method simulates the fetching of ingredients (just stub methods in
     * SamplePizzeria) and calls cookPizza()
     * 
     * @param _order the order to be processed
     */
    @Override
    public void prepareOrder(Order _order) {
        for (Pizza pizza : _order) {
            for (Ingredient i : pizza.getIngredients()) {
                System.out.println(name + ": trying to fetch " + i);
                if (i.isSauce()) pizzeria.fetchSauce();
                if (i.isCheese()) pizzeria.fetchCheese();
                if (i.isHam()) pizzeria.fetchHam();
                if (i.isVeggies()) pizzeria.fetchVeggies();
                if (i.isPineapple()) pizzeria.fetchPineapple();
                System.out.println(name + ": fetched " + i);
            }

            cookPizza(pizza);
        }
        
        pizzeria.deliverOrder(_order);
    }
}
