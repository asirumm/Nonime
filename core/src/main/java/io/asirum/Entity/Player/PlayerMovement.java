package io.asirum.Entity.Player;

public class PlayerMovement {
    private Player player;

    public static boolean moveLeft;
    public static boolean moveRight;
    public static boolean moveUp;


    public PlayerMovement(Player player) {
        this.player = player;
    }

    private void reset(){
        moveLeft =false;
        moveRight=false;
        moveUp=false;
    }

    public void render(){
        player.run(moveLeft,moveRight);
        // Jika pemain berada di tanah dan tombol lompat ditekan
        if (player.isOnGround() && moveUp) {
            player.jump();
        }

        reset();
    }

}
