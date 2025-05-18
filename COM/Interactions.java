package COM;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Interactions {

    public Map<Troop, HashMap<Troop,Boolean>> interactions;

    public Interactions(Set<Troop> allTroops) {
        Map<Troop, HashMap<Troop,Boolean>> interactions = new HashMap<>();
        for (Troop first:allTroops){
            HashMap<Troop, Boolean> fornow = new HashMap<>();
            for (Troop second:allTroops){
                fornow.put(second,WhoWins(first,second));
            }
            interactions.put(first, fornow);
        }
        this.interactions = interactions;
    }

    public Map<Troop, HashMap<Troop, Boolean>> getInteractions() {
        return interactions;
    }

    public boolean WhoWins(Troop trp1, Troop trp2){
        //trp1 want to destroy, trp2 your dude
        double troop1 = trp1.getMaxhealth() * trp2.getCool() / trp2.getDamage();
        double troop2 = trp2.getMaxhealth() * trp1.getCool() / trp1.getDamage();
        if (trp1.getRange() < trp2.getRange()){
            double dr = Math.abs(trp1.getRange()-trp2.getRange())/ trp1.getSpeed();
            troop2 += dr;
        }
        return troop1<=troop2;
    }

}
