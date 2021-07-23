package com.example.demo.login.domain.model;

import java.util.TreeMap;

public class State {
  static private TreeMap<String, String> dispStrMap;

  static {
    dispStrMap = new TreeMap<String, String>();
    dispStrMap.put("stock", "貸出可能");
    dispStrMap.put("applying", "貸出申請中");
    dispStrMap.put("lending", "貸出中");
    dispStrMap.put("pending", "貸出不可");
  }

  public static String getDispStr(String key) {
    return dispStrMap.containsKey(key) ?
        dispStrMap.get(key) : key;
  }
}

/*
public enum State {
  STOCK, APPLYING, LENDING, RETURN, RESERVE, PENDING;


  STOCK(0), APPLYING(1), LENDING(2), RETURNING(3), RESERVE(4), PENDING(5);
  private int stateNum;

  State() { this.stateNum = 0; }
  State(int stateNum) {
    if(stateNum > 5) {
      this.stateNum = 0;
    }
    this.stateNum = stateNum;
  }
  public int getSteteNum() { return this.stateNum; }
  public int SetStete() { return this.stateNum; }

}
*/
