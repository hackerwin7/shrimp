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
 * a chunk of the file
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-07-08")
public class TFileChunk implements org.apache.thrift.TBase<TFileChunk, TFileChunk._Fields>, java.io.Serializable, Cloneable, Comparable<TFileChunk> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TFileChunk");

  private static final org.apache.thrift.protocol.TField BYTES_FIELD_DESC = new org.apache.thrift.protocol.TField("bytes", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField LENGTH_FIELD_DESC = new org.apache.thrift.protocol.TField("length", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField OFFSET_FIELD_DESC = new org.apache.thrift.protocol.TField("offset", org.apache.thrift.protocol.TType.I64, (short)4);
  private static final org.apache.thrift.protocol.TField T_FROM_FIELD_DESC = new org.apache.thrift.protocol.TField("t_from", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField TS_FIELD_DESC = new org.apache.thrift.protocol.TField("ts", org.apache.thrift.protocol.TType.I64, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TFileChunkStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TFileChunkTupleSchemeFactory());
  }

  public ByteBuffer bytes; // required
  public String name; // required
  public long length; // required
  public long offset; // required
  public String t_from; // required
  public long ts; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    BYTES((short)1, "bytes"),
    NAME((short)2, "name"),
    LENGTH((short)3, "length"),
    OFFSET((short)4, "offset"),
    T_FROM((short)5, "t_from"),
    TS((short)6, "ts");

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
        case 1: // BYTES
          return BYTES;
        case 2: // NAME
          return NAME;
        case 3: // LENGTH
          return LENGTH;
        case 4: // OFFSET
          return OFFSET;
        case 5: // T_FROM
          return T_FROM;
        case 6: // TS
          return TS;
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
  private static final int __LENGTH_ISSET_ID = 0;
  private static final int __OFFSET_ISSET_ID = 1;
  private static final int __TS_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.BYTES, new org.apache.thrift.meta_data.FieldMetaData("bytes", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , true)));
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.LENGTH, new org.apache.thrift.meta_data.FieldMetaData("length", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.OFFSET, new org.apache.thrift.meta_data.FieldMetaData("offset", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.T_FROM, new org.apache.thrift.meta_data.FieldMetaData("t_from", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TS, new org.apache.thrift.meta_data.FieldMetaData("ts", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TFileChunk.class, metaDataMap);
  }

  public TFileChunk() {
  }

  public TFileChunk(
    ByteBuffer bytes,
    String name,
    long length,
    long offset,
    String t_from,
    long ts)
  {
    this();
    this.bytes = org.apache.thrift.TBaseHelper.copyBinary(bytes);
    this.name = name;
    this.length = length;
    setLengthIsSet(true);
    this.offset = offset;
    setOffsetIsSet(true);
    this.t_from = t_from;
    this.ts = ts;
    setTsIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TFileChunk(TFileChunk other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetBytes()) {
      this.bytes = org.apache.thrift.TBaseHelper.copyBinary(other.bytes);
    }
    if (other.isSetName()) {
      this.name = other.name;
    }
    this.length = other.length;
    this.offset = other.offset;
    if (other.isSetT_from()) {
      this.t_from = other.t_from;
    }
    this.ts = other.ts;
  }

  public TFileChunk deepCopy() {
    return new TFileChunk(this);
  }

  @Override
  public void clear() {
    this.bytes = null;
    this.name = null;
    setLengthIsSet(false);
    this.length = 0;
    setOffsetIsSet(false);
    this.offset = 0;
    this.t_from = null;
    setTsIsSet(false);
    this.ts = 0;
  }

  public byte[] getBytes() {
    setBytes(org.apache.thrift.TBaseHelper.rightSize(bytes));
    return bytes == null ? null : bytes.array();
  }

  public ByteBuffer bufferForBytes() {
    return org.apache.thrift.TBaseHelper.copyBinary(bytes);
  }

  public TFileChunk setBytes(byte[] bytes) {
    this.bytes = bytes == null ? (ByteBuffer)null : ByteBuffer.wrap(Arrays.copyOf(bytes, bytes.length));
    return this;
  }

  public TFileChunk setBytes(ByteBuffer bytes) {
    this.bytes = org.apache.thrift.TBaseHelper.copyBinary(bytes);
    return this;
  }

  public void unsetBytes() {
    this.bytes = null;
  }

  /** Returns true if field bytes is set (has been assigned a value) and false otherwise */
  public boolean isSetBytes() {
    return this.bytes != null;
  }

  public void setBytesIsSet(boolean value) {
    if (!value) {
      this.bytes = null;
    }
  }

  public String getName() {
    return this.name;
  }

  public TFileChunk setName(String name) {
    this.name = name;
    return this;
  }

  public void unsetName() {
    this.name = null;
  }

  /** Returns true if field name is set (has been assigned a value) and false otherwise */
  public boolean isSetName() {
    return this.name != null;
  }

  public void setNameIsSet(boolean value) {
    if (!value) {
      this.name = null;
    }
  }

  public long getLength() {
    return this.length;
  }

  public TFileChunk setLength(long length) {
    this.length = length;
    setLengthIsSet(true);
    return this;
  }

  public void unsetLength() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LENGTH_ISSET_ID);
  }

  /** Returns true if field length is set (has been assigned a value) and false otherwise */
  public boolean isSetLength() {
    return EncodingUtils.testBit(__isset_bitfield, __LENGTH_ISSET_ID);
  }

  public void setLengthIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LENGTH_ISSET_ID, value);
  }

  public long getOffset() {
    return this.offset;
  }

  public TFileChunk setOffset(long offset) {
    this.offset = offset;
    setOffsetIsSet(true);
    return this;
  }

  public void unsetOffset() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __OFFSET_ISSET_ID);
  }

  /** Returns true if field offset is set (has been assigned a value) and false otherwise */
  public boolean isSetOffset() {
    return EncodingUtils.testBit(__isset_bitfield, __OFFSET_ISSET_ID);
  }

  public void setOffsetIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __OFFSET_ISSET_ID, value);
  }

  public String getT_from() {
    return this.t_from;
  }

  public TFileChunk setT_from(String t_from) {
    this.t_from = t_from;
    return this;
  }

  public void unsetT_from() {
    this.t_from = null;
  }

  /** Returns true if field t_from is set (has been assigned a value) and false otherwise */
  public boolean isSetT_from() {
    return this.t_from != null;
  }

  public void setT_fromIsSet(boolean value) {
    if (!value) {
      this.t_from = null;
    }
  }

  public long getTs() {
    return this.ts;
  }

  public TFileChunk setTs(long ts) {
    this.ts = ts;
    setTsIsSet(true);
    return this;
  }

  public void unsetTs() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TS_ISSET_ID);
  }

  /** Returns true if field ts is set (has been assigned a value) and false otherwise */
  public boolean isSetTs() {
    return EncodingUtils.testBit(__isset_bitfield, __TS_ISSET_ID);
  }

  public void setTsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TS_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case BYTES:
      if (value == null) {
        unsetBytes();
      } else {
        setBytes((ByteBuffer)value);
      }
      break;

    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((String)value);
      }
      break;

    case LENGTH:
      if (value == null) {
        unsetLength();
      } else {
        setLength((Long)value);
      }
      break;

    case OFFSET:
      if (value == null) {
        unsetOffset();
      } else {
        setOffset((Long)value);
      }
      break;

    case T_FROM:
      if (value == null) {
        unsetT_from();
      } else {
        setT_from((String)value);
      }
      break;

    case TS:
      if (value == null) {
        unsetTs();
      } else {
        setTs((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case BYTES:
      return getBytes();

    case NAME:
      return getName();

    case LENGTH:
      return getLength();

    case OFFSET:
      return getOffset();

    case T_FROM:
      return getT_from();

    case TS:
      return getTs();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case BYTES:
      return isSetBytes();
    case NAME:
      return isSetName();
    case LENGTH:
      return isSetLength();
    case OFFSET:
      return isSetOffset();
    case T_FROM:
      return isSetT_from();
    case TS:
      return isSetTs();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TFileChunk)
      return this.equals((TFileChunk)that);
    return false;
  }

  public boolean equals(TFileChunk that) {
    if (that == null)
      return false;

    boolean this_present_bytes = true && this.isSetBytes();
    boolean that_present_bytes = true && that.isSetBytes();
    if (this_present_bytes || that_present_bytes) {
      if (!(this_present_bytes && that_present_bytes))
        return false;
      if (!this.bytes.equals(that.bytes))
        return false;
    }

    boolean this_present_name = true && this.isSetName();
    boolean that_present_name = true && that.isSetName();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
        return false;
    }

    boolean this_present_length = true;
    boolean that_present_length = true;
    if (this_present_length || that_present_length) {
      if (!(this_present_length && that_present_length))
        return false;
      if (this.length != that.length)
        return false;
    }

    boolean this_present_offset = true;
    boolean that_present_offset = true;
    if (this_present_offset || that_present_offset) {
      if (!(this_present_offset && that_present_offset))
        return false;
      if (this.offset != that.offset)
        return false;
    }

    boolean this_present_t_from = true && this.isSetT_from();
    boolean that_present_t_from = true && that.isSetT_from();
    if (this_present_t_from || that_present_t_from) {
      if (!(this_present_t_from && that_present_t_from))
        return false;
      if (!this.t_from.equals(that.t_from))
        return false;
    }

    boolean this_present_ts = true;
    boolean that_present_ts = true;
    if (this_present_ts || that_present_ts) {
      if (!(this_present_ts && that_present_ts))
        return false;
      if (this.ts != that.ts)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_bytes = true && (isSetBytes());
    list.add(present_bytes);
    if (present_bytes)
      list.add(bytes);

    boolean present_name = true && (isSetName());
    list.add(present_name);
    if (present_name)
      list.add(name);

    boolean present_length = true;
    list.add(present_length);
    if (present_length)
      list.add(length);

    boolean present_offset = true;
    list.add(present_offset);
    if (present_offset)
      list.add(offset);

    boolean present_t_from = true && (isSetT_from());
    list.add(present_t_from);
    if (present_t_from)
      list.add(t_from);

    boolean present_ts = true;
    list.add(present_ts);
    if (present_ts)
      list.add(ts);

    return list.hashCode();
  }

  @Override
  public int compareTo(TFileChunk other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetBytes()).compareTo(other.isSetBytes());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBytes()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bytes, other.bytes);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetName()).compareTo(other.isSetName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.name, other.name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLength()).compareTo(other.isSetLength());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLength()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.length, other.length);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOffset()).compareTo(other.isSetOffset());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOffset()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.offset, other.offset);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetT_from()).compareTo(other.isSetT_from());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetT_from()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.t_from, other.t_from);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTs()).compareTo(other.isSetTs());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTs()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ts, other.ts);
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
    StringBuilder sb = new StringBuilder("TFileChunk(");
    boolean first = true;

    sb.append("bytes:");
    if (this.bytes == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.bytes, sb);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("name:");
    if (this.name == null) {
      sb.append("null");
    } else {
      sb.append(this.name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("length:");
    sb.append(this.length);
    first = false;
    if (!first) sb.append(", ");
    sb.append("offset:");
    sb.append(this.offset);
    first = false;
    if (!first) sb.append(", ");
    sb.append("t_from:");
    if (this.t_from == null) {
      sb.append("null");
    } else {
      sb.append(this.t_from);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("ts:");
    sb.append(this.ts);
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

  private static class TFileChunkStandardSchemeFactory implements SchemeFactory {
    public TFileChunkStandardScheme getScheme() {
      return new TFileChunkStandardScheme();
    }
  }

  private static class TFileChunkStandardScheme extends StandardScheme<TFileChunk> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TFileChunk struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // BYTES
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.bytes = iprot.readBinary();
              struct.setBytesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.name = iprot.readString();
              struct.setNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // LENGTH
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.length = iprot.readI64();
              struct.setLengthIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // OFFSET
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.offset = iprot.readI64();
              struct.setOffsetIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // T_FROM
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.t_from = iprot.readString();
              struct.setT_fromIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // TS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.ts = iprot.readI64();
              struct.setTsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TFileChunk struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.bytes != null) {
        oprot.writeFieldBegin(BYTES_FIELD_DESC);
        oprot.writeBinary(struct.bytes);
        oprot.writeFieldEnd();
      }
      if (struct.name != null) {
        oprot.writeFieldBegin(NAME_FIELD_DESC);
        oprot.writeString(struct.name);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(LENGTH_FIELD_DESC);
      oprot.writeI64(struct.length);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(OFFSET_FIELD_DESC);
      oprot.writeI64(struct.offset);
      oprot.writeFieldEnd();
      if (struct.t_from != null) {
        oprot.writeFieldBegin(T_FROM_FIELD_DESC);
        oprot.writeString(struct.t_from);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(TS_FIELD_DESC);
      oprot.writeI64(struct.ts);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TFileChunkTupleSchemeFactory implements SchemeFactory {
    public TFileChunkTupleScheme getScheme() {
      return new TFileChunkTupleScheme();
    }
  }

  private static class TFileChunkTupleScheme extends TupleScheme<TFileChunk> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TFileChunk struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetBytes()) {
        optionals.set(0);
      }
      if (struct.isSetName()) {
        optionals.set(1);
      }
      if (struct.isSetLength()) {
        optionals.set(2);
      }
      if (struct.isSetOffset()) {
        optionals.set(3);
      }
      if (struct.isSetT_from()) {
        optionals.set(4);
      }
      if (struct.isSetTs()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetBytes()) {
        oprot.writeBinary(struct.bytes);
      }
      if (struct.isSetName()) {
        oprot.writeString(struct.name);
      }
      if (struct.isSetLength()) {
        oprot.writeI64(struct.length);
      }
      if (struct.isSetOffset()) {
        oprot.writeI64(struct.offset);
      }
      if (struct.isSetT_from()) {
        oprot.writeString(struct.t_from);
      }
      if (struct.isSetTs()) {
        oprot.writeI64(struct.ts);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TFileChunk struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.bytes = iprot.readBinary();
        struct.setBytesIsSet(true);
      }
      if (incoming.get(1)) {
        struct.name = iprot.readString();
        struct.setNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.length = iprot.readI64();
        struct.setLengthIsSet(true);
      }
      if (incoming.get(3)) {
        struct.offset = iprot.readI64();
        struct.setOffsetIsSet(true);
      }
      if (incoming.get(4)) {
        struct.t_from = iprot.readString();
        struct.setT_fromIsSet(true);
      }
      if (incoming.get(5)) {
        struct.ts = iprot.readI64();
        struct.setTsIsSet(true);
      }
    }
  }

}

