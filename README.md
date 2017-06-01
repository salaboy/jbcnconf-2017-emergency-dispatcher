# jbcnconf-2017-health-care
Health Care Demo for JBCN Conf 2017


Demo Script
* We show a Domain model: Patient, Observation, Procedure, Emergency 
	* project: model (it will be great if we can use FHIR as our model, it is not very DDD like)
* We show a business model (quickly describe it and show its value)
	* Simple decision: that defines how to categorise Patients (high risk/low risk)
	* Simple process: that defines how to treat incoming emergencies
	* Both business models emit events
* We show a couple of services:
	* Emergency Service
		* project: emergency-service
		* dependencies: activiti-engine
	* Patient Medical Records Service
		* project: patient-records-service
		* dependencies: pure crud
* We show a Front End just to have a way to interact, this will require
	* API Gateway
	* Spring Message Bus
	* Spring Data Flow 
		* We need to create sources/processors/sinks
*  Approach 1: Emergency Service will use the models by having embedded engines	 
* (Approach 2: Incoming Emergency Service will use generated code)
* (Optional) We show some kind of Business Value driven monitoring (should we use KIBANA??? ) 



* Requirements

Install Spring Data Flow:

brew install kafka

(This will also install ZooKeeper)

- Start zookeeper and kafka
> zkServer start
> kafka-server-start /usr/local/etc/kafka/server.properties

- Download Spring Data Flow
curl -O wget http://repo.spring.io/release/org/springframework/cloud/spring-cloud-dataflow-server-local/1.2.1.RELEASE/spring-cloud-dataflow-server-local-1.2.1.RELEASE.jar
- Start the Server
java -jar spring-cloud-dataflow-server-local-1.2.1.RELEASE.jar




