package com.clover.selector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.widget.picker.DateUtil;
import com.widget.picker.RegionUtil;
import com.widget.picker.bean.RegionSupportBean;
import com.widget.picker.bis.DataPickerDialog;
import com.widget.picker.bis.DatePickerDialog;
import com.widget.picker.bis.RegionPickerDialog;
import com.widget.picker.bis.TimePickerDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //    private Dialog dateDialog = null;
    private TextView tvDate;
    private TextView tvTime;
    private TextView tvCity;
    private TextView tvCustomData;
    private List<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initWidget();
        initListener();
    }

    private void initData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            datas.add("数据" + i);
        }
    }

    private void initWidget() {
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvCity = (TextView) findViewById(R.id.tv_city);
        tvCustomData = (TextView) findViewById(R.id.tv_custom_data);
    }

    private void initListener() {
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(DateUtil.getDateForString(DateUtil.getToday()));
            }
        });
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog();
            }
        });
        tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCityDialog(RegionUtil.getList1Data(MainActivity.this));
            }
        });
        tvCustomData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataDialog();
            }
        });
    }

    private void showDateDialog(List<Integer> date) {
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(this);
        builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int[] dates) {
                StringBuilder sb = new StringBuilder();
                sb.append(dates[0]);
                sb.append("-");
                if (dates[1] > 9) {
                    sb.append(dates[1]);
                } else {
                    sb.append("0").append(dates[1]);
                }
                sb.append("-");
                if (dates[2] > 9) {
                    sb.append(dates[2]);
                } else {
                    sb.append("0").append(dates[2]);
                }
                tvDate.setText(sb.toString());
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setSelectYear(date.get(0) - 1)
                .setSelectMonth(date.get(1) - 1)
                .setSelectDay(date.get(2) - 1);
        builder.setMinYear(2008).setMinMonth(1).setMinDay(1);
        builder.setMaxYear(2028).setMaxMonth(12).setMaxDay(31);
        builder.create().show();
    }

    private void showTimeDialog() {
        TimePickerDialog.Builder builder = new TimePickerDialog.Builder(this);
        builder.setOnTimeSelectedListener(new TimePickerDialog.OnTimeSelectedListener() {
            @Override
            public void onTimeSelected(int[] times) {
                String s = String.valueOf(times[0]) +
                        ":" +
                        times[1];
                tvTime.setText(s);
            }

            @Override
            public void cancel() {
                Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
            }
        })
                .create()
                .show();
    }

    @SafeVarargs
    private final void showCityDialog(List<RegionSupportBean>... list) {
        if (list == null) return;
        RegionPickerDialog.Builder builder = new RegionPickerDialog.Builder(this);
        builder.setData1(list[0]);
        if (list.length > 1) builder.setData2(list[1]);
        if (list.length > 2) builder.setData2(list[2]);
        builder.setSelection1(1)
                .setTitle("取消")
                .setOnRegionSelectedListener(new RegionPickerDialog.OnRegionSelectedListener() {
                    @Override
                    public void onRegionSelected(List<RegionSupportBean> itemValues) {
                        StringBuilder sb = new StringBuilder();
                        for (RegionSupportBean regionSupportBean : itemValues) {
                            sb.append(regionSupportBean.getName()).append("-");
                        }
                        if (sb.length() > 0) {
                            sb.deleteCharAt(sb.length() - 1);
                            tvCity.setText(sb.toString());
                        }
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                    }
                })
                .create()
                .show();
    }

    private void showDataDialog() {
        DataPickerDialog.Builder builder = new DataPickerDialog.Builder(this);
        builder.setTitle("取消")
                .setData(datas)
                .setSelection(2)
                .setOnDataSelectedListener(new DataPickerDialog.OnDataSelectedListener() {
                    @Override
                    public void onDataSelected(String itemValue, int position) {
                        String s = String.valueOf("值：" + itemValue + "-" + "下标：" + position);
                        tvCustomData.setText(s);
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                    }
                })
                .create()
                .show();
    }
}
