package de.ea234.aoc2024.day13;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day13ClawContraption
{
  /*
   * --- Day 13: Claw Contraption ---
   * https://adventofcode.com/2024/day/13 
   * 
   * https://www.youtube.com/watch?v=mgIgBJFdwf4
   *
   * https://www.reddit.com/r/adventofcode/comments/1hd4wda/2024_day_13_solutions/
   * 
   * 
   * Your puzzle answer was 27157.
   * 
   *  A     80  | cx   7520 | cy   2720 | dx    880 | dy   2680
   *  B     40  | cx    880 | cy   2680 | dx   7520 | dy   2720
   * 
   * 
   * ------------------------------------------------------
   * pButtonA Button A: X+94, Y+34
   * pButtonB Button B: X+22, Y+67
   * pPrize   Prize: X=8400, Y=5400
   * 
   * DBG_A w     40  80 880_2680
   * DBG_A w     40  | cx    880 | cy   2680 | dx   7520 | dy   2720     80 880_2680
   * 
   *  min_cost_in_token 280
   * 
   * 
   * ------------------------------------------------------
   * pButtonA Button A: X+26, Y+66
   * pButtonB Button B: X+67, Y+21
   * pPrize   Prize: X=12748, Y=12176
   * 
   * 
   *  min_cost_in_token 0
   * 
   * 
   * ------------------------------------------------------
   * pButtonA Button A: X+17, Y+86
   * pButtonB Button B: X+84, Y+37
   * pPrize   Prize: X=7870, Y=6450
   * 
   * DBG_A w     86  38 7224_3182
   * DBG_A w     86  | cx   7224 | cy   3182 | dx    646 | dy   3268     38 7224_3182
   * 
   *  min_cost_in_token 200
   * 
   * 
   * ------------------------------------------------------
   * pButtonA Button A: X+69, Y+23
   * pButtonB Button B: X+27, Y+71
   * pPrize   Prize: X=18641, Y=10279
   * 
   * 
   *  min_cost_in_token 0
   * 
   * price_in_token 480
   */

  private static final long       TOKEN_PRICE_BUTTON_A = 3;

  private static final long       TOKEN_PRICE_BUTTON_B = 1;

  //private static final BigDecimal ADD_COORDS_PRIZE_X = new BigDecimal( "10000000000000" );
  //private static final BigDecimal ADD_COORDS_PRIZE_X = new BigDecimal( "10000000" ); // Moment of slowing down
  private static final BigDecimal ADD_COORDS_PRIZE_X = new BigDecimal( "100000000" );

  private static final BigDecimal ADD_COORDS_PRIZE_0 = new BigDecimal( "0" );

  public static void main( String[] args )
  {
    String test_1 = "Button A: X+94, Y+34;Button B: X+22, Y+67;Prize: X=8400, Y=5400;;Button A: X+26, Y+66;Button B: X+67, Y+21;Prize: X=12748, Y=12176;;Button A: X+17, Y+86;Button B: X+84, Y+37;Prize: X=7870, Y=6450;;Button A: X+69, Y+23;Button B: X+27, Y+71;Prize: X=18641, Y=10279;";

    calculatePart01( test_1, ADD_COORDS_PRIZE_0, true );

    //calculatePart01( getListProd(),ADD_COORDS_PRIZE_0, true );

    //calculatePart01( test_1, ADD_COORDS_PRIZE_X, true );
  }

  private static void calculatePart01( String pString, BigDecimal pAddToPriceCoord, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( ";" ) ).map( String::trim ).collect( Collectors.toList() );

    calculatePart01( converted_string_list, pAddToPriceCoord, pKnzDebug );
  }

  private static void calculatePart01( List< String > pList, BigDecimal pAddToPriceCoord, boolean pKnzDebug )
  {
    String button_a = null;
    String button_b = null;
    String prize = null;
    int count_input_items = 0;

    int price_in_token = 0;

    for ( String akt_string : pList )
    {
      //wl( akt_string );

      if ( akt_string.isBlank() )
      {
        button_a = null;
        button_b = null;
        prize = null;
        count_input_items = 0;
      }
      else if ( akt_string.indexOf( "Button A:" ) >= 0 )
      {
        button_a = akt_string;

        count_input_items++;
      }
      else if ( akt_string.indexOf( "Button B:" ) >= 0 )
      {
        button_b = akt_string;

        count_input_items++;
      }
      else if ( akt_string.indexOf( "Prize:" ) >= 0 )
      {
        prize = akt_string;

        count_input_items++;
      }

      if ( count_input_items == 3 )
      {
        price_in_token += doCalcClawMachine( button_a, button_b, prize, pAddToPriceCoord, pKnzDebug );

        button_a = null;
        button_b = null;
        prize = null;
        count_input_items = 0;
      }
    }

    wl( "" );
    wl( "price_in_token " + price_in_token );
  }

  private static long doCalcClawMachine( String pButtonA, String pButtonB, String pPrize, BigDecimal pAddToPriceCoord, boolean pKnzDebug )
  {
    if ( pKnzDebug )
    {
      wl( "" );
      wl( "------------------------------------------------------" );
      wl( "pButtonA " + pButtonA );
      wl( "pButtonB " + pButtonB );
      wl( "pPrize   " + pPrize );
      wl( "" );
    }

    /*
     * *******************************************************************************************************
     * Reading the prize coordinates
     * *******************************************************************************************************
     */

    Pattern reg_ex_pattern = Pattern.compile( "X=(\\d+).+Y=(\\d+)" );

    Matcher reg_ex_matcher = reg_ex_pattern.matcher( pPrize );

    BigDecimal prize_x = null;
    BigDecimal prize_y = null;

    if ( reg_ex_matcher.find() )
    {
      prize_x = BigDecimal.valueOf( Integer.parseInt( reg_ex_matcher.group( 1 ) ) ).add( pAddToPriceCoord );

      prize_y = BigDecimal.valueOf( Integer.parseInt( reg_ex_matcher.group( 2 ) ) ).add( pAddToPriceCoord );
    }

    /*
     * *******************************************************************************************************
     * Reading delta x and delta y for button a
     * *******************************************************************************************************
     */

    String[] parts_button_a = pButtonA.split( ":" )[ 1 ].trim().split( "," );

    BigDecimal button_a_delta_x = BigDecimal.valueOf( Integer.parseInt( parts_button_a[ 0 ].split( "\\+" )[ 1 ].trim() ) );

    BigDecimal button_a_delta_y = BigDecimal.valueOf( Integer.parseInt( parts_button_a[ 1 ].split( "\\+" )[ 1 ].trim() ) );

    /*
     * *******************************************************************************************************
     * Reading delta x and delta y for button b
     * *******************************************************************************************************
     */

    String[] parts_button_b = pButtonB.split( ":" )[ 1 ].trim().split( "," );

    BigDecimal button_b_delta_x = BigDecimal.valueOf( Integer.parseInt( parts_button_b[ 0 ].split( "\\+" )[ 1 ].trim() ) );

    BigDecimal button_b_delta_y = BigDecimal.valueOf( Integer.parseInt( parts_button_b[ 1 ].split( "\\+" )[ 1 ].trim() ) );

    /*
     * *******************************************************************************************************
     * Creating a hashmap with the diff values to the prize for button a
     * *******************************************************************************************************
     */

    HashMap< String, Long > hash_map_button_a_diffs_to_prize = new HashMap< String, Long >();

    BigDecimal diff_x = BigDecimal.valueOf( 0 );
    BigDecimal diff_y = BigDecimal.valueOf( 0 );

    BigDecimal cur_x = BigDecimal.valueOf( 0 );
    BigDecimal cur_y = BigDecimal.valueOf( 0 );

    long button_push = 0;

    while ( cur_x.compareTo( prize_x ) <= 0 && cur_y.compareTo( prize_y ) <= 0 )
    {
      button_push++;

      cur_x = cur_x.add( button_a_delta_x );
      cur_y = cur_y.add( button_a_delta_y );

      diff_x = prize_x.subtract( cur_x );
      diff_y = prize_y.subtract( cur_y );

      String key_diff_to_prize_coordinates = diff_x.toPlainString() + "_" + diff_y.toPlainString();

      hash_map_button_a_diffs_to_prize.put( key_diff_to_prize_coordinates, Long.valueOf( button_push ) );
    }

    /*
     * *******************************************************************************************************
     * Calculating button presses b and check wether there is a diff-Key for button a
     * *******************************************************************************************************
     */ 

    cur_x = BigDecimal.valueOf( 0 );
    cur_y = BigDecimal.valueOf( 0 );

    button_push = 0;

    long min_button_presses = Integer.MAX_VALUE;
    long min_cost_in_token = 0;

    while ( cur_x.compareTo( prize_x ) <= 0 && cur_y.compareTo( prize_y ) <= 0 )
    {
      button_push++;

      cur_x = cur_x.add( button_b_delta_x );
      cur_y = cur_y.add( button_b_delta_y );

      diff_x = prize_x.subtract( cur_x );
      diff_y = prize_y.subtract( cur_y );

      String key_current_xy_coords = cur_x + "_" + cur_y;

      if ( hash_map_button_a_diffs_to_prize.containsKey( key_current_xy_coords ) )
      {
        long button_a_presses = hash_map_button_a_diffs_to_prize.get( key_current_xy_coords ).longValue();

        if ( ( button_push + button_a_presses ) < min_button_presses )
        {
          min_button_presses = button_push + button_a_presses;

          min_cost_in_token = ( button_push * TOKEN_PRICE_BUTTON_B ) + ( button_a_presses * TOKEN_PRICE_BUTTON_A );
        }

        wl( "DBG_A " + String.format( "%-3s %4d  | cx %6s | cy %6s | dx %6s | dy %6s     " + button_a_presses + " " + key_current_xy_coords, "w", button_push, cur_x.toPlainString(), cur_y.toPlainString(), diff_x.toPlainString(), diff_y.toPlainString() ) );
      }
    }

    if ( pKnzDebug )
    {
      wl( "" );
      wl( " min_cost_in_token " + min_cost_in_token );
      wl( "" );
    }

    return min_cost_in_token;
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day13_input.txt";

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
