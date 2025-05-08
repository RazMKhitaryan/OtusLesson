Feature: Page catalog courses

  @course
  Scenario: check that course page opened successfully
    Given the courses catalog page opened
    When click on random course
    Then course page should opened successfully