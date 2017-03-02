package com.weknowall.app_data.net.api.builder;

import android.support.annotation.NonNull;

import com.weknowall.app_common.sharedPrefrence.SharePreferencePlus;
import com.weknowall.app_common.utils.Logs;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * User: Joy
 * Date: 2016/11/3
 * Time: 19:03
 */

class OldInterceptorUtils {

    private static String USER_AGENT = "";
    private static final String APPSECRET = "a144c12sadf314mk$6$t!@#9t3b";
    private static final String COOKIE="cookie";

    static Request.Builder createRequestBuilder(Interceptor.Chain chain) {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
//		builder.addHeader("Referer", RefererStringCallBack.getInstance().getRefererString());
//		builder.addHeader(Constants.HTTP_HEADER_USER_AGENT, getUserAgent());

        builder.addHeader("Cookie",ResponseHeaderSharedPreference.getInstance().getHeader(COOKIE));
        // 如果是post表单提交则添加vk
        if (request.method().equals("POST") && request.body() instanceof FormBody) {
            try {
                FormBody body = (FormBody) request.body();
                FormBody.Builder formBuilder = new FormBody.Builder();
                Map<String, String> params = new HashMap<>();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("request params:   ");
                for (int i = 0; i < body.size(); i++) {
                    formBuilder.add(body.name(i), body.value(i));
                    params.put(body.name(i), body.value(i));
                    stringBuffer.append(body.name(i) + ":" + body.value(i) + "   ");
                }
                Logs.d(stringBuffer.toString());
//				formBuilder.add("vk", getVk(params));
//				formBuilder.add("source", "android");
//				formBuilder.add("format", "json");
//				formBuilder.add("lat", String.valueOf(Constants.CACHE_LOCATION[0]));
//				formBuilder.add("lon", String.valueOf(Constants.CACHE_LOCATION[1]));
                builder.post(formBuilder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        HttpUrl url = request.url();
        HttpUrl.Builder ub = url.newBuilder();
//		ub.addQueryParameter("source", "android");
//		ub.addQueryParameter("format", "json");
//		ub.addQueryParameter("fc", "msjandroid");
//		ub.addQueryParameter("lat", String.valueOf(Constants.CACHE_LOCATION[0]));
//		ub.addQueryParameter("lon", String.valueOf(Constants.CACHE_LOCATION[1]));
        builder.url(ub.build());
        return builder;
    }

    static Response createResponse(Request request, Interceptor.Chain chain) throws IOException {
        return createResponse(request, chain, "data");
    }

    /**
     * 为返回参数中 "data"字段为"obj"
     *
     * @param request request
     * @param chain   chain
     * @return Response
     * @throws IOException
     */
    static Response createResponse2(Request request, Interceptor.Chain chain) throws IOException {
        return createResponse(request, chain, "obj");
    }

    /**
     * 为返回参数中 "data"字段为"imgs_news" 为上传图片使用
     *
     * @param request request
     * @param chain   chain
     * @return Response
     * @throws IOException
     */
    static Response createResponse3(Request request, Interceptor.Chain chain) throws IOException {
        return createResponse(request, chain, "imgs_news");
    }

    static Response createResponseResult(Request request, Interceptor.Chain chain) throws IOException {
        return createResponse(request, chain, "data", new int[]{0, 1, 2}, true);
    }

    static Response createResponse(Request request, Interceptor.Chain chain, String dataKey) throws IOException {
        return createResponse(request, chain, dataKey, new int[]{1, 11}, true);
    }

    static Response createListResponse(Request request, Interceptor.Chain chain) throws IOException {
        return createResponse(request, chain, "data", new int[]{1, 11}, false);
    }

    /**
     * @param request     request
     * @param chain       chain
     * @param dataKey     返回字段应为"data"的地方实际的字段
     * @param usefulCodes 表示操作成功的code集合。由于某些地方的code返回不是1，2333
     * @throws IOException
     */
    private static Response createResponse(Request request, Interceptor.Chain chain, @NonNull String dataKey, int[] usefulCodes,
                                           boolean authCheckItems) throws IOException {
        Response response = chain.proceed(request);

        saveCookie(response);

        String bodyString = response.body().string();

        Logs.d(request.url().toString());
        Logs.d(bodyString);
        Logs.j(bodyString);

        return response.newBuilder().body(ResponseBody.create(response.body().contentType(), bodyString)).build();
    }

    private static void saveCookie(Response response) {
        if (!response.headers("Set-Cookie").isEmpty()) {
            final StringBuffer cookieBuffer = new StringBuffer();
            Observable.from(response.headers("Set-Cookie"))
                    .map(s -> {
                        String[] split = s.split(";");
                        return split[0];
                    })
                    .subscribe(s -> {
                        cookieBuffer.append(s).append(";");
                    });
            ResponseHeaderSharedPreference.getInstance().saveHeader(COOKIE,cookieBuffer.toString());
        }
    }

    /**
     * 对那些没有返回data的统一将msg返回，以便做提示用
     *
     * @param message message
     */
    private static String createMessageData(String message) {
        return String.format("{'msg':'%s'}", message);
    }

    static class ResponseHeaderSharedPreference extends SharePreferencePlus{

        private static final String NAME="response_header";

        public ResponseHeaderSharedPreference() {
            super(NAME);
        }

        private static ResponseHeaderSharedPreference sPreference;

        public static ResponseHeaderSharedPreference getInstance(){
            return sPreference==null?new ResponseHeaderSharedPreference():sPreference;
        }

        public void saveHeader(String key,String value){
            editStringValue(key,value);
        }

        public String getHeader(String key){
            return getStringValue(key,"");
        }
    }
}
