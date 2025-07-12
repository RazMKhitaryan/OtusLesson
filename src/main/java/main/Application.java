package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "main",
    "config",
    "components",
    "factory",
    "utils",
    "service",
    "pages",
    "common",
    "annotations",
    "exceptions",
    "listeners",
    "factory.settings"
})
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}