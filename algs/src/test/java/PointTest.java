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

    @Test
    public void shouldHaveCorrectSlope() {
        Point p = new Point(458, 303);
        Point q = new Point(458, 38);
        Assert.assertEquals(Double.POSITIVE_INFINITY, p.slopeTo(q), 1e-10);
        Assert.assertEquals(Double.POSITIVE_INFINITY, p.slopeTo(q), 1e-10);

        p = new Point(486, 90);
        q = new Point(486, 90);
        Assert.assertEquals(Double.NEGATIVE_INFINITY, p.slopeTo(q), 1e-10);
    }
}
