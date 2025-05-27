package io.asirum.Entity.Player;

import io.asirum.EventListener.InputManager;
import io.asirum.EventListener.InputState;

public class PlayerMovement {
    private Player player;
    private InputManager inputManager;
    private InputState inputState;

    public PlayerMovement(Player player,InputManager inputManager) {
        this.player = player;
        this.inputManager = inputManager;
        this.inputState = inputManager.getInputState();
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
