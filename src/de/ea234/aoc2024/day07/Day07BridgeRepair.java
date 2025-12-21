package de.ea234.aoc2024.day07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day07BridgeRepair
{

  /*
   * --- Day 7: Bridge Repair ---
   * https://adventofcode.com/2024/day/7
   * 
   * 
   * Answer:
   * 
   * 
   * -------------------------------------------------------------------------------------
   * 
   * checkEquation( "13: 2 3 3 5" )
   * 
   * Size                  5
   * Symbols needed        3
   * Symbols combinations  8
   * 
   * Nr 0 2 * 3 * 3 * 5 = 90
   * Nr 1 2 + 3 * 3 * 5 = 75
   * Nr 2 2 * 3 + 3 * 5 = 45
   * Nr 3 2 + 3 + 3 * 5 = 40
   * Nr 4 2 * 3 * 3 + 5 = 23
   * Nr 5 2 + 3 * 3 + 5 = 20
   * Nr 6 2 * 3 + 3 + 5 = 14
   * Nr 7 2 + 3 + 3 + 5 = 13
   * 
   * bit_mask_cur_value   7 00000011
   * calc_result          13
   * calc_equation        2 + 3 + 3 + 5
   * 
   * pEquation 13: 2 3 3 5
   * 
   * 
   * -------------------------------------------------------------------------------------
   * 
   * checkEquation( "3267: 81 40 27" )
   * 
   * Size                  4
   * Symbols needed        2
   * Symbols combinations  4
   * 
   * Nr 0 81 * 40 * 27 = 87480
   * Nr 1 81 + 40 * 27 = 3267
   * 
   * bit_mask_cur_value   1 00000000
   * calc_result          3267
   * calc_equation        81 + 40 * 27
   * 
   * pEquation 3267: 81 40 27
   * 
   * 
   * Result  OK 
   * 
   * 
   * -------------------------------------------------------------------------------------
   * 
   * checkEquation( "21037: 9 7 18 13" )
   * 
   * Size                  5
   * Symbols needed        3
   * Symbols combinations  8
   * 
   * Nr 0 9 * 7 * 18 * 13 = 14742
   * Nr 1 9 + 7 * 18 * 13 = 3744
   * Nr 2 9 * 7 + 18 * 13 = 1053
   * Nr 3 9 + 7 + 18 * 13 = 442
   * Nr 4 9 * 7 * 18 + 13 = 1147
   * Nr 5 9 + 7 * 18 + 13 = 301
   * Nr 6 9 * 7 + 18 + 13 = 94
   * Nr 7 9 + 7 + 18 + 13 = 47
   * 
   * bit_mask_cur_value   7 00000011
   * calc_result          47
   * calc_equation        9 + 7 + 18 + 13
   * 
   * pEquation 21037: 9 7 18 13
   * 
   * 
   * Result  ######### NOT OK #######
   * 
   * 
   * -------------------------------------------------------------------------------------
   * 
   * checkEquation( "161011: 16 10 13" )
   * 
   * Size                  4
   * Symbols needed        2
   * Symbols combinations  4
   * 
   * Nr 0 16 * 10 * 13 = 2080
   * Nr 1 16 + 10 * 13 = 338
   * Nr 2 16 * 10 + 13 = 173
   * Nr 3 16 + 10 + 13 = 39
   * 
   * bit_mask_cur_value   3 00000001
   * calc_result          39
   * calc_equation        16 + 10 + 13
   * 
   * pEquation 161011: 16 10 13
   * 
   * 
   * Result  ######### NOT OK #######
   * 
   * 
   */

  public static void main( String[] args )
  {
    String test_content = "190: 10 19,3267: 81 40 27,83: 17 5,156: 15 6,7290: 6 8 6 15,161011: 16 10 13,192: 17 8 14,21037: 9 7 18 13,292: 11 6 16 20";

    List< String > test_content_list = Arrays.stream( test_content.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    //calcGridPart1( test_content_list, true );

    // checkEquationPart1( "21037: 9 7 18 13" );
    //
    // checkEquationPart1( "292: 11 6 16 20" );
    //
    // checkEquationPart1( "3267: 81 40 27" );
    //
    // checkEquationPart1( "10: 1 2 3 4", true );
    //
//    checkEquationPart1( "13: 2 3 3 5", true );
    //

    //calcGridPart1( getListProd(), true );

    testNumberSystem();
  }

  private static long g_result_value = 0;

  private static long g_result_ok    = 0;

  private static long g_result_err   = 0;

  private static void calcGridPart1( List< String > pList, boolean pKnzDebug )
  {
    g_result_value = 0;
    g_result_ok = 0;
    g_result_err = 0;

    for ( String equation_input_str : pList )
    {
      checkEquationPart1( equation_input_str, pKnzDebug );
    }

    wl( "g_result_value =>" + g_result_value + "<" );
    wl( "g_result_ok    =>" + g_result_ok + "<" );
    wl( "g_result_err   =>" + g_result_err + "<" );
  }

  private static boolean checkEquationPart1( String pEquation, boolean pKnzDebug )
  {
    if ( pEquation != null )
    {
      String[] equation_parts_strings = pEquation.replace( ":", "" ).split( " " );

      long[] equation_parts_values = Arrays.stream( equation_parts_strings ).mapToLong( Long::parseLong ).toArray();

      long symbols_needed = ( equation_parts_values.length - 2 );

      /*
       * Bit-Shift to the right for the power of 2
       */
      long symbols_max = ( 1l << symbols_needed );

      if ( pKnzDebug )
      {
        wl( "" );
        wl( "-------------------------------------------------------------------------------------" );
        wl( "" );
        wl( "checkEquation( \"" + pEquation + "\" )" );
        wl( "" );
        wl( "Size                  " + equation_parts_values.length );
        wl( "Symbols needed        " + symbols_needed );
        wl( "Symbols combinations  " + symbols_max );
        wl( "" );
      }

      String calc_equation = "";

      long calc_result = 0;

      long bit_mask_max_value = symbols_max;
      long bit_mask_cur_value = 0;

      while ( ( bit_mask_cur_value < bit_mask_max_value ) && ( calc_result != equation_parts_values[ 0 ] ) )
      {
        int calc_index = 2;
        long calc_bit_mask = bit_mask_cur_value;

        /*
         * calc_result starts with the value at index-position 1
         */
        calc_result = equation_parts_values[ 1 ];
        calc_equation = "" + equation_parts_values[ 1 ];

        //while ( ( calc_index < longs.length ) && ( calc_result < longs[ 0 ] ) )
        while ( ( calc_index < equation_parts_values.length ) )
        {
          if ( ( calc_bit_mask & 1 ) == 1 )
          {
            calc_result += equation_parts_values[ calc_index ];

            calc_equation += " + " + equation_parts_values[ calc_index ];
          }
          else
          {
            calc_result *= equation_parts_values[ calc_index ];

            calc_equation += " * " + equation_parts_values[ calc_index ];
          }

          calc_bit_mask = calc_bit_mask >> 1;

          calc_index++;
        }

        if ( pKnzDebug )
        {
          wl( "Nr " + bit_mask_cur_value + " " + calc_equation + " = " + calc_result );
        }

        bit_mask_cur_value++;
      }

      bit_mask_cur_value--;

      if ( pKnzDebug )
      {
        wl( "" );
        wl( "bit_mask_cur_value   " + bit_mask_cur_value + " " + getBitPattern( bit_mask_cur_value ) );
        wl( "calc_result          " + calc_result );
        wl( "calc_equation        " + calc_equation );
        wl( "" );
        wl( "pEquation " + pEquation );
        wl( "" );
        wl( "" );
        wl( "Result " + ( ( calc_result == equation_parts_values[ 0 ] ) ? " OK " : " ######### NOT OK #######" ) );
        wl( "" );
        wl( "" );
      }

      if ( calc_result == equation_parts_values[ 0 ] )
      {
        g_result_value += equation_parts_values[ 0 ];
        g_result_ok++;

      }
      else
      {
        g_result_err++;
      }

    }

    return false;
  }

  private static boolean checkEquationPart2( String pEquation, boolean pKnzDebug )
  {
    if ( pEquation != null )
    {
      String[] equation_parts_strings = pEquation.replace( ":", "" ).split( " " );

      long[] equation_parts_values = Arrays.stream( equation_parts_strings ).mapToLong( Long::parseLong ).toArray();

      long symbols_needed = ( equation_parts_values.length - 2 );

      /*
       * Bit-Shift to the right for the power of 2
       */
      long symbols_max = ( 1l << symbols_needed );

      if ( pKnzDebug )
      {
        wl( "" );
        wl( "-------------------------------------------------------------------------------------" );
        wl( "" );
        wl( "checkEquation( \"" + pEquation + "\" )" );
        wl( "" );
        wl( "Size                  " + equation_parts_values.length );
        wl( "Symbols needed        " + symbols_needed );
        wl( "Symbols combinations  " + symbols_max );
        wl( "" );
      }

      String calc_equation = "";

      long calc_result = 0;

      long bit_mask_max_value = symbols_max;
      long bit_mask_cur_value = 0;

      while ( ( bit_mask_cur_value < bit_mask_max_value ) && ( calc_result != equation_parts_values[ 0 ] ) )
      {
        int calc_index = 2;
        long calc_bit_mask = bit_mask_cur_value;

        /*
         * calc_result starts with the value at index-position 1
         */
        calc_result = equation_parts_values[ 1 ];
        calc_equation = "" + equation_parts_values[ 1 ];

        //while ( ( calc_index < longs.length ) && ( calc_result < longs[ 0 ] ) )
        while ( ( calc_index < equation_parts_values.length ) )
        {
          if ( ( calc_bit_mask & 1 ) == 1 )
          {
            calc_result += equation_parts_values[ calc_index ];

            calc_equation += " + " + equation_parts_values[ calc_index ];
          }
          else
          {
            calc_result *= equation_parts_values[ calc_index ];

            calc_equation += " * " + equation_parts_values[ calc_index ];
          }

          calc_bit_mask = calc_bit_mask >> 1;

          calc_index++;
        }

        if ( pKnzDebug )
        {
          wl( "Nr " + bit_mask_cur_value + " " + calc_equation + " = " + calc_result );
        }

        bit_mask_cur_value++;
      }

      bit_mask_cur_value--;

      if ( pKnzDebug )
      {
        wl( "" );
        wl( "bit_mask_cur_value   " + bit_mask_cur_value + " " + getBitPattern( bit_mask_cur_value ) );
        wl( "calc_result          " + calc_result );
        wl( "calc_equation        " + calc_equation );
        wl( "" );
        wl( "pEquation " + pEquation );
        wl( "" );
        wl( "" );
        wl( "Result " + ( ( calc_result == equation_parts_values[ 0 ] ) ? " OK " : " ######### NOT OK #######" ) );
        wl( "" );
        wl( "" );
      }

      if ( calc_result == equation_parts_values[ 0 ] )
      {
        g_result_value += equation_parts_values[ 0 ];
        g_result_ok++;

      }
      else
      {
        g_result_err++;
      }
    }

    return false;
  }

  private static String getBitPattern( long pLong )
  {
    long nummer_x = pLong;

    String bit_string = "";

    for ( int bit = 0; bit < 8; bit++ )
    {
      nummer_x = nummer_x >> 1;

      bit_string = ( ( nummer_x & 1 ) == 1 ? "1" : "0" ) + bit_string;
    }

    return bit_string;
  }

  private static final String              MY_NUMBER_SYSTEM_HM_PRAEFIX = "SYS_VAL_";

//
//  private static final char                SYMBOL_CONCATENATION        = 'A';
//
//  private static final char                SYMBOL_PLUS                 = 'B';
//
//  private static final char                SYMBOL_MULTIPLICATION       = 'C';
//

  private static final char                SYMBOL_CONCATENATION        = '|';

  private static final char                SYMBOL_PLUS                 = '+';

  private static final char                SYMBOL_MULTIPLICATION       = '*';

  private static final String              MY_NUMBER_SYSTEM_SYMBOLS    = "" + SYMBOL_CONCATENATION + SYMBOL_PLUS + SYMBOL_MULTIPLICATION;

  private static final int                 MY_NUMBER_SYSTEM_BASE       = 3;

  private static final int                 MY_NUMBER_SYSTEM_START_EXP  = 16;

  private static HashMap< String, String > hash_map_number_system      = new HashMap< String, String >();

  private static void testNumberSystem()
  {
    for ( long nr = 0; nr < 255; nr++ )
    {
      String result_val = getNumberSystemValue( nr );

      wl( "nr " + nr + " = " + result_val );
    }

    String pEquation = "7290: 6 8 6 15 "; // can be made true using 6 * 8 || 6 * 15.

    pEquation = "192: 17 8 14 "; // can be made true using 6 * 8 || 6 * 15.
    pEquation = "156: 15 6"; // can be made true through a single concatenation: 15 || 6 = 156.

    String[] equation_parts_strings = pEquation.replace( ":", "" ).split( " " );

    long[] equation_parts_values = Arrays.stream( equation_parts_strings ).mapToLong( Long::parseLong ).toArray();

    long symbols_needed = ( equation_parts_values.length - 2 );

    /*
     * Bit-Shift to the right for the power of 2
     * 
     * 8 <symbol> 8
     * 
     * One Symbol is needed.
     * The number system consist out of 3 Symbols.
     * There are 3 possibilitis
     * 
     * 8 <symbol> 8 <symbol> 8
     * Two symbols are needed
     * The number system consist out of 3 Symbols.
     * there are 9 possible combinations
     * 
     * 
     */
    long symbols_max = ( 1l << symbols_needed );

    /*
     * 10101010101010101 = 87381
     */
    long current_number = 87381;

    current_number = 123;
    current_number = 3;

    String combine_pattern_string = getNumberSystemValue( current_number );

    wl( "current_number " + current_number + "  combine_pattern " + combine_pattern_string );

    int flag_concatination = 0;

    int equation_parts_idx = 1; // 0 = result , 1 = first val of eq

    String equation_new_combined = "";

    int combine_pattern_idx = 0;

    while ( ( combine_pattern_idx < combine_pattern_string.length() ) && ( equation_parts_idx < equation_parts_values.length ) )
    {
      /*
       * place the value from the equation parts into the new equation
       */
      equation_new_combined += equation_parts_strings[ equation_parts_idx ];

      /*
       * increment the equation part index
       */
      equation_parts_idx++;

      /*
       * Do we need another operator from the combining string?
       * 
       * This is the case, when the equation part index is lower then the 
       * length of the equation part array.
       */
      if ( equation_parts_idx < equation_parts_values.length )
      {
        switch ( combine_pattern_string.charAt( combine_pattern_idx ) )
        {
          case SYMBOL_CONCATENATION :

            wl( " " + combine_pattern_idx + " = " + "|" + " SYMBOL_CONCATENATION  = " + SYMBOL_CONCATENATION );

            flag_concatination = 1;

            break;

          case SYMBOL_PLUS :

            wl( " " + combine_pattern_idx + " = " + "+" + " SYMBOL_PLUS           = " + SYMBOL_PLUS );

            equation_new_combined += " + ";

            break;

          case SYMBOL_MULTIPLICATION :

            wl( " " + combine_pattern_idx + " = " + "*" + " SYMBOL_MULTIPLICATION = " + SYMBOL_MULTIPLICATION );

            equation_new_combined += " * ";

            break;
        }

        combine_pattern_idx++;
      }
    }

    wl( "equation input     " + pEquation );
    wl( "equation comb      " + equation_new_combined );
    wl( "flag_concatination " + flag_concatination );

    long equation_result = getEquationResult( equation_new_combined );

    wl( "Equation Result " + equation_result );

    if ( equation_result == equation_parts_values[ 0 ] )
    {
      wl( "############### OK " );
    }

    /*
     * Keep in mind, that for convinience reasons the result from the
     * function "getNumberSystemValue" is reversed.
     * 
     */
    testFromNumberSystem( "" + SYMBOL_CONCATENATION + SYMBOL_PLUS ); // not really: the result is reversed
    testFromNumberSystem( "" + SYMBOL_PLUS + SYMBOL_CONCATENATION );
    testFromNumberSystem( "" + SYMBOL_MULTIPLICATION + SYMBOL_PLUS + SYMBOL_MULTIPLICATION );

    testGetEquationResult( "123" );
  }

  private static long testGetEquationResult( String pEquation )
  {
    long equation_result = getEquationResult( pEquation );

    wl( "getEquationResult( \"" + pEquation + "\" ) = " + equation_result );

    return equation_result;
  }

  private static long getEquationResult( String pEquation )
  {
    String[] equation_parts_strings = pEquation.split( " " );

    long value_akt = Long.parseLong( equation_parts_strings[ 0 ] );

    int knz_math_op = 0;

    for ( int index_eq_part = 1; index_eq_part < equation_parts_strings.length; index_eq_part++ )
    {
      String current_equation_part = equation_parts_strings[ index_eq_part ];

      //wl( "current_equation_part = " + current_equation_part );

      if ( current_equation_part.equals( "+" ) )
      {
        knz_math_op = 0;
      }
      else if ( current_equation_part.equals( "*" ) )
      {
        knz_math_op = 1;
      }
      else
      {
        if ( knz_math_op == 0 )
        {
          value_akt += Long.parseLong( current_equation_part );
        }
        else if ( knz_math_op == 1 )
        {
          value_akt *= Long.parseLong( current_equation_part );
        }
        else
        {
          wl( "#### should not have happened " + pEquation );
        }
      }
    }

    return value_akt;
  }

  private static void testFromNumberSystem( String combine_pattern_string )
  {
    String f_zahlen_sys = fromZahlensystem( combine_pattern_string, MY_NUMBER_SYSTEM_SYMBOLS, MY_NUMBER_SYSTEM_BASE );

    wl( " combine_pattern_string " + combine_pattern_string + " = " + f_zahlen_sys );
  }

  private static String getNumberSystemValue( long pNumber )
  {
    String result_str = hash_map_number_system.get( MY_NUMBER_SYSTEM_HM_PRAEFIX + pNumber );

    if ( result_str == null )
    {
      result_str = getReversedString( toZahlensystem( BigInteger.valueOf( pNumber ), MY_NUMBER_SYSTEM_SYMBOLS, MY_NUMBER_SYSTEM_BASE, MY_NUMBER_SYSTEM_START_EXP ) );

      hash_map_number_system.put( MY_NUMBER_SYSTEM_HM_PRAEFIX + pNumber, result_str );
    }

    return result_str;
  }

  private static String getReversedString( String pString )
  {
    if ( pString == null )
    {
      return null;
    }

    return new StringBuilder( pString ).reverse().toString();
  }

  /**
   * <pre>
   * Berechnet aus der uebergebenen Zahl, eine Repraesentation in dem ebenfalls 
   * uebergebenen Zielzahlensystem.
   * 
   * Das Zielzahlensystem wird durch die Grundmenge und der Basiszahl definiert.
   * Dabei muss die Laenge der Grundmenge muss groesser/gleich der Basiszahl sein.
   * Die Basiszahl kann kleiner als die Grundmenge sein. Beim Binaersystem wird 
   * die Grundmenge von Hexadezimal uebergeben, die Basiszahl ist nur 2.
   * 
   *  
   *    Eingabe 0           Hex "0"      Binaer "000000000"      Alphabeth "AAAAA"        
   *    Eingabe 1           Hex "1"      Binaer "000000001"      Alphabeth "AAAAB"        
   *    Eingabe 2           Hex "2"      Binaer "000000010"      Alphabeth "AAAAC"        
   *    Eingabe 3           Hex "3"      Binaer "000000011"      Alphabeth "AAAAD"        
   *    Eingabe 4           Hex "4"      Binaer "000000100"      Alphabeth "AAAAE"        
   *    Eingabe 5           Hex "5"      Binaer "000000101"      Alphabeth "AAAAF"        
   *    Eingabe 6           Hex "6"      Binaer "000000110"      Alphabeth "AAAAG"        
   *    Eingabe 7           Hex "7"      Binaer "000000111"      Alphabeth "AAAAH"        
   *    Eingabe 8           Hex "8"      Binaer "000001000"      Alphabeth "AAAAI"        
   *    Eingabe 9           Hex "9"      Binaer "000001001"      Alphabeth "AAAAJ"        
   *    Eingabe 10          Hex "A"      Binaer "000001010"      Alphabeth "AAAAK"        
   *    Eingabe 11          Hex "B"      Binaer "000001011"      Alphabeth "AAAAL"        
   *    Eingabe 12          Hex "C"      Binaer "000001100"      Alphabeth "AAAAM"        
   *    Eingabe 13          Hex "D"      Binaer "000001101"      Alphabeth "AAAAN"        
   *    Eingabe 14          Hex "E"      Binaer "000001110"      Alphabeth "AAAAO"        
   *    Eingabe 15          Hex "F"      Binaer "000001111"      Alphabeth "AAAAP"        
   *    Eingabe 16          Hex "10"     Binaer "000010000"      Alphabeth "AAAAQ"        
   *    Eingabe 17          Hex "11"     Binaer "000010001"      Alphabeth "AAAAR"        
   *    Eingabe 205         Hex "CD"     Binaer "011001101"      Alphabeth "AAAHX"        
   *    Eingabe 206         Hex "CE"     Binaer "011001110"      Alphabeth "AAAHY"        
   *    Eingabe 234         Hex "EA"     Binaer "011101010"      Alphabeth "AAAJA"        
   *    Eingabe 80000       Hex "13880"  Binaer "010011100010000000"  Alphabeth "AEOIY"
   *    
   * Es wird hier mit der Klasse BigInteger gearbeitet. Bis zu einer Zahl mit 2500 Stellen,
   * ist die Umrechenzeit eine Sekunde. Ab 5000 Stellen 2 Sekunden, bei 11500 Stellen sind 
   * es 16 Sekunden.
   * </pre>
   * 
   * @param pZahl die umzurechnende Zahl im Dezimalsystem
   * @param pGrundmenge Grundmenge des zu verwendenden (Zahlen)systems
   * @param pBasisZahl die Basiszahl des Zahlensystemes
   * @param pStartExponent der gewuenschte Startexponent (fuehrende Nullen, -1 wenn kein Startexponent gesetzt sein soll)
   * @return einen String mit der hexadezimalen Repraesentation der Eingabe
   */
  private static String toZahlensystem( BigInteger pZahl, String pGrundmenge, int pBasisZahl, int pStartExponent )
  {
    String ergebnis = "";
    int akt_exponent = 0; // der aktuelle Exponent in der Schleife
    BigInteger akt_element_nr = null; // gibt die Position des naechsten Elementes in der Grundmenge an
    BigInteger akt_potenz = null; // vielfaches von der Basiszahl hoch dem aktuellem Exponenten
    BigInteger akt_zahl = pZahl; // Ausgangszahl
    BigInteger big_int_basis_zahl = new BigInteger( "" + pBasisZahl ); //

    /* 
     * Parameterpruefungen 
     * pGrundmenge muss gesetzt sein und muss mindestens auch zwei Zeichen umfassen.
     * Ist das nicht der Fall wird ein Leerstring zurueckgegeben.
     */
    if ( ( pGrundmenge == null ) || ( pGrundmenge.trim().length() < 2 ) )
    {
      return ergebnis;
    }

    /*
     * Eine Basiszahl von kleiner 2 macht keinen Sinn
     */
    if ( pBasisZahl < 2 )
    {
      return ergebnis;
    }

    /*
     * Die laenge der Grundmenge darf nicht kleiner als die uebergebene Basiszahl sein.
     * Die Basiszahl teilt dieser Funktion die Anzahl der zu verwendenden Zeichen aus 
     * der Grundmenge mit. 
     */
    if ( pGrundmenge.length() < pBasisZahl )
    {
      return ergebnis;
    }

    /*
     * Fehlervermeidung: Ist das hinzuzufuegende Zeichen gleich dem Endezeichen, 
     *                   ist der Endindex zu gross.
     * 
     * 08:57:00 |  Eingabe 25          Hex "19"     Binaer "000011001"      Alphabeth "Z"
     * 08:57:00 | Fehler String index out of range: 27
     * java.lang.StringIndexOutOfBoundsException: String index out of range: 27
     *   at java.lang.String.substring(String.java:1765)
     *   at de.system.FkZahlSysteme.getStringFmZahlensystem(FkZahlSysteme.java:164)
     *   at de.system.FkZahlSysteme.getAlphaBeth(FkZahlSysteme.java:32)
     */
    String x_grundmenge = pGrundmenge + "        ";

    try
    {
      /*
       * Berechnung des Exponentens, dessen Potenz mit der Basiszahl die 
       * Eingabezahl uebersteigt. 
       */
      akt_potenz = new BigInteger( "" + pBasisZahl );

      akt_exponent = 1;

      while ( akt_potenz.compareTo( akt_zahl ) < 0 )
      {
        akt_potenz = akt_potenz.multiply( big_int_basis_zahl );

        akt_exponent++;
      }

      /*
       * Der Funktion kann ein Startexponent vorgegeben werden um dem Ergebnis
       * eine feste laenge vorgeben zu koennen. Dieses sind dann fuehrende Nullen,
       * bzw. eben das Zeichen fuer Null.
       * 
       * Der Startexponent wird aber nur dann genommen, wenn dieser groesser Null 
       * und auch groesser als der zuvor berechnete Exponentenwert ist.
       * 
       * Wuerde der Startexponent generell den berechneten Startexponenten
       * ueberschreiben, kommt es es zu einem Fehler (z.B. bei getBinaer( 8000, 8 )).
       * (akt_element_nr wuerde zu gross werden)
       * 
       * Des weiteren wird durch diese Pruefung verhindert, das ein negativer 
       * Startexponent uebernommen wird, da der berechnete Exponent immer groesser 
       * als Null ist.
       */
      if ( pStartExponent > akt_exponent )
      {
        akt_exponent = pStartExponent;
      }

      /*
       * Der Exponent wird runtergezaehlt, damit zuerst die grossen  
       * Potenzen von der uebergebenen Zahl abgezogen werden. 
       */
      while ( akt_exponent >= 0 )
      {
        /*
         * Berechnung der aktuellen Potenz 
         */
        akt_potenz = big_int_basis_zahl.pow( akt_exponent );

        /*
         * Berechnung des naechsten Elementes der Grundmenge. Dieses ist 
         * genau das Ergebnis der Rechnung:
         * 
         *           Restzahl geteilt durch die aktuelle Potenz.
         *
         * Die aktuelle Potenz ist ein vielfaches der Basiszahl. Das Ergebnis
         * der Teilung ist eine Zahl zwischen 0 und der Basiszahl des Zahlen-
         * systemes. Somit bestimt die Teilung, das Zeichen aus der uebergebenen
         * Grundmenge.
         * 
         * Diese Berechnung verbraucht bei "groesseren" Zahlen (ab 2500 Stellen) 
         * die meiste Zeit.
         */
        akt_element_nr = akt_zahl.divide( akt_potenz );

        /*
         * Berechnung des Abzugsbetrages aus Potens mal akt_element_nr. Die 
         * aktuelle Zahl wird um den Abzugsbetrag vermindert.
         */
        if ( akt_element_nr.intValue() > 0 )
        {
          akt_zahl = akt_zahl.subtract( akt_element_nr.multiply( akt_potenz ) );
        }

        /*
         * Ist das aktuelle Element 0, muessen einige Besonderheiten beruecksichtigt werden.
         * 
         * Nur dann hinzufuegen, wenn
         * ... der StartExponent groesser null ist       --> fuehrende Nullen
         * ... der aktuelle Exponent null ist            --> die Eingabezahl an sich war 0 
         * ... der Ergebnisstring schon Zeichen enthaelt --> unterdrueckung von fuehrenden Nullen 
         *                                                   (Startexponent muss in diesem Fall 0 sein)
         *                                                 
         * Ist das aktuelle Elment groesser als 0, wird das Zeichen aus der Grundmenge dem 
         * Ergebnisstring hinzugefuegt. 
         */
        if ( akt_element_nr.intValue() == 0 )
        {
          if ( ( ergebnis.length() > 0 ) || ( pStartExponent > 0 ) || ( akt_exponent == 0 ) ) // nur dann hinzufuegen, wenn schon was drinsteht
          {
            ergebnis += x_grundmenge.substring( 0, 1 );
          }
        }
        else
        {
          try
          {
            ergebnis += x_grundmenge.substring( akt_element_nr.intValue(), akt_element_nr.intValue() + 1 );
          }
          catch ( StringIndexOutOfBoundsException err_inst )
          {
            //
            ergebnis += " -### Fehler " + akt_element_nr.intValue() + " ###- ";
          }
        }

        /*
         * Den Exponentenzaehler um eins vermindern
         */
        akt_exponent--;
      }
    }
    catch ( Exception err_inst )
    {
      wl( "Fehler " + err_inst.getMessage() );
    }

    akt_element_nr = null;
    akt_potenz = null;
    akt_zahl = null;
    big_int_basis_zahl = null;

    return ergebnis;
  }

  /**
   * <pre>
   * Berechnet aus der uebergebenen Zahl des uebergebenen Zahlensystems, die 
   * Zahl im dezimalen Zahlensystem. 
   * 
   * Das Zielzahlensystem wird durch die Grundmenge und der Basiszahl definiert.
   * Dabei muss die Laenge der Grundmenge muss groesser/gleich der Basiszahl sein.
   * Die Basiszahl kann kleiner als die Grundmenge sein. Beim Binaersystem wird 
   * die Grundmenge von Hexadezimal uebergeben, die Basiszahl ist nur 2.
   * 
   * Die uebergebene Zahl darf nur aus den Elementen der Grundmenge bestehen.
   * Dabei muss die uebergebene Basiszahl beruecksichtigt werden. Es kann zu einem
   * Fehler kommen wenn die binaere Zahl 010EA umgerechnet wird. Die Zeichen 
   * EA gehoeren in diesem Fall nicht zur Grundmenge.
   * 
   *  hex 0    = "0"    # alpha A    = "0"    # bin 000000000  = "0"   
   *  hex 1    = "1"    # alpha B    = "1"    # bin 000000001  = "1"   
   *  hex 2    = "2"    # alpha C    = "2"    # bin 000000010  = "2"   
   *  hex 3    = "3"    # alpha D    = "3"    # bin 000000011  = "3"   
   *  hex 4    = "4"    # alpha E    = "4"    # bin 000000100  = "4"   
   *  hex 5    = "5"    # alpha F    = "5"    # bin 000000101  = "5"   
   *  hex 6    = "6"    # alpha G    = "6"    # bin 000000110  = "6"   
   *  hex 7    = "7"    # alpha H    = "7"    # bin 000000111  = "7"   
   *  hex 8    = "8"    # alpha I    = "8"    # bin 000001000  = "8"   
   *  hex 9    = "9"    # alpha J    = "9"    # bin 000001001  = "9"   
   *  hex A    = "10"   # alpha K    = "10"   # bin 000001010  = "10"  
   *  hex B    = "11"   # alpha L    = "11"   # bin 000001011  = "11"  
   *  hex C    = "12"   # alpha M    = "12"   # bin 000001100  = "12"  
   *  hex D    = "13"   # alpha N    = "13"   # bin 000001101  = "13"  
   *  hex E    = "14"   # alpha O    = "14"   # bin 000001110  = "14"  
   *  hex F    = "15"   # alpha P    = "15"   # bin 000001111  = "15"  
   *  hex 10   = "16"   # alpha Q    = "16"   # bin 000010000  = "16"  
   *  hex 11   = "17"   # alpha R    = "17"   # bin 000010001  = "17"  
   *  hex 12   = "18"   # alpha S    = "18"   # bin 000010010  = "18"  
   *  hex 13   = "19"   # alpha T    = "19"   # bin 000010011  = "19"
   *  hex C6   = "198"  # alpha HQ   = "198"  # bin 011000110  = "198" 
   *  hex C7   = "199"  # alpha HR   = "199"  # bin 011000111  = "199" 
   *  hex EA   = "234"  # alpha JA   = "234"  # bin 011101010  = "234"
   *   
   *  hex 13880 = "80000" # alpha EOIY = "80000" # bin 010011100010000000 = "80000"
   *  
   * </pre>
   * 
   * @param pEingabe die zu wandelnde Zahl im definierten Zahlensystem
   * @param pGrundmenge die zu benutzende Grundmenge fuer die Konvertierung
   * @param pBasisZahl die Basiszahl 
   * @return die gewandelte Zahl im dezimalen Zahlensystem als String
   */
  private static String fromZahlensystem( String pEingabe, String pGrundmenge, int pBasisZahl )
  {
    int zaehler = 0; // Zeichenzaehler fuer die Postion in der Eingabe
    BigInteger ergebnis = new BigInteger( "0" ); // Summe aus den einzelnen Stellenberechnungsergebnissen
    BigInteger multiplikator = null; // der Multiplikator (=Zeichenposition der Grundmenge)
    BigInteger akt_potenz = new BigInteger( "1" ); // die aktuelle Potenz, beim ersten Durchlauf ist diese 1
    BigInteger basis_zahl = new BigInteger( "" + pBasisZahl ); // Basiszahl als BigInteger

    /*
     * Eine Basiszahl von kleiner 2 macht keinen Sinn.
     * Der Aufrufer bekommt "0" zurueck.
     */
    if ( pBasisZahl < 2 )
    {
      return "0";
    }

    /*
     * Die Anzahl der Zeichen in der Grundmenge bestimmt eigentlich die Basiszahl.
     * 
     * Es kann sein, dass nur ein Teil der Zeichen aus dem String "pGrundmenge" benutzt wird.
     * 
     * Es muss geprueft werden, ob fuer die Basiszahl auch genuegend Zeichen in der Grundmenge 
     * vorhanden sind.
     * 
     * Sind zu wenig Zeichen in der Grundmenge vorhanden, kann die Basiszahl nie erreicht werden.
     * 
     * pGrundmenge = "1234567890", pBasisZahl = 16 => dann fehlen die Zeichen ab 10.
     * 
     * Die Laenge der Grundmenge darf nicht kleiner als die uebergebene Basiszahl sein.
     * 
     * Mit dem Parameter "pBasiszahl", wird dieser Funktion die Anzahl der zu verwendenden 
     * Zeichen aus der Grundmenge mitgeteilt.
     * 
     * Sind zuwenig Zeichen in der Grundmenge vorhanden, bekommt der Aufrufer "0" zurueck. 
     */
    if ( pGrundmenge.length() < pBasisZahl )
    {
      return "0";
    }

    /*
     * Es muss eine Eingabe vorhanden sein.
     */
    if ( ( pEingabe != null ) && ( pEingabe.length() > 0 ) )
    {
      /*
       * Die Schleife beginnt beim letzten Zeichen. In Java starten Zeichenketten 
       * mit der Position 0, daher wird der Zaehler um eins vermindert.
       */
      zaehler = pEingabe.length() - 1;

      while ( zaehler >= 0 )
      {
        /*
         * Die Position des Zeichens in der Grundmenge, ist gleich der 
         * dezimalen Basiszahl der Grundmenge.
         * 
         * Grundmenge  = 0123456789ABCDEF
         * akt_zeichen = A
         * 
         * A ist das 10te zeichen der Grundmenge, also ist der Multiplikator 10
         */
        multiplikator = new BigInteger( "" + pGrundmenge.indexOf( pEingabe.charAt( zaehler ) ) );

        /*
         * Die aktuelle Potenz wird mit dem Multiplikator multipliziert
         * und ergibt somit den dezimalen Zielwert, welcher dem Ergebnis
         * hinzuaddiert wird.
         * 
         * Der Multiplikator darf nicht negativ sein. Der Multiplikator 
         * kann negativ werden, wenn ungueltige Zeichen in der Eingabe
         * vorhanden sind (Zeichen die nicht in der Grundmenge vorhanden sind).
         */
        if ( multiplikator.intValue() >= 0 )
        {
          ergebnis = ergebnis.add( akt_potenz.multiply( multiplikator ) );
        }

        /*
         * Ermittlung der Potenz fuer die naechste Zeichenposition des
         * Eingabestrings. Beim ersten Durchlauf ist die Potenz 1, bei 
         * jedem weiteren Durchlauf wird die bestehende Potenz mal der 
         * Basiszahl multipliziert.  
         */
        akt_potenz = akt_potenz.multiply( basis_zahl );

        /*
         * Der Zaehler wird um eins vermindert, damit die naechsthoehere
         * Potenz des Zahlensystems berechnet werden kann. 
         */
        zaehler--;
      }
    }

    multiplikator = null;
    akt_potenz = null;
    basis_zahl = null;

    return "" + ergebnis;
  }

  private static List< String > getListProd()
  {
    int row_count = 0;

    List< String > string_array = new ArrayList< String >();

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day07_input.txt";

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
