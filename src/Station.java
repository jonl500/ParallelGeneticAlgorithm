import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

public class Station {

   private int x;
   private int y;
    private int id;
    private final String flavor;
    FlavorGenerator flav = new FlavorGenerator();
    final int flavorOfStation = flav.getColor();
    public Station(int x,int y){
        this.x = x;
        this.y = y;
        this.flavor = flav.generateFlavor(flavorOfStation);
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public String getFlavor(){

        return flavor;
    }
    public int[] gridPositon(){
        return new int[]{
                this.x,this.y
        };
    }
}
