/*    */ package com.primalgamesllc.hqhub.guis;
/*    */ 
/*    */ import com.primalgamesllc.hqhub.Main;
/*    */ import com.primalgamesllc.hqhub.menus.InventoryClickType;
/*    */ import com.primalgamesllc.hqhub.menus.Menu;
/*    */ import com.primalgamesllc.hqhub.menus.MenuAPI;
/*    */ import com.primalgamesllc.hqhub.menus.MenuItem;
/*    */ import com.primalgamesllc.hqhub.utils.BungeeUtils;
/*    */ import com.primalgamesllc.hqhub.utils.ItemUtils;
/*    */ import com.primalgamesllc.hqhub.utils.ObjectSet;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class LobbySelector
/*    */ {
/*    */   public static void openInv(Player p) {
/* 21 */     int size = fit(Main.getInstance().getHubs().size()) / 9;
/* 22 */     if (Main.getInstance().getConfig().isInt("gui.lobby.size") && Main.getInstance().getConfig().getInt("gui.lobby.size") > 0) {
/* 23 */       size = Main.getInstance().getConfig().getInt("gui.lobby.size");
/*    */     }
/* 25 */     Menu menu = MenuAPI.getInstance().createMenu(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("gui.lobby.name")), size);
/* 26 */     List<ObjectSet> hubs = Main.getInstance().getHubs();
/* 27 */     for (int i = 0; i < hubs.size(); i++) {
/* 28 */       ObjectSet hub = hubs.get(i);
/* 29 */       final String name = hub.getA().toString();
/* 30 */       final int port = Integer.parseInt(hub.getB().toString());
/* 31 */       final int index = i;
/* 32 */       menu.addMenuItem(new MenuItem()
/*    */           {
/*    */             public void onClick(Player p, InventoryClickType click) {
/* 35 */               if (Bukkit.getPort() == port) {
/* 36 */                 p.sendMessage(Main.getInstance().format("already-connected").replace("<name>", name));
/* 37 */                 Main.getInstance().playSound(p, "already-connected");
/*    */               } else {
/* 39 */                 BungeeUtils.sendToServer(p, name);
/*    */               } 
/*    */             }
/*    */ 
/*    */             
/*    */             public ItemStack getItemStack() {
/* 45 */               return LobbySelector.getHubItem(name, port, index);
/*    */             }
/* 47 */           }menu.getInventory().firstEmpty());
/*    */     } 
/* 49 */     menu.openMenu(p);
/* 50 */     Main.getInstance().playSound(p, "open-lobby");
/*    */   }
/*    */   
/*    */   private static ItemStack getHubItem(String hub, int port, int index) {
/* 54 */     ItemStack stack = null;
/* 55 */     if (Bukkit.getPort() == port) {
/* 56 */       Map<String, Object> map = Main.getInstance().getConfig().getConfigurationSection("options.hub-already-icon").getValues(true);
/* 57 */       stack = ItemUtils.load(map, new ObjectSet[] { new ObjectSet("<hub>", hub), new ObjectSet("<index>", Integer.toString(index + 1)), new ObjectSet("<online>", Integer.toString(Bukkit.getOnlinePlayers().size())) });
/* 58 */       if (!map.containsKey("amount")) {
/* 59 */         int amount = Bukkit.getOnlinePlayers().size();
/* 60 */         stack.setAmount((amount < 1) ? 1 : Math.min(amount, 64));
/*    */       } 
/*    */     } else {
/* 63 */       int amount2 = BungeeUtils.getServerCount(hub);
/* 64 */       Map<String, Object> map2 = Main.getInstance().getConfig().getConfigurationSection("options.hub-icon").getValues(true);
/*    */       
/* 66 */       if (!map2.containsKey("amount")) {
/* 67 */         stack.setAmount((amount2 < 1) ? 1 : Math.min(amount2, 64));
/*    */       }
/*    */     } 
/* 70 */     return stack;
/*    */   }
/*    */   
/*    */   private static int fit(int slots) {
/* 74 */     if (slots < 10) {
/* 75 */       return 9;
/*    */     }
/* 77 */     if (slots < 19) {
/* 78 */       return 18;
/*    */     }
/* 80 */     if (slots < 28) {
/* 81 */       return 27;
/*    */     }
/* 83 */     if (slots < 37) {
/* 84 */       return 36;
/*    */     }
/* 86 */     if (slots < 46) {
/* 87 */       return 45;
/*    */     }
/* 89 */     return 54;
/*    */   }
/*    */ }


/* Location:              /Users/benjaminmeyer/Downloads/HQHub.jar!/com/primalgamesllc/hqhub/guis/LobbySelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */