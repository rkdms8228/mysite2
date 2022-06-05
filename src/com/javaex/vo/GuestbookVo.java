package com.javaex.vo;

public class GuestbookVo {
   
   //필드
   private int guestbookNo;
   private String name;
   private String password;
   private String content;
   private String regDate;
   
   //생성자
   public GuestbookVo() {}
   
   public GuestbookVo(String name, String password, String content, String regDate) {
      this.name = name;
      this.password = password;
      this.content = content;
      this.regDate = regDate;
   }
   
   public GuestbookVo(int guestbookNo, String name, String password, String content, String regDate) {
      this.guestbookNo = guestbookNo;
      this.name = name;
      this.password = password;
      this.content = content;
      this.regDate = regDate;
   }
   
   //메소드-gs
   public int getGuestbookNo() {
      return guestbookNo;
   }

   public void setGuestbookNo(int guestbookNo) {
      this.guestbookNo = guestbookNo;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public String getRegDate() {
      return regDate;
   }

   public void setRegDate(String regDate) {
      this.regDate = regDate;
   }
   
   //메소드-일반
   @Override
   public String toString() {
      return "GuestbookVo [guestbookNo=" + guestbookNo + ", name=" + name + ", password=" + password + ", content=" + content
            + ", regDate=" + regDate + "]";
   }

}
