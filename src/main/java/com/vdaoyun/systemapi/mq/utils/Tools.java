package com.vdaoyun.systemapi.mq.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Tools {
	
	public static JSONObject applyToken(String secretKey, String accessKey) throws InvalidKeyException, NoSuchAlgorithmException, IOException, UnrecoverableKeyException, KeyManagementException, KeyStoreException {
        String apiUrl="https://mqauth.aliyuncs.com/token/apply";
        Map<String,String >paramMap=new HashMap<String, String>();
        paramMap.put("resources","HJKJ_DATA01");
        paramMap.put("actions","R,W");
        paramMap.put("serviceName","mq");
        paramMap.put("expireTime", String.valueOf(System.currentTimeMillis()+1000000));
        String signature= Tools.doHttpSignature(paramMap, secretKey);
        paramMap.put("proxyType","MQTT");
        paramMap.put("accessKey", accessKey);
        paramMap.put("signature",signature);
        JSONObject object = Tools.httpsPost(apiUrl,paramMap);
        System.out.println(object);
        return object;
    }
	
	
	/**
     * 计算签名，参数分别是参数对以及密钥
     *
     * @param requestParams 参数对，即参与计算签名的参数
     * @param secretKey     密钥
     * @return 签名字符串
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static String doHttpSignature(Map<String, String> requestParams, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        List<String> paramList = new ArrayList<String>();
        for (Map.Entry<String, String> entry : requestParams.entrySet()) {
            paramList.add(entry.getKey() + "=" + entry.getValue());
        }
        Collections.sort(paramList);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < paramList.size(); i++) {
            if (i > 0) {
                sb.append('&');
            }
            sb.append(paramList.get(i));
        }
        return macSignature(sb.toString(), secretKey);
    }

    /**
     * @param text      要签名的文本
     * @param secretKey 阿里云 MQ secretKey
     * @return 加密后的字符串
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static String macSignature(String text, String secretKey) throws InvalidKeyException, NoSuchAlgorithmException {
        Charset charset = Charset.forName("UTF-8");
        String algorithm = "HmacSHA1";
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(secretKey.getBytes(charset), algorithm));
        byte[] bytes = mac.doFinal(text.getBytes(charset));
        return new String(Base64.encodeBase64(bytes), charset);
    }

    /**
     * 创建 HTTPS 客户端
     *
     * @return 单例模式的客户端
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private static HttpClient httpClient = null;

    public static HttpClient getHttpsClient() throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
        if (httpClient != null) {
            return httpClient;
        }
        X509TrustManager xtm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                    throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                    throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
        };
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, new TrustManager[]{xtm}, null);
        SSLConnectionSocketFactory scsf = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
        Registry<ConnectionSocketFactory> sfr = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", scsf).build();
        PoolingHttpClientConnectionManager pcm = new PoolingHttpClientConnectionManager(sfr);
        httpClient = HttpClientBuilder.create().setConnectionManager(pcm).build();
        return httpClient;
    }

    /**
     * 发起 HTTPS Get 请求，并得到返回的 JSON 响应
     *
     * @param url    接口 URL
     * @param params 参数对
     * @return
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static JSONObject httpsGet(String url, Map<String, String> params) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
        HttpClient client = Tools.getHttpsClient();
        JSONObject jsonResult = null;
        //发送 get 请求
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            urlParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        String paramUrl = URLEncodedUtils.format(urlParameters, Charset.forName("UTF-8"));
        HttpGet request = new HttpGet(url + "?" + paramUrl);
        HttpResponse response = client.execute(request);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String strResult = EntityUtils.toString(response.getEntity());
            jsonResult = JSON.parseObject(strResult);
        }
        return jsonResult;
    }
  /**
     * 工具方法，发送一个 HTTP POST 请求，并尝试将响应转换为 JSON
     *
     * @param url    请求的方法名 URL
     * @param params 参数表
     * @return 如果请求成功则返回 JSON，否则抛异常或者返回空
     * @throws IOException
     */
    public static JSONObject httpsPost(String url, Map<String, String> params) throws IOException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        JSONObject jsonResult = null;
        HttpClient client = getHttpsClient();
        //发送 get 请求
        HttpPost request = new HttpPost(url);
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            urlParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);
        request.setEntity(postParams);
        HttpResponse response = client.execute(request);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String strResult = EntityUtils.toString(response.getEntity());
            jsonResult = JSON.parseObject(strResult);
        }
        return jsonResult;
    }
	
}
