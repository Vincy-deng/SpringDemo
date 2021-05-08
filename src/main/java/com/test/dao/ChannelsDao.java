package com.test.dao;


import com.test.vo.TChannelsEntity;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelsDao extends JpaRepository<TChannelsEntity,String> {



    /*TChannelsEntity findChannels(@Param("lsh") int lsh)throws Exception;

    int insertChannels(TChannelsEntity channels);

    int delChannels(@Param("lsh") int lsh);*/
}
