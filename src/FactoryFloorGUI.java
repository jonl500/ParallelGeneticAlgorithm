import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import javax.swing.*;


public class FactoryFloorGUI extends JPanel {
    //Graphics g;
    int floorSizeX;
    int floorSizeY;
    FactoryFloor floor;

    public FactoryFloorGUI(FactoryFloor floor) {
        super();
        //JFrame frame = new JFrame("Factory Floor");

      //  g = frame.getGraphics();
        //JPanel panel = new JPanel();
        this.setOpaque(true);
        this.setBounds(750, 400, 1500, 800);
        this.setSize(2000,2000);
       // JLabel label = new JLabel("Factory Floor");
       // label.setHorizontalTextPosition(JLabel.LEFT);
       // label.setVerticalTextPosition(JLabel.TOP);
      //  Border border = BorderFactory.createTitledBorder("Factory Floor");
       // border.getBorderInsets(panel);

        //frame.add(panel);
        //frame.setVisible(true);
        this.floor = floor;
        this.floorSizeX = floor.getX();
        this.floorSizeY = floor.getY();
       // frame.setVisible(true);
    }

    public void setFloorSize(int floorSizeX, int floorSizeY) {
        this.floorSizeX = floorSizeX;
        this.floorSizeY = floorSizeY;
    }

    public int getFloorSizeY() {
        return floorSizeY;
    }


    public int getFloorSizeX() {
        return floorSizeX;
    }


    public void setFloor(FactoryFloor floor) {
        this.floor = floor;
        setFloorSize(floor.getX(),floor.getY());
    }

    public FactoryFloor getFloor() {
        return floor;
    }


    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        //this.setBackground(Color.WHITE);
        FactoryFloor f = Main.best;
        Graphics2D g2d = (Graphics2D) g;
        //Timer timer = new Timer(2, this.paint(g)); not what i need

        for (int i = 0; i < this.floorSizeX; i++) {
            for (int j = 0; j < floorSizeY; j++) {
                switch (f.getFloor()[i][j].getFlavor()) {

                    case "red":
                        g2d.setColor(Color.RED);
                        g2d.fillRect((1500/this.floorSizeX ) * j,
                                (800/ this.floorSizeY ) * i,
                                20,20);
                        break;
                    case "blue":
                        g2d.setColor(Color.BLUE);
                        g2d.fillRect((1500/this.floorSizeX ) * j,
                                (800/this.floorSizeY) * i,
                                20,20);
                        break;
                    case "green":
                        g2d.setColor(Color.GREEN);
                        g2d.fillRect((1500/this.floorSizeX ) * j,
                                (800/this.floorSizeY ) * i,
                                20,20);
                        break;
                    case "yellow":
                        g2d.setColor(Color.YELLOW);
                        g2d.fillRect((1500/this.floorSizeX) * j,
                                (800/this.floorSizeY) * i,
                                20,20);
                        break;
                    case "violet":
                        Color Violet = new Color(155, 38, 182);
                        g2d.setColor(Violet);
                        g2d.fillRect((1500/this.floorSizeX) * j,
                                (800/this.floorSizeY) * i,
                                20,20);
                        break;
                    case "brown":
                        Color brown = new Color(112, 66, 20);
                        g2d.setColor(brown);
                        g2d.fillRect((1500/this.floorSizeX) * j,
                                (800/this.floorSizeY) * i,
                                20,20);
                        break;
                    case "orange":
                        g2d.setColor(Color.ORANGE);
                        g2d.fillRect((1500/this.floorSizeX) * j,
                                (800/this.floorSizeY) * i,
                                20,20);
                        break;

                        default:
                        g2d.setColor(Color.BLACK);
                        g2d.fillRect((1500/this.floorSizeX) * j,
                                (800/this.floorSizeY) * i,
                                20, 20);
                        break;
                }
//                g2d.fillRect((this.floorSizeX / horizontalBlocks) * j,
//                        (this.floorSizeY / verticleBlocks) * i,
//                        this.floorSizeX / horizontalBlocks,
//                        this.floorSizeY / verticleBlocks);

            }
        }

    }
//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//        Graphics2D g2 = (Graphics2D) g;
//        g2.setColor(Color.BLACK);
//        g2.fillRect(130, 30,100, 80);
//    }

    //figure out swing timer
    //paint component look at java swing oracle docs
//    @Override
//    public void run() {
//        this.repaint();
//    }

}