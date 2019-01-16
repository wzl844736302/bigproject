package wzl.com.wproject.presenter;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.ILogin;
import wzl.com.wproject.utils.NetWork;

public class ImagesPresenter extends BasePresenter {

    public ImagesPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    public Observable observable(Object... args) {
        ILogin backs = NetWork.getmIntance().create(ILogin.class);
        return backs.register();
    }
}
