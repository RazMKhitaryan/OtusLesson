package components;

import annotations.Component;
import common.AbsCommon;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import utils.AnnotationUtils;

public abstract class AbsBaseComponent extends AbsCommon<AbsBaseComponent> {

  @Autowired
  private AnnotationUtils annotationUtils;

  private By componentSelector;

  public void postConstructor() {
    initializeSelector();
  }
  public void verifyComponentLoaded() {
    initializeSelector();
    waitUtils.waitTillElementVisible(componentSelector);
  }

  private void initializeSelector() {
    String[] selector = annotationUtils.getAnnotationInstance(this.getClass(), Component.class)
        .value()
        .split(":");

    componentSelector = switch (selector[0].trim()) {
      case "css" -> By.cssSelector(selector[1].trim());
      case "xpath" -> By.xpath(selector[1].trim());
      default -> throw new IllegalArgumentException("Unsupported selector type: " + selector[0]);
    };
  }
}