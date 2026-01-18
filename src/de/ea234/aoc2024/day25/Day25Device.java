package de.ea234.aoc2024.day25;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Day25Device
{
  private static final int DEVICE_TYPE_LOCK          = 1;

  private static final int DEVICE_TYPE_KEY           = 0;

  private static final int DEVICE_TYPE_NOT_DEFINED   = 3;

  private static final int DEVICE_HEIGHT_NOT_DEFINED = -100;

  private static final int DEVICE_HEIGHT_MAX         = 5;

  private List< String >   list_input_str            = null;

  private String           debug_string              = null;

  private int              device_type               = DEVICE_TYPE_KEY;

  private int              device_length             = 0;

  private int[]            device_height;

  public Day25Device( String pInput )
  {
    list_input_str = Arrays.stream( pInput.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    boolean knz_first_row_set = list_input_str.get( 0 ).matches( "[#]+" );

    boolean knz_last_row_set = list_input_str.get( ( list_input_str.size() - 1 ) ).matches( "[#]+" );

    if ( knz_first_row_set && knz_last_row_set )
    {
      device_type = DEVICE_TYPE_NOT_DEFINED;
    }
    else if ( knz_first_row_set )
    {
      device_type = DEVICE_TYPE_LOCK;
    }
    else
    {
      device_type = DEVICE_TYPE_KEY;
    }

    device_length = list_input_str.get( 0 ).length();

    /*
     * The debug_string starts with the first Line
     */
    debug_string = list_input_str.get( 0 );

    device_height = new int[ device_length ];

    for ( int input_index = 1; input_index < ( list_input_str.size() - 1 ); input_index++ )
    {
      String row_input = list_input_str.get( input_index );

      debug_string += "\n" + row_input;

      for ( int code_index = 0; code_index < device_length; code_index++ )
      {
        char cur_char = row_input.charAt( code_index );

        if ( cur_char == '#' )
        {
          device_height[ code_index ] += 1;
        }
      }
    }

    /*
     * Get the last row (for keys)
     */
    debug_string += "\n" + list_input_str.get( ( list_input_str.size() - 1 ) );

    String device_csv = getCsvList( false );

    debug_string += "\n" + device_csv;

    debug_string += "\n" + ( device_type == DEVICE_TYPE_LOCK ? "Lock " : "Key  " );
  }

  public boolean isLock()
  {
    return ( device_type == DEVICE_TYPE_LOCK );
  }

  public boolean isKey()
  {
    return ( device_type == DEVICE_TYPE_KEY );
  }

  private boolean isUndefined()
  {
    return ( device_type == DEVICE_TYPE_NOT_DEFINED );
  }

  public String getDebugStr()
  {
    return debug_string;
  }

  private int getLength()
  {
    return device_length;
  }

  private int getHeight( int pIndex )
  {
    if ( ( pIndex >= 0 ) && ( pIndex < device_length ) )
    {
      return device_height[ pIndex ];
    }

    return DEVICE_HEIGHT_NOT_DEFINED;
  }

  public boolean doMatch( Day25Device pDeviceB )
  {
    if ( pDeviceB.isKey() && isKey() )
    {
      return false;
    }

    if ( pDeviceB.isLock() && isLock() )
    {
      return false;
    }

    /*
     * They cant match, if the device length are unequal.
     */
    if ( pDeviceB.getLength() != device_length )
    {
      return false;
    }

    for ( int code_index = 0; code_index < device_length; code_index++ )
    {
      if ( pDeviceB.getHeight( code_index ) + device_height[ code_index ] > DEVICE_HEIGHT_MAX )
      {
        return false;
      }
      else if ( pDeviceB.getHeight( code_index ) + device_height[ code_index ] < 0 )
      {
        return false;
      }
    }

    return true;
  }

  public String getDebugMatch( Day25Device pDeviceB )
  {
    if ( pDeviceB.isKey() && isKey() )
    {
      return "no match - both devices are keys";
    }

    if ( pDeviceB.isLock() && isLock() )
    {
      return "no match - both devices are locks";
    }

    if ( pDeviceB.getLength() != device_length )
    {
      return "no match - different device length - A = " + getLength() + " B " + pDeviceB.getLength();
    }

    boolean knz_do_match = true;

    String height_match_a = "- height - A = " + getCsvList( true ) + " B " + pDeviceB.getCsvList( true ) + " = ";

    String no_match_at = "";

    for ( int code_index = 0; code_index < device_length; code_index++ )
    {
      if ( pDeviceB.getHeight( code_index ) + device_height[ code_index ] > DEVICE_HEIGHT_MAX )
      {
        height_match_a += "-";

        knz_do_match = false;

        no_match_at += " Index " + code_index + " A " + getHeight( code_index ) + " B " + pDeviceB.getHeight( code_index ) + ", ";
      }
      else if ( pDeviceB.getHeight( code_index ) + device_height[ code_index ] < 0 )
      {
        knz_do_match = false;

        no_match_at += " Index " + code_index + " A " + getHeight( code_index ) + " B " + pDeviceB.getHeight( code_index ) + ", ";
      }
      else
      {
        height_match_a += " ";
      }
    }

    return ( knz_do_match ? "match    " : "no match " ) + height_match_a + no_match_at;
  }

  private String getCsvList( boolean pSetKomma )
  {
    String csv_string = "";

    for ( int index = 0; index < device_length; index++ )
    {
      if ( ( pSetKomma ) && ( index > 0 ) )
      {
        csv_string += ",";
      }

      csv_string += device_height[ index ];
    }

    return csv_string;
  }
}
