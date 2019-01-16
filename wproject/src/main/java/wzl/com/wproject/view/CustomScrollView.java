package wzl.com.wproject.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollviewListener !=null){
            mScrollviewListener.OnScrollChange(this,l,t,oldl,oldt);
        }
    }

    /**
     * 接口回调
     */
    public interface ScrollviewListener{
        void OnScrollChange(CustomScrollView scrollView,int l, int t, int oldl, int oldt);
    }

    public ScrollviewListener mScrollviewListener;

    public void setScrollviewListener(ScrollviewListener scrollviewListener){
        mScrollviewListener = scrollviewListener;
    }
}
