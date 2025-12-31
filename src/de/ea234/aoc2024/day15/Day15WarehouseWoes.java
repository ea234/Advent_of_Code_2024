package de.ea234.aoc2024.day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Day15WarehouseWoes
{

  /*
   * --- Day 15: Warehouse Woes ---
   * https://adventofcode.com/2024/day/15 
   * 
   * https://www.youtube.com/watch?v=jnAj6cB2NvI
   *
   * https://www.reddit.com/r/adventofcode/comments/1hele8m/2024_day_15_solutions/
   */

  private static final int    ROW_UP                    = -1;

  private static final int    ROW_DOWN                  = 1;

  private static final int    COL_LEFT                  = -1;

  private static final int    COL_RIGHT                 = 1;

  private static final char   CHAR_WALL                 = '#';

  private static final char   CHAR_EMPTY_FLOOR          = '.';

  private static final char   CHAR_BOX                  = 'O';

  private static final char   CHAR_ROBOT                = '@';

  private static final char   CHAR_CMD_UP               = '^';

  private static final char   CHAR_CMD_DOWN             = 'v';

  private static final char   CHAR_CMD_LEFT             = '<';

  private static final char   CHAR_CMD_RIGHT            = '>';

  private static final String MAP_COORDINATES_NOT_FOUND = "_";

  public static void main( String[] args )
  {
    String test_content_1_map = "########,#..O.O.#,##@.O..#,#...O..#,#.#.O..#,#...O..#,#......#,########";

    String test_content_1_cmds = "<^^>>>vv<v>>v<<";

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
    //test_content_1 = "#########,#.......#,#..OOO..#,#.......#,#.@O....#,#.......#,#########";
    //test_content_1 = "########,#...#.E#,#.#.#.##,#S#...##,########";

    List< String > test_content_list_1 = Arrays.stream( ( test_content_1_map + ",," + test_content_1_cmds ).split( "," ) ).map( String::trim ).collect( Collectors.toList() );
    List< String > test_content_list_2 = Arrays.stream( ( test_content_2_map + ",," + test_content_2_cmds ).split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    //calculateGrid( test_content_list_1, 20, true );
    calculatePart1( test_content_list_2, 20, true );

    calculatePart1( getListProd(), 100, false );
  }

  private static void calculatePart1( List< String > pListInput, int pCheatMinLen, boolean pKnzDebug )
  {
    /*
     * *******************************************************************************************************
     * Initializing Variables
     * *******************************************************************************************************
     */

    String cmd_string = ""; // ">^^<<<<<<<<<<<<>>>>>>>>>vvvvvvvvvvv<<<<<<<<<<<<";

    Properties prop_grid_map = new Properties();

    long current_r = 0;
    long current_c = 0;

    long robot_row = 0;
    long robot_col = 0;

    long grid_height = pListInput.size();
    long grid_width = pListInput.get( 0 ).length();

    /*
     * *******************************************************************************************************
     * Initializing the grid
     * *******************************************************************************************************
     */

    boolean knz_load_map = true;

    for ( String input_str : pListInput )
    {
      /*
       * Blank line seperates map from commands
       */
      if ( input_str.trim().isBlank() )
      {
        knz_load_map = false;

        grid_height = current_r;
      }

      if ( knz_load_map )
      {
        for ( current_c = 0; current_c < input_str.length(); current_c++ )
        {
          char current_char = input_str.charAt( (int) current_c );

          if ( current_char == CHAR_ROBOT )
          {
            robot_row = current_r;

            robot_col = current_c;
          }

          prop_grid_map.setProperty( "R" + current_r + "C" + current_c, "" + current_char );

          prop_grid_map.setProperty( "OX" + current_r + "C" + current_c, "" + current_char );
        }
      }
      else
      {
        cmd_string += input_str;
      }

      current_r++;
    }

    if ( pKnzDebug )
    {
      wl( "" );
      wl( "Robot start at X" + robot_row + "C" + robot_col );
      wl( "" );
      wl( getDebugMap( prop_grid_map, grid_height, grid_width ) );
    }

    for ( int idx = 0; idx < cmd_string.length(); idx++ )
    {
      char cmd_char = cmd_string.charAt( idx );

      if ( pKnzDebug )
      {
        wl( "" );
        wl( "--------------------------------------------------------------" );
        wl( "cmd " + cmd_char + " nr " + idx );
      }

      /*
       * *******************************************************************************************************
       * SEARCH ENDPOINT OF MOVE
       * *******************************************************************************************************
       */

      long delta_row = 0;
      long delta_col = 0;

      if ( cmd_char == CHAR_CMD_UP )
      {
        delta_row = ROW_UP;
      }
      else if ( cmd_char == CHAR_CMD_DOWN )
      {
        delta_row = ROW_DOWN;
      }
      else if ( cmd_char == CHAR_CMD_LEFT )
      {
        delta_col = COL_LEFT;
      }
      else if ( cmd_char == CHAR_CMD_RIGHT )
      {
        delta_col = COL_RIGHT;
      }
      else
      {
        delta_row = -2;
      }

      /*
       * Check if a valid cmd was found.
       * Otherwise ignore the current cmd-char.
       */
      if ( delta_row != -2 )
      {
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
          free_space_row += delta_row;
          free_space_col += delta_col;

          free_space_char = prop_grid_map.getProperty( "R" + free_space_row + "C" + free_space_col, MAP_COORDINATES_NOT_FOUND ).charAt( 0 );

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
          delta_row *= -1;
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
            next_row += delta_row;
            next_col += delta_col;

            /*
             * get the char from the next source coordinates
             */
            next_char = prop_grid_map.getProperty( "R" + next_row + "C" + next_col, MAP_COORDINATES_NOT_FOUND ).charAt( 0 );

            if ( pKnzDebug )
            {
              wl( "robot R" + robot_row + "C" + robot_col + "   free space R" + free_space_row + "C" + free_space_col + "  \"" + prop_grid_map.getProperty( "R" + next_row + "C" + next_col, MAP_COORDINATES_NOT_FOUND ) + "\"    free space R" + next_row + "C" + next_col + "  \"" + next_char + "\"" );
            }

            /*
             * If the robot is found, the new robot coordinates are set.
             * 
             */
            if ( next_char == CHAR_ROBOT )
            {
              robot_row = free_space_row;
              robot_col = free_space_col;
            }

            /*
             * save the char in the current free space coordinates
             * Make the free space, occupied by something
             */
            prop_grid_map.setProperty( "R" + free_space_row + "C" + free_space_col, "" + next_char );

            /*
             * Update the free space coordinates.
             */
            free_space_row = next_row;
            free_space_col = next_col;

          } while ( ( next_char != CHAR_ROBOT ) && ( next_char != CHAR_WALL ) );

          /*
           * The last free space position is set to CHAR_EMPTY_FLOOR.
           */
          prop_grid_map.setProperty( "R" + free_space_row + "C" + free_space_col, "" + CHAR_EMPTY_FLOOR );
        }

        if ( pKnzDebug )
        {
          wl( "" );
          wl( getDebugMap( prop_grid_map, grid_height, grid_width ) );
        }
      }
    }

    /*
     * *******************************************************************************************************
     * Debug Grid
     * *******************************************************************************************************
     */
    wl( "" );
    wl( getDebugMap( prop_grid_map, grid_height, grid_width ) );
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

        if ( cur_char == CHAR_BOX )
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
    int row_count = 0;

    List< String > string_array = new ArrayList< String >();

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2024__day15_input.txt";

    try (BufferedReader buffered_reader = new BufferedReader( new FileReader( datei_input ) ))
    {
      String zeile;

      while ( ( zeile = buffered_reader.readLine() ) != null )
      {
        zeile = zeile.trim();

        string_array.add( zeile );

        row_count++;
      }
    }
    catch ( IOException err_inst )
    {
      err_inst.printStackTrace();
    }

    wl( "File Row Count " + row_count + " " + string_array.size() );

    return string_array;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }

}
