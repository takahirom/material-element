package com.github.takahirom.materialelement.main;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.github.takahirom.materialelement.R;
import com.github.takahirom.materialelement.util.AndroidVersionUtil;
import com.github.takahirom.materialelement.util.ScreenUtil;
import com.github.takahirom.materialelement.motion.durationeasing.DurationAndEasingActivity;
import com.github.takahirom.materialelement.util.ThemeUtil;

public class MainActivity extends AppCompatActivity {

    private RecyclerView implementationRecyclerView;
    private static final int REQUEST_ID_DETAIL = 2;
    private ImplementationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtil.setTaskDescriptionColor(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        implementationRecyclerView = (RecyclerView) findViewById(R.id.implementation_list);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        implementationRecyclerView.setLayoutManager(gridLayoutManager);
        adapter = new ImplementationAdapter(this, new ImplementationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ImageView fromImageView, ImplementationItem item) {
                final Intent intent = new Intent(MainActivity.this, item.getActivityClass());
                intent.putExtra(DurationAndEasingActivity.INTENT_EXTRA_ITEM, item);
                String sharedElementName = getString(R.string.transition_name_implementation_image);
                final Bundle options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, fromImageView, sharedElementName).toBundle();
                ActivityCompat.startActivityForResult(MainActivity.this, intent, REQUEST_ID_DETAIL, options);
            }
        });
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = adapter.getItemViewType(position);
                return itemViewType == ImplementationAdapter.VIEW_TYPE_HEADER ? 2 : 1;
            }
        });

        implementationRecyclerView.setAdapter(adapter);

        final float spaceSize = ScreenUtil.dp2px(4, this);
        implementationRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int adapterPosition = parent.getChildViewHolder(view).getAdapterPosition();
                GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
                int spanSize = spanSizeLookup.getSpanSize(adapterPosition);
                if (spanSize == 2) {
                    return;
                }
                int spanIndex = spanSizeLookup.getSpanIndex(adapterPosition, gridLayoutManager.getSpanCount());
                if (spanIndex == 0) {
                    outRect.set((int) spaceSize, (int) spaceSize, ((int) (spaceSize / 2)), 0);
                } else {
                    outRect.set(((int) (spaceSize / 2)), (int) spaceSize, (int) spaceSize, 0);
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
            boolean isEnable = Settings.Secure.getInt(this.getContentResolver(),
                    Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) == 1;

            if (isEnable) {
                Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
                startActivity(intent);
            } else {
                Snackbar.make(implementationRecyclerView, R.string.main_not_enabled_developer_mode, Snackbar.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
