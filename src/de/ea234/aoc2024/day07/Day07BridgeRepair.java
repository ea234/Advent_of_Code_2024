package de.ea234.aoc2024.day07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day07BridgeRepair
{

  /*
   * --- Day 7: Bridge Repair ---
   * https://adventofcode.com/2024/day/7
   * 
   * 
   * -------------------------------------------------------------------------------------
   * 
   * checkEquation( "13: 2 3 3 5" )
   * 
   * Size                  5
   * Symbols needed        3
   * Symbols combinations  8
   * 
   * Nr 0 2 * 3 * 3 * 5 = 90
   * Nr 1 2 + 3 * 3 * 5 = 75
   * Nr 2 2 * 3 + 3 * 5 = 45
   * Nr 3 2 + 3 + 3 * 5 = 40
   * Nr 4 2 * 3 * 3 + 5 = 23
   * Nr 5 2 + 3 * 3 + 5 = 20
   * Nr 6 2 * 3 + 3 + 5 = 14
   * Nr 7 2 + 3 + 3 + 5 = 13
   * 
   * bit_mask_cur_value   7 00000011
   * calc_result          13
   * calc_equation        2 + 3 + 3 + 5
   * 
   * pEquation 13: 2 3 3 5
   * 
   * 
   * -------------------------------------------------------------------------------------
   * 
   * checkEquation( "3267: 81 40 27" )
   * 
   * Size                  4
   * Symbols needed        2
   * Symbols combinations  4
   * 
   * Nr 0 81 * 40 * 27 = 87480
   * Nr 1 81 + 40 * 27 = 3267
   * 
   * bit_mask_cur_value   1 00000000
   * calc_result          3267
   * calc_equation        81 + 40 * 27
   * 
   * pEquation 3267: 81 40 27
   * 
   * 
   * Result  OK 
   * 
   * 
   * -------------------------------------------------------------------------------------
   * 
   * checkEquation( "21037: 9 7 18 13" )
   * 
   * Size                  5
   * Symbols needed        3
   * Symbols combinations  8
   * 
   * Nr 0 9 * 7 * 18 * 13 = 14742
   * Nr 1 9 + 7 * 18 * 13 = 3744
   * Nr 2 9 * 7 + 18 * 13 = 1053
   * Nr 3 9 + 7 + 18 * 13 = 442
   * Nr 4 9 * 7 * 18 + 13 = 1147
   * Nr 5 9 + 7 * 18 + 13 = 301
   * Nr 6 9 * 7 + 18 + 13 = 94
   * Nr 7 9 + 7 + 18 + 13 = 47
   * 
   * bit_mask_cur_value   7 00000011
   * calc_result          47
   * calc_equation        9 + 7 + 18 + 13
   * 
   * pEquation 21037: 9 7 18 13
   * 
   * 
   * Result  ######### NOT OK #######
   * 
   * 
   * -------------------------------------------------------------------------------------
   * 
   * checkEquation( "161011: 16 10 13" )
   * 
   * Size                  4
   * Symbols needed        2
   * Symbols combinations  4
   * 
   * Nr 0 16 * 10 * 13 = 2080
   * Nr 1 16 + 10 * 13 = 338
   * Nr 2 16 * 10 + 13 = 173
   * Nr 3 16 + 10 + 13 = 39
   * 
   * bit_mask_cur_value   3 00000001
   * calc_result          39
   * calc_equation        16 + 10 + 13
   * 
   * pEquation 161011: 16 10 13
   * 
   * 
   * Result  ######### NOT OK #######
   * 
   * 
   */

  public static void main( String[] args )
  {
    String test_content = "190: 10 19,3267: 81 40 27,83: 17 5,156: 15 6,7290: 6 8 6 15,161011: 16 10 13,192: 17 8 14,21037: 9 7 18 13,292: 11 6 16 20";

    List< String > test_content_list = Arrays.stream( test_content.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    calcGridPart1( test_content_list, true );

    // checkEquationPart1( "21037: 9 7 18 13" );
    //
    // checkEquationPart1( "292: 11 6 16 20" );
    //
    // checkEquationPart1( "3267: 81 40 27" );
    //
    // checkEquationPart1( "10: 1 2 3 4", true );
    //
    checkEquationPart1( "13: 2 3 3 5", true );
    //

    //calcGridPart1( getListProd(), true );
  }

  private static long g_result_value = 0;

  private static long g_result_ok    = 0;

  private static long g_result_err   = 0;

  private static void calcGridPart1( List< String > pList, boolean pKnzDebug )
  {
    g_result_value = 0;
    g_result_ok = 0;
    g_result_err = 0;

    for ( String equation_input_str : pList )
    {
      checkEquationPart1( equation_input_str, pKnzDebug );
    }

    wl( "g_result_value =>" + g_result_value + "<" );
    wl( "g_result_ok    =>" + g_result_ok + "<" );
    wl( "g_result_err   =>" + g_result_err + "<" );
  }

  private static boolean checkEquationPart1( String pEquation, boolean pKnzDebug )
  {
    if ( pEquation != null )
    {
      String[] equation_parts_strings = pEquation.replace( ":", "" ).split( " " );

      long[] equation_parts_values = Arrays.stream( equation_parts_strings ).mapToLong( Long::parseLong ).toArray();

      long symbols_needed = ( equation_parts_values.length - 2 );

      /*
       * Bit-Shift to the right for the power of 2
       */
      long symbols_max = ( 1l << symbols_needed );

      if ( pKnzDebug )
      {
        wl( "" );
        wl( "-------------------------------------------------------------------------------------" );
        wl( "" );
        wl( "checkEquation( \"" + pEquation + "\" )" );
        wl( "" );
        wl( "Size                  " + equation_parts_values.length );
        wl( "Symbols needed        " + symbols_needed );
        wl( "Symbols combinations  " + symbols_max );
        wl( "" );
      }

      String calc_equation = "";

      long calc_result = 0;

      long bit_mask_max_value = symbols_max;
      long bit_mask_cur_value = 0;

      while ( ( bit_mask_cur_value < bit_mask_max_value ) && ( calc_result != equation_parts_values[ 0 ] ) )
      {
        int calc_index = 2;
        long calc_bit_mask = bit_mask_cur_value;

        /*
         * calc_result starts with the value at index-position 1
         */
        calc_result = equation_parts_values[ 1 ];
        calc_equation = "" + equation_parts_values[ 1 ];

        //while ( ( calc_index < longs.length ) && ( calc_result < longs[ 0 ] ) )
        while ( ( calc_index < equation_parts_values.length ) )
        {
          if ( ( calc_bit_mask & 1 ) == 1 )
          {
            calc_result += equation_parts_values[ calc_index ];

            calc_equation += " + " + equation_parts_values[ calc_index ];
          }
          else
          {
            calc_result *= equation_parts_values[ calc_index ];

            calc_equation += " * " + equation_parts_values[ calc_index ];
          }

          calc_bit_mask = calc_bit_mask >> 1;

          calc_index++;
        }

        if ( pKnzDebug )
        {
          wl( "Nr " + bit_mask_cur_value + " " + calc_equation + " = " + calc_result );
        }

        bit_mask_cur_value++;
      }

      bit_mask_cur_value--;

      if ( pKnzDebug )
      {
        wl( "" );
        wl( "bit_mask_cur_value   " + bit_mask_cur_value + " " + getBitPattern( bit_mask_cur_value ) );
        wl( "calc_result          " + calc_result );
        wl( "calc_equation        " + calc_equation );
        wl( "" );
        wl( "pEquation " + pEquation );
        wl( "" );
        wl( "" );
        wl( "Result " + ( ( calc_result == equation_parts_values[ 0 ] ) ? " OK " : " ######### NOT OK #######" ) );
        wl( "" );
        wl( "" );
      }

      if ( calc_result == equation_parts_values[ 0 ] )
      {
        g_result_value += equation_parts_values[ 0 ];
        g_result_ok++;

      }
      else
      {
        g_result_err++;
      }

    }

    return false;
  }


  private static String getBitPattern( long pLong )
  {
    long nummer_x = pLong;

    String bit_string = "";

    for ( int bit = 0; bit < 8; bit++ )
    {
      nummer_x = nummer_x >> 1;

      bit_string = ( ( nummer_x & 1 ) == 1 ? "1" : "0" ) + bit_string;
    }

    return bit_string;
  }

  private static List< String > getListProd()
  {
    int row_count = 0;

    List< String > string_array = new ArrayList< String >();

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day07_input.txt";

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
