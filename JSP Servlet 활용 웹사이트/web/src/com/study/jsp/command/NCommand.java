package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface NCommand {
	void execute(HttpServletRequest request, HttpServletResponse response);
}
