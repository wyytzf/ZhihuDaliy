package wyy.com.learningproject.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import wyy.com.learningproject.Adapter.NewsAdapter;
import wyy.com.learningproject.Adapter.OnItemClickListener;
import wyy.com.learningproject.Bean.News;
import wyy.com.learningproject.NetUtils.HotParse;
import wyy.com.learningproject.NetUtils.OkHttp;
import wyy.com.learningproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HotFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HotFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String URL = "url";


    // TODO: Rename and change types of parameters
    private String url;


    private View view;
    private Activity activity;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private List<News> li;



    private OnFragmentInteractionListener mListener;

    public HotFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HotFragment newInstance(String url) {
        HotFragment fragment = new HotFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hot, container, false);
        activity = this.getActivity();
        initViews();
        Request();
        return view;
    }

    private void initViews() {
        mAdapter = new NewsAdapter(activity);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(activity, DetailActivity.class);
                intent.putExtra("id",li.get(position).getId());
                activity.startActivity(intent);
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperrefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void Request(){
        OkHttp.get(url, new OkHttp.ResultCallBack() {

            @Override
            public void onError(String str, Exception e) {
                Toast.makeText(activity,str,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String str) {
                Log.i("wyy","onResponse");

                HotParse hotParse = new HotParse();
                li = hotParse.Parse(str);
                mAdapter.setDatas(li);
                if(mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        Request();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
