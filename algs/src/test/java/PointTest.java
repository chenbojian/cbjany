import org.junit.Assert;
import org.junit.Test;

public class PointTest {

    private void assertSmaller(Point p1, Point p2) {
        Assert.assertTrue(p1.compareTo(p2) < 0);
    }
    private void assertEqual(Point p1, Point p2) {
        Assert.assertTrue(p1.compareTo(p2) == 0);
    }

    @Test
    public void shouldCompare() {

        assertSmaller(new Point(1, 2), new Point(2, 3));
        assertSmaller(new Point(1, 3), new Point(2, 3));
        assertSmaller(new Point(2, 2), new Point(1, 3));
        assertEqual(new Point(1, 1), new Point(1, 1));


    }
}
