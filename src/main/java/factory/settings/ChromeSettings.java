package factory.settings;

import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.VideoUtils;

public class ChromeSettings {

  public ChromeOptions settingsAmd64() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--window-size=1920,1080");
    options.addArguments("--start-maximized");
    options.addArguments("--disable-infobars");
    options.addArguments("--disable-extensions");
    options.addArguments("--disable-notifications");
    options.addArguments("--disable-popup-blocking");

    Map<String, Object> selenoidOptions = new HashMap<>();
    selenoidOptions.put("enableVNC", true);
    selenoidOptions.put("enableVideo", true);

    String videoName = VideoUtils.generateVideoName();
    selenoidOptions.put("videoName", videoName);

    options.setCapability("selenoid:options", selenoidOptions);
    return options;
  }
}
