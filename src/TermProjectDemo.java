import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class TermProjectDemo {
    public static void main(String[] args){
        
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = conn.createStatement();  //공통으로 사용하기 위해서. 빈화면이 statement고 이걸 class에 사용하고 싶은거임
        
        System.out.println("선택");

        TermProjectPolls polls = new TermProjectPolls();
        int val = polls.PollFunction();
        
        TermProjectStatics statics = new TermProjectStatics();
        val = statics.StaticsFunction();
        
        return;
    }
}
