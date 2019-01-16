package wzl.com.wproject.presenter;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.ILogin;
import wzl.com.wproject.utils.NetWork;

public class GoodsdetailsPresenter extends BasePresenter{

    public GoodsdetailsPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    public Observable observable(Object... args) {
        ILogin login = NetWork.getmIntance().create(ILogin.class);
        return login.findCommodityDetailsById((int)args[0],(String) args[1],(int)args[2]);
    }
}
