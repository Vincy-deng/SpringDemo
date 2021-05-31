package com.test.dao;

import com.test.vo.TxAccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AccountDao extends JpaRepository<TxAccountEntity,String> {

  @Modifying
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  @Query(value = "delete from tx_account where  account_id= ?1 ",nativeQuery = true)
  int deleteByaId(String aid);

  @Query(value = "select * from tx_account a where a.account_name = ?1 and a.account_property = ?2",nativeQuery = true)
  List<TxAccountEntity> findAccountByName(String accountName,int accountProperty);

  @Query(value = "select * from tx_account a,tx_ManagerAgent m where m.manager_id=?1  and a.account_id=m.agent_id",nativeQuery = true)
  List<TxAccountEntity> findAgenctById(int aid);

  @Query(value = "select * from tx_account where account_desp like ?1",nativeQuery = true)
  List<TxAccountEntity> findAllAccountByDid(String s);

  //@Query(value = "select * from tbl_user where user_name like ?",nativeQuery = true)
  @Query(value = "select * from tx_account a where a.account_name = ?1 and a.account_password = ?2",nativeQuery = true)
  List<TxAccountEntity> findByAccountNameAndPwd(String accountName, String accountPassword);

  @Query(value = "select * from tx_account where account_property=?1 ",nativeQuery = true)
  List<TxAccountEntity> findDepartmentList(String a);

  @Query(value = "select account_alias from tx_account where account_id=?1 and account_property=16 ",nativeQuery = true)
  String findDespByDespS(String a);

  @Query(value = "select account_alias from tx_account where account_id=?1 ",nativeQuery = true)
  String findDespById(String s);

  @Query(value = "select a.account_id,a.account_name,a.account_alias,a.account_desp, a.account_property,a.account_password,a.account_despdescribe,a.account_dspts,a.account_rights from tx_account a,tx_ManagerAgent m where m.agent_id=?1 and m.manager_id=a.account_id",nativeQuery = true)
  TxAccountEntity findMangen(int accountId);


  @Query(value = "SELECT * FROM TX_ACCOUNT where ?1",
      countQuery = "SELECT count(*) FROM TX_ACCOUNT where ?1",nativeQuery = true)
  Page<TxAccountEntity> multipleChoice(String string, Pageable pageable);

  @Modifying
  @Query(value = "update tx_account set account_name= ?2 , account_password= ?3 ," +
      "account_property= ?4 , account_rights= ?5, account_desp= ?6 ,account_alias= ?7 where account_id= ?1 ",nativeQuery = true)
  int updateAccount(String accountId, String accountName, String accountPassword, String accountProperty, String accountRights, String accountDesp, String accountAlias);
}
