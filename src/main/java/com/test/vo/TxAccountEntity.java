package com.test.vo;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = " tx_account ", schema = "dbo", catalog = "DBApsuite")
public class TxAccountEntity {
    private int accountId;
    private String accountName;
    private String accountPassword;
    private Integer accountProperty;
    private String accountRights;
    private String accountDesp;
    private String accountAlias;
    private Integer accountDespdescribe;
    private String accountDspts;

    @Id
    @Basic
    @Column(name = " account_id ", nullable = false)
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Basic
    @Column(name = " account_name ", nullable = false, length = 16)
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Basic
    @Column(name = " account_password ", nullable = true, length = 16)
    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    @Basic
    @Column(name = " account_property ", nullable = true)
    public Integer getAccountProperty() {
        return accountProperty;
    }

    public void setAccountProperty(Integer accountProperty) {
        this.accountProperty = accountProperty;
    }

    @Basic
    @Column(name = " account_rights ", nullable = true, length = 50)
    public String getAccountRights() {
        return accountRights;
    }

    public void setAccountRights(String accountRights) {
        this.accountRights = accountRights;
    }

    @Basic
    @Column(name = " account_desp ", nullable = true, length = 50)
    public String getAccountDesp() {
        return accountDesp;
    }

    public void setAccountDesp(String accountDesp) {
        this.accountDesp = accountDesp;
    }

    @Basic
    @Column(name = " account_alias ", nullable = true, length = 50)
    public String getAccountAlias() {
        return accountAlias;
    }

    public void setAccountAlias(String accountAlias) {
        this.accountAlias = accountAlias;
    }

    @Basic
    @Column(name = " account_despdescribe ", nullable = true)
    public Integer getAccountDespdescribe() {
        return accountDespdescribe;
    }

    public void setAccountDespdescribe(Integer accountDespdescribe) {
        this.accountDespdescribe = accountDespdescribe;
    }

    @Basic
    @Column(name = " account_dspts ", nullable = true, length = 100)
    public String getAccountDspts() {
        return accountDspts;
    }

    public void setAccountDspts(String accountDspts) {
        this.accountDspts = accountDspts;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TxAccountEntity that = (TxAccountEntity) o;
        return accountId == that.accountId &&
                Objects.equals(accountName, that.accountName) &&
                Objects.equals(accountPassword, that.accountPassword) &&
                Objects.equals(accountProperty, that.accountProperty) &&
                Objects.equals(accountRights, that.accountRights) &&
                Objects.equals(accountDesp, that.accountDesp) &&
                Objects.equals(accountAlias, that.accountAlias) &&
                Objects.equals(accountDespdescribe, that.accountDespdescribe) &&
                Objects.equals(accountDspts, that.accountDspts) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, accountName, accountPassword, accountProperty, accountRights, accountDesp, accountAlias, accountDespdescribe, accountDspts);
    }
}
