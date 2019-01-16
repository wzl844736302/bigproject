package wzl.com.wproject.presenter;

import io.reactivex.Observable;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.ILogin;
import wzl.com.wproject.utils.NetWork;

public class NewAddressPresenter extends BasePresenter{
    public NewAddressPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    public Observable observable(Object... args) {
        ILogin login = NetWork.getmIntance().create(ILogin.class);
        return login.addReceiveAddress((int)args[0],(String) args[1],(String) args[2],(String) args[3],(String) args[4],(String) args[5]);
    }
}
