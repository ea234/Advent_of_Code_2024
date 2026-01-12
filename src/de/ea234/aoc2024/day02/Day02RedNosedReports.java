package de.ea234.aoc2024.day02;

import java.nio.file.Files;
import java.nio.file.Path;

public class Day02RedNosedReports
{
  /*
   * --- Day 2: Red-Nosed Reports ---
   * https://adventofcode.com/2024/day/2
   * 
   */


  private static boolean KNZ_DEBUG                                 = false;

  private static long    FLAG_REPORT_NOT_VALID                     = 0;

  private static long    FLAG_REPORT_VALID                         = 1;

  private static long    FLAG_OK                                   = 1;

  private static long    FLAG_DIFF_IS_NOT_IN_SPEC                  = 2;

  private static long    FLAG_NEXT_NUMBER_GREATER_THAN_PREDECESSOR = 3;

  private static long    FLAG_NEXT_NUMBER_LOWER_THAN_PREDECESSOR   = 4;

  public static void main( String[] args )
  {
    String test_content = "7 6 4 2 1,1 2 7 8 9,9 7 6 2 1,1 3 2 4 5,8 6 4 4 1,1 3 6 7 9";

    List< String > test_content_list = Arrays.stream( test_content.trim().replaceAll( ",[ ]{2,}", "," ).replaceAll( " {2,}", " " ).split( "," ) ).collect( Collectors.toList() );

    KNZ_DEBUG = true;

    calcPart1( getListProd(), false );

    calcPart1( getListProd(), true );
  }

  public static void calcPart1( List< String > pList, boolean pKnzCalcPart2 )
  {
    String datei_debug_str = "";

    long sum_valid_reports = 0;

    int report_nr = 0;

    for ( String input_str : pList )
    {
      report_nr++;

      String[] arr_inp = input_str.trim().replaceAll( " {2,}", " " ).split( " " );

      long check_result = FLAG_REPORT_NOT_VALID;

      int index_omit_position = -1;

      while ( ( index_omit_position < arr_inp.length ) && ( check_result == FLAG_REPORT_NOT_VALID ) )
      {
        check_result = checkReport( arr_inp, index_omit_position );

        if ( pKnzCalcPart2 )
        {
          index_omit_position++;
        }
        else
        {
          index_omit_position = Integer.MAX_VALUE;
        }
      }

      wl( "" );
      wl( "Report Index " + report_nr + "  " + ( check_result == 0 ? "####  Unsafe  ####" : "OK" ) + " - " + input_str );

      datei_debug_str += "\nReport Index " + report_nr + " " + ( check_result == 0 ? "####" : " OK " ) + " - " + input_str;

      sum_valid_reports += check_result;
    }

    datei_debug_str += "\n\nReport Count " + report_nr;
    datei_debug_str += "\nSafe   " + sum_valid_reports;
    datei_debug_str += "\nUnsafe " + ( report_nr - sum_valid_reports );

    String datei_input = "/mnt/hd4tbb/daten/test_result_part_" + ( pKnzCalcPart2 ? "2" : "1" ) + ".txt";

    schreibeDatei( datei_input, datei_debug_str );

    wl( "" );
    wl( "" );
    wl( "Report Count " + report_nr );
    wl( "Safe   " + sum_valid_reports );
    wl( "Unsafe " + ( report_nr - sum_valid_reports ) );
  }

  private static long checkReport( String[] pArrayInput, int pOmitIndex )
  {
    long current_input_value = 0;

    long report_status = FLAG_OK;

    wl( "---- Check Ascending -----" );

    /*
     * Aufsteigend
     * Der letzte Wert muss kleiner als der aktuelle Wert sein.
     * Stimmen beide Werte ueberein, wurde sich eine Differenz von 0 ergeben = ausschluss 
     */
    int list_index = 1;

    if ( pOmitIndex == 0 )
    {
      list_index = 2;
    }

    long last_input_value = getLong( pArrayInput[ list_index - 1 ], 0 );

    while ( ( list_index < pArrayInput.length ) && ( report_status == FLAG_OK ) )
    {
      if ( list_index != pOmitIndex )
      {
        current_input_value = getLong( pArrayInput[ list_index ], 0 );

        if ( current_input_value > last_input_value )
        {
          /*
           * Von der groesseren Zahl wird die kleinere abgezogen. 
           */
          long difference_value = current_input_value - last_input_value;

          if ( ( difference_value < 1 ) || ( difference_value > 3 ) )
          {
            report_status = FLAG_DIFF_IS_NOT_IN_SPEC; // Erlaubte Differenz strimmt nicht
          }
        }
        else
        {
          report_status = FLAG_NEXT_NUMBER_LOWER_THAN_PREDECESSOR; // Aktueller Wert ist kleiner als der letzte Wert 
        }

        /*
         * Ist die Zahl gültig, ist die Stelle gültig. 
         * Die aktuelle Zahl kann zur letzten Zahl gemacht werden
         */
        last_input_value = current_input_value;
      }

      list_index++;
    }

    if ( report_status != FLAG_OK )
    {
      wl( "---- Check Descending -----" );

      report_status = FLAG_OK;

      /*
       * Absteigend
       * Die aktuelle Zahl muss kleiner als die letzte Zahl sein.
       * Die letzte Zahl muss groesser als die aktuelle Zahl sein.
       */
      list_index = 1;

      if ( pOmitIndex == 0 )
      {
        list_index = 2;
      }

      last_input_value = getLong( pArrayInput[ list_index - 1 ], 0 );

      while ( ( list_index < pArrayInput.length ) && ( report_status == FLAG_OK ) )
      {
        if ( list_index != pOmitIndex )
        {
          current_input_value = getLong( pArrayInput[ list_index ], 0 );

          if ( current_input_value < last_input_value )
          {
            /*
             * Von der groesseren Zahl wird die kleinere abgezogen.
             * Last-Value ist hierbei die groessere Zahl 
             */
            long difference_value = last_input_value - current_input_value;

            if ( ( difference_value < 1 ) || ( difference_value > 3 ) )
            {
              report_status = FLAG_DIFF_IS_NOT_IN_SPEC; // Erlaubte Differenz strimmt nicht
            }
          }
          else
          {
            report_status = FLAG_NEXT_NUMBER_GREATER_THAN_PREDECESSOR; // Aktuelle Zahl ist groesser  
          }

          /*
           * Ist die Zahl gültig, ist die Stelle gültig. 
           * Die aktuelle Zahl kann zur letzten Zahl gemacht werden
           */
          last_input_value = current_input_value;
        }

        list_index++;
      }
    }

    if ( report_status == FLAG_OK )
    {
      return FLAG_REPORT_VALID;
    }

    return FLAG_REPORT_NOT_VALID;
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day02_input.txt";

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

  /**
   * <pre>
   * Erstellt die Datei und schreibt dort den "pInhalt" rein.
   * 
   * Ist kein "pInhalt" null wird eine leere Datei erstellt.
   * </pre>
   * 
   * @param pDateiName der Dateiname 
   * @param pInhalt der zu schreibende Inhalt
   * @return TRUE wenn die Datei geschrieben werden konnte, sonst False
   */
  public static boolean schreibeDatei( String pDateiName, String pInhalt )
  {
    try
    {
      FileWriter output_stream = new FileWriter( pDateiName, false );

      if ( pInhalt != null )
      {
        output_stream.write( pInhalt );
      }

      /*
       * Aufruf von "stream.flush()"
       */
      output_stream.flush();

      output_stream.close();

      output_stream = null;

      return true;
    }
    catch ( Exception err_inst )
    {
      wl( "Fehler: errSchreibeDatei" + err_inst.getLocalizedMessage() );
    }

    return false;
  }
}
