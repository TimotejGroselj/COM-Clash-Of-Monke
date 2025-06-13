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
            if (Objects.equals(friend.getName(), "Tower")){continue;} //stolp ne moremo postavit
            ChooseTheGuy BestMonke = new ChooseTheGuy(friend, this.elixir, fightclub);
            if (!friend.isOnFrendlyGround(MainLoop.HEIGHT)) { //obrani
                this.Dude = BestMonke.DoDefence();
                if (this.Dude == null) continue;
                this.Spawn = new ChooseTheLocation(this.Dude, friend).GetSpawn("Defence");
            } else {
                if (friend.getCurrenthealth() >= 350) { //opazi nasprotnika z veliko health
                    this.Dude = BestMonke.DoBackupDefence();
                    if (this.Dude == null) continue;
                    this.Spawn = new ChooseTheLocation(this.Dude, friend).GetSpawn("BackupDefence");
                } else { //napade
                    this.Dude = BestMonke.DoOffence();
                    if (this.Dude == null) continue;
                    this.Spawn = new ChooseTheLocation(this.Dude, friend).GetSpawn("Offence");
                }
            }
        }
    }

    public Troop getDude() {
        //vrne troopa
        if (Dude == null || Spawn == null) {return null;}
        return new Troop(Spawn, false, Dude.getName());
    }

}
class ChooseTheGuy{
    //primeren troop za dano strategijo
    private final int current_elixir;
    private final Troop replica;
    public Map<Troop, HashMap<Troop, Boolean>> interactions;


    public ChooseTheGuy(Troop friend,int current_elixir, Map<Troop, HashMap<Troop,Boolean>> interactions) {
        this.current_elixir = current_elixir;
        this.interactions = interactions;
        Troop replica = null;
        //objekt trp1 ni enak objektu v interactonih, najdem tisti objekt, ki se ujema z imenom in uporabim le tega
        for (Troop i: interactions.keySet()){
            if (Objects.equals(i.getName(), friend.getName())){replica=i;break;}
        }
        this.replica = replica;
    }

    public Troop DoBackupDefence(){
        //ker sumi za velik napad ga bo zelel cim hitreje uniciti
        Random r = new Random();
        HashMap<Troop,Boolean> tobeat = interactions.get(replica);
        List<Troop> best = new ArrayList<>();
        for (Troop i: tobeat.keySet()){
            if (tobeat.get(i) && (i.getCost()<=this.current_elixir)){best.add(i);} //glede na trenutno stevilo banan aka elixirja bo dodal ustrezne troope v ožji izbor
        }
        if (best.isEmpty()) return null;
        if (best.size() == 1) return best.get(0);

        int num = r.nextInt(best.size()-1);
        for (Troop i1: best){
            if (i1.getDamage() > 200) {return i1;} //preferira troopa, ki ima visok damage
        }
        return best.get(num);
    }

    public Troop DoDefence(){
        //vzame branilca, ki ima v interactonih true za nasprotnika (ga premaga)
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
        //izbere nakljucnega napadalca glede na elixir
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
    //ustrezna lokacija troopa, ki ga robot postavi
    private final double a = MainLoop.HEIGHT;
    private final double b = MainLoop.HEIGHT;
    private final double range;
    private final double x0;
    private final double y0;


    public ChooseTheLocation(Troop enemy, Troop friend) {
        this.range = enemy.getRange();
        this.x0 = friend.getLocation().getX();
        this.y0 = friend.getLocation().getY();
    }

    public boolean IsInside(double x,double y){
        return ((x >= 450 && x <= a)&&(y >= 0 && y <= b/2)); //0-> height*1/4
    }

    public Vektor GetSpawn(String strategy) {
        Random r = new Random();
        double x = 450.0;
        double y = 0.0;
        if (strategy.equals("Offence")) {
            //postavi na drugi strani kot nasprotnik napada
            if (this.x0 < (a/2)) {
                x = r.nextInt((int)(a /2),(int)a);
                y = r.nextInt((int) (b/2));
            }
            else {
                x = r.nextInt((int) (a/2));
                y = r.nextInt((int) (b/2));
            }
            return new Vektor(x, y);
        }  else if (strategy.equals("Defence")) {
            //bot bo postavil svojega monkeya na kroznici katere polmer je range nasprotnika
            double parameter = 6;
            double fi = Math.PI/parameter;
            List<Vektor> cords = new ArrayList<>();
            for (int i = 0;i<(parameter*2 + 1);i++){
                x = this.range*Math.sin(fi) + this.x0;
                y = this.range*Math.cos(fi) + this.y0;
                fi += Math.PI/parameter;
                if (IsInside(x-100,y-100)) {
                    cords.add(new Vektor(x-100, y-100));
                }
            }
            if (cords.isEmpty()) return null;
            if (cords.size() == 1) return cords.get(0);
            int id = r.nextInt(cords.size()-1);
            return cords.get(id);
        }
        else {
            //postavi nekje v sredini, odvisnno od pozicije nasportnika
            //podobno kot pri offence samo, da ga bo zelel ubraniti
            if (this.x0 < (a/2)) {
                x = r.nextInt((int)(a/2));
                y = r.nextInt((int) (b/2));
            }
            else {
                x = r.nextInt((int)(a /2),(int)a);
                y = r.nextInt((int) (b/2));
            }
            return new Vektor(x, y);
        }
    }
}












