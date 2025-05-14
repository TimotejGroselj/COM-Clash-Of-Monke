package COM;

import java.awt.*;
import java.util.*;
import java.util.List;

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
    private double a; //to se dodelis fiksno
    private double b; //to se dodelis fiksno
    private double ry; //to se dodelis fiksno
    private double rx; //to se dodelis fiksno
    private Troop friend;


    public ChooseTheLocation(Troop enemy,Troop friend) {
        this.enemy = enemy;
        this.friend = friend;
    }

    public boolean IsInside(double x,double y){
        return ((x >= rx || x <= rx+a)&&(y >= ry || y <= ry+b));
    }

    public String PickSide(){
        Vektor r1 = new Vektor(rx + a,ry + b/2.0);
        Vektor r2 = new Vektor(rx + a,ry + b/2.0);
        Vektor wherefriend = this.friend.getLocation();
        if (Vektor.dist(r1,wherefriend) >= Vektor.dist(r2,wherefriend)){
            return "Left";
        }
        else return "Right";
        }

    public Vektor GetSpawn(String strategy){
        Random r = new Random();
        if (Objects.equals(strategy, "Attack")){
            double x = r.nextInt((int) rx,(int) (rx+a));
            double y = r.nextInt((int) ry,(int) (ry+b));
            return new Vektor(x,y);
        }
        double range = this.enemy.getRange();
        double y = range;
        double x = 0.0;
        List<Vektor> ranxy = new ArrayList<>();
        while (ranxy.size() < 4){
            if (IsInside(x,y)){
                ranxy.add(new Vektor(x,y));
            }
            y -= 0.33; //neki parameter
            x = Math.sqrt(Math.pow(range,2)-Math.pow(y,2));
        }
        int id = r.nextInt(4);
        return ranxy.get(id);
    }

}

/*Plan za jutr:
Checki za left and right stvari
Zbris metode za zbrat troopa
Metodo dodefence preured za hashmap interactionov za dani troop
U classu spawn nared zadevo da loh spawna uzad (torej ce vid tanka na drugi strani lahko sumi,da gre za push in more defendat, defenda doklr tank ne umre)
Nadaljevanje od zgornga: ce ni tanka ampak je nek semi-tank lahko izbere a bo defendu al attacku based on threatning level pa elixir, ce je pa attack pa napad na drugi strani?
Bottomline lots of work awaits you maggot
*/










