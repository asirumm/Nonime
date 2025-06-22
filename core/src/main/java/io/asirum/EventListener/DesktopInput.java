package io.asirum.EventListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Timer;
import io.asirum.Entity.Player.PlayerMovement;
import io.asirum.Service.Log;

public class DesktopInput extends InputManager{

    public DesktopInput(InputState inputState) {
        super(inputState);
    }

    public void handleInput() {
        // reset harus selalu diawal agar tidak blok playermovement.run
        reset();

        // Update state berdasarkan input keyboard
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            inputState.setMoveRight(true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            inputState.setMoveLeft(true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            inputState.setMoveUp(true);
        }

    }

}
