package io.asirum.Widget;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import io.asirum.Constant;


/// popup message akan menghilang beberapa saat
/// BELUM DIBUTUHKAN
public class ToolipPopup extends Label {
    public ToolipPopup(CharSequence text, Skin skin) {
        super(text, skin);
        initial();
    }
    public ToolipPopup( Skin skin) {
        super("", skin);
        initial();
    }

    public ToolipPopup(CharSequence text, Skin skin, String styleName) {
        super(text, skin, styleName);
        initial();
    }

    public ToolipPopup( Skin skin, String styleName) {
        super("", skin, styleName);
        initial();
    }

    private void initial() {
        setVisible(false);
        this.setTouchable(Touchable.disabled);
        this.setAlignment(Align.center);
        this.pack();
    }

    public void show() {
        // Position at center of screen
        float x = (Constant.VIRTUAL_WIDTH - getWidth()) / 2;
        float y = (Constant.VIRTUAL_HEIGHT - getHeight()) / 2;

        this.setPosition(x, y);
        this.setVisible(true);

        // Clear any existing actions
        this.clearActions();

        // Quick show and fade sequence:
        // - Instantly appear (alpha 1)
        // - Wait 1 second
        // - Fade out over 0.3 seconds
        // - Hide the tooltip
        this.addAction(Actions.sequence(
            Actions.alpha(1),            // Start fully visible
            Actions.delay(1f),           // Show for 1 second
            Actions.fadeOut(0.5f),       // Fade out over 0.3 seconds
            Actions.run(() -> setVisible(false))
        ));
    }

    public void updateText(String text){
        setText(text);
        pack();
    }
}
