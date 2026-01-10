package de.ea234.aoc2024.day09;

public class Day09AllocationBlock
{
  private long   debug_index  = 0;

  private String debug_string = "";

  private long   file_id      = 0;

  private long   capacity     = 0;

  public void setDebugString( String pDebugString )
  {
    debug_string = pDebugString;
  }

  public Day09AllocationBlock( long pDebugIndex, long pFileID, long pCapacity )
  {
    debug_index = pDebugIndex;

    file_id = pFileID;

    capacity = pCapacity;
  }

  public void setCapacity( long pCapacity )
  {
    capacity = pCapacity < 0 ? 0 : pCapacity;
  }

  public long getCapacity()
  {
    return capacity;
  }

  public void addCapacity( long pCapacity )
  {
    capacity += pCapacity;
  }

  public void setFileID( long pFileID )
  {
    file_id = pFileID;
  }

  public long getFileId()
  {
    return file_id;
  }

  public long getDebugIndex()
  {
    return debug_index;
  }

  public boolean isFreeBlockCapacity0()
  {
    return ( file_id < 0 ) && ( capacity == 0 );
  }

  public String toString()
  {
    return String.format( "%6d  ID %6d  Capacity %6d  " + debug_string, debug_index, file_id, getCapacity() );
  }
}
