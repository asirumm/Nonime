package io.asirum.GameLogic;

import io.asirum.Entity.Player.Player;
import io.asirum.SchemaObject.Payload;
import io.asirum.SchemaObject.UserData;
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

    private void changeUserLevel(){
        int currentLevel = userData.getLevel();

        int maxLevel = payload.getLevel().size;

        // jika level user sudah max maka tidak ada penambahan
        if (currentLevel!=maxLevel){
            userData.setLevel(currentLevel+1);
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

            changeUserLevel();

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
