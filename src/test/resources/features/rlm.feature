Feature: Recommerce Flows

    #static data
  @test
  Scenario: user login
    Given user navigated to login page
    When user enters "cucumber" and "12345"

    #with dataTable
  @test
  Scenario: login to the application
    Given user navigated to login page
    When user enters username and password
      | username      | password      |
      | {ab.username} | {ab.password} |

    Scenario: Generate OrderNumber
      Given fetch order number