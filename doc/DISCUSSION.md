# Peer Code Review
> NEB17; HKL6

## Harry’s Code

GamePage/cellsociety_team01/src/page/GamePage.java

```java
private void stepButton(ActionEvent event) { 
      if (simulationSelected){ 
         this.getCellSociety().setIsStep(true); 
         this.getCellSociety().setNextStep(true); 
         this.getCellSociety().beginGameLoop(); 
      } 
      else{ 
         Alert alert = new Alert(AlertType.ERROR, getMyResources().getString("SelectCommand")); 
         alert.showAndWait(); 
      } 
   } 
```
We decided to refactor this code because two separate alerts were being created when the alerts were the same. This alert is created when the user hits the start or step button before selecting an animation to run. In order to address this, we decided to use the extract method technique and replace the old code with a call to the newly created method. The new method `displayAlert (String prompt)` also was created such that you can display any type of alert and not just the one prompted from hitting the start or step button. This makes the code more readable and independent and allow for multiple alerts to be created easily.


AnimationSegregation/cellsociety_team01/src/animation/AnimationSegregation.java

```java
private class Coord { 
      int x; 
      int y;        
      private Coord (int w, int z) { 
         x  = w; 
         y = z; 
      }        
      private int getX () { 
         return x; 
      } 
      private int getY() { 
         return y; 
      } 
   } 
```
The code above was duplicated word for word in two separate classes. We had to choose this because it was practically begging to be refactored. The technique we used was extract class. We created a new class called Coord and deleted the code in the subclasses.



## Nathaniel’s Code

/cellsociety_team02/src/simulation/FireRules.java

```java

if(neighbors.get(2,1) != null)
states.add((FireState) neighbors.get(2,1).getCurrentState());

```

The above code or similar was repeated 4 times in FireRules.  To refactor, I created a method in Neighborhood to access only the adjacent cells rather than all neighboring cells.  I then used this method to access only the adjacent cell states within FireRules, and since FireState held nothing but a few constants, I moved the constants into FireRules and deleted FireState.  


/cellsociety_team02/src/xml/XMLParser.java

```java

public int getGridColumns() { 
gridColumns = Integer.parseInt(getRootElement().getElementsByTagName(DATA_FIELDS.get(1)).item(1).getTextContent()); 
return gridColumns; }

```

The above code was very similar to the getGridRows method also implemented within XMLParser.  It turns out, though, that this method and some other similar methods in XMLParser had been replaced with a more general method called getParameter(String tagName) and the methods with duplicated code were never actually used.  I deleted those methods entirely, reducing both the duplication and the clutter in XMLParser.  
