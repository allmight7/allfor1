package com.itwill.winter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 * ��ûcommand�����ؼ� ����Ǵ� ��簴ü��
 * �����ؾ��ϴ� �������̽�
 */
public interface Controller {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response);

}