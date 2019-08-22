package de.twometer.arduleucht.gui;

import java.io.Serializable;

class Tagged implements Serializable {

    private String value;

    private String tag;

    Tagged(String value) {
        this.value = value;
    }

    Tagged(String value, String tag) {
        this.value = value;
        this.tag = tag;
    }

    String getValue() {
        return value;
    }

    String getTag() {
        return tag;
    }

}
