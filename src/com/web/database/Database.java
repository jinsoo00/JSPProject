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
	
	//--------------------------Model �κ�------------------------------------
	
	//login �κ� - id, pwd�� Ȯ���� �� Select sql�� �����ؼ� �������Ʈ�� ��ȯ�Ѵ�. (Ʋ���� null ��ȯ)
	public ArrayList<Member> doLogin(HttpServletRequest req) {
		String id = req.getParameter("id");
		String pwd = req.getParameter("pwd");
		String db_id="";
		String db_pwd="";
		String select_sql = "select id,pwd from databasetest.member where id=? and pwd=?";
	
		ArrayList<Member> mb = new ArrayList<Member>();
		
		try {
			//id, pwd Ȯ�� �κ�
			PreparedStatement pstmt = con.prepareStatement(select_sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){					
				db_id = rs.getString("id");
				db_pwd = rs.getString("pwd");
			}
			
			if(db_id.equals(id) && db_pwd.equals(pwd)) {
				
				//Select sql�� �����ؼ� �������Ʈ�� ��ȯ�ϴ� �κ�
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
			//Ʋ���� null�� ��ȯ�ϴ� �κ�
			rs.close();
			pstmt.close();
			return null;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	//Search �κ� - �˻� �а��� Ȯ���� �� Select sql�� �����ؼ� �������Ʈ�� ��ȯ�Ѵ�.
	public ArrayList<Member> doSearch(HttpServletRequest req) {
		String dept = req.getParameter("f");
		String select_sql = "";
		ArrayList<Member> mb = new ArrayList<Member>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//�а��� default�� ��� ���� ��ȯ
			if (dept.equals("default")) {
				select_sql = "select id,pwd,name,tel,email,dept,gender,birth,introduction from databasetest.member";
				pstmt = con.prepareStatement(select_sql);
				rs = pstmt.executeQuery();
			}
			//�а��� ���������� ��� Ư�� �а��� ��ȯ
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
	
	//List �κ� - Select sql�� �����ؼ� �ɹ�����Ʈ�� ���� ��ȯ�Ѵ�.
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
	
	//Updatae �κ� - update�� Member��ü�� ������ Update sql�� �����ؼ� ������Ʈ ���� �������Ʈ�� ������Ʈ�� ��ȯ�Ѵ�.
	public ArrayList<Member> doUpdate(HttpServletRequest req) {
		// update�� Member��ü�� �������� �κ�
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
			
			//Updata sql�� �����ؼ� ������Ʈ�ϴ� �κ�
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
			
			//�������Ʈ�� ������Ʈ �� �� ��ȯ�ϴ� �κ�
			int index = mb.indexOf(member);
			mb.set(index, new_mb);
			
			rs.close();
			pstmt.close();
			return mb;
		} catch (SQLException e) {
			return null;
		}
	}
	
	//Insert �κ� - �̹� �ִ� �����Ϳ� ���� Insert sql���� �̿��� ���� �� ����� ��ȯ�ϴ� �κ� (�̹� ������ false��ȯ)
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
			
			//�̹� �ִ� �����Ϳ� �� �ϴ� �κ�
			pstmt = con.prepareStatement(select_sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()){					
				db_id = rs.getString("id");
			}
			
			if(db_id.equals(id)) {
				//�̹� ������ false��ȯ�ϴ� �κ�
				rs.close();
				pstmt.close();
				return false;
			}
			else {
				//Insert sql���� �̿��� ���� �ϰ� ����� ��ȯ�ϴ� �κ�
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
	
	//--------------------------Controller �κ�------------------------------------
	
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
		
		if(btn.equals("�α���")) {
			mb = doLogin(req);
			if(mb ==  null) {
				RequestDispatcher rd = req.getRequestDispatcher("output.jsp");
				req.setAttribute("msg", "��й�ȣ�� ��ġ���� �ʰų� �������� �ʴ� ID�Դϴ�!!!");
				rd.forward(req, resp); 
			}
			else {
				RequestDispatcher rd = req.getRequestDispatcher("list.jsp");
				req.setAttribute("mb", mb);
				rd.forward(req, resp); 
			}
		}
		
		else if(btn.equals("�˻�")) {
			mb = doSearch(req);
			RequestDispatcher rd = req.getRequestDispatcher("list.jsp");
			req.setAttribute("mb", mb);
			rd.forward(req, resp); 
		}
		
		else if (btn.equals("���")) {
			mb = getList(req);
			RequestDispatcher rd = req.getRequestDispatcher("list.jsp");
			req.setAttribute("mb", mb);
			rd.forward(req, resp); 
		}
		
		else if (btn.equals("Ȯ��")) {
			Member member = (Member)req.getSession().getAttribute("update");
			String pwd = req.getParameter("pwd");
			
			if(pwd.equals(member.getPwd())) {
				RequestDispatcher rd = req.getRequestDispatcher("member.jsp");
				req.setAttribute("action", "update");
				rd.forward(req, resp); 
			}
			else {
				RequestDispatcher rd = req.getRequestDispatcher("output.jsp");
				req.setAttribute("msg", "��й�ȣ�� ��ġ���� �ʽ��ϴ�!!!");
				rd.forward(req, resp); 
			}
		}
		
		else if (btn.equals("����")) {
			RequestDispatcher rd = req.getRequestDispatcher("pwcheck.jsp");
			rd.forward(req, resp); 
		}
		
		else if (btn.equals("����")) {
			RequestDispatcher rd = req.getRequestDispatcher("member.jsp");
			rd.forward(req, resp); 
		}
		
		else if (btn.equals("����")) {
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
					req.setAttribute("msg", "���Կ� �����߽��ϴ�.!!!");
					rd.forward(req, resp);
				}
				else {
					RequestDispatcher rd = req.getRequestDispatcher("output.jsp");
					req.setAttribute("msg", "�̹� �����ϴ� ���̵��Դϴ�!!!");
					rd.forward(req, resp); 
				}
			}
		}
	}
}
