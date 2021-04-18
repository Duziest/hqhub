/*     */ package com.primalgamesllc.hqhub;
/*     */ 
/*     */ import com.earth2me.essentials.Essentials;
/*     */ import com.earth2me.essentials.spawn.EssentialsSpawn;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.primalgamesllc.hqhub.hooks.MvDWPlaceholderHook;
/*     */ import com.primalgamesllc.hqhub.hooks.PlaceholderAPIHook;
/*     */ import com.primalgamesllc.hqhub.listener.BasicListeners;
/*     */ import com.primalgamesllc.hqhub.menus.MenuAPI;
/*     */ import com.primalgamesllc.hqhub.objects.HQServer;
/*     */ import com.primalgamesllc.hqhub.tasks.ServerUpdateTask;
/*     */ import com.primalgamesllc.hqhub.utils.BungeeUtils;
/*     */ import com.primalgamesllc.hqhub.utils.EnchantGlow;
/*     */ import com.primalgamesllc.hqhub.utils.ObjectSet;
/*     */ import com.primalgamesllc.hqhub.utils.Sounds;
/*     */ import java.io.File;
/*     */ import java.nio.file.Files;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ import org.bukkit.plugin.messaging.PluginMessageListener;
/*     */ 
/*     */ 
/*     */ public class Main
/*     */   extends JavaPlugin
/*     */ {
/*  33 */   public List<HQServer> serverList = new ArrayList<>();
/*     */   private Essentials ess;
/*     */   private EssentialsSpawn essSpawn;
/*     */   private List<ObjectSet> hubs;
/*     */   private int configVersion;
/*     */   
/*     */   public Main() {
/*  40 */     this.configVersion = 2;
/*     */   }
/*     */   
/*     */   public static Main getInstance() {
/*  44 */     return (Main)JavaPlugin.getPlugin(Main.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLoad() {
/*  49 */     EnchantGlow.getGlow();
/*     */   }
/*     */   
/*     */   public void onEnable() {
/*  53 */     if (Files.notExists((new File(getDataFolder(), "config.yml")).toPath(), new java.nio.file.LinkOption[0])) {
/*  54 */       saveDefaultConfig();
/*  55 */     } else if (!getConfig().isInt("version") || getConfig().getInt("version", 0) < this.configVersion) {
/*  56 */       getConfig().set("version", Integer.valueOf(this.configVersion));
/*  57 */       getConfig().options().copyDefaults(true);
/*  58 */       saveConfig();
/*     */     } 
/*  60 */     this.hubs = Lists.newArrayList();
/*  61 */     for (int i = 0; i < getConfig().getStringList("hubs").size(); i++) {
/*  62 */       String hub = getConfig().getStringList("hubs").get(i);
/*  63 */       if (!hub.contains(";") || (hub.split(";")).length < 2) {
/*  64 */         System.out.println("Error parsing the string 'Hub" + i + "'");
/*     */       } else {
/*  66 */         this.hubs.add(new ObjectSet(hub.split(";")[0], Integer.valueOf(Integer.parseInt(hub.split(";")[1]))));
/*     */       } 
/*     */     } 
/*  69 */     setEss((Essentials)Bukkit.getPluginManager().getPlugin("Essentials"));
/*  70 */     setEssSpawn((EssentialsSpawn)Bukkit.getPluginManager().getPlugin("EssentialsSpawn"));
/*  71 */     getServer().getMessenger().registerOutgoingPluginChannel((Plugin)this, "BungeeCord");
/*  72 */     getServer().getMessenger().registerIncomingPluginChannel((Plugin)this, "BungeeCord", (PluginMessageListener)new BungeeUtils());
/*  73 */     Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)this, new Runnable()
/*     */         {
/*     */           public void run() {
/*  76 */             if (Bukkit.getOnlinePlayers().size() >= 1) {
/*  77 */               BungeeUtils.queueUpdate(((Player[])Bukkit.getOnlinePlayers().toArray((T[])new Player[Bukkit.getOnlinePlayers().size()]))[0]);
/*     */             }
/*     */           }
/*     */         },  20L, 200L);
/*  81 */     PluginManager pm = Bukkit.getPluginManager();
/*  82 */     int serverUpdateInterval = getConfig().getInt("statusChecker.updateInterval");
/*  83 */     getServer().getScheduler().runTaskTimer((Plugin)this, (Runnable)new ServerUpdateTask(this), 20L, serverUpdateInterval);
/*  84 */     pm.registerEvents((Listener)new BasicListeners(), (Plugin)this);
/*  85 */     pm.registerEvents((Listener)MenuAPI.getInstance(), (Plugin)this);
/*  86 */     if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
/*  87 */       System.out.print("MVdWPlaceholderAPI Found, registering placeholders!");
/*  88 */       for (String serverKey : getConfig().getConfigurationSection("statusChecker.serverList").getKeys(false)) {
/*  89 */         MvDWPlaceholderHook.registerPlaceholder(serverKey);
/*     */       }
/*     */     } else {
/*  92 */       System.out.print("MVdWPlaceholderAPI not found, placeholders for MVdW's plugins will not work.");
/*     */     } 
/*  94 */     if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
/*  95 */       System.out.print("PlaceholderAPI Found, registering placeholders!");
/*  96 */       for (String serverKey : getConfig().getConfigurationSection("statusChecker.serverList").getKeys(false)) {
/*  97 */         (new PlaceholderAPIHook(this)).hook();
/*     */       }
/*     */     } else {
/* 100 */       System.out.print("PlaceholderAPI not found, placeholders for PlaceholderAPI and plugins which use it will not work.");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onDisable() {
/* 105 */     setHubs((List<ObjectSet>)null);
/* 106 */     setEss((Essentials)null);
/* 107 */     setEssSpawn((EssentialsSpawn)null);
/* 108 */     for (Player p : Bukkit.getOnlinePlayers()) {
/* 109 */       if (p.getOpenInventory() != null && p.getOpenInventory().getTopInventory().getHolder() instanceof com.primalgamesllc.hqhub.menus.Menu) {
/* 110 */         p.closeInventory();
/*     */       }
/* 112 */       if (p.hasMetadata("ANIMATED_GUI")) {
/* 113 */         p.removeMetadata("ANIMATED_GUI", (Plugin)this);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public String format(String msg) {
/* 119 */     String return_type = null;
/* 120 */     if (getConfig().getString("messages." + msg, (String)null) != null) {
/* 121 */       return_type = getConfig().getString("messages." + msg);
/* 122 */     } else if (getConfig().getDefaults().isString("messages." + msg)) {
/* 123 */       return_type = getConfig().getDefaults().getString("messages." + msg);
/* 124 */       getConfig().set("messages." + msg, return_type);
/* 125 */       saveConfig();
/*     */     } 
/* 127 */     return_type = ChatColor.translateAlternateColorCodes('&', return_type);
/* 128 */     return return_type;
/*     */   }
/*     */   
/*     */   public void playSound(Player p, String str) {
/* 132 */     String return_type = null;
/* 133 */     if (getConfig().getString("sounds." + str, (String)null) != null) {
/* 134 */       return_type = getConfig().getString("sounds." + str);
/* 135 */     } else if (getConfig().getDefaults().isString("sounds." + str)) {
/* 136 */       return_type = getConfig().getDefaults().getString("sounds." + str);
/* 137 */       getConfig().set("sounds." + str, return_type);
/* 138 */       saveConfig();
/*     */     } 
/* 140 */     if (return_type == null || return_type.isEmpty()) {
/*     */       return;
/*     */     }
/* 143 */     String[] parts = return_type.split(";");
/* 144 */     p.playSound(p.getLocation(), Sounds.valueOf(parts[0]).bukkitSound(), (float)Double.parseDouble(parts[1]), (float)Double.parseDouble(parts[2]));
/*     */   }
/*     */   
/*     */   public Essentials getEss() {
/* 148 */     return this.ess;
/*     */   }
/*     */   
/*     */   public void setEss(Essentials ess) {
/* 152 */     this.ess = ess;
/*     */   }
/*     */   
/*     */   public EssentialsSpawn getEssSpawn() {
/* 156 */     return this.essSpawn;
/*     */   }
/*     */   
/*     */   public void setEssSpawn(EssentialsSpawn essSpawn) {
/* 160 */     this.essSpawn = essSpawn;
/*     */   }
/*     */   
/*     */   public List<ObjectSet> getHubs() {
/* 164 */     return this.hubs;
/*     */   }
/*     */   
/*     */   public void setHubs(List<ObjectSet> hubs) {
/* 168 */     this.hubs = hubs;
/*     */   }
/*     */ }
