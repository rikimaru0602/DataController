package com.example.rainbowapps.datacontroller01.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rainbowapps.datacontroller01.R;
import com.example.rainbowapps.datacontroller01.Values.ContentsData;

import java.util.List;

/**
 * Created by rikiishikazuki on 2015/06/09.
 */
public class AddNewAdapter extends ArrayAdapter<ContentsData> {
    static class ViewHolder {
        TextView addContentsNameView;
        TextView addContentsSizeView;
        TextView addContentsTimeView;
    }

    public AddNewAdapter(Context context, List<ContentsData> contentsDataList) {
        super(context, 0, contentsDataList);
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
            holder.addContentsNameView = (TextView) targetView.findViewById(R.id.contents_name);
            holder.addContentsSizeView = (TextView) targetView.findViewById(R.id.contents_size);
            holder.addContentsTimeView = (TextView) targetView.findViewById(R.id.contents_time);
            targetView.setTag(holder);
        }
        // 既に生成されていれば再利用
        else {
            holder = (ViewHolder) targetView.getTag();
        }
        // 新しく生成されたリスト位置の駅名を取得
        ContentsData contentsData = getItem(position);
        //コンテンツ名を設定
        holder.addContentsNameView.setText(contentsData.getName());
        //コンテンツのデータサイズを設定
        holder.addContentsTimeView.setText(contentsData.getTime());

        return targetView;
    }
}
