package de.ea234.aoc2024.day24;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day24HashMap
{
  private HashMap< String, Long > m_hash_map = null;

  public Day24HashMap()
  {
    m_hash_map = new HashMap< String, Long >();
  }

  public void saveHashMapValue( String pWire, long pValue )
  {
    m_hash_map.put( pWire, Long.valueOf( pValue ) );
  }

  public void saveHashMapValue( String pWire, Long pValue )
  {
    m_hash_map.put( pWire, pValue );
  }

  public long getHashMapValue( String pWire )
  {
    try
    {
      return m_hash_map.get( pWire ).longValue();
    }
    catch ( Exception exp )
    {
    }

    return -1;
  }

  public String getHashMapDebugString()
  {
    String debug_string = "";

    long index = 0;

    for ( Map.Entry< String, Long > entry : m_hash_map.entrySet() )
    {
      debug_string += "\n" + String.format( " Nr %6d  Wire %-10s Value %3d", index, entry.getKey(), entry.getValue() );

      index++;
    }

    return debug_string;
  }

  public boolean checkZWiresForDone()
  {
    List< String > z_keys = m_hash_map.keySet().stream().filter( k -> k.startsWith( "z" ) ).sorted( Comparator.reverseOrder() ).collect( Collectors.toList() );

    for ( String z_key : z_keys )
    {
      if ( getHashMapValue( z_key ) == -1 )
      {
        return false;
      }
    }

    return true;
  }

  public String getZWireStringDebug()
  {
    List< String > z_keys = m_hash_map.keySet().stream().filter( k -> k.startsWith( "z" ) ).sorted( Comparator.reverseOrder() ).collect( Collectors.toList() );

    String debug_string = "";

    for ( String z_key : z_keys )
    {
      debug_string += "\n " + z_key + " " + getHashMapValue( z_key );
    }

    return debug_string;
  }

  public String getZWireStringBinaer()
  {
    List< String > z_keys = m_hash_map.keySet().stream().filter( k -> k.startsWith( "z" ) ).sorted().collect( Collectors.toList() );

    String debug_string = "";

    for ( String z_key : z_keys )
    {
      debug_string += getHashMapValue( z_key );
    }

    return debug_string;
  }
}
