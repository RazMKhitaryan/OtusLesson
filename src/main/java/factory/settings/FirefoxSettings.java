package factory.settings;

import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxSettings {

  public FirefoxOptions settings() {
    FirefoxOptions options = new FirefoxOptions();

    // Browser-specific prefs and args
    options.addPreference("dom.webnotifications.enabled", false);
    options.addArguments("--no-sandbox");

    // Session-level capabilities
    options.setCapability("browserVersion", "latest");
    options.setCapability("platformName", "linux");
    options.setCapability("acceptInsecureCerts", true);

    return options;
  }
}
