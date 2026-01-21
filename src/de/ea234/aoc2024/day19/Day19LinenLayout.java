package de.ea234.aoc2024.day19;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Collectors;

public class Day19LinenLayout
{
  /*
  * --- Day 19: Linen Layout ---
  * https://adventofcode.com/2024/day/19
  * 
  * https://www.youtube.com/watch?v=W-cxMF1wpuk
  * 
  * https://www.reddit.com/r/adventofcode/comments/1hhlb8g/2024_day_19_solutions/
  * 
   * wbwbgrrrrrbbwbbrguguwgururgurrwgwgguwburggggburgwguu                       0             wbwbgrrrrrbbwbbrguguwgururgurrwgwgguwburggggburgwguu    
   * wbwbgrrrrrbbwbbrguguwgururgurrwgwgguwburggggburgwguu                       3 wbw         ...bgrrrrrbbwbbrguguwgururgurrwgwgguwburggggburgwguu     0 true 
   * wbwbgrrrrrbbwbbrguguwgururgurrwgwgguwburggggburgwguu                       6 bgr         ......rrrrbbwbbrguguwgururgurrwgwgguwburggggburgwguu     0 true 
   * wbwbgrrrrrbbwbbrguguwgururgurrwgwgguwburggggburgwguu                       9 rrr         .........rbbwbbrguguwgururgurrwgwgguwburggggburgwguu     0 true 
   * wbwbgrrrrrbbwbbrguguwgururgurrwgwgguwburggggburgwguu                      12 rbb         ............wbbrguguwgururgurrwgwgguwburggggburgwguu     0 true 
   * wbwbgrrrrrbbwbbrguguwgururgurrwgwgguwburggggburgwguu                      15 wbb         ...............rguguwgururgurrwgwgguwburggggburgwguu     0 true 
   * wbwbgrrrrrbbwbbrguguwgururgurrwgwgguwburggggburgwguu                      18 rgu         ..................guwgururgurrwgwgguwburggggburgwguu     0 true 
   * wbwbgrrrrrbbwbbrguguwgururgurrwgwgguwburggggburgwguu                      21 guw         .....................gururgurrwgwgguwburggggburgwguu     0 true 
   * wbwbgrrrrrbbwbbrguguwgururgurrwgwgguwburggggburgwguu                      24 gur         ........................urgurrwgwgguwburggggburgwguu     0 true 
   * wbwbgrrrrrbbwbbrguguwgururgurrwgwgguwburggggburgwguu                      28 urgu        ............................rrwgwgguwburggggburgwguu     0 true 
   * wbwbgrrrrrbbwbbrguguwgururgurrwgwgguwburggggburgwguu                      31 rrw         ...............................gwgguwburggggburgwguu     0 true 
   * wbwbgrrrrrbbwbbrguguwgururgurrwgwgguwburggggburgwguu                      34 gwg         ..................................guwburggggburgwguu     0 true 
   * wbwbgrrrrrbbwbbrguguwgururgurrwgwgguwburggggburgwguu                      37 guw         .....................................burggggburgwguu     0 true 
   * wbwbgrrrrrbbwbbrguguwgururgurrwgwgguwburggggburgwguu                      40 bur         ........................................ggggburgwguu     0 true 
   * wbwbgrrrrrbbwbbrguguwgururgurrwgwgguwburggggburgwguu                      43 ggg         ...........................................gburgwguu     0 true 
   * wbwbgrrrrrbbwbbrguguwgururgurrwgwgguwburggggburgwguu                      47 gbur        ...............................................gwguu     0 true 
   * wbwbgrrrrrbbwbbrguguwgururgurrwgwgguwburggggburgwguu                      50 gwg         ..................................................uu     0 true 
   * wbwbgrrrrrbbwbbrguguwgururgurrwgwgguwburggggburgwguu                      52 uu          ....................................................     0 true 
   * 
   * 
   * 
   * guwgggburrwwbwgwggbrgbwrrrrubbggbuurguwrbbgrbwwbg                          0             guwgggburrwwbwgwggbrgbwrrrrubbggbuurguwrbbgrbwwbg    
   * guwgggburrwwbwgwggbrgbwrrrrubbggbuurguwrbbgrbwwbg                          6 guwggg      ......burrwwbwgwggbrgbwrrrrubbggbuurguwrbbgrbwwbg     0 true 
   * guwgggburrwwbwgwggbrgbwrrrrubbggbuurguwrbbgrbwwbg                         11 burrw       ...........wbwgwggbrgbwrrrrubbggbuurguwrbbgrbwwbg     0 true 
   * guwgggburrwwbwgwggbrgbwrrrrubbggbuurguwrbbgrbwwbg                         16 wbwgw       ................ggbrgbwrrrrubbggbuurguwrbbgrbwwbg     0 true 
   * guwgggburrwwbwgwggbrgbwrrrrubbggbuurguwrbbgrbwwbg                         19 ggb         ...................rgbwrrrrubbggbuurguwrbbgrbwwbg     0 true 
   * guwgggburrwwbwgwggbrgbwrrrrubbggbuurguwrbbgrbwwbg                         22 rgb         ......................wrrrrubbggbuurguwrbbgrbwwbg     0 true 
   * guwgggburrwwbwgwggbrgbwrrrrubbggbuurguwrbbgrbwwbg                         25 wrr         .........................rrubbggbuurguwrbbgrbwwbg     0 true 
   * guwgggburrwwbwgwggbrgbwrrrrubbggbuurguwrbbgrbwwbg                         28 rru         ............................bbggbuurguwrbbgrbwwbg     0 true 
   * guwgggburrwwbwgwggbrgbwrrrrubbggbuurguwrbbgrbwwbg                         31 bbg         ...............................gbuurguwrbbgrbwwbg     0 true 
   * guwgggburrwwbwgwggbrgbwrrrrubbggbuurguwrbbgrbwwbg                         35 gbuu        ...................................rguwrbbgrbwwbg     0 true 
   * guwgggburrwwbwgwggbrgbwrrrrubbggbuurguwrbbgrbwwbg                         38 rgu         ......................................wrbbgrbwwbg     0 true 
   * guwgggburrwwbwgwggbrgbwrrrrubbggbuurguwrbbgrbwwbg                         42 wrbb        ..........................................grbwwbg     0 true 
   * guwgggburrwwbwgwggbrgbwrrrrubbggbuurguwrbbgrbwwbg                         45 grb         .............................................wwbg     0 true 
   * guwgggburrwwbwgwggbrgbwrrrrubbggbuurguwrbbgrbwwbg                         48 wwb         ................................................g     0 true 
   * 
   * nicht gefunden g 48 
   * 
   * guwgggburrwwbwgwggbrgbwrrrrubbggbuurguwrbbgrbwwbg                         49 gr          ................................................g     1 true
   * 
   * 
   * 
   * Hashmap for patterns
   * 
   * b -> [bwu, br, b]
   * r -> [rb, r]
   * w -> [wr]
   * g -> [gb, g]
   * 
   * 
   * brwrr                 0             brwrr                   
   * brwrr                 2 br          ..wrr                    0 true 
   * brwrr                 4 wr          ....r                    0 true 
   * brwrr                 5 r           .....                    0 true 
   * 
   * bggr                  0             bggr                    
   * bggr                  1 b           .ggr                     0 true 
   * bggr                  2 g           ..gr                     0 true 
   * bggr                  3 g           ...r                     0 true 
   * bggr                  4 r           ....                     0 true 
   * 
   * gbbr                  0             gbbr                    
   * gbbr                  2 gb          ..br                     0 true 
   * gbbr                  4 br          ....                     0 true 
   * 
   * rrbgbr                0             rrbgbr                  
   * rrbgbr                1 r           .rbgbr                   0 true 
   * rrbgbr                3 rb          ...gbr                   0 true 
   * rrbgbr                5 gb          .....r                   0 true 
   * rrbgbr                6 r           ......                   0 true 
   * 
   * ubwu                  0             ubwu                    
   * 
   * bwurrg                0             bwurrg                  
   * bwurrg                3 bwu         ...rrg                   0 true 
   * bwurrg                4 r           ....rg                   0 true 
   * bwurrg                5 r           .....g                   0 true 
   * bwurrg                6 g           ......                   0 true 
   * 
   * brgr                  0             brgr                    
   * brgr                  2 br          ..gr                     0 true 
   * brgr                  3 g           ...r                     0 true 
   * brgr                  4 r           ....                     0 true 
   * 
   * bbrgwb                0             bbrgwb                  
   * bbrgwb                1 b           .brgwb                   0 true 
   * bbrgwb                3 br          ...gwb                   0 true 
   * bbrgwb                4 g           ....wb                   0 true 
   * 
   * nicht gefunden w 4 [w
   * 
   * bbrgwb                5 wr          ....wb                   1 true 
   * bbrgwb                6 b           ....w.                   1 true 
   * 
   * count_parser_algo_nok 2
   * count_parser_algo     6
   * 
   * 
   */

  public static void main( String[] args )
  {
    String test_input = "r, wr, b, g, bwu, rb, gb, br;;brwrr;bggr;gbbr;rrbgbr;ubwu;bwurrg;brgr;bbrgwb";

    calculatePart01( test_input, true );
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
     * Processing something for the patterns
     * *******************************************************************************************************
     */

    String available_towel_patterns = pListInput.get( 0 ).trim();

    /*
     * First line = towl patterns
     * Generate a list, sort that list by pattern length descending 
     */
    List< String > towel_pattern_list = Arrays.stream( available_towel_patterns.split( "," ) ).map( String::trim ).sorted( ( a, b ) -> Integer.compare( b.length(), a.length() ) ).collect( Collectors.toList() );

    /*
     * Create a hashmap, with all patterns for a given start character.
     */
    HashMap< String, List< String > > parser_pattern1 = new HashMap< String, List< String > >();

    for ( String cur_towel_pattern : towel_pattern_list )
    {
      if ( pKnzDebug )
      {
        wl( cur_towel_pattern );
      }

      /*
       * Generate the hashmap key (= first character towel pattern)
       */
      String cur_hash_map_key = "" + cur_towel_pattern.charAt( 0 );

      /*
       * Get the current List of patterns for the hashmap key
       */
      List< String > list_key_start_pattern = parser_pattern1.get( cur_hash_map_key );

      /*
       * If there is no list, create a list
       */
      if ( list_key_start_pattern == null )
      {
        list_key_start_pattern = new ArrayList< String >();
      }

      /*
       * Add the current towel pattern to the list
       */
      list_key_start_pattern.add( cur_towel_pattern );

      /*
       * Store the list in the hashmap
       */
      parser_pattern1.put( cur_hash_map_key, list_key_start_pattern );
    }

    if ( pKnzDebug )
    {
      wl( "" );
      wl( "Hashmap for patterns" );
      wl( "" );

      for ( Map.Entry< String, List< String > > entry : parser_pattern1.entrySet() )
      {
        System.out.println( entry.getKey() + " -> " + entry.getValue() );
      }

      wl( "" );
    }

    /*
     * *******************************************************************************************************
     * Check the towel designs (...and sum up the correct ones)
     * *******************************************************************************************************
     */

    List< String > not_possible_designs_list_a = new ArrayList< String >();

    int count_parser_algo_ok = 0;
    int count_parser_algo_nok = 0;

    for ( int index = 1; index < pListInput.size(); index++ )
    {
      String input_str = pListInput.get( index );

      if ( input_str.trim().isBlank() == false )
      {
        if ( checkTowelDesignParserAlgorithm( input_str, parser_pattern1 ) )
        {
          count_parser_algo_ok++;
        }
        else
        {
          count_parser_algo_nok++;

          not_possible_designs_list_a.add( input_str );
        }
      }
    }

    wl( "" );
    wl( "count_parser_algo_nok " + count_parser_algo_nok );
    wl( "count_parser_algo     " + count_parser_algo_ok );
    wl( "" );
  }

  private static boolean checkTowelDesignParserAlgorithm( String pDesign, HashMap< String, List< String > > pParserPattern )
  {
    int count_miss_match = 0;

    int index_search = 0;

    String new_string = pDesign;

    wl( "" );
    wl( String.format( "%-70s %5d %-10s  %-20s    ", pDesign, index_search, "", new_string ) );

    while ( index_search < pDesign.length() )
    {
      String cur_hash_map_key = "" + pDesign.charAt( index_search );

      List< String > list_key_start_pattern = pParserPattern.get( cur_hash_map_key );

      if ( list_key_start_pattern == null )
      {
        return false; // start char without patterns
      }
      else
      {
        boolean knz_result = false;

        String cur_pattern = null;

        for ( int index_pattern = 0; index_pattern < list_key_start_pattern.size(); index_pattern++ )
        {
          cur_pattern = list_key_start_pattern.get( index_pattern );

          if ( cur_pattern.length() == 1 )
          {
            if ( index_search > 40 )
            {
              wl( "break" );
            }
          }

          if ( checkPattern( pDesign, cur_pattern, index_search ) )
          {
            knz_result = true;

            break;
          }
        }

        if ( knz_result )
        {
          new_string = spliceString( new_string, cur_pattern, index_search );

          index_search += cur_pattern.length();
        }
        else
        {
          wl( "" );
          wl( "nicht gefunden " + cur_hash_map_key + " " + index_search + " " + list_key_start_pattern );
          wl( "" );

          count_miss_match++;

          index_search++;
        }

        wl( String.format( "%-70s %5d %-10s  %-20s  %4d %b ", pDesign, index_search, cur_pattern, new_string, count_miss_match, count_miss_match ) );
      }
    }

    return count_miss_match == 0;
  }

  private static boolean testCheckPattern( String pInput, String pCurPattern, int pIndexStart )
  {
    boolean result_check = checkPattern( pInput, pCurPattern, pIndexStart );

    wl( "checkPattern( " + pInput + ", " + pCurPattern + ", " + pIndexStart + " ) = " + result_check );

    return result_check;
  }

  /**
  *
  * CheckPattern abcdefghij, cdefg,  1 = false
  * CheckPattern abcdefghij, cdefg,  2 = true
  * CheckPattern abcdefghij, cdefg,  3 = false
  * CheckPattern abcdefghij, cdefg, 50 = false
  * CheckPattern abcdefghij, cdefg, -2 = false
  * CheckPattern abcdefghij, cdefg,  9 = false
  * 
  * 
  * @param pInput the string to be searched
  * @param pPattern the pattern 
  * @param pIndexStart the index from which the check starts
  * @return true if the pattern in the input string matches the pattern starting at the given index
  */
  private static boolean checkPattern( String pInput, String pPattern, int pIndexStart )
  {
    if ( pInput == null ) return false;

    if ( pPattern == null ) return false;

    if ( pIndexStart < 0 ) return false;

    if ( pIndexStart > pInput.length() ) return false;

    if ( pIndexStart + pPattern.length() > pInput.length() ) return false;

    for ( int cur_index_pattern = 0; cur_index_pattern < pPattern.length(); cur_index_pattern++ )
    {
      if ( pInput.charAt( pIndexStart + cur_index_pattern ) != pPattern.charAt( cur_index_pattern ) )
      {
        return false;
      }
    }

    return true;
  }

  private static String testSpliceString( String pInput, String pStrPattern, int pIndexStart )
  {
    //testSpliceString( "aaabbbccc", "aaa", 0 );
    //testSpliceString( "aaabbbccc", "bbb", 3 );
    //testSpliceString( "aaabbbccc", "ccc", 6 );
    //
    //testSpliceString( "aaabbbccc", "a", 0 );
    //testSpliceString( "aaabbbccc", "b", 3 );
    //testSpliceString( "aaabbbccc", "c", 8 );
    //testSpliceString( "aaabbbccc", "cc", 6 );
    //testSpliceString( "aaabbbccc", "cc", 7 );
    //
    //testSpliceString( "aaabbbccc", null, 3 );
    //testSpliceString( null, "aaa", 3 );
    //
    //testSpliceString( "aaabbbccc", "aaa", -5 );
    //testSpliceString( "aaabbbccc", "aaa", 50 );

    String result_string = spliceString( pInput, pStrPattern, pIndexStart );

    wl( "spliceString( " + pInput + ", " + pStrPattern + ", " + pIndexStart + " ) = " + result_string );

    return result_string;
  }

  /**
   * <pre>
   * Something like that:
   *  
   * spliceString( aaabbbccc, aaa,   0 ) = ...bbbccc
   * spliceString( aaabbbccc, bbb,   3 ) = aaa...ccc
   * spliceString( aaabbbccc, ccc,   6 ) = aaabbb...
   * spliceString( aaabbbccc, a,     0 ) = .aabbbccc
   * spliceString( aaabbbccc, b,     3 ) = aaa.bbccc
   * spliceString( aaabbbccc, c,     8 ) = aaabbbcc.
   * spliceString( aaabbbccc, cc,    6 ) = aaabbb..c
   * spliceString( aaabbbccc, cc,    7 ) = aaabbbc..
   * spliceString( aaabbbccc, null,  3 ) = aaabbbccc
   * spliceString( null,      aaa,   3 ) = null
   * spliceString( aaabbbccc, aaa,  -5 ) = aaabbbccc
   * spliceString( aaabbbccc, aaa,  50 ) = aaabbbccc
   * 
   * </pre>
   * 
   * 
   * @param pInput 
   * @param pStrPattern
   * @param pIndexStart
   * @return
   */
  private static String spliceString( String pInput, String pStrPattern, int pIndexStart )
  {
    if ( ( pInput == null ) || ( pStrPattern == null ) ) return pInput;

    if ( ( pInput.isBlank() ) || ( pStrPattern.isBlank() ) ) return pInput;

    if ( pIndexStart < 0 ) return pInput;

    if ( pIndexStart > pInput.length() ) return pInput;

    String str_first_part = "";

    if ( pIndexStart > 0 )
    {
      str_first_part = pInput.substring( 0, pIndexStart );
    }

    String str_second_part = "";

    if ( ( pIndexStart + pStrPattern.length() ) < ( pInput.length() ) )
    {
      str_second_part = pInput.substring( pIndexStart + pStrPattern.length() );
    }

    return str_first_part + ".".repeat( pStrPattern.length() ) + str_second_part;
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day19_input.txt";

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

/*
 * ... some old stuff, which doesn't work
 */

//
//
//int count_replace_algo = 0;
//
//boolean knz_do_replace_alg = false;
//
//String input_csv_device = null;
//
//List< String > not_possible_designs_list_a = new ArrayList< String >();
//
//for ( int index = 1; index < pListInput.size(); index++ )
//{
//String input_str = pListInput.get( index );
//
///*
// * Blank line seperates the Devices
// */
//if ( input_str.trim().isBlank() )
//{
//  input_csv_device = null;
//}
//else
//{
//  if ( knz_do_replace_alg )
//  {
//    if ( checkReplaceItAlgorithmDebug( input_str, towel_pattern_list, 0, 0 ) == 1 )
//    {
//      count_replace_algo++;
//    }
//    else
//    {//
//int count_replace_algo = 0;
//
//boolean knz_do_replace_alg = false;
//
//String input_csv_device = null;
//
//List< String > not_possible_designs_list_a = new ArrayList< String >();
//
//for ( int index = 1; index < pListInput.size(); index++ )
//{
//String input_str = pListInput.get( index );
//
///*
//* Blank line seperates the Devices
//*/
//if ( input_str.trim().isBlank() )
//{
//input_csv_device = null;
//}
//else
//{
//if ( knz_do_replace_alg )
//{
//  if ( checkReplaceItAlgorithmDebug( input_str, towel_pattern_list, 0, 0 ) == 1 )
//  {
//    count_replace_algo++;
//  }
//  else
//  {
//    not_possible_designs_list_a.add( input_str );
//  }
//}
//else
//{
//  not_possible_designs_list_a.add( input_str );
//}
//}
//}
//
//wl( "" );
//
//List< String > not_possible_designs_list_b = new ArrayList< String >();
//
//int count_hash_map_algo = 0;
//
//for ( String input_str : not_possible_designs_list_a )
//{
//wl( "" );
//wl( "" );
//
//if ( checkHashMapAlgorithmDebug( input_str, towel_pattern_list ) == 1 )
//{
//count_hash_map_algo++;
//}
//else
//{
//not_possible_designs_list_b.add( input_str );
//}
//}
//
//for ( String xxx : not_possible_designs_list_b )
//{
//wl( xxx );
//}
//
//wl( "" );
//wl( "" );
//wl( "" );
//wl( "count_replace_algo " + count_replace_algo );
//wl( "count_hash_map_algo " + count_hash_map_algo );
//wl( "sum                 " + ( count_hash_map_algo + count_replace_algo ) );
//wl( "" );
//      not_possible_designs_list_a.add( input_str );
//    }
//  }
//  else
//  {
//    not_possible_designs_list_a.add( input_str );
//  }
//}
//}
//
//wl( "" );
//
//List< String > not_possible_designs_list_b = new ArrayList< String >();
//
//int count_hash_map_algo = 0;
//
//for ( String input_str : not_possible_designs_list_a )
//{
//wl( "" );
//wl( "" );
//
//if ( checkHashMapAlgorithmDebug( input_str, towel_pattern_list ) == 1 )
//{
//  count_hash_map_algo++;
//}
//else
//{
//  not_possible_designs_list_b.add( input_str );
//}
//}
//
//for ( String xxx : not_possible_designs_list_b )
//{
//wl( xxx );
//}
//
//wl( "" );
//wl( "" );
//wl( "" );
//wl( "count_replace_algo " + count_replace_algo );
//wl( "count_hash_map_algo " + count_hash_map_algo );
//wl( "sum                 " + ( count_hash_map_algo + count_replace_algo ) );
//wl( "" );
//wl( "" );

//private static int checkHashMapAlgorithmDebug( String pDesign, List< String > pPattern )
//{
//if ( pDesign.isBlank() )
//{
//  return 0;
//}
//
//String cur_pattern = "";
//
//int index_search = 0;
//
//String new_string = "";
//
//String start_chars = " W w ";
//
//boolean last_p_found = false;
//
//while ( index_search < pDesign.length() )
//{
//  /*
//   * Check if exists
//   */
//  //boolean start_char = start_chars.indexOf( "" + pDesign.charAt( index_search ) ) > 0;
//
//  boolean pattern_found = pPattern.contains( cur_pattern + pDesign.charAt( index_search ) );
//
//  if ( pattern_found )
//  {
//    /*
//     * If the current pattern + the new pattern exists in the available pattern,
//     * then expand the current pattern to that pattern.
//     */
//    cur_pattern += pDesign.charAt( index_search );
//
//    index_search++;
//    /*
//     * Muster wurde gefunden
//     * Das aktuelle Muster wird um das aktuelle Zeichen am index ergänzt.
//     * Index wird auf das nächste Zeichen erhöht.
//     * Das nächste Zeichen wurde noch nicht geprüft
//     */
//  }
//  else
//  {
//    /*
//     * Ist es 1 Zeichen
//     */
//    if ( ( cur_pattern + pDesign.charAt( index_search ) ).length() == 1 )
//    {
//      /*
//       * 
//       */
//      if ( start_chars.indexOf( "" + pDesign.charAt( index_search ) ) >= 0 )
//      {
//
//      }
//    }
//  }
//
//  if ( pattern_found == false )
//  {
//    /*
//     * Muster wurde nicht gefunden
//     */
//
//    if ( last_p_found == false )
//    {
//
////      if ( start_char )
////      {
////        index_search++;
////      }
////      else
//      {
//        wl( "############  " + pDesign.substring( index_search ) );
//        return 0;
//      }
//    }
//
//    /*
//     * Wurde das letzte Muster gefunden ?
//     */
//    if ( last_p_found )
//    {
//      /*
//       * Wenn das letzte Muster gefunden wurde, wird der debug_string 
//       * bis zum index_search mit - Zeichen ausgenullt.
//       */
//      new_string = "-".repeat( index_search ) + pDesign.substring( index_search );
//
//      index_s = index_search - 1;
//    }
//
//    /*
//     * Wurde das aktuelle Muster nicht gefunden, wird der pattern string ausgenullt.
//     */
//    cur_pattern = "";
//
//    /*
//     * Es wird der Index nicht erhöht, das Zeichen an der aktuellen position noch 
//     * einmal für sich alleine geprüft werden muss. 
//     * 
//     */
//  }
//
//  wl( String.format( "%-70s %5d %-10s  %-20s", pDesign, index_search, cur_pattern, new_string ) );
//
//  last_p_found = pattern_found;
//
////  index_search++;
//}
//
//if ( pPattern.contains( cur_pattern ) )
//{
//  new_string = "-".repeat( index_search - 1 ) + pDesign.substring( index_search );
//
//  wl( String.format( "%-70s %5d %-10s  %-20s  OK  ", pDesign, index_search, cur_pattern, new_string ) );
//
//  return 1;
//}
//
//wl( String.format( "%-70s %5d %-10s  %-20s  --  ", pDesign, index_search, cur_pattern, new_string ) );
//
//return 0;
//}
//
//private static int checkHashMapAlgorithmDebugsave( String pDesign, List< String > pPattern )
//{
//if ( pDesign.isBlank() )
//{
//  return 0;
//}
//
//String cur_pattern = "";
//
//int index_search = 0;
//
//String new_string = "";
//
//while ( index_search < pDesign.length() )
//{
//  /*
//   * Check if exists
//   */
//  if ( pPattern.contains( cur_pattern + pDesign.charAt( index_search ) ) )
//  {
//    /*
//     * If the current pattern + the new pattern exists in the available pattern,
//     * then expand the current pattern to that pattern.
//     */
//    cur_pattern += pDesign.charAt( index_search );
//  }
//  else
//  {
//    if ( cur_pattern.isBlank() )
//    {
//      // did not match - pattern is blank - no action
//
//      /*
//       * The current pattern is blank.
//       * The char at index search was not found
//       * There is no towel pattern with a single character 
//       * The Design is not possible
//       */
//
//      wl( String.format( "%-70s %5d %-10s  %-20s  --  ", pDesign, index_search, cur_pattern, new_string ) );
//
//      wl( "Pattern not possible " + pDesign.charAt( index_search ) + " not found in available patterns" );
//      return 0;
//    }
//    else
//    {
//      /*
//       * If the current pattern + the new pattern doesn't exists in the available pattern,
//       * then ....
//       * ... the current pattern is found
//       * ... delete from the new string the characters in the found pattern length
//       * ... start a new pattern with the current character at index-search.
//       */
//
//      new_string = "-".repeat( index_search ) + pDesign.substring( index_search );
//
//      wl( String.format( "%-70s %5d %-10s  %-20s", pDesign, index_search, cur_pattern, new_string ) );
//
//      cur_pattern = "";
//    }
//  }
//
//  index_search++;
//}
//
//if ( pPattern.contains( cur_pattern ) )
//{
//  new_string = "-".repeat( index_search - 1 ) + pDesign.substring( index_search );
//
//  wl( String.format( "%-70s %5d %-10s  %-20s  OK  ", pDesign, index_search, cur_pattern, new_string ) );
//
//  return 1;
//}
//
//wl( String.format( "%-70s %5d %-10s  %-20s  --  ", pDesign, index_search, cur_pattern, new_string ) );
//
//return 0;
//}
//
//private static int checkReplaceItAlgorithmDebug( String pDesign, List< String > pPattern, int pIndexList, int pResultSoFar )
//{
//int check_that = checkReplaceItAlgorithmRec( pDesign, pPattern, pIndexList, pResultSoFar );
//
//wl( "check_that " + pDesign + " " + ( check_that == 1 ? "  OK   " : " not pos " ) );
//
//return check_that;
//}
//
//private static int checkReplaceItAlgorithmRec( String pDesign, List< String > pPattern, int pIndexList, int pResultSoFar )
//{
//if ( pIndexList >= pPattern.size() )
//{
//  return pResultSoFar;
//}
//
//String cur_pattern = pPattern.get( pIndexList );
//
//int index_pattern_in_design = pDesign.indexOf( cur_pattern );
//
//String new_string = null;
//
//if ( index_pattern_in_design >= 0 )
//{
//  new_string = pDesign.replace( cur_pattern, "-".repeat( cur_pattern.length() ) );
//
//  wl( String.format( "%-70s  %5d %-10s  %5d %6s %-20s", pDesign, pIndexList, cur_pattern, index_pattern_in_design, ( index_pattern_in_design >= 0 ? " OK " : " -- " ), new_string ) );
//}
//else
//{
//  new_string = pDesign;
//}
//
//boolean only_x = new_string.matches( "-+" );
//
//if ( new_string.isBlank() || only_x )
//{
//  return 1;
//}
//
//return checkReplaceItAlgorithmRec( new_string, pPattern, pIndexList + 1, 0 );
//}
