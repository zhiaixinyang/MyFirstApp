package com.example.greatbook;


import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by MBENBEN on 2016/11/13.
 */

public class InputStreamConvertFactory extends Converter.Factory {

    public static InputStreamConvertFactory create() {
        return new InputStreamConvertFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new InputStreamResponseConverter<String>();
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new InputStreamRequestConverter<String>();
    }

    public class InputStreamResponseConverter<T> implements Converter<ResponseBody,T> {

        InputStreamResponseConverter() {}

        /**
         主要思路为我需要Retrofit返回InputStream，然后使用GB2312进行转码得到我们想要的编码字符串
         */
        @Override
        public T convert(ResponseBody value) throws IOException {
            return (T) (new String(value.bytes(),"GB2312"));
        }
    }

    public class InputStreamRequestConverter<T> implements Converter<T, RequestBody> {

        private final MediaType MEDIA_TYPE = MediaType.parse("text/html; charset=UTF-8");

        /**
         这是是抄StringConverterFactory中的源码，因为我们不需要对要请求的参数进行编码
         */
        @Override
        public RequestBody convert(T value) throws IOException {
            return RequestBody.create(MEDIA_TYPE, value.toString());
        }
    }


}
