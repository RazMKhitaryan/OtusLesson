Feature: Course filtering by start dates

  Scenario: Find courses starting on or after a specific date
    Given open the browser "chrome"
    When the courses page is opened
    Then find courses starting on "24 апреля, 2025" or after