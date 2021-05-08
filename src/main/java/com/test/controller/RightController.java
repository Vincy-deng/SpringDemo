package com.test.controller;

import com.test.service.RightService;
import com.test.util.BaseResponse;
import com.test.vo.TxRightItemListEntity;
import com.test.vo.treeNode;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
@RequestMapping("/RightController")
public class RightController {

  @Autowired
  private RightService rightService;
  private final Log log= LogFactory.getLog(getClass());

  @PostMapping(value = "/getTree")
  @ResponseBody
  public ResponseEntity<BaseResponse<TxRightItemListEntity>> getTree(){
    Object result[] = new Object[2];
    TxRightItemListEntity rightItemList=new TxRightItemListEntity();
    rightItemList.setRightDesc("所有");
    rightItemList.setRightId(0);
    List<TxRightItemListEntity> firstResources = new ArrayList<>();
    firstResources.add(rightItemList);
    List<TxRightItemListEntity>secondResources = rightService.selectResource();
    List<treeNode>tree = new ArrayList<treeNode>();
    for(int i = 0; i < firstResources.size(); i++) {//循环给第一级节点添加子节点
      treeNode node0 = new treeNode();
      node0.setLabel(firstResources.get(i).getRightDesc());
      node0.setId(firstResources.get(i).getRightId());
      for(int j = 0; j < secondResources.size(); j++) {//循环第二级节点，给第一级节点添加子节点
        treeNode node1 = new treeNode();
        node1.setLabel(secondResources.get(j).getRightDesc());
        node1.setId(secondResources.get(j).getRightId());
        if(node1.getChildren().size()==0)
          node1.setChildren(null);
        node0.getChildren().add(node1);
      }
      if(node0.getChildren().size()==0)
        node0.setChildren(null);
      tree.add(node0);
    }
    result[0] = tree;
    //result[1] = accountService.selectResourceIdByRoleId(r_id);
    if (result==null){
      return BaseResponse.generateBadResponseEntity(500,"查找失败","");
    }else {
      String token="adminToken";
      HashMap obj=new HashMap();
      obj.put("token",token);
      obj.put("status",200);
      obj.put("data",result);
      return BaseResponse.generateOKResponseEntity(obj);
    }
  }
}



