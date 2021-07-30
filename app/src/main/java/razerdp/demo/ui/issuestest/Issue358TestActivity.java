package razerdp.demo.ui.issuestest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import razerdp.basepopup.BasePopupWindow;
import razerdp.basepopup.R;
import razerdp.basepopup.databinding.ActivityIssue358Binding;
import razerdp.demo.base.baseactivity.BaseActivity;
import razerdp.demo.base.baseadapter.BaseSimpleRecyclerViewHolder;
import razerdp.demo.base.baseadapter.SimpleRecyclerViewAdapter;
import razerdp.demo.utils.ViewUtil;
import razerdp.demo.widget.DPTextView;
import razerdp.util.KeyboardUtils;
import razerdp.util.animation.AnimationHelper;
import razerdp.util.animation.ScaleConfig;

/**
 * Created by 大灯泡 on 2020/9/30.
 * <p>
 * https://github.com/razerdp/BasePopup/issues/358
 */
public class Issue358TestActivity extends BaseActivity {
    DPTextView mShowPopBt;

    Issue358Popup issue358Popup;

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public ViewBinding onCreateViewBinding(LayoutInflater layoutInflater) {
        return ActivityIssue358Binding.inflate(layoutInflater);
    }

    @Override
    protected void onInitView(View decorView) {

    }


    public void onViewClicked() {
        if (issue358Popup == null) {
            issue358Popup = new Issue358Popup(this);
        }
        issue358Popup.showPopupWindow();
    }

    static class Issue358Popup extends BasePopupWindow {

        RecyclerView rvContent;
        LinearLayout container;

        public Issue358Popup(Context context) {
            super(context);
            setContentView(R.layout.popup_issue_358);
            setKeyboardAdaptive(true);
            rvContent.setLayoutManager(new LinearLayoutManager(context));
            List<Integer> data = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                data.add(i);
            }
            SimpleRecyclerViewAdapter<Integer> adapter = new SimpleRecyclerViewAdapter<>(context, data);
            adapter.setHolder(Holder.class).outher(this);
            rvContent.setAdapter(adapter);
        }


        @Override
        public void onViewCreated(@NonNull View contentView) {
            super.onViewCreated(contentView);
        }

        @Override
        protected Animation onCreateShowAnimation() {
            return AnimationHelper.asAnimation()
                    .withScale(ScaleConfig.CENTER)
                    .toShow();
        }

        @Override
        protected Animation onCreateDismissAnimation() {
            return AnimationHelper.asAnimation()
                    .withScale(ScaleConfig.CENTER)
                    .toDismiss();
        }

        class Holder extends BaseSimpleRecyclerViewHolder<Integer> {
            EditText tv1;
            EditText tv2;
            EditText tv3;
            EditText tv4;

            public Holder(@NonNull View itemView) {
                super(itemView);
                this.tv1 = findViewById(R.id.tv_1);
                this.tv2 = findViewById(R.id.tv_2);
                this.tv3 = findViewById(R.id.tv_3);
                this.tv4 = findViewById(R.id.tv_4);

                ViewUtil.setViewsFocusListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            setKeyboardAdaptionMode(v, FLAG_KEYBOARD_ALIGN_TO_VIEW | FLAG_KEYBOARD_ANIMATE_ALIGN);
                            if (KeyboardUtils.isOpen()) {
                                updateKeyboardAlign();
                            } else {
                                KeyboardUtils.open(v);
                            }
                        }
                    }
                }, tv1, tv2, tv3, tv4);

            }

            @Override
            public int inflateLayoutResourceId() {
                return R.layout.item_issue_358;
            }

            @Override
            public void onBindData(Integer data, int position) {

            }

        }
    }

}
