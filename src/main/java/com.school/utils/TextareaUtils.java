package com.school.utils;

import org.apache.http.util.TextUtils;
import sun.font.FontDesignMetrics;

import java.awt.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextareaUtils {
	private static final String ReturnRow = "\n";

	public static String convertTextareToHtml(String sourceTxt, String pageUrl)
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
				else if (isEndWithHTTP(line))//以http链接结束，需要换行
					isNeedAddBR = true;
			}

			line = addImageTag(line, pageUrl);

			if (isNeedAddBR)
				result += (line + "<br>");
			else
				result += line;
		}
		if (lines.length > 0)
			result += addImageTag(lines[lines.length - 1], pageUrl);
		result = convertStrongTag(result);
		return result;
	}

	private static Boolean isEndWithHTTP(String line)
	{
		Pattern pattern = Pattern.compile("(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]$");
		Matcher matcher = pattern.matcher(line);
		return matcher.find();
	}

	private static String addImageTag(String sourceTxt, String pageUrl)
	{
		if (TextUtils.isEmpty(sourceTxt))
			return sourceTxt;
		Pattern pattern = Pattern.compile("(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|](.png|.jpg)");
		Matcher matcher = pattern.matcher(sourceTxt);

		StringBuffer sbTest = new StringBuffer();
		Integer preStartPos = 0;

		while (matcher.find())
		{
			System.out.println(matcher.group());
			sbTest.append(sourceTxt.substring(preStartPos, matcher.start()));
			sbTest.append(String.format("<img src=\"%s\" />", matcher.group()));
			preStartPos = matcher.end();
		}
		sbTest.append(sourceTxt.substring(preStartPos));

		//<img src="/file/xxx/xxx.jpg">格式，补全url
		String imgContent = sbTest.toString();
		String regex2 = "\"/.*(.png|.jpg|.jpeg)\"";
		Pattern pattern2 =  Pattern.compile(regex2);
		Matcher matcher2 = pattern2.matcher(imgContent);
		if (matcher2.find()) {
			String temp = matcher2.group();
			temp = temp.substring(1, temp.length()-1);
			try {
				String domain = new URL(pageUrl).getHost();
				String imgUrl = String.format("\"http://%s%s\"", domain, temp);
				imgContent = imgContent.replaceAll(regex2, imgUrl);
			} catch (Exception e) {
//				return imgContent;
			}
		}
		return imgContent;
	}

	private static Boolean isEndChar(String line)
	{
		Pattern patPunc = Pattern.compile("[!)};>?！￥）}】”。？-]$");
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
