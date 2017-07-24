1.Description of the problem and solution.
  Problem

   Problem:
   Create a service that tells the user what types of food trucks might be found near a specific location on a map.
   (food truck lists are sorted by distance)

   *Data used:
   "DataSF: Food Trucks"

   *Solution:
   Define "nearby" - 1 mile in my app.

2. Whether the solution focuses on back-end, front-end or if it's full stack.

   Full stack

3. Reasoning behind your technical choices, including architectural.


   Architecture:

   **Backend**:
   -Spring boot (a spring application for rest api services). It's a Spring based production-ready framework which
   includes embeded tomcat server, which is convenient to use.
   -MySql: Used indexes on latitude and longitude columns to make searches faster. Easy to scale by sharding, load
   balancing etc.

   **Frontend**:
   jQuery and bootstrap (lightweight, and make it much easier to use JavaScript)
   Food trucks will shown as a list after hitting the "Go" button; They will shown in the map as well, clicking markers
   will display food truck details.

   **Logging and error handling**:
   Littered code with logs using slf4j
   Registered exception handler using ControllerAdvice to trap and log exceptions.
   
4. Trade-offs you might have made, anything you left out, or what you might do differently if you were to spend additional time on the project.
 
    * Enable query cache for mysql
    * Add aws cloudwatch for monitoring.
    * Add support to client to get current location using browser APIs with user permission
    * Beautify client side UI
    * Build more robust test suite
    * Make the service more secure, etc. prevent DDoS.
    
5. Link to your resume or public profile.

    [https://www.linkedin.com/in/yuxijin/]

6. Link to to the hosted application where applicable.

    [http://ec2-54-67-17-10.us-west-1.compute.amazonaws.com:8080/]

7. Steps to run the application:

    1)use mvn to build the jar
    mvn clean package -DskipTests
    2)load data into db:
    java -cp target/foodtruck-nearby-service-0.1.0.jar -Dloader.main=hello.dataloader.CsvUtil org.springframework.boot.loader.PropertiesLauncher
    3)run the application:
    java -jar target/foodtruck-nearby-service-0.1.0.jar
