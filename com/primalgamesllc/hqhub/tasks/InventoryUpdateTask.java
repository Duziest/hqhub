/*    */ package com.primalgamesllc.hqhub.tasks;
/*    */ 
/*    */ import com.primalgamesllc.hqhub.Main;
/*    */ import com.primalgamesllc.hqhub.objects.HQServer;
/*    */ import com.primalgamesllc.hqhub.utils.BungeeUtils;
/*    */ import com.primalgamesllc.hqhub.utils.EnchantGlow;
/*    */ import com.primalgamesllc.hqhub.utils.ItemUtils;
/*    */ import com.primalgamesllc.hqhub.utils.ObjectSet;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ public class InventoryUpdateTask
/*    */   implements Runnable
/*    */ {
/*    */   private Inventory inv;
/* 23 */   private Map<Integer, List<Material>> itemList = new HashMap<>();
/*    */   
/* 25 */   private Map<Integer, Integer> nextItem = new HashMap<>();
/*    */   
/*    */   private Player player;
/*    */   
/*    */   private Map<String, Object> map;
/*    */   
/*    */   public InventoryUpdateTask(Player player, Inventory inv, Map<Integer, List<Material>> itemList, Map<String, Object> map) {
/* 32 */     this.inv = inv;
/* 33 */     this.itemList = itemList;
/* 34 */     this.player = player;
/* 35 */     this.map = map;
/*    */   }
/*    */   
/*    */   public void run() {
/* 39 */     if (!this.player.hasMetadata("INV_UPDATE")) {
/*    */       return;
/*    */     }
/* 42 */     for (Integer slot : this.itemList.keySet()) {
/* 43 */       Material material = Material.STONE;
/* 44 */       if (!this.nextItem.containsKey(slot)) {
/* 45 */         this.nextItem.put(slot, Integer.valueOf(1));
/* 46 */         material = ((List<Material>)this.itemList.get(slot)).get(0);
/*    */       } else {
/* 48 */         if (((Integer)this.nextItem.get(slot)).intValue() >= ((List)this.itemList.get(slot)).size()) {
/* 49 */           this.nextItem.remove(slot);
/* 50 */           this.nextItem.put(slot, Integer.valueOf(0));
/*    */         } 
/* 52 */         material = ((List<Material>)this.itemList.get(slot)).get(((Integer)this.nextItem.get(slot)).intValue());
/* 53 */         int currentIndex = ((Integer)this.nextItem.get(slot)).intValue();
/* 54 */         currentIndex++;
/* 55 */         if (currentIndex >= ((List)this.itemList.get(slot)).size()) {
/* 56 */           currentIndex = 0;
/*    */         }
/* 58 */         this.nextItem.remove(slot);
/* 59 */         this.nextItem.put(slot, Integer.valueOf(currentIndex));
/*    */       } 
/* 61 */       ItemStack stack = null;
/* 62 */       String name = this.map.containsKey("server-name") ? this.map.get("server-name").toString() : null;
/* 63 */       int amount = BungeeUtils.getServerCount(name);
/* 64 */       for (HQServer hqServer : (Main.getInstance()).serverList) {
/* 65 */         if (hqServer.getServerName().equals(name)) {
/* 66 */           stack = ItemUtils.load(this.map, new ObjectSet[] { new ObjectSet("<server>", name), new ObjectSet("<online>", Integer.toString(amount)), new ObjectSet("<server_status>", hqServer.getStatus().booleanValue() ? ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("statusChecker.online")) : ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("statusChecker.offline"))) });
/*    */           break;
/*    */         } 
/*    */       } 
/* 70 */       if (stack == null) {
/* 71 */         stack = ItemUtils.load(this.map, new ObjectSet[] { new ObjectSet("<server>", name), new ObjectSet("<online>", Integer.toString(amount)) });
/*    */       }
/* 73 */       if (BungeeUtils.getServerCount(name) >= Main.getInstance().getConfig().getInt("statusChecker.serverList." + name + ".maxPlayers") && Main.getInstance().getConfig().getInt("statusChecker.serverList." + name + ".maxPlayers") <= 0) {
/* 74 */         stack = ItemUtils.load(this.map, new ObjectSet[] { new ObjectSet("<server>", name), new ObjectSet("<online>", Integer.toString(amount)), new ObjectSet("<server_status>", ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("statusChecker.full"))) });
/*    */       }
/* 76 */       if (material != null) {
/* 77 */         stack.setType(material);
/*    */       }
/* 79 */       if (amount > 1) {
/* 80 */         if (amount <= 64) {
/* 81 */           stack.setAmount(amount);
/*    */         } else {
/* 83 */           stack.setAmount(64);
/*    */         } 
/*    */       }
/* 86 */       if (this.map.containsKey("enchanted") && 
/* 87 */         this.map.get("enchanted").equals(Boolean.valueOf(true))) {
/* 88 */         EnchantGlow.addGlow(stack);
/*    */       }
/*    */       
/* 91 */       this.inv.setItem(slot.intValue(), stack);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setItemList(Map<Integer, List<Material>> itemList) {
/* 96 */     this.itemList = itemList;
/*    */   }
/*    */ }

