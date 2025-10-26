# CBL_mapPIN - Game instructions
MapPIN - In this game, 2 players compete to check their geographical knowlege of europe and asia in 4 rounds.

To run the project:
Unzip the the folder and run the file mapPIN\src\main\java\mappin\game\Main.java.
Make sure your current directory is mapPIN since for DB communication to work properly, you must execute the code with the mapPIN 
directory being your current directory.

Once the program has been ran, to start the game:
1. Replace the placeholder names "player 1" and "player 2" with the names of the player if you'd like.
2. Pick the game mode of your choosing.
   City mode - each player will be asked to pin a city and be scored on proximity and speed
   Country mode - each player will be asked to pin a country and will be scored on speed and only if correct
3. Press the start button

During the game:
   
4. Each player in their turn (name written on the top left part of the screen) can read the location clue
   on the top panel. Drag your mouse to the point on the map you think is closest to the clue and click on it.
5. After clicking, you will see your score presented on the screen before the next player's turn starts.
   Not pinning any point will result in a score of 0.
6. After all rounds are over, the final screen will automatically show with the scores of each player. That is the end of the program.

To quit at any point of the game, close the window as usual.

To test both modes of the games - run the project, play the first game and close the window once ended. 
Then, run the project again and choose the other mode in the start screen.

# Locations used for research 

## Git - Version Control

https://learngitbranching.js.org/

https://git-scm.com/docs/gittutorial

https://www.w3schools.com/git/

## Swing + awt - events and GUI

https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html#flow

## Resources for implementation of the distance/country recognition algorithms

https://en.wikipedia.org/wiki/Miller_cylindrical_projection - conversion between latitude-longitude to coordinates on a 2D map

https://en.wikipedia.org/wiki/Haversine_formula - distance formula between 2 lat-long coordinate on earth

https://www.mapchart.net/world-advanced.html - world map (painted every country in a different color)

## Databases and file managment

https://www.youtube.com/watch?v=03rDqI6lxdI - JDBC Tutorial for Beginners

https://mvnrepository.com/artifact/com.h2database/h2/2.4.240 - Creating and connecting to h2 databases (using maven)

https://docs.oracle.com/javase/tutorial/jdbc/overview/index.html - Basic usage of jdbc 

https://www.h2database.com/html/datatypes.html - Elaboration on datatypes 

https://www.h2database.com/javadoc/org/h2/jdbc/JdbcResultSet.html - documentation on resultset

https://docs.oracle.com/javase/8/docs/api/java/sql/ResultSet.html - documentation on resultset

https://www.w3schools.com/sql/ - learn sql syntax

https://sql-island.informatik.uni-kl.de/ - fun sql game
