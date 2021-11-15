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
	
	// DB를 배우지 않아서 DB 대신 회원의 정보를 저장하는 멤버 변수
	// 서블릿 프로세스가 생길 때 만들어지고 서블릿 프로세스가 사라질 때 같이 사라짐
	// 영구적으로 데이터를보관하는 용도가 아니기 때문에 서버를 내렸다가 올리면 계속 초기화 됨
	// 지금단계에서 회원 정보를 영구적으로 보관하고 싶다면 파일에 회원 정보를 저장해야 됨
	
	// 여기서는 선언만, 생성자나 init메서드를 쓰는게 더 좋음
	//private List<MemberInfo> memberTable;	// 회원 정보를 저장할 수 있는 클래스를 만들어 제네릭스타입으로
	
	// Signin에서 사용하기 위해 변경
	public static List<MemberInfo> memberTable;

	
	// memberTable 이 private로 선언되어 있으므로 로그인할 때 가져가려면 get으로 가져갈 수 있게 선언
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
		
		// 값이 전달 되었는지 확인
		if(id == null || pw1 == null || pw2 == null || email == null || phoneFirst == null || phoneEtc == null || job == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
			return;
		}
		
		// 사용자가 입력하는 값에 공백이 있어도되는지 아니면 안되는지도 결정을 해야함
		// 사용자가 입력하는 값에 공백이 있으면 안된다 라고 가정
		id = id.trim();
		pw1 = pw1.trim();
		pw2 = pw2.trim();
		email = email.trim();
		phoneFirst = phoneFirst.trim();
		phoneEtc = phoneEtc.trim();
		job = job.trim();
		
		// replace 메서드는 오버로딩이 되어있어서
		// 문자를 바꾸거나 문자열을 바꿀 수 있음
		id = id.replace(" ", "");
		pw1 = pw1.replace(" ", "");
		pw2 = pw2.replace(" ", "");
		email = email.replace(" ", "");
		phoneFirst = phoneFirst.replace(" ", "");
		phoneEtc = phoneEtc.replace(" ", "");
		
		// contains() -> 대상 문자열 안에서 인자로 넣은 문자열이 있는지 찾아주는 메서드
		// 있다면 true / 없다면 false
		// 12345678
		if(!phoneEtc.contains("-")) {
			phoneEtc = phoneEtc.substring(0, 4) + "-" + phoneEtc.substring(4);
		}
		
		String phone = phoneFirst + "-" + phoneEtc;
		
		job = job.replace(" ", "");
		
		// 아이디의 길이(아이디는 10까지만)
		// 비밀번호의 길이(비밀번호는 16자까지만)
		// 이메일의 길이(50자까지)
		// 연락처의 길이(연락처의 형식도 결정 01011112222(11자로 고정) or 010-1111-2222(13자로 고정))
		// 직업의 길이(9자까지)
		if(id.length() > 10 || pw1.length() >16 || pw2.length() > 16 || email.length() > 50) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
			return;
		} else if(phone.length() != 13) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
			return;
		} else if(!job.equals("프론트엔드개발자") && !job.equals("백엔드개발자")) {
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
		
		// 사용자가 입력한 도메인이 우리가 지정한 도메인인지 확인하는 부분
		
		// 이메일의 도메인 ( @ 다음에 오는 gmail.com, naver.com 과 같은 부분)
		// 도 프론트엔드 개발자와 얘기를 해서 어떤 도메인을 사용할 수 있게 할지 결정
		// gmail.com, naver.com, daum.net 세 도메인만 가능
		// 회원 가입 시 사용자가 입력 가능한 도메인 목록
		String[] domainList = {"gmail.com", "naver.com", "daum.net"};
		String[] emailArr = email.split("@");	// @를 기준으로 앞뒤로 문자열을 쪼개 배열에 저장
		String userDomain = emailArr[1];
		
		// 사용자가 입력한 도메인이 우리가 지정한 도메인이 아니다 라는 가정으로 시작
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
		// 사용자가 입력한 도메인이 우리가 지정한 도메인인지 확인하는 부분

		// 아이디 중복 여부 체크
		// 아이디 중복 여부를 저장하는 변수 / 아이디가 중복됨 이라고 가정하고 시작
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
		
		// 모든 체크가 끝나면 회원가입(회원의 정보를 저장)
		memberTable.add(memberInfo);
		
		response.setStatus(201);
	}
}
