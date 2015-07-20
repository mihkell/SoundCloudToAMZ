package eu.scamzs3.apps.scamzs3.soundcloud;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SoundCloudServiceImpl implements SoundCloudService {

    final static Logger logger = Logger.getLogger(SoundCloudServiceImpl.class);

    private final String TRACK_ID_PLACEHOLDER = "%TRACK_ID%";
    private final String USER_ID_PLACEHOLDER = "%USER_ID%";
    private static final String CLIENT_ID = "9651e6c0ffc6a4d3644af59e8c54cadc";
    private String soundCloudTrackURL = "https://api.soundcloud.com/tracks/" + TRACK_ID_PLACEHOLDER
            + "/stream?client_id=" + CLIENT_ID;
    private String soundCloudTracksListURL = "https://api.soundcloud.com/users/" + USER_ID_PLACEHOLDER
            + "/tracks?client_id=" + CLIENT_ID;

    @Override
    public List<SCTrack> getTracksJson(Long userId) {

        String responseString = getJsonString(userId);
        List<SCTrack> tracks = getResponseAsTracks(responseString);

        return tracks;
    }

    private List<SCTrack> getResponseAsTracks(String responseString) {

        JsonParser parser = new JsonParser();
        JsonElement responseJson = parser.parse(responseString);
        JsonArray trackJsonArray = responseJson.getAsJsonArray();

        JsonElement array_element;
        JsonObject trackJson;

        List<SCTrack> tracks = new ArrayList<>();
        SCTrack track;

        for (int i = 0; i < trackJsonArray.size(); i++) {
            array_element = trackJsonArray.get(i);
            trackJson = array_element.getAsJsonObject();
            track = new SCTrack();
            track.setName(trackJson.get("title").getAsString());
            track.setDownloadAble(trackJson.get("downloadable").getAsBoolean());
            track.setId(trackJson.get("id").getAsLong());
            tracks.add(track);
        }

        return tracks;
    }

    private String getJsonString(Long userId) {
        URI url = getTraksUri(userId);

        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        CloseableHttpClient httpClient = clientBuilder.build();
        HttpGet get = new HttpGet(url);

        String responseString = "";
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(get);
            HttpEntity entity = httpResponse.getEntity();
            responseString = EntityUtils.toString(entity, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseString;
    }

    private URI getTraksUri(Long userId) {
        String scTrackUrl = soundCloudTracksListURL.replaceFirst(USER_ID_PLACEHOLDER, Long.toString(userId));
        return URI.create(scTrackUrl);
    }

    @Override
    public URI getTrackURL(Long trackId) {
        String scTrackUrl = soundCloudTrackURL.replaceFirst(TRACK_ID_PLACEHOLDER, Long.toString(trackId));
        return URI.create(scTrackUrl);
    }

}
