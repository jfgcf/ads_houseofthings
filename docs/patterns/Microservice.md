# Microservice

## Problems
In order to support the discoverability of new devices we decided to adopt a strategy using microservices in the backend. 
In this new architecture the backend service will receive requests from the frontend and will communicate with the device microservices via HTTP requests.
To be possible to new devices register themselves in the house's network automatically we had to split the backend service 
from the device service as they work are separate processes. We also had to make sure that each device has a single instance
in order to preserve its state.

## Solution


## Structure
