package components;

import common.AbsCommon;
import listeners.MouseListener;
import org.openqa.selenium.WebDriver;

public class AbsComponent extends AbsCommon {
  public AbsComponent(WebDriver driver, MouseListener mouseListener) {
    super(driver, mouseListener);
  }
}
