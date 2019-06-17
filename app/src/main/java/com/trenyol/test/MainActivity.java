package com.trenyol.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.trenyol.showcase.CancelListener;
import com.trenyol.showcase.showcase.ShowcaseManager;
import com.trenyol.showcase.ui.showcase.HighlightType;
import com.trenyol.showcase.ui.tooltip.ArrowPosition;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button1 = findViewById(R.id.button1);
        final Button button2 = findViewById(R.id.button2);
        final Button button3 = findViewById(R.id.button3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ShowcaseManager.Builder()
                        .view(button1)
                        .titleText("Koleksiyon Oluşturdun Top!")
                        .descriptionText("Bir koleksiyon oluşturdun! Koleksiyon sekmesinin altından oluşturduğun koleksiyonlara ulaşabilir, yeni koleksiyon oluşturabilir ve koleksiyonlarını paylaşabilirsin. :)")
                        .titleTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent))
                        .descriptionTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary))
                        .backgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimaryDark))
                        .closeButtonColor(ContextCompat.getColor(getBaseContext(), R.color.white))
                        .showCloseButton(true)
                        .arrowPosition(ArrowPosition.AUTO)
                        .highlightType(HighlightType.CIRCLE)
                        .windowBackgroundAlpha(255)
                        .titleTextSize(30F)
                        .build()
                        .show(MainActivity.this);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShowcaseManager.Builder()
                        .view(button2)
                        .titleText("Koleksiyon Oluşturdun Buttom!")
                        .descriptionText("Bir koleksiyon oluşturdun! Koleksiyon sekmesinin altından oluşturduğun koleksiyonlara ulaşabilir, yeni koleksiyon oluşturabilir ve koleksiyonlarını paylaşabilirsin. :)")
                        .titleTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent))
                        .showCloseButton(true)
                        .arrowPosition(ArrowPosition.AUTO)
                        .highlightType(HighlightType.CIRCLE)
                        .descriptionTextSize(21)
                        .build()
                        .show(MainActivity.this);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShowcaseManager.Builder()
                        .view(button3)
                        .titleText("Koleksiyon Oluşturdun Center!")
                        .descriptionText("Bir koleksiyon oluşturdun! Koleksiyon sekmesinin altından oluşturduğun koleksiyonlara ulaşabilir, yeni koleksiyon oluşturabilir ve koleksiyonlarını paylaşabilirsin. :)")
                        .titleTextColor(ContextCompat.getColor(getBaseContext(), R.color.white))
                        .backgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimaryDark))
                        .showCloseButton(true)
                        .arrowPosition(ArrowPosition.DOWN)
                        .highlightType(HighlightType.CIRCLE)
                        .arrowPercentage(100)
                        .cancelListener(new CancelListener() {
                            @Override
                            public void onCancel() {
                                Toast.makeText(MainActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build()
                        .show(MainActivity.this);
            }
        });
    }
}
