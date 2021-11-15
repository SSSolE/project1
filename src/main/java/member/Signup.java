package member;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// DB�� ����� �ʾƼ� DB ��� ȸ���� ������ �����ϴ� ��� ����
	// ���� ���μ����� ���� �� ��������� ���� ���μ����� ����� �� ���� �����
	// ���������� �����͸������ϴ� �뵵�� �ƴϱ� ������ ������ ���ȴٰ� �ø��� ��� �ʱ�ȭ ��
	// ���ݴܰ迡�� ȸ�� ������ ���������� �����ϰ� �ʹٸ� ���Ͽ� ȸ�� ������ �����ؾ� ��
	
	// ���⼭�� ����, �����ڳ� init�޼��带 ���°� �� ����
	//private List<MemberInfo> memberTable;	// ȸ�� ������ ������ �� �ִ� Ŭ������ ����� ���׸���Ÿ������
	
	// Signin���� ����ϱ� ���� ����
	public static List<MemberInfo> memberTable;

	
	// memberTable �� private�� ����Ǿ� �����Ƿ� �α����� �� ���������� get���� ������ �� �ְ� ����
	public List<MemberInfo> getMemberTable() {
		return memberTable;
	}
	
	@Override
	public void init() throws ServletException {
		memberTable = new ArrayList<>();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String pw1 = request.getParameter("pw1");
		String pw2 = request.getParameter("pw2");
		String email = request.getParameter("email");
		String phoneFirst = request.getParameter("phoneFirst");
		String phoneEtc = request.getParameter("phoneEtc");
		String job = request.getParameter("job");
		
		// ���� ���� �Ǿ����� Ȯ��
		if(id == null || pw1 == null || pw2 == null || email == null || phoneFirst == null || phoneEtc == null || job == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
			return;
		}
		
		// ����ڰ� �Է��ϴ� ���� ������ �־�Ǵ��� �ƴϸ� �ȵǴ����� ������ �ؾ���
		// ����ڰ� �Է��ϴ� ���� ������ ������ �ȵȴ� ��� ����
		id = id.trim();
		pw1 = pw1.trim();
		pw2 = pw2.trim();
		email = email.trim();
		phoneFirst = phoneFirst.trim();
		phoneEtc = phoneEtc.trim();
		job = job.trim();
		
		// replace �޼���� �����ε��� �Ǿ��־
		// ���ڸ� �ٲٰų� ���ڿ��� �ٲ� �� ����
		id = id.replace(" ", "");
		pw1 = pw1.replace(" ", "");
		pw2 = pw2.replace(" ", "");
		email = email.replace(" ", "");
		phoneFirst = phoneFirst.replace(" ", "");
		phoneEtc = phoneEtc.replace(" ", "");
		
		// contains() -> ��� ���ڿ� �ȿ��� ���ڷ� ���� ���ڿ��� �ִ��� ã���ִ� �޼���
		// �ִٸ� true / ���ٸ� false
		// 12345678
		if(!phoneEtc.contains("-")) {
			phoneEtc = phoneEtc.substring(0, 4) + "-" + phoneEtc.substring(4);
		}
		
		String phone = phoneFirst + "-" + phoneEtc;
		
		job = job.replace(" ", "");
		
		// ���̵��� ����(���̵�� 10������)
		// ��й�ȣ�� ����(��й�ȣ�� 16�ڱ�����)
		// �̸����� ����(50�ڱ���)
		// ����ó�� ����(����ó�� ���ĵ� ���� 01011112222(11�ڷ� ����) or 010-1111-2222(13�ڷ� ����))
		// ������ ����(9�ڱ���)
		if(id.length() > 10 || pw1.length() >16 || pw2.length() > 16 || email.length() > 50) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
			return;
		} else if(phone.length() != 13) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
			return;
		} else if(!job.equals("����Ʈ���尳����") && !job.equals("�鿣�尳����")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
			return;
		} else if(id.length() == 0 || pw1.length() == 0 || pw2.length() == 0 || email.length() == 0 || phone.length() == 0 || job.length() == 0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
			return;
		}
		
		if(!pw1.equals(pw2)) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
			return;
		}
		
		// ����ڰ� �Է��� �������� �츮�� ������ ���������� Ȯ���ϴ� �κ�
		
		// �̸����� ������ ( @ ������ ���� gmail.com, naver.com �� ���� �κ�)
		// �� ����Ʈ���� �����ڿ� ��⸦ �ؼ� � �������� ����� �� �ְ� ���� ����
		// gmail.com, naver.com, daum.net �� �����θ� ����
		// ȸ�� ���� �� ����ڰ� �Է� ������ ������ ���
		String[] domainList = {"gmail.com", "naver.com", "daum.net"};
		String[] emailArr = email.split("@");	// @�� �������� �յڷ� ���ڿ��� �ɰ� �迭�� ����
		String userDomain = emailArr[1];
		
		// ����ڰ� �Է��� �������� �츮�� ������ �������� �ƴϴ� ��� �������� ����
		boolean correct = false;
		for(String domain : domainList) {
			correct = userDomain.equals(domain);
			
			if(correct) {
				break;
			}
		}
		
		if(!correct) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
			return;
		}
		// ����ڰ� �Է��� �������� �츮�� ������ ���������� Ȯ���ϴ� �κ�

		// ���̵� �ߺ� ���� üũ
		// ���̵� �ߺ� ���θ� �����ϴ� ���� / ���̵� �ߺ��� �̶�� �����ϰ� ����
		boolean exist = false;
		for(MemberInfo memberInfo : memberTable) {
			String savedId = memberInfo.getId();
			
			exist = savedId.equals(id);
			
			if(exist) {
				break;
			}
		}
		
		if(exist) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
			return;
		}
		
		
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setId(id);
		memberInfo.setPw(pw1);
		memberInfo.setEmail(email);
		memberInfo.setPhone(phone);
		memberInfo.setJob(job);
		
		// ��� üũ�� ������ ȸ������(ȸ���� ������ ����)
		memberTable.add(memberInfo);
		
		response.setStatus(201);
	}
}
