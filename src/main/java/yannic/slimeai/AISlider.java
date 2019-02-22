package yannic.slimeai;

import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.Entity;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.ArrayList;

public class AISlider extends slimeai.ModElement {

    public static int mobid = 3;
    public static int mobid2 = 4;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        EntityRegistry.registerModEntity(new ResourceLocation("slimeai:aislider"), EntityaISlider.class, "aislider", mobid, instance, 64, 1, true,
                -1, -1);
        Biome[] spawnBiomes = allbiomes(Biome.REGISTRY);
        EntityRegistry.addSpawn(EntityaISlider.class, 20, 3, 30, EnumCreatureType.MONSTER, spawnBiomes);
    }

    private Biome[] allbiomes(net.minecraft.util.registry.RegistryNamespaced<ResourceLocation, Biome> in) {
        Iterator<Biome> itr = in.iterator();
        ArrayList<Biome> ls = new ArrayList<Biome>();
        while (itr.hasNext())
            ls.add(itr.next());
        return ls.toArray(new Biome[ls.size()]);
    }

    public static class EntityaISlider extends EntitySlime {

        public EntityaISlider(World world) {
            super(world);
            setSize(1f, 1f);
            experienceValue = 5;
            this.isImmuneToFire = false;
            setNoAI(false);
        }

        // TODO: 31.01.2019, make slime collision like water for non slimes
        //makes other Entities solid for Slider (boat)
/*        @Nullable
        public AxisAlignedBB getCollisionBox(Entity entityIn)
        {
            return entityIn.canBePushed() ? entityIn.getEntityBoundingBox() : null;
        }*/

        @Nullable
        public AxisAlignedBB getCollisionBox(Entity entityIn)
        {
            return getSlimeSize() == super.getSlimeSize() ? entityIn.getEntityBoundingBox() : null;
        }

        //Makes the slime solid (old boat?)
 /*       public AxisAlignedBB getCollisionBoundingBox()
        {
            return this.isEntityAlive() ? this.getEntityBoundingBox() : null;
        }*/

        //Makes the slime solid (new boat)
 /*       @Nullable
        public AxisAlignedBB getCollisionBoundingBox()
        {
            return this.getEntityBoundingBox();
        }*/

        //Adds spider climbing AI

        private static final DataParameter<Byte> CLIMBING = EntityDataManager.<Byte>createKey(EntitySpider.class, DataSerializers.BYTE);


        protected PathNavigate createNavigator(World worldIn)
        {
            return new PathNavigateClimber(this, worldIn);
        }

        protected void entityInit()
        {
            super.entityInit();
            this.dataManager.register(CLIMBING, Byte.valueOf((byte)0));
        }


        public void onUpdate()
        {
            super.onUpdate();

            if (!this.world.isRemote)
            {
                this.setBesideClimbableBlock(this.collidedHorizontally);
            }
        }

        public boolean isOnLadder()
        {
            return this.isBesideClimbableBlock();
        }

        public boolean isBesideClimbableBlock()
        {
            return (((Byte)this.dataManager.get(CLIMBING)).byteValue() & 1) != 0;
        }

        public void setBesideClimbableBlock(boolean climbing)
        {
            byte b0 = ((Byte)this.dataManager.get(CLIMBING)).byteValue();

            if (climbing)
            {
                b0 = (byte)(b0 | 1);
            }
            else
            {
                b0 = (byte)(b0 & -2);
            }

            this.dataManager.set(CLIMBING, Byte.valueOf(b0));
        }




        // TODO: 31.01.2019, add spawnegg from tinkers with diffrent size slime

        // TODO: 31.01.2019, the slimes should only be sollid to smaller ones


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

        @Override
        public void onCollideWithPlayer(EntityPlayer entity) {
            super.onCollideWithPlayer(entity);
//            int x = (int) this.posX;
  //          int y = (int) this.posY;
    //        int z = (int) this.posZ;
            {
                java.util.HashMap<String, Object> $_dependencies = new java.util.HashMap<>();
                $_dependencies.put("entity", entity);
       //         PlayerSliderCollision.executeProcedure($_dependencies);
            }
        }
    }
}
