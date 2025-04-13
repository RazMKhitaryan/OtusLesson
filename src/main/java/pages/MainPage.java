package pages;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;

public class MainPage extends AbsBasePage {

  @Inject
  public MainPage(WebDriver driver) {
    super(driver);
  }

  @Override
  public AbsBasePage open() {
    return null;
  }

  @Override
  public String getUrl() {
    return "";
  }

}
