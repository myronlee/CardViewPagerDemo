package com.example.myronlg.cardviewpagerdemo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private static final float backgroundPageScale = 0.8F;
    private boolean dimEnabled = true;
    private MultiCardViewPager viewPager;
    private float dimRatio = 3F;
    private BeautyPagerAdapter adapter;
    private float pageWidthFactor = 0.7F;
    private float pageGoToBackgroundOffsetThreshold;
    private float frontBackScaleDelta;
    private float frontPageLeftOffset;

/*
    private static class MultiCardPageChangeListener implements MultiCardViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frontPageLeftOffset = (1 - pageWidthFactor) * 0.5F / pageWidthFactor;
        pageGoToBackgroundOffsetThreshold = 1 - frontPageLeftOffset;
        frontBackScaleDelta = 1 - backgroundPageScale;

        viewPager = (MultiCardViewPager) findViewById(R.id.card_view_pager);
        adapter = new BeautyPagerAdapter();
        viewPager.setAdapter(adapter);
//        viewPager.setPageTransformer(false, new CopyViewPager.PageTransformer() {
//            @Override
//            public void transformPage(View page, float position) {
//
//            }
//        });
        //这个逻辑比较复杂，总的来说是通过第一个page的相对窗口的偏移去计算当前可见的三个page的缩放比例，这个计算过程比较复杂
        //这跟你的page占屏幕宽度的比例，后台page缩放多少有关。
        viewPager.addOnPageChangeListener(new MultiCardViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.e("", position + "  " + positionOffset);
                if (position == 0 && positionOffset == 0) {
                    return;
                }

                View leftPage = viewPager.findViewWithTag(position);
                View middlePage = viewPager.findViewWithTag(position + 1);
                View rightPage = viewPager.findViewWithTag(position + 2);

                if (position == 0) {
                    if (middlePage != null) {
                        float scale = 1 - positionOffset * frontPageLeftOffset * frontBackScaleDelta;
                        setScale(middlePage, scale);
                        setDim(middlePage, scale);
                    }
                    if (rightPage != null) {
                        float scale = backgroundPageScale + positionOffset * frontPageLeftOffset * frontBackScaleDelta;
                        setScale(rightPage, scale);
                        setDim(rightPage, scale);
                    }
                    return;
                }

                if (leftPage != null) {
                    if (positionOffset < pageGoToBackgroundOffsetThreshold) {
                        //become smaller
                        float scale = 1 - (positionOffset + frontPageLeftOffset) * frontBackScaleDelta;
                        setScale(leftPage, scale);
                        setDim(leftPage, scale);
                    } else {
                        //stay still
                        setScale(leftPage, backgroundPageScale);
                        setDim(leftPage, backgroundPageScale);
                    }
                }

                if (middlePage != null) {
                    if (positionOffset < pageGoToBackgroundOffsetThreshold) {
                        //become bigger
                        float scale = backgroundPageScale + (positionOffset + frontPageLeftOffset) * frontBackScaleDelta;
                        setScale(middlePage, scale);
                        setDim(middlePage, scale);
                    } else {
                        //become smaller
                        float scale = 1 - (positionOffset - pageGoToBackgroundOffsetThreshold) * frontBackScaleDelta;
                        setScale(middlePage, scale);
                        setDim(middlePage, scale);
                    }
                }

                if (rightPage != null) {
                    if (positionOffset < pageGoToBackgroundOffsetThreshold) {
                        //stay still
                        setScale(rightPage, backgroundPageScale);
                        setDim(rightPage, backgroundPageScale);
                    } else {
                        //become bigger
                        float scale = backgroundPageScale + (positionOffset - pageGoToBackgroundOffsetThreshold) * frontBackScaleDelta;
                        setScale(rightPage, scale);
                        setDim(rightPage, scale);
                    }
                }


/*
                View left = viewPager.findViewWithTag(position - 1);
                View middle = viewPager.findViewWithTag(position);
                View right = viewPager.findViewWithTag(position + 1);
                if (left != null) {
                    float scale = 1 - (1 - backgroundPageScale)*positionOffset;
                    left.setScaleX(scale);
                    left.setScaleY(scale);
                }
                if (middle != null) {
                    float scale;
                    if (positionOffset < 0.8F) {
                        scale = 1 - (1 - backgroundPageScale) * (positionOffset + 0.2F);
                    } else {
                        scale = 1 - (1 - backgroundPageScale) * (1 - (positionOffset - 0.8F));
                    }
                    middle.setScaleX(scale);
                    middle.setScaleY(scale);
                    if (dimEnabled) {
                        ((CardView) middle).setDim((1 - scale)*dimRatio);
                    }
                }
                if (right != null) {
                    float scale;
                    if (positionOffset <= 0.8F) {
                        scale = backgroundPageScale + (1 - backgroundPageScale) * (positionOffset + 0.2F);
                    } else {
                        scale = backgroundPageScale + (1 - backgroundPageScale) * (1 - (positionOffset - 0.8F));
                    }

                    right.setScaleX(scale);
                    right.setScaleY(scale);
                    if (dimEnabled) {
                        ((CardView) right).setDim((1-scale)* dimRatio);
                    }
                }
*/

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOffscreenPageLimit(20);
    }

    private void setDim(View page, float scale) {
        if (dimEnabled && page instanceof CardView) {
            ((CardView) page).setDim((1 - scale) * dimRatio);
        }
    }

    private void setScale(View page, float scale) {
        page.setScaleX(scale);
        page.setScaleY(scale);
    }

    private class BeautyPagerAdapter extends PagerAdapter implements View.OnClickListener {

        private String[] datas = {"1", "2", "3", "4", "5"};
        private int[] resId = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j, R.drawable.k, R.drawable.l, R.drawable.m};
        private int[] colors = {Color.parseColor("#80e91e63"), Color.parseColor("#809c27b0"), Color.parseColor("#805677fc"), Color.parseColor("#8000bcd4"), Color.parseColor("#80009688")};
        public List<View> views;

        public BeautyPagerAdapter() {
            super();
            views = new ArrayList<>();
            views.add(new View(MainActivity.this));
            for (int position = 0; position < resId.length; position++) {
                final CardView cardView = new CardView(MainActivity.this);
                cardView.setImage(resId[position]);
                cardView.getImageView().setScaleType(ImageView.ScaleType.CENTER_CROP);
                cardView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

                if (position != 0) {
                    cardView.setScaleX(backgroundPageScale);
                    cardView.setScaleY(backgroundPageScale);
                    if (dimEnabled) {
                        cardView.setDim((1 - backgroundPageScale) * dimRatio);
                    }
                }

                cardView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        cardView.setPivotX(cardView.getWidth() * 0.5F);
                        cardView.setPivotY(cardView.getHeight() * 0.5F);
                        cardView.getViewTreeObserver().removeOnPreDrawListener(this);
                        return true;
                    }
                });
                cardView.setTag(position+1);
//                cardView.setOnClickListener(this);
                views.add(cardView);
            }
            views.add(new View(MainActivity.this));
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View view = views.get(position);

            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }

            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public float getPageWidth(int position) {
//            return pageWidthFactor;

            if (position == 0 || position == resId.length + 1) {
                return (1 - pageWidthFactor) * 0.5F;
            } else {
                return pageWidthFactor;
            }
        }

        @Override
        public int getCount() {
            return resId.length + 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, PageDetailActivity.class);
            intent.putExtra("resId", resId[((int) view.getTag())]);
            startActivity(intent);
        }
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
        if (id == R.id.action_settings) {
            dimEnabled = !dimEnabled;
            adapter = new BeautyPagerAdapter();
            viewPager.setAdapter(adapter);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
