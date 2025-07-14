package pages;

import annotations.Path;
import common.AbsCommon;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import utils.AnnotationUtils;

public abstract class AbsBasePage extends AbsCommon<AbsBasePage> {

  @Value("${baseUrl:https://otus.ru}")
  private String baseUrl;

  @Autowired
  private AnnotationUtils annotationUtils;

  private String pagePath;

  @PostConstruct
  public void postConstructor() {
    initPages();
    initializePath();
  }

  private void initializePath() {
    pagePath = annotationUtils.getAnnotationInstance(this.getClass(), Path.class).value();
  }

  public void openPage() {
    webDriverFactory.getDriver().get(baseUrl + pagePath);
    addCookie();
    webDriverFactory.getDriver().navigate().refresh();
    waitUtils.waitForPageLoad();
    initPages();
  }
}