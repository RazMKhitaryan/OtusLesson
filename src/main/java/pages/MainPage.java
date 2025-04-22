package pages;

import org.openqa.selenium.WebDriver;

public class MainPage extends AbsBasePage {

  public MainPage(WebDriver driver) {
    super(driver);
  }

  @Override
  public MainPage open() {
    openPage(getUrl());
    return this;
  }

  @Override
  public String getUrl() {
    return "";
  }

}
