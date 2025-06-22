package io.asirum.EventListener;


import io.asirum.Service.Log;

public class InputState {
    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveUp;


    // untuk desktop
    public void reset() {
        moveLeft = false;
        moveRight = false;
        moveUp = false;
    }

    // Getter dan setter
    public boolean isMoveLeft() {
        return moveLeft; }
    public void setMoveLeft(boolean value) { moveLeft = value; }
    public boolean isMoveRight() { return moveRight; }
    public void setMoveRight(boolean value) { moveRight = value; }
    public boolean isMoveUp() { return moveUp; }
    public void setMoveUp(boolean value) { moveUp = value; }
}
