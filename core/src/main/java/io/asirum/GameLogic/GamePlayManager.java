package io.asirum.GameLogic;

import com.badlogic.gdx.utils.Array;
import io.asirum.Entity.Player.Player;
import io.asirum.SchemaObject.Payload;
import io.asirum.SchemaObject.Region;
import io.asirum.SchemaObject.UserData;
import io.asirum.SchemaObject.UserLevel;
import io.asirum.Screen.LevelScreen;
import io.asirum.Service.ApplicationContext;
import io.asirum.Service.Log;
import io.asirum.Service.PreferencesUserDataManager;
import io.asirum.Service.UserEnergyManager;

public class GamePlayManager {
    private UserData userData;
    private Payload payload;
    private Player player;
    private ApplicationContext context;
    private PreferencesUserDataManager userDataManager;
    private UserEnergyManager userEnergyManager;
    private Region region; // player sedang bermain dimana

    public GamePlayManager(Region region) {
        this.region = region;
    }

    // parameter ini dibuat karena urutan eksekusi, maka dari itu tidak
    // ditaruh di konstructor
    public void start(Player player,int costEnergy) {
        context  = ApplicationContext.getInstance();

        this.userDataManager = new PreferencesUserDataManager();
        this.player = player;

        userEnergyManager = context.getUserEnergyManager();

        this.userData = context.getUserData();
        this.payload = context.getPayloadGame();

        if(userData==null|| payload==null){
            Log.warn(getClass().getName(),">>> userdata atau payload belum di set pada app context");
        }

        decreaseUserEnergy((short) costEnergy);
    }

    // pengurangan energi player, untuk play game
    private void decreaseUserEnergy(short costEnergy){
        Log.debug(getClass().getName(),"user energi berkurang "+costEnergy);

        short currentUserEnergy = userData.getEnergy();

        userData.setEnergy((short) (currentUserEnergy-costEnergy));

        userDataManager.saveData(userData);
    }

    /**
     * Menambah level pengguna di wilayah yang sedang dimainkan jika belum mencapai level maksimum.
     */
    private void updateUserLevelProgress() {
        // Nama region yang sedang dimainkan
        String currentRegionName = region.getName();

        // Loop melalui semua data level pengguna
        for (UserLevel userLevel : userData.getLevel()) {

            // Bandingkan nama region pengguna dengan region yang sedang dimainkan
            if (userLevel.getName().equals(currentRegionName)) {

                int maxLevel = region.getLevels().size;

                // Tambah level jika belum mencapai maksimum
                if (userLevel.getLevel() < maxLevel) {
                    int currentLevel = userLevel.getLevel();
                    userLevel.setLevel(currentLevel + 1);
                }

                // Jika sudah mencapai maksimum, tidak dilakukan perubahan
            }
        }
    }

    private void rewardFinish(){
        userEnergyManager.userRewardAfterWin();
    }

    /**
     * INI DIGUNAKAN UNTUK COLLISION PLAYER KE PORTAL
     * menentukan apakah player bisa finish,
     * menaikkan level user dan menambah energy
     *
     */
    public void playerFinishLogic(){
        if(player.isBringKey()){

            Log.info(getClass().getName(), ">>> user naik level ke "+userData.getLevel());

            updateUserLevelProgress();

            rewardFinish();

            Log.debug(getClass().getName(),">>> saving user data");
            userDataManager = new PreferencesUserDataManager();
            userDataManager.saveData(userData);

            context.pushScreen(new LevelScreen(),null);

        }
    }


    public void play(float deltaTime){

        // ketika player terkena obstacle maka respawn
        if (player.isPlayerNeedRespawn()){
            if(player.getPlayerLive() < 0){
                context.pushScreen(new LevelScreen(),null);
            }
            else {
                player.respawn();
                player.setPlayerNeedRespawn(false);
            }
        };

    }
}
