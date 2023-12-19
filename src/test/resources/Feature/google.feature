Feature: Test Google Feature File

  @Test001
  Scenario Outline: Invalid City Weather Scenario
  Verify weather should not be found if user searches with an invalid city name

    Given User launches OpenWeatherMap website in desired <Browser>


    Examples:
      | Browser |
      | Chrome  |
      | Firefox |

@Test002
  Scenario Outline: Valid City Weather Scenario
  Verify weather details should be displayed if user searches with a valid city name

    Given User launches OpenWeatherMap website in desired <Browser>
    When User provides a input as city
    And User clicks on search button
    Then Proper city details should be found
    And Weather details should be updated

    Examples:
      | Browser |
      | Chrome  |
      | Firefox |