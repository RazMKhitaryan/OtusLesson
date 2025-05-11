Feature: Browser selection

  @courses
  Scenario: Select a random course from training and verify it's displayed on the course page
    Given open the browser "chrome"
    And the main page is opened
    When hover over the training menu
    And click on a random course from training
    Then the course page should display the selected course