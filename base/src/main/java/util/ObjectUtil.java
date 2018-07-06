package util;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ObjectUtil {
	public ObjectUtil() {
	}

	public static Boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		} else if (obj instanceof CharSequence) {
			return ((CharSequence)obj).length() == 0;
		} else if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
		} else if (obj instanceof Collection) {
			return ((Collection)obj).isEmpty();
		} else {
			return obj instanceof Map ? ((Map)obj).isEmpty() : false;
		}
	}

	public static Boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	/**
	 * 获取实体类所有字段值
	 *
	 * @author lx
	 * @since 2018-4-18 16:58:53
	 * @param object
	 * @throws Exception
	 */
	public static String getObjectValue(Object object) throws Exception {
		String res = "";
		Object val = null;
		try {
			if (null != object) {
				Class<?> clz = object.getClass();// 拿到该类
				Field[] fields = clz.getDeclaredFields();// 获取实体类的所有属性，返回Field数组
				for (int i = 0; i < fields.length; i++) {
					Method m = object.getClass().getMethod("get" + getMethodName(fields[i].getName()));
					val = m.invoke(object);
					res += ((null == val) ? "" : val) + (i + 1 == fields.length ? "" : "~");
				}
			}
		} catch (Exception e) {
			throw new Exception("getObjectValue（获取实体类所有字段）");
		}
		return res;
	}

	/**
	 * 第一个字母转大写
	 *
	 * @author lx
	 * @since 2018-4-18 16:58:35
	 * @param str
	 */
	private static String getMethodName(String str) throws Exception {
		str = str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
		return str;
	}

	/**
	 * 将实体类转换成map【公共方法】--lx--2016-9-7 17:49:29
	 *
	 * @param obj
	 *            实体类
	 * @return
	 */
	public static HashMap<String, String> transBean2Map(Object obj) {
		if (null==obj) {
			return null;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName().substring(0, 1).toUpperCase()
						+ property.getName().substring(1);
				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					if (null==value) {
						continue;
					}
					map.put(key,(String) value);
				}

			}
		} catch (Exception e) {
		}
		return map;
	}

	/**
	 * 判断实体类种是否存在某个属性
	 * @param clazz 实体类
	 * @param field 属性
	 * @return 是否存在
	 */
	public static boolean haveField(Class clazz, String field) {
		try {
			clazz.getDeclaredField(field);
			return true;
		} catch (NoSuchFieldException e) {
			return false;
		}
	}
}
