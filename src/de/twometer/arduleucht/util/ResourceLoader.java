package de.twometer.arduleucht.util;

import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public class ResourceLoader {

    public static URL getResource(String name) {
        URL resLoc = ResourceLoader.class.getClassLoader().getResource(name);
        return Objects.requireNonNull(resLoc);
    }

    public static InputStream getResourceAsStream(String name) {
        InputStream stream = ResourceLoader.class.getClassLoader().getResourceAsStream(name);
        return Objects.requireNonNull(stream);
    }

}
