package com.example.inquiry;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
@WebServlet("/inquiry1")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class Inquiry1Servlet extends HttpServlet {
	private final InquiryDAO1 inquiryDAO1 = new InquiryDAO1();
	private static final String UPLOAD_DIRECTORY = "uploads";
	@Override
	public void init() throws ServletException {
		String uploadPath = getServletContext().getRealPath("/uploads");
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if ("abc".equals(action)) {
			req.setAttribute("inquiries1", inquiryDAO1.getAllInquiries1());
			RequestDispatcher rd = req.getRequestDispatcher("/jsp/inquiry_a.jsp");
			rd.forward(req, resp);
		} else {
			generateCaptcha(req);
			RequestDispatcher rd = req.getRequestDispatcher("jsp/touroku.jsp");
			rd.forward(req, resp);
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		if (action != null && action.equals("complete")) {
			Inquiry1 inquiry1 = (Inquiry1) session.getAttribute("inquiry1");
			if (inquiry1 != null) {
				inquiryDAO1.addInquiry1(inquiry1);
			}
			session.removeAttribute("inquiry");
			RequestDispatcher rd = req.getRequestDispatcher("/jsp/okuru.jsp");
			rd.forward(req, resp);
		} else if (action != null && action.equals("updateStatus")) {
			int index = Integer.parseInt(req.getParameter("index"));
			String newStatus = req.getParameter("newStatus");
			inquiryDAO1.updateInquiry1Status(index, newStatus);
			resp.sendRedirect("inquiry1?action=history");
		} else {
			String name = req.getParameter("name");
			String email = req.getParameter("email");
			String content = req.getParameter("content");
			String captchaInput = req.getParameter("captcha");
			String fileName = null;
			try {
				Part filePart = req.getPart("attachment");
				if (filePart != null && filePart.getSize() > 0) {
					fileName = getFileName(filePart);
					String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
					filePart.write(uploadPath + File.separator + fileName);
				}
			} catch (Exception e) {
				req.setAttribute("errorMessage", "ファイルのアップロード中にエラーが発生しました: " + e.getMessage());
			}
			Inquiry1 inquiry1 = new Inquiry1();
			inquiry1.setName(name);
			inquiry1.setEmail(email);
			inquiry1.setContent(content);
			inquiry1.setAttachmentFileName(fileName);
			Map<String, String> errors = new HashMap<>();
			if (name == null || name.trim().isEmpty()) {
				errors.put("name", "名前は必須です。");
			}
			if (email == null || email.trim().isEmpty()) {
				errors.put("email", "メールアドレスは必須です。");
			} else if (!email.matches("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,6}$")) {
				errors.put("email", "有効なメールアドレスを入力してください。");
			}
			if (content == null || content.trim().isEmpty()) {
				errors.put("content", "内容は必須です。");
			}
			if (!verifyCaptcha(req, captchaInput)) {
				errors.put("captcha", "CAPTCHA が不正です。");
			}
			if (!errors.isEmpty()) {
				req.setAttribute("errors", errors);
				req.setAttribute("inquiry", inquiry1);
				generateCaptcha(req);
				RequestDispatcher rd = req.getRequestDispatcher("jsp/touroku.jsp");
				rd.forward(req, resp);
			} else {
				session.setAttribute("inquiry", inquiry1);
				req.setAttribute("name", name);
				req.setAttribute("email", email);
				req.setAttribute("content", content);
				req.setAttribute("attachmentFileName", fileName);
				RequestDispatcher rd = req.getRequestDispatcher("/jsp/kakuninn.jsp");
				rd.forward(req, resp);
			}
		}
	}
	private void generateCaptcha(HttpServletRequest req) {
		Random rand = new Random();
		int num1 = rand.nextInt(10) + 1;
		int num2 = rand.nextInt(10) + 1;
		int answer = num1 + num2;
		req.getSession().setAttribute("captchaAnswer", answer);
		req.setAttribute("captchaQuestion", num1 + " + " + num2 + " = ?");
	}
	private boolean verifyCaptcha(HttpServletRequest req, String userAnswerStr) {
		HttpSession session = req.getSession(false);
		if (session == null)
			return false;
		Integer captchaAnswer = (Integer) session.getAttribute("captchaAnswer");
		if (captchaAnswer == null || userAnswerStr == null || userAnswerStr.isEmpty()) {
			return false;
		}
		try {
			int userAnswer = Integer.parseInt(userAnswerStr);
			return captchaAnswer.equals(userAnswer);
		} catch (NumberFormatException e) {
			return false;
		}
	}
	private String getFileName(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}