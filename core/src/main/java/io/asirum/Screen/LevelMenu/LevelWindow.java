package io.asirum.Screen.LevelMenu;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import io.asirum.Constant;
import io.asirum.Util.ButtonBuilder;
import io.asirum.Widget.StyleVars;
import io.asirum.Widget.Window.BaseWindow;
import io.asirum.Widget.Window.TooltipWindow;

public class LevelWindow extends BaseWindow {
    private WidgetFooter widgetFooter;
    private Table footerTable;
    private short index=0;

    private Array<RegionContent> regionContents;

    private Button nextRegionButton;
    private Button previousRegionButton;
    private ImageTextButton costButton;
    private TooltipWindow tooltipCost;
    private TooltipWindow tooltipUserEnergy;

    public ImageTextButton info;

    public LevelWindow(Skin skin, int userEnergy,
                       Array<RegionContent> regionContents) {

        super(skin,StyleVars.LEVEL_WINDOW);
        super.setSize(Constant.VIRTUAL_WIDTH,Constant.VIRTUAL_HEIGHT);

        this.regionContents = regionContents;

        widgetFooter = new WidgetFooter(skin,userEnergy);
        nextRegionButton = ButtonBuilder.build(skin, StyleVars.RIGHT_CONTROL, this::nextIndex);
        previousRegionButton = ButtonBuilder.build(skin,StyleVars.LEFT_CONTROL, this::previousIndex);
        // kosong dahulu nanti diberikan pada method update
        costButton = new ImageTextButton("",skin,StyleVars.COST_TEXT_BUTTON);

        tooltipUserEnergy = new TooltipWindow("Energi dipulihkan 3 poin setiap 2 jam offline",skin);
        tooltipCost = new TooltipWindow("Energi yang diperlukan untuk bermain",skin);

        tooltipListener(tooltipCost,costButton);
        tooltipListener(tooltipUserEnergy,widgetFooter.getUserEnergyTextButton());

        // menggunakan footer untuk widget
        footerTable = new Table();
        footerTable.add(widgetFooter.getWidgetOnGroup());
        super.add(footerTable).row();

        updateContent();
    }


    private void tooltipListener(TooltipWindow tooltip, Actor widget) {
        widget.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Gdx.app.getType() == Application.ApplicationType.Android) {
                    // Android behavior
                    tooltip.setVisible(true);
                    tooltip.setPosition(event.getStageX(), event.getStageY()+20);
                } else {
                    // Desktop behavior
                    tooltip.setVisible(true);
                    // Posisikan tooltip di samping kursor mouse
                    tooltip.setPosition(Gdx.input.getX() + 10, Gdx.graphics.getHeight() - Gdx.input.getY() + 10);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                tooltip.setVisible(false);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                tooltip.setVisible(false);
            }

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                if (Gdx.app.getType() != Application.ApplicationType.Android) {
                    // Update posisi tooltip saat mouse bergerak (desktop only)
                    tooltip.setPosition(event.getStageX() + 10, event.getStageY() + 10);
                }
                return true;
            }
        });
    }

    public TooltipWindow getCostTooltip() {
        return tooltipCost;
    }

    public TooltipWindow getUserEnergyTooltip() {
        return tooltipUserEnergy;
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
