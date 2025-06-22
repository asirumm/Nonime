package io.asirum.Widget.Window;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.asirum.Constant;
import io.asirum.Service.ApplicationContext;
import io.asirum.Util.AudioHelper;
import io.asirum.Widget.StyleVars;

public class BaseWindow extends Table {
    protected Table header;
    protected Table content;
    protected Label titleLabel;
    protected Button closeButton;
    private Skin skin;

    public BaseWindow(Skin skin) {
        this(null, skin,null);
        this.skin = skin;
    }

    public BaseWindow(Skin skin,String style) {
        this(null, skin,style);
        this.skin = skin;
    }

    public BaseWindow(String title, Skin skin,String styleName) {
        // Set background dari style Window
        Window.WindowStyle style =null;
        if(styleName!=null){
            style = skin.get(styleName, Window.WindowStyle.class);
        }else {
             style = skin.get(Window.WindowStyle.class);
        }
        this.setBackground(style.background);

        this.skin = skin;

        this.setSize(Constant.VIRTUAL_WIDTH, Constant.VIRTUAL_HEIGHT);

        initHeader(title);
        initContent();

        // Tambahkan ke tabel utama
        this.add(header).fillX().top();
        this.row();
        this.add(content).expand().fill().row();
    }

    private void initHeader(String title) {
        header = new Table();

        // Label judul (jika ada)
        titleLabel = new Label(title != null ? title : "", skin);

        header.add(titleLabel).expandX().center();
    }

    private void initContent() {
        content = new Table();
    }

    protected void withCloseButton(){
        // Tombol close di kanan
        closeButton = new Button(skin, StyleVars.CLOSE_BTN);
        header.add(closeButton).right();

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ApplicationContext
                    .getInstance()
                    .getGameAssets()
                    .getSoundLevelControl().play();
                setVisible(false);
            }
        });
    }

    // Untuk mengatur atau mengganti judul setelah dibuat
    protected void setTitleLabel(Label label) {
        this.titleLabel = label;
        header.clear();
        header.add(titleLabel).expandX().center();
        header.add(closeButton).right().width(closeButton.getPrefWidth());
    }
}
