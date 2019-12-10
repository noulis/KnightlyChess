# Knightly Chess

## Android Assessment

Create an Android application (Kotlin preferred) that should render an empty chessboard where 
the user will be able to mark a starting position and an ending position.
The app should then render a list of all possible paths that one knight (αλογάκι) piece 
in the starting position could take to reach the ending position in a user specified amount of moves.

## Requirements/ Things to take into account

- Some inputs might not have a solution, in this case, the program should display a message that no solution has been found. 
- The user should be able to reset the board and start again. 
- How the application will render the paths is up to you (e.g. render the paths using lines of a different color, present a list of the moves in algebraic chess notation, animation of the knight along the path). 
- The rendered chessboard could be of any size NxN where 6<=N<=16. 
- The user should be able to choose the desired chessboard size.
- The user should be able to choose the maximum number of moves (default = 3, min = 2, max = 10).
- The path calculation should not block the main thread. 
- The app should ‘remember’ the last possible solution after the app is closed (killed) and re-opened.

> Note: Use of GitHub will be highly appreciated.

# Outcome - Notes
