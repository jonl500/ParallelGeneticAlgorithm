import java.util.concurrent.ThreadLocalRandom;

public class FlavorGenerator {

   int id;
public FlavorGenerator(){
//    this.id = id;
//    String flavor;
}
    public int getColor(){
        final int color =  ThreadLocalRandom.current().nextInt(0, 9);
        return color;
    }


   public String generateFlavor(int color) {
        String flavor;
        switch (color) {

            case 1:
                flavor = "red";
                break;

            case 2:
                flavor = "blue";
                break;

            case 3:
                flavor = "green";
                break;

            case 4:
                flavor = "yellow";
                break;

            case 5:
                flavor = "violet";
                break;

            case 6:
                flavor = "brown";
                break;

            case 7:
                flavor = "orange";
                break;

            default:
                flavor = "hole";

        }
        return flavor;
    }

    public void swap(){

    }

}
