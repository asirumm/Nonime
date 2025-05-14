package io.asirum.EventListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.asirum.Entity.Player.PlayerMovement;

public class DesktopInput {

    public static void playerMovementController(){
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            PlayerMovement.moveRight = true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            PlayerMovement.moveLeft = true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            PlayerMovement.moveUp = true;
        }
    }
}
