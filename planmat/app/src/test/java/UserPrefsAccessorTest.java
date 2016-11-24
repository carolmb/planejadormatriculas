import planmat.appgui.MainActivity;
import planmat.internaldata.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import java.util.logging.Logger;

/**
 * Created by feisabel on 9/22/16.
 */
public class UserPrefsAccessorTest {
    UserPrefs userPrefs;
    UserPrefsAccessor userPrefsAccessor;
    MainActivity activity;

    @Before
    public void setUp() throws Exception {
        userPrefs = new UserPrefs("username", "name", "1", "1", "1", 1);
        activity = new MainActivity();
    }

    @After
    public void tearDown() throws Exception {
        userPrefsAccessor = null;
        userPrefs = null;
        activity = null;
    }

    @Test
    public void testLoadUserPrefs() {
        userPrefsAccessor.getInstance().storeUserPrefs(userPrefs, userPrefs.getUserName(), activity);
        UserPrefs uP = userPrefsAccessor.getInstance().loadUserPrefs(activity, userPrefs.getUserName());
        assertEquals(userPrefs, uP);
    }
}
