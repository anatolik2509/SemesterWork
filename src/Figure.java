import java.awt.*;

public class Figure {
    private int x1, y1, x2, y2;

    public enum Type{
        RECTANGLE, CIRCLE, SEGMENT
    }

    public enum Color{
        WHITE(java.awt.Color.WHITE),
        BLACK(java.awt.Color.BLACK),
        RED(java.awt.Color.RED),
        GREEN(java.awt.Color.GREEN),
        BLUE(java.awt.Color.BLUE);

        private java.awt.Color color;

        Color(java.awt.Color c){
            this.color = c;
        }

        public java.awt.Color getColor() {
            return color;
        }
    }

    private Type type;
    private Color color;

    public Figure(int x1, int y1, int x2, int y2, Type type, Color color) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.type = type;
        this.color = color;
    }

    public Figure(int x1, int y1, int x2, Type type, Color color){
        this(x1, y1, x2, 0, type, color);
    }

    public double getSquare(){
        switch (type){
            case RECTANGLE:
                return Math.abs(x1 - x2) * Math.abs(y1 - y2);

            case CIRCLE:
                return x2 * x2 * Math.PI;

            case SEGMENT:
                return 0;
        }
        return 0;
    }

    public boolean intersectsWith(Figure another){

        switch (type){
            case RECTANGLE:
                switch (another.type){
                    case RECTANGLE:
                        return pointInArea(another.x1, another.y1, x1, y1, x2, y2) ||
                                pointInArea(another.x2, another.y1, x1, y1, x2, y2) ||
                                pointInArea(another.x1, another.y2, x1, y1, x2, y2) ||
                                pointInArea(another.x2, another.y2, x1, y1, x2, y2) ||
                                pointInArea(x1, y1, another.x1, another.y1, another.x2, another.y2) ||
                                pointInArea(x2, y1, another.x1, another.y1, another.x2, another.y2) ||
                                pointInArea(x1, y2, another.x1, another.y1, another.x2, another.y2) ||
                                pointInArea(x2, y2, another.x1, another.y1, another.x2, another.y2);
                    case SEGMENT:
                        return pointInArea(another.x1, another.y1, x1, y1, x2, y2) ||
                                pointInArea(another.x2, another.y2, x1, y1, x2, y2) ||
                                another.intersectsWith(new Figure(x1, y1, x2, y1, Type.SEGMENT, null)) ||
                                another.intersectsWith(new Figure(x2, y1, x2, y2, Type.SEGMENT, null)) ||
                                another.intersectsWith(new Figure(x1, y1, x1, y2, Type.SEGMENT, null)) ||
                                another.intersectsWith(new Figure(x1, y2, x2, y2, Type.SEGMENT, null));
                    case CIRCLE:
                        return another.intersectsWith(this);
                }

            case CIRCLE:
                switch (another.type){
                    case CIRCLE:
                        return dist(x1, y1, another.x1, another.y1) <= x2 + another.x2;

                    case SEGMENT:
                        return circleAndSegment(x1, y1, x2, another.x1, another.y1, another.x2, another.y2);

                    case RECTANGLE:
                        return circleAndSegment(x1, y1, x2, another.x1, another.y1, another.x1, another.y2) ||
                                circleAndSegment(x1, y1, x2, another.x1, another.y1, another.x2, another.y1) ||
                                circleAndSegment(x1, y1, x2, another.x2, another.y1, another.x2, another.y2) ||
                                circleAndSegment(x1, y1, x2, another.x1, another.y2, another.x2, another.y2) ||
                                pointInArea(x1, y1, another.x1, another.y1, another.x2, another.y2);
                }

            case SEGMENT:
                switch (another.type){
                    case RECTANGLE:

                    case CIRCLE:
                        return another.intersectsWith(this);

                    case SEGMENT:
                        double a1 = y2 - y1;
                        double b1 = x1 - x2;
                        double c1 = (y1 - y2) * x1 + (x2 - x1) * y1;
                        double a2 = another.y2 - another.y1;
                        double b2 = another.x1 - another.x2;
                        double c2 = (another.y1 - another.y2) * another.x1 + (another.x2 - another.x1) * another.y1;
                        double det = a1 * b2 - a2 * b1;
                        double det1 = -c1 * b2 - b1 * -c2;
                        double det2 = a1 * -c2 - a2 * -c1;
                        double iy = det2 / det;
                        double ix = det1 / det;
                        return pointInArea((int)ix, (int)iy, x1, y1, x2, y2) &&
                                pointInArea((int)ix, (int)iy, another.x1, another.y1, another.x2, another.y2);
                }
        }
        return false;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public Type getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return x1 + " " + y1 + " " + x2 + " " + y2 + " " + type + " " + color;
    }

    public static boolean circleAndSegment(int cx, int cy, int r, int x1, int y1, int x2, int y2){
        double a = y2 - y1;
        double b = x1 - x2;
        double c = (y1 - y2) * x1 + (x2 - x1) * y1;
        double d = Math.abs((a * cx + b * cy + c)) / dist(a, b,0,0);
        if(d < r){
            double a1 = -b;
            double b1 = a;
            double c1 = -a1 * cx - b1 * cy;
            double det = a * b1 - a1 * b;
            double det1 = -c * b1 - b * -c1;
            double det2 = a * -c1 - a1 * -c;
            double iy = det2 / det;
            double ix = det1 / det;
            double sinR = Math.sqrt(r*r - d*d);
            return pointInArea((int)ix, (int)iy, x1, y1, x2, y2) || dist(ix, iy, x1, y1) < sinR  || dist(ix, iy, x2, y2) < sinR;
        }
        return false;
    }

    public static boolean pointInArea(int px, int py, int x1, int y1, int x2, int y2){
        return (px >= Math.min(x1, x2) && px <= Math.max(x1, x2)) && (py >= Math.min(y1, y2) && py <= Math.max(y1, y2));
    }

    public static double dist(int x1, int y1, int x2, int y2){
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public static double dist(double x1, double y1, double x2, double y2){
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}
