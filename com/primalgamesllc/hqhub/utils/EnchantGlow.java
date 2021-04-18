/*    */ package com.primalgamesllc.hqhub.utils;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import org.bukkit.enchantments.Enchantment;
/*    */ import org.bukkit.enchantments.EnchantmentTarget;
/*    */ import org.bukkit.enchantments.EnchantmentWrapper;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class EnchantGlow
/*    */   extends EnchantmentWrapper {
/*    */   private static Enchantment glow;
/*    */   
/*    */   public EnchantGlow(int id) {
/* 14 */     super(id);
/*    */   }
/*    */   
/*    */   public static Enchantment getGlow() {
/*    */     try {
/* 19 */       if (glow != null) {
/* 20 */         return glow;
/*    */       }
/* 22 */       Field f = Enchantment.class.getDeclaredField("acceptingNew");
/* 23 */       f.setAccessible(true);
/* 24 */       f.set((Object)null, Boolean.valueOf(true));
/* 25 */       Enchantment.registerEnchantment(glow = (Enchantment)new EnchantGlow(255));
/* 26 */       return glow;
/* 27 */     } catch (Exception e) {
/* 28 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void addGlow(ItemStack item) {
/*    */     try {
/* 34 */       Enchantment glow = getGlow();
/* 35 */       item.addEnchantment(glow, 1);
/* 36 */     } catch (Exception exception) {}
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canEnchantItem(ItemStack item) {
/* 41 */     return true;
/*    */   }
/*    */   
/*    */   public boolean conflictsWith(Enchantment other) {
/* 45 */     return false;
/*    */   }
/*    */   
/*    */   public EnchantmentTarget getItemTarget() {
/* 49 */     return null;
/*    */   }
/*    */   
/*    */   public int getMaxLevel() {
/* 53 */     return 10;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 57 */     return "Glow";
/*    */   }
/*    */   
/*    */   public int getStartLevel() {
/* 61 */     return 1;
/*    */   }
/*    */ }


/* Location:              /Users/benjaminmeyer/Downloads/HQHub.jar!/com/primalgamesllc/hqhub/utils/EnchantGlow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */