/* **********************
 * CSC-20004 COURSEWORK *
 * Due date: 7 May 2020 *
 * **********************/
package uk.ac.keele.csc20004.pizzeria.task1;

import uk.ac.keele.csc20004.pizzeria.task2.MyPizzeria;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.keele.csc20004.pizzeria.*;

/**
 * An implementation of the interface Cook. Taken from SampleCook.java and
 * modified.
 *
 * @author Marco Ortolani, Repurposed by 18016286.
 */
public class MyCook extends Thread implements Cook {

    private final String name; // the cook's name
    private final MyPizzeria pizzeria; // the pizzeria to which the cook belongs

    /**
     * Specifies the cook's behaviour regarding preparing orders.
     *
     * 0 = the cook prepares both eat in and take away orders. 1 = the cook
     * prepares orders from the primary (eat in) chain. 2 = the cook prepares
     * orders from the secondary (take away) chain.
     */
    protected int cookType;

    /**
     * Specifies which kind of order the cook is working with.
     *
     * 1 = eat in order. 2 = take away order.
     */
    protected int orderType;

    /**
     * A bare constructor, just to initialise the internal variables.
     *
     * @param _name the name of the cook (just used for printing purposes)
     * @param _pizzeria the pizzeria the cook belongs to
     */
    public MyCook(String _name, MyPizzeria _pizzeria, int _cookType) {
        name = _name;
        pizzeria = _pizzeria;
        cookType = _cookType;
    }

    /**
     * Simulates the working of a cook. As long as there are orders waiting to
     * be processed the cook will prepare the next order in the list of orders.
     * Orders are delivered at the end of the preparation.
     *
     * In case of task2 and task3: priority is given to eat in orders in a way
     * that should not leave the eat in order list with more orders than the
     * take away list. I didn't find many instructions regarding priority so I
     * interpreted it this way, it felt unnatural to apply priority like we've
     * seen in Practical 8 (taking on take away orders only if eat in orders are
     * finished)
     */
    @Override
    public void run() {
        while (true) {
            while (cookType == 0) {
                while (pizzeria.getNumOfWaitingOrders() > 0 || pizzeria.getNumOfWaitingTakeAwayOrders() > 0) {
                    while (pizzeria.getNumOfWaitingOrders() >= pizzeria.getNumOfWaitingTakeAwayOrders()) {
                        orderType = 1;
                        prepareOrder(pizzeria.getNextOrder());
                    }
                    while (pizzeria.getNumOfWaitingOrders() < pizzeria.getNumOfWaitingTakeAwayOrders()) {
                        orderType = 2;
                        prepareOrder(pizzeria.getNextTakeAwayOrder());
                    }
                }
            }
            while (cookType == 1) {
                while (pizzeria.getNumOfWaitingOrders() > 0) {
                    orderType = 1;
                    prepareOrder(pizzeria.getNextOrder());
                }
            }
            while (cookType == 2) {
                while (pizzeria.getNumOfWaitingTakeAwayOrders() > 0) {
                    orderType = 2;
                    prepareOrder(pizzeria.getNextTakeAwayOrder());
                }
            }
        }
    }

    /**
     * Simulate the cooking of a pizza by sleeping for the specified amount of
     * time. It is a private function as a Cook works on Orders not on single
     * Pizzas
     *
     * @param p the pizza to be cooked
     */
    private void cookPizza(Pizza p) {
        System.out.println(name + ": begins cooking pizza " + p.toString());

        try {
            Thread.sleep(p.getPrepTime());
        } catch (InterruptedException ex) {
            Logger.getLogger(MyCook.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(name + ": pizza " + p.toString() + " is ready");
    }

    /**
     * Checks if we are able to prepare an order.
     *
     * The method first checks priority (eat in cooks should prepare first) and
     * then checks if enough ingredients are contained in the shelves in order
     * to complete the order.
     *
     * @param o the order we want to check if we can prepare
     * @return true/false based on if we can prepare the order or not
     */
    public boolean canPrepare(Order o) {
        int sauceNeeded = 0;
        int cheeseNeeded = 0;
        int hamNeeded = 0;
        int veggiesNeeded = 0;
        int pineappleNeeded = 0;

        /**
         * If we are a takeAway cook: before doing any other check we see if
         * there are more eat in orders than take away. If so then we cannot
         * prepare the order since eat in orders have priority.
         */
        if (cookType == 2) {
            if (pizzeria.getNumOfWaitingOrders() >= pizzeria.getNumOfWaitingTakeAwayOrders()) {
                if (pizzeria.getNumOfWaitingTakeAwayOrders() == 0) {
                    return false;
                }
                System.out.println(name + ": giving priority to eat in");
                return false;
            }
        }

        // counting the ingredients needed by all of the pizzas in the order
        for (Pizza pizza : o) {
            for (Ingredient i : pizza.getIngredients()) {
                if (i.isSauce()) {
                    sauceNeeded++;
                }
                if (i.isCheese()) {
                    cheeseNeeded++;
                }
                if (i.isHam()) {
                    hamNeeded++;
                }
                if (i.isVeggies()) {
                    veggiesNeeded++;
                }
                if (i.isPineapple()) {
                    pineappleNeeded++;
                }
            }
        }

        // checking if the shelves contain the amount of ingredients needed
        if (sauceNeeded > pizzeria.getSauceStorageLevel()) {
            System.out.println(name + ": not enough sauce");
            return false;
        }
        if (cheeseNeeded > pizzeria.getCheeseStorageLevel()) {
            System.out.println(name + ": not enough cheese");
            return false;
        }
        if (hamNeeded > pizzeria.getHamStorageLevel()) {
            System.out.println(name + ": not enough ham");
            return false;
        }
        if (veggiesNeeded > pizzeria.getVeggiesStorageLevel()) {
            System.out.println(name + ": not enough veggies");
            return false;
        }
        if (pineappleNeeded > pizzeria.getPineappleStorageLevel()) {
            System.out.println(name + ": not enough pineapple");
            return false;
        }

        // if no conditions made us return false, then we return true
        return true;
    }

    /**
     * Simulate the preparation of an order by a double iteration: through the
     * list of the pizzas in the order, and then, for each pizza, through its
     * ingredients. This method simulates the fetching of ingredients and calls
     * cookPizza()
     *
     * @param o the order to be processed
     */
    @Override
    public void prepareOrder(Order o) {
        System.out.println(name + ": begins preparing order " + o);

        while (!canPrepare(o)) {
            try { // we sleep x seconds before checking again
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }

        System.out.println(name + ": ingredients available!");

        for (Pizza pizza : o) {
            System.out.println(name + ": begins assembling pizza " + pizza);
            for (Ingredient i : pizza.getIngredients()) {
                System.out.println(name + ": trying to fetch " + i);
                if (i.isSauce()) {
                    pizzeria.fetchSauce();
                }
                if (i.isCheese()) {
                    pizzeria.fetchCheese();
                }
                if (i.isHam()) {
                    pizzeria.fetchHam();
                }
                if (i.isVeggies()) {
                    pizzeria.fetchVeggies();
                }
                if (i.isPineapple()) {
                    pizzeria.fetchPineapple();
                }
                System.out.println(name + ": fetched " + i);
            }

            cookPizza(pizza);
        }

        switch (cookType) {
            case 0:
                if (orderType == 1) {
                    pizzeria.deliverOrder(o);
                } else if (orderType == 2) {
                    pizzeria.deliverTakeAwayOrder(o);
                }
                break;
            case 1:
                if (orderType == 1) {
                    pizzeria.deliverOrder(o);
                } else {
                    System.out.println("\nThis shouldn't have happened, check cook's deliver order\n");
                }
                break;
            case 2:
                if (orderType == 2) {
                    pizzeria.deliverTakeAwayOrder(o);
                } else {
                    System.out.println("\nThis shouldn't have happened, check cook's deliver order\n");
                }
                break;
            default:
                break;
        }
    }
}
