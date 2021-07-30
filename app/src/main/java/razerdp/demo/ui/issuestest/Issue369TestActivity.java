package razerdp.demo.ui.issuestest;

import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.viewbinding.ViewBinding;
import razerdp.basepopup.R;
import razerdp.basepopup.databinding.ActivityIssue369Binding;
import razerdp.demo.base.baseactivity.BaseActivity;
import razerdp.demo.popup.DemoPopup;
import razerdp.demo.utils.UIHelper;
import razerdp.demo.utils.ViewUtil;
import razerdp.demo.widget.DPTextView;

/**
 * Created by 大灯泡 on 2021/5/27.
 * <p>
 * https://github.com/razerdp/BasePopup/issues/369
 */
public class Issue369TestActivity extends BaseActivity {
    DPTextView mShowPopBt;
    DPTextView mShowSystemPopup;
    AppCompatCheckBox check_hidekeyboard;

    DemoPopup demoPopup;
    PopupWindow systemPopup;

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public ViewBinding onCreateViewBinding(LayoutInflater layoutInflater) {
        return ActivityIssue369Binding.inflate(layoutInflater);
    }

    @Override
    protected void onInitView(View decorView) {

    }


    public void onViewClicked() {
        if (demoPopup == null) {
            demoPopup = new DemoPopup(this);
        }
        demoPopup.hideKeyboardOnShow(check_hidekeyboard.isChecked());
        demoPopup.showPopupWindow();
    }

    public void onSystemViewClicked(View v) {
        if (systemPopup == null) {
            systemPopup = new PopupWindow(this);
            View contentView = ViewUtil.inflate(this, R.layout.popup_demo, null, false);
            contentView.setOnClickListener(v1 -> systemPopup.dismiss());
            systemPopup.setContentView(contentView);
            systemPopup.setWidth(UIHelper.dip2px(150));
            systemPopup.setHeight(UIHelper.dip2px(150));
        }
        systemPopup.showAtLocation(v, Gravity.CENTER, 0, 0);
    }
}
