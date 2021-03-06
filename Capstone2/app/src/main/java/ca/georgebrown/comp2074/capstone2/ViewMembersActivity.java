package ca.georgebrown.comp2074.capstone2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewMembersActivity extends AppCompatActivity {

    private AccountViewModel accountViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_members);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        // Long accID = sharedPref.getLong("id", 0);
        Intent i = getIntent();
        long accID = i.getLongExtra("id", 0);
        Log.d("view_members_acc_id", "" + accID);

        // set RecyclerView view and layout
        RecyclerView recyclerView = findViewById(R.id.PMembersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // add the recyclerView list adapter and the onClick listener to the recycler view
        final MemberListAdapter adapter = new MemberListAdapter(this);
        recyclerView.setAdapter(adapter);

        // get an instance of the accountViewModel
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        accountViewModel.getMembersByAccID(accID).observe(this, new Observer<List<MemberAccount>>() {
            @Override
            public void onChanged(@Nullable final List<MemberAccount> members) {
                // Update the cached copy of the members associated with this account in the adapter.
                adapter.setMembers(members);
            }
        });

        Button btnBack = findViewById(R.id.btnViewMembersHome);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), home_personal.class);
                i.putExtra("id", accID);
                startActivity(i);
                finish();
            }
        });
    }
}
