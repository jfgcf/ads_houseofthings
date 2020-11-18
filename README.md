# House of Things

### Introduction

A smart home is a residence that uses internet-connected devices to enable the remote monitoring and management of appliances and systems, such as lighting and heating.

### Goals

To develop a software system to monitor, control, and manage home automation devices and activities. The system should:

- Support multiple devices, including different sensors and actuators
  - Sensors collect data from the world
  - Actuators interact with the world, moving and controlling a mechanism or system
- Support easily adding new kinds of devices
- Support adding triggers and actions through the user interface
- Be possible to configure the system visually
- Support discoverability of new devices (i.e., plug and play)
- Be easy to integrate with well-known systems, such as SMS, Slack, WhatsApp, and other communication systems

### Prerequisites

- Java version 14 and Maven.
  - Make sure to set Java 14 SDK in IntelliJ. More information here if needed: https://crunchify.com/intellij-idea-how-to-set-latest-java-sdk-and-fix-an-error-errorjava-error-release-version-14-not-supported/

### Architecture

- This project is divided into three parts: 
    - Frontend implemented with Java FX. 
    - Backend built as a microservice with a single instance.
    - Device microservice considering that each instance of a device is a microservice.
- The backend part has four packages:
  - <b>launch</b>: Will add the necessary packages and will start Tomcat to listen to API calls.
  - <b>backend</b>: Receives REST APIs requests from the frontend and interacts with the device microservice.
  - <b>common</b>: Groups models and methods that are common to both backend and device services.
  - <b>device</b>: Holds the logic related to devices.

![Diagram](./docs/UML_Package_Diagram.png)

We chose the microservice architecture style because we wanted to run multiple instances of the device services at the same time we have a single instance 
of the backend service. In this context each device service represents a single instance of device. 
This project uses Java Rest API with Embedded Tomcat.

### Building

The command below builds the project and must be executed after every change.

```bash
mvn package
```

#### Backend microservice

This starts the backend listening to port 8080.

```bash
# On linux / mac:

SERVER_TYPE=backend ENDPOINT=http://localhost:8080  bash target/bin/webapp

# on windows

set SERVER_TYPE=backend 
set ENDPOINT=http://localhost:8080
cd target/bin/webapp
webapp.bat
```

#### Device microservice

This starts a temperature sensor listening to port 8081.

```bash
# On linux / mac:

SERVER_TYPE=device \
    ENDPOINT=http://localhost:8081 \
    DEVICE_NAME=tempSensor1 \
    DEVICE_TYPE=TEMPERATURE_SENSOR \
    BACKEND_ENDPOINT=http://localhost:8080 \
    bash target/bin/webapp

# on windows

set SERVER_TYPE=device 
set ENDPOINT=http://localhost:8081
set DEVICE_NAME=tempSensor1
set DEVICE_TYPE=TEMPERATURE_SENSOR
set BACKEND_ENDPOINT=http://localhost:8080
cd target/bin/webapp
webapp.bat
```

This starts an air conditioner listening to port 8082.

```bash
# On linux / mac:

SERVER_TYPE=device \
    ENDPOINT=http://localhost:8082 \
    DEVICE_NAME=aircon1 \
    DEVICE_TYPE=AIR_CONDITIONER \
    BACKEND_ENDPOINT=http://localhost:8080 \
    MODE=COOL \
    TARGET_TEMPERATURE=20 \
    bash target/bin/webapp

# on windows

set SERVER_TYPE=device 
set ENDPOINT=http://localhost:8082
set DEVICE_NAME=aircon1
set DEVICE_TYPE=AIR_CONDITIONER
set BACKEND_ENDPOINT=http://localhost:8080
set MODE=COOL
set TARGET_TEMPERATURE=20
cd target/bin/webapp
webapp.bat
```

### API Documentation

#### Backend microservice APIs

1. `GET /devices` - Lists the current devices.
2. `POST /devices` - Adds a new device.
3. `PUT /devices` - Edits a device.


#### Device microservice APIs

1. `GET /device` - Returns the details of the device
2. `POST /device/reading` - Sends an array of updated device readings to allow the device to react if necessary
3. `PUT /device` - Edits a device

## The Patterns 

### API Gateway
#### Problem in Context
In order to support the discoverability of new devices we decided to adopt a strategy using microservices in the backend. 
To be possible to new devices register themselves in the house's network automatically we had to split the backend service 
from the device service as they work as separate processes. 

We wanted to concentrate all frontend requests in the backend
and make the backend communicates with the device microservices via HTTP/REST requests so that the client won't need to handle
multiple calls to microservices endpoints.

#### The Pattern
The backend service acts like an API Gateway since is the single point of entry for the device microservices calls. It acts
as a reverse proxy, routing requests from clients to services.

#### Implementation
Frontend requests connect to a single endpoint, the backend service (acts like an API Gateway) that's configured to forward 
requests to device microservices.

##### UML
![Diagram](./docs/patterns/API_Gateway.png)

#### Implementation
###### Controller in the backend service
![alt text](./docs/patterns/APIGatewaySnippet1.png)
###### Service in the backend service calls controller in device service
![alt text](./docs/patterns/APIGatewaySnippet2.png)
###### Controller in the device service
![alt text](./docs/patterns/APIGatewaySnippet3.png)
#### Consequences
- Insulates the client from the problem of determining the locations of service instances.
- Simplifies the client by moving logic for calling multiple services from the client to API Gateway.
- Increase in code complexity.

### Transfer Object Pattern
#### Problem in Context
In this application the backend microservice receives requests from the frontend and creates requests to each instance of the device 
microservice. The business logic is in the device microservice and is not needed in the backend service. 

Since the backend service only transfers the data to the device service we needed a way to create an object that could attend 
the frontend needs without replicating code or exposing the business logic in the backend service.

#### The Pattern
The data transfer object pattern is a POJO class having only getter/setter methods and is serializable so that it can be transferred
over the network. It's basically used to carry data between processes.

#### Implementation

##### UML

![alt text](./docs/patterns/DataTransferObject.png)

###### DeviceDTO
![alt text](./docs/patterns//DataTransferSnippet1.png)

###### Home
![alt text](./docs/patterns/DataTransferSnippet2.png)
###### Devices Controller
![alt text](./docs/patterns/DataTransferSnippet3.png)
#### Consequences
- Reduces code duplication since the behavior behind these objects is only in device microservice and is not duplicated in backend microservice.
- Simplifies remote object and remote interface so the frontend calls can only deal with simplified objects.

### Template Method
#### Problem in Context

### Team Members
| Nome | Contato |
| ------ | ------ |
|Diana Mourão | up202002016@fe.up.pt |
|João Fernandes | up202003038@fe.up.pt |
|Kadu Barral | up202000017@fe.up.pt |
|Priscilla Melin | up201900048@fe.up.pt |
