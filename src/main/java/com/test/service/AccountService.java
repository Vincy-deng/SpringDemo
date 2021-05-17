package com.test.service;

import com.test.vo.TxAccountEntity;
import com.test.vo.TxManagerAgentEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {
  int delChannels(String aid);

  int delMbyId(int valueOf);

  List<TxAccountEntity> findAccountByName(String accountName,int accountProperty);

  List<TxAccountEntity> findAgenctById(int aid);

  List<TxAccountEntity> findAllAccount();

  List<TxAccountEntity> findAllAccountByDid(String s);

  List<TxAccountEntity> findByAccountNameAndPwd(String username, String password);

  List<TxAccountEntity> findDepartmentList();

  String findDespByDespS(String a);

  String findDespById(String s);

  TxAccountEntity findMangen(int accountId);

  Page<TxAccountEntity> getAccountLista(int pageSize, int pageNum);

  int insertAccount(List list);

  int saveManage(TxManagerAgentEntity parseInt);

  int updateAccount(String accountId, String accountName, String accountPassword, String accountProperty, String accountRights, String accountDesp, String accountAlias);
}
