package com.lsf.org.mp.msvn.dbcom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SQL Server ��ݿ����Ƚ�
 * @author Eric zhou
 * @since 2013-2-28
 */
public class SqlServerComparator {
	public static String url1 = "jdbc:sqlserver://localhost:1433;DatabaseName=xx1";
	public static String url2 = "jdbc:sqlserver://localhost:1433;DatabaseName=xx1";
	public static String USERNAME = "sa";
	public static String PASSWORD = "123";
	
	public static void main(String[] args) throws Exception{
		SqlServerComparator com = new SqlServerComparator();
		Connection con1 = com.getConnection(url1,USERNAME,PASSWORD);
		Connection con2 = com.getConnection(url2,USERNAME,PASSWORD);
		System.out.println("�����ӵ�������ݿ�...������ݿ�1Ϊ����ݿ���бȽ�");
		String sql = "select name from sys.all_objects where type_desc='USER_TABLE'";
		List list1 = com.Rs2List(com.getRsBySQL(sql, con1));
		List list2 = com.Rs2List(com.getRsBySQL(sql, con2));
		com.compare(list1, list2,con1,con2);
		System.out.println("�Ƚ����....");
	}
	private void compare(List list1,List list2,Connection con1,Connection con2) throws Exception {
		for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			if(list2.contains(name)){
				this.TableCompare(name, con1, con2);
			}else{
				System.out.println("��ݿ�2�У�ȱ�ٱ�:"+name);
			}
		}
	}
	private void TableCompare(String name,Connection con1,Connection con2) throws Exception {
		String sql = "select c.name,c.system_type_id from sys.all_columns c left join  sys.all_objects o on c.object_id=o.object_id where o.name='"+name+"' ";
		Map<String,Integer> map1 = this.parseColumnList(this.getRsBySQL(sql, con1));
		Map<String,Integer> map2 = this.parseColumnList(this.getRsBySQL(sql, con2));
		Set set = map1.keySet();
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			String cname = (String) iterator.next();
			if(map2.containsKey(cname)){
				if(map2.get(cname).intValue()!=map1.get(cname).intValue()){
					System.out.println("��ݿ�2�� "+name+" ���е��ֶ�:"+cname+" ����ݿ�1��������Ͳ�һ��");
				}
			}else{
				System.out.println("��ݿ�2�� "+name+" ���У�ȱ���ֶ�:"+cname);
			}
		}
	}
	private Map parseColumnList(ResultSet rs1) throws Exception {
		Map map = new HashMap();
		while(rs1.next()){
			map.put(rs1.getString("name"), rs1.getInt("system_type_id"));
		}
		return map;
	}
	private List Rs2List(ResultSet rs1)throws Exception{
		List list = new ArrayList();
		while(rs1.next()){
			list.add(rs1.getString("name"));
		}
		return list;
	}
	private ResultSet getRsBySQL(String sql,Connection con1)throws Exception{
		Statement stmt = con1.createStatement();
		return stmt.executeQuery(sql);
	}
	
	public static String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static {
		try {
			Class.forName(DRIVER).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private Connection getConnection(String url,String username,String password){
		try {
			return DriverManager.getConnection(url,username,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
