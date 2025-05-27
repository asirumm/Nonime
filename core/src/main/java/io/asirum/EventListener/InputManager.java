package io.asirum.EventListener;


public abstract class InputManager {
    protected InputState inputState;

    public InputManager (){
        this.inputState = new InputState();
    }

    public abstract void handleInput();

    // reset value input state ke false
    protected void reset(){
        inputState.reset();
    }

    public InputState getInputState(){
        return inputState;
    }
}
