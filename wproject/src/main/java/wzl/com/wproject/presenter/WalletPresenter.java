package wzl.com.wproject.presenter;

import io.reactivex.Observable;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.ILogin;
import wzl.com.wproject.utils.NetWork;

public class WalletPresenter extends BasePresenter{
    public WalletPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    public Observable observable(Object... args) {
        ILogin login = NetWork.getmIntance().create(ILogin.class);
        return login.findUserWallet((int)args[0],(String) args[1],(int)args[2],(int)args[3]);
    }
}
