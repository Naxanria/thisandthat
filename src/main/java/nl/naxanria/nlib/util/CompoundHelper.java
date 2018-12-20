package nl.naxanria.nlib.util;

import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CompoundHelper
{
  private class CompoundInfo
  {
    public final String name;
    public final Consumer<Integer> writer;
    public final Supplier<Integer> reader;
  
    public CompoundInfo(String name, Consumer<Integer> writer, Supplier<Integer> reader)
    {
      this.name = name;
      this.writer = writer;
      this.reader = reader;
    }
  }
  
  private HashMap<String, CompoundInfo> sync = new HashMap<>();
  
  public CompoundHelper()
  { }
  
  public CompoundHelper create(String name, Supplier<Integer> reader, Consumer<Integer> writer)
  {
    CompoundInfo info = new CompoundInfo(name, writer, reader);
    
    sync.put(name, info);
    
    return this;
  }
  
  public void writeToNBT(NBTTagCompound compound)
  {
    for (CompoundInfo info :
      sync.values())
    {
      compound.setInteger(info.name, info.reader.get());
    }
  }
  
  public void readFromNBT(NBTTagCompound compound)
  {
    for (CompoundInfo info :
      sync.values())
    {
      info.writer.accept(compound.getInteger(info.name));
    }
  }
}
