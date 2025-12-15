package de.ea234.aoc2024.day02;

public class Day02RedNosedReports
{
  /*
   * --- Day 2: Red-Nosed Reports ---
   * https://adventofcode.com/2024/day/2
   * 
   */

  private static boolean KNZ_DEBUG = false;

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

    long valid_rep = 0;

    int report_nr = 0;

    for ( String input_str : pList )
    {
      report_nr++;

      String[] arr_inp = input_str.trim().replaceAll( " {2,}", " " ).split( " " );

      long check_result = 0;

      int x_indes = -1;

      while ( ( x_indes < arr_inp.length ) && ( check_result == 0 ) )
      {
        check_result = checkReport( arr_inp, x_indes );

        x_indes++;
      }

      wl( "" );
      wld( "Report Index " + report_nr + "  " + ( check_result == 0 ? "####  Unsafe  ####" : "OK" ) + " - " + input_str );

      datei_debug_str += "\nReport Index " + report_nr + "  " + ( check_result == 0 ? "####" : " OK " ) + " - " + input_str;

      valid_rep += check_result;
    }

    datei_debug_str += "\n\nReport Count " + report_nr;
    datei_debug_str += "\nSafe   " + valid_rep;
    datei_debug_str += "\nUnsafe " + ( report_nr - valid_rep );

    String datei_input = "/mnt/hd4tbb/daten/test_result_part_" + ( pKnzCalcPart2 ? "2" : "1" ) + ".txt";

    schreibeDatei( datei_input, datei_debug_str );

    wld( "" );
    wld( "" );
    wld( "Report Count " + report_nr );
    wld( "Safe   " + valid_rep );
    wld( "Unsafe " + ( report_nr - valid_rep ) );
  }

  private static long checkReport( String[] pArrayInput, int pOmitIndex )
  {
    long akt_value_input = 0;

    long last_value_input = getLong( pArrayInput[ 0 ], 0 );

    long diff = 0;

    boolean fkt_erg = true;

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
      last_value_input = getLong( pArrayInput[ 1 ], 0 );
    }

    while ( ( list_index < pArrayInput.length ) && ( fkt_erg ) )
    {
      if ( list_index != pOmitIndex )
      {
        akt_value_input = getLong( pArrayInput[ list_index ], 0 );

        if ( akt_value_input > last_value_input )
        {
          /*
           * Von der groesseren Zahl wird die kleinere abgezogen. 
           */
          diff = akt_value_input - last_value_input;

          if ( ( diff < 1 ) || ( diff > 3 ) )
          {
            fkt_erg = false; // Erlaubte Differenz strimmt nicht
          }
        }
        else
        {
          fkt_erg = false; // Aktueller Wert ist kleiner als der letzte Wert 
        }

        /*
         * Ist die Zahl gültig, ist die Stelle gültig. 
         * Die aktuelle Zahl kann zur letzten Zahl gemacht werden
         */
        last_value_input = akt_value_input;
      }

      list_index++;

      //last_value_input = akt_value_input;
    }

    if ( fkt_erg )
    {
      return 1;
    }

    wl( "---- Check Descending -----" );

    fkt_erg = true;

    /*
     * Absteigend
     * [51, 52, 55, 58, 60, 61, 62, 61]
     * Die aktuelle Zahl muss kleiner als die letzte Zahl sein.
     * Die letzte Zahl muss groesser als die aktuelle Zahl sein.
     */
    list_index = 1;
    last_value_input = getLong( pArrayInput[ 0 ], 0 );

    if ( pOmitIndex == 0 )
    {
      list_index = 2;
      last_value_input = getLong( pArrayInput[ 1 ], 0 );
    }

    while ( ( list_index < pArrayInput.length ) && ( fkt_erg ) )
    {
      if ( list_index != pOmitIndex )
      {
        akt_value_input = getLong( pArrayInput[ list_index ], 0 );

        if ( akt_value_input < last_value_input )
        {
          /*
           * Von der groesseren Zahl wird die kleinere abgezogen.
           * Last-Value ist hierbei die groessere Zahl 
           */
          diff = last_value_input - akt_value_input;

          if ( ( diff < 1 ) || ( diff > 3 ) )
          {
            fkt_erg = false; // Erlaubte Differenz strimmt nicht
          }
        }
        else
        {
          fkt_erg = false; // Aktuelle Zahl ist groesser  
        }

        /*
         * Ist die Zahl gültig, ist die Stelle gültig. 
         * Die aktuelle Zahl kann zur letzten Zahl gemacht werden
         */
        last_value_input = akt_value_input;
      }

      list_index++;
    }

    if ( fkt_erg )
    {
      return 1;
    }

    return 0;
  }

  private static List< String > getListProd()
  {
    int row_count = 0;

    List< String > string_array = new ArrayList< String >();

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day02_input.txt";

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

  private static void wld( String pString )
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
      wld( "Fehler: errSchreibeDatei" + err_inst.getLocalizedMessage() );
    }

    return false;
  }
}
