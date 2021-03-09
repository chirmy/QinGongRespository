package com.example.qingong.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.qingong.R;
import com.example.qingong.share.HttpUtil;
import com.example.qingong.share.Share;
import com.example.qingong.share.ShareAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShareFragment extends Fragment {

    private ImageView bingPicImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.share_fragment,container,false);
        //RecycerView
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.share_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());//设置布局管理器
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        ShareAdapter adapter = new ShareAdapter(initData());//设置recyclerView适配器
        recyclerView.setAdapter(adapter);
        //初始化各控件
        //初始化各控件
        bingPicImg = view.findViewById(R.id.share_imgView);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String bingPicURL = prefs.getString("bing_pic",null);
//        if(bingPicURL!=null){
//            Glide.with(getActivity()).load(bingPicURL).into(bingPicImg);
//        }else {
//            loadBingPic();
//        }

        loadBingPic();
        Log.d("222","a" +
                ";lsjdflsdk");
        return view;
    }


    private List<Share> initData(){
        List<Share> shareList = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Share share = new Share(R.drawable.icon_image,"chirmy","9:15",getRandomLengthContent("任萌琳我爱你"),R.drawable.bing);
            shareList.add(share);
        }
        return  shareList;
    }

    private  String getRandomLengthContent(String content){
        Random random=new Random();
        int length=random.nextInt(20)+1;
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<length;i++){
            builder.append(content);
        }
        return builder.toString();
    }


    public void loadBingPic(){
        String requestBingpic = "https://api.xygeng.cn/Bing/url/";
        HttpUtil.sendOkHttpRequest(requestBingpic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("999","shi bai"+e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();//获取必应背景图的链接
                Log.d("111","bignPic:"+bingPic);
                SharedPreferences.Editor editor= (SharedPreferences.Editor) PreferenceManager.getDefaultSharedPreferences(getActivity());
                editor.putString("bing_pic",bingPic);
                editor.apply();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(getActivity()).load(bingPic).into(bingPicImg);
//https://api.xygeng.cn/Bing/url/
                    }
                });
            }
        });
    }



}
