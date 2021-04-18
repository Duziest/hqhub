/*    */ package com.primalgamesllc.hqhub.utils;
/*    */ 
/*    */ import org.bukkit.enchantments.Enchantment;
/*    */ 
/*    */ public class EnchantUtils {
/*    */   public static Enchantment argsToEnchant(String args) {
/*  7 */     if (args.equalsIgnoreCase("Power")) {
/*  8 */       return Enchantment.ARROW_DAMAGE;
/*    */     }
/* 10 */     if (args.equalsIgnoreCase("Flame")) {
/* 11 */       return Enchantment.ARROW_FIRE;
/*    */     }
/* 13 */     if (args.equalsIgnoreCase("Infinity")) {
/* 14 */       return Enchantment.ARROW_INFINITE;
/*    */     }
/* 16 */     if (args.equalsIgnoreCase("Punch")) {
/* 17 */       return Enchantment.ARROW_KNOCKBACK;
/*    */     }
/* 19 */     if (args.equalsIgnoreCase("Sharpness")) {
/* 20 */       return Enchantment.DAMAGE_ALL;
/*    */     }
/* 22 */     if (args.equalsIgnoreCase("BaneofArthropods")) {
/* 23 */       return Enchantment.DAMAGE_ARTHROPODS;
/*    */     }
/* 25 */     if (args.equalsIgnoreCase("Smite")) {
/* 26 */       return Enchantment.DAMAGE_UNDEAD;
/*    */     }
/* 28 */     if (args.equalsIgnoreCase("Efficiency")) {
/* 29 */       return Enchantment.DIG_SPEED;
/*    */     }
/* 31 */     if (args.equalsIgnoreCase("Unbreaking")) {
/* 32 */       return Enchantment.DURABILITY;
/*    */     }
/* 34 */     if (args.equalsIgnoreCase("Fireaspect")) {
/* 35 */       return Enchantment.FIRE_ASPECT;
/*    */     }
/* 37 */     if (args.equalsIgnoreCase("Knockback")) {
/* 38 */       return Enchantment.KNOCKBACK;
/*    */     }
/* 40 */     if (args.equalsIgnoreCase("Fortune")) {
/* 41 */       return Enchantment.LOOT_BONUS_BLOCKS;
/*    */     }
/* 43 */     if (args.equalsIgnoreCase("Looting")) {
/* 44 */       return Enchantment.LOOT_BONUS_MOBS;
/*    */     }
/* 46 */     if (args.equalsIgnoreCase("LuckoftheSea")) {
/* 47 */       return Enchantment.LUCK;
/*    */     }
/* 49 */     if (args.equalsIgnoreCase("Lure")) {
/* 50 */       return Enchantment.LURE;
/*    */     }
/* 52 */     if (args.equalsIgnoreCase("Respiration")) {
/* 53 */       return Enchantment.OXYGEN;
/*    */     }
/* 55 */     if (args.equalsIgnoreCase("Protection")) {
/* 56 */       return Enchantment.PROTECTION_ENVIRONMENTAL;
/*    */     }
/* 58 */     if (args.equalsIgnoreCase("BlastProtection")) {
/* 59 */       return Enchantment.PROTECTION_EXPLOSIONS;
/*    */     }
/* 61 */     if (args.equalsIgnoreCase("FeatherFalling")) {
/* 62 */       return Enchantment.PROTECTION_FALL;
/*    */     }
/* 64 */     if (args.equalsIgnoreCase("FireProtection")) {
/* 65 */       return Enchantment.PROTECTION_FIRE;
/*    */     }
/* 67 */     if (args.equalsIgnoreCase("ProjectileProtection")) {
/* 68 */       return Enchantment.PROTECTION_PROJECTILE;
/*    */     }
/* 70 */     if (args.equalsIgnoreCase("Silktouch")) {
/* 71 */       return Enchantment.SILK_TOUCH;
/*    */     }
/* 73 */     if (args.equalsIgnoreCase("Thorns")) {
/* 74 */       return Enchantment.THORNS;
/*    */     }
/* 76 */     if (args.equalsIgnoreCase("Aqua Affinity")) {
/* 77 */       return Enchantment.WATER_WORKER;
/*    */     }
/* 79 */     return Enchantment.getByName(args);
/*    */   }
/*    */ }
