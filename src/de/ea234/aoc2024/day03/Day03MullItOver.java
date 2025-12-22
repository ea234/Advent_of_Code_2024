package de.ea234.aoc2024.day03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
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
   * ------------------------------------------------------------------------------------------
   * 
   * xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))
   * 
   * Multiplication Nr 1 =  2 * 4 =  8
   * Multiplication Nr 2 =  5 * 5 = 25
   * Multiplication Nr 3 = 11 * 8 = 88
   * Multiplication Nr 4 =  8 * 5 = 40
   * 
   * result_value_input_line 161
   * 
   * 
   * Result Part 1: 161
   * 
   * 
   * ------------------------------------------------------------------------------------------
   * 
   * Found: mul(2,4) at pos 1
   * 
   * DO    At 1 mul(2,4) 2 2 = 8
   * 
   * Found: don't() at pos 20
   * 
   * Found: mul(5,5) at pos 28
   * 
   * DONT    At 28 mul(5,5) 5 5 = 25
   * 
   * Found: mul(11,8) at pos 48
   * 
   * DONT    At 48 mul(11,8) 11 11 = 88
   * 
   * Found: do() at pos 59
   * 
   * Found: mul(8,5) at pos 64
   * 
   * DO    At 64 mul(8,5) 8 8 = 40
   * 
   * DO   result_value_sum_do_instructions   48
   * DONT result_value_sum_dont_instructions 113
   * SUM                                     161
   * 
   * Result Part 1: 161
   * Result Part 2: 48

   * 
   * 
   * ------------------------------------------------------------------------------------------
   * 
   * Realy nice one, once you spottet, that the input lines are not seperated lines.
   * 
   * DO   result_value_sum_do_instructions   76729637
   * DONT result_value_sum_dont_instructions 102065073
   * SUM                                     178794710
   * 
   * Result Part 1 178794710
   * Result Part 2 76729637
   * 
   * 
   */

  private static final Pattern PATTERN_DO_DONT                      = Pattern.compile( "mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don\\'t\\(\\)" );

  private static final Pattern PATTERN_MUL_REG_EX                   = Pattern.compile( "mul\\((\\d{1,3}),(\\d{1,3})\\)" );

  private static final String  PRAEFIX_DEBUG_STRING                 = "";

  private static final int     CMD_DO                               = 1;

  private static final int     CMD_DONT                             = 0;

  private static long          result_value_sum_dont_instructions_g = 0;

  public static void main( String[] args )
  {
    String test_content_part_1 = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))#split#";

    String test_content_part_2 = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))#split#";

    List< String > test_content_list_part_1 = Arrays.stream( test_content_part_1.split( "#split#" ) ).map( String::trim ).collect( Collectors.toList() );

    List< String > test_content_list_part_2 = Arrays.stream( test_content_part_2.split( "#split#" ) ).map( String::trim ).collect( Collectors.toList() );

    //calcInputPart1( test_content_list_part_1, true );
    //
    //calcInputPart1( getListProd(), true );
    //
    //calcInputPart2( test_content_list_part_2, true );
    //
    calcInputPart2( getListProd(), true );
  }

  private static void calcInputPart1( List< String > pListInput, boolean pKnzDebug )
  {
    long result_value_sum = 0;

    for ( String input_str : pListInput )
    {
      if ( pKnzDebug )
      {
        wl( "" );
        wl( "------------------------------------------------------------------------------------------" );
        wl( "" );
        wl( PRAEFIX_DEBUG_STRING + FkStringText.getStringMaxCols( input_str, 1000, PRAEFIX_DEBUG_STRING, "\n" ) );
        wl( "" );
      }

      long result_value_input_line = calcInputLinePart1( input_str, "", pKnzDebug );

      result_value_sum += result_value_input_line;

      if ( pKnzDebug )
      {
        wl( "" );
        wl( "result_value_input_line " + result_value_input_line );
        wl( "" );
      }
    }

    wl( "" );
    wl( "Result Part 1: " + result_value_sum );
    wl( "" );
    wl( "" );
  }

  private static long calcInputLinePart1( String pInput, String pDebugPraefix, boolean pKnzDebug )
  {
    Matcher matcher_reg_ex_pattern = PATTERN_MUL_REG_EX.matcher( pInput );

    int mul_nr = 0;

    long result_value = 0;

    while ( matcher_reg_ex_pattern.find() )
    {
      long value_part_1 = Long.parseLong( matcher_reg_ex_pattern.group( 1 ) );

      long value_part_2 = Long.parseLong( matcher_reg_ex_pattern.group( 2 ) );

      if ( pKnzDebug )
      {
        mul_nr++;

        wl( "Multiplication Nr " + mul_nr + " = " + value_part_1 + " * " + value_part_2 + " = " + ( value_part_1 * value_part_2 ) );
      }

      result_value += ( value_part_1 * value_part_2 );
    }

    return result_value;
  }

  private static void calcInputPart2( List< String > pListInput, boolean pKnzDebug )
  {
    result_value_sum_dont_instructions_g = 0;

    long result_value_sum_do_instructions = 0;

    /*
     * That was fun to realise. (4 hrs)
     *
     * Soundtrack by Stevie Wonder: "I just call to say I hate you"
     */
    String my_fking_input = "";

    for ( String input_str : pListInput )
    {
      my_fking_input += input_str;
    }

    long result_value_input_line = calcInputLinePart2( my_fking_input, pKnzDebug );

    result_value_sum_do_instructions += result_value_input_line;

    wl( "" );
    wl( "" );
    wl( "DO   result_value_sum_do_instructions   " + result_value_sum_do_instructions );
    wl( "DONT result_value_sum_dont_instructions " + result_value_sum_dont_instructions_g );
    wl( "SUM                                     " + ( result_value_sum_do_instructions + result_value_sum_dont_instructions_g ) );
    wl( "" );
    wl( "Result Part 1: " + ( result_value_sum_do_instructions + result_value_sum_dont_instructions_g ) );
    wl( "Result Part 2: " + result_value_sum_do_instructions );
    wl( "" );
    wl( "" );
  }

  private static long calcInputLinePart2( String input, boolean pKnzDebug )
  {
    Matcher matcher_do_dont = PATTERN_DO_DONT.matcher( input );

    long result_value_sum_do_instructions = 0;
    long result_value_sum_dont_instructions = 0;

    /*
     * At the beginning mul are enabled
     */
    int current_cmd = CMD_DO;

    String debug_praefix = "DO   ";

    while ( matcher_do_dont.find() )
    {
      if ( pKnzDebug )
      {
        wl( "" );
        wl( "----------------------------------------------------" );
        wl( "Found: " + matcher_do_dont.group() + " at pos " + matcher_do_dont.start() );
        wl( "" );
      }

      if ( matcher_do_dont.group().equals( "don't()" ) )
      {
        current_cmd = CMD_DONT;
      }
      else if ( matcher_do_dont.group().equals( "do()" ) )
      {
        current_cmd = CMD_DO;
      }
      else // if ( matcher_do_dont.group().equals( "mul(x,y)" ) )
      {
        long value_part_1 = Long.parseLong( matcher_do_dont.group( 1 ) );

        long value_part_2 = Long.parseLong( matcher_do_dont.group( 2 ) );

        long value_current_result = value_part_1 * value_part_2;

        if ( current_cmd == CMD_DO )
        {
          result_value_sum_do_instructions += value_current_result;

          debug_praefix = "DO   ";
        }
        else if ( current_cmd == CMD_DONT )
        {
          result_value_sum_dont_instructions += value_current_result;

          debug_praefix = "DONT   ";
        }

        if ( pKnzDebug )
        {
          wl( debug_praefix + " At " + matcher_do_dont.start() + " " + matcher_do_dont.group() + " " + value_part_1 + " " + value_part_1 + " = " + value_current_result );
        }
      }
    }

    if ( pKnzDebug )
    {
      wl( "" );
      wl( "DO   result_value_sum_do_instructions   " + result_value_sum_do_instructions );
      wl( "DONT result_value_sum_dont_instructions " + result_value_sum_dont_instructions );
      wl( "" );
      wl( "SUM                                     " + ( result_value_sum_do_instructions + result_value_sum_dont_instructions ) );
      wl( "" );
      wl( "" );
    }

    result_value_sum_dont_instructions_g += result_value_sum_dont_instructions;

    return result_value_sum_do_instructions;
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
