package com.school.utils;

import org.apache.http.util.TextUtils;
import sun.font.FontDesignMetrics;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextareaUtils {
	private static final String ReturnRow = "\n";

	public static String convertTextareToHtml(String sourceTxt)
	{
		if (TextUtils.isEmpty(sourceTxt))
			return sourceTxt;

		String[] lines = sourceTxt.split(ReturnRow);
		Font f = new Font("宋体", Font.PLAIN, 10);
		FontMetrics fm = FontDesignMetrics.getMetrics(f);

		Integer maxWidth = 0;
		for (String line : lines)
		{
			Integer width = fm.stringWidth(line);
			if (width > maxWidth)
				maxWidth = width;//获取文字在屏幕上显示的最大长度
		}

		Integer percentWidth = (int)(0.7 * maxWidth);// 70%的宽度

		String result = "";
		for (int ii = 0; ii < lines.length - 1; ++ii)
		{
			String line = lines[ii];

			Integer width = fm.stringWidth(line);
			Boolean isNeedAddBR = false;//是否需要换行

			if (width <= percentWidth)
				isNeedAddBR = true;
			else
			{//检查是否需要换行
				String nextLine = lines[ii + 1];
				if (nextLine.length() == 0 || nextLine.charAt(0) == ' ')//下一行空，或下一行空格开头
					isNeedAddBR = true;
				else if (isEndChar(line))//最后一个字符是结束行
					isNeedAddBR = true;
			}

			if (isNeedAddBR)
				result += (line + "<br>");
			else
				result += line;
		}
		if (lines.length > 0)
			result += lines[lines.length - 1];
		result = convertStrongTag(result);
		return result;
	}

	private static Boolean isEndChar(String line)
	{
		Pattern patPunc = Pattern.compile("[!)};>?！￥）}】”。？]$");
		Matcher matcher = patPunc.matcher(line);
		return matcher.find();
	}

	private static String convertStrongTag(String sourceTxt)
	{
		String txt = sourceTxt.replace("[b]", "<b>");
		txt = txt.replace("[/b]", "</b>");
		return txt;
	}
}
