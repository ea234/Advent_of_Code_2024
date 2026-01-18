package de.ea234.aoc2024.day22;

public class Day22Buyer
{
  private long   secret_number_start = 0;

  private long   secret_number_cur   = 0;

  private long   price_prev          = 0;

  private long   price_cur           = 0;

  private long   price_diff          = 0;

  private long   count_loop          = 0;

  private long   seq_price           = 0;

  private long   seq_count_loop      = 0;

  private long[] arr_sequenze_search;

  private long[] arr_sequenze_check;

  private int    arr_sequenze_index  = 0;

  public Day22Buyer( String pSecretNumber, String pSequenze )
  {
    secret_number_start = Long.parseLong( pSecretNumber );

    secret_number_cur = secret_number_start;

    price_cur = secret_number_cur % 10;

    price_prev = price_cur;

    price_diff = 0;

    String[] parts = pSequenze.split( "," );

    arr_sequenze_search = new long[ parts.length ];

    arr_sequenze_check = new long[ parts.length ];

    for ( int idx = 0; idx < parts.length; idx++ )
    {
      arr_sequenze_search[ idx ] = Long.parseLong( parts[ idx ].trim() );

      arr_sequenze_check[ idx ] = 0;
    }
  }

  public void generateNextSecretNumber()
  {
    count_loop++;

    price_prev = price_cur;

    long step_1 = generateNextSecretNumber( 1, secret_number_cur );

    long step_2 = generateNextSecretNumber( 2, step_1 );

    long step_3 = generateNextSecretNumber( 3, step_2 );

    secret_number_cur = step_3;

    /*
     * Part 2
     */

    price_cur = secret_number_cur % 10;

    price_diff = price_cur - price_prev;

    if ( seq_count_loop == 0 )
    {
      /*
       * Increase the insert index for the new price-diff value
       */
      arr_sequenze_index++;

      /*
       * If the insert index exceeds the lenth of the array, then 
       * place the index back to front.
       */
      if ( arr_sequenze_index >= arr_sequenze_check.length )
      {
        arr_sequenze_index = 0;
      }

      /*
       * Save the current price diff to the insert index.
       */
      arr_sequenze_check[ arr_sequenze_index ] = price_diff;

      /*
       * Do a check for the sequenze.
       * The check starts with the current insert index.
       */
      if ( checkSequenze() )
      {
        seq_price = price_cur;

        seq_count_loop = count_loop;
      }

      if ( seqToString().equals( "[-2,1,-1,3]" ) )
      {
        System.out.println( "Moin" );
      }
    }
  }

  private boolean checkSequenze()
  {
    /*
     * Check in reverse
     * The last difference, is the last value in the search sequenze 
     */

    int index_check_array = arr_sequenze_index;

    for ( int index_search_array = ( arr_sequenze_check.length - 1 ); index_search_array >= 0; index_search_array-- )
    {
      /*
       * Get the current price-diff from the check-array at index index_check_array.
       * 
       * Get the searched price-diff from the sequenze-array at index_search_array.
       * 
       * If these values are different the check is over. 
       * False is returned.
       */
      if ( arr_sequenze_check[ index_check_array ] != arr_sequenze_search[ index_search_array ] )
      {
        return false;
      }

      /*
       * Increment the check index.
       */
      index_check_array--;

      if ( index_check_array < 0 )
      {
        index_check_array = arr_sequenze_check.length - 1;
      }
    }

    return true;
  }

  private String seqToString()
  {
    String x_string = "[";

    int index_check_array = arr_sequenze_index;

    for ( int index_for = ( arr_sequenze_check.length - 1 ); index_for >= 0; index_for-- )
    {
      if ( index_for > 0 )
      {
        x_string += ",";
      }

      x_string += arr_sequenze_check[ index_check_array ];

      index_check_array--;

      if ( index_check_array < 0 )
      {
        index_check_array = arr_sequenze_check.length - 1;
      }
    }

    return x_string + "]";
  }

  public long generateNextSecretNumber( int seq_nr, long secret_number_cur )
  {
    long secret_nr_new = 0;

    if ( seq_nr == 1 )
    {
      secret_nr_new = secret_number_cur * 64;
    }
    else if ( seq_nr == 2 )
    {
      secret_nr_new = secret_number_cur / 32;
    }
    else
    {
      secret_nr_new = secret_number_cur * 2048;
    }

    long secret_nr_after_mixing = secret_number_cur ^ secret_nr_new;

    long secret_nr_after_pruning = secret_nr_after_mixing % 16777216;

    return secret_nr_after_pruning;
  }

  public long getSecretNumberCur()
  {
    return secret_number_cur;
  }

  public long getSecretNumberStart()
  {
    return secret_number_start;
  }

  public long getPrice()
  {
    return price_cur;
  }

  public long getSeqPrice()
  {
    return seq_price;
  }

  public String toString()
  {
    return String.format( "%6d %15d - price old %d  new %d  diff %3d - seq idx %5d price %3d  %s ", count_loop, secret_number_cur, price_prev, price_cur, price_diff, seq_count_loop, seq_price, seqToString() );
  }

}
