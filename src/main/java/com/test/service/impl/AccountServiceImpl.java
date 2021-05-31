package com.test.service.impl;

import com.test.dao.AccountDao;
import com.test.dao.ManagerAgentDao;
import com.test.service.AccountService;
import com.test.vo.TxAccountEntity;
import com.test.vo.TxManagerAgentEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private ManagerAgentDao managerAgentDao;
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
  public int delMbyId(int valueOf) {
    try {
       return managerAgentDao.delMbyId(valueOf);
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
  public List<TxAccountEntity> findAllAccountByDid(String s) {
    try {
      return  accountDao.findAllAccountByDid("%"+s+"%");
    } catch (Exception e) {
      log.debug("AccountServiceImpl- ->>findByAccountNameAndPwd",e);
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
  public Page<TxAccountEntity> multipleChoice(String name, String alias, String desp, int property,int pageNum,int pageSize) {
    try {
      Pageable pageable = new PageRequest(pageNum, pageSize);
      StringBuffer stringBuffer=new StringBuffer();
      if (name.isEmpty() && alias.isEmpty() && desp.isEmpty() && property==0){
        //System.out.println("进这里了，这里是查全部");
        Page<TxAccountEntity> accountList = accountDao.findAll(pageable);
        return accountList;
      }else {
        if (!name.isEmpty()){
          stringBuffer.append("account_name like "+"'%"+name+"%'");
        }
        if (!alias.isEmpty() && !name.isEmpty()){
          stringBuffer.append(" and account_alias like "+"'%"+alias+"%'");
        }else if (!alias.isEmpty() && name.isEmpty()){
          stringBuffer.append(" account_alias like "+"'%"+alias+"%'");
        }
        if (!desp.isEmpty() && !alias.isEmpty()){
          stringBuffer.append(" and (charindex('"+desp+"',account_desp) > 0 )");
        }else if (!desp.isEmpty() && alias.isEmpty()){
          stringBuffer.append(" (charindex('"+desp+"',account_desp) > 0 )");
        }
        if (property!=0 && !desp.isEmpty()){
          stringBuffer.append(" and account_property = "+property);
        }else if (property!=0 && desp.isEmpty()){
          stringBuffer.append(" account_property = "+property);
        }
        System.out.println("================"+stringBuffer.toString());
        Page<TxAccountEntity> accountList =accountDao.multipleChoice(stringBuffer.toString(),pageable);
        log.debug(accountList);
        return accountList;
      }

    } catch (Exception e) {
      log.debug("AccountServiceImpl- ->>findDepartmentList",e);
    }
    return null;
  }


  @Transactional
  @Override
  public int saveManage(TxManagerAgentEntity parseInt) {
    try {
      return managerAgentDao.saveManavge(parseInt.getManagerId(),parseInt.getAgentId());
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
