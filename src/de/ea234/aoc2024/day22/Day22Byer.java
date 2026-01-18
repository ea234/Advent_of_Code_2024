package de.ea234.aoc2024.day22;

public class Day22Byer
{
  private long   secret_number_start = 0;

  private long   secret_number_cur   = 0;

  private long   price_prev          = 0;

  private long   price_cur           = 0;

  private long   price_diff          = 0;

  private String sequenze_of_changes = "";

  private long   count_loop          = 0;

  public Day22Byer( String pSecretNumber )
  {
    secret_number_start = Long.valueOf( pSecretNumber );

    secret_number_cur = secret_number_start;

    price_cur = secret_number_cur % 10;

    price_prev = price_cur;

    price_diff = price_cur - price_prev;
  }

  public void generateNextSecretNumber()
  {
    count_loop++;

    price_prev = price_cur;

    long s1 = generateNextSecretNumber( 1, secret_number_cur );

    long s2 = generateNextSecretNumber( 2, s1 );

    long s3 = generateNextSecretNumber( 3, s2 );

    secret_number_cur = s3;

    /*
     * Part 2
     */

    price_cur = secret_number_cur % 10;

    price_diff = price_cur - price_prev;

    sequenze_of_changes += "," + price_diff;
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

  public long getPriceCur()
  {
    return price_cur;
  }

  public long getPriceDiff()
  {
    return price_diff;
  }

  public String toString()
  {
    return String.format( "%6d %15d - price old %d  new %d  diff %3d  %s ", count_loop, secret_number_cur, price_prev, price_cur, price_diff, "");
    
    //return String.format( "%6d %15d - price old %d  new %d  diff %3d  %s ", count_loop, secret_number_cur, price_prev, price_cur, price_diff, sequenze_of_changes );

    //return String.format( "%6d %15d  %15d ", count_loop, secret_number_start, secret_number_cur );
  }
}
