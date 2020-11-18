# Microservice

## Problem in Context
In order to support the discoverability of new devices we decided to adopt a strategy using microservices in the backend. 
In this architecture the backend service receives requests from the frontend and communicates with the device microservices via HTTP requests.
To be possible to new devices register themselves in the house's network automatically we had to split the backend service from the device service
as they work as separate processes. 

## The Architecture
We chose the microservice architecture pattern because we wanted to run multiple device services and at the same time we have a single instance 
of the backend service. In this context each device service represents a single instance of device.

## The Patterns 

### API Gateway
The backend service acts like an API Gateway since is the single point of entry for the device microservices calls.

## Implementation
We decomposed the backend in services considering its capabilities. Each service contains a model, controller and view packages.
- The backend service's main capability is to receive requests from the frontend and transform the data in a type of model the device service recognizes. 
It will later communicate with the device service via HTTP/REST protocol. 
- The device service's main capability is to contain the business logic concerning the program functionalities.
- For the classes that are common to these services we grouped them in a package. When the program starts the common package is added to the server 
and becomes available to different services.

## Consequences