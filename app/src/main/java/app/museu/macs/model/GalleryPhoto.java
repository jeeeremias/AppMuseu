package app.museu.macs.model;

/**
 * Created by jeremias on 24/08/15.
 */
public class GalleryPhoto {
    private String id;
    private String uri;
    private String description;

    public GalleryPhoto(String id, String uri, String description) {
        this.id = id;
        this.description = description;
        this.uri = uri;
    }

    public GalleryPhoto() {
        
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
