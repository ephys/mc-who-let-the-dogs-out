package be.ephys.wltdo;

import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.util.UUID;

public final class WolfSkin {
  public static final String WOLF_SKIN_KEY = ExampleMod.MODID + ":skin";

  public static final String[] textureNames = new String[] {
    "vanilla",
    "black",
    "collie1",
    "collie2",
//    "creeper",
    "dalmatian",
    "dog1",
//    "fire",
    "huskylike",
    "moonmoon",
//    "skelly",
    "white",
//    "zombie"
  };

  private static final ResourceLocation[][] textureCache = new ResourceLocation[WolfSkin.textureNames.length][];

  static {
    // vanilla textures
    textureCache[0] = new ResourceLocation[] {
      new ResourceLocation("textures/entity/wolf/wolf.png"),
      new ResourceLocation("textures/entity/wolf/wolf_tame.png"),
      new ResourceLocation("textures/entity/wolf/wolf_angry.png")
    };

    for (int i = 1; i < textureNames.length; i++) {
      getTextures(i);
    }
  }

  public static int availableTextureCount() {
    return textureNames.length;
  }

  public static ResourceLocation[] getTextures(int id) {
    if (textureCache[id] != null) {
      return textureCache[id];
    }

    String textureName = textureNames[id];
    ResourceLocation[] textures = new ResourceLocation[] {
      new ResourceLocation(ExampleMod.MODID, "textures/entity/wolf/" + textureName + "/neutral.png"),
      new ResourceLocation(ExampleMod.MODID, "textures/entity/wolf/" + textureName + "/tame.png"),
      new ResourceLocation(ExampleMod.MODID, "textures/entity/wolf/" + textureName + "/angry.png")
    };

    textureCache[id] = textures;

    return textures;
  }

  public static void setWolfSkinId(EntityWolf wolf, int id) {
    NBTTagCompound wolfData = wolf.getEntityData();
    wolfData.setInteger(WOLF_SKIN_KEY, id);
  }

  public static int getWolfSkinId(UUID uuid) {
    int textureCount = availableTextureCount();

    return Math.abs((int) ((uuid.getLeastSignificantBits() % textureCount) + (uuid.getMostSignificantBits() % textureCount))) % textureCount;
  }

  public static int getWolfSkinId(EntityWolf wolf) {
    NBTTagCompound wolfData = wolf.getEntityData();

    if (wolfData.hasKey(WolfSkin.WOLF_SKIN_KEY)) {
      return wolfData.getInteger(WolfSkin.WOLF_SKIN_KEY);
    } else {
      return WolfSkin.getWolfSkinId(wolf.getUniqueID());
    }
  }

  public static boolean hasPermanentSkin(EntityWolf wolf) {
    return wolf.getEntityData().hasKey(WolfSkin.WOLF_SKIN_KEY);
  }
}
