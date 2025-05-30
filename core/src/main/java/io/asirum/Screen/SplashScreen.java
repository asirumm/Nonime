package io.asirum.Screen;

import de.eskalon.commons.screen.ManagedScreen;
import io.asirum.Service.*;

public class SplashScreen extends ManagedScreen {
    private ApplicationContext context;

    public SplashScreen() {
        Log.debug(getClass().getName(),"[berhasil switch screen]");

        context = ApplicationContext.getInstance();

        context
            .getAssetLoader()
            .loadAssets();


        // load payload dan user data
        PayloadJsonManager jsonManager = new PayloadJsonManager();
        PreferencesUserDataManager userDataManager = new PreferencesUserDataManager();


        context.setPayload(jsonManager.load());
        context.setUserData(userDataManager.loadData());


        // menambahkan data user level
        // jika user baru
        // atau ada region baru
        UserLevelManager levelManager = new UserLevelManager(context.getUserData());

        // proses penambahan
        levelManager.fillUserLevels(context.getPayloadGame().getRegions());


        // manajemen energi
        // untuk memberikan reward ke user setelah off beberapa lama
        UserEnergyManager userEnergyManager = new UserEnergyManager(context.getUserData());

        // proses
        userEnergyManager.userRewardProcessAfterOfflineSeveralTimes();

        context.setUserEnergyManager(userEnergyManager);

        // saving data
        userDataManager.saveData(levelManager.getUserData());
    }


    @Override
    public void render(float delta) {
        // update status assets
        if(context.getAssetLoader().update()){

            context.getGameAssets().build();

            context.pushScreen(new HomeScreen(),null);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }
}
