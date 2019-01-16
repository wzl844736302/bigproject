package wzl.com.wproject.presenter;

import io.reactivex.Observable;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.ILogin;
import wzl.com.wproject.utils.NetWork;

public class SearchPresenter extends BasePresenter{
    private int page = 1;
    public SearchPresenter(DataCall dataCall) {
        super(dataCall);
    }
    @Override
    public Observable observable(Object... args) {
        ILogin login = NetWork.getmIntance().create(ILogin.class);
        boolean refresh = (boolean) args[1];
        if (refresh){
            page = 1;
        }else {
            page++;
        }
        return login.findCommodityByKeyword((String) args[0],page,20);
    }
}
