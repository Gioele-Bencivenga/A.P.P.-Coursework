/* **********************
 * CSC-20004 COURSEWORK *
 * Due date: 7 May 2020 *
 * **********************/ 

package uk.ac.keele.csc20004.pizzeria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** The representation of a Pizza. Only 4 different types of pizzas are currently
 * available 
 *
 * @author Marco Ortolani
 */
public class Pizza {
    public static final int PIZZA_TYPES = 4;
    
    public static final int MARGHERITA = 0;
    public static final int VEGETARIAN = 1;
    public static final int ROMANA = 2;
    public static final int HAWAIIAN = 3;
    
    public static final int PREP_TIME_MARGHERITA = 1000;
    public static final int PREP_TIME_VEGETARIAN = 1000;
    public static final int PREP_TIME_ROMANA = 1500;
    public static final int PREP_TIME_HAWAIIAN = 2000;
    
    private final ArrayList<Ingredient> ingredients;
    private final int preparationTime;

    /** This constructor is private; instances of Pizza can only be created 
     * via the create*() methods below. This ensures that only pizzas of the 
     * correct types may be created. The type of the pizza is inferred from the 
     * ingredients.
     * 
     * @param in an array of Ingredient used to initialise the pizza. Only some
     * combinations of ingredients are allowed (as specified in the coursework text).
     * An IllegalArgumentException is thrown is the wrong combination of ingredients
     * is used
     * @param prepTime the (simulated) time it takes for a pizza to be cooked 
     * 
     */
    private Pizza(Ingredient[] in, int prepTime) {
        int pizzaType = checkIngredients(in);
        if (pizzaType < 0 || pizzaType >= Pizza.PIZZA_TYPES) {
            throw new IllegalArgumentException("Wrong sequence of ingredients");
        }
        
        ingredients = new ArrayList<>(in.length);
        Collections.addAll(ingredients, in);
        
        preparationTime = prepTime;        
    }

    /** A helper method to check if a combination of ingredients represents one
     * of the allowed pizzas.
     * 
     * @param in the array with the combination of ingredients to be checked.
     * @return the int used to encode the type of pizza
     */
    private int checkIngredients(Ingredient[] in) {
        Ingredient sauce = Ingredient.createSauce();
        Ingredient cheese = Ingredient.createCheese();
        Ingredient ham = Ingredient.createHam();
        Ingredient veggies = Ingredient.createVeggies();
        Ingredient pineapple = Ingredient.createPineapple();
        
        List inList = Arrays.asList(in);
        if (inList.contains(sauce) && inList.contains(cheese) &&
            !(inList.contains(ham) || inList.contains(veggies) || inList.contains(pineapple))) {
            return Pizza.MARGHERITA;
        } else if (inList.contains(sauce) && inList.contains(veggies) &&
            !(inList.contains(ham) || inList.contains(cheese) || inList.contains(pineapple))) {
            return Pizza.VEGETARIAN;
        } else if (inList.contains(cheese) && inList.contains(ham) &&
            !(inList.contains(sauce) || inList.contains(veggies) || inList.contains(pineapple))) {
            return Pizza.ROMANA;
        } else if (inList.contains(ham) && inList.contains(pineapple) &&
            !(inList.contains(sauce) || inList.contains(cheese) || inList.contains(veggies))) {
            return Pizza.HAWAIIAN;
        } else {
            throw new IllegalArgumentException("Wrong sequence of ingredients");
        }
    }
    
    /** The constructor of this class is private, hence it is not accessible, not 
     * even by subclasses; instances of Pizza can only be created 
     * via the create*() methods like this. This ensures that only Pizza of 
     * the correct types may be created. 
     * 
     * @return a properly formed Pizza object, of type Margherita
     */
    public static Pizza createMargherita() {
        Ingredient sauce = Ingredient.createSauce();
        Ingredient cheese = Ingredient.createCheese();
        Ingredient[] in = {sauce, cheese};
        
        return new Pizza(in, Pizza.PREP_TIME_MARGHERITA);
    }

    /** The constructor of this class is private, hence it is not accessible, not 
     * even by subclasses; instances of Pizza can only be created 
     * via the create*() methods like this. This ensures that only Pizza of 
     * the correct types may be created. 
     * 
     * @return a properly formed Pizza object, of type Romana
     */
    public static Pizza createRomana() {
        Ingredient cheese = Ingredient.createCheese();
        Ingredient ham = Ingredient.createHam();
        Ingredient[] in = {cheese, ham};
        
        return new Pizza(in, Pizza.PREP_TIME_ROMANA);
    }
    
    /** The constructor of this class is private, hence it is not accessible, not 
     * even by subclasses; instances of Pizza can only be created 
     * via the create*() methods like this. This ensures that only Pizza of 
     * the correct types may be created. 
     * 
     * @return a properly formed Pizza object, of type Vegetarian
     */
    public static Pizza createVegetarian() {
        Ingredient sauce = Ingredient.createSauce();
        Ingredient veggies = Ingredient.createVeggies();

        Ingredient[] in = {sauce, veggies};
        
        return new Pizza(in, Pizza.PREP_TIME_VEGETARIAN);
    }
    
    /** The constructor of this class is private, hence it is not accessible, not 
     * even by subclasses; instances of Pizza can only be created 
     * via the create*() methods like this. This ensures that only Pizza of 
     * the correct types may be created. 
     * 
     * @return a properly formed Pizza object, of type Hawaiian
     */
    public static Pizza createHawaiian() {
        Ingredient ham = Ingredient.createHam();
        Ingredient pineapple = Ingredient.createPineapple();
        Ingredient[] in = {ham, pineapple};
        
        return new Pizza(in, Pizza.PREP_TIME_HAWAIIAN);
    }
    
    /** Checks the list of ingredients to decide which kind of pizza this is.
     * I preferred to check the ingredients as opposed to storing the type, in case
     * the inner representation is changed in a later version.
     * 
     * @return true if this is a Margherita pizza
     */
    public boolean isMargherita() {
        return (hasCheese() && hasSauce()) &&
                !(hasVeggies() || hasHam() || hasPineapple());
    }
    
    /** Checks the list of ingredients to decide which kind of pizza this is.
     * I preferred to check the ingredients as opposed to storing the type, in case
     * the inner representation is changed in a later version.
     * 
     * @return true if this is a Vegetarian pizza
     */
    public boolean isVegetarian() {
        return (hasSauce() && hasVeggies()) &&
                !(hasCheese() || hasHam() || hasPineapple());
    }
    
    /** Checks the list of ingredients to decide which kind of pizza this is.
     * I preferred to check the ingredients as opposed to storing the type, in case
     * the inner representation is changed in a later version.
     * 
     * @return true if this is a Romana pizza
     */
    public boolean isRomana() {
        return (hasCheese() && hasHam()) &&
                !(hasSauce() || hasVeggies() || hasPineapple());
    }

    /** Checks the list of ingredients to decide which kind of pizza this is.
     * I preferred to check the ingredients as opposed to storing the type, in case
     * the inner representation is changed in a later version.
     * 
     * @return true if this is a Hawaiian pizza
     */
    public boolean isHawaiian() {
        return (hasHam() && hasPineapple()) &&
                !(hasSauce() || hasCheese() || hasVeggies());
    }

    /** Helper method to check if this pizza contains a specific ingredient.
     * 
     * @return true if the pizza contains cheese
     */
    public boolean hasCheese() {
        Ingredient cheese = Ingredient.createCheese();

        return ingredients.contains(cheese);
    }

    /** Helper method to check if this pizza contains a specific ingredient.
     * 
     * @return true if the pizza contains cheese
     */
    public boolean hasSauce() {
        Ingredient sauce = Ingredient.createSauce();

        return ingredients.contains(sauce);
    }

    /** Helper method to check if this pizza contains a specific ingredient.
     * 
     * @return true if the pizza contains veggies
     */
    public boolean hasVeggies() {
        Ingredient veggies = Ingredient.createVeggies();

        return ingredients.contains(veggies);
    }

    /** Helper method to check if this pizza contains a specific ingredient.
     * 
     * @return true if the pizza contains ham
     */
    public boolean hasHam() {
        Ingredient ham = Ingredient.createHam();

        return ingredients.contains(ham);
    }

    /** Helper method to check if this pizza contains a specific ingredient.
     * 
     * @return true if the pizza contains pineapple
     */
    public boolean hasPineapple() {
        Ingredient pineapple = Ingredient.createPineapple();

        return ingredients.contains(pineapple);
    }
    
    /** Helper method to get a list of all ingredients in the pizza.
     * 
     * @return a (copy of the) list of the ingredients of the pizza
     */
    public Ingredient[] getIngredients() {
        Ingredient[] i = new Ingredient[ingredients.size()];
        
        return ingredients.toArray(i);
    }
    
    /** Helper method to get the time it takes for the pizza to cook.
     * 
     * @return the preparation time (millisec)
     */
    public int getPrepTime() {
        return preparationTime;
    }
    
    /** Overridden toString() method to provide a concise textual visualisation
     * of the pizza
     * 
     * @return a string representing the list of ingredients in the pizza
     */
    @Override
    public String toString(){
        if(this.isMargherita()) return "margherita (tomatosauce+cheese)";
        else if(this.isRomana()) return "romana (tomatosauce+cheese+ham)";
        else if(this.isVegetarian()) return "vegetarian (tomatosauce+veggies)";
        else if(this.isHawaiian()) return "hawaiian (tomatosauce+ham+pineapple)";
        else return super.toString();
    }
}
