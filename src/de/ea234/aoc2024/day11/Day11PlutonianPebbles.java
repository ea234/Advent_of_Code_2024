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

  private static void calcPart01( String pInput, boolean pKnzDebug )
  {

  }

  private static String testcns( String pStrOldList, String toexp )
  {
    String resu = calcNewStonesMod( pStrOldList );

    wl( "" );
    wl( "New List " + resu );
    wl( "Expected " + toexp );

    return resu;
  }

  private static int getNr( String pStrOldList, int pCycles )
  {
    String liste_stones = pStrOldList;

    for ( int cnr = 0; cnr < pCycles; cnr++ )
    {
      wl( "cnr " + cnr );
      liste_stones = calcNewStones( liste_stones );
    }

    String[] numbers_old_list = liste_stones.trim().split( " " );

    return numbers_old_list.length;
  }

  private static String calcNewStones( String pStrOldList )
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

      if ( long_array[ index_i ] == 0 )
      {
        str_ergebnis += str_spacer + "1";
      }
      else if ( ( numbers_old_list[ index_i ].length() % 2 ) == 0 )
      {
        int pos_str_mid = numbers_old_list[ index_i ].length() / 2; 

        String firstHalf = numbers_old_list[ index_i ].substring( 0, pos_str_mid );

        String secondHalf = numbers_old_list[ index_i ].substring( pos_str_mid );

        str_ergebnis += str_spacer + Long.parseLong( firstHalf ) + str_spacer + Long.parseLong( secondHalf );
      }
      else
      {
        str_ergebnis += str_spacer + ( long_array[ index_i ] * 2024 );
      }
    }

    return str_ergebnis.trim();
  }

  
  private static String calcNewStonesMod( String pStrOldList )
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

      if ( long_array[ index_i ] == 0 )
      {
        str_ergebnis += str_spacer + "1";
      }
      else if ( ( numbers_old_list[ index_i ].length() % 2 ) == 0 )
      {
        int pos_str_mid = numbers_old_list[ index_i ].length() / 2; 

        String firstHalf = numbers_old_list[ index_i ].substring( 0, pos_str_mid );

        String secondHalf = numbers_old_list[ index_i ].substring( pos_str_mid );

        str_ergebnis += str_spacer + Long.parseLong( firstHalf ) + str_spacer + Long.parseLong( secondHalf );
      }
      else
      {
        str_ergebnis += str_spacer + ( long_array[ index_i ] * 2024 );
      }
    }

    return str_ergebnis.trim();
  }
  
  
  
  
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
