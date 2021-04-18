/*    */ package com.primalgamesllc.hqhub.hooks;
/*    */ 
/*    */ import com.primalgamesllc.hqhub.Main;
/*    */ import com.primalgamesllc.hqhub.objects.HQServer;
/*    */ import com.primalgamesllc.hqhub.utils.BungeeUtils;
/*    */ import java.util.Iterator;
/*    */ import me.clip.placeholderapi.external.EZPlaceholderHook;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class PlaceholderAPIHook
/*    */   extends EZPlaceholderHook {
/*    */   public PlaceholderAPIHook(Main plugin) {
/* 15 */     super((Plugin)plugin, "hqhub");
/* 16 */     this.plugin = plugin;
/*    */   }
/*    */   private Main plugin;
/*    */   
/*    */   public String onPlaceholderRequest(Player p, String identifier) {
/* 21 */     Iterator<HQServer> iterator = (Main.getInstance()).serverList.iterator(); if (iterator.hasNext()) { HQServer hqServer = iterator.next();
/* 22 */       if (hqServer.getServerName().equalsIgnoreCase(identifier)) {
/* 23 */         if (BungeeUtils.getServerCount(identifier) >= Main.getInstance().getConfig().getInt("statusChecker.serverList." + identifier + ".maxPlayers")) {
/* 24 */           return ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("statusChecker.full"));
/*    */         }
/* 26 */         return hqServer.getStatus().booleanValue() ? ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("statusChecker.online")) : ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("statusChecker.offline"));
/*    */       } 
/* 28 */       return "&c&lServer not found."; }
/*    */     
/* 30 */     return "&c&lUnknown Error Occured";
/*    */   }
/*    */ }


/* Location:              /Users/benjaminmeyer/Downloads/HQHub.jar!/com/primalgamesllc/hqhub/hooks/PlaceholderAPIHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */