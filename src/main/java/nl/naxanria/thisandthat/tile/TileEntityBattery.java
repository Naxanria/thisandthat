package nl.naxanria.thisandthat.tile;

import nl.naxanria.nlib.tile.power.EnergyStorageBase;

public class TileEntityBattery extends nl.naxanria.nlib.tile.power.TileEntityBattery
{
  public TileEntityBattery()
  {
    super(new EnergyStorageBase(2000000, 500, 500));
    
    
  }
}
