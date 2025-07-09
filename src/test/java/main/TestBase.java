package main;

import components.HeaderComponent;
import components.TrainingComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import pages.CoursePage;
import pages.CoursesPage;
import pages.MainPage;
import springmvc.SpringMvcApplication;

@SpringBootTest(classes = SpringMvcApplication.class)
public class TestBase extends AbstractTestNGSpringContextTests {

  @Autowired
  protected CoursesPage coursesPage;

  @Autowired
  protected CoursePage coursePage;

  @Autowired
  protected TrainingComponent trainingComponent;

  @Autowired
  protected HeaderComponent headerComponent;

  @Autowired
  protected MainPage mainPage;

}
