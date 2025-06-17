package io.asirum.GameLogic;

import com.badlogic.gdx.utils.Array;
import io.asirum.Entity.Player.Player;
import io.asirum.SchemaObject.GameLevel;
import io.asirum.SchemaObject.Region;
import io.asirum.SchemaObject.UserData;
import io.asirum.Screen.LevelMenu.RegionContent;
import io.asirum.Screen.LevelScreen;
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

        short userLevel = levelManager.getUserLevelByRegion(regionWherePlayerPlaying);
        short maxLevelRegion = (short) regionWherePlayerPlaying.getLevels().size();

        if (userLevel < maxLevelRegion) {
            short newLevel = (short) (userLevel + 1);

            // mengganti nilai level di region user
            userData.getLevel().forEach(data -> {
                if (data.getName().equals(regionWherePlayerPlaying.getName())) {
                    data.setLevel(newLevel);
                }
            });

            Log.debug(getClass().getCanonicalName(), "Berhasil menambah level user ke %d", newLevel);
            return newLevel;

        } else if (userLevel==maxLevelRegion){
            Log.debug(getClass().getCanonicalName(), "Level user sudah maksimum: %d", userLevel);
            return userLevel;

        }else {
            // Jika data region tidak ditemukan di userData
            Log.warn(getClass().getCanonicalName(), "Region %s tidak ditemukan di data user", currentRegionName);
            return userLevel;
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

            short currentUserLevel = levelManager.getUserLevelByRegion(regionWherePlayerPlaying);

            Log.info(getClass().getName(), "user naik level dari level %s pada region %s",currentUserLevel, regionWherePlayerPlaying.getName());

            short newLevel = unlockingUserLevelAtCurrentRegion();

            updateToUndisableLevelButton(newLevel);

            processingRewardFinishGame();

            userDataManager = new PreferencesUserDataManager();

            userDataManager.saveData(userData);


            context.pushScreen(new LevelScreen(),null);
        }
    }


    public void play(float deltaTime){

        // ketika player terkena obstacle maka respawn
        if (player.isPlayerNeedRespawn()){
            // apabila life habis maka berakhir
            if(player.getPlayerLive() < 0){
                context.pushScreen(new LevelScreen(),null);
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

                content.unlockLevel(gameLevel);
            }
        }
    }
}
