package pages;

import annotations.Path;
import com.google.inject.Inject;
import scope.ScenScoped;

@Path("/")
public class MainPage extends AbsBasePage {

  @Inject
  public MainPage(ScenScoped scenScoped) {
    super(scenScoped.getDriver());
  }

}
