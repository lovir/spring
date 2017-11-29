package hello;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

//import m2.context.msg.protocol.Protocol;
import m2.context.msg.protocol.query.Query;
import m2.context.msg.protocol.query.QuerySet;
//import m2.context.msg.protocol.query.SelectSet;
//import m2.context.msg.protocol.query.WhereSet;
import m2.context.msg.protocol.result.Result;
import m2.context.msg.protocol.result.ResultSet;
import m2.earth.command.CommandSearchRequest;
import m2.earth.util.EarthException;

public class CateList {
	public CateList(){}
	
public List<Autocat> autoCatResult(String vocNo, String searchTerm){
    	
    	String host = "172.22.11.16";
    	int port = 6666;
    	String collectionID = "METOR_VOC";	
    	
    	String keywords = "";
    	String ext_data = "";
    	String vocContent = "";
    	
    	Result [] resultlist = null;
    	Query query = null;
    	
    	List<Autocat> cateList = new ArrayList<Autocat>();
    	
    	System.out.println("vocNo ######### " + vocNo);

    	
    	if (!vocNo.equals("") && vocNo != null){
    		vocContent = getVocContent(vocNo);
    		
        	//System.out.println("vocContent ######### " + vocContent);

    		if (!vocContent.equals(""))
    			searchTerm = vocContent;
    	}
    	
    	//System.out.println("searchTerm ######### " + searchTerm);
    	
    	//SelectSet	
    	if(!"".equals(searchTerm)&&searchTerm!=null){
    		QuerySet querySet = new QuerySet(1);
    		query = new Query();
    		query.setFrom(collectionID);
    		query.setValue("search", "false");
    		query.setValue("debug", "true");
    		query.setResultModifier("category");
    		query.setValue("category", "CATEGORY_CODE_NAME 0 4");
    		query.setValue("catKeyword", searchTerm);
    		query.setValue("catOption", "2");
    		querySet.addQuery(query);
    		
    		CommandSearchRequest command = new CommandSearchRequest(host, port);
    		
    		int rs = 0;
    		try {
				rs = command.request(querySet);
			} catch (EarthException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		if(rs >= 0){
    			ResultSet results = command.getResultSet();
    			resultlist = results.getResultList();
    		}else{
    			resultlist = new Result[0];
    		}
    	}else{
    		resultlist = new Result[0];
    	}
    	
    	for(int k=0; k<resultlist.length && resultlist[k]!=null; k++) {
			
    		Result result = resultlist[k];
			
			String val = result.getValue("catResult");
			String prval = result.getValue("catPR");
			
			ext_data = val;

			if(!val.equals("") && !prval.equals("")){
				StringTokenizer tokenizer = new StringTokenizer(val, "\n");
				StringTokenizer tokenizerpr = new StringTokenizer(prval, "\n");
				
				int tokens = tokenizer.countTokens();
				int tokenspr = tokenizerpr.countTokens();
				if(tokens != tokenspr){
					System.out.println("COUNT ERROR");
				}
				
				String[] categories = new String[tokens];
				String[] prs = new String[tokenspr];
				
				for(int i = 0 ; i < tokens ; i++){
					categories[i] = tokenizer.nextToken();
					
					System.out.println("category:"+categories[i] );
					//System.out.println("categoryCode:"+categories[i].split("\\^")[0] );
					//System.out.println("categoryName:"+categories[i].split("\\^")[1] );
					prs[i] = tokenizerpr.nextToken();
					
					cateList.add(new hello.Autocat(i + 1, categories[i].split("\\^")[0], categories[i].split("\\^")[1], prs[i]));
				}
				
				keywords = categories[0].split("\\^")[1];
			}
    	}
    	
    	insertM2FullLog(keywords, ext_data, vocNo);
    	
		return cateList;
    	
    }

	private String getVocContent(String vocNo) {
	// TODO Auto-generated method stub
		
		String driver        = "oracle.jdbc.driver.OracleDriver";
	    String url           = "jdbc:oracle:thin:@10.1.8.3:1521/SSDS";
	    String uId           = "ifdataone";
	    String uPwd          = "smscdataone14";
	    
	    Connection               con = null;
	    PreparedStatement        pstmt;
	    java.sql.ResultSet       rs;
	    
	    String vocConent = "";
	    
	    try {
	           Class.forName(driver);
	           con = DriverManager.getConnection(url, uId, uPwd);
	           
	           if( con != null ){ System.out.println("데이터 베이스 접속 성공"); }
	           
	       } catch (ClassNotFoundException e) { System.out.println("드라이버 로드 실패");    } 
	         catch (SQLException e) { System.out.println("데이터 베이스 접속 실패"); }
	    
	    String sql    = "select COMPCONT from NGENAPP01.TGENVOCMAIN where VOCNO='" + vocNo + "'";
        try {
            pstmt                = con.prepareStatement(sql);
            rs                   = pstmt.executeQuery();
            
            while(rs.next()){
                //System.out.println("COMPCONT       : " + rs.getString("COMPCONT"));
                
                vocConent = rs.getString("COMPCONT");
            }
            
            pstmt.close();

        } catch (SQLException e) { System.out.println("쿼리 수행 실패"); }
        
        try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
        return vocConent;
	}

	private void insertM2FullLog(String keywords, String ext_data, String vocNo) {
	// TODO Auto-generated method stub
		String driver        = "org.mariadb.jdbc.Driver";
	    String url           = "jdbc:mariadb://172.22.11.16:3307/DQCAT";
	    String uId           = "diquest";
	    String uPwd          = "ek2znptm2!@";
	    
	    Connection               con = null;
	    PreparedStatement        pstmt;
	    
	    
	    try {
	           Class.forName(driver);
	           con = DriverManager.getConnection(url, uId, uPwd);
	           
	           if( con != null ){ System.out.println("데이터 베이스 접속 성공"); }
	           
	       } catch (ClassNotFoundException e) { System.out.println("드라이버 로드 실패");    } 
	         catch (SQLException e) { System.out.println("데이터 베이스 접속 실패"); }
	    
	    String sql = "insert into M2_LOG_FULL (KEYWORDS, COLLECTION, RESULT_SIZE, USER_NAME, EXT_DATA, RESPONSE_TIME, REG_DATE) values (?,'METRO_VOC', '5', ?, ?, 0, now())";     
        
        try {
        	        		
    		pstmt = con.prepareStatement(sql);
    		pstmt.setString(1, keywords);
    		pstmt.setString(2, vocNo);
    		pstmt.setString(3, ext_data);
    		
    		pstmt.executeUpdate();        	         
        	
            pstmt.close();

        } catch (SQLException e) { System.out.println("쿼리 수행 실패"); }
        
        try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
