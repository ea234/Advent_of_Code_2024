package de.ea234.aoc2024.day24;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day24CrossedWires
{

  /*
   * --- Day 24: Crossed Wires ---
   * https://adventofcode.com/2024/day/24
   * 
   * https://www.youtube.com/watch?v=1tIMaTxLl08
   * 
   * https://www.reddit.com/r/adventofcode/comments/1hl698z/2024_day_24_solutions/
   * 
   * 
   * 
   *  A ----+\
   *        | XOR ----> Sum
   *  B ----+/
   *             \
   *              XOR ----> Sum
   *                    ^
   *   Cin  -----------|
   *                    \
   *                     AND ----> Cout
   *  A ---------------+         /
   *                    \       /
   *  B --------------+  \     /
   *                    OR ----/ 
   *  Cin -------------+ 
   * 
   * 
   * A ----+---+--- XOR ---+ 
   *       |   |           \
   * B ----+---+--- XOR ----> Sum
   *           |           /
   * Cin ------+----------+
   *           |         AND
   *           +---------+----> Cout
   *           
   *           
   *           
   * Bits A=1, B=1. Ein Full Adder hat ein Cin (Carry-in). 
  
  Ein einfacher Full Adder:
  
    Sum = A XOR B XOR Cin
    Cout = majority(A, B, Cin) = (A AND B) OR (A AND Cin)   OR (B AND Cin)
                                  A_AND_B      A_AND_CARRY     B_AND_CARRY
                                  
                                  
  Bei A=1, B=1, Cin=0:
  
    Sum = 1 XOR 1 XOR 0 = 0
    Cout = (1 AND 1) OR (1 AND 0) OR (1 AND 0) = 1
  
  Ergebnis:
  
    Sum = 0
    Cout = 1
  
  a00 XOR b00     -> A_XOR_B
  c00 XOR A_XOR_B -> Z00
  
  a00 AND b00 -> A_AND_B
  a00 AND c00 -> A_AND_CARRY
  y00 AND c00 -> B_AND_CARRY
  
  A_AND_B OR A_AND_CARRY = C_OR1
  C_OR1   OR B_AND_CARRY = C_OR2

   * 
   * --------------------------------------------------------------------------------------------- 
   * Resultvalues Z-Wires 
   * 
   * 
   *  z45 1
   *  z44 0
   *  z43 1
   *  z42 1
   *  z41 1
   *  z40 1
   *  z39 0
   *  z38 0
   *  z37 0
   *  z36 0
   *  z35 1
   *  z34 1
   *  z33 1
   *  z32 1
   *  z31 1
   *  z30 1
   *  z29 1
   *  z28 1
   *  z27 1
   *  z26 1
   *  z25 1
   *  z24 0
   *  z23 1
   *  z22 0
   *  z21 1
   *  z20 1
   *  z19 0
   *  z18 1
   *  z17 0
   *  z16 1
   *  z15 1
   *  z14 1
   *  z13 1
   *  z12 0
   *  z11 0
   *  z10 1
   *  z09 0
   *  z08 0
   *  z07 0
   *  z06 1
   *  z05 1
   *  z04 1
   *  z03 0
   *  z02 0
   *  z01 0
   *  z00 0
   * 
   * 
   * result_value_binary  1011110000111111111110101101011110010001110000
   * 
   * result_value_decimal 51745744348272
   * 
   * 
   * 
   */

  private static final long NO_OUTPUT = -1;

  public static void main( String[] args )
  {
    String test_1 = "x00: 1,x01: 1,x02: 1,y00: 0,y01: 1,y02: 0,,x00 AND y00 -> z00,x01 XOR y01 -> z01,x02 OR y02 -> z02";

    String j_str = "";

    j_str += "x00: 1";
    j_str += ",x01: 0";
    j_str += ",x02: 1";
    j_str += ",x03: 1";
    j_str += ",x04: 0";
    j_str += ",y00: 1";
    j_str += ",y01: 1";
    j_str += ",y02: 1";
    j_str += ",y03: 1";
    j_str += ",y04: 1";
    j_str += ",";
    j_str += ",ntg XOR fgs -> mjb";
    j_str += ",y02 OR  x01 -> tnw";
    j_str += ",kwq OR  kpj -> z05";
    j_str += ",x00 OR  x03 -> fst";
    j_str += ",tgd XOR rvg -> z01";
    j_str += ",vdt OR  tnw -> bfw";
    j_str += ",bfw AND frj -> z10";
    j_str += ",ffh OR  nrd -> bqk";
    j_str += ",y00 AND y03 -> djm";
    j_str += ",y03 OR  y00 -> psh";
    j_str += ",bqk OR  frj -> z08";
    j_str += ",tnw OR  fst -> frj";
    j_str += ",gnj AND tgd -> z11";
    j_str += ",bfw XOR mjb -> z00";
    j_str += ",x03 OR  x00 -> vdt";
    j_str += ",gnj AND wpb -> z02";
    j_str += ",x04 AND y00 -> kjc";
    j_str += ",djm OR  pbm -> qhw";
    j_str += ",nrd AND vdt -> hwm";
    j_str += ",kjc AND fst -> rvg";
    j_str += ",y04 OR  y02 -> fgs";
    j_str += ",y01 AND x02 -> pbm";
    j_str += ",ntg OR  kjc -> kwq";
    j_str += ",psh XOR fgs -> tgd";
    j_str += ",qhw XOR tgd -> z09";
    j_str += ",pbm OR  djm -> kpj";
    j_str += ",x03 XOR y03 -> ffh";
    j_str += ",x00 XOR y04 -> ntg";
    j_str += ",bfw OR  bqk -> z06";
    j_str += ",nrd XOR fgs -> wpb";
    j_str += ",frj XOR qhw -> z04";
    j_str += ",bqk OR  frj -> z07";
    j_str += ",y03 OR  x01 -> nrd";
    j_str += ",hwm AND bqk -> z03";
    j_str += ",tgd XOR rvg -> z12";
    j_str += ",tnw OR  pbm -> gnj";

    //calculatePart01( j_str, true );

    calculatePart01( getListProd(), true );
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

    HashMap< String, Day24LogicGate > hash_map_logic_gates = new HashMap< String, Day24LogicGate >();

    Day24HashMap hash_map_wires = new Day24HashMap();

    /*
     * *******************************************************************************************************
     * Loading the start values into the hashmap
     * *******************************************************************************************************
     */

    boolean knz_read_cmds = false;

    for ( String input_str : pListInput )
    {
      /*
       * Blank line 
       */
      if ( input_str.trim().isBlank() )
      {
        knz_read_cmds = true;
      }
      else if ( knz_read_cmds )
      {
        Day24LogicGate xlog = new Day24LogicGate( input_str );

        hash_map_logic_gates.put( xlog.getOutputWireName(), xlog );

        //wl( xlog.toString() );
      }
      else
      {
        String[] pp = input_str.split( ":" );

        //wl( "Startvalue " + input_str );

        hash_map_wires.saveHashMapValue( pp[ 0 ], Long.valueOf( pp[ 1 ].trim() ) );
      }
    }

    /*
     * *******************************************************************************************************
     * Loop over commands
     * *******************************************************************************************************
     */

    long logic_gates_sum = hash_map_logic_gates.size();

    wl( "" );
    wl( "" );

    long logic_gates_ok = 0;

    long loop_counter = 0;

    while ( ( logic_gates_ok != logic_gates_sum ) && ( loop_counter < 100_000_000 ) )
    {
      loop_counter++;

      wl( "*******************************************************************************************************" );
      wl( "" );
      wl( "Loop " + loop_counter );
      wl( "" );

      logic_gates_ok = 0;

      for ( Map.Entry< String, Day24LogicGate > entry : hash_map_logic_gates.entrySet() )
      {
        logic_gates_ok += entry.getValue().doCmd( hash_map_wires );

        wl( entry.getValue().toString() );
      }

      wl( "" );
      wl( String.format( "Logic-Gates OK %5d from %5d ", logic_gates_ok, logic_gates_sum ) );
      wl( "" );

      if ( hash_map_wires.checkZWiresForDone() )
      {
        break;
      }
    }

    /*
     * *******************************************************************************************************
     * Calculate the result value for part 1
     * *******************************************************************************************************
     */

    wl( "" );
    wl( "Resultvalues Z-Wires " );
    wl( "" );
    wl( hash_map_wires.getZWireStringDebug() );
    wl( "" );

    String result_value_binary = hash_map_wires.getZWireStringBinaer();

    BigInteger value = new BigInteger( result_value_binary, 2 );

    String result_value_decimal = value.toString( 10 );

    wl( "" );
    wl( "result_value_binary  " + result_value_binary );
    wl( "" );
    wl( "result_value_decimal " + result_value_decimal );
    wl( "" );
    wl( "" );
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day24_input.txt";

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
