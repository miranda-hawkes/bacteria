# Bacteria

1. Any live bacteria cell with fewer than two live neighbours dies, as if caused
   by under-population
2. Any live bacteria cell with two or three live neighbours lives on to the next
   generation.
3. Any live bacteria cell with more than three live neighbours dies, as if by
   overcrowding.
4. Any dead bacteria cell with exactly three live neighbours becomes a live
   bacteria cell, as if by reproduction.

## Assumptions

1. Petri dish is of 'infinite' size but input coordinates cannot go below (0,0)

   > e.g. cell (-1,0) is not considered a valid 'neighbour' of cell (0,0)