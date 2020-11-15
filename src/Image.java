import java.awt.image.BufferedImage;

public class Image {
    public static BufferedImage getImage() {
        return new BufferedImage(500, 500, BufferedImage.TYPE_BYTE_GRAY);
    }
}
