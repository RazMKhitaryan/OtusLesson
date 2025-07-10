package pages;

import annotations.Path;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Path("/")
public class MainPage extends AbsBasePage {
  @Autowired
  public MainPage(WebDriver driver) {
    super(driver);
  }

}
