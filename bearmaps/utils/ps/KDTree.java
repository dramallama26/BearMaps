package bearmaps.utils.ps;

import java.util.Comparator;
import java.util.List;

public class KDTree {
    protected kdNode root;
    public KDTree(List<Point> points){
       List <Point> xSorted = points;
       for (int i = 0; i < points.size(); i++){
            add(points.get(i));
       }
    }
    /* Adds a node for KEY iff KEY isn't in the BST already. */
    public void add(Point key) {
        if (this.root == null) {
            this.root = new kdNode(key,true);
        }
        addHelper(this.root, key);

    }
    public void addHelper(kdNode t, Point key) {
        //if (t.item == null){t = new TreeNode(key);}
        xsort xS = new xsort();
        ysort yS = new ysort();
        if (key.equals(t.item)) {
            return;
        }
        if (t.xNotY == true) {
            if (xS.compare(t.item, key) > 0) {
                if (t.left == null) {
                    t.left = new kdNode(key, null, null, false);
                } else {
                    addHelper(t.left, key);
                }
            } else {

                if (t.right == null) {
                    t.right = new kdNode(key, null, null,false);
                } else {
                    addHelper(t.right, key);
                }
            }
        }
        else{
            if (yS.compare(t.item, key) > 0) {
                if (t.left == null) {
                    t.left = new kdNode(key, null, null, true);
                } else {
                    addHelper(t.left, key);
                }
            } else {

                if (t.right == null) {
                    t.right = new kdNode(key, null, null,true);
                } else {
                    addHelper(t.right, key);
                }
            }
        }
        }
    public Point nearest(double x, double y){
        return nearestHelper(root, new Point(x,y), root).item;
    }
    public kdNode nearestHelper(kdNode n, Point goal, kdNode best){
    if (n == null) {
        return best;
    }
    if (n.item.distance(n.item,goal) < best.item.distance(best.item,goal)) {
        best = n;
    }
    kdNode goodSide;
    kdNode badSide;
    if (n.xNotY){
        xsort xS = new xsort();
        if (xS.compare(n.item, goal) > 0) {
            goodSide = n.left;
            badSide = n.right;
        }
        else{
            goodSide = n.right;
            badSide = n.left;
        }
    }
    else{
        ysort yS = new ysort();
        if (yS.compare(n.item, goal) > 0) {
            goodSide = n.left;
            badSide = n.right;
        }
        else{
            goodSide = n.right;
            badSide = n.left;
        }
    }
            best = nearestHelper(goodSide, goal, best);
        if (((Math.pow(goal.getX() - n.item.getX(), 2) <= goal.distance(goal, best.item))
                && n.xNotY)
                || (Math.pow(goal.getY() - n.item.getY(), 2) <= goal.distance(goal, best.item) && !n.xNotY)) {
            best = nearestHelper(badSide, goal, best);
        }

return best;
    }

    class xsort implements Comparator<Point>{
        public int compare(Point a, Point b) {
            if (a.getX()-b.getX() < 0){
                return -1;
            }
            else if (a.getX()-b.getX() > 0){
                return 1;
            }
            return 0;
        }
    }
    class ysort implements Comparator<Point>{
        public int compare(Point a, Point b) {
            if (a.getY()-b.getY() < 0){
                return -1;
            }
            else if (a.getY()-b.getY() > 0){
                return 1;
            }
            return 0;
        }
    }
    protected class kdNode {

        Point item;
        boolean xNotY;
        kdNode left;
        kdNode right;

        public kdNode(Point item, boolean xNotY) {
            this.item = item; left = right = null;
            this.xNotY = xNotY;
        }

        public kdNode(Point item, kdNode left, kdNode right, boolean xNotY) {
            this.item = item;
            this.left = left;
            this.right = right;
            this.xNotY = xNotY;
        }

    }
}