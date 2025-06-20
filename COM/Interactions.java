package COM;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Interactions {
    // vse mozne interactone za med troopi in kdo koga zmaga
    // bool true pomeni da troop2 zmaga troop1
    public Map<Troop, HashMap<Troop,Boolean>> interactions;

    public Interactions(String[] allTroops) {
        Map<Troop, HashMap<Troop,Boolean>> interactions = new HashMap<>();
        for (String first:allTroops){
            Troop attacker = new Troop(new Vektor(0,0),true,first);
            HashMap<Troop, Boolean> fornow = new HashMap<>();
            for (String second:allTroops){
                Troop defender = new Troop(new Vektor(0,0),false,second);
                fornow.put(defender,WhoWins(attacker,defender));
            }
            interactions.put(attacker, fornow);
        }
        this.interactions = interactions;
    }

    public Map<Troop, HashMap<Troop, Boolean>> getInteractions() {
        return interactions;
    }

    public boolean WhoWins(Troop trp1, Troop trp2){
        //trp1 want to destroy, trp2 your dude
        //zmago presodi po dps (damage per second) in v koliko korakih ga unici
        double troop1 = trp1.getMaxhealth() * trp2.getCool() / trp2.getDamage();
        double troop2 = trp2.getMaxhealth() * trp1.getCool() / trp1.getDamage();
        if (trp1.getRange() < trp2.getRange()){
            double dr = Math.abs(trp1.getRange()-trp2.getRange())/ trp1.getSpeed(); //ce je oddaljen stran dobi prednost troop z vecjim rangom
            troop2 += dr; //pridobi dr korakov prednosti
        }
        return troop1<=troop2; //kdo ima vec korakov zmaga
    }

}
