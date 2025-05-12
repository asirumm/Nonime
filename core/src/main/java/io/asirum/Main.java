package io.asirum;

import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import io.asirum.Service.ApplicationContext;
import io.asirum.Service.Log;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ManagedGame<ManagedScreen, ScreenTransition> {
    @Override
    public void create() {
        super.create();

        // selalu clear log file
        // TODO : mengganti toFile = true jika production
        Log.clear();
        Log.configure(false,true, Log.LogLevel.DEBUG);

    }

    @Override
    public void dispose() {
        ApplicationContext.instance.dispose();

        Log.debug(getClass().getName(),"[dispose]");
    }
}
