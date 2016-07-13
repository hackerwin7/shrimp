/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.github.hackerwin7.shrimp.thrift.gen;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
/**
 * Thrift Err, Err class in the thrift
 * 
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-07-13")
public class TErr implements org.apache.thrift.TBase<TErr, TErr._Fields>, java.io.Serializable, Cloneable, Comparable<TErr> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TErr");

  private static final org.apache.thrift.protocol.TField ERR_FIELD_DESC = new org.apache.thrift.protocol.TField("err", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField COMMIT_FIELD_DESC = new org.apache.thrift.protocol.TField("commit", org.apache.thrift.protocol.TType.I64, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TErrStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TErrTupleSchemeFactory());
  }

  public int err; // required
  public long commit; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ERR((short)1, "err"),
    COMMIT((short)2, "commit");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // ERR
          return ERR;
        case 2: // COMMIT
          return COMMIT;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __ERR_ISSET_ID = 0;
  private static final int __COMMIT_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ERR, new org.apache.thrift.meta_data.FieldMetaData("err", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.COMMIT, new org.apache.thrift.meta_data.FieldMetaData("commit", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TErr.class, metaDataMap);
  }

  public TErr() {
  }

  public TErr(
    int err,
    long commit)
  {
    this();
    this.err = err;
    setErrIsSet(true);
    this.commit = commit;
    setCommitIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TErr(TErr other) {
    __isset_bitfield = other.__isset_bitfield;
    this.err = other.err;
    this.commit = other.commit;
  }

  public TErr deepCopy() {
    return new TErr(this);
  }

  @Override
  public void clear() {
    setErrIsSet(false);
    this.err = 0;
    setCommitIsSet(false);
    this.commit = 0;
  }

  public int getErr() {
    return this.err;
  }

  public TErr setErr(int err) {
    this.err = err;
    setErrIsSet(true);
    return this;
  }

  public void unsetErr() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ERR_ISSET_ID);
  }

  /** Returns true if field err is set (has been assigned a value) and false otherwise */
  public boolean isSetErr() {
    return EncodingUtils.testBit(__isset_bitfield, __ERR_ISSET_ID);
  }

  public void setErrIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ERR_ISSET_ID, value);
  }

  public long getCommit() {
    return this.commit;
  }

  public TErr setCommit(long commit) {
    this.commit = commit;
    setCommitIsSet(true);
    return this;
  }

  public void unsetCommit() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __COMMIT_ISSET_ID);
  }

  /** Returns true if field commit is set (has been assigned a value) and false otherwise */
  public boolean isSetCommit() {
    return EncodingUtils.testBit(__isset_bitfield, __COMMIT_ISSET_ID);
  }

  public void setCommitIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __COMMIT_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ERR:
      if (value == null) {
        unsetErr();
      } else {
        setErr((Integer)value);
      }
      break;

    case COMMIT:
      if (value == null) {
        unsetCommit();
      } else {
        setCommit((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ERR:
      return getErr();

    case COMMIT:
      return getCommit();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ERR:
      return isSetErr();
    case COMMIT:
      return isSetCommit();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TErr)
      return this.equals((TErr)that);
    return false;
  }

  public boolean equals(TErr that) {
    if (that == null)
      return false;

    boolean this_present_err = true;
    boolean that_present_err = true;
    if (this_present_err || that_present_err) {
      if (!(this_present_err && that_present_err))
        return false;
      if (this.err != that.err)
        return false;
    }

    boolean this_present_commit = true;
    boolean that_present_commit = true;
    if (this_present_commit || that_present_commit) {
      if (!(this_present_commit && that_present_commit))
        return false;
      if (this.commit != that.commit)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_err = true;
    list.add(present_err);
    if (present_err)
      list.add(err);

    boolean present_commit = true;
    list.add(present_commit);
    if (present_commit)
      list.add(commit);

    return list.hashCode();
  }

  @Override
  public int compareTo(TErr other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetErr()).compareTo(other.isSetErr());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetErr()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.err, other.err);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCommit()).compareTo(other.isSetCommit());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCommit()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.commit, other.commit);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("TErr(");
    boolean first = true;

    sb.append("err:");
    sb.append(this.err);
    first = false;
    if (!first) sb.append(", ");
    sb.append("commit:");
    sb.append(this.commit);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TErrStandardSchemeFactory implements SchemeFactory {
    public TErrStandardScheme getScheme() {
      return new TErrStandardScheme();
    }
  }

  private static class TErrStandardScheme extends StandardScheme<TErr> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TErr struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ERR
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.err = iprot.readI32();
              struct.setErrIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // COMMIT
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.commit = iprot.readI64();
              struct.setCommitIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, TErr struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ERR_FIELD_DESC);
      oprot.writeI32(struct.err);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(COMMIT_FIELD_DESC);
      oprot.writeI64(struct.commit);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TErrTupleSchemeFactory implements SchemeFactory {
    public TErrTupleScheme getScheme() {
      return new TErrTupleScheme();
    }
  }

  private static class TErrTupleScheme extends TupleScheme<TErr> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TErr struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetErr()) {
        optionals.set(0);
      }
      if (struct.isSetCommit()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetErr()) {
        oprot.writeI32(struct.err);
      }
      if (struct.isSetCommit()) {
        oprot.writeI64(struct.commit);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TErr struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.err = iprot.readI32();
        struct.setErrIsSet(true);
      }
      if (incoming.get(1)) {
        struct.commit = iprot.readI64();
        struct.setCommitIsSet(true);
      }
    }
  }

}

