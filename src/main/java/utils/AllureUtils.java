package utils;

import io.qameta.allure.Allure;
import java.io.File;
import java.io.FileInputStream;

public class AllureUtils {

  public static void attachVideoToAllure(String videoPath) {
    File videoFile = new File(videoPath);
    if (!videoFile.exists()) {
      throw new RuntimeException("Video file not found: " + videoPath);
    }

    try (FileInputStream fis = new FileInputStream(videoFile)) {
      Allure.addAttachment("Test Video", "video/mp4", fis, ".mp4");
    } catch (Exception e) {
      throw new RuntimeException("Failed to attach video to Allure", e);
    }
  }

}
