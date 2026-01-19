package de.ea234.aoc2024.day22;

import java.util.HashMap;
import java.util.Map.Entry;

public class Day22Buyer
{
  private long                    secret_number_start = 0;

  private long                    secret_number_cur   = 0;

  private long                    price_prev          = 0;

  private long                    price_cur           = 0;

  private long                    price_diff          = 0;

  private long                    count_loop          = 0;

  private long                    seq_price           = 0;

  private long                    seq_count_loop      = 0;

  private long[]                  arr_sequenze_search;

  private long[]                  arr_sequenze_check;

  private int                     arr_sequenze_index  = 0;

  private HashMap< String, Long > hash_map_key        = new HashMap< String, Long >();

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

      arr_sequenze_check[ idx ] = -88;
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

    /*
     * Calculate the price
     */
    price_cur = secret_number_cur % 10;

    /*
     * Calculate the difference to the previous price
     */
    price_diff = price_cur - price_prev;

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
     * Save Key-Sequenze and price to the hashmap
     */
    if ( count_loop > 3 )
    {
      /*
       * The hashmap key is the diff sequenze
       */
      String key_diff_sequenze = diffSequenzeToString();

      /*
       * Check if the key is already in the hashmap.
       * 
       * The first occurance of the diff sequenze counts.
       * If the key is not in the hashmap, then the key 
       * and the price is saved to hashmap. 
       */
      if ( hash_map_key.containsKey( key_diff_sequenze ) == false )
      {
        hash_map_key.put( key_diff_sequenze, Long.valueOf( price_cur ) );
      }
    }

    /*
     * Search for a specific sequenze is not realy needed anymore.
     */
    if ( seq_count_loop == 0 )
    {
      /*
       * Do a check for the sequenze.
       * The check starts with the current insert index.
       */
      if ( diffSequenzeCheck() )
      {
        seq_price = price_cur;

        seq_count_loop = count_loop;
      }
    }
  }

  public long generateNextSecretNumber( int pSequenzeNr, long pSecretNumber )
  {
    long secret_nr_new = 0;

    if ( pSequenzeNr == 1 )
    {
      secret_nr_new = pSecretNumber * 64;
    }
    else if ( pSequenzeNr == 2 )
    {
      secret_nr_new = pSecretNumber / 32;
    }
    else
    {
      secret_nr_new = pSecretNumber * 2048;
    }

    long secret_nr_after_mixing = pSecretNumber ^ secret_nr_new;

    long secret_nr_after_pruning = secret_nr_after_mixing % 16777216;

    return secret_nr_after_pruning;
  }

  private boolean diffSequenzeCheck()
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
       * Decrement the check index.
       */
      index_check_array--;

      if ( index_check_array < 0 )
      {
        index_check_array = arr_sequenze_check.length - 1;
      }
    }

    return true;
  }

  private String diffSequenzeToString()
  {
    String str_diff_sequenze = "[";

    String str_comma = "";

    int index_check_array = arr_sequenze_index;

    for ( int index_for = ( arr_sequenze_check.length - 1 ); index_for >= 0; index_for-- )
    {
      str_diff_sequenze += str_comma + arr_sequenze_check[ index_check_array ];

      str_comma = ",";

      index_check_array--;

      if ( index_check_array < 0 )
      {
        index_check_array = arr_sequenze_check.length - 1;
      }
    }

    return str_diff_sequenze + "]";
  }

  public void addToHashMap( HashMap< String, Long > pHashMap )
  {
    for ( Entry< String, Long > entry : hash_map_key.entrySet() )
    {
      /*
       * Get the current stored value for the key from the parameter hash map.
       */
      Long entry_value_current = pHashMap.get( entry.getKey() );

      /*
       * Check if a value was found. 
       */
      if ( entry_value_current == null )
      {
        /*
         * If there is no value, then the value is the current value
         */
        entry_value_current = entry.getValue();
      }
      else
      {
        /*
         * If there is a value in the hashmap, then add the value 
         * from this instance to the existing one.
         */
        entry_value_current = Long.valueOf( entry_value_current.longValue() + entry.getValue() );
      }

      /*
       * Store the value in the parameter hashmap.
       */
      pHashMap.put( entry.getKey(), entry_value_current );
    }
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
    return String.format( "%6d %15d - price old %d  new %d  diff %3d - seq idx %5d price %3d  %s ", count_loop, secret_number_cur, price_prev, price_cur, price_diff, seq_count_loop, seq_price, diffSequenzeToString() );
  }
}
