import java.awt.*;
import java.awt.image.BufferedImage;

public class CreateGraphics {
    public Graphics getGraphics(){
        // Create a BufferedImage with desired dimensions
        int width = 160;
        int height = 144;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Get the Graphics object for the BufferedImage
        Graphics g = image.getGraphics();
        return g;
    }
}
