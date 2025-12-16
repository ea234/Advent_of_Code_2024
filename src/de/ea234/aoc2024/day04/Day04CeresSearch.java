package de.ea234.aoc2024.day04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Day04CeresSearch
{
  /*
   * --- Day 4: Ceres Search ---
   * https://adventofcode.com/2024/day/4
   * 
   * -------------------------------------------------------------------------------------------- 
   * 
   * MMMSXXMASM    ....XX....    ....11....
   * MSAMXMSMSA    ....X.....    ....1.....
   * AMXSXMAAMM    ..........    ..0.0.....
   * MSAMASMSMX    .........X    .........2
   * XMASAMXAMM    X.....X...    1.....2...
   * XXAMMXXAMA    X.....X...    10...01...
   * SMSMSASXSS    ..........    .......0..
   * SAXAMASAAA    ..........    ..0.......
   * MAMMMXMMMM    ..........    .....0....
   * MXMXAXMASX    .X.X.X...X    .1.2.3...2
   * 
   * 
   * .....XMAS.    ..........    ....X.....    ....XXMAS.
   * .SAMX.....    ......S...    .....M....    .SAMXMS...
   * ..........    ......A...    ...S..A...    ...S..A...
   * ..........    ......M..X    ..A.A..S.X    ..A.A.MS.X
   * XMASAMX...    ......X..M    .M...M..M.    XMASAMX.MM
   * ..........    .........A    X.....XA..    X.....XA.A
   * ..........    .........S    S.S.S.S.S.    S.S.S.S.SS
   * ..........    .........A    .A.A.A.A..    .A.A.A.A.A
   * ..........    .........M    ..M.M.M.M.    ..M.M.M.MM
   * .....XMAS.    .........X    .X.X.X...X    .X.X.XMASX
   * 
   * Found xmas 18
   * 
   * -------------------------------------------------------------------------------------------- 
   * 
   * MMMSXXMASM    ..........    .......0..
   * MSAMXMSMSA    ..A.......    ..1......0
   * AMXSXMAAMM    ......AA..    0.....11..
   * MSAMASMSMX    ..A.A.....    ..1.1.....
   * XMASAMXAMM    ..........    ..0.0..0..
   * XXAMMXXAMA    ..........    ..0....0.0
   * SMSMSASXSS    ..........    .....0....
   * SAXAMASAAA    .A.A.A.A..    .1.1.1.100
   * MAMMMXMMMM    ..........    .0........
   * MXMXAXMASX    ..........    ....0..0..
   * 
   * 
   * .M.S......    ..........    ..........    .M.S......
   * ..A.......    .....MSMS.    ..........    ..A..MSMS.
   * .M.S......    ......AA..    ...S.M....    .M.S.MAA..
   * ..A.......    .....SMSM.    ....A.....    ..A.ASMSM.
   * .M.S......    ..........    ...S.M....    .M.S.M....
   * ..........    ..........    ..........    ..........
   * ..........    S.S.S.S.S.    ..........    S.S.S.S.S.
   * ..........    .A.A.A.A..    ..........    .A.A.A.A..
   * ..........    M.M.M.M.M.    ..........    M.M.M.M.M.
   * ..........    ..........    ..........    ..........
   * 
   * Found mas 9 
   *   
   */

  private static final char   CHAR_X               = 'X';

  private static final char   CHAR_M               = 'M';

  private static final char   CHAR_A               = 'A';

  private static final char   CHAR_S               = 'S';

  private static final char   CHAR_EMPTY_SPACE     = '.';

  private static final String KEY_ROW              = "R";

  private static final String KEY_COL              = "C";

  private static final String KEY_DIAGONAL         = "D";

  private static final String KEY_HORIZONTAL       = "H";

  private static final String KEY_VERTICAL         = "V";

  private static final String KEY_TOTAL            = "T";

  private static Properties   properties_positions = new Properties();

  public static void main( String[] args )
  {
    String test_content = "MMMSXXMASM,MSAMXMSMSA,AMXSXMAAMM,MSAMASMSMX,XMASAMXAMM,XXAMMXXAMA,SMSMSASXSS,SAXAMASAAA,MAMMMXMMMM,MXMXAXMASX";

    List< String > test_content_list = Arrays.stream( test_content.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    wl( calcGridPart2( getListProd(), false ) );

    wl( calcGridPart2( test_content_list, true ) );
  }

  private static String calcGridPart1( List< String > pList, boolean pKnzDebug )
  {
    wl( "" );
    wl( "calcGridPart1" );
    wl( "" );

    properties_positions = null;

    String str_spacer_debug = "    ";

    int count_xmas_sum = 0;

    String result_str = "";

    String str_cr_lf = "";

    for ( int list_index = 0; list_index < pList.size(); list_index++ )
    {
      String str_current_line = pList.get( list_index );

      String str_floor_plan = "";

      String str_current_position_count = "";

      for ( int col_index = 0; col_index < str_current_line.length(); col_index++ )
      {
        if ( ( str_current_line.charAt( col_index ) == CHAR_X ) )
        {
          int current_xmas_count = 0;

          current_xmas_count += checkHorizontalPlus( pList, list_index, col_index );
          current_xmas_count += checkHorizontalMinus( pList, list_index, col_index );

          current_xmas_count += checkVerticalPlus( pList, list_index, col_index );
          current_xmas_count += checkVerticalMinus( pList, list_index, col_index );

          current_xmas_count += checkDiagonalPlus( pList, list_index, col_index );
          current_xmas_count += checkDiagonalMinus( pList, list_index, col_index );

          count_xmas_sum += current_xmas_count;

          if ( pKnzDebug )
          {
            if ( current_xmas_count > 0 )
            {
              str_floor_plan += CHAR_X;
            }
            else
            {
              str_floor_plan += CHAR_EMPTY_SPACE;
            }

            str_current_position_count += "" + current_xmas_count;
          }
        }
        else
        {
          str_current_position_count += CHAR_EMPTY_SPACE;

          str_floor_plan += CHAR_EMPTY_SPACE;
        }
      }

      if ( pKnzDebug )
      {
        result_str += str_cr_lf + str_current_line + str_spacer_debug + str_floor_plan + str_spacer_debug + str_current_position_count;
      }

      str_cr_lf = "\n";
    }

    if ( pKnzDebug )
    {
      result_str += str_cr_lf;
      result_str += str_cr_lf;

      String str_current_line = pList.get( 0 );

      for ( int list_index = 0; list_index < pList.size(); list_index++ )
      {
        String str_floor_plan_h = "";
        String str_floor_plan_v = "";
        String str_floor_plan_d = "";
        String str_floor_plan_t = "";

        for ( int col_index = 0; col_index < str_current_line.length(); col_index++ )
        {
          str_floor_plan_h += getDebugChar( KEY_HORIZONTAL, list_index, col_index );
          str_floor_plan_v += getDebugChar( KEY_VERTICAL, list_index, col_index );
          str_floor_plan_d += getDebugChar( KEY_DIAGONAL, list_index, col_index );
          str_floor_plan_t += getDebugChar( KEY_TOTAL, list_index, col_index );
        }

        result_str += str_cr_lf + str_floor_plan_h + str_spacer_debug + str_floor_plan_v + str_spacer_debug + str_floor_plan_d + str_spacer_debug + str_floor_plan_t;
      }
    }
    else
    {
      result_str = "";
    }

    result_str += "\n\nFound xmas " + count_xmas_sum + " \n";

    return result_str;
  }

  private static String calcGridPart2( List< String > pList, boolean pKnzDebug )
  {
    wl( "" );
    wl( "calcGridPart2" );
    wl( "" );

    properties_positions = null;

    String str_spacer_debug = "    ";

    int count_xmas_sum = 0;

    String result_str = "";

    String str_cr_lf = "";

    for ( int list_index = 0; list_index < pList.size(); list_index++ )
    {
      String str_current_line = pList.get( list_index );

      String str_floor_plan = "";

      String str_current_position_count = "";

      for ( int col_index = 0; col_index < str_current_line.length(); col_index++ )
      {
        if ( ( str_current_line.charAt( col_index ) == CHAR_A ) )
        {
          int current_xmas_count = 0;

          current_xmas_count += checkMas( pList, list_index, col_index );

          count_xmas_sum += current_xmas_count;

          if ( pKnzDebug )
          {
            if ( current_xmas_count > 0 )
            {
              str_floor_plan += CHAR_A;
            }
            else
            {
              str_floor_plan += CHAR_EMPTY_SPACE;
            }

            str_current_position_count += "" + current_xmas_count;
          }
        }
        else
        {
          str_current_position_count += CHAR_EMPTY_SPACE;

          str_floor_plan += CHAR_EMPTY_SPACE;
        }
      }

      if ( pKnzDebug )
      {
        result_str += str_cr_lf + str_current_line + str_spacer_debug + str_floor_plan + str_spacer_debug + str_current_position_count;
      }

      str_cr_lf = "\n";
    }

    if ( pKnzDebug )
    {
      result_str += str_cr_lf;
      result_str += str_cr_lf;

      String str_current_line = pList.get( 0 );

      for ( int list_index = 0; list_index < pList.size(); list_index++ )
      {
        String str_floor_plan_h = "";
        String str_floor_plan_v = "";
        String str_floor_plan_d = "";
        String str_floor_plan_t = "";

        for ( int col_index = 0; col_index < str_current_line.length(); col_index++ )
        {
          str_floor_plan_h += getDebugChar( KEY_HORIZONTAL, list_index, col_index );
          str_floor_plan_v += getDebugChar( KEY_VERTICAL, list_index, col_index );
          str_floor_plan_d += getDebugChar( KEY_DIAGONAL, list_index, col_index );
          str_floor_plan_t += getDebugChar( KEY_TOTAL, list_index, col_index );
        }

        result_str += str_cr_lf + str_floor_plan_h + str_spacer_debug + str_floor_plan_v + str_spacer_debug + str_floor_plan_d + str_spacer_debug + str_floor_plan_t;
      }
    }
    else
    {
      result_str = "";
    }

    result_str += "\n\nFound xmas " + count_xmas_sum + " \n";

    return result_str;
  }

  private static long checkMas( List< String > pList, int pCurrentRow, int pCurrentCol )
  {
    boolean knz_check_result = true;

    /*
     * Both M on the left, Both S on the right
     * 
     * M.S
     * .A.
     * M.S
     */
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 0, 0, CHAR_A );

    if ( knz_check_result )
    {
      knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, -1, -1, CHAR_M );
      knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 1, -1, CHAR_M );

      knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, -1, 1, CHAR_S );
      knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 1, 1, CHAR_S );
    }

    if ( knz_check_result )
    {
      setDebugList( KEY_HORIZONTAL, pCurrentRow, pCurrentCol, 0, 0, CHAR_A );

      setDebugList( KEY_HORIZONTAL, pCurrentRow, pCurrentCol, -1, -1, CHAR_M );
      setDebugList( KEY_HORIZONTAL, pCurrentRow, pCurrentCol, 1, -1, CHAR_M );

      setDebugList( KEY_HORIZONTAL, pCurrentRow, pCurrentCol, -1, 1, CHAR_S );
      setDebugList( KEY_HORIZONTAL, pCurrentRow, pCurrentCol, 1, 1, CHAR_S );
    }

    long temp_value = knz_check_result ? 1 : 0;

    /*
     * Both S on the left, Both M on the right
     * 
     * S.M
     * .A.
     * S.M
     */

    knz_check_result = checkChar( pList, pCurrentRow, pCurrentCol, 0, 0, CHAR_A );

    if ( knz_check_result )
    {
      knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, -1, -1, CHAR_S );
      knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 1, -1, CHAR_S );

      knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, -1, 1, CHAR_M );
      knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 1, 1, CHAR_M );
    }

    if ( knz_check_result )
    {
      setDebugList( KEY_DIAGONAL, pCurrentRow, pCurrentCol, 0, 0, CHAR_A );

      setDebugList( KEY_DIAGONAL, pCurrentRow, pCurrentCol, -1, -1, CHAR_S );
      setDebugList( KEY_DIAGONAL, pCurrentRow, pCurrentCol, 1, -1, CHAR_S );

      setDebugList( KEY_DIAGONAL, pCurrentRow, pCurrentCol, -1, 1, CHAR_M );
      setDebugList( KEY_DIAGONAL, pCurrentRow, pCurrentCol, 1, 1, CHAR_M );
    }

    temp_value += knz_check_result ? 1 : 0;

    /*
     * Both M above, Both S below
     * On each side, there is a M and an S
     * 
     * M.M
     * .A.
     * S.S
     */

    knz_check_result = checkChar( pList, pCurrentRow, pCurrentCol, 0, 0, CHAR_A );

    if ( knz_check_result )
    {
      knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, -1, -1, CHAR_M ); // above minus
      knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 1, -1, CHAR_S ); // below minus 

      knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, -1, 1, CHAR_M ); // above plus
      knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 1, 1, CHAR_S ); // below plus
    }

    if ( knz_check_result )
    {
      setDebugList( KEY_VERTICAL, pCurrentRow, pCurrentCol, 0, 0, CHAR_A );

      setDebugList( KEY_VERTICAL, pCurrentRow, pCurrentCol, -1, -1, CHAR_M );
      setDebugList( KEY_VERTICAL, pCurrentRow, pCurrentCol, -1, 1, CHAR_M );

      setDebugList( KEY_VERTICAL, pCurrentRow, pCurrentCol, 1, -1, CHAR_S );
      setDebugList( KEY_VERTICAL, pCurrentRow, pCurrentCol, 1, 1, CHAR_S );
    }

    temp_value += knz_check_result ? 1 : 0;

    /*
     * Both S above, Both M below
     * 
     * S.S
     * .A.
     * M.M
     */

    knz_check_result = checkChar( pList, pCurrentRow, pCurrentCol, 0, 0, CHAR_A );

    if ( knz_check_result )
    {
      knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, -1, -1, CHAR_S ); // above minus
      knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, -1, 1, CHAR_S ); // above plus

      knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 1, -1, CHAR_M ); // below minus
      knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 1, 1, CHAR_M ); // below plus
    }

    if ( knz_check_result )
    {
      setDebugList( KEY_VERTICAL, pCurrentRow, pCurrentCol, 0, 0, CHAR_A );

      setDebugList( KEY_VERTICAL, pCurrentRow, pCurrentCol, -1, -1, CHAR_S );
      setDebugList( KEY_VERTICAL, pCurrentRow, pCurrentCol, -1, 1, CHAR_S );

      setDebugList( KEY_VERTICAL, pCurrentRow, pCurrentCol, 1, -1, CHAR_M );
      setDebugList( KEY_VERTICAL, pCurrentRow, pCurrentCol, 1, 1, CHAR_M );
    }

    temp_value += knz_check_result ? 1 : 0;

    return temp_value;
  }

  private static long checkHorizontalPlus( List< String > pList, int pCurrentRow, int pCurrentCol )
  {
    boolean knz_check_result = true;

    //knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 0, 1, CHAR_M );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 0, 2, CHAR_A );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 0, 3, CHAR_S );

    if ( knz_check_result )
    {
      setDebugList( KEY_HORIZONTAL, pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
      setDebugList( KEY_HORIZONTAL, pCurrentRow, pCurrentCol, 0, 1, CHAR_M );
      setDebugList( KEY_HORIZONTAL, pCurrentRow, pCurrentCol, 0, 2, CHAR_A );
      setDebugList( KEY_HORIZONTAL, pCurrentRow, pCurrentCol, 0, 3, CHAR_S );
    }

    return knz_check_result ? 1 : 0;
  }

  private static long checkHorizontalMinus( List< String > pList, int pCurrentRow, int pCurrentCol )
  {
    boolean knz_check_result = true;

    //knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 0, -1, CHAR_M );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 0, -2, CHAR_A );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 0, -3, CHAR_S );

    if ( knz_check_result )
    {
      setDebugList( KEY_HORIZONTAL, pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
      setDebugList( KEY_HORIZONTAL, pCurrentRow, pCurrentCol, 0, -1, CHAR_M );
      setDebugList( KEY_HORIZONTAL, pCurrentRow, pCurrentCol, 0, -2, CHAR_A );
      setDebugList( KEY_HORIZONTAL, pCurrentRow, pCurrentCol, 0, -3, CHAR_S );
    }

    return knz_check_result ? 1 : 0;
  }

  private static long checkVerticalPlus( List< String > pList, int pCurrentRow, int pCurrentCol )
  {
    boolean knz_check_result = true;

    //knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 1, 0, CHAR_M );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 2, 0, CHAR_A );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 3, 0, CHAR_S );

    if ( knz_check_result )
    {
      setDebugList( KEY_VERTICAL, pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
      setDebugList( KEY_VERTICAL, pCurrentRow, pCurrentCol, 1, 0, CHAR_M );
      setDebugList( KEY_VERTICAL, pCurrentRow, pCurrentCol, 2, 0, CHAR_A );
      setDebugList( KEY_VERTICAL, pCurrentRow, pCurrentCol, 3, 0, CHAR_S );
    }

    return knz_check_result ? 1 : 0;
  }

  private static long checkVerticalMinus( List< String > pList, int pCurrentRow, int pCurrentCol )
  {
    boolean knz_check_result = true;

    //knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, -1, 0, CHAR_M );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, -2, 0, CHAR_A );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, -3, 0, CHAR_S );

    if ( knz_check_result )
    {
      setDebugList( KEY_VERTICAL, pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
      setDebugList( KEY_VERTICAL, pCurrentRow, pCurrentCol, -1, 0, CHAR_M );
      setDebugList( KEY_VERTICAL, pCurrentRow, pCurrentCol, -2, 0, CHAR_A );
      setDebugList( KEY_VERTICAL, pCurrentRow, pCurrentCol, -3, 0, CHAR_S );
    }

    return knz_check_result ? 1 : 0;
  }

  private static long checkDiagonalPlus( List< String > pList, int pCurrentRow, int pCurrentCol )
  {
    boolean knz_check_result = true;

    //knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 1, 1, CHAR_M );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 2, 2, CHAR_A );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 3, 3, CHAR_S );

    if ( knz_check_result )
    {
      setDebugList( KEY_DIAGONAL, pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
      setDebugList( KEY_DIAGONAL, pCurrentRow, pCurrentCol, 1, 1, CHAR_M );
      setDebugList( KEY_DIAGONAL, pCurrentRow, pCurrentCol, 2, 2, CHAR_A );
      setDebugList( KEY_DIAGONAL, pCurrentRow, pCurrentCol, 3, 3, CHAR_S );
    }

    long temp_value = knz_check_result ? 1 : 0;

    knz_check_result = true;

    //knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 1, -1, CHAR_M );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 2, -2, CHAR_A );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 3, -3, CHAR_S );

    if ( knz_check_result )
    {
      setDebugList( KEY_DIAGONAL, pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
      setDebugList( KEY_DIAGONAL, pCurrentRow, pCurrentCol, 1, -1, CHAR_M );
      setDebugList( KEY_DIAGONAL, pCurrentRow, pCurrentCol, 2, -2, CHAR_A );
      setDebugList( KEY_DIAGONAL, pCurrentRow, pCurrentCol, 3, -3, CHAR_S );
    }

    temp_value += knz_check_result ? 1 : 0;

    return temp_value;
  }

  private static long checkDiagonalMinus( List< String > pList, int pCurrentRow, int pCurrentCol )
  {
    boolean knz_check_result = true;

    //knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, -1, -1, CHAR_M );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, -2, -2, CHAR_A );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, -3, -3, CHAR_S );

    if ( knz_check_result )
    {
      setDebugList( KEY_DIAGONAL, pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
      setDebugList( KEY_DIAGONAL, pCurrentRow, pCurrentCol, -1, -1, CHAR_M );
      setDebugList( KEY_DIAGONAL, pCurrentRow, pCurrentCol, -2, -2, CHAR_A );
      setDebugList( KEY_DIAGONAL, pCurrentRow, pCurrentCol, -3, -3, CHAR_S );
    }

    long temp_value = knz_check_result ? 1 : 0;

    knz_check_result = true;

    //knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, -1, 1, CHAR_M );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, -2, 2, CHAR_A );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, -3, 3, CHAR_S );

    if ( knz_check_result )
    {
      setDebugList( KEY_DIAGONAL, pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
      setDebugList( KEY_DIAGONAL, pCurrentRow, pCurrentCol, -1, 1, CHAR_M );
      setDebugList( KEY_DIAGONAL, pCurrentRow, pCurrentCol, -2, 2, CHAR_A );
      setDebugList( KEY_DIAGONAL, pCurrentRow, pCurrentCol, -3, 3, CHAR_S );
    }

    temp_value += knz_check_result ? 1 : 0;

    return temp_value;
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

  private static void setDebugList( String pSpec, int pCurrentRow, int pCurrentCol, int pDeltaRow, int pDeltaCol, char pChar )
  {
    int target_row = pCurrentRow + pDeltaRow;
    int target_col = pCurrentCol + pDeltaCol;

    getSaveProp().setProperty( pSpec + KEY_ROW + target_row + KEY_COL + target_col, "" + pChar );

    getSaveProp().setProperty( "TR" + target_row + KEY_COL + target_col, "" + pChar );
  }

  private static List< String > getListProd()
  {
    int row_count = 0;

    List< String > string_array = new ArrayList< String >();

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day04_input.txt";

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
