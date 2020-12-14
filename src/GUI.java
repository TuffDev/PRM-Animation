import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI extends JPanel{
    private ArrayList<DrawableItem> items;

    public GUI(){
//        this.frame = new JFrame();
//        this.frame.setSize(500, 500);
//        this.frame.setVisible(true);
        this.items = new ArrayList<>();

        Timer timer = new Timer(5, new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                for (DrawableItem item : items) {
                    item.tick();
                    repaint();
                }
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (DrawableItem item : items) {
            item.draw(g);
        }
    }

    public void addRobot(Robot robot){
        this.items.add(robot);
    }

    public void addObstacles(Obstacle obstacle){
        this.items.add(obstacle);
    }

    public void addPath(Path path) {
        this.items.add(path);
    }
}
