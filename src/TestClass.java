public class TestClass {
    public static void main(String[] args) {
        GraphicPic pic = new GraphicPic(null);
        Figure f1 = new Figure(370, 320, 80, 0, Figure.Type.CIRCLE, Figure.Color.WHITE);
        Figure f2 = new Figure(115, 230, 295, 360, Figure.Type.RECTANGLE, Figure.Color.RED);


        long start, end;
        start = System.nanoTime();
        pic.insert(f1);
        end = System.nanoTime();
        System.out.println(end - start);
        pic.insert(f2);

        start = System.nanoTime();
        pic.show();
        end = System.nanoTime();
        System.out.println(end - start);

        start = System.nanoTime();
        pic.delete(Figure.Type.SEGMENT);
        end = System.nanoTime();
        System.out.println(end - start);

        start = System.nanoTime();
        pic.commonWith(f1);
        end = System.nanoTime();
        System.out.println(end - start);

        start = System.nanoTime();
        GraphicPic newPic = pic.hasSquareBiggerThanS(1000);
        end = System.nanoTime();
        System.out.println(end - start);

        Figure.Color[] colors = {Figure.Color.GREEN, Figure.Color.RED};
        start = System.nanoTime();
        GraphicPic anotherPic = pic.getByColor(colors);
        end = System.nanoTime();
        System.out.println(end - start);

        start = System.nanoTime();
        pic.save("test.txt");
        end = System.nanoTime();
        System.out.println(end - start);
    }
}
