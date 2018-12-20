package nl.naxanria.thisandthat.item;

import nl.naxanria.nlib.item.ItemBase;
import nl.naxanria.nlib.registry.ItemRegistry;

public class ItemsInit
{
  public static final ItemBase TEST_ITEM = (ItemBase) new ItemBase("test_item").setMaxStackSize(16);
  
  public static class Ingots
  {
    public static final ItemBase INGOT_PRIMITIVE = new ItemBase("ingot_primitive");
    public static final ItemBase INGOT_BASIC = new ItemBase("ingot_basic");
  }
  
  public static class Blobs
  {
    public static final ItemBase BLOB_PRIMITIVE = new ItemBase("blob_primitive");
    public static final ItemBase BLOB_BASIC = new ItemBase("blob_basic");
  }
  
  public static void init(ItemRegistry registry)
  {
    registry.addAll
    (
      TEST_ITEM,
      
      Blobs.BLOB_PRIMITIVE,
      Blobs.BLOB_BASIC,
      
      Ingots.INGOT_PRIMITIVE,
      Ingots.INGOT_BASIC
    );
  }
}
