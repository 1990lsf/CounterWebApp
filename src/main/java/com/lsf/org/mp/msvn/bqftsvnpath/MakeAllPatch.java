package com.lsf.org.mp.msvn.bqftsvnpath;

public class MakeAllPatch {
	public static String destPath = "D:\\bqft\\update0218\\BQFT\\"; 
	public static String tomcatPath = "D:\\DeveloperTools\\Tomcat\\BQFT_tomcat\\apache-tomcat-5.5.28\\webapps\\"; 
	public static String start_date = "20160202010000";// 开始时间yyyyMMddHHmmss
	public static String   end_date = "20160218173000";// 结束时间yyyyMMddHHmmss
	public static void main(String[] args) {
		args=new String[]{destPath,tomcatPath,start_date,end_date};
		PatchMaker_bqft.main(args);
//		PatchMaker_cmis.main(args);
	//	PatchMaker_report.main(args);
		System.out.println("完成");
	}

}
