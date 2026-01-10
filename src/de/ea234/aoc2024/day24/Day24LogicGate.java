package de.ea234.aoc2024.day24;

public class Day24LogicGate
{
  private String  input_wire_1_name  = null;

  private String  input_wire_2_name  = null;

  private long    input_wire_1_value = -1;

  private long    input_wire_2_value = -1;

  private String  cmd_operation      = null;

  private String  output_wire_name   = null;

  private long    output_wire_value  = -1;

  private boolean cmd_was_done       = false;

  public Day24LogicGate( String pInput )
  {
//    String[] part_arr = "tgd XOR rvg -> z12".split( " " );
//
//    for ( int i = 0; i < cchar_arr.length; i++ )
//    {
//      System.out.println( i + ": " + cchar_arr[ i ] );
//    }
//    
//    0: tgd
//    1: XOR
//    2: rvg
//    3: ->
//    4: z12

    String[] part_arr = pInput.trim().split( "\\s+" );

    input_wire_1_name = part_arr[ 0 ];

    input_wire_2_name = part_arr[ 2 ];

    output_wire_name = part_arr[ 4 ];

    cmd_operation = part_arr[ 1 ];

    cmd_was_done = false;
  }

  public long doCmd( Day24HashMap pHashMap )
  {
    if ( cmd_was_done )
    {
      return 1l;
    }

    input_wire_1_value = pHashMap.getHashMapValue( input_wire_1_name );

    input_wire_2_value = pHashMap.getHashMapValue( input_wire_2_name );

    boolean can_compute = ( input_wire_1_value > -1 ) && ( input_wire_2_value > -1 );

    if ( can_compute )
    {
      if ( cmd_operation.equals( "AND" ) )
      {
        output_wire_value = input_wire_1_value & input_wire_2_value;
      }
      else if ( cmd_operation.equals( "OR" ) )
      {
        output_wire_value = input_wire_1_value | input_wire_2_value;
      }
      else if ( cmd_operation.equals( "XOR" ) )
      {
        output_wire_value = input_wire_1_value ^ input_wire_2_value;
      }

      cmd_was_done = true;
    }
    else
    {
      output_wire_value = -1l;
    }

    pHashMap.saveHashMapValue( output_wire_name, output_wire_value );

    return can_compute ? 1l : 0l;
  }

  public String toString()
  {
    return String.format( " %-4s (%2d)  %-4s  %-4s (%2d)   %-4s (%2d)   has done " + cmd_was_done, input_wire_1_name, input_wire_1_value, cmd_operation, input_wire_2_name, input_wire_2_value, output_wire_name, output_wire_value );
  }

  public String getOutputWireName()
  {
    return output_wire_name;
  }

  public String getOutputWireValue()
  {
    return output_wire_name;
  }
}
