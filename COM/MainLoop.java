package COM;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MainLoop {
    public static void main(String[] args) throws IOException {
        Set<Troop> frendlys = new HashSet<Troop>(); frendlys.add(new Troop(new Vektor(10,10),true, "Tower")); frendlys.add(new Troop(new Vektor(90,10),true, "Tower"));
        Set<Troop> enemys = new HashSet<Troop>(); enemys.add(new Troop(new Vektor(10,90),false, "Tower")); enemys.add(new Troop(new Vektor(90,90),false, "Tower"));
        //nrdi set frendly pa enemy k vsebuje towerje pa bridge
        Grid grid = new Grid(frendlys, enemys, 8008.5, 8008.5);
        int i = 0;
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
                if (freTroop.getName().equals("Bridge")) {
                    continue;
                }
                freTroop.pathFind(grid);    
            }
            //zanka za pathfinderja enemyu
             for (Troop eneTroop: enemys) {
                if (eneTroop.getName().equals("Bridge")) {
                    continue;
                }
                eneTroop.pathFind(grid);    
            }
            //zanka za in range pa atack za frendlye in enemye
            for (Troop freTroop: frendlys) {
                for (Troop eneTroop: enemys) {
                    if (freTroop.getName().equals("Bridge") || eneTroop.getName().equals("Bridge")) {
                        continue;
                    }
                    if (freTroop.isInRange(eneTroop)) {
                        if (i-freTroop.getLastAttack() <= freTroop.getCool()) {
                            freTroop.attack(eneTroop);
                            freTroop.setLastAttack(i);
                        }
                    }
                    else {
                        freTroop.move();
                    }
                }
            }
            for (Troop eneTroop: enemys) {
                for (Troop freTroop: frendlys) {
                    if (freTroop.getName().equals("Bridge") || eneTroop.getName().equals("Bridge")) {
                        continue;
                    }
                    if (i-eneTroop.getLastAttack() <= eneTroop.getCool()) {
                            eneTroop.attack(freTroop);
                            eneTroop.setLastAttack(i);
                    }
                    else {
                        eneTroop.move();
                    }
                    }
                }
                i++;
            }
        }
    }


