package com.lsf.org.mp.msvn.bqftsvnpath;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc2.ISvnObjectReceiver;
import org.tmatesoft.svn.core.wc2.SvnLog;
import org.tmatesoft.svn.core.wc2.SvnOperationFactory;
import org.tmatesoft.svn.core.wc2.SvnRevisionRange;
import org.tmatesoft.svn.core.wc2.SvnTarget;
/**
 * �Զ��������ĳ���
 * <strong>Title : Test<br></strong>
 * <strong>Description : </strong>��ע��˵��д�ڴ˴�<br> 
 * <strong>Create on : Apr 11, 2014<br></strong>
 * <p>
 * <strong>Copyright (C) <br></strong>
 * <p>
 * @author zhouhaiyun<br>
 * @version <strong>DHCC</strong><br>
 * <br>
 * <strong>�޸���ʷ:</strong><br>
 * �޸���		�޸�����		�޸�����<br>
 * -------------------------------------------<br>
 * <br>
 * <br>
 */
public class PatchMaker_bqft {
		/**
		 * ����һ��
		 */
		public static String destPath = ""; //���·�� ,�����Զ�����
		public static String tomcatPath = "D:\\DeveloperTools\\Tomcat\\BQFT_tomcat\\apache-tomcat-5.5.28\\webapps\\BQFT\\"; //tomcat��·������Ҫ�����µĶ������ļ�Ŷ
		public static String start_date = "20160202000000";//�ϴθ���ʱ�� yyyyMMddHHmmss
		public static String   end_date = "20160218173000";//����ʱ�� yyyyMMddHHmmss
		private static Map<String,String> typeDic = new HashMap<String,String>();
		static {
			typeDic.put("M", "�޸�"); 
			typeDic.put("A", "����");
		}
		public static void main(String[] args) {
			if(args.length>0){
				destPath=args[0]+destPath;
				start_date=args[2];
				end_date=args[3];
			}
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");
			final SvnOperationFactory svnOperationFactory = new SvnOperationFactory();
			try {
				//svn�� url 
				final SVNURL url =  SVNURL.parseURIEncoded("https://www.dhcjrb.com:8206/svn/bi-dhc/czj/bqft/BQFT");
				svnOperationFactory.setAuthenticationManager(new BasicAuthenticationManager(
								"liushaofeng", "48cb4097"));//svn�û�������
				final SvnLog log = svnOperationFactory.createLog();
				Date date1 = sdf.parse(start_date);
				Date date2 = sdf.parse(end_date);
				log.addRange(SvnRevisionRange.create(SVNRevision.create(date1),SVNRevision.create(date2)));
				log.setDiscoverChangedPaths(true);
				log.setSingleTarget(SvnTarget.fromURL(url));
				log.setReceiver(new ISvnObjectReceiver<SVNLogEntry>() {
					public void receive(SvnTarget arg0, SVNLogEntry arg1)
							throws SVNException {
						//ÿ���汾ִ��һ��
						System.out.println("�汾:"+arg1.getRevision()+"===========���ߣ�"+arg1.getAuthor()+"======ʱ�䣺"+sdf2.format(arg1.getDate()));
						System.out.println("===�޸�����:"+arg1.getMessage());
						Map<String,SVNLogEntryPath> map = arg1.getChangedPaths();
						if(map.size()>0){
							Set set = map.keySet();
							for (Iterator iterator = set.iterator(); iterator.hasNext();) {
								String key = (String) iterator.next();
								SVNLogEntryPath path = map.get(key);
								System.out.println(typeDic.get(path.getType()+"")+":"+key);
								if(typeDic.get(path.getType()+"")!=null)handleFile(key);
							}
						}
						System.out.println("\n");
					}
				});
				
				log.run();
			}catch(Exception ex){
				ex.printStackTrace();
			} finally {
				svnOperationFactory.dispose();
			}
	}
	/**
	 * ���� �����ظ��ļ�
	 */
	private static Map<String,String> cache = new HashMap<String,String>();
	/**
	 * �����ļ�
	 * ��������
	 * @param path
	 */
	public static void handleFile(String path){
		if(cache.get("path")==null){
			if(path.indexOf("/WebRoot/")>-1){
				String purePath = path.substring(path.indexOf("/WebRoot/")+9);
				String srcPath = tomcatPath+purePath.replace("/", "\\");
				String desPath = destPath+purePath.replace("/", "\\");
				copyFile(srcPath,desPath);
			}else if(path.indexOf("/src/")>-1){
				if(path.toLowerCase().endsWith("java")){
					String purePath="";
					if(path.indexOf("/src/tradeflat/")>-1){
						purePath = path.substring(path.indexOf("/src/tradeflat/")+15);
					}else if(path.indexOf("/src/serv/")>-1){
						purePath = path.substring(path.indexOf("/src/serv/")+10);
					}else if(path.indexOf("/src/main/java/")>-1){
						purePath = path.substring(path.indexOf("/src/main/java/")+15);
					}else if(path.indexOf("/src/main/resources/")>-1){
						purePath = path.substring(path.indexOf("/src/main/resources/")+20);
					}else {
						purePath = path.substring(path.indexOf("/src/")+5);
					}
					String className = purePath.substring(purePath.lastIndexOf("/")+1).replace(".java", "");
					String classPath = purePath.substring(0,purePath.lastIndexOf("/")+1).replace("/", "\\");
					String srcPath = tomcatPath+"WEB-INF\\classes\\"+classPath;
					String desPath = destPath+"WEB-INF\\classes\\"+classPath;
					copyJava(srcPath,desPath,className);

					
				}else{
					String purePath="";
					if(path.indexOf("/src/tradeflat/")>-1){
						purePath = path.substring(path.indexOf("/src/tradeflat/")+15);
					}else if(path.indexOf("/src/serv/")>-1){
						purePath = path.substring(path.indexOf("/src/serv/")+10);
					}else if(path.indexOf("/src/main/java/")>-1){
						purePath = path.substring(path.indexOf("/src/main/java/")+15);
					}else if(path.indexOf("/src/main/resources/")>-1){
						purePath = path.substring(path.indexOf("/src/main/resources/")+20);
					}else {
						purePath = path.substring(path.indexOf("/src/")+5);
					}
					
					String srcPath = tomcatPath+"WEB-INF\\classes\\"+purePath.replace("/", "\\");
					String desPath = destPath+"WEB-INF\\classes\\"+purePath.replace("/", "\\");
					copyFile(srcPath,desPath);

				}
			}
			cache.put(path, "1");//�ŵ�����
		}else{
			//������� ���
		}
	}
	/**
	 * ������ͨ�ļ�
	 * ��������
	 * @param src
	 * @param dest
	 */
	public static void copyFile(String src,String dest){
		File destF = new File(dest);
		if(!destF.getParentFile().exists()){
			destF.getParentFile().mkdirs();
		}
		try {
			File fsrc = new File(src);
			File fdest = new File(dest);
			if(fsrc.isDirectory()){
				if(!fdest.exists()){
					fdest.mkdirs();
				}
			}else{
				copyFile(fsrc,fdest);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ��Ҫ�ǿ��� �����ڲ���� java�ļ�
	 * ��������
	 * @param src
	 * @param dest
	 */
	public static void copyJava(String src,String dest,String cname){
		File destF = new File(dest);
		if(!destF.exists()){
			destF.mkdirs();
		}
		File srcF = new File(src);
		File [] files = srcF.listFiles();
		if(files!=null){
		for (int i = 0; i < files.length; i++) {
			if(files[i].getName().startsWith(cname)){
				//�����ڲ����˰�
				String destPath1 = dest+files[i].getName();
				try {
					copyFile(files[i],new File(destPath1));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		}
	}
	/**
	 * �����ļ�
	 * ��������
	 * @param sourceFile
	 * @param targetFile
	 * @throws IOException
	 */
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
        	//System.out.println("ԭʼ�ļ���"+sourceFile.getPath());
        	//System.out.println("=========��");
        	System.out.println("Ŀ���ļ���"+targetFile.getPath());
            // �½��ļ���������������л���
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // �½��ļ��������������л���
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // ��������
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // ˢ�´˻���������
            outBuff.flush();
        } finally {
            // �ر���
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
}
