package painter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {
    public static BufferedImage loadImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path)); // PNG images will retain transparency
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
