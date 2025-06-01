package io.asirum.Screen.LevelMenu;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import io.asirum.SchemaObject.GameLevel;
import io.asirum.SchemaObject.Region;
import io.asirum.SchemaObject.UserData;
import io.asirum.Service.UserLevelManager;
import io.asirum.Widget.StyleVars;

public class RegionContent {
    private Table rootContainer;
    private Array<LevelContent> levelContents;
    private Label regionName;
    private TextButton regionCost;
    private String name;
    // digunakan untuk pencarian, fitur user naik level
    // akan iterasi level data dan me undisable sesuai level user

    private Skin skin;

    public RegionContent(Skin skin){
        this.skin = skin;
        levelContents = new Array<>();
        rootContainer = new Table();
    }

    public void build(Region region, UserData userData){
        buildRegionNameLabel(region.getName());
        buildRegionCostLabel(String.valueOf(region.getCost()));

        name = region.getName();

        // header table untuk nama dan cost
        Table headerTable = new Table();
        headerTable.add(regionCost).left().expandX();
        headerTable.add(regionName).left().expandX();

        // table level
        Table footerTable = new Table();

//        footerTable.setDebug(true);
        footerTable.top();
//        headerTable.setDebug(true);

        rootContainer.add(headerTable).expandX().fillX().row();
        rootContainer.add(footerTable).expand().fill();

        UserLevelManager userLevelManager = new UserLevelManager(userData);

        short userLevelForThisRegion = userLevelManager.getUserLevelByRegion(region);

        for (GameLevel gameLevel: region.getLevels()){

            LevelContent level = new LevelContent(skin);

            level.build(
                userLevelForThisRegion,
                userData.getEnergy(),
                region,
                gameLevel
            );

            levelContents.add(level);

            footerTable.add(level.getTextButton());

            // membuat row apabila pada table konten level
            // sudah ada 5 jadi seperti
            //  o o o o o
            //  o o o o o
            if ((gameLevel.getLevel()) % 5 == 0) {
                footerTable.row();
            }

        }
    }

    private void buildRegionNameLabel(String name) {
        regionName =  new Label(name,skin, StyleVars.TITLE_LIGHT_LABEL);
        regionName.setAlignment(Align.center);
    }

    private void buildRegionCostLabel(String cost) {
        regionCost =  new TextButton(cost,skin, StyleVars.COST_TEXT_BUTTON);
    }

    public Table getContent() {
        return rootContainer;
    }

    public String getRegionName(){
        return name;
    }

    public void unlockLevel(short userLevel,short playerEnergy,GameLevel gameLevel,Region region){
        for (LevelContent levelContent : levelContents){
            if (levelContent.getLevel() == userLevel){
                levelContent.undisable(playerEnergy,gameLevel,region);
            }
        }
    }
}
