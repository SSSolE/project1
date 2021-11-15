package member;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignIn
 */
@WebServlet("/member/signin")
public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 사용자가 로그인하려고 입력한 회원정보
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		// 우리가 메모한대로 중간에 코드를 이렇게 저렇게 잘 넣었다
		// 정상적으로 동작한다 라고 가정

		// 가져와야할 MemberTable 이 Signup 에서 private로 되어있으니 get을 만들어 이렇게 사용
		// 일반적으로는 이렇게 하지 않음 지금은 특수한 경우니 이렇게 사용
//		Signup signup = new Signup();
//		List<MemberInfo> memberTable = signup.getMemberTable();
		
		// DB를 배우지 않았으니까 사용자가 입력한 값으로 회원 정보를 조회하는데 시간이 오래 걸리지 않음
		// DB를 배우고 DB를 연동해서 회원 정보를 조회하면 조회하는데 시간이 상당히 오래 걸림
		// 조회할 가치가 없는 요청은 굳이 오래 걸리는 DB 조회를 할 필요가 없음
		// 그래서 DB 조회를 하기 전에 DB 조회를 할 가치가 있는 요청인지 꼭 체크를 해야됨
		// 이런 체크는 테스트를 통해서 찾을 수 있음
		
		// 방법 2 signup 에서 private 를 public static으로 변경해서 이렇게 씀
		List<MemberInfo> memberTable = Signup.memberTable;
		
		// 로그인 테스트를 위한 코드
		if(memberTable == null) {
			memberTable = new ArrayList<>();
			
			MemberInfo memberInfo = new MemberInfo();
			memberInfo.setEmail("id");
			memberInfo.setEmail("pw");
			
			memberTable.add(memberInfo);
		}
		
		for(MemberInfo memberInfo : memberTable) {
			String nthMemberId = memberInfo.getId();
			String nthMemberPw = memberInfo.getPw();
			
			if(id.equals(nthMemberId) && pw.equals(nthMemberPw)) {
				// 로그인 성공
				response.setStatus(HttpServletResponse.SC_OK);
				
				return;
			} // end if
		} // end for
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
