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
     * 
     *  0  Computer    aq - ,cg,vc,wq,yn
     *  1  Computer    cg - ,aq,de,tb,yn
     *  2  Computer    co - ,de,ka,ta,tc
     *  3  Computer    de - ,cg,co,ka,ta
     *  4  Computer    ka - ,co,de,ta,tb
     *  5  Computer    kh - ,qp,ta,tc,ub
     *  6  Computer    qp - ,kh,td,ub,wh
     *  7  Computer    ta - ,co,de,ka,kh
     *  8  Computer    tb - ,cg,ka,vc,wq
     *  9  Computer    tc - ,co,kh,td,wh
     * 10  Computer    td - ,qp,tc,wh,yn
     * 11  Computer    ub - ,kh,qp,vc,wq
     * 12  Computer    vc - ,aq,tb,ub,wq
     * 13  Computer    wh - ,qp,tc,td,yn
     * 14  Computer    wq - ,aq,tb,ub,vc
     * 15  Computer    yn - ,aq,cg,td,wh
     * 
     * 
     * ----------------------------------------------
     * Computers connected to "ta"
     * 
     * 1  Computer    de - ,cg,co,ka,ta * co,ka,ta
     * 2  Computer    co - ,de,ka,ta,tc * de,ka,ta
     * 3  Computer    ka - ,co,de,ta,tb * co,de,ta
     * 
     * 4  Computer    kh - ,qp,ta,tc,ub 
     * 
     * ----------------------------------------------
     * Computers connected to "tb"
     * 
     * 1  Computer    cg - ,aq,de,tb,yn
     * 2  Computer    vc - ,aq,tb,ub,wq
     * 3  Computer    ka - ,co,de,ta,tb
     * 4  Computer    wq - ,aq,tb,ub,vc
     * 
     * ----------------------------------------------
     * Computers connected to "tc"
     * 
     * 1  Computer    co - ,de,ka,ta,tc
     * 2  Computer    td - ,qp,tc,wh,yn
     * 3  Computer    wh - ,qp,tc,td,yn
     * 4  Computer    kh - ,qp,ta,tc,ub
     * 
     * ----------------------------------------------
     * Computers connected to "td"
     * 
     * 1  Computer    tc - ,co,kh,td,wh
     * 2  Computer    wh - ,qp,tc,td,yn
     * 3  Computer    yn - ,aq,cg,td,wh
     * 4  Computer    qp - ,kh,td,ub,wh
    
    */

    /*
     * 
     * <computer a1> to <computer b1>, <computer b1> to <computer c1>, <computer c1> to <computer a1>
     * 
     * Computer A has the following connections
     *  
     *    A - B
     *    A - C
     *    A - D
     *    A - Z
     *
     * The first two connections are checked.
     *  
     *    A - B
     *    A - C
     *
     * If there is a circle (a group of 3) then there 
     * have to be a connection between B and C
     * 
     *    B - C
     *    C - B
     *    
     * If thats not the case, the first and the 
     * second connection are checked. 
     * 
     *   A - B
     *   A - D
     * 
     * There should be a Connection between B and D. 
     * 
     *   B - D
     *   D - B 
     *   
     *
     * -------------------------------------------------
     * 
     * yn-aq  1 with 2   1 with 3   1 with 4
     * yn-cg  2 with 1   2 with 3   2 with 4
     * yn-td  3 with 1   3 with 2   3 with 4
     * yn-wh  4 with 1   4 with 3   4 with 1
     * 
     * yn-aq  1 with 2   1 with 3   1 with 4
     * yn-cg             2 with 3   2 with 4
     * yn-td                        3 with 4
     * yn-wh                      
     *
     * -------------------------------------------------
     * 
     * n+0 = 1
     * n+1 = 2
     * n+2 = 3
     * n+3 = 4
     * 
     * yn-aq  n+0 with n+1   n+0 with n+2   n+0 with n+3
     * yn-cg                 n+1 with n+2   n+1 with n+3
     * yn-td                                n+2 with n+3
     * yn-wh                      
     * 
     * 
     * n+0 with n+1    
     * n+0 with n+2  
     * n+0 with n+3
     * 
     * n+1 with n+2  
     * n+1 with n+3
     * 
     * n+2 with n+3
     * 
     * 
     * ------------------------------------
     * 
     *  index 1    0 = aq-cg = cg   
     *  index 2    1 = aq-vc = vc   
     * 
     *  con_check_1      cg-vc  connection exists  false
     *  con_check_2      vc-cg  connection exists  false
     * 
     * 
     * ------------------------------------
     * 
     *  index 1    0 = aq-cg = cg   
     *  index 2    2 = aq-wq = wq   
     * 
     *  con_check_1      cg-wq  connection exists  false
     *  con_check_2      wq-cg  connection exists  false
     * 
     * 
     * ------------------------------------
     * 
     *  index 1    0 = aq-cg = cg   
     *  index 2    3 = aq-yn = yn   
     * 
     *  con_check_1      cg-yn  connection exists  true
     *  con_check_2      yn-cg  connection exists  true
     * 
     * 
     * con_exists     true
     * 
     * index_1 0
     * index_2 3
     * 
     * 
     * Loop aq - cg - yn  
     */

    List< String > list_all_connections_c1 = getListAllConnectionsC( "aq" );

    int count_con = list_all_connections_c1.size();

    boolean con_exists = false;

    int break_1 = count_con - 1;
    int break_2 = count_con;

    int con_1_index = 0;
    int con_2_index = 0;

    while ( ( con_exists == false ) && ( con_1_index < break_1 ) )
    {
      String con_1_connection_string = list_all_connections.get( con_1_index );

      String[] con_1_array_computer_names = con_1_connection_string.split( "-" );

      for ( con_2_index = con_1_index + 1; con_2_index < break_2; con_2_index++ )
      {
        /*
         * Get the connections from the list of connections 
         */
        String con_2_connection_string = list_all_connections.get( con_2_index );

        /*
         * Split the connection-string to obtain the target computer names
         */
        String[] con_2_array_computer_names = con_2_connection_string.split( "-" );

        /*
         * Create the connections to be checked.
         */
        String con_1_connection_string_new = con_1_array_computer_names[ 1 ] + "-" + con_2_array_computer_names[ 1 ];
        String con_2_connection_string_new = con_2_array_computer_names[ 1 ] + "-" + con_1_array_computer_names[ 1 ];

        con_exists = list_all_connections.contains( con_1_connection_string_new );

        if ( con_exists == false )
        {
          con_exists = list_all_connections.contains( con_2_connection_string_new );
        }

        wl( "" );
        wl( "------------------------------------" );
        wl( "" );
        wl( String.format( " index 1 %4d = %s = %s   ", con_1_index, con_1_connection_string, con_1_array_computer_names[ 1 ] ) );
        wl( String.format( " index 2 %4d = %s = %s   ", con_2_index, con_2_connection_string, con_2_array_computer_names[ 1 ] ) );
        wl( "" );
        wl( " connection 1 " + con_1_connection_string_new + "  connection exists  " + list_all_connections.contains( con_1_connection_string_new ) );
        wl( " connection 2 " + con_2_connection_string_new + "  connection exists  " + list_all_connections.contains( con_2_connection_string_new ) );

        if ( con_exists )
        {
          break;
        }
      }

      if ( con_exists == false )
      {
        con_1_index++;
      }
    }

    wl( "" );
    wl( "" );
    wl( "con_exists     " + con_exists );
    wl( "" );
    wl( "index_1 " + con_1_index );
    wl( "index_2 " + con_2_index );
    wl( "" );
    wl( "" );

    String con_1 = list_all_connections.get( con_1_index );
    String con_2 = list_all_connections.get( con_2_index );

    String[] con_1_computer = con_1.split( "-" );
    String[] con_2_computer = con_2.split( "-" );

    wl( "Loop " + con_1_computer[ 0 ] + " - " + con_1_computer[ 1 ] + " - " + con_2_computer[ 1 ] + "  " );
  }

  private static HashMap< String, String > m_hash_map = null;

  private static void addConnectedComputers( String pComputerNameA, String pComputerNameB )
  {
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

  private static List< String > getListAllConnectionsC( String pKeyComputerNameFrom )
  {
    List< String > list_all_connections = new ArrayList< String >();

    String[] input_parts = m_hash_map.get( pKeyComputerNameFrom ).split( "," );

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
      list_all_connections.add( pKeyComputerNameFrom + "-" + computer_name_to );
    }

    return list_all_connections;
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
