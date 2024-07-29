package com.codigo04.budget_manager.category.infra;

public class CategoryErrorMessages {

    public static final String CATEGORY_NOT_FOUND = "category.not.found";
    public static final String CATEGORY_NAME_ALREADY_EXISTS = "category.name.already.exists";

    private CategoryErrorMessages() {
        throw new IllegalStateException("Utility class");
    }

}
