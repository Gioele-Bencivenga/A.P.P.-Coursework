/* **********************
 * CSC-20004 COURSEWORK *
 * Due date: 7 May 2020 *
 * **********************/ 

package uk.ac.keele.csc20004.pizzeria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/** An implementation of an order for a pizzeria. 
 * The order is basically a Collection storing a list of Pizzas. The inner data 
 * structure used as storage does not need to be disclosed. The only functionality
 * that is advertised to users of this class is the fact that it is possible to
 * iterate through the elements of the class itself.
 *
 * @author Marco Ortolani
 */
public class Order implements Iterable<Pizza> {
    private final ArrayList pizzas;
    private final int[] quantities = new int[Pizza.PIZZA_TYPES];
        
    /** This constructor initialises the order via an existing collection.
     * Internally, it will compute how many pizzas of the different types are
     * contained in the order. This may be useful for further processing.
     * 
     * @see Pizza
     * @see Collection
     * 
     * @param order a Collection (any class implementing the Collection interface) 
     * of Pizza objects
     */
    public Order(Collection<Pizza> order) {
        pizzas = new ArrayList(order);

        for(Pizza p : order) {
            if (p.isMargherita()) quantities[Pizza.MARGHERITA]++;
            else if (p.isRomana()) quantities[Pizza.ROMANA]++;
            else if (p.isVegetarian()) quantities[Pizza.VEGETARIAN]++;
            else if (p.isHawaiian()) quantities[Pizza.HAWAIIAN]++;
        }
    }

    /** Builds an iterator for this order. In this implementation an ArrayList is
     * used internally as a data structure, so its iterator is returned.
     * 
     * @return the iterator to go through the pizzas in the order
     */
    @Override
    public Iterator<Pizza> iterator() {
        return pizzas.iterator();
    }
    
    /** Overridden toString() method to provide a concise textual visualisation
     * of the order
     * 
     * @return a string representing the list of the pizzas in the order, and their
     * quantity
     */
    @Override
    public String toString() {
        String description = "[";
        
        for (int i = 0; i < Pizza.PIZZA_TYPES; i++) {
            if (quantities[i] == 0) continue;
            
            switch(i) {
                case Pizza.MARGHERITA:
                    description += " " + quantities[i] + " margheritas";
                    break;
                case Pizza.ROMANA:
                    description += " " + quantities[i] + " romanas";
                    break;
                case Pizza.VEGETARIAN:
                    description += " " + quantities[i] + " veg pizzas";
                    break;
                case Pizza.HAWAIIAN:
                    description += " " + quantities[i] + " hawaiian pizzas";
                    break;
            }
        }
        description += " ]";
        
        return description;
    }
}
