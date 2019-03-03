# Parking Lot
The application is designed to accommodate multi-level parking lot. Every level could have a number of slots.
Application allocates slot from lowest parking level and in turn, nearest slot in the level from entrance (assuming slots are numbered in increasing order from entrance).

- Currently, this application allocates one parking lot with a number parking lots. However, it is extensible for multiple levels by adding more functionalities. 
- Though it is written to accommodate only cars, more vehicles could be added.
- Addition to current spots for cars, different types of spot for different vehicles could be added.
   

## Prerequisties
Maven ( >= 3.5 ) should be installed since this is a maven project.
Java 1.8 should be installed.

## How to run
1. Run bin/setup which will build the current project. This will create parking_lot-1.0-SNAPSHOT.jar.
2. Run bin/parking_lot which will run the jar file by reading input from file or command line.