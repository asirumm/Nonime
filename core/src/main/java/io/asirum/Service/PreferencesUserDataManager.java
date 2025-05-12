package io.asirum.Service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import io.asirum.Constant;
import io.asirum.SchemaObject.UserData;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class PreferencesUserDataManager {
    private Preferences pref;
    private final String LEVEL_DATA = "level-data";

    public PreferencesUserDataManager(){
        pref = Gdx.app.getPreferences(Constant.USER_DATA_PREFERENCES);


        // apabila belum ada preferences
        if(!pref.contains(LEVEL_DATA)){
            Log.info(getClass().getName(),">>> user belum memiliki data preferense");
            Log.info(getClass().getName(),">>> mulai membuat data preferense user");
            initializerUserData();
        }
    }

    /**
     * membuat preferences user baru dengan energy 12
     *
     */
    private void initializerUserData() {
        UserData userData = new UserData();
        userData.setEnergy(12);//default
        userData.setLevel(1);//default

        saveData(userData);

        pref.flush();
    }

    private void saveData(UserData data){
        String jsonData = json.toJson(data, UserData.class);
        pref.putString(LEVEL_DATA, jsonData);

        pref.flush();
    }

    public UserData loadData() {
        String jsonData = pref.getString(LEVEL_DATA);

        UserData userData = json.fromJson(UserData.class, jsonData);

        Log.debug(getClass().getName(),">>> load data user :"+userData.toString());

        return userData;
    }
}
