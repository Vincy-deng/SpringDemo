package com.test.service.impl;

import com.test.dao.ChannelsDao;
import com.test.service.ChannelsService;
import com.test.vo.TChannelsEntity;
import com.test.vo.TxAccountEntity;
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
@Transactional
public class ChannelsServiceImpl implements ChannelsService {
    @Autowired
    private ChannelsDao channelsDao;

    private final Log log= LogFactory.getLog(getClass());

    @Override
    public TChannelsEntity findChannels(int lsh) throws Exception {
        try {
            return channelsDao.getOne(lsh+"");
        } catch (Exception e) {
            log.debug("ChannelsServiceImpl- ->>findChannels",e);
        }
        return null;
    }


    @Override
    public Page<TChannelsEntity> getChannelsList(int pageSize, int pageNum) {
        try {
            Sort sort = new Sort(Sort.Direction.DESC, "lsh");
            Pageable pageable = new PageRequest(pageNum, pageSize, sort);
            Page<TChannelsEntity> channelsList =channelsDao.findAll(pageable);
            return channelsList;
        } catch (Exception e) {
            log.debug("ChannelsServiceImpl- ->>findDepartmentList",e);
        }
        return null;
    }

    @Override
    public TChannelsEntity insertChannels(TChannelsEntity channels) {
        try {
            return  channelsDao.save(channels);
        } catch (Exception e) {
            log.debug("ChannelsServiceImpl- ->>insertChannels",e);
        }
        return null;
    }

    @Override
    public int delChannels(int lsh) throws Exception {
        try {
            channelsDao.deleteById(lsh+"");
        } catch (Exception e) {
            log.debug("ChannelsServiceImpl- ->>delChannels",e);
        }

        return lsh;
    }

    @Override
    public List<TChannelsEntity> findAllChannels() {
        return channelsDao.findAll();
    }
}
