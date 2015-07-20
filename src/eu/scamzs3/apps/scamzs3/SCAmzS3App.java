package eu.scamzs3.apps.scamzs3;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import eu.scamzs3.AccessData;
import eu.scamzs3.SaveData;
import eu.scamzs3.apps.scamzs3.soundcloud.SCTrack;
import eu.scamzs3.apps.scamzs3.soundcloud.SoundCloud;
import eu.scamzs3.apps.scamzs3.soundcloud.SoundCloudImp;

public class SCAmzS3App {

    final static Logger logger = Logger.getLogger(SCAmzS3App.class);
    
    private SaveData saveData;
    private AccessData accessData;
    private Scanner scn = new Scanner(System.in);
    private SoundCloud soundCloud = new SoundCloudImp();

    private String fileExtentsion = ".mp3";
    
    public SCAmzS3App (AccessData accessData, SaveData saveData ){
        this.accessData = accessData;
        this.saveData = saveData;
    }

    public void startApp() {
        logger.info("SoundCloud to AMZ app Started! (user id used for testing: 102351326)");
        // Asks ID
        Long userId = askFromClientSoundCloudUserId();

        // Displays list of musics, with id's if it can be downloaded
        List<SCTrack> tracks = getTracks(userId);
        showTrackList(tracks);

        // if link id is inserterd then download and save
        Long trackId = getMp3IdToBeDownloaded();

        File file = getMP3(trackId);
        
        boolean fileSaved = saveFile(file);
        
        if(fileSaved){
            System.out.println("File saved!");
        }else{
            System.out.println("Unable to save file");
        }
        
    }

    private List<SCTrack> getTracks(Long userId) {
        
        soundCloud.setId(userId);
        List<SCTrack> tracks = soundCloud.getTracks();
        
        return tracks;
    }

    private boolean saveFile(File file) {
        try {
            saveData.saveFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private File getMP3(Long trackId) {
        File file = null;
        try {
            file = accessData.getFile(soundCloud.getTrackUri(trackId), Long.toString(trackId), fileExtentsion );
        } catch (IOException e1) {
            System.out.println("Wasn't able to access(download) the file.");
            e1.printStackTrace();
        }
        return file;
    }

    private Long getMp3IdToBeDownloaded() {
        System.out.println("insert the track ID you wish to download:");
        Long trackId = (long) scn.nextInt();
        return trackId;
    }

    private void showTrackList(List<SCTrack> tracks) {
        // Usually I use string builder here, but at this instance JVM will do the job
        // for me.
        System.out.println("*Track ID's will only be shown when tracks can be downloaded");
        String s;
        for (SCTrack track : tracks) {
            s = "";
            s += track.getName();
            s += "; ";
            if (track.isDownloadAble()) {
                s+= "Track id is ";
                s += track.getId();
            }
            System.out.println(s);
        }

    }

    private Long askFromClientSoundCloudUserId() {

        System.out.println("Please insert SoundCloud user id from who you wish to download music:");
        int id = scn.nextInt();

        return (long) id;
    }

    public SaveData getSaveData() {
        return saveData;
    }

    public void setSaveData(SaveData saveData) {
        if(saveData == null){
            return;
        }
        this.saveData = saveData;
    }

    public AccessData getAccessData() {
        return accessData;
    }

    public void setAccessData(AccessData accessData) {
        if(accessData == null){
            return;
        }
        this.accessData = accessData;
    }

}
