package com.test.service.impl;

import com.test.dao.RightDao;
import com.test.service.RightService;
import com.test.vo.TxRightItemListEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RightServiceImpl implements RightService {

  @Autowired
  private RightDao rightDao;
  private final Log log= LogFactory.getLog(getClass());

  @Override
  public List<TxRightItemListEntity> selectResource() {
    return rightDao.findAll();
  }
}
