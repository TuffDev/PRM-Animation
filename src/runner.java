import javafx.beans.Observable;

import javax.swing.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class runner {
    Obstacle obs;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(600, 600);
        frame.setVisible(true);

        GUI gui = new GUI();

        frame.add(gui);

        // select start and end points for the robot
        Point2D.Double start = new Point2D.Double(380, 100);
        Point2D.Double finish = new Point2D.Double(100, 380);
        Robot r1 = new Robot(10, 10, (int) start.getX(), (int) start.getY(), 0);
        Obstacle obs = new Obstacle();
        Path path = new Path();

        gui.addObstacles(obs);

        PRM prm = new PRM(r1, obs, path);

        prm.start(start, finish);

        gui.addPath(path);
        gui.addRobot(r1);

    }





}
