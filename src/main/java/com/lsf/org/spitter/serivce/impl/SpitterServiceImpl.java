package com.lsf.org.spitter.serivce.impl;
import static java.lang.Math.*;
import static java.util.Collections.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lsf.org.spitter.domain.Spitter;
import com.lsf.org.spitter.domain.Spittle;
import com.lsf.org.spitter.serivce.SpitterService;

@Service("spitterService")
@Transactional(propagation=Propagation.REQUIRED)
public class SpitterServiceImpl implements SpitterService{

	  public void saveSpittle(Spittle spittle) {
	    spittle.setWhen(new Date());
	//    spitterDao.saveSpittle(spittle);
	  }

	  @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	  public List<Spittle> getRecentSpittles(int count) {
	    
	      List<Spittle> list = new ArrayList();
	    
	    return list;
	  }
	  
	 
	  


	  public void startFollowing(Spitter follower, Spitter followee) {
	    // TODO Auto-generated method stub  
	  }
	  
	
}
