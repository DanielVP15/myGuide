package com.example.dvpires.guideapp.save_data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dvpires.guideapp.MainActivity;
import com.example.dvpires.guideapp.R;

import static java.security.AccessController.getContext;

public class SharedPreferenceActivity extends AppCompatActivity implements View.OnClickListener {

    // É possivel salvar Boolean, float, int ,long e string set

    public EditText stringEditText;
    public TextView stringTextView;
    public Button saveButton;

    public static final String SAVED_STRING = "guideAppSavedString";
    public static final String DEFAULT_STRING = "DIGITE AQUI";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);
        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        init();
        loadSharedPreference();
    }

    public void init() {
        stringEditText = (EditText) findViewById(R.id.string_shared_preference_edit_text);
        stringTextView = (TextView) findViewById(R.id.string_shared_preference_text);
        saveButton = (Button) findViewById(R.id.shared_preference_button);

        saveButton.setOnClickListener(this);

    }

    public void saveSharedPreference() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(SAVED_STRING, stringEditText.getText().toString());
        editor.commit();
    }

    public void loadSharedPreference() {

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String savedString = sharedPref.getString(SAVED_STRING, DEFAULT_STRING);
        stringEditText.setText(savedString);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shared_preference_button:
                saveSharedPreference();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
        }

    }
}
