package factory.settings;

import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeSettings implements IBrowserSettings {

  public ChromeOptions settings() {
    return new ChromeOptions();
  }
}
