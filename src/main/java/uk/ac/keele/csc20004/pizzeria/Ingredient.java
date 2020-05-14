/* **********************
 * CSC-20004 COURSEWORK *
 * Due date: 7 May 2020 *
 * **********************/ 

package uk.ac.keele.csc20004.pizzeria;

/** A class whose objects represent any of the possible ingredients for our
 * simulated pizzas.
 *
 * @author Marco Ortolani
 */
public class Ingredient {
    private static final int SAUCE      = 0;
    private static final int CHEESE     = 1;
    private static final int VEGGIES    = 2;
    private static final int HAM        = 3;
    private static final int PINEAPPLE  = 4;
    
    private final int ingredientType;
    
    /** This constructor is private; instances of Ingredient can only be created 
     * via the create*() methods below. This ensures that only ingredients of the 
     * correct types may be created.
     * 
     * @param type the inner int used to represent the different types of ingredients
     */
    private Ingredient(int type) {
        ingredientType = type;
    }
    
    /** The constructor of this class is private, hence it is not accessible, not 
     * even by subclasses; instances of Ingredient can only be created 
     * via the create*() methods like this. This ensures that only Ingredients of 
     * the correct types may be created. Note how the ingredientType variable is
     * never exposed.
     * 
     * @return an Ingredient object representing Tomato sauce
     */
    public static Ingredient createSauce() {
        return new Ingredient(SAUCE);
    }

    /** The constructor of this class is private, hence it is not accessible, not 
     * even by subclasses; instances of Ingredient can only be created 
     * via the create*() methods like this. This ensures that only Ingredients of 
     * the correct types may be created. Note how the ingredientType variable is
     * never exposed.
     * 
     * @return an Ingredient object representing Cheese
     */
    public static Ingredient createCheese() {
        return new Ingredient(CHEESE);
    }

    /** The constructor of this class is private, hence it is not accessible, not 
     * even by subclasses; instances of Ingredient can only be created 
     * via the create*() methods like this. This ensures that only Ingredients of 
     * the correct types may be created. Note how the ingredientType variable is
     * never exposed.
     * 
     * @return an Ingredient object representing Veggies
     */
    public static Ingredient createVeggies() {
        return new Ingredient(VEGGIES);
    }

    /** The constructor of this class is private, hence it is not accessible, not 
     * even by subclasses; instances of Ingredient can only be created 
     * via the create*() methods like this. This ensures that only Ingredients of 
     * the correct types may be created. Note how the ingredientType variable is
     * never exposed.
     * 
     * @return an Ingredient object representing Ham
     */
    public static Ingredient createHam() {
        return new Ingredient(HAM);
    }
    
    /** The constructor of this class is private, hence it is not accessible, not 
     * even by subclasses; instances of Ingredient can only be created 
     * via the create*() methods like this. This ensures that only Ingredients of 
     * the correct types may be created. Note how the ingredientType variable is
     * never exposed.
     * 
     * @return an Ingredient object representing Pineapple
     */
    public static Ingredient createPineapple() {
        return new Ingredient(PINEAPPLE);
    }
    
    /** Checks the if this Ingredient is of a specific type.
     * 
     * @return true if this is Sauce
     */
    public boolean isSauce() {
        return (ingredientType == SAUCE);
    }

    /** Checks the if this Ingredient is of a specific type.
     * 
     * @return true if this is Cheese
     */
    public boolean isCheese() {
        return (ingredientType == CHEESE);
    }

    /** Checks the if this Ingredient is of a specific type.
     * 
     * @return true if this is Veggies
     */

    public boolean isVeggies() {
        return (ingredientType == VEGGIES);
    }

    /** Checks the if this Ingredient is of a specific type.
     * 
     * @return true if this is Ham
     */
    public boolean isHam() {
        return (ingredientType == HAM);
    }

    /** Checks the if this Ingredient is of a specific type.
     * 
     * @return true if this is Pineapple
     */
    public boolean isPineapple() {
        return (ingredientType == PINEAPPLE);
    }
    
    /** Overridden version of the toString() method of Object(). Two ingredients are
     * equal if they are of the same type (and of course, both instances of class 
     * Ingredient)
     * 
     * @param o the Object to compare to
     * @return true if both objects are instances of Ingredient and are of the 
     * same type
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Ingredient) {
            return (((Ingredient)o).ingredientType == this.ingredientType);
        } else {
            return super.equals(o);
        }
    }
    
    /** Overridden version of the equals() method of Object(), that just prints a 
     *  string representing the ingredient type.
     * 
     * @return the "name" of the ingredient type
     */
    @Override
    public String toString() {
        switch (ingredientType) {
            case SAUCE:
                return "tomato sauce";
            case CHEESE:
                return "cheese";
            case VEGGIES:
                return "veggies";
            case HAM:
                return "ham";
            case PINEAPPLE:
                return "pineapple";
            default:
                return super.toString();
        }
    }
}
