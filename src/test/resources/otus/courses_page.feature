Feature: Browser selection

  @courses
  Scenario: Select a random course from training and verify it's displayed on the course page
    Given I open the browser "chrome"
    And the main page is opened
    When I hover over the training menu
    And I click on a random course from training
    Then the course page should display the selected course