/**
 * 
 */
package com.lsf.org.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.lsf.org.spitter.domain.Student;
import com.lsf.org.spitter.domain.User;




/**
 * @author Administrator
 *
 */
public class BeanUtil {
/**
 * 完全复制
 * @param class1
 * @param class2
 * @throws Exception
 */
	public static void reflectionAttr(Object class1,Object class2)throws Exception{
		Class clazz1=Class.forName(class1.getClass().getName());
		Class clazz2=Class.forName(class2.getClass().getName());
		Field[] fields1=clazz1.getDeclaredFields();
		Field[] fields2=clazz2.getDeclaredFields();
		BeanUtil bu = new BeanUtil();
		for(Field f1:fields1){
			if(f1.getName().equals("id"))continue;
			Object value=bu.invokeGetMethod(class1,f1.getName(),null);
			for(Field f2:fields2){
				if(f1.getName().equals(f2.getName())){
					Object[] obj=new Object[1];
					obj[0]=value;
					bu.invokeSetMethod(class2,f2.getName(),obj);
				}
			}
		}
		
	}
	
	/**
     * 
     * 执行某个Field的getField方法
     * 
     * @param clazz 类
     * @param fieldName 类的属性名称
     * @param args 参数，默认为null
     * @return 
     */
    private Object invokeGetMethod(Object clazz, String fieldName, Object[] args)
    {
        String methodName = fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);
        Method method = null;
        try 
        {
            method = Class.forName(clazz.getClass().getName()).getDeclaredMethod("get" + methodName);
            return method.invoke(clazz);
        } 
        catch (Exception e)
        {
        	e.printStackTrace();
            return "";
        }
    }
    
    /**
     * 
     * 执行某个Field的setField方法
     * 
     * @param clazz 类
     * @param fieldName 类的属性名称
     * @param args 参数，默认为null
     * @return 
     */
    private Object invokeSetMethod(Object clazz, String fieldName, Object[] args)
    {       
        String methodName = fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);
        Method method = null;
        try 
        {
        	Class[] parameterTypes = new Class[1];
        	Class c = Class.forName(clazz.getClass().getName());
        	Field field = c.getDeclaredField(fieldName); 
        	parameterTypes[0] = field.getType();
            method = c.getDeclaredMethod("set" + methodName,parameterTypes);
            return method.invoke(clazz,args);
        } 
        catch (Exception e)
        {
        	e.printStackTrace();
            return "";
        }
    }


/**
 * 根据指定的filedName 进行复制
 * class1 为原始对象
 * class2 为新赋值对象
 */
    public static Object reflectionAttrByFiledName(Object class1,Object class2,String[] fieldName)throws Exception{
    	for(int i=0,j=fieldName.length;i<j;i++){
    		String fieldString=fieldName[i];
    		//获取class1的get属性的值
    		String methodName=fieldString.substring(0,1).toUpperCase()+fieldString.substring(1);
    		Method method=Class.forName(class1.getClass().getName()).getDeclaredMethod("get"+methodName);
    		Object obj1=method.invoke(class1);
    		
    		//设置class2的set属性的值
    		Class clazz2=Class.forName(class2.getClass().getName());
    		Field field = clazz2.getDeclaredField(fieldString); 
            method = clazz2.getDeclaredMethod("set" + methodName,field.getType());
            return method.invoke(class2,obj1);
    	}
    	return class2;
    }
	/**
	 * @param args
	 */
    public static void main(String[] args) {
		User user= new User();
		user.setId("12412312");
		user.setName("支付宝");
		String[] fieldName = {"id","name"};
		BeanUtil bu = new BeanUtil();
		try {
			Student object=(Student) bu.reflectionAttrByFiledName(user, new Student(),fieldName);
			System.out.println(object.getId());
			System.out.println(object.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
