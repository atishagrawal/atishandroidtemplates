
package com.xecute.app;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class UserListAdapter extends ArrayAdapter<ParseUser> {
    private final Context mContext;
    private final ArrayList<ParseUser> userList;

    public UserListAdapter(Context context,ArrayList<ParseUser> userList) {
        super(context, R.layout.user_list_row, userList);
        this.userList = userList;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.user_list_row, null);
            Log.i("USER_ADAPTER", "Layout Inflated");
        }
        TextView taskUserName = (TextView) convertView.findViewById(R.id.taskUserName);
        taskUserName.setText(userList.get(position).getUsername());

        return convertView;

    }
}
