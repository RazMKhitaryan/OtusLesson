package factory.settings;

import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;
import java.util.Map;

public class ChromeSettings implements IBrowserSettings {

  public ChromeOptions settingsAmd64() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("start-maximized");
    Map<String, Object> selenoidOptions = new HashMap<>();
    selenoidOptions.put("enableVNC", true);
    options.setCapability("selenoid:options", selenoidOptions);
    return options;
  }
}
