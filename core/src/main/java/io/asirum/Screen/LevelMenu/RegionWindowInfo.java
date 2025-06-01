package io.asirum.Screen.LevelMenu;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import io.asirum.Constant;
import io.asirum.SchemaObject.Region;
import io.asirum.Widget.StyleVars;
import io.asirum.Widget.Window.BaseWindow;

public class RegionWindowInfo extends BaseWindow {

    public RegionWindowInfo(String title, Skin skin) {
        super(title, skin);
    }

    public RegionWindowInfo(Skin skin, Region region) {
        super("information", skin);

        setSize((float) Constant.VIRTUAL_WIDTH /2 , (float) Constant.VIRTUAL_HEIGHT/2 );
        setPosition((float) Constant.VIRTUAL_WIDTH /2,(float) Constant.VIRTUAL_HEIGHT /2,Align.center);

        getTitleLabel().setAlignment(Align.center);
        getTitleLabel().setStyle(skin.get(StyleVars.TITLE_LIGHT_LABEL, Label.LabelStyle.class));


        String text = "cost : "+region.getCost();

        // Content label
        Label info = new Label(text, skin);
        info.setWrap(true);

        // In RegionWindowInfo constructor:
        contentTable.pad(0); // Add this line before adding the info label
        contentTable.add(info).top().expand().fill();


        this.setVisible(false);

    }
}
