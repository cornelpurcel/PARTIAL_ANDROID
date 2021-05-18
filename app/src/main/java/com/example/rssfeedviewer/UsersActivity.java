package com.example.rssfeedviewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsersActivity extends AppCompatActivity implements addUserFragment.addUserListener{

    userItemAdapter adapter;
    RecyclerView recyclerView;
    UserItemClickListener listener;

    List<String> usernameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        loadUsernameList();

        recyclerView = (RecyclerView)findViewById(R.id.user_recyclerView);
        listener = new UserItemClickListener() {
            @Override
            public void onClickItem(View view, int position) {
                // CHANGE USER
                SharedPreferences usersPref = getSharedPreferences("users", 0);
                SharedPreferences.Editor userEditor = usersPref.edit();
                MyApplication app = (MyApplication) getApplication();
                userEditor.putBoolean(app.getUsername(), false);
                userEditor.putBoolean(usernameList.get(position), true);
                app.setUsername(usernameList.get(position));

                userEditor.commit();
            }

            @Override
            public void onClickDelete(View view, int position) {
                // delete user
                SharedPreferences usersPref = getSharedPreferences("users", 0);
                SharedPreferences.Editor userEditor = usersPref.edit();
                MyApplication app = (MyApplication) getApplication();
                String usernameToDelete = usernameList.get(position);
                if (!app.getUsername().equals(usernameToDelete)){
                    userEditor.remove(usernameToDelete);
                }
                userEditor.commit();

                SharedPreferences usersSitePref = getSharedPreferences("sites_pref_" + usernameToDelete, 0);
                usersSitePref.edit().clear().commit();

                new File(getFilesDir().getParent() + "/shared_prefs/sites_pref_" + usernameToDelete + ".xml").delete();

                loadUsernameList();
                adapter.setUsernameList(usernameList);
                adapter.notifyDataSetChanged();
            }
        };

        adapter = new userItemAdapter(usernameList, getApplication(), listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(UsersActivity.this));
    }

    private void loadUsernameList() {
        usernameList = new ArrayList<String>();
        SharedPreferences usersPref = getSharedPreferences("users", 0);
        Map<String, ?> allEntries = usersPref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            usernameList.add(entry.getKey());
        }
    }

    public void showAddUserDialog(View view){
        DialogFragment addUserFrag = new addUserFragment();
        addUserFrag.show(getSupportFragmentManager(), "addUser");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        EditText textbox = (EditText) dialog.getDialog().findViewById(R.id.username_textbox);
        String newUser = textbox.getText().toString();
        if (!newUser.isEmpty()){
            SharedPreferences usersPref = getSharedPreferences("users", 0);
            SharedPreferences.Editor userEditor = usersPref.edit();
            userEditor.putBoolean(newUser, false);
            userEditor.commit();
        }
        loadUsernameList();
        adapter.setUsernameList(usernameList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();

    }
}