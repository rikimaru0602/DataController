package com.example.rainbowapps.datacontroller01.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.rainbowapps.datacontroller01.R;
import com.example.rainbowapps.datacontroller01.Values.ContentsData;

import java.util.List;

public class ImportListAdapter2 extends ArrayAdapter<ContentsData> {

    LayoutInflater mInflater;

    public ImportListAdapter2(Context context, List<ContentsData> contentsDataList) {
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
            targetView = inflater.inflate(R.layout.list_item_contents_2, parent, false);

            holder = new ViewHolder();
            holder.impContentsNameView = (TextView) targetView.findViewById(R.id.contents_name);
            holder.impContentsPlayView = (TextView) targetView.findViewById(R.id.contents_play);
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


        //コンテンツのデータサイズを設定
        return targetView;
    }

    static class ViewHolder {
        TextView impContentsNameView;
        TextView impContentsPlayView;
    }

}
