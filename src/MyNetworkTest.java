import com.oocourse.spec3.exceptions.PersonIdNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MyNetworkTest {

    private final MyPerson pa = new MyPerson(1, "a", 12);
    private final MyPerson pb = new MyPerson(2, "b", 13);
    private final MyPerson pc = new MyPerson(3, "c", 14);
    private final MyPerson pd = new MyPerson(4, "d", 15);
    private final MyPerson pe = new MyPerson(5, "e", 16);
    private final MyPerson pf = new MyPerson(6, "f", 16);
    private MyNetwork net = new MyNetwork();

    @BeforeEach
    void setUp() throws Exception {
        net.addPerson(pa);
        net.addPerson(pb);
        net.addPerson(pc);
        net.addPerson(pd);
        net.addPerson(pe);
        net.addPerson(pf);
        net.addRelation(1, 2, 10);
        net.addRelation(1, 3, 20);
        net.addRelation(3, 4, 10);
        net.addRelation(4, 5, 1);
        net.addRelation(3, 5, 10);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void isCircle() throws PersonIdNotFoundException {
        assertTrue(net.isCircle(1, 4));
        assertFalse(net.isCircle(1, 6));
        assertTrue(net.isCircle(1, 1));
    }

    @Test
    void queryBlockSum() {
        assertEquals(2, net.queryBlockSum());
    }

}