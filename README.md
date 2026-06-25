# Car-Racing-Simulation
OOP-based turn-by-turn Car Racing Simulation using Car, RaceTrack &amp; RaceGame classes. Implements random boost mechanics, track distance tracking &amp; winner declaration. Demonstrates encapsulation, inheritance &amp; polymorphism with optional Car subclasses (SportsCar, TruckCar) and dynamic weather effects.
🏎️ Car Racing Simulation — Java OOP + GUI (JavaFX)

> **A fully interactive turn-based car racing simulation** built in Java with a graphical user interface. Leverages core OOP principles — encapsulation, inheritance, and polymorphism — across three primary classes: `Car`, `RaceTrack`, and `RaceGame`, with a live-updating GUI race display, animated progress bars, and real-time winner declaration.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Class Architecture](#class-architecture)
- [Core Classes](#core-classes)
- [GUI Components](#gui-components)
- [Game Mechanics](#game-mechanics)
- [Optional Extensions](#optional-extensions)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

---

## 🗂️ Overview

The **Car Racing Simulation** is a fully playable Java application combining robust OOP architecture with an interactive graphical interface. Each car advances per turn via randomized speed boosts, rendered visually through animated progress bars and a live leaderboard panel. Built with Java Swing (or JavaFX), the GUI updates in real-time each turn, reflecting every car's position on the track until a winner crosses the finish line.

**Core OOP Concepts Demonstrated:**
- **Encapsulation** — each class owns and protects its own data
- **Inheritance** — `SportsCar`, `TruckCar`, `ElectricCar` extend `Car`
- **Polymorphism** — subclasses override `randomBoost()` for unique behavior
- **Composition** — `RaceGame` owns `RaceTrack` and manages `Car` objects
- **Event-Driven Programming** — GUI button listeners trigger game actions
- **Swing/JavaFX MVC Pattern** — game logic separated from view layer

---

## ✅ Features

| Feature | Description |
|---|---|
| 🖥️ **Java Swing / JavaFX GUI** | Fully graphical interface with panels, buttons, and progress bars |
| 🎲 **Randomized Boost Engine** | Each car receives a random speed boost every turn |
| 📊 **Live Progress Bars** | Animated `JProgressBar` tracks each car's position in real time |
| 🏆 **Winner Declaration Dialog** | Pop-up announcement when a car crosses the finish line |
| 📋 **Real-Time Leaderboard Panel** | Side panel shows ranked car positions updated each turn |
| 🎮 **Start / Reset Controls** | GUI buttons to start the race, run next turn, or reset the simulation |
| 🚗 **Multi-Car Support** | Configurable number of cars racing simultaneously |
| 🧬 **Car Subclasses** *(Optional)* | `SportsCar`, `TruckCar`, `ElectricCar` with distinct boost profiles |
| 🌦️ **Weather Effects** *(Optional)* | Dropdown selector applies weather multipliers to boosts |
| 💾 **Game History** *(Optional)* | Saves race results and stats to a `.txt` file |

---

## 🏗️ Class Architecture

```
RaceGame  (Controller + GUI Launcher)
│
├── RaceTrack          (Model: track distance, finish-line logic)
├── RacePanel          (View: Swing panel rendering track progress)
├── LeaderboardPanel   (View: ranked position display)
│
└── Car[]              (Model: array of competing car objects)
    ├── Car            (Base: name, speed, position, boost)
    ├── SportsCar      (Subclass: high boost range 15–30)
    ├── TruckCar       (Subclass: low boost range 3–10)
    └── ElectricCar    (Subclass: consistent fixed boost)
```

---

## ⚙️ Core Classes

### `Car.java`
Base class representing a single racing vehicle with position tracking, boost logic, and GUI-ready status output.

**Attributes:**
```java
private String name;          // Car display name
private int    baseSpeed;     // Constant speed added every turn
private int    position;      // Current distance covered on track
private int    boostValue;    // Last random boost applied
private Color  carColor;      // Color used in GUI progress bar
```

**Key Methods:**
```java
void move()                   // position += baseSpeed + randomBoost()
int  randomBoost()            // Returns random int within boost range
int  getPosition()            // Getter: current track position
String getStatusString()      // Returns formatted "Name | Pos: X | Boost: +Y"
void reset()                  // Resets position to 0 for new race
```

---

### `RaceTrack.java`
Defines the race environment — total distance and finish-line validation.

**Attributes:**
```java
private int    trackDistance;  // Total track length
private String trackName;      // Display name for GUI header
```

**Key Methods:**
```java
boolean hasFinished(Car car)      // Returns true if car.position >= trackDistance
int     getTrackDistance()        // Getter: total track distance
String  getTrackName()            // Getter: track name for GUI label
```

---

### `RaceGame.java`
Main controller class — initializes the race, manages the game loop, and connects model logic to the GUI view.

**Attributes:**
```java
private Car[]       cars;          // All participating Car objects
private RaceTrack   track;         // The active RaceTrack instance
private int         turnCount;     // Number of turns elapsed
private boolean     raceOver;      // Flag: stops loop when winner found
```

**Key Methods:**
```java
void startRace()                   // Initialize cars, track, and launch GUI
void runTurn()                     // One full turn: all cars move, GUI updates
void declareWinner(Car car)        // Show winner JOptionPane dialog
void displayLeaderboard()          // Sort and render leaderboard panel
void resetRace()                   // Reset all car positions and turn counter
```

---

### `RacePanel.java` *(GUI View)*
Swing panel rendering each car's live progress bar and status label.

**Attributes:**
```java
private JProgressBar[] progressBars;   // One per car, max = trackDistance
private JLabel[]       carLabels;      // Shows car name + current position
```

**Key Methods:**
```java
void updateDisplay(Car[] cars)         // Refreshes all progress bars and labels
void initPanel(Car[] cars, int track)  // Builds initial panel layout
```

---

### `LeaderboardPanel.java` *(GUI View)*
Side panel displaying real-time ranked positions of all cars.

**Key Methods:**
```java
void updateLeaderboard(Car[] cars)     // Sorts cars by position, re-renders rank list
```

---

## 🖥️ GUI Components

| Component | Type | Purpose |
|---|---|---|
| **Race Progress Panel** | `JPanel` + `JProgressBar[]` | Animated per-car position bars |
| **Car Status Labels** | `JLabel[]` | Shows name, position, last boost per turn |
| **Start Race Button** | `JButton` | Triggers `startRace()` |
| **Next Turn Button** | `JButton` | Manually steps through one turn |
| **Auto-Run Toggle** | `JCheckBox` | Automatically runs all turns with delay |
| **Reset Button** | `JButton` | Resets all positions and turn counter |
| **Leaderboard Panel** | `JPanel` + `JLabel[]` | Real-time ranked car positions |
| **Winner Dialog** | `JOptionPane` | Pop-up declaring winner and turn count |
| **Weather Dropdown** | `JComboBox` *(Optional)* | Selects weather condition affecting boosts |
| **Turn Counter Label** | `JLabel` | Displays current turn number |

---

## 🎮 Game Mechanics

| Mechanic | Rule |
|---|---|
| Turn structure | Every car calls `move()` once per turn in sequence |
| Boost formula | `position += baseSpeed + Random(minBoost, maxBoost)` |
| Finish condition | First car with `position >= trackDistance` wins |
| Tie handling | Multiple cars crossing on same turn → tie declared |
| GUI update | `SwingUtilities.invokeLater()` ensures thread-safe UI refresh |
| Auto-run delay | `Timer` or `Thread.sleep()` adds delay between turns for animation |

### Boost Formula
```
newPosition = currentPosition + baseSpeed + Random(minBoost, maxBoost)
```

---

## 🧬 Optional Extensions

### Car Subclasses
```java
public class SportsCar extends Car {
    public SportsCar(String name) { super(name, 10, 15, 30); }

    @Override
    public int randomBoost() {
        return (int)(Math.random() * 16) + 15;  // Boost: 15–30
    }
}

public class TruckCar extends Car {
    public TruckCar(String name) { super(name, 8, 3, 10); }

    @Override
    public int randomBoost() {
        return (int)(Math.random() * 8) + 3;    // Boost: 3–10
    }
}

public class ElectricCar extends Car {
    public ElectricCar(String name) { super(name, 12, 12, 12); }

    @Override
    public int randomBoost() { return 12; }     // Fixed boost: always 12
}
```

### Weather Effects
```java
public class WeatherSystem {
    private String condition;    // "Sunny", "Rainy", "Stormy", "Foggy"
    private double multiplier;   // Applied to all boost values

    public double getBoostMultiplier() {
        return switch (condition) {
            case "Stormy" -> 0.4;
            case "Rainy"  -> 0.7;
            case "Foggy"  -> 0.85;
            default       -> 1.0;  // Sunny
        };
    }
}
```

---

## 🚀 Getting Started

### Prerequisites
- **Java 11+** (Java 17 recommended)
- **IDE:** IntelliJ IDEA, Eclipse, or VS Code with Java Extension Pack
- **Build Tool:** Maven or Gradle *(optional)*

### Compile & Run

```bash
# Clone the repository
git clone https://github.com/your-username/car-racing-simulation.git
cd car-racing-simulation

# Compile all source files
javac -d bin src/*.java

# Run the application
java -cp bin RaceGame
```

### With Maven

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="RaceGame"
```

### With IntelliJ IDEA
1. Open project → `File > Open` → select project folder
2. Mark `src/` as Sources Root
3. Run `RaceGame.java` via the green ▶️ button

---

## 💻 GUI Preview

```
╔══════════════════════════════════════════════════════╗
║           🏎️  CAR RACING SIMULATION                  ║
║          Track: Grand Prix | Distance: 200           ║
╠══════════════════════════════════════════════════════╣
║                                                      ║
║  Ferrari      [████████████░░░░░░░░░]  120 / 200    ║
║  Lamborghini  [██████████░░░░░░░░░░░]  105 / 200    ║
║  McLaren      [█████████████░░░░░░░░]  135 / 200    ║
║  Bugatti      [███████░░░░░░░░░░░░░░]   78 / 200    ║
║                                                      ║
╠══════════════════════════════════════════════════════╣
║  Turn: 7        [▶ Next Turn]  [⟳ Reset]  [⏩ Auto] ║
╠══════════════════════════════════════════════════════╣
║  LEADERBOARD         ║  Last Turn Boosts             ║
║  1. McLaren   135    ║  Ferrari:      +18            ║
║  2. Ferrari   120    ║  Lamborghini:  +22            ║
║  3. Lamborghini 105  ║  McLaren:      +15            ║
║  4. Bugatti    78    ║  Bugatti:      +9             ║
╚══════════════════════════════════════════════════════╝
```

---

## 📁 Project Structure

```
car-racing-simulation/
│
├── src/
│   ├── RaceGame.java            # Main controller + GUI launcher
│   ├── Car.java                 # Base Car model class
│   ├── RaceTrack.java           # Track distance and finish-line logic
│   ├── RacePanel.java           # Swing panel: progress bars + labels
│   ├── LeaderboardPanel.java    # Swing panel: real-time ranked leaderboard
│   ├── SportsCar.java           # (Optional) High-boost Car subclass
│   ├── TruckCar.java            # (Optional) Low-boost Car subclass
│   ├── ElectricCar.java         # (Optional) Fixed-boost Car subclass
│   └── WeatherSystem.java       # (Optional) Weather boost multipliers
│
├── assets/
│   └── car_icons/               # (Optional) Car image assets for GUI
│
├── tests/
│   ├── CarTest.java             # JUnit: move(), randomBoost(), reset()
│   ├── RaceTrackTest.java       # JUnit: hasFinished() edge cases
│   └── RaceGameTest.java        # JUnit: full race simulation tests
│
├── history/
│   └── race_results.txt         # (Optional) Saved race outcomes
│
├── README.md
└── pom.xml / build.gradle
```

---

## 🧪 Running Tests

```bash
# Compile tests with JUnit 5
javac -cp .:junit-5.jar -d bin tests/*.java src/*.java

# Run all tests
java -cp bin:junit-5.jar org.junit.platform.console.ConsoleLauncher --scan-classpath

# Or with Maven
mvn test
```

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/weather-gui-dropdown`
3. Commit using Conventional Commits: `git commit -m "feat: add weather selector JComboBox to GUI"`
4. Push and open a Pull Request

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---

## 👤 Author

**Nejar Medhat**
- GitHub: [@nejarmedhat](https://github.com/nejarmedhat)
---

<p align="center">Pure Java OOP meets interactive GUI — every class has a role, every car has a race 🏎️💨</p>
