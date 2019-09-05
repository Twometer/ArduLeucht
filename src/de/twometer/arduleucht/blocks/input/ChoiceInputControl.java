package de.twometer.arduleucht.blocks.input;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ChoiceInputControl extends InputControl {

    private List<String> choices = new ArrayList<>();

    public ChoiceInputControl(Consumer<String> valueConsumer) {
        super(valueConsumer);
    }

    public List<String> getChoices() {
        return choices;
    }

}
