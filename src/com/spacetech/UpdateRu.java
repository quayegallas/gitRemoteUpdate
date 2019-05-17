package com.spacetech;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.terminal.JBTerminalPanel;

import javax.swing.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: lz
 * @Date: 2019-05-15 17:38
 */
public class UpdateRu extends Thread {
	AnActionEvent e;

	public void setE(AnActionEvent e) {
		this.e = e;
	}

	@Override
	public void run() {
		StringBuffer output = new StringBuffer();
		Process p;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		Project project = e.getProject();
		String url = project.getProjectFilePath();
		String trueUrl = url.substring(0, url.indexOf("/.idea"));
		String line = "";
		try {
			p = Runtime.getRuntime().exec("which git");
			inputStreamReader = new InputStreamReader(p.getInputStream(), "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String git = reader.readLine();

			ToolWindow terminal = ToolWindowManager.getInstance(e.getProject()).getToolWindow("Terminal");

		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				if (reader != null) {
					reader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
