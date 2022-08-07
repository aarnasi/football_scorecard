The application is intended to manage a World cup football Scoreboard.

The application assumes the below for a game to be managed via the scoreboard application:
1. Game is played between two teams only.
2. Only a single game could be in running state between two teams.
3. Scoreboard displays running that are in progress.
4. Each teams score will be always be greater than zero.
5  Updated score of a running game will always be greater than or equal to existing score.

Other:
1. Game is identified to be unique by the pair team names associated with a game.

The application is expected to support four operations to manage a scoreboard.

1. Start a game.
2. Finish a game.
3. Update score of a game.
4. Display summary of the running games.

Instruction:

1. Running tests: gradlew test
2. Buidling the application: gradlew build
3. Running application in console: Running src/main/java/com/sportsradar/Main.java using any JAVA IDE. 
