/*    */ package com.primalgamesllc.hqhub.tasks;
/*    */ 
/*    */ import com.primalgamesllc.hqhub.Main;
/*    */ import com.primalgamesllc.hqhub.objects.HQServer;
/*    */ import java.io.IOException;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.net.Socket;
/*    */ import java.net.UnknownHostException;
/*    */ import org.bukkit.configuration.ConfigurationSection;
/*    */ 
/*    */ public class ServerUpdateTask
/*    */   implements Runnable
/*    */ {
/*    */   private Main plugin;
/*    */   
/*    */   public ServerUpdateTask(Main plugin) {
/* 17 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 22 */     (Main.getInstance()).serverList.clear();
/* 23 */     for (String key : Main.getInstance().getConfig().getConfigurationSection("statusChecker.serverList").getKeys(false)) {
/* 24 */       ConfigurationSection section = Main.getInstance().getConfig().getConfigurationSection("statusChecker.serverList." + key);
/* 25 */       String serverName = key;
/* 26 */       String ip = section.getString("ip");
/* 27 */       String port = section.getString("port");
/* 28 */       Boolean online = Boolean.valueOf(false);
/*    */       try {
/* 30 */         Socket s = new Socket();
/* 31 */         s.connect(new InetSocketAddress(ip, Integer.parseInt(port)), 20);
/* 32 */         s.close();
/* 33 */         online = Boolean.valueOf(true);
/* 34 */       } catch (UnknownHostException e) {
/* 35 */         online = Boolean.valueOf(false);
/* 36 */       } catch (IOException e) {
/* 37 */         online = Boolean.valueOf(false);
/*    */       } 
/* 39 */       HQServer server = new HQServer(online, serverName);
/* 40 */       (Main.getInstance()).serverList.add(server);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/benjaminmeyer/Downloads/HQHub.jar!/com/primalgamesllc/hqhub/tasks/ServerUpdateTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */