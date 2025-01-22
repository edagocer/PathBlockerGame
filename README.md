# Path Blocker Game

## Overview
**Path Blocker** is a Java-based puzzle game where players solve levels to reach specific goals. Each level presents a unique challenge, encouraging strategic thinking and problem-solving skills.

---

## Features
- **10 Unique Levels**: Levels increase in difficulty as players progress.
- **Algorithmic Pathfinding**: Utilizes **Breadth-First Search (BFS)**, an optimal graph traversal algorithm, to solve puzzles efficiently.

---

## Project Structure
The project consists of the following components:

### Level Files
- **`level01.txt` to `level10.txt`**: Text files defining each puzzle's layout and objectives.

### Java Classes
- **`Solver.java`**: Implements the main logic for solving puzzles using BFS.
- **`Step.java`**: Tracks each move in the solution process.
- **`Node.java`**: Represents nodes and their relationships in the BFS algorithm.
- **`Test.java`**: Includes test cases for validating puzzle solutions.
