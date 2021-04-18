/*    */ package com.primalgamesllc.hqhub.utils;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.primalgamesllc.hqhub.Main;
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.messaging.PluginMessageListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BungeeUtils
/*    */   implements PluginMessageListener
/*    */ {
/* 21 */   private static Map<String, Integer> serverList = Maps.newHashMap();
/* 22 */   private static List<String> SERVERS = Main.getInstance().getConfig().getStringList("servers");
/*    */ 
/*    */   
/*    */   public static void sendToServer(Player p, String targetServer) {
/* 26 */     ByteArrayOutputStream b = new ByteArrayOutputStream();
/* 27 */     DataOutputStream out = new DataOutputStream(b);
/*    */     try {
/* 29 */       out.writeUTF("Connect");
/* 30 */       out.writeUTF(targetServer);
/* 31 */     } catch (Exception ignore) {
/* 32 */       ignore.printStackTrace();
/*    */     } 
/* 34 */     p.sendMessage(Main.getInstance().format("connecting").replace("<name>", targetServer));
/* 35 */     Main.getInstance().playSound(p, "connecting");
/* 36 */     p.sendPluginMessage((Plugin)Main.getInstance(), "BungeeCord", b.toByteArray());
/*    */   }
/*    */   
/*    */   public static void queueUpdate(Player p) {
/* 40 */     for (String serverName : SERVERS) {
/* 41 */       ByteArrayOutputStream b = new ByteArrayOutputStream();
/* 42 */       DataOutputStream out = new DataOutputStream(b);
/*    */       try {
/* 44 */         out.writeUTF("PlayerCount");
/* 45 */         out.writeUTF(serverName);
/* 46 */       } catch (Exception exception) {}
/*    */       
/* 48 */       p.sendPluginMessage((Plugin)Main.getInstance(), "BungeeCord", b.toByteArray());
/*    */     } 
/*    */   }
/*    */   
/*    */   public static int getServerCount(String serverName) {
/* 53 */     if (serverList.get(serverName) != null) {
/* 54 */       return ((Integer)serverList.get(serverName)).intValue();
/*    */     }
/* 56 */     return 0;
/*    */   }
/*    */   
/*    */   public void onPluginMessageReceived(String channel, Player player, byte[] message) {
/* 60 */     if (!channel.equals("BungeeCord")) {
/*    */       return;
/*    */     }
/*    */     try {
/* 64 */       DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
/* 65 */       String command = in.readUTF();
/* 66 */       if (command.equals("PlayerCount")) {
/* 67 */         String server = in.readUTF();
/* 68 */         int playerCount = in.readInt();
/* 69 */         serverList.put(server, Integer.valueOf(playerCount));
/*    */       } 
/* 71 */     } catch (Exception exception) {}
/*    */   }
/*    */ }


