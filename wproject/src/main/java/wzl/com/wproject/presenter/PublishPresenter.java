package wzl.com.wproject.presenter;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import wzl.com.wproject.core.DataCall;
import wzl.com.wproject.core.ILogin;
import wzl.com.wproject.utils.NetWork;

public class PublishPresenter extends BasePresenter{
    public PublishPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    public Observable observable(Object... args) {
        ILogin login = NetWork.getmIntance().create(ILogin.class);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("content", (String)args[3]);
        builder.addFormDataPart("commodityId", "1");
        List<Object> list = (List<Object>) args[4];
        if (list.size()>1) {
            for (int i = 1; i < list.size(); i++) {
                File file = new File((String) list.get(i));
                builder.addFormDataPart("image", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/octet-stream"),
                                file));
            }
        }
        return login.releaseCircle((int)args[0],(String) args[1],builder.build());
    }
}
