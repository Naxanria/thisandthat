package nl.naxanria.nlib.util;

import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class CompoundHelper<T>
{
  private class CompoundInfo<TInternal>
  {
    public final String name;
    public final Consumer<TInternal> writer;
    public final Supplier<TInternal> reader;
  
    public CompoundInfo(String name, Consumer<TInternal> writer, Supplier<TInternal> reader)
    {
      this.name = name;
      this.writer = writer;
      this.reader = reader;
    }
  }
  
  private HashMap<String, CompoundInfo> sync = new HashMap<>();
  
  protected CompoundHelper()
  { }
  
  public CompoundHelper<T> create(String name, Supplier<T> reader, Consumer<T> writer)
  {
    CompoundInfo info = new CompoundInfo<T>(name, writer, reader);
    
    sync.put(name, info);
    
    return this;
  }
  
  public void writeToNBT(NBTTagCompound compound)
  {
    for (CompoundInfo info :
      sync.values())
    {
      write(compound, info.name, (T) info.reader.get());
    }
  }
  
  public void readFromNBT(NBTTagCompound compound)
  {
    for (CompoundInfo info :
      sync.values())
    {
      info.writer.accept(read(compound, info.name));
    }
  }
  
  protected abstract void write(NBTTagCompound compound, String name, T val);
  protected abstract T read(NBTTagCompound compound, String name);
  
  public static CompoundHelper<Integer> Int()
  {
    return new CompoundHelper<Integer>()
    {
      @Override
      protected void write(NBTTagCompound compound, String name, Integer val)
      {
        compound.setInteger(name, val);
      }
  
      @Override
      protected Integer read(NBTTagCompound compound, String name)
      {
        return compound.getInteger(name);
      }
    };
  }
  
  public static CompoundHelper<String> Str()
  {
    return new CompoundHelper<String>()
    {
      @Override
      protected void write(NBTTagCompound compound, String name, String val)
      {
        compound.setString(name, val);
      }
  
      @Override
      protected String read(NBTTagCompound compound, String name)
      {
        return compound.getString(name);
      }
    };
  }
  
  public static CompoundHelper<Float> Float()
  {
    return new CompoundHelper<Float>()
    {
      @Override
      protected void write(NBTTagCompound compound, String name, Float val)
      {
        compound.setFloat(name, val);
      }
  
      @Override
      protected Float read(NBTTagCompound compound, String name)
      {
        return compound.getFloat(name);
      }
    };
  }
}
