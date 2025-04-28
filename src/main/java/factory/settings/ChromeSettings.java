package factory.settings;

import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeSettings implements IBrowserSettings {

  public ChromeOptions settings() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.addArguments("start-maximized");
    return chromeOptions;
  }
}
