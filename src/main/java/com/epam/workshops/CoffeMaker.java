package com.epam.workshops;

import java.io.*;
import java.util.*;

public class CoffeMaker {

    private static final int MAX_COFFEE = 2000;
    private static final int MAX_WATER = 1500;
    private static final int MAX_MILK = 1000;
    private static final int MAX_COCOA = 2000;
    public static final String MAKE_COFFEE = "make coffee";

    Map<String, Method> methods;
    Map<String, Recipe> recipes;
    Map<Ingredient, Integer> ingredientsAmount;
    public CoffeMaker() {
        Random random = new Random();
        this.ingredientsAmount = new EnumMap<>(Ingredient.class);
        ingredientsAmount.put(Ingredient.COFFEE, random.nextInt(MAX_COFFEE));
        ingredientsAmount.put(Ingredient.WATER, random.nextInt(MAX_WATER));
        ingredientsAmount.put(Ingredient.MILK, random.nextInt(MAX_MILK));
        ingredientsAmount.put(Ingredient.COCOA, random.nextInt(MAX_COCOA));

        this.methods = new HashMap<>();
        this.methods.put("hello", () -> System.out.println("Hello!"));
        this.methods.put(MAKE_COFFEE, this::makeCoffee);
        this.methods.put("status", this::showStatus);
        this.methods.put("exit", () -> System.exit(0));
        this.methods.put("priviet", () -> System.out.println("Priviet!"));

        this.recipes = new HashMap<>();
        this.recipes.put(MAKE_COFFEE, createCoffeeRecipe());
    }

    public void callCommand(String command) {

        Optional<File> file = Optional.empty();
        String[] afterSplit = command.split(" ");
        if (afterSplit.length > 1) {
            file = Optional.of(new File(System.getProperty("user.dir") +
                    "\\src\\main\\java\\com\\epam\\workshops\\" +
                    afterSplit[1] + ".rcp"));
        }

        Method method = this.methods.get(command.toLowerCase(Locale.ROOT));
        if (method != null) {
            method.execute();
        } else if (afterSplit[0].equals("make") && afterSplit.length > 1 && file.isPresent() && file.get().exists()) {
            try {
                makeBeverageFromFile(file.get());
            } catch (FileNotFoundException e) {
                // Intentional ignore, since existence of file is checked earlier
            }
        } else {
            System.out.println("Unknown Command");
        }
    }

    private void makeBeverageFromFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        Recipe recipe = new Recipe();
        while (scanner.hasNextLine()) {
            String line = scanner.next();
            recipe.addStep(createStep(line));
        }
        scanner.close();

        runRecipe(recipe);
    }

    private Step createStep(String line) {
        String[] parts = line.split(":");
        return new Step(Ingredient.valueOf(parts[1]), Integer.parseInt(parts[2]), Stage.valueOf(parts[0]),
                Integer.parseInt(parts[3]));
    }

    public void makeCoffee() {
        runRecipe(recipes.get(MAKE_COFFEE));
    }

    public void showStatus() {
        Integer coffeeAmount = ingredientsAmount.get(Ingredient.COFFEE);
        System.out.println("Coffee: " + (coffeeAmount * 100)/MAX_COFFEE + "% " + coffeeAmount + "g");
        Integer waterAmount = ingredientsAmount.get(Ingredient.WATER);
        System.out.println("Water: " + (waterAmount * 100)/MAX_WATER + "% " + waterAmount + "g");
        Integer milkAmount = ingredientsAmount.get(Ingredient.MILK);
        System.out.println("Milk: " + (milkAmount * 100)/MAX_MILK + "% " + milkAmount + "g");
        Integer cocoaAmount = ingredientsAmount.get(Ingredient.COCOA);
        System.out.println("Cocoa: " + (cocoaAmount * 100)/MAX_COCOA + "% " + cocoaAmount + "g");
    }

    private Recipe createCoffeeRecipe() {
        Recipe recipe = new Recipe();
        recipe.addStep(new Step(Ingredient.COFFEE, 11, Stage.GRIND, 10));
        recipe.addStep(new Step(Ingredient.WATER, 200, Stage.HEAT, 15));
        recipe.addStep(new Step(Ingredient.WATER, 30, Stage.POUR, 20));
        recipe.addStep(new Step(Ingredient.WATER, 170, Stage.POUR, 10));

        return recipe;
    }

    private void runRecipe(Recipe recipe) {
        if (recipe.getTotal(Ingredient.COFFEE) > ingredientsAmount.get(Ingredient.COFFEE)) {
            System.out.println("Not enough coffee!");
        } else if (recipe.getTotal(Ingredient.WATER) > ingredientsAmount.get(Ingredient.WATER)) {
            System.out.println("Not enough water!");
        } else if (recipe.getTotal(Ingredient.MILK) > ingredientsAmount.get(Ingredient.MILK)) {
            System.out.println("Not enough milk!");
        } else if (recipe.getTotal(Ingredient.COCOA) > ingredientsAmount.get(Ingredient.COCOA)) {
            System.out.println("Not enough cocoa!");
        } else {
            for (Step step: recipe.getSteps()) {
                step.print();
                updateIngredients(step);
            }
        }
    }

    private void updateIngredients(Step step) {
        if (!step.getStage().equals(Stage.POUR)) {
            Integer amount = ingredientsAmount.get(step.getIngredient());
            amount -= step.getIngredientAmount();
            ingredientsAmount.put(step.getIngredient(), amount);
        }
    }
}
