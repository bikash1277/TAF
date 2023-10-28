Feature: Test Google Feature File

  Background:

  @Test01
  Scenario Outline: Test Google
    When  User launch https://app.hubspot.com/login on <browser>
    And User close browser

    Examples:
      | browser |
      | chrome  |
      | firefox |
      | Edge    |