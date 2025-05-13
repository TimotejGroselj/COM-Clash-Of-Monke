package COM;

import java.awt.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class MonkeThinker {

    public MonkeThinker(Set<Troop> friendly,Set<Troop> enemy,Grid grid,int elixir) {
        for (Troop friend:friendly){
            ChooseTheGuy chooser = new ChooseTheGuy(friend,enemy,elixir);
            boolean ground = grid.isOnFrendlyGround(friend);
            Troop guy = ground ? chooser.DoDefence():chooser.DoOffense();
            if (guy == null) continue; //ce nima elixirja
            Vektor spawn = new ChooseTheLocation(guy,friend).GetSpawn(ground ? "Defence" : "Attack");
            guy.setLocation(spawn);
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

    public Troop DoDefence(){
        IsOneShotable();
        HasBetterRange();
        Emergency();
        int prej = 0;
        Troop guy = null;
        for (Troop dude:this.best.keySet()){
            int tren = this.best.get(dude);
            if ((tren >= prej) && (this.current_elixir > dude.getCost())){
                guy = dude;
            }
            prej = tren;
        }
        return guy;
    }

    public Troop DoOffense(){
        return null;
    }

    public void IsOneShotable(){
        for (Troop dude: this.best.keySet()){
            boolean first = this.friend.getCurrenthealth() - dude.getDamage() <= 0;
            boolean second = dude.getMaxhealth()-this.friend.getDamage() > 0;
            if (first && second) this.best.put(dude, this.best.get(dude) + 1);
        }
    }
    public void HasBetterRange(){
        for (Troop dude: this.best.keySet()){
            if (this.friend.getRange() <= dude.getRange()){
                    this.best.put(dude, this.best.get(dude) + 1);
            }
        }
    }

    public void Emergency(){
        for (Troop dude: this.best.keySet()){
            if (dude.getDamage() >= 5){ //nimam pojma kok so kj damagi
                this.best.put(dude, this.best.get(dude) + 1);
            }
        }
    }
}

class ChooseTheLocation{
    private Troop enemy;
    private int a; //to se dodelis fiksno
    private int b; //to se dodelis fiksno
    private int ry; //to se dodelis fiksno
    private int rx; //to se dodelis fiksno
    private Troop friend;


    public ChooseTheLocation(Troop enemy,Troop friend) {
        this.enemy = enemy;
        this.friend = friend;
    }

    public boolean IsInside(int x,int y){
        Rectangle rect = new Rectangle(rx,ry,a,b);
        Point spawn = new Point(x,y);
        return rect.contains(spawn);
    }

    public String PickSide(){
        Vektor r1 = new Vektor((double) rx + a,(double) ry + b/2.0);
        Vektor r2 = new Vektor((double) rx + a,(double) ry + b/2.0);
        Vektor wherefriend = this.friend.getLocation();
        if (Vektor.dist(r1,wherefriend) >= Vektor.dist(r2,wherefriend)){
            return "Left";
        }
        else return "Right";
        }

    public Vektor GetSpawn(String strategy){
        Random r = new Random();
        if (Objects.equals(strategy, "Attack")){
            int x = r.nextInt(rx,rx+a);
            int y = r.nextInt(ry,ry+b);
            return new Vektor((double) x,(double) y);
        }
        //se za defence
        return null;
    }

    }

