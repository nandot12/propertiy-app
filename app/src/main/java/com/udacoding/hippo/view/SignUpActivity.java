package com.udacoding.hippo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.udacoding.hippo.R;
import com.udacoding.hippo.model.ResponseSignUp;
import com.udacoding.hippo.networks.NetworkConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.signUpEmail)
    EditText signUpEmail;
    @BindView(R.id.signUpName)
    EditText signUpName;
    @BindView(R.id.signUpPassword)
    EditText signUpPassword;
    @BindView(R.id.signUpConfirm)
    EditText signUpConfirm;
    @BindView(R.id.signSubmit)
    Button signSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.signSubmit)
    public void onViewClicked() {

        String name = signUpName.getText().toString();
        String email = signUpEmail.getText().toString();
        String password = signUpPassword.getText().toString();
        String passwordConf  = signUpConfirm.getText().toString();




        if(name.isEmpty()){
            signUpName.setError("name can't be empty");
        }
        else if (email.isEmpty()){
            signUpEmail.setError("email can't be empty");
        }

        else {

            //submit server

            NetworkConfig config = new NetworkConfig();

            config.getService()
                    .signUpRequest(name,email,password)
                    .enqueue(new Callback<ResponseSignUp>() {
                        @Override
                        public void onResponse(Call<ResponseSignUp> call, Response<ResponseSignUp> response) {


                            if(response.isSuccessful()){

                                String pesan = response.body().getPesan();

                                Toast.makeText(SignUpActivity.this, pesan, Toast.LENGTH_SHORT).show();



                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseSignUp> call, Throwable t) {

                        }
                    });

        }
    }
}
