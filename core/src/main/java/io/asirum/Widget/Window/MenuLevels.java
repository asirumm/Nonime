package io.asirum.Widget.Window;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import io.asirum.Util.StageHelper;

public class MenuLevels extends BaseWindow {
    private final String text ="1. every 1 hour you can get 3 energy\n"+
        "2. failed or back to home still cost energy";

    public MenuLevels(Skin skin){
        super("information",skin);

        Label infoLabel = new Label(text,skin);
        infoLabel.setAlignment(Align.left);
        infoLabel.setWrap(true);

        addChildToTable(infoLabel);

        StageHelper.debugStage(true,mainTable,this);

        this.setVisible(false);
    }
}
