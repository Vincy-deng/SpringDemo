package com.test.dao;
import com.test.vo.TxManagerAgentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ManagerAgentDao extends JpaRepository<TxManagerAgentEntity,String> {

  @Modifying
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  @Query(value = "delete from tx_ManagerAgent where  manager_id= ?1 ",nativeQuery = true)
  int delMbyId(int valueOf);

  @Modifying
  @Query(value = "insert into tx_ManagerAgent (manager_id,agent_id)values(?1,?2)",nativeQuery = true)
  int saveManavge(Long mid,Long aid);
}
