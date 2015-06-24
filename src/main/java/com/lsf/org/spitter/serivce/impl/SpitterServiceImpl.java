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

	@Override
	public Spitter getSpitter(String username) {
		Spitter spitter = new Spitter();
		spitter.setUsername("Lili");
		spitter.setEmail("1243@12.com");
		spitter.setFullName("John.Lilies");
		spitter.setId("000987");
		spitter.setPassword("325325432");
		List<Spittle> spittles = new ArrayList();
		spitter.setSpittles(spittles);
		spitter.setUpdateByEmail(true);
		return spitter;
	}

	@Override
	public List<Spittle> getSpitterlesForSpitter(String username) {
		List<Spittle> spittle_list = new ArrayList();
		
		for(int i=0,j=10;i<j;i++){
			Spittle ObjectSpittle = new Spittle();
			ObjectSpittle.setText("32532532");
			ObjectSpittle.setWhen(new Date());
			ObjectSpittle.setId(String.valueOf(i));
			spittle_list.add(ObjectSpittle);
			
		}
		return spittle_list;
	}

	@Override
	public void saveSpitter(Spitter spitter) {
		// TODO Auto-generated method stub
		
	}
	  
	
}
