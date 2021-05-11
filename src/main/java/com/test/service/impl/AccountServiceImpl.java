package com.test.service.impl;

import com.test.dao.AccountDao;
import com.test.service.AccountService;
import com.test.vo.TxAccountEntity;
import com.test.vo.resultAccountList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;
    private final Log log= LogFactory.getLog(getClass());

    @Override
    public int delChannels(String aid) {
        try {
            return  accountDao.deleteByaId(aid);
        } catch (Exception e) {
            log.debug("AccountServiceImpl- ->>findByAccountNameAndPwd",e);
        }
        return 0;
    }


  @Override
  public List<TxAccountEntity> findAccountByName(String accountName,int accountProperty) {
    try {
      return  accountDao.findAccountByName(accountName,accountProperty);
    } catch (Exception e) {
      log.debug("AccountServiceImpl- ->>findAccountByName",e);
    }
    return null;
  }


  @Override
  public List<TxAccountEntity> findAgenctById(int aid) {
    try {
      return  accountDao.findAgenctById(aid);
    } catch (Exception e) {
      log.debug("AccountServiceImpl- ->>findAccountByName",e);
    }
    return null;
  }


  @Override
  public List<TxAccountEntity> findAllAccount() {
    try {
      return accountDao.findAll();
    } catch (Exception e) {
      log.debug("AccountServiceImpl- ->>findAllAccount",e);
    }
    return null;
  }


  @Override
    public List<TxAccountEntity> findByAccountNameAndPwd(String username, String password) {
        try {
            return  accountDao.findByAccountNameAndPwd(username,password);
        } catch (Exception e) {
            log.debug("AccountServiceImpl- ->>findByAccountNameAndPwd",e);
        }
        return null;
    }


  @Override
  public List<TxAccountEntity> findDepartmentList() {
    try {
      String a="16";
      return  accountDao.findDepartmentList(a);
    } catch (Exception e) {
      log.debug("AccountServiceImpl- ->>findDepartmentList",e);
    }
    return null;
  }

  @Override
  public String findDespByDespS(String a) {
    try {
        return  accountDao.findDespByDespS(a);
    } catch (Exception e) {
      log.debug("AccountServiceImpl- ->>findDespByDespS",e);
    }
    return null;
  }


  @Override
  public String findDespById(String s) {
    try {
      if (s.equals("0") || s==null || s==""){
        return "默认部门";
      }
      return accountDao.findDespById(s);
    } catch (Exception e) {
      log.debug("AccountServiceImpl- ->>findDespById",e);
    }
    return null;
  }

  @Override
  public TxAccountEntity findMangen(int accountId) {
    try {
      return accountDao.findMangen(accountId);

    } catch (Exception e) {
      log.debug("AccountServiceImpl- ->>findMangen",e);
    }
    return null;
  }


  @Override
  public Page<TxAccountEntity> getAccountLista(int pageSize, int pageNum) {
    //将参数传给这个方法就可以实现物理分页了，非常简单。
    try {
      Sort sort = new Sort(Sort.Direction.DESC, "accountId");
      Pageable pageable = new PageRequest(pageNum, pageSize, sort);
      Page<TxAccountEntity> accountList =accountDao.findAll(pageable);
      return accountList;
    } catch (Exception e) {
      log.debug("AccountServiceImpl- ->>findDepartmentList",e);
    }
    return null;
  }


  @Override
  public int insertAccount(List list) {
    try {
      return accountDao.saveAll(list).size();

    } catch (Exception e) {
      log.debug("AccountServiceImpl- ->>insertAccount",e);
    }
    return 0;
  }


  @Override
  public int updateAccount(String accountId, String accountName, String accountPassword, String accountProperty, String accountRights, String accountDesp, String accountAlias) {
    try {
      return accountDao.updateAccount(accountId,accountName,accountPassword,accountProperty,
          accountRights,accountDesp,accountAlias);

    } catch (Exception e) {
      log.debug("AccountServiceImpl- ->>insertAccount",e);
    }
    return 0;
  }


}
