package eu.scamzs3.apps.scamzs3.soundcloud;

import java.net.URI;
import java.util.List;

/**
 * SoundCloud API interface for our app.
 * If something changes in the SoundCloud API this is the class that should
 * change.
 * 
 * @author mihkel
 * 
 */
public interface SoundCloudService {

    URI getTrackURL(Long trackId);

    List<SCTrack> getTracksJson(Long userId);
}
