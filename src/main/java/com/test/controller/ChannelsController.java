package com.test.controller;



import com.test.service.ChannelsService;
import com.test.util.BaseResponse;
import com.test.vo.TChannelsEntity;
import com.test.vo.TxAccountEntity;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
@RequestMapping("/TChannelsEntity")
public class ChannelsController {
    @Autowired
    private ChannelsService channelsService;
    private final Log log= LogFactory.getLog(getClass());


/*    @PostMapping("findAllChannelsEntity")
    public ResponseEntity<BaseResponse<TChannelsEntity>> findAllChannelsEntity(){
        List<TChannelsEntity> channels=channelsService.findAllChannels();
        if (channels==null){
            return BaseResponse.generateBadResponseEntity(500,"查找失败","");
        }else {
            String token="adminToken";
            HashMap obj=new HashMap();
            obj.put("token",token);
            obj.put("TChannelsEntity",channels);
            return BaseResponse.generateOKResponseEntity(obj);
        }
    }*/


    @GetMapping("/list")
    public ResponseEntity<BaseResponse<TChannelsEntity>>  selectAll(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5")int pageSize){
        try {
            Page<TChannelsEntity> userIterator =channelsService.getChannelsList(pageSize,pageNum);
            int total=userIterator.getTotalPages();
            Iterator<TChannelsEntity> aIterator =  userIterator.iterator();
            List<TChannelsEntity> list = new ArrayList<>();
            while(aIterator.hasNext()){
                list.add(aIterator.next());
            }
            if (list==null){
                return BaseResponse.generateBadResponseEntity(500,"查找失败","");
            }else {
                String token="adminToken";
                HashMap obj=new HashMap();
                obj.put("token",token);
                obj.put("total",total);
                obj.put("channelsList",list);
                return BaseResponse.generateOKResponseEntity(obj);
            }
        } catch (Exception e) {
            return BaseResponse.generateBadResponseEntity(500,"服务器内部错误","");
        }
    }

    @PostMapping("/findTChannelsEntity")
    public ResponseEntity<BaseResponse<TChannelsEntity>> findTChannelsEntity(@RequestBody TChannelsEntity TChannelsEntity)throws Exception{
        TChannelsEntity TChannelsEntityBadse=channelsService.findChannels(TChannelsEntity.getLsh());
        if (TChannelsEntityBadse==null){
            return BaseResponse.generateBadResponseEntity(500,"查找失败","");
        }else {
            String token="adminToken";
            HashMap obj=new HashMap();
            obj.put("token",token);
            obj.put("TChannelsEntityBadse",TChannelsEntityBadse);
            return BaseResponse.generateOKResponseEntity(obj);
        }
    }

    @PostMapping("/insertTChannelsEntity")
    public ResponseEntity<BaseResponse<TChannelsEntity>> insertTChannelsEntity(@RequestBody TChannelsEntity TChannelsEntity)throws Exception{
        try {
            com.test.vo.TChannelsEntity flag=channelsService.insertChannels(TChannelsEntity);
            if (flag!=null){
                return BaseResponse.generateBadResponseEntity("新增成功！","");
            }else {
                return BaseResponse.generateBadResponseEntity(500,"新增失败","");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.generateBadResponseEntity(500,"内部错误","");
        }
    }

    @PostMapping(value = "/delTChannelsEntity")
    public ResponseEntity<BaseResponse<TChannelsEntity>> delTChannelsEntity(HttpServletRequest request,@RequestBody TChannelsEntity TChannelsEntity){
        try {
            int flag =channelsService.delChannels(TChannelsEntity.getLsh());
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
}
