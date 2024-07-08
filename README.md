# Football Scorecard Manager

## Overview
Football Scorecard Manager is a console based application and manages World cup football scoreboard.

Football Scoreboard Manager assumptions :
1. Football can be played only beteen two teams.
2. Only a single football game could be in running state between two teams.
3. Each team's score will be always be greater than zero.
4. A football game is identified to be unique by the pair team names associated with a game.
5  Updated score of a running game will always be greater than or equal to existing score.

### Supported operations:

1. Start a football game.
2. Finish a football game.
3. Update score of a football game.
4. Display summary of the running football games.

### Local development instructions:

Run tests:
   ```shell
    gradlew test
   ```
Build application
```shell
gradlew build
```
Run application in console
```shell
java  com.sportsradar.Main
```
