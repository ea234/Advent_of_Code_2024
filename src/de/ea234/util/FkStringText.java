package de.ea234.util;

public class FkStringText
{
  /**
   * <pre>
   * Formatiert einen Text spaltenweise
   * 
   * Auf Wortebene wird aufgrund der kuerzesten Differenz zur berechneten BIS-Position getrennt.
   * 
   * 
   * String string_eingabe = "A B C D E F G H ";
   * String string_praefix = "...";
   * 
   * int    anzahl_zeichen_je_zeile = string_eingabe.length();
   * 
   * String string_ausgabe = FkStringText.getStringMaxCols( string_eingabe + string_eingabe + string_eingabe + string_eingabe + " A      Z", anzahl_zeichen_je_zeile, string_praefix, "\n" );
   * 
   * System.out.println( string_ausgabe );
   * 
   *                     =          10        20        30        40        50        60        70        80        90 
   *                     = 12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012
   *                     = A B C D E F G H A B C D E F G H A B C D E F G H A B C D E F G H  A      Z
   *  Breite          16 =                |               |               |               |               |
   * 
   * Von     0 bis    17 = A B C D E F G H A
   * Von    18 bis    35 = B C D E F G H A B
   * Von    36 bis    53 = C D E F G H A B C
   * Von    54 bis    70 = D E F G H  A    
   * Von    72 bis    73 = Z
   * 
   * A B C D E F G H A
   * ...B C D E F G H A B
   * ...C D E F G H A B C
   * ...D E F G H  A    
   * ...Z
   * 
   * </pre>
   * 
   * @param pEingabe der zu formartierende Text
   * @param pAnzahlZeichenJeZeile die Anzahl der Zeichen je Zeile
   * @param pEinzug der Einzug, welcher ab Zeile 2 hinzugefuegt wird
   * @param pNewLineZeichen das zu benutzende New-Line-Zeichen (ab Zeile 2)
   * @return den formartierten Text
   */
  public static String getStringMaxCols( String pEingabe, int pAnzahlZeichenJeZeile, String pEinzug, String pNewLineZeichen )
  {
    boolean knz_debug_ausgabe = false;

    char char_leer_zeichen = ' ';
    char char_zeilenumbruch = '\n';

    String str_ergebnis = "";
    String str_neue_zeile = "";
    String my_cr = "";

    int trenn_position_ab = -1;

    int trenn_position_bis_init = -1;
    int trenn_position_bis_plus = 0;
    int trenn_position_bis_minus = -1;

    int max_ueberspringen_anzahl = 5;
    int max_ueberspringen_pos = 0;

    int zaehler = 0;

    /* 
     * Pruefung: Parameter "pAnzahlZeichenJeZeile" kleiner gleich 10?
     * Ist der Parameter kleiner der Mindesspaltenanzahl von 10, wird die
     * Anzahl der der Spalten auf die Vorgabe von 10 Stellen gesetzt.
     */
    if ( pAnzahlZeichenJeZeile <= 10 )
    {
      pAnzahlZeichenJeZeile = 10;
    }

//    if ( knz_debug_ausgabe )
//    {
//      FkLogger.wl( "" );
//      FkLogger.wl( "    " + FkString.right( "       ", 5 ) + "     " + FkString.right( "       ", 5 ) + " =          10        20        30        40        50        60        70        80        90        100       110       120       130       140       150       160       170       180" );
//      FkLogger.wl( "    " + FkString.right( "       ", 5 ) + "     " + FkString.right( "       ", 5 ) + " = 123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890" );
//      FkLogger.wl( "    " + FkString.right( "       ", 5 ) + "     " + FkString.right( "       ", 5 ) + " = " + FkString.left( pEingabe, 180 ) );
//
//      String breite_s = FkString.nMal( pAnzahlZeichenJeZeile - 1, " " ) + "|";
//
//      FkLogger.wl( " Breite  " + FkString.right( "                " + +pAnzahlZeichenJeZeile, 10 ) + " = " + breite_s + breite_s + ( pAnzahlZeichenJeZeile < 50 ? breite_s : "" ) + ( pAnzahlZeichenJeZeile < 40 ? breite_s : "" ) + ( pAnzahlZeichenJeZeile < 30 ? breite_s : "" ) + ( pAnzahlZeichenJeZeile <= 20 ? breite_s : "" ) + ( pAnzahlZeichenJeZeile <= 15 ? breite_s + breite_s + breite_s + breite_s + breite_s + breite_s + breite_s : "" ) );
//      FkLogger.wl( "" );
//    }

    /*
     * Pruefung: Parameter "pEingabe" ungleich "null" ?
     * 
     * Ist der Parameter "pEingabe" gleich "null", bekommt der Aufrufer den 
     * mit einem Leerstring initialisierten Ergebnisstring zurueck.
     * 
     * Ist der Parameter "pEingabe" ungleich "null" wird die Verarbeitung gestartet.
     */
    if ( pEingabe != null )
    {
      int max_str_pos = pEingabe.length() - 1;

      char akt_char = char_leer_zeichen;

      /* 
       * Die Schleife laeuft solange wie
       * ... die BIS-Position noch kleiner der Laenge der Eingabe ist.
       * ... der Endlosschleifenverhinderungszaehler kleiner 32123 ist.
       */
      while ( ( trenn_position_bis_plus < max_str_pos ) && ( zaehler < 32123 ) )
      {
        /* 
         * Startposition
         * 
         * Die Position ab welcher die neue Zeile aus der Eingabe herausgetrennt 
         * wird, ist die letzte Bis-Trennposition. 
         * 
         * Die BIS-Position wurde bei der Deklaration mit "0" initialisiert. 
         * 
         * Die BIS-Position ist nie Bestandteil der aktuellen Zeile.
         * (Bei der letzten Zeile evtl. schon)
         */
        trenn_position_ab = trenn_position_bis_plus;

        /*
         * Es wird das Zeichen an der Startposition gelesen 
         */
        akt_char = pEingabe.charAt( trenn_position_ab );

        /*
         * Leerzeichen am Zeilenstart ueberlesen ?
         * 
         * Pruefung: Zeichen an Startpositon ungleich Zeilenumbruch ?
         * 
         * Ist das Zeichen an der Startposition ein Zeilenumbruch, handelt es 
         * sich bei der aktuell anstehenden Zeile um eine Leerzeile. Es ist 
         * in diesem Fall nicht notwendig, die nachfolgenden Zeichen zu pruefen.
         * 
         * Ist das Zeichen an der Startposition kein Zeilenumbruch, werden
         * eventuell vorhandene Leerzeichen am Start uebersprungen. 
         * 
         * Es wird nur eine festgelegt Anzahl von Leerzeichen uebersprungen.
         * Werden Leerzeichen in der Anzahl der Zeilenbreite uebersprungen, 
         * wird dieses im Ergebnis auch zu einer Leerzeile.  
         * 
         * Nach dem Ueberspringen der Leerzeichen, muss nochmals geprueft
         * werden, ob an der Startposition ein Zeilenumbruchszeichen steht.
         */
        if ( akt_char != char_zeilenumbruch )
        {
          /*
           * Es wird die Position berechnet, bis zu welcher die Leerzeichen 
           * maximal uebersprungen werden sollen.
           */
          max_ueberspringen_pos = trenn_position_ab + max_ueberspringen_anzahl;

          /*
           * Liegt die Max-Ueberspringen-Pos hinter dem Stringende, wird diese 
           * Position auf das Stringende gelegt.
           */
          if ( max_ueberspringen_pos > max_str_pos )
          {
            max_ueberspringen_pos = max_str_pos;
          }

          /*
           * While-Schleife fuer das Ueberspringen der Leerzeichen am Ende der aktuellen Zeile.
           * 
           * Die While-Schleife laeuft solange, wie
           * ... das aktuelle Zeichen an der Leseposition noch ein Leerzeichen ist
           * ... die aktuelle Position noch kleiner als die berechnete Maximalposition 
           *     fuer die aktuelle Zeile ist.
           */
          while ( ( trenn_position_ab < max_ueberspringen_pos ) && ( akt_char == char_leer_zeichen ) )
          {
            /*
             * Trennposition erhoehen
             */
            trenn_position_ab++;

            /*
             * Zeichen an der Trennposition lesen
             */
            akt_char = pEingabe.charAt( trenn_position_ab );
          }

          /*
           * Pruefung: Maximale Anzahl der zu ueberspringenden Leerzeichen eingehalten ?
           * 
           * Ist die AB-Position gleich der berechneten Grenzpositon fuer das 
           * Ueberspringen, wird die Ab-Positon wieder auf die letzte Bis-Postion
           * gesetzt.
           */
          if ( trenn_position_ab == max_ueberspringen_pos )
          {
            trenn_position_ab = trenn_position_bis_plus;
          }
        }

        /*
         * Pruefung: Start mit Zeilenumbruch ?
         * 
         * Ist das Zeichen an der Abschneideposition ein Zeilenumbruch, ist 
         * die naechste Zeile gefunden.
         * 
         * Die Trennposition bis wird auf das naechste Zeichen gestellt. 
         * 
         * Die neue Zeile ist ein Leerstring, da ein Zeilenumbruch weiter
         * unten hinzugefuegt wird.
         * 
         * Die aktuell hinzuzufuegende Zeile besitzt selber nie einen Zeilenumbruch.
         */
        if ( akt_char == char_zeilenumbruch )
        {
          trenn_position_bis_plus = trenn_position_ab + 1;

          str_neue_zeile = "";
        }
        /*
         * Pruefung: Startposition gleich Stringende ?
         */
        else if ( trenn_position_ab == max_str_pos )
        {
          trenn_position_bis_plus = trenn_position_ab + 1;

          str_neue_zeile = pEingabe.substring( trenn_position_ab );
        }
        else
        {
          /*
           * End-Positon
           * 
           * Es wird die naechste initialie Trennposition berechnet.
           * 
           * Diese berechnet sich aus der Startposition zuzueglich der Anzahl von  
           * Zeichen aus dem Parameter "pAnzahlZeichenJeZeile".
           */
          trenn_position_bis_plus = trenn_position_ab + pAnzahlZeichenJeZeile;

          /*
           * Pruefung: BIS-Position nach Stringende ?
           * 
           * Liegt die berechnete Bis-Positon nach dem Stringende, wird die 
           * Bis-Positon auf das Stringende gesetzt. 
           * 
           * Es muss in diesem Fall nur die Pruefung auf eventuelle 
           * Zeilenumbrueche innerhalb des Teilstrings gemacht werden. 
           */
          if ( trenn_position_bis_plus >= max_str_pos )
          {
            trenn_position_bis_plus = max_str_pos;
          }
          else
          {
            /*
             * Liegt die BIS-Position vor dem Stringende, wird die berechnete 
             * Bis-Positon in der Variablen "trenn_position_bis_init" gespeichert.
             * 
             * Die berechnete BIS-Position wird durch die naechste While-Schleife
             * veraendert. Die Ausgangsposition muss fuer die nachfolgenden 
             * Berechnungen erhalten bleiben.
             */
            trenn_position_bis_init = trenn_position_bis_plus;

            /*
             * Suche nach dem naechsten Leer- oder Newline-Zeichen ab der BIS-Position
             * 
             * Ist das Zeichen an der Bis-Positon schon ein Trennzeichen, wird die 
             * Schleife nicht gestartet.
             * 
             * Befindet sich an der BIS-Position ein anderes Zeichen, wird die Suchschleife gestartet.
             * 
             * Die Suche in der Schleife bewegt sich zum Stringende hin.
             */
            akt_char = pEingabe.charAt( trenn_position_bis_plus );

            while ( ( trenn_position_bis_plus < max_str_pos ) && ( ( akt_char != char_leer_zeichen ) && ( akt_char != char_zeilenumbruch ) ) )
            {
              trenn_position_bis_plus++;

              akt_char = pEingabe.charAt( trenn_position_bis_plus );
            }

            /*
             * Flattersatz vermeiden
             * 
             * Liegt die naechste Trennposition mehr als 5 Zeichen hinter der initialen
             * Startposition wird in einer weiteren Suchschleife, das letzte Trennzeichen
             * ab der BIS-Position gesucht.
             */
            if ( ( trenn_position_bis_plus - trenn_position_bis_init ) > 5 )
            {
              /*
               * Startwert fuer die Suchschleife ist die berechnete initiale Trennpositon.
               */
              trenn_position_bis_minus = trenn_position_bis_init;

              /*
               * Suche nach dem letztem Leer- oder Newline-Zeichen von der BIS-Position
               * 
               * Die Suche in der Schleife bewegt sich zur Startposition hin.
               */
              akt_char = pEingabe.charAt( trenn_position_bis_minus );

              while ( ( trenn_position_bis_minus > trenn_position_ab ) && ( ( akt_char != char_leer_zeichen ) && ( akt_char != char_zeilenumbruch ) ) )
              {
                trenn_position_bis_minus--;

                akt_char = pEingabe.charAt( trenn_position_bis_minus );
              }

              /*
               * Trennpositionsermittlung
               * 
               * Von der initialen BIS-Position werden die Zeichen bis zum naechsten Trennzeichen gezaehlt.
               *  
               * Das einmal in Richtung Startposition und in Richtung Stringende.
               * 
               * Es wird die kleinere Distanz-Position ab der initial berechneten 
               * BIS-Position fuer das Ende der neuen Zeile genommen. 
               */
              if ( ( trenn_position_bis_init - trenn_position_bis_minus ) < ( trenn_position_bis_plus - trenn_position_bis_init ) )
              {
                trenn_position_bis_plus = trenn_position_bis_minus;
              }
            }
          }

          /*
           * Pruefung auf Zeilenumbruch im neuen Teilstring. 
           * 
           * Es wird der erste Zeilenumbruch nach der Startposition gesucht.
           * 
           * Ein Zeilenumbruch zwischen den berechneten Postionen teilt die Eingabe.
           * Damit der eventuell vorhandene Einzug korrekt gesetzt wird, muss der 
           * Zeilenumbruch beruecksichtigt werden. 
           * 
           * Die Suche wird aktuell von der Endposition zur Startposition gesucht. 
           */
          trenn_position_bis_init = -1;

          trenn_position_bis_minus = trenn_position_bis_plus;

          akt_char = pEingabe.charAt( trenn_position_bis_minus );

          while ( trenn_position_bis_minus > trenn_position_ab )
          {
            akt_char = pEingabe.charAt( trenn_position_bis_minus );

            if ( akt_char == char_zeilenumbruch )
            {
              trenn_position_bis_init = trenn_position_bis_minus;
            }

            trenn_position_bis_minus--;
          }

          /*
           * Pruefung: Zeilenumbruch gefunden ?
           * 
           * Wurde ein Zeilenumbruch gefunden, wird die Bis-Positon auf die 
           * Position des Zeilenumbruches gestellt.
           */
          if ( trenn_position_bis_init != -1 )
          {
            trenn_position_bis_plus = trenn_position_bis_init;
          }

          /*
           * Pruefung: BIS-Position nach Stringende ?
           * 
           * Liegt die berechnete BIS-Position auf oder nach dem Stringende, 
           * ist das Ergebnis gleich dem Rest des Eingabestrings ab der 
           * Startposition.
           * 
           * Liegt die berechnete End-Position genau auf dem Stringende, darf 
           * nicht mit der Substring-Funktkion gearbeitet werden, da diese 
           * Funktion nur bis zur angegebenen Position abschneided.
           */
          if ( trenn_position_bis_plus >= max_str_pos )
          {
            str_neue_zeile = pEingabe.substring( trenn_position_ab );
          }
          else
          {
            /*
             * Teilstring liegt im String
             * Liegt die berechnete BIS-Position vor dem Stringende, ist die neue Zeile
             * gleich dem Teilstring ab der AB-Position bis zur BIS-Position.
             * 
             * Die BIS-Position ist nicht Bestandteil des Ergebnisstrings.
             */
            str_neue_zeile = pEingabe.substring( trenn_position_ab, trenn_position_bis_plus );
          }
        }

//        /*
//         * Bei der Debugausgabe werden die Trennpositionen und der String ausgegeben.
//         */
//        if ( knz_debug_ausgabe )
//        {
//          FkLogger.wl( "Von " + FkString.right( "       " + trenn_position_ab, 5 ) + " bis " + FkString.right( "       " + trenn_position_bis_plus, 5 ) + " = " + str_neue_zeile );
//        }

        /*
         * Pruefung: neue Zeile gefunden ?
         */
        if ( str_neue_zeile != null )
        {
          str_ergebnis = str_ergebnis + my_cr + str_neue_zeile;
        }
        
        /*
         * Variable fuer das New-Line Zeichen wird am Ende der Funktion um den 
         * uebergebenen Einzug erweitert. Die erste Zeile wird ohne den Einzug 
         * dem Ergebnisstring hinzugefuegt.         
         */
        my_cr = pNewLineZeichen + pEinzug;

        /*
         * Am Schleifenende wird der Endlosschleifenverhinderungszaehler um 1 erhoeht.
         */
        zaehler = zaehler + 1;
      }
    }

//    /*
//     * Bei der Debugausgabe wird am Ende der Funktion noch eine Leerzeile ausgegeben.
//     */
//    if ( knz_debug_ausgabe )
//    {
//      FkLogger.wl( "" );
//    }

    /*
     * Am Funktionsende wird dem Aufrufer der erstellte Ergebnisstring zurueckgegeben. 
     */
    return str_ergebnis;
  }

}
