package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.time.Instant;
import java.util.List;

public class Messaging extends AppCompatActivity {

    DataBaseOpenHelper dataBaseOpenHelper;
    EditText message;
    Button button;
    RecyclerView messages;
    MessageAdapter messageAdapter;
    SharedPreferences pref;
    String email;
    String username;
    List<MessageModel> patientMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        Button button1 = findViewById(R.id.button5);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Messaging.this, PatientMessage.class);
                startActivity(intent);
            }
        });

        email = getIntent().getStringExtra("email");
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        username = pref.getString("username", null);
        dataBaseOpenHelper = new DataBaseOpenHelper(Messaging.this);
        patientMessages = dataBaseOpenHelper.getPatientMessages(username, email);
        messageAdapter = new MessageAdapter(Messaging.this, patientMessages, username);
        messages = findViewById(R.id.recyclerView);
        messages.setLayoutManager(new LinearLayoutManager(Messaging.this));
        messages.setAdapter(messageAdapter);

        button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = getIntent().getStringExtra("email");
                pref = getSharedPreferences("pref", MODE_PRIVATE);
                username = pref.getString("username", null);
                message = findViewById(R.id.etSendMessage);
                dataBaseOpenHelper = new DataBaseOpenHelper(Messaging.this);
                MessageModel model = new MessageModel(message.getText().toString(), username, email);
                boolean b = dataBaseOpenHelper.addMessage(model);
                if (!b){
                    Toast.makeText(Messaging.this, "message failed to send", Toast.LENGTH_SHORT).show();
                } else{
                    patientMessages = dataBaseOpenHelper.getPatientMessages(username, email);
                    messageAdapter = new MessageAdapter(Messaging.this, patientMessages, username);
                    messages.setAdapter(messageAdapter);
                    message.getText().clear();
                }
            }
        });
    }
}