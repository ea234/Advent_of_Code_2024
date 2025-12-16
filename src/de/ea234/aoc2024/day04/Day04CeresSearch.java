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
   */

  private static final char CHAR_X           = 'X';

  private static final char CHAR_M           = 'M';

  private static final char CHAR_A           = 'A';

  private static final char CHAR_S           = 'S';

  private static final char CHAR_EMPTY_SPACE = '.';

  public static void main( String[] args )
  {
    String test_content = "MMMSXXMASM,MSAMXMSMSA,AMXSXMAAMM,MSAMASMSMX,XMASAMXAMM,XXAMMXXAMA,SMSMSASXSS,SAXAMASAAA,MAMMMXMMMM,MXMXAXMASX";

    List< String > test_content_list = Arrays.stream( test_content.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    calcGrid( getListProd(), false, false );

    calcGrid( test_content_list, true, true );
  }

  private static void calcGrid( List< String > pList, boolean pKnzCalculatePart2, boolean pKnzDebug )
  {
    wl( getDebugGrid( pList, pKnzDebug ) );
  }

  private static String getDebugGrid( List< String > pList, boolean pKnzDebug )
  {
    if ( pList == null )
    {
      wl( "pList == null" );

      return "";
    }

    String str_spacer_debug = "    ";

    int count_xmas = 0;

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
          int check_current_x = 0;

          check_current_x += checkHorizontalPlus( pList, list_index, col_index );
          check_current_x += checkHorizontalMinus( pList, list_index, col_index );

          check_current_x += checkVerticalPlus( pList, list_index, col_index );
          check_current_x += checkVerticalMinus( pList, list_index, col_index );

          check_current_x += checkDiagonalPlus( pList, list_index, col_index );
          check_current_x += checkDiagonalMinus( pList, list_index, col_index );

          count_xmas += check_current_x;

          if ( pKnzDebug )
          {
            if ( check_current_x > 0 )
            {

              str_floor_plan += CHAR_X;
            }
            else
            {
              str_floor_plan += CHAR_EMPTY_SPACE;
            }

            str_current_position_count += "" + check_current_x;
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
          str_floor_plan_h += getDebugChar( "H", list_index, col_index );
          str_floor_plan_v += getDebugChar( "V", list_index, col_index );
          str_floor_plan_d += getDebugChar( "D", list_index, col_index );
          str_floor_plan_t += getDebugChar( "T", list_index, col_index );
        }

        result_str += str_cr_lf + str_floor_plan_h + str_spacer_debug + str_floor_plan_v + str_spacer_debug + str_floor_plan_d + str_spacer_debug + str_floor_plan_t;
      }
    }
    else
    {
      result_str = "";
    }

    result_str += "\n\nFound xmas " + count_xmas + " \n";

    return result_str;
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
      setDebugList( "H", pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
      setDebugList( "H", pCurrentRow, pCurrentCol, 0, 1, CHAR_M );
      setDebugList( "H", pCurrentRow, pCurrentCol, 0, 2, CHAR_A );
      setDebugList( "H", pCurrentRow, pCurrentCol, 0, 3, CHAR_S );
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
      setDebugList( "H", pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
      setDebugList( "H", pCurrentRow, pCurrentCol, 0, -1, CHAR_M );
      setDebugList( "H", pCurrentRow, pCurrentCol, 0, -2, CHAR_A );
      setDebugList( "H", pCurrentRow, pCurrentCol, 0, -3, CHAR_S );
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
      setDebugList( "V", pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
      setDebugList( "V", pCurrentRow, pCurrentCol, 1, 0, CHAR_M );
      setDebugList( "V", pCurrentRow, pCurrentCol, 2, 0, CHAR_A );
      setDebugList( "V", pCurrentRow, pCurrentCol, 3, 0, CHAR_S );
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
      setDebugList( "V", pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
      setDebugList( "V", pCurrentRow, pCurrentCol, -1, 0, CHAR_M );
      setDebugList( "V", pCurrentRow, pCurrentCol, -2, 0, CHAR_A );
      setDebugList( "V", pCurrentRow, pCurrentCol, -3, 0, CHAR_S );
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
      setDebugList( "D", pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
      setDebugList( "D", pCurrentRow, pCurrentCol, 1, 1, CHAR_M );
      setDebugList( "D", pCurrentRow, pCurrentCol, 2, 2, CHAR_A );
      setDebugList( "D", pCurrentRow, pCurrentCol, 3, 3, CHAR_S );
    }

    long temp_value = knz_check_result ? 1 : 0;

    knz_check_result = true;

    //knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 1, -1, CHAR_M );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 2, -2, CHAR_A );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 3, -3, CHAR_S );

    if ( knz_check_result )
    {
      setDebugList( "D", pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
      setDebugList( "D", pCurrentRow, pCurrentCol, 1, -1, CHAR_M );
      setDebugList( "D", pCurrentRow, pCurrentCol, 2, -2, CHAR_A );
      setDebugList( "D", pCurrentRow, pCurrentCol, 3, -3, CHAR_S );
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
      setDebugList( "D", pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
      setDebugList( "D", pCurrentRow, pCurrentCol, -1, -1, CHAR_M );
      setDebugList( "D", pCurrentRow, pCurrentCol, -2, -2, CHAR_A );
      setDebugList( "D", pCurrentRow, pCurrentCol, -3, -3, CHAR_S );
    }

    long temp_value = knz_check_result ? 1 : 0;

    knz_check_result = true;

    //knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, -1, 1, CHAR_M );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, -2, 2, CHAR_A );
    knz_check_result &= checkChar( pList, pCurrentRow, pCurrentCol, -3, 3, CHAR_S );

    if ( knz_check_result )
    {
      setDebugList( "D", pCurrentRow, pCurrentCol, 0, 0, CHAR_X );
      setDebugList( "D", pCurrentRow, pCurrentCol, -1, 1, CHAR_M );
      setDebugList( "D", pCurrentRow, pCurrentCol, -2, 2, CHAR_A );
      setDebugList( "D", pCurrentRow, pCurrentCol, -3, 3, CHAR_S );
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

  public static char getChar( List< String > pList, int pCurrentRow, int pCurrentCol, int pDeltaRow, int pDeltaCol )
  {
    int target_row = pCurrentRow + pDeltaRow;
    int target_col = pCurrentCol + pDeltaCol;

    if ( ( target_row >= 0 ) && ( target_row < pList.size() ) )
    {
      String target_str = pList.get( target_row );

      if ( ( target_col >= 0 ) && ( target_col < target_str.length() ) )
      {
        return target_str.charAt( target_col );
      }
    }

    return CHAR_EMPTY_SPACE;
  }

  private static Properties x_save_pos = new Properties();

  private static Properties getSaveProp()
  {
    if ( x_save_pos == null )
    {
      x_save_pos = new Properties();
    }

    return x_save_pos;
  }

  public static String getDebugChar( String pSpec, int pCurrentRow, int pCurrentCol )
  {
    return getSaveProp().getProperty( pSpec + "R" + pCurrentRow + "C" + pCurrentCol, "." );
  }

  public static void setDebugList( String pSpec, int pCurrentRow, int pCurrentCol, int pDeltaRow, int pDeltaCol, char pChar )
  {
    int target_row = pCurrentRow + pDeltaRow;
    int target_col = pCurrentCol + pDeltaCol;

    getSaveProp().setProperty( pSpec + "R" + target_row + "C" + target_col, "" + pChar );
    getSaveProp().setProperty( "TR" + target_row + "C" + target_col, "" + pChar );
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

  /**
   * Ausgabe auf System.out
   * 
   * @param pString der auszugebende String
   */
  private static void wl( String pString )
  {
    System.out.println( pString );
  }
}
