#Emergency Service

This project is based on Spring boot 2.0.O M1
Used module spring boot modules:
- Web

####Entry point app:
http://localhost:9999
but you can see what it does from the log, nothing for now on the url
		
####Additional dependency to the pom.xml
I had to manually add the Activiti dependency and some others because is not available form the online tool

```
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>

<dependency>
    <groupId>org.codehaus.groovy</groupId>
    <artifactId>groovy-all</artifactId>
</dependency>

<dependency>
    <groupId>org.activiti</groupId>
    <artifactId>activiti-spring-boot-starter-basic</artifactId>
    <version>5.21.0</version>
</dependency>

```
