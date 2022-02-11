# Continuous integration server (CI server)

This repository includes code for implementing a basic CI server. The implementation offers pulling the code from the git repository, building and testing the code. This project uses the [CI server skeleton](https://github.com/KTH-DD2480/smallest-java-ci) implemented in Java which is called as webhook by Github. The HTTP part of the skeleton is based on Jetty. This project is made for Software Engineering Fundamentals course (DD2480) in KTH.

# Test Execuation
Test execuation is done using Gradle. When the CI server gets a push, new changes are fetched using ```git fetch```. Then the CI server checks out the main branch using ```git checkout main``` and then the code is tested using ```./gradlew test```.

Test update readme


# How to compile

## Requirements:
- Java 8 

## Windows
Clone the project and navigate to the project folder. You can then compile the code using ```./gradlew.bat shadowJar``` and test it using ```./gradlew.bat test``` in command line (cmd).


## Linux and Mac

Clone the project and navigate to the project folder. You can then run the code using ```./gradlew shadowJar``` and test it using ```./gradlew test``` in terminal.


# Contributions

This project has been done by the members of Group 20: 

- Chloé Porion, porion@kth.se 
    - CI server notification implementation
    - Project planning
    - Code review
    - Documentation

- Nicky Obreykov, obreykov@kth.se
    - Initial server setup
    - SSH setup
    - Code review
    - Documentation

- Benjamin Ronneling, benron@kth.se
    - Organisation and team setup 
    - Code review
    - Documentation

   
- Martin Larsson, mala7@kth.se
    - Gradle setup
    - Created datastructures 
    - Github webhook setup   
    - Code review
    - Documentation



