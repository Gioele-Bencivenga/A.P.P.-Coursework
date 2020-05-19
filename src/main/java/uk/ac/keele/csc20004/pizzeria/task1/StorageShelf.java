/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.keele.csc20004.pizzeria.task1;

import java.util.LinkedList;
import java.util.Queue;
import uk.ac.keele.csc20004.pizzeria.Ingredient;

/**
 * A spin on the Buffer class seen in the practicals. Basically a queue
 * implemented with a linked list.
 *
 * @author Marco Ortolani Repurposed by 18016286
 */
public class StorageShelf {

    /**
     * The maximum number of ingredients that can be stored in the shelf.
     * Further ingredients may be put on hold or just rejected.
     */
    public static final int MAX_INGREDIENTS = 25;

    /**
     * The type of ingredient that is stored in the shelf.
     *
     * SAUCE = 0; CHEESE = 1; VEGGIES = 2; HAM = 3; PINEAPPLE = 4;
     */
    public int shelfType;

    private Queue<Ingredient> list;
    private int size;

    public StorageShelf(int _size, int _shelfType) {
        list = new LinkedList<>();
        size = _size;

        shelfType = _shelfType;
    }

    /**
     * Inserts the specified element into this queue.
     *
     * @param _ingredient the ingredient to add to the queue
     */
    public synchronized void add(Ingredient _ingredient) throws InterruptedException {
        while (list.size() >= size) {
            wait();
        }
        list.add(_ingredient);
        notifyAll();
    }

    /**
     * Retrieves and removes the head of this queue, or returns null if this
     * queue is empty.
     *
     * @return the head of the queue or null if the queue is empty
     */
    public synchronized Ingredient poll() throws InterruptedException {
        while (list.size() == 0) {
            wait();
        }
        Ingredient ingredient = list.poll();
        notifyAll();
        return ingredient;
    }

    /**
     * Returns the number of elements in this collection.
     *
     * @return the number of elements in this collection
     */
    public int getSize() {
        return list.size();
    }
}
