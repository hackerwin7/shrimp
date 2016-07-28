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
 * information about file
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-07-28")
public class TFileInfo implements org.apache.thrift.TBase<TFileInfo, TFileInfo._Fields>, java.io.Serializable, Cloneable, Comparable<TFileInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TFileInfo");

  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField LENGTH_FIELD_DESC = new org.apache.thrift.protocol.TField("length", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField SUFFIX_FIELD_DESC = new org.apache.thrift.protocol.TField("suffix", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField T_FROM_FIELD_DESC = new org.apache.thrift.protocol.TField("t_from", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField TS_FIELD_DESC = new org.apache.thrift.protocol.TField("ts", org.apache.thrift.protocol.TType.I64, (short)5);
  private static final org.apache.thrift.protocol.TField MD5_FIELD_DESC = new org.apache.thrift.protocol.TField("md5", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField START_FIELD_DESC = new org.apache.thrift.protocol.TField("start", org.apache.thrift.protocol.TType.I64, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TFileInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TFileInfoTupleSchemeFactory());
  }

  public String name; // required
  public long length; // required
  public String suffix; // required
  public String t_from; // required
  public long ts; // required
  public String md5; // required
  public long start; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    NAME((short)1, "name"),
    LENGTH((short)2, "length"),
    SUFFIX((short)3, "suffix"),
    T_FROM((short)4, "t_from"),
    TS((short)5, "ts"),
    MD5((short)6, "md5"),
    START((short)7, "start");

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
        case 1: // NAME
          return NAME;
        case 2: // LENGTH
          return LENGTH;
        case 3: // SUFFIX
          return SUFFIX;
        case 4: // T_FROM
          return T_FROM;
        case 5: // TS
          return TS;
        case 6: // MD5
          return MD5;
        case 7: // START
          return START;
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
  private static final int __TS_ISSET_ID = 1;
  private static final int __START_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.LENGTH, new org.apache.thrift.meta_data.FieldMetaData("length", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.SUFFIX, new org.apache.thrift.meta_data.FieldMetaData("suffix", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.T_FROM, new org.apache.thrift.meta_data.FieldMetaData("t_from", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TS, new org.apache.thrift.meta_data.FieldMetaData("ts", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.MD5, new org.apache.thrift.meta_data.FieldMetaData("md5", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.START, new org.apache.thrift.meta_data.FieldMetaData("start", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TFileInfo.class, metaDataMap);
  }

  public TFileInfo() {
  }

  public TFileInfo(
    String name,
    long length,
    String suffix,
    String t_from,
    long ts,
    String md5,
    long start)
  {
    this();
    this.name = name;
    this.length = length;
    setLengthIsSet(true);
    this.suffix = suffix;
    this.t_from = t_from;
    this.ts = ts;
    setTsIsSet(true);
    this.md5 = md5;
    this.start = start;
    setStartIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TFileInfo(TFileInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetName()) {
      this.name = other.name;
    }
    this.length = other.length;
    if (other.isSetSuffix()) {
      this.suffix = other.suffix;
    }
    if (other.isSetT_from()) {
      this.t_from = other.t_from;
    }
    this.ts = other.ts;
    if (other.isSetMd5()) {
      this.md5 = other.md5;
    }
    this.start = other.start;
  }

  public TFileInfo deepCopy() {
    return new TFileInfo(this);
  }

  @Override
  public void clear() {
    this.name = null;
    setLengthIsSet(false);
    this.length = 0;
    this.suffix = null;
    this.t_from = null;
    setTsIsSet(false);
    this.ts = 0;
    this.md5 = null;
    setStartIsSet(false);
    this.start = 0;
  }

  public String getName() {
    return this.name;
  }

  public TFileInfo setName(String name) {
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

  public TFileInfo setLength(long length) {
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

  public String getSuffix() {
    return this.suffix;
  }

  public TFileInfo setSuffix(String suffix) {
    this.suffix = suffix;
    return this;
  }

  public void unsetSuffix() {
    this.suffix = null;
  }

  /** Returns true if field suffix is set (has been assigned a value) and false otherwise */
  public boolean isSetSuffix() {
    return this.suffix != null;
  }

  public void setSuffixIsSet(boolean value) {
    if (!value) {
      this.suffix = null;
    }
  }

  public String getT_from() {
    return this.t_from;
  }

  public TFileInfo setT_from(String t_from) {
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

  public TFileInfo setTs(long ts) {
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

  public String getMd5() {
    return this.md5;
  }

  public TFileInfo setMd5(String md5) {
    this.md5 = md5;
    return this;
  }

  public void unsetMd5() {
    this.md5 = null;
  }

  /** Returns true if field md5 is set (has been assigned a value) and false otherwise */
  public boolean isSetMd5() {
    return this.md5 != null;
  }

  public void setMd5IsSet(boolean value) {
    if (!value) {
      this.md5 = null;
    }
  }

  public long getStart() {
    return this.start;
  }

  public TFileInfo setStart(long start) {
    this.start = start;
    setStartIsSet(true);
    return this;
  }

  public void unsetStart() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __START_ISSET_ID);
  }

  /** Returns true if field start is set (has been assigned a value) and false otherwise */
  public boolean isSetStart() {
    return EncodingUtils.testBit(__isset_bitfield, __START_ISSET_ID);
  }

  public void setStartIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __START_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
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

    case SUFFIX:
      if (value == null) {
        unsetSuffix();
      } else {
        setSuffix((String)value);
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

    case MD5:
      if (value == null) {
        unsetMd5();
      } else {
        setMd5((String)value);
      }
      break;

    case START:
      if (value == null) {
        unsetStart();
      } else {
        setStart((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case NAME:
      return getName();

    case LENGTH:
      return getLength();

    case SUFFIX:
      return getSuffix();

    case T_FROM:
      return getT_from();

    case TS:
      return getTs();

    case MD5:
      return getMd5();

    case START:
      return getStart();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case NAME:
      return isSetName();
    case LENGTH:
      return isSetLength();
    case SUFFIX:
      return isSetSuffix();
    case T_FROM:
      return isSetT_from();
    case TS:
      return isSetTs();
    case MD5:
      return isSetMd5();
    case START:
      return isSetStart();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TFileInfo)
      return this.equals((TFileInfo)that);
    return false;
  }

  public boolean equals(TFileInfo that) {
    if (that == null)
      return false;

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

    boolean this_present_suffix = true && this.isSetSuffix();
    boolean that_present_suffix = true && that.isSetSuffix();
    if (this_present_suffix || that_present_suffix) {
      if (!(this_present_suffix && that_present_suffix))
        return false;
      if (!this.suffix.equals(that.suffix))
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

    boolean this_present_md5 = true && this.isSetMd5();
    boolean that_present_md5 = true && that.isSetMd5();
    if (this_present_md5 || that_present_md5) {
      if (!(this_present_md5 && that_present_md5))
        return false;
      if (!this.md5.equals(that.md5))
        return false;
    }

    boolean this_present_start = true;
    boolean that_present_start = true;
    if (this_present_start || that_present_start) {
      if (!(this_present_start && that_present_start))
        return false;
      if (this.start != that.start)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_name = true && (isSetName());
    list.add(present_name);
    if (present_name)
      list.add(name);

    boolean present_length = true;
    list.add(present_length);
    if (present_length)
      list.add(length);

    boolean present_suffix = true && (isSetSuffix());
    list.add(present_suffix);
    if (present_suffix)
      list.add(suffix);

    boolean present_t_from = true && (isSetT_from());
    list.add(present_t_from);
    if (present_t_from)
      list.add(t_from);

    boolean present_ts = true;
    list.add(present_ts);
    if (present_ts)
      list.add(ts);

    boolean present_md5 = true && (isSetMd5());
    list.add(present_md5);
    if (present_md5)
      list.add(md5);

    boolean present_start = true;
    list.add(present_start);
    if (present_start)
      list.add(start);

    return list.hashCode();
  }

  @Override
  public int compareTo(TFileInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

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
    lastComparison = Boolean.valueOf(isSetSuffix()).compareTo(other.isSetSuffix());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSuffix()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.suffix, other.suffix);
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
    lastComparison = Boolean.valueOf(isSetMd5()).compareTo(other.isSetMd5());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMd5()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.md5, other.md5);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStart()).compareTo(other.isSetStart());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStart()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.start, other.start);
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
    StringBuilder sb = new StringBuilder("TFileInfo(");
    boolean first = true;

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
    sb.append("suffix:");
    if (this.suffix == null) {
      sb.append("null");
    } else {
      sb.append(this.suffix);
    }
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
    if (!first) sb.append(", ");
    sb.append("md5:");
    if (this.md5 == null) {
      sb.append("null");
    } else {
      sb.append(this.md5);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("start:");
    sb.append(this.start);
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

  private static class TFileInfoStandardSchemeFactory implements SchemeFactory {
    public TFileInfoStandardScheme getScheme() {
      return new TFileInfoStandardScheme();
    }
  }

  private static class TFileInfoStandardScheme extends StandardScheme<TFileInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TFileInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.name = iprot.readString();
              struct.setNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // LENGTH
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.length = iprot.readI64();
              struct.setLengthIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SUFFIX
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.suffix = iprot.readString();
              struct.setSuffixIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // T_FROM
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.t_from = iprot.readString();
              struct.setT_fromIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // TS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.ts = iprot.readI64();
              struct.setTsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // MD5
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.md5 = iprot.readString();
              struct.setMd5IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // START
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.start = iprot.readI64();
              struct.setStartIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TFileInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.name != null) {
        oprot.writeFieldBegin(NAME_FIELD_DESC);
        oprot.writeString(struct.name);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(LENGTH_FIELD_DESC);
      oprot.writeI64(struct.length);
      oprot.writeFieldEnd();
      if (struct.suffix != null) {
        oprot.writeFieldBegin(SUFFIX_FIELD_DESC);
        oprot.writeString(struct.suffix);
        oprot.writeFieldEnd();
      }
      if (struct.t_from != null) {
        oprot.writeFieldBegin(T_FROM_FIELD_DESC);
        oprot.writeString(struct.t_from);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(TS_FIELD_DESC);
      oprot.writeI64(struct.ts);
      oprot.writeFieldEnd();
      if (struct.md5 != null) {
        oprot.writeFieldBegin(MD5_FIELD_DESC);
        oprot.writeString(struct.md5);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(START_FIELD_DESC);
      oprot.writeI64(struct.start);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TFileInfoTupleSchemeFactory implements SchemeFactory {
    public TFileInfoTupleScheme getScheme() {
      return new TFileInfoTupleScheme();
    }
  }

  private static class TFileInfoTupleScheme extends TupleScheme<TFileInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TFileInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetName()) {
        optionals.set(0);
      }
      if (struct.isSetLength()) {
        optionals.set(1);
      }
      if (struct.isSetSuffix()) {
        optionals.set(2);
      }
      if (struct.isSetT_from()) {
        optionals.set(3);
      }
      if (struct.isSetTs()) {
        optionals.set(4);
      }
      if (struct.isSetMd5()) {
        optionals.set(5);
      }
      if (struct.isSetStart()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetName()) {
        oprot.writeString(struct.name);
      }
      if (struct.isSetLength()) {
        oprot.writeI64(struct.length);
      }
      if (struct.isSetSuffix()) {
        oprot.writeString(struct.suffix);
      }
      if (struct.isSetT_from()) {
        oprot.writeString(struct.t_from);
      }
      if (struct.isSetTs()) {
        oprot.writeI64(struct.ts);
      }
      if (struct.isSetMd5()) {
        oprot.writeString(struct.md5);
      }
      if (struct.isSetStart()) {
        oprot.writeI64(struct.start);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TFileInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.name = iprot.readString();
        struct.setNameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.length = iprot.readI64();
        struct.setLengthIsSet(true);
      }
      if (incoming.get(2)) {
        struct.suffix = iprot.readString();
        struct.setSuffixIsSet(true);
      }
      if (incoming.get(3)) {
        struct.t_from = iprot.readString();
        struct.setT_fromIsSet(true);
      }
      if (incoming.get(4)) {
        struct.ts = iprot.readI64();
        struct.setTsIsSet(true);
      }
      if (incoming.get(5)) {
        struct.md5 = iprot.readString();
        struct.setMd5IsSet(true);
      }
      if (incoming.get(6)) {
        struct.start = iprot.readI64();
        struct.setStartIsSet(true);
      }
    }
  }

}

