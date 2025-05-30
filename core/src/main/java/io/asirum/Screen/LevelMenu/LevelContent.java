package io.asirum.Screen.LevelMenu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.asirum.SchemaObject.GameLevel;
import io.asirum.SchemaObject.Region;
import io.asirum.Screen.PlayScreen;
import io.asirum.Service.ApplicationContext;
import io.asirum.Service.Log;
import io.asirum.Widget.StyleVars;

public class LevelContent {
    private TextButton name;
    private short level;

    private Skin skin;

    public LevelContent(Skin skin){
            this.skin = skin;
    }

    public void build(short userLevel, short playerEnergy,
                      Region region, GameLevel gameLevel){

        level = gameLevel.getLevel();

        name = new TextButton(String.valueOf(level),skin, StyleVars.LEVEL_TEXT_BUTTON);

        // apabila level lebih besar dari userLevel maka disable
        if(userLevel>=level){
            onClick(playerEnergy,region.getCost(),gameLevel,region);
        }else {
            name.setDisabled(true);
        }
    }

    private void onClick(short playerEnergy,short regionCost,GameLevel gameLevel,Region region) {

        name.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // apabila user energy mencukupi
                if(playerEnergy>=regionCost){

                    ApplicationContext
                        .getInstance()
                        .pushScreen(new PlayScreen(gameLevel,region),null);

                }else {
                    Log.debug(LevelContent.class.getCanonicalName(),"energy user tidak cukup cost %s energi user %s",regionCost,playerEnergy);

                }
            }
        });
    }

    public void undisable(){
        name.setDisabled(false);
    }

    public short getLevel() {
        return level;
    }

    public TextButton getTextButton() {
        return name;
    }
}
