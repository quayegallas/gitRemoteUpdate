package com.spacetech;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import java.io.*;

/**
 * @Description:
 * @Author: lz
 * @Date: 2019-05-15 09:11
 */
public class Remote extends AnAction {

	@Override
	public void actionPerformed(AnActionEvent e) {
		StringBuffer output = new StringBuffer();
		Process p;
		Project project = e.getProject();
		String url = project.getProjectFilePath();
		String trueUrl = url.substring(0, url.indexOf("/.idea"));
		String headName = "";
		try {
			p = Runtime.getRuntime().exec(new String[] { "/bin/bash", "-c", "git remote update origin -p" }, null,
					new File(trueUrl));
			BufferedReader shellErrorResultReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			BufferedReader shellInfoResultReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String infoLine;
			while ((infoLine = shellInfoResultReader.readLine()) != null) {
				output.append("分支更新信息:{" + infoLine + "}\n");
			}
			String errorLine;
			while ((errorLine = shellErrorResultReader.readLine()) != null) {
				output.append("分支更新:{" + errorLine + "}\n");
			}
			// 等待程序执行结束并输出状态
			Integer exitCode = p.waitFor();
			if (0 == exitCode) {
				output.append("分支更新成功:{" + exitCode + "}\n");
			} else {
				output.append("分支更新失败:{" + exitCode + "}\n");
			}
			p = Runtime.getRuntime().exec(new String[] { "/bin/bash", "-c", "git symbolic-ref --short -q HEAD" }, null,
					new File(trueUrl));
			shellErrorResultReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			shellInfoResultReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((infoLine = shellInfoResultReader.readLine()) != null) {
				headName = infoLine;
			}
			p = Runtime.getRuntime().exec(new String[] { "/bin/bash", "-c", "git branch -u origin/" + headName }, null,
					new File(trueUrl));
			shellErrorResultReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			shellInfoResultReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((infoLine = shellInfoResultReader.readLine()) != null) {
				output.append(infoLine);
			}
			while ((infoLine = shellErrorResultReader.readLine()) != null) {
				output.append(infoLine);
			}
			p.destroy();
			Messages.showMessageDialog("正在更新..\n" + output.toString(), "更新远端分支", Messages.getInformationIcon());
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

}
