package me.otho.customItems.mod.handler;

import java.util.Random;

import me.otho.customItems.configuration.jsonReaders.common.Cfg_drop;
import me.otho.customItems.configuration.jsonReaders.entities.Cfg_entityDrop;
import me.otho.customItems.registry.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EntityDropHandler {

  protected int getItemDropQuantity(Cfg_drop data) {
    Random rand = new Random();
    int ret = data.min;
    int i;

    // TODO: looting effect
    for (i = data.min; i < data.max; i++) {
      boolean willDrop = rand.nextFloat() * 100 < data.chance;
      if (willDrop) {
        ret++;
      }
    }

    return ret;
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public void onEntityDrop(LivingDropsEvent event) {
    Random random = new Random();
    LivingEntity ent = event.getEntityLiving();
    
    /*
    if (EntityList.classToStringMapping.containsKey(event.entityLiving.getClass())) {
      String entityId = ((String) EntityList.classToStringMapping.get(event.entityLiving.getClass()));

      if (EntityRegistry.drops.containsKey(entityId)) {
        Cfg_entityDrop entityDrop = EntityRegistry.drops.get(entityId);

        if (entityDrop.overrides) {
          event.getDrops().clear();
        }

        for (Cfg_drop drop : entityDrop.drops) {

          String[] parser = drop.id.split(":");
          String modId = parser[0];
          String name = parser[1];
          int damage = 0;
          if (parser.length > 2) {
            damage = Integer.parseInt(parser[2]);
          }

          
          Item item = GameRegistry.findItem(modId, name);
          int quantity = getItemDropQuantity(drop);

          event.drops.add(new EntityItem(event.entity.worldObj, ent.posX, ent.posY, ent.posZ,
              new ItemStack(item, quantity, damage)));
        }
      }
    }
    */
  }
}
