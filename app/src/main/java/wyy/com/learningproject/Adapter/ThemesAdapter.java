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
import wyy.com.learningproject.Bean.Theme;
import wyy.com.learningproject.R;

/**
 * Created by weiyuyang on 16-7-1.
 */
public class ThemesAdapter extends RecyclerView.Adapter<ThemesAdapter.MyViewHolder> {

    private List<Theme> list;
    private Context context;

    private OnItemClickListener mOnItemClickListener;

    public ThemesAdapter(Context context){
        this.context = context;
        list = new ArrayList<Theme>();
    }
    public ThemesAdapter(Context context,List<Theme> list){
        this.context = context;
        this.list = list;
    }

    public void setDatas(List<Theme> list){
        this.list = list;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ThemesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view_themes,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ThemesAdapter.MyViewHolder holder, final int position) {

        holder.title.setText(list.get(position).getName());
        holder.description.setText(list.get(position).getDescription());
        Glide.with(context).load(list.get(position).getThumbnail()).into(holder.imageView);
        if (mOnItemClickListener!=null){
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
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title;
        TextView description;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.text);
            description = (TextView) itemView.findViewById(R.id.description);
        }
    }
}
