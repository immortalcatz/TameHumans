package com.tamehumans.entity;

import com.tamehumans.entity.ai.EntityAIDefensiveArrowAttack;
import com.tamehumans.entity.ai.EntityAITamedNearestAttackableTarget;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityArcher extends EntityHumanBase implements ITameableRangedAttackMob {

    public EntityArcher(World p_i1683_1_) {
        super(p_i1683_1_);
        this.goldNeededToTame += this.rand.nextInt(15);

        int taskPriority = 0;
        this.tasks.addTask(++taskPriority, new EntityAISwimming(this));
        this.tasks.addTask(++taskPriority, new EntityAIDefensiveArrowAttack(this, 20, 40, 15.0F, 10.0F));
        this.tasks.addTask(++taskPriority, new EntityAIFollowOwner(this, 1.0D, 10.0F, 4.0F));
        this.tasks.addTask(++taskPriority, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(++taskPriority, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(++taskPriority, new EntityAILookIdle(this));

        int targetPriority = 0;
        this.targetTasks.addTask(++targetPriority, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(++targetPriority, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(++targetPriority, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(++targetPriority, new EntityAITamedNearestAttackableTarget(this, EntityCreeper.class, 0, true));
        this.targetTasks.addTask(++targetPriority, new EntityAITamedNearestAttackableTarget(this, EntityZombie.class, 0, true));
        this.targetTasks.addTask(++targetPriority, new EntityAITamedNearestAttackableTarget(this, EntitySkeleton.class, 0, true));
        this.targetTasks.addTask(++targetPriority, new EntityAITamedNearestAttackableTarget(this, EntitySpider.class, 0, true));
        this.targetTasks.addTask(++targetPriority, new EntityAITamedNearestAttackableTarget(this, EntityCaveSpider.class, 0, true));
        this.targetTasks.addTask(++targetPriority, new EntityAITamedNearestAttackableTarget(this, EntityBlaze.class, 0, true));
        this.targetTasks.addTask(++targetPriority, new EntityAITamedNearestAttackableTarget(this, EntityGhast.class, 0, true));
        this.targetTasks.addTask(++targetPriority, new EntityAITamedNearestAttackableTarget(this, EntityWither.class, 0, true));
        this.targetTasks.addTask(++targetPriority, new EntityAITamedNearestAttackableTarget(this, EntityDragon.class, 0, true));
    }

    public String getMyName() {
        return "Archer";
    }

    public Item getDefaultEquipment() {
        return Items.bow;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
    }

    public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_) {
        EntityArrow entityarrow = new EntityArrow(this.worldObj, this, p_82196_1_, 1.6F, (float)(14 - this.worldObj.difficultySetting.getDifficultyId() * 4));
        entityarrow.setDamage((double)(p_82196_2_ * 2.0F) + this.rand.nextGaussian() * 0.5D + 3D);

        int powerEffect = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
        if (powerEffect > 0)
        {
            entityarrow.setDamage(entityarrow.getDamage() + (double)powerEffect * 0.5D + 0.5D);
        }

        int punchEffect = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());
        if (punchEffect > 0)
        {
            entityarrow.setKnockbackStrength(punchEffect);
        }

        this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.worldObj.spawnEntityInWorld(entityarrow);
    }

    public boolean canAttackClass(Class p_70686_1_)
    {
        return true;
    }
}
