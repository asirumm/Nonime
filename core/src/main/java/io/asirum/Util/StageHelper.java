package io.asirum.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.asirum.Service.ApplicationContext;
import io.asirum.Service.Log;

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


    public static void debugStage(boolean on, WidgetGroup... widget){
        if(on){
            for (WidgetGroup w: widget){
                w.setDebug(true);
            }
        }else {
            for (WidgetGroup w: widget){
                w.setDebug(false);
            }
        }
    }
}
