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
    private short levelContent;

    private Skin skin;

    public LevelContent(Skin skin){
            this.skin = skin;
    }

    public void build(short userLevel, short playerEnergy,
                      Region region, GameLevel gameLevel){

        levelContent = gameLevel.getLevel();

        name = new TextButton(String.valueOf(levelContent),skin, StyleVars.LEVEL_TEXT_BUTTON);

        if(userLevel>= levelContent){
            name.setDisabled(false);
        }else {
            name.setDisabled(true);
        }

        onClick(playerEnergy,region.getCost(),gameLevel,region);
    }

    private void onClick(short playerEnergy,short regionCost,GameLevel gameLevel,Region region) {

        if (name.isDisabled()){
            return;
        }

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

    public void undisable(short playerEnergy,GameLevel gameLevel,Region region){
        name.setDisabled(false);
        onClick(playerEnergy,region.getCost(),gameLevel,region);
    }

    public short getLevel() {
        return levelContent;
    }

    public TextButton getTextButton() {
        return name;
    }
}
