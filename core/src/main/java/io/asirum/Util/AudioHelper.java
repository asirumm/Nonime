package io.asirum.Util;

import io.asirum.Service.ApplicationContext;

public class AudioHelper {
    public static void playMusic(){
        loopingMusic();
        ApplicationContext
            .getInstance()
            .getGameAssets()
            .getSoundMusic().play();;
    }

    private static void loopingMusic(){
        ApplicationContext
            .getInstance()
            .getGameAssets()
            .getSoundMusic().setLooping(true);;
    }
    public static void stopMusic(){
        ApplicationContext
            .getInstance()
            .getGameAssets()
            .getSoundMusic().stop();
    }
}
