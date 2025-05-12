import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MainLoop {
	
public static void main(String[] args) throws IOException {
	Grid grid = new Grid();
	Set<Troop> frendly = new HashSet<Troop>();
	//Set<Troop> enemy = new HashSet<Troop>();
	while (true) {
		//magično iz cursorja dobiš lokacijo pa kir troop je selectan k ga deploya
		Vektor location = new Vektor(1,2);
		Troop monke = new TesterMonke(location);
		if (grid.isOnFrendlyGround(location)) {
			//če je klik playerja biu na frendly area pol ugotoviš kua je objective tega pieca glede na to kam je postaulen in pol ga addas v aktivne monkeyu na gridu
			monke.pathFind(grid);
			frendly.add(monke);
		}
		
		
	}
	
	
}
}
