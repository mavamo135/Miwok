package com.example.android.miwok;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView numbers = (TextView)findViewById(R.id.numbers);
        numbers.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(view.getContext(), " Open the list of numbers", Toast.LENGTH_SHORT).show();
                Intent numbersIntent = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(numbersIntent);
            }
        });
        TextView family = (TextView)findViewById(R.id.family);
        family.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(view.getContext(), " Open the list of family members", Toast.LENGTH_SHORT).show();
                Intent familyIntent = new Intent(MainActivity.this, FamilyActivity.class);
                startActivity(familyIntent);
            }
        });
        TextView colors = (TextView)findViewById(R.id.colors);
        colors.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(view.getContext(), " Open the list of colors", Toast.LENGTH_SHORT).show();
                Intent colorsIntent = new Intent(MainActivity.this, ColorsActivity.class);
                startActivity(colorsIntent);
            }
        });
        TextView phrases = (TextView)findViewById(R.id.phrases);
        phrases.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(view.getContext(), " Open the list of phrases", Toast.LENGTH_SHORT).show();
                Intent phrasesIntent = new Intent(MainActivity.this, PhrasesActivity.class);
                startActivity(phrasesIntent);
            }
        });
    }
}
