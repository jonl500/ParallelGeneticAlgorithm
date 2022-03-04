import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Main extends JPanel {
    static FactoryFloor best;
    static ConcurrentHashMap<Long, FactoryFloor> factoryFloors = new ConcurrentHashMap<>();
    ReentrantLock lock = new ReentrantLock();


    public Main() {

    }





    public void printMetric() {
        if (best != null)
            System.out.println(best.fintnessMetric());
    }

    public ConcurrentHashMap<Long, FactoryFloor> getFactoryFloors() {
        return factoryFloors;
    }
    static volatile boolean visible = false;
    public FactoryFloor getBest() {
        return best;
    }
    public static void main(final String[] args) {

        int proc = Runtime.getRuntime().availableProcessors();

        System.out.println("Number of available processors: " +proc);
        //FactoryFloor[] factoryFloorsArray = new FactoryFloor[proc];
        if (best == null) {
            FactoryFloor fl = new FactoryFloor(32, 32);
            fl.generateStations();
            best = fl;
        }

        FactoryFloorGUI gui = new FactoryFloorGUI(best);
//        Thread guiThread = new Thread()
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
              //  System.out.println("Generating GUI? " + SwingUtilities.isEventDispatchThread());
                JFrame frame = new JFrame("Factory Floor");
                frame.setSize(1500, 850);
                frame.setResizable(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().setBackground(new Color(255, 255, 255));

                frame.add(gui);

                System.out.println("hallo, gui is in");
                frame.setVisible(true);
                visible = true;
            }
        });
//        if (best != null)
//            System.out.println("Final room metric: " + best.fintnessMetric());

        //guiThread.start();
        new Timer(17,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                gui.repaint();
            }
        }).start();
        //Exchanger<Station[][]> exchanger = new Exchanger<>();
        while (!visible){Thread.onSpinWait();}
        final Phaser phaser = new Phaser(){
            @Override
            protected boolean onAdvance(int phase, int registeredParties){
                /*if (Main.best != null) {
                    for (int i = 0; i < Main.best.getFloor().length; i++) {
                        for (int j = 0; j < Main.best.getFloor()[i].length; j++) {
                            System.out.print("[" + Main.best.getFloor()[i][j].getX() + " "
                                    + Main.best.getFloor()[i][j].getY() + " "
                                    + Main.best.getFloor()[i][j].getFlavor() + "]");
                        }
                        System.out.println(" ");
                    }
                    System.out.println("\n");
                    System.out.println(Main.best.fintnessMetric());


                }*/
                System.out.println("Phase: " + phase);
                return phase >= 100 || registeredParties==0;
            }
        };
        phaser.register();
        int phase = phaser.getPhase();

        ForkJoinPool newWorkStealingPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors(),
                ForkJoinPool.defaultForkJoinWorkerThreadFactory,
                null, true);

        //SwingUtilities.invokeLater(guiThread);
        for (int i = 0; i<proc;i++) {
           //int phaseCheck = phaser.getPhase();
            phaser.register();
            //System.out.println("Current phase: "+phaseCheck);
            newWorkStealingPool.submit(
                    new Thread(){
                FactoryFloor f = new FactoryFloor(32,32);
                public void run() {
                    do {
                       // System.out.println("current phase: " + phase);
                        f.run();
                        //could make phasers interruptable
                        //System.out.println(f.fintnessMetric());
                        //System.out.println("current phase: " + phase);
                        phaser.arriveAndAwaitAdvance();
              //          System.out.println("current phase: " + phaseCheck);
                    }while (!phaser.isTerminated());

                }

            });

            phaser.arriveAndDeregister();
            //phaser.arrive();
            System.out.println("Final metric is: " + best.fintnessMetric());
        }
        //phaser.arriveAndAwaitAdvance();



        //phaser.arriveAndAwaitAdvance();


        //phaser.arriveAndAwaitAdvance();


        //  Main thread2 = new Main(phaser,exchanger);


        //newWorkStealingPool.submit(thread2);


        System.out.println("# of threads invoking: " + newWorkStealingPool.getActiveThreadCount());
        // Exchanger<FactoryFloor> crossOver = new Exchanger<FactoryFloor>();


    }
}
