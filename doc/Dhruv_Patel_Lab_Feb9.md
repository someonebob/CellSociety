# Lab, Feb 9

## Decision 1:
In the XML parser file, we had repeated code to get the grid's row and column parameters. However, we had a getParameter method already that works for anything, and we don't use the getRow and getColumn methods anyway, so I just deleted them.

## Decision 2:
Also in the parser, we have two methods: one that gets an attribute from a tag, and one that gets the contents of a tag from the attributes. However, they use very different logic and functionalities, so I decided that leaving it how it was would be more simple and elegant than trying to remove repetition.