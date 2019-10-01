# screenshot-as-service
Bunch of micro services which are written in Java to implement screenshot as a service

This repository serves services relates to screenshot as service which is written in Java and Spring boot framework to demostrate the concepts and best practices of micro service pattern.

## Technology Stack
- Java 8
- Spring Boot|Clould
- JMS queues
- H2 DB
- Chorme headless driver

## Use Cases

- As a user should be able to execute the endpoint with list of URL(s).Then Service should take the screenshots of those URL(s) and save data into the database.
- Later on user should be able to view the image(.png) of the URL provided.

## Application Architecture

- Application contains three services which are ,
  
  - API server - Acts as service registry(http://localhost:8761/).
  - Screenshot service - Responsible for taking screenshot(s) of given URL(s) and post data into database service.
  - Database service -  Responsible for persisting data.
  
  
## API Specification

 - Invoke screenshot service to take the screenshot(s) of given URL(s) 

[!POST

 - http://localhost:8081/api/screenshot
   {
	"urls":["https://github.com/"]
   }
 
 - Retrive the screenshot of a given URL as .png file
 
[!GET
   
 - http://localhost:8081/api/screenshot?url=https://github.com/