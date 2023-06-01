<br />
<div align="center">
<h3 align="center">PRAGMA POWER-UP</h3>
  <p align="center">
    This project develops an application that centralizes the services and orders of a restaurant chain that has different branches in the city..
  </p>
</div>

### Built With

* ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
* ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
* ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
* ![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)


### Unit tests are performed with
* [JUnit5](https://junit.org/junit5/) - Library used for testing
* [Mockito](https://site.mockito.org/) - Framework used for code testing
* [Jacoco](https://www.jacoco.org/jacoco/trunk/index.html) - Test coverage tool


<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these steps.

### Prerequisites

* JDK 17 [https://jdk.java.net/java-se-ri/17](https://jdk.java.net/java-se-ri/17)
* Gradle [https://gradle.org/install/](https://gradle.org/install/)
* MySQL [https://dev.mysql.com/downloads/installer/](https://dev.mysql.com/downloads/installer/)



### Installation

1. Clone the repository
2. Create a new database in MySQL called square
3. Update the database connection settings
   ```yml
   # src/main/resources/application-dev.yml
   spring:
      datasource:
          url: jdbc:mysql://localhost/square
          username: root
          password: <your-password>
   ```
<!-- USAGE -->
## Usage

1. Right-click the class PowerUpApplication and choose Run
2. Open [http://localhost:8091/swagger-ui/index.html](http://localhost:8090/swagger-ui/index.html) in your web browser

<!-- ROADMAP -->
## Tests

- Right-click the test folder and choose Run tests with coverage


## Project structure

### Configuration Layer
It is the outermost layer and is in charge of security configuration, class assignment and dependency injection.

### Layer Adapters
This layer contains two layers: the driven layer and the driving layer.

#### Driving layer
It is the user's entry point when connecting to the application.

It is the most internal module of the architecture, it belongs to 
the domain layer and encapsulates the business logic and rules.


## Entry Points

### Entry point create restaurant
![Image text](img/EntryPointCreateRestaurant.jpg)

### Exit point create restaurant
![Image text](/img/ExitPointCreateRestaurant.jpg)

### Entry point create dish Owner Authentication token
![Image text](img/EntryPointCreateDishAutenticationOwner.jpg)

### Exit point create dish
![Image text](/img/ExitPointCreateDish.jpg)

### Entry point update dish Owner Authentication token
![Image text](img/EntryPointUpdateDish.jpg)

### Exit point update dish
![Image text](/img/ExitPointUpdateDish.jpg)

### Entry point enable/disable dish
![Image text](img/EntryPointEnableDisableDish.jpg)

### Exit point enable/disable dish
![Image text](/img/ExitPointEnableDisableDish.jpg)

### Entry point List Restaurants
![Image text](img/EntryPointListRestaurants.jpg)

### Exit point List Restaurants
![Image text](/img/ExitPointListRestaurants.jpg)


## Author ✒️
* **Valentina Santa Muñoz** 

