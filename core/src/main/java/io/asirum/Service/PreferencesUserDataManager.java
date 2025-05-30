package io.asirum.Service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import io.asirum.Constant;
import io.asirum.SchemaObject.UserData;
import io.asirum.SchemaObject.UserLevel;

import java.time.LocalDateTime;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class PreferencesUserDataManager {
    private Preferences pref;
    private final String LEVEL_DATA = "level-data";
    private UserLastPlayedTimeService timeService;
    private final short ENERGY_NEW_PLAYER = 12;

    public PreferencesUserDataManager(){
        pref = Gdx.app.getPreferences(Constant.USER_DATA_PREFERENCES);

        timeService = new UserLastPlayedTimeService();

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
        Array<UserLevel> userLevels = new Array<>();

        userData.setLevel(userLevels);
        userData.setEnergy( ENERGY_NEW_PLAYER);//default
        userData.setLastPlayedTime(timeService.timeNow());

        Log.debug(getClass().getName(),"user data baru "+userData);

        saveData(userData);

        pref.flush();
    }

    public void saveData(UserData data){
        data.setLastPlayedTime(timeService.timeNow());

        Log.info(getClass().getName(),">>> save data user "+ data );

        String jsonData = json.toJson(data, UserData.class);
        pref.putString(LEVEL_DATA, jsonData);

        pref.flush();
    }

    public UserData loadData() {
        String jsonData = pref.getString(LEVEL_DATA);

        UserData userData = json.fromJson(UserData.class, jsonData);

        Log.info(getClass().getName(),">>> load data user :"+userData.toString());

        return userData;
    }
}
