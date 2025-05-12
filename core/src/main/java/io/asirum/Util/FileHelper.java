package io.asirum.Util;

import com.badlogic.gdx.files.FileHandle;

import java.io.IOException;

public class FileHelper {
    public static void checkFileExists(FileHandle fileHandle) throws IOException {
        if (!fileHandle.exists()) {
            throw new IOException(fileHandle.name() + " not exist, path " + fileHandle.path());
        }
    }


    public static void checkFileHasContent(FileHandle fileHandle) throws IOException {
        if (fileHandle.length() == 0) {
            throw new IOException(fileHandle.name() + " no content, path " + fileHandle.path());
        }
    }
}
