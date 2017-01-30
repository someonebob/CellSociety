
Cell Society Planning
==================
# Introduction
The main goal of our design is to be able to add as many different cellular automaton models as we can, making it as easy as possible for someone to add their own model to the working system. Models can be customized in two different ways: 

1. **States**: A state consists of all the values that make a certain cell unique in a grid. Because different models consider different aspects of each cell, one must be able to define a cell state easily when creating a new model.

2. **Rules**: Rules determine how a cell acts given its surroundings because cells in different models act differently, defining a new set of rules is also a crucial part of making a new model, and therefore it should also be easy to do.
# Overview

## Cell
**Methods:**
> Cell(Rules rules, State startingState)
> void setNeighbors(Cell[3][3] neighbors)
> void calculateFutureState()
> void refreshState()
> State getCurrentState()


**Descriptions:**

 * **Cell:** Creates Cell object, given a set of rules, and sets its initial state
 * **setNeighbors:** Locally stores a 3x3 array of all neighboring cells
 * **calculateFutureState:** Uses neighboring cell states to calculate the next state of the cell. Does not update since it must wait for all other cells to calculate their future state
 * **refreshState:** Updates current state given most recent future state
 * **getCurrentState:** Returns cell's current state

## Grid
**Methods:**
> Grid(File setupInformation)
> Group getGroup()
> void nextFrame()

**Descriptions:**

 * **Grid:** Defines a Rule set using information specified in setupInformation file. Creates an NxN array of Cells as specified by the rule set and the setup information file. 
 *  **getGroup:** Returns JavaFX Group containing Nodes of all Cell 
 * **nextFrame:** Calculates all future cell states, refreshes all cell states, and replaces all Nodes inside its group.
 
## Rules
**Methods:**
>  Rules getRules(String name)
>  abstract State getStartingState(String stateText)
>  abstract State getNewState(State[3][3] cellStates)

**Descriptions:**

 * **getRules:** gets a concrete instance of Rules with the specified name
 *  **getStartingState:** gets an initialized state associated with this rule set as specified by the given text
 * **getNewState:** calculates a cell's new state based on the cell and its neighbors' current states.  


## State
**Methods:**
> abstract Node getStateNode()

**Descriptions:**

 * **getStateNode:** returns a JavaFX Node to represent the state of the cell onscreen

## Menu
**Methods:**

> Scene initialize()
> File getFile()

**Descriptions:**

* **initialize:** sets up the scene for the menu.
* **getFile:** gets an XML file containing a simulation to run.


## Animation 
**Methods:**

> Scene initialize()
> void runAnimation(File setupInfo)

**Descriptions:**

* **initialize:** sets up the scene for the animation.
* **runAnimation:** runs the cellular automata animation specified in the given file, until the user returns to the menu.  


## Window

**Methods:**

> main
> start(Stage s)

**Descriptions:**

* **main:** launches the application.
* **start:** loops between selecting a file and running the animation based on that file.  



# User Interface
> When the program is run a window will open up with a start up screen that has two buttons centered in the window. The title will be above the buttons and there will be a background with some bubbly cells. The first button will read "Load File" and upon clicking it will open up a file explorer that allows you to choose an XML file to run. The second button will read "Quit" and quits the program. After loading a file the scene will change and start the animation of the cells. There will be a button at the top left that reads "Menu" which lets the user return to the menu screen where they can choose a new file or quit. At the top right there will be either a slider or some buttons that allows the use to speed up or slow down the animation. Lastly the user will be able to press any key to pause or play the animation.

>Errors will be reported to the user through pop up windows that has an error number and a description of what the error was (empty data, bad formatting).
# Design Details

## Cell
**Methods (Private Included):**
> public Cell(Rules rules, State startingState)
> public void setNeighbors(Cell[3][3] neighbors)
> public void calculateFutureState()
> public void refreshState()
> public State getCurrentState()
> private State[3][3] getNeighborStates()

**Descriptions:**

 * **Initialize:** This creates a new Cell object. It links a private variable `currentState` to the cell's state and also a private variable `rules` to its rule set.  It also creates a private variable `futureState` to contain the Cells future state. Finally, it initializes an initially empty 3x3 array of Cells `neighbors`, which will be filled with the cell's neighbors once all neighbors are initialized.
 * **setNeighbors:** This sets the private 3x3 array of Cells `neighbors` to the array passed in the argument.
 * **getNeighborStates():** This initially creates a 3x3 array of States `neighborStates`. It then loops through the private array of neighbors. If the neighbor is not null, it calls the `getCurrentState()` method of the neighbor. If the neighbor's state is not null (and the neighbor itself is not null), it fills the 3x3 array of States with the neighbor's state. Otherwise, it keeps the State at the current indices *null*. It returns `neighborStates`.
 * **calculateFutureState:** This uses the cell's private `getNeighborStates()` method to pass into it's neighbor states into its Rule object's `getNewState()` method. It then sets it's `futureState` to the output of `getNewState()`.
 * **refreshState:** This sets `currentState`, to `futureState`.
 * **getCurrentState:** This returns `currentState`.
 
## Grid
**Methods (Private Included):**
> public Grid(File setupInformation)
> public Group getGroup()
> public void nextFrame()
> private Rules getRules()
> private void initializeArray()
> private void passNeighbors()
> private void updateGroup()

**Design Details:**

 * **Constructor:** The constructor for Grid takes in a File containing the setup information.  From this XML file, it extracts the name of the concrete subclass of Rules that will be used in this simulation.  It then uses the getRules method in Rules to get the concrete subclass of rules used for this simulation.  Next, it begins initialization of the 2D array of Cell objects used for the simulation.  From the XML file, the dimensions of the array are extracted and used to initialize the array.  The array is then filled with new Cells, each of which is passed the previously extracted Rules.  In addition to the rules, from the XML file, the string representation of the state of each cell is read, and a new State representing that state is created using the method getNewState found in Rules.  A new state for each cell is passed in the constructor along with the rules.  Once every cell has been initialized, every cell is passed its eight surrounding neighbors using the method setNeighbors found in Cell.  The final step of the constructor is to initialize the JavaFX group containing visualizations of all Cells.  The entire array of cells will be iterated over, and the JavaFX Node representing the State of each Cell will be arranged in a Group based on the location of each Cell.  Once this is complete, the constructor is finished and the entire grid has been fully initialized, and can be added to the Animation window through the JavaFX Group that was created.  Each separate process described above will be extracted in its own private method listed above, called in order from the constructor.  
 *  **getGroup:** This method will return the JavaFX Group containing Nodes visualizing all the Cells, initialized in the constructor and stored as a field within Grid.   
 * **nextFrame:** This method will be used to update the graphics and data structure to the next frame in the animation.  First, every cell in the 2D array will have its calculateFutureState method called.  When this is done, every Cell in the 2D array will then have its refreshState method called, updating the data structure to the next frame in the animation.  When this is complete, the Group representing the data structure in the animation will be cleared and updated using the private method described at the end of the Grid constructor.

## State
 
**Methods:**
> public abstract Node getStateNode()

**Descriptions:**

 * **getStateNode:** This method will be implemented differently in every subclass, but it will create a JavaFX Node (usually a Shape or ImageView) that represents the current state. It will consider all its private variables and make a decision on what to display. It will then cast the object as a Node to ensure compatibility with higher level objects.

**Format for subclasses:**

Subclasses will contain a series of *getter* and *setter* methods to hold and set data structures pertaining to the state type. For example, if a state is defined by RGB colors, one has the option to have a method to get and set each component color value or simply get and set one array of all values. Subclasses may also contain private helper methods to determine what the State Node should be.

## Rules
**Methods (Private Included):**
>  public Rules getRules(String name)
>  public abstract State getStartingState(String stateText)
>  public abstract State getNewState(State[3][3] cellStates)

**Design Details:**

 * **getRules:** This method will take in the name of the concrete subclass of Rules, and will then return an instance of that subclass.  Current plans for implementation utilize an if tree checking the provided name against all possible subclasses, however we as a group intend to investigate ways of implementing this without needing a list of all possible subclasses of Rules, and to instead initialize an instance of the subclass from the String representation of the class name alone.  We are not yet sure whether this is possible.  
 *  **getStartingState:** gets an initialized state associated with this rule set as specified by the given text.  This is used to initialize concrete subclasses of State without requiring a list of all possible States.  Since any concrete subclass of Rules will only work with one type of State, the concrete subclass can easily provide an initialized instance of the specific  concrete subclass of State that that rule set works with, allowing us to abstract away the determination of what State is used, making it easy to implement new Rules and States in code without needing to modify any existing code to add a new State.  
 * **getNewState:** calculates a cell's new state based on the states of the cell and its neighbors.  This method requires unique rules, depending on the model being used, for each state, and thus its function must be abstracted to concrete subclasses of Rules because each subclass will have a different means of calculating the next state of a cell.    

## Menu
**Methods:**

> Scene initialize()
> File getFile()

**Design Details:**

* **initialize:** This method sets up the scene for the menu screen in the program. It will have a root for the title, the load and quit buttons, and a background. The buttons will use lambda expressions to fulfill their necessary jobs. Upon clicking the load button, a second window will be created that allows the user to choose which file to load into the program. This method will be responsible for creating that window and scene as well.
*  **getFile:** This method returns the file that was chosen by the user from the menu screen. It will be taken in by the Animation class to use for the simulation.

## Animation 
**Methods (Private Included):**

> public Scene initialize()
> public void runAnimation(File setupInfo)
> private void setupAnimation()
> private void setupControls()

**Design Details:**

* **initialize:** This sets up the Scene for the animation screen in the program.  This initializes the animation loop, which, when running, makes a call via a lambda expression to Grid's method nextFrame, which updates the Grid Group.  This method also creates a root Group for the Scene, placing the necessary buttons for adjusting the animation speed, pausing, and returning to the menu into the Group, and programming in the buttons' functions using lambda expressions.  It is split into two private methods, one to initialize the animation and one to initialize the buttons and controls.  
* **runAnimation:** This creates a new Grid based on the given file, then loads the Grid's Group into the Animation Group.  It then starts the animation, and waits until the user requests to return to the menu.  When this request occurs, this method stops the animation, removes the Grid Group from the Animation group, and returns.   

## Window
**Methods:**
> main
> start(Stage s)

**Design Details:**

* **main:** This just calls launch(args) to start the application
* **start:** This method will create an instance of Menu and set the stage to display the menu scene. It also sets a title and shows the stage.

## Use Cases

* To apply the rules to a middle cell we call the calculateFutureState method on the cell. The cell will already know which set of rules to apply because it is passed in as a parameter to the cell constructor.
* To apply the rules to an edge cell we do the same thing. The setNeighbors method accounts for edge cells by checking if there is a null pointer or index out of bounds error through a try/catch. Then the calculateFutureState method only takes into account the neighboring cells that are not null.
* To update all the states we call refreshState on all the cells after the future state of each has been calculated. Then the nextFrame method is called on the grid to update the graphics of the simulation.
* The rules subclass dedicated to the fire simulation will take in a String that has been parsed from the XML file with the specifics of the rules. This String will be coming from the Grid class.
* To switch simulations, there will be a button in the UI that lets the user go back to the menu screen and choose a new file to load up.

# Design Considerations


## Location Information
We discussed at length where the location (x and y coordinates) of a cell should be stored. 

Our two options were to either store the x and y coordinates of a cell as parameters inside the Cell class, or to use the row and column that the cell was located at within the 2D array of cells in the Grid class to determine the location.  The first option would allow for easy access of the location from within Cell but would require more parameters to be passed in when creating cells.  The second option simplified Cell, but required that all location-dependent processes take place within Grid rather than Cell.  

We chose the second option, leaving the coordinates of a cell outside of the Cell class, because processes that depend on the location of a cell within the grid of cells belonged in the Grid class rather than the Cell class.  

## Determining Rule Set and States
Each different CA model has a different rule set, and different possible states.  For our program to work with any rule set and states, all code except for subclasses of Rules and State should work with only the abstract classes Rules and State.  When initializing the Cells in Grid, we needed a way to set their initial State with a concrete subclass of State, and pass them the correct concrete subclass of Rules.  

The subclasses of Rules and State needed to be listed somewhere such that subclasses of Rules and State could be initialized during setup.  We debated where to put the code that initialized these concrete subclasses, because we did not want to hard-code specific rules and states into our more general classes like Cell, Grid, or Animation.  Thus we needed to find a good location to initialize these classes, that could be easily added to without editing the main body of the program.  

We created an abstract method in Rules that initializes State based on input from the XML file.  Since a certain rule set only applies to a specific state, the specific rule set can instantiate the correct State for the program to use.  To instantiate the Rules set, we have decided to create a method in Rules that, given an input from the XML file, will initialize the correct subclass of Rules to be used by the rest of the game.  This one method may need a list of all possible Rules subclasses, but we feel that this list is best placed in the Rules class than anywhere else in the program.  

## Neighbors Information
The progress of the simulation greatly depends on the neighbors of each cell, as they are used in calculating the next state, so the way in which we stored them was a big decision. We debated between having the neighbors being found in Grid vs in Cell. Grid would make sense because it holds the 2D array of cells and could easily look at the neighboring cells and store them. But ultimately we decided that from a design standpoint, the cell should know its own neighbors, so it should be contained within Cell.

A pro of our choice is that it will be much easier to call the neighbors of a cell whenever it is needed since it will be contained within cell. However, the con is that we still have to find the neighbors in Grid and pass it into cell as a parameter, so it is a little extra work.

## Rules-State Interaction

There are two factors that distinguish all simulation types. Firstly, simulation types can differ in the rules that each cell follows to go from one frame to the next. Secondly, different simulations can have different definitions of what a state is, and what variables each cell should store. Therefore, in order to create a modular design, we must make it as easy and intuitive as possible to define a new set of rules and states. We discussed various ways to integrate both rules and states in a way that makes our design modular.

One way to integrate these two concepts is to create one combined abstract class as a framework to define both rules and state. This design would be the easiest to add to because to design a new type of simulation, one would only have to create one class and link it to one other spot in the Grid class. However, this design assumes that each possible state can only have one possible rule set. To define a rule set for an already existing state, one would have to define the same state again, forcing code repetition.

Another way to deal with this would be to have two separate abstract classes: one for each Rule and State, that both link to grid. Although this would allow much more flexibility in the rule-state interaction, creating a new simulation would force one to create two classes and link them in two spots for most cases. It also leaves room for compatibility errors, because every rule set only works with a certain state set.

Our final consideration, which we eventually agreed to keep, is to have two separate classes, but link to a particular State type in each rule set. This is optimal because it allows for flexibility in creating multiple rule sets for the same state set, while making compatibility errors impossible (assuming each rule set references the correct state set). It still forces one to create two classes for most new simulations, but conceptually it makes sense to separate the two classes as two different ideas anyway.


## The Scope of the "root" Group

In order to visibly add a Node to the JavaFX screen, the Node must be added in some way to the "root" group. We debated shortly about whether the Cell, Grid, or Animation should be responsibility for doing this.

Conceptually, it makes sense for the *Cell* class to add itself to root. This makes it easier for higher level classes to deal with groups of *Cells*, without worrying about the details of JavaFX implementation. However, because *Grid* holds *Cell* and Animation holds *Grid* and *root*, *root* would have to be passed in the parameters of both Grid and *Cell* to perform this functionality. Because *root* is a such a core component of animation, it may be dangerous to pass it to lower-level objects (We don't want to give one Cell the ability to kill all other cells).

Another possibility is to only pass *root* into *Grid* and allow *Grid* the control to add objects. However, it does not escape the problem at hand; it just lessens its impact. 

To truly keep *root* secure, we decided to keep it exclusively in the *Animation* class. *Grid* creates its own "sub-root" object, which contains all cell Nodes. When updated, *Grid* passes this "sub-root" up to *Animation*. This ensures that all objects can only control other objects within their scope. Consequently, it also allows *Animation* to simultaneously control all objects in *Grid*, because it creates a "sub-group". One negative aspect of this structure is that it forces higher-level objects to implement slightly lower-level functions. 

We chose to stick with the final idea because it follows the most responsible access structure. Although it may make things slightly more difficult for higher-level objects like *Animation* and *Grid*, it prevents dangerous functionalities from being created by lower-level objects like *Cell*.

## Screen Organization
Originally we wanted all the functionality of Window, Menu, and Animation all in the window class. It made sense to have everything regarding what's displayed on the screen in one class. However, we felt that it was too much responsibility for one class and decided to split it up into a main class that launches the program, a menu class that sets up the splash screen, and an animation class that handles all the timeline elements.

# Team Responsibilities

We have decided to divide programming up by class.  All specified methods in the Overview must be implemented and public, and all other classes will have access to those methods alone.  

Primary responsibilities, as listed below, are set to be finished by Thursday's class. Unassigned responsibilities will be divided up and finished by Friday. If something does not get completed by Wednesday, group member will alert team, and work will be divided into secondary responsibilities.

### Dhruv
> *  Cell
> *  State
> * State/Rules: *Game of Life*

## Nathaniel
> * Grid (secondary Dhruv)
> * Animation
> * Rules

## Jesse
> * Menu
> * Window
> * State/Rules: *Fire*

## Unassigned
> * State/Rules: *Segregation*
> * State/Rules: *Predator-prey*