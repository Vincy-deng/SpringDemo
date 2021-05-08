package com.test.controller;

import com.test.service.AccountService;
import com.test.util.BaseResponse;
import com.test.util.RightItemUtil;
import com.test.vo.TxAccountEntity;
import com.test.vo.resultAccountList;
import com.test.vo.treeNode;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
@RequestMapping("/AccountController")
public class AccountController {

  @Autowired
    private AccountService accountService;
    private final Log log= LogFactory.getLog(getClass());

    /*查找用户表中所有的部门信息*/
    @PostMapping("/findDepartmentList")
    public ResponseEntity<BaseResponse<TxAccountEntity>> findDepartmentList(){
        List<TxAccountEntity> departmentList=accountService.findDepartmentList();
        if (departmentList==null){
            return BaseResponse.generateBadResponseEntity(500,"查找失败","");
        }else {
            String token="adminToken";
            HashMap obj=new HashMap();
            obj.put("token",token);
            obj.put("departmentList",departmentList);
            return BaseResponse.generateOKResponseEntity(obj);
        }
    }


  /*登录信息*/
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<TxAccountEntity>> login(@RequestParam String password,@RequestParam String username){
        List<TxAccountEntity> accountList=accountService.findByAccountNameAndPwd(username,password);
        if (accountList==null){
            return BaseResponse.generateBadResponseEntity(500,"查找失败","");
        }else {
            String token="adminToken";
            HashMap obj=new HashMap();
            obj.put("token",token);
            obj.put("accountList",accountList);
            return BaseResponse.generateOKResponseEntity(obj);
        }
    }


    @PostMapping("/addAccount")
    public ResponseEntity<BaseResponse<TxAccountEntity>> addAccount(@RequestParam String accountId,
                                                                    @RequestParam String accountName,
                                                                    @RequestParam String accountPassword,
                                                                    @RequestParam String accountProperty,
                                                                    @RequestParam String accountRights,
                                                                    @RequestParam String accountDesp,
                                                                    @RequestParam String accountAlias){
      TxAccountEntity account=new TxAccountEntity();
      account.setAccountAlias(accountAlias);
      account.setAccountDesp(accountDesp);
      account.setAccountPassword(accountPassword);
      List<String> isExist=new ArrayList<>();
      List list=new ArrayList();
      //把权限码转成通用的权限码
      String relRights = null;
      if (accountRights != null && !"".equals(accountRights.trim()))
      {
        String[] rightsArr = accountRights.split(",");
        short[] rightShort = new short[] {};
        for (String str : rightsArr)
        {
          rightShort =  RightItemUtil.GrantRight(rightShort, Integer.parseInt(str));
        }
        relRights = RightItemUtil.ByteArrayRightToStringRight(rightShort);
      }
      account.setAccountRights(relRights);
      //对accountname进行批量新增的一个方法
      if (accountName.contains(",")){
        String[] a = accountName.split(",");
        for (int i=Integer.parseInt(a[1]);i<Integer.parseInt(a[2]);i++){
          List<TxAccountEntity> accountEntities=accountService.findAccountByName(a[0]+""+i,Integer.parseInt(accountProperty));
          if (accountEntities.size()!=0){
            account.setAccountName(a[0]+""+i);
          }else {
            isExist.add(a[0]+""+i);
          }
          list.add(account);
        }
      }else {
        list.add(account);
      }
      try {
      int result=accountService.insertAccount(list);
        System.out.println("这里就是result------"+result);
           if (result > 0){
              return BaseResponse.generateOKResponseEntity("添加成功");
           }
            return BaseResponse.generateBadResponseEntity(500,"添加失败,该帐号已存在",isExist);
       }catch (Exception e) {
        return BaseResponse.generateBadResponseEntity(500, "系统异常", "");
      }
    }


  @Transactional
  @PostMapping("/updateAccount")
  public ResponseEntity<BaseResponse<TxAccountEntity>> updateAccount(@RequestParam String accountId,
                                                                     @RequestParam String accountName,
                                                                     @RequestParam String accountPassword,
                                                                     @RequestParam String accountProperty,
                                                                     @RequestParam String accountRights,
                                                                     @RequestParam String accountDesp,
                                                                     @RequestParam String accountAlias){
    //把权限码转成通用的权限码
    String relRights = null;
    if (accountRights != null && !"".equals(accountRights.trim()))
    {
      String[] rightsArr = accountRights.split(",");
      short[] rightShort = new short[] {};
      for (String str : rightsArr)
      {
        rightShort =  RightItemUtil.GrantRight(rightShort, Integer.parseInt(str));
      }
      relRights = RightItemUtil.ByteArrayRightToStringRight(rightShort);
    }
    accountRights=relRights;
    int result=accountService.updateAccount(accountId,accountName,accountPassword,accountProperty,
        accountRights,accountDesp,accountAlias);
    if (result==0){
      return BaseResponse.generateBadResponseEntity(500,"更新失败","");
    }else {
      return BaseResponse.generateOKResponseEntity("更新成功");
    }
  }


  @PostMapping("/delAccount")
  public ResponseEntity<BaseResponse<TxAccountEntity>> delAccount(@RequestParam String aid){
    try {
      int flag =accountService.delChannels(aid);
      if (flag==1){
        return BaseResponse.generateOKResponseEntity("删除成功","");
      }else {

        return BaseResponse.generateBadResponseEntity(500,"删除失败","");
      }
    } catch (Exception e) {
      e.printStackTrace();
      return BaseResponse.generateBadResponseEntity(500,"服务器内部错误","");
    }
  }


  @GetMapping("/list")
  public ResponseEntity<BaseResponse<TxAccountEntity>>  selectAll(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5")int pageSize){
    try {
      Page<TxAccountEntity> userIterator =accountService.getAccountLista(pageSize,pageNum);
      int total=userIterator.getTotalPages();
      Iterator<TxAccountEntity> aIterator =  userIterator.iterator();
      List<TxAccountEntity> list = new ArrayList<>();
      Map<Integer, String> listDesp = new HashMap<Integer, String>();
      while(aIterator.hasNext()){
        TxAccountEntity accountEntity=new TxAccountEntity();
        accountEntity=aIterator.next();
        String[] a=accountEntity.getAccountDesp().split(",");
        String Desp="";
        for (int i = 0; i <a.length ; i++) {
          Desp+=accountService.findDespById(a[i])+",";
        }
        listDesp.put(accountEntity.getAccountId(),Desp);
        list.add(accountEntity);
      }

      if (list==null){
        return BaseResponse.generateBadResponseEntity(500,"查找失败","");
      }else {
        String token="adminToken";
        HashMap obj=new HashMap();
        obj.put("token",token);
        obj.put("total",total);
        obj.put("listDesp",listDesp);
        obj.put("accountList",list);
        return BaseResponse.generateOKResponseEntity(obj);
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println(e);
      return BaseResponse.generateBadResponseEntity(500,"服务器内部错误","");
    }
  }


  @PostMapping("/findDespById")
  public ResponseEntity<BaseResponse<TxAccountEntity>> findDespById(@RequestParam String aid){
        String result="";
        String[] a=aid.split(",");
        for (int i = 0; i <a.length ; i++) {
          result += accountService.findDespById(a[i])+",";
        }
        if (result==null || result==""){
          result="默认部门";
          String token = "adminToken";
          HashMap obj = new HashMap();
          obj.put("token", token);
          obj.put("result", result);
          return BaseResponse.generateBadResponseEntity(500,"查找失败",obj);
        }
      else {
        String token = "adminToken";
        HashMap obj = new HashMap();
        obj.put("token", token);
        obj.put("result", result);
        return BaseResponse.generateOKResponseEntity(obj);
      }
  }

  @PostMapping("/findAllAccount")
  public ResponseEntity<BaseResponse<TxAccountEntity>> findAllAccount(){
    try {
      List<TxAccountEntity> account =accountService.findAllAccount();
      List<resultAccountList> accountList=new ArrayList<resultAccountList>();
      HashMap<Integer,TxAccountEntity> accountMangen = new HashMap<>();
      for (int i = 0; i <account.size() ; i++) {
        TxAccountEntity accountM=accountService.findMangen(account.get(i).getAccountId());
        accountMangen.put(account.get(i).getAccountId(),accountM);
        String D=(account.get(i).getAccountDesp()==null)?"0":account.get(i).getAccountDesp();
        String[] as=D.split(",");
        resultAccountList resultAccountList=new resultAccountList();
        for (int j = 0; j <as.length ; j++) {
          String b="";
          String a="";
            if (!as[j].equals("0") && !as[j].isEmpty()){
              a=accountService.findDespByDespS(as[j]);
              if (a == null || a.length() == 0){
                b=accountService.findDespById(as[j]);
                b+=b+",";
              }else {
                a+=a+",";
              }
              resultAccountList.setAid(account.get(i).getAccountId());
              resultAccountList.setDespStr(a);
              resultAccountList.setManStr(b);
            }else {
              resultAccountList.setAid(account.get(i).getAccountId());
              resultAccountList.setDespStr("默认部门");
            }
        }
        accountList.add(resultAccountList);
      }
      if (account!=null){
        String token = "adminToken";
        HashMap obj = new HashMap();
        obj.put("token", token);
        obj.put("account", account);
        obj.put("accountList", accountList);
        obj.put("accountMangen", accountMangen);
        return BaseResponse.generateOKResponseEntity(obj);
      }else {
        return BaseResponse.generateBadResponseEntity(500,"查询失败","");
      }
    } catch (Exception e) {
      e.printStackTrace();
      return BaseResponse.generateBadResponseEntity(500,"服务器内部错误","");
    }
  }

  @PostMapping("/findAgentById")
  public ResponseEntity<BaseResponse<TxAccountEntity>> findAgentById(@RequestParam int aid){
    try {
      List<TxAccountEntity> account =accountService.findAgenctById(aid);
      if (account!=null){
        String token = "adminToken";
        HashMap obj = new HashMap();
        obj.put("token", token);
        obj.put("account", account);
        return BaseResponse.generateOKResponseEntity(obj);
      }else {
        return BaseResponse.generateBadResponseEntity(500,"查询失败","");
      }
    } catch (Exception e) {
      e.printStackTrace();
      return BaseResponse.generateBadResponseEntity(500,"服务器内部错误","");
    }
  }

}