package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import components.TrainingComponent;
import listeners.MouseListener;
import models.CourseModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import pages.CoursePage;
import pages.CoursesPage;
import pages.MainPage;
import utils.ActionUtils;

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
  public MainPage getMainPage(ActionUtils actionUtils, MouseListener mouseListener) {
    return new MainPage(driver, actionUtils, mouseListener);
  }

  @Provides
  @Singleton
  public CoursesPage getCoursesPage(MouseListener mouseListener) {
    return new CoursesPage(driver, mouseListener);
  }

  @Provides
  @Singleton
  public CoursePage getCoursePage(MouseListener mouseListener) {
    return new CoursePage(driver, mouseListener);
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

  @Provides
  @Singleton
  public ActionUtils getActionUtils(Actions actions) {
    return new ActionUtils(actions);
  }

  @Provides
  @Singleton
  public Actions getActions() {
    return new Actions(driver);
  }

  @Provides
  @Singleton
  public TrainingComponent getTrainingComponent(MouseListener mouseListener) {
    return new TrainingComponent(driver, mouseListener);
  }

}
