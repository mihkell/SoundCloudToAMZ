package eu.scamzs3.apps.scamzs3.soundcloud;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import eu.scamzs3.SaveData;

/*
 * Class for testing
 */
public class SoundCloudSaveData implements SaveData {

    final static Logger logger = Logger.getLogger(SoundCloudSaveData.class);
    private String fileName = "SoundCloudMusic";
    private String fileExtentsion = ".mp3";

    @Override
    public void saveFile(File fileToSave, String fileName) throws IOException {
        saveFile(fileToSave);

    }

    @Override
    public void saveFile(File fileToSave) throws IOException {
        try {

            Path mp3Path = Paths.get(fileName + fileExtentsion);
            File file = new File(mp3Path.toAbsolutePath().toString());
            if (file.exists()) {
                file.delete();
            }

            Files.createFile(mp3Path.toAbsolutePath());
            FileUtils.copyFile(fileToSave, mp3Path.toFile());

        } finally {
            fileToSave.delete();
        }

    }
}
