package com.github.takahirom.material_design_animation_playground;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.github.takahirom.material_design_animation_playground.durationeasing.DurationAndEasingActivity;

public class MainActivity extends AppCompatActivity {

    private RecyclerView implementationRecyclerView;
    private static final int REQUEST_ID_DETAIL = 2;
    private ImplementationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        implementationRecyclerView = (RecyclerView) findViewById(R.id.implementation_list);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        implementationRecyclerView.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        adapter = new ImplementationAdapter(new ImplementationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ImageView fromImageView, ListItem item) {
                final Intent intent = new Intent(MainActivity.this, item.getActivityClass());
                intent.putExtra(DurationAndEasingActivity.INTENT_EXTRA_ITEM, item);
                final Bundle options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, fromImageView, "shared_element").toBundle();
                ActivityCompat.startActivityForResult(MainActivity.this, intent, REQUEST_ID_DETAIL, options);
            }
        });
        implementationRecyclerView.setAdapter(adapter);

        final float spaceSize = ScreenUtil.dp2px(1, this);
        implementationRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildViewHolder(view).getAdapterPosition() % 2 == 0) {
                    outRect.set((int)spaceSize, (int)spaceSize, ((int) (spaceSize / 2)), 0);
                }else{
                    outRect.set(((int) (spaceSize / 2)), (int)spaceSize, (int)spaceSize, 0);
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
    public void onActivityReenter(int resultCode, Intent data) {
        if (data == null || resultCode != RESULT_OK
                || !data.hasExtra(DurationAndEasingActivity.RESULT_EXTRA_ITEM_ID)) return;

        // When reentering, if the shared element is no longer on screen (e.g. after an
        // orientation change) then scroll it into view.
        final int itemId = data.getIntExtra(DurationAndEasingActivity.RESULT_EXTRA_ITEM_ID, -1);
        if (itemId != -1                                             // returning from a shot
                && adapter.getItemCount() > 0                           // grid populated
                && implementationRecyclerView.findViewHolderForItemId(itemId) == null) {    // view not attached
            final int position = adapter.getItemPosition(itemId);
            if (position == RecyclerView.NO_POSITION) return;

            // delay the transition until our shared element is on-screen i.e. has been laid out
            ActivityCompat.postponeEnterTransition(this);
            implementationRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int l, int t, int r, int b,
                                           int oL, int oT, int oR, int oB) {
                    implementationRecyclerView.removeOnLayoutChangeListener(this);
                    ActivityCompat.startPostponedEnterTransition(MainActivity.this);
                }
            });
            implementationRecyclerView.scrollToPosition(position);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
