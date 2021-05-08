package com.test.service;

import com.test.vo.TxAccountEntity;
import com.test.vo.resultAccountList;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {
  int delChannels(String aid);

  List<TxAccountEntity> findAccountByName(String accountName,int accountProperty);

  List<TxAccountEntity> findAgenctById(int aid);

  List<TxAccountEntity> findAllAccount();

  List<TxAccountEntity> findByAccountNameAndPwd(String username, String password);

  List<TxAccountEntity> findDepartmentList();

  String findDespByDespS(String a);

  String findDespById(String s);

  TxAccountEntity findMangen(int accountId);

  Page<TxAccountEntity> getAccountLista(int pageSize, int pageNum);

  int insertAccount(List list);

  int updateAccount(String accountId, String accountName, String accountPassword, String accountProperty, String accountRights, String accountDesp, String accountAlias);
}
