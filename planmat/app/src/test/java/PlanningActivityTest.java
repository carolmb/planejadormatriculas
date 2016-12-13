import planmat.appgui.*;
import planmat.datarepresentation.*;
import planmat.internaldata.UserPrefs;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
/**
 * Created by feisabel on 9/20/16.
 */
public class PlanningActivityTest {
    private PlanningActivity pA;
    private Requirements reqs;
    private ClassList cL;

    @Before
    public void setUp() throws Exception {
        /*Component c1 = new Component("IMD0017", "PRÁTICAS DE LEITURA E ESCRITA EM PORTUGUÊS I");
        Component c2 = new Component("IMD0018", "PRÁTICAS DE LEITURA EM INGLÊS");
        cL = new ClassList();
        //c1.setClassList(cL);
        //c2.setClassList(cL);
        StatList sL = new StatList();
        ArrayList<StatList.Entry> statEntryList = sL.getEntries();
        StatList.Entry statEntry1 = new StatList.Entry(5, 15, 5);
        StatList.Entry statEntry2 = new StatList.Entry(15, 10, 5);
        StatList.Entry statEntry3 = new StatList.Entry(10, 0, 10);
        statEntryList.add(statEntry1);
        statEntryList.add(statEntry2);
        statEntryList.add(statEntry3);
        //c1.setStatList(sL);
        //c2.setStatList(sL);
        ArrayList<Component> comps = new ArrayList<Component>();
        comps.add(c1);
        comps.add(c2);
        Semester s1 = new Semester(comps);
        ArrayList<Semester> semesters = new ArrayList<Semester>();
        semesters.add(s1);
        reqs = new Requirements("1", semesters);
        pA = new PlanningActivity();
        UserPrefs.Semester s = new UserPrefs.Semester();
        ArrayList<String> comps2 = s.getComponents();
        comps2.add("IMD0017");
        comps2.add("IMD0018");
        pA.setSelectedSemester(s);
*/
    }

    @After
    public void tearDown() throws Exception {
        pA = null;
        reqs = null;
        cL = null;
    }

    @Test
    public void testCheckSemesterDifficulty() throws Exception {
        //boolean b = pA.checkSemesterDifficulty(reqs);
        //assertEquals(true, b);
    }
}
