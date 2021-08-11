package razerdp.demo.popup.options;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.GridLayoutManager;
import razerdp.basepopup.R;
import razerdp.basepopup.databinding.PopupOptionArrowBinding;
import razerdp.demo.base.baseadapter.BaseSimpleRecyclerViewHolder;
import razerdp.demo.base.baseadapter.OnItemClickListener;
import razerdp.demo.base.baseadapter.SimpleRecyclerViewAdapter;
import razerdp.demo.model.common.CommonArrowInfo;
import razerdp.demo.utils.UIHelper;
import razerdp.demo.widget.decoration.GridItemDecoration;
import razerdp.demo.widget.decoration.SpaceOption;

/**
 * Created by 大灯泡 on 2020/05/07
 * <p>
 * Description：slide相关的配置
 */
public class PopupArrowOption extends BaseOptionPopup<CommonArrowInfo> {
    PopupOptionArrowBinding mBinding;
    SimpleRecyclerViewAdapter<Info> mAdapter;

    public PopupArrowOption(Context context) {
        super(context);
        setContentView(R.layout.popup_option_arrow);
        List<Info> infos = new ArrayList<>();
        infos.add(new Info(Gravity.LEFT, "Gravity.Left"));
        infos.add(new Info(Gravity.TOP, "Gravity.Top"));
        infos.add(new Info(Gravity.RIGHT, "Gravity.RIGHT"));
        infos.add(new Info(Gravity.BOTTOM, "Gravity.BOTTOM", true));
        infos.add(new Info(Gravity.CENTER_VERTICAL, "Gravity.CENTER_VERTICAL"));
        infos.add(new Info(Gravity.CENTER_HORIZONTAL, "Gravity.CENTER_HORIZONTAL"));
        infos.add(new Info(Gravity.CENTER, "Gravity.CENTER"));

        mAdapter = new SimpleRecyclerViewAdapter<>(context, infos);
        mAdapter.setHolder(InnerViewHolder.class);
        mBinding.rvContent.setLayoutManager(new GridLayoutManager(context, 2));
        mBinding.rvContent.addItemDecoration(new GridItemDecoration(new SpaceOption.Builder().size(
                UIHelper.DP12).build()));
        mBinding.rvContent.setItemAnimator(null);
        mAdapter.setOnItemClickListener(new OnItemClickListener<Info>() {
            @Override
            public void onItemClick(View v, int position, Info data) {
                data.checked = !data.checked;
                mAdapter.notifyItemChanged(position);
            }
        });
        mBinding.rvContent.setAdapter(mAdapter);
        mBinding.tvGo.setOnClickListener(v -> apply());
    }

    @Override
    public void onViewCreated(@NonNull View contentView) {
        mBinding = PopupOptionArrowBinding.bind(contentView);
    }

    void apply() {
        int gravity = Gravity.NO_GRAVITY;
        for (Info data : mAdapter.getDatas()) {
            if (data.checked) {
                gravity |= data.gravity;
            }
        }
        mInfo.gravity = gravity;
        mInfo.blur = mBinding.checkBlur.isChecked();
        mInfo.gravityMode = mBinding.checkAlignToSide.isChecked() ? GravityMode.ALIGN_TO_ANCHOR_SIDE : GravityMode.RELATIVE_TO_ANCHOR;
        dismiss();
    }

    static class InnerViewHolder extends BaseSimpleRecyclerViewHolder<Info> {
        AppCompatCheckBox checkBox;

        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = findViewById(R.id.check_box);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> getData().checked = isChecked);
        }

        @Override
        public int inflateLayoutResourceId() {
            return R.layout.item_slide_option;
        }

        @Override
        public void onBindData(Info data, int position) {
            checkBox.setChecked(data.checked);
            checkBox.setText(data.name);
        }
    }

    static class Info {
        int gravity;
        String name;
        boolean checked;

        public Info(int gravity, String name) {
            this(gravity, name, false);
        }

        public Info(int gravity, String name, boolean checked) {
            this.gravity = gravity;
            this.name = name;
            this.checked = checked;
        }
    }
}
