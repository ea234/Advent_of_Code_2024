package de.ea234.aoc2024.day23;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day23LanParty
{
  /*
   * --- Day 23: LAN Party ---
   * https://adventofcode.com/2024/day/23
   * 
   * https://www.youtube.com/watch?v=2GP_4TdoAL0
   * 
   * https://www.reddit.com/r/adventofcode/comments/1hkgj5b/2024_day_23_solutions/
   * 
   */

  public static void main( String[] args )
  {
    String test_1 = "kh-tc,qp-kh,de-cg,ka-co,yn-aq,qp-ub,cg-tb,vc-aq,tb-ka,wh-tc,yn-cg,kh-ub,ta-co,de-co,tc-td,tb-wq,wh-td,ta-ka,td-qp,aq-cg,wq-ub,ub-vc,de-ta,wq-aq,wq-vc,wh-yn,ka-de,kh-ta,co-tc,wh-qp,tb-vc,td-yn";

    calculatePart01( test_1, true );
  }

  private static void calculatePart01( String pString, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    calculatePart01( converted_string_list, pKnzDebug );
  }

  private static void calculatePart01( List< String > pListInput, boolean pKnzDebug )
  {
    /*
     * *******************************************************************************************************
     * Initializing Variables
     * *******************************************************************************************************
     */

    m_hash_map = new HashMap< String, String >();

    /*
     * *******************************************************************************************************
     * Loading the start values into the hashmap
     * *******************************************************************************************************
     */

    for ( String input_str : pListInput )
    {
      String[] connected_computers = input_str.split( "-" );

      addConnectedComputers( connected_computers[ 0 ], connected_computers[ 1 ] );

      addConnectedComputers( connected_computers[ 1 ], connected_computers[ 0 ] );
    }

    wl( "" );
    wl( "Result Lan Party " );
    wl( "" );
    wl( getHashMapDebugString() );
    wl( "" );
    wl( getConnectedToComputer( "t" ) );
    wl( "" );
    wl( getConnectedToComputer( "ta" ) );
    wl( getConnectedToComputer( "tb" ) );
    wl( getConnectedToComputer( "tc" ) );
    wl( getConnectedToComputer( "td" ) );
    wl( "" );
    //wl( getSortedListInputNew() );

    List< String > list_all_connections = getListAllConnections();
    
    for ( String cur_connection : list_all_connections )
    {
      wl( cur_connection );
    }


    /*
     * 
    * aq,cg,yn
    * aq,vc,wq
    * co,de,ka
    * co,de,ta
    * co,ka,ta
    * de,ka,ta
    * kh,qp,ub
    * qp,td,wh
    * tb,vc,wq
    * tc,td,wh
    * td,wh,yn
    * ub,vc,wq
    *     
    * co,de,ta
    * co,ka,ta
    * de,ka,ta
    * qp,td,wh
    * tb,vc,wq
    * tc,td,wh
    * td,wh,yn
    *

    Wenn A folgende Verbindungen hat
     
       A - B
       A - C
       A - D
       
    dann ist der Ring geschlossen, wenn es 
    
       B - C
       C - B
       
    gibt. Ist das nicht der Fall, wird die 
    Verbindung nach D geprueft: 
    
      A - B
      A - D
    
   Es muss dann eine Verbindung 
   
      B - D
      D - B 
      
   geben.


 Finde direkte Verbindungen.
 Finde eine Verbindung derart: <computer a> nach <computer b>, <computer b> nach <computer c>, <computer c> nach <computer a>

 Also, finde nicht alle Verbindungen zu einem Computer, sondern 
 finde in der Eingabe einen Verbindungsring, bei welchem 3 Computer enthalten sind und der letzte 
 Computer gleich wieder der Anfangscomputer ist.

 cq = cg-aq,cg-de,cg-tb,cg-yn


1. Hashmap
     Key Computer-Name 1 
     Key CSV-Liste der Verbindungen <computer name 1> nach <computer name 2> 

2. Rekursion
     Endbedinung = 3 Computer
     Endbedingung = liste erschöpft
     
     Eingabe Liste der Verbindungen die es zu testen gilt
     
aq-cg
    cg-aq - fällt weg, shon enthalten

    cg-de
        de-cg
        de-co
        de-ka
        de-ta
    
    cg-tb
        tb-cg
        tb-ka
        tb-vc
        tb-wq
    
    cg-yn
        yn-aq -------- Start Computer name
        yn-cg
        yn-td
        yn-wh


    * aq,cg,yn
    *
    * aq-cg
    * 
    * yn-aq
    * yn-cg
    *
    * cg-tb
    * 
    * Finde direkte ring pare
    * ag - cg   cg - yn   yn - ag 
    * 
    * 
    * * aq,vc,wq
    * vc-aq
    * 
    *  aq - vc  wq-aq   


    Result Lan Party 
    
     0  Computer    aq - ,cg,vc,wq,yn
     1  Computer    cg - ,aq,de,tb,yn
     2  Computer    co - ,de,ka,ta,tc
     3  Computer    de - ,cg,co,ka,ta
     4  Computer    ka - ,co,de,ta,tb
     5  Computer    kh - ,qp,ta,tc,ub
     6  Computer    qp - ,kh,td,ub,wh
     7  Computer    ta - ,co,de,ka,kh
     8  Computer    tb - ,cg,ka,vc,wq
     9  Computer    tc - ,co,kh,td,wh
    10  Computer    td - ,qp,tc,wh,yn
    11  Computer    ub - ,kh,qp,vc,wq
    12  Computer    vc - ,aq,tb,ub,wq
    13  Computer    wh - ,qp,tc,td,yn
    14  Computer    wq - ,aq,tb,ub,vc
    15  Computer    yn - ,aq,cg,td,wh
    
    ----------------------------------------------
    Computers connected to "t"
    
     1  Computer    de - ,cg,co,ka,ta
     2  Computer    cg - ,aq,de,tb,yn
     3  Computer    co - ,de,ka,ta,tc
     4  Computer    vc - ,aq,tb,ub,wq
     5  Computer    tc - ,co,kh,td,wh
     6  Computer    td - ,qp,tc,wh,yn
     7  Computer    wh - ,qp,tc,td,yn
     8  Computer    yn - ,aq,cg,td,wh
     9  Computer    ka - ,co,de,ta,tb
    10  Computer    wq - ,aq,tb,ub,vc
    11  Computer    kh - ,qp,ta,tc,ub
    12  Computer    qp - ,kh,td,ub,wh
    
    
    ----------------------------------------------
    Computers connected to "ta"
    
    1  Computer    de - ,cg,co,ka,ta
    2  Computer    co - ,de,ka,ta,tc
    3  Computer    ka - ,co,de,ta,tb
    4  Computer    kh - ,qp,ta,tc,ub
    
    ----------------------------------------------
    Computers connected to "tb"
    
    1  Computer    cg - ,aq,de,tb,yn
    2  Computer    vc - ,aq,tb,ub,wq
    3  Computer    ka - ,co,de,ta,tb
    4  Computer    wq - ,aq,tb,ub,vc
    
    ----------------------------------------------
    Computers connected to "tc"
    
    1  Computer    co - ,de,ka,ta,tc
    2  Computer    td - ,qp,tc,wh,yn
    3  Computer    wh - ,qp,tc,td,yn
    4  Computer    kh - ,qp,ta,tc,ub
    
    ----------------------------------------------
    Computers connected to "td"
    
    1  Computer    tc - ,co,kh,td,wh
    2  Computer    wh - ,qp,tc,td,yn
    3  Computer    yn - ,aq,cg,td,wh
    4  Computer    qp - ,kh,td,ub,wh
    
    
    aq-cg
    aq-vc
    aq-wq
    aq-yn
    
    cg-aq
    cg-de
    cg-tb
    cg-yn
    
    co-de
    co-ka
    co-ta
    co-tc
    
    de-cg
    de-co
    de-ka
    de-ta
    
    ka-co
    ka-de
    ka-ta
    ka-tb
    
    kh-qp
    kh-ta
    kh-tc
    kh-ub
    
    qp-kh
    qp-td
    qp-ub
    qp-wh
    
    ta-co
    ta-de
    ta-ka
    ta-kh
    
    tb-cg
    tb-ka
    tb-vc
    tb-wq
    
    tc-co
    tc-kh
    tc-td
    tc-wh
    
    td-qp
    td-tc
    td-wh
    td-yn
    
    ub-kh
    ub-qp
    ub-vc
    ub-wq
    
    vc-aq
    vc-tb
    vc-ub
    vc-wq
    
    wh-qp
    wh-tc
    wh-td
    wh-yn
    
    wq-aq
    wq-tb
    wq-ub
    wq-vc
    
    yn-aq
    yn-cg
    yn-td
    yn-wh
     * 
     */
  }


  public static String checkPair( String pConnection1, String pConnection2 )
  {
    String[] connected_computers_1 = pConnection1.split( "-" );

    String from_1 = null;
    String to_1 = null;

    String from_2 = null;
    String to_2 = null;

    if ( connected_computers_1[ 0 ].compareToIgnoreCase( connected_computers_1[ 1 ] ) < 0 )
    {
      wl( "A " + connected_computers_1[ 0 ] + " + " + connected_computers_1[ 1 ] );

      from_1 = connected_computers_1[ 0 ];
      to_1 = connected_computers_1[ 1 ];
    }
    else
    {
      wl( "B " + connected_computers_1[ 1 ] + " + " + connected_computers_1[ 0 ] );

      from_1 = connected_computers_1[ 1 ];
      to_1 = connected_computers_1[ 0 ];
    }

    String[] connected_computers_2 = pConnection1.split( "-" );

    if ( connected_computers_2[ 0 ].compareToIgnoreCase( connected_computers_2[ 1 ] ) < 0 )
    {
      wl( "A " + connected_computers_2[ 0 ] + " + " + connected_computers_2[ 1 ] );

      from_2 = connected_computers_1[ 0 ];
      to_2 = connected_computers_1[ 1 ];

    }
    else
    {
      wl( "B " + connected_computers_2[ 1 ] + " + " + connected_computers_2[ 0 ] );

      from_2 = connected_computers_1[ 1 ];
      to_2 = connected_computers_1[ 0 ];
    }

    if ( from_1.equals( from_2 ) )
    {
      String check_connection = to_1 + "-" + to_2;

      boolean verbindung_existiert = false;

      if ( verbindung_existiert )
      {
        return check_connection;
      }

      check_connection = to_2 + "-" + to_1;

      verbindung_existiert = false;

      if ( verbindung_existiert )
      {
        return check_connection;
      }
    }

    return null;
  }

  private static List< String > getListAllConnections()
  {
    List< String > list_all_connections = new ArrayList< String >();

    List< String > keys_all_computer_names = new ArrayList< String >( m_hash_map.keySet() );

    Collections.sort( keys_all_computer_names );

    for ( String key_computer_name_from : keys_all_computer_names )
    {
      String[] input_parts = m_hash_map.get( key_computer_name_from ).split( "," );

      List< String > list_computer_names = new ArrayList< String >();

      for ( String computer_name : input_parts )
      {
        if ( computer_name != null && !computer_name.isEmpty() )
        {
          list_computer_names.add( computer_name );
        }
      }

      Collections.sort( list_computer_names );

      for ( String computer_name_to : list_computer_names )
      {
        list_all_connections.add( key_computer_name_from + "-" + computer_name_to );
      }
    }

    return list_all_connections;
  }



  private static HashMap< String, String > m_hash_map = null;

  private static void addConnectedComputers( String pComputerNameA, String pComputerNameB )
  {
    /*
     * Assuming that both Strings are not null
     */

    String list_computer = getHashMapValue( pComputerNameA );

    if ( list_computer == null )
    {
      list_computer = "," + pComputerNameB;
    }
    else
    {
      list_computer = getSortetListConnectedComputer( list_computer + "," + pComputerNameB );
    }

    saveHashMapValue( pComputerNameA, list_computer );
  }

  private static void saveHashMapValue( String pKey, String pValue )
  {
    m_hash_map.put( pKey, pValue );
  }

  private static String getHashMapValue( String pKey )
  {
    return m_hash_map.get( pKey );
  }

  private static String getHashMapDebugString()
  {
    String debug_string = "";

    int entry_index = 0;

    List< String > keys = new ArrayList< String >( m_hash_map.keySet() );

    Collections.sort( keys );

    for ( String entry_key : keys )
    {
      debug_string += "\n" + String.format( "%4d  Computer %5s - %s", entry_index, entry_key, m_hash_map.get( entry_key ) );

      entry_index++;
    }

    return debug_string;
  }

  private static String getConnectedToComputer( String pStartPrefix )
  {
    String debug_string = "";

    debug_string += "\n----------------------------------------------\n";
    debug_string += "Computers connected to \"" + pStartPrefix + "\"\n";

    int entry_nr = 1;

    for ( Map.Entry< String, String > entry : m_hash_map.entrySet() )
    {
      if ( entry.getValue().indexOf( "," + pStartPrefix ) >= 0 )
      {
        debug_string += "\n" + String.format( "%4d  Computer %5s - %s", entry_nr, entry.getKey(), entry.getValue() );

        entry_nr++;
      }
    }

    return debug_string;
  }

  private static String getSortetListConnectedComputer( String pInput )
  {
    String[] input_parts = pInput.split( "," );

    List< String > list_computer_names = new ArrayList< String >();

    for ( String computer_name : input_parts )
    {
      if ( computer_name != null && !computer_name.isEmpty() )
      {
        list_computer_names.add( computer_name );
      }
    }

    Collections.sort( list_computer_names );

    return "," + String.join( ",", list_computer_names );
  }

  private static String getSortetListConnectedComputerX( String pComputerFrom, String pInput )
  {
    String[] input_parts = pInput.split( "," );

    List< String > list_computer_names = new ArrayList< String >();

    for ( String computer_name : input_parts )
    {
      if ( computer_name != null && !computer_name.isEmpty() )
      {
        list_computer_names.add( computer_name );
      }
    }

    Collections.sort( list_computer_names );

    StringBuilder sb = new StringBuilder();

    for ( String computer_name_to : list_computer_names )
    {
      sb.append( pComputerFrom + "-" + computer_name_to + "\n" );
    }

    return sb.toString();
  }

  private static String getSortedListInputNew()
  {
    String debug_string = "";

    List< String > keys = new ArrayList< String >( m_hash_map.keySet() );

    Collections.sort( keys );

    for ( String entry_key : keys )
    {
      debug_string += "\n" + getSortetListConnectedComputerX( entry_key, m_hash_map.get( entry_key ) );
    }

    return debug_string;

  }

  private static int getCountConnectedComputer( String pInput )
  {
    String[] input_parts = pInput.split( "," );

    return input_parts.length;
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day23_input.txt";

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
