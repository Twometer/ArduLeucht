package de.twometer.arduleucht.render.api;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;

public class Polygon {

    private List<Point> points = new ArrayList<>();

    private double xo;

    private double yo;

    public Polygon(double xo, double yo) {
        this.xo = xo;
        this.yo = yo;
    }

    public void addPoint(double x, double y) {
        this.points.add(new Point(x + xo + 0.5, y + yo + 0.5));
    }

    public void render(GraphicsContext ctx, Paint stroke) {
        double[] x = new double[points.size()];
        double[] y = new double[points.size()];

        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            x[i] = point.getX();
            y[i] = point.getY();
        }

        ctx.fillPolygon(x, y, points.size());

        ctx.setEffect(null);
        ctx.setStroke(stroke);
        ctx.strokePolygon(x, y, points.size());
    }

    public boolean test(Point test) {
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
            if ((points.get(i).getY() > test.getY()) != (points.get(j).getY() > test.getY()) &&
                    (test.getX() < (points.get(j).getX() - points.get(i).getX()) * (test.getY() - points.get(i).getY()) / (points.get(j).getY() - points.get(i).getY()) + points.get(i).getX())) {
                result = !result;
            }
        }
        return result;
    }

    private double[] toArray(List<Double> dbl) {
        double[] val = new double[dbl.size()];
        for (int i = 0; i < dbl.size(); i++) val[i] = dbl.get(i);
        return val;
    }

}
