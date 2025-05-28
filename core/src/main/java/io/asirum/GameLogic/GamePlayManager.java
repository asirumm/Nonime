package io.asirum.GameLogic;

import com.badlogic.gdx.Gdx;
import io.asirum.Entity.Player.Player;
import io.asirum.SchemaObject.Payload;
import io.asirum.SchemaObject.UserData;
import io.asirum.Screen.LevelScreen;
import io.asirum.Screen.PlayScreen;
import io.asirum.Service.ApplicationContext;
import io.asirum.Service.Log;
import io.asirum.Service.PreferencesUserDataManager;
import io.asirum.Util.ButtonActionHelper;
import io.asirum.Util.CameraHelper;

public class GamePlayManager {
    private UserData userData;
    private Payload payload;
    private final int REWARD_ENERGY = 1;
    private Player player;
    private ApplicationContext context;
    private PreferencesUserDataManager userDataManager;

    public void start(Player player,int costEnergy) {
        this.userDataManager = new PreferencesUserDataManager();
        this.player = player;
        context  = ApplicationContext.getInstance();

        this.userData = context.getUserData();
        this.payload = context.getPayloadGame();

        if(userData==null|| payload==null){
            Log.warn(getClass().getName(),">>> userdata atau payload belum di set pada app context");
        }

        decreaseUserEnergy(costEnergy);
    }

    // pengurangan energi player, untuk play game
    private void decreaseUserEnergy(int costEnergy){
        int currentUserEnergy = userData.getEnergy();
            userData.setEnergy(currentUserEnergy-costEnergy);
            userDataManager.saveData(userData);
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
     * menentukan apakah player bisa finish,
     * menaikkan level user dan menambah energy
     *
     */
    public void playerFinishLogic(){
        if(player.isBringKey()){

            Log.info(getClass().getName(), ">>> user naik level ke "+userData.getLevel());

            changeUserLevel();

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
}
