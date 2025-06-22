package io.asirum.Widget.Window;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class SimpleWindow extends BaseWindow{

    public SimpleWindow(Skin skin) {
        super(skin);
    }

    public SimpleWindow(Skin skin, String style) {
        super(skin, style);
    }

    public SimpleWindow(String title, Skin skin, String styleName) {
        super(title, skin, styleName);
    }

    public Table getContentTable(){
        return super.content;
    }

    public Table getHeaderTable(){
        return super.header;
    }

    public void addCloseButton(){
        super.withCloseButton();
    }


}
