package com.test.service;

import com.test.vo.TChannelsEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChannelsService {
    public TChannelsEntity findChannels(int lsh)throws Exception;

  Page<TChannelsEntity> getChannelsList(int pageSize, int pageNum);

  TChannelsEntity insertChannels(TChannelsEntity channels);

    public int delChannels(int lsh)throws Exception;

    List<TChannelsEntity> findAllChannels();
}
