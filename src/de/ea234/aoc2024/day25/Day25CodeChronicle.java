package de.ea234.aoc2024.day25;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day25CodeChronicle
{
  /*
   * --- Day 25: Code Chronicle ---
   * https://adventofcode.com/2024/day/25
   * 
   * https://www.youtube.com/watch?v=EtKZSHuiYT0
   * 
   * https://www.reddit.com/r/adventofcode/comments/1hlu4ht/2024_day_25_solutions/
   */

  private static final String STR_COMBINE_SPACER = "      ";

  public static void main( String[] args )
  {
    String test_input = "#####,.####,.####,.####,.#.#.,.#...,.....,,#####,##.##,.#.##,...##,...#.,...#.,.....,,.....,#....,#....,#...#,#.#.#,#.###,#####,,.....,.....,#.#..,###..,###.#,###.#,#####,,.....,.....,.....,#....,#.#..,#.#.#,#####";

    calculatePart01( test_input, true );

    calculatePart01( getListProd(), true );

    testFktDevice();

    System.exit( 0 );
  }

  private static void testFktDevice()
  {
    /*
     * --------------------------------------------------
     * 
     * #####    #####    .....
     * .####    ##.##    #....
     * .####    .#.##    #....
     * .####    ...##    #...#
     * .#.#.    ...#.    #.#.#
     * .#...    ...#.    #.###
     * .....    .....    #####
     * 05343    12053    50213
     * Lock     Lock     Key  
     * 
     * no match - height - A = 0,5,3,4,3 B 5,0,2,1,3 =     - Index 4 A 3 B 3, 
     * no match - height - A = 1,2,0,5,3 B 5,0,2,1,3 =    -- Index 3 A 5 B 1,  Index 4 A 3 B 3, 
     * 
     * --------------------------------------------------
     * 
     * #####    #####    .....
     * .####    ##.##    .....
     * .####    .#.##    #.#..
     * .####    ...##    ###..
     * .#.#.    ...#.    ###.#
     * .#...    ...#.    ###.#
     * .....    .....    #####
     * 05343    12053    43402
     * Lock     Lock     Key  
     * 
     * no match - height - A = 0,5,3,4,3 B 4,3,4,0,2 =  --   Index 1 A 5 B 3,  Index 2 A 3 B 4, 
     * match    - height - A = 1,2,0,5,3 B 4,3,4,0,2 =      
     * 
     * --------------------------------------------------
     * 
     * #####    #####    .....
     * .####    ##.##    .....
     * .####    .#.##    .....
     * .####    ...##    #....
     * .#.#.    ...#.    #.#..
     * .#...    ...#.    #.#.#
     * .....    .....    #####
     * 05343    12053    30201
     * Lock     Lock     Key  
     * 
     * match    - height - A = 0,5,3,4,3 B 3,0,2,0,1 =      
     * match    - height - A = 1,2,0,5,3 B 3,0,2,0,1 =  
     * 
     * --------------------------------------------------
     * 
     * Lock 0,5,3,4,3 and key 
     *      5,0,2,1,3: overlap in the last column.
     *              6
     * 
     * Lock 0,5,3,4,3 and key 
     *      4,3,4,0,2: overlap in the second column.
     *        8 
     *         
     * Lock 1,2,0,5,3 and key 
     *      5,0,2,1,3: overlap in the first column.
     *      6 
     * 
     * 
     * 
     * Lock 0,5,3,4,3 and key 
     *      3,0,2,0,1: all columns fit!
     * 
     * Lock 1,2,0,5,3 and 
     *  key 4,3,4,0,2: all columns fit!
     * 
     * Lock 1,2,0,5,3 and 
     *  key 3,0,2,0,1: all columns fit!
     * 
     */

    String test_device_input_lock_1 = "#####,.####,.####,.####,.#.#.,.#...,.....";
    String test_device_input_lock_2 = "#####,##.##,.#.##,...##,...#.,...#.,.....";
    String test_device_input_key_1 = ".....,#....,#....,#...#,#.#.#,#.###,#####";
    String test_device_input_key_2 = ".....,.....,#.#..,###..,###.#,###.#,#####";
    String test_device_input_key_3 = ".....,.....,.....,#....,#.#..,#.#.#,#####";

    Day25Device device_lock_1 = new Day25Device( test_device_input_lock_1 );
    Day25Device device_lock_2 = new Day25Device( test_device_input_lock_2 );
    Day25Device device_key_1 = new Day25Device( test_device_input_key_1 );
    Day25Device device_key_2 = new Day25Device( test_device_input_key_2 );
    Day25Device device_key_3 = new Day25Device( test_device_input_key_3 );

    String debug_devices_1 = combineStrings( device_lock_1.getDebugStr(), combineStrings( device_lock_2.getDebugStr(), device_key_1.getDebugStr() ) );
    String debug_devices_2 = combineStrings( device_lock_1.getDebugStr(), combineStrings( device_lock_2.getDebugStr(), device_key_2.getDebugStr() ) );
    String debug_devices_3 = combineStrings( device_lock_1.getDebugStr(), combineStrings( device_lock_2.getDebugStr(), device_key_3.getDebugStr() ) );

    wl( "" );
    wl( "--------------------------------------------------" );
    wl( "" );
    wl( debug_devices_1 );
    wl( "" );
    wl( "" + device_lock_1.getDebugMatch( device_key_1 ) );
    wl( "" + device_lock_2.getDebugMatch( device_key_1 ) );
    wl( "" );
    wl( "--------------------------------------------------" );
    wl( "" );
    wl( debug_devices_2 );
    wl( "" );
    wl( "" + device_lock_1.getDebugMatch( device_key_2 ) );
    wl( "" + device_lock_2.getDebugMatch( device_key_2 ) );
    wl( "" );
    wl( "--------------------------------------------------" );
    wl( "" );
    wl( debug_devices_3 );
    wl( "" );
    wl( "" + device_lock_1.getDebugMatch( device_key_3 ) );
    wl( "" + device_lock_2.getDebugMatch( device_key_3 ) );
    wl( "" );
  }

  private static void calculatePart01( String pString, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    calculatePart01( converted_string_list, pKnzDebug );
  }

  private static void calculatePart01( List< String > pListInput, boolean pKnzDebug )
  {
    /*
     * *******************************************************************************************************
     * Initializing Variables
     * *******************************************************************************************************
     */
    List< Day25Device > list_locks = new ArrayList< Day25Device >();

    List< Day25Device > list_keys = new ArrayList< Day25Device >();

    /*
     * *******************************************************************************************************
     * Initializing the grid
     * *******************************************************************************************************
     */

    String input_csv_device = null;

    for ( String input_str : pListInput )
    {
      /*
       * Blank line seperates the map from the commands
       */
      if ( input_str.trim().isBlank() )
      {
        wl( input_csv_device );

        Day25Device device_cur = new Day25Device( input_csv_device );

        if ( device_cur.isLock() )
        {
          list_locks.add( device_cur );
        }
        else if ( device_cur.isKey() )
        {
          list_keys.add( device_cur );
        }

        input_csv_device = null;
      }
      else
      {
        if ( input_csv_device == null )
        {
          input_csv_device = input_str;
        }
        else
        {
          input_csv_device += "," + input_str;
        }
      }
    }

    if ( input_csv_device != null )
    {
      wl( input_csv_device );

      Day25Device device_cur = new Day25Device( input_csv_device );

      if ( device_cur.isLock() )
      {
        list_locks.add( device_cur );
      }
      else
      {
        list_keys.add( device_cur );
      }

      input_csv_device = null;
    }

    if ( pKnzDebug )
    {
      wl( "" );
      wl( "" );
      wl( "Locks -------------------------" );
      wl( "" );

      for ( Day25Device device_cur : list_locks )
      {
        wl( device_cur.getDebugStr() + "\n" );
      }

      wl( "" );
      wl( "" );
      wl( "Keys ---------------------------" );
      wl( "" );

      for ( Day25Device device_cur : list_keys )
      {
        wl( device_cur.getDebugStr() + "\n" );
      }
    }

    //for ( Day25Device device_lock : list_locks )
    //{
    //  for ( Day25Device device_key : list_keys )
    //  {
    //    String debug_devices = combineStrings( device_lock.getDebugStr(), device_key.getDebugStr() );
    //
    //    wl( debug_devices );
    //    wl( "" );
    //
    //    boolean knz_do_match = device_lock.doMatch( device_key );
    //
    //    wl( "knz_do_match " + knz_do_match );
    //    wl( "" );
    //    wl( "lock key " + device_lock.getDebugMatch( device_key ) );
    //    wl( "" );
    //  }
    //}

    int count_lock_key_match = 0;

    for ( Day25Device device_lock : list_locks )
    {
      for ( Day25Device device_key : list_keys )
      {
        boolean knz_do_match = device_lock.doMatch( device_key );

        if ( knz_do_match )
        {
          count_lock_key_match++;
        }

        wl( "lock key " + device_lock.getDebugMatch( device_key ) );
      }
    }

    wl( "" );
    wl( "" );
    wl( "Result Part 1 " + count_lock_key_match );
    wl( "" );
    wl( "" );
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day25_input.txt";

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
}
