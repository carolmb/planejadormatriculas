import planmat.datarepresentation.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import java.util.logging.Logger;
/**
 * Created by feisabel on 9/26/16.
 */
public class ComponentTest {
    private static final Logger LOG = Logger.getLogger( ComponentTest.class.getName() );
    private ClassList classList;
    private StatList statList;
    private Component component;

    @Before
    public void setUp() throws Exception{
        classList = new ClassList();
        statList = new StatList();
        ArrayList<StatList.Entry> statEntryList = statList.getEntries();
        StatList.Entry statEntry1 = new StatList.Entry(5, 15, 5);
        StatList.Entry statEntry2 = new StatList.Entry(15, 10, 5);
        StatList.Entry statEntry3 = new StatList.Entry(10, 0, 10);
        statEntryList.add(statEntry1);
        statEntryList.add(statEntry2);
        statEntryList.add(statEntry3);
        component = new Component("DIM0549", "GRAFOS");
        //component.setStatList(statList);
        //component.setClassList(classList);
    }

    @After
    public void tearDown() throws Exception{
        classList = null;
        statList = null;
        component = null;
    }

    @Test
    public void testGetSuccessRate() throws Exception {
        //float successRate = component.getSuccessRate();
        //assertEquals(0.4, successRate, 0.000001);
    }
}
