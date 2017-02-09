
Lab Feb 9
=========

jty4 team 2
kw238 team 17

Refactoring
-------
Jesse chose to refactor `FireRules` because it had a lot of duplicated code to get the neighbors. He removed the multiple get methods by refactoring the `getNeighbors` method in `Neighborhood` to take in a String that indicated whether you wanted all neighbors or just adjacent neighbors. This way in `FireRules` he could just call `getNeighbors("adjacent")` to get all the adjacent neighbors instead of individually getting them.

Keping chose to refactor `WaTorRule` since there was duplication in cases for fish and sharks. He did this through creating a method that took in an integer parameter representing the number of births.