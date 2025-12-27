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
import de.ea234.util.FkStringText;

public class Day11PlutonianPebbles
{

  /*
   * -- Day 11: Plutonian Pebbles ---
   * https://adventofcode.com/2024/day/11 
   * https://www.youtube.com/watch?v=QI8mfgkOzos
   * 
   */

  private static final String STR_SPACE = " ";

  public static void main( String[] args )
  {
    //String list_stones = "125 17";
    //list_stones = testCalcStoneList( list_stones, "253000 1 7" );
    //list_stones = testCalcStoneList( list_stones, "253 0 2024 14168" );
    //list_stones = testCalcStoneList( list_stones, "512072 1 20 24 28676032" );
    //list_stones = testCalcStoneList( list_stones, "512 72 2024 2 0 2 4 2867 6032" );
    //list_stones = testCalcStoneList( list_stones, "1036288 7 2 20 24 4048 1 4048 8096 28 67 60 32" );
    //list_stones = testCalcStoneList( list_stones, "2097446912 14168 4048 2 0 2 4 40 48 2024 40 48 80 96 2 8 6 7 6 0 3 2" );

    wl( "Number of Stones " + calcPart2( "1", 20 ) );
  }

  /*
   * Doing some commentry in German:
   * 
   * Gedanken, wie man die Iterationsschritte runterbrechen kann. 
   * Ausgehend davon, dass der Algorithmus immer wieder auf einstellige Zahlen hinausläuft.
   * Diese Einstelligen Zahlen ergeben immer wieder die gleiche Zahlenfolge.
   * Eine einzelne Zahl hat keinen Einfluss auf die anderen Zahlen in der Liste.
   * 
   * Das Problem ist aktuell:
   * - Wie kann ich die HashMap am besten bestücken, so dass die gespeicherte Information 
   *   zielbringend genutzt werden kann.
   *   
   * - Wo hört meine rekursive Suche auf:
   *   - Bei einstelligen Zahlen, welche noch nicht in der Hashmap gespeichert worden sind.
   *   - Wie erstelle ich die rekusive suche am besten:
   *      
   *          Eventuell in 2 Funktionen aufspalten
   *          - Die Split-Regel beruht auf String-Vergleich
   *          - Die anderen beiden auf zahlen
   * 
   * Problem sind die Folgelemente nach x iterationen
   * 
   * - Wie viele Elemente bekomme ich nur durch Regel 2
   * 
   * - Wie viele iterationen benötige ich, um eine gegebene Zahl auf 
   *   nur Cache-Hits runter zu bringen.
   *   
   *   Elemente welche nur 1 stellig oder aus einer graden anzahl von 
   *   Elementen besteht.
   *   
   *   Die 1 stelligen Elemente ergeben immer ein Vielfaches von 2048.
   *   
   * Liste durch manipulation eines Arrays erstellen
   * FALSCH - Es muss irgendwie berechenbar sein.
   *        - Eine Arrayliste wird nachher auch eine große menge von Objekten enthalten
   *   
   *   
   * Eingabe            1
   * A nr   1 cnt   1 | 2024
   * A nr   2 cnt   2 | 20 24
   * A nr   3 cnt   4 | 2 0 2 4
   * A nr   4 cnt   4 | 4048 1 4048 8096
   * A nr   5 cnt   7 | 40 48 2024 40 48 80 96
   * A nr   6 cnt  14 | 4 0 4 8 20 24 4 0 4 8 8 0 9 6
   * A nr   7 cnt  16 | 8096 1 8096 16192 2 0 2 4 8096 1 8096 16192 16192 1 18216 12144
   * A nr   8 cnt  20 | 80 96 2024 80 96 32772608 4048 1 4048 8096 80 96 2024 80 96 32772608 32772608 2024 36869184 24579456
   *   
   * Bei einer Eingabe von 1 habe ich nach
   *  
   * Iteration 1 = 1 Wert  (Liste wurde nicht verlängert)
   * Iteration 2 = 2 Werte (Grade Zahl wurde geteilt)
   * Iteration 3 = 4 Werte (Grade Zahl wurde geteilt)
   * Iteration 5 = 4 Werte (0 wurde durch 1 ersetzt, die anderen Zahlen mit 2024 multipliziert)
   * 
   * Nach 3 Iterationen ist aus einer 4stelligen Zahl eine Zahlenfolge von 1stelligen Zahlen geworden.
   * Diese einstelligen Zahlen multiplizieren sich im nächsten Schritt mal 4
   *  
   * Nach 5 Iterationen kommt der Algorithmus wieder an die Stelle, von welcher sich wieder 
   * vielfache von 2048 ergeben.
   * 
   * Bei Iteration 6 sind nur ein- und zweistellige Zahlen in der Liste vorhanden.
   * Dort könnte die Iteration abbrechen, wenn die Anzahl der sich ergebenden Elemente
   * für X-weitere Schritte bekannt wäre.
   * 
   * 
   * 
   * 
   * Wie viele Elemente erzeugt eine Zahl nach x schritten
   * Habe ich eine 1, habe ich nach 6 Schritten 14 Elemente.
   * 
   * Treffe ich bei Iteration 30 auf die Zahl 1 in der Eingabe,
   * wird sich die Stelle in 6 weiteren Schritten bei Iteration 36
   * auf 14 Elemente erweitert haben.
   * 
   * Ich muss aus dem Cache den Elementwert nach x Schritten berechnen können.
   * 
   * int iteration_nr_current = 30
   * int iteration_nr_max     = 75
   * 
   * int number_from_list = 1
   * 
   * int iteration_cycles_left = iteration_nr_max - iteration_nr_current
   * 
   * int number_of_elements = getCacheValue( number_from_list, iteration_cycles_left )
   * 
   * if ( number_of_elements < 0 ) // cache miss
   * 
   *   String new_list = "" + number_from_list
   *   
   *   number_of_element = calcNumberOfElements( new_list, 25 ); // 25 cycles more for the cache 
   * 
   * int elements_result += number_of_elements
   * 
   * 
   *  
   * 
   * jede zahl, welche nicht einstellig ist, erzeugt einen eigenen iterationszweig bei 0
   * der iterationszweig endet bei aktueller iteration + 3
   * 
   * map key = eingabe + nr : value = zahl der Elemente
   * 
   * ist die Eingabe vorhanden 
   * 
   * key = pStrListInput + pIterationsNr
   * 
   * value = prop.get( key )
   * 
   * if ( value == null ) 
   *    
   *    berechne den schlüssel
   *    
   * anzahl_elemente = anzahl_elemente + value
   */

  private static Properties m_hash_map = null;

  private static Properties getHashMap()
  {
    if ( m_hash_map == null )
    {
      m_hash_map = new Properties();
    }

    return m_hash_map;
  }

  private static void setHashMapElementCount( String pListInput, long pIterationNr, long pElementCount )
  {
    getHashMap().setProperty( pListInput + "_" + pIterationNr, "" + pElementCount );
  }

  private static long getHashMapElementCount( String pListInput, long pIterationNr )
  {
    String hash_map_element_count_str = getHashMap().getProperty( pListInput + "_" + pIterationNr );

    if ( hash_map_element_count_str != null )
    {
      return Integer.parseInt( hash_map_element_count_str );
    }

    return -1;
  }

  private static long getElementCountFromList( String pListInput )
  {
    String[] numbers_old_list = pListInput.trim().split( " " );

    return numbers_old_list.length;
  }

  private static long calcPart2( String pListInput, int pNrOfIterations )
  {
    String list_stones_input = pListInput;
    String list_stones_new = pListInput;

    for ( long iteration_nr = 0; iteration_nr < pNrOfIterations; iteration_nr++ )
    {
      long hash_map_element_count_long = getHashMapElementCount( list_stones_input, iteration_nr );

      if ( hash_map_element_count_long < 0 )
      {
        list_stones_new = calcNewList( list_stones_input );

        hash_map_element_count_long = getElementCountFromList( list_stones_new );

        setHashMapElementCount( list_stones_input, iteration_nr, hash_map_element_count_long );

        wl( "\n\nA nr " + FkStringFeld.getFeldRechtsMin( iteration_nr, 3 ) + " cnt " + FkStringFeld.getFeldRechtsMin( hash_map_element_count_long, 3 ) + " | " + FkStringText.getStringMaxCols( list_stones_new, 1000, "              | ", "\n" ) );
      }
      else
      {
        wl( "\n\nB nr " + FkStringFeld.getFeldRechtsMin( iteration_nr, 3 ) + " cnt " + FkStringFeld.getFeldRechtsMin( hash_map_element_count_long, 3 ) + " | " + FkStringText.getStringMaxCols( list_stones_new, 1000, "              | ", "\n" ) );
      }

      list_stones_input = list_stones_new;
    }

    return getElementCountFromList( list_stones_new ); // Anzahl der letzten liste
  }

  private static long calcPart2A( String pListInput, int pNrOfIterations )
  {
    String list_stones_input = pListInput;
    String list_stones_new = pListInput;

    for ( long iteration_nr = 0; iteration_nr < pNrOfIterations; iteration_nr++ )
    {
      long hash_map_element_count_long = getHashMapElementCount( list_stones_input, iteration_nr );

      if ( hash_map_element_count_long < 0 )
      {
        list_stones_new = calcNewList( list_stones_input );

        hash_map_element_count_long = getElementCountFromList( list_stones_new );

        setHashMapElementCount( list_stones_input, iteration_nr, hash_map_element_count_long );
      }

      wl( "\n\nnr " + FkStringFeld.getFeldRechtsMin( iteration_nr, 3 ) + "  | " + FkStringText.getStringMaxCols( list_stones_new, 1000, "        | ", "\n" ) );
    }

    return getElementCountFromList( list_stones_new ); // Anzahl der letzten liste
  }


  private static long calcNewListX( String pListInput, long pMaxIteration )
  {
    String[] list_stones_string = pListInput.trim().split( STR_SPACE );

    long element_count = 0;

    long iteration_nr_max = pMaxIteration;

    for ( long iteration_nr_current = 0; iteration_nr_current < iteration_nr_max; iteration_nr_current++ )
    {
      long iteration_cycles_left = iteration_nr_max - iteration_nr_current;

      for ( String current_number : list_stones_string )
      {
        long hash_map_element_count_long = getHashMapElementCount( current_number, iteration_cycles_left );

        if ( hash_map_element_count_long < 0 )
        {
          String list_stones_new = calcNewList( current_number );

          hash_map_element_count_long = getElementCountFromList( list_stones_new );

          setHashMapElementCount( current_number, iteration_nr_current, hash_map_element_count_long );

          element_count += hash_map_element_count_long;
        }
      }
    }

    return element_count;
  }

  private static String calcNewList( String pListInput )
  {
    String[] list_stones_string = pListInput.trim().split( STR_SPACE );

    long[] list_stones_long = new long[ list_stones_string.length ];

    for ( int index_i = 0; index_i < list_stones_string.length; index_i++ )
    {
      list_stones_long[ index_i ] = Long.parseLong( list_stones_string[ index_i ] );
    }

    String str_ergebnis = "";

    for ( int index_i = 0; index_i < list_stones_string.length; index_i++ )
    {
      list_stones_long[ index_i ] = Long.parseLong( list_stones_string[ index_i ] );

      if ( ( list_stones_string[ index_i ].length() % 2 ) == 0 )
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

        int pos_str_mid = list_stones_string[ index_i ].length() / 2;

        String first_part = list_stones_string[ index_i ].substring( 0, pos_str_mid );

        String second_part = list_stones_string[ index_i ].substring( pos_str_mid );

        str_ergebnis += STR_SPACE + Long.parseLong( first_part ) + STR_SPACE + Long.parseLong( second_part );
      }
      else if ( list_stones_long[ index_i ] == 0 )
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
        str_ergebnis += STR_SPACE + "1";
      }
      else
      {
        /*
         * This rule ensures, that the algorithm will get into a period with multiples of 2048
         */
        str_ergebnis += STR_SPACE + ( list_stones_long[ index_i ] * 2024 );
      }
    }

    return str_ergebnis.trim();
  }

  private static String testCalcStoneList( String pListInput, String pListExpected )
  {
    String list_stones_new = calcNewStoneListPart1( pListInput );

    wl( "" );
    wl( "New List " + list_stones_new );
    wl( "Expected " + pListExpected );

    return list_stones_new;
  }

  private static long calcPart1( String pListInput, int pCycles )
  {
    String str_list_stones = pListInput;

    for ( int iteration_nr = 0; iteration_nr < pCycles; iteration_nr++ )
    {
      wl( "iteration_nr " + iteration_nr );

      str_list_stones = calcNewStoneListPart1( str_list_stones );
    }

    return getElementCountFromList( str_list_stones );
  }

  private static String calcNewStoneListPart1( String pListInput )
  {
    String[] list_stones_string = pListInput.trim().split( STR_SPACE );

    long[] list_stones_long = new long[ list_stones_string.length ];

    for ( int index_i = 0; index_i < list_stones_string.length; index_i++ )
    {
      list_stones_long[ index_i ] = Long.parseLong( list_stones_string[ index_i ] );
    }

    String str_ergebnis = "";

    for ( int index_i = 0; index_i < list_stones_string.length; index_i++ )
    {
      list_stones_long[ index_i ] = Long.parseLong( list_stones_string[ index_i ] );

      if ( ( list_stones_string[ index_i ].length() % 2 ) == 0 )
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

        int pos_str_mid = list_stones_string[ index_i ].length() / 2;

        String first_part = list_stones_string[ index_i ].substring( 0, pos_str_mid );

        String second_part = list_stones_string[ index_i ].substring( pos_str_mid );

        str_ergebnis += STR_SPACE + Long.parseLong( first_part ) + STR_SPACE + Long.parseLong( second_part );
      }
      else if ( list_stones_long[ index_i ] == 0 )
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
        str_ergebnis += STR_SPACE + "1";
      }
      else
      {
        /*
         * This rule ensures, that the algorithm will get into a period with multiples of 2048
         */
        str_ergebnis += STR_SPACE + ( list_stones_long[ index_i ] * 2024 );
      }
    }

    return str_ergebnis.trim();
  }

  private static String getStringProd()
  {
    String result = "";

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day11_input.txt";

    try (BufferedReader buffered_reader = new BufferedReader( new FileReader( datei_input ) ))
    {
      String zeile;

      while ( ( zeile = buffered_reader.readLine() ) != null )
      {
        zeile = zeile.trim();

        result += zeile;
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

  private static void testArraySplit()
  {
    String test_content_1 = " 1  1  3  9  1  1 ";

    List< Long > long_list = Arrays.stream( test_content_1.split( " " ) ).map( String::trim ).filter( s -> !s.isEmpty() ).map( Long::valueOf ).collect( Collectors.toList() );

    String test_content_2 = " 4 5 6 7 ";

    List< Long > long_list2 = Arrays.stream( test_content_2.split( " " ) ).map( String::trim ).filter( s -> !s.isEmpty() ).map( Long::valueOf ).collect( Collectors.toList() );

    for ( Long v : long_list )
    {
      System.out.print( v + " " );
    }

    wl( "" );

    int cur_index = 2;

    wl( "cur_index = " + cur_index + " = " + long_list.get( cur_index ).longValue() );

    long_list.addAll( cur_index, long_list2 );

    for ( Long v : long_list )
    {
      System.out.print( v + " " ); // oder println für Zeilenende 
    }

    wl( "" );

    long_list.remove( cur_index );

    for ( Long v : long_list )
    {
      System.out.print( v + " " ); // oder println für Zeilenende 
    }

    wl( "" );
  }

}
