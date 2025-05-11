Feature: Page catalog course

  @course
  Scenario: check that course page opened successfully
    Given I open the browser "chrome"
    And the courses catalog page opened
    When click on "BI-аналитика" course
    Then course page should opened successfully