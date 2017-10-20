package com.android.longc.testpopuwindow;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends Activity {

    PopupWindow popupWindow;

    private EditText et_input;
    private ListView listView;
    private ArrayList<String> msgs;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_input = (EditText) findViewById(R.id.et_input);
        et_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null) {
                    popupWindow = new PopupWindow(MainActivity.this);
                    popupWindow.setWidth(et_input.getWidth());
                    popupWindow.setHeight(200);

                    popupWindow.setContentView(listView);
                    popupWindow.setFocusable(true);// 设置焦点

                }
                popupWindow.showAsDropDown(et_input, 0, 0);

            }
        });
        listView = new ListView(this);
        listView.setBackgroundResource(R.drawable.listview_background);
        // 准备数据
        msgs = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            msgs.add(i + "-aaaaaa-" + i);
        }
        myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 得到数据
                String msg = msgs.get(position);
                // 设置到输入框中
                et_input.setText(msg);
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return msgs.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.item_main, null);
                holder = new ViewHolder();
                holder.tv_msg = (TextView) convertView.findViewById(R.id.tv_msg);
                holder.iv_detele = (ImageView) convertView.findViewById(R.id.iv_detele);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            // 根据位置得到数据
            final String msg = msgs.get(position);
            holder.tv_msg.setText(msg);

            // 设置删除
            holder.iv_detele.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 从集合中移除
                    msgs.remove(msg);
                    // 刷新UI-适配器刷新
                    myAdapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }

    }

    static class ViewHolder {
        TextView tv_msg;
        ImageView iv_detele;
    }
}