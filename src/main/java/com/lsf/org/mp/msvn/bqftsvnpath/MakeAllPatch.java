package com.lsf.org.mp.msvn.bqftsvnpath;

public class MakeAllPatch {
	public static String destPath = "D:\\bqft���²���\\update0218\\BQFT\\"; //���·�� ,�����Զ�����
	public static String tomcatPath = "D:\\DeveloperTools\\Tomcat\\BQFT_tomcat\\apache-tomcat-5.5.28\\webapps\\"; //tomcat��·������Ҫ�����µĶ������ļ�Ŷ
	public static String start_date = "20160202010000";//�ϴθ���ʱ�� yyyyMMddHHmmss
	public static String   end_date = "20160218173000";//����ʱ�� yyyyMMddHHmmss
	public static void main(String[] args) {
		args=new String[]{destPath,tomcatPath,start_date,end_date};
		PatchMaker_bqft.main(args);
//		PatchMaker_cmis.main(args);
	//	PatchMaker_report.main(args);
		System.out.println("�������");
	}

}
