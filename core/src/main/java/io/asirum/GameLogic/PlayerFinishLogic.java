package io.asirum.GameLogic;

import io.asirum.Entity.Items.Portal;
import io.asirum.SchemaObject.Payload;
import io.asirum.SchemaObject.UserData;
import io.asirum.Service.ApplicationContext;
import io.asirum.Service.Log;
import io.asirum.Service.PreferencesUserDataManager;

public class PlayerFinishLogic {
    private UserData userData;
    private Payload payload;
    private final int REWARD_ENERGY = 3;



    public PlayerFinishLogic() {
        userData =
            ApplicationContext
                .getInstance()
                .getUserData();

        if(userData==null){
            Log.warn(getClass().getName(),
                ">>> ada yang salah user data belum di append di app context" +
                ", tidak bisa update level user setelah finish");
        }

        payload =
            ApplicationContext
                .getInstance()
                .getPayloadGame();

        if(userData==null){
            Log.warn(getClass().getName(),
                ">>> ada yang salah payload belum di append di app context");
        }
    }

    private void changeUserLevel(){
        int currentLevel = userData.getLevel();

        int maxLevel = payload.getLevel().size;

        // jika level user sudah max maka tidak ada penambahan
        if (currentLevel!=maxLevel){
            userData.setLevel(currentLevel+1);
        }

    }

    private void rewardFinish(){
        int currentEnergy = userData.getEnergy();

        userData.setEnergy(currentEnergy + REWARD_ENERGY);
    }

    /**
     * INI DIGUNAKAN UNTUK COLLISION PLAYER KE PORTAL
     */
    public void playerFinish(Portal portal){
        if(portal.getKey().isCollected()){

            Log.info(getClass().getName(), ">>> user naik level ke "+userData.getLevel());

            changeUserLevel();

            rewardFinish();

            PreferencesUserDataManager manager = new PreferencesUserDataManager();
            manager.saveData(userData);
        }
    }
}
