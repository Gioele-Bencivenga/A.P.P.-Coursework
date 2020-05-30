/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.keele.csc20004.pizzeria.task1;

import uk.ac.keele.csc20004.pizzeria.Ingredient;
import uk.ac.keele.csc20004.pizzeria.Pizzeria;

/**
 * Like MyCook, but with shelves instead of pizzas.
 *
 * @author 18016286.
 */
public class Supplier extends Thread {

    private final String name; // the supplier's name
    private final Pizzeria pizzeria; // the pizzeria the supplier supplies

    public Supplier(String _name, Pizzeria _pizzeria) {
        name = _name;
        pizzeria = _pizzeria;
    }
    
    /**
     * Simulates the working of a supplier. As long as there are orders waiting to
     * be processed the cook will prepare the next order in the list of orders.
     * Orders are delivered at the end of the preparation.
     */
    @Override
    public void run(){
 
    }
    
    /**
     * Refills a shelf with 1 unit of an ingredient.
     *
     * @param _shelf the shelf we want to refill
     * @param _ingredient the ingredient we want to refill the shelf with
     */
    public void refillShelf(StorageShelf _shelf, Ingredient _ingredient, int aVariable) {
        try {
            _shelf.add(_ingredient);
        } catch (InterruptedException _exception) {
            System.err.println(_exception);
        }
    }
}
