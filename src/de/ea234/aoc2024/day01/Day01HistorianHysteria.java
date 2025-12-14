package de.ea234.aoc2024.day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day01HistorianHysteria
{
  /*
   *
   * --- Day 1: Historian Hysteria ---
   * https://adventofcode.com/2024/day/1
   * 
   * Part1 =   1222801
   * Part2 =  22545250
   */

  private static boolean KNZ_DEBUG = false;

  public static void main( String[] args )
  {
    String test_content = " 3   4, 4   3, 2   5, 1   3, 3   9, 3   3";

    List< String > test_content_list = Arrays.stream( test_content.trim().replaceAll( ",[ ]{2,}", "," ).replaceAll( " {2,}", " " ).split( "," ) ).collect( Collectors.toList() );

    KNZ_DEBUG = true;

    calcPart2( test_content_list );

    calcPart2( getListProd() );
  }

  public static void calcPart1( List< String > pList )
  {
    List< Long > int_list_left = new ArrayList< Long >();
    List< Long > int_list_right = new ArrayList< Long >();

    int list_index = 0;

    for ( String input_str : pList )
    {
      wl( list_index + " " + input_str );

      String[] arr_inp = input_str.trim().replaceAll( " {2,}", " " ).split( " " );

      int_list_left.add( Long.valueOf( "" + getLong( arr_inp[ 0 ], 0 ) ) );
      int_list_right.add( Long.valueOf( "" + getLong( arr_inp[ 1 ], 0 ) ) );
    }

    Collections.sort( int_list_left, Collections.reverseOrder() );
    Collections.sort( int_list_right, Collections.reverseOrder() );

    list_index = 0;

    long sum_v = 0;
    long count_values = int_list_left.size();

    while ( list_index < count_values )
    {
      long left_v = int_list_left.get( list_index ).longValue();
      long right_v = int_list_right.get( list_index ).longValue();

      long diff = left_v - right_v;

      if ( diff < 0 )
      {
        diff = right_v - left_v;
      }

      sum_v += diff;

      wl( "list_index " + list_index + " left = " + left_v + " right = " + right_v + " diff " + diff + " sum " + sum_v + "" );

      list_index++;
    }
  }

  public static void calcPart2( List< String > pList )
  {
    List< Long > int_list_left = new ArrayList< Long >();
    List< Long > int_list_right = new ArrayList< Long >();

    int list_index = 0;

    for ( String input_str : pList )
    {
      wl( list_index + " " + input_str );

      String[] arr_inp = input_str.trim().replaceAll( " {2,}", " " ).split( " " );

      int_list_left.add( Long.valueOf( "" + getLong( arr_inp[ 0 ], 0 ) ) );
      int_list_right.add( Long.valueOf( "" + getLong( arr_inp[ 1 ], 0 ) ) );
    }

    HashMap< Long, Long > cache_nr_frequenzy = new HashMap< Long, Long >();

    list_index = 0;

    long count_values = int_list_left.size();

    while ( list_index < count_values )
    {
      Long cache_keykey = int_list_right.get( list_index );

      Long cache_keyvalue = cache_nr_frequenzy.get( cache_keykey );

      if ( cache_keyvalue == null )
      {
        cache_nr_frequenzy.put( cache_keykey, Long.valueOf( "1" ) );
      }
      else
      {
        cache_nr_frequenzy.put( cache_keykey, Long.valueOf( "" + ( cache_keyvalue.longValue() + 1 ) ) );
      }

      list_index++;
    }

    cache_nr_frequenzy.forEach( ( k, v ) -> System.out.println( "Key: " + k + ", Value: " + v ) );

    wl( "" );

    list_index = 0;

    long sum_v = 0;

    while ( list_index < count_values )
    {
      long left_v = int_list_left.get( list_index ).longValue();

      Long cache_keykey = int_list_left.get( list_index );

      Long cache_keyvalue = cache_nr_frequenzy.get( cache_keykey );

      long multiplikator_right_value = 0;

      if ( cache_keyvalue != null )
      {
        multiplikator_right_value = cache_keyvalue.longValue();
      }

      long result_multiplication = left_v * multiplikator_right_value;

      sum_v += result_multiplication;

      wl( "list_index " + list_index + " left = " + left_v + " right freq = " + multiplikator_right_value + " result " + result_multiplication + " sum " + sum_v + "" );

      list_index++;
    }
  }

  private static List< String > getListProd()
  {
    int row_count = 0;

    List< String > string_array = new ArrayList< String >();

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day01_input.txt";

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
    if ( KNZ_DEBUG )
    {
      System.out.println( pString );
    }
  }

  /**
   * <pre>
   * Ermittelt aus dem Parameter "pString" den Longwert.
   * 
   * Kommt es bei der Umwandlung zu einer NumberFormatException,
   * wird der Vorgabewert zurueckgegeben. 
   * 
   * Auf pString wird ein TRIM ausgefuehrt.
   * </pre>
   * 
   * @param pString zu parsende Zeichenkette
   * @param pVorgabeWert Vorgabewert im Fehlerfall
   * @return der Wert als long oder der Vorgabewert
   */
  private static long getLong( String pString, long pVorgabeWert )
  {
    try
    {
      if ( pString != null )
      {
        return Long.parseLong( pString.trim() );
      }
    }
    catch ( NumberFormatException err_inst )
    {
      // keine Fehlerbehandlung, da im Fehlerfall der Vorgabewert zurueckgegeben wird
    }

    return pVorgabeWert;
  }

}
