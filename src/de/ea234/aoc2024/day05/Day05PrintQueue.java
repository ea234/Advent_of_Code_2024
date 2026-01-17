package de.ea234.aoc2024.day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
   * number_updates_false 3
   * number_updates_ok    3
   * 
   * OK
   * 
   *   61  =  75,47,61,53,29 
   *   53  =  97,61,53,29,13 
   *   29  =  75,29,13 
   * result_part_1 143
   * 
   * Fehlerhaft 
   * 
   * ---------------------------------
   * 75,97,47,61,53
   * 
   * calcx = p0 47   p1  13 29 53 61
   * 47     "75,97,47,61,53",      " 13 29 53 61" 
   * 
   * calcx = p0 29   p1  13
   * 29     "75,97,47,61,53",      " 13" 
   * 
   * calcx = p0 61   p1  13 29 53
   * 61     "75,97,47,61,53",      " 13 29 53" 
   * 
   * calcx = p0 53   p1  13 29
   * 53     "75,97,47,61,53",      " 13 29" 
   * 
   * calcx = p0 75   p1  13 29 47 53 61
   * 75     "75,97,47,61,53",      " 13 29 47 53 61" 
   * 
   * calcx = p0 97   p1  13 29 47 53 61 75
   * 97     "97,75,47,61,53",      " 13 29 47 53 61 75" 
   * 
   * 
   * 
   * ---------------------------------
   * 61,13,29
   * 
   * calcx = p0 47   p1  13 29 53 61
   * 47     "61,13,29",      " 13 29 53 61" 
   * 
   * calcx = p0 29   p1  13
   * 29     "61,29,13",      " 13" 
   * 
   * calcx = p0 61   p1  13 29 53
   * 61     "61,29,13",      " 13 29 53" 
   * 
   * calcx = p0 53   p1  13 29
   * 53     "61,29,13",      " 13 29" 
   * 
   * calcx = p0 75   p1  13 29 47 53 61
   * 75     "61,29,13",      " 13 29 47 53 61" 
   * 
   * calcx = p0 97   p1  13 29 47 53 61 75
   * 97     "61,29,13",      " 13 29 47 53 61 75" 
   * 
   * 
   * 
   * ---------------------------------
   * 97,13,75,29,47
   * 
   * calcx = p0 47   p1  13 29 53 61
   * 47     "97,47,75,29,13",      " 13 29 53 61" 
   * 
   * calcx = p0 29   p1  13
   * 29     "97,47,75,29,13",      " 13" 
   * 
   * calcx = p0 61   p1  13 29 53
   * 61     "97,47,75,29,13",      " 13 29 53" 
   * 
   * calcx = p0 53   p1  13 29
   * 53     "97,47,75,29,13",      " 13 29" 
   * 
   * calcx = p0 75   p1  13 29 47 53 61
   * 75     "97,75,47,29,13",      " 13 29 47 53 61" 
   * 
   * calcx = p0 97   p1  13 29 47 53 61 75
   * 97     "97,75,47,29,13",      " 13 29 47 53 61 75" 
   * 
   * XXXXK     47  =  97,75,47,61,53 
   * XXXXK     29  =  61,29,13 
   * XXXXK     47  =  97,75,47,29,13 
   * result_part_2 123
   */

  public static void main( String[] args )
  {
    String test_input_part_1 = "";

    test_input_part_1 += "29|13";
    test_input_part_1 += ";47|13";
    test_input_part_1 += ";47|29";
    test_input_part_1 += ";47|53";
    test_input_part_1 += ";47|61";
    test_input_part_1 += ";53|13";
    test_input_part_1 += ";53|29";
    test_input_part_1 += ";61|13";
    test_input_part_1 += ";61|29";
    test_input_part_1 += ";61|53";
    test_input_part_1 += ";75|13";
    test_input_part_1 += ";75|29";
    test_input_part_1 += ";75|47";
    test_input_part_1 += ";75|53";
    test_input_part_1 += ";75|61";
    test_input_part_1 += ";97|13";
    test_input_part_1 += ";97|29";
    test_input_part_1 += ";97|47";
    test_input_part_1 += ";97|53";
    test_input_part_1 += ";97|61";
    test_input_part_1 += ";97|75";
    test_input_part_1 += ";";
    test_input_part_1 += ";75,47,61,53,29";
    test_input_part_1 += ";97,61,53,29,13";
    test_input_part_1 += ";75,29,13";
    test_input_part_1 += ";75,97,47,61,53";
    test_input_part_1 += ";61,13,29";
    test_input_part_1 += ";97,13,75,29,47";

    calculatePart01( test_input_part_1, true );
    //calculatePart01( getListProd(), true );
    System.exit( 0 );
  }

  private static String calcListNewForPageNr1( String pPageNr, String pListCur, String pPageBeforeList )
  {
    String new_list = calcListNewForPageNr( pPageNr, pListCur, pPageBeforeList );

    wl( pPageNr + "     \"" + new_list + "\",      \"" + pPageBeforeList + "\" " );

    return new_list;
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
     * Initializing the rules and the page update list
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

    List< String > rule_list_part_2 = new ArrayList< String >();

    for ( Map.Entry< String, List< Day05PageRule > > map_entry : m_hash_map.entrySet() )
    {
      List< Day05PageRule > page_rules = map_entry.getValue();

      String page_numbers_after_page_1 = "";

      for ( Day05PageRule page_rule : page_rules )
      {
        page_numbers_after_page_1 += " " + page_rule.getPageBStr();
      }

     // rule_list_part_2.add( map_entry.getKey() + "=" + getSortedPageNr( page_numbers_after_page_1 ) );
      rule_list_part_2.add( map_entry.getKey() + "=" + ( page_numbers_after_page_1 ) );

      if ( pKnzDebug )
      {
        wl( "HashMap-Key: Page " + map_entry.getKey() + " Page before " + page_numbers_after_page_1 );
      }
    }

    //rule_list_part_2.sort( ( str_a, str_b ) -> Integer.compare( str_b.length(), str_a.length() ) );

    if ( pKnzDebug )
    {
      for ( int index_cur = 0; index_cur < rule_list_part_2.size(); index_cur++ )
      {
        wl( String.format( " %4d - %s", index_cur, rule_list_part_2.get( index_cur ) ) );
      }

    }

    /*
     * *******************************************************************************************************
     * Seperating correct update list's from false ones
     * *******************************************************************************************************
     */

    List< String > dbg_list_ok = new ArrayList< String >();

    List< String > dbg_list_false = new ArrayList< String >();

    int number_updates_ok = 0;

    int number_updates_false = 0;

    for ( String page_update_string : list_page_updates )
    {
      if ( page_update_string.isBlank() )
      {
        continue;
      }

      if ( page_update_string.isEmpty() )
      {
        continue;
      }

      if ( pKnzDebug )
      {
        wl( "" );
        wl( "-------------------------------------------------------------------------------------" );

        wl( page_update_string );
      }

      int number_of_violated_rules_update = 0;

      int index_page = 0;

      String[] array_page_numbers = page_update_string.split( "," );

      String page_update_string_with_commas = "," + page_update_string + ",";

      for ( String page_number_cur : array_page_numbers )
      {
        int number_of_violated_rules_page = 0;

        if ( page_number_cur.isBlank() == false )
        {
          /*
           * Get all Rules for the current page number
           */
          List< Day05PageRule > page_rules = m_hash_map.get( page_number_cur );

          if ( page_rules == null )
          {
            /*
             * If the page has no rules, the page number position is correct.
             */

            if ( pKnzDebug )
            {
              wl( String.format( "Page Nr. %4d %s - no rules", index_page, page_number_cur ) );
            }
          }
          else
          {
            /*
             * If the page number has page rules, the rules are checked.
             */
            int rule_index = 0;

            for ( Day05PageRule page_rule : page_rules )
            {
              if ( page_rule.apply( page_update_string_with_commas ) == false )
              {
                /*
                 * If the rule does not apply, there is only a debug output 
                 */

                if ( pKnzDebug )
                {
                  wl( String.format( "Page Nr. %4d %s - rules %4d %s - Rule does not apply", index_page, page_number_cur, rule_index, page_rule.toString() ) );
                }
              }
              else
              {
                /*
                 * If the page rule apply, the page order has to be checked.
                 * 
                 * If the pages are in the correct order, the result will be 0.
                 * If the pages are noth in the correct order, the result will be 1.
                 */
                int result_page_rule_check = page_rule.isPageABeforePageB( page_update_string_with_commas );

                /*
                 * Do some debug stuff
                 */
                if ( pKnzDebug )
                {
                  wl( String.format( "Page Nr. %4d %s - rules %4d %s - Rule does apply      - %d ", index_page, page_number_cur, rule_index, page_rule.toString(), result_page_rule_check ) );
                }

                /*
                 * Add up the results of violated rules
                 */
                number_of_violated_rules_update += result_page_rule_check;

                number_of_violated_rules_page += result_page_rule_check;
              }

              rule_index++;
            }
          }

          index_page++;
        }

        if ( pKnzDebug )
        {
          wl( String.format( "Page Nr. %4d %s - Number of violated rules %4d ", index_page, page_number_cur, number_of_violated_rules_page ) );
          wl( "" );
        }
      }

      wl( String.format( "Update %s - Number of violated rules %4d ", page_update_string, number_of_violated_rules_update ) );
      wl( "" );

      number_updates_false += ( number_of_violated_rules_update > 0 ? 1 : 0 );

      number_updates_ok += ( number_of_violated_rules_update > 0 ? 0 : 1 );

      if ( number_of_violated_rules_update > 0 )
      {
        dbg_list_false.add( page_update_string );
      }
      else
      {
        if ( page_update_string.trim().isEmpty() == false )
        {
          dbg_list_ok.add( page_update_string );
        }
      }
    }

    wl( "" );
    wl( "number_updates_false " + number_updates_false );
    wl( "number_updates_ok    " + number_updates_ok );

    /*
     * *******************************************************************************************************
     * Calculating the result for part 1
     * *******************************************************************************************************
     */

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

    wl( "result_part_1 " + sum_middle_page_numbers );

    wl( "" );
    wl( "Fehlerhaft " );
    wl( "" );

    List< String > ar_new_list = new ArrayList< String >();

    for ( String update_s : dbg_list_false )
    {
      ar_new_list.add( calcX( rule_list_part_2, update_s ) );
    }

    sum_middle_page_numbers = 0;

    for ( String update_s : ar_new_list )
    {
      int middle_page_number = getMiddlePageNumber( update_s );

      sum_middle_page_numbers += middle_page_number;

      wl( String.format( "XXXXK   %4d  =  %s ", middle_page_number, update_s ) );
    }

    wl( "result_part_2 " + sum_middle_page_numbers );
    wl( "" );
    wl( "" );
  }

  private static String calcX( List< String > pRules, String pListCur )
  {
    wl( "" );
    wl( "---------------------------------" );
    wl( "" + pListCur );
    wl( "" );

    List< String > ar_new_list = new ArrayList< String >();

    /*
     * Create an array of the current list
     */
    String[] list_current = pListCur.split( "," );

    String new_list = pListCur;

    for ( int index_rule = 0; index_rule < pRules.size(); index_rule++ )
    {
      /*
       * Obtain the page number from the list of before values
       */
      String cur_rule = pRules.get( index_rule );

      String[] parts = cur_rule.split( "=" );

      wl( "calcx = p0 " + parts[ 0 ] + "   p1 " + parts[ 1 ] );

      new_list = calcListNewForPageNr1( parts[ 0 ], new_list, parts[ 1 ] );

      wl( "" );
    }

    return new_list;
  }

  private static String calcListNewForPageNr( String pPageNr1, String pListCur, String pPage2List )
  {
    /*
     * Create an array for all Pages that must be after page 1 
     */
    String[] list_of_page_numbers_after_page_1 = pPage2List.split( " " );

    /*
     * Create an array of the current list
     */
    String[] list_current = pListCur.split( "," );

    /*
     * Get the current index for page 1
     */
    int index_page_1_cur = getIndexString( list_current, pPageNr1 );

    /*
     * Store the current index for page 1 to index_page_1_new 
     */
    int index_page_1_new = index_page_1_cur;

    /*
     * Check all pages, which must occur after page 1
     */
    for ( int index_list_page_2 = 0; index_list_page_2 < list_of_page_numbers_after_page_1.length; index_list_page_2++ )
    {
      /*
       * Obtain the page number from the list of before values
       */
      String page_number_to_be_checked_from_rule = list_of_page_numbers_after_page_1[ index_list_page_2 ];

      /*
       * Check, if the page 2 has an index in the current list.
       * If page 2 is not found in the list, the page is not in the update. (index = -1)
       * If page 2 is found in the list, than we have an index in the current list.
       */
      int index_page_2 = getIndexString( list_current, page_number_to_be_checked_from_rule );

      /*
       * check if rule apply
       * ... both pages must be in the update
       *     = both pages must be found in the array list_current
       *     
       * ... page 1 is in the report, because we are searching for a better position
       * ... page 2 is in the report, when an index_page_2 was found
       */
      if ( index_page_2 >= 0 )
      {
        /*
         * Page 1 before Page 2
         * 
         * If the Index P2 is less than Index P1, then
         *  
         * ... Page 2 is currently before Page 1 
         *     which must be corrected.
         *     
         * Store the Index P2 in index_page_1_new
         */

        if ( index_page_2 >= 0 )
        {
          if ( index_page_2 < index_page_1_new )
          {
            index_page_1_new = index_page_2;
          }
        }
      }
    }

    /*
     * Did the index_page_1_new change?
     */
    if ( index_page_1_new != index_page_1_cur )
    {
      /*
       * At the index for the current page 1, the value of the current page 2 is set.
       */
      list_current[ index_page_1_cur ] = list_current[ index_page_1_new ];

      /*
       * At the new index for page 1, the value from the parameter pPageNr1 is set.
       */
      list_current[ index_page_1_new ] = pPageNr1;
    }

    return getCsvList( list_current );
  }

  private static int getIndexString( String[] pArray, String page_b_str )
  {
    if ( pArray == null || page_b_str == null )
    {
      return -1;
    }

    for ( int index_cur = 0; index_cur < pArray.length; index_cur++ )
    {
      if ( page_b_str.equals( pArray[ index_cur ] ) )
      {
        return index_cur;
      }
    }

    return -1;
  }

  private static String getCsvList( String[] pList )
  {
    String csv_string = "";

    for ( int index = 0; index < pList.length; index++ )
    {
      if ( index > 0 )
      {
        csv_string += ",";
      }

      csv_string += pList[ index ];
    }

    return csv_string;
  }

  private static String getSortedPageNr( String pPageNumbers )
  {
    String[] array_string = pPageNumbers.trim().split( "\\s+" );

    int[] array_int = new int[ array_string.length ];

    for ( int index_cur = 0; index_cur < array_string.length; index_cur++ )
    {
      array_int[ index_cur ] = Integer.parseInt( array_string[ index_cur ] );
    }

    java.util.Arrays.sort( array_int );

    StringBuilder string_builder = new StringBuilder();

    for ( int index_cur = 0; index_cur < array_int.length; index_cur++ )
    {
      if ( index_cur > 0 ) string_builder.append( " " );

      string_builder.append( array_int[ index_cur ] );
    }

    return string_builder.toString();
  }

  private static int calcMiddlPageSum( List< String > pListInput )
  {
    int sum_middle_page_numbers = 0;

    for ( String update_s : pListInput )
    {
      int middle_page_number = getMiddlePageNumber( update_s );

      sum_middle_page_numbers += middle_page_number;

      wl( String.format( "%4d  =  %s ", middle_page_number, update_s ) );
    }

    return sum_middle_page_numbers;
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

  private static boolean istGerade( int zahl )
  {
    return zahl % 2 == 0;
  }

  private static boolean istUngerade( int zahl )
  {
    return zahl % 2 != 0;
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
