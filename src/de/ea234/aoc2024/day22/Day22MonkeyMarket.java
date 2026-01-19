package de.ea234.aoc2024.day22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day22MonkeyMarket
{
  /*
   * --- Day 22: Monkey Market ---
   * https://adventofcode.com/2024/day/22
   *
   * https://www.youtube.com/watch?v=Afh6YcMiayw
   * 
   * https://www.reddit.com/r/adventofcode/comments/1hjroap/2024_day_22_solutions
   * 
   * ----------------------------------------------------------------------
   * 
   *   2000         8685429 - price old 2  new 9  diff   7 - seq idx  1964 price   7  [7,-1,3,-6] 
   *   2000         5976613 - price old 6  new 3  diff  -3 - seq idx   291 price   7  [-3,0,1,5] 
   *   2000        14660944 - price old 8  new 4  diff  -4 - seq idx     0 price   0  [-4,5,2,1] 
   *   2000         8667524 - price old 8  new 4  diff  -4 - seq idx   455 price   9  [-4,7,-6,7] 
   * 
   * sum_secret_nr    37990510  <- Result Part 1
   * 
   * 
   * First 15 Key Sequenzes (from 7014)
   * 
   *    0  [3,-1,1,-2]        23  <- Result Part 2
   *    1  [0,1,3,-1]         22 
   *    2  [1,5,-3,1]         21 
   *    3  [-1,0,6,-5]        20 
   *    4  [-1,5,-1,-4]       20 
   *    5  [0,0,5,0]          20 
   *    6  [0,2,3,-1]         19 
   *    7  [3,0,2,-1]         19 
   *    8  [-1,0,5,0]         19 
   *    9  [0,8,1,-6]         18 
   *   10  [5,4,-8,0]         18 
   *   11  [3,5,1,-4]         18 
   *   12  [2,1,1,-4]         18 
   *   13  [4,3,-3,-2]        18 
   *   14  [0,3,-1,5]         18 
   * 
   * ----------------------------------------------------------------------
   * 
   * sum_secret_nr 15613157363  <- Result Part 1
   * 
   * 
   * First 15 Key Sequenzes (from 40951)
   * 
   *    0  [1,1,-1,0]       1784  <- Result Part 2
   *    1  [1,3,-3,1]       1693 
   *    2  [3,-3,1,1]       1665 
   *    3  [3,-3,1,-1]      1662 
   *    4  [0,1,0,2]        1659 
   *    5  [0,0,2,0]        1641 
   *    6  [1,-1,0,0]       1639 
   *    7  [2,1,0,-3]       1638 
   *    8  [2,-2,1,1]       1637 
   *    9  [2,-2,2,-2]      1637 
   *   10  [3,0,0,0]        1636 
   *   11  [0,1,2,-3]       1629 
   *   12  [2,0,0,-2]       1629 
   *   13  [0,3,-3,3]       1625 
   *   14  [0,2,-1,-1]      1617 
   * 
   * ----------------------------------------------------------------------
   * 
   * Test with 5 diff sequenze
   * 
   * First 15 Key Sequenzes (from 462936)
   * 
   *    0  [0,1,0,-1,0]    296  <- Result Part 2
   *    1  [0,4,-4,4,-3]   273
   *    2  [4,-4,3,-3,3]   271
   *    3  [3,0,0,0,-2]    268
   *    4  [3,-1,-2,1,0]   265
   *    5  [3,0,-1,-2,1]   263
   *    6  [4,-3,1,0,2]    263
   *    7  [2,1,-1,-1,0]   261
   *    8  [0,3,-3,3,-3]   258
   *    9  [1,3,-3,1,1]    257
   *   10  [5,-3,2,-3,2]   256
   *   11  [0,3,1,-3,1]    255
   *   12  [1,1,0,2,0]     254
   *   13  [4,-1,-3,4,-2]  252
   *   14  [1,0,-1,0,5]    252
   */

  private static final String SEQUENZE_TO_SEARCH = "-2,1,-1,3";

  public static void main( String[] d )
  {
    String test_input = "1,10,100,2024";

    test_input = "1,2,3,2024";

    calculatePart01( test_input, true, 2000 );

    //calculatePart01( getListProd(), false, 2000 );
  }

  private static void calculatePart01( String pString, boolean pKnzDebug, int pMaxLoop )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    calculatePart01( converted_string_list, pKnzDebug, pMaxLoop );
  }

  private static void calculatePart01( List< String > pListInput, boolean pKnzDebug, int pMaxLoop )
  {
    /*
     * *******************************************************************************************************
     * Initializing Variables
     * *******************************************************************************************************
     */
    List< Day22Buyer > list_buyer = new ArrayList< Day22Buyer >();

    /*
     * *******************************************************************************************************
     * Generating List of Buyer's
     * *******************************************************************************************************
     */

    for ( String input_str : pListInput )
    {
      list_buyer.add( new Day22Buyer( input_str, SEQUENZE_TO_SEARCH ) );
    }

    /*
     * *******************************************************************************************************
     * Do Loops
     * *******************************************************************************************************
     */
    int max_loop = pMaxLoop;

    for ( int loop_count = 0; loop_count < max_loop; loop_count++ )
    {
      for ( Day22Buyer buyer : list_buyer )
      {
        buyer.generateNextSecretNumber();

        if ( pKnzDebug )
        {
          wl( buyer.toString() );
        }
      }
    }

    /*
     * *******************************************************************************************************
     * Calculate the sum of the secret numbers 
     * *******************************************************************************************************
     */

    wl( "" );
    wl( "----------------------------------------------------------------------" );
    wl( "" );

    long sum_secret_nr = 0;

    HashMap< String, Long > key_seq = new HashMap< String, Long >();

    for ( Day22Buyer buyer : list_buyer )
    {
      /*
       * Add up the secret numbers from all buyers
       */
      sum_secret_nr += buyer.getSecretNumberCur();

      /*
       * Add all difference sequenzes to the hashmap
       */
      buyer.addToHashMap( key_seq );

      if ( pKnzDebug )
      {
        wl( buyer.toString() );
      }
    }

    wl( "" );
    wl( String.format( "sum_secret_nr %11d  <- Result Part 1", sum_secret_nr ) );
    wl( "" );
    wl( "" );

    /*
     * *******************************************************************************************************
     * Sorting the hashmap with the difference sequenzes decending by value (Part 2)
     * *******************************************************************************************************
     */

    List< Map.Entry< String, Long > > entries = new ArrayList<>( key_seq.entrySet() );

    entries.sort( ( entry_1, entry_2 ) -> Long.compare( entry_2.getValue(), entry_1.getValue() ) );

    int max_key_seq = entries.size() > 15 ? 15 : entries.size();

    wl( "First " + max_key_seq + " Key Sequenzes " + ( entries.size() > 15 ? "(from " + entries.size() + ")" : "" ) );
    wl( "" );

    int index = 0;

    for ( Map.Entry< String, Long > e : entries )
    {
      wl( String.format( "%4d  %-15s  %4d " + ( index == 0 ? " <- Result Part 2" : "" ), index, e.getKey(), e.getValue() ) );

      index++;

      if ( index == max_key_seq )
      {
        break;
      }
    }

    wl( "" );
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day22_input.txt";

    try
    {
      string_array = Files.readAllLines( Path.of( datei_input ) );
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }

    return string_array;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }
}
