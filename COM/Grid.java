package COM;

import java.util.Set;

public class Grid {
    private int xSize;
    private int ySize;
    private Set frendlys;
    private Set enemys;
    private double windowWidth;
    private double windowHeight;
    
  public Grid(Set<Troop> frendlys, Set<Troop> enemys, double windowWidth, double windowHeight) {
        this.xSize = 100;
        this.ySize = 200;
        this.frendlys = frendlys;
        this.enemys = enemys;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

  public int getxSize() {
    return xSize;
}
  public int getySize() {
    return ySize;
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
        if (troop.getLocation().getY()<=this.getySize()/2) {
            return troop.isFrendly();
        }
        return !troop.isFrendly();
    }
}