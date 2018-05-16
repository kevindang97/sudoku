# Sudoku

This is a little Sudoku player I made that can load in text files to play Sudoku games. I will eventually add in the ability to generate new, random Sudoku grids of varying difficulties.

For now, Sudoku input files can be used, which consist of plain text files with the extension (.sud). They must consist of 81 numbers between 0 to 9 inclusive, each separated by whitespace. The number 0 is a special number that represents an empty space on the grid.

## Current plan of additional features:
* Have a better win screen
* Have more consistent keyboard input (currently, clicking the on-screen input pad removes focus off the grid and prevents keyboard input)
* Options that allow existing features (such as number highlighting or the colours used) to be toggled
* Add support for sub numbers
* Grid generation with varying difficulties: https://dlbeer.co.nz/articles/sudoku.html
* (Maybe) Auto resizing support
* (Maybe) Make it prettier

## Development

Sudoku is structured in a way that supports being imported as a [Maven project][maven] into your IDE of choice.

Supported IDEs include:

### Eclipse

Sudoku can be imported into [Eclipse][eclipse] as a Maven project. Once cloned, in Eclipse,

1. Select `File -> Import`,
1. Select `Maven -> Existing Maven Projects`,
1. Select `Browse`, and
1. Navigate to, and select, the cloned directory where `pom.xml` exists.
1. Click `Finish`.

[eclipse]: https://www.eclipse.org
[maven]: https://maven.apache.org/what-is-maven.html
