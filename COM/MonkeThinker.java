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
                this.Dude = BestMonke.DoDefence(friend);
                if (this.Dude == null) continue;
                this.Spawn = new ChooseTheLocation(this.Dude, friend).GetSpawn("Defence");
            } else {
                this.Dude = BestMonke.DoOffence();
                if (this.Dude == null) continue;
                this.Spawn = new ChooseTheLocation(this.Dude, friend).GetSpawn("Offence");

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
    private final Map<Troop, HashMap<Troop, Boolean>> interactions;


    public ChooseTheGuy(Troop friend,int current_elixir, Map<Troop, HashMap<Troop,Boolean>> interactions) {
        this.current_elixir = current_elixir;
        this.interactions = interactions;
    }


    public Troop DoDefence(Troop tr1){
        Random r = new Random();
        HashMap<Troop,Boolean> tobeat = interactions.get(tr1);
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
            if (thing.getCost() <= this.current_elixir){best.add(thing);}
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
        } else {
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
    }
}












