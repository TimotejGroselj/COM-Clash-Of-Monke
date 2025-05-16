package COM;


import java.util.*;
import java.util.List;

public class MonkeThinker {

    public Vektor Spawn;
    public Troop Dude;
    public int elixir;

    public MonkeThinker(Set<Troop> friendly,Set<Troop> enemy,int elixir) {
        this.elixir = elixir;
        int onfield = 0;
        for (Troop friend:friendly){
            ChooseTheGuy BestMonke = new ChooseTheGuy(friend,enemy,this.elixir);
            if (!friend.isOnFrendlyGround(MainLoop.HEIGHT)){
                this.Dude = BestMonke.DoDefence();
                this.Spawn = new ChooseTheLocation(this.Dude,friend).GetSpawn("Defence");
            }
            else {
                if (friend.getCurrenthealth() > 100) { //tuki se to stotko nastimi, to je za tanke
                    this.Dude = BestMonke.DoBackupDefence();
                    this.Spawn = new ChooseTheLocation(this.Dude,friend).GetSpawn("BackupDefence");
                }
                else {
                    this.Dude = BestMonke.DoOffence();
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

    public Troop DoOffence(){
        return null;
    }
}

class ChooseTheLocation {
    private Troop enemy;
    private double a = MainLoop.WIDTH;
    private double ry = MainLoop.HEIGHT/2.0; //left y cord
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
            int id = (int)(Math.random()* cords.size());
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
Zbildi class za interactione med troopi (kdo koga zmaga glede na range: enemy range in pa friendly range)
S tem zbuildi metode: BackupDefence bos iskou heavy hitterja al pa tanka,DoOffence zberes kergakol,DoDefence zberes najbolsga
USE GLEDAS SE NA ELIXIR K JE NA VOLJO!!
BackupDefence loh nardis da coresponda z lokacijo enemy tanka, pa da defenda doklr tank ne umre
Kaj pa ni nobenega friendlyja na mapi kaj pa potem?
Bottomline lots of work awaits you maggot
*/










