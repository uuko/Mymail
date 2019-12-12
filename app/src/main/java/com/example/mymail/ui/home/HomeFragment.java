package com.example.mymail.ui.home;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.mymail.CategoryAdapter;
import com.example.mymail.CategoryModel;
import com.example.mymail.R;
import com.example.mymail.SliderAdapter;
import com.example.mymail.SliderModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView categoryRecycleView;
    private CategoryAdapter categoryAdapter;
    private int Currentpage=2;
    /////////////// banner slider
    private ViewPager bannersliderviewPager;
    private List<SliderModel> sliderModelList;
    private Timer timer;
    final private long DELAY_T=2000;
    final private long PERIOD_T=3000;
    ////////////////
    private ImageView stripimg;
    private ConstraintLayout stripconstaint;
    private com.example.mymail.HomeFragment.OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static com.example.mymail.HomeFragment newInstance(String param1, String param2) {
        com.example.mymail.HomeFragment fragment = new com.example.mymail.HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home2, container, false);
        categoryRecycleView=view.findViewById(R.id.category_recycle_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecycleView.setLayoutManager(layoutManager);
        List<CategoryModel> categoryModelList=new ArrayList<CategoryModel>();
        categoryModelList.add(new CategoryModel("link","Home"));
        categoryModelList.add(new CategoryModel("link","Appliance"));
        categoryModelList.add(new CategoryModel("link","家具"));
        categoryModelList.add(new CategoryModel("link","fashion"));
        categoryModelList.add(new CategoryModel("link","toyz"));
        categoryModelList.add(new CategoryModel("link","sports"));
        categoryModelList.add(new CategoryModel("link","wall arts"));
        categoryModelList.add(new CategoryModel("link","books"));
        categoryModelList.add(new CategoryModel("link","shoes"));
        Log.d("5555", "onCreateView: "+categoryModelList.get(0).getCategoryName());
        categoryAdapter=new CategoryAdapter(categoryModelList);
        Log.d("5555", "onCreateView: "+categoryAdapter.getItemCount());
        categoryRecycleView.setAdapter(categoryAdapter);
        Log.d("5555", "onCreateView: "+categoryRecycleView.getAdapter());
        categoryAdapter.notifyDataSetChanged();

        /////////banner slider
        bannersliderviewPager=view.findViewById(R.id.banner_viewpager);
        sliderModelList=new ArrayList<SliderModel>();
        sliderModelList.add(new SliderModel(R.drawable.ic_home_black_24dp,"#FF44AA"));
        sliderModelList.add(new SliderModel(R.drawable.ic_settings_applications_black_24dp,"#FF44AA"));

        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FF44AA"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FF44AA"));

        sliderModelList.add(new SliderModel(R.drawable.ic_home_black_24dp,"#FF44AA"));
        sliderModelList.add(new SliderModel(R.drawable.ic_settings_applications_black_24dp,"#FF44AA"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FF44AA"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FF44AA"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FF44AA"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FF44AA"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FF44AA"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FF44AA"));
        sliderModelList.add(new SliderModel(R.drawable.ic_menu_send,"#FF44AA"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FF44AA"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#FF44AA"));
        Log.d("qq", "onCreateView: "+sliderModelList.get(0).getBanner());

        SliderAdapter sliderAdapter=new SliderAdapter(sliderModelList);
        bannersliderviewPager.setAdapter(sliderAdapter);
        bannersliderviewPager.setClipToPadding(false);
        bannersliderviewPager.setCurrentItem(Currentpage);
        bannersliderviewPager.setPageMargin(20);
        ViewPager.OnPageChangeListener onPageChangeListener =new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Currentpage=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state==ViewPager.SCROLL_STATE_IDLE){
                    pageLooper();
                }
            }
        };
        bannersliderviewPager.addOnPageChangeListener(onPageChangeListener);
        startbannershow();
        bannersliderviewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pageLooper();
                stopbannershow();
                if (event.getAction()==MotionEvent.ACTION_MOVE){
                    startbannershow();
                }
                return false;
            }

        });
        stripimg=view.findViewById(R.id.strip_ad_img);
        stripconstaint=view.findViewById(R.id.strip_ad_container);
        stripimg.setImageResource(R.drawable.common_google_signin_btn_icon_light_normal_background);
        stripconstaint.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        return view;
    }

    //////////banner
    private void pageLooper(){
        if (Currentpage ==sliderModelList.size()-2){
            Currentpage=2;
            bannersliderviewPager.setCurrentItem(Currentpage,false);
        }
        if (Currentpage ==1){
            Currentpage=sliderModelList.size()-3;
            bannersliderviewPager.setCurrentItem(Currentpage,false);
        }

    }
    private void  startbannershow(){
        final Handler handler=new Handler();
        final Runnable update=new Runnable() {
            @Override
            public void run() {
                if (Currentpage>=sliderModelList.size()){
                    Currentpage=1;
                }
                      bannersliderviewPager.setCurrentItem(Currentpage++,true);
            }

        };
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },DELAY_T,PERIOD_T);
    }
    private  void  stopbannershow(){
        timer.cancel();
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}