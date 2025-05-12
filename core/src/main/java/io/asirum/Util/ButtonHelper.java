package io.asirum.Util;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.asirum.Service.ApplicationContext;
import io.asirum.Widget.StyleVars;

public class ButtonHelper {

    public static Button build(Skin skin,String style,Runnable runnable){
        Button button = new Button(skin,style);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ApplicationContext
                    .getInstance()
                    .getGameAssets()
                    .getSoundLevelControl().play();

                runnable.run();
            }
        });
        return button;
    }

    public static Button build(Skin skin,Runnable runnable){
        Button button = new Button(skin);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ApplicationContext
                    .getInstance()
                    .getGameAssets()
                    .getSoundLevelControl().play();

                runnable.run();
            }
        });
        return button;
    }


    /**
     * membuat close button untuk window
     */
    public static Button closeButton(Window window){
        Skin skin =  ApplicationContext.getInstance().getGameAssets().getWidgetSkin();

        return build(skin, StyleVars.CLOSE_BTN,()->window.setVisible(false));
    }
}
