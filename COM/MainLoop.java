package COM;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MainLoop {

    public static void main(String[] args) throws IOException {

        Set<Troop> frendlys = new HashSet<Troop>(); frendlys.add(new Tower(Math.PI/2, new Vektor(10,10))); frendlys.add(new Tower(Math.PI/2, new Vektor(90,10)));
        Set<Troop> enemys = new HashSet<Troop>(); enemys.add(new Tower(Math.PI/2, new Vektor(10,190))); enemys.add(new Tower(Math.PI/2, new Vektor(90,190)));
        //nrdi set frendly pa enemy k vsebuje towerje pa bridge
        Grid grid = new Grid(frendlys, enemys, 8008.5, 8008.5);
        while (true) {
            //magično iz cursorja dobiš lokacijo pa kir troop je selectan k ga deploya
            Vektor location = new Vektor(1,2);
            Troop monke = new TesterMonke(location);
            if (grid.isOnFrendlyGround(location)) {
                //če je klik playerja biu na frendly area pol ugotoviš kua je objective tega pieca glede na to kam je postaulen in pol ga addas v aktivne monkeyu na gridu
                monke.pathFind(grid);
                frendlys.add(monke);
            }


        }


    }
}
