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
    // Selenoid capabilities
    Map<String, Object> selenoidOptions = new HashMap<>();
    selenoidOptions.put("enableVNC", true);
    options.setCapability("selenoid:options", selenoidOptions);

    return options;
  }
}
