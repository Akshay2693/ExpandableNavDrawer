package com.expandablenavdrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vivek on 24/03/16.
 */

public class NavigationExpandableAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private ExpandableListView mExpandableListView;
    private List<NavigationGroup> mGroupCollection;

    public NavigationExpandableAdapter(Context pContext,
                                       ExpandableListView pExpandableListView,
                                       List<NavigationGroup> pGroupCollection) {
        mContext = pContext;
        mGroupCollection = pGroupCollection;
        mExpandableListView = pExpandableListView;
    }


    @Override
    public String getChild(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return mGroupCollection.get(arg0).GroupItemCollection.get(arg1);
    }

    @Override
    public long getChildId(int arg0, int arg1) {
        return 0;
    }

    @Override
    public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
                             ViewGroup arg4) {
        ChildHolder childHolder;
        if (arg3 == null) {
            arg3 = LayoutInflater.from(mContext).inflate(
                    R.layout.navigation_group_item, null);
            childHolder = new ChildHolder();

            childHolder.title = (TextView) arg3.findViewById(R.id.navigation_item_title);
//            childHolder.title.setTypeface(FontFactory.setProximaNovaRegular(mContext));
            arg3.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) arg3.getTag();
        }

        childHolder.title.setText(mGroupCollection.get(arg0).GroupItemCollection.get(arg1));


        return arg3;
    }

    @Override
    public int getChildrenCount(int arg0) {
        return mGroupCollection.get(arg0).GroupItemCollection.size();
    }

    @Override
    public Object getGroup(int arg0) {
        return mGroupCollection.get(arg0);
    }

    @Override
    public int getGroupCount() {
        return mGroupCollection.size();
    }

    @Override
    public long getGroupId(int arg0) {
        return arg0;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        GroupHolder groupHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.navigation_group,
                    null);
            groupHolder = new GroupHolder();
            groupHolder.title = (TextView) convertView.findViewById(R.id.group_title);
            groupHolder.group_icon = (ImageView) convertView.findViewById(R.id.group_icon);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        groupHolder.title.setText(mGroupCollection.get(groupPosition).Name);

        if (isExpanded) {
            groupHolder.group_icon.setImageResource(R.drawable.ic_remove_white_24dp);
        } else {
            groupHolder.group_icon.setImageResource(R.drawable.ic_add_white_36dp);
        }


        return convertView;
    }

    class GroupHolder {
        TextView title;
        ImageView group_icon;
    }

    class ChildHolder {
        TextView title;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return true;
    }

}