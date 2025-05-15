package COM;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MonkeThinker {

    public MonkeThinker(Set<Troop> friendly,Set<Troop> enemy,int elixir) {
        for (Troop friend:friendly){
            double x = friend.getLocation().getX();
            double y = friend.getLocation().getY();
        }
    }
}

class ChooseTheGuy{

    private Set<Troop> enemies;
    private Troop friend;
    private HashMap<Troop,Integer> best;
    private int current_elixir;

    public ChooseTheGuy(Troop friend, Set<Troop> enemies,int current_elixir) {
        this.current_elixir = current_elixir;
        this.enemies = enemies;
        this.friend = friend;
        this.best = new HashMap<>();
        for (Troop dude: enemies){
            this.best.put(dude,0);
        }
    }

    public Troop DoBackupDefence(){
        return null;
    }

    public Troop DoDefence(){
        return null;
    }

    public Troop DoOffense(){
        return null;
    }
}

class ChooseTheLocation {
    private Troop enemy;
    private double a; //arena width
    private double b; //arena height/2.0
    private double ry; //left y cord
    private double rx; //left x cord (
    private Troop friend;
    private double range;
    private double x0;
    private double y0;


    public ChooseTheLocation(Troop enemy, Troop friend) {
        this.range = enemy.getRange();
        this.x0 = friend.getLocation().getX();
        this.y0 = friend.getLocation().getY();
    }

    public boolean IsInside(double x,double y){
        return ((x >= rx || x <= rx+a)&&(y >= ry || y <= ry+b));
    }

    public Vektor GetSpawn(String strategy) {
        Random r = new Random();
        double x,y = 0.0;
        if (Objects.equals(strategy, "Attack")) {
            x = r.nextInt((int) rx, (int) (rx + a));
            y = r.nextInt((int) ry, (int) (ry + b));
            return new Vektor(x, y);
        } else if (Objects.equals(strategy, "Defence")) {
            double parameter = 6.0;
            double fi = Math.PI/parameter;
            List<Vektor> cords = new ArrayList<>();
            for (int i = 0;i<(int)(parameter*2 + 1.0);i++){
                x = this.range*Math.sin(fi) + this.x0;
                y = this.range*Math.cos(fi) + this.y0;
                fi += Math.PI/parameter;
                if (IsInside(x,y)) {
                    cords.add(new Vektor(x, y));
                }
            }
            int id = (int)(Math.random()* cords.size());
            return cords.get(id);
        }
        return null;
    }

}

/*Plan za jutr:
Dodj se emergency defence
Pokomenteri rx,ry
Pa une metode k so null
Metodo dodefence preured za hashmap interactionov za dani troop
U classu spawn nared zadevo da loh spawna uzad (torej ce vid tanka na drugi strani lahko sumi,da gre za push in more defendat, defenda doklr tank ne umre)
Nadaljevanje od zgornga: ce ni tanka ampak je nek semi-tank lahko izbere a bo defendu al attacku based on threatning level pa elixir, ce je pa attack pa napad na drugi strani?
Bottomline lots of work awaits you maggot
*/










