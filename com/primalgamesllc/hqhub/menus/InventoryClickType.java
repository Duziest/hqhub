/*    */ package com.primalgamesllc.hqhub.menus;
/*    */ 
/*    */ import org.bukkit.event.inventory.InventoryAction;
/*    */ 
/*    */ public enum InventoryClickType {
/*  6 */   LEFT("LEFT", 0, true, false),
/*  7 */   SHIFT_LEFT("SHIFT_LEFT", 1, true, true),
/*  8 */   RIGHT("RIGHT", 2, false, false),
/*  9 */   OTHER("OTHER", 3, false, false);
/*    */   
/*    */   private boolean leftClick;
/*    */   private boolean shiftClick;
/*    */   
/*    */   InventoryClickType(String s, int n, boolean leftClick, boolean shiftClick) {
/* 15 */     this.leftClick = leftClick;
/* 16 */     this.shiftClick = shiftClick;
/*    */   }
/*    */   
/*    */   public static InventoryClickType fromInventoryAction(InventoryAction action) {
/* 20 */     switch (action) {
/*    */       case PICKUP_ALL:
/*    */       case PLACE_ALL:
/*    */       case PLACE_SOME:
/*    */       case SWAP_WITH_CURSOR:
/* 25 */         return LEFT;
/*    */       
/*    */       case PICKUP_HALF:
/*    */       case PLACE_ONE:
/* 29 */         return RIGHT;
/*    */       
/*    */       case MOVE_TO_OTHER_INVENTORY:
/* 32 */         return SHIFT_LEFT;
/*    */     } 
/*    */     
/* 35 */     return OTHER;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isLeftClick() {
/* 41 */     return (this.leftClick && this != OTHER);
/*    */   }
/*    */   
/*    */   public boolean isRightClick() {
/* 45 */     return (!this.leftClick && this != OTHER);
/*    */   }
/*    */   
/*    */   public boolean isShiftClick() {
/* 49 */     return this.shiftClick;
/*    */   }
/*    */ }


/* Location:              /Users/benjaminmeyer/Downloads/HQHub.jar!/com/primalgamesllc/hqhub/menus/InventoryClickType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */