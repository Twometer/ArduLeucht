package de.twometer.arduleucht.blocks.base;

public enum BlockCategory {

    TETRAPIX("category.tetrapix", "icons/category-tetrapix.png"),
    CONTROL("category.control", "icons/category-control.png"),
    VARIABLES("category.variables", "icons/category-var.png");

    private String name;

    private String iconPath;

    BlockCategory(String name, String iconPath) {
        this.name = name;
        this.iconPath = iconPath;
    }

    public String getName() {
        return name;
    }

    public String getIconPath() {
        return iconPath;
    }
}
