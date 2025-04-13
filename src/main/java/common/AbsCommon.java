package common;

import org.openqa.selenium.WebDriver;

public abstract class AbsCommon {

  protected WebDriver driver;

  public AbsCommon(WebDriver driver) {
    this.driver = driver;
  }
}
