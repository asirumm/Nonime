package io.asirum.EventListener;


public abstract class InputManager {
    protected InputState inputState;

    public InputManager (InputState inputState){
        this.inputState = inputState;
    }

    public abstract void handleInput();

    // reset value input state ke false
    protected void reset(){
        inputState.reset();
    }

}
