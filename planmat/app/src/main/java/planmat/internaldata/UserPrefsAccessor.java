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

    public UserPrefs loadUserPrefs(Context cont, String fileName) {
        UserPrefs userPrefs = null;
        FileInputStream fileInputStream;
        try {
            fileInputStream = cont.openFileInput(fileName);
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            userPrefs = (UserPrefs) inputStream.readObject();
            inputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            Log.d("UserPrefs not found: ", e.getMessage());
        }
        return userPrefs;
    }

    public void storeUserPrefs(UserPrefs userPrefs, String fileName, Context cont) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = cont.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(userPrefs);
            outputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
