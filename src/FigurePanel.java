import javax.swing.*;
import java.awt.*;

public class FigurePanel extends JPanel {

    private LinkedList<Figure> list;

    public FigurePanel(LayoutManager layout, boolean isDoubleBuffered, LinkedList<Figure> list) {
        super(layout, isDoubleBuffered);
        this.list = list;
    }

    public FigurePanel(LayoutManager layout, LinkedList<Figure> list) {
        super(layout);
        this.list = list;
    }

    public FigurePanel(boolean isDoubleBuffered, LinkedList<Figure> list) {
        super(isDoubleBuffered);
        this.list = list;
    }

    public FigurePanel(LinkedList<Figure> list) {
        this.list = list;
    }

    public void paint(Graphics g){
        super.paint(g);
        for(Figure f : list){
            g.setColor(f.getColor().getColor());
            switch (f.getType()){
                case CIRCLE:
                    g.fillOval(f.getX1() - f.getX2(), f.getY1() - f.getX2(),
                            2 * f.getX2(), 2 * f.getX2());
                    break;

                case SEGMENT:
                    g.drawLine(f.getX1(), f.getY1(), f.getX2(), f.getY2());
                    break;

                case RECTANGLE:
                    g.fill3DRect(f.getX1(), f.getY1(), f.getX2() - f.getX1(), f.getY2() - f.getY1(), false);
                    break;
            }
        }
    }
}
