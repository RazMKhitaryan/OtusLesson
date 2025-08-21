package utils;

import io.qameta.allure.Allure;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class AllureUtils {

  public static synchronized void attachLatestTestVideo(String folderPath)
      throws IOException, InterruptedException {
    File folder = new File(folderPath);
    if (!folder.isDirectory()) {
      throw new RuntimeException("Folder not found or not a directory: " + folderPath);
    }

    File latestAvi = Arrays.stream(Objects.requireNonNull(
            folder.listFiles((d, n) -> n.startsWith("Test_") && n.endsWith(".avi"))))
        .max(Comparator.comparingLong(File::lastModified))
        .orElseThrow(() -> new RuntimeException(
            "No video files starting with 'Test_' found in: " + folderPath));

    File mp4File = new File(latestAvi.getParent(), latestAvi.getName().replace(".avi", ".mp4"));

    System.out.println("Converting: " + latestAvi.getName());
    Process process = new ProcessBuilder("ffmpeg", "-i", latestAvi.getAbsolutePath(),
        mp4File.getAbsolutePath())
        .inheritIO()
        .start();

    if (process.waitFor() != 0) {
      throw new RuntimeException("FFmpeg conversion failed for file: " + latestAvi.getName());
    }
    System.out.println("Created: " + mp4File.getName());

    try (FileInputStream fis = new FileInputStream(mp4File)) {
      Allure.addAttachment("Test Video", "video/mp4", fis, ".mp4");
    } catch (Exception e) {
      throw new RuntimeException("Failed to attach video to Allure: " + mp4File.getAbsolutePath(),
          e);
    }
  }
}