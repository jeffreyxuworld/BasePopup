package razerdp.demo.ui.issuestest;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import androidx.viewbinding.ViewBinding;
import razerdp.basepopup.databinding.ActivityIssue230Binding;
import razerdp.demo.base.baseactivity.BaseActivity;
import razerdp.demo.popup.issue.PopupIssue230;
import razerdp.demo.widget.DPTextView;

/**
 * Created by 大灯泡 on 2019/10/8.
 * <p>
 * https://github.com/razerdp/BasePopup/issues/230
 */
public class Issue230TestActivity extends BaseActivity {
    DPTextView mTvShow;

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public ViewBinding onCreateViewBinding(LayoutInflater layoutInflater) {
        return ActivityIssue230Binding.inflate(layoutInflater);
    }

    @Override
    protected void onInitView(View decorView) {

    }

    void show() {
        new PopupIssue230(this).showPopupWindow();
    }

}
