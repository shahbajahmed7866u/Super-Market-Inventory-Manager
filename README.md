# SuperMarket Management System (Java)

A **polished, console & GUI-based Java application** designed for small to medium stores to efficiently manage their inventory. Built using **Core Java**, **Swing**, and **file-based persistence**, this project demonstrates real-world inventory management with modern user interface design.

---

## Table of Contents
- [Features](#features)
- [Technologies](#technologies)
- [Project Structure](#project-structure)
- [Installation & Running](#installation--running)
- [Usage](#usage)
- [Screenshots](#screenshots)
- [License](#license)

---

## Features
- **Add, Delete, Update Items** – Easily manage items in the store.
- **Update Quantity & Price** – Modify existing stock and prices quickly.
- **Auto-Calculate Total Store Worth** – Always visible in GUI, updates dynamically.
- **Persistent Data Storage** – All inventory stored in `items.txt`.
- **Interactive Table** – Click row to edit item.
- **Input Validation & Error Handling** – Prevents invalid entries and duplicates.
- **Professional GUI** – Clean, intuitive Swing interface with modern look.

---

## Technologies
- **Language:** Java 17+
- **GUI Framework:** Swing
- **Architecture:** MVC (Model-View-Controller)
- **Storage:** File-based persistence (`items.txt`)
- **Development Environment:** VS Code, JDK 17+

---

## Project Structure

<ul>
    <li>src
        <ul>
            <li>model
                <ul>
                    <li>Item.java</li>
                </ul>
            </li>
            <li>service
                <ul>
                    <li>Store.java</li>
                </ul>
            </li>
            <li>database
                <ul>
                    <li>items.txt</li>
                </ul>
            </li>
            <li>App.java</li>
            <li>SupermarketGUI.java</li>
        </ul>
    </li>
    <li>lib</li>
    <li>bin</li>
    <li>README.md</li>
</ul>


---

## Installation & Running

### 1. Clone Repository

git clone <your-repo-url>
cd SuperMarket


### 2. Compile Java Files

From src folder:
javac model/Item.java service/Store.java gui/SupermarketGUI.java

### 3. Run the GUI
java gui.SupermarketGUI

### 4. Optional: Run Console App
java App

## Usage

1. Add Item – Enter name, price, and quantity.

2. Delete Item – Select an item from the table or enter the name.

3. Update Price/Quantity – Select item and modify fields.

4. Refresh Table – Updates table with latest inventory and totals.

5. Total Store Worth & Item Count – Always displayed in GUI panel.

## Screenshots

(Optional: Add actual screenshots of your GUI here for visual reference)

## License

This project is open-source and free to use for learning and personal purposes.

## Author

Shahbaj Ahmed


---



