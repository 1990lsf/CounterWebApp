package com.lsf.org.spitter.serivce;

import java.util.Set;

import com.lsf.org.spitter.domain.User;

public interface UserService {
	 /** 
     * @param uid 
     * @param address 
     */  
    void save(User user);  
  
    /** 
     * @param uid 
     * @return 
     */  
    User read(String id);  
  
    /** 
     * @param uid 
     */  
    void delete(String id); 
    
    void set(String key,User user);
    
    User get(String key);
}
