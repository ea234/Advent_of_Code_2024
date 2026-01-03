package de.ea234.aoc2024.day15;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Day15WarehouseWoesP02 extends Day15WarehouseWoes
{

  /*
   * --- Day 15: Warehouse Woes ---
   * https://adventofcode.com/2024/day/15 
   * 
   * https://www.youtube.com/watch?v=jnAj6cB2NvI
   * 
   * https://www.reddit.com/r/adventofcode/comments/1hele8m/2024_day_15_solutions/
   * 
   * 

Example works

####################
##[].......[].[][]##
##[]...........[].##
##[]........[][][]##
##[]......[]....[]##
##..##......[]....##
##..[]............##
##..@......[].[][]##
##......[][]..[]..##
####################

GPS 9021




   */

  private static final int    ROW_UP                    = -1;

  private static final int    ROW_DOWN                  = 1;

  private static final int    COL_LEFT                  = -1;

  private static final int    COL_RIGHT                 = 1;

  private static final char   CHAR_WALL                 = '#';

  private static final char   CHAR_EMPTY_FLOOR          = '.';

  private static final char   CHAR_BOX                  = 'O';

  private static final char   CHAR_BOX2_S               = '[';

  private static final char   CHAR_BOX2_E               = ']';

  private static final char   CHAR_ROBOT                = '@';

  private static final char   CHAR_CMD_UP               = '^';

  private static final char   CHAR_CMD_DOWN             = 'v';

  private static final char   CHAR_CMD_LEFT             = '<';

  private static final char   CHAR_CMD_RIGHT            = '>';

  private static final String MAP_COORDINATES_NOT_FOUND = "_";

  private static final int    NR_OF_DIGITS_LINE_INFO    = 4;

  private static Properties   m_prop_grid_map           = null;

  public static void main( String[] args )
  {
    String test_content_temp_map = "########,#..O.O.#,##@.O..#,#...O..#,#.#.O..#,#...O..#,#......#,########";

    String test_content_temp_cmds = "<^^>>>vv<v>>v<<";

    String test_content_2_map = "##########,#..O..O.O#,#......O.#,#.OO..O.O#,#..O@..O.#,#O#..O...#,#O..O..O.#,#.OO.O.OO#,#....O...#,##########";

    String test_content_2_cmds = "";

    test_content_2_cmds += "<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^";
    test_content_2_cmds += "vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v";
    test_content_2_cmds += "><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<";
    test_content_2_cmds += "<<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^";
    test_content_2_cmds += "^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><";
    test_content_2_cmds += "^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^";
    test_content_2_cmds += ">^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^";
    test_content_2_cmds += "<><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>";
    test_content_2_cmds += "^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>";
    test_content_2_cmds += "v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^";

    //test_content_1 = "######,#....#,#....#,#.@O.#,#....#,######";
    //test_content_1 = "########,#......#,#..OO..#,#......#,#.@O...#,#......#,########";

    test_content_temp_map = "#########,#.......#,#..OOO..#,#.......#,#.@O....#,#.......#,#########";
    test_content_temp_cmds = ">>>^^^";

    test_content_temp_map = "#########,#.......#,#.......#,#.......#,#.O.OO..#,#..OOO..#,#.O.OO..#,#.......#,#.@O....#,#.......#,#########";
    test_content_temp_cmds = ">>>^^^^^^^^^^^^vv>>>>>>>>>>>>>>>>>>>><";

    test_content_temp_map = "#########,#.......#,#.......#,#.......#,#.O.OO..#,#..OOO..#,#.O.OO..#,#.......#,#..O.@..#,#.......#,#########";
    test_content_temp_cmds = "<<<<<<<^^^^^^^vv>>vv>>>^^^";

    //test_content_1 = "########,#...#.E#,#.#.#.##,#S#...##,########";

    /*
     * Streight up
     */
    test_content_temp_map = "#########,#.......#,#.......#,#...OO..#,#...OO..#,#...@O..#,#.......#,#########";
    test_content_temp_cmds = "^^^";

    test_content_temp_map = "#########,#.......#,#.......#,#..O.O..#,#...OO..#,#...@O..#,#.......#,#########";
    test_content_temp_cmds = "^^^";

    test_content_temp_map = "#########,#.......#,#.......#,#...OO..#,#...OO..#,#...O@..#,#.......#,#########";
    test_content_temp_cmds = "<v<^^^";

    /*
     * Box right
     */
    test_content_temp_map = "#########,#.......#,#.......#,#.......#,#...OO..#,#...O@..#,#.......#,#########";
    test_content_temp_cmds = "<v<^^^^^^^^^";

    /*
     * Box right and left
     */
    test_content_temp_map = "#########,#.......#,#.......#,#.......#,#..OOO..#,#...O@..#,#.......#,#########";
    test_content_temp_cmds = "<v<^^^^^^^^^";

    String test_content_1_map = "########,#..O.O.#,##@.O..#,#...O..#,#.#.O..#,#...O..#,#......#,########";

    String test_content_1_cmds = "<^^>>>vv<v>>v<<";

    List< String > test_content_list_1 = Arrays.stream( ( test_content_1_map + ",," + test_content_1_cmds ).split( "," ) ).map( String::trim ).collect( Collectors.toList() );
    List< String > test_content_list_2 = Arrays.stream( ( test_content_2_map + ",," + test_content_2_cmds ).split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    List< String > test_content_list_temp = Arrays.stream( ( test_content_temp_map + ",," + test_content_temp_cmds ).split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    calculatePart1( test_content_list_1, 20, true );
    calculatePart1( test_content_list_2, 20, true );

    //calculatePart1( getListProd(), 100, false );
  }

  private static void calculatePart1( List< String > pListInput, int pCheatMinLen, boolean pKnzDebug )
  {
    /*
     * *******************************************************************************************************
     * Initializing Variables
     * *******************************************************************************************************
     */

    String cmd_string = ""; // ">^^<<<<<<<<<<<<>>>>>>>>>vvvvvvvvvvv<<<<<<<<<<<<";

    m_prop_grid_map = new Properties();

    long current_row = 0;
    long current_col = 0;

    long robot_row = 0;
    long robot_col = 0;

    long grid_height = pListInput.size() * 2;
    long grid_width = pListInput.get( 0 ).length() * 2;

    /*
     * *******************************************************************************************************
     * Initializing the grid
     * *******************************************************************************************************
     */

    boolean knz_load_map = true;

    for ( String input_str : pListInput )
    {
      /*
       * Blank line seperates the map from the commands
       */
      if ( input_str.trim().isBlank() )
      {
        knz_load_map = false;

        grid_height = current_row;
      }

      if ( knz_load_map )
      {
        long current_new_grid_col = 0;

        for ( current_col = 0; current_col < input_str.length(); current_col++ )
        {
          char current_char = input_str.charAt( (int) current_col );

          char char_1 = CHAR_WALL;
          char char_2 = CHAR_WALL;

          if ( current_char == CHAR_EMPTY_FLOOR )
          {
            char_1 = CHAR_EMPTY_FLOOR;
            char_2 = CHAR_EMPTY_FLOOR;
          }
          else if ( current_char == CHAR_BOX )
          {
            char_1 = CHAR_BOX2_S;
            char_2 = CHAR_BOX2_E;
          }
          else if ( current_char == CHAR_ROBOT )
          {
            char_1 = CHAR_ROBOT;
            char_2 = CHAR_EMPTY_FLOOR;

            robot_row = current_row;
            robot_col = current_new_grid_col;
          }

          m_prop_grid_map.setProperty( "R" + current_row + "C" + current_new_grid_col, "" + char_1 );
          m_prop_grid_map.setProperty( "OX" + current_row + "C" + current_new_grid_col, "" + char_1 );

          current_new_grid_col++;

          m_prop_grid_map.setProperty( "R" + current_row + "C" + current_new_grid_col, "" + char_2 );
          m_prop_grid_map.setProperty( "OX" + current_row + "C" + current_new_grid_col, "" + char_2 );

          current_new_grid_col++;
        }
      }
      else
      {
        cmd_string += input_str;
      }

      current_row++;
    }

    if ( pKnzDebug )
    {
      wl( "" );
      wl( "Robot start at X" + robot_row + "C" + robot_col );
      wl( "" );
      wl( getDebugMap( m_prop_grid_map, grid_height, grid_width ) );
    }

    for ( int index_command = 0; index_command < cmd_string.length(); index_command++ )
    {
      char cmd_char = cmd_string.charAt( index_command );

      if ( pKnzDebug )
      {
        wl( "" );
        wl( "--------------------------------------------------------------" );
        wl( "cmd " + cmd_char + " nr " + index_command );
      }

      if ( cmd_char == CHAR_CMD_UP )
      {
        robot_row = moveVertical( robot_row, robot_col, ROW_UP, pKnzDebug );
      }
      else if ( cmd_char == CHAR_CMD_DOWN )
      {
        robot_row = moveVertical( robot_row, robot_col, ROW_DOWN, pKnzDebug );
      }
      else if ( cmd_char == CHAR_CMD_LEFT )
      {
        robot_col = moveHorizontal( robot_row, robot_col, COL_LEFT, pKnzDebug );
      }
      else if ( cmd_char == CHAR_CMD_RIGHT )
      {
        robot_col = moveHorizontal( robot_row, robot_col, COL_RIGHT, pKnzDebug );
      }

      if ( pKnzDebug )
      {
        wl( "" );
        wl( getDebugMap( m_prop_grid_map, grid_height, grid_width ) );
      }
    }

    /*
     * *******************************************************************************************************
     * Debug Grid
     * *******************************************************************************************************
     */
    wl( "" );
    wl( getDebugMap( m_prop_grid_map, grid_height, grid_width ) );
  }

  private static long moveVertical( long robot_row, long robot_col, long delta_row, boolean pKnzDebug )
  {
    long do_move = robot_row;

    /*
     * Get the char above the robot
     */
    char char_col1 = m_prop_grid_map.getProperty( "R" + ( robot_row + delta_row ) + "C" + robot_col, MAP_COORDINATES_NOT_FOUND ).charAt( 0 );

    if ( char_col1 == CHAR_WALL )
    {
      /*
       * Move not possible, we hit a wall
       * Robot already against wall.
       * ########
       * .@....
       * ########
       */
      do_move = 0;
    }
    else if ( char_col1 == CHAR_EMPTY_FLOOR )
    {
      /*
       * Move possible
       * 
       * Next Robot position is free floor
       * ########
       * ..[].[].
       * ....@...
       * 
       */
      do_move = 1;

      /*
       * Set the robot to the now coordinates
       */
      m_prop_grid_map.setProperty( "R" + ( robot_row + delta_row ) + "C" + robot_col, "" + CHAR_ROBOT );

      /*
       * Set the previous robot coordinates to free floor.
       */
      m_prop_grid_map.setProperty( "R" + robot_row + "C" + robot_col, "" + CHAR_EMPTY_FLOOR );

      /*
       * Set the return value to the new robot row
       */
      robot_row += delta_row;
    }
    else
    {
      long fake_col1 = robot_col;
      long fake_col2 = robot_col + 1;

      if ( char_col1 == CHAR_BOX2_E )
      {
        /*
         * Is the char above the robot the box-end char,
         * then correct the position for the rec-search
         * by minus one. 
         * 
         * The rec-search, should have always find a box start char
         * at the given position.
         * 
         * ........
         * ...[].....
         * ..[][]..
         * ...@....
         * 
         * Ensure that the space over the robot is always 
         * the start of the box.
         */
        fake_col1--;
        fake_col2--;
      }

      List< String > list_info = new ArrayList< String >();

      do_move = checkVertical( list_info, true, ( robot_row + delta_row ), delta_row, fake_col1, fake_col2 );

      if ( do_move == 1 )
      {
        if ( delta_row < 0 )
        {
          list_info.sort( null );
        }
        else
        {
          list_info.sort( Comparator.reverseOrder() );
        }

        for ( String info_str : list_info )
        {
          wl( "List Info " + info_str );

          String[] parts = info_str.split( "," );

          m_prop_grid_map.setProperty( parts[ 2 ], parts[ 3 ] );
          m_prop_grid_map.setProperty( parts[ 1 ], "" + CHAR_EMPTY_FLOOR );
        }

        /*
         * Set the robot to the now coordinates
         */
        m_prop_grid_map.setProperty( "R" + ( robot_row + delta_row ) + "C" + robot_col, "" + CHAR_ROBOT );

        /*
         * Set the previous robot coordinates to free floor.
         */
        m_prop_grid_map.setProperty( "R" + robot_row + "C" + robot_col, "" + CHAR_EMPTY_FLOOR );

        /*
         * Set the return value to the new robot row
         */
        robot_row += delta_row;
      }
    }

    return robot_row;
  }

  private static long checkVertical( List< String > pListC, boolean knz_change_grid, long check_row, long delta_row, long check_col1, long check_col2 )
  {
    /*
     * Negativ check coordinates - no move - Error
     */
    if ( ( check_col1 < 0 ) || ( check_col1 < 0 ) || ( check_col2 < 0 ) )
    {
      wl( "error " );

      return 0;
    }

    /*
     * Get the chars from the check coordinates
     * 
     * char_col1  char_col2
     *     [          ]       = Box found = check direct above   
     * 
     *     ]          [       = Closing Box to the left    = Check Left  = check_col1 - 1 to get the Box-Start
     *                        = Starting Box to the right  = Check Right = check_col1 + 1 to get the Box-Start at position check_col1
     *                        
     *     ]          .       = Closing Box to the left    = Check Left  = check_col1 - 1 to get the Box-Start
     *                        
     *     .          [       = Starting Box to the right  = Check Right = check_col1 + 1 to get the Box-Start at position check_col1
     * 
     * 
     *     #          [       = char_col1 hit wall         = no move possible = return 0
     *     #          .       = char_col1 hit wall         = no move possible = return 0
     *     
     *     ]          #       = char_col2 hit wall         = no move possible = return 0
     *     .          #       = char_col2 hit wall         = no move possible = return 0
     *                        
     *     #          #       = both chars hit wall         = no move possible = return 0
     *                        
     *     .          .       = both chars are empty floor  = move possible    = return 1 = recursion end
     *     
     */
    char char_col1 = m_prop_grid_map.getProperty( "R" + check_row + "C" + check_col1, MAP_COORDINATES_NOT_FOUND ).charAt( 0 );
    char char_col2 = m_prop_grid_map.getProperty( "R" + check_row + "C" + check_col2, MAP_COORDINATES_NOT_FOUND ).charAt( 0 );

    if ( ( char_col1 == CHAR_WALL ) || ( char_col1 == CHAR_WALL ) )
    {
      /*
       * No move possible if one char hit a wall.
       * Even if the other char is "empty floor" or some Box-char.
       */
      return 0;
    }

    if ( ( char_col1 == CHAR_EMPTY_FLOOR ) && ( char_col2 == CHAR_EMPTY_FLOOR ) )
    {
      /*
       * char_col are both empty floor, we can move to this spot.
       * the position will not be remembered, because we move to this spot.
       */

      return 1;
    }

    long box_left_col1 = -1;
    long box_left_col2 = -1;

    long box_right_col1 = -1;
    long box_right_col2 = -1;

    if ( ( char_col1 == CHAR_BOX2_S ) && ( char_col2 == CHAR_BOX2_E ) )
    {
      box_left_col1 = check_col1;
      box_left_col2 = check_col2;
    }
    else
    {
      if ( char_col1 == CHAR_BOX2_E )
      {
        box_left_col1 = check_col1 - 1;
        box_left_col2 = check_col2 - 1;
      }

      if ( char_col2 == CHAR_BOX2_S )
      {
        box_right_col1 = check_col1 + 1;
        box_right_col2 = check_col2 + 1;
      }
    }

    long do_move = 0; // dont move

    if ( ( char_col1 == CHAR_BOX2_S ) && ( char_col2 == CHAR_BOX2_E ) )
    {
      do_move = checkVertical( pListC, knz_change_grid, check_row + delta_row, delta_row, box_left_col1, box_left_col2 );
    }
    else
    {
      long do_move_left = 1;
      long do_move_right = 1;

      if ( char_col1 == CHAR_BOX2_E )
      {
        do_move_left = checkVertical( pListC, knz_change_grid, check_row + delta_row, delta_row, box_left_col1, box_left_col2 );

        do_move = do_move_left;
      }

      if ( ( do_move_left == 1 ) && ( char_col2 == CHAR_BOX2_S ) )
      {
        do_move_right = checkVertical( pListC, knz_change_grid, check_row + delta_row, delta_row, box_right_col1, box_right_col2 );

        do_move = do_move_right;
      }
    }

    if ( ( knz_change_grid ) && ( do_move == 1 ) )
    {
      if ( box_left_col1 > 0 )
      {
        String prop_key = "";

        prop_key += "R" + getNumberWithLeadingZeros( check_row, NR_OF_DIGITS_LINE_INFO ) + "C" + getNumberWithLeadingZeros( box_left_col1, NR_OF_DIGITS_LINE_INFO ); // Sorting Info
        prop_key += "," + "R" + ( check_row ) + "C" + box_left_col1; // From
        prop_key += "," + "R" + ( check_row + delta_row ) + "C" + box_left_col1; // To
        prop_key += "," + CHAR_BOX2_S; // Char-Info
        prop_key += "," + char_col1; // Char-Info
        prop_key += ",START"; // Debug-Info

        pListC.add( prop_key );

        prop_key = "";

        prop_key += "R" + getNumberWithLeadingZeros( check_row, NR_OF_DIGITS_LINE_INFO ) + "C" + getNumberWithLeadingZeros( box_left_col2, NR_OF_DIGITS_LINE_INFO ); // Sorting Info
        prop_key += "," + "R" + ( check_row ) + "C" + box_left_col2; // From
        prop_key += "," + "R" + ( check_row + delta_row ) + "C" + box_left_col2; // To
        prop_key += "," + CHAR_BOX2_E; // Char-Info
        prop_key += "," + char_col2; // Char-Info
        prop_key += ",END"; // Debug-Info

        pListC.add( prop_key );
      }

      if ( box_right_col1 > 0 )
      {
        String prop_key = "";

        prop_key += "R" + getNumberWithLeadingZeros( check_row, NR_OF_DIGITS_LINE_INFO ) + "C" + getNumberWithLeadingZeros( box_right_col1, NR_OF_DIGITS_LINE_INFO ); // Sorting Info
        prop_key += "," + "R" + ( check_row ) + "C" + box_right_col1; // From
        prop_key += "," + "R" + ( check_row + delta_row ) + "C" + box_right_col1; // To
        prop_key += "," + CHAR_BOX2_S; // Char-Info
        prop_key += "," + char_col1; // Char-Info
        prop_key += ",START"; // Debug-Info

        pListC.add( prop_key );

        prop_key = "";

        prop_key += "R" + getNumberWithLeadingZeros( check_row, NR_OF_DIGITS_LINE_INFO ) + "C" + getNumberWithLeadingZeros( box_right_col2, NR_OF_DIGITS_LINE_INFO ); // Sorting Info
        prop_key += "," + "R" + ( check_row ) + "C" + box_right_col2; // From
        prop_key += "," + "R" + ( check_row + delta_row ) + "C" + box_right_col2; // To
        prop_key += "," + CHAR_BOX2_E; // Char-Info
        prop_key += "," + char_col2; // Char-Info
        prop_key += ",END"; // Debug-Info

        pListC.add( prop_key );
      }
    }

    return do_move;
  }

  private static long moveHorizontal( long robot_row, long robot_col, long delta_col, boolean pKnzDebug )
  {
    long do_move = robot_col;

    long free_space_row = robot_row;
    long free_space_col = robot_col;

    /*
     * First check the char from the robots perspektive.
     * Check if in the move direction is a wall.
     */
    char free_space_char = CHAR_BOX;

    /*
     * Search the next free space in the search direction.
     * The search is over, when there is a wall or an empty floor spot. 
     */
    while ( ( free_space_char != CHAR_WALL ) && ( free_space_char != CHAR_EMPTY_FLOOR ) )
    {
      free_space_col += delta_col;

      free_space_char = m_prop_grid_map.getProperty( "R" + free_space_row + "C" + free_space_col, MAP_COORDINATES_NOT_FOUND ).charAt( 0 );

      if ( pKnzDebug )
      {
        wl( "robot R" + robot_row + "C" + robot_col + "   free space R" + free_space_row + "C" + free_space_col + "  " + free_space_char );
      }
    }

    /*
     * Check, if a free floor space was found
     * 
     * If not, the free_space_char can only be wall.
     */
    if ( free_space_char == CHAR_EMPTY_FLOOR )
    {
      /*
       * Reverse the move direction
       * 
       * From the found free space, the search reverses.
       * 
       * Now it is the task to find the robot in the 
       * reverse move direction.
       * 
       * Every Item (can only be Box) is placed to the 
       * current free position coordinates.
       */
      delta_col *= -1;

      /*
       * Search the robot from the free space
       */
      char next_char = CHAR_EMPTY_FLOOR;

      long next_row = free_space_row;
      long next_col = free_space_col;

      do
      {
        /*
         * Calculate the next source position for the map swap
         */
        next_col += delta_col;

        /*
         * get the char from the next source coordinates
         */
        next_char = m_prop_grid_map.getProperty( "R" + next_row + "C" + next_col, MAP_COORDINATES_NOT_FOUND ).charAt( 0 );

        if ( pKnzDebug )
        {
          wl( "robot R" + robot_row + "C" + robot_col + "   free space R" + free_space_row + "C" + free_space_col + "  \"" + m_prop_grid_map.getProperty( "R" + next_row + "C" + next_col, MAP_COORDINATES_NOT_FOUND ) + "\"    free space R" + next_row + "C" + next_col + "  \"" + next_char + "\"" );
        }

        /*
         * If the robot is found, the new robot coordinates are set.
         * 
         */
        if ( next_char == CHAR_ROBOT )
        {
          do_move = free_space_col;
        }

        /*
         * save the char in the current free space coordinates
         * Make the free space, occupied by something
         */
        m_prop_grid_map.setProperty( "R" + free_space_row + "C" + free_space_col, "" + next_char );

        /*
         * Update the free space coordinates.
         */
        free_space_col = next_col;

      } while ( ( next_char != CHAR_ROBOT ) && ( next_char != CHAR_WALL ) );

      /*
       * The last free space position is set to CHAR_EMPTY_FLOOR.
       */
      m_prop_grid_map.setProperty( "R" + free_space_row + "C" + free_space_col, "" + CHAR_EMPTY_FLOOR );
    }

    return do_move;
  }

  private static String getDebugMap( Properties pGrid, long grid_height, long grid_width )
  {
    long goods_position_system_sum_value = 0;

    String debug_map = "";

    for ( long cur_x = 0; cur_x < grid_height; cur_x++ )
    {
      String cur_line = "";

      for ( long cur_y = 0; cur_y < grid_width; cur_y++ )
      {
        char cur_char = pGrid.getProperty( "R" + cur_x + "C" + cur_y, MAP_COORDINATES_NOT_FOUND ).charAt( 0 );

        if ( cur_char == CHAR_BOX2_S )
        {
          long goods_position_system_value = ( cur_x * 100 ) + cur_y;

          goods_position_system_sum_value += goods_position_system_value;
        }

        cur_line += cur_char;
      }

      debug_map += cur_line + "\n";
    }

    debug_map += "\nGPS " + goods_position_system_sum_value;

    return debug_map;
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day15_input.txt";

    try
    {
      string_array = Files.readAllLines( Path.of( datei_input ) );
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }

    return string_array;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }

  /**
   * @param pZahl die Zahl
   * @param pLaenge die vorgegebene Laenge
   * @return ein String der vorgegebenen Laenge mit den fuehrenden Nullen.
   */
  private static String getNumberWithLeadingZeros( long pZahl, int pLaenge )
  {
    return getFeldRechts( "" + pZahl, "0", pLaenge );
  }

  /**
   * @param pInhalt der Inhalt des Feldes
   * @param pAuffuellZeichen das zu benutzende Auffuellzeichen
   * @param pLaenge die Laenge
   * @return ein String der vorgegebenen Laenge und dem Inhalt rechts ausgerichtet
   */
  private static String getFeldRechts( String pInhalt, String pAuffuellZeichen, int pLaenge )
  {
    if ( pInhalt == null )
    {
      pInhalt = "";
    }

    if ( pInhalt.length() >= pLaenge )
    {
      return pInhalt.substring( 0, pLaenge );
    }

    return getNChars( pLaenge - pInhalt.length(), pAuffuellZeichen ) + pInhalt;
  }

  /**
   * <pre>
   * Gibt einen String in der angegebenen Laenge und der angegebenen Zeichenfolge zurueck.
   *  
   * Ist die Laenge negativ oder 0, wird ein Leerstring zurueckgegeben
   * 
   * Ist der Parameeter "pZeichen" gleich null, wird ein Leerstring zurueckgegeben.
   * </pre>
   * 
   * @param pAnzahlStellen die Laenge
   * @param pZeichen das zu wiederholende Zeichen
   * @return einen String der angegebenen Laenge mit dem uebergebenen Zeichen
   */
  private static String getNChars( int pAnzahlStellen, String pZeichen )
  {
    if ( pZeichen == null )
    {
      return "";
    }

    /*
     * Ist die Laenge negativ oder 0, wird ein Leerstring zurueckgegeben
     */
    if ( pAnzahlStellen <= 0 )
    {
      return "";
    }

    if ( pAnzahlStellen > 15000 )
    {
      pAnzahlStellen = 15000;
    }

    String ergebnis = pZeichen + pZeichen + pZeichen + pZeichen + pZeichen + pZeichen + pZeichen + pZeichen + pZeichen + pZeichen;

    /*
     * Der String "ergebnis" wird solange verdoppelt bis die Laenge groesser der Anzahl aus dem Parameter ist. 
     * Anschliessend wird ein Substring der Parameter-Laenge zurueckgegeben.
     */
    int zaehler = 1;

    while ( ( zaehler <= 50 ) && ( ergebnis.length() <= pAnzahlStellen ) )
    {
      ergebnis += ergebnis;

      zaehler++;
    }

    return ergebnis.substring( 0, pAnzahlStellen );
  }
}
