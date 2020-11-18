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

### Domain Model

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

### Architecture

- This project is divided into three parts: 
    - Frontend implemented with Java FX. 
      - Source code can be downloaded at https://github.com/kadubarral/houseofthingsfx
    - Backend built as a microservice with a single instance.
    - Device microservice considering that each instance of a device is a microservice.
- The backend part has four packages:
  - <b>launch</b>: Will add the necessary packages and will start Tomcat to listen to API calls.
  - <b>backend</b>: Receives REST APIs requests from the frontend and interacts with the device microservice.
  - <b>common</b>: Groups models and methods that are common to both backend and device services.
  - <b>device</b>: Holds the logic related to devices.

![Diagram](./docs/UML_Package_Diagram.png)

We chose the microservice architecture pattern because we wanted to run multiple instances of the device services at the same time we have a single instance 
of the backend service. In this context each device service represents a single instance of device. 
This project uses Java Rest API with Embedded Tomcat.

![Diagram](./docs/UML_Communication_Diagram.png)

## The Patterns 

### API Gateway
#### Problem in Context
In order to support the discoverability of new devices we decided to adopt a strategy using microservices in the backend. 
In this architecture the backend service receives requests from the frontend and communicates with the device microservices via HTTP requests.
To be possible to new devices register themselves in the house's network automatically we had to split the backend service from the device service
as they work as separate processes. 
#### The Pattern
The backend service acts like an API Gateway since is the single point of entry for the device microservices calls.

##### Implementation
We decomposed the backend in services considering its capabilities.
- The backend service's main capability is to receive requests from the frontend and transform the data in a type of model the device service recognizes. 
It will later communicate with the device service via HTTP/REST protocol. 
- The device service's main capability is to contain the business logic concerning the program functionalities.
- For the classes that are common to these services we grouped them in a package. When the program starts the common package is added to the server 
and becomes available to different services.

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
![alt text](./docs/patterns/DataTransferSnippet1.png)

###### Home
![alt text](./docs/patterns/DataTransferSnippet2.png)
###### Devices Controller
![alt text](./docs/patterns/DataTransferSnippet3.png)
#### Consequences
- Reduces code duplication since the behavior behind these objects is only in device microservice and is not duplicated in backend microservice.
- Simplifies remote object and remote interface so the frontend calls can only deal with simplified objects.

### Model-View-Controller (MVC)

#### Problem in Context 
In this application MVC design pattern helped a lot because it's a easy way to communicate with the view, it separates the representation of information from the logic used to obtain and manipulate the content.

#### The Pattern
MVC patterns separate the input, processing, and output of an application. This model divided into three interconnected called model, the view, and the controller. 
All of the three above given components are built to handle some specific development aspects of any web or.net application development.

#### Implementation

###### Device.java
![alt text](./docs/patterns/MVC_model.png)

###### DeviceStatusView
![alt text](./docs/patterns/MVC_view.png)

###### DeviceController
![alt text](./docs/patterns/MVC_controller.png)

#### Consequences 

- Faster development process.
- Ability to provide multiple views.
- The modification does not affect the entire model.


### Template Method
#### Problem in Context
On House of Things the devices receive updates from the sensors. As there are several sensors types, we need an abstract class that can be adapted to read from different kind of sensors.

#### The Pattern 
Template Method is a behavioural design pattern that defines the skeleton of an algorithm in the superclass but lets subclasses override specific steps of the algorithm without changing its structure.

#### Implementation

![alt text](./docs/patterns/ImplementationTemplate.png)

###### Actuator.Java
![alt text](./docs/patterns/Actuator.Java.png)

###### AirConditioner.Java
![alt text](./docs/patterns/AirConditioner.java.png)

###### Door.Java
![alt text](./docs/patterns/Door.Java.png)

#### Consequences 
The Actuator.class is an abstract class containing the skeleton. The algorithm to update a sensor reading is defined in the Actuator.class. But the subclasses (AirConditioner.class and Door.class) have different individual operations, the way the individual’s operations are performed vary on the subclasses.

The template method is used for the following reasons:
- Let subclasses implement varying behaviour (through “@Overriding”)
- Avoid duplication in the code, the skeleton is implemented once in the abstract class’s algorithm, and variations are defined in the subclasses.
- Control at what points subclassing is allowed. As opposed to a simple polymorphic override, where the base method would be entirely rewritten allowing radical change to the workflow, only the specific details of the workflow are allowed to change.



### Team Members
| Nome | Contato |
| ------ | ------ |
|Diana Mourão | up202002016@fe.up.pt |
|João Fernandes | up202003038@fe.up.pt |
|Kadu Barral | up202000017@fe.up.pt |
|Priscilla Melin | up201900048@fe.up.pt |
