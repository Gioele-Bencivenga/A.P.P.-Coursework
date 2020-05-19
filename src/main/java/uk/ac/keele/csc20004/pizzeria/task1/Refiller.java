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
public class Refiller extends Thread {

    private final String name; // the refiller's name
    private final Pizzeria pizzeria; // the pizzeria to which the refiller belongs

    public Refiller(String _name, Pizzeria _pizzeria) {
        name = _name;
        pizzeria = _pizzeria;
    }

    public void refillShelf(StorageShelf _shelf, Ingredient _ingredient) {
        try {
            _shelf.add(_ingredient);
        } catch (InterruptedException _exception) {
            System.err.println(_exception);
        }
    }
}
