package app.museu.macs.model;

/**
 * Created by jeremias on 24/08/15.
 */
public class GalleryPhoto {
    private String id;
    private String uriPicture;
    private String uriSource;
    private String description;

    public GalleryPhoto(String id, String description, String uriPicture, String uriSource) {
        this.id = id;
        this.description = description;
        this.uriPicture = uriPicture;
        this.uriSource = uriSource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUriPicture() {
        return uriPicture;
    }

    public void setUriPicture(String uriPicture) {
        this.uriPicture = uriPicture;
    }

    public String getUriSource() {
        return uriSource;
    }

    public void setUriSource(String uriSource) {
        this.uriSource = uriSource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
