package com.test.vo;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = " tx_manageragent ", schema = "dbo", catalog = "DBApsuite")
public class TxManagerAgentEntity {
  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "manager_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long managerId;
  @Column(name = "agent_id")
  private Long agentId;

  public TxManagerAgentEntity()
  {
    super();
  }

  public TxManagerAgentEntity(Long managerId, Long agentId)
  {
    super();
    this.managerId = managerId;
    this.agentId = agentId;
  }

  public Long getManagerId()
  {
    return managerId;
  }

  public void setManagerId(Long managerId)
  {
    this.managerId = managerId;
  }

  public Long getAgentId()
  {
    return agentId;
  }

  public void setAgentId(Long agentId)
  {
    this.agentId = agentId;
  }
}
