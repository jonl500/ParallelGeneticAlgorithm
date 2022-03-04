import java.util.concurrent.*;

public class FactoryFloor extends Thread implements Runnable,Comparable {

    private final int x;
    private final int y;
    //private int[] stationNum;
    private final int id;
    private boolean isSwapped;
    final private static Exchanger<Station[][]> exchanger = new Exchanger<>();
    private Station[][] floor;
    final private long tId;

    public FactoryFloor(int xBound, int yBound) {
        x = xBound;
        y = yBound;
        floor = new Station[x][y];
        this.id = ThreadLocalRandom.current().nextInt(0,1000);
        isSwapped = false;
        tId = Thread.currentThread().getId();
        //generate station

            //Station station = new Station(ThreadLocalRandom.current().nextInt(gen,33),ThreadLocalRandom.current().nextInt(gen,33));
            generateStations();


       // placeFloorsInHashMap();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Station[][] getFloor() {
        return floor;
    }

    public void setFloor(Station[][] floor) {
        this.floor = floor;
    }


    //FlavorGenerator flavGen = new FlavorGenerator();
    //making an arrayList of all positions in thread to keep track
    public long getThreadId() {
        return tId;
    }

    public long getId() {
        return id;
    }

    public void swap(Station station1, Station station2) {
        Station temp = station1;
        station1 = station2;
        station2 = temp;
        isSwapped = true;
    }

   // public void resetSwap() {
    //    isSwapped = false;
   // }

    public Station[][] mutate() {
        for (int i = 0; i < floor.length; i++) {
            for (int j = 0; j < floor[i].length; j++) {
                if (i % 3 == 0) {
                    int swapX = ThreadLocalRandom.current().nextInt(0, floor.length);
                    int swapY = ThreadLocalRandom.current().nextInt(0, floor[i].length);
                    swap(floor[i][j], floor[swapX][swapY]);
                }
            }
        }
        return floor;
    }

    public void crossOver(){
        try {

            Station[][] myHalf = this.meiosis();
            Station[][] newHalf = exchanger.exchange(myHalf, 2, TimeUnit.SECONDS);
            for (int i = 0; i < floor.length / 2; i++) {
                if(i%5 == 0)
                System.arraycopy(newHalf[i], 0, floor[i], 0, floor[i].length);
            }

        } catch (InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    //figure out how to effectively split and merge this array
    public Station[][] meiosis() {
        int halfFloor = floor.length / 2;
        Station[][] gamete = new Station[halfFloor][y];
        for (int i = 0; i < halfFloor; i++) {
            System.arraycopy(floor[i], 0, gamete[i], 0, floor[i].length);
        }
        return gamete;
    }


    public void generateStations() {
        for (int i = 0; i < floor.length; i++) {
            for (int j = 0; j < floor[i].length; j++) {
                if(floor[i][j] == null)
                floor[i][j] = new Station(i,j);
            }
        }
    }

    public int affinityCheck(Station s) {
        int sum = 0;
        int affinity = 0;
        for (Station[] stations : floor) {
            for (Station station : stations) {
                double distancex = Math.abs(station.getX() - s.getX());
                double distancey = Math.abs(station.getY() - s.getY());
                double check = Math.hypot(distancex, distancey);
                if (station.getFlavor().equals("hole") || (s.getFlavor().equals(station.getFlavor()) && check <= 1)) {
                    affinity = 0;
                } else if (check > 1) {
                    affinity = 1;
                }
                sum += affinity;
            }
        }
        return sum;
    }

    public int fintnessMetric() {
        int sum = 0;
        for (Station[] stations : floor) {
            for (Station station : stations) {
                sum = this.affinityCheck(station);


            }
        }
        return sum;
    }

    @Override
    public int compareTo(Object o) {
        FactoryFloor other = (FactoryFloor) o;
        if (this.fintnessMetric() > other.fintnessMetric()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public void run() {
        //phaser.arriveAndAwaitAdvance();

        //FactoryFloor factoryFloor = new FactoryFloor(10, 10);
       // for (int gen = 0; gen < 100; gen++) {


            //  System.out.println("Thread ID: " + factoryFloor.getThreadId());


      //  for (int gen = 0; gen < 100; gen++) {


            // phaser.arriveAndAwaitAdvance();
            for (int i = 0; i < 50 && !Thread.currentThread().isInterrupted(); i++) {
                this.mutate();
            }


            this.crossOver();

            for (int i = 0; i < 50; i++) {
                this.mutate();
            }

    //}
     placeFloorsInHashMap();

       // }



    }

    synchronized void placeFloorsInHashMap() {
        Main.factoryFloors.put(this.getThreadId(), this);
        for (long tID : Main.factoryFloors.keySet()) {
            if (Main.best == null) {
                Main.best = Main.factoryFloors.get(tID);
            } else if (Main.best.fintnessMetric() <= this.fintnessMetric()) {
                Main.best = this;
            }
        }
    }
}
