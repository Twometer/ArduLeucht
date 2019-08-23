package de.twometer.arduleucht.render;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

class Polygon {

    private List<Double> x = new ArrayList<>();

    private List<Double> y = new ArrayList<>();

    private double xo;

    private double yo;

    Polygon(double xo, double yo) {
        this.xo = xo;
        this.yo = yo;
    }

    void addPoint(double x, double y) {
        this.x.add(x + xo + 0.5);
        this.y.add(y + yo + 0.5);
    }

    void render(GraphicsContext ctx) {
        ctx.fillPolygon(toArray(x), toArray(y), x.size());

        ctx.setEffect(null);
        ctx.setStroke(LeuchtColors.ASPHALT);
        ctx.strokePolygon(toArray(x), toArray(y), x.size());
    }

    private double[] toArray(List<Double> dbl) {
        double[] val = new double[dbl.size()];
        for (int i = 0; i < dbl.size(); i++) val[i] = dbl.get(i);
        return val;
    }

}
