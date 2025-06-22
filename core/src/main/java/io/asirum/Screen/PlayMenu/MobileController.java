package io.asirum.Screen.PlayMenu;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.asirum.EventListener.MobileInput;
import io.asirum.Service.Log;
import io.asirum.Widget.StyleVars;

public class MobileController {
    private Button leftControl;
    private Button rightControl;
    private Button upControl;

    private Skin skin;
    private Table mobileControllerTable;
    private MobileInput mobileInput;

    public MobileController(Skin skin, MobileInput mobileInput) {
        this.skin = skin;
        mobileControllerTable = new Table();
        this.mobileInput = mobileInput;
    }

    public void buildMobileControllerButton(){
        leftControl  = new Button(skin, StyleVars.MOBILE_CONTROL_LEFT);
        rightControl = new Button(skin,StyleVars.MOBILE_CONTROL_RIGHT);
        upControl    = new Button(skin,StyleVars.MOBILE_CONTROL_UP);

        Table leftTable = new Table();
        leftTable.add(leftControl);
        leftTable.add(rightControl).padLeft(15);

        Table rightTable = new Table();
        rightTable.add(upControl);

        mobileControllerTable.add(leftTable).expandX().left();
        mobileControllerTable.add(rightTable).expandX().right();
        mobileControllerTable.padBottom(10);

        Log.debug(getClass().getName(),">>> setup mobile controller to input mobile");
        mobileInput.setButton(leftControl,rightControl,upControl);
    }

    public Table getMobileControllerTable() {
        return mobileControllerTable;
    }
}
