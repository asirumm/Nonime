package io.asirum.Util;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ButtonHelper {
    public static Button build(Skin skin,String style,Runnable runnable){
        Button button = new Button(skin,style);
        return button;
    }

    public static Button build(Skin skin,Runnable runnable){
        Button button = new Button(skin);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                runnable.run();
            }
        });
        return button;
    }

}
