package bearmaps.utils.pq;

import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapPQTest {

    @Test
    public void test1() {
        MinHeapPQ<String> pq = new MinHeapPQ<>();
        pq.insert("Bob", 1);
        pq.insert("Karen", 100);
        pq.insert("Megan", 0);
        pq.insert("Wyatt", -1);
        pq.changePriority("Karen", -100);
        assertEquals("Karen", pq.peek());
        assertTrue(pq.contains("Bob"));
        pq.changePriority("Karen", 100);
        assertEquals("Wyatt", pq.peek());

        assertEquals("Wyatt", pq.poll());
        assertFalse(pq.contains("Wyatt"));
    }
}
