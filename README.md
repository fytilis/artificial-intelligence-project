# Artificial Intelligence Projects

This repository contains projects developed for an Artificial Intelligence course, focusing on classic search algorithms and game theory.

---

## Project 1: Maze Solving with UCS and A* Search

This project implements and compares two fundamental pathfinding algorithms, **Uniform Cost Search (UCS)** and **A***, to find the optimal path in a randomly generated N×N maze.

### Problem Definition
The maze is represented as a 2D grid where each cell can be one of the following:
- `.` : An empty, traversable cell.
- `#` : An obstacle (wall).
- `S` : The starting point.
- `G` : The goal point.
- `A`, `B`: Teleporters that allow instant travel between them.

**Movement Costs**:
- Horizontal, vertical, and diagonal moves have a cost of **1**.
- Using a teleporter has a cost of **2**.

### Algorithms

#### 1. Uniform Cost Search (UCS)
UCS is an uninformed search algorithm that finds the least-cost path from a start node to a goal node. It explores the maze by always expanding the node with the lowest path cost (`g(n)`) from the start. While it guarantees an optimal solution, it can be inefficient as it explores in all directions, much like a blind search.

#### 2. A* Search
A* is an informed search algorithm that improves upon UCS by using a heuristic function to guide its search towards the goal. The total cost of a node `n` is calculated as `f(n) = g(n) + h(n)`, where:
- `g(n)` is the actual cost from the start to node `n`.
- `h(n)` is the estimated (heuristic) cost from node `n` to the goal.

### Heuristic Function: Chebyshev Distance

For this project, the **Chebyshev Distance** was chosen as the heuristic function `h(n)`:
`h(n) = max(|n.x - goal.x|, |n.y - goal.y|)`

**Justification**:
- **Admissibility**: The Chebyshev distance never overestimates the actual cost. It calculates the minimum number of moves required to reach the goal on a grid where 8-directional movement (including diagonals) is allowed, assuming no obstacles. This makes it an admissible heuristic.
- **Consistency**: The heuristic is also consistent (or monotonic), as the cost of moving from a node `n` to a neighbor `n'` plus the heuristic of `n'` is never less than the heuristic of `n`. This ensures that A* does not need to re-expand nodes, making it more efficient.
- **Superiority over other heuristics**:
    - **Manhattan Distance** was rejected because it only considers horizontal and vertical moves, underestimating the cost in a grid that allows diagonal movement.
    - **Euclidean Distance** was rejected because it is suited for continuous spaces, not discrete grids with fixed move costs.

### Results
Experimental results consistently show that **A* finds the optimal path by expanding significantly fewer nodes** compared to UCS. This demonstrates the power of a well-chosen heuristic in reducing computational cost while maintaining optimality.

---

## Project 2: Implementing the MINIMAX Algorithm for a Custom Game

This project implements the **MINIMAX** algorithm for a custom 4x3 two-player game, enabling an AI opponent to play optimally against a human player.

### Game Rules
The game is played on a 4x3 board between two players:
- **MAX**: The AI (computer), using the symbol 'X'.
- **MIN**: The human player, using the symbol 'O'.

Players take turns placing their symbols on empty cells. The game ends when:
1.  **MAX wins**: Forms the pattern "XOX" (horizontally, vertically, or diagonally). The game state has a value of **+1**.
2.  **MIN wins**: Forms the pattern "OXO". The game state has a value of **-1**.
3.  **Draw**: The board is full, and no winning pattern has been formed. The game state has a value of **0**.

### MINIMAX Algorithm
The MINIMAX algorithm is a recursive, depth-first search algorithm used for decision-making in two-player, zero-sum games. It works by:
1.  Building a complete game tree from the current state.
2.  Exploring all possible future moves for both players with chebyshev heuretic.
3.  The **MAX** player aims to maximize the final score, while the **MIN** player aims to minimize it.
4.  The algorithm backpropagates the utility values from the terminal nodes up the tree to determine the optimal move at the current state.

This implementation explores the full game tree without any pruning (like Alpha-Beta pruning) or heuristics for non-terminal states, guaranteeing a truly optimal move.

### Implementation Details
- `minimax()`: A recursive function that alternates between MAX and MIN turns to evaluate the best possible outcome from any given board state.
- `findBestMove()`: Iterates through all available moves for the AI, calls `minimax()` for each, and selects the move that leads to the highest guaranteed score.
- `checkWin()` and `isGameOver()`: Helper functions to check for terminal states (win, loss, or draw).

The AI player is designed to always play optimally, leading to a win or a draw if the human player does not make any mistakes.
