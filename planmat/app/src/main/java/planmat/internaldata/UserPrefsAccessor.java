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

    private UserPrefsAccessor() {}

    public static UserPrefsAccessor getInstance() {
        return instance;
    }

    public UserPrefs loadUserPrefs(Context cont) {
        UserPrefs userPrefs = null;
        FileInputStream fileInputStream;
        try {
            fileInputStream = cont.openFileInput("file");
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            userPrefs = (UserPrefs) inputStream.readObject();
            inputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userPrefs;
    }

    public void storeUserPrefs(UserPrefs userPrefs, Context cont) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = cont.openFileOutput("file", Context.MODE_PRIVATE);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(userPrefs);
            outputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
