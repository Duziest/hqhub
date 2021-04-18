/*    */ package com.primalgamesllc.hqhub.hooks;
/*    */ import be.maximvdw.placeholderapi.PlaceholderAPI;
/*    */ import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
/*    */ import be.maximvdw.placeholderapi.PlaceholderReplacer;
/*    */ import com.primalgamesllc.hqhub.Main;
/*    */ import com.primalgamesllc.hqhub.objects.HQServer;
/*    */ import com.primalgamesllc.hqhub.utils.BungeeUtils;
/*    */ import java.util.Iterator;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class MvDWPlaceholderHook {
/*    */   public static void registerPlaceholder(final String serverName) {
/* 14 */     System.out.print("Registering MvDWPlaceholderAPI placeholder for server " + serverName);
/* 15 */     PlaceholderAPI.registerPlaceholder((Plugin)Main.getInstance(), "hqhub_" + serverName, new PlaceholderReplacer()
/*    */         {
/*    */           public String onPlaceholderReplace(PlaceholderReplaceEvent placeholderReplaceEvent)
/*    */           {
/* 19 */             Iterator<HQServer> iterator = (Main.getInstance()).serverList.iterator(); if (iterator.hasNext()) { HQServer hqServer = iterator.next();
/* 20 */               if (hqServer.getServerName().equalsIgnoreCase(serverName)) {
/* 21 */                 if (BungeeUtils.getServerCount(serverName) >= Main.getInstance().getConfig().getInt("statusChecker.serverList." + serverName + ".maxPlayers")) {
/* 22 */                   return ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("statusChecker.full"));
/*    */                 }
/* 24 */                 return hqServer.getStatus().booleanValue() ? ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("statusChecker.online")) : ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("statusChecker.offline"));
/*    */               } 
/* 26 */               return "&c&lServer not found."; }
/*    */             
/* 28 */             return "&c&lUnknown Error Occured";
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              /Users/benjaminmeyer/Downloads/HQHub.jar!/com/primalgamesllc/hqhub/hooks/MvDWPlaceholderHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */