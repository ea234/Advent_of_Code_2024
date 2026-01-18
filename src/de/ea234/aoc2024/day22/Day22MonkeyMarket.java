package de.ea234.aoc2024.day22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day22MonkeyMarket
{

  /*
   * --- Day 22: Monkey Market ---
   * https://adventofcode.com/2024/day/22
   *
   * https://www.youtube.com/watch?v=Afh6YcMiayw
   * 
   * https://www.reddit.com/r/adventofcode/comments/1hjroap/2024_day_22_solutions
   * 
   *      1        15887950 - price old 3  new 0  diff  -3  ,-3 
   *      2        16495136 - price old 0  new 6  diff   6  ,-3,6 
   *      3          527345 - price old 6  new 5  diff  -1  ,-3,6,-1 
   *      4          704524 - price old 5  new 4  diff  -1  ,-3,6,-1,-1 
   *      5         1553684 - price old 4  new 4  diff   0  ,-3,6,-1,-1,0 
   *      6        12683156 - price old 4  new 6  diff   2  ,-3,6,-1,-1,0,2 
   *      7        11100544 - price old 6  new 4  diff  -2  ,-3,6,-1,-1,0,2,-2 
   *      8        12249484 - price old 4  new 4  diff   0  ,-3,6,-1,-1,0,2,-2,0 
   *      9         7753432 - price old 4  new 2  diff  -2  ,-3,6,-1,-1,0,2,-2,0,-2 
   *     10         5908254 - price old 2  new 4  diff   2  ,-3,6,-1,-1,0,2,-2,0,-2,2 
   *     11         2731930 - price old 4  new 0  diff  -4  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4 
   *     12        10144594 - price old 0  new 4  diff   4  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4 
   *     13        13647660 - price old 4  new 0  diff  -4  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4 
   *     14         8741773 - price old 0  new 3  diff   3  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3 
   *     15        12399819 - price old 3  new 9  diff   6  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6 
   *     16        13000251 - price old 9  new 1  diff  -8  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8 
   *     17        10534524 - price old 1  new 4  diff   3  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3 
   *     18         4776055 - price old 4  new 5  diff   1  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3,1 
   *     19        10594906 - price old 5  new 6  diff   1  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3,1,1 
   *     20        14976316 - price old 6  new 6  diff   0  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3,1,1,0 
   *     21        14716013 - price old 6  new 3  diff  -3  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3,1,1,0,-3 
   *     22        10038164 - price old 3  new 4  diff   1  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3,1,1,0,-3,1 
   *     23         5394656 - price old 4  new 6  diff   2  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3,1,1,0,-3,1,2 
   *     24         1926055 - price old 6  new 5  diff  -1  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3,1,1,0,-3,1,2,-1 
   *     25         7734836 - price old 5  new 6  diff   1  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3,1,1,0,-3,1,2,-1,1 
   *     26         4939629 - price old 6  new 9  diff   3  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3,1,1,0,-3,1,2,-1,1,3 
   *     27         9961484 - price old 9  new 4  diff  -5  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3,1,1,0,-3,1,2,-1,1,3,-5 
   *     28         8676116 - price old 4  new 6  diff   2  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3,1,1,0,-3,1,2,-1,1,3,-5,2 
   *     29         8479524 - price old 6  new 4  diff  -2  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3,1,1,0,-3,1,2,-1,1,3,-5,2,-2 
   *     30        14995317 - price old 4  new 7  diff   3  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3,1,1,0,-3,1,2,-1,1,3,-5,2,-2,3 
   *     31         8686244 - price old 7  new 4  diff  -3  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3,1,1,0,-3,1,2,-1,1,3,-5,2,-2,3,-3 
   *     32         3594937 - price old 4  new 7  diff   3  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3,1,1,0,-3,1,2,-1,1,3,-5,2,-2,3,-3,3 
   *     33         4097886 - price old 7  new 6  diff  -1  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3,1,1,0,-3,1,2,-1,1,3,-5,2,-2,3,-3,3,-1 
   *     34        13199960 - price old 6  new 0  diff  -6  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3,1,1,0,-3,1,2,-1,1,3,-5,2,-2,3,-3,3,-1,-6 
   *     35         9089978 - price old 0  new 8  diff   8  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3,1,1,0,-3,1,2,-1,1,3,-5,2,-2,3,-3,3,-1,-6,8 
   *     36         5896147 - price old 8  new 7  diff  -1  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3,1,1,0,-3,1,2,-1,1,3,-5,2,-2,3,-3,3,-1,-6,8,-1 
   *     37         4029195 - price old 7  new 5  diff  -2  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3,1,1,0,-3,1,2,-1,1,3,-5,2,-2,3,-3,3,-1,-6,8,-1,-2 
   *     38         4230149 - price old 5  new 9  diff   4  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3,1,1,0,-3,1,2,-1,1,3,-5,2,-2,3,-3,3,-1,-6,8,-1,-2,4 
   *     39        15329583 - price old 9  new 3  diff  -6  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3,1,1,0,-3,1,2,-1,1,3,-5,2,-2,3,-3,3,-1,-6,8,-1,-2,4,-6 
   *     40         6881272 - price old 3  new 2  diff  -1  ,-3,6,-1,-1,0,2,-2,0,-2,2,-4,4,-4,3,6,-8,3,1,1,0,-3,1,2,-1,1,3,-5,2,-2,3,-3,3,-1,-6,8,-1,-2,4,-6,-1 
   * 
   */

  private static final String SEQUENZE_TO_SEARCH = "-2,1,-1,3";
  //private static final String SEQUENZE_TO_SEARCH = "4,4,-9,5";;

  public static void main( String[] d )
  {
    String test_input = "1,10,100,2024";

    test_input = "1,2,3,2024";
    test_input = "1";

    calculatePart01( test_input, true, 2000 );
//
//    calculatePart01( getListProd(), false , 2000 );
//
  }

  private static void calculatePart01( String pString, boolean pKnzDebug, int pMaxLoop )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    calculatePart01( converted_string_list, pKnzDebug, pMaxLoop );
  }

  private static void calculatePart01( List< String > pListInput, boolean pKnzDebug, int pMaxLoop )
  {
    /*
     * *******************************************************************************************************
     * Initializing Variables
     * *******************************************************************************************************
     */
    List< Day22Buyer > list_buyer = new ArrayList< Day22Buyer >();

    /*
     * *******************************************************************************************************
     * Generating List of Buyer's
     * *******************************************************************************************************
     */

    for ( String input_str : pListInput )
    {
      list_buyer.add( new Day22Buyer( input_str, SEQUENZE_TO_SEARCH ) );
    }

    /*
     * *******************************************************************************************************
     * Do Loops
     * *******************************************************************************************************
     */
    int max_loop = pMaxLoop;

    for ( int loop_count = 0; loop_count < max_loop; loop_count++ )
    {
      for ( Day22Buyer buyer : list_buyer )
      {
        buyer.generateNextSecretNumber();

        if ( pKnzDebug )
        {
          if ( buyer.getPrice() == 7 ) 
          {
          wl( buyer.toString() );
          }
        }
      }
    }

    /*
     * *******************************************************************************************************
     * Calculate the sum of the secret numbers
     * *******************************************************************************************************
     */

    wl( "" );
    wl( "----------------------------------------------------------------------" );
    wl( "" );

    long sum_secret_nr = 0;

    long sum_seq_price = 0;

    for ( Day22Buyer buyer : list_buyer )
    {
      sum_secret_nr += buyer.getSecretNumberCur();

      sum_seq_price += buyer.getSeqPrice();

      if ( pKnzDebug )
      {
        wl( buyer.toString() );
      }
    }

    wl( "" );
    wl( String.format( "sum_secret_nr %11d  <- Result Part 1", sum_secret_nr ) );
    wl( "" );
    wl( String.format( "sum_seq_price %11d  <- Result Part 1", sum_seq_price ) );
    wl( "" );
    wl( "" );
  }

  private static void doTestAlgorithm()
  {
    long secret_number_cur = 123;

    wl( "15887950 =>" + generateNextSecretNumber1( 0, secret_number_cur ) + "<" );
    wl( "16495136 =>" + generateNextSecretNumber1( 0, last_test_secret_number ) + "<" );
    wl( "527345   =>" + generateNextSecretNumber1( 0, last_test_secret_number ) + "<" );
    wl( "704524   =>" + generateNextSecretNumber1( 0, last_test_secret_number ) + "<" );
    wl( "1553684  =>" + generateNextSecretNumber1( 0, last_test_secret_number ) + "<" );
    wl( "12683156 =>" + generateNextSecretNumber1( 0, last_test_secret_number ) + "<" );
    wl( "11100544 =>" + generateNextSecretNumber1( 0, last_test_secret_number ) + "<" );
    wl( "12249484 =>" + generateNextSecretNumber1( 0, last_test_secret_number ) + "<" );
    wl( "7753432  =>" + generateNextSecretNumber1( 0, last_test_secret_number ) + "<" );
    wl( "5908254  =>" + generateNextSecretNumber1( 0, last_test_secret_number ) + "<" );

    Day22Buyer buyer = new Day22Buyer( "123", SEQUENZE_TO_SEARCH );

    wl( "" );
    wl( "" );
    wl( "" );

    int max_loop = 10;

    for ( int loop_count = 0; loop_count < max_loop; loop_count++ )
    {
      buyer.generateNextSecretNumber();

      wl( buyer.toString() );
    }

    //long a = 42;
    //long b = 15;
    //
    //long c = a ^ b;
    //
    //System.out.println( c );
    //
    //long a1 = 100000000;
    //long b1 = 16777216;
    //
    //long c1 = a1 % b1;
    //
    //System.out.println( c1 );
    //
  }

  private static long last_test_secret_number = 0;

  public static long generateNextSecretNumber1( int seq_nr, long secret_number_cur )
  {
    long s1 = generateNextSecretNumber( 1, secret_number_cur );
    long s2 = generateNextSecretNumber( 2, s1 );
    long s3 = generateNextSecretNumber( 3, s2 );

    long sn_n = s3; // generateNextSecretNumber( seq_nr, secret_number_cur );

    last_test_secret_number = s3;

    //wl( String.format( "%6d %15d   %15d ", seq_nr, secret_number_cur, sn_n ) );

    return sn_n;
  }

  public static long generateNextSecretNumber( int seq_nr, long secret_number_cur )
  {
    long secret_nr_new = 0;

    if ( seq_nr == 1 )
    {
      secret_nr_new = secret_number_cur * 64;
    }
    else if ( seq_nr == 2 )
    {
      secret_nr_new = secret_number_cur / 32;
    }
    else
    {
      secret_nr_new = secret_number_cur * 2048;

      seq_nr = 0;
    }

    long secret_nr_after_mixing = secret_number_cur ^ secret_nr_new;

    long secret_nr_after_pruning = secret_nr_after_mixing % 16777216;

    return secret_nr_after_pruning;
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day22_input.txt";

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
