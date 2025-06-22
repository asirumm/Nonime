package io.asirum.GameLogic;

import com.badlogic.gdx.utils.Array;
import io.asirum.Entity.Player.Player;
import io.asirum.SchemaObject.GameLevel;
import io.asirum.SchemaObject.Region;
import io.asirum.SchemaObject.UserData;
import io.asirum.Screen.FailedScreen;
import io.asirum.Screen.LevelMenu.RegionContent;
import io.asirum.Screen.LevelScreen;
import io.asirum.Screen.WinScreen;
import io.asirum.Service.*;

/**
 * Kelas ini merupakan manajemen player bermain
 * menginstruksikan kematian, respawn, reward dan sebagainya
 * dalam in game
 */
public class GamePlayManager {
    private UserData userData;
    private Player player;
    private ApplicationContext context;
    private PreferencesUserDataManager userDataManager;
    private UserEnergyManager userEnergyManager;
    private Region regionWherePlayerPlaying; // player sedang bermain dimana
    private UserLevelManager levelManager;
    private GameLevel gameLevel;

    public GamePlayManager(Region region,GameLevel gameLevel) {
        this.regionWherePlayerPlaying = region;
        this.gameLevel = gameLevel;
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
    private short unlockingUserLevelAtCurrentRegion() {
        String currentRegionName = regionWherePlayerPlaying.getName();
        Log.debug(getClass().getCanonicalName(), "Memproses unlock level baru pada region %s", currentRegionName);

        short userLevelOnCurrentRegion = levelManager.getUserLevelByRegion(regionWherePlayerPlaying);
        short maxLevelRegion = (short) regionWherePlayerPlaying.getLevels().size();

        if (userLevelOnCurrentRegion < maxLevelRegion) {
            // menambahkan level
            short newLevel = (short) (userLevelOnCurrentRegion + 1);

            // mengganti nilai level di region user
            userData.getLevel().forEach(data -> {
                if (data.getName().equals(regionWherePlayerPlaying.getName())) {
                    data.setLevel(newLevel);
                }
            });

            Log.debug(getClass().getCanonicalName(), "Berhasil menambah level user ke %d", newLevel);
            return newLevel;

        } else if (userLevelOnCurrentRegion==maxLevelRegion){
            Log.debug(getClass().getCanonicalName(), "Level user sudah maksimum: %d", userLevelOnCurrentRegion);
            return userLevelOnCurrentRegion;

        }else {
            // Jika data region tidak ditemukan di userData
            Log.warn(getClass().getCanonicalName(), "Region %s tidak ditemukan di data user", currentRegionName);
            return userLevelOnCurrentRegion;
        }

    }

    private void processingRewardFinishGame(){
        userEnergyManager.energyRewardAfterWinningGame();
    }

    /**
     * INI DIGUNAKAN UNTUK COLLISION PLAYER KE PORTAL
     * menentukan apakah player bisa finish,
     * menaikkan level user dan menambah energy
     *
     */
    public void playerFinishLogic(){
        // apabila player sudah memiliki kunci
        if(player.isBringKey()){

            short levelUserAtCurrentRegion = levelManager.getUserLevelByRegion(regionWherePlayerPlaying);
            short levelWhereUserPlayNow = gameLevel.getLevel();

            // pengecekan apabila user bermain di level 1 padahal
            // ia sudah berada di level 3 maka level tidak bertambah

            if(levelWhereUserPlayNow >= levelUserAtCurrentRegion){
                Log.info(getClass().getName(), "user naik level dari level %s pada region %s",levelUserAtCurrentRegion, regionWherePlayerPlaying.getName());

                short newLevel = unlockingUserLevelAtCurrentRegion();

                updateToUndisableLevelButton(newLevel);

            }

            processingRewardFinishGame();

            userDataManager = new PreferencesUserDataManager();

            userDataManager.saveData(userData);

            context.pushScreen(new WinScreen(),null);
        }
    }


    public void play(float deltaTime){

        // ketika player terkena obstacle maka respawn
        if (player.isPlayerNeedRespawn()){
            // apabila life habis maka berakhir
            if(player.getPlayerLive() < 0){
                context.pushScreen(new FailedScreen(),null);
            }
            else {
                player.respawn();
            }
        };

    }

    /**
     * membuka button level yang sebelumnya didisable
     */
    private void updateToUndisableLevelButton(short newLevel){
        Array<RegionContent> regionContents = context.getRegionContents();

        for (RegionContent content : regionContents){

            String contentRegionName = content.getRegion().getName();
            String currentRegionName = regionWherePlayerPlaying.getName();

            if (currentRegionName.equals(contentRegionName)){

                Log.info(getClass().getCanonicalName(),"berhasil unlock level %s di region %s",newLevel, regionWherePlayerPlaying.getName());

                content.unlockLevel();
            }
        }
    }
}
