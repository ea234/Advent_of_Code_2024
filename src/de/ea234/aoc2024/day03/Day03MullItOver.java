package de.ea234.aoc2024.day03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import de.ea234.util.FkStringText;

public class Day03MullItOver
{

  /*
   * 
   * --- Day 3: Mull It Over ---
   * https://adventofcode.com/2024/day/3
   * 
   */

  public static void main( String[] args )
  {
    String test_content = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))#split#";

    List< String > test_content_list = Arrays.stream( test_content.split( "#split#" ) ).map( String::trim ).collect( Collectors.toList() );

    calcGridPart1( test_content_list, true );

    calcGridPart1( getListProd(), false );
  }

  private static final Pattern PATTERN_MUL_REG_EX   = Pattern.compile( "mul\\((\\d{1,3}),(\\d{1,3})\\)" );

  private static final String  PRAEFIX_DEBUG_STRING = "";

  private static void calcGridPart1( List< String > list_mul, boolean pKnzDebug )
  {
    long result_value_sum = 0;

    for ( String input_str : list_mul )
    {
      long result_value_input_line = calcInput( input_str, pKnzDebug );

      result_value_sum += result_value_input_line;

      wl( "" );
      wl( "------------------------------------------------------------------------------------------" );
      wl( "" );
      wl( PRAEFIX_DEBUG_STRING + FkStringText.getStringMaxCols( input_str, 1000, PRAEFIX_DEBUG_STRING, "\n" ) );
      wl( "" );
      wl( "result_value_input_line " + result_value_input_line );
      wl( "" );
    }

    wl( "" );
    wl( "result_value_sum " + result_value_sum );
    wl( "" );
    wl( "" );
  }

  private static long calcInput( String pInput, boolean pKnzDebug )
  {
    Matcher matcher_reg_ex_pattern = PATTERN_MUL_REG_EX.matcher( pInput );

    List< long[] > list_array_long_values = new ArrayList< long[] >();

    while ( matcher_reg_ex_pattern.find() )
    {
      long value_part_1 = Integer.parseInt( matcher_reg_ex_pattern.group( 1 ) );

      long value_part_2 = Integer.parseInt( matcher_reg_ex_pattern.group( 2 ) );

      list_array_long_values.add( new long[] { value_part_1, value_part_2 } );
    }

    long result_value = 0;

    int mul_nr = 0;

    for ( long[] equation_input_str : list_array_long_values )
    {
      if ( pKnzDebug )
      {
        wl( "  " + mul_nr + " " + equation_input_str[ 0 ] + " " + equation_input_str[ 1 ] + " = " + ( equation_input_str[ 0 ] * equation_input_str[ 1 ] ) );
      }

      result_value += ( equation_input_str[ 0 ] * equation_input_str[ 1 ] );

      mul_nr++;
    }

    return result_value;
  }

  private static List< String > getListProd()
  {
    int row_count = 0;

    List< String > string_array = new ArrayList< String >();

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day03_input.txt";

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
