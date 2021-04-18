/*     */ package com.primalgamesllc.hqhub.utils;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.primalgamesllc.hqhub.Main;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.WordUtils;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.EnchantmentStorageMeta;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.SkullMeta;
/*     */ 
/*     */ public class ItemUtils
/*     */ {
/*     */   private static List<String> replaceColors(List<String> list, ObjectSet... placeholders) {
/*  19 */     List<String> listTemp = Lists.newArrayList();
/*  20 */     for (String s : list) {
/*  21 */       s = ChatColor.translateAlternateColorCodes('&', s);
/*  22 */       if (s.contains("%date_")) {
/*  23 */         long dateMillis = Long.parseLong(StringUtils.substringBetween(s, "%date_", "%"));
/*  24 */         listTemp.add(s.replace("%date_" + Long.toString(dateMillis) + "%", DateUtil.formatDateDiff(dateMillis))); continue;
/*     */       } 
/*  26 */       for (ObjectSet placeholder : placeholders) {
/*  27 */         s = s.replace(placeholder.getA().toString(), placeholder.getB().toString());
/*     */       }
/*  29 */       listTemp.add(s);
/*     */     } 
/*     */     
/*  32 */     return listTemp;
/*     */   }
/*     */   
/*     */   public static ItemStack load(Map<String, Object> keys, ObjectSet... placeholders) {
/*     */     try {
/*  37 */       ItemStack stack = null;
/*  38 */       String item = "";
/*  39 */       if (keys.containsKey("item")) {
/*  40 */         if (keys.get("item") instanceof List) {
/*  41 */           List<String> list = (List<String>)keys.get("item");
/*  42 */           item = list.get(keys.containsKey("index") ? ((Integer)keys.get("index")).intValue() : ThreadLocalRandom.current().nextInt(list.size()));
/*     */         } else {
/*  44 */           item = keys.get("item").toString();
/*     */         } 
/*     */       }
/*  47 */       if (keys.containsKey("item") && keys.containsKey("amount")) {
/*  48 */         stack = Main.getInstance().getEss().getItemDb().get(item, Integer.parseInt(keys.get("amount").toString()));
/*     */       } else {
/*  50 */         stack = Main.getInstance().getEss().getItemDb().get(item, 1);
/*     */       } 
/*  52 */       ItemMeta meta = stack.getItemMeta();
/*  53 */       if (keys.containsKey("name")) {
/*  54 */         String name = ChatColor.translateAlternateColorCodes('&', keys.get("name").toString());
/*  55 */         name = name.replace("<name>", WordUtils.capitalizeFully(stack.getType().name().replace("_", " ")));
/*  56 */         for (ObjectSet placeholder : placeholders) {
/*  57 */           name = name.replace(placeholder.getA().toString(), placeholder.getB().toString());
/*     */         }
/*  59 */         meta.setDisplayName(name);
/*     */       } 
/*  61 */       if (keys.containsKey("owner")) {
/*  62 */         String owner = ChatColor.translateAlternateColorCodes('&', keys.get("owner").toString());
/*  63 */         for (ObjectSet placeholder : placeholders) {
/*  64 */           owner = owner.replace(placeholder.getA().toString(), placeholder.getB().toString());
/*     */         }
/*  66 */         ((SkullMeta)meta).setOwner(owner);
/*     */       } 
/*  68 */       if (keys.containsKey("lore")) {
/*  69 */         List<String> lore = replaceColors((List<String>)keys.get("lore"), placeholders);
/*  70 */         meta.setLore(lore);
/*     */       } 
/*  72 */       if (keys.containsKey("enchants")) {
/*  73 */         List<String> enchants = (List<String>)keys.get("enchants");
/*  74 */         for (String s : enchants) {
/*  75 */           String[] parts = s.split(":");
/*  76 */           if (meta instanceof EnchantmentStorageMeta) {
/*  77 */             ((EnchantmentStorageMeta)meta).addStoredEnchant(EnchantUtils.argsToEnchant(parts[0]), Integer.parseInt(parts[1]), true); continue;
/*     */           } 
/*  79 */           meta.addEnchant(EnchantUtils.argsToEnchant(parts[0]), Integer.parseInt(parts[1]), true);
/*     */         } 
/*     */       } 
/*     */       
/*  83 */       stack.setItemMeta(meta);
/*  84 */       return stack;
/*  85 */     } catch (Exception ignore) {
/*  86 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isSimilar(ItemStack item, ItemStack compare) {
/*  91 */     if (item == null || compare == null) {
/*  92 */       return false;
/*     */     }
/*  94 */     if (item == compare) {
/*  95 */       return true;
/*     */     }
/*  97 */     if (item.getTypeId() != compare.getTypeId()) {
/*  98 */       return false;
/*     */     }
/* 100 */     if (item.getDurability() != compare.getDurability()) {
/* 101 */       return false;
/*     */     }
/* 103 */     if (item.hasItemMeta() != compare.hasItemMeta()) {
/* 104 */       return false;
/*     */     }
/* 106 */     if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
/* 107 */       if (item.getItemMeta().hasDisplayName() != compare.getItemMeta().hasDisplayName()) {
/* 108 */         return false;
/*     */       }
/* 110 */       if (!item.getItemMeta().getDisplayName().equals(compare.getItemMeta().getDisplayName())) {
/* 111 */         return false;
/*     */       }
/*     */     } 
/* 114 */     return true;
/*     */   }
/*     */ }

