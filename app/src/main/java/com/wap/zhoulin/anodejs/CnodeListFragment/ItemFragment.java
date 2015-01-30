package com.wap.zhoulin.anodejs.CnodeListFragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Outline;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.shamanland.fab.ShowHideOnScroll;
import com.squareup.picasso.Picasso;
import com.wap.zhoulin.anodejs.HttpMethod;
import com.wap.zhoulin.anodejs.ParseJson;
import com.wap.zhoulin.anodejs.R;
import android.widget.AdapterView.OnItemClickListener;
import com.shamanland.fab.FloatingActionButton;
import java.util.*;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * interface.
 */
public class ItemFragment extends ListFragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SECTION_NUMBER = "section_number";

    // TODO: Rename and change types of parameters
    private static int section_number;


    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;
    private int mPreviousTotalCount = 0;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    private List<SumaryItem> sumaryItemsList = new ArrayList<>();


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public static ItemFragment newInstance(int section_number){
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt("SECTION_NUMBER",section_number);
        fragment.setArguments(args);
        return fragment;
    }

    String[] urls = {
            //ALL
            "https://cnodejs.org/api/v1/topics?tab=all&page=pageNum&limit=limitNum",
            //Good
            "https://cnodejs.org/api/v1/topics?tab=good&page=pageNum&limit=limitNum",
            //Share
            "https://cnodejs.org/api/v1/topics?tab=share&page=pageNum&limit=limitNum",
            //Ask
            "https://cnodejs.org/api/v1/topics?tab=ask&page=pageNum&limit=limitNum",
            //Job
            "https://cnodejs.org/api/v1/topics?tab=job&page=pageNum&limit=limitNum"
    };
    private List<SumaryItem> getData(int pageNum,int limitNum) {
        section_number = this.getArguments().getInt("SECTION_NUMBER");
        String url = urls[section_number];
        Log.d("ANODEJS getData",section_number+"");
        url = url.replace("pageNum",String.valueOf(pageNum))
                .replace("limitNum", String.valueOf(limitNum));
        Log.d("ANODEJS getData",url);
        String rawJSONData = HttpMethod.downloadJSON(url);
        sumaryItemsList.addAll(ParseJson.parseJSON(rawJSONData));
        return sumaryItemsList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupAdapter();

        //TODO get pageNum and limitNum from saved status
        new FetchItemsJSON(1,10).execute();
    }

    private void setupAdapter(){
        if(sumaryItemsList != null) {
            ItemAdapter adapter = new ItemAdapter(getActivity());
            setListAdapter(adapter);
        }else{
            setListAdapter(null);
        }
        if(mListView != null) {
            mListView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d("ANODEJS SCROLL",mPreviousTotalCount+"");
                    mListView.smoothScrollToPosition(mPreviousTotalCount > 0 ? mPreviousTotalCount - 1 : 0);
                }
            },500);
        }
    }

    private void setupListViewScrollListener(){
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d("ANODEJS","firstVisibleItem: " + firstVisibleItem);
                Log.d("ANODEJS","visibleItemCount: " + visibleItemCount);
                Log.d("ANODEJS","totalItemCount: " + totalItemCount);
                //More than 10 items, because the pageSize 10
                if(totalItemCount >= 10){
                    if(firstVisibleItem + visibleItemCount >= totalItemCount && totalItemCount != mPreviousTotalCount){
                        mPreviousTotalCount = totalItemCount;
                        int page = totalItemCount/10 + 1;
                        new FetchItemsJSON(page,10).execute();
                    }
                }
            }
        });
    }


    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View layout = inflater.inflate(R.layout.cnode_main,container,false);
        mListView =(ListView)layout.findViewById(android.R.id.list);
        setupListViewScrollListener();
        //set up fab button
//        View fab = layout.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("ANODEJS","fab clicked");
//                Toast.makeText(getActivity(),"Fab Clicked",Toast.LENGTH_SHORT);
//            }
//        });
        //set up my Fab
//        ImageButton myfab = (ImageButton)layout.findViewById(R.id.my_fab);
//        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
//            @Override
//            public void getOutline(View view, Outline outline) {
//                int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
//                outline.setOval(0,0,size,size);
//            }
//        };
//        myfab.setOutlineProvider(viewOutlineProvider);

 //       mListView.setOnTouchListener(new ShowHideOnScroll(fab));
        return layout;
    }


    public void onListItemClick(ListView l,View v,int position, long id){
        Log.d("ANODEJS", "List Item Click" + sumaryItemsList.get(position).getTitle());
        FragmentManager fragmentManager = getFragmentManager();
        //Change Fragment
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container,
                ItemDetailFragment.newInstance(sumaryItemsList.get(position).getId(),
                        sumaryItemsList.get(position).getTitle(),
                        sumaryItemsList.get(position).getLoginname(),
                        sumaryItemsList.get(position).getContent()));
        transaction.addToBackStack(null);
        transaction.commit();
    }



    private final class ViewHolder {
        public ImageView avatar;
        public TextView title;
        public TextView loginname;
    }
    private class ItemAdapter extends BaseAdapter implements OnItemClickListener{
        private LayoutInflater inflater;
        private Context context;
        public ItemAdapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        public void onItemClick(AdapterView<?> parent, View v, int position,long id){
            Log.d("ANODEJS","Adapter Click");
        }

        public int getCount(){
             return sumaryItemsList.size();
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
                convertView = inflater.inflate(R.layout.cnode_item,null);
                holder.avatar =(ImageView) convertView.findViewById(R.id.avatar);
                holder.title = (TextView)convertView.findViewById(R.id.title);
                holder.loginname = (TextView)convertView.findViewById(R.id.loginname);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }

            //holder.avatar.setBackgroundResource((Integer)getData().get(position).get("avatar"));
//            Ion.with(holder.avatar)
//                    .placeholder(R.drawable.place_holder)
//                    .error(R.drawable.ic_launcher)
//                    .load("https://avatars.githubusercontent.com/u/3739368?v=2&s=120");
            Log.d("ANODEJS","GET IMAGE " + ((SumaryItem)sumaryItemsList.get(position)).getAvatar_url());
//            UrlImageViewHelper.setUrlDrawable(holder.avatar,
//                    "http://icons.iconarchive.com/icons/yellowicon/game-stars/256/Mario-icon.png"
//                   );
            Picasso.with(this.context)
                    .load(((SumaryItem)sumaryItemsList.get(position)).getAvatar_url())
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.ic_launcher)
                    .into(holder.avatar);
            Log.d("ANODEJS","FINISH GET IMAGE");
            holder.title.setText(((SumaryItem)sumaryItemsList.get(position)).getTitle());
            holder.loginname.setText(((SumaryItem)sumaryItemsList.get(position)).getLoginname());
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("ANODEJS","convert view click");
//                }
//            });
            return convertView;
        }
    }

    private class FetchItemsJSON extends AsyncTask<Void,Void,List<SumaryItem>> {
        //initial size
        private int pageNum = 0;
        private int limitNum = 10;
        private FetchItemsJSON(int pageNum,int limitNum) {
            this.pageNum = pageNum;
            this.limitNum = limitNum;
        }

        private ProgressDialog progressDialog = new ProgressDialog(getActivity());
        protected void onPreExecute(){
            progressDialog.setMessage("加载中....");
            progressDialog.show();
        }
        protected List<SumaryItem> doInBackground(Void... params){
            return getData(pageNum,limitNum);
        }

        protected void onPostExecute(List<SumaryItem> items){
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            sumaryItemsList = items;
            setupAdapter();
        }
    }
}
