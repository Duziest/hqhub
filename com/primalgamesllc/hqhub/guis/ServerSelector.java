/*     */ package com.primalgamesllc.hqhub.guis;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.primalgamesllc.hqhub.Main;
/*     */ import com.primalgamesllc.hqhub.menus.InventoryClickType;
/*     */ import com.primalgamesllc.hqhub.menus.Menu;
/*     */ import com.primalgamesllc.hqhub.menus.MenuAPI;
/*     */ import com.primalgamesllc.hqhub.menus.MenuItem;
/*     */ import com.primalgamesllc.hqhub.objects.HQServer;
/*     */ import com.primalgamesllc.hqhub.tasks.InventoryUpdateTask;
/*     */ import com.primalgamesllc.hqhub.utils.BungeeUtils;
/*     */ import com.primalgamesllc.hqhub.utils.EnchantGlow;
/*     */ import com.primalgamesllc.hqhub.utils.ItemUtils;
/*     */ import com.primalgamesllc.hqhub.utils.ObjectSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.metadata.FixedMetadataValue;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class ServerSelector
/*     */ {
/*     */   public static void openInv(final Player p) {
/*  32 */     if (!p.hasMetadata("ANIMATED_GUI")) {
/*  33 */       p.setMetadata("ANIMATED_GUI", (MetadataValue)new FixedMetadataValue((Plugin)Main.getInstance(), ""));
/*  34 */       Menu menu = MenuAPI.getInstance().createMenu(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("gui.selector.name")), Main.getInstance().getConfig().getInt("gui.selector.size") / 9);
/*  35 */       setup(menu);
/*  36 */       menu.openMenu(p);
/*  37 */       Main.getInstance().playSound(p, "open-selector");
/*  38 */       final List<Integer> ignoredSlots = new ArrayList<>();
/*  39 */       for (String key : Main.getInstance().getConfig().getConfigurationSection("gui.selector.items").getKeys(false)) {
/*  40 */         if (!p.hasMetadata("INV_UPDATE"))
/*  41 */           p.setMetadata("INV_UPDATE", (MetadataValue)new FixedMetadataValue((Plugin)Main.getInstance(), "")); 
/*  42 */         Map<String, Object> map = Main.getInstance().getConfig().getConfigurationSection("gui.selector.items." + key).getValues(true);
/*  43 */         if (map.containsKey("scrollItems") && map.containsKey("scrollItemList") && 
/*  44 */           map.get("scrollItems").equals(Boolean.valueOf(true))) {
/*  45 */           ignoredSlots.add(Integer.valueOf(map.get("slot").toString()));
/*  46 */           List<String> scrollItemListRaw = Main.getInstance().getConfig().getStringList("gui.selector.items." + key + ".scrollItemList");
/*  47 */           Map<Integer, List<Material>> scrollItemList = new HashMap<>();
/*  48 */           List<Material> materialList = new ArrayList<>();
/*  49 */           for (String materialName : scrollItemListRaw) {
/*  50 */             materialList.add(Material.getMaterial(materialName));
/*     */           }
/*  52 */           scrollItemList.put(Integer.valueOf(map.get("slot").toString()), materialList);
/*  53 */           InventoryUpdateTask inventoryUpdateTask = new InventoryUpdateTask(p, menu.getInventory(), scrollItemList, map);
/*  54 */           Bukkit.getScheduler().runTaskTimer((Plugin)Main.getInstance(), (Runnable)inventoryUpdateTask, 0L, 20L);
/*     */         } 
/*     */       } 
/*     */       
/*  58 */       (new BukkitRunnable() {
/*     */           public void run() {
/*  60 */             if (p == null || Bukkit.getPlayer(p.getName()) == null) {
/*  61 */               cancel();
/*     */               return;
/*     */             } 
/*  64 */             if (p.getOpenInventory() == null || !(p.getOpenInventory().getTopInventory().getHolder() instanceof Menu)) {
/*  65 */               p.removeMetadata("ANIMATED_GUI", (Plugin)Main.getInstance());
/*  66 */               if (p.hasMetadata("INV_UPDATE")) {
/*  67 */                 p.removeMetadata("INV_UPDATE", (Plugin)Main.getInstance());
/*     */               }
/*  69 */               cancel();
/*     */               return;
/*     */             } 
/*  72 */             Menu menu = (Menu)p.getOpenInventory().getTopInventory().getHolder();
/*  73 */             ServerSelector.setup(menu, ignoredSlots);
/*  74 */             menu.updateMenu();
/*     */           }
/*  76 */         }).runTaskTimer((Plugin)Main.getInstance(), 5L, 5L);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void setup(Menu menu, List<Integer> ignoredSlots) {
/*  81 */     List<Integer> usedSlots = Lists.newArrayList();
/*  82 */     for (Integer integer : ignoredSlots) {
/*  83 */       usedSlots.add(integer);
/*     */     }
/*  85 */     for (String key : Main.getInstance().getConfig().getConfigurationSection("gui.selector.items").getKeys(false)) {
/*  86 */       Map<String, Object> map = Main.getInstance().getConfig().getConfigurationSection("gui.selector.items." + key).getValues(true);
/*  87 */       int slot = map.containsKey("slot") ? Integer.parseInt(map.get("slot").toString()) : menu.getInventory().firstEmpty();
/*  88 */       if (ignoredSlots.contains(Integer.valueOf(slot))) {
/*     */         continue;
/*     */       }
/*  91 */       final String name = map.containsKey("server-name") ? map.get("server-name").toString() : null;
/*  92 */       int amount = BungeeUtils.getServerCount(name);
/*  93 */       ItemStack stack = null;
/*  94 */       for (HQServer hqServer : (Main.getInstance()).serverList) {
/*  95 */         if (hqServer.getServerName().equals(name)) {
/*  96 */           stack = ItemUtils.load(map, new ObjectSet[] { new ObjectSet("<server>", name), new ObjectSet("<online>", Integer.toString(amount)), new ObjectSet("<server_status>", hqServer.getStatus().booleanValue() ? ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("statusChecker.online")) : ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("statusChecker.offline"))) });
/*     */           break;
/*     */         } 
/*     */       } 
/* 100 */       if (stack == null) {
/* 101 */         stack = ItemUtils.load(map, new ObjectSet[] { new ObjectSet("<server>", name), new ObjectSet("<online>", Integer.toString(amount)) });
/*     */       }
/* 103 */       if (BungeeUtils.getServerCount(name) >= Main.getInstance().getConfig().getInt("statusChecker.serverList." + name + ".maxPlayers") && Main.getInstance().getConfig().getInt("statusChecker.serverList." + name + ".maxPlayers") <= 0) {
/* 104 */         stack = ItemUtils.load(map, new ObjectSet[] { new ObjectSet("<server>", name), new ObjectSet("<online>", Integer.toString(amount)), new ObjectSet("<server_status>", ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("statusChecker.full"))) });
/*     */       }
/* 106 */       if (!map.containsKey("amount")) {
/* 107 */         stack.setAmount((amount < 1) ? 1 : Math.min(amount, 64));
/*     */       }
/* 109 */       if (map.containsKey("enchanted") && 
/* 110 */         map.get("enchanted").equals(Boolean.valueOf(true))) {
/* 111 */         EnchantGlow.addGlow(stack);
/*     */       }
/*     */       
/* 114 */       final ItemStack finalStack = stack;
/* 115 */       menu.addMenuItem(new MenuItem()
/*     */           {
/*     */             public void onClick(Player p, InventoryClickType click) {
/* 118 */               BungeeUtils.sendToServer(p, name);
/*     */             }
/*     */ 
/*     */             
/*     */             public ItemStack getItemStack() {
/* 123 */               return finalStack;
/*     */             }
/*     */           }slot);
/* 126 */       usedSlots.add(Integer.valueOf(slot));
/*     */     } 
/* 128 */     final ItemStack spacer = ItemUtils.load(Main.getInstance().getConfig().getConfigurationSection("options.rainbow-spacer").getValues(true), new ObjectSet[0]);
/* 129 */     for (int i = 0; i < menu.getInventory().getSize(); i++) {
/* 130 */       if (!usedSlots.contains(Integer.valueOf(i))) {
/* 131 */         menu.addMenuItem((MenuItem)new MenuItem.UnclickableMenuItem()
/*     */             {
/*     */               public ItemStack getItemStack() {
/* 134 */                 return spacer;
/*     */               }
/*     */             },  i);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void setup(Menu menu) {
/* 142 */     List<Integer> usedSlots = Lists.newArrayList();
/* 143 */     for (String key : Main.getInstance().getConfig().getConfigurationSection("gui.selector.items").getKeys(false)) {
/* 144 */       Map<String, Object> map = Main.getInstance().getConfig().getConfigurationSection("gui.selector.items." + key).getValues(true);
/* 145 */       final String name = map.containsKey("server-name") ? map.get("server-name").toString() : null;
/* 146 */       int amount = BungeeUtils.getServerCount(name);
/* 147 */       ItemStack stack = null;
/* 148 */       for (HQServer hqServer : (Main.getInstance()).serverList) {
/* 149 */         if (hqServer.getServerName().equals(name)) {
/* 150 */           stack = ItemUtils.load(map, new ObjectSet[] { new ObjectSet("<server>", name), new ObjectSet("<online>", Integer.toString(amount)), new ObjectSet("<server_status>", hqServer.getStatus().booleanValue() ? ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("statusChecker.online")) : ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("statusChecker.offline"))) });
/*     */           break;
/*     */         } 
/*     */       } 
/* 154 */       if (stack == null) {
/* 155 */         stack = ItemUtils.load(map, new ObjectSet[] { new ObjectSet("<server>", name), new ObjectSet("<online>", Integer.toString(amount)) });
/*     */       }
/* 157 */       if (BungeeUtils.getServerCount(name) >= Main.getInstance().getConfig().getInt("statusChecker.serverList." + name + ".maxPlayers") && Main.getInstance().getConfig().getInt("statusChecker.serverList." + name + ".maxPlayers") <= 0) {
/* 158 */         stack = ItemUtils.load(map, new ObjectSet[] { new ObjectSet("<server>", name), new ObjectSet("<online>", Integer.toString(amount)), new ObjectSet("<server_status>", ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("statusChecker.full"))) });
/*     */       }
/* 160 */       if (!map.containsKey("amount")) {
/* 161 */         stack.setAmount((amount < 1) ? 1 : Math.min(amount, 64));
/*     */       }
/* 163 */       if (map.containsKey("enchanted") && 
/* 164 */         map.get("enchanted").equals(Boolean.valueOf(true))) {
/* 165 */         EnchantGlow.addGlow(stack);
/*     */       }
/*     */       
/* 168 */       int slot = map.containsKey("slot") ? Integer.parseInt(map.get("slot").toString()) : menu.getInventory().firstEmpty();
/* 169 */       final ItemStack finalStack = stack;
/* 170 */       menu.addMenuItem(new MenuItem()
/*     */           {
/*     */             public void onClick(Player p, InventoryClickType click) {
/* 173 */               BungeeUtils.sendToServer(p, name);
/*     */             }
/*     */ 
/*     */             
/*     */             public ItemStack getItemStack() {
/* 178 */               return finalStack;
/*     */             }
/*     */           }slot);
/* 181 */       usedSlots.add(Integer.valueOf(slot));
/*     */     } 
/* 183 */     final ItemStack spacer = ItemUtils.load(Main.getInstance().getConfig().getConfigurationSection("options.rainbow-spacer").getValues(true), new ObjectSet[0]);
/* 184 */     for (int i = 0; i < menu.getInventory().getSize(); i++) {
/* 185 */       if (!usedSlots.contains(Integer.valueOf(i)))
/* 186 */         menu.addMenuItem((MenuItem)new MenuItem.UnclickableMenuItem()
/*     */             {
/*     */               public ItemStack getItemStack() {
/* 189 */                 return spacer;
/*     */               }
/*     */             },  i); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/benjaminmeyer/Downloads/HQHub.jar!/com/primalgamesllc/hqhub/guis/ServerSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */