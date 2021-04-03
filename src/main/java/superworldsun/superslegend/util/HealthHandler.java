package superworldsun.superslegend.util;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.renderer.OverlayRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
//import net.minecraft.arrows.SharedMonsterAttributes;
//import net.minecraft.arrows.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
//import net.minecraftforge.client.ForgeIngameGui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class HealthHandler {
    protected static final Random rand = new Random();
    public static int playerHealth;
    protected static long healthUpdateCounter;
    protected static int lastPlayerHealth;
    protected static long lastSystemTime;
    protected static int updateCounter;
    protected static int rupees;
    protected static int rupeeTextureX;
    protected static int ArmorPosY;

    public HealthHandler() {
    }

    /*@SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void renderPlayerHealth(RenderGameOverlayEvent.Pre event) {
        Minecraft minecraft = Minecraft.getInstance();
        IngameGui ingameGUI=minecraft.ingameGUI;
        if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
            MainWindow scaledRes = minecraft.getMainWindow();
            if (!event.isCanceled()) {
                event.setCanceled(true);
            }
            if (minecraft.getRenderViewEntity() instanceof PlayerEntity) {
                updateCounter = ingameGUI.getTicks();
                PlayerEntity entityplayer = (PlayerEntity) minecraft.getRenderViewEntity();
                int health = MathHelper.ceil(entityplayer.getHealth());
                boolean flag = healthUpdateCounter > (long) updateCounter && (healthUpdateCounter - (long) updateCounter) / 3L % 2L == 1L;

                if (health < playerHealth && entityplayer.hurtResistantTime > 0) {
                    healthUpdateCounter = (long) (updateCounter + 20);
                } else if (health > playerHealth && entityplayer.hurtResistantTime > 0) {
                    healthUpdateCounter = (long) (updateCounter + 10);
                }

                if (updateCounter%20==0) {
                    playerHealth = health;
                    lastPlayerHealth = health;
                }

                playerHealth = health;
                int lastHealth = lastPlayerHealth;
                rand.setSeed((long) (updateCounter * 312871));
                FoodStats foodstats = entityplayer.getFoodStats();
                int foodLevel = foodstats.getFoodLevel();
                int widthleft = scaledRes.getScaledWidth() / 2 - 91;
                int widthright = scaledRes.getScaledWidth() / 2 + 91;
                int height = scaledRes.getScaledHeight() - 39;

                //ModifiableAttributeInstance iattributeinstance = entityplayer.getAttribute(playerHealth);
                float maxHealth =(float) health;
                int absorption = MathHelper.ceil(entityplayer.getAbsorptionAmount());
                int rows = Math.max(MathHelper.ceil((maxHealth + (float) absorption) / 4.0F / 10.0F), 1);
                int rows1 = Math.max(10 - (rows - 2), 3);
                int armorPosY = height - ((rows - 1) * rows1) - 10;
                ArmorPosY = scaledRes.getScaledHeight()-armorPosY;
                int airPosY = height - 10;
                int absorption1 = absorption;
                int armorValue = entityplayer.getTotalArmorValue();
                int regen = -1;

                if (entityplayer.isPotionActive(Effects.REGENERATION)) {
                    regen = updateCounter % MathHelper.ceil(maxHealth + 5.0F);
                }
                minecraft.getProfiler().startSection("armor");
                minecraft.getTextureManager().bindTexture(new ResourceLocation("minecraft:textures/gui/icons.png"));

                for (int i = 0; i < 10; ++i) {
                    if (armorValue > 0) {
                        int armorPosX = widthleft + i * 8;

                        if (i * 2 + 1 < armorValue) {
                            ingameGUI.blit(event.getMatrixStack(), armorPosX, armorPosY, 34, 9, 9, 9);
                        }

                        if (i * 2 + 1 == armorValue) {
                            ingameGUI.blit(event.getMatrixStack(), armorPosX, armorPosY, 25, 9, 9, 9);
                        }

                        if (i * 2 + 1 > armorValue) {
                            ingameGUI.blit(event.getMatrixStack(), armorPosX, armorPosY, 16, 9, 9, 9);
                        }
                    }
                }
                minecraft.getProfiler().endStartSection("health");
                minecraft.getTextureManager().bindTexture(new ResourceLocation("superslegend:textures/gui/icons.png"));

                for (int heartsToDraw = MathHelper.ceil((maxHealth + (float) absorption) / 4.0F) - 1; heartsToDraw >= 0; --heartsToDraw) {
                    int textureX = 0;

                    if (entityplayer.isPotionActive(Effects.POISON)) {
                        textureX += 108;
                    } else if (entityplayer.isPotionActive(Effects.WITHER)) {
                        textureX += 180;
                    }

                    int textureXOffset = 0;

                    if (flag) {
                        textureXOffset = 1;
                    }

                    int posYHelper = MathHelper.ceil((float) (heartsToDraw + 1) / 10.0F) - 1;
                    int posX = widthleft + heartsToDraw % 10 * 8;
                    int posY = height - posYHelper * rows1;

                    if (health <= 6) {
                        posY += rand.nextInt(2);
                    }

                    if (absorption1 <= 0 && heartsToDraw == regen) {
                        posY -= 2;
                    }

                    int hardcore = 0;

                    if (entityplayer.world.getWorldInfo().isHardcore()) {
                        hardcore = 1;
                    }

                    ingameGUI.blit(event.getMatrixStack(), posX, posY, textureXOffset * 9, 18 * hardcore, 9, 9);

                    if (flag) {
                        if (heartsToDraw * 4 + 3 < lastHealth) {
                            ingameGUI.blit(event.getMatrixStack(), posX, posY, textureX + 36, 18 * hardcore, 9, 9);
                        }
                        if (heartsToDraw * 4 + 2 < lastHealth) {
                            ingameGUI.blit(event.getMatrixStack(), posX, posY, textureX + 45, 18 * hardcore, 9, 9);
                        }
                        if (heartsToDraw * 4 + 1 < lastHealth) {
                            ingameGUI.blit(event.getMatrixStack(), posX, posY, textureX + 54, 18 * hardcore, 9, 9);
                        }
                        if (heartsToDraw * 4 + 1 == lastHealth) {
                            ingameGUI.blit(event.getMatrixStack(), posX, posY, textureX + 63, 18 * hardcore, 9, 9);
                        }
                    }

                    if (absorption1 > 0) {
                        if (absorption1 % 4 == 0) {
                            ingameGUI.blit(event.getMatrixStack(), posX, posY, textureX, 18 * hardcore + 9, 9, 9);
                            absorption1 = absorption1 - 4;
                        } else if (absorption1 == absorption && (absorption1 - 3) % 4 == 0) {
                            ingameGUI.blit(event.getMatrixStack(), posX, posY, textureX + 9, 18 * hardcore + 9, 9, 9);
                            absorption1 = absorption1 - 3;
                        } else if (absorption1 == absorption && (absorption1 - 2) % 4 == 0) {
                            ingameGUI.blit(event.getMatrixStack(), posX, posY, textureX + 18, 12 * hardcore + 9, 9, 9);
                            absorption1 = absorption1 - 2;
                        } else if (absorption1 == absorption && (absorption1 - 1) % 4 == 0) {
                            ingameGUI.blit(event.getMatrixStack(), posX, posY, textureX + 27, 9 * hardcore + 9, 9, 9);
                            absorption1--;
                        }
                    } else {
                        if (heartsToDraw * 4 + 3 < health) {
                            ingameGUI.blit(event.getMatrixStack(), posX, posY, textureX + 36, 18 * hardcore, 9, 9);
                        }
                        if (heartsToDraw * 4 + 2 < health) {
                            ingameGUI.blit(event.getMatrixStack(), posX, posY, textureX + 45, 18 * hardcore, 9, 9);
                        }
                        if (heartsToDraw * 4 + 1 < health) {
                            ingameGUI.blit(event.getMatrixStack(), posX, posY, textureX + 54, 18 * hardcore, 9, 9);
                        }
                        if (heartsToDraw * 4 + 1 == health) {
                            ingameGUI.blit(event.getMatrixStack(), posX, posY, textureX + 63, 18 * hardcore, 9, 9);
                        }
                    }
                }

                Entity entity = entityplayer.getRidingEntity();

                if (!(entity instanceof LivingEntity)) {
                    minecraft.getProfiler().endStartSection("food");
                    minecraft.getTextureManager().bindTexture(new ResourceLocation("minecraft:textures/gui/icons.png"));

                    for (int foodToDraw = 0; foodToDraw < 10; ++foodToDraw) {
                        int j6 = height;
                        int l6 = 16;
                        int j7 = 0;

                        if (entityplayer.isPotionActive(Effects.HUNGER)) {
                            l6 += 36;
                            j7 = 13;
                        }

                        if (entityplayer.getFoodStats().getSaturationLevel() <= 0.0F && updateCounter % (foodLevel * 3 + 1) == 0) {
                            j6 = height + (rand.nextInt(3) - 1);
                        }

                        int l7 = widthright - foodToDraw * 8 - 9;
                        ingameGUI.blit(event.getMatrixStack(), l7, j6, 16 + j7 * 9, 27, 9, 9);

                        if (foodToDraw * 2 + 1 < foodLevel) {
                            ingameGUI.blit(event.getMatrixStack(), l7, j6, l6 + 36, 27, 9, 9);
                        }

                        if (foodToDraw * 2 + 1 == foodLevel) {
                            ingameGUI.blit(event.getMatrixStack(), l7, j6, l6 + 45, 27, 9, 9);
                        }
                    }
                }

                minecraft.getProfiler().endStartSection("air");

                if (entityplayer.isInWater()) {
                    int i6 = minecraft.player.getAir();
                    int k6 = MathHelper.ceil((double) (i6 - 2) * 10.0D / 300.0D);
                    int i7 = MathHelper.ceil((double) i6 * 10.0D / 300.0D) - k6;

                    for (int k7 = 0; k7 < k6 + i7; ++k7) {
                        if (k7 < k6) {
                            ingameGUI.blit(event.getMatrixStack(), widthright - k7 * 8 - 9, airPosY, 16, 18, 9, 9);
                        } else {
                            ingameGUI.blit(event.getMatrixStack(), widthright - k7 * 8 - 9, airPosY, 25, 18, 9, 9);
                        }
                    }
                }

                minecraft.getProfiler().endSection();
            }
        }
        if (event.getType() == RenderGameOverlayEvent.ElementType.ARMOR) {
            event.setCanceled(true);
            ForgeIngameGui.left_height = ArmorPosY;
        }
    }*/
}
