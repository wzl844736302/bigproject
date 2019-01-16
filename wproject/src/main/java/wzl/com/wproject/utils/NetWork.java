package wzl.com.wproject.utils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWork {

    private Retrofit retrofit;
    private static NetWork mIntance;
    private NetWork(){
        init();
    }
    //设置单例模式
    public static NetWork getmIntance(){
        if (mIntance==null){
            mIntance = new NetWork();
        }
        return mIntance;
    }
    private void init(){
        //初始化okhttp并设置拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        //初始化Retrofit
        retrofit = new Retrofit.Builder()
                .client(client)
                /*.baseUrl("http://mobile.bwstudent.com/small/")//设置url*/
                .baseUrl("http://172.17.8.100/small/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//处理回调数据
                .addConverterFactory(GsonConverterFactory.create())//gson转换器
                .build();
    }
    //把接口的注解翻译为okhttp请求
    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }
}
