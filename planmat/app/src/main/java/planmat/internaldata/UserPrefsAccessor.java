package planmat.internaldata;
import java.io.FileOutputStream;
import android.content.Context;
import android.util.Log;

import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.FileInputStream;

/**
 * Created by Luisa on 18/09/2016.
 */
public class UserPrefsAccessor {

    private static UserPrefsAccessor instance = new UserPrefsAccessor();
    private UserPrefs prefs;

    private UserPrefsAccessor() {}

    public static UserPrefsAccessor getInstance() {
        return instance;
    }

    public UserPrefs getPrefs() {
        return prefs;
    }

    public void setPrefs(UserPrefs prefs) {
        this.prefs = prefs;
    }

    public boolean loadUserPrefs(Context cont, String fileName) {
        FileInputStream fileInputStream;
        try {
            fileInputStream = cont.openFileInput(fileName);
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            UserPrefs userPrefs = (UserPrefs) inputStream.readObject();
            inputStream.close();
            fileInputStream.close();
            prefs = userPrefs;
            return true;
        } catch (Exception e) {
            Log.e("UserPrefs not found", fileName);
            return false;
        }
    }

    public void saveUserPrefs(Context cont) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = cont.openFileOutput(prefs.getUserName(), Context.MODE_PRIVATE);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(prefs);
            outputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
