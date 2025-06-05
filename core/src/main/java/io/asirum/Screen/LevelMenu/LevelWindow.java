package io.asirum.Screen.LevelMenu;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import io.asirum.Util.ButtonBuilder;
import io.asirum.Widget.StyleVars;
import io.asirum.Widget.Window.BaseWindow;

public class LevelWindow extends BaseWindow {
    private WidgetFooter widgetFooter;
    private Table footerTable;
    private short index=0;

    private Array<RegionContent> regionContents;

    private Button nextRegionButton;
    private Button previousRegionButton;
    private ImageTextButton costButton;

    public ImageTextButton info;

    public LevelWindow(Skin skin, short userEnergy,
                       Array<RegionContent> regionContents) {
        super(skin,StyleVars.LEVEL_WINDOW);

        this.regionContents = regionContents;

        widgetFooter = new WidgetFooter(skin,userEnergy);
        nextRegionButton = ButtonBuilder.build(skin, StyleVars.RIGHT_CONTROL, this::nextIndex);
        previousRegionButton = ButtonBuilder.build(skin,StyleVars.LEFT_CONTROL, this::previousIndex);
        // kosong dahulu nanti diberikan pada method update
        costButton = new ImageTextButton("",skin,StyleVars.ICON_COST);

        // menggunakan footer untuk widget
        footerTable = new Table();
        footerTable.add(widgetFooter.getWidgetOnGroup());
        super.add(footerTable).row();

        updateContent();
    }

      /**
     * update data dalam konten
     */
    private void updateContent(){
        content.clear();
        header.clear();

        RegionContent currentContent = regionContents.get(index);

        costButton.setText(String.valueOf(currentContent.getRegion().getCost()));

        super.header.add(costButton).left();
        super.header.add(currentContent.getRegionNameLabel())
            .center()
            .expandX()
            // diberikan pad agar ditengah pas pada layout window level
            .padRight(costButton.getPrefWidth());


        super.content.add(previousRegionButton).left();
        super.content
            .add(currentContent.getTableLevelData())
            .expand().fill()
            .center();
        super.content.add(nextRegionButton).right();
    }

    private void nextIndex() {
        index = (short)((index + 1) % regionContents.size);
        updateContent();
    }

    private void previousIndex() {
        index = (short)((index - 1 + regionContents.size) % regionContents.size);
        updateContent();
    }
}
