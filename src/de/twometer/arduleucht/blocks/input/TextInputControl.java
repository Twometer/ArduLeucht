package de.twometer.arduleucht.blocks.input;

import java.util.function.Consumer;

public class TextInputControl extends InputControl {

    public TextInputControl(Consumer<String> valueConsumer) {
        super(valueConsumer);
    }

}
