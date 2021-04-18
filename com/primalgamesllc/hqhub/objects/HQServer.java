/*    */ package com.primalgamesllc.hqhub.objects;
/*    */ 
/*    */ public class HQServer
/*    */ {
/*  5 */   private Boolean status = Boolean.valueOf(false);
/*    */   
/*  7 */   private String serverName = null;
/*    */   
/*    */   public HQServer(Boolean status, String serverName) {
/* 10 */     this.status = status;
/* 11 */     this.serverName = serverName;
/*    */   }
/*    */   
/*    */   public Boolean getStatus() {
/* 15 */     return this.status;
/*    */   }
/*    */   
/*    */   public void setStatus(Boolean status) {
/* 19 */     this.status = status;
/*    */   }
/*    */   
/*    */   public String getServerName() {
/* 23 */     return this.serverName;
/*    */   }
/*    */   
/*    */   public void setServerName(String serverName) {
/* 27 */     this.serverName = serverName;
/*    */   }
/*    */ }


/* Location:              /Users/benjaminmeyer/Downloads/HQHub.jar!/com/primalgamesllc/hqhub/objects/HQServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */