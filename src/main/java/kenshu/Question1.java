package kenshu;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/Question1"})
public class Question1 extends HttpServlet {
	public void doGet(
			HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		// 文字コードをセット
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		//Page.header(out);

		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(
					"java:/comp/env/jdbc/kenshu");
			Connection con = ds.getConnection();

			PreparedStatement st = con.prepareStatement(
					"SELECT * FROM STUDENT");
			ResultSet rs = st.executeQuery();
			out.println("<h1>STUDENTテーブル</h1>");
			out.println("<table border=1><th>STUDENT_ID</th><th>STUDENT_NAME</th><th>COURSE_ID</th>");

			while (rs.next()) {
				out.println("<tr>");
				out.println("<td>" + rs.getInt("STUDENT_ID") + "</td>");
				out.println("<td>" + rs.getString("STUDENT_NAME") + "</td>");
				out.println("<td>" + rs.getInt("COURSE_ID") + "</td>");
				out.println("</tr>");
			}
			out.println("</table>");
			st.close();

			PreparedStatement st2 = con.prepareStatement(
					"SELECT * FROM COURSE");
			ResultSet rs2 = st2.executeQuery();
			out.println("<h1>COURSEテーブル</h1>");
			out.println("<table border=1><th>COURSE_ID</th><th>COURSE_NAME</th>");
			while (rs2.next()) {
				out.println("<tr>");
				out.println("<td>" + rs2.getInt("COURSE_ID") + "</td>");
				out.println("<td>" + rs2.getString("COURSE_NAME") + "</td>");
				out.println("</tr>");
			}
			out.println("</table>");

			st2.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace(out);

		}

		//Page.footer(out);
	}
}
