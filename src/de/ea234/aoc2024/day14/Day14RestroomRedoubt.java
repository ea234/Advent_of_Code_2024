package de.ea234.aoc2024.day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Day14RestroomRedoubt
{
  /*
   * --- Day 14: Restroom Redoubt ---
   * https://adventofcode.com/2024/day/14
   *
   * https://www.youtube.com/watch?v=Km6kcOnjSXM
   * 
   * https://www.reddit.com/r/adventofcode/comments/1hdvhvu/2024_day_14_solutions/
   */

  public static void main( String[] args )
  {
    //String test_1 = "p=2,4 v=2,-3";

    //calculatePart01( test_2, true, 7, 10, 5 );

    //String test_2 = "p=0,4 v=3,-3;p=6,3 v=-1,-3;p=10,3 v=-1,2;p=2,0 v=2,-1;p=0,0 v=1,3;p=3,0 v=-2,-2;p=7,6 v=-1,-3;p=3,0 v=-1,-2;p=9,3 v=2,3;p=7,3 v=-1,2;p=2,4 v=2,-3;p=9,5 v=-3,-3";

    //calculatePart01( test_2, true, 7, 10, 100 );

    calculatePart01( getListProd(), false, 103, 101, 100 );

    System.exit( 0 );
  }

  private static void calculatePart01( String pString, boolean pKnzDebug, int pNumberOfRows, int pNumberOfCols, int pNumberOfMoves )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( ";" ) ).map( String::trim ).collect( Collectors.toList() );

    calculatePart01( converted_string_list, pKnzDebug, pNumberOfRows, pNumberOfCols, pNumberOfMoves );
  }

  private static void calculatePart01( List< String > pListInput, boolean pKnzDebug, int pNumberOfRows, int pNumberOfCols, int pNumberOfMoves )
  {
    /*
     * *******************************************************************************************************
     * Initializing Variables
     * *******************************************************************************************************
     */

    List< Day14Robot > robot_list = new ArrayList< Day14Robot >();

    properties_floor_plan = new Properties();

    /*
     * *******************************************************************************************************
     * Loading the start-positions of the robots
     * *******************************************************************************************************
     */

    int map_rows = pNumberOfRows;
    int map_cols = pNumberOfCols;

    int robot_id = 0;

    for ( String input_str : pListInput )
    {
      robot_list.add( new Day14Robot( robot_id, input_str ) );

      robot_id++;
    }

    if ( pKnzDebug )
    {
      for ( Day14Robot robot : robot_list )
      {
        wl( robot.toString() );
      }
    }

    createFloorPlan( robot_list, map_rows, map_cols );

    wl( getDebugMap( map_rows, map_cols ) );

    /*
     * *******************************************************************************************************
     * Moving the robots
     * *******************************************************************************************************
     */

    for ( int move_nr = 1; move_nr <= pNumberOfMoves; move_nr++ )
    {
      for ( Day14Robot robot : robot_list )
      {
        robot.move( map_rows, map_cols );
      }

      if ( xMasTreeCheck( robot_list, map_rows, map_cols ) )
      {
        createFloorPlan( robot_list, map_rows, map_cols );

        wl( "\nxMasTreeCheck?  " + move_nr + "\n" + getDebugMap( map_rows, map_cols ) );
        wl( "" );
      }

      if ( pKnzDebug )
      {
        for ( Day14Robot robot : robot_list )
        {
          wl( robot.toString() );
        }

        createFloorPlan( robot_list, map_rows, map_cols );

        wl( "\n" + move_nr + "\n" + getDebugMap( map_rows, map_cols ) );
      }
    }

    /*
     * *******************************************************************************************************
     * Zeoring out the middle positions and count the number of robots in each quadrant
     * *******************************************************************************************************
     */

    zeroOutMiddleFloorPlan( robot_list, map_rows, map_cols );

    if ( pKnzDebug )
    {
      wl( "\n" + 0 + "\n" + getDebugMap( map_rows, map_cols ) );
    }

    int mid_row = ( map_rows - 1 ) / 2;
    int mid_col = ( map_cols - 1 ) / 2;

    int quadrant_1_robot_count = 0;
    int quadrant_2_robot_count = 0;
    int quadrant_3_robot_count = 0;
    int quadrant_4_robot_count = 0;

    for ( int cur_row = 0; cur_row < map_rows; cur_row++ )
    {
      for ( int cur_col = 0; cur_col < map_cols; cur_col++ )
      {
        String cur_key = "r" + cur_row + "c" + cur_col;

        int cur_robot_count = getHashMapValue( cur_key );

        if ( cur_robot_count > 0 )
        {
          if ( cur_row < mid_row )
          {
            if ( cur_col < mid_col )
            {
              quadrant_1_robot_count += cur_robot_count;
            }
            else
            {
              quadrant_2_robot_count += cur_robot_count;
            }
          }
          else
          {
            if ( cur_col < mid_col )
            {
              quadrant_3_robot_count += cur_robot_count;
            }
            else
            {
              quadrant_4_robot_count += cur_robot_count;
            }
          }
        }
      }
    }

    wl( "mid_row                " + mid_row + "" );
    wl( "mid_col                " + mid_col + "" );
    wl( "" );
    wl( "quadrant_1_robot_count " + quadrant_1_robot_count + "" );
    wl( "quadrant_2_robot_count " + quadrant_2_robot_count + "" );
    wl( "quadrant_3_robot_count " + quadrant_3_robot_count + "" );
    wl( "quadrant_4_robot_count " + quadrant_4_robot_count + "" );
    wl( "" );
    wl( "" );

    /*
     * *******************************************************************************************************
     * Calculating the result for part 1
     * *******************************************************************************************************
     */

    int result_value_part_1 = quadrant_1_robot_count * quadrant_2_robot_count * quadrant_3_robot_count * quadrant_4_robot_count;

    wl( "result part 1 " + result_value_part_1 );
  }

  private static Properties properties_floor_plan = new Properties();

  private static void saveHashMapValue( String pKey, int pValue )
  {
    properties_floor_plan.setProperty( pKey, "" + pValue );
  }

  private static int getHashMapValue( String pKey )
  {
    return Integer.parseInt( properties_floor_plan.getProperty( pKey, "100" ) );
  }

  private static void zeroOutMiddleFloorPlan( List< Day14Robot > robot_list, int pNumberOfRows, int pNumberOfCols )
  {
    int mid_row = ( pNumberOfRows - 1 ) / 2;
    int mid_col = ( pNumberOfCols - 1 ) / 2;

    for ( int cur_row = 0; cur_row < pNumberOfRows; cur_row++ )
    {
      saveHashMapValue( "r" + cur_row + "c" + mid_col, 0 );
    }

    for ( int cur_col = 0; cur_col < pNumberOfCols; cur_col++ )
    {
      saveHashMapValue( "r" + mid_row + "c" + cur_col, 0 );
    }
  }

  private static boolean xMasTreeCheck( List< Day14Robot > robot_list, int pNumberOfRows, int pNumberOfCols )
  {
    //int robots_row_prev = 0;
    //
    //for ( int cur_row = 0; cur_row < pNumberOfRows; cur_row++ )
    //{
    //  int robots_row_cur = 0;
    //
    //  for ( int cur_col = 0; cur_col < pNumberOfCols; cur_col++ )
    //  {
    //    robots_row_cur += getHashMapValue( "r" + cur_row + "c" + cur_col );
    //  }
    //
    //  if ( ( cur_row > 0 ) && ( ( robots_row_cur - robots_row_prev ) != 1 ) )
    //  {
    //    return false;
    //  }
    //
    //  robots_row_prev = robots_row_cur;
    //}
    //
    //return true;

    return false;
  }

  private static void createFloorPlan( List< Day14Robot > robot_list, int pNumberOfRows, int pNumberOfCols )
  {
    for ( int cur_row = 0; cur_row < pNumberOfRows; cur_row++ )
    {
      for ( int cur_col = 0; cur_col < pNumberOfCols; cur_col++ )
      {
        saveHashMapValue( "r" + cur_row + "c" + cur_col, 0 );
      }
    }

    for ( Day14Robot robot : robot_list )
    {
      int cur_robot_count = getHashMapValue( robot.getKey() );

      saveHashMapValue( robot.getKey(), cur_robot_count + 1 );
    }
  }

  public static String getDebugMap( int pNumberOfRows, int pNumberOfCols )
  {
    String debug_string = "";

    for ( int cur_row = 0; cur_row < pNumberOfRows; cur_row++ )
    {
      debug_string += String.format( "%5d  ", cur_row );

      for ( int cur_col = 0; cur_col < pNumberOfCols; cur_col++ )
      {
        int cur_robot_count = getHashMapValue( "r" + cur_row + "c" + cur_col );

        if ( cur_robot_count == 0 )
        {
          debug_string += ".";
        }
        else
        {
          debug_string += cur_robot_count;
        }
      }

      debug_string += "\n";
    }

    return debug_string;
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day14_input.txt";

    try
    {
      string_array = Files.readAllLines( Path.of( datei_input ) );
    }
    catch ( IOException err_inst )
    {
      err_inst.printStackTrace();
    }

    return string_array;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }
}
