/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.keele.csc20004.pizzeria.task1;

import java.util.Random;
import uk.ac.keele.csc20004.pizzeria.*;

/**
 * Like MyCook, but with shelves instead of pizzas.
 *
 * @author 18016286.
 */
public class Supplier extends Thread {

    protected final String name; // the supplier's name
    protected final MyPizzeria pizzeria; // the pizzeria the supplier supplies

    public Supplier(String _name, MyPizzeria _pizzeria) {
        name = _name;
        pizzeria = _pizzeria;
    }

    /**
     * Simulates the working of a supplier.
     *
     * The supplier prioritises refilling shelves that are about to run out of
     * ingredients, if no shelf is about to run out then it refills a random
     * shelf.
     */
    @Override
    public void run() {
        while (true) {
            if (pizzeria.getSauceStorageLevel() < 5) {
                refillShelf(0);
            } else if (pizzeria.getCheeseStorageLevel() < 5) {
                refillShelf(1);
            } else if (pizzeria.getVeggiesStorageLevel() < 5) {
                refillShelf(2);
            } else if (pizzeria.getHamStorageLevel() < 5) {
                refillShelf(3);
            } else if (pizzeria.getPineappleStorageLevel() < 5) {
                refillShelf(4);
            } else {
                refillRandomShelf();
            }
        }
    }

    /**
     * Refills a shelf with 1 unit of an ingredient.
     *
     * 0 = sauce, 1 = cheese, 2 = veggies, 3 = ham, 4 = pineapple
     *
     * @param _shelfIndex the shelf we want to refill
     */
    private void refillShelf(int _shelfIndex) {
        switch (_shelfIndex) {
            case 0:
                if (pizzeria.getSauceStorageLevel() < StorageShelf.MAX_INGREDIENTS) {
                    try {
                        pizzeria.refillSauce();
                        System.out.println(this.name + ": Refilled sauce, left: " + pizzeria.getSauceStorageLevel());

                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.err.println(e);
                    }
                }
            case 1:
                if (pizzeria.getCheeseStorageLevel() < StorageShelf.MAX_INGREDIENTS) {
                    try {
                        pizzeria.refillCheese();
                        System.out.println(this.name + ": Refilled cheese, left: " + pizzeria.getCheeseStorageLevel());

                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        System.err.println(e);
                    }
                }
            case 2:
                if (pizzeria.getVeggiesStorageLevel() < StorageShelf.MAX_INGREDIENTS) {
                    try {
                        pizzeria.refillVeggies();
                        System.out.println(this.name + ": Refilled veggies, left: " + pizzeria.getVeggiesStorageLevel());

                        Thread.sleep(700);
                    } catch (InterruptedException e) {
                        System.err.println(e);
                    }
                }
            case 3:
                if (pizzeria.getHamStorageLevel() < StorageShelf.MAX_INGREDIENTS) {
                    try {
                        pizzeria.refillHam();
                        System.out.println(this.name + ": Refilled ham, left: " + pizzeria.getHamStorageLevel());

                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        System.err.println(e);
                    }
                }

            case 4:
                if (pizzeria.getPineappleStorageLevel() < StorageShelf.MAX_INGREDIENTS) {
                    try {
                        pizzeria.refillPineapple();
                        System.out.println(this.name + ": Refilled pineapple, left: " + pizzeria.getPineappleStorageLevel());

                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.err.println(e);
                    }
                }
        }
    }

    /**
     * Refills a random shelf with 1 unit of an ingredient.
     */
    private void refillRandomShelf() {
        Random random = new Random();

        refillShelf(random.nextInt(5));
    }
}
