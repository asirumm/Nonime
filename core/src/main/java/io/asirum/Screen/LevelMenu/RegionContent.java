package io.asirum.Screen.LevelMenu;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import io.asirum.SchemaObject.GameLevel;
import io.asirum.SchemaObject.Region;
import io.asirum.SchemaObject.UserData;
import io.asirum.Service.UserLevelManager;
import io.asirum.Widget.StyleVars;

public class RegionContent {
    private Label regionNameLabel;
    private Region region;
    private UserData userData;
    private Skin skin;
    private Table tableLevelData;
    private Array<LevelButton> listOfLevel;

    public RegionContent(Region region, UserData userData) {
        this.region = region;
        this.userData = userData;
        tableLevelData = new Table();
        listOfLevel = new Array<>();
    }

    public void build(Skin skin){
        this.skin = skin;

        regionNameLabel = new Label(region.getName(),skin, StyleVars.TITLE_UNDERLINE);
        buildLevelContent();
    }

    public Label getRegionNameLabel() {
        return regionNameLabel;
    }

    public Table getTableLevelData() {
        return tableLevelData;
    }

    public void unlockLevel( GameLevel gameLevel){
        UserLevelManager userLevelManager = new UserLevelManager(userData);

        short userLevel = userLevelManager.getUserLevelByRegion(region);

        for (LevelButton levelContent : listOfLevel){

            if (levelContent.getLevel() == userLevel){
                levelContent.undisable(userData.getEnergy(),gameLevel,region);
            }
        }
    }

    /**
     * Membuat konten button level yang sesuai dengan level user
     */
    public void buildLevelContent(){
        UserLevelManager userLevelManager = new UserLevelManager(userData);

        short userLevelForThisRegion = userLevelManager.getUserLevelByRegion(region);

        for (GameLevel gameLevel: region.getLevels()){

            LevelButton level = new LevelButton(skin);

            level.build(
                userLevelForThisRegion,
                userData.getEnergy(),
                region,
                gameLevel
            );

            listOfLevel.add(level);

            tableLevelData.add(level.getLevelButton()).pad(2);

            // membuat row apabila pada table konten level
            // sudah ada 5 jadi seperti
            //  o o o o o
            //  o o o o o
            if ((gameLevel.getLevel()) % 5 == 0) {
                tableLevelData.row();
            }
        }
    }

    public Region getRegion() {
        return region;
    }
}
