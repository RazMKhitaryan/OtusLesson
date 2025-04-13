package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import listeners.MouseListener;
import models.CourseModel;
import org.openqa.selenium.WebDriver;
import pages.CoursePage;
import pages.CoursesPage;
import pages.MainPage;

public class GuicePagesModule extends AbstractModule {

  private WebDriver driver;

  public GuicePagesModule(WebDriver driver) {
    this.driver = driver;
  }

  @Provides
  public WebDriver getDriver() {
    return driver;
  }

  @Provides
  @Singleton
  public MainPage getMainPage() {
    return new MainPage(driver);
  }

  @Provides
  @Singleton
  public CoursesPage getCoursesPage(CoursePage coursePage, CourseModel courseModel) {
    return new CoursesPage(driver, coursePage);
  }

  @Provides
  @Singleton
  public CoursePage getCoursePage() {
    return new CoursePage(driver);
  }

  @Provides
  @Singleton
  public MouseListener getMouseListener() {
    return new MouseListener(driver);
  }

  @Provides
  @Singleton
  public CourseModel getCourseModel() {
    return new CourseModel();
  }
}
