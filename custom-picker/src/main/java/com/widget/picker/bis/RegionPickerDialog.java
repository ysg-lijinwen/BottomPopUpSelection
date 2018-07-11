package com.widget.picker.bis;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.widget.picker.LoopListener;
import com.widget.picker.LoopView;
import com.widget.picker.R;
import com.widget.picker.RegionUtil;
import com.widget.picker.bean.RegionSupportBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:地区选择
 * Created by Kevin.Li on 2018-01-10.
 */
public class RegionPickerDialog extends Dialog {
    private RegionPickerDialog.Params params;
    private Context context;

    public RegionPickerDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public RegionPickerDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected RegionPickerDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    private void setParams(RegionPickerDialog.Params params) {
        this.params = params;
    }

    public interface OnRegionSelectedListener {
        void onRegionSelected(List<RegionSupportBean> itemValues);

        void onCancel();
    }

    private static final class Params {
        private boolean shadow = true;
        private boolean canCancel = true;
        private LoopView loopView1;
        private LoopView loopView2;
        private LoopView loopView3;
        private String title;
        private int initSelection1;
        private int initSelection2;
        private int initSelection3;
        private RegionPickerDialog.OnRegionSelectedListener callback;
        private final List<RegionSupportBean> list1 = new ArrayList<>();
        private final List<RegionSupportBean> list2 = new ArrayList<>();
        private final List<RegionSupportBean> list3 = new ArrayList<>();
    }

    public static class Builder {
        //下标偏移，用于处理列表顶部添加“不限”导致业务数据获取错误的问题。
        private final int offsetIndex = 1;
        private final Context context;
        private final RegionPickerDialog.Params params;

        public Builder(Context context) {
            this.context = context;
            params = new RegionPickerDialog.Params();
        }

        private List<RegionSupportBean> getCurrDateValue() {
            List<RegionSupportBean> result = new ArrayList<>();
            int index = params.loopView1.getCurrentItem();
            if (index > 0 && params.list1.size() > 0) {
                result.add(params.list1.get(index - offsetIndex));
            }
            index = params.loopView2.getCurrentItem();
            if (index > 0 && params.list2.size() > 0) {
                result.add(params.list2.get(index - offsetIndex));
            }
            index = params.loopView3.getCurrentItem();
            if (index > 0 && params.list3.size() > 0) {
                result.add(params.list3.get(index - offsetIndex));
            }
            return result;
        }

        public RegionPickerDialog.Builder setData1(List<RegionSupportBean> list1) {
            params.list1.clear();
            params.list1.addAll(list1);
            return this;
        }

        public RegionPickerDialog.Builder setData2(List<RegionSupportBean> list2) {
            params.list2.clear();
            params.list2.addAll(list2);
            return this;
        }

        public RegionPickerDialog.Builder setData3(List<RegionSupportBean> list3) {
            params.list3.clear();
            params.list3.addAll(list3);
            return this;
        }

        public RegionPickerDialog.Builder setTitle(String title) {
            params.title = title;
            return this;
        }

        public RegionPickerDialog.Builder setSelection1(int selection) {
            params.initSelection1 = selection;
            return this;
        }

        public RegionPickerDialog.Builder setSelection2(int selection) {
            params.initSelection2 = selection;
            return this;
        }

        public RegionPickerDialog.Builder setSelection3(int selection) {
            params.initSelection3 = selection;
            return this;
        }

        public RegionPickerDialog.Builder setOnRegionSelectedListener(RegionPickerDialog.OnRegionSelectedListener onRegionSelectedListener) {
            params.callback = onRegionSelectedListener;
            return this;
        }

        private List<String> getRegionName(@NonNull List<RegionSupportBean> list) {
            List<String> strings = new ArrayList<>((list.size() + 1));
            strings.add(context.getString(R.string.time_picker_unlimited));
            for (RegionSupportBean r : list) {
                strings.add(r.getName());
            }
            return strings;
        }

        public RegionPickerDialog create() {
            final RegionPickerDialog dialog = new RegionPickerDialog(context, params.shadow ? R.style.Theme_Light_NoTitle_Dialog : R.style.Theme_Light_NoTitle_NoShadow_Dialog);
            View view = LayoutInflater.from(context).inflate(R.layout.layout_picker_region, null);

            if (!TextUtils.isEmpty(params.title)) {
                TextView txTitle = (TextView) view.findViewById(R.id.tx_title);
                txTitle.setText(params.title);
                txTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        params.callback.onCancel();
                    }
                });
            }

            final LoopView loopView1 = (LoopView) view.findViewById(R.id.loop_view1);
            final LoopView loopView2 = (LoopView) view.findViewById(R.id.loop_view2);
            final LoopView loopView3 = (LoopView) view.findViewById(R.id.loop_view3);
            loopView1.setArrayList(getRegionName(params.list1));
            loopView1.setNotLoop();
            loopView1.setListener(new LoopListener() {
                @Override
                public void onItemSelect(int item) {
                    params.list2.clear();
                    params.list3.clear();
                    if (item == 0) {
                        loopView2.setArrayList(null);
                        loopView3.setArrayList(null);
                    } else {
                        if (params.list1.size() > 0) {
                            params.list2.addAll(RegionUtil.getList2Data(context, params.list1.get(item - offsetIndex).getCode()));
                            loopView2.setArrayList(getRegionName(params.list2));
                            loopView2.setCurrentItem(0);//新数据选择第一个
                        }
                    }
                }
            });

//            if (params.list2.size() < 1) params.list2.add("--");
            loopView2.setArrayList(getRegionName(params.list2));
            loopView2.setNotLoop();
            loopView2.setListener(new LoopListener() {
                @Override
                public void onItemSelect(int item) {
                    params.list3.clear();
                    if (item == 0) {
                        loopView3.setArrayList(null);
                    } else {
                        if (params.list2.size() > 0) {
                            params.list3.addAll(RegionUtil.getList3Data(context, getCurrDateValue().get(0).getCode(), params.list2.get(item - offsetIndex).getCode()));
                            loopView3.setArrayList(getRegionName(params.list3));
                            loopView3.setCurrentItem(0);//新数据选择第一个
                        }
                    }
                }
            });

//            if (params.list3.size() < 1) params.list3.add("--");
            loopView3.setArrayList(getRegionName(params.list3));
            loopView3.setNotLoop();

            if (params.list1.size() > 0) loopView1.setCurrentItem(params.initSelection1);
            if (params.list2.size() > 0) loopView2.setCurrentItem(params.initSelection2);
            if (params.list3.size() > 0) loopView3.setCurrentItem(params.initSelection3);
            view.findViewById(R.id.tx_finish).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    params.callback.onRegionSelected(getCurrDateValue());
                }
            });

            Window win = dialog.getWindow();
            assert win != null;
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setAttributes(lp);
            win.setGravity(Gravity.BOTTOM);
            win.setWindowAnimations(R.style.Animation_Bottom_Rising);

            dialog.setContentView(view);
            dialog.setCanceledOnTouchOutside(params.canCancel);
            dialog.setCancelable(params.canCancel);

            params.loopView1 = loopView1;
            params.loopView2 = loopView2;
            params.loopView3 = loopView3;
            dialog.setParams(params);

            return dialog;
        }
    }
}
