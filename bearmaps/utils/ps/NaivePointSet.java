package bearmaps.utils.ps;
import java.util.List;
public class NaivePointSet implements PointSet {
    int size;
    List<Point>points;
    public NaivePointSet(List<Point> points){
        size = points.size();
        this.points = points;
    }
    public Point nearest(double x, double y){
        Point compare = new Point(x,y);
        int chosenIndex = 0;
        double minDist = compare.distance(compare, points.get(0));
        for (int i = 1; i<size; i++){
            double dist = compare.distance(compare,points.get(i));
            if (dist < minDist){
                minDist = dist;
                chosenIndex = i;
            }
        }
        return points.get(chosenIndex);
    }
}