package nl.naxanria.thisandthat.tile;


import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import nl.naxanria.nlib.tile.TileEntityBase;
import nl.naxanria.nlib.tile.inventory.TileEntityInventoryBase;
import nl.naxanria.nlib.util.CompoundHelper;
import nl.naxanria.nlib.util.FurnaceHelper;
import nl.naxanria.nlib.util.StackUtil;
import nl.naxanria.thisandthat.util.ModRegistry;

public class TileEntityPrimitiveFurnace extends TileEntityInventoryBase
{
  public static final int SLOT_SMELT0 = 0;
  public static final int SLOT_SMELT1 = 1;
  public static final int SLOT_FUEL = 2;
  public static final int SLOT_OUTPUT0 = 3;
  public static final int SLOT_OUTPUT1 = 4;
  
  private final CompoundHelper helper;
  
  
  private int furnaceBurnTime;
  private int totalFurnaceBurnTime;
  private int cookTime;
  private int totalCookTime;
  
  //private int burnTimeDelay = 0;
  
  public TileEntityPrimitiveFurnace()
  {
    super(5);
    
    helper = new CompoundHelper()
    .create
      (
        "FurnaceBurnTime",
        this::getFurnaceBurnTime,
        this::setFurnaceBurnTime
      )
    .create
      (
        "TotalFurnaceBurnTime",
        this::getTotalFurnaceBurnTime,
        this::setTotalFurnaceBurnTime
      )
    .create
      (
        "CookTime",
        this::getCookTime,
        this::setCookTime
      )
    .create
      (
        "TotalCookTime",
        this::getTotalCookTime,
        this::setTotalCookTime
      );
  }
  
  @Override
  public boolean validForSlot(int slot, ItemStack stack)
  {
    if (slot == SLOT_SMELT0 || slot == SLOT_SMELT1)
    {
      return FurnaceHelper.isSmeltable(stack);
    }
    else if (slot == SLOT_FUEL)
    {
      return FurnaceHelper.isFuel(stack);
    }
    
    return false;
  }
  
  @Override
  protected void entityUpdate()
  {
    super.entityUpdate();
    
    boolean dirty = false;
    
    if (isBurning())
    {
      IBlockState state = world.getBlockState(pos.down());
      int burnTimeDelay = ModRegistry.getFurnaceModifier(state.getBlock());
//      if (state.getBlock() == Blocks.FIRE)
//      {
//        burnTimeDelay = 2;
//      }
//      else if (state.getBlock() == Blocks.LAVA)
//      {
//        burnTimeDelay = 3;
//      }
      
      if (burnTimeDelay == 0 || ticksPassed % burnTimeDelay == 0)
      {
        furnaceBurnTime--;
        dirty = true;
      }
    }
    
    if (!world.isRemote)
    {
      // check fuel
      if (!isBurning())
      {
        if (hasSmeltableItems())
        {
          ItemStack fuelItem = inventory.getStackInSlot(SLOT_FUEL);
  
          int burnTime = FurnaceHelper.getBurnTime(fuelItem);
  
          // only burn new one if we have items to smelt
  
          fuelItem.shrink(1);
          furnaceBurnTime = burnTime;
          
          dirty = true;
        }
      }
      
      // smelting
      if (isBurning())
      {
        // try smelt, right slot first then left.
        ItemStack smeltItem = inventory.getStackInSlot(SLOT_SMELT1);
        if (smeltItem.isEmpty())
        {
          smeltItem = inventory.getStackInSlot(SLOT_SMELT0);
        }
        
        if (!smeltItem.isEmpty())
        {
          // update cooktime
          cookTime++;
          totalCookTime = getCookTime(smeltItem);
          
          dirty = true;
          
          if (cookTime > totalCookTime)
          {
            cookTime = totalCookTime;
          }
          
          if (cookTime == totalCookTime)
          {
            // check if we can output the item
            ItemStack result = FurnaceHelper.getBurningResult(smeltItem);
            int idx = getResultValidSlot(smeltItem);
            if (idx == -1)
            {
              return;
            }
            
            
            ItemStack output = inventory.getStackInSlot(idx);
            
            if (output.isEmpty())
            {
              inventory.setStackInSlot(idx, result.copy());
            }
            else
            {
              StackUtil.changeStackSize(inventory.getStackInSlot(idx), 1);
            }
            //inventory.getStackInSlot(idx).grow(1);
            smeltItem.shrink(1);
            
            cookTime = 0;
          }
        }
        else
        {
          cookTime = 0;
        }
        
      }
      
      if (dirty)
      {
        markDirty();
      }
    }
  }
  
  public int getResultValidSlot(ItemStack smeltItem)
  {
    ItemStack result = FurnaceHelper.getBurningResult(smeltItem);
    
    if (result.isEmpty())
    {
      return -1;
    }
    
    int startSlot = SLOT_OUTPUT0;
    int endSlot = SLOT_OUTPUT1;
    
    for (int i = startSlot; i <= endSlot; i++)
    {
      ItemStack out = inventory.getStackInSlot(i);
      if (out.isEmpty())
      {
        return i;
      }
      
      if ( StackUtil.areItemsEqual(result, out, true))
      {
        if (out.getCount() + 1 <= out.getMaxStackSize())
        {
          return i;
        }
      }
    }
    
    return -1;
  }
  public int getCookTime(ItemStack stack)
  {
    return 200;
  }
  
  public float getCookProgress()
  {
    return 1;
  }
  
  public float getBurnProgress()
  {
    return 1;
  }
  
  private boolean hasSmeltableItems()
  {
    ItemStack s1 = inventory.getStackInSlot(SLOT_SMELT0);
    ItemStack s2 = inventory.getStackInSlot(SLOT_SMELT1);
    
    return getResultValidSlot(s1) != -1 | getResultValidSlot(s2) != -1;
  }
  
  
  public boolean isBurning()
  {
    return furnaceBurnTime > 0;
  }
  
  public int getFurnaceBurnTime()
  {
    return furnaceBurnTime;
  }
  
  public void setFurnaceBurnTime(int furnaceBurnTime)
  {
    this.furnaceBurnTime = furnaceBurnTime;
  }
  
  public int getCookTime()
  {
    return cookTime;
  }
  
  public void setCookTime(int cookTime)
  {
    this.cookTime = cookTime;
  }
  
  public int getTotalCookTime()
  {
    return totalCookTime;
  }
  
  public void setTotalCookTime(int totalCookTime)
  {
    this.totalCookTime = totalCookTime;
  }
  
  @Override
  public void writeSyncableNBT(NBTTagCompound compound, NBTType type)
  {
//    compound.setInteger("FurnaceBurnTime", furnaceBurnTime);
//    compound.setInteger("CookTime", cookTime);
//    compound.setInteger("TotalCookTime", totalCookTime);
   
    helper.writeToNBT(compound);
    
    super.writeSyncableNBT(compound, type);
  }
  
  @Override
  public void readSyncableNBT(NBTTagCompound compound, NBTType type)
  {
//    furnaceBurnTime = compound.getInteger("FurnaceBurnTime");
//    cookTime = compound.getInteger("CookTime");
//    totalCookTime = compound.getInteger("TotalCookTime");
    
    helper.readFromNBT(compound);
    
    super.readSyncableNBT(compound, type);
  }
  
  public int getTotalFurnaceBurnTime()
  {
    return totalFurnaceBurnTime;
  }
  
  public void setTotalFurnaceBurnTime(int totalFurnaceBurnTime)
  {
    this.totalFurnaceBurnTime = totalFurnaceBurnTime;
  }
}
