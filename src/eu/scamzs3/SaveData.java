package eu.scamzs3;

import java.io.File;
import java.io.IOException;

public interface SaveData {

    void saveFile(File file) throws IOException;

    void saveFile(File file, String fileName) throws IOException;
}
