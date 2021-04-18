/*     */ package com.primalgamesllc.hqhub.listener;
/*     */ import com.primalgamesllc.hqhub.Main;
/*     */ import com.primalgamesllc.hqhub.utils.ItemUtils;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.block.BlockBreakEvent;
/*     */ import org.bukkit.event.block.BlockPlaceEvent;
/*     */ import org.bukkit.event.block.EntityBlockFormEvent;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityInteractEvent;
/*     */ import org.bukkit.event.entity.FoodLevelChangeEvent;
/*     */ import org.bukkit.event.hanging.HangingBreakByEntityEvent;
/*     */ import org.bukkit.event.player.PlayerBedEnterEvent;
/*     */ import org.bukkit.event.player.PlayerBucketEmptyEvent;
/*     */ import org.bukkit.event.player.PlayerBucketFillEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEntityEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerKickEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class BasicListeners implements Listener {
/*     */   @EventHandler
/*     */   public void onDamage(EntityDamageEvent e) {
/*  28 */     e.setCancelled(true);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerDrop(PlayerDropItemEvent e) {
/*  33 */     e.setCancelled(true);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerJoin(PlayerJoinEvent e) {
/*  38 */     Player p = e.getPlayer();
/*  39 */     e.setJoinMessage((String)null);
/*  40 */     p.setFoodLevel(20);
/*  41 */     p.setHealth(20.0D);
/*  42 */     p.setFireTicks(0);
/*  43 */     p.setExp(0.0F);
/*  44 */     p.setLevel(0);
/*  45 */     p.setGameMode(GameMode.ADVENTURE);
/*  46 */     p.getInventory().clear();
/*  47 */     p.getInventory().setArmorContents((ItemStack[])null);
/*  48 */     p.teleport(Main.getInstance().getEssSpawn().getSpawn("default"));
/*  49 */     ItemStack servers = ItemUtils.load(Main.getInstance().getConfig().getConfigurationSection("options.server-selector").getValues(true), new com.primalgamesllc.hqhub.utils.ObjectSet[0]);
/*  50 */     ItemStack lobbies = ItemUtils.load(Main.getInstance().getConfig().getConfigurationSection("options.lobby-selector").getValues(true), new com.primalgamesllc.hqhub.utils.ObjectSet[0]);
/*  51 */     p.getInventory().setItem(Main.getInstance().getConfig().getInt("gui.selector.slot"), servers);
/*  52 */     p.getInventory().setItem(Main.getInstance().getConfig().getInt("gui.lobby.slot"), lobbies);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerQuit(PlayerQuitEvent e) {
/*  57 */     e.setQuitMessage((String)null);
/*  58 */     Player p = e.getPlayer();
/*  59 */     if (p.hasMetadata("ANIMATED_GUI")) {
/*  60 */       p.removeMetadata("ANIMATED_GUI", (Plugin)Main.getInstance());
/*     */     }
/*  62 */     if (p.hasMetadata("INV_UPDATE")) {
/*  63 */       p.removeMetadata("INV_UPDATE", (Plugin)Main.getInstance());
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerKick(PlayerKickEvent e) {
/*  69 */     e.setLeaveMessage((String)null);
/*  70 */     Player p = e.getPlayer();
/*  71 */     if (p.hasMetadata("ANIMATED_GUI")) {
/*  72 */       p.removeMetadata("ANIMATED_GUI", (Plugin)Main.getInstance());
/*     */     }
/*  74 */     if (p.hasMetadata("INV_UPDATE")) {
/*  75 */       p.removeMetadata("INV_UPDATE", (Plugin)Main.getInstance());
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerPickup(PlayerPickupItemEvent e) {
/*  81 */     e.setCancelled(true);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onWeatherChange(WeatherChangeEvent e) {
/*  86 */     e.setCancelled(true);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onChangeXP(PlayerExpChangeEvent e) {
/*  91 */     e.setAmount(0);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onEntitySpawn(CreatureSpawnEvent e) {
/*  96 */     if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
/*  97 */       e.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onFoodChange(FoodLevelChangeEvent e) {
/* 103 */     if (e.getEntity() instanceof Player) {
/* 104 */       Player p = (Player)e.getEntity();
/* 105 */       e.setFoodLevel(20);
/* 106 */       p.setSaturation(20.0F);
/* 107 */       p.setExhaustion(20.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onBedEnter(PlayerBedEnterEvent e) {
/* 113 */     Player p = e.getPlayer();
/* 114 */     if (!p.hasPermission("hub.bedenter")) {
/* 115 */       e.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onForm(BlockFormEvent e) {
/* 121 */     e.setCancelled(true);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onEntityForm(EntityBlockFormEvent e) {
/* 126 */     if (e.getEntity() instanceof Player) {
/* 127 */       Player p = (Player)e.getEntity();
/* 128 */       if (p.getGameMode() != GameMode.CREATIVE || !p.hasPermission("hub.build")) {
/* 129 */         e.setCancelled(true);
/*     */       }
/*     */     } else {
/* 132 */       e.setCancelled(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onBuild(BlockPlaceEvent e) {
/* 138 */     Player p = e.getPlayer();
/* 139 */     if (!p.hasPermission("hub.build")) {
/* 140 */       e.setCancelled(true);
/* 141 */       e.setBuild(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onBreak(BlockBreakEvent e) {
/* 147 */     Player p = e.getPlayer();
/* 148 */     if (!p.hasPermission("hub.break")) {
/* 149 */       e.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onHangingBreak(HangingBreakByEntityEvent e) {
/* 155 */     if (e.getRemover().getType() == EntityType.PLAYER) {
/* 156 */       Entity entity = e.getRemover();
/* 157 */       Player p = (Player)entity;
/* 158 */       if (!p.hasPermission("hub.break")) {
/* 159 */         e.setCancelled(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onHangingPlace(HangingPlaceEvent e) {
/* 166 */     Player p = e.getPlayer();
/* 167 */     if (!p.hasPermission("hub.build")) {
/* 168 */       e.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onBucketEmpty(PlayerBucketEmptyEvent e) {
/* 174 */     Player p = e.getPlayer();
/* 175 */     if (!p.hasPermission("hub.bucketempty")) {
/* 176 */       e.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onBucketFill(PlayerBucketFillEvent e) {
/* 182 */     Player p = e.getPlayer();
/* 183 */     if (!p.hasPermission("hub.bucketfill")) {
/* 184 */       e.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInteractEntity(PlayerInteractEntityEvent e) {
/* 190 */     Player p = e.getPlayer();
/* 191 */     if (e.getRightClicked() instanceof org.bukkit.entity.ItemFrame && !p.hasPermission("hub.itemframe")) {
/* 192 */       e.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onEntityInteract(EntityInteractEvent e) {
/* 198 */     if (e.getBlock().getType() == Material.SOIL) {
/* 199 */       if (e.getEntity() instanceof Player) {
/* 200 */         Player p = (Player)e.getEntity();
/* 201 */         if (!p.hasPermission("hub.farmland")) {
/* 202 */           e.setCancelled(true);
/*     */         }
/*     */       } else {
/* 205 */         e.setCancelled(true);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInteractAt(PlayerInteractEntityEvent e) {
/* 212 */     Player p = e.getPlayer();
/* 213 */     if (e.getRightClicked() instanceof org.bukkit.entity.ItemFrame && !p.hasPermission("hub.itemframe")) {
/* 214 */       e.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onEntityDamage(EntityDamageByEntityEvent e) {
/* 220 */     Entity en = e.getEntity();
/* 221 */     Entity damager = e.getDamager();
/* 222 */     if (en instanceof org.bukkit.entity.ItemFrame) {
/* 223 */       if (damager instanceof Player) {
/* 224 */         Player p = (Player)e.getDamager();
/* 225 */         if (!p.hasPermission("hub.itemframe")) {
/* 226 */           e.setCancelled(true);
/*     */         }
/*     */       } else {
/* 229 */         e.setCancelled(true);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerInteract(PlayerInteractEvent e) {
/* 236 */     Player p = e.getPlayer();
/* 237 */     if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
/* 238 */       ItemStack servers = ItemUtils.load(Main.getInstance().getConfig().getConfigurationSection("options.server-selector").getValues(true), new com.primalgamesllc.hqhub.utils.ObjectSet[0]);
/* 239 */       ItemStack lobbies = ItemUtils.load(Main.getInstance().getConfig().getConfigurationSection("options.lobby-selector").getValues(true), new com.primalgamesllc.hqhub.utils.ObjectSet[0]);
/* 240 */       if (ItemUtils.isSimilar(servers, e.getItem())) {
/* 241 */         ServerSelector.openInv(p);
/* 242 */       } else if (ItemUtils.isSimilar(lobbies, e.getItem())) {
/* 243 */         LobbySelector.openInv(p);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }
