//package dbdoc;
//
//import java.awt.Color;
//import java.io.FileOutputStream;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import com.lowagie.text.Cell;
//import com.lowagie.text.Document;
//import com.lowagie.text.PageSize;
//import com.lowagie.text.Paragraph;
//import com.lowagie.text.Table;
//import com.lowagie.text.rtf.RtfWriter2;
///**
// * �����ֵ������� Mysql
// * @author Eric zhou
// *
// */
//public class MySQL_DBDocer {
//	//�������ֵ�
//	private static Map<String,String> keyType = new HashMap<String,String>();
//	//��ʼ��jdbc
//	static{
//		try {
//			keyType.put("PRI", "����");
//			keyType.put("UNI", "Ψһ��");
//			Class.forName("com.mysql.jdbc.Driver");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
//	private static String url = "jdbc:mysql://127.0.0.1:3308/xedk";//����url
//	private static String username = "xedk"; //�û���
//	private static String password = "admin"; //����
//	private static String schema = "xedk"; //Ŀ�����ݿ� ��
//	//��ѯ���б��sql���
//	private static String sql_get_all_tables = "select table_name,TABLE_COMMENT from INFORMATION_SCHEMA.tables where TABLE_SCHEMA='"+schema+"' and TABLE_TYPE='BASE TABLE'";
//	//��ѯ�����ֶε�sql���
//	private static String sql_get_all_columns = "select column_name,data_type,character_octet_length,COLUMN_COMMENT,is_nullable,COLUMN_key from information_schema.`COLUMNS` where TABLE_NAME='{table_name}' and TABLE_SCHEMA='xedk'";
//	public static void main(String[] args) throws Exception {
//		//��ʼ��word�ĵ�
//		Document document = new Document(PageSize.A4); 
//		RtfWriter2.getInstance(document,new FileOutputStream("E:/word.doc"));  
//		document.open();  
//		//��ѯ��ʼ
//		Connection conn = getConnection();
//		//��ȡ���б�
//		List tables = getDataBySQL(sql_get_all_tables,conn);
//		int i=1;
//		for (Iterator iterator = tables.iterator(); iterator.hasNext();) {
//			String [] arr = (String []) iterator.next();
//			if(arr[0].startsWith("clear"))continue;
//			//ѭ����ȡ�ֶ���Ϣ
//			System.out.print(i+".���ڴ������ݱ�-----------"+arr[0]);
//			addTableMetaData(document,arr,i);
//			List columns = getDataBySQL(sql_get_all_columns.replace("{table_name}", arr[0]),conn);
//			addTableDetail(document,columns);
//			addBlank(document);
//			System.out.println("...done");
//			i++;
//		}
//		document.close();  
//		conn.close();
//	}
//	/**
//	 * ���һ������
//	 * @param document
//	 * @throws Exception
//	 */
//	public static void addBlank(Document document)throws Exception{
//		Paragraph ph = new Paragraph("");
//		ph.setAlignment(Paragraph.ALIGN_LEFT); 
//		document.add(ph);
//	}
//	/**
//	 * ��Ӱ����ֶ���ϸ��Ϣ�ı��
//	 * @param document
//	 * @param arr1
//	 * @param columns
//	 * @throws Exception
//	 */
//	public static void addTableDetail(Document document,List columns)throws Exception{
//		Table table = new Table(6);  
//		table.setWidth(100f);//��� ���100%
//	    table.setBorderWidth(1);  
//	    table.setBorderColor(Color.BLACK);  
//	    table.setPadding(0);  
//	    table.setSpacing(0);  
//	    Cell cell1 = new Cell("���");// ��Ԫ��
//	    cell1.setHeader(true);  
//	    
//	    Cell cell2 = new Cell("����");// ��Ԫ��
//	    cell2.setHeader(true); 
//	    
//	    Cell cell3 = new Cell("����");// ��Ԫ��
//	    cell3.setHeader(true); 
//	    
//	    Cell cell4 = new Cell("����");// ��Ԫ��
//	    cell4.setHeader(true); 
//	    
//	    Cell cell5 = new Cell("��");// ��Ԫ��
//	    cell5.setHeader(true); 
//	    
//	    Cell cell6 = new Cell("˵��");// ��Ԫ��
//	    cell6.setHeader(true);
//	    //���ñ�ͷ��ʽ
//	    table.setWidths(new float[]{8f,30f,15f,8f,10f,29f});
//	    cell1.setHorizontalAlignment(Cell.ALIGN_CENTER);
//	    cell1.setBackgroundColor(Color.gray);
//	    cell2.setHorizontalAlignment(Cell.ALIGN_CENTER);
//	    cell2.setBackgroundColor(Color.gray);
//	    cell3.setHorizontalAlignment(Cell.ALIGN_CENTER);
//	    cell3.setBackgroundColor(Color.gray);
//	    cell4.setHorizontalAlignment(Cell.ALIGN_CENTER);
//	    cell4.setBackgroundColor(Color.gray);
//	    cell5.setHorizontalAlignment(Cell.ALIGN_CENTER);
//	    cell5.setBackgroundColor(Color.gray);
//	    cell6.setHorizontalAlignment(Cell.ALIGN_CENTER);
//	    cell6.setBackgroundColor(Color.gray);
//	    table.addCell(cell1);  
//	    table.addCell(cell2);  
//	    table.addCell(cell3);  
//	    table.addCell(cell4);  
//	    table.addCell(cell5);
//	    table.addCell(cell6);
//	    table.endHeaders();// ��ͷ����
//	    int x = 1;
//	    for (Iterator iterator = columns.iterator(); iterator.hasNext();) {
//			String [] arr2 = (String []) iterator.next();
//			Cell c1 = new Cell(x+"");
//			Cell c2 = new Cell(arr2[0]);
//			Cell c3 = new Cell(arr2[1]);
//			Cell c4 = new Cell(arr2[2]);
//			
//			String key = keyType.get(arr2[5]);
//			if(key==null)key = "";
//			Cell c5 = new Cell(key);
//			Cell c6 = new Cell(arr2[3]);
//			c1.setHorizontalAlignment(Cell.ALIGN_CENTER);
//			c2.setHorizontalAlignment(Cell.ALIGN_CENTER);
//			c3.setHorizontalAlignment(Cell.ALIGN_CENTER);
//			c4.setHorizontalAlignment(Cell.ALIGN_CENTER);
//			c5.setHorizontalAlignment(Cell.ALIGN_CENTER);
//			c6.setHorizontalAlignment(Cell.ALIGN_CENTER);
//			table.addCell(c1);
//			table.addCell(c2);
//			table.addCell(c3);
//			table.addCell(c4);
//			table.addCell(c5);
//			table.addCell(c6);
//			x++;
//		}
//	    document.add(table);
//	}
//	/**
//	 * ���ӱ��Ҫ��Ϣ
//	 * @param dcument
//	 * @param arr
//	 * @param i
//	 * @throws Exception
//	 */
//	public static void addTableMetaData(Document dcument,String [] arr,int i) throws Exception{
//		Paragraph ph = new Paragraph(i+". ����: "+arr[0]+"        ˵��: "+(arr[1]==null?"":arr[1]));
//		ph.setAlignment(Paragraph.ALIGN_LEFT); 
//		dcument.add(ph);
//	}
//	/**
//	 * ��SQL����ѯ���б�
//	 * @param sql
//	 * @param conn
//	 * @return
//	 */
//	public static List getDataBySQL(String sql,Connection conn){
//		Statement stmt = null;
//		ResultSet rs = null;
//		List list = new ArrayList();
//		try {
//			stmt = conn.createStatement();
//			rs = stmt.executeQuery(sql);
//			while(rs.next()){
//				String [] arr = new String[rs.getMetaData().getColumnCount()];
//				for(int i=0;i<arr.length;i++){
//					arr[i] = rs.getString(i+1);
//				}
//				list.add(arr);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally{
//			try {
//				if(rs!=null)rs.close();
//				if(stmt!=null)stmt.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return list;
//	}
//	/**
//	 * ��ȡ���ݿ�����
//	 * @return
//	 */
//	public static Connection getConnection(){
//		try {
//			return DriverManager.getConnection(url, username, password);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//}
