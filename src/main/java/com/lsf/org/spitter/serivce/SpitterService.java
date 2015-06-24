package com.lsf.org.spitter.serivce;

import java.util.List;

import com.lsf.org.spitter.domain.Spitter;
import com.lsf.org.spitter.domain.Spittle;

public interface SpitterService {
	List<Spittle> getRecentSpittles(int count);

	Spitter getSpitter(String username);

	List<Spittle> getSpitterlesForSpitter(String username);

	void saveSpitter(Spitter spitter);
}
