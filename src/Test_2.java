import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class Test_2 {
    public static final int ITERATIONS = 2000;
    public static void main(String[] args) {
        GraphicPic pic;
        Random r = new Random();
        long deltaTime = 0;
        long startTime;
        long finishTime;
        long times[] = new long[ITERATIONS];
        double[] averages = new double[100];
        double[] sigmas = new double[100];
        for(int n = 10; n <= 1000; n += 10) {
            for (int j = 0; j < ITERATIONS; j++) {
                pic = new GraphicPic(null);
                Figure f[] = new Figure[n];
                for(int i = 0; i < n; i++) {
                    f[i] = new Figure(r.nextInt(500),
                            r.nextInt(500),
                            r.nextInt(500),
                            r.nextInt(500),
                            Figure.Type.values()[r.nextInt(Figure.Type.values().length)],
                            Figure.Color.BLACK);
                    pic.insert(f[i]);
                }
                Figure figure = new Figure(r.nextInt(500),
                        r.nextInt(500),
                        r.nextInt(500),
                        r.nextInt(500),
                        Figure.Type.values()[r.nextInt(Figure.Type.values().length)],
                        Figure.Color.BLACK);
                startTime = System.nanoTime();
                pic.commonWith(figure);
                finishTime = System.nanoTime();
                deltaTime = finishTime - startTime;
                times[j] = deltaTime;
            }
            Arrays.sort(times);
            int b = (int)(times.length * 0.05);
            long[] newTimes = Arrays.copyOfRange(times, b, times.length - b);
            averages[n/10 - 1] = average(newTimes);
            sigmas[n/10 - 1] = sigma(newTimes, averages[n/10 - 1]);
        }
        try(PrintWriter wr = new PrintWriter("result.csv")){
            for(int i = 0; i < averages.length; i++){
                wr.print(((i + 1) * 10) + ";");
            }
            wr.println();
            for(int i = 0; i < averages.length; i++){
                wr.print((int)averages[i] + ";");
            }
            wr.println();
            for(int i = 0; i < sigmas.length; i++){
                wr.print((int)sigmas[i] + ";");
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    private static double average(long[] longs){
        double sum = 0;
        for(long l : longs){
            sum += l;
        }
        sum /= longs.length;
        return sum;
    }

    private static double sigma(long[] longs, double average){
        double sum = 0;
        for(long l : longs){
            sum += (l - average) * (l - average);
        }
        sum /= longs.length;
        sum = Math.sqrt(sum);
        return sum;
    }

}
