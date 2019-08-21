package de.twometer.arduleucht.util;

import java.net.URL;
import java.util.Objects;

public class ResourceLoader {

    public static URL getResource(String name) {
        URL resLoc = ResourceLoader.class.getClassLoader().getResource(name);
        return Objects.requireNonNull(resLoc);
    }

}
