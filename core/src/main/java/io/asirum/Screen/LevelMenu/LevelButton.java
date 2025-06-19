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

/**
 * Kelas ini menjadi pengelola level text button,
 *  dari disabel dan aktifasi onlistener
 */
public class LevelButton {

    private TextButton levelButton;
    private short levelContent;

    private Skin skin;

    public LevelButton(Skin skin){
            this.skin = skin;
    }

    public void build(short userLevel, short playerEnergy,
                      Region region, GameLevel gameLevel){

        levelContent = gameLevel.getLevel();

        levelButton = new TextButton(String.valueOf(levelContent),skin, StyleVars.LEVEL_TEXT_BUTTON);

        if(userLevel>= levelContent){
            levelButton.setDisabled(false);
        }else {
            levelButton.setDisabled(true);
        }

        onClick(playerEnergy,region.getCost(),gameLevel,region);
    }

    private void onClick(short playerEnergy,short regionCost,GameLevel gameLevel,Region region) {

        if (levelButton.isDisabled()){
            return;
        }

        levelButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
               ApplicationContext context = ApplicationContext.getInstance();

               context.getGameAssets().getSoundLevelControl().play();

                // apabila user energy mencukupi
                if(playerEnergy>=regionCost){

                    context
                        .pushScreen(new PlayScreen(gameLevel,region),null);

                }else {
                    Log.debug(LevelButton.class.getCanonicalName(),"energy user tidak cukup cost %s energi user %s",regionCost,playerEnergy);

                }
            }
        });
    }

    public void undisable(short playerEnergy,GameLevel gameLevel,Region region){
        levelButton.setDisabled(false);
        onClick(playerEnergy,region.getCost(),gameLevel,region);
    }

    public short getLevel() {
        return levelContent;
    }

    public TextButton getLevelButton() {
        return levelButton;
    }
}
