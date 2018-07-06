package util.httpClient;

/***
 * http请求类型
 * 
 * @author laidan
 * @since 2017-01-04
 */
public class QtHttpItem extends QtHttpRequest {

	public QtMethod mehtod;// 请求方式默认为GET方式

	public QtHttpItem(String url) {
		super(url);
		mehtod = QtMethod.GET;
	}

	public enum QtMethod {
		GET, POST
	}
}
