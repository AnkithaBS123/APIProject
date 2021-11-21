Feature: SCI Test Script Demo

  Scenario: Automating SCI

    Given I logged into SCI application "https://www.savethechildren.net/"
    When I select Go to the UK Site button from the popup window
    And I click on search in UK site
    When I search for "How to Donate"
    And I Click on first link appeared in results
    Then it should redirect to make a donation page.