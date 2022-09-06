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
        String max_num_QUERY;
        String user_name = "0";
        String user_input = "0";
        int user_anw = 1;
        
        // 요구사항 1. Home화면 (통계 & 설문 중 선택하기)
        System.out.print("선택하세요(P 또는 S로 입력)>> ");
    
        TermProjectPolls polls = new TermProjectPolls();
        int val = polls.PollFunction();
        
        TermProjectStatics statics = new TermProjectStatics();
        val = statics.StaticsFunction();

        user_input = sc.nextLine();
        
        // class로 만들기 Select
        if(user_input.compareTo("S") == 0){ 
            // System.out.println("저는 S에요^^");
        }
        else if(user_input.compareTo("P") == 0){ 
            try {
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement(); 

                // 1. 사용자 이름을 입력받기
                System.out.print("이름을 입력하세요>> ");
                user_name = sc.nextLine();
                System.out.println("");

                // 2. 입력받은 사용자이름은 client_survey라는 곳에 저장되어야 함.
                // 2. 해당 컬럽의 max값을 찾아 + 1하여 추가하는 방식
                max_num_QUERY = "select ifnull(max(Client_ID), 0) as max_col from client_survey";
                ResultSet rs = stmt.executeQuery(max_num_QUERY);
                rs.next();
                int max_mun = rs.getInt("max_col") + 1;
                
                QUERY = "insert into client_survey(Client_ID, Name) "  +
                         "values( "+max_mun+", '"+user_name+"' )";
                stmt.executeUpdate(QUERY);

                // 3. 질문이 시작됨. 선택지는 동일. for문으로 반복함
                // 3. select문을 이용해서 출력
                for (int num = 1; num <5 ; num ++){
                    QUERY = "select Questions from questions " +
                            "where 1=1 and Questions_ID = "+num+"";
                    rs = stmt.executeQuery(QUERY);
                    // Extract data from result set
                        while (rs.next()){
                            // 3. 질문을 출력함
                            System.out.println("Questions: " + rs.getString("Questions"));
                            System.out.println("1.매우 만족  2.만족  3.보통  4.불만족  5.매우불만족");
                        }

                    // 4. user에게 답변 받아 result table에 저장해야 함.
                    System.out.print("답변을 입력하세요.");
                    user_anw = Integer.parseInt(sc.nextLine()); 
                    System.out.println("");
                   
                    QUERY = "insert into result(Questions_ID, Answers_ID, Client_ID) " +
                         "values( "+num+", "+user_anw+" , "+max_mun+" )";
                    stmt.executeUpdate(QUERY);

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
