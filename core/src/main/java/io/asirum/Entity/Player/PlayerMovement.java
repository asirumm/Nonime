package io.asirum.Entity.Player;

import io.asirum.EventListener.InputManager;
import io.asirum.EventListener.InputState;
import io.asirum.Service.Log;

public class PlayerMovement {
    private Player player;
    private InputManager inputManager;
    private InputState inputState;

    public PlayerMovement(Player player,InputManager inputManager,InputState inputState) {
        Log.debug(getClass().getCanonicalName(),"playerMovement telah di inisialisasi");

        this.player = player;
        this.inputManager = inputManager;
        this.inputState = inputState;
    }

    public void run(){
        inputManager.handleInput();

        player.run(inputState.isMoveLeft(),inputState.isMoveRight());

        // Jika pemain berada di tanah dan tombol lompat ditekan
        if (player.isOnGround() && inputState.isMoveUp()) {
            player.jump();
        }
    }
}
