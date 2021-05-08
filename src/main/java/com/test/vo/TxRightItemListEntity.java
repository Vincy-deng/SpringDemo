package com.test.vo;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "tx_rightitemlist", schema = "dbo", catalog = "DBApsuite")
public class TxRightItemListEntity {
  private int rightCode;
  private String rightDesc;
  private int rightId;
  private int rightType;

  @Id
  @Basic
  @Column(name = "right_code", nullable = false)
  public int getRightCode() {
    return rightCode;
  }

  public void setRightCode(int rightCode) {
    this.rightCode = rightCode;
  }

  @Basic
  @Column(name = "right_desc", nullable = false, length = 100)
  public String getRightDesc() {
    return rightDesc;
  }

  public void setRightDesc(String rightDesc) {
    this.rightDesc = rightDesc;
  }

  @Basic
  @Column(name = "right_id", nullable = false)
  public int getRightId() {
    return rightId;
  }

  public void setRightId(int rightId) {
    this.rightId = rightId;
  }

  @Basic
  @Column(name = "right_type", nullable = false)
  public int getRightType() {
    return rightType;
  }

  public void setRightType(int rightType) {
    this.rightType = rightType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(rightId, rightDesc, rightType, rightCode);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TxRightItemListEntity that = (TxRightItemListEntity) o;
    return rightId == that.rightId &&
        rightType == that.rightType &&
        rightCode == that.rightCode &&
        Objects.equals(rightDesc, that.rightDesc);
  }
}
