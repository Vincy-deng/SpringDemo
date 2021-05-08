package com.test.vo;

public class resultAccountList {
  private int aid;
  private String DespStr;
  private String ManStr;

  public resultAccountList() {
  }

  public resultAccountList(int aid, String despStr, String manStr) {
    this.aid = aid;
    DespStr = despStr;
    ManStr = manStr;
  }

  public int getAid() {
    return aid;
  }

  public void setAid(int aid) {
    this.aid = aid;
  }

  public String getDespStr() {
    return DespStr;
  }

  public void setDespStr(String despStr) {
    DespStr = despStr;
  }

  public String getManStr() {
    return ManStr;
  }

  public void setManStr(String manStr) {
    ManStr = manStr;
  }
}
