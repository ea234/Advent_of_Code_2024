package de.ea234.aoc2024.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import de.ea234.util.FkStringFeld;

public class Day12GardenGroups
{
 /*
  * --- Day 12: Garden Groups ---
  * https://adventofcode.com/2024/day/12
  * 
  * https://www.youtube.com/watch?v=jx0Y07_LcfQ
  * 
  * https://www.reddit.com/r/adventofcode/comments/1hcdnk0/2024_day_12_solutions/
  * https://www.reddit.com/r/adventofcode/comments/1hcfuz6/2024_day_12_part_2_solutions_handle_all_5/
  * https://www.reddit.com/r/adventofcode/comments/1hcpyic/2024_day_12_part_2_what_kind_of_algorithm_did_you/
  * 
  * https://www.reddit.com/r/adventofcode/comments/1pvfh5d/2024_day_12_part_2_line_count_via_sorted_list/
  *  
  *   
  * AAAA   4    3223  10               
  * BBCD   4    2234  11               
  * BBCC   4    2222   8               
  * EEEC   4    3233  11               
  *  Char Count 16    Sum Values 40    
  * 
  * 
  * List of Regions 
  * 
  * Entry R1C2
  *   -       1    R1C2  3         3        3
  *   -       2    R2C2  2         5       10
  *   -       3    R2C3  2         7       21
  *   -       4    R3C3  3        10       40
  * 
  * Entry R3C0
  *   -       1    R3C0  3         3        3
  *   -       2    R3C1  2         5       10
  *   -       3    R3C2  3         8       24
  * 
  * Entry R1C3
  *   -       1    R1C3  4         4        4
  * 
  * Entry R1C0
  *   -       1    R1C0  2         2        2
  *   -       2    R1C1  2         4        8
  *   -       3    R2C1  2         6       18
  *   -       4    R2C0  2         8       32
  * 
  * Entry R0C0
  *   -       1    R0C0  3         3        3
  *   -       2    R0C1  2         5       10
  *   -       3    R0C2  2         7       21
  *   -       4    R0C3  3        10       40
  * sum_plants_count = 16
  * sum_plants_count = 140 <- Result Part 1
  * 
  * AAAA   4    3223  10               
  * BBCD   4    2234  11               
  * BBCC   4    2222   8               
  * EEEC   4    3233  11               
  *  Char Count 16    Sum Values 40    
  * 
  * AAAA   4    3223  10                   ....   0    ....   0                   ....   0    ....   0               
  * ....   0    ....   0                   BB..   2    22..   4                   ..C.   1    ..3.   3               
  * ....   0    ....   0                   BB..   2    22..   4                   ..CC   2    ..22   4               
  * ....   0    ....   0                   ....   0    ....   0                   ...C   1    ...3   3               
  *  Char Count 4    Sum Values 10          Char Count 4    Sum Values 8           Char Count 4    Sum Values 10     
  * 
  * 
  * ....   0    ....   0                   ....   0    ....   0               
  * ...D   1    ...4   4                   ....   0    ....   0               
  * ....   0    ....   0                   ....   0    ....   0               
  * ....   0    ....   0                   EEE.   3    323.   8               
  *  Char Count 1    Sum Values 4           Char Count 3    Sum Values 8      
  * 
  * -----------------------------------------------------------------------------
  * 
  * OOOOO   5    22122   9             
  * OXOXO   5    24242  14             
  * OOOOO   5    12021   6             
  * OXOXO   5    24242  14             
  * OOOOO   5    22122   9             
  *  Char Count 25    Sum Values 52    
  * 
  * 
  * List of Regions 
  * 
  * Entry R3C3
  *   -       1    R3C3  4         4        4
  * 
  * Entry R1C3
  *   -       1    R1C3  4         4        4
  * 
  * Entry R3C1
  *   -       1    R3C1  4         4        4
  * 
  * Entry R1C1
  *   -       1    R1C1  4         4        4
  * 
  * Entry R0C0
  *   -       1    R0C0  2         2        2
  *   -       2    R0C1  2         4        8
  *   -       3    R0C2  1         5       15
  *   -       4    R0C3  2         7       28
  *   -       5    R0C4  2         9       45
  *   -       6    R1C4  2        11       66
  *   -       7    R2C4  1        12       84
  *   -       8    R3C4  2        14      112
  *   -       9    R4C4  2        16      144
  *   -      10    R4C3  2        18      180
  *   -      11    R4C2  1        19      209
  *   -      12    R4C1  2        21      252
  *   -      13    R4C0  2        23      299
  *   -      14    R3C0  2        25      350
  *   -      15    R2C0  1        26      390
  *   -      16    R2C1  2        28      448
  *   -      17    R2C2  0        28      476
  *   -      18    R2C3  2        30      540
  *   -      19    R3C2  2        32      608
  *   -      20    R1C2  2        34      680
  *   -      21    R1C0  2        36      756
  * sum_plants_count = 25
  * sum_plants_count = 772 <- Result Part 1
  * 
  * OOOOO   5    22122   9             
  * OXOXO   5    24242  14             
  * OOOOO   5    12021   6             
  * OXOXO   5    24242  14             
  * OOOOO   5    22122   9             
  *  Char Count 25    Sum Values 52    
  * 
  * OOOOO   5    22122   9                 .....   0    .....   0             
  * O.O.O   3    2.2.2   6                 .X.X.   2    .4.4.   8             
  * OOOOO   5    12021   6                 .....   0    .....   0             
  * O.O.O   3    2.2.2   6                 .X.X.   2    .4.4.   8             
  * OOOOO   5    22122   9                 .....   0    .....   0             
  *  Char Count 21    Sum Values 36         Char Count 4    Sum Values 16     
  * 
  * -----------------------------------------------------------------------------
  * 
  * RRRRIICCFF  10    2112222232  19   
  * RRRRIICCCF  10    2101221132  15   
  * VVRRRCCFFF  10    2211322211  17   
  * VVRCCCJFFF  10    1133123202  18   
  * VVVVCJJCFE  10    1022231433  21   
  * VVIVCCJJEE  10    1133221221  18   
  * VVIIICJJEE  10    2211231111  15   
  * MIIIIIJJEE  10    3200121211  13   
  * MIIISIJEEE  10    2211332201  17   
  * MMMISSJEEE  10    2233233212  23   
  *  Char Count 100    Sum Values 176  
  * 
  * 
  * List of Regions 
  * 
  * Entry R4C7
  *   -       1    R4C7  4         4        4
  * 
  * Entry R8C4
  *   -       1    R8C4  3         3        3
  *   -       2    R9C4  2         5       10
  *   -       3    R9C5  3         8       24
  * 
  * Entry R3C6
  *   -       1    R3C6  3         3        3
  *   -       2    R4C6  1         4        8
  *   -       3    R5C6  1         5       15
  *   -       4    R5C7  2         7       28
  *   -       5    R6C7  1         8       40
  *   -       6    R7C7  2        10       60
  *   -       7    R7C6  1        11       77
  *   -       8    R8C6  2        13      104
  *   -       9    R9C6  3        16      144
  *   -      10    R6C6  1        17      170
  *   -      11    R4C5  3        20      220
  * 
  * Entry R5C2
  *   -       1    R5C2  3         3        3
  *   -       2    R6C2  1         4        8
  *   -       3    R6C3  1         5       15
  *   -       4    R6C4  2         7       28
  *   -       5    R7C4  1         8       40
  *   -       6    R7C5  2        10       60
  *   -       7    R8C5  3        13       91
  *   -       8    R7C3  0        13      104
  *   -       9    R8C3  1        14      126
  *   -      10    R9C3  3        17      170
  *   -      11    R8C2  1        18      198
  *   -      12    R8C1  2        20      240
  *   -      13    R7C1  2        22      286
  *   -      14    R7C2  0        22      308
  * 
  * Entry R7C0
  *   -       1    R7C0  3         3        3
  *   -       2    R8C0  2         5       10
  *   -       3    R9C0  2         7       21
  *   -       4    R9C1  2         9       36
  *   -       5    R9C2  3        12       60
  * 
  * Entry R0C8
  *   -       1    R0C8  3         3        3
  *   -       2    R0C9  2         5       10
  *   -       3    R1C9  2         7       21
  *   -       4    R2C9  1         8       32
  *   -       5    R3C9  2        10       50
  *   -       6    R3C8  0        10       60
  *   -       7    R4C8  3        13       91
  *   -       8    R3C7  2        15      120
  *   -       9    R2C7  2        17      153
  *   -      10    R2C8  1        18      180
  * 
  * Entry R0C6
  *   -       1    R0C6  2         2        2
  *   -       2    R0C7  2         4        8
  *   -       3    R1C7  1         5       15
  *   -       4    R1C8  3         8       32
  *   -       5    R1C6  1         9       45
  *   -       6    R2C6  2        11       66
  *   -       7    R2C5  2        13       91
  *   -       8    R3C5  2        15      120
  *   -       9    R3C4  1        16      144
  *   -      10    R4C4  2        18      180
  *   -      11    R5C4  2        20      220
  *   -      12    R5C5  2        22      264
  *   -      13    R6C5  3        25      325
  *   -      14    R3C3  3        28      392
  * 
  * Entry R0C4
  *   -       1    R0C4  2         2        2
  *   -       2    R0C5  2         4        8
  *   -       3    R1C5  2         6       18
  *   -       4    R1C4  2         8       32
  * 
  * Entry R2C0
  *   -       1    R2C0  2         2        2
  *   -       2    R2C1  2         4        8
  *   -       3    R3C1  1         5       15
  *   -       4    R4C1  0         5       20
  *   -       5    R4C2  2         7       35
  *   -       6    R4C3  2         9       54
  *   -       7    R5C3  3        12       84
  *   -       8    R5C1  1        13      104
  *   -       9    R6C1  2        15      135
  *   -      10    R6C0  2        17      170
  *   -      11    R5C0  1        18      198
  *   -      12    R4C0  1        19      228
  *   -      13    R3C0  1        20      260
  * 
  * Entry R0C0
  *   -       1    R0C0  2         2        2
  *   -       2    R0C1  1         3        6
  *   -       3    R0C2  1         4       12
  *   -       4    R0C3  2         6       24
  *   -       5    R1C3  1         7       35
  *   -       6    R2C3  1         8       48
  *   -       7    R2C4  3        11       77
  *   -       8    R2C2  1        12       96
  *   -       9    R3C2  3        15      135
  *   -      10    R1C2  0        15      150
  *   -      11    R1C1  1        16      176
  *   -      12    R1C0  2        18      216
  * 
  * Entry R4C9
  *   -       1    R4C9  3         3        3
  *   -       2    R5C9  1         4        8
  *   -       3    R6C9  1         5       15
  *   -       4    R7C9  1         6       24
  *   -       5    R8C9  1         7       35
  *   -       6    R9C9  2         9       54
  *   -       7    R9C8  1        10       70
  *   -       8    R9C7  2        12       96
  *   -       9    R8C7  2        14      126
  *   -      10    R8C8  0        14      140
  *   -      11    R7C8  1        15      165
  *   -      12    R6C8  1        16      192
  *   -      13    R5C8  2        18      234
  * sum_plants_count = 100
  * sum_plants_count = 1930 <- Result Part 1
  * 
  * 
  * ......CC..   2    ......22..   4       ..........   0    ..........   0       ........FF   2    ........32   5   
  * ......CCC.   3    ......113.   5       ..........   0    ..........   0       .........F   1    .........2   2   
  * .....CC...   2    .....22...   4       ..........   0    ..........   0       .......FFF   3    .......211   4   
  * ...CCC....   3    ...312....   6       ..........   0    ..........   0       .......FFF   3    .......202   4   
  * ....C..C..   2    ....2..4..   6       .........E   1    .........3   3       ........F.   1    ........3.   3   
  * ....CC....   2    ....22....   4       ........EE   2    ........21   3       ..........   0    ..........   0   
  * .....C....   1    .....3....   3       ........EE   2    ........11   2       ..........   0    ..........   0   
  * ..........   0    ..........   0       ........EE   2    ........11   2       ..........   0    ..........   0   
  * ..........   0    ..........   0       .......EEE   3    .......201   3       ..........   0    ..........   0   
  * ..........   0    ..........   0       .......EEE   3    .......212   5       ..........   0    ..........   0   
  *  Char Count 15    Sum Values 32         Char Count 13    Sum Values 18         Char Count 10    Sum Values 18    
  * 
  * 
  * ....II....   2    ....22....   4       ..........   0    ..........   0       ..........   0    ..........   0   
  * ....II....   2    ....22....   4       ..........   0    ..........   0       ..........   0    ..........   0   
  * ..........   0    ..........   0       ..........   0    ..........   0       ..........   0    ..........   0   
  * ..........   0    ..........   0       ......J...   1    ......3...   3       ..........   0    ..........   0   
  * ..........   0    ..........   0       .....JJ...   2    .....31...   4       ..........   0    ..........   0   
  * ..I.......   1    ..3.......   3       ......JJ..   2    ......12..   3       ..........   0    ..........   0   
  * ..III.....   3    ..112.....   4       ......JJ..   2    ......11..   2       ..........   0    ..........   0   
  * .IIIII....   5    .20012....   5       ......JJ..   2    ......12..   3       M.........   1    3.........   3   
  * .III.I....   4    .211.3....   7       ......J...   1    ......2...   2       M.........   1    2.........   2   
  * ...I......   1    ...3......   3       ......J...   1    ......3...   3       MMM.......   3    223.......   7   
  *  Char Count 18    Sum Values 30         Char Count 11    Sum Values 20         Char Count 5    Sum Values 12     
  * 
  * 
  * RRRR......   4    2112......   6       ..........   0    ..........   0       ..........   0    ..........   0   
  * RRRR......   4    2101......   4       ..........   0    ..........   0       ..........   0    ..........   0   
  * ..RRR.....   3    ..113.....   5       ..........   0    ..........   0       VV........   2    22........   4   
  * ..R.......   1    ..3.......   3       ..........   0    ..........   0       VV........   2    11........   2   
  * ..........   0    ..........   0       ..........   0    ..........   0       VVVV......   4    1022......   5   
  * ..........   0    ..........   0       ..........   0    ..........   0       VV.V......   3    11.3......   5   
  * ..........   0    ..........   0       ..........   0    ..........   0       VV........   2    22........   4   
  * ..........   0    ..........   0       ..........   0    ..........   0       ..........   0    ..........   0   
  * ..........   0    ..........   0       ....S.....   1    ....3.....   3       ..........   0    ..........   0   
  * ..........   0    ..........   0       ....SS....   2    ....23....   5       ..........   0    ..........   0   
  *  Char Count 12    Sum Values 18         Char Count 3    Sum Values 8           Char Count 13    Sum Values 20    
  *  
  *  
  * ----------------------------------------------------------------------------------------------------------------
  *  
  * PART 2 - Line Count
  * 
  * No Recursive algorithm to traverse the outer or inner lines.
  * That was to complicated.
  * 
  * Main principle: Difference 1 = same line, Difference not 1 new line  
  * 
  * 
  * Each existing fence line from a Region is stored in a List of Strings
  * 
  * The line information is stored as follows:
  * 
  * - first the plant type ... for debugging
  * 
  * - type of line "LINE_BOTTOM", "LINE_LEFT", "LINE_RIGHT" and "LINE_TOP"
  *   the type has a fixed length 
  * 
  * - Row/Column Info
  *   Horizontal lines store the row-information
  *   Vertical lines store the col-information.
  *   The number is stored with a length of 4 characters, with leading zeros.
  * 
  * - Comma (for seperation)
  * 
  * - Row/Column Info
  *   Horizontal lines store the col-information
  *   Vertical lines store the row-information.
  *   The number is stored with a length of 4 characters, with leading zeros.
  *   
  * You end up with the pattern:
  * <plant_type><Line type><Row or Column 1><sperator><Row or Column 2>
  * 
  * This would result in a list like this: (... after sorting)
  * B_LINE_BOTTOM_R0002,C0003 
  * B_LINE_BOTTOM_R0002,C0004 
  * B_LINE_LEFT___C0003,R0001 
  * B_LINE_LEFT___C0003,R0002 
  * B_LINE_RIGHT__C0004,R0001 
  * B_LINE_RIGHT__C0004,R0002 
  * B_LINE_TOP____R0001,C0003 
  * B_LINE_TOP____R0001,C0004 
  * 
  * The List gets sorted.
  * 
  * The benefit is now, that lines on the same row or column will 
  * be listed after another.
  * 
  * Now we can calculate the difference.
  *                   
  * If a line is continious, the difference is 1.
  * If a line gets seperated in the row or column the difference is unequal to 1.
  * 
  * If a line starts with a different start string (first bit until comma),
  * we also found a new line.
  * 
  * 
  * Shape for both plant types:
  *  
  *    -  -  -  -  -  -    |                     
  *   |A  A  A  A  A  A|   |                     
  *             -  -       |          -  -                     
  *   |A  A  A|      |A|   |         |B  B|      
  *   |A  A  A|      |A|   |         |B  B|      
  *       -  -  -  -       |    -  -  -  -           
  *   |A|      |A  A  A|   |   |B  B|            
  *   |A|      |A  A  A|   |   |B  B|            
  *       -  -             |    -  -                     
  *   |A  A  A  A  A  A|   |                     
  *    -  -  -  -  -  -    |                     
  * 
  * Start R1C3
  * 
  * Lines for Region which starts at R1C3
  * 
  * B_LINE_BOTTOM_R0002,C0003  -  0003 diff    0  line length    1    line count 1     last_start B_LINE_BOTTOM_R0002
  * B_LINE_BOTTOM_R0002,C0004  -  0004 diff    1  line length    2    line count 1     last_start B_LINE_BOTTOM_R0002
  * B_LINE_LEFT___C0003,R0001  -  0001 diff    0  line length    1    line count 2     last_start B_LINE_LEFT___C0003
  * B_LINE_LEFT___C0003,R0002  -  0002 diff    1  line length    2    line count 2     last_start B_LINE_LEFT___C0003
  * B_LINE_RIGHT__C0004,R0001  -  0001 diff    0  line length    1    line count 3     last_start B_LINE_RIGHT__C0004
  * B_LINE_RIGHT__C0004,R0002  -  0002 diff    1  line length    2    line count 3     last_start B_LINE_RIGHT__C0004
  * B_LINE_TOP____R0001,C0003  -  0003 diff    0  line length    1    line count 4     last_start B_LINE_TOP____R0001
  * B_LINE_TOP____R0001,C0004  -  0004 diff    1  line length    2    line count 4     last_start B_LINE_TOP____R0001
  * 
  * Region R1C3 count_plants    4 count_lines    4 region_price         16
  * 
  * Start R3C1
  * 
  * Lines for Region which starts at R3C1
  * 
  * B_LINE_BOTTOM_R0004,C0001  -  0001 diff    0  line length    1    line count 1     last_start B_LINE_BOTTOM_R0004
  * B_LINE_BOTTOM_R0004,C0002  -  0002 diff    1  line length    2    line count 1     last_start B_LINE_BOTTOM_R0004
  * B_LINE_LEFT___C0001,R0003  -  0003 diff    0  line length    1    line count 2     last_start B_LINE_LEFT___C0001
  * B_LINE_LEFT___C0001,R0004  -  0004 diff    1  line length    2    line count 2     last_start B_LINE_LEFT___C0001
  * B_LINE_RIGHT__C0002,R0003  -  0003 diff    0  line length    1    line count 3     last_start B_LINE_RIGHT__C0002
  * B_LINE_RIGHT__C0002,R0004  -  0004 diff    1  line length    2    line count 3     last_start B_LINE_RIGHT__C0002
  * B_LINE_TOP____R0003,C0001  -  0001 diff    0  line length    1    line count 4     last_start B_LINE_TOP____R0003
  * B_LINE_TOP____R0003,C0002  -  0002 diff    1  line length    2    line count 4     last_start B_LINE_TOP____R0003
  * 
  * Region R3C1 count_plants    4 count_lines    4 region_price         16
  * Start R0C0
  * 
  * Lines for Region which starts at R0C0
  * 
  * A_LINE_BOTTOM_R0000,C0003  -  0003 diff    0  line length    1    line count 1     last_start A_LINE_BOTTOM_R0000
  * A_LINE_BOTTOM_R0000,C0004  -  0004 diff    1  line length    2    line count 1     last_start A_LINE_BOTTOM_R0000
  * A_LINE_BOTTOM_R0002,C0001  -  0001 diff    0  line length    1    line count 2     last_start A_LINE_BOTTOM_R0002
  * A_LINE_BOTTOM_R0002,C0002  -  0002 diff    1  line length    2    line count 2     last_start A_LINE_BOTTOM_R0002
  * A_LINE_BOTTOM_R0005,C0000  -  0000 diff    0  line length    1    line count 3     last_start A_LINE_BOTTOM_R0005
  * A_LINE_BOTTOM_R0005,C0001  -  0001 diff    1  line length    2    line count 3     last_start A_LINE_BOTTOM_R0005
  * A_LINE_BOTTOM_R0005,C0002  -  0002 diff    1  line length    3    line count 3     last_start A_LINE_BOTTOM_R0005
  * A_LINE_BOTTOM_R0005,C0003  -  0003 diff    1  line length    4    line count 3     last_start A_LINE_BOTTOM_R0005
  * A_LINE_BOTTOM_R0005,C0004  -  0004 diff    1  line length    5    line count 3     last_start A_LINE_BOTTOM_R0005
  * A_LINE_BOTTOM_R0005,C0005  -  0005 diff    1  line length    6    line count 3     last_start A_LINE_BOTTOM_R0005
  * A_LINE_LEFT___C0000,R0000  -  0000 diff    0  line length    1    line count 4     last_start A_LINE_LEFT___C0000
  * A_LINE_LEFT___C0000,R0001  -  0001 diff    1  line length    2    line count 4     last_start A_LINE_LEFT___C0000
  * A_LINE_LEFT___C0000,R0002  -  0002 diff    1  line length    3    line count 4     last_start A_LINE_LEFT___C0000
  * A_LINE_LEFT___C0000,R0003  -  0003 diff    1  line length    4    line count 4     last_start A_LINE_LEFT___C0000
  * A_LINE_LEFT___C0000,R0004  -  0004 diff    1  line length    5    line count 4     last_start A_LINE_LEFT___C0000
  * A_LINE_LEFT___C0000,R0005  -  0005 diff    1  line length    6    line count 4     last_start A_LINE_LEFT___C0000
  * A_LINE_LEFT___C0003,R0003  -  0003 diff    0  line length    1    line count 5     last_start A_LINE_LEFT___C0003
  * A_LINE_LEFT___C0003,R0004  -  0004 diff    1  line length    2    line count 5     last_start A_LINE_LEFT___C0003
  * A_LINE_LEFT___C0005,R0001  -  0001 diff    0  line length    1    line count 6     last_start A_LINE_LEFT___C0005
  * A_LINE_LEFT___C0005,R0002  -  0002 diff    1  line length    2    line count 6     last_start A_LINE_LEFT___C0005
  * A_LINE_RIGHT__C0000,R0003  -  0003 diff    0  line length    1    line count 7     last_start A_LINE_RIGHT__C0000
  * A_LINE_RIGHT__C0000,R0004  -  0004 diff    1  line length    2    line count 7     last_start A_LINE_RIGHT__C0000
  * A_LINE_RIGHT__C0002,R0001  -  0001 diff    0  line length    1    line count 8     last_start A_LINE_RIGHT__C0002
  * A_LINE_RIGHT__C0002,R0002  -  0002 diff    1  line length    2    line count 8     last_start A_LINE_RIGHT__C0002
  * A_LINE_RIGHT__C0005,R0000  -  0000 diff    0  line length    1    line count 9     last_start A_LINE_RIGHT__C0005
  * A_LINE_RIGHT__C0005,R0001  -  0001 diff    1  line length    2    line count 9     last_start A_LINE_RIGHT__C0005
  * A_LINE_RIGHT__C0005,R0002  -  0002 diff    1  line length    3    line count 9     last_start A_LINE_RIGHT__C0005
  * A_LINE_RIGHT__C0005,R0003  -  0003 diff    1  line length    4    line count 9     last_start A_LINE_RIGHT__C0005
  * A_LINE_RIGHT__C0005,R0004  -  0004 diff    1  line length    5    line count 9     last_start A_LINE_RIGHT__C0005
  * A_LINE_RIGHT__C0005,R0005  -  0005 diff    1  line length    6    line count 9     last_start A_LINE_RIGHT__C0005
  * A_LINE_TOP____R0000,C0000  -  0000 diff    0  line length    1    line count 10     last_start A_LINE_TOP____R0000
  * A_LINE_TOP____R0000,C0001  -  0001 diff    1  line length    2    line count 10     last_start A_LINE_TOP____R0000
  * A_LINE_TOP____R0000,C0002  -  0002 diff    1  line length    3    line count 10     last_start A_LINE_TOP____R0000
  * A_LINE_TOP____R0000,C0003  -  0003 diff    1  line length    4    line count 10     last_start A_LINE_TOP____R0000
  * A_LINE_TOP____R0000,C0004  -  0004 diff    1  line length    5    line count 10     last_start A_LINE_TOP____R0000
  * A_LINE_TOP____R0000,C0005  -  0005 diff    1  line length    6    line count 10     last_start A_LINE_TOP____R0000
  * A_LINE_TOP____R0003,C0003  -  0003 diff    0  line length    1    line count 11     last_start A_LINE_TOP____R0003
  * A_LINE_TOP____R0003,C0004  -  0004 diff    1  line length    2    line count 11     last_start A_LINE_TOP____R0003
  * A_LINE_TOP____R0005,C0001  -  0001 diff    0  line length    1    line count 12     last_start A_LINE_TOP____R0005
  * A_LINE_TOP____R0005,C0002  -  0002 diff    1  line length    2    line count 12     last_start A_LINE_TOP____R0005
  * 
  * Region R0C0 count_plants   28 count_lines   12 region_price        336
  * 
  * AAAAAA   6    322223  14               ......   0    ......   0           
  * AAA..A   4    212..3   8               ...BB.   2    ...33.   6           
  * AAA..A   4    212..3   8               ...BB.   2    ...22.   4           
  * A..AAA   4    3..322  10               .BB...   2    .33...   6           
  * A..AAA   4    3..212   8               .BB...   2    .22...   4           
  * AAAAAA   6    222112  10               ......   0    ......   0           
  *  Char Count 28    Sum Values 58         Char Count 8    Sum Values 20   
  *  
  *  
  * ---------------------------------------------------------------------------------------------------------------- 
  *  
  * 
  *        -  -  -  -  -            
  *       |A  A  A  A  A|           
  *           -  -  -               
  *       |A|         |A|           
  *       |A|         |A|           
  *       |A|         |A|           
  *           -  -  -               
  *       |A  A  A  A  A|           
  *        -  -  -  -  -            
  * 
  * List of Regions 
  * 
  * Start Region R2C2
  * 
  * 
  * Lines for Region which starts at R2C2
  * 
  * A_LINE_BOTTOM_R0002,C0003  -  0003 diff    0  line length    1    line count 1     last_start A_LINE_BOTTOM_R0002
  * A_LINE_BOTTOM_R0002,C0004  -  0004 diff    1  line length    2    line count 1     last_start A_LINE_BOTTOM_R0002
  * A_LINE_BOTTOM_R0002,C0005  -  0005 diff    1  line length    3    line count 1     last_start A_LINE_BOTTOM_R0002
  * A_LINE_BOTTOM_R0006,C0002  -  0002 diff    0  line length    1    line count 2     last_start A_LINE_BOTTOM_R0006
  * A_LINE_BOTTOM_R0006,C0003  -  0003 diff    1  line length    2    line count 2     last_start A_LINE_BOTTOM_R0006
  * A_LINE_BOTTOM_R0006,C0004  -  0004 diff    1  line length    3    line count 2     last_start A_LINE_BOTTOM_R0006
  * A_LINE_BOTTOM_R0006,C0005  -  0005 diff    1  line length    4    line count 2     last_start A_LINE_BOTTOM_R0006
  * A_LINE_BOTTOM_R0006,C0006  -  0006 diff    1  line length    5    line count 2     last_start A_LINE_BOTTOM_R0006
  * A_LINE_LEFT___C0002,R0002  -  0002 diff    0  line length    1    line count 3     last_start A_LINE_LEFT___C0002
  * A_LINE_LEFT___C0002,R0003  -  0003 diff    1  line length    2    line count 3     last_start A_LINE_LEFT___C0002
  * A_LINE_LEFT___C0002,R0004  -  0004 diff    1  line length    3    line count 3     last_start A_LINE_LEFT___C0002
  * A_LINE_LEFT___C0002,R0005  -  0005 diff    1  line length    4    line count 3     last_start A_LINE_LEFT___C0002
  * A_LINE_LEFT___C0002,R0006  -  0006 diff    1  line length    5    line count 3     last_start A_LINE_LEFT___C0002
  * A_LINE_LEFT___C0006,R0003  -  0003 diff    0  line length    1    line count 4     last_start A_LINE_LEFT___C0006
  * A_LINE_LEFT___C0006,R0004  -  0004 diff    1  line length    2    line count 4     last_start A_LINE_LEFT___C0006
  * A_LINE_LEFT___C0006,R0005  -  0005 diff    1  line length    3    line count 4     last_start A_LINE_LEFT___C0006
  * A_LINE_RIGHT__C0002,R0003  -  0003 diff    0  line length    1    line count 5     last_start A_LINE_RIGHT__C0002
  * A_LINE_RIGHT__C0002,R0004  -  0004 diff    1  line length    2    line count 5     last_start A_LINE_RIGHT__C0002
  * A_LINE_RIGHT__C0002,R0005  -  0005 diff    1  line length    3    line count 5     last_start A_LINE_RIGHT__C0002
  * A_LINE_RIGHT__C0006,R0002  -  0002 diff    0  line length    1    line count 6     last_start A_LINE_RIGHT__C0006
  * A_LINE_RIGHT__C0006,R0003  -  0003 diff    1  line length    2    line count 6     last_start A_LINE_RIGHT__C0006
  * A_LINE_RIGHT__C0006,R0004  -  0004 diff    1  line length    3    line count 6     last_start A_LINE_RIGHT__C0006
  * A_LINE_RIGHT__C0006,R0005  -  0005 diff    1  line length    4    line count 6     last_start A_LINE_RIGHT__C0006
  * A_LINE_RIGHT__C0006,R0006  -  0006 diff    1  line length    5    line count 6     last_start A_LINE_RIGHT__C0006
  * A_LINE_TOP____R0002,C0002  -  0002 diff    0  line length    1    line count 7     last_start A_LINE_TOP____R0002
  * A_LINE_TOP____R0002,C0003  -  0003 diff    1  line length    2    line count 7     last_start A_LINE_TOP____R0002
  * A_LINE_TOP____R0002,C0004  -  0004 diff    1  line length    3    line count 7     last_start A_LINE_TOP____R0002
  * A_LINE_TOP____R0002,C0005  -  0005 diff    1  line length    4    line count 7     last_start A_LINE_TOP____R0002
  * A_LINE_TOP____R0002,C0006  -  0006 diff    1  line length    5    line count 7     last_start A_LINE_TOP____R0002
  * A_LINE_TOP____R0006,C0003  -  0003 diff    0  line length    1    line count 8     last_start A_LINE_TOP____R0006
  * A_LINE_TOP____R0006,C0004  -  0004 diff    1  line length    2    line count 8     last_start A_LINE_TOP____R0006
  * A_LINE_TOP____R0006,C0005  -  0005 diff    1  line length    3    line count 8     last_start A_LINE_TOP____R0006
  * 
  * Region R2C2 count_plants   16 count_lines    8 region_price        128
  * 
  * -----------------------------------------------------------------------------------
  * 
  * Price 128 <- Result Part 2
  * 
  * 
  * 
  * ..........  10    0000000000   0   
  * ..........  10    0000000000   0   
  * ..AAAAA...  10    0032223000  12   
  * ..A...A...  10    0030003000   6   
  * ..A...A...  10    0030003000   6   
  * ..A...A...  10    0030003000   6   
  * ..AAAAA...  10    0022222000  10   
  * ..........  10    0000000000   0   
  * ..........  10    0000000000   0   
  *  Char Count 90    Sum Values 40    
  * 
  * ..........   0    ..........   0   
  * ..........   0    ..........   0   
  * ..AAAAA...   5    ..32223...  12   
  * ..A...A...   2    ..3...3...   6   
  * ..A...A...   2    ..3...3...   6   
  * ..A...A...   2    ..3...3...   6   
  * ..AAAAA...   5    ..22222...  10   
  * ..........   0    ..........   0   
  * ..........   0    ..........   0   
  *  Char Count 16    Sum Values 40
  *  
  *  
  * ---------------------------------------------------------------------------------------------------------------- 
  *  
  * 
  *           -  -             
  *          |A  A|            
  *        -        -  -       
  *       |A  A  A  A  A|      
  *           -  -  -          
  *       |A|         |A|      
  *       |A|         |A|      
  *       |A|         |A|      
  *           -  -  -          
  *       |A  A  A  A  A|      
  *        -  -  -  -  -       
  * 
  * List of Regions 
  * 
  * Start Region R1C3
  * 
  * 
  * Lines for Region which starts at R1C3
  * 
  * A_LINE_BOTTOM_R0002,C0003  -  0003 diff    0  line length    1    line count 1     last_start A_LINE_BOTTOM_R0002
  * A_LINE_BOTTOM_R0002,C0004  -  0004 diff    1  line length    2    line count 1     last_start A_LINE_BOTTOM_R0002
  * A_LINE_BOTTOM_R0002,C0005  -  0005 diff    1  line length    3    line count 1     last_start A_LINE_BOTTOM_R0002
  * A_LINE_BOTTOM_R0006,C0002  -  0002 diff    0  line length    1    line count 2     last_start A_LINE_BOTTOM_R0006
  * A_LINE_BOTTOM_R0006,C0003  -  0003 diff    1  line length    2    line count 2     last_start A_LINE_BOTTOM_R0006
  * A_LINE_BOTTOM_R0006,C0004  -  0004 diff    1  line length    3    line count 2     last_start A_LINE_BOTTOM_R0006
  * A_LINE_BOTTOM_R0006,C0005  -  0005 diff    1  line length    4    line count 2     last_start A_LINE_BOTTOM_R0006
  * A_LINE_BOTTOM_R0006,C0006  -  0006 diff    1  line length    5    line count 2     last_start A_LINE_BOTTOM_R0006
  * A_LINE_LEFT___C0002,R0002  -  0002 diff    0  line length    1    line count 3     last_start A_LINE_LEFT___C0002
  * A_LINE_LEFT___C0002,R0003  -  0003 diff    1  line length    2    line count 3     last_start A_LINE_LEFT___C0002
  * A_LINE_LEFT___C0002,R0004  -  0004 diff    1  line length    3    line count 3     last_start A_LINE_LEFT___C0002
  * A_LINE_LEFT___C0002,R0005  -  0005 diff    1  line length    4    line count 3     last_start A_LINE_LEFT___C0002
  * A_LINE_LEFT___C0002,R0006  -  0006 diff    1  line length    5    line count 3     last_start A_LINE_LEFT___C0002
  * A_LINE_LEFT___C0003,R0001  -  0001 diff    0  line length    1    line count 4     last_start A_LINE_LEFT___C0003
  * A_LINE_LEFT___C0006,R0003  -  0003 diff    0  line length    1    line count 5     last_start A_LINE_LEFT___C0006
  * A_LINE_LEFT___C0006,R0004  -  0004 diff    1  line length    2    line count 5     last_start A_LINE_LEFT___C0006
  * A_LINE_LEFT___C0006,R0005  -  0005 diff    1  line length    3    line count 5     last_start A_LINE_LEFT___C0006
  * A_LINE_RIGHT__C0002,R0003  -  0003 diff    0  line length    1    line count 6     last_start A_LINE_RIGHT__C0002
  * A_LINE_RIGHT__C0002,R0004  -  0004 diff    1  line length    2    line count 6     last_start A_LINE_RIGHT__C0002
  * A_LINE_RIGHT__C0002,R0005  -  0005 diff    1  line length    3    line count 6     last_start A_LINE_RIGHT__C0002
  * A_LINE_RIGHT__C0004,R0001  -  0001 diff    0  line length    1    line count 7     last_start A_LINE_RIGHT__C0004
  * A_LINE_RIGHT__C0006,R0002  -  0002 diff    0  line length    1    line count 8     last_start A_LINE_RIGHT__C0006
  * A_LINE_RIGHT__C0006,R0003  -  0003 diff    1  line length    2    line count 8     last_start A_LINE_RIGHT__C0006
  * A_LINE_RIGHT__C0006,R0004  -  0004 diff    1  line length    3    line count 8     last_start A_LINE_RIGHT__C0006
  * A_LINE_RIGHT__C0006,R0005  -  0005 diff    1  line length    4    line count 8     last_start A_LINE_RIGHT__C0006
  * A_LINE_RIGHT__C0006,R0006  -  0006 diff    1  line length    5    line count 8     last_start A_LINE_RIGHT__C0006
  * A_LINE_TOP____R0001,C0003  -  0003 diff    0  line length    1    line count 9     last_start A_LINE_TOP____R0001
  * A_LINE_TOP____R0001,C0004  -  0004 diff    1  line length    2    line count 9     last_start A_LINE_TOP____R0001
  * A_LINE_TOP____R0002,C0002  -  0002 diff    0  line length    1    line count 10     last_start A_LINE_TOP____R0002
  * A_LINE_TOP____R0002,C0005  -  0005 diff    3  line length    1    line count 11     last_start A_LINE_TOP____R0002
  * A_LINE_TOP____R0002,C0006  -  0006 diff    1  line length    2    line count 11     last_start A_LINE_TOP____R0002
  * A_LINE_TOP____R0006,C0003  -  0003 diff    0  line length    1    line count 12     last_start A_LINE_TOP____R0006
  * A_LINE_TOP____R0006,C0004  -  0004 diff    1  line length    2    line count 12     last_start A_LINE_TOP____R0006
  * A_LINE_TOP____R0006,C0005  -  0005 diff    1  line length    3    line count 12     last_start A_LINE_TOP____R0006
  * 
  * Region R1C3 count_plants   18 count_lines   12 region_price        216
  * 
  * -----------------------------------------------------------------------------------
  * 
  * Region R1C3 count_plants   18 count_lines   12 region_price        216
  * 
  * Price 216 <- Result Part 2
  * 
  * 
  * 
  * ..........  10    0000000000   0   
  * ...AA.....  10    0003300000   6   
  * ..AAAAA...  10    0031123000  10   
  * ..A...A...  10    0030003000   6   
  * ..A...A...  10    0030003000   6   
  * ..A...A...  10    0030003000   6   
  * ..AAAAA...  10    0022222000  10   
  * ..........  10    0000000000   0   
  * ..........  10    0000000000   0   
  *  Char Count 90    Sum Values 44    
  * 
  * ..........   0    ..........   0   
  * ...AA.....   2    ...33.....   6   
  * ..AAAAA...   5    ..31123...  10   
  * ..A...A...   2    ..3...3...   6   
  * ..A...A...   2    ..3...3...   6   
  * ..A...A...   2    ..3...3...   6   
  * ..AAAAA...   5    ..22222...  10   
  * ..........   0    ..........   0   
  * ..........   0    ..........   0   
  *  Char Count 18    Sum Values 44    
  *  
  *  
  * ----------------------------------------------------------------------------------------------------------------
  *  
  * It's a triangular World
  * Try maps with different input line length.  
  * 
  * AAAAAAAA   8    21222113  14       
  * AABBBAA-   7    1121312-  11       
  * AABBAA--   6    111222--   9       
  * AABAA---   5    11322---   9       
  * AAAA----   4    1012----   4       
  * AAA-----   3    102-----   3       
  * AA------   2    12------   3       
  * A-------   1    3-------   3       
  *  Char Count 36    Sum Values 56    
  * 
  * AAAAAAAA   8    21222113  14           ........   0    ........   0       
  * AA...AA-   4    11...12-   5           ..BBB..-   3    ..213..-   6       
  * AA..AA--   4    11..22--   6           ..BB..--   2    ..12..--   3       
  * AA.AA---   4    11.22---   6           ..B..---   1    ..3..---   3       
  * AAAA----   4    1012----   4           ....----   0    ....----   0       
  * AAA-----   3    102-----   3           ...-----   0    ...-----   0       
  * AA------   2    12------   3           ..------   0    ..------   0       
  * A-------   1    3-------   3           .-------   0    .-------   0       
  *  Char Count 30    Sum Values 44         Char Count 6    Sum Values 12     
  * 
  * 
  * part_1_sum_price_total = 1392 <- Result Part 1
  * part_2_sum_price_total = 828 <- Result Part 2
  *      
  * 
  * Lines for Region which starts at R1C2
  * 
  * B_LINE_BOTTOM_R0001,C0004  -  0004 diff    0  line length    1    line count 1     last_start B_LINE_BOTTOM_R0001
  * B_LINE_BOTTOM_R0002,C0003  -  0003 diff    0  line length    1    line count 2     last_start B_LINE_BOTTOM_R0002
  * B_LINE_BOTTOM_R0003,C0002  -  0002 diff    0  line length    1    line count 3     last_start B_LINE_BOTTOM_R0003
  * B_LINE_LEFT___C0002,R0001  -  0001 diff    0  line length    1    line count 4     last_start B_LINE_LEFT___C0002
  * B_LINE_LEFT___C0002,R0002  -  0002 diff    1  line length    2    line count 4     last_start B_LINE_LEFT___C0002
  * B_LINE_LEFT___C0002,R0003  -  0003 diff    1  line length    3    line count 4     last_start B_LINE_LEFT___C0002
  * B_LINE_RIGHT__C0002,R0003  -  0003 diff    0  line length    1    line count 5     last_start B_LINE_RIGHT__C0002
  * B_LINE_RIGHT__C0003,R0002  -  0002 diff    0  line length    1    line count 6     last_start B_LINE_RIGHT__C0003
  * B_LINE_RIGHT__C0004,R0001  -  0001 diff    0  line length    1    line count 7     last_start B_LINE_RIGHT__C0004
  * B_LINE_TOP____R0001,C0002  -  0002 diff    0  line length    1    line count 8     last_start B_LINE_TOP____R0001
  * B_LINE_TOP____R0001,C0003  -  0003 diff    1  line length    2    line count 8     last_start B_LINE_TOP____R0001
  * B_LINE_TOP____R0001,C0004  -  0004 diff    1  line length    3    line count 8     last_start B_LINE_TOP____R0001
  * Region R1C2       count_plants    6 count_lines    8 region_price         48
  * 
  * Start Region R0C0, Plant-Type A
  * 
  * 
  * Lines for Region which starts at R0C0
  * 
  * A_LINE_BOTTOM_R0000,C0002  -  0002 diff    0  line length    1    line count 1     last_start A_LINE_BOTTOM_R0000
  * A_LINE_BOTTOM_R0000,C0003  -  0003 diff    1  line length    2    line count 1     last_start A_LINE_BOTTOM_R0000
  * A_LINE_BOTTOM_R0000,C0004  -  0004 diff    1  line length    3    line count 1     last_start A_LINE_BOTTOM_R0000
  * A_LINE_BOTTOM_R0000,C0007  -  0007 diff    3  line length    1    line count 2     last_start A_LINE_BOTTOM_R0000
  * A_LINE_BOTTOM_R0001,C0006  -  0006 diff    0  line length    1    line count 3     last_start A_LINE_BOTTOM_R0001
  * A_LINE_BOTTOM_R0002,C0005  -  0005 diff    0  line length    1    line count 4     last_start A_LINE_BOTTOM_R0002
  * A_LINE_BOTTOM_R0003,C0004  -  0004 diff    0  line length    1    line count 5     last_start A_LINE_BOTTOM_R0003
  * A_LINE_BOTTOM_R0004,C0003  -  0003 diff    0  line length    1    line count 6     last_start A_LINE_BOTTOM_R0004
  * A_LINE_BOTTOM_R0005,C0002  -  0002 diff    0  line length    1    line count 7     last_start A_LINE_BOTTOM_R0005
  * A_LINE_BOTTOM_R0006,C0001  -  0001 diff    0  line length    1    line count 8     last_start A_LINE_BOTTOM_R0006
  * A_LINE_BOTTOM_R0007,C0000  -  0000 diff    0  line length    1    line count 9     last_start A_LINE_BOTTOM_R0007
  * A_LINE_LEFT___C0000,R0000  -  0000 diff    0  line length    1    line count 10     last_start A_LINE_LEFT___C0000
  * A_LINE_LEFT___C0000,R0001  -  0001 diff    1  line length    2    line count 10     last_start A_LINE_LEFT___C0000
  * A_LINE_LEFT___C0000,R0002  -  0002 diff    1  line length    3    line count 10     last_start A_LINE_LEFT___C0000
  * A_LINE_LEFT___C0000,R0003  -  0003 diff    1  line length    4    line count 10     last_start A_LINE_LEFT___C0000
  * A_LINE_LEFT___C0000,R0004  -  0004 diff    1  line length    5    line count 10     last_start A_LINE_LEFT___C0000
  * A_LINE_LEFT___C0000,R0005  -  0005 diff    1  line length    6    line count 10     last_start A_LINE_LEFT___C0000
  * A_LINE_LEFT___C0000,R0006  -  0006 diff    1  line length    7    line count 10     last_start A_LINE_LEFT___C0000
  * A_LINE_LEFT___C0000,R0007  -  0007 diff    1  line length    8    line count 10     last_start A_LINE_LEFT___C0000
  * A_LINE_LEFT___C0003,R0003  -  0003 diff    0  line length    1    line count 11     last_start A_LINE_LEFT___C0003
  * A_LINE_LEFT___C0004,R0002  -  0002 diff    0  line length    1    line count 12     last_start A_LINE_LEFT___C0004
  * A_LINE_LEFT___C0005,R0001  -  0001 diff    0  line length    1    line count 13     last_start A_LINE_LEFT___C0005
  * A_LINE_RIGHT__C0000,R0007  -  0007 diff    0  line length    1    line count 14     last_start A_LINE_RIGHT__C0000
  * A_LINE_RIGHT__C0001,R0001  -  0001 diff    0  line length    1    line count 15     last_start A_LINE_RIGHT__C0001
  * A_LINE_RIGHT__C0001,R0002  -  0002 diff    1  line length    2    line count 15     last_start A_LINE_RIGHT__C0001
  * A_LINE_RIGHT__C0001,R0003  -  0003 diff    1  line length    3    line count 15     last_start A_LINE_RIGHT__C0001
  * A_LINE_RIGHT__C0001,R0006  -  0006 diff    3  line length    1    line count 16     last_start A_LINE_RIGHT__C0001
  * A_LINE_RIGHT__C0002,R0005  -  0005 diff    0  line length    1    line count 17     last_start A_LINE_RIGHT__C0002
  * A_LINE_RIGHT__C0003,R0004  -  0004 diff    0  line length    1    line count 18     last_start A_LINE_RIGHT__C0003
  * A_LINE_RIGHT__C0004,R0003  -  0003 diff    0  line length    1    line count 19     last_start A_LINE_RIGHT__C0004
  * A_LINE_RIGHT__C0005,R0002  -  0002 diff    0  line length    1    line count 20     last_start A_LINE_RIGHT__C0005
  * A_LINE_RIGHT__C0006,R0001  -  0001 diff    0  line length    1    line count 21     last_start A_LINE_RIGHT__C0006
  * A_LINE_RIGHT__C0007,R0000  -  0000 diff    0  line length    1    line count 22     last_start A_LINE_RIGHT__C0007
  * A_LINE_TOP____R0000,C0000  -  0000 diff    0  line length    1    line count 23     last_start A_LINE_TOP____R0000
  * A_LINE_TOP____R0000,C0001  -  0001 diff    1  line length    2    line count 23     last_start A_LINE_TOP____R0000
  * A_LINE_TOP____R0000,C0002  -  0002 diff    1  line length    3    line count 23     last_start A_LINE_TOP____R0000
  * A_LINE_TOP____R0000,C0003  -  0003 diff    1  line length    4    line count 23     last_start A_LINE_TOP____R0000
  * A_LINE_TOP____R0000,C0004  -  0004 diff    1  line length    5    line count 23     last_start A_LINE_TOP____R0000
  * A_LINE_TOP____R0000,C0005  -  0005 diff    1  line length    6    line count 23     last_start A_LINE_TOP____R0000
  * A_LINE_TOP____R0000,C0006  -  0006 diff    1  line length    7    line count 23     last_start A_LINE_TOP____R0000
  * A_LINE_TOP____R0000,C0007  -  0007 diff    1  line length    8    line count 23     last_start A_LINE_TOP____R0000
  * A_LINE_TOP____R0002,C0004  -  0004 diff    0  line length    1    line count 24     last_start A_LINE_TOP____R0002
  * A_LINE_TOP____R0003,C0003  -  0003 diff    0  line length    1    line count 25     last_start A_LINE_TOP____R0003
  * A_LINE_TOP____R0004,C0002  -  0002 diff    0  line length    1    line count 26     last_start A_LINE_TOP____R0004
  * Region R0C0       count_plants   30 count_lines   26 region_price        780
  * 
  * -----------------------------------------------------------------------------------
  * 
  * Region R1C2       count_plants    6 count_lines    8 region_price         48
  * Region R0C0       count_plants   30 count_lines   26 region_price        780
  *      
  */

  private static final int    ROW_PLUS_1               = 1;

  private static final int    COL_PLUS_1               = 1;

  private static final int    DEBUG_MAX_MAPS_IN_ROW    = 3;

  private static final int    NR_OF_DIGITS_LINE_INFO   = 4;

  private static final long   DEFAULT_CELL_VALUE       = 4;

  private static int          DEBUG_PADDING_VALUE      = 35;

  private static char         DEBUG_PADDING_CHAR       = ' ';

  private static final String KEY_CELL_FENCE_TOP       = "CFT_";

  private static final String KEY_CELL_EDGE            = "EDGE_";

  private static final String KEY_CELL_FENCE_BOTTOM    = "CFB_";

  private static final String KEY_CELL_FENCE_LEFT      = "CFL_";

  private static final String KEY_CELL_FENCE_RIGHT     = "CFR_";

  private static final int    FENCE_1                  = 1;

  private static final int    FENCE_0                  = 0;

  private static final int    FENCE_ERR                = 99;

  private static final String FENCE_1_CHAR_TOP         = "-";

  private static final String FENCE_1_CHAR_BOTTOM      = "-";

  private static final String FENCE_1_CHAR_LEFT        = "|";

  private static final String FENCE_1_CHAR_RIGHT       = "|";

  private static final String FENCE_0_CHAR_TOP         = " ";

  private static final String FENCE_0_CHAR_BOTTOM      = " ";

  private static final String FENCE_0_CHAR_LEFT        = " ";

  private static final String FENCE_0_CHAR_RIGHT       = " ";

  private static final String FENCE_CHAR_EMPTY         = " ";

  private static final String PRAEFIX_PLANT_TYPE       = "PLANT_TYPE_";

  private static final String PRAEFIX_REGION           = "REGION_INFO_";

  private static final String PRAEFIX_FENCE            = "FENCE_INFO_";

  private static final long   DEFAULT_CELL_VALUE_EMPTY = 0;

  private static final char   DEFAULT_CHAR_NOT_FOUND   = '#';

  private static final char   CHAR_EMPTY_SPACE         = '.';

  private static final char   CHAR_DEBUG_ALL           = '_';

  private static final char   CHAR_NO_MAP              = '-';

  private static final String STR_DEBUG_SPACER         = "    ";

  private static final String STR_COMBINE_SPACER       = "    ";

  public static void main( String[] args )
  {
    String test_content_1 = "AAAA,BBCD,BBCC,EEEC";
    String test_content_2 = "OOOOO,OXOXO,OOOOO,OXOXO,OOOOO";
    String test_content_3 = "RRRRIICCFF,RRRRIICCCF,VVRRRCCFFF,VVRCCCJFFF,VVVVCJJCFE,VVIVCCJJEE,VVIIICJJEE,MIIIIIJJEE,MIIISIJEEE,MMMISSJEEE";
    String test_content_4 = "AAAAAA,AAABBA,AAABBA,ABBAAA,ABBAAA,AAAAAA";
    String test_content_5 = "EEEEE,EXXXX,EEEEE,EXXXX,EEEEE";

    String test_triangle_map = "AAAAAAAA,AABBBAA,AABBAA,AABAA,AAAA,AAA,AA,A";

    List< String > test_content_list_1 = Arrays.stream( test_content_1.split( "," ) ).map( String::trim ).collect( Collectors.toList() );
    List< String > test_content_list_2 = Arrays.stream( test_content_2.split( "," ) ).map( String::trim ).collect( Collectors.toList() );
    List< String > test_content_list_3 = Arrays.stream( test_content_3.split( "," ) ).map( String::trim ).collect( Collectors.toList() );
    List< String > test_content_list_4 = Arrays.stream( test_content_4.split( "," ) ).map( String::trim ).collect( Collectors.toList() );
    List< String > test_content_list_5 = Arrays.stream( test_content_5.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    String test_content_square_1 = "..........,..........,..AAAAA...,..A...A...,..A...A...,..A...A...,..AAAAA...,..........,..........";

    String test_content_square_2 = "..........,...AA.....,..AAAAA...,..A...A...,..A...A...,..A...A...,..AAAAA...,..........,..........";

    String test_content_complex_1 = "..........,.AAAA.....,....A.....,..AAAAA...,..A...A...,..A...A...,..A...A...,..AAAAA...,....A.....,....AAAA..,..........";

    String test_content_complex_2 = "..........,.A........,.A.AAA....,.A...A....,.AAAAAAA..,..AA..AAA.,..AAA.AA..,.AAAAAAA..,....A.....,..AAAAA...,..A.A.....,....AAAA..,..........";

    /*
     * https://www.reddit.com/r/adventofcode/comments/1hcfurk/2024_day_12_another_test_case/
     */
    String test_content_reddit = "AAAAAAAA,AACBBDDA,AACBBAAA,ABBAAAAA,ABBADDDA,AAAADADA,AAAAAAAA";

    List< String > list_test_content_square_1 = Arrays.stream( test_content_square_1.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    List< String > list_test_content_square_2 = Arrays.stream( test_content_square_2.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    List< String > list_test_content_complex_1 = Arrays.stream( test_content_complex_1.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    List< String > list_test_content_reddit = Arrays.stream( test_content_reddit.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    List< String > list_test_triangle_map = Arrays.stream( test_triangle_map.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    //String test_content_4 = "OOOOO,OXOXX,OOOXO,OOOOO";

    //String test_content_4 = "......CC..,......CCC.,.....CC...,...CCC....,....C..C..,....CC....,.....C....,..........,..........,..........";
    //
    //String test_content_5 = "....II....,....II....,..........,..........,..........,..I.......,..III.....,.IIIII....,.III.I....,...I......";
    //List< String > test_content_list_4 = Arrays.stream( test_content_5.split( "," ) ).map( String::trim ).collect( Collectors.toList() );
    //
    //String test_content_6 = "..........,..........,..........,..........,....X.....,..........,..........,.........X,..........,..........";
    //List< String > test_content_list_6 = Arrays.stream( test_content_6.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    //calculateGrid( test_content_list_6, true );
    //calculateGrid( test_content_list_1, true );

    //calculateGrid( test_content_list_1, true );
    //calculateGrid( test_content_list_2, true );
    calculateGrid( test_content_list_3, true );

    //calculateGrid( list_test_content_square_1, true );
    //calculateGrid( list_test_content_square_2, true );
    //calculateGrid( list_test_content_complex_1, true );
    //calculateGrid( list_test_content_reddit, true );
    calculateGrid( list_test_triangle_map, true );

    //calculateGrid( getListProd(), false );
  }

  private static HashMap< String, List< String > > m_hash_map_region_info = new HashMap< String, List< String > >();

  private static HashMap< String, Long >           m_hash_map_cell_values = null;

  private static int                               m_max_col              = 0;

  private static void calculateGrid( List< String > pListInput, boolean pKnzDebug )
  {
    /*
     * *******************************************************************************************************
     * Initializing Hash-Maps and Property-Instances
     * *******************************************************************************************************
     */
    clearHashMapCellValues();

    m_hash_map_region_info = new HashMap< String, List< String > >();

    m_max_col = 0;

    Properties prop_debug_plant_types = new Properties();

    Properties prop_grid_plants = new Properties();

    Properties prop_grid_start_regions = new Properties();

    long part_1_sum_price_total = 0;
    long part_2_sum_price_total = 0;

    int current_row = 0;
    int current_col = 0;

    /*
     * *******************************************************************************************************
     * Initializing the grid
     * *******************************************************************************************************
     */

    for ( String input_str : pListInput )
    {
      /*
       * Find the max input line length.
       * 
       * (Not needed for Advent of Code challenge, but hey, I can)
       */
      if ( input_str.length() > m_max_col )
      {
        m_max_col = input_str.length();
      }

      for ( current_col = 0; current_col < input_str.length(); current_col++ )
      {
        /*
         * For the recursive region search the coordinates and plant type are 
         * stored in a property instance.
         * 
         * The key are the coordinates, the value is the plant type.
         * During the recursive region search, these information is changed.
         * Every visited coordinate will be removed.
         */
        prop_grid_plants.setProperty( "R" + current_row + "C" + current_col, "" + input_str.charAt( current_col ) );

        /*
         * Setting the cell value
         * 
         * Each cell of the grid is to be considered a single area.
         * This area is bordered by neighboring cells on all four sides.
         * In the worst case, no plant type of the same type borders the cell.
         * Therefore, the value 4 is assumed for a single cell.
         */
        setCellValue( current_row, current_col, DEFAULT_CELL_VALUE );

        /*
         * Setting Grid-Cell-Fences
         * 
         * At the start, each grid cell is its own 1 plant region.
         * So it must have 4 fences.
         */
        setCellFence( KEY_CELL_FENCE_TOP, current_row, current_col, FENCE_1 );
        setCellFence( KEY_CELL_FENCE_BOTTOM, current_row, current_col, FENCE_1 );
        setCellFence( KEY_CELL_FENCE_LEFT, current_row, current_col, FENCE_1 );
        setCellFence( KEY_CELL_FENCE_RIGHT, current_row, current_col, FENCE_1 );
      }

      current_row++;
    }

    /*
     * *******************************************************************************************************
     * Calculating which Grid-Cells merge together
     * *******************************************************************************************************
     */

    /*
     * Loop over the grid.
     * 
     * Grid-Cells with the plant type specified as CHAR_EMPTY_SPACE are ignored.
     * 
     * For every valid plant type two check's are made.
     * Combine horizontal (Two same plant types next to each other)
     * Combine vertical (Two same plant types stand above of each other)
     * 
     * If there is a match, then two actions are taken:
     * - Reducing the cell value
     * - Removing Fence-Lines
     */
    current_row = 0;

    for ( String input_str : pListInput )
    {
      for ( current_col = 0; current_col < input_str.length(); current_col++ )
      {
        char current_cell_char = input_str.charAt( current_col );

        /*
         * Check for valid plant type
         * ... or, if the current_cell_char is not empty space 
         */
        if ( current_cell_char != CHAR_EMPTY_SPACE )
        {
          /*
           * DEBUG: Store the plant type in a Property instance
           *        Build a dictionary of the plant types.
           */
          prop_debug_plant_types.setProperty( PRAEFIX_PLANT_TYPE + current_cell_char, "" + current_cell_char );

          long current_cell_value = getCellValueDef( current_row, current_col, DEFAULT_CELL_VALUE );

          /*
           * Check next char for same plant type
           */

          int next_col = current_col + COL_PLUS_1;

          if ( ( next_col < input_str.length() ) && ( input_str.charAt( next_col ) == current_cell_char ) )
          {
            /*
             * Found two matching plant types horizontal
             */

            /*
             * Removing Fence's 
             * 1. remove the right fence from the current position
             * 2. remove the left fence from the next position
             */
            setCellFence( KEY_CELL_FENCE_RIGHT, current_row, current_col, FENCE_0 );
            setCellFence( KEY_CELL_FENCE_LEFT, current_row, next_col, FENCE_0 );

            /*
             * Reducing the cell value
             * 1. reduce the current cell value by one
             * 2. save the reduced cell value in the hash map
             * 3. get the next cell value from the hash map
             * 4. reduce the next cell value by one
             * 5. save the reduced next cell value in the hash map
             */
            current_cell_value--;

            setCellValue( current_row, current_col, current_cell_value );

            long next_cell_value = getCellValueDef( current_row, next_col, DEFAULT_CELL_VALUE );

            next_cell_value--;

            setCellValue( current_row, next_col, next_cell_value );
          }

          /*
           * Check next row same col for same plant type
           */

          int next_row = current_row + ROW_PLUS_1;

          if ( ( next_row < pListInput.size() ) && ( getChar( pListInput, next_row, current_col, DEFAULT_CHAR_NOT_FOUND ) == current_cell_char ) )
          {
            /*
             * Found two matching plant types vertical
             */

            /*
             * Removing Fence's 
             * 1. remove the bottom fence from the current position
             * 2. remove the top fence from the next position
             */
            setCellFence( KEY_CELL_FENCE_BOTTOM, current_row, current_col, FENCE_0 );
            setCellFence( KEY_CELL_FENCE_TOP, next_row, current_col, FENCE_0 );

            /*
             * Found two matching plant types
             * 1. reduce the current cell value by one
             * 2. save the reduced cell value in the hash map
             * 3. get the next cell value from the hash map
             * 4. reduce the next cell value by one
             * 5. save the reduced next cell value in the hash map
             */
            current_cell_value--;

            setCellValue( current_row, current_col, current_cell_value );

            long next_cell_value = getCellValueDef( next_row, current_col, DEFAULT_CELL_VALUE );

            next_cell_value--;

            setCellValue( next_row, current_col, next_cell_value );
          }
        }
      }

      current_row++;
    }

    /*
     * *******************************************************************************************************
     * Collecting the regions and Cell-Information
     * *******************************************************************************************************
     */

    /*
     * Loop over the grid.
     * Grid-Cells with empty space are ignored.
     * 
     * For every valid plant type two list are calculated.
     * - Region List 
     * - Fence List
     * 
     * Both lists are stored in the global hashmap "m_hash_map_region_info"
     * 
     * The lists are calculated with a recursive function. 
     */
    int max_col = m_max_col + 1;

    for ( current_row = 0; current_row < pListInput.size(); current_row++ )
    {
      for ( current_col = 0; current_col < max_col; current_col++ )
      {
        char current_char = prop_grid_plants.getProperty( "R" + current_row + "C" + current_col, "." ).charAt( 0 );

        if ( current_char != CHAR_EMPTY_SPACE )
        {
          wl( "Calulating Region Entry at " + "R" + current_row + "C" + current_col + ", Plant Type " + current_char );

          /*
           * List to store all coordinates for one region.
           * Something like this: [R1C3, R1C4, R2C4, R2C3]
           */
          List< String > list_regions = new ArrayList< String >();

          /*
           * List to store all Fence-Lines for one region.
           * The list gets sorted.
           * 
           * Input: "..........,....A.....,..........,";
           * 
           * List:  [A_LINE_BOTTOM_R0001,C0004, A_LINE_LEFT___C0004,R0001, A_LINE_RIGHT__C0004,R0001, A_LINE_TOP____R0001,C0004]
           */
          List< String > list_line_info = new ArrayList< String >();

          getPlantRegion( list_line_info, list_regions, prop_grid_plants, current_row, current_col, current_char, pListInput.size(), max_col );

          m_hash_map_region_info.put( PRAEFIX_REGION + "R" + current_row + "C" + current_col, list_regions );

          list_line_info.sort( null );

          m_hash_map_region_info.put( PRAEFIX_FENCE + "R" + current_row + "C" + current_col, list_line_info );

          prop_grid_start_regions.setProperty( "R" + current_row + "C" + current_col, "" + current_char );
        }
      }
    }

    if ( pKnzDebug )
    {
      wl( "" );
      wl( "List of Regions " );
    }

    /*
     * *******************************************************************************************************
     * Calculating Part 1
     * *******************************************************************************************************
     */

    long sum_plants_count = 0;

    for ( Map.Entry< String, List< String > > map_entry : m_hash_map_region_info.entrySet() )
    {
      if ( map_entry.getKey().startsWith( PRAEFIX_REGION ) )
      {
        if ( pKnzDebug )
        {
          wl( "" );
          wl( "Calculating Part 01 - Region " + map_entry.getKey() );
        }

        long current_region_sum_values = 0;

        long current_region_sum_plants = 0;

        for ( String grid_coordinates : map_entry.getValue() )
        {
          long cell_value = getLongValue( grid_coordinates, 0 );

          current_region_sum_values += cell_value;

          current_region_sum_plants++;

          if ( pKnzDebug )
          {
            wl( "  -   " + FkStringFeld.getFeldRechtsMin( current_region_sum_plants, 5 ) + " " + grid_coordinates + "  " + cell_value + "   " + FkStringFeld.getFeldRechtsMin( current_region_sum_values, 7 ) + "  " + FkStringFeld.getFeldRechtsMin( current_region_sum_plants * current_region_sum_values, 7 ) );
          }
        }

        sum_plants_count += current_region_sum_plants;

        part_1_sum_price_total += ( current_region_sum_values * current_region_sum_plants );
      }
    }

    /*
     * *******************************************************************************************************
     * Calculating Part 2
     * *******************************************************************************************************
     */

    String summary_str_regions = "";

    for ( String prop_key : prop_grid_start_regions.stringPropertyNames() )
    {
      if ( pKnzDebug )
      {
        wl( "" );
        wl( "Start Region " + prop_key + ", Plant-Type " + prop_grid_start_regions.getProperty( prop_key ) );
        wl( "" );
      }

      List< String > list_regions = m_hash_map_region_info.get( PRAEFIX_REGION + prop_key );

      List< String > list_fence = m_hash_map_region_info.get( PRAEFIX_FENCE + prop_key );

      long count_plants = list_regions.size();

      long count_lines = getLineCount( prop_key, list_fence, pKnzDebug );

      long part_2_region_price = count_lines * count_plants;

      part_2_sum_price_total += part_2_region_price;

      String debug_string = "Region " + FkStringFeld.getFeldLinksMin( prop_key, 10 ) + " count_plants " + FkStringFeld.getFeldRechtsMin( count_plants, 4 ) + " count_lines " + FkStringFeld.getFeldRechtsMin( count_lines, 4 ) + " region_price " + FkStringFeld.getFeldRechtsMin( part_2_region_price, 10 );

      summary_str_regions += "\n" + debug_string;

      if ( pKnzDebug )
      {
        wl( debug_string );
      }
    }

    wl( "\n-----------------------------------------------------------------------------------" );

    if ( pKnzDebug )
    {
      wl( summary_str_regions );
    }

    wl( "" );
    wl( "Price " + part_2_sum_price_total + " <- Result Part 2" );
    wl( "" );

    /*
     * *******************************************************************************************************
     * Doing some Debug-Stuff
     * *******************************************************************************************************
     */

    if ( pKnzDebug )
    {
      wl( "" );
      wl( "" );

      String debug_plant_types = "";

      wl( getDebugMapPart1( pListInput, CHAR_DEBUG_ALL ) );

      for ( String prop_key : prop_debug_plant_types.stringPropertyNames() )
      {
        if ( prop_key.startsWith( PRAEFIX_PLANT_TYPE ) )
        {
          char current_plant_type = prop_debug_plant_types.getProperty( prop_key, "" + DEFAULT_CHAR_NOT_FOUND ).charAt( 0 );

          if ( current_plant_type != DEFAULT_CHAR_NOT_FOUND )
          {
            debug_plant_types += current_plant_type;

            prop_debug_plant_types.setProperty( "" + current_plant_type, getDebugMapPart1( pListInput, current_plant_type ) );
          }
        }
      }

      char[] array_chars = debug_plant_types.toCharArray();

      java.util.Arrays.sort( array_chars );

      String sorted_plant_types = new String( array_chars );

      String debug_map_str = "";

      int debug_map_nr = 1;

      for ( char char_c : sorted_plant_types.toCharArray() )
      {
        String map_cur = prop_debug_plant_types.getProperty( "" + char_c );

        if ( debug_map_nr > 1 )
        {
          debug_map_str = combineStrings( debug_map_str, map_cur );
        }
        else
        {
          debug_map_str = map_cur;
        }

        if ( debug_map_nr == DEBUG_MAX_MAPS_IN_ROW )
        {
          wl( debug_map_str + "\n\n" );

          debug_map_str = null;

          debug_map_nr = 0;
        }

        debug_map_nr++;
      }

      if ( debug_map_str != null )
      {
        wl( debug_map_str + "\n\n" );
      }

      //wl( getDebugMapPart2( pListInput, 'A' ) );
      //wl( getDebugMapPart2( pListInput, 'B' ) );
    }

    wl( "" );
    wl( "" );
    wl( "part_1_sum_price_total = " + part_1_sum_price_total + " <- Result Part 1" );
    wl( "part_2_sum_price_total = " + part_2_sum_price_total + " <- Result Part 2" );
    wl( "" );

    clearHashMapCellValues();
  }

  private static long getLineCount( String pRegionStartCoordinates, List< String > pListLines, boolean pKnzDebug )
  {
    if ( pKnzDebug )
    {
      wl( "" );
      wl( "Lines for Region which starts at " + pRegionStartCoordinates );
      wl( "" );
    }

    long difference_to_last_value = 0;

    long last_value = -10;

    long line_count = 0;

    long line_length = 0;

    /*
     * A_LINE_BOTTOM_R0000,C0003  -  0003 diff    0  line length    1    line count 1     last_start A_LINE_BOTTOM_R0000
     * A_LINE_BOTTOM_R0000,C0004  -  0004 diff    1  line length    2    line count 1     last_start A_LINE_BOTTOM_R0000
     * A_LINE_BOTTOM_R0002,C0001  -  0001 diff    0  line length    1    line count 2     last_start A_LINE_BOTTOM_R0002 <- Change in the start string
     * A_LINE_BOTTOM_R0002,C0002  -  0002 diff    1  line length    2    line count 2     last_start A_LINE_BOTTOM_R0002    Horizontal line on row 2
     */

    String last_start = "start";

    for ( String cur_line_str : pListLines )
    {
      /*
       * Get the last NR_OF_DIGITS_LINE_INFO characters from the current line.
       * 
       * This value represents:
       * Horizontal line = column value
       * Vertical line   = row value
       */
      String last_bits = cur_line_str.substring( cur_line_str.length() - NR_OF_DIGITS_LINE_INFO );

      /*
       * If the current line, starts with the start-information 
       * from the last line, then it is the same row or column.
       */
      if ( cur_line_str.startsWith( last_start ) )
      {
        /*
         * Calculate the difference.
         * In a continious line, the new number is 1 higher than the last number.
         */
        difference_to_last_value = Long.parseLong( last_bits ) - last_value;

        if ( difference_to_last_value == 1 )
        {
          /*
           * Difference is 1, the line is one cell longer
           * (... we don't need this number)
           */
          line_length++;
        }
        else
        {
          /*
           * Difference is not 1.
           * We found a new line.
           * Line length is 1.
           * Line count is one more.
           */
          line_length = 1;
          line_count++;
        }
      }
      else
      {
        /*
         * New line, because the start-info has changed
         */

        difference_to_last_value = 0;

        line_length = 1;

        /*
         * Line count is one more
         */
        line_count++;

        String[] bits_str = cur_line_str.split( "," );

        last_start = bits_str[ 0 ];
      }

      if ( pKnzDebug )
      {
        wl( cur_line_str + "  -  " + last_bits + " diff " + FkStringFeld.getFeldRechtsMin( "" + difference_to_last_value, 4 ) + "  line length " + FkStringFeld.getFeldRechtsMin( line_length, 4 ) + "    line count " + line_count + "     last_start " + last_start );
      }

      /*
       * Set the new last value
       */
      last_value = Long.parseLong( last_bits );
    }

    return line_count;
  }

  private static void getPlantRegion( List< String > pListLines, List< String > pListRegions, Properties pGrid, long pRow, long pCol, char pTargetPlantType, int pMaxRow, int pMaxCol )
  {
    /*
     * The new row exceeds the max rows, so no match 
     */
    if ( pRow > pMaxRow )
    {
      return;
    }

    /*
     * The new col exceeds the max cols, so no match
     */
    if ( pCol > pMaxCol )
    {
      return;
    }

    /*
     * Get the plant type from the grid.
     * The grid is just a property-instance.
     */
    char grid_plant_type = pGrid.getProperty( "R" + pRow + "C" + pCol, "." ).charAt( 0 );

    /*
     * If the plant type from the grid doesn't match the 
     * target plant type, so its no match.
     */
    if ( grid_plant_type != pTargetPlantType )
    {
      return;
    }

    /*
     * Found match for plant type.
     * This match can also be the first entry grid coordinates.
     */

    /*
     * Add the Coordinates to the List of regions
     */
    pListRegions.add( "R" + pRow + "C" + pCol );

    /*
     * Remove the Char from the grid
     * Preventing this coordinates to be found again by this recursive function.
     * It will go to the left and upwards, just to find odly shapes.
     */
    pGrid.setProperty( "R" + pRow + "C" + pCol, "." );

    /*
     * Get all 4 fence info settings from the hashmap.
     * 
     * A fence is set, when its value is 1 (=FENCE_1). 
     * A fence is not present, when its value is 0 (=FENCE_0).
     */
    long fence_top = getCellFence( KEY_CELL_FENCE_TOP, pRow, pCol );

    long fence_bottom = getCellFence( KEY_CELL_FENCE_BOTTOM, pRow, pCol );

    long fence_left = getCellFence( KEY_CELL_FENCE_LEFT, pRow, pCol );

    long fence_right = getCellFence( KEY_CELL_FENCE_RIGHT, pRow, pCol );

    if ( fence_top == FENCE_1 )
    {
      pListLines.add( grid_plant_type + "_LINE_TOP____R" + getNumberWithLeadingZeros( pRow, NR_OF_DIGITS_LINE_INFO ) + ",C" + getNumberWithLeadingZeros( pCol, NR_OF_DIGITS_LINE_INFO ) );
    }

    if ( fence_bottom == FENCE_1 )
    {
      pListLines.add( grid_plant_type + "_LINE_BOTTOM_R" + getNumberWithLeadingZeros( pRow, NR_OF_DIGITS_LINE_INFO ) + ",C" + getNumberWithLeadingZeros( pCol, NR_OF_DIGITS_LINE_INFO ) );
    }

    if ( fence_left == FENCE_1 )
    {
      pListLines.add( grid_plant_type + "_LINE_LEFT___C" + getNumberWithLeadingZeros( pCol, NR_OF_DIGITS_LINE_INFO ) + ",R" + getNumberWithLeadingZeros( pRow, NR_OF_DIGITS_LINE_INFO ) );
    }

    if ( fence_right == FENCE_1 )
    {
      pListLines.add( grid_plant_type + "_LINE_RIGHT__C" + getNumberWithLeadingZeros( pCol, NR_OF_DIGITS_LINE_INFO ) + ",R" + getNumberWithLeadingZeros( pRow, NR_OF_DIGITS_LINE_INFO ) );
    }

    /*
     * 
     * Some preparations for edge finding ... since i learned that is another way for solving part 2
     * 
    
    long edges = 0;
    
    if ( ( fence_top == FENCE_1 ) && ( fence_left == FENCE_1 ) )
    {
      edges += 1;
    }
    
    if ( ( fence_top == FENCE_1 ) && ( fence_right == FENCE_1 ) )
    {
      edges += 1;
    }
    
    if ( ( fence_bottom == FENCE_1 ) && ( fence_right == FENCE_1 ) )
    {
      edges += 1;
    }
    
    if ( ( fence_bottom == FENCE_1 ) && ( fence_left == FENCE_1 ) )
    {
      edges += 1;
    }
    
    if ( edges > 0 )
    {
      setCellEdge( pRow, pCol, edges );
    }
    
    */

    /*
     * Find more plants of the same type to the right
     */
    getPlantRegion( pListLines, pListRegions, pGrid, pRow, pCol + 1, grid_plant_type, pMaxRow, pMaxCol );

    /*
     * Find more plants beneath this coordinates
     */
    getPlantRegion( pListLines, pListRegions, pGrid, pRow + 1, pCol, grid_plant_type, pMaxRow, pMaxCol );

    /*
     * Go to the left
     */
    getPlantRegion( pListLines, pListRegions, pGrid, pRow, pCol - 1, grid_plant_type, pMaxRow, pMaxCol );

    /*
     * Go up
     */
    getPlantRegion( pListLines, pListRegions, pGrid, pRow - 1, pCol, grid_plant_type, pMaxRow, pMaxCol );

    return;
  }

  private static String getDebugMapPart1( List< String > pListInput, char pChar )
  {
    String result_str = "";

    int current_row = 0;

    long sum_cell_values = 0;
    long sum_char_count = 0;

    for ( String input_str : pListInput )
    {
      long sum_row_val = 0;
      long sum_row_count = 0;

      String str_map_char = "";
      String str_map_values = "";
      String str_map_edges = "";

      long input_str_len = input_str.length();

      for ( int current_col = 0; current_col < m_max_col; current_col++ )
      {
        if ( current_col >= input_str_len )
        {
          str_map_values += CHAR_NO_MAP;

          str_map_char += CHAR_NO_MAP;

          str_map_edges += CHAR_NO_MAP;
        }
        else if ( ( input_str.charAt( current_col ) == pChar ) || ( pChar == CHAR_DEBUG_ALL ) )
        {
          str_map_char += input_str.charAt( current_col );

          sum_char_count++;

          sum_row_count++;

          long current_cell_value = getCellValueDef( current_row, current_col, DEFAULT_CELL_VALUE_EMPTY );

          sum_row_val += (long) current_cell_value;

          sum_cell_values += (long) current_cell_value;

          str_map_values += current_cell_value;

          str_map_edges += getCellEdge( current_row, current_col );
        }
        else
        {
          str_map_values += CHAR_EMPTY_SPACE;

          str_map_char += CHAR_EMPTY_SPACE;

          str_map_edges += CHAR_EMPTY_SPACE;
        }
      }

      current_row++;

      result_str += padRight( str_map_char + " " + FkStringFeld.getFeldRechtsMin( sum_row_count, 3 ) + STR_DEBUG_SPACER + str_map_values + " " + FkStringFeld.getFeldRechtsMin( sum_row_val, 3 ), DEBUG_PADDING_VALUE, DEBUG_PADDING_CHAR ) + "\n";

      //result_str += padRight( str_map_char + " " + FkStringFeld.getFeldRechtsMin( sum_row_count, 3 ) + STR_DEBUG_SPACER + str_map_values + " " + FkStringFeld.getFeldRechtsMin( sum_row_val, 3 ) + "   " + str_map_edges, DEBUG_PADDING_VALUE, DEBUG_PADDING_CHAR ) + "\n";
    }

    String str_map_char = " Char Count " + sum_char_count + " ";
    String str_map_values = " Sum Values " + sum_cell_values + " ";

    result_str += padRight( str_map_char + "  " + str_map_values, DEBUG_PADDING_VALUE, DEBUG_PADDING_CHAR ) + "\n";

    return result_str;
  }

  private static String getDebugMapPart2( List< String > pListInput, char pChar )
  {
    String result_str = "";

    String str_map_empty_cell_row = FENCE_CHAR_EMPTY + FENCE_CHAR_EMPTY + FENCE_CHAR_EMPTY;
    String str_map_overflow_row = "";

    str_map_overflow_row += CHAR_NO_MAP;
    str_map_overflow_row += CHAR_NO_MAP;
    str_map_overflow_row += CHAR_NO_MAP;
//    
//    + CHAR_NO_MAP  + CHAR_NO_MAP + CHAR_NO_MAP;
//
    int current_row = 0;

    long sum_cell_values = 0;
    long sum_char_count = 0;

    for ( String input_str : pListInput )
    {
      long sum_row_val = 0;
      long sum_row_count = 0;

      String str_map_values = "";

      String str_above = "";
      String str_middle = "";
      String str_bottom = "";
      long input_str_len = input_str.length();

      for ( int current_col = 0; current_col < m_max_col; current_col++ )
      {
        if ( current_col >= input_str_len )
        {
          str_above += str_map_overflow_row;
          str_middle += str_map_overflow_row;
          str_bottom += str_map_overflow_row;

          str_map_values += str_map_overflow_row;
        }
        else if ( ( input_str.charAt( current_col ) == pChar ) || ( pChar == CHAR_DEBUG_ALL ) )
        {
          sum_char_count++;
          sum_row_count++;

          str_above += FENCE_CHAR_EMPTY + getCellFenceChar( KEY_CELL_FENCE_TOP, current_row, current_col ) + FENCE_CHAR_EMPTY;

          str_middle += getCellFenceChar( KEY_CELL_FENCE_LEFT, current_row, current_col );
          str_middle += input_str.charAt( current_col );
          str_middle += getCellFenceChar( KEY_CELL_FENCE_RIGHT, current_row, current_col );

          str_bottom += FENCE_CHAR_EMPTY + getCellFenceChar( KEY_CELL_FENCE_BOTTOM, current_row, current_col ) + FENCE_CHAR_EMPTY;

          long current_cell_value = 0l;

          sum_row_val += (long) current_cell_value;

          sum_cell_values += (long) current_cell_value;

          str_map_values += FENCE_CHAR_EMPTY + current_cell_value + FENCE_CHAR_EMPTY;
        }
        else
        {
          str_above += str_map_empty_cell_row;
          str_middle += str_map_empty_cell_row;
          str_bottom += str_map_empty_cell_row;

          str_map_values += str_map_empty_cell_row;
        }
      }

      current_row++;

      result_str += padRight( str_above + " " + FkStringFeld.getFeldRechtsMin( sum_row_count, 3 ) + STR_DEBUG_SPACER + str_map_empty_cell_row + " " + FkStringFeld.getFeldRechtsMin( sum_row_val, 3 ), DEBUG_PADDING_VALUE, DEBUG_PADDING_CHAR ) + "\n";
      result_str += padRight( str_middle + " " + FkStringFeld.getFeldRechtsMin( sum_row_count, 3 ) + STR_DEBUG_SPACER + str_map_values + " " + FkStringFeld.getFeldRechtsMin( sum_row_val, 3 ), DEBUG_PADDING_VALUE, DEBUG_PADDING_CHAR ) + "\n";
      result_str += padRight( str_bottom + " " + FkStringFeld.getFeldRechtsMin( sum_row_count, 3 ) + STR_DEBUG_SPACER + str_map_empty_cell_row + " " + FkStringFeld.getFeldRechtsMin( sum_row_val, 3 ), DEBUG_PADDING_VALUE, DEBUG_PADDING_CHAR ) + "\n";
    }

    String str_map_char = " Char Count " + sum_char_count + " ";
    String str_map_values = " Sum Values " + sum_cell_values + " ";

    result_str += padRight( str_map_char + "  " + str_map_values, DEBUG_PADDING_VALUE, DEBUG_PADDING_CHAR ) + "\n";

    return result_str;
  }

  private static char getChar( List< String > pList, int pCurrentRow, int pCurrentCol, char pDefaultChar )
  {
    int target_row = pCurrentRow;
    int target_col = pCurrentCol;

    if ( ( target_row >= 0 ) && ( target_row < pList.size() ) )
    {
      String target_str = pList.get( target_row );

      if ( ( target_col >= 0 ) && ( target_col < target_str.length() ) )
      {
        return target_str.charAt( target_col );
      }
    }

    return pDefaultChar;
  }

  private static HashMap< String, Long > getHashMapCellValues()
  {
    if ( m_hash_map_cell_values == null )
    {
      m_hash_map_cell_values = new HashMap< String, Long >();
    }

    return m_hash_map_cell_values;
  }

  private static void clearHashMapCellValues()
  {
    if ( m_hash_map_cell_values != null )
    {
      m_hash_map_cell_values.clear();
    }

    m_hash_map_cell_values = null;
  }

  private static long getLongValue( String pKey, long pDefaultValue )
  {
    Long long_value = getHashMapCellValues().get( pKey );

    if ( long_value != null )
    {
      return long_value.intValue();
    }

    return pDefaultValue;
  }

  private static long getCellValueDef( int pRow, int pCol, long pDefaultValue )
  {
    Long long_value = getHashMapCellValues().get( "R" + pRow + "C" + pCol );

    if ( long_value != null )
    {
      return long_value.intValue();
    }

    return setCellValue( pRow, pCol, pDefaultValue );
  }

  private static long setCellValue( int pRow, int pCol, long pValue )
  {
    getHashMapCellValues().put( "R" + pRow + "C" + pCol, Long.valueOf( pValue ) );

    return pValue;
  }

  private static long setCellFence( String pFenceType, long pRow, long pCol, long pValue )
  {
    getHashMapCellValues().put( pFenceType + "R" + pRow + "C" + pCol, Long.valueOf( pValue ) );

    return pValue;
  }

  private static long getCellFence( String pFenceType, long pRow, long pCol )
  {
    Long long_value = getHashMapCellValues().get( pFenceType + "R" + pRow + "C" + pCol );

    if ( long_value != null )
    {
      return long_value.longValue();
    }

    return FENCE_ERR;
  }

  private static long setCellEdge( long pRow, long pCol, long pValue )
  {
    getHashMapCellValues().put( KEY_CELL_EDGE + "R" + pRow + "C" + pCol, Long.valueOf( pValue ) );

    return pValue;
  }

  private static long getCellEdge( long pRow, long pCol )
  {
    Long long_value = getHashMapCellValues().get( KEY_CELL_EDGE + "R" + pRow + "C" + pCol );

    if ( long_value != null )
    {
      return long_value.longValue();
    }

    return 0l;
  }

  private static String getCellFenceChar( String pFenceType, int pRow, int pCol )
  {
    Long long_instance = getHashMapCellValues().get( pFenceType + "R" + pRow + "C" + pCol );

    if ( long_instance != null )
    {
      long long_value = long_instance.longValue();

      if ( pFenceType.equals( KEY_CELL_FENCE_TOP ) )
      {
        return ( long_value == FENCE_1 ? FENCE_1_CHAR_TOP : FENCE_0_CHAR_TOP );
      }

      if ( pFenceType.equals( KEY_CELL_FENCE_BOTTOM ) )
      {
        return ( long_value == FENCE_1 ? FENCE_1_CHAR_BOTTOM : FENCE_0_CHAR_BOTTOM );
      }

      if ( pFenceType.equals( KEY_CELL_FENCE_LEFT ) )
      {
        return ( long_value == FENCE_1 ? FENCE_1_CHAR_LEFT : FENCE_0_CHAR_LEFT );
      }

      if ( pFenceType.equals( KEY_CELL_FENCE_RIGHT ) )
      {
        return ( long_value == FENCE_1 ? FENCE_1_CHAR_RIGHT : FENCE_0_CHAR_RIGHT );
      }
    }

    return FENCE_CHAR_EMPTY;
  }

  private static List< String > getListProd()
  {
    int row_count = 0;

    List< String > string_array = new ArrayList< String >();

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day12_input.txt";

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

  private static String combineStrings( String pString1, String pString2 )
  {
    String[] lines1 = pString1 != null ? pString1.split( "\r?\n" ) : new String[ 0 ];

    String[] lines2 = pString2 != null ? pString2.split( "\r?\n" ) : new String[ 0 ];

    int max_lines = Math.max( lines1.length, lines2.length );

    StringBuilder string_builder = new StringBuilder();

    for ( int line_index = 0; line_index < max_lines; line_index++ )
    {
      String str_a = line_index < lines1.length ? lines1[ line_index ] : "";

      String str_b = line_index < lines2.length ? lines2[ line_index ] : "";

      string_builder.append( str_a ).append( STR_COMBINE_SPACER ).append( str_b );

      if ( line_index < max_lines - 1 )
      {
        string_builder.append( "\n" );
      }
    }

    return string_builder.toString();
  }

  private static String padRight( String pString, int pLength, char pPadChar )
  {
    if ( pString == null ) pString = "";

    int padLength = Math.max( 0, pLength - pString.length() );

    if ( padLength <= 0 ) return pString;

    StringBuilder string_builder = new StringBuilder( pLength );

    string_builder.append( pString );

    for ( int char_count = 0; char_count < padLength; char_count++ )
    {
      string_builder.append( pPadChar );
    }

    return string_builder.toString();
  }

  /**
   * @param pZahl die Zahl
   * @param pLaenge die vorgegebene Laenge
   * @return ein String der vorgegebenen Laenge mit den fuehrenden Nullen.
   */
  private static String getNumberWithLeadingZeros( long pZahl, int pLaenge )
  {
    return getFeldRechts( "" + pZahl, "0", pLaenge );
  }

  /**
   * @param pInhalt der Inhalt des Feldes
   * @param pAuffuellZeichen das zu benutzende Auffuellzeichen
   * @param pLaenge die Laenge
   * @return ein String der vorgegebenen Laenge und dem Inhalt rechts ausgerichtet
   */
  private static String getFeldRechts( String pInhalt, String pAuffuellZeichen, int pLaenge )
  {
    if ( pInhalt == null )
    {
      pInhalt = "";
    }

    if ( pInhalt.length() >= pLaenge )
    {
      return pInhalt.substring( 0, pLaenge );
    }

    return getNChars( pLaenge - pInhalt.length(), pAuffuellZeichen ) + pInhalt;
  }

  /**
   * <pre>
   * Gibt einen String in der angegebenen Laenge und der angegebenen Zeichenfolge zurueck.
   *  
   * Ist die Laenge negativ oder 0, wird ein Leerstring zurueckgegeben
   * 
   * Ist der Parameeter "pZeichen" gleich null, wird ein Leerstring zurueckgegeben.
   * </pre>
   * 
   * @param pAnzahlStellen die Laenge
   * @param pZeichen das zu wiederholende Zeichen
   * @return einen String der angegebenen Laenge mit dem uebergebenen Zeichen
   */
  private static String getNChars( int pAnzahlStellen, String pZeichen )
  {
    if ( pZeichen == null )
    {
      return "";
    }

    /*
     * Ist die Laenge negativ oder 0, wird ein Leerstring zurueckgegeben
     */
    if ( pAnzahlStellen <= 0 )
    {
      return "";
    }

    if ( pAnzahlStellen > 15000 )
    {
      pAnzahlStellen = 15000;
    }

    String ergebnis = pZeichen + pZeichen + pZeichen + pZeichen + pZeichen + pZeichen + pZeichen + pZeichen + pZeichen + pZeichen;

    /*
     * Der String "ergebnis" wird solange verdoppelt bis die Laenge groesser der Anzahl aus dem Parameter ist. 
     * Anschliessend wird ein Substring der Parameter-Laenge zurueckgegeben.
     */
    int zaehler = 1;

    while ( ( zaehler <= 50 ) && ( ergebnis.length() <= pAnzahlStellen ) )
    {
      ergebnis += ergebnis;

      zaehler++;
    }

    return ergebnis.substring( 0, pAnzahlStellen );
  }

  /*
   * 
   * ****************************************************************************************
   * Doing some commentry in German ... 
   * 
   * ANMERKUNG: Diese Kommentare stimmen fuer die Berechnung der Grenzen.
   * 
   *            Um tatsaechlich das Ergebnis zu bestimmen, muessen die Regionen per 
   *            Rekursion gefunden werden.
   *            
   *            Ansonsten wuerde man einzelne Gruppen global zusammenfuegen.
   *            
   * Part 1:
   * Jede Zelle des Grids ist als jeweils ein einzelner Bereich anzusehen.
   *  
   * Dieser Bereich grenzt an allen 4 Seiten an Nachbarzellen.
   *    - Im schlechtestem Fall, grenzt keine Pflanzenart derselben Sorte an die Zelle.
   *      Somit ist bei einer einzelnen Zelle der Wert 4 anzunehmen.
   *  
   * Es werden die Randzellen nicht speziell betrachtet.
   *  
   * Somit hat jede Zelle den Wert 4 als Initialwert.
   *    - Es geht praktish darum, diesen Wert von 4 zu reduzieren.
   *    - Eine Reduzierung erfolgt, wenn in einem benachtbartem Feld 
   *      eine gleiche Pflanzenart (=gleicher Buchstabe) gefunden wird.
   *  
   * Dieser Wert verringert sich, sobald die Zelle horizontal oder vertikal mit 
   * einer Zelle der gleichen Pflanzenart verbunden ist.
   *   
   * Ist die Zelle mit der gleichen Pflanzenart verbunden, reduziert sich dessen Wert um 1.
   * Bei der angrenzenden Zelle reduziert sich ebenfalls der Wert um 1.
   *
   * 
   * In einer Hashmap wird fuer jedes Feld der aktuelle Wert vermerkt. 
   * Initial ist das der Wert 4.
   * 
   * Es wird fuer jede Zeile geprueft, ob diese horizontal (nach rechts) oder vertikal (nach unten)
   * mit einer gleichen Pflanzenart verbunden ist. 
   * 
   * Wird eine "Verbundenheit" festgestellt, wird reduziert sich der Zellenwert beider beteiligten 
   * Zellen jeweils um 1. 
   * 
   * Das Ergebnis fuer den ersten Teil ergibt sich dann in der Aufsummierung aller Zellenwerte der Hashmap.
   * 
   * Alternativ kann das Ergebnis auch dadurch errechnet werden, in dem alle Felder des Grids mal 4 genommen werden.
   * Davon werden dann alle Reduzierungen (... welche man sich gespeichert hat) abgezogen werden.
   * 
   * -----------------------------------------------------------------------------
   * 
   * Grid als jeweils 1 Regions initialisieren, Jede Zelle hat den Wert von 4 Ecken
   * 
   * Der Zellenwert reduziert sich, wenn 2 Pflanzen sich beruehren
   * 
   * Zum Beispiel die Pflanzenart "A" - zwei Felder beruehren sich horizontal
   * 
   *  AA = Initial 4 + 4 reduziert bei beiden Pflanzenarten A den Feldwert von 4 auf 3
   *       Beim ersten A reduziert sich die rechte Verbindung. (= -1)
   *       Beim zweitem A reduziert sich die linke Verbindung. (= -1)
   *       Von Initial (4+4) 8, verbleiben (3+3) = 6
   *       Es wurden 2 "Boundaries" Eliminiert
   *       
   *  Speicherung der Region in einer hashmap:
   *    Key = Pflanzenart
   *    Value = die Felder in X und Y welche die
   *    
   *  Horizontal kann sich immer nur vom Ausgang die rechte Seite reduzieren,
   *  bei dem angrenzendem Feld kann sich nur die linke Seite reduzieren.
   *  (reduzieren = Seiten verschmelzen = Verbindung wird aufgeloest = Auf beiden Seiten eine Kante weniger = -2 Punkte (je einer auf beiden Seiten))
   *  
   *  Vertikal kann sich beim Ausgang immer nur die untere Seite reduzieren,
   *  bei dem Angrenzendem Feld kann sich nur die obere Seite Reduzieren.
   *   
   *          - In einer Hashmap wird fuer jede Pflanzenart vermerk, wie viele Reduzierungen je Pflanzenart es gibt.
   *  
   * Man bekommt die Eingabe als Grid
   * 
   *  Auessere Schleife Zeilen
   *     innere Schleife Spalten
   *        
   *        Pflanzentyp bestimmen
   *        
   *        In der Hashmap nachsehen, welcher Wert gespeichert worden ist. 
   *        Initial ist der Wert eben 4.  
   *        
   *        Horizontal: 
   *        - Ist das naechste Feld gleich der Pflanzenart des aktuellen Feldes 
   *          -Beim aktuellem Feld den Wert um 1 Reduzieren 
   *          -naechstes Feld mit Wert 4 eventuell anlegen
   *          - beim naechsten Feld den Wert um 1 reduzieren.
   *          
   *        Vertikal:
   *        - Ist das unter ein stehende Feld gleich der Pflanzenart 
   *          Beim aktuellen Feld den Wert um 1 vermindern
   *          unteres Feld eventuell mit Wert 4 anlegen
   *          beim unterem Feld den Wert um 1 vermindern
   *          
   * Der Ergebniswert kann auf 2 Arten ermittelt werden:
   *  - Jede Grid-Zelle einzelnd abfragen und den Wert aufsummieren.
   *    Die Unterscheidung nach Pflanzenarten ist hierbei egal, da es 
   *    nur um den Wert geht, mit wie vielen Ecken die Zelle eine andere Pflanzenart beruehrt.
   *  
   *  - Den Gridwert berechnen ( (grid.rows * grid.cols ) * 4 ) - grid.reduzierungen
   */

}
