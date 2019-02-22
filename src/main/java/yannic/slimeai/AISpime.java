package yannic.slimeai;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.Minecraft;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.ArrayList;

public class AISpime extends slimeai.ModElement {

    public static int mobid = 1;
    public static int mobid2 = 2;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        EntityRegistry.registerModEntity(new ResourceLocation("slimeai:aispime"), EntityaISpime.class, "aispime", mobid, instance, 64, 1, true, -1,
                -1);
        Biome[] spawnBiomes = allbiomes(Biome.REGISTRY);
        EntityRegistry.addSpawn(EntityaISpime.class, 20, 3, 30, EnumCreatureType.MONSTER, spawnBiomes);
    }

    private Biome[] allbiomes(net.minecraft.util.registry.RegistryNamespaced<ResourceLocation, Biome> in) {
        Iterator<Biome> itr = in.iterator();
        ArrayList<Biome> ls = new ArrayList<Biome>();
        while (itr.hasNext())
            ls.add(itr.next());
        return ls.toArray(new Biome[ls.size()]);
    }

    @Override
    public void registerRenderers() {
        RenderLiving customRender = new RenderLiving(Minecraft.getMinecraft().getRenderManager(), new ModelSlime(0), 0) {

            protected ResourceLocation getEntityTexture(Entity par1Entity) {
                return new ResourceLocation("logo_48.png");
            }
        };
        RenderingRegistry.registerEntityRenderingHandler(EntityaISpime.class, customRender);
    }

    public static class EntityaISpime extends EntitySpider {

        public EntityaISpime(World world) {
            super(world);
            setSize(1f, 1f);
            experienceValue = 5;
            this.isImmuneToFire = false;
            setNoAI(false);
            this.tasks.addTask(1, new EntityAILookIdle(this));
            this.tasks.addTask(2, new EntityAILookIdle(this));
            this.tasks.addTask(3, new EntityAILookIdle(this));
        }

        //Makes the slime solid
        // TODO: 31.01.2019 make slime collision like water for non slimes
        @Nullable
        public AxisAlignedBB getCollisionBoundingBox()
        {
            return this.getEntityBoundingBox();
        }



        @Override
        public EnumCreatureAttribute getCreatureAttribute() {
            return EnumCreatureAttribute.UNDEFINED;
        }

        @Override
        protected Item getDropItem() {
            return null;
        }

        @Override
        public net.minecraft.util.SoundEvent getAmbientSound() {
            return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation(""));
        }

        @Override
        public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
            return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation(""));
        }

        @Override
        public net.minecraft.util.SoundEvent getDeathSound() {
            return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation(""));
        }

        @Override
        protected float getSoundVolume() {
            return 1.0F;
        }
    }
}
