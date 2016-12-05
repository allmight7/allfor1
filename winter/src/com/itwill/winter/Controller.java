package com.itwill.winter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 * 요청command에의해서 실행되는 모든객체가
 * 구현해야하는 인터페이스
 */
public interface Controller {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response);

}