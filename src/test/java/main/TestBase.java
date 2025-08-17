package main;

import factory.WebDriverFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.AllureUtils;
import utils.VideoUtils;

public abstract class TestBase {

  protected WebDriverFactory webDriverFactory = new WebDriverFactory();

  @BeforeMethod
  public void setUp() throws Exception {
    webDriverFactory.create();
  }

  @AfterMethod
  public void tearDown() {
    try {
      String videoPath = VideoUtils.getVideoPath();
      AllureUtils.attachVideoToAllure(videoPath);
      webDriverFactory.killDriver();
      VideoUtils.clear();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
