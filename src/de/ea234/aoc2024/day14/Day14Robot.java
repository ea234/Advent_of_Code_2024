package de.ea234.aoc2024.day14;

public class Day14Robot
{
  private String id           = "";

  private int    row_position = 0;

  private int    col_position = 0;

  private int    row_velocity = 0;

  private int    col_velocity = 0;

  public Day14Robot( int pIndex, String pInput )
  {
    id = "" + pIndex;

    String[] parts = pInput.trim().substring( 2 ).replace( " v=", "," ).split( "," );

    col_position = Integer.parseInt( parts[ 0 ] );

    row_position = Integer.parseInt( parts[ 1 ] );

    col_velocity = Integer.parseInt( parts[ 2 ] );

    row_velocity = Integer.parseInt( parts[ 3 ] );
  }

  public void move( int pNumberOfRows, int pNumberOfCols )
  {
    row_position += row_velocity;

    col_position += col_velocity;

    if ( col_position < 0 )
    {
      col_position += pNumberOfCols;
    }
    else if ( col_position >= pNumberOfCols )
    {
      col_position -= pNumberOfCols;
    }

    if ( row_position < 0 )
    {
      row_position += pNumberOfRows;
    }
    else if ( row_position >= pNumberOfRows )
    {
      row_position -= pNumberOfRows;
    }
  }

  public String getKey()
  {
    return "r" + row_position + "c" + col_position;
  }

  public int getRow()
  {
    return row_position;
  }

  public int getCol()
  {
    return col_position;
  }

  public void reverse()
  {
    row_velocity *= -1;
    col_velocity *= -1;
  }

  public String toString()
  {
    return String.format( "%6s = row %5d (%3d)  col %5d (%3d) " + getKey(), id, row_position, row_velocity, col_position, col_velocity );
  }
}
