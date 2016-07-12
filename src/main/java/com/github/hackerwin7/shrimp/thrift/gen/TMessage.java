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
 * communication message
 * 
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-07-11")
public class TMessage implements org.apache.thrift.TBase<TMessage, TMessage._Fields>, java.io.Serializable, Cloneable, Comparable<TMessage> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TMessage");

  private static final org.apache.thrift.protocol.TField SRC_FIELD_DESC = new org.apache.thrift.protocol.TField("src", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField DES_FIELD_DESC = new org.apache.thrift.protocol.TField("des", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField OP_FIELD_DESC = new org.apache.thrift.protocol.TField("op", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField TS_FIELD_DESC = new org.apache.thrift.protocol.TField("ts", org.apache.thrift.protocol.TType.I64, (short)4);
  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField OFFSET_FIELD_DESC = new org.apache.thrift.protocol.TField("offset", org.apache.thrift.protocol.TType.I64, (short)6);
  private static final org.apache.thrift.protocol.TField EXT_FIELD_DESC = new org.apache.thrift.protocol.TField("ext", org.apache.thrift.protocol.TType.STRING, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TMessageStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TMessageTupleSchemeFactory());
  }

  public String src; // required
  public String des; // required
  /**
   * 
   * @see TOperation
   */
  public TOperation op; // required
  public long ts; // required
  public String name; // required
  public long offset; // required
  public String ext; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SRC((short)1, "src"),
    DES((short)2, "des"),
    /**
     * 
     * @see TOperation
     */
    OP((short)3, "op"),
    TS((short)4, "ts"),
    NAME((short)5, "name"),
    OFFSET((short)6, "offset"),
    EXT((short)7, "ext");

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
        case 1: // SRC
          return SRC;
        case 2: // DES
          return DES;
        case 3: // OP
          return OP;
        case 4: // TS
          return TS;
        case 5: // NAME
          return NAME;
        case 6: // OFFSET
          return OFFSET;
        case 7: // EXT
          return EXT;
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
  private static final int __TS_ISSET_ID = 0;
  private static final int __OFFSET_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SRC, new org.apache.thrift.meta_data.FieldMetaData("src", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DES, new org.apache.thrift.meta_data.FieldMetaData("des", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.OP, new org.apache.thrift.meta_data.FieldMetaData("op", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, TOperation.class)));
    tmpMap.put(_Fields.TS, new org.apache.thrift.meta_data.FieldMetaData("ts", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.OFFSET, new org.apache.thrift.meta_data.FieldMetaData("offset", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.EXT, new org.apache.thrift.meta_data.FieldMetaData("ext", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TMessage.class, metaDataMap);
  }

  public TMessage() {
  }

  public TMessage(
    String src,
    String des,
    TOperation op,
    long ts,
    String name,
    long offset,
    String ext)
  {
    this();
    this.src = src;
    this.des = des;
    this.op = op;
    this.ts = ts;
    setTsIsSet(true);
    this.name = name;
    this.offset = offset;
    setOffsetIsSet(true);
    this.ext = ext;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TMessage(TMessage other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetSrc()) {
      this.src = other.src;
    }
    if (other.isSetDes()) {
      this.des = other.des;
    }
    if (other.isSetOp()) {
      this.op = other.op;
    }
    this.ts = other.ts;
    if (other.isSetName()) {
      this.name = other.name;
    }
    this.offset = other.offset;
    if (other.isSetExt()) {
      this.ext = other.ext;
    }
  }

  public TMessage deepCopy() {
    return new TMessage(this);
  }

  @Override
  public void clear() {
    this.src = null;
    this.des = null;
    this.op = null;
    setTsIsSet(false);
    this.ts = 0;
    this.name = null;
    setOffsetIsSet(false);
    this.offset = 0;
    this.ext = null;
  }

  public String getSrc() {
    return this.src;
  }

  public TMessage setSrc(String src) {
    this.src = src;
    return this;
  }

  public void unsetSrc() {
    this.src = null;
  }

  /** Returns true if field src is set (has been assigned a value) and false otherwise */
  public boolean isSetSrc() {
    return this.src != null;
  }

  public void setSrcIsSet(boolean value) {
    if (!value) {
      this.src = null;
    }
  }

  public String getDes() {
    return this.des;
  }

  public TMessage setDes(String des) {
    this.des = des;
    return this;
  }

  public void unsetDes() {
    this.des = null;
  }

  /** Returns true if field des is set (has been assigned a value) and false otherwise */
  public boolean isSetDes() {
    return this.des != null;
  }

  public void setDesIsSet(boolean value) {
    if (!value) {
      this.des = null;
    }
  }

  /**
   * 
   * @see TOperation
   */
  public TOperation getOp() {
    return this.op;
  }

  /**
   * 
   * @see TOperation
   */
  public TMessage setOp(TOperation op) {
    this.op = op;
    return this;
  }

  public void unsetOp() {
    this.op = null;
  }

  /** Returns true if field op is set (has been assigned a value) and false otherwise */
  public boolean isSetOp() {
    return this.op != null;
  }

  public void setOpIsSet(boolean value) {
    if (!value) {
      this.op = null;
    }
  }

  public long getTs() {
    return this.ts;
  }

  public TMessage setTs(long ts) {
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

  public String getName() {
    return this.name;
  }

  public TMessage setName(String name) {
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

  public long getOffset() {
    return this.offset;
  }

  public TMessage setOffset(long offset) {
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

  public String getExt() {
    return this.ext;
  }

  public TMessage setExt(String ext) {
    this.ext = ext;
    return this;
  }

  public void unsetExt() {
    this.ext = null;
  }

  /** Returns true if field ext is set (has been assigned a value) and false otherwise */
  public boolean isSetExt() {
    return this.ext != null;
  }

  public void setExtIsSet(boolean value) {
    if (!value) {
      this.ext = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SRC:
      if (value == null) {
        unsetSrc();
      } else {
        setSrc((String)value);
      }
      break;

    case DES:
      if (value == null) {
        unsetDes();
      } else {
        setDes((String)value);
      }
      break;

    case OP:
      if (value == null) {
        unsetOp();
      } else {
        setOp((TOperation)value);
      }
      break;

    case TS:
      if (value == null) {
        unsetTs();
      } else {
        setTs((Long)value);
      }
      break;

    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((String)value);
      }
      break;

    case OFFSET:
      if (value == null) {
        unsetOffset();
      } else {
        setOffset((Long)value);
      }
      break;

    case EXT:
      if (value == null) {
        unsetExt();
      } else {
        setExt((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SRC:
      return getSrc();

    case DES:
      return getDes();

    case OP:
      return getOp();

    case TS:
      return getTs();

    case NAME:
      return getName();

    case OFFSET:
      return getOffset();

    case EXT:
      return getExt();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SRC:
      return isSetSrc();
    case DES:
      return isSetDes();
    case OP:
      return isSetOp();
    case TS:
      return isSetTs();
    case NAME:
      return isSetName();
    case OFFSET:
      return isSetOffset();
    case EXT:
      return isSetExt();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TMessage)
      return this.equals((TMessage)that);
    return false;
  }

  public boolean equals(TMessage that) {
    if (that == null)
      return false;

    boolean this_present_src = true && this.isSetSrc();
    boolean that_present_src = true && that.isSetSrc();
    if (this_present_src || that_present_src) {
      if (!(this_present_src && that_present_src))
        return false;
      if (!this.src.equals(that.src))
        return false;
    }

    boolean this_present_des = true && this.isSetDes();
    boolean that_present_des = true && that.isSetDes();
    if (this_present_des || that_present_des) {
      if (!(this_present_des && that_present_des))
        return false;
      if (!this.des.equals(that.des))
        return false;
    }

    boolean this_present_op = true && this.isSetOp();
    boolean that_present_op = true && that.isSetOp();
    if (this_present_op || that_present_op) {
      if (!(this_present_op && that_present_op))
        return false;
      if (!this.op.equals(that.op))
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

    boolean this_present_name = true && this.isSetName();
    boolean that_present_name = true && that.isSetName();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
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

    boolean this_present_ext = true && this.isSetExt();
    boolean that_present_ext = true && that.isSetExt();
    if (this_present_ext || that_present_ext) {
      if (!(this_present_ext && that_present_ext))
        return false;
      if (!this.ext.equals(that.ext))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_src = true && (isSetSrc());
    list.add(present_src);
    if (present_src)
      list.add(src);

    boolean present_des = true && (isSetDes());
    list.add(present_des);
    if (present_des)
      list.add(des);

    boolean present_op = true && (isSetOp());
    list.add(present_op);
    if (present_op)
      list.add(op.getValue());

    boolean present_ts = true;
    list.add(present_ts);
    if (present_ts)
      list.add(ts);

    boolean present_name = true && (isSetName());
    list.add(present_name);
    if (present_name)
      list.add(name);

    boolean present_offset = true;
    list.add(present_offset);
    if (present_offset)
      list.add(offset);

    boolean present_ext = true && (isSetExt());
    list.add(present_ext);
    if (present_ext)
      list.add(ext);

    return list.hashCode();
  }

  @Override
  public int compareTo(TMessage other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetSrc()).compareTo(other.isSetSrc());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSrc()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.src, other.src);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDes()).compareTo(other.isSetDes());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDes()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.des, other.des);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOp()).compareTo(other.isSetOp());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOp()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.op, other.op);
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
    lastComparison = Boolean.valueOf(isSetExt()).compareTo(other.isSetExt());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetExt()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ext, other.ext);
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
    StringBuilder sb = new StringBuilder("TMessage(");
    boolean first = true;

    sb.append("src:");
    if (this.src == null) {
      sb.append("null");
    } else {
      sb.append(this.src);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("des:");
    if (this.des == null) {
      sb.append("null");
    } else {
      sb.append(this.des);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("op:");
    if (this.op == null) {
      sb.append("null");
    } else {
      sb.append(this.op);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("ts:");
    sb.append(this.ts);
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
    sb.append("offset:");
    sb.append(this.offset);
    first = false;
    if (!first) sb.append(", ");
    sb.append("ext:");
    if (this.ext == null) {
      sb.append("null");
    } else {
      sb.append(this.ext);
    }
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

  private static class TMessageStandardSchemeFactory implements SchemeFactory {
    public TMessageStandardScheme getScheme() {
      return new TMessageStandardScheme();
    }
  }

  private static class TMessageStandardScheme extends StandardScheme<TMessage> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TMessage struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SRC
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.src = iprot.readString();
              struct.setSrcIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DES
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.des = iprot.readString();
              struct.setDesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // OP
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.op = com.github.hackerwin7.shrimp.thrift.gen.TOperation.findByValue(iprot.readI32());
              struct.setOpIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // TS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.ts = iprot.readI64();
              struct.setTsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.name = iprot.readString();
              struct.setNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // OFFSET
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.offset = iprot.readI64();
              struct.setOffsetIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // EXT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.ext = iprot.readString();
              struct.setExtIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TMessage struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.src != null) {
        oprot.writeFieldBegin(SRC_FIELD_DESC);
        oprot.writeString(struct.src);
        oprot.writeFieldEnd();
      }
      if (struct.des != null) {
        oprot.writeFieldBegin(DES_FIELD_DESC);
        oprot.writeString(struct.des);
        oprot.writeFieldEnd();
      }
      if (struct.op != null) {
        oprot.writeFieldBegin(OP_FIELD_DESC);
        oprot.writeI32(struct.op.getValue());
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(TS_FIELD_DESC);
      oprot.writeI64(struct.ts);
      oprot.writeFieldEnd();
      if (struct.name != null) {
        oprot.writeFieldBegin(NAME_FIELD_DESC);
        oprot.writeString(struct.name);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(OFFSET_FIELD_DESC);
      oprot.writeI64(struct.offset);
      oprot.writeFieldEnd();
      if (struct.ext != null) {
        oprot.writeFieldBegin(EXT_FIELD_DESC);
        oprot.writeString(struct.ext);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TMessageTupleSchemeFactory implements SchemeFactory {
    public TMessageTupleScheme getScheme() {
      return new TMessageTupleScheme();
    }
  }

  private static class TMessageTupleScheme extends TupleScheme<TMessage> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TMessage struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSrc()) {
        optionals.set(0);
      }
      if (struct.isSetDes()) {
        optionals.set(1);
      }
      if (struct.isSetOp()) {
        optionals.set(2);
      }
      if (struct.isSetTs()) {
        optionals.set(3);
      }
      if (struct.isSetName()) {
        optionals.set(4);
      }
      if (struct.isSetOffset()) {
        optionals.set(5);
      }
      if (struct.isSetExt()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetSrc()) {
        oprot.writeString(struct.src);
      }
      if (struct.isSetDes()) {
        oprot.writeString(struct.des);
      }
      if (struct.isSetOp()) {
        oprot.writeI32(struct.op.getValue());
      }
      if (struct.isSetTs()) {
        oprot.writeI64(struct.ts);
      }
      if (struct.isSetName()) {
        oprot.writeString(struct.name);
      }
      if (struct.isSetOffset()) {
        oprot.writeI64(struct.offset);
      }
      if (struct.isSetExt()) {
        oprot.writeString(struct.ext);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TMessage struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.src = iprot.readString();
        struct.setSrcIsSet(true);
      }
      if (incoming.get(1)) {
        struct.des = iprot.readString();
        struct.setDesIsSet(true);
      }
      if (incoming.get(2)) {
        struct.op = com.github.hackerwin7.shrimp.thrift.gen.TOperation.findByValue(iprot.readI32());
        struct.setOpIsSet(true);
      }
      if (incoming.get(3)) {
        struct.ts = iprot.readI64();
        struct.setTsIsSet(true);
      }
      if (incoming.get(4)) {
        struct.name = iprot.readString();
        struct.setNameIsSet(true);
      }
      if (incoming.get(5)) {
        struct.offset = iprot.readI64();
        struct.setOffsetIsSet(true);
      }
      if (incoming.get(6)) {
        struct.ext = iprot.readString();
        struct.setExtIsSet(true);
      }
    }
  }

}

