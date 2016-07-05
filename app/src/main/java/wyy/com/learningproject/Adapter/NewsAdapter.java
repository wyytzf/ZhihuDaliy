package wyy.com.learningproject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import wyy.com.learningproject.Bean.News;
import wyy.com.learningproject.R;

/**
 * Created by weiyuyang on 16-6-10.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private List<News> list;
    private Context context;


    private OnItemClickListener mOnItemClickListener;

    public NewsAdapter(Context context){
        this.context = context;
        list = new ArrayList<News>();
    }
    public NewsAdapter(Context context,List<News> list){
        this.context = context;
        this.list = list;
    }

    public void setDatas(List<News> list){
        this.list = list;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_view_news, parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.mTextView.setText(list.get(position).getTitle());
        Glide.with(context).load(list.get(position).getImage()).into(holder.mImageView);
        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(list!=null)
            return list.size();
        else
            return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTextView;
        public MyViewHolder(View view){
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.image);
            mTextView = (TextView) view.findViewById(R.id.text);
        }
    }



}
