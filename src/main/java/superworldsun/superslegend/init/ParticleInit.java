package superworldsun.superslegend.init;


import com.mojang.serialization.Codec;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import superworldsun.superslegend.SupersLegend;
//import superworldsun.superslegend.particles.fairy.FairyParticleData;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

//public class ParticleInit {

    /*private static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, SupersLegend.modid);

    public static final RegistryObject<ParticleType<FairyParticleData>> FAIRY = r("fairy", () -> new ParticleType<FairyParticleData>(false, FairyParticleData.DESERIALIZER) {
        @Override
        @Nonnull
        public Codec<FairyParticleData> func_230522_e_() {
            return FairyParticleData.CODEC;
        }
    });
    //public static final RegistryObject<ParticleType<BasicParticleType>> FLAME = r("flame", () -> new BasicParticleType(false));

    private static <T extends IParticleData> RegistryObject<ParticleType<T>> r(String name, Supplier<ParticleType<T>> b) {
        return PARTICLES.register(name, b);
    }

    public static void subscribe(IEventBus modEventBus) {
        PARTICLES.register(modEventBus);
    }

}*/