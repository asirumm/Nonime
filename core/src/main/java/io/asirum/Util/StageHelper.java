package io.asirum.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.asirum.Service.ApplicationContext;

public class StageHelper {
    /**
     * membuat object stage juga set input processor
     * - include viewport
     */
    public static Stage createInstance(){
        Viewport viewport = ApplicationContext.getInstance().getViewport();
        Stage stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        return stage;
    }

    public static void addActors(Stage stage, Actor... actors) {

        for (Actor actor : actors) {
            stage.addActor(actor);
        }
    }


}
