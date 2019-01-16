package wzl.com.wproject.presenter;

import io.reactivex.Observable;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.ILogin;
import wzl.com.wproject.utils.NetWork;

public class CirclePresenter extends BasePresenter{
    int page = 1;
    public CirclePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    public Observable observable(Object... args) {
        ILogin login = NetWork.getmIntance().create(ILogin.class);
        boolean refresh = (boolean) args[0];
        if (refresh){
            page=1;
        }else {
            page++;
        }
        return login.findCircleList((int)args[1],(String) args[2],page,20);
    }
}
