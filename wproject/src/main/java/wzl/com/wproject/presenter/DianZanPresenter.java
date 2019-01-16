package wzl.com.wproject.presenter;

import io.reactivex.Observable;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.ILogin;
import wzl.com.wproject.utils.NetWork;

public class DianZanPresenter extends BasePresenter{
    public DianZanPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    public Observable observable(Object... args) {
        ILogin iLogin = NetWork.getmIntance().create(ILogin.class);
        return iLogin.addCircleGreat((int)args[0],(String) args[1],(int)args[2]);
    }
}
