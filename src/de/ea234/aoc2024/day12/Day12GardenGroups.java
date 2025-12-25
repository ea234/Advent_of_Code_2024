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
import de.ea234.util.FkStringText;

public class Day12GardenGroups
{
  /*
   * --- Day 12: Garden Groups ---
   * https://adventofcode.com/2024/day/12
   * https://www.youtube.com/watch?v=jx0Y07_LcfQ
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
   * Grid als jeweils 1 Regions initialisieren, Jede Zelle hat den Wert von 4 (Ecken?)
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
   *          
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
   * 
   * -----------------------------------------------------------------------------
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
   */


  private static final int    ROW_PLUS_1                 = 1;

  private static final int    COL_PLUS_1                 = 1;

  private static final long   DEFAULT_CELL_VALUE         = 4;

  private static int          DEBUG_PADDING_VALUE        = 35;

  private static char         DEBUG_PADDING_CHAR         = ' ';

  private static final String KEY_PRAEFIX_CELL_VALUE     = "CELL_VALUE_";

  private static final String KEY_PRAEFIX_CELL_FENCE     = "CELL_FENCE_";

  private static final String KEY_PRAEFIX_CELL_DIRECTION = "CELL_DIR_";

  private static final String KEY_CELL_FENCE_TOP         = "CFT_";

  private static final String KEY_CELL_FENCE_BOTTOM      = "CFB_";

  private static final String KEY_CELL_FENCE_LEFT        = "CFL_";

  private static final String KEY_CELL_FENCE_RIGHT       = "CFR_";

  private static final int    FENCE_1                    = 1;

  private static final int    FENCE_0                    = 0;

  private static final int    DIRECTION_NONE             = 0;

  private static final int    DIRECTION_UP               = 1;

  private static final int    DIRECTION_RIGHT            = 2;

  private static final int    DIRECTION_DOWN             = 3;

  private static final int    DIRECTION_LEFT             = 4;

  private static final int    DIRECTION_FINISHED         = 5;

  private static final int    FENCE_ERR                  = 99;

  private static final String FENCE_1_CHAR_TOP           = "-";

  private static final String FENCE_1_CHAR_BOTTOM        = "-";

  private static final String FENCE_1_CHAR_LEFT          = "|";

  private static final String FENCE_1_CHAR_RIGHT         = "|";

  //private static final String   FENCE_0_CHAR_TOP         = ".";
  //private static final String   FENCE_0_CHAR_BOTTOM      = ".";
  //private static final String   FENCE_0_CHAR_LEFT        = ":";
  //private static final String   FENCE_0_CHAR_RIGHT       = ":";

  //private static final String   FENCE_0_CHAR_TOP         = ".";
  //private static final String   FENCE_0_CHAR_BOTTOM      = ".";
  //private static final String   FENCE_0_CHAR_LEFT        = ":";
  //private static final String   FENCE_0_CHAR_RIGHT       = ":";

  private static final String FENCE_0_CHAR_TOP           = " ";

  private static final String FENCE_0_CHAR_BOTTOM        = " ";

  private static final String FENCE_0_CHAR_LEFT          = " ";

  private static final String FENCE_0_CHAR_RIGHT         = " ";

  private static final String FENCE_CHAR_EMPTY           = " ";

  private static final String KEY_PRAEFIX_PLANT_TYPE     = "PLANT_TYPE_";

  private static final long   DEFAULT_CELL_VALUE_EMPTY   = 0;

  private static final char   DEFAULT_CHAR_NOT_FOUND     = '#';

  private static final char   CHAR_EMPTY_SPACE           = '.';

  private static final char   CHAR_DEBUG_ALL             = '_';

  private static final String STR__DEBUG_SPACER          = "    ";

  public static void main( String[] args )
  {
    String test_content_1 = "AAAA,BBCD,BBCC,EEEC";
    String test_content_2 = "OOOOO,OXOXO,OOOOO,OXOXO,OOOOO";
    String test_content_3 = "RRRRIICCFF,RRRRIICCCF,VVRRRCCFFF,VVRCCCJFFF,VVVVCJJCFE,VVIVCCJJEE,VVIIICJJEE,MIIIIIJJEE,MIIISIJEEE,MMMISSJEEE";
    String test_content_4 = "AAAAAA,AAABBA,AAABBA,ABBAAA,ABBAAA,AAAAAA";
    String test_content_5 = "EEEEE,EXXXX,EEEEE,EXXXX,EEEEE";

    List< String > test_content_list_1 = Arrays.stream( test_content_1.split( "," ) ).map( String::trim ).collect( Collectors.toList() );
    List< String > test_content_list_2 = Arrays.stream( test_content_2.split( "," ) ).map( String::trim ).collect( Collectors.toList() );
    List< String > test_content_list_3 = Arrays.stream( test_content_3.split( "," ) ).map( String::trim ).collect( Collectors.toList() );
    List< String > test_content_list_4 = Arrays.stream( test_content_4.split( "," ) ).map( String::trim ).collect( Collectors.toList() );
    List< String > test_content_list_5 = Arrays.stream( test_content_5.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    //String test_content_square_1 = "..........,..........,..AAAAA...,..A...A...,..A...A...,..A...A...,..AAAAA...,..........,..........";

    String test_content_square_1 = "..........,...AA.....,..AAAAA...,..A...A...,..A...A...,..A...A...,..AAAAA...,..........,..........";

    //String test_content_complex_1 = "..........,.AAAA.....,....A.....,..AAAAA...,..A...A...,..A...A...,..A...A...,..AAAAA...,....A.....,....AAAA..,..........";
    String test_content_complex_1 = "..........,.A........,.A.AAA....,.A...A....,.AAAAAAA..,..AA..AAA.,..AAA.AA..,.AAAAAAA..,....A.....,..AAAAA...,..A.A.....,....AAAA..,..........";

    List< String > list_test_content_square_1 = Arrays.stream( test_content_square_1.split( "," ) ).map( String::trim ).collect( Collectors.toList() );
    List< String > test_content_complex_1l = Arrays.stream( test_content_complex_1.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    //String test_content_4 = "OOOOO,OXOXX,OOOXO,OOOOO";

//    String test_content_4 = "......CC..,......CCC.,.....CC...,...CCC....,....C..C..,....CC....,.....C....,..........,..........,..........";
//
//    String test_content_5 = "....II....,....II....,..........,..........,..........,..I.......,..III.....,.IIIII....,.III.I....,...I......";
//    List< String > test_content_list_4 = Arrays.stream( test_content_5.split( "," ) ).map( String::trim ).collect( Collectors.toList() );
//
//    String test_content_6 = "..........,..........,..........,..........,....X.....,..........,..........,.........X,..........,..........";
//    List< String > test_content_list_6 = Arrays.stream( test_content_6.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    //calcPart01( test_content_list_6, true );
    //calcPart01( test_content_list_1, true );

    //calcPart01( test_content_list_1, true );
    //calcPart01( test_content_list_2, true );
    //calcPart01( test_content_list_3, true );

    calcPart02( test_content_complex_1l, true );

    //calcPart01( getListProd(), false );
  }

  private static String calcPart02( List< String > pListInput, boolean pKnzDebug )
  {
    clearHashMap();

    m_hash_map_regions = new HashMap< String, List< String > >();

    Properties prop_debug_plant_types = new Properties();

    Properties prop_grid_plants = new Properties();

    String res_str = "";

    int current_row = 0;
    int current_col = 0;

    /*
     * Initializing the grid with coordinates for the key and 
     * plant type for the value.
     */

    for ( String input_str : pListInput )
    {
      for ( current_col = 0; current_col < input_str.length(); current_col++ )
      {
        prop_grid_plants.setProperty( "R" + current_row + "C" + current_col, "" + input_str.charAt( current_col ) );

        setCellFence( KEY_CELL_FENCE_TOP, current_row, current_col, FENCE_1 );
        setCellFence( KEY_CELL_FENCE_BOTTOM, current_row, current_col, FENCE_1 );
        setCellFence( KEY_CELL_FENCE_LEFT, current_row, current_col, FENCE_1 );
        setCellFence( KEY_CELL_FENCE_RIGHT, current_row, current_col, FENCE_1 );
      }

      current_row++;
    }

    current_row = 0;

    for ( String input_str : pListInput )
    {
      for ( current_col = 0; current_col < input_str.length(); current_col++ )
      {
        char current_cell_char = input_str.charAt( current_col );

        if ( current_cell_char != '.' )
        {
          prop_debug_plant_types.setProperty( KEY_PRAEFIX_PLANT_TYPE + current_cell_char, "" + current_cell_char );

          long current_char_count = getCharCountDef( current_cell_char, 0 );

          current_char_count++;

          setCharCount( current_cell_char, current_char_count );

          long current_cell_value = getCellValueDef( current_row, current_col, DEFAULT_CELL_VALUE );

          /*
           * Check next char for same plant type
           */

          int next_col = current_col + COL_PLUS_1;

          if ( ( next_col < input_str.length() ) && ( input_str.charAt( next_col ) == current_cell_char ) )
          {
            /*
             * Found two matching plant types horizontal
             * 1. remove the right fence from the current position
             * 2. remove the left fence from the next position
             */
            setCellFence( KEY_CELL_FENCE_RIGHT, current_row, current_col, FENCE_0 );
            setCellFence( KEY_CELL_FENCE_LEFT, current_row, next_col, FENCE_0 );

            current_cell_value--;

            setCellValue( current_row, current_col, current_cell_value );

            long next_cell_value = getCellValueDef( current_row, next_col, DEFAULT_CELL_VALUE );

            next_cell_value--;

            setCellValue( current_row, next_col, next_cell_value );
          }

          /*
           * Check next row same col for same plant typesetCellValue
           */

          int next_row = current_row + ROW_PLUS_1;

          if ( ( next_row < pListInput.size() ) && ( getChar( pListInput, next_row, current_col, DEFAULT_CHAR_NOT_FOUND ) == current_cell_char ) )
          {
            /*
             * Found two matching plant types vertical
             * 1. remove the bottom fence from the current position
             * 2. remove the top fence from the next position
             */
            setCellFence( KEY_CELL_FENCE_BOTTOM, current_row, current_col, FENCE_0 );
            setCellFence( KEY_CELL_FENCE_TOP, next_row, current_col, FENCE_0 );

            setCellValue( current_row, current_col, current_cell_value );

            long next_cell_value = getCellValueDef( next_row, current_col, DEFAULT_CELL_VALUE );

            next_cell_value--;

            setCellValue( next_row, current_col, next_cell_value );
          }
        }
      }

      current_row++;

      res_str += "\n";
    }

    /*
     * Collecting the regions for the plant types.
     */
    int max_col = current_col + 1;

    for ( current_row = 0; current_row < pListInput.size(); current_row++ )
    {
      for ( current_col = 0; current_col < max_col; current_col++ )
      {
        char current_char = prop_grid_plants.getProperty( "R" + current_row + "C" + current_col, "." ).charAt( 0 );

        if ( current_char != '.' )
        {
          List< String > pListRegions = new ArrayList< String >();

          long sum_count = getPlantRegion( pListRegions, prop_grid_plants, current_row, current_col, current_char, pListInput.size(), max_col );

          getPlantFenceLinear( prop_grid_plants, current_row, current_col, current_char, pListInput.size(), max_col, current_row, current_col );

          m_hash_map_regions.put( "R" + current_row + "C" + current_col, pListRegions );
        }
      }

      res_str += "\n";
    }

    for ( current_row = 0; current_row < pListInput.size(); current_row++ )
    {
      for ( current_col = 0; current_col < max_col; current_col++ )
      {
        char current_char = prop_grid_plants.getProperty( "R" + current_row + "C" + current_col, "." ).charAt( 0 );

        if ( current_char != '.' )
        {
          List< String > pListRegions = new ArrayList< String >();

//          long sum_count = getPlantFence( pListRegions, prop_grid_plants, current_row, current_col, current_char, pListInput.size(), max_col, current_row, current_col );

          getPlantFenceLinear( prop_grid_plants, current_row, current_col, current_char, pListInput.size(), max_col, current_row, current_col );

          m_hash_map_regions.put( "R" + current_row + "C" + current_col, pListRegions );
        }
      }

      res_str += "\n";
    }

    wl( "fence_outer_string " + fence_outer_string );

    if ( pKnzDebug )
    {
//      wl( getDebugMapCharPart2( pListInput, CHAR_DEBUG_ALL ) );
      wl( getDebugMapCharPart2( pListInput, 'A' ) );

      wl( "" );
      wl( "List of Regions " );
    }
//
//    long sum_plant_values_total = 0;
//    long sum_plants_count = 0;
//
//    for ( Map.Entry< String, List< String > > map_entry : m_hash_map_regions.entrySet() )
//    {
//      if ( pKnzDebug )
//      {
//        wl( "" );
//        wl( "Entry " + map_entry.getKey() );
//      }
//
//      long current_region_sum_values = 0;
//      long current_region_sum_plants = 0;
//
//      for ( String key_node : map_entry.getValue() )
//      {
//        long cell_perimeter = getLongValue( key_node, 0 );
//
//        current_region_sum_values += cell_perimeter;
//
//        current_region_sum_plants++;
//
//        if ( pKnzDebug )
//        {
//          wl( "  -   " + FkStringFeld.getFeldRechtsMin( current_region_sum_plants, 5 ) + "    " + key_node + "  " + cell_perimeter + "   " + FkStringFeld.getFeldRechtsMin( current_region_sum_values, 7 ) + "  " + FkStringFeld.getFeldRechtsMin( current_region_sum_plants * current_region_sum_values, 7 ) );
//        }
//      }
//
//      sum_plants_count += current_region_sum_plants;
//
//      sum_plant_values_total += ( current_region_sum_values * current_region_sum_plants );
//    }
//
//    wl( "sum_plants_count = " + sum_plants_count );
//    wl( "sum_plants_count = " + sum_plant_values_total + " <- Result Part 1" );
//    wl( "" );
//
//    if ( pKnzDebug )
//    {
//      String debug_plant_types = "";
//
//      wl( getDebugMapChar( pListInput, CHAR_DEBUG_ALL ) );
//
//      for ( String prop_key : prop_debug_plant_types.stringPropertyNames() )
//      {
//        if ( prop_key.startsWith( KEY_PRAEFIX_PLANT_TYPE ) )
//        {
//          char current_plant_type = prop_debug_plant_types.getProperty( prop_key, "" + DEFAULT_CHAR_NOT_FOUND ).charAt( 0 );
//
//          if ( current_plant_type != DEFAULT_CHAR_NOT_FOUND )
//          {
//            debug_plant_types += current_plant_type;
//
//            prop_debug_plant_types.setProperty( "" + current_plant_type, getDebugMapChar( pListInput, current_plant_type ) );
//          }
//        }
//      }
//
//      char[] array_chars = debug_plant_types.toCharArray();
//
//      java.util.Arrays.sort( array_chars );
//
//      String sorted_plant_types = new String( array_chars );
//
//      String debug_map_str = "";
//
//      int debug_map_nr = 1;
//
//      for ( char char_c : sorted_plant_types.toCharArray() )
//      {
//        String map_cur = prop_debug_plant_types.getProperty( "" + char_c );
//
//        if ( debug_map_nr > 1 )
//        {
//          debug_map_str = combineStrings( debug_map_str, map_cur );
//        }
//        else
//        {
//          debug_map_str = map_cur;
//        }
//
//        if ( debug_map_nr == 3 )
//        {
//          wl( debug_map_str + "\n\n" );
//
//          debug_map_str = null;
//
//          debug_map_nr = 0;
//        }
//
//        debug_map_nr++;
//      }
//
//      if ( debug_map_str != null )
//      {
//        wl( debug_map_str + "\n\n" );
//      }
//    }

    return res_str;
  }

  private static String fence_outer_string = "";

  private static long getPlantFenceLinear( Properties pGrid, long pRow, long pCol, char pTargetPlantType, int pMaxRow, int pMaxCol, int pStartRow, int pStartCol )
  {
    /*
     * The new row exceeds the max rows, so no match 
     */
    if ( pRow > pMaxRow )
    {
      return 0;
    }

    /*
     * The new col exceeds the max cols, so no match
     */
    if ( pCol > pMaxCol )
    {
      return 0;
    }

    long current_row = pRow;
    long current_col = pCol;

    long row_next = pRow;
    long col_next = pCol;

    long cell_direct_cur = DIRECTION_UP;
    long long_while_nr = 0;
    boolean knz_do_while = true;

    while ( knz_do_while )
    {
      long_while_nr++;

      long fence_top = getCellFence( KEY_CELL_FENCE_TOP, current_row, current_col );
      long fence_bottom = getCellFence( KEY_CELL_FENCE_BOTTOM, current_row, current_col );
      long fence_left = getCellFence( KEY_CELL_FENCE_LEFT, current_row, current_col );
      long fence_right = getCellFence( KEY_CELL_FENCE_RIGHT, current_row, current_col );

      boolean knz_can_move_up = fence_top == FENCE_0;
      boolean knz_can_move_down = fence_bottom == FENCE_0;
      boolean knz_can_move_left = fence_left == FENCE_0;
      boolean knz_can_move_right = fence_right == FENCE_0;

      long cell_direction = getCellDirection( current_row, current_col );

      long knz_moved = 0;

      row_next = current_row;
      col_next = current_col;

      if ( ( cell_direct_cur == DIRECTION_NONE ) && ( knz_moved == 0 ) )
      {
        cell_direction = cell_direct_cur;

        setCellDirection( current_row, current_col, cell_direction );

        if ( knz_can_move_up )
        {
          row_next--;

          knz_moved = 1;
        }
      }

      if ( ( cell_direct_cur == DIRECTION_UP ) && ( knz_moved == 0 ) )
      {
        cell_direction = cell_direct_cur;

        setCellDirection( current_row, current_col, cell_direction );

        if ( knz_can_move_up )
        {
          row_next--;

          knz_moved = 1;
        }

      }

      if ( ( cell_direct_cur == DIRECTION_RIGHT ) && ( knz_moved == 0 ) )
      {
        cell_direction = cell_direct_cur;

        setCellDirection( current_row, current_col, cell_direction );

        if ( knz_can_move_right )
        {
          col_next++;

          knz_moved = 1;
        }
      }

      if ( ( cell_direct_cur == DIRECTION_DOWN ) && ( knz_moved == 0 ) )
      {
        cell_direction = cell_direct_cur;

        setCellDirection( current_row, current_col, cell_direction );

        if ( knz_can_move_down )
        {
          row_next++;

          knz_moved = 1;
        }
      }

      if ( ( cell_direction == DIRECTION_LEFT ) && ( knz_moved == 0 ) )
      {
        cell_direction = cell_direct_cur;

        setCellDirection( current_row, current_col, cell_direction );

        if ( knz_can_move_left )
        {
          col_next--;

          knz_moved = 1;
        }

      }

      /*
       * 
       * 
       * 
       * 
       * 
       * 
       * 
       * 
       * 
       * 
       * 
       */

      if ( ( cell_direction == DIRECTION_NONE ) && ( knz_moved == 0 ) )
      {
        cell_direction = DIRECTION_UP;

        setCellDirection( current_row, current_col, cell_direction );

        if ( knz_can_move_up )
        {
          row_next--;

          knz_moved = 1;
        }
      }

      if ( ( cell_direction == DIRECTION_UP ) && ( knz_moved == 0 ) )
      {
        cell_direction = DIRECTION_RIGHT;

        setCellDirection( current_row, current_col, cell_direction );

        if ( knz_can_move_right )
        {
          col_next++;

          knz_moved = 1;
        }
      }

      if ( ( cell_direction == DIRECTION_RIGHT ) && ( knz_moved == 0 ) )
      {
        cell_direction = DIRECTION_DOWN;

        setCellDirection( current_row, current_col, cell_direction );

        if ( knz_can_move_down )
        {
          row_next++;

          knz_moved = 1;
        }
      }

      if ( ( cell_direction == DIRECTION_DOWN ) && ( knz_moved == 0 ) )
      {
        cell_direction = DIRECTION_LEFT;

        setCellDirection( current_row, current_col, cell_direction );

        if ( knz_can_move_left )
        {
          col_next--;

          knz_moved = 1;
        }
      }

      if ( ( cell_direction == DIRECTION_LEFT ) && ( knz_moved == 0 ) )
      {
        cell_direction = DIRECTION_UP;

        setCellDirection( current_row, current_col, cell_direction );

        if ( knz_can_move_up )
        {
          row_next--;

          knz_moved = 1;
        }
        else
        {
          cell_direction = DIRECTION_FINISHED;

          setCellDirection( current_row, current_col, cell_direction );
        }
      }

      fence_outer_string += cell_direction;

      current_row = row_next;
      current_col = col_next;

      cell_direct_cur = cell_direction;

      if ( long_while_nr > 255 )
      {
        knz_do_while = false;
      }
    }

    return 0l;
  }

  private static long getPlantFence( List< String > pListRegions, Properties pGrid, long pRow, long pCol, char pTargetPlantType, int pMaxRow, int pMaxCol, int pStartRow, int pStartCol )
  {
    /*
     * The new row exceeds the max rows, so no match 
     */
    if ( pRow > pMaxRow )
    {
      return 0;
    }

    /*
     * The new col exceeds the max cols, so no match
     */
    if ( pCol > pMaxCol )
    {
      return 0;
    }

    /*
     * Were we startet
     */
    if ( ( pRow == pStartRow ) && ( pCol == pStartCol ) )
    {
      return 0;
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
      return 0;
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
     *         -  -                   -            
     *        |A  A|                 |A|          
     *      -        -  -       -  -  -  -  -       -  -     -  -        
     *     |A  A  A  A  A|     |A  A  A  A  A|     |A  A  A  A  A|       
     *         -  -  -             -  -  -             -  -  -           
     *     |A|         |A|     |A|         |A|     |A|         |A|       
     *     |A|         |A|     |A|         |A|     |A|         |A|       
     *     |A|         |A|     |A|         |A|     |A|         |A|       
     *         -  -  -             -  -  -             -  -  -           
     *     |A  A  A  A  A|     |A  A  A  A  A|     |A  A  A  A  A|       
     *      -  -  -  -  -       -  -  -  -  -       -  -  -  -  -
     *   
     * 
     *     -                          
     *    |A|                         
     *           -  -  -              
     *    |A|   |A  A  A|             
     *           -  -                 
     *    |A|         |A|             
     *        -  -  -     -  -        
     *    |A  A  A  A  A  A  A|       
     *     -        -  -        -              
     *       |A  A|      |A  A  A|    
     *              -           -       
     *       |A  A  A|   |A  A|    
     *     -           -                      
     *    |A  A  A  A  A  A  A|       
     *     -  -  -     -  -  -        
     *             |A|                
     *        -  -     -  -           
     *       |A  A  A  A  A|          
     *           -     -  -           
     *       |A|   |A|                
     *        -                       
     *                 -  -  -        
     *             |A  A  A  A|       
     *              -  -  -  -        
     *   
     * 
     * Das Startfeld hat immer eine linke und obere Begrenzung
     * Es geht darum die aeussere Begrenzung zu ermitteln.
     * 
     * Es wird nur anhand der Begrenzungen geprüft.
     * 
     * Die Pruefung auf gleiche Pflanzenart (=Buchstabe) wurde bei der 
     * Ermittlung der Grenzen gemacht und kann hier entfallen.
     * 
     * 
     * Es muss immer eine Bewegung nach "rechts" sein (von einem virtuellem Betrachter)
     * Ist die Bewegung nach rechts nicht möglich, ....
     * 
     * Die Bewegung kann nur zu einem Feld hinfuehren welches mindestens eine Begrenzung hat.
     * 
     * Start-position 
     * -nach oben 
     * -nach rechts
     * -nach unten
     * -nach links
     * 
     * 
     * Eventuell ist die Rekursion nicht zielführend.
     * Mit der Rekursion würde für jedes Feld sichergestellt werden, dass alle Richtungen 
     * vollzogen werden. Dass muss nicht immer stimmen.
     * 
     * Eventuell wäre es besser einen start punkt zu haben und sich durch das feld 
     * zu hangeln. 
     * 
     * Für jedes Feld wird festgehalten, welche die letzte richtung war (hashmap)
     * Kommt der Algorithmuss wieder beim Feld vorbei, kann diese Richtung wieder 
     * gelesen werden und um 90 Grad gedreht werden. 
     * 
     * Kann keine neue richtung mehr ermittelt werden, ist der algorithmus beendet.
     * (Es sollte dann wieder der Startpunkt sein).
     * 
     * 
     * 
     * 
     * Äussere Begrenzung
     * - nach Rechts  - wenn obere begrenzung 1, wenn rechte begrenzung 0
     * - nach Oben    - wenn obere begrenzung 0, wenn linke begrenzung 0 
     * - nach unten   - wenn untere begrenzung 0, wenn rechte begrenzung 1
     * - nach links   - wenn linke begrenzung 0 ist.
     * 
     * 
     * - nach Rechts  - Kriterium sind die obere und rechte Begrenzungen
     * 
     *                - Die obere Begrenzung stellt die Beeendigung der 
     *                  Bewegung nach rechts fest.
     *                  
     *                  Fehlt die obere Begrenzung kann der Algorithmus 
     *                  eine Zeile nach oben (zeile -1) gehen. 
     *                  
     *                  Die rechte Begrenzung ist in diesem Fall unerheblich.
     *                  Die Bewegung nach oben hat vorrang vor der Bewegung nach rechts.
     *                  
     *                - Die rechte Begrenzung stellt den Weg für eine Bewegung nach rechts dar.
     *                 
     *                  Fehlt die rechte Begrenzung und die Begrenzung nach oben ist vorhanden, 
     *                  kann der Algorithmus zum nächsten horizontalen (spalte + 1) gehen.
     *                  
     *                  Die Begrenzung ist in diesem Fall 0.
     *                  
     *                - Eine Bewegung nach rechts verlängert den oberen Zaun.
     *                  
     * 
     * - nach oben    - Kriterium sind die obere und linke Begrenzung
     * 
     *                - Die 
     * 
     * 
     */

    /*
     * The function result is set to 1, to represent a valid coordinate
     */
    long sum_conections = 1;

//    private static final String KEY_CELL_FENCE_TOP       = "CFT_";
//
//    private static final String KEY_CELL_FENCE_BOTTOM    = "CFB_";
//
//    private static final String KEY_CELL_FENCE_LEFT      = "CFL_";
//
//    private static final String KEY_CELL_FENCE_RIGHT     = "CFR_";

    long fence_top = getCellFence( KEY_CELL_FENCE_TOP, pRow, pCol );
    long fence_bottom = getCellFence( KEY_CELL_FENCE_BOTTOM, pRow, pCol );
    long fence_left = getCellFence( KEY_CELL_FENCE_LEFT, pRow, pCol );
    long fence_right = getCellFence( KEY_CELL_FENCE_RIGHT, pRow, pCol );

    /*
     * Move to the right
     * - only when the top fence is present
     * - movement to the right, enlarges the top fence
     * 
     */
    if ( ( fence_right == FENCE_0 ) && ( fence_top == FENCE_1 ) )
    {
      /*
       * count the top fence from THIS POSITION to the outer fence string
       */
      fence_outer_string += "T";

      /*
       * Move to the next horizontal Position
       */
      sum_conections += getPlantFence( pListRegions, pGrid, pRow, pCol + 1, grid_plant_type, pMaxRow, pMaxCol, pStartRow, pStartCol );
    }
    else
    {
      /*
       * no more movement to the right
       * the outer fence changes direction
       * end the current line with a comma
       */
      fence_outer_string += ",";
    }

    /*
     * Move up
     * - only when the top fence is absence
     * - only when the left fence is present (because, if was absence, we would go left)
     */

    if ( ( fence_top == FENCE_0 ) && ( fence_left == FENCE_1 ) )
    {
      /*
       * count the Left fence from THIS POSITION to the outer fence string
       */
      fence_outer_string += "L";

      /*
       * Move to the next horizontal Position
       */
      sum_conections += getPlantFence( pListRegions, pGrid, pRow - 1, pCol, grid_plant_type, pMaxRow, pMaxCol, pStartRow, pStartCol );
    }
    else
    {
      /*
       * no more movement to the top
       * the outer fence changes direction
       * end the current line with a comma
       */
      fence_outer_string += ",";
    }

    /*
     * Move left
     * - only when the top fence is absence
     * - only when the left fence is present (because, if was absence, we would go left)
     *   
     *    
     *        -                   
     *       |A|                  
     *              -  -  -       
     *       |A|   |A  A  A|      
     *              -  -          
     *       |A|         |A|      
     *           -  -  -     -  - 
     *       |A  A  A  A  A  A  A|
     * 
     * 
     */

    /*
     * Check fence left from the cell above.
     * No boundary checks neccessary because the function "getCellFence" is
     * getting the value from a hash map.
     * 
     * If the above field is not present in the hash-map the function will 
     * return the value from FENCE_ERR.
     * 
     * 
     */
    long fence_left_above = getCellFence( KEY_CELL_FENCE_LEFT, pRow - 1, pCol );

//    if ( ( fence_top == FENCE_0 ) && ( fence_left_above == FENCE_1 ) )
    if ( ( fence_top == FENCE_0 ) )
    {
      /*
       * count the Left fence from THIS POSITION to the outer fence string
       */
      fence_outer_string += "L";

      /*
       * Move to the next horizontal Position
       */
      sum_conections += getPlantFence( pListRegions, pGrid, pRow - 1, pCol, grid_plant_type, pMaxRow, pMaxCol, pStartRow, pStartCol );
    }
    else
    {
      /*
       * no more movement to the right,
       * so the outer fence changes direction
       * end the current line with a comma
       */
      fence_outer_string += ",";
    }

//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    /*
//     * Find more plants beneath this coordinates
//     */
//    sum_conections += getPlantFence( pListRegions, pGrid, pRow + 1, pCol, grid_plant_type, pMaxRow, pMaxCol, pStartRow, pStartCol );
//
//    /*
//     * Go to the left
//     */
//    sum_conections += getPlantFence( pListRegions, pGrid, pRow, pCol - 1, grid_plant_type, pMaxRow, pMaxCol, pStartRow, pStartCol );
//
//    /*
//     * Go up
//     */
//    sum_conections += getPlantFence( pListRegions, pGrid, pRow - 1, pCol, grid_plant_type, pMaxRow, pMaxCol, pStartRow, pStartCol );

    /*
     * Return all connections
     * (... the return value is actually never used)
     */
    return sum_conections;
  }

  private static HashMap< String, List< String > > m_hash_map_regions = new HashMap< String, List< String > >();

  private static String calcPart01( List< String > pListInput, boolean pKnzDebug )
  {
    clearHashMap();

    m_hash_map_regions = new HashMap< String, List< String > >();

    Properties prop_debug_plant_types = new Properties();

    Properties prop_grid_plants = new Properties();

    String res_str = "";

    int current_row = 0;
    int current_col = 0;

    /*
     * Initializing the grid with coordinates for the key and 
     * plant type for the value.
     */
    for ( String input_str : pListInput )
    {
      for ( current_col = 0; current_col < input_str.length(); current_col++ )
      {
        prop_grid_plants.setProperty( "R" + current_row + "C" + current_col, "" + input_str.charAt( current_col ) );

        char current_cell_char = input_str.charAt( current_col );

        if ( current_cell_char != '.' )
        {
          prop_debug_plant_types.setProperty( KEY_PRAEFIX_PLANT_TYPE + current_cell_char, "" + current_cell_char );

          long current_char_count = getCharCountDef( current_cell_char, 0 );

          current_char_count++;

          setCharCount( current_cell_char, current_char_count );

          long current_cell_value = getCellValueDef( current_row, current_col, DEFAULT_CELL_VALUE );

          /*
           * Check next char for same plant type
           */

          int next_col = current_col + COL_PLUS_1;

          if ( ( next_col < input_str.length() ) && ( input_str.charAt( next_col ) == current_cell_char ) )
          {
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

            long next_cell_value = getCellValueDef( current_row, next_col, DEFAULT_CELL_VALUE );

            next_cell_value--;

            setCellValue( current_row, next_col, next_cell_value );
          }

          /*
           * Check next row same col for same plant typesetCellValue
           */

          int next_row = current_row + ROW_PLUS_1;

          if ( ( next_row < pListInput.size() ) && ( getChar( pListInput, next_row, current_col, DEFAULT_CHAR_NOT_FOUND ) == current_cell_char ) )
          {
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

      res_str += "\n";
    }

    /*
     * Collecting the regions for the plant types.
     */
    int max_col = current_col + 1;

    for ( current_row = 0; current_row < pListInput.size(); current_row++ )
    {
      for ( current_col = 0; current_col < max_col; current_col++ )
      {
        char current_char = prop_grid_plants.getProperty( "R" + current_row + "C" + current_col, "." ).charAt( 0 );

        if ( current_char != '.' )
        {
          List< String > pListRegions = new ArrayList< String >();

          long sum_count = getPlantRegion( pListRegions, prop_grid_plants, current_row, current_col, current_char, pListInput.size(), max_col );

          m_hash_map_regions.put( "R" + current_row + "C" + current_col, pListRegions );
        }
      }

      res_str += "\n";
    }

    if ( pKnzDebug )
    {
      wl( getDebugMapChar( pListInput, CHAR_DEBUG_ALL ) );

      wl( "" );
      wl( "List of Regions " );
    }

    long sum_plant_values_total = 0;
    long sum_plants_count = 0;

    for ( Map.Entry< String, List< String > > map_entry : m_hash_map_regions.entrySet() )
    {
      if ( pKnzDebug )
      {
        wl( "" );
        wl( "Entry " + map_entry.getKey() );
      }

      long current_region_sum_values = 0;
      long current_region_sum_plants = 0;

      for ( String key_node : map_entry.getValue() )
      {
        long cell_perimeter = getLongValue( key_node, 0 );

        current_region_sum_values += cell_perimeter;

        current_region_sum_plants++;

        if ( pKnzDebug )
        {
          wl( "  -   " + FkStringFeld.getFeldRechtsMin( current_region_sum_plants, 5 ) + "    " + key_node + "  " + cell_perimeter + "   " + FkStringFeld.getFeldRechtsMin( current_region_sum_values, 7 ) + "  " + FkStringFeld.getFeldRechtsMin( current_region_sum_plants * current_region_sum_values, 7 ) );
        }
      }

      sum_plants_count += current_region_sum_plants;

      sum_plant_values_total += ( current_region_sum_values * current_region_sum_plants );
    }

    wl( "sum_plants_count = " + sum_plants_count );
    wl( "sum_plants_count = " + sum_plant_values_total + " <- Result Part 1" );
    wl( "" );

    if ( pKnzDebug )
    {
      String debug_plant_types = "";

      wl( getDebugMapChar( pListInput, CHAR_DEBUG_ALL ) );

      for ( String prop_key : prop_debug_plant_types.stringPropertyNames() )
      {
        if ( prop_key.startsWith( KEY_PRAEFIX_PLANT_TYPE ) )
        {
          char current_plant_type = prop_debug_plant_types.getProperty( prop_key, "" + DEFAULT_CHAR_NOT_FOUND ).charAt( 0 );

          if ( current_plant_type != DEFAULT_CHAR_NOT_FOUND )
          {
            debug_plant_types += current_plant_type;

            prop_debug_plant_types.setProperty( "" + current_plant_type, getDebugMapChar( pListInput, current_plant_type ) );
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

        if ( debug_map_nr == 3 )
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
    }

    return res_str;
  }

  private static String combineStrings( String pString1, String pString2 )
  {
    String str_spacer = "    ";

    String[] lines1 = pString1 != null ? pString1.split( "\r?\n" ) : new String[ 0 ];

    String[] lines2 = pString2 != null ? pString2.split( "\r?\n" ) : new String[ 0 ];

    int max_lines = Math.max( lines1.length, lines2.length );

    StringBuilder string_builder = new StringBuilder();

    for ( int line_index = 0; line_index < max_lines; line_index++ )
    {
      String str_a = line_index < lines1.length ? lines1[ line_index ] : "";
      String str_b = line_index < lines2.length ? lines2[ line_index ] : "";

      string_builder.append( str_a ).append( str_spacer ).append( str_b );

      if ( line_index < max_lines - 1 )
      {
        string_builder.append( "\n" );
      }
    }

    return string_builder.toString();
  }

  private static long getPlantRegion( List< String > pListRegions, Properties pGrid, long pRow, long pCol, char pTargetPlantType, int pMaxRow, int pMaxCol )
  {
    /*
     * The new row exceeds the max rows, so no match 
     */
    if ( pRow > pMaxRow )
    {
      return 0;
    }

    /*
     * The new col exceeds the max cols, so no match
     */
    if ( pCol > pMaxCol )
    {
      return 0;
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
      return 0;
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
     * The function result is set to 1, to represent a valid coordinate
     */
    long sum_conections = 1;

    /*
     * Find more plants of the same type to the right
     */
    sum_conections += getPlantRegion( pListRegions, pGrid, pRow, pCol + 1, grid_plant_type, pMaxRow, pMaxCol );

    /*
     * Find more plants beneath this coordinates
     */
    sum_conections += getPlantRegion( pListRegions, pGrid, pRow + 1, pCol, grid_plant_type, pMaxRow, pMaxCol );

    /*
     * Go to the left
     */
    sum_conections += getPlantRegion( pListRegions, pGrid, pRow, pCol - 1, grid_plant_type, pMaxRow, pMaxCol );

    /*
     * Go up
     */
    sum_conections += getPlantRegion( pListRegions, pGrid, pRow - 1, pCol, grid_plant_type, pMaxRow, pMaxCol );

    /*
     * Return all connections
     * (... the return value is actually never used)
     */
    return sum_conections;
  }

  private static String getDebugMapCharPart2( List< String > pListInput, char pChar )
  {
    String res_str = "";

    String str_map_empty_cell_row = FENCE_CHAR_EMPTY + FENCE_CHAR_EMPTY + FENCE_CHAR_EMPTY;

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

      for ( int current_col = 0; current_col < input_str.length(); current_col++ )
      {
        if ( ( input_str.charAt( current_col ) == pChar ) || ( pChar == CHAR_DEBUG_ALL ) )
        {
          sum_char_count++;
          sum_row_count++;

          str_above += FENCE_CHAR_EMPTY + getCellFenceChar( KEY_CELL_FENCE_TOP, current_row, current_col ) + FENCE_CHAR_EMPTY;

          str_middle += getCellFenceChar( KEY_CELL_FENCE_LEFT, current_row, current_col );
          str_middle += input_str.charAt( current_col );
          str_middle += getCellFenceChar( KEY_CELL_FENCE_RIGHT, current_row, current_col );

          str_bottom += FENCE_CHAR_EMPTY + getCellFenceChar( KEY_CELL_FENCE_BOTTOM, current_row, current_col ) + FENCE_CHAR_EMPTY;

          long current_cell_value = 0l; //getCellValueDef( current_row, current_col, FENCE_CHAR_EMPTY );

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

      res_str += padRight( str_above + " " + FkStringFeld.getFeldRechtsMin( sum_row_count, 3 ) + STR__DEBUG_SPACER + str_map_empty_cell_row + " " + FkStringFeld.getFeldRechtsMin( sum_row_val, 3 ), DEBUG_PADDING_VALUE, DEBUG_PADDING_CHAR ) + "\n";
      res_str += padRight( str_middle + " " + FkStringFeld.getFeldRechtsMin( sum_row_count, 3 ) + STR__DEBUG_SPACER + str_map_values + " " + FkStringFeld.getFeldRechtsMin( sum_row_val, 3 ), DEBUG_PADDING_VALUE, DEBUG_PADDING_CHAR ) + "\n";
      res_str += padRight( str_bottom + " " + FkStringFeld.getFeldRechtsMin( sum_row_count, 3 ) + STR__DEBUG_SPACER + str_map_empty_cell_row + " " + FkStringFeld.getFeldRechtsMin( sum_row_val, 3 ), DEBUG_PADDING_VALUE, DEBUG_PADDING_CHAR ) + "\n";
    }

    String str_map_char = " Char Count " + sum_char_count + " ";
    String str_map_values = " Sum Values " + sum_cell_values + " ";

    res_str += padRight( str_map_char + "  " + str_map_values, DEBUG_PADDING_VALUE, DEBUG_PADDING_CHAR ) + "\n";

    return res_str;
  }

  private static String getDebugMapChar( List< String > pListInput, char pChar )
  {
    String res_str = "";

    int current_row = 0;

    long sum_cell_values = 0;
    long sum_char_count = 0;

    for ( String input_str : pListInput )
    {
      long sum_row_val = 0;
      long sum_row_count = 0;

      String str_map_char = "";
      String str_map_values = "";

      for ( int current_col = 0; current_col < input_str.length(); current_col++ )
      {
        if ( ( input_str.charAt( current_col ) == pChar ) || ( pChar == CHAR_DEBUG_ALL ) )
        {
          str_map_char += input_str.charAt( current_col );

          sum_char_count++;
          sum_row_count++;

          long current_cell_value = getCellValueDef( current_row, current_col, DEFAULT_CELL_VALUE_EMPTY );

          sum_row_val += (long) current_cell_value;
          sum_cell_values += (long) current_cell_value;

          str_map_values += current_cell_value;
        }
        else
        {
          str_map_values += CHAR_EMPTY_SPACE;
          str_map_char += CHAR_EMPTY_SPACE;
        }
      }

      current_row++;

      res_str += padRight( str_map_char + " " + FkStringFeld.getFeldRechtsMin( sum_row_count, 3 ) + STR__DEBUG_SPACER + str_map_values + " " + FkStringFeld.getFeldRechtsMin( sum_row_val, 3 ), DEBUG_PADDING_VALUE, DEBUG_PADDING_CHAR ) + "\n";
    }

    String str_map_char = " Char Count " + sum_char_count + " ";
    String str_map_values = " Sum Values " + sum_cell_values + " ";

    res_str += padRight( str_map_char + "  " + str_map_values, DEBUG_PADDING_VALUE, DEBUG_PADDING_CHAR ) + "\n";

    return res_str;
  }

  private static String padRight( String pString, int pLength, char pPadChar )
  {
    if ( pString == null ) pString = "";

    int padLength = Math.max( 0, pLength - pString.length() );

    if ( padLength <= 0 ) return pString;

    StringBuilder sb = new StringBuilder( pLength );

    sb.append( pString );

    for ( int i = 0; i < padLength; i++ )
      sb.append( pPadChar );

    return sb.toString();
  }

  private static char getChar( List< String > pList, int pCurrentRow, int pCurrentCol, char pDefChar )
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

    return pDefChar;
  }

  private static HashMap< String, Long > m_hash_map_cell_values = null;

  private static HashMap< String, Long > getHashMapCellValues()
  {
    if ( m_hash_map_cell_values == null )
    {
      m_hash_map_cell_values = new HashMap< String, Long >();
    }

    return m_hash_map_cell_values;
  }

  private static void clearHashMap()
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

  private static long setCellDirection( long pRow, long pCol, long pDirection )
  {
    getHashMapCellValues().put( KEY_PRAEFIX_CELL_DIRECTION + "R" + pRow + "C" + pCol, Long.valueOf( pDirection ) );

    return pDirection;
  }

  private static long getCellDirection( long pRow, long pDirection )
  {
    Long long_value = getHashMapCellValues().get( KEY_PRAEFIX_CELL_DIRECTION + "R" + pRow + "C" + pDirection );

    if ( long_value != null )
    {
      return long_value.longValue();
    }

    return DIRECTION_NONE;
  }

  private static String getCellFenceChar( String pFenceType, int pRow, int pCol )
  {
    Long long_value = getHashMapCellValues().get( pFenceType + "R" + pRow + "C" + pCol );

    if ( long_value != null )
    {
      long l_val = long_value.longValue();

      if ( pFenceType.equals( KEY_CELL_FENCE_TOP ) )
      {
        return ( l_val == FENCE_1 ? FENCE_1_CHAR_TOP : FENCE_0_CHAR_TOP );
      }

      if ( pFenceType.equals( KEY_CELL_FENCE_BOTTOM ) )
      {
        return ( l_val == FENCE_1 ? FENCE_1_CHAR_BOTTOM : FENCE_0_CHAR_BOTTOM );
      }

      if ( pFenceType.equals( KEY_CELL_FENCE_LEFT ) )
      {
        return ( l_val == FENCE_1 ? FENCE_1_CHAR_LEFT : FENCE_0_CHAR_LEFT );
      }

      if ( pFenceType.equals( KEY_CELL_FENCE_RIGHT ) )
      {
        return ( l_val == FENCE_1 ? FENCE_1_CHAR_RIGHT : FENCE_0_CHAR_RIGHT );
      }
    }

    return FENCE_CHAR_EMPTY;
  }

  private static long setCellValue( int pRow, int pCol, long pValue )
  {
    getHashMapCellValues().put( "R" + pRow + "C" + pCol, Long.valueOf( pValue ) );

    return pValue;
  }

  private static long getCharCountDef( char pChar, long pDefaultValue )
  {
    Long long_value = getHashMapCellValues().get( "char_" + pChar );

    if ( long_value != null )
    {
      return long_value.longValue();
    }

    return setCharCount( pChar, pDefaultValue );
  }

  private static long setCharCount( char pChar, long pValue )
  {
    getHashMapCellValues().put( "char_" + pChar, Long.valueOf( pValue ) );

    return pValue;
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

  private static void wl( String pString )
  {
    System.out.println( pString );
  }
}
