package com.wap.zhoulin.anodejs.CnodeListFragment;

import android.app.Activity;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wap.zhoulin.anodejs.HtmlImage.URLImageParser;
import com.wap.zhoulin.anodejs.HttpMethod;
import com.wap.zhoulin.anodejs.ParseJson;
import com.wap.zhoulin.anodejs.R;

import com.wap.zhoulin.anodejs.CnodeListFragment.dummy.DummyContent;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wap.zhoulin.anodejs.ui.widget.CheckableFrameLayout;
import com.wap.zhoulin.anodejs.util.LUtils;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * interface.
 */
public class ItemDetailFragment extends ListFragment {

    private List<ReplyItem> replyItems;
    private boolean mStarred;
    private CheckableFrameLayout mAddScheduleButton;
    private LUtils mLUtils;

    public static ItemDetailFragment newInstance(String topicID,String title,String author,String content){
        ItemDetailFragment fragment = new ItemDetailFragment();
        Bundle args = new Bundle();
        args.putString("TOPIC_ID", topicID);
        args.putString("TOPIC_TITLE",title);
        args.putString("TOPIC_AUTHOR",author);
        args.putString("TOPIC_CONTENT",content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupAdapter();
        new FetchItemsJSON().execute();
        mLUtils = LUtils.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cnode_detail, container, false);
        //Add listHeaderView
        ListView listView = (ListView)view.findViewById(android.R.id.list);
        RelativeLayout header = (RelativeLayout)inflater.inflate(R.layout.cnode_detail_header,null,false);
        listView.addHeaderView(header);
        //Initialize the topic content
        TextView detailTitle = (TextView)header.findViewById(R.id.detail_title);
        detailTitle.setText(this.getArguments().getString("TOPIC_TITLE"));
        TextView detailAuthor = (TextView)header.findViewById(R.id.detail_author);
        detailAuthor.setText(this.getArguments().getString("TOPIC_AUTHOR"));
        TextView detailContent = (TextView)header.findViewById(R.id.detail_content);
        mAddScheduleButton = (CheckableFrameLayout)header.findViewById(R.id.add_schedule_button);
        setupFabClickListener();
        URLImageParser p = new URLImageParser(detailContent,getActivity());
        Spanned htmlSpan = Html.fromHtml(this.getArguments().getString("TOPIC_CONTENT"),p,null);
        detailContent.setText(htmlSpan);
        return view;
    }

    private void setupFabClickListener(){
        mAddScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ANODEJS","Fab clicked");
                boolean starred = !mStarred;
                showStarred(starred,true);
            }
        });
    }

    private final class ViewHolder {
        public ImageView reply_avatar;
        public TextView reply_content;
        public TextView reply_author;
    }

    private void setupAdapter() {
        if(replyItems != null){
            ReplyAdapter adapter = new ReplyAdapter(getActivity());
            setListAdapter(adapter);
        }else {
            setListAdapter(null);
        }
    }

    private class ReplyAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private Context context;
        public ReplyAdapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        public int getCount(){
            return replyItems.size();
        }

        public Object getItem(int argo){
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public View getView(int position,View convertView,ViewGroup container) {
            ViewHolder holder = null;
            if(convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.cnode_reply,null);
                holder.reply_avatar =(ImageView) convertView.findViewById(R.id.reply_avatar);
                holder.reply_content = (TextView)convertView.findViewById(R.id.reply_content);
                holder.reply_author = (TextView)convertView.findViewById(R.id.reply_author);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }
            Picasso.with(this.context)
                    .load(((ReplyItem)replyItems.get(position)).getReply_avatar_url())
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.ic_launcher)
                    .into(holder.reply_avatar);
            holder.reply_author.setText(((ReplyItem)replyItems.get(position)).getReply_loginname());
            URLImageParser p = new URLImageParser(holder.reply_content,getActivity());
            Spanned htmlSpan = Html.fromHtml(((ReplyItem) replyItems.get(position)).getReply_content(),p,null);
            holder.reply_content.setText(htmlSpan);
            return convertView;
        }
    }

    private String URL = "https://cnodejs.org/api/v1/topic/topicID";
    private Map<String,Object> getReplies(){
        String topicID = this.getArguments().getString("TOPIC_ID");
        String url = URL.replace("topicID",topicID);
        String rawJSONData = HttpMethod.downloadJSON(url);
        return ParseJson.parseReplyJSON(rawJSONData);
    }

    private class FetchItemsJSON extends AsyncTask<Void,Void,List<ReplyItem>> {
        //initial size

        private ProgressDialog progressDialog = new ProgressDialog(getActivity());
        protected void onPreExecute(){
            progressDialog.setMessage("加载中....");
            progressDialog.show();
        }
        protected List<ReplyItem> doInBackground(Void... params){
            return (List<ReplyItem>)getReplies().get("REPLIES");
        }

        protected void onPostExecute(List<ReplyItem> items){
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            replyItems = items;
            setupAdapter();
        }
    }


    private void showStarred(boolean starred, boolean allowAnimate) {
        mStarred = starred;
        mAddScheduleButton.setChecked(mStarred, allowAnimate);
        ImageView iconView = (ImageView) mAddScheduleButton.findViewById(R.id.add_schedule_icon);
        mLUtils.setOrAnimatePlusCheckIcon(iconView, starred, allowAnimate);
        mAddScheduleButton.setContentDescription(getString(starred
                ? R.string.add_to_collection
                : R.string.remove_from_collection));
    }
}
