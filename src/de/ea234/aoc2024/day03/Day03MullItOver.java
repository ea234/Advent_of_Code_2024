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

   *
   * ------------------------------------------------------------------------------------------
   * 
   * xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))
   * 
   * 
   * ----------------------------------------------------
   * Found: don't() at pos 20
   * 
   * DO  
   * DO     From 0 To 20  20 Characters
   * DO  
   * DO     xmul(2,4)&mul[3,7]!^
   *   0 2 4 = 8
   * 
   * ----------------------------------------------------
   * Found: do() at pos 59
   * 
   * DONT
   * DONT   From 20 To 59  39 Characters
   * DONT
   * DONT   don't()_mul(5,5)+mul(32,64](mul(11,8)un
   * 
   * ----------------------------------------------------
   * DO  
   * DO   From 59 To String End 73  14 Characters
   * DO  
   * DO     do()?mul(8,5))
   *   0 8 5 = 40
   * 
   * result_value_sum 48
   * 
   * 
   * result_value_input_line 48
   * 
   * 
   * calc modi 1 result_value_sum 48

   * 
   * 
   * 
   * 
   */

  private static final int CMD_DO                          = 1;

  private static final int CMD_DONT                        = 0;

  private static final int CALC_ALL_MUL_INSTRUCTIONS       = 1;

  private static final int CALC_ONLY_FIRST_MUL_INSTRUCTION = 0;

  public static void main( String[] args )
  {
    String test_content_part_1 = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))#split#";

    String test_content_part_2 = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))#split#";

    List< String > test_content_list_part_1 = Arrays.stream( test_content_part_1.split( "#split#" ) ).map( String::trim ).collect( Collectors.toList() );

    List< String > test_content_list_part_2 = Arrays.stream( test_content_part_2.split( "#split#" ) ).map( String::trim ).collect( Collectors.toList() );

    calcInputPart2( test_content_list_part_2, CALC_ALL_MUL_INSTRUCTIONS, true );

    calcInputPart2( getListProd(), CALC_ALL_MUL_INSTRUCTIONS, true );

//    calcInputLinePart2a( test_content_part_2, true );
  }

  /*
   *  Muster, das entweder "do()" oder "don't()" findet
   */
  private static final Pattern PATTERN_DO_DONT      = Pattern.compile( "do\\(\\)|don\\'t\\(\\)" );

  private static final Pattern PATTERN_MUL_REG_EX   = Pattern.compile( "mul\\((\\d{1,3}),(\\d{1,3})\\)" );

  private static final String  PRAEFIX_DEBUG_STRING = "";

  private static void calcInputPart1( List< String > list_mul, boolean pKnzDebug )
  {
    long result_value_sum = 0;

    for ( String input_str : list_mul )
    {
      long result_value_input_line = calcInputLinePart1( input_str, pKnzDebug );

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

  private static long calcInputLinePart1( String pInput, boolean pKnzDebug )
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

  private static void calcInputPart2( List< String > list_mul, int pCalcModi, boolean pKnzDebug )
  {
    long result_value_sum = 0;

    for ( String input_str : list_mul )
    {
      wl( "" );
      wl( "------------------------------------------------------------------------------------------" );
      wl( "" );
      wl( PRAEFIX_DEBUG_STRING + FkStringText.getStringMaxCols( input_str, 1000, PRAEFIX_DEBUG_STRING, "\n" ) );
      wl( "" );
      
      long result_value_input_line = calcInputLinePart2a( input_str, pCalcModi, pKnzDebug );

      result_value_sum += result_value_input_line;

      wl( "result_value_input_line " + result_value_input_line );
      wl( "" );
    }

    wl( "" );
    wl( "calc modi " + pCalcModi + " result_value_sum " + result_value_sum );
    wl( "" );
    wl( "" );
  }

  private static long calcInputLinePart2a( String input, int pCalcModi, boolean pKnzDebug )
  {
    Matcher matcher_do_dont = PATTERN_DO_DONT.matcher( input );

    long result_value_sum_do_instructions = 0;

    /*
     * At the beginning mul are enabled
     */
    int last_find_start_index = 0;
    int last_find_cmd = CMD_DO;

    while ( matcher_do_dont.find() )
    {
      if ( pKnzDebug )
      {
        wl( "" );
        wl( "----------------------------------------------------" );
        wl( "Found: " + matcher_do_dont.group() + " at pos " + matcher_do_dont.start() );
        wl( "" );
      }

      if ( last_find_start_index != -1 )
      {
        if ( last_find_cmd == CMD_DO )
        {
          if ( pKnzDebug )
          {
            wl( "DO  " );
            wl( "DO     From " + last_find_start_index + " To " + matcher_do_dont.start() + "  " + ( matcher_do_dont.start() - last_find_start_index ) + " Characters" );
            wl( "DO  " );
            wl( "DO     " + input.substring( last_find_start_index, matcher_do_dont.start() ) );
          }

          long result_value_input_line = calcInputLinePart2b( input.substring( last_find_start_index, matcher_do_dont.start() + 3 ), pCalcModi, true );

          result_value_sum_do_instructions += result_value_input_line;
        }
        else
        {
          if ( pKnzDebug )
          {
            wl( "DONT" );
            wl( "DONT   From " + last_find_start_index + " To " + matcher_do_dont.start() + "  " + ( matcher_do_dont.start() - last_find_start_index ) + " Characters" );
            wl( "DONT" );
            wl( "DONT   " + input.substring( last_find_start_index, matcher_do_dont.start() ) );
          }
        }

      }

      last_find_start_index = matcher_do_dont.start();

      last_find_cmd = ( matcher_do_dont.group().equalsIgnoreCase( "do()" ) ? CMD_DO : CMD_DONT );
    }

    if ( last_find_start_index != -1 )
    {
      wl( "" );
      wl( "----------------------------------------------------" );

      if ( last_find_cmd == CMD_DO )
      {
        if ( pKnzDebug )
        {
          wl( "DO  " );
          wl( "DO   From " + last_find_start_index + " To String End " + input.length() + "  " + ( input.length() - last_find_start_index ) + " Characters" );
          wl( "DO  " );
          wl( "DO     " + input.substring( last_find_start_index ) );
        }

        long result_value_input_line = calcInputLinePart2b( input.substring( last_find_start_index ), pCalcModi, true );

        result_value_sum_do_instructions += result_value_input_line;
      }
      else
      {
        if ( pKnzDebug )
        {
          wl( "DONT" );
          wl( "DONT   " + input.substring( last_find_start_index ) );
        }
      }
    }

    if ( pKnzDebug )
    {
      wl( "" );
      wl( "result_value_sum " + result_value_sum_do_instructions );
      wl( "" );
      wl( "" );
    }
    /*
     * 7410217
     */

    return result_value_sum_do_instructions;
  }

  private static long calcInputLinePart2b( String pInput, int pCalcModi, boolean pKnzDebug )
  {
    if ( pCalcModi == CALC_ALL_MUL_INSTRUCTIONS )
    {
      return calcInputLinePart1( pInput, pKnzDebug );
    }

    Matcher matcher_reg_ex_pattern = PATTERN_MUL_REG_EX.matcher( pInput );

    if ( matcher_reg_ex_pattern.find() )
    {
      long value_part_1 = Integer.parseInt( matcher_reg_ex_pattern.group( 1 ) );

      long value_part_2 = Integer.parseInt( matcher_reg_ex_pattern.group( 2 ) );

      if ( pKnzDebug )
      {
        wl( "DO   First Match " + matcher_reg_ex_pattern.start() + " " + value_part_1 + " " + value_part_1 + " = " + ( value_part_1 * value_part_2 ) );
      }

      return value_part_1 * value_part_2;
    }
    
    wl( "DO #########################" );

    return 0;
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
