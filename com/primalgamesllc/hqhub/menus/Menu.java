/*     */ package com.primalgamesllc.hqhub.menus;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.primalgamesllc.hqhub.Main;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class Menu
/*     */   implements InventoryHolder {
/*     */   protected ConcurrentMap<Integer, MenuItem> items;
/*     */   protected String title;
/*     */   protected int rows;
/*     */   protected boolean exitOnClickOutside;
/*     */   protected MenuAPI.MenuCloseBehaviour menuCloseBehaviour;
/*     */   protected boolean bypassMenuCloseBehaviour;
/*     */   protected Menu parentMenu;
/*     */   private Inventory inventory;
/*     */   
/*     */   public Menu(String title, int rows) {
/*  29 */     this(title, rows, null);
/*     */   }
/*     */   
/*     */   public Menu(String title, int rows, Menu parentMenu) {
/*  33 */     this.items = Maps.newConcurrentMap();
/*  34 */     this.exitOnClickOutside = false;
/*  35 */     this.bypassMenuCloseBehaviour = false;
/*  36 */     this.title = title;
/*  37 */     this.rows = rows;
/*  38 */     this.parentMenu = parentMenu;
/*     */   }
/*     */   
/*     */   public MenuAPI.MenuCloseBehaviour getMenuCloseBehaviour() {
/*  42 */     return this.menuCloseBehaviour;
/*     */   }
/*     */   
/*     */   public void setMenuCloseBehaviour(MenuAPI.MenuCloseBehaviour menuCloseBehaviour) {
/*  46 */     this.menuCloseBehaviour = menuCloseBehaviour;
/*     */   }
/*     */   
/*     */   public void setBypassMenuCloseBehaviour(boolean bypassMenuCloseBehaviour) {
/*  50 */     this.bypassMenuCloseBehaviour = bypassMenuCloseBehaviour;
/*     */   }
/*     */   
/*     */   public boolean bypassMenuCloseBehaviour() {
/*  54 */     return this.bypassMenuCloseBehaviour;
/*     */   }
/*     */   
/*     */   public void setExitOnClickOutside(boolean exit) {
/*  58 */     this.exitOnClickOutside = exit;
/*     */   }
/*     */   
/*     */   public Map<Integer, MenuItem> getMenuItems() {
/*  62 */     return this.items;
/*     */   }
/*     */   
/*     */   public boolean addMenuItem(MenuItem item, int x, int y) {
/*  66 */     return addMenuItem(item, y * 9 + x);
/*     */   }
/*     */   
/*     */   public MenuItem getMenuItem(int index) {
/*  70 */     return this.items.get(Integer.valueOf(index));
/*     */   }
/*     */   
/*     */   public boolean addMenuItem(MenuItem item, int index) {
/*  74 */     ItemStack slot = getInventory().getItem(index);
/*  75 */     if (slot != null && slot.getType() != Material.AIR) {
/*  76 */       removeMenuItem(index);
/*     */     }
/*  78 */     item.setSlot(index);
/*  79 */     getInventory().setItem(index, item.getItemStack());
/*  80 */     this.items.put(Integer.valueOf(index), item);
/*  81 */     item.addToMenu(this);
/*  82 */     return true;
/*     */   }
/*     */   
/*     */   public boolean removeMenuItem(int x, int y) {
/*  86 */     return removeMenuItem(y * 9 + x);
/*     */   }
/*     */   
/*     */   public boolean removeMenuItem(int index) {
/*  90 */     ItemStack slot = getInventory().getItem(index);
/*  91 */     if (slot == null || slot.getType().equals(Material.AIR)) {
/*  92 */       return false;
/*     */     }
/*  94 */     getInventory().clear(index);
/*  95 */     ((MenuItem)this.items.remove(Integer.valueOf(index))).removeFromMenu(this);
/*  96 */     return true;
/*     */   }
/*     */   
/*     */   protected void selectMenuItem(Player player, int index, InventoryClickType clickType) {
/* 100 */     if (this.items.containsKey(Integer.valueOf(index))) {
/* 101 */       MenuItem item = this.items.get(Integer.valueOf(index));
/* 102 */       item.onClick(player, clickType);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void openMenu(Player player) {
/* 107 */     if (!getInventory().getViewers().contains(player)) {
/* 108 */       player.openInventory(getInventory());
/*     */     }
/*     */   }
/*     */   
/*     */   public void closeMenu(Player player) {
/* 113 */     if (getInventory().getViewers().contains(player)) {
/* 114 */       getInventory().getViewers().remove(player);
/* 115 */       player.closeInventory();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void scheduleUpdateTask(final Player player, int ticks) {
/* 120 */     (new BukkitRunnable() {
/*     */         public void run() {
/* 122 */           if (player == null || Bukkit.getPlayer(player.getName()) == null) {
/* 123 */             cancel();
/*     */             return;
/*     */           } 
/* 126 */           if (player.getOpenInventory() == null || player.getOpenInventory().getTopInventory() == null || player.getOpenInventory().getTopInventory().getHolder() == null) {
/* 127 */             cancel();
/*     */             return;
/*     */           } 
/* 130 */           if (!(player.getOpenInventory().getTopInventory().getHolder() instanceof Menu)) {
/* 131 */             cancel();
/*     */             return;
/*     */           } 
/* 134 */           Menu menu = (Menu)player.getOpenInventory().getTopInventory().getHolder();
/* 135 */           if (!menu.inventory.equals(Menu.this.inventory)) {
/* 136 */             cancel();
/*     */             return;
/*     */           } 
/* 139 */           for (Map.Entry<Integer, MenuItem> entry : menu.items.entrySet()) {
/* 140 */             Menu.this.getInventory().setItem(((Integer)entry.getKey()).intValue(), ((MenuItem)entry.getValue()).getItemStack());
/*     */           }
/*     */         }
/* 143 */       }).runTaskTimer((Plugin)Main.getInstance(), ticks, ticks);
/*     */   }
/*     */   
/*     */   public Menu getParent() {
/* 147 */     return this.parentMenu;
/*     */   }
/*     */   
/*     */   public void setParent(Menu menu) {
/* 151 */     this.parentMenu = menu;
/*     */   }
/*     */   
/*     */   public Inventory getInventory() {
/* 155 */     if (this.inventory == null) {
/* 156 */       this.inventory = Bukkit.createInventory(this, this.rows * 9, this.title);
/*     */     }
/* 158 */     return this.inventory;
/*     */   }
/*     */   
/*     */   public boolean exitOnClickOutside() {
/* 162 */     return this.exitOnClickOutside;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Menu clone() {
/* 167 */     Menu clone = new Menu(this.title, this.rows);
/* 168 */     clone.setExitOnClickOutside(this.exitOnClickOutside);
/* 169 */     clone.setMenuCloseBehaviour(this.menuCloseBehaviour);
/* 170 */     for (Map.Entry<Integer, MenuItem> entry : this.items.entrySet()) {
/* 171 */       clone.addMenuItem(entry.getValue(), ((Integer)entry.getKey()).intValue());
/*     */     }
/* 173 */     return clone;
/*     */   }
/*     */   
/*     */   public void updateMenu() {
/* 177 */     for (HumanEntity entity : getInventory().getViewers())
/* 178 */       ((Player)entity).updateInventory(); 
/*     */   }
/*     */ }


/* Location:              /Users/benjaminmeyer/Downloads/HQHub.jar!/com/primalgamesllc/hqhub/menus/Menu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */