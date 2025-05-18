package io.asirum.GameLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import io.asirum.Entity.Items.Key;
import io.asirum.Entity.Items.Portal;
import io.asirum.Entity.Player.Player;
import io.asirum.SchemaObject.Payload;
import io.asirum.SchemaObject.UserData;
import io.asirum.Service.ApplicationContext;
import io.asirum.Service.Log;
import io.asirum.Service.PreferencesUserDataManager;

public class GamePlayManager {
    private UserData userData;
    private Payload payload;
    private final int REWARD_ENERGY = 3;
    private Player player;
    private Key key;

    public GamePlayManager(Player player, Key key) {
        this.player = player;
        this.key    = key;

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
     * menentukan apakah player bisa finish,
     * menaikkan level user dan menambah energy
     *
     * param portal diperlukan untuk mengetahui apakah
     * key sudah di ambil oleh player
     */
    public void isPlayerCanFinish(Portal portal){
        if(portal.getKey().isCollected()){

            Log.info(getClass().getName(), ">>> user naik level ke "+userData.getLevel());

            changeUserLevel();

            rewardFinish();

            PreferencesUserDataManager manager = new PreferencesUserDataManager();
            manager.saveData(userData);
        }
    }


    public void play(float deltaTime){
        // proses mendelete item key yang sudah di collect
        if(!key.getToDestroy().isEmpty()){
            for (Body body : key.getToDestroy()) {
                key.getWorld().destroyBody(body);
            }
            key.getToDestroy().clear();
        }

        // ketika player terkena obstacle maka respawn
        if (player.isPlayerNeedRespawn()){
            if(player.getPlayerLive() < 0){
                Gdx.app.exit();// TODO game kalah ketika live habis
            }
            player.respawn();
            player.setPlayerNeedRespawn(false);
        };

    }
}
