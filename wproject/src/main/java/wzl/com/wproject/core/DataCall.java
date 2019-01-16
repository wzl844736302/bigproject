package wzl.com.wproject.core;

import wzl.com.wproject.core.exception.ApiException;

public interface DataCall<T> {

    void success(T data);
    void fail(ApiException e);

}
