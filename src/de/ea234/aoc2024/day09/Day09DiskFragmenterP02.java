package de.ea234.aoc2024.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import de.ea234.util.FkStringFeld;

public class Day09DiskFragmenterP02
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
   * 2333133121414131402
   * 
   * 
   *      0      0  ID      0  Capacity      2  
   *      1      1  ID     -1  Capacity      3  
   *      2      2  ID      1  Capacity      3  
   *      3      3  ID     -1  Capacity      3  
   *      4      4  ID      2  Capacity      1  
   *      5      5  ID     -1  Capacity      3  
   *      6      6  ID      3  Capacity      3  
   *      7      7  ID     -1  Capacity      1  
   *      8      8  ID      4  Capacity      2  
   *      9      9  ID     -1  Capacity      1  
   *     10     10  ID      5  Capacity      4  
   *     11     11  ID     -1  Capacity      1  
   *     12     12  ID      6  Capacity      4  
   *     13     13  ID     -1  Capacity      1  
   *     14     14  ID      7  Capacity      3  
   *     15     15  ID     -1  Capacity      1  
   *     16     16  ID      8  Capacity      4  
   *     17     17  ID     -1  Capacity      0  
   *     18     18  ID      9  Capacity      2  
   * 
   * 
   * 
   * ----------------------------------------------------------------------
   * 
   * File ID 9  index_fat_alloc_block_from 19  index_fat_free_block 1
   * 
   * 0099.111...2...333.44.5555.6666.777.8888..    3432
   * 
   * 
   *      0      0  ID      0  Capacity      2  
   *      1 100018  ID      9  Capacity      2  
   *      2      1  ID     -1  Capacity      1  
   *      3      2  ID      1  Capacity      3  
   *      4      3  ID     -1  Capacity      3  
   *      5      4  ID      2  Capacity      1  
   *      6      5  ID     -1  Capacity      3  
   *      7      6  ID      3  Capacity      3  
   *      8      7  ID     -1  Capacity      1  
   *      9      8  ID      4  Capacity      2  
   *     10      9  ID     -1  Capacity      1  
   *     11     10  ID      5  Capacity      4  
   *     12     11  ID     -1  Capacity      1  
   *     13     12  ID      6  Capacity      4  
   *     14     13  ID     -1  Capacity      1  
   *     15     14  ID      7  Capacity      3  
   *     16     15  ID     -1  Capacity      1  
   *     17     16  ID      8  Capacity      4  
   *     18     17  ID     -1  Capacity      0  Block - 1 from 19
   *     19     18  ID     -1  Capacity      2  Alt 9
   * 
   * 
   * ----------------------------------------------------------------------
   * 
   * File ID 8  index_fat_alloc_block_from 17  index_fat_free_block -1
   * 
   * 0099.111...2...333.44.5555.6666.777.8888..    3432
   * 
   * 
   *      0      0  ID      0  Capacity      2  
   *      1 100018  ID      9  Capacity      2  
   *      2      1  ID     -1  Capacity      1  
   *      3      2  ID      1  Capacity      3  
   *      4      3  ID     -1  Capacity      3  
   *      5      4  ID      2  Capacity      1  
   *      6      5  ID     -1  Capacity      3  
   *      7      6  ID      3  Capacity      3  
   *      8      7  ID     -1  Capacity      1  
   *      9      8  ID      4  Capacity      2  
   *     10      9  ID     -1  Capacity      1  
   *     11     10  ID      5  Capacity      4  
   *     12     11  ID     -1  Capacity      1  
   *     13     12  ID      6  Capacity      4  
   *     14     13  ID     -1  Capacity      1  
   *     15     14  ID      7  Capacity      3  
   *     16     15  ID     -1  Capacity      1  
   *     17     16  ID      8  Capacity      4  
   *     18     17  ID     -1  Capacity      0  Block - 1 from 19
   *     19     18  ID     -1  Capacity      2  Alt 9
   * 
   * 
   * ----------------------------------------------------------------------
   * 
   * File ID 7  index_fat_alloc_block_from 16  index_fat_free_block 4
   * 
   * 0099.1117772...333.44.5555.6666.....8888..    2928
   * 
   * 
   *      0      0  ID      0  Capacity      2  
   *      1 100018  ID      9  Capacity      2  
   *      2      1  ID     -1  Capacity      1  
   *      3      2  ID      1  Capacity      3  
   *      4 100014  ID      7  Capacity      3  
   *      5      3  ID     -1  Capacity      0  
   *      6      4  ID      2  Capacity      1  
   *      7      5  ID     -1  Capacity      3  
   *      8      6  ID      3  Capacity      3  
   *      9      7  ID     -1  Capacity      1  
   *     10      8  ID      4  Capacity      2  
   *     11      9  ID     -1  Capacity      1  
   *     12     10  ID      5  Capacity      4  
   *     13     11  ID     -1  Capacity      1  
   *     14     12  ID      6  Capacity      4  
   *     15     13  ID     -1  Capacity      0  Block + 1 from 16
   *     16     14  ID     -1  Capacity      5  Alt 7
   *     17     15  ID     -1  Capacity      0  
   *     18     16  ID      8  Capacity      4  
   *     19     17  ID     -1  Capacity      0  Block - 1 from 19
   *     20     18  ID     -1  Capacity      2  Alt 9
   * 
   * 
   * ----------------------------------------------------------------------
   * 
   * File ID 6  index_fat_alloc_block_from 14  index_fat_free_block -1
   * 
   * 0099.1117772...333.44.5555.6666.....8888..    2928
   * 
   * 
   *      0      0  ID      0  Capacity      2  
   *      1 100018  ID      9  Capacity      2  
   *      2      1  ID     -1  Capacity      1  
   *      3      2  ID      1  Capacity      3  
   *      4 100014  ID      7  Capacity      3  
   *      5      3  ID     -1  Capacity      0  
   *      6      4  ID      2  Capacity      1  
   *      7      5  ID     -1  Capacity      3  
   *      8      6  ID      3  Capacity      3  
   *      9      7  ID     -1  Capacity      1  
   *     10      8  ID      4  Capacity      2  
   *     11      9  ID     -1  Capacity      1  
   *     12     10  ID      5  Capacity      4  
   *     13     11  ID     -1  Capacity      1  
   *     14     12  ID      6  Capacity      4  
   *     15     13  ID     -1  Capacity      0  Block + 1 from 16
   *     16     14  ID     -1  Capacity      5  Alt 7
   *     17     15  ID     -1  Capacity      0  
   *     18     16  ID      8  Capacity      4  
   *     19     17  ID     -1  Capacity      0  Block - 1 from 19
   *     20     18  ID     -1  Capacity      2  Alt 9
   * 
   * 
   * ----------------------------------------------------------------------
   * 
   * File ID 5  index_fat_alloc_block_from 12  index_fat_free_block -1
   * 
   * 0099.1117772...333.44.5555.6666.....8888..    2928
   * 
   * 
   *      0      0  ID      0  Capacity      2  
   *      1 100018  ID      9  Capacity      2  
   *      2      1  ID     -1  Capacity      1  
   *      3      2  ID      1  Capacity      3  
   *      4 100014  ID      7  Capacity      3  
   *      5      3  ID     -1  Capacity      0  
   *      6      4  ID      2  Capacity      1  
   *      7      5  ID     -1  Capacity      3  
   *      8      6  ID      3  Capacity      3  
   *      9      7  ID     -1  Capacity      1  
   *     10      8  ID      4  Capacity      2  
   *     11      9  ID     -1  Capacity      1  
   *     12     10  ID      5  Capacity      4  
   *     13     11  ID     -1  Capacity      1  
   *     14     12  ID      6  Capacity      4  
   *     15     13  ID     -1  Capacity      0  Block + 1 from 16
   *     16     14  ID     -1  Capacity      5  Alt 7
   *     17     15  ID     -1  Capacity      0  
   *     18     16  ID      8  Capacity      4  
   *     19     17  ID     -1  Capacity      0  Block - 1 from 19
   *     20     18  ID     -1  Capacity      2  Alt 9
   * 
   * 
   * ----------------------------------------------------------------------
   * 
   * File ID 4  index_fat_alloc_block_from 11  index_fat_free_block 7
   * 
   * 0099.111777244.333....5555.6666.....8888..    2872
   * 
   * 
   *      0      0  ID      0  Capacity      2  
   *      1 100018  ID      9  Capacity      2  
   *      2      1  ID     -1  Capacity      1  
   *      3      2  ID      1  Capacity      3  
   *      4 100014  ID      7  Capacity      3  
   *      5      3  ID     -1  Capacity      0  
   *      6      4  ID      2  Capacity      1  
   *      7 100008  ID      4  Capacity      2  
   *      8      5  ID     -1  Capacity      1  
   *      9      6  ID      3  Capacity      3  
   *     10      7  ID     -1  Capacity      0  Block + 1 from 11
   *     11      8  ID     -1  Capacity      4  Alt 4
   *     12      9  ID     -1  Capacity      0  
   *     13     10  ID      5  Capacity      4  
   *     14     11  ID     -1  Capacity      1  
   *     15     12  ID      6  Capacity      4  
   *     16     13  ID     -1  Capacity      0  Block + 1 from 16
   *     17     14  ID     -1  Capacity      5  Alt 7
   *     18     15  ID     -1  Capacity      0  
   *     19     16  ID      8  Capacity      4  
   *     20     17  ID     -1  Capacity      0  Block - 1 from 19
   *     21     18  ID     -1  Capacity      2  Alt 9
   * 
   * 
   * ----------------------------------------------------------------------
   * 
   * File ID 3  index_fat_alloc_block_from 9  index_fat_free_block -1
   * 
   * 0099.111777244.333....5555.6666.....8888..    2872
   * 
   * 
   *      0      0  ID      0  Capacity      2  
   *      1 100018  ID      9  Capacity      2  
   *      2      1  ID     -1  Capacity      1  
   *      3      2  ID      1  Capacity      3  
   *      4 100014  ID      7  Capacity      3  
   *      5      3  ID     -1  Capacity      0  
   *      6      4  ID      2  Capacity      1  
   *      7 100008  ID      4  Capacity      2  
   *      8      5  ID     -1  Capacity      1  
   *      9      6  ID      3  Capacity      3  
   *     10      7  ID     -1  Capacity      0  Block + 1 from 11
   *     11      8  ID     -1  Capacity      4  Alt 4
   *     12      9  ID     -1  Capacity      0  
   *     13     10  ID      5  Capacity      4  
   *     14     11  ID     -1  Capacity      1  
   *     15     12  ID      6  Capacity      4  
   *     16     13  ID     -1  Capacity      0  Block + 1 from 16
   *     17     14  ID     -1  Capacity      5  Alt 7
   *     18     15  ID     -1  Capacity      0  
   *     19     16  ID      8  Capacity      4  
   *     20     17  ID     -1  Capacity      0  Block - 1 from 19
   *     21     18  ID     -1  Capacity      2  Alt 9
   * 
   * 
   * ----------------------------------------------------------------------
   * 
   * File ID 2  index_fat_alloc_block_from 7  index_fat_free_block 2
   * 
   * 00992111777.44.333....5555.6666.....8888..    2858
   * 
   * 
   *      0      0  ID      0  Capacity      2  
   *      1 100018  ID      9  Capacity      2  
   *      2 100004  ID      2  Capacity      1  
   *      3      1  ID     -1  Capacity      0  
   *      4      2  ID      1  Capacity      3  
   *      5 100014  ID      7  Capacity      3  
   *      6      3  ID     -1  Capacity      0  Block - 1 from 7
   *      7      4  ID     -1  Capacity      1  Alt 2
   *      8 100008  ID      4  Capacity      2  
   *      9      5  ID     -1  Capacity      1  
   *     10      6  ID      3  Capacity      3  
   *     11      7  ID     -1  Capacity      0  Block + 1 from 11
   *     12      8  ID     -1  Capacity      4  Alt 4
   *     13      9  ID     -1  Capacity      0  
   *     14     10  ID      5  Capacity      4  
   *     15     11  ID     -1  Capacity      1  
   *     16     12  ID      6  Capacity      4  
   *     17     13  ID     -1  Capacity      0  Block + 1 from 16
   *     18     14  ID     -1  Capacity      5  Alt 7
   *     19     15  ID     -1  Capacity      0  
   *     20     16  ID      8  Capacity      4  
   *     21     17  ID     -1  Capacity      0  Block - 1 from 19
   *     22     18  ID     -1  Capacity      2  Alt 9
   * 
   * 
   * ----------------------------------------------------------------------
   * 
   * File ID 1  index_fat_alloc_block_from 4  index_fat_free_block -1
   * 
   * 00992111777.44.333....5555.6666.....8888..    2858
   * 
   * 
   *      0      0  ID      0  Capacity      2  
   *      1 100018  ID      9  Capacity      2  
   *      2 100004  ID      2  Capacity      1  
   *      3      1  ID     -1  Capacity      0  
   *      4      2  ID      1  Capacity      3  
   *      5 100014  ID      7  Capacity      3  
   *      6      3  ID     -1  Capacity      0  Block - 1 from 7
   *      7      4  ID     -1  Capacity      1  Alt 2
   *      8 100008  ID      4  Capacity      2  
   *      9      5  ID     -1  Capacity      1  
   *     10      6  ID      3  Capacity      3  
   *     11      7  ID     -1  Capacity      0  Block + 1 from 11
   *     12      8  ID     -1  Capacity      4  Alt 4
   *     13      9  ID     -1  Capacity      0  
   *     14     10  ID      5  Capacity      4  
   *     15     11  ID     -1  Capacity      1  
   *     16     12  ID      6  Capacity      4  
   *     17     13  ID     -1  Capacity      0  Block + 1 from 16
   *     18     14  ID     -1  Capacity      5  Alt 7
   *     19     15  ID     -1  Capacity      0  
   *     20     16  ID      8  Capacity      4  
   *     21     17  ID     -1  Capacity      0  Block - 1 from 19
   *     22     18  ID     -1  Capacity      2  Alt 9
   * 
   * 
   * ----------------------------------------------------------------------
   * 
   * Cleared all 0 capacity blocks
   * 
   * 00992111777.44.333....5555.6666.....8888..    2858
   * 
   * 
   *      0      0  ID      0  Capacity      2  
   *      1 100018  ID      9  Capacity      2  
   *      2 100004  ID      2  Capacity      1  
   *      3      2  ID      1  Capacity      3  
   *      4 100014  ID      7  Capacity      3  
   *      5      4  ID     -1  Capacity      1  Alt 2
   *      6 100008  ID      4  Capacity      2  
   *      7      5  ID     -1  Capacity      1  
   *      8      6  ID      3  Capacity      3  
   *      9      8  ID     -1  Capacity      4  Alt 4
   *     10     10  ID      5  Capacity      4  
   *     11     11  ID     -1  Capacity      1  
   *     12     12  ID      6  Capacity      4  
   *     13     14  ID     -1  Capacity      5  Alt 7
   *     14     16  ID      8  Capacity      4  
   *     15     18  ID     -1  Capacity      2  Alt 9
   * 
   * 
   * pInput.length() = 19
   * max file_id     = 9
   * 
   * Result Part 2   = 2858
   * 
   * 
   * 0099.111...2...333.44.5555.6666.777.8888..    3432
   * 0099.111...2...333.44.5555.6666.777.8888..    3432
   * 0099.1117772...333.44.5555.6666.....8888..    2928
   * 0099.1117772...333.44.5555.6666.....8888..    2928
   * 0099.1117772...333.44.5555.6666.....8888..    2928
   * 0099.111777244.333....5555.6666.....8888..    2872
   * 0099.111777244.333....5555.6666.....8888..    2872
   * 00992111777.44.333....5555.6666.....8888..    2858
   * 00992111777.44.333....5555.6666.....8888..    2858
   * Time 74
   * 
   * pInput.length() = 19999
   * max file_id     = 9999
   * 
   * Result Part 2   = 6381624803796
   * 
   * Time 276 MS
   * 
   *
   * Just for fun, the timed version of a python script:
   * 
   * ea234@MsiZ370:/mnt/hd4tbb/daten/ang$ time python3 xxx.py < aoc.txt
   * 6359213660505
   * 6381624803796
   * 
   * real    0m25,014s
   * user    0m25,010s
   * sys     0m0,004s
   * 
   */

  private static long    FREE_SPACE_FILE_ID     = -1;

  private static String  CHAR_FREE_SPACE        = ".";

  private static boolean TOGGLE_FREE_SPACE      = true;

  private static boolean TOGGLE_BLOCK_ALLOCATED = false;

  public static void main( String[] args )
  {
    String test_input_1 = "2333133121414131402";

    calcPart02( test_input_1, true );

    //calcPart02( getListProd(), false );
  }

  private static void calcPart02( String pInput, boolean pKnzDebug )
  {
    long start_time = System.currentTimeMillis();

    /*
     * *******************************************************************************************************
     * Generating Disk Map
     * *******************************************************************************************************
     */

    String debug_str_disk_maps = "";

    /*
     * First file-id is 0
     */
    long file_id_counter = -1;

    long file_id_for_alloc_block = -1;

    List< Day09AllocationBlock > fat = new ArrayList< Day09AllocationBlock >();

    /*
     * The toggle starts with an allocated block
     */
    boolean knz_toggle = TOGGLE_BLOCK_ALLOCATED;

    int index_fat = 0;

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
          file_id_for_alloc_block = FREE_SPACE_FILE_ID;
        }
        else
        {
          /*
           * If the toggle var is file, than
           * ... the file_id is increased by one
           * ... the cur_file_id is set to that id
           */
          file_id_counter++;

          file_id_for_alloc_block = file_id_counter;
        }

        fat.add( new Day09AllocationBlock( index_fat, file_id_for_alloc_block, nr_repeat ) );

        /*
         * Increment the index for the file allocation table.
         */
        index_fat++;

        /*
         * Switch the toggle value
         */
        knz_toggle = !knz_toggle;
      }
    }

    if ( pKnzDebug )
    {
      wl( "" );
      wl( pInput );
      wl( "" );

      debugFileAllocationTable( fat );

      wl( "" );
    }

    /*
     * *******************************************************************************************************
     * Doing Optimization
     * *******************************************************************************************************
     */

    int index_fat_free_block = 1;
    int index_fat_alloc_block_from = 5;

    while ( file_id_for_alloc_block > 0 )
    {
      /*
       * Get the list index for the current file-id
       */
      index_fat_alloc_block_from = getIndexAllocBlockFileID( fat, file_id_for_alloc_block );

      /*
       * Check if the index was found
       */
      if ( index_fat_alloc_block_from > 0 )
      {
        /*
         * Get the instance of the allocation-block 
         */
        Day09AllocationBlock alloc_block = fat.get( index_fat_alloc_block_from );

        /*
         * Get the next free space from the fat for 
         * at least the length of the allocation block
         */
        index_fat_free_block = getIndexFreeBlock( fat, index_fat_alloc_block_from, alloc_block.getCapacity() );

        /*
         * "Move it to the left most free space"
         * 
         * Dont move the file, if the found free-space-index is 
         * greater then the index of the file.
         * 
         * Dont move the 00 to a free space
         *  
         * 0099.111777244.333....5555.6666.....8888..
         */
        if ( index_fat_free_block > index_fat_alloc_block_from )
        {
          index_fat_free_block = -1;
        }

        Day09AllocationBlock free_block = null;

        /*
         * Check if a fitting free space block was found
         */
        if ( index_fat_free_block != -1 )
        {
          /*
           * If a index was found, get the instance of the allocation-block
           */
          free_block = fat.get( index_fat_free_block );
        }

        if ( free_block != null )
        {
          long capacity_free = free_block.getCapacity();

          long capacity_free_after_move = capacity_free - alloc_block.getCapacity();

          /*
           * Create a new allocation block for the file
           * with a capacity of that file.
           * 
           * Copy the file-id from the from block to the new.
           */
          Day09AllocationBlock y_new = new Day09AllocationBlock( 100_000 + alloc_block.getDebugIndex(), alloc_block.getFileId(), alloc_block.getCapacity() );

          /*
           * Add the new allocation-block to the list, at 
           * the index of the free block.
           * 
           * This will shift all remaining instances one position 
           * to the right (+1).
           */
          fat.add( index_fat_free_block, y_new );

          /*
           * The old index has shifted one position to the right
           */
          index_fat_alloc_block_from++;

          /*
           * Set the new free capacity at the free block.
           */
          free_block.setCapacity( capacity_free_after_move );

          /*
           * Set the old allocation block to the free-space file id.
           */
          alloc_block.setFileID( FREE_SPACE_FILE_ID );

          /*
           * Set some debug string
           */
          alloc_block.setDebugString( "OLD " + file_id_for_alloc_block );

          /*
           * Merge free spaces together
           */

          Day09AllocationBlock block_right = null;

          Day09AllocationBlock block_left = null;

          if ( index_fat_alloc_block_from > 0 )
          {
            block_left = fat.get( index_fat_alloc_block_from - 1 );

            if ( block_left.getFileId() == FREE_SPACE_FILE_ID )
            {
              //wl( block_left.toString() );

              alloc_block.addCapacity( block_left.getCapacity() );

              block_left.setCapacity( 0 );

              block_left.setDebugString( "Block - 1 from " + index_fat_alloc_block_from );
            }
          }

          if ( ( index_fat_alloc_block_from + 1 ) < fat.size() )
          {
            block_right = fat.get( index_fat_alloc_block_from + 1 );

            if ( block_right.getFileId() == FREE_SPACE_FILE_ID )
            {
              //wl( block_right.toString() );

              alloc_block.addCapacity( block_right.getCapacity() );

              block_right.setCapacity( 0 );

              block_left.setDebugString( "Block + 1 from " + index_fat_alloc_block_from );
            }
          }

          /*
           * Something that refused to work:
           */
          //          
          //if ( block_left != null ) 
          //{
          //  //fat.remove( block_left );
          //  fat.remove( index_fat_alloc_block_from - 1  );
          //}
          //
          //if ( block_right != null ) 
          //{
          //  fat.remove( block_right );
          //}
          //
        }
      }

      if ( pKnzDebug )
      {
        wl( "" );
        wl( "----------------------------------------------------------------------" );
        wl( "" );
        wl( "File ID " + file_id_for_alloc_block + "  index_fat_alloc_block_from " + index_fat_alloc_block_from + "  index_fat_free_block " + index_fat_free_block );
        wl( "" );

        String cur_disk_map = getDebugString( fat, true );

        debug_str_disk_maps += "\n" + left( cur_disk_map, 700 );

        wl( cur_disk_map );
        wl( "" );

        debugFileAllocationTable( fat );
      }

      file_id_for_alloc_block--;
    }

    clearFreeBlock0Capacity( fat );

    if ( pKnzDebug )
    {
      wl( "" );
      wl( "----------------------------------------------------------------------" );
      wl( "" );
      wl( "Cleared all 0 capacity blocks" );
      wl( "" );
      wl( getDebugString( fat, true ) );
      wl( "" );

      debugFileAllocationTable( fat );
    }

    /*
     * *******************************************************************************************************
     * Calculating Result Part 2
     * *******************************************************************************************************
     */

    long checksum_filesystem_sum = getFileSystemCheckSum( fat );

    wl( "" );
    wl( "pInput.length() = " + pInput.length() );
    wl( "max file_id     = " + file_id_counter );
    wl( "" );
    wl( "Result Part 2   = " + checksum_filesystem_sum );
    wl( "" );

    if ( pKnzDebug )
    {
      wl( debug_str_disk_maps );
    }

    long end_time = System.currentTimeMillis();

    wl( "Time " + ( end_time - start_time ) );
  }

  private static void debugFileAllocationTable( List< Day09AllocationBlock > pFileAllocationTable )
  {
    wl( "" );

    long index_x = 0;

    for ( Day09AllocationBlock alloc_block : pFileAllocationTable )
    {
      wl( FkStringFeld.getFeldRechtsMin( "" + index_x, 6 ) + " " + alloc_block.toString() );

      index_x++;
    }

    wl( "" );
  }

  private static int clearFreeBlock0Capacity( List< Day09AllocationBlock > pFileAllocationTable )
  {
    int index_cur = 0;

    while ( index_cur < pFileAllocationTable.size() )
    {
      if ( pFileAllocationTable.get( index_cur ).isFreeBlockCapacity0() )
      {
        pFileAllocationTable.remove( index_cur );
      }
      else
      {
        index_cur++;
      }
    }

    return -1;
  }

  private static int getIndexFreeBlock( List< Day09AllocationBlock > pFileAllocationTable, int pSearchIndexEnd, long pCapacity )
  {
    for ( int index_cur = 0; index_cur < pSearchIndexEnd; index_cur++ )
    {
      if ( pFileAllocationTable.get( index_cur ).getFileId() == FREE_SPACE_FILE_ID )
      {
        if ( pFileAllocationTable.get( index_cur ).getCapacity() >= pCapacity )
        {
          return index_cur;
        }
      }
    }

    return -1;
  }

  private static int getIndexAllocBlockFileID( List< Day09AllocationBlock > pFileAllocationTable, long pFileID )
  {
    for ( int index_cur = pFileAllocationTable.size() - 1; index_cur > 0; index_cur-- )
    {
      if ( pFileAllocationTable.get( index_cur ).getFileId() == pFileID )
      {
        return index_cur;
      }
    }

    return -1;
  }

  private static String getDebugString( List< Day09AllocationBlock > pFileAllocationTable, boolean pKnzAppendChecksum )
  {
    String debug_string = "";

    long checksum_filesystem_sum = 0;

    long list_index = 0;

    for ( Day09AllocationBlock alloc_block : pFileAllocationTable )
    {
      if ( alloc_block.isFreeBlockCapacity0() == false )
      {
        long cur_file_id = alloc_block.getFileId();

        String char_to_append = "" + alloc_block.getFileId();

        if ( alloc_block.getFileId() == FREE_SPACE_FILE_ID )
        {
          char_to_append = CHAR_FREE_SPACE;

          cur_file_id = 0;
        }

        for ( int x_times = 0; x_times < alloc_block.getCapacity(); x_times++ )
        {
          debug_string += char_to_append;

          checksum_filesystem_sum += cur_file_id * list_index;

          list_index++;
        }
      }
    }

    if ( pKnzAppendChecksum )
    {
      debug_string += "    " + checksum_filesystem_sum;
    }

    return debug_string;
  }

  private static long getFileSystemCheckSum( List< Day09AllocationBlock > pFileAllocationTable )
  {
    long checksum_filesystem_sum = 0;

    long list_index = 0;

    for ( Day09AllocationBlock alloc_block : pFileAllocationTable )
    {
      if ( alloc_block.isFreeBlockCapacity0() == false )
      {
        long cur_file_id = alloc_block.getFileId();

        if ( alloc_block.getFileId() == FREE_SPACE_FILE_ID )
        {
          cur_file_id = 0;
        }

        /*
         * Save the file-id at the index 'len_list' to the hashmap.
         * Do that x-times.
         */
        for ( int x_times = 0; x_times < alloc_block.getCapacity(); x_times++ )
        {
          checksum_filesystem_sum += cur_file_id * list_index;

          list_index++;
        }
      }
    }

    return checksum_filesystem_sum;
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

  /**
   * <pre>
   * Schneidet Anzahl-Stellen von dem uebergebenen String ab und gibt diesen zurueck.
   * 
   * Ist der Parameter "pString" gleich "null", wird ein Leerstring zurueckgegeben.
   * 
   * Uebersteigt die Anazhl der abzuschneidenden Stellen die Stringlaenge, wird der
   * Quellstring insgesamt zurueckgegeben.
   * 
   * Ist die Anzahl der abzuschneidenden Stellen negativ oder 0, wird ein Leerstring zurueckgegeben.
   * 
   * FkString.left( "ABC.DEF.GHI.JKL",  3 ) = "ABC"
   * FkString.left( "ABC.DEF.GHI.JKL",  4 ) = "ABC."
   * FkString.left( "ABC.DEF.GHI.JKL", 20 ) = "ABC.DEF.GHI.JKL"
   * 
   * FkString.left( "ABC.DEF.GHI.JKL", -3 ) = "" = negative Anzahl von Stellen = Leerstring
   * FkString.left(                "", 10 ) = "" = pString ist Leerstring      = Leerstring
   * FkString.left(              null, 10 ) = "" = pString ist null            = Leerstring
   * </pre>
   * 
   * @param pString der Quellstring
   * @param pAnzahlStellen die Anzahl der von links abzuschneidenden Stellen
   * @return den sich ergebenden String, Leerstring wenn die Anzahl der Stellen negativ ist oder pString null ist
   */
  private static String left( String pString, int pAnzahlStellen )
  {
    /*
     * Pruefung: "pString" gleich "null" ?
     * 
     * Ist der Parameter "pString" gleich "null" gibt es keinen String. 
     * Der Aufrufder bekommt einen Leerstring zurueck
     */
    if ( pString == null )
    {
      return "";
    }

    /*
     * Pruefung: Anzahl der Stellen negativ?
     * Ist die Anzahl der abzuschneidenden Stellen negativ, bleibt 
     * kein Teil von pString uebrig. Dieser Fall wird analog einer 
     * Uebergabe von 0 Zeichen abschneiden behandelt.  
     * 
     * Der Aufrufer bekommt einen Leerstring zurueck.
     */
    if ( pAnzahlStellen <= 0 )
    {
      return "";
    }

    /*
     * Pruefung: Teilstring zurueckgeben?
     * Ist die Anzahl der Stellen kleiner als die Laenge von "pString", 
     * wird ein Teilstring zurueckgegeben.
     *
     * Der Aufrufer bekommt den Teilstring ab der Position 0 bis zur
     * Anzahl der abzuschneidenden Stellen zurueck. 
     */
    if ( pAnzahlStellen < pString.length() )
    {
      return pString.substring( 0, pAnzahlStellen );
    }

    /*
     * Ueberschreitet die Anzahl der abzuschneidenden Stellen die 
     * Laenge des Eingabestrings, muss kein Zeichen vom Eingabestring
     * abgeschnitten werden. 
     * 
     * Der Aufrufer bekommt die Eingabe zurueck.
     */
    return pString;
  }

  private static String calcPart2DiskLayoutTest( String pInput )
  {
    String free_space = "";
    String block_alloc = "";

    boolean knz_toggle = TOGGLE_BLOCK_ALLOCATED;

    for ( char cur_char : pInput.toCharArray() )
    {
      wl( "" + cur_char );

      int nr_repeat = ( ( (int) cur_char ) - 48 );

      if ( knz_toggle == TOGGLE_FREE_SPACE )
      {
        free_space += cur_char;
      }
      else
      {
        block_alloc += cur_char;
      }

      knz_toggle = !knz_toggle;
    }

    StringBuilder result_disk_layout = new StringBuilder();

    knz_toggle = TOGGLE_BLOCK_ALLOCATED;

    for ( char cur_char : pInput.toCharArray() )
    {
      wl( "" + cur_char );

      int nr_repeat = ( ( (int) cur_char ) - 48 );

      char repeat_char = cur_char;

      if ( knz_toggle == TOGGLE_FREE_SPACE )
      {
        repeat_char = '.';
      }

      for ( int idx = 0; idx < nr_repeat; idx++ )
      {
        result_disk_layout.append( repeat_char );
      }

      knz_toggle = !knz_toggle;
    }

    String disk_l = result_disk_layout.toString();

    wl( "" );
    wl( "-----------------------------------------" );
    wl( "" );
    wl( "pInput       " + pInput );
    wl( "disk_l       " + disk_l );
    wl( "" );
    wl( "free_space   " + free_space );
    wl( "block_alloc  " + block_alloc );

    String input = block_alloc;
    String reversed = new StringBuilder( input ).reverse().toString();

    wl( "block_allocr " + reversed );

    return "";
  }
}
