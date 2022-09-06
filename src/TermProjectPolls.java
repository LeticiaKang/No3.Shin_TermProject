import java.util.*;
public class TermProjectPolls {
    Scanner sc = new Scanner(System.in);

    // PollFunction : Home에서 설문 선택지 메소드
    public int PollFunction(){
        System.out.print("설문(P) ");
        return 1;
    }

    // InputName : 사용자 이름을 입력 받는 메소드입니다.
    public String InputName(){
        System.out.print("이름을 입력하세요>> ");
        String user_name = sc.nextLine();
        System.out.println("");
        return user_name;
    }

    

}
