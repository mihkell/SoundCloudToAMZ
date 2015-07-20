package eu.scamzs3.apps.scamzs3.soundcloud;

import java.net.URI;
import java.util.List;

/**
 * Constructs necessary Classes out of SoundClouds responses.
 * 
 */
public interface SoundCloud {

    List<SCTrack> getTracks();

    void setId(Long id);

    URI getTrackUri(Long trackId);
}
