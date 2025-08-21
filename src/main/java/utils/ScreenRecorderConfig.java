package utils;

import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.io.File;
import org.monte.media.Format;

public class ScreenRecorderConfig {

  private final GraphicsConfiguration cfg;
  private final Rectangle captureArea;
  private final Format fileFormat;
  private final Format screenFormat;
  private final Format mouseFormat;
  private final Format audioFormat;
  private final File movieFolder;

  public ScreenRecorderConfig(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat,
      Format screenFormat, Format mouseFormat, Format audioFormat, File movieFolder) {
    this.cfg = cfg;
    this.captureArea = new Rectangle(captureArea); // defensive copy
    this.fileFormat = fileFormat;
    this.screenFormat = screenFormat;
    this.mouseFormat = mouseFormat;
    this.audioFormat = audioFormat;
    this.movieFolder = movieFolder;

    // Ensure movie folder exists (log a warning instead of throwing)
    if (!movieFolder.exists() && !movieFolder.mkdirs()) {
      System.err.println("Warning: Could not create movie folder: " + movieFolder.getAbsolutePath());
    }
  }

  public GraphicsConfiguration getCfg() {
    return cfg;
  }

  public Rectangle getCaptureArea() {
    return new Rectangle(captureArea); // return a copy to avoid external mutation
  }

  public Format getFileFormat() {
    return fileFormat;
  }

  public Format getScreenFormat() {
    return screenFormat;
  }

  public Format getMouseFormat() {
    return mouseFormat;
  }

  public Format getAudioFormat() {
    return audioFormat;
  }

  public File getMovieFolder() {
    return movieFolder;
  }
}
