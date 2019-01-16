package wzl.com.wproject.presenter;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import wzl.com.wproject.bean.Result;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.exception.CustomException;
import wzl.com.wproject.core.exception.ResponseTransformer;

public abstract class BasePresenter {
    private DataCall dataCall;
    private boolean run;
    public BasePresenter(DataCall dataCall) {
        this.dataCall = dataCall;
    }

    public abstract Observable observable(Object...args);

    public void mLogin(Object...args){
        if (run){
            return;
        }
        run=true;
        observable(args)
                .compose(ResponseTransformer.handleResult())
                .compose(new ObservableTransformer() {
                    @Override
                    public ObservableSource apply(Observable upstream) {
                        Observable observable = upstream.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                        return observable;
                    }
                })
 /*               .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())*/
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        run=false;
                        dataCall.success(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        run=false;
                        //处理异常
                        dataCall.fail(CustomException.handleException(throwable));
                    }
                });
    }

    public boolean isRun() {
        return run;
    }
}
