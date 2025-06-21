import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(Parent.class)
public class FileTesting {
    final String PATH_MANIFEST ="../assets/manifest/";
    @Test
    // akupun tak mengerti kenapa pathnya ../
    // tapi berhasil lho...
    public void widgetFiles() {
        assertTrue("pengecekan file widget.json",
            Gdx.files
                .internal(PATH_MANIFEST+"widget/widget.json").exists());

        assertTrue("pengecekan file font",
            Gdx.files
                .internal(PATH_MANIFEST+"widget/font.png").exists());
    }
}
