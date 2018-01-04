package platfotm.drawapp.View;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

/**
 * @author Ly
 * @date 2017/12/26
 */

public class BaseView extends View {
    protected Paint mPaint;
    protected Path mPath;

    {
        mPaint = new Paint();
        mPath = new Path();
    }

    public BaseView(Context context) {
        this(context, null);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        this(context, null, -1);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected int getViewWidth(LinearLayout view){
        view.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        return view.getMeasuredWidth();
    }
    protected int getViewHeight(LinearLayout view){
        view.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        return view.getMeasuredHeight();
    }
}
