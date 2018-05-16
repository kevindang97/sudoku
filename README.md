# Sudoku

This is a little Sudoku player I made that can load in text files to play Sudoku games. I will eventually add in the ability to generate new, random Sudoku grids of varying difficulties.

For now, Sudoku input files can be used, which consist of plain text files with the extension (.sud). They must consist of 81 numbers between 0 to 9 inclusive, each separated by whitespace. The number 0 is a special number that represents an empty space on the grid.

## Set-up
* To be able to import this into your IDE of choice, import it as an existing Maven project
* For Eclipse, go to File -> Import -> Existing Maven Project
* Not sure about other IDEs but if there are compability errors then put up an issue and I'll attempt to fix it

## Current plan of additional features:
* Have a better win screen
* Have more consistent keyboard input (currently, clicking the on-screen input pad removes focus off the grid and prevents keyboard input)
* Options that allow existing features (such as number highlighting or the colours used) to be toggled
* Add support for sub numbers
* Grid generation with varying difficulties: https://dlbeer.co.nz/articles/sudoku.html
* (Maybe) Auto resizing support
* (Maybe) Make it prettier
