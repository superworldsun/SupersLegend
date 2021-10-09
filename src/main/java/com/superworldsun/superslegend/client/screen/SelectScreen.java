package com.superworldsun.superslegend.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.container.SelectContainer;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.SelectInteractionMessage;
import com.superworldsun.superslegend.registries.SoundInit;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

public class SelectScreen extends ContainerScreen<SelectContainer>
{
	final SelectContainer container;
	final PlayerInventory playerInventory;

	int buttonYOffset = 0;

	public SelectScreen(SelectContainer container, PlayerInventory playerInventory, ITextComponent title)
	{
		this(container, playerInventory, title, 0);
	}

	List<Slot> slots;
	private SelectScreen(SelectContainer container, PlayerInventory playerInventory, ITextComponent title, int menuIndex)
	{
		super(container, playerInventory, title);

		this.container = container;
		this.playerInventory = playerInventory;
		this.menuIndex = menuIndex;

		slots = container.slots;

		switch(menuIndex)
		{
			case 0:
				imageWidth = 215;
				imageHeight = 98;

				titleLabelX = 27;
				titleLabelY = 4;
				setupSwordInv();
				break;

			case 1:
				imageWidth = 215;
				imageHeight = 121;

				buttonYOffset = 121 - 98;

				titleLabelX = 17;
				titleLabelY = 4;
				setupEquipmentInv();
				break;

			case 3:
				imageWidth = 215;
				imageHeight = 121;

				buttonYOffset = 121 - 98;

				titleLabelX = 54;
				titleLabelY = 4;
				setupMaskInv();
				break;

			case 2:
				imageWidth = 215;
				imageHeight = 188;

				buttonYOffset = 188 - 98;

				titleLabelX = 36;
				titleLabelY = 4;
				setupRingInv();
				break;

			case 4:
				imageWidth = 215;
				imageHeight = 166;

				buttonYOffset = 166 - 98;

				titleLabelX = 5;
				titleLabelY = 5;
				setupStatusInv();
				break;
		}
	}

	@Override
	protected void init()
	{
		super.init();
	}

	@Override
	protected void renderLabels(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_)
	{
		this.font.draw(p_230451_1_, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
	}

	@Override
	protected void renderBg(MatrixStack stack, float p_230450_2_, int p_230450_3_, int p_230450_4_)
	{
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(
				new ResourceLocation(SupersLegendMain.MOD_ID, "textures/gui/select" + menuIndex + ".png"));

		int i = (this.width - getXSize()) / 2;
		int j = (this.height - getYSize()) / 2;

		this.blit(stack, i, j, 0, 0, getXSize(), getYSize());
	}

	public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_)
	{
		this.renderBackground(p_230430_1_);
		super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
		this.renderTooltip(p_230430_1_, p_230430_2_, p_230430_3_);
	}

	public int menuIndex;
	String[] titles = new String[]{ "Swords", "Equipment", "Rings", "Masks", "Status" };

	public boolean mouseClicked(double x, double y, int p_231044_5_)
	{
		int startingX = (this.width - this.imageWidth) / 2;
		int startingY = (this.height - this.imageHeight) / 2;

		int offset = 0;

		if(y - startingY >= 79 + buttonYOffset && y - startingY <= 92 + buttonYOffset)
		{
			if (x - startingX >= 4 && x - startingX <= 28) offset = -1;
			if (x - startingX >= 195 && x - startingX <= 209) offset = 1;

			if(offset != 0)
			{
				menuIndex = Math.floorMod((offset + menuIndex), container.inventories.length);
				NetworkDispatcher.networkChannel.sendToServer(new SelectInteractionMessage(offset, false));
			}
		}

		return super.mouseClicked(x, y, p_231044_5_);
	}

	public void resetScreen(int menuIndex)
	{
		minecraft.player.playSound(SoundInit.ARROW_HIT_SHOCK.get(), 1, 1);
		this.menuIndex = menuIndex;
		minecraft.setScreen(new SelectScreen(container, playerInventory, new StringTextComponent(titles[menuIndex]), menuIndex));
	}

	private void setupSwordInv()
	{
		int maxSlot = 36;
		for(int k = 0; k < 9; k++)
		{
			slots.get(k).x = 27 + 18 * k;
			slots.get(k).y = 76;
		}

		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				slots.get(x + y * 9 + 9).x = 27 + 18 * (x % 9);
				slots.get(x + y * 9 + 9).y = 18 + 18 * y;
			}
		}

		for(int k = maxSlot; k < slots.size(); k++)
		{
			slots.get(k).x = 1000;
		}
	}

	private void setupEquipmentInv()
	{
		int maxSlot = 49;
		for(int k = 0; k < 9; k++)
		{
			slots.get(k).x = 27 + 18 * k;
			slots.get(k).y = 99;
		}

		for(int y = 0; y < 4; y++)
		{
			for(int x = 0; x < 10; x++)
			{
				slots.get(x + y * 10 + 9).x = 18 + 18 * (x % 10);
				slots.get(x + y * 10 + 9).y = 18 + 18 * y;
			}
		}

		for(int k = maxSlot; k < slots.size(); k++)
		{
			slots.get(k).x = 1000;
		}
	}

	private void setupRingInv()
	{
		int maxSlot = 7 + 7 * 9 + 10;
		for(int k = 0; k < 9; k++)
		{
			slots.get(k).x = 27 + 18 * k;
			slots.get(k).y = 166;
		}

		for(int y = 0; y < 8; y++)
		{
			for(int x = 0; x < 8; x++)
			{
				slots.get(x + y * 8 + 9).x = 37 + 18 * (x % 9);
				slots.get(x + y * 8 + 9).y = 18 + 18 * y;
			}
		}

		for(int k = maxSlot; k < slots.size(); k++)
		{
			slots.get(k).x = 1000;
		}
	}

	private void setupMaskInv()
	{
		int maxSlot = 33;
		for(int k = 0; k < 9; k++)
		{
			slots.get(k).x = 27 + 18 * k;
			slots.get(k).y = 99;
		}

		for(int y = 0; y < 4; y++)
		{
			for(int x = 0; x < 6; x++)
			{
				slots.get(x + y * 6 + 9).x = 55 + 18 * (x % 9);
				slots.get(x + y * 6 + 9).y = 18 + 18 * y;
			}
		}

		for(int k = maxSlot; k < slots.size(); k++)
		{
			slots.get(k).x = 1000;
		}
	}

	private void setupStatusInv()
	{
		for(Slot slot : slots)
		{
			slot.x = 1000;
		}
	}
}
