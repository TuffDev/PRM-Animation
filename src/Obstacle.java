import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Obstacle implements DrawableItem{

    private ArrayList<Rectangle2D> rectangles;

    public Obstacle() {
        this.rectangles = new ArrayList<>();
    }

    public void addRectangle(int x, int y, int width, int height){
        Rectangle rect = new Rectangle(x, y, width, height);
        this.rectangles.add(rect);
    }


    public ArrayList<Rectangle2D> getRectangles() {
        return this.rectangles;
    }

    public boolean contains(Point2D pnt, Robot rob){
        boolean res = false;
        int robW = rob.getWidth();
        int robH = rob.getHeight();

        for (Rectangle2D rect : this.rectangles) {
            // buffered rectangle to ensure robot doesn't touch sides.
            Rectangle bufferedRect = new Rectangle((int)rect.getX() - robW/2, (int)rect.getY() - robH/2, (int)rect.getWidth()+robW, (int)rect.getHeight()+robH);
            if (bufferedRect.contains(pnt)) {
                res = true;
            }
        }
        return res;
    }

    public boolean intersects(Line2D line, Robot rob) {
        boolean res = false;

        for (Rectangle2D rect : this.rectangles) {
            int robW = rob.getWidth();
            int robH = rob.getHeight();
            Rectangle bufferedRect = new Rectangle((int)rect.getX() - robW/2, (int)rect.getY() - robH/2, (int)rect.getWidth()+robW, (int)rect.getHeight()+robH);
            if (bufferedRect.intersectsLine(line)) {
                res = true;
            }
        }
        return res;
    }


    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        for (Rectangle2D rect : rectangles) {
            g2.fill(rect);
        }
    }

    @Override
    public void tick() {
    }
}
