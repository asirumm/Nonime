package io.asirum.EventListener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import io.asirum.Entity.Player.PlayerMovement;

public class MobileInput extends InputManager{
    private Button leftButton;
    private Button rightButton;
    private Button upButton;

    public MobileInput(InputState inputState) {
        super(inputState);
    }

    public void setButton(Button leftButton,Button rightButton, Button upButton){
        this.leftButton = leftButton;
        this.upButton = upButton;
        this.rightButton = rightButton;

        setup();
    }


    private void setup(){
        leftControl();
        rightControl();
        upControl();
    }

    public void handleInput(){
        // tidak perlu implement karena kita sudah pakai add listener
    }


    private void leftControl(){
        leftButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                inputState.setMoveLeft(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                inputState.setMoveLeft(false);
            }
        });
    }

    private void rightControl(){
        rightButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                inputState.setMoveRight(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                inputState.setMoveRight(false);
            }
        });
    }

    private void upControl(){
        upButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                inputState.setMoveUp(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                inputState.setMoveUp(false);
            }
        });
    }

}
