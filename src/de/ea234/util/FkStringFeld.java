package de.ea234.util;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class FkStringFeld
{
  /** Konstante fuer ein Leerzeichen */
  private static final String LEERZEICHEN = " ";

  /** Konstante fuer einen Leerstring */
  private static final String LEERSTRING  = "";

  /**
   * <pre>
   * Gibt einen String in der angegebenen Laenge und der angegebenen Zeichenfolge zurueck.
   *  
   * Ist die Laenge negativ oder 0, wird ein Leerstring zurueckgegeben
   * 
   * Ist der Parameeter "pZeichen" gleich null, wird ein Leerstring zurueckgegeben.
   * </pre>
   * 
   * @param pAnzahlStellen die Laenge
   * @param pZeichen das zu wiederholende Zeichen
   * @return einen String der angegebenen Laenge mit dem uebergebenen Zeichen
   */
  private static String nZeichen( int pAnzahlStellen, String pZeichen )
  {
    if ( pZeichen == null )
    {
      return "";
    }

    /*
     * Ist die Laenge negativ oder 0, wird ein Leerstring zurueckgegeben
     */
    if ( pAnzahlStellen <= 0 )
    {
      return "";
    }

    if ( pAnzahlStellen > 15000 )
    {
      pAnzahlStellen = 15000;
    }

    String ergebnis = pZeichen + pZeichen + pZeichen + pZeichen + pZeichen + pZeichen + pZeichen + pZeichen + pZeichen + pZeichen;

    /*
     * Der String "ergebnis" wird solange verdoppelt bis die Laenge groesser der Anzahl aus dem Parameter ist. 
     * Anschliessend wird ein Substring der Parameter-Laenge zurueckgegeben.
     */
    int zaehler = 1;

    while ( ( zaehler <= 50 ) && ( ergebnis.length() <= pAnzahlStellen ) )
    {
      ergebnis += ergebnis;

      zaehler++;
    }

    return ergebnis.substring( 0, pAnzahlStellen );
  }

  /**
   * @param pAnzahlStellenAlsString die Anzahl der Wiederholungen als String (=Stringlaenge wird ermittelt)
   * @param pZeichen das zu wiederholende Zeichen
   * @return einen String des vorgegebenen Zeichens, welcher so lang wie der erste Parameter ist.
   */
  private static String nZeichen( String pAnzahlStellenAlsString, String pZeichen )
  {
    if ( pAnzahlStellenAlsString == null )
    {
      return "";
    }

    int anzahl_stellen = pAnzahlStellenAlsString.length();

    /*
     * Ist die Laenge negativ oder 0, wird ein Leerstring zurueckgegeben
     */
    if ( anzahl_stellen <= 0 )
    {
      return "";
    }

    String str_ergebnis = "";

    if ( pZeichen != null )
    {
      str_ergebnis = pZeichen + pZeichen + pZeichen + pZeichen + pZeichen + pZeichen;
    }
    else
    {
      str_ergebnis = "          ";
    }

    /*
     * Der String "ergebnis" wird solange verdoppelt bis die Laenge groesser der Anzahl aus dem Parameter ist. 
     * Anschliessend wird ein Substring der Parameter-Laenge zurueckgegeben.
     */
    int zaehler = 1;

    while ( ( str_ergebnis.length() <= anzahl_stellen ) && ( zaehler < 20 ) )
    {
      str_ergebnis += str_ergebnis;

      zaehler++;
    }

    return str_ergebnis.substring( 0, anzahl_stellen );
  }

  /**
   * @param pAnzahlWiederholungen die Anzahl der Wiederholungen
   * @param pZeichen die zu wiederholende Zeichenkette
   * @return einen String aus pZeichen mit der vorgegebenen Anzahl von Wiederholungen
   */
  private static String nMal( int pAnzahlWiederholungen, String pZeichen )
  {
    /*
     * Ist die Laenge negativ oder 0, wird ein Leerstring zurueckgegeben
     */
    if ( pAnzahlWiederholungen <= 0 )
    {
      return "";
    }

    if ( pZeichen == null )
    {
      pZeichen = " ";
    }

    StringBuffer str_ergebnis = new StringBuffer();

    int zaehler = 1;

    while ( zaehler <= pAnzahlWiederholungen )
    {
      str_ergebnis.append( pZeichen );

      zaehler++;
    }

    return str_ergebnis.toString();
  }

  /**
   * Aufruf der gleichnamigen Funktion getFeldLinks mit dem Leerzeichen als Auffuellparameter.
   * 
   * @param pInhalt der Inhalt des Feldes
   * @param pLaenge die Laenge des Feldes
   * @return ein Feld in der angegebenen Laenge, wobei der Feldinhalt links steht und evtl. mit
   * Leerzeichen aufgefuellt wurde
   */
  private static String getFeldLinks( String pInhalt, int pLaenge )
  {
    return getFeldLinks( pInhalt, " ", pLaenge );
  }

  /**
   * @param pInhalt der Inhalt als int-Zahl
   * @param pLaenge die Laenge des Feldes
   * @return ein Feld in der angegebenen Laenge
   */
  private static String getFeldLinks( int pInhalt, int pLaenge )
  {
    return getFeldLinks( "" + pInhalt, " ", pLaenge );
  }

  /**
   * @param pInhalt der Inhalt als long-Zahl
   * @param pLaenge die Laenge des Feldes
   * @return ein Feld in der angegebenen Laenge
   */
  private static String getFeldLinks( long pInhalt, int pLaenge )
  {
    return getFeldLinks( "" + pInhalt, " ", pLaenge );
  }

  /**
   * Gibt einen String der uebergebenen Laenge zurueck, wobei der Feldinhalt links steht.
   * 
   * @param pInhalt der Inhalt des Feldes
   * @param pAuffuellZeichen das zur Auffuellung vorgegebene Zeichen
   * @param pLaenge die Laenge des Feldes
   * @return ein Feld in der angegebenen Laenge, wobei der Feldinhalt links steht und evtl. mit dem
   * uebergebenen Auffuellzeichen aufgefuellt wurde
   */
  private static String getFeldLinks( String pInhalt, String pAuffuellZeichen, int pLaenge )
  {
    if ( pInhalt == null )
    {
      pInhalt = "";
    }

    if ( pInhalt.length() >= pLaenge )
    {
      return pInhalt.substring( 0, pLaenge );
    }

    return pInhalt + nZeichen( pLaenge - pInhalt.length(), pAuffuellZeichen );
  }

  /**
   * @param nr der Inhalt des Feldes
   * @param pMindestLaenge die vorgegebene Mindestlaenge des Rueckgabestrings
   * @return einen String, welcher mindestens pMindesLaenge breit ist (evtl. aufgefuellt mit Leerzeichen)
   */
  public static String getFeldLinksMin( String nr, int pMindestLaenge )
  {
    if ( nr == null )
    {
      nr = "";
    }

    if ( nr.length() >= pMindestLaenge )
    {
      return nr;
    }

    return nr + nZeichen( pMindestLaenge - nr.length(), " " );
  }


  public static String getFeldLinksMin( int pInhalt, int pMindestLaenge )
  {
    return getFeldLinksMin( "" + pInhalt, " ", pMindestLaenge, pMindestLaenge );
  }

  public static String getFeldLinksMin( long pInhalt, int pMindestLaenge )
  {
    return getFeldLinksMin( "" + pInhalt, " ", pMindestLaenge, pMindestLaenge );
  }

  public static String getFeldLinksMin( boolean pInhalt, int pMindestLaenge )
  {
    return getFeldLinksMin( "" + pInhalt, " ", pMindestLaenge, pMindestLaenge );
  }

  /**
   * @param pInhalt der Inhalt des Feldes
   * @param pAuffuellZeichen das zu verwendende Auffuellzeichen
   * @param pMindestLaenge die vorgegebene Mindestlaenge des Rueckgabestrings
   * @return einen String, welcher mindestens pMindesLaenge breit ist (evtl. aufgefuellt mit dem angegebenen Auffuellzeichen)
   */
  private static String getFeldLinksMin( String pInhalt, String pAuffuellZeichen, int pMindestLaenge )
  {
    return getFeldLinksMin( pInhalt, pAuffuellZeichen, pMindestLaenge, pMindestLaenge );
  }

  /**
   * @param pInhalt der Inhalt des Feldes
   * @param pAuffuellZeichen das zu verwendende Auffuellzeichen
   * @param pMindestLaenge1 die vorgegebene Mindestlaenge 1 des Rueckgabestrings
   * @param pMindestLaenge2 die vorgegebene Mindestlaenge 2 des Rueckgabestrings
   * @return einen String, welcher mindestens pMindesLaenge breit ist (evtl. aufgefuellt mit dem angegebenen Auffuellzeichen)
   */
  private static String getFeldLinksMin( String pInhalt, String pAuffuellZeichen, int pMindestLaenge1, int pMindestLaenge2 )
  {
    if ( pInhalt == null )
    {
      pInhalt = "";
    }

    if ( pInhalt.length() >= pMindestLaenge2 )
    {
      return pInhalt;
    }

    if ( pInhalt.length() >= pMindestLaenge1 )
    {
      return pInhalt;
    }

    return pInhalt + nZeichen( pMindestLaenge1 - pInhalt.length(), pAuffuellZeichen );
  }

  public static String getFeldRechtsMin( String pFeldWert, int pMindestLaenge )
  {
    String feld_wert = "" + pFeldWert;

    if ( feld_wert.length() >= pMindestLaenge )
    {
      return feld_wert;
    }

    return nZeichen( pMindestLaenge - feld_wert.length(), " " ) + feld_wert;
  }

  private static String getFeldRechtsMin( boolean pFeldWert, int pMindestLaenge )
  {
    return getFeldRechtsMin( "" + pFeldWert, pMindestLaenge );
  }

  private static String getFeldRechtsMin( BigDecimal pFeldWert, int pMindestLaenge )
  {
    if ( pFeldWert == null )
    {
      return getFeldRechtsMin( "null", pMindestLaenge );
    }

    return getFeldRechtsMin( pFeldWert.toPlainString(), pMindestLaenge );
  }

  public static String getFeldRechtsMin( int pFeldWert, int pMindestLaenge )
  {
    String feld_wert = "" + pFeldWert;

    if ( feld_wert.length() >= pMindestLaenge )
    {
      return feld_wert;
    }

    return nZeichen( pMindestLaenge - feld_wert.length(), " " ) + feld_wert;
  }

  private static String getFeldRechtsMin( double pFeldWert, int pMindestLaenge )
  {
    String feld_wert = "" + pFeldWert;

    if ( feld_wert.length() >= pMindestLaenge )
    {
      return feld_wert;
    }

    return nZeichen( pMindestLaenge - feld_wert.length(), " " ) + feld_wert;
  }

  public static String getFeldRechtsMin( long pFeldWert, int pMindestLaenge )
  {
    String feld_wert = "" + pFeldWert;

    if ( feld_wert.length() >= pMindestLaenge )
    {
      return feld_wert;
    }

    return nZeichen( pMindestLaenge - feld_wert.length(), " " ) + feld_wert;
  }

  private static String getFeldRechtsMin( Date pDatum, int pMindestLaenge )
  {
    String feld_wert = null;

    if ( pDatum != null )
    {
      Calendar datum = Calendar.getInstance();

      datum.setTime( pDatum );

      int tag = datum.get( Calendar.DATE );

      int monat = datum.get( Calendar.MONTH ) + 1;

      feld_wert = ( tag < 10 ? "0" : "" ) + tag + "." + ( monat < 10 ? "0" : "" ) + monat + "." + datum.get( Calendar.YEAR );
    }
    else
    {
      feld_wert = "null";
    }

    if ( feld_wert.length() >= pMindestLaenge )
    {
      return feld_wert;
    }

    return nZeichen( pMindestLaenge - feld_wert.length(), " " ) + feld_wert;
  }

  private static String getFeldRechtsMin( String pInhalt, String pAuffuellZeichen, int pMindestLaenge )
  {
    if ( pInhalt == null )
    {
      pInhalt = "";
    }

    if ( pInhalt.length() >= pMindestLaenge )
    {
      return pInhalt;
    }

    return nZeichen( pMindestLaenge - pInhalt.length(), pAuffuellZeichen ) + pInhalt;
  }

  /**
   * @param pInhalt der Inhalt des Feldes
   * @param pAuffuellZeichen das zu benutzende Auffuellzeichen
   * @param pLaenge die Laenge
   * @return ein String der vorgegebenen Laenge und dem Inhalt rechts ausgerichtet
   */
  private static String getFeldRechts( String pInhalt, String pAuffuellZeichen, int pLaenge )
  {
    if ( pInhalt == null )
    {
      pInhalt = "";
    }

    if ( pInhalt.length() >= pLaenge )
    {
      return pInhalt.substring( 0, pLaenge );
    }

    return nZeichen( pLaenge - pInhalt.length(), pAuffuellZeichen ) + pInhalt;
  }

  /**
   * @param pInhalt der Inhalt als int
   * @param pLaenge die vorgegebene Laenge
   * @return ein String der vorgegebebenen Laenge und dem Inhalt rechts ausgerichtet
   */
  private static String getFeldRechts( String pInhalt, int pLaenge )
  {
    return getFeldRechts( pInhalt, LEERZEICHEN, pLaenge );
  }

  /**
   * @param pInhalt der Inhalt als int
   * @param pLaenge die vorgegebene Laenge
   * @return ein String der vorgegebebenen Laenge und dem Inhalt rechts ausgerichtet
   */
  private static String getFeldRechts( int pInhalt, int pLaenge )
  {
    return getFeldRechts( LEERSTRING + pInhalt, LEERZEICHEN, pLaenge );
  }

  /**
  * @param pInhalt der Inhalt als long
   * @param pLaenge die vorgegebene Laenge
   * @return ein String der vorgegebebenen Laenge und dem Inhalt rechts ausgerichtet
   */
  private static String getFeldRechts( long pInhalt, int pLaenge )
  {
    return getFeldRechts( LEERSTRING + pInhalt, LEERZEICHEN, pLaenge );
  }

  /**
   * @param pZahl die Zahl
   * @param pLaenge die vorgegebene Laenge
   * @return ein String der vorgegebenen Laenge mit den fuehrenden Nullen.
   */
  private static String getZahlMitFuehrendenNullen( int pZahl, int pLaenge )
  {
    return getFeldRechts( "" + pZahl, "0", pLaenge );
  }

  /**
   * <pre>
   * Liefert von pString alle Zeichen bis zum ersten Vorkommen des Trennzeichens von rechts.
   * 
   * FkString.right( "ABC.DEF.GHI.JKL",   "." ) = JKL
   * FkString.right( "ABC.DEF.GHI.JKL", "DEF" ) = ".GHI.JKL"
   * FkString.right( "ABC.DEF.GHI.JKL",   "A" ) = "BC.DEF.GHI.JKL"
   * FkString.right( "ABC.DEF.GHI.JKL",  "KL" ) = ""
   * 
   * Trennzeichen ist null oder wird nicht gefunden = Rueckgabe Eingabestring
   * 
   * FkString.right( "ABC.DEF.GHI.JKL", "XYZ" ) = "ABC.DEF.GHI.JKL"
   * FkString.right( "ABC.DEF.GHI.JKL",  null ) = "ABC.DEF.GHI.JKL"
   *  
   * </pre>
   * 
   * @param pString der String 
   * @param pTrennzeichen das Trennzeichen (oder Trennstring)
   * @return den sich ergebenden String bis zur Fundstelle des Trennzeichens
   */
  private static String right( String pString, String pTrennzeichen )
  {
    /*
     * Es wird die Variable "pos_trennzeichen" deklariert und 
     * mit -1 vorbelegt. 
     */
    int pos_trennzeichen = -1;

    /*
     * Pruefung: Sind beide Parameter ungleich "null" ?
     * 
     * Wenn beide Parameter ungleich "null" sind, wird die 
     * letzte Position des Trennzeichens gesucht. Das 
     * Ergebnis wird in der Variablen "pos_trennzeichen"
     * gespeichert.
     */
    if ( ( pString != null ) && ( pTrennzeichen != null ) )
    {
      pos_trennzeichen = pString.lastIndexOf( pTrennzeichen );
    }

    /*
     * Wurde das Trennzeichen nicht gefunden, kann das Ergebnis nur 
     * der EingabeString sein. Ist der Eingabestring selber null 
     * erhaelt der Aufrufer "null" zurueck.
     */
    if ( pos_trennzeichen == -1 )
    {
      return pString;
    }

    /*
     * Wurde das Trennzeichen gefunden 
     */
    return pString.substring( pos_trennzeichen + pTrennzeichen.length(), pString.length() );
  }

  /**
   * <pre>
   * Liefert von pString alle Zeichen bis zum ersten Vorkommen des Trennzeichens von rechts.
   * Das Trennzeichen muss vorhanden sein.
   * 
   * Ist das Trennzeichen in "pString" nicht vorhanden, wird null zurueckgegeben.
   * 
   * FkString.rightX( "ABC.DEF",  "." ) = "DEF"
   * FkString.rightX( "ABC.DEF",  "=" ) = null = Trennzeichen nicht in Eingabe vorhanden
   * 
   * FkString.rightX(      null,  "." ) = null = pString ist null
   * FkString.rightX( "ABC.DEF", null ) = null = Trennzeichen ist null
   * FkString.rightX(      null, null ) = null = keine Eingaben
   * </pre>
   * 
   * @param pString der String 
   * @param pTrennzeichen das Trennzeichen (oder Trennstring)
   * @return den sich ergebenden String bis zur Fundstelle des Trennzeichens
   */
  private static String rightX( String pString, String pTrennzeichen )
  {
    /*
     * Es wird die Variable "pos_trennzeichen" deklariert und 
     * mit -1 vorbelegt. 
     */
    int pos_trennzeichen = -1;

    /*
     * Pruefung: Sind beide Parameter ungleich "null" ?
     * 
     * Wenn beide Parameter ungleich "null" sind, wird die 
     * letzte Position des Trennzeichens gesucht. Das 
     * Ergebnis wird in der Variablen "pos_trennzeichen"
     * gespeichert.
     */
    if ( ( pString != null ) && ( pTrennzeichen != null ) )
    {
      pos_trennzeichen = pString.lastIndexOf( pTrennzeichen );
    }

    /*
     * Wurde das Trennzeichen nicht gefunden, wird "null" zurueckgegeben. 
     */
    if ( pos_trennzeichen == -1 )
    {
      return null;
    }

    return pString.substring( pos_trennzeichen + pTrennzeichen.length(), pString.length() );
  }

  /**
   * <pre>
   * Fuehrt im Endeffekt "pString.substring( pAbStelle )" aus, behandelt aber Sonderfaelle.
   * 
   * FkString.rightLeft( "ABC.DEF.GHI.JKL", 7  ) = .GHI.JKL
   * 
   * </pre>
   * 
   * @param pString der Eingabestring 
   * @param pAbStelle die Stelle von links, ab welcher abgeschnitten werden soll
   * @return den Reststring ab der vorgegebenen Stelle 
   */
  private static String rightLeft( String pString, int pAbStelle )
  {
    if ( pString == null )
    {
      return pString; // kein String, kein Ergebnis
    }

    if ( pAbStelle <= 0 )
    {
      return pString; // kleiner gleich 0 ist der Eingabestring selber
    }

    if ( pString.length() < pAbStelle )
    {
      return ""; // die Ab-Position liegt hinter dem Stringende
    }

    return pString.substring( pAbStelle );
  }

  /**
   * <pre>
   * Ermittelt einen Teilstring von Rechts bis zum ersten Auftreten des Trennzeichens von links.
   * Das Trennzeichen selber ist nicht Bestandteil des Ergebnisses.
   * 
   * Ist pString null, wird null zurueckgegeben.
   * Ist das Trennzeichen null wird pString zurueckgegeben.
   * 
   * FkString.rightLeft( "ABC.DEF.GHI.JKL",    "."  ) =  DEF.GHI.JKL
   * FkString.rightLeft( "ABC++DEF++GHI++JKL", "++" ) =  DEF++GHI++JKL = Trennzeichen ist 2 Stellen lang
   * FkString.rightLeft( "ABC++",              "++" ) =  ""            = Leerstring, wenn Trennzeichen = Stringende ist
   *  
   * </pre>
   * 
   * @param pString der Eingabestring 
   * @param pTrennzeichen das definierende Trennzeichen fuer die Stelle von links, ab welcher abgeschnitten werden soll
   * @return den Teilstring ab der berechneten Stelle
   */
  private static String rightLeft( String pString, String pTrennzeichen )
  {
    if ( pString == null )
    {
      return ""; // kein String, kein Ergebnis
    }

    if ( pTrennzeichen == null )
    {
      return pString;
    }

    int erste_position_trennzeichen = pString.indexOf( pTrennzeichen );

    /*
     * Wurde das Trennzeichen nicht gefunden, kann das Ergebnis 
     * nur der EingabeString sein.
     */
    if ( erste_position_trennzeichen == -1 )
    {
      return pString;
    }

    /*
     * Das Ergebnis liegt nach dem Trennzeichen. Es wird geprueft, 
     * ob die Fundstelle zuzueglich der Laenge des Trennzeichens noch 
     * ein Rueckgabeergebnis ergibt. 
     */
    if ( pString.length() < ( erste_position_trennzeichen + pTrennzeichen.length() ) )
    {
      return ""; // die Ab-Position liegt hinter dem Stringende
    }

    return pString.substring( erste_position_trennzeichen + pTrennzeichen.length() );
  }

  /**
   * <pre>
   * Schneided von "pString" von links ab, bis zur letzten Position
   * von "pTrennzeichen".
   * 
   * Das Trennzeichen ist nicht auf eine Stelle begrenzt. 
   * Das Trennzeichen kann auch ein Suchstring sein.
   * 
   * Ist der Eingabestring "null", wird "null" zurueckgegeben. 
   * Ist das Trennzeichen "null", wird "pString" zurueckgegeben.
   * 
   * Wird das Trennzeichen nicht gefunden, wird "pString" zurueckgeben.
   * 
   * FkString.leftRight( "ABC.DEF.GHI.JKL",   "." ) = "ABC.DEF.GHI"
   * 
   * FkString.leftRight( "ABC.DEF.GHI.JKL", "GHI" ) = "ABC.DEF."
   * 
   * FkString.leftRight( "ABC.DEF.GHI.JKL", "ABC" ) = ""
   * 
   * FkString.leftRight( "ABC.DEF.GHI.JKL",    "" ) = "ABC.DEF.GHI.JKL"
   * 
   * FkString.leftRight( "ABC.DEF.GHI.JKL",  null ) = "ABC.DEF.GHI.JKL"
   * 
   * FkString.leftRight( null,                "." ) = "null"
   * 
   * </pre>
   * 
   * @param pString der String
   * @param pTrennzeichen das Trennzeichen (oder Suchstring)
   * @return den sich ergebenden String 
   */
  private static String leftRight( String pString, String pTrennzeichen )
  {
    /*
     * Parameterpruefung
     * "pString" und "pTrennzeichen" muessen gesetzt sein
     */
    if ( ( pString != null ) && ( pTrennzeichen != null ) )
    {
      /*
       * Position des Trennzeichens in pString ermitteln
       */
      int letzte_position_trennzeichen = pString.lastIndexOf( pTrennzeichen );

      /*
       * Wurde das Trennzeichen gefunden, wird bis zu 
       * der Position des Trennzeichens das Ergebnis aus 
       * pString herausgeschnitten und zurueckgegeben. 
       */
      if ( letzte_position_trennzeichen >= 0 )
      {
        return pString.substring( 0, letzte_position_trennzeichen );
      }
    }

    /*
     * Standardrueckgabe
     * Wurde das Trennzeichen nicht gefuden, oder einer 
     * der beiden Parameter "null" sein, wird "pString" 
     * zurueckgegeben.
     */
    return pString;
  }

  /**
   * <pre>
   * Schneidet vom uebergebenen String von links Zeichen bis zur angegebenen Position von Rechts ab.
   * 
   * FkString.leftRight( "ABC.DEF",  0 ) = "ABC.DEF" 
   * FkString.leftRight( "ABC.DEF",  1 ) = "ABC.DE"
   * FkString.leftRight( "ABC.DEF",  2 ) = "ABC.D"
   * FkString.leftRight( "ABC.DEF",  6 ) = "A" 
   * FkString.leftRight( "ABC.DEF",  7 ) = ""
   * FkString.leftRight( "ABC.DEF", -1 ) = "ABC.DEF" 
   * FkString.leftRight( "ABC.DEF", 90 ) = ""
   * FkString.leftRight(      null, 90 ) = null
   * </pre>
   * 
   * @param pString der String 
   * @param pBisRPos Stelle von Rechts bis zu der abgeschnitten werden soll
   * @return der sich ergebende String, null wenn die Eingabe selber null ist
   */
  private static String leftRight( String pString, int pBisRPos )
  {
    /*
     * Parameterpruefung
     * Der Parameter "pString" muss gesetzt sein und mindestens ein Zeichen 
     * beinhalten (ohne Trim).
     * 
     * Der Parameter "pBisRPost" muss groesser als 0 sein.
     */
    if ( ( pString != null ) && ( pString.length() > 0 ) && ( pBisRPos > 0 ) )
    {
      /*
       * Rueckgabe Leerstring?
       * Ist die Anzahl der abzuschneidenden Zeichen von Rechts groesser als 
       * die Laenge der Eingabe, bleibt kein Zeichen der Eingabe uebrig. Das 
       * Ergebnis ist ein Leerstring.
       */
      if ( pBisRPos >= pString.length() )
      {
        return "";
      }

      /*
       * Rueckgabe Teilstring
       * Die Rueckgabe bestimmt sich aus der Laenge von pString abzueglich der 
       * Stellen von pBisRPos.
       */
      return pString.substring( 0, pString.length() - pBisRPos );
    }

    /*
     * Rueckgabe von pString
     * Ist pString selber null           = Von Nichts kann nichts abgeschnitten werden. 
     * Ist die pBisRPos kleiner gleich 0 = 0-Stellen von Rechts bzw. noch weiter nach Rechts im Negativ-Fall
     */
    return pString;
  }

  /**
   * <pre>
   * Schneided vom uebergebenen String von links ab, bis zur Position des ersten
   * Auftretens von pTrennzeichen von links.
   * 
   * Wird das Trennzeichen nicht gefunden, wird der gesamte String wieder zurueckgegeben.
   * 
   * FkString.left( "ABC.DEF.GHI.JKL",   "." ) = "ABC"
   * FkString.left( "ABC.DEF.GHI.JKL", "GHI" ) = "ABC.DEF."
   * FkString.left( "ABC.DEF.GHI.JKL", "ABC" ) = ""
   * FkString.left( "ABC.DEF.GHI.JKL",    "" ) = ""
   * 
   * FkString.left( "ABC.DEF.GHI.JKL",  null ) = "ABC.DEF.GHI.JKL"
   * 
   * FkString.left(              null,   "." ) = "null"
   * </pre>
   * 
   * @param pString der String
   * @param pTrennzeichen das Trennzeichen
   * @return der String bis zum ersten Auftretens von "pTrennzeichen" 
   */
  private static String left( String pString, String pTrennzeichen )
  {
    /*
     * Parameterpruefung
     * "pString" und "pTrennzeichen" muessen gesetzt sein
     */
    if ( ( pString != null ) && ( pTrennzeichen != null ) )
    {
      /*
       * Position des Trennzeichens in pString ermitteln
       */
      int position_trennzeichen = pString.indexOf( pTrennzeichen );

      /*
       * Wurde das Trennzeichen gefunden, wird bis zu 
       * der Position des Trennzeichens das Ergebnis aus 
       * pString herausgeschnitten und zurueckgegeben. 
       */
      if ( position_trennzeichen >= 0 )
      {
        return pString.substring( 0, position_trennzeichen );
      }
    }

    /*
     * Standardrueckgabe
     * Wurde das Trennzeichen nicht gefuden, oder einer 
     * der beiden Parameter "null" sein, wird "pString" 
     * zurueckgegeben.
     */
    return pString;
  }

  /**
   * <pre>
   * Ermittelt den Teilstring zwischen den beiden Trennzeichen 1 und 2. 
   * 
   * FkString.subString( "ABC.DEF.GHIJ.KLM",  ".",  "." ) = DEF
   * FkString.subString( "ABC.DEF.GHIJ.KLM", "-.", ".-" ) = ABC.DEF.GHIJ.KLM
   * </pre>
   * 
   * @param pString die Eingabezeichenfolge
   * @param pTrennzeichen1 der erste von links zu suchende String
   * @param pTrennzeichen2 der zweite String, welcher ab der ersten Fundstelle gesucht wird
   * @return einen String von der ersten Fundstelle bis zur zweiten Fundstelle, oder den gesamten String, sofern die Trennzeichen null sind
   */
  private static String subString( String pString, String pTrennzeichen1, String pTrennzeichen2 )
  {
    /*
     * Pruefung: Parameter "pString" gesetzt?
     * 
     * Ist der Parameter "pString" gleich "null", wird "null" zurueckgegeben.
     */
    if ( pString == null )
    {
      return null;
    }

    /*
     * Position AB
     * Es wird eine Variable deklariert, in welcher die Startposition
     * fuer den Teilstring gespeichert wird. 
     * 
     * Diese Variable wird initial auf 0 eingestellt (=erstes Zeichen der Eingabe)
     */
    int position_ab = 0;

    /*
     * Position BIS
     * Es wird eine Variable deklariert, in welcher die Endposition
     * fuer den Teilstring gespeichert wird. 
     * 
     * Diese Variable wird initial auf -1 eingestellt 
     */
    int position_bis = -1;

    /*
     * Pruefung: Trennzeichen 1 ungleich "null" ?
     */
    if ( pTrennzeichen1 != null )
    {
      position_ab = pString.indexOf( pTrennzeichen1, 0 );

      if ( position_ab < 0 )
      {
        position_ab = 0;
      }
      else
      {
        position_ab = position_ab + pTrennzeichen1.length();
      }
    }

    if ( pTrennzeichen2 != null )
    {
      position_bis = pString.indexOf( pTrennzeichen2, position_ab );
    }

    if ( position_bis < 0 )
    {
      position_bis = pString.length();
    }

    return pString.substring( position_ab, position_bis );
  }

  /**
   * <pre>
   * Sucht nach den Trennzeichn in der Eingabe und gibt im Ergfolgsfall den String 
   * bis zur Trennzeichenposition zurueck. 
   * 
   * Die Suche nach den Trennzeichen beginnt von links.
   * Es wird beim ersten auftreten des eines Trennzeichens getrennt.
   * 
   * Werden die Trennzeichen nicht gefunden, wird die Eingabe zurueckgegeben.
   * 
   * Ist die Eingabe null, wird null zurueckgegeben.
   * 
   * FkString.leftString( "aaaa?b=1&c=2",   '/', '?' ) = "aaaa"
   * FkString.leftString( "aaaa/?b=1&c=2",  '/', '?' ) = "aaaa"
   * 
   * FkString.leftString( "aaaa/?b=1&c=2",  '?', '?' ) = "aaaa/"
   * FkString.leftString( "aaaa/?b=1&c=2",  '-', '-' ) = "aaaa/?b=1&c=2" = Trennzeichen nicht in Eingabe vorhanden
   * FkString.leftString( "/aaaa/?b=1&c=2", '/', '?' ) = ""              = Trennzeichen an erster Stelle vorhanden 
   * FkString.leftString( "aaaaa?b=1&c=2/", '/', '/' ) = "aaaaa?b=1&c=2" = Trennzeichen an letzter Stelle vorhanden
   * FkString.leftString( null,             '/', '?' ) = null            = Eingabestring ist null
   *
   * </pre>
   * 
   * @param pEingabe der Eingabestring
   * @param pTrennzeichen1 das erste Trennzeichen
   * @param pTrennzeichen2 das zweite Trennzeichen
   * @return wurde ein Trennzeichen gefunden, wird ein String bis zum Trennzeichen zurueckgegeben.
   *         Wird kein Trennzeichen gefunden, wird die Eingabe zurueckgegeben.
   */
  private static String leftString( String pEingabe, char pTrennzeichen1, char pTrennzeichen2 )
  {
    /*
     * Pruefung: pEingabe ungleich null ?
     * Die Routine wird nur gestartet, wenn die Eingabe gesetzt ist. 
     */
    if ( pEingabe != null )
    {
      int anzahl_zeichen_eingabe = pEingabe.length();

      /*
       * Leseposition
       * Die Variable "position_trennzeichen" ist die Index-Position im Eingabestring. 
       * Die Index-Position beginnt bei 0. 
       */
      int position_trennzeichen = 0;

      /*
       * While-Schleife
       * Die While-Schleife laeuft solange, wie es noch Zeichen im Eingabestring gibt.
       * Es wird nach dem ersten "/" oder dem ersten "?" gesucht.
       */
      while ( position_trennzeichen < anzahl_zeichen_eingabe )
      {
        /*
         * Aktuelles Zeichen aus der Eingabe lesen
         */
        char akt_char = pEingabe.charAt( position_trennzeichen );

        /*
         * Zeichenpruefung: Ist das aktuelle Zeichen ein "/" oder ein "?"
         * Ist dem so, wird der Ergebnisstring bis zur Trennzeichenpostion
         * dem Aufrufer zurueckgegeben.
         */
        if ( ( akt_char == pTrennzeichen1 ) || ( akt_char == pTrennzeichen2 ) )
        {
          return pEingabe.substring( 0, position_trennzeichen );
        }

        /*
         * Leseprozess ein Zeichen weiter stellen 
         */
        position_trennzeichen++;
      }
    }

    /*
     * Trennzeichen wurden nicht gefunden. 
     * Die Rueckgabe ist der gesamte Eingabestring
     */
    return pEingabe;
  }

  /**
   * <pre>
   * Wandelt den uebergebenen String in Grossbuchstaben um.
   *  
   * Bei einer Uebergabe von null wird null zurueckgegeben.
   * 
   * Fuehrt im Endeffekt "pString.toUpperCase()" aus
   * </pre>
   * 
   * @param pString die zu wandelnde Zeichenkette
   * @return der String in Grossbuchstaben, oder null
   */
  private static String ucase( String pString )
  {
    if ( pString != null )
    {
      return pString.toUpperCase();
    }

    return null;
  }

  /**
   * <pre>
   * Wandelt den uebergebenen String in Kleinbuchstaben um.
   *  
   * Bei einer Uebergabe von null wird null zurueckgegeben.
   * 
   * Fuehrt im Endeffekt "pString.toLowerCase()" aus
   * </pre>
   * 
   * @param pString die zu wandelnde Zeichenkette
   * @return der String in Kleinbuchstaben, oder null
   */
  private static String lcase( String pString )
  {
    if ( pString != null )
    {
      return pString.toLowerCase();
    }

    return null;
  }

  /**
   * <pre>
   * Fuehrt einen Trim auf den uebergebenen String aus.
   *  
   * Ist der Parameter "pString" null oder ein Leerstring, wird "null" zurueckgeben.
   * 
   * FkString.trimNull( " A B " ) = "A B"
   * 
   * FkString.trimNull( "     " ) = null = Eingabe ist Leerstring
   * FkString.trimNull(    null ) = null = Eingabe ist nicht gesetzt
   * 
   * </pre> 
   * 
   * @param pString der zu trimmende String
   * @return null wenn der String null oder ein Leerstring ist, sonst der getrimmte String
   */
  private static String trimNull( String pString )
  {
    String str_ergebnis_trim = null;

    /*
     * Pruefung: "pString" ungleich "null" ?
     */
    if ( pString != null )
    {
      /*
       * Ist "pString" gesetzt, wird am dort die Funktion "trim" aufgerufen
       */
      str_ergebnis_trim = pString.trim();

      /*
       * Pruefung: Ist das Ergebnis der Trim-Funktion ein Leerstring ?
       * 
       * Ist es nach dem Trim ein Leerstring, wird die 
       * Ergebnisvariable auf "null" gesetzt.
       */
      if ( str_ergebnis_trim.length() == 0 )
      {
        str_ergebnis_trim = null;
      }
    }

    /*
     * Rueckgabe der Ergebnisstrings.
     */
    return str_ergebnis_trim;
  }

  /**
   * <pre>
   * Fuehrt einen Trim auf die gesamte Zeichenlaenge aus.
   * Es werden auch doppelte Leerzeichen innerhalb des Strings entfernt.
   * 
   * FkString.trimX( "    A  B    C  " ) = "A B C"
   * FkString.trimX( "    A  B    C"   ) = "A B C"
   * FkString.trimX( "ABC"             ) = "ABC"
   * FkString.trimX( "      "          ) = ""
   * FkString.trimX( ""                ) = ""
   * FkString.trimX( null              ) = null
   * </pre>
   * 
   * @param pString die Eingabezeichenfolge
   * @return einen String ohne vorlaufende und nachlaufende Leerzeichen und keinen doppelten Leerzeichen zwischen den Zeichen, null wenn die Eingabe selbst null ist.
   */
  private static String trimX( String pString )
  {
    /*
     * Pruefung: Ist "pString" gleich "null" ?
     * 
     * Ist der Eingabestring "null", wird "null" zurueckgegeben.
     */
    if ( pString == null )
    {
      return null;
    }

    StringBuffer str_ergebnis = new StringBuffer();

    /* 
     * Variable "letztes_zeichen"
     * Speichert das zuletzt hinzugefuegt Zeichen im Ergebnis. Der Startwert 
     * eines Leerzeichens sorgt dafuer, dass die fuehrenden Leerzeichen entfernt
     * werden. 
     */
    char letztes_zeichen = ' ';

    /* 
     * Variable "aktuelles_zeichen"
     * Speichert das aktuelle Zeichen aus der Eingabezeichenfolge. Der Startwert
     * ist nur wegen der Initialisierung der Variable vorhanden.
     */
    char aktuelles_zeichen = '!';

    /*
     * Schleife Zeichenpruefung
     * Ueber eine While-Schleife wird jedes Zeichen der Eingabe geprueft.
     */
    int akt_index = 0;

    while ( akt_index < pString.length() )
    {
      aktuelles_zeichen = pString.charAt( akt_index );

      /*
       * Pruefung: aktuelles Zeichen ist Leerzeichen
       */
      if ( aktuelles_zeichen == ' ' )
      {
        /* Leerzeichen gefunden
         * Ist das aktuelle Zeichen ein Leerzeichen darf dieses nur dem
         * Ergebnis hinzugefuegt werden, wenn das zuletzt hinzugefuegte 
         * Zeichen ungleich einem Leerzeichen war. 
         * 
         * War das letzte Zeichen ein Leerzeichen, wird das neue Leerzeichen ueberlesen.
         */
        if ( letztes_zeichen != ' ' )
        {
          str_ergebnis.append( aktuelles_zeichen );

          letztes_zeichen = aktuelles_zeichen;
        }
      }
      else
      {
        /*
         * Zeichen ungleich Leerzeichen
         * Alle anderen Zeichen werden dem Ergebnisstring hinzugefuegt. Das aktuelle
         * Zeichen wird in der Variablen "letztes_zeichen" gespeichert.
         */
        str_ergebnis.append( aktuelles_zeichen );

        letztes_zeichen = aktuelles_zeichen;
      }

      akt_index++;
    }

    /* 
     * Abschlusspruefung
     * Pruefung, ob das Ergebnis auf ein Leerzeichen endet.
     * Das Ergebnis endet auf einem Leerzeichen, wenn das letzte hinzugefuegte Zeichen ein Leerzeichen war.
     */
    if ( letztes_zeichen == ' ' )
    {
      if ( str_ergebnis.length() == 0 )
      {
        return "";
      }

      return str_ergebnis.toString().substring( 0, str_ergebnis.length() - 1 );
    }

    return str_ergebnis.toString();
  }

  /**
   * @param pString der zu trimmende String
   * @param pTrimZeichen das noch wegzutrimmende Zeichen (ausser Leerzeichen, darf nur 1 Zeichen umfassen (sonst nur normales Trim))
   * @return den getrimmten String
   */
  private static String trim( String pString, String pTrimZeichen )
  {
    if ( pTrimZeichen != null )
    {
      if ( pTrimZeichen.length() == 1 )
      {
        return trim( pString, pTrimZeichen.charAt( 0 ) );
      }
    }

    return pString.trim();
  }

  /**
   * @param pString der zu trimmende String 
   * @return den getrimmten String, oder null, wenn pString null ist.
   */
  private static String trim( String pString )
  {
    /*
     * Pruefung: Ist "pString" gleich "null" ?
     * 
     * Ist der Eingabestring "null", wird "null" zurueckgegeben.
     */
    if ( pString == null )
    {
      return null;
    }

    /*
     * Ist "pString" gesetzt, wird dort die Funktion "trim" aufgerufen
     * und das Ergebnis dem Aurufer zurueckgegeben.
     */
    return pString.trim();
  }

  /**
   * <pre>
   * Fuehrt einen Trim auf pString aus, beruecksichtigt dabei aber noch das Zeichen 
   * aus dem zweiten Parameter.
   * 
   * FkString.trim( "  ;ABC;  ", ';' ) = "ABC"
   * FkString.trim( "  ;A;    ", ';' ) = "A"
   * FkString.trim( "  ; A ;  ", ';' ) = "A"
   * FkString.trim( "  ;;;    ", ';' ) = ""           = Rueckgabe Leerstring
   * FkString.trim( "ABCDEFGHI", ';' ) = "ABCDEFGHI"  = es gibt nichts zu trimmen
   * FkString.trim( null,        ';' ) = null         = Eingabe ist null = Ergebnis ist null
   * </pre>
   * 
   * @param pString der zu trimmende String
   * @param pTrimZeichen das noch wegzutrimmende Zeichen (ausser Leerzeichen)
   * @return den getrimmten String
   */
  private static String trim( String pString, char pTrimZeichen )
  {
    /*
     * Parameterpruefung
     * pString = darf nicht null sein, wenn doch wird null zurueckgegeben
     */
    if ( pString != null )
    {
      int position_start = 0;

      int position_ende = pString.length();

      char[] char_werte = pString.toCharArray();

      /*
       * Funktionsweise
       * Es laufen zwei Positionszeiger von vorne und von hinten los. Dabei werden
       * alle Leerzeichen und das zuzaetzliche Trimzeichen ueberlesen. Die beiden 
       * Suchschleifen sind beendet, wenn ein anderes als das Leerzeichen oder 
       * Suchzeichen gefunden wird, oder sich die beiden Zeiger kreuzen.
       *  
       * Startposition
       * Die Startposition wird vom Stringanfang gezaehlt. 
       * Die Startposition darf nicht laenger als das Stringende (in position_ende) sein.
       */
      while ( ( position_start < position_ende ) && ( char_werte[ position_start ] == ' ' || char_werte[ position_start ] == pTrimZeichen ) )
      {
        position_start++;
      }

      /*
       * Endposition ermitteln
       * Die Endposition fuer einen Teilstring beginnt am Ende des Eingabestrings und 
       * wird solange nach vorne bewegt, wie noch Leerzeichen und das zusaetzliche 
       * Zeichen gefunden werden. Dabei darf das Ende nicht kleiner als die 
       * Startposition werden.
       */
      while ( ( position_ende > position_start ) && ( char_werte[ position_ende - 1 ] == ' ' || char_werte[ position_ende - 1 ] == pTrimZeichen ) )
      {
        position_ende--;
      }

      char_werte = null;

      /*
       * Pruefung: Teilstring zurueckgeben
       * Liegen der Start oder das Ende nicht mehr an den Initialwerten (Stringgrenzen), 
       * ist die Rueckgabe ein Teilstring der Eingabe. Dieser Teilstring wird 
       * dem Aufrufer zurueckgegeben.
       */
      if ( ( position_start > 0 ) || ( position_ende < pString.length() ) )
      {
        return pString.substring( position_start, position_ende );
      }
    }

    /*
     * Rueckgabe des Eingabestrings
     * Ist der Eingabestring selber null oder es muss kein Teilstring zurueckgegeben 
     * werden, wird die Variable pString selber wieder zurueckgegeben.
     */
    return pString;
  }

  /**
   * <pre>
   * Fuehrt einen Trim auf pString aus, beruecksichtigt dabei aber noch die Zeichen 
   * aus den beiden Parametern.
   * 
   * FkString.trim( " .:ABC.DEF.GHI:. ", ':', '.' ) = "ABC.DEF.GHI"
   * FkString.trim( "ABC.DEF.GHI",       ':', '.' ) = "ABC.DEF.GHI"
   * FkString.trim( " .:B",              ':', '.' ) = "B"
   * FkString.trim( " .::. ",            ':', '.' ) = ""
   * FkString.trim( " .:ABC.DEF.GHI:. ", ':', '.' ) = "ABC.DEF.GHI"
   * 
   * FkString.trim(   "",                ':', '.' ) = ""
   * FkString.trim( null,                ':', '.' ) = null
   * </pre>
   * 
   * @param pString der zu trimmende String
   * @param pTrimZeichen das noch wegzutrimmende Zeichen (ausser Leerzeichen)
   * @return den getrimmten String
   */
  private static String trim( String pString, char pTrimZeichen1, char pTrimZeichen2 )
  {
    /*
     * Parameterpruefung
     * pString = darf nicht null sein, wenn doch wird null zurueckgegeben
     */
    if ( pString != null )
    {
      int position_start = 0;

      int position_ende = pString.length();

      char[] char_werte = pString.toCharArray();

      /*
       * Funktionsweise
       * Es laufen zwei Positionszeiger von vorne und von hinten los. Dabei werden alle 
       * Leerzeichen und das zuzaetzliche Trimzeichen ueberlesen. Die beiden Suchschleifen 
       * sind beendet, wenn ein anderes als das Leerzeichen oder Suchzeichen gefunden 
       * wird, oder sich die beiden Zeiger kreuzen.
       *  
       * Startposition
       * Die Startposition wird vom Stringanfang gezaehlt. 
       * Die Startposition darf nicht laenger als das Stringende (in position_ende) sein.
       */
      while ( ( position_start < position_ende ) && ( char_werte[ position_start ] == ' ' || char_werte[ position_start ] == pTrimZeichen1 || char_werte[ position_start ] == pTrimZeichen2 ) )
      {
        position_start++;
      }

      /*
       * Endposition ermitteln
       * Die Endposition fuer einen Teilstring beginnt am Ende des Eingabestrings und 
       * wird solange nach vorne bewegt, wie noch Leerzeichen und das zusaetzliche 
       * Zeichen gefunden werden. Dabei darf das Ende nicht kleiner als die 
       * Startposition werden.
       */
      while ( ( position_ende > position_start ) && ( char_werte[ position_ende - 1 ] == ' ' || char_werte[ position_ende - 1 ] == pTrimZeichen1 || char_werte[ position_ende - 1 ] == pTrimZeichen2 ) )
      {
        position_ende--;
      }

      char_werte = null;

      /*
       * Pruefung: Teilstring zurueckgeben
       * Liegen der Start oder das Ende nicht mehr an den Initialwerten (Stringgrenzen), 
       * ist die Rueckgabe ein Teilstring der Eingabe. Dieser Teilstring wird 
       * dem Aufrufer zurueckgegeben.
       */
      if ( ( position_start > 0 ) || ( position_ende < pString.length() ) )
      {
        return pString.substring( position_start, position_ende );
      }
    }

    /*
     * Rueckgabe des Eingabestrings
     * Ist der Eingabestring selber null oder es muss kein Teilstring zurueckgegeben 
     * werden, wird die Variable pString selber wieder zurueckgegeben.
     */
    return pString;
  }

  private static String trimT( String pString )
  {
    /*
     * Parameterpruefung
     * pString = darf nicht null sein, wenn doch wird null zurueckgegeben
     */
    if ( pString != null )
    {
      int position_start = 0;

      int position_ende = pString.length();

      char[] char_werte = pString.toCharArray();

      /*
       * Endposition ermitteln
       * Die Endposition fuer einen Teilstring beginnt am Ende des Eingabestrings und 
       * wird solange nach vorne bewegt, wie noch Leerzeichen und das zusaetzliche 
       * Zeichen gefunden werden. Dabei darf das Ende nicht kleiner als die 
       * Startposition werden.
       */
      while ( ( position_ende > position_start ) && ( char_werte[ position_ende - 1 ] == ' ' ) )
      {
        position_ende--;
      }

      char_werte = null;

      /*
       * Pruefung: Teilstring zurueckgeben
       * Liegen der Start oder das Ende nicht mehr an den Initialwerten (Stringgrenzen), 
       * ist die Rueckgabe ein Teilstring der Eingabe. Dieser Teilstring wird 
       * dem Aufrufer zurueckgegeben.
       */
      if ( ( position_start > 0 ) || ( position_ende < pString.length() ) )
      {
        return pString.substring( position_start, position_ende );
      }
    }

    /*
     * Rueckgabe des Eingabestrings
     * Ist der Eingabestring selber null oder es muss kein Teilstring zurueckgegeben 
     * werden, wird die Variable pString selber wieder zurueckgegeben.
     */
    return pString;
  }

  /**
   * <pre>
   * Loescht aus der Eingabe die Zeichenfolge "pStart", sofern "pString" mit dieser Zeichenfolge beginnt.
   * 
   * FkString.trimStart( "m_kv", "m_" ) = "kv"
   * 
   * Die Startzeichenfolge wird nur einmal entfernt:
   * FkString.trimStart( "ABC.ABC.ABC.DEF.GHI", "ABC." ) = "ABC.ABC.DEF.GHI"
   *  
   * Gross- Kleinschreibung wird beachtet: 
   * FkString.trimStart( "ABC.ABC.ABC.DEF.GHI", "abc." ) = "ABC.ABC.ABC.DEF.GHI"
   *  
   * pString = pStart
   * FkString.trimStart( "ABC.ABC.ABC.DEF.GHI", "ABC.ABC.ABC.DEF.GHI" ) = ""
   *   
   * FkString.trimStart( "ABC.ABC.ABC.DEF.GHI", null ) = "ABC.ABC.ABC.DEF.GHI" = kein pStart angegeben
   * FkString.trimStart( null, "ABC.ABC.ABC.DEF.GHI" ) = null                  = kein pString angegeben
   * </pre>
   * 
   * @param pString der eventuell zu kuerzende String
   * @param pStart der String, welcher eventuell einmalig zu entfernen ist.
   * @return pString ohne der Startzeichenfolge (Die Startzeichenfolge wird nur einmal entfernt).
   */
  private static String trimStart( String pString, String pStart )
  {
    /*
     * Der zu untersuchende String muss gesetzt sein 
     */
    if ( ( pString != null ) && ( pString.trim().length() > 0 ) )
    {
      /*
       * Die zu pruefende Startzeichenfolge muss gesetzt sein 
       */
      if ( ( pStart != null ) && ( pStart.trim().length() > 0 ) )
      {
        /*
         * Die Startzeichenfolge muss kleiner als der zu untersuchende String sein.
         */
        if ( pStart.length() <= pString.length() )
        {
          /*
           * Pruefung: beginnt pString mit pStart?
           */
          if ( pString.startsWith( pStart ) )
          {
            /*
             * Beginnt pString mit pStart, bekommt der Aufrufer den gekuerzten String zurueck. 
             */
            return pString.substring( pStart.length() );
          }
        }
      }
    }

    /*
     * Sollte ein Fehler in den Vorgaben vorliegen, oder aber  "pString" nicht 
     * mit "pStart" beginnen, bekommt der Aufrufer pString zurueck.
     */
    return pString;
  }

  /**
   * <pre>
   * Trimmt alle Vorkommen des Trimzeichens am Stringstart weg.
   * 
   * Leerzeichen werden nicht wegetrimmt.
   * 
   * Besteht die Eingabe nur aus Zeichen des Trimmzeichens, wird 
   * ein Leerstring zurueckgegeben.
   * 
   * FkString.trimStartX( "000123", '0' ) = "123"
   * FkString.trimStartX( "001000", '0' ) = "1000"
   * FkString.trimStartX( "000123", '9' ) = "000123" = Trimzeichen nicht im String
   * FkString.trimStartX( "000123", '1' ) = "000123" = Eingabe startet nicht mit Trimzeichen
   * FkString.trimStartX( "123456", '0' ) = "123456" = Trimzeichen nicht gefunden
   * FkString.trimStartX( " 00123", '0' ) = " 00123" = erstes Zeichen ist ein Leerzeichen
   * FkString.trimStartX( "000000", '0' ) = ""       = alle Zeichen werden weggetrimmt
   * FkString.trimStartX(       "", '0' ) = ""       = Eingabe ist Leerstring
   * FkString.trimStartX(     null, '0' ) = null     = der zu trimmende String ist null
   * </pre>
   * 
   * @param pString der zu trimmende String
   * @param pTrimZeichen das zu entfernende Zeichen am Start
   * @return den getrimmten String. Null, wenn die Eingabe null ist.
   */
  private static String trimStartX( String pString, char pTrimZeichen )
  {
    /*
     * Parameterpruefung
     * Ist "pString" null, bekommt der Aufrufer "null" zurueck.
     */
    if ( pString != null )
    {
      int position_start = 0;

      char[] char_werte = pString.toCharArray();

      /*
       * While-Schleife, laeuft solange wie
       * ... die Startposition noch nicht am Stringende angekommen ist
       * ... das Zeichen an der Startposition gleich dem Trimzeichen ist.
       */
      while ( ( position_start < pString.length() ) && ( char_werte[ position_start ] == pTrimZeichen ) )
      {
        position_start++;
      }

      char_werte = null;

      /*
       * Pruefung: Rueckgabe Leerstring ?
       * Ist die Postion plus 1 gleich der Laenge des Eingabestrings, 
       * bleibt von der Eingabe kein Zeichen mehr uebrig. In diesem 
       * Fall wird ein Leerstring zurueck gegeben.
       */
      if ( position_start + 1 == pString.length() )
      {
        return "";
      }

      /*
       * Pruefung: Startposition groesser 0 ?
       */
      if ( position_start > 0 )
      {
        return pString.substring( position_start );
      }
    }

    /*
     * Rueckgabe des Eingabestrings
     * Ist der Eingabestring selber null oder es muss kein Teilstring zurueckgegeben 
     * werden, wird die Variable pString selber wieder zurueckgegeben.
     */
    return pString;
  }

  /**
   * <pre>
   * Trimmt einen String mehrmalig weg
   * 
   * FkString.trimString( "ABC.ABC.ABC.DEF.GHI", "ABC." ) = "DEF.GHI"
   * </pre>
   * 
   * @param pString der zu pruefende String
   * @param pStart der String, mit dem geprueft wird
   * @return den sich ergebenden String
   */
  private static String trimString( String pString, String pStart )
  {
    /*
     * Parameterpruefung
     */
    if ( ( pString != null ) && ( pStart != null ) )
    {
      int trim_string_eingabe_len = pStart.length();

      int position_ende = pString.length();

      if ( trim_string_eingabe_len <= position_ende )
      {
        int position_trennzeichen = 0;

        int position_start = 0;

        char[] trim_zeichen = pStart.toCharArray();

        char[] char_werte = pString.toCharArray();

        while ( ( position_start < position_ende ) && ( char_werte[ position_start ] == trim_zeichen[ position_trennzeichen ] ) )
        {
          position_trennzeichen++;

          if ( position_trennzeichen >= trim_string_eingabe_len )
          {
            position_trennzeichen = 0;
          }

          position_start++;
        }

        trim_zeichen = null;

        char_werte = null;

        /*
         * Pruefung: Teilstring zurueckgeben
         * Liegt der Start oder das Ende nicht mehr an deren Initialwerten (Stringgrenzen), 
         * ist die Rueckgabe ein Teilstring der Eingabe. Dieser Teilstring wird 
         * dem Aufrufer zurueckgegeben.
         */
        if ( ( position_start >= 0 ) || ( position_ende < pString.length() ) )
        {
          return pString.substring( position_start, position_ende );
        }
      }
    }

    /*
     * Rueckgabe des Eingabestrings
     * Ist der Eingabestring selber null oder es muss kein Teilstring zurueckgegeben 
     * werden, wird die Variable pString selber wieder zurueckgegeben.
     */
    return pString;
  }

  /**
   * @param pAscii der Ascii-Code
   * @return das Zeichen fuer den Ascii-Code
   */
  private static char getChar( int pAscii )
  {
    return (char) pAscii;
  }

  /**
   * <pre>
   * Erstellt einen String mit einer maximalen Zeilenbreite der Ausgabe.
   * 
   * Ist die aktuelle Zeile aus dem Eingabestring kleiner als die Breite, 
   * wird die Zeile ohne Aenderung uebernommen. 
   * 
   * Ist die aktuelle Zeile laenger als die Breite, wird die aktuelle
   * Zeile in Zeilen der gewuenschten maxmimalen Laenge zerkleinert.
   * 
   * FkString.getStringMaxBreite( "AAAAAAAAAABBBBBBBBBBCCCCCCCCCC\n\n        \nENDE", null, 10, "praefix +", "+ suffix"  ) 
   * 
   * praefix +AAAAAAAAAA+ suffix
   * praefix +BBBBBBBBBB+ suffix
   * praefix +CCCCCCCCCC+ suffix
   * praefix ++ suffix
   * praefix +        + suffix
   * praefix +ENDE+ suffix
   * 
   * 
   * Test: Anzahl Zeilenbreite ist negativ
   * FkString.getStringMaxBreite( "AAAAAAAAAABBBBBBBBBBCCCCCCCCCC\n\n        \nENDE", null, -10, "praefix +", "+ suffix"  )
   * 
   * praefix +AAAAAAAAAABBBBBBBBBBCCCCCCCCCC+ suffix
   * praefix ++ suffix
   * praefix +        + suffix
   * praefix +ENDE+ suffix
   * 
   * 
   * Test: Trennzeichen nicht in Eingabe vorhanden 
   * FkString.getStringMaxBreite( "AAAAAAAAAABBBBBBBBBBCCCCCCCCCCENDE", "trenn_zeichen", 7, " praefix +", "+ suffix" )
   * 
   * praefix +AAAAAAA+ suffix
   * praefix +AAABBBB+ suffix
   * praefix +BBBBBBC+ suffix
   * praefix +CCCCCCC+ suffix
   * praefix +CCENDE+ suffix
   * 
   * Test: Trennzeichen ist eine Zeichenfolge:
   * FkString.getStringMaxBreite( "1 1234|8|2 12343 1234|8|4 1234|8|5 12", "|8|", 6, " praefix +", "+ suffix" ) 
   * 
   * praefix +1 1234+ suffix
   * praefix +2 1234+ suffix
   * praefix +3 1234+ suffix
   * praefix +4 1234+ suffix
   * praefix +5 12+ suffix
   * 
   * ----------------------------------------------------------------------------------------------------------- 
   * 
   * Properties test_properties = FkTest.getTestDaten();
   * 
   * String str_text         = FkProperties.getStrPropertiesKeyValue( test_properties, true, 50 );
   * String str_trennzeichen = null;
   * String str_praefix      = null;
   * String str_suffix       = null;
   *
   * int max_breite          = 1000;
   * 
   * FkDatei.schreibeDatei( FkSystem.getStdRootVerzeichnis() + "propertie_ausgabe.txt", FkString.getStringMaxBreite( str_text, str_trennzeichen, max_breite, str_praefix, str_suffix ) );
   *
   * </pre>
   * 
   * @param pText der zu formatierende Text
   * @param pTrennzeichen das zu verwendende Trennzeichen (null = Zeilenumbruch)
   * @param pMaxBreite die maximale Breite einer Zeile 
   * @param pPraefix (optional) Ist der Parameter ungleich null wird der hier stehende String dem aktuellen Teilstring vorangestellt
   * @param pSuffix (optional) Ist der Parameter ungleich null wird der hier stehende String dem aktuellen Teilstring drangehaengt
   */
  private static String getStringMaxBreite( String pText, String pTrennzeichen, int pMaxBreite, String pPraefix, String pSuffix )
  {
    StringBuffer str_ergebnis = new StringBuffer(); // Stringbuffer fuer die Aufnahme des Rueckgabestrings
    String str_zeilenumbruch = ""; // String fuer das Zeilenumbruchzeichen des Rueckgabestrings 
    String csv_trennzeichen = null; // das ermittelte Trennzeichen
    String csv_praefix = ( pPraefix == null ? "" : pPraefix );
    String csv_suffix = ( pSuffix == null ? "" : pSuffix );
    int aktuelle_startposition = 0; // die akutelle Start-Leseposition der aeusseren Schleife
    int csv_zaehler = 0; // Zaehler fuer Vermeidung von Endlosschleifen
    int csv_start = 0; // Startposition des aktuellen Strings aus dem Eingabeparameter "pText" 
    int csv_ende = 0; // Endposition des aktuellen Strings aus dem Eingabeparameter "pText" 
    int naechste_position = 0; // Position des naechsten gefundenen Trennzeichens
    int max_br_end = 0; // Endposition fuer die Zerkleinerungsschleife

    int max_br_toleranz = pMaxBreite + 30;
    int naechste_position_lz = 0;
    boolean knz_ausrichtung_an_wortgrenzen = false;

    try
    {
      /* 
       * Pruefung: pText gesetzt? 
       */
      if ( pText != null )
      {
        /* 
         * Trennzeichen setzen
         * Wurde ein Trennzeichen uebergeben, wird jenes genommen. 
         * Ist der Parameter "pTrennzeichen" null, wird als Trennzeichen 
         * das normale Zeilenumbruchszeichen genommen.
         */
        if ( pTrennzeichen != null )
        {
          csv_trennzeichen = pTrennzeichen;
        }
        else
        {
          csv_trennzeichen = "\n";
        }

        /* 
         * Die Suchschleife laeuft solange wie...
         * ... die Variable "naechste_position" groesser/gleich 0 ist 
         *     (das Trennzeichen noch gefunden wurde) 
         * ... der CSV-Zaehler noch kleiner als 32123 ist
         */
        while ( ( naechste_position >= 0 ) && ( csv_zaehler <= 32123 ) )
        {
          /* 
           * Naechste Position Trennzeichen
           * Ab der aktuellen Startposition wird die naechste Position des Trennzeichens gesucht
           */
          csv_start = aktuelle_startposition;

          naechste_position = pText.indexOf( csv_trennzeichen, csv_start );

          /* 
           * Pruefung: Trennzeichen gefunden ?
           */
          if ( naechste_position >= 0 )
          {
            /*
             * Endposition Teilstring
             * Die Position CSV-Ende ist die Position des naechsten gefundenen Trennzeichens.
             * Diese Position wird nicht Bestandteil des Ergebnisstrings, da die genutzte
             * "subString()"-Funktion BIS VOR diesesr Position abschneidet. 
             */
            csv_ende = naechste_position;

            /* 
             * Startposition aktualisieren
             * Die naechste aktuelle Startposition liegt ab der Fundstelle zuzueglich der
             * Laenge des Trennzeichens ( hier = Zeilenumbruchzeichens ).
             */
            aktuelle_startposition = naechste_position + csv_trennzeichen.length();
          }
          else
          {
            /* 
             * Pruefung: Noch ungelesener Teilstring vorhanden ?
             * 
             * Dieses ist der Fall, wenn die letzte Leseposition kleiner
             * gleich der Stringlaenge ist. Von der letzten Leseposition 
             * wird bis zum Stringende die aktuelle Zeile ermittelt.
             * 
             * Ist das nicht der Fall, wird die letzte Startposition auf -1 gestellt.
             */
            if ( csv_start <= pText.length() )
            {
              csv_ende = pText.length();
            }
            else
            {
              csv_start = -1;
            }
          }

          /* 
           * Pruefung: csv_start >= 0 ?
           * 
           * Nur wenn das der Fall ist, darf weitergemacht werden.
           */
          if ( csv_start >= 0 )
          {
            /*
             * Pruefung: Ist "csv_akt_string" = Leerzeile?
             * Damit Leerzeilen korrekt uebernommen werden, muss bei einer 
             * Laenge von 0 Zeichen einmal der Praefix, der Suffix und das 
             * Zeilenumbruchzeichen dem Ergebnis hinzugefuegt werden. 
             */
            if ( ( csv_ende - csv_start ) == 0 )
            {
              str_ergebnis.append( str_zeilenumbruch );
              str_ergebnis.append( csv_praefix );
              str_ergebnis.append( csv_suffix );

              str_zeilenumbruch = "\n";
            }
            else
            {
              /*
               * Startwert Schleife
               * Die erste End-Position ist die Startposition zuzueglich der maximalen 
               * Zeilenbreite aus dem Parameter "pMaxBreite".
               */
              max_br_end = csv_start + pMaxBreite;

              /*
               * Pruefung: Endwert kleiner/gleich Startwert
               * Das ist der Fall, wenn im Parameter "pMaxBreite" ein Wert 
               * von 0 oder kleiner uebergeben worden ist. 
               * 
               * In diesem Fall soll die Ausgabebreite vernachlaessigt werden, 
               * bzw. die vorgefundene Zeilenbreite uebernommen werden.
               *  
               * Die End-Position wird auf die Endposition des aktuellen CSV-Strings gesetzt. 
               * Die nachfolgenden Pruefungen sorgen dafuer, das die While-Schleife nur 
               * einmal ausgefuehrt wird. 
               */
              if ( max_br_end <= csv_start )
              {
                max_br_end = csv_ende;
              }

              /*
               * While-Schleife
               * Die "Zerkleinerungsschleife" wird solange ausgefuehrt, bis die Variable
               * fuer den Startwert die Endposition des aktuellen CSV-Strings erreicht
               * oder ueberschritten hat.
               */
              while ( csv_start < csv_ende )
              {
                /*
                 * Pruefung: Endposition groesser der CSV-Endposition
                 * Ist dem so, muss die Endposition angepasst werden, damit nur bis zum 
                 * aktuellen CSV-String-Ende der Teilstring abgeschnitten wird. 
                 */
                if ( max_br_end > csv_ende )
                {
                  max_br_end = csv_ende;
                }

                if ( knz_ausrichtung_an_wortgrenzen )
                {
                  naechste_position_lz = Math.min( pText.indexOf( " ", max_br_end ), pText.indexOf( ",", max_br_end ) );

                  if ( ( naechste_position_lz >= csv_start ) && ( naechste_position_lz < csv_ende ) && ( ( ( naechste_position_lz - csv_start ) < max_br_toleranz ) ) )
                  {
                    max_br_end = naechste_position_lz + 1; // Das gefundene Zeichen mit in die aktuelle Zeile aufnehmen.
                  }
                  else
                  {
                    naechste_position_lz = pText.indexOf( ";", max_br_end );

                    if ( ( naechste_position_lz >= csv_start ) && ( naechste_position_lz < csv_ende ) && ( ( naechste_position_lz - csv_start ) < max_br_toleranz ) )
                    {
                      max_br_end = naechste_position_lz + 1; // Das gefundene Zeichen mit in die aktuelle Zeile aufnehmen.
                    }
                    else
                    {
                      naechste_position_lz = pText.indexOf( ".", max_br_end );

                      if ( ( naechste_position_lz >= csv_start ) && ( naechste_position_lz < csv_ende ) && ( ( naechste_position_lz - csv_start ) < max_br_toleranz ) )
                      {
                        max_br_end = naechste_position_lz + 1; // Das gefundene Zeichen mit in die aktuelle Zeile aufnehmen.
                      }
                    }
                  }

                  if ( max_br_end > pText.length() )
                  {
                    max_br_end = pText.length();
                  }
                }

                /*
                 * Aufbau Ergebnis
                 * Es werden die Bestandteile Zeilenumbruch + Praefix + Teilstring + Suffix dem 
                 * Ergebnisstring hinzugefuegt.  
                 * 
                 * Der Zeilenumbruch ist im ersten Durchlauf noch ein Leerstring. Somit wird 
                 * im ersten Durchlauf kein falscher Zeilenumbruch hinzugefuegt.
                 *  
                 * Der Zeilenumbruch-String wird unten der Variablen hinzugefuegt. 
                 * Jeder weitere Durchlauf durch diese Routine setzt daher den Zeilenumbruch 
                 * fuer die vorhergehende Zeile. Dieses Vorgehen verhindert, dass der letzte
                 * Zeilenumbruch kein falscher Zeilenumbruch wird.
                 */
                str_ergebnis.append( str_zeilenumbruch );
                str_ergebnis.append( csv_praefix );
                str_ergebnis.append( pText.substring( csv_start, max_br_end ) );
                str_ergebnis.append( csv_suffix );

                /*
                 * Leseposition weiterschalten
                 * CSV-Start wird auf die aktuelle Endposition gestellt.
                 *  
                 * Die Endposition wird um die Max-Breite erhoeht. Sollte die sich
                 * ergebende Breite kleiner/gleich 0 sein, wurde schon durch die 
                 * obere Pruefung sichergestellt, dass in einem solchen fall hier
                 * der Wert der Startposition gleich der Max-Laenge des aktuellen
                 * Strings ist (d.h. die Schleifenendbedingung erreicht ist).
                 */
                csv_start = max_br_end;

                max_br_end = max_br_end + pMaxBreite;

                str_zeilenumbruch = "\n";
              }
            }
          }

          /* 
           * Endlosschleifenverhinderungszaehler 1 weiterstellen
           */
          csv_zaehler = csv_zaehler + 1;
        }
      }
    }
    catch ( Exception err_inst )
    {
      err_inst.printStackTrace( System.out );
    }

    return str_ergebnis.toString();
  }

  /**
   * <pre>
   * NUR FUER DIE PROGRAMMIERUNG ENTHALTEN
   * ... im richtigen Leben macht diese Funktion natuerlich keinen Sinn, kann aber
   * in der Programmierung mal zweckdienlich sein, weil es keinen Compilerfehler gibt
   * </pre>
   * 
   * @param pString der  String 
   * @return 0 wenn der String nicht gesetzt ist, sonst die Laenge des Strings
   */
  private static int getMaxLen( String pString )
  {
    if ( pString != null )
    {
      return pString.length();
    }

    return 0;
  }

  /**
   * <pre>
   * NUR FUER DIE PROGRAMMIERUNG ENTHALTEN
   * ... im richtigen Leben macht diese Funktion natuerlich keinen Sinn, kann 
   * aber in der Programmierung mal zweckdienlich sein.
   * </pre>
   * 
   * @param pLaenge1 die Laenge
   * @return pLaenge1
   */
  private static int getMaxLen( int pLaenge1 )
  {
    return pLaenge1;
  }

  private static boolean isSetOr( String pString1, String pString2 )
  {
    return ( ( pString1 != null ) && ( pString1.trim().length() > 0 ) ) || ( ( pString2 != null ) && ( pString2.trim().length() > 0 ) );
  }

  private static boolean isSetOr( String pString1, String pString2, String pString3 )
  {
    return ( ( pString1 != null ) && ( pString1.trim().length() > 0 ) ) || ( ( pString2 != null ) && ( pString2.trim().length() > 0 ) ) || ( ( pString3 != null ) && ( pString3.trim().length() > 0 ) );
  }

  /**
   * <pre>
   * Analoge Funktion fuer die mit Java 1.6 hinzugekommene String-Funktion isEmpty.
   * Es wird kein TRIM auf pString ausgefuehrt.
   * </pre>
   * 
   * @param pString der zu pruefende String 
   * @return TRUE wenn pString null oder "pString.length() == 0" ist
   */
  private static boolean isEmpty( String pString )
  {
    /*
     * Pruefung: Parameter "pString" gleich "null" ?
     * 
     * Ist pString nicht gesetzt, wird true zurueck gegeben.
     */
    if ( pString == null )
    {
      return true;
    }

    return pString.length() == 0;
  }

  private static boolean isTrimEmpty( String pString )
  {
//    if ( ( pString == null ) || ( pString.trim().length() == 0 ) )
//    {
//      return true;
//    }

    /*
     * Pruefung: Parameter "pString" gleich "null" ?
     * 
     * Ist pString nicht gesetzt, wird true zurueck gegeben.
     */
    if ( pString == null )
    {
      return true;
    }

    return pString.trim().length() == 0;
  }

  private static boolean isTrimSet( String pString )
  {
    /*
     * Pruefung: Parameter "pString" gleich "null" ?
     * 
     * Ist pString nicht gesetzt, wird false zurueck gegeben.
     */
    if ( pString == null )
    {
      return false;
    }

    return pString.trim().length() > 0;
  }

  /**
   * Prueft ob ein String NICHT gesetzt ist.
   * Bei der Laengenpruefung wird ein TRIM auf den Eingabestring gemacht.
   * 
   * @param pString der zu pruefende String
   * @return TRUE wenn der String null oder ein Leerstring ist.
   */
  private static boolean isNotSet( String pString )
  {
    if ( ( pString == null ) || ( pString.length() == 0 ) )
    {
      return true; // = Ja, der String ist null oder nicht gesetzt
    }

    /* Kopiervorlage - Pruefung auf String gesetzt */
    //if ( ( pString != null ) && ( pString.length() > 0 ) ) { return false; // Nein der String ist gesetzt und hat eine Laenge
    //}
    return false; // Nein der String ist gesetzt und hat eine Laenge
    //    if ( pString == null ) { return true; } // Ist der String null, ist der String nicht gesetzt
    //
    //    return pString.trim().length() == 0; // Hat der String eine Laenge von 0, ist der String nicht gesetzt
  }

  private static boolean istLeerstring( String pString )
  {
    if ( pString == null )
    {
      return true;
    }

    return pString.length() == 0;
  }

  private static boolean istKeinLeerstring( String pString )
  {
    if ( pString == null )
    {
      return false;
    }

    return pString.length() > 0;
  }

  private static int InStr( String pZuDurchsuchenderString, String pSuchString )
  {
    if ( pZuDurchsuchenderString == null )
    {
      return -1;
    }

    if ( pSuchString == null )
    {
      return -1;
    }

    return pZuDurchsuchenderString.indexOf( pSuchString, 0 );
  }

  private static int InStr( long pStartPosition, String pZuDurchsuchenderString, String pSuchString )
  {
    if ( pZuDurchsuchenderString == null )
    {
      return -1;
    }

    if ( pSuchString == null )
    {
      return -1;
    }

    return pZuDurchsuchenderString.indexOf( pSuchString, (int) pStartPosition );
  }

  /**
   * <pre>
   * Gleichnamige Version aus Visual-Basic.
   * 
   * FkString.InStr( 0    , "ABC.DEF.GHI.KLM",  "ABC" )   =  0
   * FkString.InStr( 0    , "ABC.DEF.GHI.KLM",  "GHI" )   =  8
   * FkString.InStr( 1    , "ABC.DEF.GHI.KLM",  "ABC" )   = -1
   * FkString.InStr( 0    , null,               "ABC" )   = -1
   * FkString.InStr( 0    , "ABC.DEF.GHI.KLM",  "XYZ" )   = -1
   * FkString.InStr( 0    , "ABC.DEF.GHI.KLM",  null  )   = -1
   * FkString.InStr( 100  , "ABC.DEF.GHI.KLM",  "ABC" )   = -1
   * FkString.InStr( -10  , "ABC.DEF.GHI.KLM",  "ABC" )   =  0
   * </pre>
   * 
   * @param pStartPosition die Position ab welcher mit der Suche begonnen werden soll
   * @param pZuDurchsuchenderString der zu durchsuchende String 
   * @param pSuchString der String, welcher gesucht wird
   * @return die eventuell gefundene Position, im Fehlerfall -1
   */
  private static int InStr( int pStartPosition, String pZuDurchsuchenderString, String pSuchString )
  {
    if ( pZuDurchsuchenderString == null )
    {
      return -1;
    }

    if ( pSuchString == null )
    {
      return -1;
    }

    return pZuDurchsuchenderString.indexOf( pSuchString, pStartPosition );
  }

  /**
   * <pre>
   * Sucht in pString die Start- und die Endzeichenfolge und gibt den "eingeschlossenen" Teilstring zurueck. 
   * 
   * Wird die Startzeichenfolge nicht gefunden, wird ab Stringanfang abgeschnitten.
   * Wird die Endzeichenfolge nicht gefunden, wird bis zum Stringende abgeschnitten.
   * 
   * Ist die Startzeichenfolge "null" wird ab Stringanfang abgeschnitten.
   * Ist die Endzeichenfolge "null" wird bis zum Stringende abgeschnitten. 
   * 
   * FkString.MidX( "1234567890", "5", "6" )                               = ""
   * FkString.MidX( "1234567890", "6", "5" )                               = "7890"
   * FkString.MidX( "1234567890", "4", "7" )                               = "56"
   * FkString.MidX( "1234567890", "3", "8" )                               = "4567"
   * FkString.MidX( "Eins Zwei Drei Vier Fuenf Sechs", "Zwei", "Fuenf" )   = " Drei Vier "
   * FkString.MidX( "Eins Zwei Drei Vier Fuenf Sechs", "Fuenf", "Zwei" )   = " Sechs"
   * FkString.MidX( "Eins Zwei Drei Vier Fuenf Sechs", "Zwei", "Neun"  )   = " Drei Vier Fuenf Sechs"
   * FkString.MidX( "Eins Zwei Drei Vier Fuenf Sechs", Null,   "Fuenf" )   = "Eins Zwei Drei Vier "
   * FkString.MidX( "Eins Zwei Drei Vier Fuenf Sechs", Null,   "Neun"  )   = "Eins Zwei Drei Vier Fuenf Sechs"
   * FkString.MidX( null, "Zwei", "Fuenf" )                                = "null"
   * FkString.MidX( "Eins Zwei Drei Vier Fuenf Sechs", null, "Fuenf"  )    = "Eins Zwei Drei Vier "
   * FkString.MidX( "Eins Zwei Drei Vier Fuenf Sechs", "Zwei", null   )    = " Drei Vier Fuenf Sechs"
   * FkString.MidX( "Eins Zwei Drei Vier Fuenf Sechs", "null", null   )    = "Eins Zwei Drei Vier Fuenf Sechs"
   * FkString.MidX( "Eins Zwei Drei Vier Fuenf Sechs", "Sechs", "Fuenf" )  = ""
   * </pre>
   * 
   * @param pString der String mit dem herauszutrennenden Teilstring
   * @param pAbString Suchzeichenfolge fuer den Start
   * @param pBisString Suchzeichenfolge fuer das Ende
   * @return den sich ergebenden Teilstring
   */
  private static String MidX( String pString, String pAbString, String pBisString )
  {
    /*
     * Pruefung: pString ungleich null?
     */
    if ( pString != null )
    {
      /*
       * Position der Startzeichenfolge mit -1 vorbelegen.
       */
      int ab_position = -1;

      /*
       * Pruefung: Abstring vorhanden?
       * Ist eine Startzeichenfolge vorhanden, wird diese im Eingabestring gesucht.
       */
      if ( pAbString != null )
      {
        ab_position = pString.indexOf( pAbString );
      }

      /*
       * Pruefung: Startzeichenfolge gefunden?
       * Wurde die Startzeichenfolge nicht gefunden, wird vom Stringanfang an 
       * abgeschnitten. Die Ab-Position wird auf 0 gestellt.
       */
      if ( ab_position < 0 )
      {
        ab_position = 0;
      }
      else
      {
        /* 
         * Wurde die Startzeichenfolge gefunden, muss die Ab-Position um die 
         * Laenge der Startzeichenfolge erhoeht werden. Die Startzeichenfolge 
         * ist nicht Bestandteil der Rueckgabe.  
         */
        ab_position += pAbString.length();
      }

      /*
       * Vorbelegung der Bis-Position mit -1.
       */
      int end_position = -1;

      /*
       * Pruefung: Endzeichenfolge uebergeben?
       * Wurde eine Endzeichenfolge uebergeben, wird die Endzeichenfolge im Eingabestring gesucht.
       * Die Endzeichenfolge wird ab der Ab-Positon gesucht.
       */
      if ( pBisString != null )
      {
        end_position = pString.indexOf( pBisString, ab_position );
      }

      /*
       * Pruefung: Endzeichenfolge gefunden?
       * Wurde die Endzeichenfolge nicht gefunden, wird bis zum Stringende abgeschnitten. 
       * Die Bis-Position ist daher gleich der Laenge des Eingabestrings.
       */
      if ( end_position < 0 )
      {
        end_position = pString.length();
      }

      /*
       * Rueckgabe des Strings von der Ab-Position bis zur End-Position.
       */
      return pString.substring( ab_position, end_position );
    }

    /*
     * Als Standardrueckgabe wird eine null-Referenz zurueckgegeben. 
     */
    return null;
  }

  /**
   * <pre>
   * Sucht das Suchwort in pString und gibt diesen inklusive der Relativen Abschneidepositionen zurueck.
   * 
   * Anfangs- und Endposition des Raustrennens modifizieren
   * 
   * FkString.getStringRelativ( "ABC.DEF.GHI.JKL", "GHI",     0,   0 );  = "GHI"
   * FkString.getStringRelativ( "ABC.DEF.GHI.JKL", "GHI",    -1,   1 );  = ".GHI."
   * FkString.getStringRelativ( "ABC.DEF.GHI.JKL", "GHI",    -2,   2 );  = "F.GHI.J"
   * FkString.getStringRelativ( "ABC.DEF.GHI.JKL", "GHI",    -3,   3 );  = "EF.GHI.JK"
   * FkString.getStringRelativ( "ABC.DEF.GHI.JKL", "GHI",    -4,   4 );  = "DEF.GHI.JKL"
   * 
   * FkString.getStringRelativ( "ABC.DEF.GHI.JKL", "GHI",     1, -1 );  = "H"
   * FkString.getStringRelativ( "ABC.DEF.GHI.JKL", "GHI",     2, -2 );  = "" = Ab-Position groesser als Bis-Position = Leerstring
   * 
   *
   * 
   * FkString.getStringRelativ( "ABC.DEF.GHI.JKL", "GHI",     0, 100 );  = "GHI.JKL"      = Bis-Position laenger als Eingabe
   * FkString.getStringRelativ( "ABC.DEF.GHI.JKL", "GHI",  -100,   0 );  = "ABC.DEF.GHI"  = Ab-Position auf Start
   * 
   * FkString.getStringRelativ(              null, "GHI",    -1, 1 );  = null = kein Eingabetext gesetzt
   * FkString.getStringRelativ( "ABC.DEF.GHI.JKL",  null,    -1, 1 );  = null = kein Suchwort angegeben
   * FkString.getStringRelativ( "ABC.DEF.GHI.JKL", "   ",    -1, 1 );  = null = Suchwort wird nicht gefunden
   * 
   * </pre>
   * 
   * @param pString der String, in welchem das Suchwort zu finden ist
   * @param pSuchwort das Suchwort
   * @param pMinusAnfang relative Anzahl von Stellen, welche auf die Startposition des Suchwortes draufaddiert werden
   * @param pPlusEnde relative Anzahl von Stellen, welche auf die Endposition draufaddiert werden
   * @return den sich ergebenden String
   */
  private static String getStringRelativ( String pString, String pSuchwort, int pMinusAnfang, int pPlusEnde )
  {
    /*
     * Pruefung: Sind die Parameter ungleich null?
     */
    if ( ( pString != null ) && ( ( pSuchwort != null ) && ( pSuchwort.length() > 0 ) ) )
    {
      int ab_position = pString.indexOf( pSuchwort );

      /*
       * Pruefung: Suchwort gefunden?
       * Wurde das Suchwort nicht nicht gefunden, bekommt der Aufrufer "null" zurueck.
       */
      if ( ab_position >= 0 )
      {
        /*
         * Bis-Position
         * Die Bis-Position ist von der Position der ersten Fundstelle des 
         * Suchwortes, zuzueglich dessen Laenge und zuzueglich der Anzahl 
         * Stellen aus dem Parameter "pPlusEnde" zu finden.
         */
        int bis_position = ab_position + pSuchwort.length() + pPlusEnde;

        /*
         * Pruefung: Bis-Position groesser als Laenge "pString"?
         * Ist die berechnete Bis-Position laenger als der Eingeabestring "pString" 
         * selber, wird die Bis-Position auf das Stringende eingestellt.
         */
        if ( bis_position > pString.length() )
        {
          bis_position = pString.length();
        }

        /*
         * Pruefung: Bis-Position kleiner 0?
         * Ist die Bis-Position durch die Anzahl der Stellen aus dem 
         * Parameter "pPlusEnde" kleiner als 0, wird die Bis-Position 
         * auf 0 gestellt.
         */
        if ( bis_position < 0 )
        {
          bis_position = 0;
        }

        /*
         * Ab-Positon verschieben
         * Auf die Ab-Position werden die Anzahl von Stellen aus dem 
         * Parameter "pMinusAnfang" draufaddiert. 
         * 
         * Normalerweise sollter der Parameter einen negativen Wert haben.
         */
        ab_position = ab_position + pMinusAnfang;

        /*
         * Pruefung: Ab-Positon kleiner 0?
         * Ist die Ab-Position kleiner als 0, wird die Ab-Position auf 0 gestellt.
         */
        if ( ab_position < 0 )
        {
          ab_position = 0;
        }

        /*
         * Pruefung: Ab-Position groesser gleich der Bis-Position?
         * Ist die Ab-Position groesser/gleich der Bis-Position, ist das 
         * Funktionsergebnis ein Leerstring. 
         */
        if ( ab_position >= bis_position )
        {
          return "";
        }

        /*
         * Rueckgabe des sich ergebenden Strings
         */
        return pString.substring( ab_position, bis_position );
      }
    }

    /*
     * Als Standardrueckgabe wird eine null-Referenz zurueckgegeben. 
     */
    return null;
  }

  /**
   * <pre>
   * Sucht in pString die Start- und die Endzeichenfolge und gibt den 
   * "eingeschlossenen" Teilstring zurueck. 
   * 
   * Es wird ein String zurueckgeben, wenn die Start- und Endzeichenfolge 
   * gefunden wurden und hintereinander stehen. Es erfolgt keine automatische
   * Umkehrung, wenn der Start hinter dem Ende gefunden wird.  
   * In allen anderen Faellen wird null zurueckgegeben. 
   * 
   * FkString.Mid( "1234567890", "5", "6" )                                = ""
   * FkString.Mid( "1234567890", "6", "5" )                                = null
   * FkString.Mid( "1234567890", "4", "7" )                                = "56"
   * FkString.Mid( "1234567890", "3", "8" )                                = "4567"
   * 
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", "Zwei",  "Fuenf"  )  = " Drei Vier "
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", "Fuenf", "Zwei"   )  = null
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", "Zwei",  "Neun"   )  = null
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", "Null",  "Fuenf"  )  = null
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", "Null",  "Neun"   )  = null
   * FkString.Mid( null,                              "Zwei",  "Fuenf"  )  = null
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", null,    "Fuenf"  )  = null
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", "Zwei",  null     )  = null
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", null,    null     )  = null
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", "Sechs", "Fuenf"  )  = null
   * </pre>
   * 
   * @param pString der String mit dem herauszutrennenden Teilstring
   * @param pAbString Suchzeichenfolge fuer den Start
   * @param pBisString Suchzeichenfolge fuer das Ende
   * @return den Teilstring, wenn Start und Ende gefunden wurden, sonst null
   */
  private static String Mid( String pString, String pAbString, String pBisString )
  {
    /*
     * Pruefung: Sind die Parameter ungleich null?
     */
    if ( ( pString != null ) && ( pAbString != null ) && ( pBisString != null ) )
    {
      /*
       * Position der Startzeichenfolge suchen.
       */
      int ab_position = pString.indexOf( pAbString );

      /*
       * Pruefung: Startzeichenfolge gefunden?
       * Wurde die Startzeichenfolge nicht gefunden, bekommt der Aufrufer "null" zurueck.
       */
      if ( ab_position >= 0 )
      {
        /* 
         * Wurde die Startzeichenfolge gefunden, muss die Ab-Position um die 
         * Laenge der Startzeichenfolge erhoeht werden. Die Startzeichenfolge 
         * ist nicht Bestandteil der Rueckgabe.  
         */
        ab_position += pAbString.length();

        /*
         * Die Bis-Zeichenfolge wird ab der Ab-Positon gesucht.
         */
        int bis_position = pString.indexOf( pBisString, ab_position );

        /*
         * Pruefung: Bis-Zeichenfolge gefunden?
         * Wurde zwar die Startzeichenfolge gefunden, aber die Bis-Zeichenfolge 
         * nicht im Suchstring enthalten ist, bekommt der Aufrufer ebenfalls null zurueck.
         */
        if ( bis_position >= 0 )
        {
          /*
           * Rueckgabe des eingeschlossenen Strings
           */
          return pString.substring( ab_position, bis_position );
        }
      }
    }

    /*
     * Als Standardrueckgabe wird eine null-Referenz zurueckgegeben. 
     */
    return null;
  }

  private static String Mid( String pString, long pAbPosition, long pLaenge )
  {
    try
    {
      if ( pString != null )
      {
        if ( pLaenge < 0 )
        {
          return pString.substring( (int) pAbPosition );
        }

        return pString.substring( (int) pAbPosition, (int) ( pAbPosition + pLaenge ) );
      }
    }
    catch ( Exception err_inst )
    {
    }

    return "";
  }

  private static String Mid( String pString, long pAbPosition, int pLaenge )
  {
    try
    {
      if ( pString != null )
      {
        if ( pLaenge < 0 )
        {
          return pString.substring( (int) pAbPosition );
        }

        return pString.substring( (int) pAbPosition, (int) ( pAbPosition + pLaenge ) );
      }
    }
    catch ( Exception err_inst )
    {
    }

    return "";
  }

  private static String Mid( String pString, long pAbPosition )
  {
    try
    {
      if ( pString != null )
      {
        return pString.substring( (int) pAbPosition );
      }
    }
    catch ( Exception err_inst )
    {
    }

    return "";
  }

  private static String Mid( String pString, int pAbPosition )
  {
    try
    {
      if ( pString != null )
      {
        return pString.substring( (int) pAbPosition );
      }
    }
    catch ( Exception err_inst )
    {
    }

    return "";
  }

  /**
   * <pre>
   * MID-Funktion
   * Liefert einen String, die eine festgelegte Zeichenanzahl aus einer Zeichenfolge enthaelt.
   * 
   * </pre>
   * 
   * @param pString der Quellstring, aus welchem herausgeschnitten werden soll
   * @param pAbPosition die Position, ab welcher abgeschnitten werden soll
   * @param pLaenge die Laenge der abzuschneidenden Zeichen 
   * @return einen String 
   */
  private static String Mid( String pString, int pAbPosition, int pLaenge )
  {
    try
    {
      if ( pString != null )
      {
        if ( pLaenge < 0 )
        {
          return pString.substring( pAbPosition );
        }

        return pString.substring( pAbPosition, pAbPosition + pLaenge );
      }
    }
    catch ( Exception err_inst )
    {
    }

    return "";

    /*
     * int trenn_pos_ab = (int) ( );
     * int trenn_laenge = (int) ( );
     * 
     * if ( trenn_laenge < 0 )
     * {
     *   ergebnis_mid = pEingabe.substring( trenn_pos_ab );
     * }
     * else if ( laenge_eingabe < trenn_pos_ab + trenn_laenge ) 
     * {
     *   ergebnis_mid = pEingabe.substring( trenn_pos_ab );
     * }
     * else
     * {
     *   ergebnis_mid = pEingabe.substring( trenn_pos_ab, trenn_pos_ab + trenn_laenge );
     * }
     */
  }

  /**
   * <pre>
   * MID-Funktion
   * Liefert einen String, die eine festgelegte Zeichenanzahl aus einer Zeichenfolge enthaelt.
   * 
   * </pre>
   * 
   * @param pString der Quellstring, aus welchem herausgeschnitten werden soll
   * @param pAbPosition die Position, ab welcher abgeschnitten werden soll
   * @param pLaenge die Laenge der abzuschneidenden Zeichen 
   * @return einen String 
   */
  private static String MidVB( String pString, int pAbPosition, int pLaenge )
  {
    try
    {
      if ( pString != null )
      {
        if ( pLaenge < 0 )
        {
          return pString.substring( pAbPosition );
        }

        int int_ab_pos = pAbPosition - 1;

        if ( pAbPosition < 0 )
        {

        }

        return pString.substring( pAbPosition, pAbPosition + pLaenge );
      }
    }
    catch ( Exception err_inst )
    {
    }

    return "";
  }

  private static String nString( int pAnzahlWiederholungen, String pString )
  {
    /*
     * Ist die Laenge negativ oder 0, wird ein Leerstring zurueckgegeben
     */
    if ( pAnzahlWiederholungen <= 0 )
    {
      return "";
    }

    if ( pAnzahlWiederholungen > 1500 )
    {
      pAnzahlWiederholungen = 1500;
    }

    String ergebnis = "";

    /*
     * Der String ergebnis wird solange verdoppelt bis die laenge groesser der Anzahl ist. Danach wird ein Substring der uebergebenen Laenge zurueckgegeben.
     */
    for ( int zaehler = 1; ergebnis.length() <= pAnzahlWiederholungen; zaehler++ )
    {
      ergebnis += pString;
    }

    return ergebnis;
  }

  private static String nString( long pAnzahlWiederholungen, String pString )
  {
    /*
     * Ist die Laenge negativ oder 0, wird ein Leerstring zurueckgegeben
     */
    if ( pAnzahlWiederholungen <= 0 )
    {
      return "";
    }

    if ( pAnzahlWiederholungen > 1500 )
    {
      pAnzahlWiederholungen = 1500;
    }

    String ergebnis = "";

    /*
     * Der String ergebnis wird solange verdoppelt bis die laenge groesser der Anzahl ist. Danach wird ein Substring der uebergebenen Laenge zurueckgegeben.
     */
    for ( int zaehler = 1; ergebnis.length() <= pAnzahlWiederholungen; zaehler++ )
    {
      ergebnis += pString;
    }

    return ergebnis;
  }
}
