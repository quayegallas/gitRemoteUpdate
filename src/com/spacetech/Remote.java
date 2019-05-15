package com.spacetech;

import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;

import java.io.*;

/**
 * @Description:
 * @Author: lz
 * @Date: 2019-05-15 09:11
 */
public class Remote extends AnAction {

	@Override
	public void actionPerformed(AnActionEvent e) {
		// TODO: insert action logic here
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
			line = reader.readLine();
			p = Runtime.getRuntime().exec(line + " remote update origin -p", null, new File(trueUrl));
			p.waitFor();
			inputStreamReader = new InputStreamReader(p.getInputStream(), "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			System.out.println(output.toString());
			Messages.showMessageDialog("正在更新..", "更新远端分支", Messages.getInformationIcon());
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

}
