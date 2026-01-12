package de.ea234.aoc2024.day06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Day06GuardGallivant
{

  /* 
   * --- Day 6: Guard Gallivant ---
   * https://adventofcode.com/2024/day/6
   *   
   */

  private static final char   CHAR_OBSTACLE        = '#';

  private static final char   CHAR_GUARDIAN_PATH   = 'x';

  private static final char   CHAR_GUARDIAN_UP     = '^';

  private static final char   CHAR_GUARDIAN_DOWN   = 'v';

  private static final char   CHAR_GUARDIAN_LEFT   = '<';

  private static final char   CHAR_GUARDIAN_RIGHT  = '>';

  private static final char   CHAR_EMPTY_SPACE     = '.';

  private static final String CHAR_GUARDIAN_START  = "^";

  private static final String KEY_ROW              = "R";

  private static final String KEY_COL              = "C";

  private static final int    ROW_0                = 0;

  private static final int    COL_0                = 0;

  private static final int    ROW_MINUS_1          = -1;

  private static final int    ROW_PLUS_1           = 1;

  private static final int    COL_MINUS_1          = -1;

  private static final int    COL_PLUS_1           = 1;

  private static final String KEY_TOTAL            = "T";

  private static Properties   properties_positions = new Properties();

  public static void main( String[] args )
  {
    String test_content = "....#.....,.........#,..........,..#.......,.......#..,..........,.#..^.....,........#.,#.........,......#...";

    List< String > test_content_list = Arrays.stream( test_content.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    //wl( calcGridPart1( test_content_list, true ) );
    wl( calcGridPart1( getListProd(), true ) );

    wl( "Prop Size " + getSaveProp().size() );
  }

  private static String calcGridPart1( List< String > pList, boolean pKnzDebug )
  {
    properties_positions = new Properties();

    wl( "" );
    wl( "calcGridPart1" );
    wl( "" );

    int guardian_pos_row = -1;
    int guardian_pos_col = -1;

    int map_width = 0;
    int map_height = 0;

    int row_index = 0;

    for ( String map_str : pList )
    {
      wl( map_str );

      if ( map_str.contains( CHAR_GUARDIAN_START ) )
      {
        guardian_pos_row = row_index;

        guardian_pos_col = map_str.indexOf( CHAR_GUARDIAN_START );

        map_width = map_str.length();
      }

      row_index++;
    }

    map_height = pList.size();

    wl( "" );
    wl( "Guardian Start Row " + guardian_pos_row + " Col " + guardian_pos_col );
    wl( "" );

    int loop_prevent_cirquit_breaker = 0;

    int add_row = ROW_MINUS_1;
    int add_col = COL_0;

    boolean knz_guardian_in_field = true;

    char debug_c = CHAR_GUARDIAN_UP;

    /*
     * Mark start position ... counts as a field in the result
     */
    setDebugList( "T", guardian_pos_row, guardian_pos_col, 'S' );

    while ( ( knz_guardian_in_field ) && ( loop_prevent_cirquit_breaker < Integer.MAX_VALUE ) )
    {
      loop_prevent_cirquit_breaker++;

      /*
       * Check next Field
       */

      int next_pos_row = guardian_pos_row + add_row;
      int next_pos_col = guardian_pos_col + add_col;

      knz_guardian_in_field = ( ( ( next_pos_col >= 0 ) && ( next_pos_col < map_width ) ) && ( ( next_pos_row >= 0 ) && ( next_pos_row < map_height ) ) );

      if ( knz_guardian_in_field )
      {
        /*
         * Next Position is in the field
         * Check for a free field in front of you.
         * The field-positions are the calculated positions
         */
        boolean knz_next_field_is_empty = !checkField( pList, next_pos_row, next_pos_col, CHAR_OBSTACLE );

        if ( knz_next_field_is_empty )
        {
          /*
           * empty field next one
           */
          guardian_pos_row = next_pos_row;
          guardian_pos_col = next_pos_col;

          setDebugList( "T", next_pos_row, next_pos_col, debug_c );
        }
        else
        {
          /*
           * found obstacle
           */
          if ( add_row == ROW_MINUS_1 )
          {
            add_row = ROW_0;
            add_col = COL_PLUS_1;

            debug_c = CHAR_GUARDIAN_RIGHT;
          }
          else if ( add_col == COL_PLUS_1 )
          {
            add_row = ROW_PLUS_1;
            add_col = COL_0;

            debug_c = CHAR_GUARDIAN_DOWN;
          }
          else if ( add_row == ROW_PLUS_1 )
          {
            add_row = ROW_0;
            add_col = COL_MINUS_1;

            debug_c = CHAR_GUARDIAN_LEFT;
          }
          else if ( add_col == COL_MINUS_1 )
          {
            add_row = ROW_MINUS_1;
            add_col = COL_0;

            debug_c = CHAR_GUARDIAN_UP;
          }
        }
      }
    }

    return getDebugMaps( pList );
  }

  private static String getDebugMaps( List< String > pList )
  {
    String result_str = "";

    String str_cr_lf = "";

    String str_current_line = pList.get( 0 );

    for ( int row_index = 0; row_index < pList.size(); row_index++ )
    {
      String str_map_plan_t = "";

      for ( int col_index = 0; col_index < str_current_line.length(); col_index++ )
      {
        str_map_plan_t += getDebugChar( KEY_TOTAL, row_index, col_index );
      }

      result_str += str_cr_lf + str_map_plan_t;

      str_cr_lf = "\n";
    }

    return result_str;
  }

  private static boolean checkField( List< String > pList, int pRow, int pCol, char pTargetChar )
  {
    if ( ( pRow >= 0 ) && ( pRow < pList.size() ) )
    {
      String target_str = pList.get( pRow );

      if ( ( pCol >= 0 ) && ( pCol < target_str.length() ) )
      {
        return target_str.charAt( pCol ) == pTargetChar;
      }
    }

    return false;
  }

  private static Properties getSaveProp()
  {
    if ( properties_positions == null )
    {
      properties_positions = new Properties();
    }

    return properties_positions;
  }

  private static String getDebugChar( String pSpec, int pCurrentRow, int pCurrentCol )
  {
    return getSaveProp().getProperty( pSpec + KEY_ROW + pCurrentRow + KEY_COL + pCurrentCol, "." );
  }

  private static void setDebugList( String pSpec, int pCurrentRow, int pCurrentCol, char pChar )
  {
    getSaveProp().setProperty( pSpec + KEY_ROW + pCurrentRow + KEY_COL + pCurrentCol, "" + pChar );
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day06_input.txt";

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

  private static void wl( String pString )
  {
    System.out.println( pString );
  }

}
