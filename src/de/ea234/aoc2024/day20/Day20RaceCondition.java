package de.ea234.aoc2024.day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import de.ea234.util.FkStringFeld;

public class Day20RaceCondition
{
  /*
   * --- Day 20: Race Condition ---
   * https://adventofcode.com/2024/day/20
   * 
   * https://www.youtube.com/watch?v=NE3ftNlfkdw
   * 
   *  
   * ----------------------------------------------------------------------------------------------------------------
   *  
   * 
   * ########
   * #...#.E#
   * #.#.#.##
   * #S#...##
   * ########
   * 
   * Start at R3C1
   * 
   * 
   * #### FOUND CHEAT POINT START ##### Nr 1  Direction RIGHT_   From R3C3 To R3C2
   * Laenge      2  Coordinates From R3C1       To R2C1        . UP
   * #### FOUND CHEAT POINT START ##### Nr 2  Direction RIGHT_   From R2C3 To R2C2
   * Laenge      3  Coordinates From R2C1       To R1C1        . UP
   * Laenge      4  Coordinates From R1C1       To R1C2        . RIGHT
   * Laenge      5  Coordinates From R1C2       To R1C3        . RIGHT
   * #### FOUND CHEAT POINT START ##### Nr 3  Direction RIGHT_   From R1C5 To R1C4
   * Laenge      6  Coordinates From R1C3       To R2C3        . DOWN
   * #### FOUND CHEAT POINT END ##### Nr 2  Direction RIGHT_   To R2C3
   * #### FOUND CHEAT POINT START ##### Nr 4  Direction RIGHT_   From R2C5 To R2C4
   * Laenge      7  Coordinates From R2C3       To R3C3        . DOWN
   * #### FOUND CHEAT POINT END ##### Nr 1  Direction RIGHT_   To R3C3
   * Laenge      8  Coordinates From R3C3       To R3C4        . RIGHT
   * Laenge      9  Coordinates From R3C4       To R3C5        . RIGHT
   * Laenge     10  Coordinates From R3C5       To R2C5        . UP
   * #### FOUND CHEAT POINT END ##### Nr 4  Direction RIGHT_   To R2C5
   * Laenge     11  Coordinates From R2C5       To R1C5        . UP
   * #### FOUND CHEAT POINT END ##### Nr 3  Direction RIGHT_   To R1C5
   * Laenge     12  Coordinates From R1C5       To R1C6        E RIGHT
   * 
   * 
   * 
   *     0  ########            
   *     1  #...#.E#     xxx-xE 
   *     2  #.#.#.##     x-x-x  
   *     3  #S#...##     x-xxx  
   *     4  ########            
   * 
   * 
   *     1  RIGHT_R2C1 => CNR      2 Len      2 Start R2C1       To R2C3         2 - 6 = -2
   *     2  RIGHT_R2C3 => CNR      4 Len      2 Start R2C3       To R2C5         6 - 10 = -2
   *     3  RIGHT_R3C1 => CNR      1 Len      4 Start R3C1       To R3C3         1 - 7 = -4
   *     4  RIGHT_R1C3 => CNR      3 Len      4 Start R1C3       To R1C5         5 - 11 = -4
   * 
   *     1      1 CNR      1 Len      4 Start R3C1       To R3C3         1 - 7 = -4
   *     2      2 CNR      3 Len      4 Start R1C3       To R1C5         5 - 11 = -4
   * 
   *     3      1 CNR      2 Len      2 Start R2C1       To R2C3         2 - 6 = -2
   *     4      2 CNR      4 Len      2 Start R2C3       To R2C5         6 - 10 = -2
   *  
   * ----------------------------------------------------------------------------------------------------------------
   *  
   * ###############
   * #...#...#.....#
   * #.#.#.#.#.###.#
   * #S#...#.#.#...#
   * #######.#.#.###
   * #######.#.#...#
   * #######.#.###.#
   * ###..E#...#...#
   * ###.#######.###
   * #...###...#...#
   * #.#####.#.###.#
   * #.#...#.#.#...#
   * #.#.#.#.#.#.###
   * #...#...#...###
   * ###############
   * 
   * #### FOUND CHEAT POINT START ##### Nr 1  Direction RIGHT    From R3C3 To R3C2
   * #### MOVE #### Laenge      2  Coordinates From R3C1       To R2C1        . UP
   * #### FOUND CHEAT POINT START ##### Nr 2  Direction RIGHT    From R2C3 To R2C2
   * #### MOVE #### Laenge      3  Coordinates From R2C1       To R1C1        . UP
   * #### MOVE #### Laenge      4  Coordinates From R1C1       To R1C2        . RIGHT
   * #### MOVE #### Laenge      5  Coordinates From R1C2       To R1C3        . RIGHT
   * #### FOUND CHEAT POINT START ##### Nr 3  Direction RIGHT    From R1C5 To R1C4
   * #### MOVE #### Laenge      6  Coordinates From R1C3       To R2C3        . DOWN
   * #### FOUND CHEAT POINT END ##### Nr 2  Direction RIGHT    To R2C3
   * #### FOUND CHEAT POINT START ##### Nr 4  Direction RIGHT    From R2C5 To R2C4
   * #### MOVE #### Laenge      7  Coordinates From R2C3       To R3C3        . DOWN
   * #### FOUND CHEAT POINT END ##### Nr 1  Direction RIGHT    To R3C3
   * #### MOVE #### Laenge      8  Coordinates From R3C3       To R3C4        . RIGHT
   * #### MOVE #### Laenge      9  Coordinates From R3C4       To R3C5        . RIGHT
   * #### FOUND CHEAT POINT START ##### Nr 5  Direction RIGHT    From R3C7 To R3C6
   * #### MOVE #### Laenge     10  Coordinates From R3C5       To R2C5        . UP
   * #### FOUND CHEAT POINT END ##### Nr 4  Direction RIGHT    To R2C5
   * #### FOUND CHEAT POINT START ##### Nr 6  Direction RIGHT    From R2C7 To R2C6
   * #### MOVE #### Laenge     11  Coordinates From R2C5       To R1C5        . UP
   * #### FOUND CHEAT POINT END ##### Nr 3  Direction RIGHT    To R1C5
   * #### MOVE #### Laenge     12  Coordinates From R1C5       To R1C6        . RIGHT
   * #### MOVE #### Laenge     13  Coordinates From R1C6       To R1C7        . RIGHT
   * #### FOUND CHEAT POINT START ##### Nr 7  Direction RIGHT    From R1C9 To R1C8
   * #### MOVE #### Laenge     14  Coordinates From R1C7       To R2C7        . DOWN
   * #### FOUND CHEAT POINT END ##### Nr 6  Direction RIGHT    To R2C7
   * #### FOUND CHEAT POINT START ##### Nr 8  Direction RIGHT    From R2C9 To R2C8
   * #### MOVE #### Laenge     15  Coordinates From R2C7       To R3C7        . DOWN
   * #### FOUND CHEAT POINT END ##### Nr 5  Direction RIGHT    To R3C7
   * #### FOUND CHEAT POINT START ##### Nr 9  Direction RIGHT    From R3C9 To R3C8
   * #### MOVE #### Laenge     16  Coordinates From R3C7       To R4C7        . DOWN
   * #### FOUND CHEAT POINT START ##### Nr 10  Direction RIGHT    From R4C9 To R4C8
   * #### MOVE #### Laenge     17  Coordinates From R4C7       To R5C7        . DOWN
   * #### FOUND CHEAT POINT START ##### Nr 11  Direction RIGHT    From R5C9 To R5C8
   * #### MOVE #### Laenge     18  Coordinates From R5C7       To R6C7        . DOWN
   * #### FOUND CHEAT POINT START ##### Nr 12  Direction RIGHT    From R6C9 To R6C8
   * #### MOVE #### Laenge     19  Coordinates From R6C7       To R7C7        . DOWN
   * #### FOUND CHEAT POINT START ##### Nr 13  Direction DOWN     From R9C7 To R8C7
   * #### FOUND CHEAT POINT START ##### Nr 14  Direction LEFT     From R7C5 To R7C6
   * #### MOVE #### Laenge     20  Coordinates From R7C7       To R7C8        . RIGHT
   * #### FOUND CHEAT POINT START ##### Nr 15  Direction DOWN     From R9C8 To R8C8
   * #### MOVE #### Laenge     21  Coordinates From R7C8       To R7C9        . RIGHT
   * #### FOUND CHEAT POINT START ##### Nr 16  Direction DOWN     From R9C9 To R8C9
   * #### FOUND CHEAT POINT START ##### Nr 17  Direction RIGHT    From R7C11 To R7C10
   * #### MOVE #### Laenge     22  Coordinates From R7C9       To R6C9        . UP
   * #### FOUND CHEAT POINT END ##### Nr 12  Direction RIGHT    To R6C9
   * #### MOVE #### Laenge     23  Coordinates From R6C9       To R5C9        . UP
   * #### FOUND CHEAT POINT END ##### Nr 11  Direction RIGHT    To R5C9
   * #### FOUND CHEAT POINT START ##### Nr 18  Direction RIGHT    From R5C11 To R5C10
   * #### MOVE #### Laenge     24  Coordinates From R5C9       To R4C9        . UP
   * #### FOUND CHEAT POINT END ##### Nr 10  Direction RIGHT    To R4C9
   * #### FOUND CHEAT POINT START ##### Nr 19  Direction RIGHT    From R4C11 To R4C10
   * #### MOVE #### Laenge     25  Coordinates From R4C9       To R3C9        . UP
   * #### FOUND CHEAT POINT END ##### Nr 9  Direction RIGHT    To R3C9
   * #### FOUND CHEAT POINT START ##### Nr 20  Direction RIGHT    From R3C11 To R3C10
   * #### MOVE #### Laenge     26  Coordinates From R3C9       To R2C9        . UP
   * #### FOUND CHEAT POINT END ##### Nr 8  Direction RIGHT    To R2C9
   * #### MOVE #### Laenge     27  Coordinates From R2C9       To R1C9        . UP
   * #### FOUND CHEAT POINT END ##### Nr 7  Direction RIGHT    To R1C9
   * #### MOVE #### Laenge     28  Coordinates From R1C9       To R1C10       . RIGHT
   * #### MOVE #### Laenge     29  Coordinates From R1C10      To R1C11       . RIGHT
   * #### FOUND CHEAT POINT START ##### Nr 21  Direction DOWN     From R3C11 To R2C11
   * #### MOVE #### Laenge     30  Coordinates From R1C11      To R1C12       . RIGHT
   * #### FOUND CHEAT POINT START ##### Nr 22  Direction DOWN     From R3C12 To R2C12
   * #### MOVE #### Laenge     31  Coordinates From R1C12      To R1C13       . RIGHT
   * #### MOVE #### Laenge     32  Coordinates From R1C13      To R2C13       . DOWN
   * #### MOVE #### Laenge     33  Coordinates From R2C13      To R3C13       . DOWN
   * #### FOUND CHEAT POINT START ##### Nr 23  Direction DOWN     From R5C13 To R4C13
   * #### MOVE #### Laenge     34  Coordinates From R3C13      To R3C12       . LEFT
   * #### FOUND CHEAT POINT END ##### Nr 22  Direction DOWN     To R3C12
   * #### FOUND CHEAT POINT START ##### Nr 24  Direction DOWN     From R5C12 To R4C12
   * #### MOVE #### Laenge     35  Coordinates From R3C12      To R3C11       . LEFT
   * #### FOUND CHEAT POINT END ##### Nr 21  Direction DOWN     To R3C11
   * #### FOUND CHEAT POINT END ##### Nr 20  Direction RIGHT    To R3C11
   * #### MOVE #### Laenge     36  Coordinates From R3C11      To R4C11       . DOWN
   * #### FOUND CHEAT POINT END ##### Nr 19  Direction RIGHT    To R4C11
   * #### MOVE #### Laenge     37  Coordinates From R4C11      To R5C11       . DOWN
   * #### FOUND CHEAT POINT END ##### Nr 18  Direction RIGHT    To R5C11
   * #### FOUND CHEAT POINT START ##### Nr 25  Direction DOWN     From R7C11 To R6C11
   * #### MOVE #### Laenge     38  Coordinates From R5C11      To R5C12       . RIGHT
   * #### FOUND CHEAT POINT END ##### Nr 24  Direction DOWN     To R5C12
   * #### FOUND CHEAT POINT START ##### Nr 26  Direction DOWN     From R7C12 To R6C12
   * #### MOVE #### Laenge     39  Coordinates From R5C12      To R5C13       . RIGHT
   * #### FOUND CHEAT POINT END ##### Nr 23  Direction DOWN     To R5C13
   * #### MOVE #### Laenge     40  Coordinates From R5C13      To R6C13       . DOWN
   * #### MOVE #### Laenge     41  Coordinates From R6C13      To R7C13       . DOWN
   * #### FOUND CHEAT POINT START ##### Nr 27  Direction DOWN     From R9C13 To R8C13
   * #### MOVE #### Laenge     42  Coordinates From R7C13      To R7C12       . LEFT
   * #### FOUND CHEAT POINT END ##### Nr 26  Direction DOWN     To R7C12
   * #### FOUND CHEAT POINT START ##### Nr 28  Direction DOWN     From R9C12 To R8C12
   * #### MOVE #### Laenge     43  Coordinates From R7C12      To R7C11       . LEFT
   * #### FOUND CHEAT POINT END ##### Nr 25  Direction DOWN     To R7C11
   * #### FOUND CHEAT POINT END ##### Nr 17  Direction RIGHT    To R7C11
   * #### MOVE #### Laenge     44  Coordinates From R7C11      To R8C11       . DOWN
   * #### MOVE #### Laenge     45  Coordinates From R8C11      To R9C11       . DOWN
   * #### FOUND CHEAT POINT START ##### Nr 29  Direction DOWN     From R11C11 To R10C11
   * #### FOUND CHEAT POINT START ##### Nr 30  Direction LEFT     From R9C9 To R9C10
   * #### MOVE #### Laenge     46  Coordinates From R9C11      To R9C12       . RIGHT
   * #### FOUND CHEAT POINT END ##### Nr 28  Direction DOWN     To R9C12
   * #### FOUND CHEAT POINT START ##### Nr 31  Direction DOWN     From R11C12 To R10C12
   * #### MOVE #### Laenge     47  Coordinates From R9C12      To R9C13       . RIGHT
   * #### FOUND CHEAT POINT END ##### Nr 27  Direction DOWN     To R9C13
   * #### MOVE #### Laenge     48  Coordinates From R9C13      To R10C13      . DOWN
   * #### MOVE #### Laenge     49  Coordinates From R10C13     To R11C13      . DOWN
   * #### MOVE #### Laenge     50  Coordinates From R11C13     To R11C12      . LEFT
   * #### FOUND CHEAT POINT END ##### Nr 31  Direction DOWN     To R11C12
   * #### MOVE #### Laenge     51  Coordinates From R11C12     To R11C11      . LEFT
   * #### FOUND CHEAT POINT END ##### Nr 29  Direction DOWN     To R11C11
   * #### FOUND CHEAT POINT START ##### Nr 32  Direction LEFT     From R11C9 To R11C10
   * #### MOVE #### Laenge     52  Coordinates From R11C11     To R12C11      . DOWN
   * #### FOUND CHEAT POINT START ##### Nr 33  Direction LEFT     From R12C9 To R12C10
   * #### MOVE #### Laenge     53  Coordinates From R12C11     To R13C11      . DOWN
   * #### MOVE #### Laenge     54  Coordinates From R13C11     To R13C10      . LEFT
   * #### MOVE #### Laenge     55  Coordinates From R13C10     To R13C9       . LEFT
   * #### FOUND CHEAT POINT START ##### Nr 34  Direction LEFT     From R13C7 To R13C8
   * #### MOVE #### Laenge     56  Coordinates From R13C9      To R12C9       . UP
   * #### FOUND CHEAT POINT END ##### Nr 33  Direction LEFT     To R12C9
   * #### FOUND CHEAT POINT START ##### Nr 35  Direction LEFT     From R12C7 To R12C8
   * #### MOVE #### Laenge     57  Coordinates From R12C9      To R11C9       . UP
   * #### FOUND CHEAT POINT END ##### Nr 32  Direction LEFT     To R11C9
   * #### FOUND CHEAT POINT START ##### Nr 36  Direction LEFT     From R11C7 To R11C8
   * #### MOVE #### Laenge     58  Coordinates From R11C9      To R10C9       . UP
   * #### FOUND CHEAT POINT START ##### Nr 37  Direction LEFT     From R10C7 To R10C8
   * #### MOVE #### Laenge     59  Coordinates From R10C9      To R9C9        . UP
   * #### FOUND CHEAT POINT END ##### Nr 16  Direction DOWN     To R9C9
   * #### FOUND CHEAT POINT END ##### Nr 30  Direction LEFT     To R9C9
   * #### MOVE #### Laenge     60  Coordinates From R9C9       To R9C8        . LEFT
   * #### FOUND CHEAT POINT END ##### Nr 15  Direction DOWN     To R9C8
   * #### MOVE #### Laenge     61  Coordinates From R9C8       To R9C7        . LEFT
   * #### FOUND CHEAT POINT END ##### Nr 13  Direction DOWN     To R9C7
   * #### MOVE #### Laenge     62  Coordinates From R9C7       To R10C7       . DOWN
   * #### FOUND CHEAT POINT END ##### Nr 37  Direction LEFT     To R10C7
   * #### MOVE #### Laenge     63  Coordinates From R10C7      To R11C7       . DOWN
   * #### FOUND CHEAT POINT END ##### Nr 36  Direction LEFT     To R11C7
   * #### FOUND CHEAT POINT START ##### Nr 38  Direction LEFT     From R11C5 To R11C6
   * #### MOVE #### Laenge     64  Coordinates From R11C7      To R12C7       . DOWN
   * #### FOUND CHEAT POINT END ##### Nr 35  Direction LEFT     To R12C7
   * #### FOUND CHEAT POINT START ##### Nr 39  Direction LEFT     From R12C5 To R12C6
   * #### MOVE #### Laenge     65  Coordinates From R12C7      To R13C7       . DOWN
   * #### FOUND CHEAT POINT END ##### Nr 34  Direction LEFT     To R13C7
   * #### MOVE #### Laenge     66  Coordinates From R13C7      To R13C6       . LEFT
   * #### MOVE #### Laenge     67  Coordinates From R13C6      To R13C5       . LEFT
   * #### FOUND CHEAT POINT START ##### Nr 40  Direction LEFT     From R13C3 To R13C4
   * #### MOVE #### Laenge     68  Coordinates From R13C5      To R12C5       . UP
   * #### FOUND CHEAT POINT END ##### Nr 39  Direction LEFT     To R12C5
   * #### FOUND CHEAT POINT START ##### Nr 41  Direction LEFT     From R12C3 To R12C4
   * #### MOVE #### Laenge     69  Coordinates From R12C5      To R11C5       . UP
   * #### FOUND CHEAT POINT END ##### Nr 38  Direction LEFT     To R11C5
   * #### MOVE #### Laenge     70  Coordinates From R11C5      To R11C4       . LEFT
   * #### MOVE #### Laenge     71  Coordinates From R11C4      To R11C3       . LEFT
   * #### FOUND CHEAT POINT START ##### Nr 42  Direction UP       From R9C3 To R10C3
   * #### FOUND CHEAT POINT START ##### Nr 43  Direction LEFT     From R11C1 To R11C2
   * #### MOVE #### Laenge     72  Coordinates From R11C3      To R12C3       . DOWN
   * #### FOUND CHEAT POINT END ##### Nr 41  Direction LEFT     To R12C3
   * #### FOUND CHEAT POINT START ##### Nr 44  Direction LEFT     From R12C1 To R12C2
   * #### MOVE #### Laenge     73  Coordinates From R12C3      To R13C3       . DOWN
   * #### FOUND CHEAT POINT END ##### Nr 40  Direction LEFT     To R13C3
   * #### MOVE #### Laenge     74  Coordinates From R13C3      To R13C2       . LEFT
   * #### MOVE #### Laenge     75  Coordinates From R13C2      To R13C1       . LEFT
   * #### MOVE #### Laenge     76  Coordinates From R13C1      To R12C1       . UP
   * #### FOUND CHEAT POINT END ##### Nr 44  Direction LEFT     To R12C1
   * #### MOVE #### Laenge     77  Coordinates From R12C1      To R11C1       . UP
   * #### FOUND CHEAT POINT END ##### Nr 43  Direction LEFT     To R11C1
   * #### MOVE #### Laenge     78  Coordinates From R11C1      To R10C1       . UP
   * #### MOVE #### Laenge     79  Coordinates From R10C1      To R9C1        . UP
   * #### MOVE #### Laenge     80  Coordinates From R9C1       To R9C2        . RIGHT
   * #### MOVE #### Laenge     81  Coordinates From R9C2       To R9C3        . RIGHT
   * #### FOUND CHEAT POINT END ##### Nr 42  Direction UP       To R9C3
   * #### MOVE #### Laenge     82  Coordinates From R9C3       To R8C3        . UP
   * #### MOVE #### Laenge     83  Coordinates From R8C3       To R7C3        . UP
   * #### MOVE #### Laenge     84  Coordinates From R7C3       To R7C4        . RIGHT
   * #### MOVE #### Laenge     85  Coordinates From R7C4       To R7C5        E RIGHT
   * #### FOUND CHEAT POINT END ##### Nr 14  Direction LEFT     To R7C5
   * 
   * Debug Map
   * 
   *     0  ###############                   
   *     1  #...#...#.....#     xxx-xxx-xxxxx 
   *     2  #.#.#.#.#.###.#     x-x-x-x-x --x 
   *     3  #S#...#.#.#...#     x-xxx-x-x-xxx 
   *     4  #######.#.#.###           x-x-x-- 
   *     5  #######.#.#...#           x-x-xxx 
   *     6  #######.#.###.#           x-x --x 
   *     7  ###..E#...#...#       xxE-xxx-xxx 
   *     8  ###.#######.###       x   --- x-- 
   *     9  #...###...#...#     xxx   xxx-xxx 
   *    10  #.#####.#.###.#     x -   x-x --x 
   *    11  #.#...#.#.#...#     x-xxx-x-x-xxx 
   *    12  #.#.#.#.#.#.###     x-x-x-x-x-x   
   *    13  #...#...#...###     xxx-xxx-xxx   
   *    14  ###############                   
   * 
   * Cheat-Points found
   * 
   *     1  DOWN_R5C12      => CNR     26 Len      2 Start R5C12      To R7C12            38 -     42 =     -2
   *     2  DOWN_R3C12      => CNR     24 Len      2 Start R3C12      To R5C12            34 -     38 =     -2
   *     3  DOWN_R3C13      => CNR     23 Len      4 Start R3C13      To R5C13            33 -     39 =     -4
   *     4  DOWN_R1C11      => CNR     21 Len      4 Start R1C11      To R3C11            29 -     35 =     -4
   *     5  DOWN_R7C12      => CNR     28 Len      2 Start R7C12      To R9C12            42 -     46 =     -2
   *     6  LEFT_R11C9      => CNR     36 Len      4 Start R11C9      To R11C7            57 -     63 =     -4
   *     7  DOWN_R7C13      => CNR     27 Len      4 Start R7C13      To R9C13            41 -     47 =     -4
   *     8  LEFT_R12C9      => CNR     35 Len      6 Start R12C9      To R12C7            56 -     64 =     -6
   *     9  DOWN_R5C11      => CNR     25 Len      4 Start R5C11      To R7C11            37 -     43 =     -4
   *    10  LEFT_R13C9      => CNR     34 Len      8 Start R13C9      To R13C7            55 -     65 =     -8
   *    11  DOWN_R9C12      => CNR     31 Len      2 Start R9C12      To R11C12           46 -     50 =     -2
   *    12  DOWN_R1C12      => CNR     22 Len      2 Start R1C12      To R3C12            30 -     34 =     -2
   *    13  DOWN_R7C8       => CNR     15 Len     38 Start R7C8       To R9C8             20 -     60 =    -38
   *    14  DOWN_R7C7       => CNR     13 Len     40 Start R7C7       To R9C7             19 -     61 =    -40
   *    15  LEFT_R11C11     => CNR     32 Len      4 Start R11C11     To R11C9            51 -     57 =     -4
   *    16  LEFT_R12C5      => CNR     41 Len      2 Start R12C5      To R12C3            68 -     72 =     -2
   *    17  LEFT_R11C7      => CNR     38 Len      4 Start R11C7      To R11C5            63 -     69 =     -4
   *    18  LEFT_R13C5      => CNR     40 Len      4 Start R13C5      To R13C3            67 -     73 =     -4
   *    19  DOWN_R7C9       => CNR     16 Len     36 Start R7C9       To R9C9             21 -     59 =    -36
   *    20  LEFT_R10C9      => CNR     37 Len      2 Start R10C9      To R10C7            58 -     62 =     -2
   *    21  LEFT_R12C7      => CNR     39 Len      2 Start R12C7      To R12C5            64 -     68 =     -2
   *    22  LEFT_R11C3      => CNR     43 Len      4 Start R11C3      To R11C1            71 -     77 =     -4
   *    23  LEFT_R7C7       => CNR     14 Len     64 Start R7C7       To R7C5             19 -     85 =    -64
   *    24  LEFT_R12C3      => CNR     44 Len      2 Start R12C3      To R12C1            72 -     76 =     -2
   *    25  RIGHT_R2C5      => CNR      6 Len      2 Start R2C5       To R2C7             10 -     14 =     -2
   *    26  RIGHT_R2C7      => CNR      8 Len     10 Start R2C7       To R2C9             14 -     26 =    -10
   *    27  RIGHT_R3C5      => CNR      5 Len      4 Start R3C5       To R3C7              9 -     15 =     -4
   *    28  RIGHT_R1C7      => CNR      7 Len     12 Start R1C7       To R1C9             13 -     27 =    -12
   *    29  RIGHT_R2C1      => CNR      2 Len      2 Start R2C1       To R2C3              2 -      6 =     -2
   *    30  RIGHT_R2C3      => CNR      4 Len      2 Start R2C3       To R2C5              6 -     10 =     -2
   *    31  RIGHT_R3C1      => CNR      1 Len      4 Start R3C1       To R3C3              1 -      7 =     -4
   *    32  RIGHT_R1C3      => CNR      3 Len      4 Start R1C3       To R1C5              5 -     11 =     -4
   *    33  RIGHT_R5C9      => CNR     18 Len     12 Start R5C9       To R5C11            23 -     37 =    -12
   *    34  RIGHT_R7C9      => CNR     17 Len     20 Start R7C9       To R7C11            21 -     43 =    -20
   *    35  RIGHT_R4C7      => CNR     10 Len      6 Start R4C7       To R4C9             16 -     24 =     -6
   *    36  RIGHT_R3C7      => CNR      9 Len      8 Start R3C7       To R3C9             15 -     25 =     -8
   *    37  RIGHT_R6C7      => CNR     12 Len      2 Start R6C7       To R6C9             18 -     22 =     -2
   *    38  RIGHT_R4C9      => CNR     19 Len     10 Start R4C9       To R4C11            24 -     36 =    -10
   *    39  RIGHT_R5C7      => CNR     11 Len      4 Start R5C7       To R5C9             17 -     23 =     -4
   *    40  RIGHT_R3C9      => CNR     20 Len      8 Start R3C9       To R3C11            25 -     35 =     -8
   *    41  UP_R11C3        => CNR     42 Len      8 Start R11C3      To R9C3             71 -     81 =     -8
   *    42  DOWN_R9C11      => CNR     29 Len      4 Start R9C11      To R11C11           45 -     51 =     -4
   *    43  LEFT_R12C11     => CNR     33 Len      2 Start R12C11     To R12C9            52 -     56 =     -2
   *    44  LEFT_R9C11      => CNR     30 Len     12 Start R9C11      To R9C9             45 -     59 =    -12
   * 
   * 
   * 
   * Searching for cheats with at least 20 savings
   * 
   *     1 OK     1       1 CNR     14 Len     64 Start R7C7       To R7C5             19 -     85 =    -64
   *     2 OK     2       1 CNR     13 Len     40 Start R7C7       To R9C7             19 -     61 =    -40
   *     3 OK     3       1 CNR     15 Len     38 Start R7C8       To R9C8             20 -     60 =    -38
   *     4 OK     4       1 CNR     16 Len     36 Start R7C9       To R9C9             21 -     59 =    -36
   *     5 OK     5       1 CNR     17 Len     20 Start R7C9       To R7C11            21 -     43 =    -20
   *     6 --     0       1 CNR      7 Len     12 Start R1C7       To R1C9             13 -     27 =    -12
   *     7 --     0       2 CNR     18 Len     12 Start R5C9       To R5C11            23 -     37 =    -12
   *     8 --     0       3 CNR     30 Len     12 Start R9C11      To R9C9             45 -     59 =    -12
   *     9 --     0       1 CNR      8 Len     10 Start R2C7       To R2C9             14 -     26 =    -10
   *    10 --     0       2 CNR     19 Len     10 Start R4C9       To R4C11            24 -     36 =    -10
   *    11 --     0       1 CNR     34 Len      8 Start R13C9      To R13C7            55 -     65 =     -8
   *    12 --     0       2 CNR      9 Len      8 Start R3C7       To R3C9             15 -     25 =     -8
   *    13 --     0       3 CNR     20 Len      8 Start R3C9       To R3C11            25 -     35 =     -8
   *    14 --     0       4 CNR     42 Len      8 Start R11C3      To R9C3             71 -     81 =     -8
   *    15 --     0       1 CNR     35 Len      6 Start R12C9      To R12C7            56 -     64 =     -6
   *    16 --     0       2 CNR     10 Len      6 Start R4C7       To R4C9             16 -     24 =     -6
   *    17 --     0       1 CNR     23 Len      4 Start R3C13      To R5C13            33 -     39 =     -4
   *    18 --     0       2 CNR     21 Len      4 Start R1C11      To R3C11            29 -     35 =     -4
   *    19 --     0       3 CNR     36 Len      4 Start R11C9      To R11C7            57 -     63 =     -4
   *    20 --     0       4 CNR     27 Len      4 Start R7C13      To R9C13            41 -     47 =     -4
   *    21 --     0       5 CNR     25 Len      4 Start R5C11      To R7C11            37 -     43 =     -4
   *    22 --     0       6 CNR     32 Len      4 Start R11C11     To R11C9            51 -     57 =     -4
   *    23 --     0       7 CNR     38 Len      4 Start R11C7      To R11C5            63 -     69 =     -4
   *    24 --     0       8 CNR     40 Len      4 Start R13C5      To R13C3            67 -     73 =     -4
   *    25 --     0       9 CNR     43 Len      4 Start R11C3      To R11C1            71 -     77 =     -4
   *    26 --     0      10 CNR      5 Len      4 Start R3C5       To R3C7              9 -     15 =     -4
   *    27 --     0      11 CNR      1 Len      4 Start R3C1       To R3C3              1 -      7 =     -4
   *    28 --     0      12 CNR      3 Len      4 Start R1C3       To R1C5              5 -     11 =     -4
   *    29 --     0      13 CNR     11 Len      4 Start R5C7       To R5C9             17 -     23 =     -4
   *    30 --     0      14 CNR     29 Len      4 Start R9C11      To R11C11           45 -     51 =     -4
   *    31 --     0       1 CNR     26 Len      2 Start R5C12      To R7C12            38 -     42 =     -2
   *    32 --     0       2 CNR     24 Len      2 Start R3C12      To R5C12            34 -     38 =     -2
   *    33 --     0       3 CNR     28 Len      2 Start R7C12      To R9C12            42 -     46 =     -2
   *    34 --     0       4 CNR     31 Len      2 Start R9C12      To R11C12           46 -     50 =     -2
   *    35 --     0       5 CNR     22 Len      2 Start R1C12      To R3C12            30 -     34 =     -2
   *    36 --     0       6 CNR     41 Len      2 Start R12C5      To R12C3            68 -     72 =     -2
   *    37 --     0       7 CNR     37 Len      2 Start R10C9      To R10C7            58 -     62 =     -2
   *    38 --     0       8 CNR     39 Len      2 Start R12C7      To R12C5            64 -     68 =     -2
   *    39 --     0       9 CNR     44 Len      2 Start R12C3      To R12C1            72 -     76 =     -2
   *    40 --     0      10 CNR      6 Len      2 Start R2C5       To R2C7             10 -     14 =     -2
   *    41 --     0      11 CNR      2 Len      2 Start R2C1       To R2C3              2 -      6 =     -2
   *    42 --     0      12 CNR      4 Len      2 Start R2C3       To R2C5              6 -     10 =     -2
   *    43 --     0      13 CNR     12 Len      2 Start R6C7       To R6C9             18 -     22 =     -2
   *    44 --     0      14 CNR     33 Len      2 Start R12C11     To R12C9            52 -     56 =     -2
   * 
   * cheat_nr_total 44
   * count_part_1   5
   * 
   */

  private static final int    DEBUG_LEN_COORDINATES     = 10;

  private static final int    DEBUG_LEN_KEY_COORDINATES = 15;

  private static final char   CHAR_RACETRACK_START      = 'S';

  private static final char   CHAR_RACETRACK_END        = 'E';

  private static final char   CHAR_RACETRACK_GIVEN      = '.';

  private static final char   CHAR_RACETRACK_VISITED    = 'x';

  private static final char   CHAR_WALL_SET             = '#';

  private static final char   CHAR_WALL_EMPTY           = ' ';

  private static final char   CHAR_WALL_CHEAT_POINT     = '-';

  private static final String RACETRACK_NOT_FOUND       = "K";

  private static final String PRAEFIX_TO_COORDINATES    = "ToCoordinates";

  private enum MoveDirection
  {
    UP, RIGHT, DOWN, LEFT
  }

  private static HashMap< String, Day20CheatPoint > m_hash_map_cheat_points    = null;

  private static int                                m_global_cheat_point_count = 0;

  public static void main( String[] args )
  {
    String test_content_1 = "###############,#...#...#.....#,#.#.#.#.#.###.#,#S#...#.#.#...#,#######.#.#.###,#######.#.#...#,#######.#.###.#,###..E#...#...#,###.#######.###,#...###...#...#,#.#####.#.###.#,#.#...#.#.#...#,#.#.#.#.#.#.###,#...#...#...###,###############";

    //test_content_1 = "########,#...#.E#,#.#.#.##,#S#...##,########";

    List< String > test_content_list_1 = Arrays.stream( test_content_1.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    calculateGrid( test_content_list_1, 20, true );

    //calculateGrid( getListProd(), 100, false );
  }

  private static void calculateGrid( List< String > pListInput, int pCheatMinLen, boolean pKnzDebug )
  {
    /*
     * *******************************************************************************************************
     * Initializing Hash-Maps and Property-Instances
     * *******************************************************************************************************
     */
    m_hash_map_cheat_points = new HashMap< String, Day20CheatPoint >();

    m_global_cheat_point_count = 0;

    Properties prop_grid_racetrack = new Properties();

    long current_row = 0;
    long current_col = 0;

    long start_row = 0;
    long start_col = 0;

    /*
     * *******************************************************************************************************
     * Initializing the grid
     * *******************************************************************************************************
     */

    for ( String input_str : pListInput )
    {
      if ( pKnzDebug )
      {
        wl( input_str );
      }

      for ( current_col = 0; current_col < input_str.length(); current_col++ )
      {
        char current_char = input_str.charAt( (int) current_col );

        if ( current_char == CHAR_RACETRACK_START )
        {
          start_row = current_row;

          start_col = current_col;
        }

        prop_grid_racetrack.setProperty( "R" + current_row + "C" + current_col, "" + current_char );

        prop_grid_racetrack.setProperty( "OR" + current_row + "C" + current_col, "" + current_char );
      }

      current_row++;
    }

    /*
     * Set start positions
     */
    current_row = start_row;
    current_col = start_col;

    long next_row = 1;

    long next_col = 1;

    long while_loop_nr = 1;

    long len_race_track = 1;

    char current_char_rct = CHAR_RACETRACK_START;

    MoveDirection cur_move_direction = MoveDirection.RIGHT;

    MoveDirection next_move_direction = MoveDirection.RIGHT;

    while ( ( current_char_rct != CHAR_RACETRACK_END ) && ( while_loop_nr < Long.MAX_VALUE ) && ( ( next_row + next_col ) > 0 ) )
    {
      /*
       * **************************************************************************
       * Determine the new Direction of the racetrack
       * **************************************************************************
       */

      /*
       * 1. check next char in current moving direction
       * 
       *    Calculate new position, check char.
       */
      next_row = current_row;
      next_col = current_col;

      switch ( cur_move_direction )
      {
        case UP :
          next_row--;
          break;

        case DOWN :
          next_row++;
          break;

        case LEFT :
          next_col--;
          break;

        case RIGHT :
          next_col++;
          break;
      }

      char next_char_rct = prop_grid_racetrack.getProperty( "R" + next_row + "C" + next_col, RACETRACK_NOT_FOUND ).charAt( 0 );

      if ( ( next_char_rct == CHAR_RACETRACK_GIVEN ) || ( next_char_rct == CHAR_RACETRACK_END ) )
      {
        /*
         * Moving direction is still correct
         * 
         * Next char in direction is either racetrack or end char.
         */
      }
      else
      {
        /*
         * Moving Direction has changed.
         * 
         * Moving direction was not racetrack or end char.
         * New moving direction is neccessary
         * 
         * In the 4 possible directions, there can only be one with racetrack-char.
         * The visited racetrack fields are marked with the char from CHAR_RACETRACK_VISITED.
         * The other two surrounding chars must be wall. 
         */

        /*
         * Loop over the direction enum.
         */
        for ( MoveDirection new_move_direction : MoveDirection.values() )
        {
          next_row = current_row;
          next_col = current_col;

          switch ( new_move_direction )
          {
            case UP :
              next_row--;
              break;

            case DOWN :
              next_row++;
              break;

            case LEFT :
              next_col--;
              break;

            case RIGHT :
              next_col++;
              break;
          }

          next_char_rct = prop_grid_racetrack.getProperty( "R" + next_row + "C" + next_col, RACETRACK_NOT_FOUND ).charAt( 0 );

          if ( ( next_char_rct == CHAR_RACETRACK_GIVEN ) || ( next_char_rct == CHAR_RACETRACK_END ) )
          {
            next_move_direction = new_move_direction;

            break;
          }
          else
          {
            next_row = 0;
            next_col = 0;
          }
        }
      }

      /*
       * **************************************************************************
       * Move to the next Race-Track-Coordinates
       * **************************************************************************
       */

      if ( ( next_row + next_col ) > 0 )
      {
        /*
         * Check Cheat-Point start, from the current position (before move)
         */
        checkCheatStart( MoveDirection.UP, current_row, current_col, len_race_track, prop_grid_racetrack, pKnzDebug );
        checkCheatStart( MoveDirection.DOWN, current_row, current_col, len_race_track, prop_grid_racetrack, pKnzDebug );
        checkCheatStart( MoveDirection.LEFT, current_row, current_col, len_race_track, prop_grid_racetrack, pKnzDebug );
        checkCheatStart( MoveDirection.RIGHT, current_row, current_col, len_race_track, prop_grid_racetrack, pKnzDebug );

        /*
         * Increase the race track len
         */
        len_race_track++;

        /*
         * Do debug stuff
         */
        if ( pKnzDebug )
        {
          wl( "#### MOVE #### Laenge " + FkStringFeld.getFeldRechtsMin( len_race_track, 6 ) + "  Coordinates From " + FkStringFeld.getFeldLinksMin( "R" + current_row + "C" + current_col, DEBUG_LEN_COORDINATES ) + " To " + FkStringFeld.getFeldLinksMin( "R" + next_row + "C" + next_col, DEBUG_LEN_COORDINATES ) + "  " + next_char_rct + " " + next_move_direction );
        }

        /*
         * Mark the current position as visited.
         * Prevent the position to be found as given racetrack again.
         */
        prop_grid_racetrack.setProperty( "R" + current_row + "C" + current_col, "" + CHAR_RACETRACK_VISITED );

        /*
         * Update the current positions
         */
        current_row = next_row;

        current_col = next_col;

        /*
         * Set the new moving direction
         */
        cur_move_direction = next_move_direction;

        /*
         * Get the new current char from the new coordinates
         */
        current_char_rct = prop_grid_racetrack.getProperty( "R" + current_row + "C" + current_col, RACETRACK_NOT_FOUND ).charAt( 0 );

        /*
         * Check for existing Cheat Endpoint (after move)
         * ... is the current position has a mark in the properties
         */
        checkCheatEnd( MoveDirection.UP, current_row, current_col, len_race_track, prop_grid_racetrack, pKnzDebug );
        checkCheatEnd( MoveDirection.DOWN, current_row, current_col, len_race_track, prop_grid_racetrack, pKnzDebug );
        checkCheatEnd( MoveDirection.LEFT, current_row, current_col, len_race_track, prop_grid_racetrack, pKnzDebug );
        checkCheatEnd( MoveDirection.RIGHT, current_row, current_col, len_race_track, prop_grid_racetrack, pKnzDebug );
      }
      else
      {
        if ( pKnzDebug )
        {
          wl( "Coordinates From " + "R" + current_row + "C" + current_col + " To " + "R" + next_row + "C" + next_col + "  " + next_char_rct );

          wl( "######## Error #########" );
        }

        current_char_rct = CHAR_RACETRACK_END;
      }

      /*
       * Increase the endlesswhilelooppreventer
       */
      while_loop_nr++;
    }

    /*
     * If debug is enabled, print the map and the cheat points.
     */
    if ( pKnzDebug )
    {
      wl( "" );
      wl( "Debug Map" );
      wl( "" );

      current_row = 0;

      for ( String input_str : pListInput )
      {
        String str_line_new = "";
        String str_line_ori = "";

        for ( current_col = 0; current_col < input_str.length(); current_col++ )
        {
          str_line_ori += prop_grid_racetrack.getProperty( "OR" + current_row + "C" + current_col, " " );

          char new_charg = prop_grid_racetrack.getProperty( "R" + current_row + "C" + current_col, " " ).charAt( 0 );

          if ( new_charg == CHAR_WALL_SET )
          {
            str_line_new += CHAR_WALL_EMPTY;
          }
          else
          {
            str_line_new += new_charg;
          }
        }

        wl( FkStringFeld.getFeldRechtsMin( current_row, 5 ) + "  " + str_line_ori + "    " + str_line_new );

        current_row++;
      }

      wl( "" );
      wl( "Cheat-Points found" );
      wl( "" );

      long cheat_nr_g = 0;

      for ( Map.Entry< String, Day20CheatPoint > map_entry : m_hash_map_cheat_points.entrySet() )
      {
        cheat_nr_g++;

        wl( FkStringFeld.getFeldRechtsMin( cheat_nr_g, 5 ) + "  " + FkStringFeld.getFeldLinksMin( map_entry.getKey(), DEBUG_LEN_KEY_COORDINATES ) + " => " + map_entry.getValue() );
      }

      wl( "" );
      wl( "" );
    }

    /*
     * Get a sorted list from the cheatpoints.
     * Sorted by the lenght of the savings.
     */

    List< Day20CheatPoint > sorted_cheat_point_list = new ArrayList< Day20CheatPoint >();

    if ( m_hash_map_cheat_points != null )
    {
      sorted_cheat_point_list.addAll( m_hash_map_cheat_points.values() );
    }

    sorted_cheat_point_list.sort( Comparator.comparingLong( Day20CheatPoint::getLen ) );

    /*
     * Calculate part 1
     */
    long cheat_len_old = 0;

    long cheat_nr_total = 0;

    long cheat_nr_in_range = 0;

    long result_count_cheats_part_1 = 0;

    long cheat_len_count = pCheatMinLen;

    wl( "" );
    wl( "Searching for cheats with at least " + cheat_len_count + " savings" );
    wl( "" );

    for ( Day20CheatPoint cheat_inst : sorted_cheat_point_list )
    {
      if ( cheat_inst.getLen() != cheat_len_old )
      {
        cheat_nr_in_range = 0;

        cheat_len_old = cheat_inst.getLen();
      }

      cheat_nr_in_range++;

      cheat_nr_total++;

      if ( cheat_inst.getLenAbs() >= cheat_len_count )
      {
        result_count_cheats_part_1++;

        wl( FkStringFeld.getFeldRechtsMin( cheat_nr_total, 5 ) + " OK " + FkStringFeld.getFeldRechtsMin( result_count_cheats_part_1, 5 ) + "   " + FkStringFeld.getFeldRechtsMin( cheat_nr_in_range, 5 ) + " " + cheat_inst.toString() );
      }
      else
      {
        if ( pKnzDebug )
        {
          wl( FkStringFeld.getFeldRechtsMin( cheat_nr_total, 5 ) + " -- " + FkStringFeld.getFeldRechtsMin( "0", 5 ) + "   " + FkStringFeld.getFeldRechtsMin( cheat_nr_in_range, 5 ) + " " + cheat_inst.toString() );
        }
      }
    }

    wl( "" );
    wl( "cheat_nr_total " + cheat_nr_total );
    wl( "count_part_1   " + result_count_cheats_part_1 );
  }

  private static void checkCheatEnd( MoveDirection pMoveDirection, long pCurrentRow, long pCurrentCol, long pRaceTrackLen, Properties pPropGridRacetrack, boolean pKnzDebug )
  {
    /*
     * Construct the praefix for the key
     */
    String preafix_direction = pMoveDirection.toString() + "_";

    /*
     * Try to find a key in the properties.
     * Is there a field in the properties wich is the end-point of a cheat-point.
     * If there is a field, it's propertie-value is the key for the hashmap.
     */
    String prop_key_from_coordinates = pPropGridRacetrack.getProperty( preafix_direction + PRAEFIX_TO_COORDINATES + "R" + pCurrentRow + "C" + pCurrentCol );

    /*
     * Check if a field was found
     */
    if ( prop_key_from_coordinates != null )
    {
      /*
       * If a key was found, get the cheat point from the hashmap
       */
      Day20CheatPoint cach_cheat_point = m_hash_map_cheat_points.get( prop_key_from_coordinates );

      /*
       * If the cheatpoint was found in the hashmap, the end length must be set.
       */
      if ( cach_cheat_point != null )
      {
        if ( pKnzDebug )
        {
          wl( "#### FOUND CHEAT POINT END ##### Nr " + cach_cheat_point.getNr() + "  Direction " + FkStringFeld.getFeldLinksMin( pMoveDirection.toString(), 6 ) + "   To " + "R" + pCurrentRow + "C" + pCurrentCol );
        }

        cach_cheat_point.setEndLen( pRaceTrackLen );
      }
    }
  }

  private static void checkCheatStart( MoveDirection pMoveDirection, long pCurrentRow, long pCurrentCol, long pRaceTrackLen, Properties pPropGridRacetrack, boolean pKnzDebug )
  {
    /*
     * Init coordinats with the current positions.
     */
    long coordinates_cp_wall_row = pCurrentRow;
    long coordinates_cp_wall_col = pCurrentCol;

    long coordinates_cp_end_row = pCurrentRow;
    long coordinates_cp_end_col = pCurrentCol;

    /*
     * Calc the new coordinates with the current move direction
     */
    switch ( pMoveDirection )
    {
      case UP :
        coordinates_cp_wall_row = pCurrentRow - 1;
        coordinates_cp_end_row = pCurrentRow - 2;
        break;

      case DOWN :
        coordinates_cp_wall_row = pCurrentRow + 1;
        coordinates_cp_end_row = pCurrentRow + 2;
        break;

      case LEFT :
        coordinates_cp_wall_col = pCurrentCol - 1;
        coordinates_cp_end_col = pCurrentCol - 2;
        break;

      case RIGHT :
        coordinates_cp_wall_col = pCurrentCol + 1;
        coordinates_cp_end_col = pCurrentCol + 2;
        break;
    }

    /*
     * Get both chars
     * - The first must be a wall (Wall Set or Wall CheatPoint)
     * - The second must be a part of the racetrack (Racetrack given or Racetrack end)
     */

    char char_wall = pPropGridRacetrack.getProperty( "R" + coordinates_cp_wall_row + "C" + coordinates_cp_wall_col, RACETRACK_NOT_FOUND ).charAt( 0 );

    char char_behind_the_wall = pPropGridRacetrack.getProperty( "R" + coordinates_cp_end_row + "C" + coordinates_cp_end_col, RACETRACK_NOT_FOUND ).charAt( 0 );

    if ( ( ( char_behind_the_wall == CHAR_RACETRACK_GIVEN ) || ( char_behind_the_wall == CHAR_RACETRACK_END ) ) && ( ( char_wall == CHAR_WALL_SET ) || ( char_wall == CHAR_WALL_CHEAT_POINT ) ) )
    {
      /*
       * Increment the global cheat point count
       */
      m_global_cheat_point_count++;

      /*
       * Set a new Character at the Wall-Coordinates (... just for debugging and visualisation)
       */
      pPropGridRacetrack.setProperty( "R" + coordinates_cp_wall_row + "C" + coordinates_cp_wall_col, "" + CHAR_WALL_CHEAT_POINT );

      String preafix_direction = pMoveDirection.toString() + "_";

      /*
       * Do some debug stuff
       */
      if ( pKnzDebug )
      {
        wl( "#### FOUND CHEAT POINT START ##### Nr " + m_global_cheat_point_count + "  Direction " + FkStringFeld.getFeldLinksMin( pMoveDirection.toString(), 6 ) + "   From " + "R" + coordinates_cp_end_row + "C" + coordinates_cp_end_col + " To " + "R" + coordinates_cp_wall_row + "C" + coordinates_cp_wall_col );
      }

      /*
       * Set a virtual "break point", so that the cheat-point-to coordinats can be found.
       * The value are the origin coordinates ... which will serve as a key for the hash map.
       */
      pPropGridRacetrack.setProperty( preafix_direction + PRAEFIX_TO_COORDINATES + "R" + coordinates_cp_end_row + "C" + coordinates_cp_end_col, preafix_direction + "R" + pCurrentRow + "C" + pCurrentCol );

      /*
       * Create a new cheat point instance
       */
      Day20CheatPoint new_cheat_point = new Day20CheatPoint( pCurrentRow, pCurrentCol, pRaceTrackLen, coordinates_cp_end_row, coordinates_cp_end_col, m_global_cheat_point_count );

      /*
       * Store the new cheat-point-instance in the hash map.
       */
      m_hash_map_cheat_points.put( preafix_direction + "R" + pCurrentRow + "C" + pCurrentCol, new_cheat_point );
    }
  }

  private static List< String > getListProd()
  {
    int row_count = 0;

    List< String > string_array = new ArrayList< String >();

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day20_input.txt";

    try (BufferedReader buffered_reader = new BufferedReader( new FileReader( datei_input ) ))
    {
      String zeile;

      while ( ( zeile = buffered_reader.readLine() ) != null )
      {
        zeile = zeile.trim();

        string_array.add( zeile );

        row_count++;
      }
    }
    catch ( IOException err_inst )
    {
      err_inst.printStackTrace();
    }

    wl( "File Row Count " + row_count + " " + string_array.size() );

    return string_array;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }
}
