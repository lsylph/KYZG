package test.bwie.com.example.ins7566.kyzg.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lenovo on 2017/5/2.
 */

public class LunBoAdapter extends PagerAdapter {
    private List<View> mList;

    public LunBoAdapter(List<View> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (container!=null){
            container.removeView(mList.get(position%mList.size()));
        }
        container.addView(mList.get(position%mList.size()));
        return mList.get(position%mList.size());
    }
}