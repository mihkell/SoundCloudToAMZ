package eu.scamzs3.apps.scamzs3.soundcloud;

/**
 * DTO class
 */
public class SCTrack {

    private String name;
    private boolean downloadAble;
    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDownloadAble() {
        return downloadAble;
    }

    public void setDownloadAble(boolean downloadAble) {
        this.downloadAble = downloadAble;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
