package utils;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.monte.media.Format;
import org.monte.screenrecorder.ScreenRecorder;

public class VideoRecorder extends ScreenRecorder {

  private final String name;

  public VideoRecorder(ScreenRecorderConfig config, String name) throws IOException, AWTException {
    super(config.getCfg(),
        config.getCaptureArea(),
        config.getFileFormat(),
        config.getScreenFormat(),
        config.getMouseFormat(),
        config.getAudioFormat(),
        config.getMovieFolder());
    this.name = name + "_" + UUID.randomUUID();
  }

  @Override
  protected File createMovieFile(Format fileFormat) {
    if (!movieFolder.exists()) {
      boolean created = movieFolder.mkdirs();
      if (!created) {
        System.err.println(
            "Warning: Could not create movie folder: " + movieFolder.getAbsolutePath());
      }
    }
    return new File(movieFolder, name + ".avi");
  }

  public String getName() {
    return name;
  }
}
