package COM;


import java.util.*;
import java.util.List;


public class MonkeThinker {

    public Vektor Spawn;
    public Troop Dude;
    public int elixir;

    public MonkeThinker(Set<Troop> friendly, int elixir, Map<Troop, HashMap<Troop, Boolean>> fightclub) {
        this.Dude = null;
        this.elixir = elixir;
        for (Troop friend : friendly) {
            if (Objects.equals(friend.getName(), "Tower")){continue;}
            ChooseTheGuy BestMonke = new ChooseTheGuy(friend, this.elixir, fightclub);
            if (!friend.isOnFrendlyGround(MainLoop.HEIGHT)) {
                this.Dude = BestMonke.DoDefence();
                if (this.Dude == null) continue;
                this.Spawn = new ChooseTheLocation(this.Dude, friend).GetSpawn("Defence");
            } else {
                if (friend.getCurrenthealth() >= 70) { //tuki se to stotko nastimi, to je za tanke
                    this.Dude = BestMonke.DoBackupDefence();
                    if (this.Dude == null) continue;
                    this.Spawn = new ChooseTheLocation(this.Dude, friend).GetSpawn("BackupDefence");
                } else {
                    this.Dude = BestMonke.DoOffence();
                    if (this.Dude == null) continue;
                    this.Spawn = new ChooseTheLocation(this.Dude, friend).GetSpawn("Offence");
                }
            }
        }
    }

    public Troop getDude() {
        if (Dude == null) {return null;}
        return new Troop(Spawn, false, Dude.getName());
    }

}
class ChooseTheGuy{

    private final int current_elixir;
    private final Troop replica;
    public Map<Troop, HashMap<Troop, Boolean>> interactions;


    public ChooseTheGuy(Troop friend,int current_elixir, Map<Troop, HashMap<Troop,Boolean>> interactions) {
        this.current_elixir = current_elixir;
        this.interactions = interactions;
        Troop replica = null;
        for (Troop i: interactions.keySet()){
            if (Objects.equals(i.getName(), friend.getName())){replica=i;break;}
        }
        this.replica = replica;
    }

    public Troop DoBackupDefence(){
        Random r = new Random();
        HashMap<Troop,Boolean> tobeat = interactions.get(replica);
        List<Troop> best = new ArrayList<>();
        for (Troop i: tobeat.keySet()){
            if (tobeat.get(i) && (i.getCost()<=this.current_elixir)){best.add(i);}
        }
        if (best.isEmpty()) return null;
        if (best.size() == 1) return best.get(0);

        int num = r.nextInt(best.size()-1);
        for (Troop i1: best){
            if (i1.getDamage() > 80) {return i1;} //ta 80 se se stima
        }
        return best.get(num);
    }

    public Troop DoDefence(){
        Random r = new Random();
        HashMap<Troop,Boolean> tobeat = interactions.get(replica);
        List<Troop> best = new ArrayList<>();
        for (Troop i: tobeat.keySet()){
            if (tobeat.get(i) && (i.getCost()<=this.current_elixir)){best.add(i);}
        }
        if (best.isEmpty()) return null;
        if (best.size() == 1) return best.get(0);
        int num = r.nextInt(best.size()-1);
        return best.get(num);
    }

    public Troop DoOffence(){
        Random r = new Random();
        List<Troop> best = new ArrayList<>();
        for (Troop thing:interactions.keySet()){
            if (thing.getCost() <= this.current_elixir){best.add(thing);System.out.println(thing.getName());}
        }
        if (best.isEmpty()) return null;
        if (best.size() == 1) return best.get(0);
        int num = r.nextInt(best.size() - 1);
        return best.get(num);
    }
}

class ChooseTheLocation {
    private final double a = MainLoop.HEIGHT;
    private final double b = MainLoop.HEIGHT; //left y cord
    private final double range;
    private final double x0;
    private final double y0;


    public ChooseTheLocation(Troop enemy, Troop friend) {
        this.range = enemy.getRange();
        this.x0 = friend.getLocation().getX();
        this.y0 = friend.getLocation().getY();
    }

    public boolean IsInside(double x,double y){
        return ((x >= 0 && x <= a)&&(y >= 0 && y <= b/2)); //0-> height*1/4
    }

    public Vektor GetSpawn(String strategy) {
        Random r = new Random();
        double x,y = 0.0;
        if (strategy.equals("Offence")) {
            if (this.x0 < a/2) {
                x = r.nextInt((int)(a /2), (int) (a));
                y = r.nextInt( 0, (int) (b/2));
            }
            else {
                x = r.nextInt(0, (int) (a / 2));
                y = r.nextInt(0, (int) (b/2));
            }
            return new Vektor(x, y);
        }  else if (strategy.equals("Defence")) {
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
            if (cords.size() == 1) return cords.get(0);
            int id = r.nextInt(cords.size()-1);
            return cords.get(id);
        }
        else {
            if (this.x0 < a/2) {
                x = r.nextInt((int) (a / 3), (int) (a /2));
                y = r.nextInt(0, (int) (b/2));
            }
            else {
                x = r.nextInt((int) (a / 2), (int) (2*a /3));
                y = r.nextInt(0, (int) (b/2));
            }
            return new Vektor(x, y);
        }
    }
}












