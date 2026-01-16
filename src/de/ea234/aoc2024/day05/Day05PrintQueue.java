package de.ea234.aoc2024.day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class Day05PrintQueue
{
  /*
   * -- Day 5: Print Queue ---
   * https://adventofcode.com/2024/day/5
   * 
   * https://www.youtube.com/watch?v=jQ2IShKwtLA
   * 
   * https://www.reddit.com/r/adventofcode/comments/1h71eyz/2024_day_5_solutions/
   * 
   * 
   * 29|13
   * 47|13
   * 47|29
   * 47|53
   * 47|61
   * 53|13
   * 53|29
   * 61|13
   * 61|29
   * 61|53
   * 75|13
   * 75|29
   * 75|47
   * 75|53
   * 75|61
   * 97|13
   * 97|29
   * 97|47
   * 97|53
   * 97|61
   * 97|75
   * 47 => [(Rule    47    13), (Rule    47    29), (Rule    47    53), (Rule    47    61)]
   * 29 => [(Rule    29    13)]
   * 61 => [(Rule    61    13), (Rule    61    29), (Rule    61    53)]
   * 53 => [(Rule    53    13), (Rule    53    29)]
   * 75 => [(Rule    75    13), (Rule    75    29), (Rule    75    47), (Rule    75    53), (Rule    75    61)]
   * 97 => [(Rule    97    13), (Rule    97    29), (Rule    97    47), (Rule    97    53), (Rule    97    61), (Rule    97    75)]
   * 
   * -------------------------------------------------------------------------------------
   * 75,47,61,53,29
   * Page Nr.    0 75 - rules    0 (Rule    75    13) - Rule does not apply
   * Page Nr.    0 75 - rules    1 (Rule    75    29) - Rule does apply      - 0 
   * Page Nr.    0 75 - rules    2 (Rule    75    47) - Rule does apply      - 0 
   * Page Nr.    0 75 - rules    3 (Rule    75    53) - Rule does apply      - 0 
   * Page Nr.    0 75 - rules    4 (Rule    75    61) - Rule does apply      - 0 
   * Page Nr.    1 75 - Number of violated rules    0 
   * 
   * Page Nr.    1 47 - rules    0 (Rule    47    13) - Rule does not apply
   * Page Nr.    1 47 - rules    1 (Rule    47    29) - Rule does apply      - 0 
   * Page Nr.    1 47 - rules    2 (Rule    47    53) - Rule does apply      - 0 
   * Page Nr.    1 47 - rules    3 (Rule    47    61) - Rule does apply      - 0 
   * Page Nr.    2 47 - Number of violated rules    0 
   * 
   * Page Nr.    2 61 - rules    0 (Rule    61    13) - Rule does not apply
   * Page Nr.    2 61 - rules    1 (Rule    61    29) - Rule does apply      - 0 
   * Page Nr.    2 61 - rules    2 (Rule    61    53) - Rule does apply      - 0 
   * Page Nr.    3 61 - Number of violated rules    0 
   * 
   * Page Nr.    3 53 - rules    0 (Rule    53    13) - Rule does not apply
   * Page Nr.    3 53 - rules    1 (Rule    53    29) - Rule does apply      - 0 
   * Page Nr.    4 53 - Number of violated rules    0 
   * 
   * Page Nr.    4 29 - rules    0 (Rule    29    13) - Rule does not apply
   * Page Nr.    5 29 - Number of violated rules    0 
   * 
   * Update 75,47,61,53,29 - Number of violated rules    0 
   * 
   * 
   * -------------------------------------------------------------------------------------
   * 97,61,53,29,13
   * Page Nr.    0 97 - rules    0 (Rule    97    13) - Rule does apply      - 0 
   * Page Nr.    0 97 - rules    1 (Rule    97    29) - Rule does apply      - 0 
   * Page Nr.    0 97 - rules    2 (Rule    97    47) - Rule does not apply
   * Page Nr.    0 97 - rules    3 (Rule    97    53) - Rule does apply      - 0 
   * Page Nr.    0 97 - rules    4 (Rule    97    61) - Rule does apply      - 0 
   * Page Nr.    0 97 - rules    5 (Rule    97    75) - Rule does not apply
   * Page Nr.    1 97 - Number of violated rules    0 
   * 
   * Page Nr.    1 61 - rules    0 (Rule    61    13) - Rule does apply      - 0 
   * Page Nr.    1 61 - rules    1 (Rule    61    29) - Rule does apply      - 0 
   * Page Nr.    1 61 - rules    2 (Rule    61    53) - Rule does apply      - 0 
   * Page Nr.    2 61 - Number of violated rules    0 
   * 
   * Page Nr.    2 53 - rules    0 (Rule    53    13) - Rule does apply      - 0 
   * Page Nr.    2 53 - rules    1 (Rule    53    29) - Rule does apply      - 0 
   * Page Nr.    3 53 - Number of violated rules    0 
   * 
   * Page Nr.    3 29 - rules    0 (Rule    29    13) - Rule does apply      - 0 
   * Page Nr.    4 29 - Number of violated rules    0 
   * 
   * Page Nr.    4 13 - no rules
   * Page Nr.    5 13 - Number of violated rules    0 
   * 
   * Update 97,61,53,29,13 - Number of violated rules    0 
   * 
   * 
   * -------------------------------------------------------------------------------------
   * 75,29,13
   * Page Nr.    0 75 - rules    0 (Rule    75    13) - Rule does apply      - 0 
   * Page Nr.    0 75 - rules    1 (Rule    75    29) - Rule does apply      - 0 
   * Page Nr.    0 75 - rules    2 (Rule    75    47) - Rule does not apply
   * Page Nr.    0 75 - rules    3 (Rule    75    53) - Rule does not apply
   * Page Nr.    0 75 - rules    4 (Rule    75    61) - Rule does not apply
   * Page Nr.    1 75 - Number of violated rules    0 
   * 
   * Page Nr.    1 29 - rules    0 (Rule    29    13) - Rule does apply      - 0 
   * Page Nr.    2 29 - Number of violated rules    0 
   * 
   * Page Nr.    2 13 - no rules
   * Page Nr.    3 13 - Number of violated rules    0 
   * 
   * Update 75,29,13 - Number of violated rules    0 
   * 
   * 
   * -------------------------------------------------------------------------------------
   * 75,97,47,61,53
   * Page Nr.    0 75 - rules    0 (Rule    75    13) - Rule does not apply
   * Page Nr.    0 75 - rules    1 (Rule    75    29) - Rule does not apply
   * Page Nr.    0 75 - rules    2 (Rule    75    47) - Rule does apply      - 0 
   * Page Nr.    0 75 - rules    3 (Rule    75    53) - Rule does apply      - 0 
   * Page Nr.    0 75 - rules    4 (Rule    75    61) - Rule does apply      - 0 
   * Page Nr.    1 75 - Number of violated rules    0 
   * 
   * Page Nr.    1 97 - rules    0 (Rule    97    13) - Rule does not apply
   * Page Nr.    1 97 - rules    1 (Rule    97    29) - Rule does not apply
   * Page Nr.    1 97 - rules    2 (Rule    97    47) - Rule does apply      - 0 
   * Page Nr.    1 97 - rules    3 (Rule    97    53) - Rule does apply      - 0 
   * Page Nr.    1 97 - rules    4 (Rule    97    61) - Rule does apply      - 0 
   * Page Nr.    1 97 - rules    5 (Rule    97    75) - Rule does apply      - 1 
   * Page Nr.    2 97 - Number of violated rules    1 
   * 
   * Page Nr.    2 47 - rules    0 (Rule    47    13) - Rule does not apply
   * Page Nr.    2 47 - rules    1 (Rule    47    29) - Rule does not apply
   * Page Nr.    2 47 - rules    2 (Rule    47    53) - Rule does apply      - 0 
   * Page Nr.    2 47 - rules    3 (Rule    47    61) - Rule does apply      - 0 
   * Page Nr.    3 47 - Number of violated rules    0 
   * 
   * Page Nr.    3 61 - rules    0 (Rule    61    13) - Rule does not apply
   * Page Nr.    3 61 - rules    1 (Rule    61    29) - Rule does not apply
   * Page Nr.    3 61 - rules    2 (Rule    61    53) - Rule does apply      - 0 
   * Page Nr.    4 61 - Number of violated rules    0 
   * 
   * Page Nr.    4 53 - rules    0 (Rule    53    13) - Rule does not apply
   * Page Nr.    4 53 - rules    1 (Rule    53    29) - Rule does not apply
   * Page Nr.    5 53 - Number of violated rules    0 
   * 
   * Update 75,97,47,61,53 - Number of violated rules    1 
   * 
   * 
   * -------------------------------------------------------------------------------------
   * 61,13,29
   * Page Nr.    0 61 - rules    0 (Rule    61    13) - Rule does apply      - 0 
   * Page Nr.    0 61 - rules    1 (Rule    61    29) - Rule does apply      - 0 
   * Page Nr.    0 61 - rules    2 (Rule    61    53) - Rule does not apply
   * Page Nr.    1 61 - Number of violated rules    0 
   * 
   * Page Nr.    1 13 - no rules
   * Page Nr.    2 13 - Number of violated rules    0 
   * 
   * Page Nr.    2 29 - rules    0 (Rule    29    13) - Rule does apply      - 1 
   * Page Nr.    3 29 - Number of violated rules    1 
   * 
   * Update 61,13,29 - Number of violated rules    1 
   * 
   * 
   * -------------------------------------------------------------------------------------
   * 97,13,75,29,47
   * Page Nr.    0 97 - rules    0 (Rule    97    13) - Rule does apply      - 0 
   * Page Nr.    0 97 - rules    1 (Rule    97    29) - Rule does apply      - 0 
   * Page Nr.    0 97 - rules    2 (Rule    97    47) - Rule does apply      - 0 
   * Page Nr.    0 97 - rules    3 (Rule    97    53) - Rule does not apply
   * Page Nr.    0 97 - rules    4 (Rule    97    61) - Rule does not apply
   * Page Nr.    0 97 - rules    5 (Rule    97    75) - Rule does apply      - 0 
   * Page Nr.    1 97 - Number of violated rules    0 
   * 
   * Page Nr.    1 13 - no rules
   * Page Nr.    2 13 - Number of violated rules    0 
   * 
   * Page Nr.    2 75 - rules    0 (Rule    75    13) - Rule does apply      - 1 
   * Page Nr.    2 75 - rules    1 (Rule    75    29) - Rule does apply      - 0 
   * Page Nr.    2 75 - rules    2 (Rule    75    47) - Rule does apply      - 0 
   * Page Nr.    2 75 - rules    3 (Rule    75    53) - Rule does not apply
   * Page Nr.    2 75 - rules    4 (Rule    75    61) - Rule does not apply
   * Page Nr.    3 75 - Number of violated rules    1 
   * 
   * Page Nr.    3 29 - rules    0 (Rule    29    13) - Rule does apply      - 1 
   * Page Nr.    4 29 - Number of violated rules    1 
   * 
   * Page Nr.    4 47 - rules    0 (Rule    47    13) - Rule does apply      - 1 
   * Page Nr.    4 47 - rules    1 (Rule    47    29) - Rule does apply      - 1 
   * Page Nr.    4 47 - rules    2 (Rule    47    53) - Rule does not apply
   * Page Nr.    4 47 - rules    3 (Rule    47    61) - Rule does not apply
   * Page Nr.    5 47 - Number of violated rules    2 
   * 
   * Update 97,13,75,29,47 - Number of violated rules    4 
   * 
   * 
   * number_updates_false 3
   * number_updates_ok    3
   * 
   * OK
   * 
   *   61  =  75,47,61,53,29 
   *   53  =  97,61,53,29,13 
   *   29  =  75,29,13 
   *
   * result_part_1 143
   */

  public static void main( String[] args )
  {
    String test_input = "";

    test_input += "29|13";
    test_input += ";47|13";
    test_input += ";47|29";
    test_input += ";47|53";
    test_input += ";47|61";
    test_input += ";53|13";
    test_input += ";53|29";
    test_input += ";61|13";
    test_input += ";61|29";
    test_input += ";61|53";
    test_input += ";75|13";
    test_input += ";75|29";
    test_input += ";75|47";
    test_input += ";75|53";
    test_input += ";75|61";
    test_input += ";97|13";
    test_input += ";97|29";
    test_input += ";97|47";
    test_input += ";97|53";
    test_input += ";97|61";
    test_input += ";97|75";
    test_input += ";";
    test_input += ";75,47,61,53,29";
    test_input += ";97,61,53,29,13";
    test_input += ";75,29,13";
    test_input += ";75,97,47,61,53";
    test_input += ";61,13,29";
    test_input += ";97,13,75,29,47";

    calculatePart01( test_input, true );
    //calculatePart01( getListProd(), false );

    System.exit( 0 );
  }

  private static void calculatePart01( String pString, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( ";" ) ).map( String::trim ).collect( Collectors.toList() );

    calculatePart01( converted_string_list, pKnzDebug );
  }

  private static void calculatePart01( List< String > pListInput, boolean pKnzDebug )
  {
    /*
     * *******************************************************************************************************
     * Initializing Variables
     * *******************************************************************************************************
     */

    m_hash_map = new HashMap< String, List< Day05PageRule > >();

    List< String > list_page_updates = new ArrayList< String >();

    /*
     * *******************************************************************************************************
     * Initializing the grid
     * *******************************************************************************************************
     */

    boolean knz_load_regeln = true;

    for ( String input_str : pListInput )
    {
      knz_load_regeln = input_str.indexOf( '|' ) > 0;

      if ( knz_load_regeln )
      {
        wl( input_str );

        String[] parts = input_str.split( "\\|" );

        saveHashMapValue( parts[ 0 ], input_str );
      }
      else
      {
        list_page_updates.add( input_str );
      }
    }

    for ( Map.Entry< String, List< Day05PageRule > > e : m_hash_map.entrySet() )
    {
      System.out.println( e.getKey() + " => " + e.getValue().toString() );
    }

    List< String > dbg_list_ok = new ArrayList< String >();
    List< String > dbg_list_false = new ArrayList< String >();

    int number_updates_ok = 0;
    int number_updates_false = 0;

    for ( String update_p : list_page_updates )
    {
      if ( update_p.isBlank() )
      {
        continue;
      }

      if ( update_p.isEmpty() )
      {
        continue;
      }

      wl( "" );
      wl( "-------------------------------------------------------------------------------------" );

      wl( update_p );

      String update_kommatas = "," + update_p + ",";

      int number_of_violated_rules_update = 0;

      int index_page = 0;

      String[] page_numbers_update = update_p.split( "," );

      for ( String page_nr : page_numbers_update )
      {
        int number_of_violated_rules_page = 0;

        if ( page_nr.isBlank() == false )
        {
          List< Day05PageRule > page_rules = m_hash_map.get( page_nr );

          if ( page_rules != null )
          {
            int rule_index = 0;

            for ( Day05PageRule pr : page_rules )
            {
              if ( pr.apply( update_kommatas ) == false )
              {
                if ( pKnzDebug )
                {
                  wl( String.format( "Page Nr. %4d %s - rules %4d %s - Rule does not apply", index_page, page_nr, rule_index, pr.toString() ) );
                }
              }
              else
              {
                int res_check = pr.isPageABeforePageB( update_kommatas );

                if ( pKnzDebug )
                {
                  wl( String.format( "Page Nr. %4d %s - rules %4d %s - Rule does apply      - %d ", index_page, page_nr, rule_index, pr.toString(), res_check ) );
                }
                number_of_violated_rules_update += res_check;

                number_of_violated_rules_page += res_check;
              }

              rule_index++;
            }
          }
          else
          {
            if ( pKnzDebug )
            {
              wl( String.format( "Page Nr. %4d %s - no rules", index_page, page_nr ) );
            }
          }

          index_page++;
        }

        if ( pKnzDebug )
        {
          wl( String.format( "Page Nr. %4d %s - Number of violated rules %4d ", index_page, page_nr, number_of_violated_rules_page ) );
          wl( "" );
        }
      }

      wl( String.format( "Update %s - Number of violated rules %4d ", update_p, number_of_violated_rules_update ) );
      wl( "" );

      number_updates_false += ( number_of_violated_rules_update > 0 ? 1 : 0 );

      number_updates_ok += ( number_of_violated_rules_update > 0 ? 0 : 1 );

      if ( number_of_violated_rules_update > 0 )
      {
        dbg_list_false.add( update_p );
      }
      else
      {
        if ( update_p.trim().isEmpty() == false )
        {
          dbg_list_ok.add( update_p );
        }
      }
    }

    wl( "" );
    wl( "number_updates_false " + number_updates_false );
    wl( "number_updates_ok    " + number_updates_ok );
    wl( "" );
    wl( "OK" );
    wl( "" );

    int sum_middle_page_numbers = 0;

    for ( String update_s : dbg_list_ok )
    {
      int middle_page_number = getMiddlePageNumber( update_s );

      sum_middle_page_numbers += middle_page_number;

      wl( String.format( "%4d  =  %s ", middle_page_number, update_s ) );
    }

    wl( "" );
    wl( "result_part_1 " + sum_middle_page_numbers );
    wl( "" );
  }

  private static int getMiddlePageNumber( String pUpdateStr )
  {
    if ( pUpdateStr.trim().isBlank() ) return 0;
    if ( pUpdateStr.trim().isEmpty() ) return 0;

    String[] parts = pUpdateStr.replaceAll( ",", " " ).trim().split( " " );

    int index_mid = getMidNumber( parts.length );

    index_mid--; // Array starts at 0, so decrease by one

    return Integer.valueOf( parts[ index_mid ] );
  }

  private static int getMidNumber( int pNumber )
  {
    if ( pNumber % 2 == 0 )
    {
      // even 
      return pNumber / 2;
    }

    // odd

    return ( ( pNumber - 1 ) / 2 ) + 1;
  }

  private static HashMap< String, List< Day05PageRule > > m_hash_map = null;

  private static void saveHashMapValue( String pKeyPageA, String pValueRule )
  {
    List< Day05PageRule > list_cur = m_hash_map.get( pKeyPageA );

    if ( list_cur == null )
    {
      list_cur = new ArrayList< Day05PageRule >();

      m_hash_map.put( pKeyPageA, list_cur );
    }

    list_cur.add( new Day05PageRule( pValueRule ) );
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day05_input.txt";

    try
    {
      string_array = Files.readAllLines( Path.of( datei_input ) );
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }

    return string_array;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }
}
