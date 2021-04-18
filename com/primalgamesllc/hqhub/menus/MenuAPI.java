/*     */ package com.primalgamesllc.hqhub.menus;
/*     */ 
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.inventory.InventoryAction;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryCloseEvent;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ 
/*     */ public class MenuAPI implements Listener {
/*     */   private static MenuAPI instance;
/*     */   
/*     */   public static MenuAPI getInstance() {
/*  19 */     if (instance == null) {
/*  20 */       synchronized (MenuAPI.class) {
/*  21 */         if (instance == null) {
/*  22 */           instance = new MenuAPI();
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  27 */     return instance;
/*     */   }
/*     */   
/*     */   public Menu createMenu(String title, int rows) {
/*  31 */     return new Menu(title, rows);
/*     */   }
/*     */   
/*     */   public Menu cloneMenu(Menu menu) {
/*  35 */     return menu.clone();
/*     */   }
/*     */   
/*     */   public void removeMenu(Menu menu) {
/*  39 */     for (HumanEntity viewer : menu.getInventory().getViewers()) {
/*  40 */       if (viewer instanceof Player) {
/*  41 */         menu.closeMenu((Player)viewer); continue;
/*     */       } 
/*  43 */       viewer.closeInventory();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.LOWEST)
/*     */   public void onMenuItemClicked(InventoryClickEvent event) {
/*  50 */     Inventory inventory = event.getInventory();
/*  51 */     if (inventory.getHolder() instanceof Menu) {
/*  52 */       event.setCancelled(true);
/*  53 */       ((Player)event.getWhoClicked()).updateInventory();
/*  54 */       switch (event.getAction()) {
/*     */       
/*  56 */       }  Menu menu = (Menu)inventory.getHolder();
/*  57 */       if (event.getWhoClicked() instanceof Player) {
/*     */ 
/*     */         
/*  60 */         Player player = (Player)event.getWhoClicked();
/*  61 */         if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) {
/*  62 */           if (menu.exitOnClickOutside()) {
/*  63 */             menu.closeMenu(player);
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/*  68 */           int index = event.getRawSlot();
/*  69 */           if (index < inventory.getSize()) {
/*  70 */             if (event.getAction() != InventoryAction.NOTHING) {
/*  71 */               menu.selectMenuItem(player, index, InventoryClickType.fromInventoryAction(event.getAction()));
/*     */             
/*     */             }
/*     */           
/*     */           }
/*  76 */           else if (menu.exitOnClickOutside()) {
/*  77 */             menu.closeMenu(player);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
/*     */   public void onMenuClosed(InventoryCloseEvent event) {
/*  90 */     if (event.getPlayer() instanceof Player) {
/*  91 */       Inventory inventory = event.getInventory();
/*  92 */       if (inventory.getHolder() instanceof Menu) {
/*  93 */         Menu menu = (Menu)inventory.getHolder();
/*  94 */         MenuCloseBehaviour menuCloseBehaviour = menu.getMenuCloseBehaviour();
/*  95 */         if (menuCloseBehaviour != null) {
/*  96 */           menuCloseBehaviour.onClose((Player)event.getPlayer(), menu, menu.bypassMenuCloseBehaviour());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
/*     */   public void onPlayerLogoutCloseMenu(PlayerQuitEvent event) {
/* 104 */     if (event.getPlayer().getOpenInventory() == null || !(event.getPlayer().getOpenInventory().getTopInventory().getHolder() instanceof Menu)) {
/*     */       return;
/*     */     }
/* 107 */     Menu menu = (Menu)event.getPlayer().getOpenInventory().getTopInventory().getHolder();
/* 108 */     menu.setBypassMenuCloseBehaviour(true);
/* 109 */     menu.setMenuCloseBehaviour(null);
/* 110 */     event.getPlayer().closeInventory();
/*     */   }
/*     */   
/*     */   public static interface MenuCloseBehaviour {
/*     */     void onClose(Player param1Player, Menu param1Menu, boolean param1Boolean);
/*     */   }
/*     */ }


