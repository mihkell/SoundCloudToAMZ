package eu.scamzs3.apps.scamzs3.soundcloud;

import java.net.URI;
import java.util.List;


public class SoundCloudImp implements SoundCloud {

    private Long userId;
    private SoundCloudService soundCloudService = new SoundCloudServiceImpl();

    @Override
    public void setId(Long userId) {
        this.userId = userId;
    }

    @Override
    public List<SCTrack> getTracks() {
        List<SCTrack> tracks = soundCloudService.getTracksJson(userId);

        return tracks;
    }

    @Override
    public URI getTrackUri(Long trackId) {
        return soundCloudService.getTrackURL(trackId);
    }

}
