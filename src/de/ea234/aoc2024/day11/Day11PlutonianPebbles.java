package de.ea234.aoc2024.day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import de.ea234.util.FkStringFeld;

public class Day11PlutonianPebbles
{

  /*
   * -- Day 11: Plutonian Pebbles ---
   * https://adventofcode.com/2024/day/11 
   * https://www.youtube.com/watch?v=QI8mfgkOzos
   * 
   * Your puzzle answer was 184927.
   */

  public static void main( String[] args )
  {
    String liste_stones = "125 17";

    liste_stones = testcns( liste_stones, "253000 1 7" );
    liste_stones = testcns( liste_stones, "253 0 2024 14168" );
    liste_stones = testcns( liste_stones, "512072 1 20 24 28676032" );
    liste_stones = testcns( liste_stones, "512 72 2024 2 0 2 4 2867 6032" );
    liste_stones = testcns( liste_stones, "1036288 7 2 20 24 4048 1 4048 8096 28 67 60 32" );
    liste_stones = testcns( liste_stones, "2097446912 14168 4048 2 0 2 4 40 48 2024 40 48 80 96 2 8 6 7 6 0 3 2" );

    liste_stones = "125 17";

   
    wl( "Number of Stones " + getNr( liste_stones, 25 ) );
    
    String list_prd = getStringProd();
    
    wl( "Number of Stones " + getNr( liste_stones, 75 ) );
  }

  private static String testcns( String pStrOldList, String toexp )
  {
    String resu = calcNewStonesRe( pStrOldList );

    wl( "" );
    wl( "New List " + resu );
    wl( "Expected " + toexp );

    return resu;
  }

  
  private static int getNrD( String pStrOldList, int pCycles )
  {
    String liste_stones = pStrOldList;

    for ( int cnr = 0; cnr < pCycles; cnr++ )
    {
      wl( "cnr " + cnr );

      liste_stones = calcNewStonesRe( liste_stones );
      
      wl( liste_stones );
    }

    String[] numbers_old_list = liste_stones.trim().split( " " );

    return numbers_old_list.length;
  }
  
  private static int getNr( String pStrOldList, int pCycles )
  {
    String liste_stones = pStrOldList;

    for ( int cnr = 0; cnr < pCycles; cnr++ )
    {
      wl( "cnr " + cnr );
      liste_stones = calcNewStonesRe( liste_stones );
    }

    String[] numbers_old_list = liste_stones.trim().split( " " );

    return numbers_old_list.length;
  }
  
  private static String calcNewStonesRe( String pStrOldList )
  {
    String[] numbers_old_list = pStrOldList.trim().split( " " );

    long[] long_array = new long[ numbers_old_list.length ];

    for ( int index_i = 0; index_i < numbers_old_list.length; index_i++ )
    {
      long_array[ index_i ] = Long.parseLong( numbers_old_list[ index_i ] );
    }

    String str_ergebnis = "";
    String str_spacer = " ";

    for ( int index_i = 0; index_i < numbers_old_list.length; index_i++ )
    {
      long_array[ index_i ] = Long.parseLong( numbers_old_list[ index_i ] );

      if ( ( numbers_old_list[ index_i ].length() % 2 ) == 0 )
      {
        /*
         * Numbers with an even number of digits
         * 
         * This rule ensures that the numbers are getting smaller
         * 
         * Iteration 1   2048
         * Iteration 2   20 48
         * Iteration 3   2 0 4 8
         * 
         * This rule expands the List by 2.
         */
        
        int pos_str_mid = numbers_old_list[ index_i ].length() / 2;

        String firstHalf = numbers_old_list[ index_i ].substring( 0, pos_str_mid );

        String secondHalf = numbers_old_list[ index_i ].substring( pos_str_mid );

        str_ergebnis += str_spacer + Long.parseLong( firstHalf ) + str_spacer + Long.parseLong( secondHalf );
      }
      else if ( long_array[ index_i ] == 0 )
      {
        /*
         * Number 0
         * 
         * This rule ensures that there are no zeros.
         * It ensures furthermore, that in the next iteration the index will become 2048
         * 
         * Iteration 1   0
         * Iteration 2   1
         * Iteration 3   2048 (... and than the algorithm gets into a period)
         * 
         * This rule doesn't expand the list, it alters the list.
         */
        str_ergebnis += str_spacer + "1";
      }
      else
      {
        /*
         * This rule ensures, that the algorithm will get into a period with multiples of 2048
         */
        str_ergebnis += str_spacer + ( long_array[ index_i ] * 2024 );
      }
    }

    return str_ergebnis.trim();
  }

  private static HashMap< String, Long > m_hm = new HashMap< String, Long >();

  private static long getValue( long pIterationsNr, long pIndex )
  {
    return m_hm.get( "IT" + pIterationsNr + "K" + pIndex ).longValue();
  }

  private static void setValue( long pIterationsNr, long pIndex, long pVal )
  {
    m_hm.put( "IT" + pIterationsNr + "K" + pIndex, Long.valueOf( pVal ) );
  }

  private static void setValue( long pIterationsNr, long pIndex, Long pVal )
  {
    m_hm.put( "IT" + pIterationsNr + "K" + pIndex, pVal );
  }

//  private static long getCM( String pList, int pMaxIteration )
//  {
////    String[] numbers_old_list = pList.trim().split( " " );
//
//    List< Long > long_list2 = Arrays.stream( pList.split( " " ) ).map( String::trim ).filter( s -> !s.isEmpty() ).map( Long::valueOf ).collect( Collectors.toList() );
//
//    int index = 0;
//    for ( Long valuex : long_list2 )
//    {
//      setValue( 1, index, pMaxIteration );
//
//      index++;
//    }
//
//    return 0l;
//  }
//
//  private static int calcNewStonesMod( String pStrOldList, int pIterationNr, int pMaxIteration, long pIndex )
//  {
//    String[] numbers_old_list = pStrOldList.trim().split( " " );
//
//    List< Long > long_list2 = Arrays.stream( pStrOldList.split( " " ) ).map( String::trim ).filter( s -> !s.isEmpty() ).map( Long::valueOf ).collect( Collectors.toList() );
//
//    if ( pIterationNr == pMaxIteration )
//    {
//      return long_list2;
//    }
//
//    for ( int index_i = 0; index_i < numbers_old_list.length; index_i++ )
//    {
//      //long_array[ index_i ] = Long.parseLong( numbers_old_list[ index_i ] );
//
//      if ( long_list2.get( index_i ).longValue() == 0 )
//      {
//        /*
//         * This rule doesnt expand the List.
//         * The Value is changed from 0 to 1.
//         */
//        long_list2.set( index_i, Long.valueOf( 1 ) );
//      }
//      
//      
//      else if ( ( numbers_old_list[ index_i ].length() % 2 ) == 0 )
//      {
//        int pos_str_mid = numbers_old_list[ index_i ].length() / 2;
//
//        String firstHalf = numbers_old_list[ index_i ].substring( 0, pos_str_mid );
//
//        String secondHalf = numbers_old_list[ index_i ].substring( pos_str_mid );
//
//        long firsthalf_l = Long.parseLong( firstHalf );
//        long secondhalf_l = Long.parseLong( secondHalf );
//
//        /*
//         * Replace Value ad index
//         */
//        List< Long > result_rec = calcNewStonesMod( Long.parseLong( firstHalf ) + " " + Long.parseLong( secondHalf ), pIterationNr, pMaxIteration );
//
//        long_list2.addAll( index_i, result_rec );
//        long_list2.remove( index_i );
//      }
//      else
//      {
//        long new_value = ( long_list2.get( index_i ).longValue() * 2024 );
//
//        long_list2.set( index_i, Long.valueOf( new_value ) );
//      }
//    }
//
//    return long_list2;
//  }

  private static String getStringProd()
  {
    int row_count = 0;

    String result = "";

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day11_input.txt";

    try (BufferedReader buffered_reader = new BufferedReader( new FileReader( datei_input ) ))
    {
      String zeile;

      while ( ( zeile = buffered_reader.readLine() ) != null )
      {
        zeile = zeile.trim();

        result += zeile;

        row_count++;
      }
    }
    catch ( IOException err_inst )
    {
      err_inst.printStackTrace();
    }

    return result;
  }

  private static void wl( String pString )
  {
    System.out.println( pString );
  }


}
