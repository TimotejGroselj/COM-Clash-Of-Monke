package COM;


import java.util.*;
import java.util.List;

public class MonkeThinker {

    public Vektor Spawn;
    public Troop Dude;
    public int elixir;

    public MonkeThinker(Set<Troop> friendly,Set<Troop> enemy,int elixir) {
        this.elixir = elixir;
        for (Troop friend:friendly){
            ChooseTheGuy BestMonke = new ChooseTheGuy(friend,enemy,this.elixir);
            if (!friend.isOnFrendlyGround(MainLoop.HEIGHT)){
                this.Dude = BestMonke.DoDefence(friend);
                if (this.Dude == null) continue;
                this.Spawn = new ChooseTheLocation(this.Dude,friend).GetSpawn("Defence");
            }
            else {
                if (friend.getCurrenthealth() > 70) { //tuki se to stotko nastimi, to je za tanke
                    this.Dude = BestMonke.DoBackupDefence(friend);
                    if (this.Dude == null) continue;
                    this.Spawn = new ChooseTheLocation(this.Dude,friend).GetSpawn("BackupDefence");
                }
                else {
                    this.Dude = BestMonke.DoOffence();
                    if (this.Dude == null) continue;
                    this.Spawn = new ChooseTheLocation(this.Dude,friend).GetSpawn("Offence");
                }
            }
        }
    }

    public Troop getDude() {
        return Dude;
    }

    public Vektor getSpawn() {
        return Spawn;
    }
}

class ChooseTheGuy{

    private final Set<Troop> enemies;
    private final int current_elixir;
    private final Map<Troop, HashMap<Troop,Boolean>> interactions = new Interactions(null).getInteractions();


    public ChooseTheGuy(Troop friend, Set<Troop> enemies,int current_elixir) {
        this.current_elixir = current_elixir;
        this.enemies = enemies;
    }

    public Troop DoBackupDefence(Troop tr1){
        Random r = new Random();
        HashMap<Troop,Boolean> tobeat = interactions.get(tr1);
        List<Troop> best = new ArrayList<>();
        for (Troop i: tobeat.keySet()){
            if (tobeat.get(i) && (i.getCost()<this.current_elixir)){best.add(i);}
        }
        if (best.isEmpty()) return null;
        if (best.size() == 1) return best.getFirst();

        int num = r.nextInt(best.size()-1);
        for (Troop i1: best){
            if (i1.getDamage() > 80) {return i1;} //ta 80 se se stima
        }
        return best.get(num);
    }

    public Troop DoDefence(Troop tr1){
        Random r = new Random();
        HashMap<Troop,Boolean> tobeat = interactions.get(tr1);
        List<Troop> best = new ArrayList<>();
        for (Troop i: tobeat.keySet()){
            if (tobeat.get(i) && (i.getCost()<this.current_elixir)){best.add(i);}
        }
        if (best.isEmpty()) return null;
        if (best.size() == 1) return best.getFirst();
        int num = r.nextInt(best.size()-1);
        return best.get(num);
    }

    public Troop DoOffence(){
        Random r = new Random();
        List<Troop> best = new ArrayList<>();
        for (Troop thing:enemies){
            if (thing.getCost() <= this.current_elixir){best.add(thing);}
        }
        if (best.isEmpty()) return null;
        if (best.size() == 1) return best.getFirst();
        int num = r.nextInt(best.size() - 1);
        return best.get(num);
    }
}

class ChooseTheLocation {
    private final double a = MainLoop.WIDTH;
    private final double ry = MainLoop.HEIGHT/2.0; //left y cord
    private final double range;
    private final double x0;
    private final double y0;


    public ChooseTheLocation(Troop enemy, Troop friend) {
        this.range = enemy.getRange();
        this.x0 = friend.getLocation().getX();
        this.y0 = friend.getLocation().getY();
    }

    public boolean IsInside(double x,double y){
        return ((x >= 0 && x <= a)&&(y >= ry/2 && y <= ry));
    }

    public Vektor GetSpawn(String strategy) {
        Random r = new Random();
        double x,y = 0.0;
        if (strategy.equals("Offence")) {
            if (this.x0 < a/2) {
                x = r.nextInt(0, (int) (a /2));
                y = r.nextInt((int) (ry/2), (int) (ry));
            }
            else {
                x = r.nextInt((int) (a / 2), (int) a);
                y = r.nextInt((int) (2*ry/3), (int) (ry));
            }
            return new Vektor(x, y);
        } else if (strategy.equals("Defence")) {
            double parameter = 6;
            double fi = Math.PI/parameter;
            List<Vektor> cords = new ArrayList<>();
            for (int i = 0;i<(parameter*2 + 1);i++){
                x = this.range*Math.sin(fi) + this.x0;
                y = this.range*Math.cos(fi) + this.y0;
                fi += Math.PI/parameter;
                if (IsInside(x,y)) {
                    cords.add(new Vektor(x, y));
                }
            }
            if (cords.isEmpty()) return null;
            if (cords.size() == 1) return cords.getFirst();
            int id = r.nextInt(cords.size()-1);
            return cords.get(id);
        }
        else {
            if (this.x0 < a/2) {
                x = r.nextInt((int) (a / 3), (int) (a /2));
                y = r.nextInt((int) (2*ry/3), (int) (ry));
            }
            else {
                x = r.nextInt((int) (a / 2), (int) (2*a /3));
                y = r.nextInt((int) (2*ry/3), (int) (ry));
            }
            return new Vektor(x, y);
        }
    }
}

/*Plan za jutr:


USE GLEDAS SE NA ELIXIR K JE NA VOLJO!!
BackupDefence loh nardis da coresponda z lokacijo enemy tanka, pa da defenda doklr tank ne umre
Kaj pa ni nobenega friendlyja na mapi kaj pa potem?
Bottomline lots of work awaits you maggot
*/










