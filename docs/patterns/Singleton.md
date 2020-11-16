# Singleton


--------------The description of the design context and the concrete problem that motivated the instantiation of the pattern. 


## Problems
A device microservice has several operations relevant to a single device. This device has a state to affect the way it functions.
So we needed to make sure we can easily manage its state in a centralized manner.


## Solution
In this architecture we use the singleton design pattern to make sure each device is a single instance of the device microservice. In the class 
responsible for creating the server's device we have a private and static attribute that is instantiated in a method that checks
if this attribute is null before instantiating it. Once this device object is instanciated once
it won't be instantiated again.


## Structure
