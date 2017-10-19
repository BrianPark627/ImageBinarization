import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedImage image = ImageIO.read(new File("snow.jpg"));
        int width = image.getWidth();
        int height = image.getHeight();

        int[][] greyScaleValue = new int[width][height];
        int[] occurrence = new int[256];

        for(int i = 0; i < width; i ++){
            for(int j = 0; j < height; j++){
                int p = image.getRGB(i,j);
                int r = (p>>16) & 0xff;
                int g = (p>>8) & 0xff;
                int b = p & 0xff;
                greyScaleValue[i][j] = (r + b + g )/3;
                occurrence[greyScaleValue[i][j]]++;
            }
        }

        Threshold threshold = new Threshold(occurrence, 0);
        threshold.getBetweenClassVariance(0);
        int T = threshold.getThreshold();
        System.out.println(T);
        for(int i = 0; i < width; i ++){
            for(int j = 0; j < height; j++){
                int p = image.getRGB(i,j);
                int r = (p>>16) & 0xff;
                int g = (p>>8) & 0xff;
                int b = p & 0xff;
                int greyScale = (r + b + g )/3;
                if( greyScale < T){
                    p = (255<<24) | (0<<16) | (0<<8) | 0;
                    image.setRGB(i, j, p);
                }
                else if (greyScale > T){
                    p = (255<<24) | (255<<16) | (255<<8) | 255;
                    image.setRGB(i, j, p);
                }
            }
        }

        File f= new File("C:\\testimages\\output.jpg");
        ImageIO.write(image, "jpg", f);

    }
}