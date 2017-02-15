# Cell Society
Nathaniel Brooke
Dhruv Patel
Jesse Yue

January 28, 2017  - February 12, 2017
Hours worked: ~30 each

## Roles
**Nathaniel**:
Worked on the initial implementation of the `Grid` and `Animation` classes in backend and created the WaTor simulation.   In the second sprint, created the `GridImager`, `Graph` classes, and some additional animation controls.  Implemented error handling in the frontend.  

**Dhruv**:
Worked on mostly back end. Initially started by creating the `Cell`, `States`, and `Neighborhood` classes and created two simulations. In second sprint, refactored `Neighborhood` and `Grid` to encompass multiple grid shapes and  implemented different grid edge types, e.g. `toroidal` and `infinite`. 

**Jesse**:
Worked on mostly front end and bridging the gap between frontend and backend. In initial sprint created the main class `Window`, `Menu`, and `FireRules`. Also did the majority of xml stuff, making the class `XMLParser` and formatting xml files. In the second sprint created the gui for controlling the extra features in `Animation` as well as adding functionality to the `XMLParser` to support new features.

## Resources
Oracle, Stack Exchange

## How to start:
Run the `Window.java` file, located in the default package. This will open up a menu where you can choose to select an XML starter file. By selecting a file from the range of options, you can choose which simulation you want to run. During the simulation, you can use the toolbar and menu sidebar to control the simulation and change parameters.

## How to test
The program can be tested with any of the provided XML files in the *xml* directory within the project.  

## Required Resource Files
Within the *images* directory is the main menu background image, which is required for the program to run
Within the `resourcefiles` package are four Resource bundle files, all of which are required for the program to run. 
The program can run without an XML file from the *data* directory, however all the interesting features of the program only appear when a properly formatted XML file is present and selected through the file chooser accessible via the program main menu.

## Program Information
This program is functionally simple.  After starting the program, an elegant, ergonomic, minimalistic menu window appears.  In this beautifully designed menu are two options: *Load File* and *Quit*.  Selecting *Quit* efficiently exits the program.  Selecting *Load File* opens a file selection pane that automatically displays all files in the *data* folder, giving the user a choice from an array of well-designed and gorgeous simulations.  

Upon selecting a simulation and pressing *open*, the user will be presented with a new screen.  In the center of the screen will be the beautifully rendered animation, already playing and ready for the user’s enjoyment.  

Along the top of the screen are a range of options, including a *Menu* button to return to the main menu, a *New* button to open a new simulation in front of the current simulation, allowing the user to view two magnificent simulations simultaneously, a *Step* button that allows the user to increment the simulation by one step, a *Play/Pause* button that, one would hope, is self-explanatory, a *reset* button that resets the simulation to the initial conditions from launch, a *restart* simulation that starts the simulation over without resetting all conditions, and a slider that allows the user to change the animation speed. 

To the left of the animation appears a large side menu.  A button in the top left corner on the top bar allows this menu to be minimized and maximized at the user’s whim.  On the menu are simulation-specific adjustments, including grid shape, edge types, color schemes, and specific parameters within the simulation that can be modified.  When the modification is complete, the simulation will automatically restart with the newly specified parameters in place.  The cell size in the animation can also be adjusted real time, and outlines can be added to the cells at any time during animation.  

Conveniently located along the bottom of the screen, a bar graph keeps track, real-time, of the number of each type of cell in the simulation, and conveniently plots those values.  A legend is provided to clarify the bar graph, as, unfortunately, colors of the graph do not always match up with colors in the animation.  

## Bugs and Crashes
Currently, the *Foraging Ants* simulation does not work properly. Most of the code is written, but due to a lack of time we were not able to fully finish it. Running the simulation will cause a runtime error, causing the program to crash.

## Extra Features
Retractable menu, so you can see two simulations at the same time
UI resizes itself to fit your computer’s window
Neighbor passing for different neighbor shapes is very accurate

## Assignment Impressions
This was a fun assignment, but the extra simulations and features were too time consuming. If we had the time, it would have been much more enjoyable, but the time constraint was too small on top of other classes.

## Easter eggs
If you go on `Segregation`, you can choose a fun “toilet” color scheme.
If you are color-blind, do not choose the `Game of Life`, “color blind” color scheme.