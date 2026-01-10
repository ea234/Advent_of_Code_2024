package de.ea234.aoc2024.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class Day09DiskFragmenter
{
  /*
   * --- Day 9: Disk Fragmenter ---
   * https://adventofcode.com/2024/day/9
   * 
   * https://www.youtube.com/watch?v=bED-WiPjrSU
   * 
   * https://www.reddit.com/r/adventofcode/comments/1ha27bo/2024_day_9_solutions/
   * 
   * 
   * pInput.length() = 19999
   * len_list        = 94702
   * max file_id     = 9999
   * 
   * count_switch    = 19999
   * 
   * Result Part 1   = 6359213660505
   * 
   * Time 40
   * 
   *   -----------------------------------------
   *   
   *   pInput       2333133121414131402
   *   disk_l       22...333...1...333.22.4444.4444.333.444422
   *   
   *   free_space   333111110
   *   block_alloc  2313244342
   *   block_allocr 2434423132
   * 
   *   free_space   333111110
   *   block_alloc  2434423132
   *   
   *   
   *   free_space   3 3 3 1 1 1 1 1 0
   *   block_alloc  2 4 3 4 4 2 3 1 32
   *   
   * ea234@MsiZ370:/mnt/hd4tbb/daten/ang$ python3 xxx < aoc.txt
   * 6359213660505
   * 6381624803796
   */

  private static long    FREE_SPACE_HASH_MAP_VAL = -1;

  private static char    CHAR_FREE_SPACE         = '.';

  private static boolean TOGGLE_FREE_SPACE       = true;

  private static boolean TOGGLE_BLOCK_ALLOCATED  = false;

  public static void main( String[] args )
  {
    String test_input_1 = "2333133121414131402";
    String test_input_2 = "12345";
    String test_input_3 = "90909";

    calcPart01( test_input_1, true );

    //calcPart01( getListProd(), false );
  }

  private static HashMap< Long, Long > m_hash_map = new HashMap< Long, Long >();

  private static void saveHashMapValue( long pLongIndex, long pValue )
  {
    m_hash_map.put( Long.valueOf( pLongIndex ), Long.valueOf( pValue ) );
  }

  private static long getHashMapValue( long pLongIndex )
  {
    return m_hash_map.get( Long.valueOf( pLongIndex ) ).longValue();
  }

  private static void calcPart01( String pInput, boolean pKnzDebug )
  {
    long start_time = System.currentTimeMillis();

    m_hash_map = new HashMap< Long, Long >();

    /*
     * *******************************************************************************************************
     * Generating Disk Map
     * *******************************************************************************************************
     */

    /*
     * First file-id is 0
     */
    long file_id = -1;
    long len_list = -1;
    long cur_file_id = -1;

    /*
     * The toggle starts with an allocated block
     */
    boolean knz_toggle = TOGGLE_BLOCK_ALLOCATED;

    for ( char cur_char : pInput.toCharArray() )
    {
      /*
       * Check for valid character 
       */
      if ( ( cur_char >= '0' ) && ( cur_char <= '9' ) )
      {
        /*
         * The number of repetitions is determined from the character.
         */
        int nr_repeat = ( ( (int) cur_char ) - 48 );

        /*
         * Determine the block-art.
         */
        if ( knz_toggle == TOGGLE_FREE_SPACE )
        {
          /*
           * If the toggle var is 'free space', the file_id is the free space value
           */
          cur_file_id = FREE_SPACE_HASH_MAP_VAL;
        }
        else
        {
          /*
           * If the toggle var is file, than
           * ... the file_id is increased by one
           * ... the cur_file_id is set to that id
           */
          file_id++;

          cur_file_id = file_id;
        }

        /*
         * Save the file-id at the index 'len_list' to the hashmap.
         * Do that x-times.
         */
        for ( int x_times = 0; x_times < nr_repeat; x_times++ )
        {
          len_list++;

          saveHashMapValue( len_list, cur_file_id );
        }

        /*
         * Switch the toggle value
         */
        knz_toggle = !knz_toggle;
      }
    }

    /*
     * *******************************************************************************************************
     * Doing the optimization
     * *******************************************************************************************************
     */

    long count_switch = 0;

    long index_to_free_space = 0;

    long index_from_block = len_list;

    while ( ( index_from_block > index_to_free_space ) && ( count_switch < 300_000_000 ) )
    {
      /*
       * Determine the next index with free space 
       * Search from the last position to the position of the last block.
       * This is a ascending search.
       */
      index_to_free_space = getIndexNextFreeSpace( index_to_free_space, index_from_block );

      /*
       * Determine the next index with a file.
       * This is a descending search.
       */
      index_from_block = getIndexNextBlock( index_from_block, index_to_free_space );

      /*
       * Check if the from-index is still greater than the to index.
       */
      if ( index_from_block > index_to_free_space )
      {
        /*
         * Increment the counter
         */
        count_switch++;

        /*
         * get file id from the from position
         */
        long file_id_from_pos = getHashMapValue( index_from_block );

        /*
         * save the file_id to the new position
         */
        saveHashMapValue( index_to_free_space, file_id_from_pos );

        /*
         * set the from position to free space
         */
        saveHashMapValue( index_from_block, FREE_SPACE_HASH_MAP_VAL );

        if ( pKnzDebug )
        {
          wl( String.format( "Nr %6d  to %6d  from %6d  value %6d", count_switch, index_to_free_space, index_from_block, file_id_from_pos ) );
        }

        //wl( getDebugString2( 0, len_list ) );
      }
      /*
       * increase the index free
       */
      index_to_free_space++;

      /*
       * decrease the index from
       */
      index_from_block--;
    }

    /*
     * *******************************************************************************************************
     * Calculating Result Part 1
     * *******************************************************************************************************
     */

    long checksum_filesystem_sum = 0;

    for ( long index = 0; index < len_list; index++ )
    {
      try
      {
        long checksum_filesystem_cur = 0;

        long file_id_from_hash_map = getHashMapValue( index );

        if ( file_id_from_hash_map == FREE_SPACE_HASH_MAP_VAL )
        {
          file_id_from_hash_map = 0;
        }
        else
        {
          checksum_filesystem_cur = index * file_id_from_hash_map;
        }

        checksum_filesystem_sum += checksum_filesystem_cur;

        if ( pKnzDebug )
        {
          wl( String.format( "%6d * %6d = %6d  =  %6d", index, file_id_from_hash_map, checksum_filesystem_cur, checksum_filesystem_sum ) );
        }
      }
      catch ( Exception e )
      {
        wl( e.getLocalizedMessage() );
        e.printStackTrace( System.out );
      }
    }

    wl( "pInput.length() = " + pInput.length() );
    wl( "len_list        = " + len_list );
    wl( "max file_id     = " + file_id );
    wl( "" );
    wl( "count_switch    = " + pInput.length() );
    wl( "" );
    wl( "Result Part 1   = " + checksum_filesystem_sum );
    wl( "" );

    long end_time = System.currentTimeMillis();

    wl( "Time " + ( end_time - start_time ) );
  }

  private static long getIndexNextFreeSpace( long pLongFrom, long pLongTo )
  {
    for ( long index = pLongFrom; index < pLongTo; index++ )
    {
      try
      {
        long file_id_from_hash_map = getHashMapValue( index );

        if ( file_id_from_hash_map == FREE_SPACE_HASH_MAP_VAL )
        {
          return index;
        }
      }
      catch ( Exception e )
      {
        wl( e.getLocalizedMessage() );
        e.printStackTrace( System.out );
      }
    }

    return pLongTo;
  }

  private static long getIndexNextBlock( long pLongFrom, long pLongTo )
  {
    for ( long index = pLongFrom; index > pLongTo; index-- )
    {
      try
      {
        long file_id_from_hash_map = getHashMapValue( index );

        if ( file_id_from_hash_map != FREE_SPACE_HASH_MAP_VAL )
        {
          return index;
        }
      }
      catch ( Exception e )
      {
        wl( e.getLocalizedMessage() );
        e.printStackTrace( System.out );
      }
    }

    return pLongTo;
  }

  private static String getDebugString( long pLongFrom, long pLongTo )
  {
    String debug_string = "";

    for ( long index = pLongFrom; index < pLongTo; index++ )
    {
      try
      {
        debug_string += "\nIndex " + index + " " + getHashMapValue( index );
      }
      catch ( Exception e )
      {
      }
    }

    return debug_string;
  }

  private static String getDebugString2( long pLongFrom, long pLongTo )
  {
    String debug_string = "";

    for ( long index = pLongFrom; index < pLongTo; index++ )
    {
      try
      {
        if ( getHashMapValue( index ) == FREE_SPACE_HASH_MAP_VAL )
        {
          debug_string += "" + CHAR_FREE_SPACE;

        }
        else
        {
          debug_string += "" + getHashMapValue( index );
        }
      }
      catch ( Exception e )
      {
        //
      }
    }

    return debug_string;
  }

  private static String calcDiskLayout( String pInput )
  {
    StringBuilder result_disk_layout = new StringBuilder();

    boolean knz_toggle = TOGGLE_BLOCK_ALLOCATED;

    for ( char cur_char : pInput.toCharArray() )
    {
      wl( "" + cur_char );

      int nr_repeat = ( ( (int) cur_char ) - 48 );

      char repeat_char = cur_char;

      if ( knz_toggle == TOGGLE_FREE_SPACE )
      {
        repeat_char = CHAR_FREE_SPACE;
      }

      for ( int idx = 0; idx < nr_repeat; idx++ )
      {
        result_disk_layout.append( repeat_char );
      }

      knz_toggle = !knz_toggle;
    }

    return result_disk_layout.toString();
  }

  private static String getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day09_input.txt";

    try
    {
      string_array = Files.readAllLines( Path.of( datei_input ) );
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }

    String result_string = "";

    for ( String v : string_array )
    {
      result_string += v;
    }

    return result_string;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }
}
