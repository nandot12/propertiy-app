package com.udacoding.hippo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.udacoding.hippo.R;
import com.udacoding.hippo.adapter.MyItemRecyclerViewAdapter;
import com.udacoding.hippo.fragment.dummy.ResponsePlace;
import com.udacoding.hippo.model.DataItem;
import com.udacoding.hippo.model.ResponseConstruct;
import com.udacoding.hippo.networks.NetworkConfig;
import com.udacoding.hippo.DetailMapsActivity;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    List<DataItem> dataItems ;


    CarouselView carouselView ;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);


        carouselView = v.findViewById(R.id.carouselView);


        getPlace();


        return v ;
    }

    private void getPlace() {
        NetworkConfig config = new NetworkConfig();
        config.getService().actionPlace().enqueue(new Callback<ResponsePlace>() {
            @Override
            public void onResponse(Call<ResponsePlace> call, Response<ResponsePlace> response) {

                if(response.isSuccessful()){

                    String pesan = response.body().getPesan();
                    Boolean status = response.body().isStatus();



                    if(status){
                        List<com.udacoding.hippo.fragment.dummy.DataItem> data = response.body().getData();

                        showPlace(data);
                    }




                }
            }

            @Override
            public void onFailure(Call<ResponsePlace> call, Throwable t) {

            }
        });

    }

    private void showPlace(List<com.udacoding.hippo.fragment.dummy.DataItem> data) {

        MyItemRecyclerViewAdapter adapter = new MyItemRecyclerViewAdapter(data, new OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(com.udacoding.hippo.fragment.dummy.DataItem item) {

                Intent intent = new Intent(getContext(), DetailMapsActivity.class);
                intent.putExtra("data", (Serializable) item);
                startActivity(intent);


            }
        });




    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NetworkConfig config = new NetworkConfig();
        config.getService().actionConstruct().enqueue(new Callback<ResponseConstruct>() {
            @Override
            public void onResponse(Call<ResponseConstruct> call, Response<ResponseConstruct> response) {

                if(response.isSuccessful()){

                    String pesan = response.body().getPesan();
                    Boolean status = response.body().isStatus();



                    if(status){
                        List<DataItem> data = response.body().getData();

                        showData(data);
                    }




                }
            }

            @Override
            public void onFailure(Call<ResponseConstruct> call, Throwable t) {

            }
        });




    }

    private void showData(List<DataItem> data) {

        dataItems = data ;
        carouselView.setImageListener(imageListener);
        carouselView.setPageCount(dataItems.size());




    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {

            Glide.with(getActivity())
                    .load("http://192.168.0.17/gambar/"+dataItems.get(position).getGambar())
                    .error(R.mipmap.ic_launcher)
                    .into(imageView);


        }
    };
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(com.udacoding.hippo.fragment.dummy.DataItem item);
    }

}
