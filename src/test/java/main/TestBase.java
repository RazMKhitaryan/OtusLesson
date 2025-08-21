package main;

import factory.WebDriverFactory;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.AllureUtils;
import utils.VideoUtils;

@SpringBootTest(classes = Application.class)
public abstract class TestBase extends AbstractTestNGSpringContextTests {

  private String sessionId;
  private RemoteWebDriver driver;
  @Autowired
  private WebDriverFactory webDriverFactory;

  @BeforeMethod
  public void setUp() throws Exception {
    webDriverFactory.create();
    driver = webDriverFactory.getRawDriver();
    sessionId = driver.getSessionId().toString();
  }

  @AfterMethod
  public void tearDown() {
    try {
      webDriverFactory.killDriver();
      String videoPath = VideoUtils.downloadVideo(sessionId);
      AllureUtils.attachVideoToAllure(videoPath);
      VideoUtils.deleteVideo(driver.getSessionId().toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
