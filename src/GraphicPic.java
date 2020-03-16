import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GraphicPic {
    private LinkedList<Figure> list;

    public GraphicPic(String path){
        list = new LinkedList<>();
        if(path != null){
            Scanner sc;
            try {
                sc = new Scanner(new File(path));
            }catch (IOException e){
                e.printStackTrace();
                return;
            }
            int x1, y1, x2, y2;
            String type, color;
            Figure.Type t;
            Figure.Color c;
            while(sc.hasNext()){
                x1 = Integer.parseInt(sc.next());
                y1 = Integer.parseInt(sc.next());
                x2 = Integer.parseInt(sc.next());
                y2 = Integer.parseInt(sc.next());
                type = sc.next();
                color = sc.next();
                list.addFirst(new Figure(x1, y1, x2, y2, Figure.Type.valueOf(type), Figure.Color.valueOf(color)));
            }
            sc.close();
        }
    }

    public void insert(Figure f){
        list.addFirst(f);
    }

    public void show(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        FigurePanel panel = new FigurePanel(list);
        frame.add(panel);
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setVisible(true);
    }

    public void delete(Figure.Type t){
        for(Figure f : list){
            if(f.getType() == t){
                list.remove(f);
            }
        }
    }

    public GraphicPic commonWith(Figure r){
        GraphicPic pic = new GraphicPic(null);
        for(Figure f : list){
           if(f.intersectsWith(r)){
               pic.insert(f);
           }
        }
        return pic;
    }

    public GraphicPic hasSquareBiggerThanS(double s){
        GraphicPic pic = new GraphicPic(null);
        for(Figure f : list){
            if(f.getSquare() > s){
                pic.insert(f);
            }
        }
        return pic;
    }

    public void save(String path){
        PrintWriter wr;
        try{
            wr = new PrintWriter(new File(path));
        }
        catch (IOException e){
            e.printStackTrace();
            return;
        }
        for(Figure f : list) {
            wr.println(f);
        }
        wr.close();
    }

    public GraphicPic getByColor(Figure.Color[] colors){
        GraphicPic pic = new GraphicPic(null);
        for(Figure f : list){
            for(Figure.Color c : colors){
                if(f.getColor() == c){
                    pic.insert(f);
                    break;
                }
            }
        }
        return pic;
    }


    public static void main(String[] args) {
        GraphicPic pic = new GraphicPic(null);
        Figure f1 = new Figure(0, 0, 20, 20, Figure.Type.SEGMENT, Figure.Color.BLACK);
        Figure f2 = new Figure(11, 31, 31, 11, Figure.Type.SEGMENT, Figure.Color.RED);
        pic.insert(f1);
        pic.insert(f2);
        System.out.println(f1.intersectsWith(f2));
        pic.show();
        pic.save("test.txt");
    }
}
