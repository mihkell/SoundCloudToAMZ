package eu.scamzs3;

import java.util.Scanner;

import eu.scamzs3.apps.scamzs3.SCAmzS3App;
import eu.scamzs3.apps.scamzs3.amzs3.AmazonSaveData;
import eu.scamzs3.apps.scamzs3.soundcloud.SoundCloudAccessData;
import eu.scamzs3.apps.scamzs3.soundcloud.SoundCloudSaveData;

public class Main {

    public static void main(String[] args) {
        
        
        
        Scanner scn = new Scanner(System.in);
        System.out.println("Save to Amazone? y/n");
        String yOrN = scn.next("y|n");
        
        System.out.println("");
        
        if("y".equals(yOrN)){
            runSoundCloudToAmazon();
        }else if("n".equals(yOrN)){
            runSoundCloudToFile();
        }
        scn.close();
        
    }

    private static void runSoundCloudToFile() {

        SCAmzS3App scAmzS3App = new SCAmzS3App(new SoundCloudAccessData(), new SoundCloudSaveData());
        scAmzS3App.startApp();
        
    }

    private static void runSoundCloudToAmazon() {
        SCAmzS3App scAmzS3App = new SCAmzS3App(new SoundCloudAccessData(), new AmazonSaveData());
        scAmzS3App.startApp();
        
    }
}
