import javafx.beans.Observable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Stack;

public class Robot implements DrawableItem{
    private int width, height;
    private double x, y;
    private double goalX, goalY;
    double orientation, goalOrientation;
    private Stack<Line2D.Double> solution;

    public Robot(int width, int height, int x, int y, double orientation) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.goalX = x;
        this.goalY = y;
        this.goalOrientation = 0;
        this.orientation = 0;
        this.solution = new Stack<>();
    }
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle2D rect1 = new Rectangle2D.Double(this.x, this.y, this.width, this.height);
        AffineTransform transform = new AffineTransform();
        transform.rotate(this.orientation, (double)(this.x + width/2), (double) (this.y + this.height/2));
        Shape s = transform.createTransformedShape(rect1);
        g2.setColor(Color.RED);
        g2.fill(s);
        g2.setColor(Color.RED);
    }

    public void rotate(double degrees){
        this.goalOrientation = degrees;
    }

    public void setGoalPos(int x, int y) {
        this.goalX = x;
        this.goalY = y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void addPath(Stack<Line2D.Double> solution) {
        this.solution = solution;
    }

    public void step() {
        if (!this.solution.empty()) {
            Line2D.Double line = this.solution.pop();
            System.out.println("here: " + (int) line.getX1() + ", " + (int) line.getY1());
            double theta = Math.atan2(line.getY2() - line.getY1(), line.getX2() - line.getX1());
            //this.rotate(theta);
            this.orientation = theta;
            this.goalOrientation = theta;
            this.setGoalPos((int) line.getX2(), (int) line.getY2());
        }
    }

    public boolean goalsReached() {
        return (int) this.orientation == (int)this.goalOrientation &&(int) this.x == (int)this.goalX && (int)this.y == (int)this.goalY;
    }


    public void tick() {
        System.out.println(this.x + ", " + this.y);
        System.out.println(this.goalX + ", "+ this.goalY);
        System.out.println(this.orientation);
        System.out.println(this.goalOrientation);

        double x = Math.cos(this.goalOrientation);
        double y = Math.sin(this.goalOrientation);
        System.out.println("cos: " + x + ", sin: " + y);

        double speed = .1;
        if (this.orientation != this.goalOrientation) {
            if (this.goalOrientation - this.orientation > .2){
                this.orientation += speed;
            } else if (this.goalOrientation - this.orientation < -.2) {
                this.orientation -= speed;
            } else {
                this.orientation = this.goalOrientation;
            }
        }

        if (this.x != this.goalX ) {
            if (this.goalX - this.x < -1 || this.goalX - this.x > 1) {
                this.x += x;
            }
             else { this.x = this.goalX; }

        }
        if (this.y != this.goalY) {
            if (this.goalY - this.y < -1 || this.goalY - this.y > 1) {
                this.y += y;
            } else { this.y = this.goalY;}
        }
        if (this.goalsReached()){
            this.step();
        }
    }
}
