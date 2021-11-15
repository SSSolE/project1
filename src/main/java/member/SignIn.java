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
		// ����ڰ� �α����Ϸ��� �Է��� ȸ������
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		// �츮�� �޸��Ѵ�� �߰��� �ڵ带 �̷��� ������ �� �־���
		// ���������� �����Ѵ� ��� ����

		// �����;��� MemberTable �� Signup ���� private�� �Ǿ������� get�� ����� �̷��� ���
		// �Ϲ������δ� �̷��� ���� ���� ������ Ư���� ���� �̷��� ���
//		Signup signup = new Signup();
//		List<MemberInfo> memberTable = signup.getMemberTable();
		
		// DB�� ����� �ʾ����ϱ� ����ڰ� �Է��� ������ ȸ�� ������ ��ȸ�ϴµ� �ð��� ���� �ɸ��� ����
		// DB�� ���� DB�� �����ؼ� ȸ�� ������ ��ȸ�ϸ� ��ȸ�ϴµ� �ð��� ����� ���� �ɸ�
		// ��ȸ�� ��ġ�� ���� ��û�� ���� ���� �ɸ��� DB ��ȸ�� �� �ʿ䰡 ����
		// �׷��� DB ��ȸ�� �ϱ� ���� DB ��ȸ�� �� ��ġ�� �ִ� ��û���� �� üũ�� �ؾߵ�
		// �̷� üũ�� �׽�Ʈ�� ���ؼ� ã�� �� ����
		
		// ��� 2 signup ���� private �� public static���� �����ؼ� �̷��� ��
		List<MemberInfo> memberTable = Signup.memberTable;
		
		// �α��� �׽�Ʈ�� ���� �ڵ�
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
				// �α��� ����
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
