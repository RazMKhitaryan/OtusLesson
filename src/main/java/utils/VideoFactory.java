package utils;

import static org.monte.media.AudioFormatKeys.EncodingKey;
import static org.monte.media.AudioFormatKeys.FrameRateKey;
import static org.monte.media.AudioFormatKeys.KeyFrameIntervalKey;
import static org.monte.media.AudioFormatKeys.MIME_AVI;
import static org.monte.media.AudioFormatKeys.MediaType;
import static org.monte.media.AudioFormatKeys.MediaTypeKey;
import static org.monte.media.AudioFormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.QualityKey;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import org.monte.media.Format;
import org.monte.media.math.Rational;

public class VideoFactory {

  public static VideoRecorder createRecorder() throws Exception {
    System.out.println("Headless? " + GraphicsEnvironment.isHeadless());
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    System.out.println("Screen: " + gd.getIDstring());

    GraphicsConfiguration gc = GraphicsEnvironment
        .getLocalGraphicsEnvironment()
        .getDefaultScreenDevice()
        .getDefaultConfiguration();

    Rectangle captureSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

    Format fileFormat = new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI);

    Format screenFormat = new Format(
        MediaTypeKey, MediaType.VIDEO,
        EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
        CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
        DepthKey, 24, FrameRateKey, Rational.valueOf(15),
        QualityKey, 1.0f,
        KeyFrameIntervalKey, 15 * 60
    );

    Format mouseFormat = new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black",
        FrameRateKey, Rational.valueOf(30));

    Format audioFormat = null; // no audio

    File movieFolder = new File("target/videos");

    ScreenRecorderConfig config = new ScreenRecorderConfig(gc, captureSize, fileFormat,
        screenFormat, mouseFormat, audioFormat, movieFolder);

    return new VideoRecorder(config, "Test");
  }
}
