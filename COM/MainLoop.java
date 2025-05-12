package COM;

import java.io.IOException;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class MainLoop {

    public static void main(String[] args) throws IOException {
        Set<Troop> frendlys = new HashSet<Troop>(); frendlys.add(new Tower(Math.PI/2, new Vektor(10,10),true)); frendlys.add(new Tower(Math.PI/2, new Vektor(90,10), true));
        Set<Troop> enemys = new HashSet<Troop>(); enemys.add(new Tower(Math.PI/2, new Vektor(10,190),false)); enemys.add(new Tower(Math.PI/2, new Vektor(90,190),false));
        //nrdi set frendly pa enemy k vsebuje towerje pa bridge
        Grid grid = new Grid(frendlys, enemys, 8008.5, 8008.5);
        while (true) {
            ////magično iz cursorja dobiš lokacijo pa kir troop je selectan k ga deploya
            //Vektor location = new Vektor(1,2);
            //Troop monke = new TesterMonke(location);
            //if (grid.isOnFrendlyGround(location)) {
            //    //če je klik playerja biu na frendly area pol ugotoviš kua je objective tega pieca glede na to kam je postaulen in pol ga addas v aktivne monkeyu na gridu
            //    monke.pathFind(grid);
            //    frendlys.add(monke, true);

            //vse zgori nuca jt v mouse listenerja if you know what i mean


            //zanka de pathfinder frendlyu
            for (Troop freTroop: frendlys) {
                if (freTroop instanceof Bridge) {
                    continue;
                }
                freTroop.pathFind(grid);    
            }
            //zanka za pathfinderja enemyu
             for (Troop eneTroop: enemys) {
                if (eneTroop instanceof Bridge) {
                    continue;
                }
                eneTroop.pathFind(grid);    
            }


            //zanka za in range pa atack za frendlye in enemye
            boolean IsAttacking;
            for (Troop freTroop: frendlys) {
                IsAttacking = false;
                double timeStart = 0;
                for (Troop eneTroop: enemys) {
                    if (freTroop instanceof Bridge || eneTroop instanceof Bridge) {
                        continue;
                    }
                    if (freTroop.isInRange(eneTroop)) {
                        if (Instant.now().toEpochMilli()-timeStart>=freTroop.getCool()) {
                          	freTroop.attack(eneTroop);
                            timeStart = Instant.now().toEpochMilli();
                        }
                        IsAttacking = true;
                        break;
                    }
                    if (!IsAttacking) {
                        //freTroop.move(timestep somehow);
                    }
                }
            }
            for (Troop eneTroop: enemys) {
                for (Troop freTroop: frendlys) {
                    if (freTroop instanceof Bridge || eneTroop instanceof Bridge) {
                        continue;
                    }
                   if (eneTroop.isInRange(freTroop)) {
                        //start timer 
                        //if current time-timer>=eneTroop.getcool(){
                        //  	eneTroop.attack(freTroop);
                        //}
                    }
                }
            }
        }
    }
}

