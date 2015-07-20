package eu.scamzs3.apps.scamzs3.amzs3;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

import eu.scamzs3.SaveData;

public class AmazonSaveData implements SaveData {

    final static Logger logger = Logger.getLogger(AmazonSaveData.class);

    private String accessKeyId = "";
    private String secretAccessKey = "";
    private String bucketName = "";

    private String filename = "amz.properties";

    public AmazonSaveData ( ){

        setAMZKeysAndBucketName();

    }

    private void setAMZKeysAndBucketName() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = getClass().getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                logger.error("Didn't find properies file for Amazon");
                return;
            }
            prop.load(input);

            // get the property value and print it out
            accessKeyId = prop.getProperty("access_key_id");
            secretAccessKey = prop.getProperty("secret_access_key");
            bucketName = prop.getProperty("bucket_name");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            IOUtils.closeQuietly(input);
        }

    }

    @Override
    public void saveFile(File file) throws IOException {
        saveFile(file, RandomStringUtils.randomAlphanumeric(10));
    }

    @Override
    public void saveFile(File file, String fileName) throws IOException {

        AmazonS3 s3Client = getAmzS3Client();

        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));

        file.delete();

    }

    private AmazonS3 getAmzS3Client() {

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        AmazonS3 s3Client = new AmazonS3Client(awsCreds);
        Region region = Region.getRegion(Regions.EU_WEST_1);
        s3Client.setRegion(region);

        return s3Client;
    }

}
