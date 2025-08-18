package utils;

import java.io.File;
import java.util.UUID;

public class VideoUtils {

  private static final ThreadLocal<String> VIDEO_NAME = new ThreadLocal<>();

  /** Generate and store video name for current thread */
  public static String generateVideoName() {
    String name = "video-" + UUID.randomUUID() + ".mp4";
    VIDEO_NAME.set(name);
    return name;
  }

  /** Get stored video name for current thread */
  public static String getVideoName() {
    return VIDEO_NAME.get();
  }

  /** Full path to video file and create folder if missing */
  public static String getVideoPath() {
    File dir = new File("target/videos");
    if (!dir.exists()) {
      dir.mkdirs(); // create folder if missing
    }
    return "target/videos/" + getVideoName();
  }

  /** Clear ThreadLocal */
  public static void clear() {
    VIDEO_NAME.remove();
  }
}
