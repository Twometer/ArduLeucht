package de.twometer.arduleucht.blocks.input;

import java.util.function.Consumer;

public abstract class InputControl {

    private Consumer<String> valueConsumer;

    private String descriptionKey;

    InputControl(Consumer<String> valueConsumer) {
        this.valueConsumer = valueConsumer;
    }

    public Consumer<String> getValueConsumer() {
        return valueConsumer;
    }

    public InputControl describedBy(String key) {
        this.descriptionKey = key;
        return this;
    }

    public String getDescriptionKey() {
        return descriptionKey;
    }
}
