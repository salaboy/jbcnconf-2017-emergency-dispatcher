#Patient Medical Records Service

This project is based on Spring boot 2.0.O M1
Used module spring boot modules:
- Web
- Rest Repositories
- JPA
- H2


####Objects:
- Patient
- Observation
- Procedure, 
- Emergency

####Entry point app:
http://localhost:8080

####All the patients:
curl  http://localhost:8080/patient/

####Create a new patient:
curl -i -X POST -H "Content-Type:application/json" -d "{  \"name\" : \"Bruce\",  \"surname\" : \"Wayne\" }" http://localhost:8080/patient

####Update a patient:
curl -i -X PUT -H "Content-Type:application/json" -d "{  \"name\" : \"Thomas\",  \"surname\" : \"Wayne\" }" http://localhost:8080/patient/3

##Pending:

Define  properties for all the objects