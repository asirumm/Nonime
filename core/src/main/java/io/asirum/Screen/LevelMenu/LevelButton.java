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

    public void build(short userLevel,
                      Region region, GameLevel gameLevel){

        levelContent = gameLevel.getLevel();

        levelButton = new TextButton(String.valueOf(levelContent),skin, StyleVars.LEVEL_TEXT_BUTTON);

        if(userLevel>= levelContent){
            levelButton.setDisabled(false);
        }else {
            levelButton.setDisabled(true);
        }

        onClick(region.getCost(),gameLevel,region);
    }

    private void onClick(short regionCost,GameLevel gameLevel,Region region) {

        levelButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (levelButton.isDisabled()) return;

                ApplicationContext context = ApplicationContext.getInstance();

                context.getGameAssets().getSoundLevelControl().play();

                short userEnergy = context.getUserData().getEnergy();

                // apabila user energy mencukupi
                if(userEnergy>=regionCost){

                    context
                        .pushScreen(new PlayScreen(gameLevel,region),null);

                }else {
                    Log.debug(LevelButton.class.getCanonicalName(),"energy user tidak cukup cost %s energi user %s",regionCost,userEnergy);

                }
            }
        });
    }

    public void setUndisable(){
        levelButton.setDisabled(false);
    }

    public short getLevel() {
        return levelContent;
    }

    public TextButton getLevelButton() {
        return levelButton;
    }
}
