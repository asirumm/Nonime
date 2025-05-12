package io.asirum.Screen;

import de.eskalon.commons.screen.ManagedScreen;
import io.asirum.Service.ApplicationContext;
import io.asirum.Service.Log;

public class SplashScreen extends ManagedScreen {
    private ApplicationContext context;

    public SplashScreen() {
        Log.debug(getClass().getName(),"[berhasil switch screen]");

        context = ApplicationContext.getInstance();

        context
            .getAssetLoader()
            .loadAssets();
    }
    @Override
    public void render(float delta) {
        if(context.getAssetLoader().update()){

            context.getGameAssets().build();

            context.pushScreen(new HomeScreen(),null);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }
}
