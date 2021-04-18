/*    */ package com.primalgamesllc.hqhub.menus;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public abstract class MenuItem {
/*    */   private Menu menu;
/*    */   private int slot;
/*    */   
/*    */   protected void addToMenu(Menu menu) {
/* 11 */     this.menu = menu;
/*    */   }
/*    */   
/*    */   protected void removeFromMenu(Menu menu) {
/* 15 */     if (this.menu == menu) {
/* 16 */       this.menu = null;
/*    */     }
/*    */   }
/*    */   
/*    */   public Menu getMenu() {
/* 21 */     return this.menu;
/*    */   }
/*    */   
/*    */   public int getSlot() {
/* 25 */     return this.slot;
/*    */   }
/*    */   
/*    */   public void setSlot(int slot) {
/* 29 */     this.slot = slot;
/*    */   }
/*    */   
/*    */   public abstract void onClick(Player paramPlayer, InventoryClickType paramInventoryClickType);
/*    */   
/*    */   public abstract ItemStack getItemStack();
/*    */   
/*    */   public static abstract class UnclickableMenuItem extends MenuItem {
/*    */     public void onClick(Player player, InventoryClickType clickType) {}
/*    */   }
/*    */ }


