@Example
Feature: Second Feature that show how does that framework works

@OpenPageExample7
Scenario: Open rambler page 7
Given I open Rambler Page
When I simulate work process
Then I check that page is opened

@OpenPageExample8
Scenario: Open rambler page 8
Given I open Rambler Page
When I simulate work process
Then I check that page is opened

@OpenPageExample9
Scenario: Open rambler page 9
Given I open Rambler Page
When I simulate work process
Then I check that page is opened

@OpenPageExample10
Scenario: Open rambler page 10
Given I open Rambler Page
When I simulate work process
Then I check that page is opened

@OpenPageExample11
Scenario: Open rambler page 11
Given I open Rambler Page
When I simulate work process
Then I check that page is opened

@OpenPageExample12
Scenario: Open rambler page 12
Given I open Rambler Page
When I simulate work process
And I mark step as pending
Then I check that page is opened

@OpenPageExample13
Scenario: Open rambler page 13
Given I open Rambler Page
When I simulate work process
And I fail scenario
Then I check that page is opened