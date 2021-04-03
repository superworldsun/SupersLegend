package superworldsun.superslegend.particles.fairy;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/*public class FairyParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite spriteSet;
    public static final IParticleRenderType TERRAIN_SHEET = new IParticleRenderType() {
        @SuppressWarnings("deprecation")
        public void beginRender(BufferBuilder bufferBuilder, TextureManager textureManager) {
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            textureManager.bindTexture(AtlasTexture.LOCATION_PARTICLES_TEXTURE);
            bufferBuilder.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        }

        public void finishRender(Tessellator tesselator) {
            tesselator.draw();
        }

        public String toString() {
            return "TERRAIN_SHEET";
        }
    };

    private FairyParticle(ClientWorld world, double x, double y, double z, double moveX, double moveY, double moveZ, FairyParticleData type, IAnimatedSprite sprites) {
        super(world, x, y, z);
        this.multiplyParticleScaleBy(2.0F * type.getScale());
        this.setSize(2F, 2F);
        this.maxAge = (int) 0.1F;//this.rand.nextInt(5) + 15;
        this.spriteSet = sprites;
        this.particleRed = 256F - type.getRed();
        this.particleGreen = 256F - type.getGreen();
        this.particleBlue = 256F - type.getBlue();
        this.selectSpriteRandomly(spriteSet);
        this.particleGravity = 0;
    }



    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if(this.age++ < this.maxAge && !(this.particleAlpha <= 0.0F)) {
            if(this.age >= this.maxAge - 60 && this.particleAlpha > 0.01F) {
                this.particleAlpha += 1.0F;
            }
            this.selectSpriteRandomly(spriteSet);
        } else {
            this.setExpired();
        }
    }

    public IParticleRenderType getRenderType() {
        return TERRAIN_SHEET;
    }

    @OnlyIn(Dist.CLIENT)
    public static class FairyFactory implements IParticleFactory<FairyParticleData> {
        private final IAnimatedSprite spriteSet;

        public FairyFactory(IAnimatedSprite sprites) {
            this.spriteSet = sprites;
        }

        public Particle makeParticle(FairyParticleData type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new FairyParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, type, spriteSet);
        }
    }*/
