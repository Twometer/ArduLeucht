package de.twometer.arduleucht.blocks.model;

public enum BlockCategory {

    TETRAPIX("category.tetrapix", "icons/category-tetrapix.png"),
    CONTROL("category.control", "icons/category-control.png"),
    DATA("category.data", "icons/category-data.png");

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
