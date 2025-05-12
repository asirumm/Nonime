package io.asirum;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
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
}
