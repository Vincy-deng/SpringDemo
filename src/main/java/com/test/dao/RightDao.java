package com.test.dao;

import com.test.vo.TxRightItemListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RightDao extends JpaRepository<TxRightItemListEntity,String> {

  @Query(value = "select * from tx_RightItemList ",nativeQuery = true)
  List<TxRightItemListEntity> findAllRight();
}
