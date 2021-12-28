import com.oocourse.spec3.exceptions.PersonIdNotFoundException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@Nested
@DisplayName("when new")
class WhenNew {
    private final MyPerson pa = new MyPerson(1, "a", 12);
    private final MyPerson pb = new MyPerson(2, "b", 13);
    private final MyPerson pc = new MyPerson(3, "c", 14);
    private final MyPerson pd = new MyPerson(4, "d", 15);
    private final MyPerson pe = new MyPerson(5, "e", 16);
    private final MyPerson pf = new MyPerson(6, "f", 16);
    private MyNetwork net = new MyNetwork();

    @BeforeEach
    void createNewNetwork() throws Exception {
        net = new MyNetwork();
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

    @Test
    @DisplayName("throws MyPersonIdNotFoundException when query a unexist id")
    void throwsMyPersonIdNotFoundException() {
        assertThrows(MyPersonIdNotFoundException.class, () -> net.addRelation(1, 7, 10)); //这里的()->是个啥实在没理解，但是加上就可以这么测试了

    }

    @Nested
    @DisplayName("after init")
    class AfterInit {

        @Test
        @DisplayName("contains")
        void containsTest() {
            assertTrue(net.contains(4));
            assertFalse(net.contains(10));
        }

        @Test
        @DisplayName("qps test")
        void queryPeopleSumTest() {
            assertEquals(6, net.queryPeopleSum());
        }
    }


}