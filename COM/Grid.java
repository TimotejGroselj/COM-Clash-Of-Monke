package COM;

import java.util.Set;

public class Grid {
    private Set frendlys;
    private Set enemys;
    private double windowWidth;
    private double windowHeight;
    
  public Grid(Set<Troop> frendlys, Set<Troop> enemys, double windowWidth, double windowHeight) {

        this.frendlys = frendlys;
        this.enemys = enemys;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

  public Set<Troop> getTroops(boolean isFrendly) {
    if (isFrendly) {
      return frendlys;
    }
    return enemys;
  }
  public double getWindowWidth() {
    return windowWidth;
  }
  public double getWindowHeight() {
    return windowHeight;
  }
    public boolean isOnFrendlyGround(Troop troop) {
        if (troop.getLocation().getY()<=this.getWindowHeight()/2) {
            return troop.isFrendly();
        }
        return !troop.isFrendly();
    }
}