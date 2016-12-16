package com.example.ahmaadyunus.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {
    TextView tv_respond, tv_result_api;
    EditText name_ET, email_ET;
    Button btn_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_respond = (TextView)findViewById(R.id.tv_respond);
        tv_result_api = (TextView)findViewById(R.id.tv_result_api);
        name_ET = (EditText) findViewById(R.id.name_ET);
        email_ET = (EditText)findViewById(R.id.email_ET);
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://private-80e9a-android23.apiary-mock.com/user/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        UserApi user_api = retrofit.create(UserApi.class);

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser();
            }
        });
        // // implement interface for get all user

        Call<Users> call = user_api.getUsers();
        call.enqueue(new Callback<Users>() {

            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                int status = response.code();
                tv_respond.setText(String.valueOf(status));
                //this extract data from retrofit with for() loop
                for(Users.UserItem user : response.body().getUsers()) {
                    tv_result_api.append(
                            "Id = " + String.valueOf(user.getId()) +
                                    System.getProperty("line.separator") +
                                    "Email = " + user.getEmail() +
                                    System.getProperty("line.separator")
                    );
                }
            }


            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                tv_respond.setText(String.valueOf(t));
            }
        });
    }
    public void saveUser (){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://private-80e9a-android23.apiary-mock.com/users/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserApi user_api = retrofit.create(UserApi.class);
        User user = new User();
        user.setName(name_ET.getText().toString());
        user.setEmail(email_ET.getText().toString());
        Call<User> userSave = user_api.saveUser(user);
        userSave.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(MainActivity.this,""+response.body().getMessage(), LENGTH_SHORT).show();

                Log.e("onResponse", "" + response.code() +
                        "  response body "  + response.body() +
                        " responseError " + response.errorBody() +
                        " responseMessage " +
                        response.message());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("onFailure", t.toString());             }         });
            }



}
