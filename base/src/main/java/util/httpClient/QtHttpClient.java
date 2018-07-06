package util.httpClient;

import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.*;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.cookie.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.*;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.CookieSpecBase;
import org.apache.http.impl.cookie.DefaultCookieSpecProvider;
import org.apache.http.impl.cookie.RFC6265CookieSpecProvider;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriterFactory;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.*;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.CodingErrorAction;
import java.util.*;

/***
 * httpclient
 * 已重写类
 *
 * @author laidan
 * @since 2017-01-04
 */
public class QtHttpClient {

    public String defaultUserAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36 QtHttpClient/1.1.0";
    public CookieStore cookieStore = new BasicCookieStore();// Use custom cookie store if necessary.
    private PoolingHttpClientConnectionManager connManager;
    private ConnectionKeepAliveStrategy keepAliveStrategy;
    private CredentialsProvider credentialsProvider;
    private RequestConfig defaultRequestConfig;
    private QtHttpProxy qtProxy;
    private CloseableHttpClient httpclient;
    private String cookieType = "easy";
    private boolean isRun = false;
    public int maxTotal = 1000;
    public int defaultMaxPerRoute = 10;
    public int maxPerRoute = 20;
    public int socketTimeout = 5000;
    public int connectTimeout = 5000;
    public int connectionRequestTimeout = 5000;
    public int keepAliveTimeout;

    public QtHttpClient() {
        loadCustomHTTpClient(null);
    }

    public QtHttpClient(QtHttpProxy qtProxy) {
        HttpHost proxyHttpHost = null;
        if (null != qtProxy) {
            proxyHttpHost = (new HttpHost(qtProxy.getHostName(), qtProxy.getPort()));
        }
        loadCustomHTTpClient(proxyHttpHost);
        if (null != qtProxy) {
            addAuthProxy(qtProxy);
        }
    }

    /**
     * 初始化方法
     *
     * @param defaultProxyHttpHost 代理ip
     */
    private void loadCustomHTTpClient(HttpHost defaultProxyHttpHost) {
        // Use custom message parser / writer to customize the way HTTP
        // messages are parsed from and written out to the data stream.
        HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {
            @Override
            public HttpMessageParser<HttpResponse> create(SessionInputBuffer buffer, MessageConstraints constraints) {
                LineParser lineParser = new BasicLineParser() {
                    @Override
                    public Header parseHeader(final CharArrayBuffer buffer) {
                        try {
                            return super.parseHeader(buffer);
                        } catch (ParseException ex) {
                            return new BasicHeader(buffer.toString(), null);
                        }
                    }

                };
                return new DefaultHttpResponseParser(buffer, lineParser, DefaultHttpResponseFactory.INSTANCE, constraints) {
                    @Override
                    protected boolean reject(final CharArrayBuffer line, int count) {
                        // try to ignore all garbage preceding a status line
                        // infinitely
                        return false;
                    }
                };
            }
        };
        HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();

        // Use a custom connection factory to customize the process of
        // initialization of outgoing HTTP connections. Beside standard
        // connection
        // configuration parameters HTTP connection factory can define message
        // parser / writer routines to be employed by individual connections.
        HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(requestWriterFactory, responseParserFactory);
        // Client HTTP connection objects when fully initialized can be bound to
        // an arbitrary network socket. The process of network socket
        // initialization,
        // its connection to a remote address and binding to a local one is
        // controlled
        // by a connection socket factory.
        // SSL context for secure connections can be created either based on
        // system or application specific properties.
        SSLContext sslcontext = SSLContexts.createSystemDefault();
        // Create a registry of custom connection socket factories for supported
        // protocol schemes.
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext)).build();
        // BrowserCompatSpec
        CookieSpecProvider easySpecProvider = new CookieSpecProvider() {
            @Override
            public CookieSpec create(HttpContext httpcontext) {
                return new CookieSpecBase() {
                    public List<Cookie> parse(Header header, CookieOrigin origin) throws MalformedCookieException {
                        final HeaderElement[] elems = header.getElements();
                        final List<Cookie> cookies = new ArrayList<Cookie>(elems.length);
                        for (final HeaderElement headerelement : elems) {
                            final String name = headerelement.getName();
                            final String value = headerelement.getValue();
                            if (value == null) {
                                continue;
                            }
                            if (name == null || name.length() == 0) {
                                throw new MalformedCookieException("Cookie name may not be empty");
                            }
                            final BasicClientCookie cookie = new BasicClientCookie(name, value);
                            cookie.setPath(getDefaultPath(origin));
                            cookie.setDomain(getDefaultDomain(origin));
                            // cycle through the parameters
                            final NameValuePair[] attribs = headerelement.getParameters();
                            for (int j = attribs.length - 1; j >= 0; j--) {
                                final NameValuePair attrib = attribs[j];
                                final String s = attrib.getName().toLowerCase(Locale.ENGLISH);
                                cookie.setAttribute(s, attrib.getValue());
                                final CookieAttributeHandler handler = findAttribHandler(s);
                                if (handler != null) {
                                    handler.parse(cookie, attrib.getValue());
                                }
                            }
                            cookies.add(cookie);
                        }
                        return cookies;
                    }

                    private boolean isQuoteEnclosed(String s) {
                        return s != null && s.startsWith("\"") && s.endsWith("\"");
                    }

                    public List<Header> formatCookies(List<Cookie> cookies) {
                        Args.notEmpty(cookies, "List of cookies");
                        CharArrayBuffer buffer = new CharArrayBuffer(20 * cookies.size());
                        buffer.append("Cookie");
                        buffer.append(": ");
                        for (int i = 0; i < cookies.size(); i++) {
                            Cookie cookie = (Cookie) cookies.get(i);
                            if (i > 0)
                                buffer.append("; ");
                            String cookieName = cookie.getName();
                            String cookieValue = cookie.getValue();
                            if (cookie.getVersion() > 0 && !isQuoteEnclosed(cookieValue)) {
                                BasicHeaderValueFormatter.INSTANCE.formatHeaderElement(buffer,
                                        new BasicHeaderElement(cookieName, cookieValue), false);
                                continue;
                            }
                            buffer.append(cookieName);
                            buffer.append("=");
                            if (cookieValue != null)
                                buffer.append(cookieValue);
                        }
                        List<Header> headers = new ArrayList<Header>(1);
                        headers.add(new BufferedHeader(buffer));
                        return headers;
                    }

                    public int getVersion() {
                        return 0;
                    }

                    public Header getVersionHeader() {
                        return null;
                    }

                    public String toString() {
                        return "compatibility";
                    }
                };
            }
        };
        PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.getDefault();
        // Create a registry of custom cookie for supported
        Registry<CookieSpecProvider> cookieSpecRegistry = RegistryBuilder.<CookieSpecProvider>create()
                .register(CookieSpecs.DEFAULT, (CookieSpecProvider) new DefaultCookieSpecProvider(publicSuffixMatcher))
                .register(CookieSpecs.STANDARD, new RFC6265CookieSpecProvider(publicSuffixMatcher))
                .register(cookieType, easySpecProvider).build();
        // Use custom DNS resolver to override the system DNS resolution.
        DnsResolver dnsResolver = new SystemDefaultDnsResolver() {
            @Override
            public InetAddress[] resolve(final String host) throws UnknownHostException {
                if (host.equalsIgnoreCase("myhost")) {
                    return new InetAddress[]{InetAddress.getByAddress(new byte[]{127, 0, 0, 1})};
                } else {
                    return super.resolve(host);
                }
            }
        };

        // Create a connection manager with custom configuration.
        connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, connFactory, dnsResolver);
        // Create socket configuration
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
        // Configure the connection manager to use socket configuration either
        // by default or for a specific host.
        connManager.setDefaultSocketConfig(socketConfig);
        connManager.setSocketConfig(new HttpHost("somehost", 80), socketConfig);// $$$
        // Validate connections after 1 sec of inactivity
        connManager.setValidateAfterInactivity(1000);
        // Create message constraints
        MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200)
                .setMaxLineLength(2000).build();
        // Create connection configuration
        ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
                .setMessageConstraints(messageConstraints).build();
        // Configure the connection manager to use connection configuration
        // either
        // by default or for a specific host.
        connManager.setDefaultConnectionConfig(connectionConfig);
        connManager.setConnectionConfig(new HttpHost("somehost", 80), ConnectionConfig.DEFAULT);// $$$
        // Configure total max or per route limits for persistent connections
        // that can be kept in the pool or leased by the connection manager.
        connManager.setMaxTotal(maxTotal);
        connManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        connManager.setMaxPerRoute(new HttpRoute(new HttpHost("somehost", 80)), maxPerRoute);// $$$
        // Use custom credentials provider if necessary.
        credentialsProvider = new BasicCredentialsProvider();
        // Create global request configuration
        defaultRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).setExpectContinueEnabled(true)
                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
        keepAliveStrategy = new DefaultConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                long keepAlive = super.getKeepAliveDuration(response, context);
                // HttpHost target = (HttpHost)
                // context.getAttribute(HttpClientContext.HTTP_TARGET_HOST);
                // target.getHostName()
                if (keepAlive == -1 || keepAliveTimeout > 0) {
                    // 如果服务器没有设置keep-alive这个参数，我们就把它设置成1分钟
                    keepAlive = keepAliveTimeout;
                }
                return keepAlive;
            }
        };
        // Lookup<CookieSpecProvider> cookieSpecRegistry=new
        // Lookup<CookieSpecProvider>() {public CookieSpecProvider lookup(String
        // s) {System.out.println(s);return null;}};
        // Create an HttpClient with the given custom dependencies and
        // configuration.
        httpclient = HttpClients.custom().setConnectionManager(connManager)
                .setDefaultCookieSpecRegistry(cookieSpecRegistry).setDefaultCookieStore(cookieStore)
                .setKeepAliveStrategy(keepAliveStrategy).setUserAgent(defaultUserAgent)
                .setDefaultCredentialsProvider(credentialsProvider).setProxy(defaultProxyHttpHost)
                .setDefaultRequestConfig(defaultRequestConfig).build();
    }

    /**
     * GET(Main)
     *
     * @param request
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public QtHttpResult get(QtHttpRequest request) throws ClientProtocolException, IOException {
        QtHttpResult qhr = new QtHttpResult();
        keepAliveTimeout = request.keepAlive;
        String url = request.url;
        HttpHost otherProxyHttpHost = null;
        if (null != request.proxy) {
            otherProxyHttpHost = new HttpHost(request.proxy.getHostName(), request.proxy.getPort());
        }
        try {
            HttpGet httpget = new HttpGet(url);
            // Request configuration can be overridden at the request level.
            // They will take precedence over the one set at the client level.
            RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
                    .setSocketTimeout(request.getSocketTimeout()).setConnectTimeout(request.getConnectTimeout())
                    .setConnectionRequestTimeout(request.getConnectionRequestTimeout()).setProxy(otherProxyHttpHost)
                    .setRedirectsEnabled(request.redirectsEnabled).setCookieSpec(cookieType).build();
            httpget.setConfig(requestConfig);
            request.headers.forEach((key, value) -> {
                httpget.addHeader(key, value);
            });
            // Execution context can be customized locally.
            HttpClientContext context = HttpClientContext.create();
            // Contextual attributes set the local context level will take
            // precedence over those set at the client level.
            if (null != request.cookieStore && !request.cookieStore.getCookies().isEmpty()) {
                context.setCookieStore(request.cookieStore);
            }
            context.setCredentialsProvider(credentialsProvider);
            CloseableHttpResponse response = httpclient.execute(httpget, context);
            try {
                qhr = getHttpResult(context, response);
                httpget.abort();
            } finally {
                response.close();
            }
        } finally {
            closeHttpClient();
        }
        return qhr;
    }

    /**
     * GET
     *
     * @param url
     * @param proxyIP
     * @param proxyPort
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public QtHttpResult get(String url, String proxyIP, int proxyPort) throws ClientProtocolException, IOException {
        QtHttpRequest request = new QtHttpRequest(url) {
            {
                proxy = new QtHttpProxy(proxyIP, proxyPort);
            }
        };
        return get(request);
    }

    /**
     * GET
     *
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public QtHttpResult get(String url) throws ClientProtocolException, IOException {
        QtHttpRequest request = new QtHttpRequest(url) {
            {
                if (null != qtProxy) {
                    proxy = new QtHttpProxy(qtProxy.getHostName(), qtProxy.getPort());
                }
            }
        };
        return get(request);
    }

    /**
     * POST--lx--2017-12-21 11:53:04
     *
     * @param postUrl
     * @param postData
     * @return
     * @throws Exception
     */
    public QtHttpResult post(String postUrl, String postData) throws Exception {
        QtHttpRequest request = new QtHttpRequest(postUrl);
        request.postData = postData;
        keepAliveTimeout = request.keepAlive;
        String url = request.url;
        HttpHost otherProxyHttpHost = null;
        if (null != request.proxy) {
            otherProxyHttpHost = new HttpHost(request.proxy.getHostName(), request.proxy.getPort());
        }
        QtHttpResult qhr = new QtHttpResult();
        try {
            RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
                    .setSocketTimeout(request.getSocketTimeout()).setConnectTimeout(request.getConnectTimeout())
                    .setConnectionRequestTimeout(request.getConnectionRequestTimeout()).setProxy(otherProxyHttpHost)
                    .setRedirectsEnabled(request.redirectsEnabled).setCookieSpec(cookieType).build();
            HttpPost httppost = new HttpPost(url);
            httppost.setConfig(requestConfig);
            // Header
            if (null != request.headers) {
                request.headers.forEach((key, value) -> {
                    httppost.addHeader(key, value);
                });
            }
            // Post text,json,xml...
            if (null != request.postData && !request.postData.isEmpty()) {
                StringEntity reqEntity = new StringEntity(request.postData, "UTF-8");
                httppost.setEntity(reqEntity);
            }
            HttpClientContext context = HttpClientContext.create();
            if (null != request.cookieStore && !request.cookieStore.getCookies().isEmpty()) {
                context.setCookieStore(request.cookieStore);
            }
            context.setCredentialsProvider(credentialsProvider);
            CloseableHttpResponse response = httpclient.execute(httppost, context);
            try {
                qhr = getHttpResult(context, response);
                httppost.abort();
            } finally {
                response.close();
            }
        } finally {
            closeHttpClient();
        }
        return qhr;
    }

    /**
     * 只执行一次(注)
     *
     * @param callBack
     * @throws IOException
     */
    public void Runs(QtHttpCallBack callBack) throws IOException {
        try {
            isRun = true;
            callBack.completed(this);
        } finally {
            httpclient.close();
        }
    }

    /**
     * 设置默认代理
     *
     * @param hostName
     * @param port
     * @param userName
     * @param password
     */
    public QtHttpProxy setAuthProxy(String hostName, int port, String userName, String password) {
        qtProxy = new QtHttpProxy(hostName, port, userName, password);
        return addAuthProxy(qtProxy);
    }

    /**
     * 设置代理（可以多个）
     *
     * @param hostName
     * @param port
     * @param userName
     * @param password
     */
    public QtHttpProxy addAuthProxy(String hostName, int port, String userName, String password) {
        QtHttpProxy qtProxy = new QtHttpProxy(hostName, port, userName, password);
        return addAuthProxy(qtProxy);
    }

    /***
     * 设置代理（可以多个）
     *
     * @param qtProxy
     * @return
     */
    public QtHttpProxy addAuthProxy(QtHttpProxy qtProxy) {
        credentialsProvider.setCredentials(new AuthScope(qtProxy.getHostName(), qtProxy.getPort()),
                new UsernamePasswordCredentials(qtProxy.getUserName(), qtProxy.getPassword()));
        return qtProxy;
    }

    /**
     * 获取结果
     *
     * @param context
     * @param response
     * @return
     * @throws ParseException
     * @throws IOException
     */
    private QtHttpResult getHttpResult(HttpClientContext context, CloseableHttpResponse response) throws ParseException, IOException {
        // Once the request has been executed the local context can
        // be used to examine updated state and various objects affected
        // by the request execution.
        // // Last executed request
        // HttpRequest lastReq= context.getRequest();
        // // Execution route
        // context.getHttpRoute();
        // // Target auth state
        // context.getTargetAuthState();
        // // Proxy auth state
        // context.getTargetAuthState();
        // // Cookie origin
        // context.getCookieOrigin();
        // // Cookie spec used
        // context.getCookieSpec();
        // // User security token
        // context.getUserToken();
        QtHttpResult qhr = new QtHttpResult();
        HttpEntity entity = response.getEntity();
        qhr.setStatusCode(response.getStatusLine().getStatusCode());
        qhr.setHeader(response.getAllHeaders());
        qhr.setCookieStore(context.getCookieStore());
        qhr.setHtml(EntityUtils.toString(entity));
        if (response.getAllHeaders().length > 0) {
            List<Header> headers = Arrays.asList(response.getAllHeaders());
            Header locaHeader = headers.stream().filter(x -> "Location".equalsIgnoreCase(x.getName())).findFirst().orElse(null);
            if (locaHeader != null) {
                qhr.redirectUrl = locaHeader.getValue();
            }
        }
        qhr.redirectLocations = context.getRedirectLocations();
        EntityUtils.consume(entity);
        return qhr;
    }

    /**
     * 关闭httpclient客户端
     *
     * @throws IOException
     */
    private void closeHttpClient() throws IOException {
        if (!isRun) {
            httpclient.close();
        }
    }

    public static void main(String[] args) {
//		String testUri = "http://ip.chinaz.com/getip.aspx";
//		String proxyIP = "";
//		int port = 10000;
//		String proxyUsername = "";
//		String proxyPassword = "";
//		// 普通Get请求
//		try {
//			QtHttpClient qt = new QtHttpClient();
//			QtHttpResult qhr = qt.get(testUri);
//			qhr.getCookieStore().getCookies().forEach(x -> {
//				System.out.println("cks:" + x.getName() + "\t" + x.getValue());
//			});
//			System.out.println("<0>:" + qhr.html);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		// 代理Get请求
//		try {
//			QtHttpClient qt = new QtHttpClient();
//			qt.setAuthProxy(proxyIP, port, proxyUsername, proxyPassword);// 设置默认代理
//			QtHttpResult qhr = qt.get(testUri);
//			System.out.println("<1>:" + qhr.html);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		// 多个共用一个代理ip请求
//		try {
//			QtHttpClient qt = new QtHttpClient();
//			qt.addAuthProxy(proxyIP, port, proxyUsername, proxyPassword);// 新增代理
//			qt.Runs(new QtHttpCallBack() {
//				@Override
//				public void completed(QtHttpClient qhc) {
//					try {
//						// <<请求1>>
//						QtHttpResult qhr1 = qhc.get(testUri, proxyIP, port);
//						System.out.println("<2.1>:" + qhr1.html);
//						qhr1.getCookieStore().getCookies().forEach(x -> {
//							System.out.println("cks:" + x.getName() + "\t" + x.getValue());
//						});
//						// <<请求2>>
//						QtHttpResult qhr2 = qhc.get(testUri, proxyIP, port);
//						System.out.println("<2.2>:" + qhr2.html);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// 普通POST文件请求
//		try {
//			Map<String, String> nvs = new HashMap<String, String>();
//			nvs.put("qt", "qiteng");
//			QtHttpClient qt = new QtHttpClient();
//			// QtHttpResult qhr =
//			// qt.post("http://localhost:8080/OPS/ImportShopAircomConfig.htm",
//			// nvs,"D:\\shopAircomConfig.xls");
//			// System.out.println("<3>:" + qhr.html);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
    }
}
