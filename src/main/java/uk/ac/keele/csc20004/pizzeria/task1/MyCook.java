/* **********************
 * CSC-20004 COURSEWORK *
 * Due date: 7 May 2020 *
 * **********************/
package uk.ac.keele.csc20004.pizzeria.task1;

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
     */
    @Override
    public void run() {
        while (true) {
            while (cookType == 1) {
                while (pizzeria.getNumOfWaitingOrders() > 0) {
                    prepareOrder(pizzeria.getNextOrder());
                }
            }
            while (cookType == 2) {
                while (pizzeria.getNumOfWaitingTakeAwayOrders() > 0) {
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

    /**
     * Simulate the preparation of an order by a double iteration: through the
     * list of the pizzas in the order, and then, for each pizza, through its
     * ingredients. This method simulates the fetching of ingredients (just stub
     * methods in SamplePizzeria) and calls cookPizza()
     *
     * @param o the order to be processed
     */
    @Override
    public void prepareOrder(Order o) {
        System.out.println(name + ": begins working on order " + o);
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

        if (cookType == 1) {
            pizzeria.deliverOrder(o);
        } else if (cookType == 2) {
            pizzeria.deliverTakeAwayOrder(o);
        }
        /*
        Should I modify Order by creating MyOrder which also has orderType to 
        indentify if it's a takeaway or eatin order?
        It would be very useful to deliver orders for task3, otherwise cooks 
        will have to remember from which chain the current order came from.
        Check on the discussion board to see if marco answered.
         */
    }
}
