package com.epam.workshops;

import java.util.Locale;

public class Step {
    private Ingredient ingredient;
    private int ingredientAmount;
    private Stage stage;
    private int time;

    public Step(Ingredient ingredient, int ingredientAmount, Stage stage, int time) {
        this.ingredient = ingredient;
        this.ingredientAmount = ingredientAmount;
        this.stage = stage;
        this.time = time;
    }

    void print() {
        System.out.println(
                stage.name().substring(0, 1).toUpperCase() +
                        stage.name().substring(1).toLowerCase(Locale.ROOT) +
                        " " +
                        ingredient.name().toLowerCase(Locale.ROOT) +
                        "..." +
                        "[waits " +
                        time +
                        " secs]");
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public int getIngredientAmount() {
        return ingredientAmount;
    }

    public Stage getStage() {
        return stage;
    }
}
