package client;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Props {
    private double downloadedSizeProperty = 0;
    private double downloadedFilesNumberProperty = 0;
    private String size = "";
    private String downloaded = "";

    public Props(double downloadedSizeProperty, double downloadedFilesNumberProperty, String size, String downloaded) {
        this.downloadedSizeProperty = downloadedSizeProperty;
        this.downloadedFilesNumberProperty = downloadedFilesNumberProperty;
        this.size = size;
        this.downloaded = downloaded;
    }

    public Props( String size, String downloaded) {
;
        this.size = size;
        this.downloaded = downloaded;
    }


    public double getDownloadedSizeProperty() {
        return downloadedSizeProperty;
    }

    public void setDownloadedSizeProperty(double downloadedSizeProperty) {
        this.downloadedSizeProperty = downloadedSizeProperty;
    }

    public double getDownloadedFilesNumberProperty() {
        return downloadedFilesNumberProperty;
    }

    public void setDownloadedFilesNumberProperty(double downloadedFilesNumberProperty) {
        this.downloadedFilesNumberProperty = downloadedFilesNumberProperty;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(String downloaded) {
        this.downloaded = downloaded;
    }
}
