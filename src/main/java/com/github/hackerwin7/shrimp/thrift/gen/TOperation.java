/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.github.hackerwin7.shrimp.thrift.gen;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum TOperation implements org.apache.thrift.TEnum {
  download(0),
  upload(1),
  heartbeat(2),
  restart(3);

  private final int value;

  private TOperation(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static TOperation findByValue(int value) { 
    switch (value) {
      case 0:
        return download;
      case 1:
        return upload;
      case 2:
        return heartbeat;
      case 3:
        return restart;
      default:
        return null;
    }
  }
}
