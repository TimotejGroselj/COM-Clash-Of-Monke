package COM;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tower extends Troop{
    //basicly troop sam de se ne premika lol
    private boolean isFrendly;
    public Tower(double orientation, final Vektor location, boolean isFrendly) throws IOException{
        super(ImageIO.read(new File("TeseterMonke.png")), 0, 5, 5, 0.5, 30,
         0, orientation, location, null, 30, isFrendly);
    }

}
