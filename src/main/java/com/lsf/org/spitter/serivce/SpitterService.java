package com.lsf.org.spitter.serivce;

import java.util.List;

import com.lsf.org.spitter.domain.Spittle;

public interface SpitterService {
	List<Spittle> getRecentSpittles(int count);
}
