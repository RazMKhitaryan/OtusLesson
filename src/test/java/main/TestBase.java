package main;

import factory.WebDriverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.AllureUtils;
import utils.VideoUtils;

@SpringBootTest(classes = Application.class)
public abstract class TestBase extends AbstractTestNGSpringContextTests {

  @Autowired
  private WebDriverFactory webDriverFactory;

  @BeforeMethod
  public void setUp() throws Exception {
    webDriverFactory.create();
  }

  @AfterMethod
  public void tearDown() {
    try {
      webDriverFactory.killDriver();
      String videoPath = VideoUtils.getVideoPath();
      AllureUtils.attachVideoToAllure(videoPath);
      VideoUtils.clear();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
