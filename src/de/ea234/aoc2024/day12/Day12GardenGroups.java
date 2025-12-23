package de.ea234.aoc2024.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import de.ea234.util.FkStringFeld;

public class Day12GardenGroups
{
  /*
   * --- Day 12: Garden Groups ---
   * https://adventofcode.com/2024/day/12
   *  
   * 
   * -------------------------------------------------------------------------------------
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
   * In einer Hashmap wird für jedes Feld der aktuelle Wert vermerkt. 
   * Initial ist das der Wert 4.
   * 
   * Es wird für jede Zeile geprüft, ob diese horizontal (nach rechts) oder vertikal (nach unten)
   * mit einer gleichen Pflanzenart verbunden ist. 
   * 
   * Wird eine "Verbundenheit" festgestellt, wird reduziert sich der Zellenwert beider beteiligten 
   * Zellen jeweils um 1. 
   * 
   * Das Ergebnis für den ersten Teil ergibt sich dann in der Aufsummierung aller Zellenwerte der Hashmap.
   * 
   * Alternativ kann das Ergebnis auch dadurch errechnet werden, in dem alle Felder des Grids mal 4 genommen werden.
   * Davon werden dann alle Reduzierungen (... welche man sich gespeichert hat) abgezogen werden.
   * 
   * -------------------------------------------------------------------------------------
   * 
   * 
   * Grid als jeweils 1 Regions initialisieren, Jede Zelle hat den Wert von 4 (Ecken?)
   * 
   * Der Zellenwert reduziert sich, wenn 2 Pflanzen sich berühren
   * 
   * Zum Beispiel die Pflanzenart "A" - zwei Felder berühren sich horizontal
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
   *  (reduzieren = Seiten verschmelzen = Verbindung wird aufgelöst = Auf beiden Seiten eine Kante weniger = -2 Punkte (je einer auf beiden Seiten))
   *  
   *  Vertikal kann sich beim Ausgang immer nur die untere Seite reduzieren,
   *  bei dem Angrenzendem Feld kann sich nur die obere Seite Reduzieren.
   *   
   *          - In einer Hashmap wird für jede Pflanzenart vermerk, wie viele Reduzierungen je Pflanzenart es gibt.
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
   *        - Ist das nächste Feld gleich der Pflanzenart des aktuellen Feldes 
   *          -Beim aktuellem Feld den Wert um 1 Reduzieren 
   *          -nächstes Feld mit Wert 4 eventuell anlegen
   *          - beim nächsten Feld den Wert um 1 reduzieren.
   *          
   *          
   *          
   *        Vertikal:
   *        - Ist das unter ein stehende Feld gleich der Pflanzenart 
   *          Beim aktuellen Feld den Wert um 1 vermindern
   *          unteres Feld eventuell mit Wert 4 anlegen
   *          beim unterem Feld den Wert um 1 vermindern
   *          
   *  Es wird nur einmal durchs grid gegangen
   *  
   * Der Ergebniswert kann auf 2 Arten ermittelt werden:
   *  - Jede Grid-Zelle einzelnd abfragen und den Wert aufsummieren.
   *    Die Unterscheidung nach Pflanzenarten ist hierbei egal, da es 
   *    nur um den Wert geht, mit wie vielen Ecken die Zelle eine andere Pflanzenart berührt.
   *  
   *  - Den Gridwert berechnen ( (grid.rows * grid.cols ) * 4 ) - grid.reduzierungen
   *          
   *  
   *  
OOOOO
OXOXO
OOOOO
OXOXO
OOOOO
grid_width  5
grid_height 5
grid_cells  ( width 5 * height 5 ) 25
grid_total  ( cells 25 * cost_val 4 ) 100

Result Part 1: 45



grid_total_start 100
grid_total_end   45
grid_total_diff  55



22122
22122
22122
22122
22122

  'X' Count     4 * Perimeter      16 =      64
  'O' Count    21 * Perimeter      36 =     756
                                      =     820


.....    .....
.X.X.    .4.4.
.....    .....
.X.X.    .4.4.
.....    .....
 4      16 

12345 
OOOOO 1   22122
O.O.O 2   2.2.2
OOOOO 3   12021
O.O.O 4   2.2.2
OOOOO 5   22122
 21      36 

  
  
   *  
   * In the second example, 
   * region O plants has price 21 * 36 = 756, 
   * region X plants has price  1 *  4 =   4
   * 
   * for a total price of 772 (756 + 4 + 4 + 4 + 4).
   * 
   * 4 X = 4X mal 4 Ecken = 16 Punkte
   * 



RRRRIICCFF
RRRRIICCCF
VVRRRCCFFF
VVRCCCJFFF
VVVVCJJCFE
VVIVCCJJEE
VVIIICJJEE
MIIIIIJJEE
MIIISIJEEE
MMMISSJEEE
grid_width  10
grid_height 10
grid_cells  ( width 10 * height 10 ) 100
grid_total  ( cells 100 * cost_val 4 ) 400

Result Part 1: 176



grid_total_start 400
grid_total_end   176
grid_total_diff  224



2112222232
2101221132
2211322211
1133123202
1022231433
1133221221
2211231111
3200121211
2211332201
2233233212


  'R' Count    12 * Perimeter      18 =     216  A region of R plants with price 12 * 18 = 216. 
  'F' Count    10 * Perimeter      18 =     180  A region of F plants with price 10 * 18 = 180.
  'V' Count    13 * Perimeter      20 =     260  A region of V plants with price 13 * 20 = 260.
  'J' Count    11 * Perimeter      20 =     220  A region of J plants with price 11 * 20 = 220.
  'E' Count    13 * Perimeter      18 =     234  A region of E plants with price 13 * 18 = 234.
  'S' Count     3 * Perimeter       8 =      24  A region of S plants with price  3 *  8 = 24.
  'M' Count     5 * Perimeter      12 =      60  A region of M plants with price  5 * 12 = 60.
  ---------------------------------------------------------------------------------------------
  'C' Count    15 * Perimeter      32 =     480  A region of C plants with price 14 * 28 = 392.
  'I' Count    18 * Perimeter      30 =     540  A region of I plants with price  4 *  8 = 32.
                                     
......CC..    ......22..
......CCC.    ......113.
.....CC...    .....22...
...CCC....    ...312....
....C..C..    ....2..4..
....CC....    ....22....
.....C....    .....3....
..........    ..........
..........    ..........
..........    ..........
 15      32 


....II....    ....22....
....II....    ....22....
..........    ..........
..........    ..........
..........    ..........
..I.......    ..3.......
..III.....    ..112.....
.IIIII....    .20012....
.III.I....    .211.3....
...I......    ...3......
 18      30 





RRRR......    2112......
RRRR......    2101......
..RRR.....    ..113.....
..R.......    ..3.......
..........    ..........
..........    ..........
..........    ..........
..........    ..........
..........    ..........
..........    ..........
 12      18 

..........    ..........
..........    ..........
..........    ..........
..........    ..........
..........    ..........
..........    ..........
..........    ..........
..........    ..........
....S.....    ....3.....
....SS....    ....23....
 3      8 

..........    ..........
..........    ..........
..........    ..........
..........    ..........
.........E    .........3
........EE    ........21
........EE    ........11
........EE    ........11
.......EEE    .......201
.......EEE    .......212
 13      18 

........FF    ........32
.........F    .........2
.......FFF    .......211
.......FFF    .......202
........F.    ........3.
..........    ..........
..........    ..........
..........    ..........
..........    ..........
..........    ..........
 10      18 

..........    ..........
..........    ..........
VV........    22........
VV........    11........
VVVV......    1022......
VV.V......    11.3......
VV........    22........
..........    ..........
..........    ..........
..........    ..........
 13      20 


..........    ..........
..........    ..........
..........    ..........
......J...    ......3...
.....JJ...    .....31...
......JJ..    ......12..
......JJ..    ......11..
......JJ..    ......12..
......J...    ......2...
......J...    ......3...
 11      20 

..........    ..........
..........    ..........
..........    ..........
..........    ..........
..........    ..........
..........    ..........
..........    ..........
M.........    3.........
M.........    2.........
MMM.......    223.......
 5      12 
 
   *  
   *  
   */

  private static final int    ROW_PLUS_1             = 1;

  private static final int    COL_PLUS_1             = 1;

  private static final long   DEFAULT_CELL_VALUE     = 4;

  private static final char   DEFAULT_CHAR_NOT_FOUND = '#';

  private static final char   CHAR_EMPTY_SPACE       = '.';

  private static final String STR__DEBUG_SPACER      = "    ";

  public static void main( String[] args )
  {
    String test_content_1 = "AAAA,BBCD,BBCC,EEEC";
    String test_content_2 = "OOOOO,OXOXO,OOOOO,OXOXO,OOOOO";
    String test_content_3 = "RRRRIICCFF,RRRRIICCCF,VVRRRCCFFF,VVRCCCJFFF,VVVVCJJCFE,VVIVCCJJEE,VVIIICJJEE,MIIIIIJJEE,MIIISIJEEE,MMMISSJEEE";

    List< String > test_content_list_1 = Arrays.stream( test_content_1.split( "," ) ).map( String::trim ).collect( Collectors.toList() );
    List< String > test_content_list_2 = Arrays.stream( test_content_2.split( "," ) ).map( String::trim ).collect( Collectors.toList() );
    List< String > test_content_list_3 = Arrays.stream( test_content_3.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    String test_content_4 = "OOOOO,OXOXX,OOOXO,OOOOO";

    List< String > test_content_list_4 = Arrays.stream( test_content_4.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    //calcInputPart1( test_content_list_4, true );
    //calcInputPart1( test_content_list_1, true );
    //calcInputPart1( test_content_list_2, true );
    calcInputPart1( test_content_list_3, true );
  }

  private static void calcInputPart1( List< String > pListInput, boolean pKnzDebug )
  {
    clearHashMap();

    if ( pKnzDebug )
    {
      wl( "" );
      wl( "---------------------------------------------------------" );
      wl( "" );
    }

    long grid_width = pListInput.get( 0 ).length();
    long grid_height = pListInput.size();
    long grid_cells = grid_height * grid_width;
    long cost_val = 4;

    long result_value_sum = 0;

    for ( String input_str : pListInput )
    {
      if ( pKnzDebug )
      {
        wl( input_str );
      }
    }

    Properties propc = new Properties();

    int current_row = 0;

    for ( String input_str : pListInput )
    {
      for ( int current_col = 0; current_col < input_str.length(); current_col++ )
      {
        char current_cell_char = input_str.charAt( current_col );

        propc.setProperty( "" + current_cell_char, "1" );

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
           * Found to matching plant types
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
           * Found to matching plant types
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

      current_row++;
    }

    result_value_sum = getMapSumValues( pListInput );

    wl( "grid_width  " + grid_width + "" );
    wl( "grid_height " + grid_height + "" );
    wl( "grid_cells  ( width " + grid_width + " * height " + grid_height + " ) " + grid_cells + "" );
    wl( "grid_total  ( cells " + grid_cells + " * cost_val " + cost_val + " ) " + ( grid_cells * cost_val ) + "" );
    wl( "" );
    wl( "Result Part 1: " + result_value_sum );
    wl( "" );
    wl( "" );
    wl( "" );
    wl( "grid_total_start " + ( grid_cells * cost_val ) + "" );
    wl( "grid_total_end   " + result_value_sum + "" );
    wl( "grid_total_diff  " + ( ( grid_cells * cost_val ) - result_value_sum ) + "" );
    wl( "" );
    wl( "" );
    wl( "" );

    wl( getDebugMapValues( pListInput ) );

    long sum_total = 0;

    for ( String prop_key : propc.stringPropertyNames() )
    {
      if ( prop_key.length() == 1 )
      {
        propc.setProperty( prop_key, getDebugMapChar( pListInput, prop_key.charAt( 0 ) ) );

        long sum_char_count = getLongValue( "RES_" + prop_key.charAt( 0 ) + "_SUM_CHAR_COUNT", 0 );
        long sum_cell_values = getLongValue( "RES_" + prop_key.charAt( 0 ) + "_SUM_MAP_VALUES", 0 );
        long sum_char_cost = getLongValue( "RES_" + prop_key.charAt( 0 ) + "_COST", 0 );

        sum_total += sum_char_cost;

        wl( "  '" + prop_key.charAt( 0 ) + "' Count " + FkStringFeld.getFeldRechtsMin( sum_char_count, 5 ) + " * Perimeter " + FkStringFeld.getFeldRechtsMin( sum_cell_values, 7 ) + " = " + FkStringFeld.getFeldRechtsMin( sum_char_cost, 7 ) );
      }
    }

    wl( "           " + FkStringFeld.getFeldRechtsMin( "", 5 ) + "             " + FkStringFeld.getFeldRechtsMin( "", 7 ) + " = " + FkStringFeld.getFeldRechtsMin( sum_total, 7 ) );
    wl( "" );

    propc.stringPropertyNames().forEach( prop_key -> {

      if ( prop_key.length() == 1 )
      {
        wl( propc.getProperty( prop_key ) );
      }
    } );
  }

  private static long getMapSumValues( List< String > pListInput )
  {
    long sum_cell_values = 0;

    int current_row = 0;

    for ( String input_str : pListInput )
    {
      for ( int current_col = 0; current_col < input_str.length(); current_col++ )
      {
        sum_cell_values += (long) getCellValueDef( current_row, current_col, DEFAULT_CELL_VALUE );
      }

      current_row++;
    }

    return sum_cell_values;
  }

  private static String getDebugMapValues( List< String > pListInput )
  {
    String res_str = "";

    int current_row = 0;

    for ( String input_str : pListInput )
    {
      for ( int current_col = 0; current_col < input_str.length(); current_col++ )
      {
        res_str += getCellValueDef( current_row, current_col, DEFAULT_CELL_VALUE );
      }

      current_row++;

      res_str += "\n";
    }

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
      String str_map_char = "";
      String str_map_values = "";

      for ( int current_col = 0; current_col < input_str.length(); current_col++ )
      {
        if ( input_str.charAt( current_col ) == pChar )
        {
          str_map_char += pChar;

          sum_char_count++;

          long current_cell_value = getCellValueDef( current_row, current_col, DEFAULT_CELL_VALUE );

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

      res_str += str_map_char + STR__DEBUG_SPACER + str_map_values + "\n";
    }

    String str_map_char = " " + sum_char_count + " ";
    String str_map_values = " " + sum_cell_values + " ";

    res_str += str_map_char + STR__DEBUG_SPACER + str_map_values + "\n";

    getHashMapCellValues().put( "RES_" + pChar + "_SUM_CHAR_COUNT", Long.valueOf( sum_char_count ) );
    getHashMapCellValues().put( "RES_" + pChar + "_SUM_MAP_VALUES", Long.valueOf( sum_cell_values ) );

    getHashMapCellValues().put( "RES_" + pChar + "_COST", Long.valueOf( sum_cell_values * sum_char_count ) );

    return res_str;
  }

  private static boolean checkChar( List< String > pList, int pCurrentRow, int pCurrentCol, int pDeltaRow, int pDeltaCol, char pTargetChar )
  {
    int target_row = pCurrentRow + pDeltaRow;
    int target_col = pCurrentCol + pDeltaCol;

    if ( ( target_row >= 0 ) && ( target_row < pList.size() ) )
    {
      String target_str = pList.get( target_row );

      if ( ( target_col >= 0 ) && ( target_col < target_str.length() ) )
      {
        return target_str.charAt( target_col ) == pTargetChar;
      }
    }

    return false;
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
    Long int_val = getHashMapCellValues().get( pKey );

    if ( int_val != null )
    {
      return int_val.intValue();
    }

    return pDefaultValue;
  }

  private static long getCellValueDef( int pRow, int pCol, long pDefaultValue )
  {
    Long int_val = getHashMapCellValues().get( "R" + pRow + "C" + pCol );

    if ( int_val != null )
    {
      return int_val.intValue();
    }

    return setCellValue( pRow, pCol, pDefaultValue );
  }

  private static long setCellValue( int pRow, int pCol, long pValue )
  {
    getHashMapCellValues().put( "R" + pRow + "C" + pCol, Long.valueOf( pValue ) );

    return pValue;
  }

  private static long getCharCountDef( char pChar, long pDefaultValue )
  {
    Long int_val = getHashMapCellValues().get( "char_" + pChar );

    if ( int_val != null )
    {
      return int_val.longValue();
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
