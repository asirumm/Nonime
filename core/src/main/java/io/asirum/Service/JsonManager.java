package io.asirum.Service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import io.asirum.Util.FileHelper;

import java.io.IOException;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class JsonManager {
    private JsonReader jsonReader;

    public JsonManager() {
        jsonReader = new JsonReader();

    }

    public <T> T parser(Class<T> clazz,String path){
        FileHandle file = Gdx.files.internal(path);

        return json.fromJson(clazz, file);
    }

    public void toJson(Object object, String filePath) {
        FileHandle file = Gdx.files.local(filePath);
        file.writeString(json.prettyPrint(object), false);
    }

    /**
     * mengecek json file dari is exist dan adakah konten didalamnya?
     */
    private void jsonFileChecker(String filePath) throws IOException {
        FileHandle fileHandle = Gdx.files.internal(filePath);
        FileHelper.checkFileExists(fileHandle);
        FileHelper.checkFileHasContent(fileHandle);
    }
}
