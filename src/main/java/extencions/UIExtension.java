package extencions;

import com.google.inject.Guice;
import com.google.inject.Injector;
import factory.WebDriverFactory;
import modules.GuicePagesModule;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import pages.CoursesPage;

public class UIExtension implements BeforeEachCallback, AfterEachCallback {

  private Injector injector = null;

  @Override
  public void afterEach(ExtensionContext context) {
    WebDriver driver = injector.getInstance(WebDriver.class);
    new CoursesPage(driver).open(); // возврат страницы в исходное состояние
    if (driver != null) {
      driver.quit();
    }
  }

  @Override
  public void beforeEach(ExtensionContext context) {
    WebDriver driver = new WebDriverFactory().create();
    driver.manage().window().maximize();
    injector = Guice.createInjector(new GuicePagesModule(driver));
    injector.injectMembers(context.getTestInstance().get());
  }
}
