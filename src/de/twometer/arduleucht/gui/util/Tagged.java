package de.twometer.arduleucht.gui.util;

import java.io.Serializable;

public class Tagged implements Serializable {

    private String value;

    private String tag;

    public Tagged(String value) {
        this.value = value;
    }

    public Tagged(String value, String tag) {
        this.value = value;
        this.tag = tag;
    }

    public String getValue() {
        return value;
    }

    public String getTag() {
        return tag;
    }

}
