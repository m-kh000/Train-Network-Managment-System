# Train Network Management System - Project Report

## 1. Basic idea and usage
The project is a desktop Java application for managing a railway train network. It lets a user model stations and the routes between them, then view the network as a graph and compute shortest paths.

The application is built with Java Swing and is organized into two major parts:
- Logic layer: classes that represent the network model and file persistence.
- UI layer: screens for viewing stations, adding/editing stations and routes, showing maps, and finding shortest paths.

### Typical usage flow
1. Start the app from the main entry point in the logic package.
2. Load or create a network.
3. Add stations and routes through the UI.
4. Export the current network to a text file or import a previously saved one.
5. View the network graph and calculate shortest paths between stations and check for cycles.

## 2. Main project structure
### Logic package
- App.java: application entry point.
- Network.java: central controller for stations, routes, shortest path logic, cycle detection, and network utilities.
- Station.java and Route.java: representing stations, their info and directed weighted routes between them.
- Fileio.java: handles importing and exporting the network from/to text files.

### UI package
- UI.java: main frame and switching logic between screens.
- MainPage.java: main menu of the app.
- Manager.java: shared styling, constants and methods for general looks, font creation and button styling.
- Components: RouteComponent and StationComponent for drawing graph elements.
- Methods: AddStation, AddRoute, EditStation, ShowStations, ShowMap, Shortest etc... panels for the user workflows.

## 3. Data structures used
The project uses a mixture of simple Java data structures and custom domain objects.

### Core domain objects
- Station -represents a node in the railway graph.
- Route -represents a connection/edge between two stations.

### Network storage structures
- HashMap<Integer, Station> stations
  - Used for fast lookup by id.

- HashMap<Station, HashMap<Station, Route>> routes
  - Outer key: source station
  - Inner map: destination station -> Route
  - Used to model adjacency from a station to its outgoing routes.

### Supporting collections
usining Lists, ArrayLists, HashSets, Collections of Routs and Stations for various reasons like :dispaying, storing, tracking

## 4. Algorithms used
### 4.1 Shortest path: Dijkstra's algorithm
The core path-finding logic in Network.findShortestPath uses Dijkstra's algorithm.

How it works:
- It initializes all distances to infinity except the starting station.
- It repeatedly picks the unvisited station with the smallest known distance.
- It relaxes edges to neighboring stations using route weights.
- It reconstructs the path by following predecessor pointers.

### 4.2 Cycle detection
The Network.hasCycle method checks whether the graph contains a cycle.

Approach:
- It uses DFS-style traversal with three sets:
  - all nodes
  - visiting nodes
  - visited nodes
- If a node is encountered while it is still in the visiting set, a cycle is detected.

### 4.3 Graph layout and drawing
The graph view is drawn using a circular layout:
- Stations are positioned around a circle based on the number of stations.
- Routes are drawn as arrows using the station coordinates.
- The shortest path is highlighted visually.

### 4.4 File parsing and persistence
The Fileio class parses a custom text format and loads the network from files in the files directory.
- The app exports the current network into a readable text form.
- It imports data back into the in-memory network.

## 5. Features of the project
### Station management
- Add stations with names.
- Edit station names.
- Delete stations.
- View all stations in a list.
- Sort stations by route count.

### Route management
- Add routes between stations.
- Specify route weights.
- Edit/remove route associations through the station editor form.

### Network visualization
- Display a graphical map of stations and routes.
- Draw routes as arrows.
- Highlight the currently selected shortest path.

### Pathfinding
- Find the shortest path between two stations.
- Show the result as a total distance and highlight the path on the graph.

### Cycle detection
- Report whether the current network contains a cycle.

### Persistence
- Import network data from text files.
- Export the network to text files.
- Save state to a default file called recent.txt.

## 6. Important implementation details
### Static state usage
The network is managed through static fields in the Network class. This keeps the data accessible across different UI screens without passing the model around everywhere.

### Swing-based UI
The UI is built using Swing components such as:
- JFrame
- JPanel
- JButton
- JComboBox
- JTextField
- JScrollPane
- JDialog

### Custom visual components
The graph drawing uses custom components:
- StationComponent: draws station markers and labels.
- RouteComponent: draws weighted arrows between stations.

### File format
The app uses a simple text-based format for persistence. Each line contains a station and its outgoing routes in a custom notation. This makes the project lightweight and easy to inspect manually.

## 7. Strengths of the project
- Clear separation between logic and user interface.
- The core graph model is straightforward and easy to extend.
- Supports both interactive editing and visual analysis of the network.
- Includes import/export support for data persistence.

## 8. Possible improvements
Although the project is functional, there are some areas that could be improved:
- Replace the static global network state with a more object-oriented design.
- Improve error handling and validation in file import/export.
- Handle duplicate routes and multi-edge cases more explicitly.
- Add better support for undirected/directed route semantics in a more formal graph model.
- Improve code structure by reducing repetition in the Swing UI classes.

## 9. Summary
This project is a small but complete railway network management application. It combines graph theory, file I/O, and a Swing-based GUI to let users manage stations and routes, visualize the network, and compute shortest paths. The central logic revolves around stations and weighted routes, with Dijkstra's algorithm supporting shortest-path queries and DFS-based logic supporting cycle detection.
