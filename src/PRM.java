import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class PRM{
    Robot robot;
    Obstacle obs;
    Path path;
    Point2D.Double start, finish;
    Stack<Line2D.Double> solution;

    ArrayList<Point2D.Double> points;

    public PRM(Robot robot, Obstacle obs, Path path) {
        this.robot = robot;
        this.obs = obs;
        this.path = path;
        this.solution = new Stack<>();
        this.points = new ArrayList<>();
    }

    public void start(Point2D.Double start, Point2D.Double finish){
        this.start = start;
        this.finish = finish;
        this.createSparseObstacles();
        this.generatePoints();
        System.out.println("Points generated");
        this.generatePath();
        System.out.println("Path generated");
        this.path.BFS(start, finish);
        System.out.println("BFS calculated");
        // build stack of robot movements and reverse it
        ArrayList<Line2D.Double> reversedSol = this.path.getSolution();
        Collections.reverse(reversedSol);
        this.solution.addAll(reversedSol);
        this.robot.addPath(this.solution);

    }

    public void createObstacles() {
        this.obs.addRectangle(0, 0, 200, 50);
        this.obs.addRectangle(200, 0, 50, 200);
        this.obs.addRectangle(500, 0, 50, 500);
        this.obs.addRectangle(0, 500, 500, 50);
        this.obs.addRectangle(0, 350, 200, 50);
        this.obs.addRectangle(150, 300, 50, 200);
        this.obs.addRectangle(0, 200, 150, 50);
        this.obs.addRectangle(250, 250, 200, 50);
        this.obs.addRectangle(450, 0, 50, 300);
        this.obs.addRectangle(375, 400, 50, 100);
    }

    public void createSparseObstacles() {
        this.obs.addRectangle(0, 0, 50, 50);
        this.obs.addRectangle(40, 10, 50, 80);
        this.obs.addRectangle(0, 70, 50, 50);
        this.obs.addRectangle(350, 350, 50, 50);
        this.obs.addRectangle(275, 50, 50, 300);
        this.obs.addRectangle(200, 75, 80, 50);
        this.obs.addRectangle(200, 250, 80, 50);
        this.obs.addRectangle(0, 300, 100, 50);
        this.obs.addRectangle(0, 200, 70, 50);
    }

    public void generatePoints() {

        int newPoints = 0;
        int min = 0;
        int max = 500;

        while (newPoints < 100) {
            int x = ThreadLocalRandom.current().nextInt(min, max + 1);
            int y = ThreadLocalRandom.current().nextInt(min, max + 1);

            Point2D.Double pnt = new Point2D.Double(x, y);
            if (!this.obs.contains(pnt, this.robot)) {
                this.points.add(pnt);
                newPoints++;
            }
        }
        this.points.add(start);
        this.points.add(finish);
    }

    public void generatePath() {
        for (Point2D.Double pnt : this.points) {
            for (Point2D.Double pnt2 : this.points) {
                if (pnt != pnt2){
                    Line2D.Double line = new Line2D.Double(pnt.getX(), pnt.getY(), pnt2.getX(), pnt2.getY());
                    if (!this.obs.intersects(line, this.robot)){
                        this.path.addLine(line);
                    }
                }
            }
        }
    }
}
