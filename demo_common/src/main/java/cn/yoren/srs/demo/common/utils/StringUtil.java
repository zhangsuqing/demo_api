package cn.yoren.srs.demo.common.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

public class StringUtil extends StringUtils {

	/***************************************************************************
	 * 格式化数字
	 * 
	 * @param digit
	 * 			数字
	 * @param num
	 * 			精确小数位数
	 * @return
	 */
	public static String digitFormat(double digit, int num) {
		java.text.DecimalFormat df = null;
		switch (num) {
		case 0:
			df = new java.text.DecimalFormat("#0");
			break;
		case 1:
			df = new java.text.DecimalFormat("#0.0");
			break;
		case 2:
			df = new java.text.DecimalFormat("#0.00");
			break;
		case 3:
			df = new java.text.DecimalFormat("#0.000");
			break;
		case 4:
			df = new java.text.DecimalFormat("#0.0000");
			break;
		default:
			df = new java.text.DecimalFormat("#0");
			break;
		}
		return df.format(digit);
	}

	/***************************************************************************
	 * 处理JSON格式数据的特殊字符
	 * 
	 * @param jsonData
	 *            json数据
	 * @return
	 */
	public static String handleJsonData(String jsonData) {
		if (isEmpty(jsonData))
			return jsonData;
		jsonData = jsonData.replaceAll("\n", "").replaceAll("\r", "");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < jsonData.length(); i++) {
			char c = jsonData.charAt(i);
			switch (c) {
			case '\"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '\'':
				sb.append("\\'");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/****
	 * 把字符串转成utf8编码
	 * 
	 * @param s
	 * @return
	 */
	public static String handleToUtf8String(String s) {
		if (isEmpty(s))
			return "default";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	public static boolean isBlankOr(Object... objects) {

		int length = objects.length;
		for (int i = 0; i < length; i++) {
			Object obj = objects[i];
			if (isBlankObj(obj))
				return true;
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	public static boolean isBlankObj(Object obj) {
		if (null == obj) {
			return true;
		}
		if (obj instanceof String) {
			if (isBlank(obj.toString()) || "null".equals(obj.toString()))
				return true;
		}
		if (obj instanceof List) {
			List ls = (List) obj;
			if (ls.isEmpty())
				return true;
		}
		if (obj instanceof Map) {
			Map m = (Map) obj;
			if (m.isEmpty())
				return true;
		}
		if (obj instanceof Set) {
			Set s = (Set) obj;
			if (s.isEmpty())
				return true;
		}
		if (obj instanceof Object[]) {
			Object[] array = (Object[]) obj;
			if (array.length == 0)
				return true;
		}

		return false;
	}

	public static boolean isNotBlankObj(Object obj) {
		return !isBlankObj(obj);
	}

	public static boolean isNotBlankOr(Object... objects) {
		return !isBlankOr(objects);
	}

	/*********
	 * 得到全球唯一的32位UUID编号
	 * 
	 * @return
	 */
	public static String getUUID32() {
		return getUUID36().replaceAll("-", "");
	}

	/*********
	 * 得到全球唯一的36位UUID编号
	 * 
	 * @return
	 */
	public static String getUUID36() {
		return java.util.UUID.randomUUID().toString();
	}
}
