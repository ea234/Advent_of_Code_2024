package de.ea234.aoc2024.day20;

import de.ea234.util.FkStringFeld;

public class Day20CheatPoint
{
  private static final int WALL_TIME           = 1;

  private String           m_start_coordinates = "";

  private String           m_end_coordinates   = "";

  private long             m_start_len         = 0;

  private long             m_end_len           = 0;

  private long             m_cheat_point_nr    = 0;

  public Day20CheatPoint( long pR1, long pC1, long pLen1, long pR2, long pC2, long pNr )
  {
    m_start_coordinates = "R" + pR1 + "C" + pC1;

    m_end_coordinates = "R" + pR2 + "C" + pC2;

    m_start_len = pLen1;

    m_cheat_point_nr = pNr;
  }

  public long getNr()
  {
    return m_cheat_point_nr;
  }

  public long getLen()
  {
    /*
     * Startpunkt zählt nicht mit.
     * Erst im nächsten Schritt kommt die Einsparung
     * 
     * Der durchsprungene wall muss hinzuaddiert werden
     */
    return ( ( m_start_len + 1 ) - m_end_len ) + WALL_TIME;
  }

  public long getLenAbs()
  {
    return Math.abs( getLen() );
  }

  public void setEndLen( long pEndLen )
  {
    m_end_len = pEndLen;
  }

  public String toString()
  {
    return "CNR " + FkStringFeld.getFeldRechtsMin( m_cheat_point_nr, 6 ) + " Len " + FkStringFeld.getFeldRechtsMin( getLenAbs(), 6 ) + " Start " + FkStringFeld.getFeldLinksMin( m_start_coordinates, 10 ) + " To " + FkStringFeld.getFeldLinksMin( m_end_coordinates, 10 ) + "   " + FkStringFeld.getFeldRechtsMin( m_start_len, 6 ) + " - " + FkStringFeld.getFeldRechtsMin( m_end_len, 6 ) + " = " + FkStringFeld.getFeldRechtsMin( getLen(), 6 );
  }
}
