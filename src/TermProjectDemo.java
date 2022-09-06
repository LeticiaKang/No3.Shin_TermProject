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
        boolean repeat = true;
        
        do{ // Do-While문을 사용해서 Home으로 돌아올 수 있음. 

            // 요구사항 1. Home화면 (통계(S) 설문(P) 종료(Q) 중 선택하기)
            System.out.print("다음 중 선택하세요. ");
         
            // PollFunction : 설문(P)가 출력되는 메소드
            TermProjectPolls polls = new TermProjectPolls();
            int val = polls.PollFunction();
             
            // StaticsFunction : 통계(S)가 출력되는 메소드
            TermProjectStatics statics = new TermProjectStatics();
            val = statics.StaticsFunction();

            // EndFunction : 종료(Q)가 출력되는 메소드
            TermProjectEnd end = new TermProjectEnd();
            val = end.EndFunction();

            // user가 입력하는 값이 user_input에 저장됨
            user_input = sc.nextLine();
            
            // user_input에 저장된 값을 If-else문을 이용해서 비교후 해당되는 곳의 실행구문이 동작됨
            if(user_input.compareTo("S") == 0){ 
                // 요구사항 5. 통계(S)화면 출력 (설문 참여자 수, 각 문항별, 답항별 개수 출력)
                            
                System.out.println("======이용 만족도 설문 결과 다음과 같습니다.======");
                
                try {
                    Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    Statement stmt = conn.createStatement(); 
                    
                    // 5-1. 설문에 참여한 인원을 구하여 출력(count 이용)
                    String user_count_QUERY = "select count(*) as user_count from Client_survey"; //설문에 참여한 인원수
                    ResultSet rs = stmt.executeQuery(user_count_QUERY);
                        while(rs.next()){
                        System.out.println("참여자수: " + rs.getInt("user_count"));}

                    // 5-2. 각 설문의 문항별, 답항별 개수 출력(groupby 이용)
                    String answer_count_QUERY = "select Questions_ID, Answers_ID,count(Answers_ID) "+
                                                "from result " +
                                                "group by Questions_ID, Answers_ID " +
                                                "order by Questions_ID";
                    rs = stmt.executeQuery(answer_count_QUERY);
                        while(rs.next()){
                            System.out.println("문항 "+ rs.getInt( "Questions_ID") + "번, 답항 "
                                                + rs.getInt("Answers_ID") + "번의 개수 "
                                                + rs.getInt("count(Answers_ID)") + "개 입니다.");
                        }    
                    } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                

            }
            else if(user_input.compareTo("P") == 0){ 
                // 요구사항 2. 설문(P)화면 출력 (이름 등록 → 설문 시작)
                try {
                    Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    Statement stmt = conn.createStatement(); 

                    // 2-1. InputName : 사용자 이름을 입력받는 메소드
                    user_name = polls.InputName();
                    
                    // 2-2. 입력받은 사용자이름은 client_survey라는 곳에 저장되어야 함.
                    // 2-2. 해당 컬럽의 max값을 찾아 + 1하여 추가하는 방식
                    // max_num_QUERY : 해당 컬럼의 최대값을 구함
                    max_num_QUERY = "select ifnull(max(Client_ID), 0) as max_col from client_survey";
                    ResultSet rs = stmt.executeQuery(max_num_QUERY);
                    rs.next();
                    int max_mun = rs.getInt("max_col") + 1;
                    
                    // QUERY : client_survey테이블에 해당하는 값을 insert해줌
                    QUERY = "insert into client_survey(Client_ID, Name) "  +
                            "values( "+max_mun+", '"+user_name+"' )";
                    stmt.executeUpdate(QUERY);
                                      
                    // 2-3. 질문이 시작됨. 선택지는 동일. for문으로 반복함
                    for (int num = 1; num <5 ; num ++){
                        // QUERY : int num을 매개로 where문을 사용해서 문항을 출력함
                        QUERY = "select Questions from questions " +
                                "where 1=1 and Questions_ID = "+num+"";
                        rs = stmt.executeQuery(QUERY);
                            while (rs.next()){
                                System.out.println("Questions: " + rs.getString("Questions"));
                                System.out.println("1.매우 만족  2.만족  3.보통  4.불만족  5.매우불만족");
                            }

                        // 2-4. user에게 답변 받아 result 테이블에 저장해야 함.
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
            // 요구사항 7. 종료(Q) 입력시, 설문 마치기
            else if(user_input.compareTo("Q") == 0){
                System.out.println("설문을 종료합니다.");
                repeat = false;
            }
            else{
                System.out.println("다시 입력하세요.");
            }
        }while(repeat);
        
        return;
    }
}
