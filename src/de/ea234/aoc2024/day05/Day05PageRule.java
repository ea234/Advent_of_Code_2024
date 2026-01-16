package de.ea234.aoc2024.day05;

public class Day05PageRule
{
  private int    page_a_int = 0;

  private int    page_b_int = 0;

  private String page_a_str = null;

  private String page_b_str = null;

  public Day05PageRule( String pInput )
  {
    String[] parts = pInput.split( "\\|" );

    page_a_int = Integer.parseInt( parts[ 0 ] );
    page_b_int = Integer.parseInt( parts[ 1 ] );

    page_a_str = parts[ 0 ];

    page_b_str = parts[ 1 ];
  }

  public int getPageA()
  {
    return page_a_int;
  }

  public int getPageB()
  {
    return page_b_int;
  }

  public String toString()
  {
    return String.format( "(Rule %5d %5d)", page_a_int, page_b_int );
  }

  public boolean apply( String pUpdateString )
  {
    /*
     * Rule only applies, when both pages are in one update
     */

    int pos_page_a = pUpdateString.indexOf( "," + page_a_str + "," );

    int pos_page_b = pUpdateString.indexOf( "," + page_b_str + "," );

    return ( ( pos_page_a >= 0 ) && ( pos_page_b >= 0 ) );
  }

  public int isPageABeforePageB( String pUpdateString )
  {
    int pos_page_a = pUpdateString.indexOf( "," + page_a_str + "," );

    int pos_page_b = pUpdateString.indexOf( "," + page_b_str + "," );

    /*
     * Both pages found and page a before page b
     */

    return ( ( ( pos_page_a >= 0 ) && ( pos_page_b >= 0 ) ) && ( pos_page_a < pos_page_b ) ) ? 0 : 1;
  }
}
