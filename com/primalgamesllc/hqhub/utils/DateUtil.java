/*    */ package com.primalgamesllc.hqhub.utils;
/*    */ 
/*    */ import com.earth2me.essentials.I18n;
/*    */ import java.util.Calendar;
/*    */ import java.util.GregorianCalendar;
/*    */ 
/*    */ public class DateUtil
/*    */ {
/*    */   private static int dateDiff(int type, Calendar fromDate, Calendar toDate, boolean future) {
/* 10 */     int diff = 0;
/* 11 */     long savedDate = fromDate.getTimeInMillis();
/* 12 */     while ((future && !fromDate.after(toDate)) || (!future && !fromDate.before(toDate))) {
/* 13 */       savedDate = fromDate.getTimeInMillis();
/* 14 */       fromDate.add(type, future ? 1 : -1);
/* 15 */       diff++;
/*    */     } 
/* 17 */     diff--;
/* 18 */     fromDate.setTimeInMillis(savedDate);
/* 19 */     return diff;
/*    */   }
/*    */   
/*    */   public static String formatDateDiff(long date) {
/* 23 */     Calendar c = new GregorianCalendar();
/* 24 */     c.setTimeInMillis(date);
/* 25 */     Calendar now = new GregorianCalendar();
/* 26 */     return formatDateDiff(now, c);
/*    */   }
/*    */   
/*    */   public static String formatDateDiff(Calendar fromDate, Calendar toDate) {
/* 30 */     boolean future = false;
/* 31 */     if (toDate.equals(fromDate)) {
/* 32 */       return I18n.tl("now", new Object[0]);
/*    */     }
/* 34 */     if (toDate.after(fromDate)) {
/* 35 */       future = true;
/*    */     }
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     int[] types = { 1, 2, 5, 11, 12, 13 };
/* 39 */     String[] names = { "y", "y", "m", "m", "d", "d", "h", "h", "m", "m", "s", "s" };
/* 40 */     for (int accuracy = 0, i = 0; i < types.length && accuracy <= 3; i++) {
/* 41 */       int diff = dateDiff(types[i], fromDate, toDate, future);
/* 42 */       if (diff > 0) {
/* 43 */         accuracy++;
/* 44 */         sb.append(" ").append(diff).append(names[i * 2 + ((diff > 1) ? 1 : 0)]);
/*    */       } 
/*    */     } 
/* 47 */     if (sb.length() == 0) {
/* 48 */       return "now";
/*    */     }
/* 50 */     return sb.toString().trim();
/*    */   }
/*    */ }


/* Location:              /Users/benjaminmeyer/Downloads/HQHub.jar!/com/primalgamesllc/hqhub/utils/DateUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */