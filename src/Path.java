import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Path implements DrawableItem{

    ArrayList<Line2D.Double> lines;
    ArrayList<Line2D.Double> solution;
    HashMap<Point2D.Double, ArrayList<Point2D.Double>> graph;

    public Path() {
        lines = new ArrayList<>();
        solution = new ArrayList<>();
        graph = new HashMap<>();
    }

    public void addLine(Line2D.Double line){
        if (graph.containsKey((Point2D.Double) line.getP1())) {
            graph.get((Point2D.Double) line.getP1()).add((Point2D.Double) line.getP2());
        } else {
            ArrayList<Point2D.Double> nodes = new ArrayList<>();
            nodes.add((Point2D.Double) line.getP2());
            graph.put((Point2D.Double) line.getP1(), nodes);
        }
        lines.add(line);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        for (Line2D.Double line : lines) {
            g.drawLine((int)line.getX1(),
                    (int)line.getY1(),
                    (int)line.getX2(),
                    (int)line.getY2());
        }
        g.setColor(Color.GREEN);
        for (Line2D.Double line : solution) {
            g.drawLine((int)line.getX1(),
                    (int)line.getY1(),
                    (int)line.getX2(),
                    (int)line.getY2());
        }
    }

    @Override
    public void tick() {

    }

    public ArrayList<Line2D.Double> getSolution() {
        return this.solution;
    }

    public void AStar(Point2D.Double start, Point2D.Double finish) {

    }

    public void BFS(Point2D.Double start, Point2D.Double finish) {
        Point2D.Double curr = start;
        ArrayList<Point2D.Double> next;
        while (!curr.equals(finish)) {
            next = graph.get(curr);
            System.out.println(curr);
            double best = next.get(0).distance(finish);
            Point2D.Double bestPoint = next.get(0);
            for (Point2D.Double pt : next) {
                if (pt.distance(finish) < best) {
                    best = pt.distance(finish);
                    bestPoint = pt;
                }
            }
            this.solution.add(new Line2D.Double(curr, bestPoint));
            this.lines.remove(new Line2D.Double(curr, bestPoint));

            curr = bestPoint;
        }
    }
}
