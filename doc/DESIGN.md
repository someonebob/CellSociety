## Overall Design

### Simulation  
The main component of the simulation is the `Grid` structure, which holds all `Cells` in the simulation as well as their global coordinates within the system. The purpose of the Grid is to perform all group controls on the Cell, without worrying about each individual cell. When a grid is created, it instantiates cells given an initial set of configurations. It then stores all Cells. Next, it goes through each cell, and passes the Cell its local `Neighborhood` of Cells in coordinates local to the cell. This Neighborhood depends both on the Cells' positions and also the `Edge` type of the Grid.This ensures that each Cell only has access to its neighbors - not the global system. Each iteration of the control loop, a method is called in the Grid in which it iterates through all of its Cells and forces them to update their state given their neighbors. 

Each Cell, after being instantiated by the grid and passed its initial configuration and `Rules` set and is in charge of maintaining its `State`. It is also passed its Neighborhood and local coordinates of neighbors after the Grid instantiates all cells. When the Grid tells each individual Cell to update its state, the Cell passes itself and its neighbors, in the form of a Neighborhood object to its Rules set. The rules are specific for each simulation and return the Cells new State given it's current configuration and its neighbors' configurations. However, the cell does not immediately update its State. Instead, it stores its future State until the grid updates all other Cells. This precaution is taken to ensure that all cells are only affect by neighbors of the current state.

States and Rules sets are what make each simulation unique, which I will elaborate on in a different section.

### Configuration 
Most of the initial configuration of Cells is held in an XML file, that is unique to each simulation. This includes starting states, simulation-specific parameters, colors among other things. An `XML Parser` structure is used to retrieve different types of data from the file. It is essentially our program's interface with the XML files. It is initially created in the Grid, but any object that is stored in the Grid that needs its initial configuration, for example Cells, is also passed the XML Parser. 

Another form of configuration is the resource files. These files are used inside the Visualization to determine what the text in the displays says. Although our software is in English, the resource files allow easy expansion to different languages.

### Visualization
The Visualization begins with the main structure, `Window`. Window manages two different structures:  
  
* `Menu`: This deals with the startup menu and file loading. It consists of two buttons. On the event of the "Exit" button press, the Window is closed. On the event of the "Load" button press, and FileChooser is created to select a file, in which case the Menu returns the file name back to the Animation.  

* `Animation`: This class deals with the bulk of the Visualization. Mainly, it creates an instance of a Grid Imager subclass, which extends the Grid Imager class and differs in the shape that patterns on the screen. The Grid Imager is the only messenger between the Visualization and Simulation. It creates and stores a Grid object, tell the grid to update every frame, and then plots the Cells on the frame according to the Grid. It distinguishes each Cell by color, which is standardized in the JavaScript format throughout the project. The Animation also create a side pane of buttons and sliders that, depending on their functions, trigger changes in the Grid and/or Animation.

## Adding New Features

### New Simulation
Adding a new Simulation consists mainly of adding subclasses two specific structures:

* `State`: The State class is meant to hold all of the data that makes a cell special in a simulation. By default, a state is defined by a String identifier. Any object that stores a State can either retrieve the State's specific String or get a string representing the State's color in JavaScript form. If the State is more complicated than just a String, one can extend the class and implement more functionality there.

* `Rules`: The Rules class essentially implements all functionality of a Simulation. Its job is to return certain States depending on the scenario. This class must be extended in order to implement a simulation. Other than returning the default and starting states, the Rules class must be able to output a State given a certain neighborhood of cells, in which the Cell of focus is in the center of the neighborhood. It is important that each Rules class returns State objects that are Specific to the Simulation. To get specific parameters from XML files, the rules class has access to the configuration XML Parser. This allows it to search files for specific keyword parameters.

To complete the implementation of a Simulation, one must create an XML file with the specific class path of the Rules set in the `<subclass>` tag. Next, one must define in the file the color scheme, grid type, initial states, and a few other parameters pertaining to the Simulation. Finally, in the RulesLoader class, one must add the subclasses to the list of Rules subclasses in order for the class to be considered as an Animation to run. The simulation can now be run by selection the file in the Menu file loader.  


###New Configuration
Adding a new configuration is done by adding a new subclass for whatever type of feature you want to implement. If you wanted pentagonal grids you would make a subclass of GridImager and Neighborhood. The other area where more configuration can be added is within the XML file. In there you can add whatever new parameters you want to be a part of the simulation, and whatever color schemes you want.

## Design Choices

How a Cell Sees Its Neighbors
Because the Cell's relationship with its neighboring Cells is fundamental to the function of any simulation, we decided to create a structure that allowed Cells to interact more smoothly with surrounding Cells, without leaving all of the implementation to the Grid. By creating a Neighborhood class, we decided that we would be able to more easily integrate the interaction between the Grid of all Cells and each individual Cell. In the first sprint, we implemented it such that for each Cell in the Grid, the Grid would manually have to create the Cells neighborhood structure relative to the Cells' positions (leaving the position null if the Cell was on an edge). During the second sprint, we decided to give the Neighborhood structure a higher level of responsibility. By adding the local-global coordinate system (which I described previously), we were able to more cleanly implement the passing of neighbors from the Grid to each individual Cell. Finally, by this neighborhood object to Cells, we were able to control the amount of information each Cell receives, carefully making sure that each Cell does not receive too much information.
Splitting the Grid and GridImager
During this project we tried to maintain the theme of completely decoupling the User Interface and Model. Originally, we decided not to do this, but after a couple lectures we decided to revise our design. We separated every class into the two categories, except the Grid, which was the only class that handled both responsibilities. After even more discussion, we decided to split the Grid into tow classes, the Grid (model) and the Grid Imager. The Grid is responsible for maintaining every Cell and every State of the Cells, while the Grid Imager is responsible for placing the images of the Cells in the GUI. There is limited interaction between the two classes. The only interaction left is as follows:
The Grid Imager holds the grid
The Grid Imager tells the grid to update every iteration of the Animation
The Grid Imager can access the Grid's Cells' colors at every iteration

