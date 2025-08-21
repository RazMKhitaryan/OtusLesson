package factory.settings;

import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeSettings {

  public ChromeOptions settingsAmd64(String testName) {
    ChromeOptions options = new ChromeOptions();

    // Chrome arguments
    options.addArguments("--window-size=1920,1080");
    options.addArguments("--start-maximized");
    options.addArguments("--disable-infobars");
    options.addArguments("--disable-extensions");
    options.addArguments("--disable-notifications");
    options.addArguments("--disable-popup-blocking");

    // Make sure headless is OFF
    // options.addArguments("--headless"); // <-- remove/comment this line if exists

    // Selenoid capabilities
    Map<String, Object> selenoidOptions = new HashMap<>();
    selenoidOptions.put("enableVNC", true);              // for VNC access
    selenoidOptions.put("enableVideo", true);            // enable video recording
    selenoidOptions.put("videoName", testName + ".avi"); // unique video name per test
    selenoidOptions.put("videoScreenSize", "1920x1080");// optional

    options.setCapability("selenoid:options", selenoidOptions);

    return options;
  }
}
