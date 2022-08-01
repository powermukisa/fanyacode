package com.fanyacode.fanyacode.authorization;

public enum Role {
  DEFAULT_USER(1), ADMIN_USER(2);

  private int role;
  Role(int role) {
    this.role = role;
  }

  public int getRole() {
    return this.role;
  }
}
