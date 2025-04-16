package pages;

import com.google.inject.Inject;
import components.TrainingComponent;
import org.openqa.selenium.WebDriver;

public class MainPage extends AbsBasePage {

  private TrainingComponent trainingComponent;

  @Inject
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
