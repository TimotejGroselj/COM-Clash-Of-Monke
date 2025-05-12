package COM;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TesterMonke extends Troop {
    private static final double SPEED=1;
    private static final int DAMAGE=2;
    private static final int RANGE=2;
    private static final double COOL=0.5;
    private static final int MAXHEATLH=10;
    private static final int COST=5;
    private static final int[][] ANIMATION = new int[2][2];
    //arraya nimata taprave velikosti sam bosta slikce
    //stvari k jih noben ne slata so kot konstante
    //stvari k se z igro spreminjajo pa so obične privat sprem
    //location je parametr konstruktorja, kr to bo edina stvar k jo določš k spawnaš troop
    //vse dobiš z get metodam
    //kar lhka spreminjaš (current health, orientation, location) spreminjaš z set metodam
    public TesterMonke(Vektor location) throws IOException {
        super(ImageIO.read(new File("TeseterMonke.png")), SPEED, DAMAGE, RANGE, COOL, MAXHEATLH, COST, Math.PI/2, location, ANIMATION, MAXHEATLH);
    }
}
