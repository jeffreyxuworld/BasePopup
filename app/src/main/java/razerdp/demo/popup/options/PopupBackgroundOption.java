package razerdp.demo.popup.options;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatSeekBar;
import razerdp.basepopup.R;
import razerdp.demo.model.common.CommonBackgroundInfo;
import razerdp.demo.utils.ColorUtil;
import razerdp.demo.utils.ToolUtil;
import razerdp.demo.utils.UIHelper;
import razerdp.demo.widget.DPTextView;
import razerdp.demo.widget.SquareFrameLayout;
import razerdp.util.animation.AnimationHelper;
import razerdp.util.animation.TranslationConfig;

/**
 * Created by 大灯泡 on 2019/9/20.
 */
public class PopupBackgroundOption extends BaseOptionPopup<CommonBackgroundInfo> {
    TextView mTvAlpha;
    AppCompatSeekBar mProgressAlpha;
    DPTextView mTvRefreshColor;
    SquareFrameLayout mViewColor1;
    SquareFrameLayout mViewColor2;
    SquareFrameLayout mViewColor3;
    SquareFrameLayout mViewColor4;
    SquareFrameLayout mViewColor5;
    SquareFrameLayout mViewColor6;
    SquareFrameLayout mViewColor7;
    AppCompatCheckBox mCheckNobackground;
    AppCompatCheckBox mCheckPicBackground;
    AppCompatCheckBox mCheckBlur;
    DPTextView mTvGo;

    View[] colorViews;

    Drawable selectedBackground;

    public PopupBackgroundOption(Context context) {
        super(context);
        setContentView(R.layout.popup_option_background);
        colorViews = new View[]{mViewColor1, mViewColor2, mViewColor3, mViewColor4, mViewColor5, mViewColor6, mViewColor7};
        randomColors();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedBackground = v.getBackground();
                mCheckNobackground.setChecked(false);
                mCheckPicBackground.setChecked(false);
                mTvGo.setNormalBackgroundColor(((ColorDrawable) v.getBackground()).getColor());
            }
        };
        for (View colorView : colorViews) {
            colorView.setOnClickListener(listener);
        }
        mProgressAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    float alpha = (float) progress / 100;
                    mTvAlpha.setText(String.format("透明度：%s%%", String.valueOf(progress)));
                    for (View colorView : colorViews) {
                        colorView.getBackground().setAlpha((int) (alpha * 255));
                    }
                    if (selectedBackground != null) {
                        selectedBackground.setAlpha((int) (alpha * 255));
                        if (selectedBackground instanceof ColorDrawable) {
                            int color = ((ColorDrawable) selectedBackground).getColor();
                            mTvGo.setNormalBackgroundColor(ColorUtil.alphaColor(alpha, color));
                        }
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mCheckNobackground.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedBackground = null;
                    mCheckPicBackground.setChecked(false);
                    mTvGo.setNormalBackgroundColor(UIHelper.getColor(R.color.color_blue));
                }
            }
        });
        mCheckPicBackground.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedBackground = UIHelper.getDrawable(R.drawable.popup_bg_drawable);
                    selectedBackground.setAlpha((int) (((float) mProgressAlpha.getProgress() / 100) * 255));
                }
            }
        });
    }

    void randomColors() {
        for (int i = 0; i < colorViews.length; i++) {
            ColorDrawable drawable = ToolUtil.cast(colorViews[i].getBackground(),
                                                   ColorDrawable.class);
            if (drawable == null) {
                drawable = new ColorDrawable(ColorUtil.alphaColor((float) mProgressAlpha.getProgress() / 100,
                                                                  randomColor()));
                colorViews[i].setBackground(drawable);
            } else {
                drawable.setColor(randomColor());
            }
        }
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return AnimationHelper.asAnimation()
                .withTranslation(TranslationConfig.FROM_BOTTOM)
                .toShow();
    }


    @Override
    protected Animation onCreateDismissAnimation() {
        return AnimationHelper.asAnimation()
                .withTranslation(TranslationConfig.TO_BOTTOM)
                .toDismiss();
    }


    private int randomColor() {
        Random random = new Random();
        return 0xff000000 | random.nextInt(0x00ffffff);
    }

    void ok() {
        mInfo.background = selectedBackground;
        mInfo.blur = mCheckBlur.isChecked();
        dismiss();
    }
}
