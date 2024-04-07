# Scoreboard library


### Assumptions made based on the requirements

* There can't be two matches between the same two teams in a single moment.
* Matches are referenced by names of teams, but it is easy extendable to e.g. ID if requirements changed.
* The entire data model is encapsulated within a single class, considering the simplicity of the task. 


### Using the Scoreboard library

#### Prerequisites
* Maven 3.8+
* Java 17+

To use the Scoreboard library, follow these steps:

1. Clone the Repository
   ```bash
   git clone https://github.com/mateuszcer/scoreboard.git
2. Navigate to the Project Directory
   ```bash
   cd scoreboard
3. Build the Project
    ```bash
    mvn clean package
4. Include JAR in Your Project
    ```bash
    target/scoreboard-1.0-jar-with-dependencies.jar

### Usage

1. **Initialize Scoreboard**
   ```java
   Scoreboard scoreboard = new Scoreboard();

2. **Start matches**
    ```java
    scoreboard.startMatch("Mexico", "Canada");
    scoreboard.startMatch("Spain", "Brazil");
    scoreboard.startMatch("Germany", "France");
    scoreboard.startMatch("Uruguay", "Italy");
    scoreboard.startMatch("Argentina", "Australia");
3. **Update scores**
    ```java
    scoreboard.updateScore("Mexico", "Canada", 0, 5);
    scoreboard.updateScore("Spain", "Brazil", 10, 2);
    scoreboard.updateScore("Germany", "France", 2, 2);
    scoreboard.updateScore("Uruguay", "Italy", 6, 6);
    scoreboard.updateScore("Argentina", "Australia", 3, 1);

3. **Get summary**
    ```java
    scoreboard.getSummarySortedByScore()
    // [Uruguay 6 - Italy 6, Spain 10 - Brazil 2, Mexico 0 - Canada 5, Argentina 3 - Australia 1, Germany 2 - France 2]

4. **Finish match**
    ```java
    scoreboard.finishMatch("Mexico", "Canada");
    scoreboard.finishMatch("Germany", "France");

5. **Get string representation of summary**
    ```java
    scoreboard.getStringSummary()
    /*
        Uruguay 6 - Italy 6
        pain 10 - Brazil 2
        Argentina 3 - Australia 1
    */