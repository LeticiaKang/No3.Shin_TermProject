import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class TermProjectDemo {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        final String USER = "root";
        final String PASS = "tbrs00002b";
        final String DB_URL = "jdbc:mysql://localhost:3306/data_biz_polls";
        String QUERY = "select Questions from questions " +
                        "where 1=1 and Questions_ID = 1";
        String user_name = "0";
        String user_input = "0";
        String user_anw = "0";
        
        // 요구사항 1. Home화면 (통계 & 설문 중 선택하기)
        System.out.print("선택하세요(P 또는 S로 입력)>> ");
    
        TermProjectPolls polls = new TermProjectPolls();
        int val = polls.PollFunction();
        
        TermProjectStatics statics = new TermProjectStatics();
        val = statics.StaticsFunction();

        user_input = sc.nextLine();
        
        // class로 만들기 Select
        if(user_input.compareTo("S") == 0){ //통계(S)를 선택했을 경우
            // System.out.println("저는 S에요^^");
        }
        else if(user_input.compareTo("P") == 0){ //설문(P)를 선택했을 경우
            System.out.println("이름을 입력하세요>> ");
            user_name = sc.nextLine()
            try {
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();  //공통으로 사용하기 위해서. 빈화면이 statement고 이걸 class에 사용하고 싶은거임
                // 문항이 나오는 Query 적기 ** for이용해서 자동으로 넘어갈 수 있도록 수정
                for (int num = 1; num <5 ; num ++){
                    QUERY = "select Questions from questions " +
                            "where 1=1 and Questions_ID = "+num+"";
                    ResultSet rs = stmt.executeQuery(QUERY);
                    // Extract data from result set
                        while (rs.next()){
                            // Retrieve by column name
                            System.out.println("Questions: " + rs.getString("Questions"));
                            System.out.println("1.매우 만족  2.만족  3.보통  4.불만족  5.매우불만족");
                        }
                    // user에게 답변 받기
                    System.out.print("답변을 입력하세요.");
                    user_anw = sc.nextLine(); 
                    System.out.println("");

                }
    
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
        }
        else{
            System.out.println("다시 입력하세요.");
            // 나중에 수정하기
        }
        
        
        return;
    }
}
