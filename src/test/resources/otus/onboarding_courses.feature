Feature: Page catalog course

  @course
  Scenario: check that cheapest and expensive courses are in page
    Given I open the browser "chrome"
    When the courses catalog page opened
    And click on Подготовительные курсы
    Then find cheapest and expensive courses
