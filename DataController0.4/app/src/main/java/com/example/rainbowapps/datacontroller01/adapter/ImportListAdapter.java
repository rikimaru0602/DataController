package com.example.rainbowapps.datacontroller01.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rainbowapps.datacontroller01.R;
import com.example.rainbowapps.datacontroller01.Values.ContentsData;

import java.util.List;

public class ImportListAdapter extends ArrayAdapter<ContentsData> {

    LayoutInflater mInflater;

    public ImportListAdapter(Context context, List<ContentsData> contentsDataList) {
        super(context, 0, contentsDataList);
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        super(context, android.R.layout.simple_list_item_multiple_choice, contentsDataList);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View targetView = convertView;
        ViewHolder holder;
        Context context = getContext();

        // convertViewがなければ生成
        if (targetView == null) {
            final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            targetView = inflater.inflate(R.layout.list_item_contents, parent, false);

            holder = new ViewHolder();
            holder.impContentsNameView = (TextView) targetView.findViewById(R.id.contents_name);
            holder.impContentsPlayView = (TextView) targetView.findViewById(R.id.contents_play);
            holder.checkbox = (CheckBox) targetView.findViewById(R.id.checkbox);
            targetView.setTag(holder);
        }
        // 既に生成されていれば再利用
        else {
            holder = (ViewHolder) targetView.getTag();
        }
        // 新しく生成されたリスト位置のコンテンツ名を取得
        final ContentsData contentsData = getItem(position);
        //コンテンツ名を設定
        holder.impContentsNameView.setText(contentsData.getName());
        holder.impContentsPlayView.setText(contentsData.getTime());

        // チェックボックスに変更イベントを設定する
        CheckBox chk = holder.checkbox;
        chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                contentsData.isChecked = isChecked;
            }
        });

        // イベント設定後にチェックを入れる
        // ※イベントより前に設定すると別の行の値が書き換えられてしまう
        holder.checkbox.setChecked(contentsData.isChecked);

        //コンテンツのデータサイズを設定
        return targetView;
    }

    static class ViewHolder {
        TextView impContentsNameView;
        TextView impContentsPlayView;
        CheckBox checkbox;
    }

}
