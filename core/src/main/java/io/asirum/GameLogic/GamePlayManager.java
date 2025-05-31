package io.asirum.GameLogic;

import com.badlogic.gdx.utils.Array;
import io.asirum.Entity.Player.Player;
import io.asirum.SchemaObject.Region;
import io.asirum.SchemaObject.UserData;
import io.asirum.SchemaObject.UserLevel;
import io.asirum.Screen.LevelMenu.RegionContent;
import io.asirum.Screen.LevelScreen;
import io.asirum.Service.*;

public class GamePlayManager {
    private UserData userData;
    private Player player;
    private ApplicationContext context;
    private PreferencesUserDataManager userDataManager;
    private UserEnergyManager userEnergyManager;
    private Region region; // player sedang bermain dimana
    private UserLevelManager levelManager;

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

        if(userData==null){
            Log.warn(getClass().getCanonicalName(),"userdata belum di tambahkan pada app context");
        }

        levelManager = new UserLevelManager(userData);

        decreaseUserEnergy((short) costEnergy);
    }

    // pengurangan energi player, untuk play game
    private void decreaseUserEnergy(short costEnergy){
        Log.info(getClass().getName(),"user energi berkurang karena bermain sejumlah %s",costEnergy);

        short currentUserEnergy = userData.getEnergy();

        userData.setEnergy((short) (currentUserEnergy-costEnergy));

        userDataManager.saveData(userData);
    }

    /**
     * Menambah level pengguna di wilayah yang sedang dimainkan jika belum mencapai level maksimum.
     */
    private short unlockUserLevel() {
        String currentRegionName = region.getName();
        Log.debug(getClass().getCanonicalName(), "Memproses unlock level baru pada region %s", currentRegionName);

        for (UserLevel data : userData.getLevel()) {
            if (data.getName().equals(currentRegionName)) {
                short currentLevel = data.getLevel();
                int maxLevel = region.getLevels().size(); // jumlah level maksimal di region

                if (currentLevel < maxLevel) {
                    short newLevel = (short) (currentLevel + 1);
                    data.setLevel(newLevel);
                    Log.debug(getClass().getCanonicalName(), "Berhasil menambah level user ke %d", newLevel);
                    return newLevel;
                } else {
                    Log.debug(getClass().getCanonicalName(), "Level user sudah maksimum: %d", currentLevel);
                    return currentLevel;
                }
            }
        }

        // Jika data region tidak ditemukan di userData
        Log.warn(getClass().getCanonicalName(), "Region %s tidak ditemukan di data user", currentRegionName);
        return 1;
    }

    private void rewardFinish(){
        userEnergyManager.energyRewardAfterWinningGame();
    }

    /**
     * INI DIGUNAKAN UNTUK COLLISION PLAYER KE PORTAL
     * menentukan apakah player bisa finish,
     * menaikkan level user dan menambah energy
     *
     */
    public void playerFinishLogic(){
        if(player.isBringKey()){

            short currentUserLevel = levelManager.getUserLevelByRegion(region);

            Log.info(getClass().getName(), "user naik level dari level %s pada region %s",currentUserLevel,region.getName());

            short newLevel = unlockUserLevel();

            updateLevelUnlockUI(newLevel);

            rewardFinish();

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

    private void updateLevelUnlockUI(short newLevel){
        Array<RegionContent> regionContents = context.getRegionContents();

        for (RegionContent content : regionContents){
            if (content.getRegionName().equals(region.getName())){

                Log.info(getClass().getCanonicalName(),"berhasil unlock level %s di region %s",newLevel,region.getName());

                content.unlockLevel(newLevel);
            }
        }
    }
}
