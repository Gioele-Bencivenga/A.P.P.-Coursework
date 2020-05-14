/* **********************
 * CSC-20004 COURSEWORK *
 * Due date: 7 May 2020 *
 * **********************/ 

package uk.ac.keele.csc20004.pizzeria;

/** An interface defining the basic behaviour of a cook.
 *
 * @author Marco Ortolani
 */
public interface Cook {
    
    /** The only mandatory task for a cook is to be able to work on
     * an order.
     * 
     * You can of course add further functionalities, if necessary
     * 
     * @param o the order
     */
    public void prepareOrder(Order o);
}
