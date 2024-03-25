package bearmaps.utils.pq;

import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapTest {
    @Test
    public void test1() {
        MinHeap<Integer> m = new MinHeap<>();
        m.insert(5);
        m.insert(6);
        m.insert(7);
        assertTrue(m.contains(7));
        assertEquals(5, (int) m.removeMin());
        assertFalse(m.contains(5));
    }


}
