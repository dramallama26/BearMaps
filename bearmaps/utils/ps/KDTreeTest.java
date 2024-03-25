package bearmaps.utils.ps;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
public class KDTreeTest {
    @Test
    public void test1()
    {
        //List<Point> p = Stream.of(new Point(2, 3), new Point(1, 5), new Point(4, 2), new Point(4,5), new Point(3,3), new Point(4,4))
            //   .collect(Collectors.toList());
        //List<Point> p1 = Stream.of(new Point(2, 3), new Point(1, 5), new Point(4, 2))
          //      .collect(Collectors.toList());
        List<Point> p2 = Stream.of(new Point (188.3541835438, -730.3409314820), new Point(-36.8399405401,-815.8883292022), new Point (818.3340334168, 699.3291524571))
                .collect(Collectors.toList());
        KDTree kd1 = new KDTree(p2);
        System.out.println(kd1.nearest(818.3340334168, 700.3291524571));
    }
}