package exeptions;

import static java.lang.String.format;

public class BrowserNotSupportedException extends RuntimeException {

  public BrowserNotSupportedException(String browser) {
    super(format("Browser not supported", "%s", browser));
  }

}
