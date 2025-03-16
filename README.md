# Memory Game - Java Swing

## 📌 Project Overview
This is a **Memory Game** developed in Java using the **Swing** framework for the graphical user interface (GUI). The game allows players to flip and match cards, track their best times, and store scores based on difficulty levels.

## 🎮 Features
- **Multiple difficulty levels:** Easy (4x4), Medium (6x6), Hard (8x8)
- **Graphical User Interface (GUI) using Java Swing**
- **Highscore system** that saves and loads results from files
- **Sound effects and background music**
- **Dynamic menu system**
- **Settings panel to change game difficulty**

## 🛠️ How to Run
### **1. Clone the Repository**
```sh
git clone https://github.com/YourUsername/Java_MemoryGame.git
cd Java_MemoryGame
```

### **2. Compile and Run**
Using the command line:
```sh
javac -d bin src/*.java
java -cp bin Main
```
Or run the **Main.java** file from an IDE like **IntelliJ IDEA** or **Eclipse**.

## 📂 Project Structure
```
Java_MemoryGame/
│-- src/
│   │-- Card.java            # Represents a memory card
│   │-- Difficulty.java      # Enum for difficulty levels
│   │-- FileHandler.java     # Handles saving and loading scores
│   │-- GameFrame.java       # Main game window
│   │-- GameLogic.java       # Core game logic
│   │-- GamePanel.java       # Game board UI
│   │-- HighscorePanel.java  # Highscore display
│   │-- ImageLoader.java     # Loads images for cards
│   │-- Main.java            # Entry point
│   │-- MenuPanel.java       # Main menu UI
│   │-- ResultPanel.java     # Results UI
│   │-- SettingsPanel.java   # Settings menu UI
│   │-- SoundHandler.java    # Handles sounds and background music
│-- assets/                  # Images and sounds folder
│-- README.md                # Project documentation
```

## 🎵 Sound and Images
- Game includes background music and sound effects.
- Images for the game are stored in folders (`images_4x4/`, `images_6x6/`, `images_8x8/`).

## 🏆 Highscore System
- The game saves the player’s best time for each difficulty in a file (`results_easy.txt`, `results_medium.txt`, etc.).
- The results are sorted by time, and only the best attempts are stored.

## 🔧 Future Improvements
- Add multiplayer mode
- Improve UI/UX with animations
- Implement an online leaderboard

## 📜 License
This project is open-source. Feel free to modify and distribute it as needed!

🚀 **Happy Coding!**
