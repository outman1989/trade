package util.httpClient;

import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCookieStore;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * http请求类
 * 
 * @author laidan
 * @since 2017-01-04
 */
public class QtHttpRequest {
	
	public String url;// 请求URL必须填写
	public int timeout;// 默认请求超时时间
	public int keepAlive;// 资源建立持久性连接时间
	public boolean redirectsEnabled;// 是否自动重定向 默认 false	
	public Map<String, String> headers;// 标头信息
	public String postCharset;// 默认UTF-8
	public ContentType postContentType;
	public List<NameValuePair> nvps;// 表单提交数据,etc:username=Jack&password=123456
	public String postData;// text json xml等数据
	public List<File> postFile;// 文件
	public QtHttpProxy proxy;// ip代理
	public CookieStore cookieStore;
	private int socketTimeout;// 默认请求超时时间
	private int connectTimeout;// 默认请求超时时间
	private int connectionRequestTimeout;// 默认请求超时时间

	public QtHttpRequest(String url) {
		this.url = url;
		timeout = 15000;
		socketTimeout = 15000;
		connectTimeout = 15000;
		connectionRequestTimeout = 15000;
		keepAlive = -60000;// 如果服务器没有设置keep-alive这个参数，我们就把它设置成1分钟
		headers = new HashMap<String, String>();
		nvps = new ArrayList<NameValuePair>();
		postCharset = "UTF-8";
		cookieStore = new BasicCookieStore();
		if (null != postContentType) {
			if (null != postContentType.getCharset()) {
				postContentType.withCharset(postCharset);// (Charset.forName("UTF-8"));
			}
		} else {
			postContentType = ContentType.DEFAULT_TEXT;
			postContentType.withCharset(postCharset);
		}
	}

	/**
	 * 组合Cookie
	 * 
	 * @param cookies
	 * @return
	 */
	public void putCookieStores(CookieStore cookies) {
		if (null != cookies) {
			cookies.getCookies().forEach(y -> {
				cookieStore.addCookie(y);
			});
		}
	}

	public void putCookieStores(String cookies) {
		if (null != cookies) {
			// BasicClientCookie cookie = new BasicClientCookie(name, value);
		}
	}

	// 超时
	private int getTimeout(int time) {
		return time == 0 ? timeout : time;
	}

	public int getSocketTimeout() {
		return getTimeout(socketTimeout);
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public int getConnectTimeout() {
		return getTimeout(connectTimeout);
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getConnectionRequestTimeout() {
		return getTimeout(connectionRequestTimeout);
	}

	public void setConnectionRequestTimeout(int connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
	}

}
