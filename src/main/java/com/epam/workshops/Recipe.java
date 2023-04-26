package com.epam.workshops;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    List<Step> steps;

    public Recipe() {
        steps = new ArrayList<>();
    }

    void addStep(Step step) {
        steps.add(step);
    }

    int getTotal(Ingredient ingredient) {
        int total = 0;
        for (Step step : steps) {
            if (step.getIngredient().equals(ingredient) && !step.getStage().equals(Stage.POUR)) {
                total += step.getIngredientAmount();
            }
        }
        return total;
    }

    public List<Step> getSteps() {
        return steps;
    }
}
