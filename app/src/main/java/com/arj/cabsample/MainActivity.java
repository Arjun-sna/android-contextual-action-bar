package com.arj.cabsample;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.arj.cab.CabCallback;
import com.arj.cab.ContextualActionBar;

public class MainActivity extends AppCompatActivity {

    private ContextualActionBar customCab;
    private CabCallback cabCallback = new CabCallback() {
        @Override
        public boolean onCreateCab(ContextualActionBar cab, Menu menu) {
            return true;
        }

        @Override
        public boolean onCabItemClicked(MenuItem item) {
            switch (item.getItemId()){
                case R.id.delete_chat_heads:
                    Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                    customCab.finish();
                    return true;
                case R.id.select_all:
                    Toast.makeText(MainActivity.this, "Selected all", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.select_none:
                    Toast.makeText(MainActivity.this, "Deselected all", Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public boolean onDestroyCab(ContextualActionBar cab) {
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.materialToolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(customCab == null) {
                    customCab = new ContextualActionBar(MainActivity.this, R.id.cab_stub)
                            .setMenu(R.menu.sample_menu)
                            .setCloseDrawableRes(R.drawable.up_button_white)
                            .setTitle(getResources().getString(R.string.cab_text))
                            .setLayoutAnim(R.anim.layout_anim)
                            .setTitleTypeFace(Typeface.createFromAsset(MainActivity.this.getAssets(), "fonts/Avenir-Medium.ttf"))
                            .start(cabCallback);
                } else {
                    customCab.restore();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_open_cab) {
            if(customCab == null) {
                customCab = new ContextualActionBar(MainActivity.this, R.id.cab_stub)
                        .setMenu(R.menu.sample_menu)
                        .setCloseDrawableRes(R.drawable.up_button_white)
                        .setTitle(getResources().getString(R.string.cab_text))
                        .start(cabCallback);
            } else {
                customCab.setTitle(getResources().getString(R.string.cab_text))
                        .restore();
            }
           return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
