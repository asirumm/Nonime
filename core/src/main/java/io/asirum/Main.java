package io.asirum;

import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import io.asirum.Screen.HomeScreen;
import io.asirum.Screen.SplashScreen;
import io.asirum.Service.ApplicationContext;
import io.asirum.Service.AssetLoader;
import io.asirum.Service.Log;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ManagedGame<ManagedScreen, ScreenTransition> {
    private  AssetLoader assetLoader;

    @Override
    public void create() {
        super.create();

        // selalu clear log file
        // TODO : mengganti toFile = true jika production
        Log.clear();
        Log.configure(false,true, Log.LogLevel.DEBUG);

        // set screen manager untuk switch screen
        ApplicationContext
            .getInstance()
            .setScreenManager(getScreenManager());


        getScreenManager().pushScreen(new SplashScreen(),null);
    }

    @Override
    public void dispose() {
        ApplicationContext.getInstance().dispose();

        Log.debug(getClass().getName(),"[dispose]");
    }
}
