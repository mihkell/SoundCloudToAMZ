package eu.scamzs3.apps.scamzs3.soundcloud;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import eu.scamzs3.AccessData;

public class SoundCloudAccessData implements AccessData {

    final static Logger logger = Logger.getLogger(SoundCloudAccessData.class);

    @Override
    public File getFile(URI url, String fileName, String fileExtentsion) throws IOException {
        InputStream content = null;
        OutputStream outStream = null;
        File file;
        try {
            content = getInputStream(url);
            outStream = getOuputStream(content);
            file = getTempFile(outStream, fileName, fileExtentsion);
        } finally {
            IOUtils.closeQuietly(content);
            IOUtils.closeQuietly(outStream);
        }
        return file;
    }

    private InputStream getInputStream(URI url) {
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        CloseableHttpClient httpClient = clientBuilder.build();

        HttpGet get = new HttpGet(url);
        InputStream content = null;

        try {
            CloseableHttpResponse httpResponse = httpClient.execute(get);
            HttpEntity entity = httpResponse.getEntity();
            content = entity.getContent();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    private File getTempFile(OutputStream outputStream, String fileName, String fileExtentsion) throws IOException {

        File file = File.createTempFile(fileName, fileExtentsion);
        file.deleteOnExit();

        OutputStream fileOutputStream = new FileOutputStream(file);
        ((ByteArrayOutputStream) outputStream).writeTo(fileOutputStream);

        return file;
    }

    private OutputStream getOuputStream(InputStream inputStream) throws IOException {
        OutputStream outputStream = new ByteArrayOutputStream();
        IOUtils.copy(inputStream, outputStream);

        return outputStream;
    }

}
