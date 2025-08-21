package utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class VideoUtils {

  private static final String SELENOID_URL = "http://45.132.17.22:4444";
  private static final String VIDEO_DIR = System.getProperty("video.dir");

  /**
   * Downloads video file from Selenoid
   */
  public static String downloadVideo(String sessionId) throws IOException {
    String videoUrl = SELENOID_URL + "/video/" + sessionId + ".mp4";
    Files.createDirectories(Paths.get(VIDEO_DIR));

    URL url = new URL(videoUrl);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");

    if (conn.getResponseCode() == 200) {
      try (InputStream in = conn.getInputStream();
          FileOutputStream out = new FileOutputStream(VIDEO_DIR + "/" + sessionId + ".mp4")) {
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
          out.write(buffer, 0, bytesRead);
        }
        System.out.println("✅ Video downloaded: " + VIDEO_DIR + "/" + sessionId + ".mp4");
      }
    } else {
      System.err.println("❌ Failed to download video. HTTP code: " + conn.getResponseCode());
    }
    return VIDEO_DIR + "/" + sessionId + ".mp4";
  }

  /**
   * Deletes video from Selenoid after downloading
   */
  public static void deleteVideo(String sessionId) throws IOException {
    String deleteUrl = SELENOID_URL + "/video/" + sessionId + ".mp4";

    URL url = new URL(deleteUrl);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("DELETE");

    if (conn.getResponseCode() == 200) {
      System.out.println("✅ Video deleted from Selenoid: " + sessionId + ".mp4");
    } else {
      System.err.println("❌ Failed to delete video. HTTP code: " + conn.getResponseCode());
    }
  }

}
