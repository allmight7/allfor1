package com.itwill.winter;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 * 클라이언트의 모든요청을 받는 서블릿
 */
//@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
	private HandlerMapping handlerMap;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println(">>> 1.DispatcherServlet.init() 서블릿 생성자호출직후 단한번호출되는메쏘드");
		ServletContext context = this.getServletContext();
		/*
		<servlet>
	  		<servlet-class>com.itwill.winter.DispatcherServlet</servlet-class>
	  		<init-param>
	  			<param-name>controllerMappingLocation</param-name>
	  			<param-value>/WEB-INF/controller_mapping.properties</param-value>
	  		</init-param>
	  	</servlet> 
		 */
		String controllerMappingLocation=
				config.getInitParameter("controllerMappingLocation");
		String propertiesFileRealPath = 
				context.getRealPath(controllerMappingLocation);
		System.out.println(">>> 2.설정파일경로:"+propertiesFileRealPath);
		this.handlerMap=new HandlerMapping(propertiesFileRealPath);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}
	
	private void processRequest(
			HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		/*
		  1.클라이언트 요청분석
		  << 요청command >>
		   /guest_main.do
		   /guest_list.do
		   /guest_view.do
		   /guest_write_form.do
		   /guest_write_action.do
		   /guest_remove_action.do
		   /guest_modify_form.do
		   /guest_modify_action.do 
		 */
		String command = 
				request.getRequestURI()
					.substring(
							request.getContextPath().length());	
		System.out.println("1.request command:"+command);
		
		/*
		 * 2.요청command에따른 업무실행
		 */
		String forwardPath="";
		Controller controller=null;
		/****************************************/
		//HandlerMap객체로부터 요청command에해당하는
		//Controller객체반환
		controller=handlerMap.getController(command);
		/****************************************/
		/*
		 * 3.forward,redirect
		 */
		forwardPath = controller.handleRequest(request,response);
		/*
		 * ex> forward:/xxx.jsp
		 *     redirect:/xxx.do 
		 */
		String[] tempPath = forwardPath.split(":");
		String forwardString = tempPath[0];
		forwardPath = tempPath[1];
		
		if(forwardString.equals("redirect")){
			response.sendRedirect(forwardPath);
		}else if(forwardString.equals("forward")){
			String prefix="/WEB-INF/jsp/";
			String surffix=".jsp";
			forwardPath=prefix+forwardPath+surffix;
			RequestDispatcher rd=
					request.getRequestDispatcher(forwardPath);
			rd.forward(request, response);
		}
		
	}

}






