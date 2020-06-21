package com.web.database;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.database.Member;

@WebServlet("/data")
public class Database extends HttpServlet{
	String jdbc_driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://127.0.0.1:3306/databasetest?serverTimezone=UTC";
	Connection con = null;
	
	//--------------------------Model 부분------------------------------------
	
	//login 부분 - id, pwd를 확인한 후 Select sql을 실행해서 멤버리스트를 반환한다. (틀릴시 null 반환)
	public ArrayList<Member> doLogin(HttpServletRequest req) {
		String id = req.getParameter("id");
		String pwd = req.getParameter("pwd");
		String db_id="";
		String db_pwd="";
		String select_sql = "select id,pwd from databasetest.member where id=? and pwd=?";
	
		ArrayList<Member> mb = new ArrayList<Member>();
		
		try {
			//id, pwd 확인 부분
			PreparedStatement pstmt = con.prepareStatement(select_sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){					
				db_id = rs.getString("id");
				db_pwd = rs.getString("pwd");
			}
			
			if(db_id.equals(id) && db_pwd.equals(pwd)) {
				
				//Select sql을 실행해서 멤버리스트를 반환하는 부분
				select_sql = "select id,pwd,name,tel,email,dept,gender,birth,introduction from databasetest.member";
				pstmt = con.prepareStatement(select_sql);
				rs = pstmt.executeQuery();
				
				while(rs.next()){	
					mb.add(new Member(rs.getString("id"),rs.getString("pwd"),rs.getString("name"),rs.getString("tel")
							,rs.getString("email"), rs.getString("dept"),rs.getString("gender"),rs.getString("birth"),
							rs.getString("introduction")));
				}
				rs.close();
				pstmt.close();
				return mb;
			}
			//틀릴시 null을 반환하는 부분
			rs.close();
			pstmt.close();
			return null;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	//Search 부분 - 검색 학과를 확인한 후 Select sql을 실행해서 멤버리스트를 반환한다.
	public ArrayList<Member> doSearch(HttpServletRequest req) {
		String dept = req.getParameter("f");
		String select_sql = "";
		ArrayList<Member> mb = new ArrayList<Member>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//학과가 default일 경우 전부 반환
			if (dept.equals("default")) {
				select_sql = "select id,pwd,name,tel,email,dept,gender,birth,introduction from databasetest.member";
				pstmt = con.prepareStatement(select_sql);
				rs = pstmt.executeQuery();
			}
			//학과가 정해져있을 경우 특정 학과만 반환
			else {
				select_sql = "select id,pwd,name,tel,email,dept,gender,birth,introduction from databasetest.member"
						+" where dept=?";
				pstmt = con.prepareStatement(select_sql);
				pstmt.setString(1, dept);
				rs = pstmt.executeQuery();
			}
			
			while(rs.next()){	
				mb.add(new Member(rs.getString("id"),rs.getString("pwd"),rs.getString("name"),rs.getString("tel")
						,rs.getString("email"), rs.getString("dept"),rs.getString("gender"),rs.getString("birth"),
						rs.getString("introduction")));
			}
			rs.close();
			pstmt.close();
			return mb;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//List 부분 - Select sql을 실행해서 맴버리스트를 전부 반환한다.
	public ArrayList<Member> getList(HttpServletRequest req) {
		String select_sql = "select id,pwd,name,tel,email,dept,gender,birth,introduction from databasetest.member";
		ArrayList<Member> mb = new ArrayList<Member>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(select_sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){	
				mb.add(new Member(rs.getString("id"),rs.getString("pwd"),rs.getString("name"),rs.getString("tel")
						,rs.getString("email"), rs.getString("dept"),rs.getString("gender"),rs.getString("birth"),
						rs.getString("introduction")));
			}

			rs.close();
			pstmt.close();
			return mb;
		} catch (SQLException e) {
			return null;
		}
	}
	
	//Updatae 부분 - update할 Member객체를 가져와 Update sql을 실행해서 업데이트 한후 멤버리스트를 업테이트후 반환한다.
	public ArrayList<Member> doUpdate(HttpServletRequest req) {
		// update할 Member객체를 가져오는 부분
		Member member = (Member)req.getSession().getAttribute("update");
		ArrayList<Member> mb = (ArrayList<Member>)req.getSession().getAttribute("detail");
		String id = member.getId();
		String pwd =  req.getParameter("pwd");
		String tel = req.getParameter("tel");
		String email = req.getParameter("email");
		String dept = req.getParameter("dept");
		String gender = req.getParameter("gender");
		String birth = req.getParameter("birth");
		String introduction = req.getParameter("introduction");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String update_sql = "update databasetest.member set pwd=?, tel=?, email=?, dept=?,"
					+ " gender=?, birth=?, introduction=? where id=?";
			pstmt = con.prepareStatement(update_sql);
			pstmt.setString(1,pwd);
			pstmt.setString(2,tel);
			pstmt.setString(3,email);
			pstmt.setString(4,dept);
			pstmt.setString(5,gender);
			pstmt.setString(6,birth);
			pstmt.setString(7,introduction);
			pstmt.setString(8,id);	
			pstmt.executeUpdate();
			
			//Updata sql을 실행해서 업데이트하는 부분
			String select_sql = "select id,pwd,name,tel,email,dept,gender,birth,introduction from databasetest.member where id=?";
			pstmt = con.prepareStatement(select_sql);
			pstmt.setString(1,id);
			rs = pstmt.executeQuery();
			
			Member new_mb = null;
			while(rs.next()){					
				new_mb = new Member(rs.getString("id"),rs.getString("pwd"),rs.getString("name"),rs.getString("tel")
						,rs.getString("email"), rs.getString("dept"),rs.getString("gender"),rs.getString("birth"),
						rs.getString("introduction"));
			}
			
			//멤버리스트를 업데이트 한 후 반환하는 부분
			int index = mb.indexOf(member);
			mb.set(index, new_mb);
			
			rs.close();
			pstmt.close();
			return mb;
		} catch (SQLException e) {
			return null;
		}
	}
	
	//Insert 부분 - 이미 있는 데이터와 비교후 Insert sql문을 이용해 삽입 후 결과를 반환하는 부분 (이미 있을시 false반환)
	public boolean doInsert(HttpServletRequest req) {
		String id = req.getParameter("id");
		String name =  req.getParameter("name");
		String pwd =  req.getParameter("pwd");
		String tel = req.getParameter("tel");
		String email = req.getParameter("email");
		String dept = req.getParameter("dept");
		String gender = req.getParameter("gender");
		String birth = req.getParameter("birth");
		String introduction = req.getParameter("introduction");
		
		ArrayList<Member> mb = new ArrayList<Member>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String db_id="";
			String select_sql = "select id from databasetest.member where id=?";
			
			//이미 있는 데이터와 비교 하는 부분
			pstmt = con.prepareStatement(select_sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()){					
				db_id = rs.getString("id");
			}
			
			if(db_id.equals(id)) {
				//이미 있을시 false반환하는 부분
				rs.close();
				pstmt.close();
				return false;
			}
			else {
				//Insert sql문을 이용해 삽입 하고 결과를 반환하는 부분
				String insert_sql = "insert into databasetest.member(id,pwd,name,tel,email,dept,gender,birth,introduction)"
						+ " values(?,?,?,?,?,?,?,?,?)";
				pstmt = con.prepareStatement(insert_sql);
				pstmt.setString(1,id);	
				pstmt.setString(2,pwd);
				pstmt.setString(3,name);
				pstmt.setString(4,tel);
				pstmt.setString(5,email);
				pstmt.setString(6,dept);
				pstmt.setString(7,gender);
				pstmt.setString(8,birth);
				pstmt.setString(9,introduction);
				pstmt.executeUpdate();

				rs.close();
				pstmt.close();
				return true;
			}
		} catch (SQLException e) {
			return false;
		}
	}
	
	//--------------------------Controller 부분------------------------------------
	
	@Override
	public void init() throws ServletException {
		try {
			Class.forName(jdbc_driver).newInstance();
			con = DriverManager.getConnection(url, "root", "123123");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		super.init();
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		ArrayList<Member> mb = null;
		
		String btn = req.getParameter("btn");
		
		if(btn.equals("로그인")) {
			mb = doLogin(req);
			if(mb ==  null) {
				RequestDispatcher rd = req.getRequestDispatcher("output.jsp");
				req.setAttribute("msg", "비밀번호가 일치하지 않거나 존재하지 않는 ID입니다!!!");
				rd.forward(req, resp); 
			}
			else {
				RequestDispatcher rd = req.getRequestDispatcher("list.jsp");
				req.setAttribute("mb", mb);
				rd.forward(req, resp); 
			}
		}
		
		else if(btn.equals("검색")) {
			mb = doSearch(req);
			RequestDispatcher rd = req.getRequestDispatcher("list.jsp");
			req.setAttribute("mb", mb);
			rd.forward(req, resp); 
		}
		
		else if (btn.equals("목록")) {
			mb = getList(req);
			RequestDispatcher rd = req.getRequestDispatcher("list.jsp");
			req.setAttribute("mb", mb);
			rd.forward(req, resp); 
		}
		
		else if (btn.equals("확인")) {
			Member member = (Member)req.getSession().getAttribute("update");
			String pwd = req.getParameter("pwd");
			
			if(pwd.equals(member.getPwd())) {
				RequestDispatcher rd = req.getRequestDispatcher("member.jsp");
				req.setAttribute("action", "update");
				rd.forward(req, resp); 
			}
			else {
				RequestDispatcher rd = req.getRequestDispatcher("output.jsp");
				req.setAttribute("msg", "비밀번호가 일치하지 않습니다!!!");
				rd.forward(req, resp); 
			}
		}
		
		else if (btn.equals("수정")) {
			RequestDispatcher rd = req.getRequestDispatcher("pwcheck.jsp");
			rd.forward(req, resp); 
		}
		
		else if (btn.equals("가입")) {
			RequestDispatcher rd = req.getRequestDispatcher("member.jsp");
			rd.forward(req, resp); 
		}
		
		else if (btn.equals("전송")) {
			String act = (String)req.getSession().getAttribute("action");
			if (act=="update")
			{
				Member member = (Member)req.getSession().getAttribute("update");
				ArrayList<Member> mblist = (ArrayList<Member>)req.getSession().getAttribute("detail");
				int index = mblist.indexOf(member);

				mb = doUpdate(req);
				
				RequestDispatcher rd = req.getRequestDispatcher("detail.jsp?num="+index);
				rd.forward(req, resp); 
			}
			else {
				if(doInsert(req)) {						
					RequestDispatcher rd = req.getRequestDispatcher("output.jsp");
					req.setAttribute("msg", "가입에 성공했습니다.!!!");
					rd.forward(req, resp);
				}
				else {
					RequestDispatcher rd = req.getRequestDispatcher("output.jsp");
					req.setAttribute("msg", "이미 존재하는 아이디입니다!!!");
					rd.forward(req, resp); 
				}
			}
		}
	}
}
