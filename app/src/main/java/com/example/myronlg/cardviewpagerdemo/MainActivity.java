package com.example.myronlg.cardviewpagerdemo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private static final float SCALE_DST = 0.8F;
    private MultiCardViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (MultiCardViewPager) findViewById(R.id.card_view_pager);
        viewPager.setAdapter(new BeautyPagerAdapter());
//        viewPager.setPageTransformer(false, new CopyViewPager.PageTransformer() {
//            @Override
//            public void transformPage(View page, float position) {
//
//            }
//        });
        viewPager.addOnPageChangeListener(new MultiCardViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                View left = viewPager.findViewWithTag(position - 1);
//                Log.e("", position + " " + positionOffset);
                View middle = viewPager.findViewWithTag(position);
                View right = viewPager.findViewWithTag(position + 1);
//                if (left != null) {
//                    left.setPivotX(left.getWidth() * 0.5F);
//                    left.setPivotY(left.getHeight() * 0.5F);
//                    float scale = 1 - (1 - SCALE_DST)*positionOffset;
//                    left.setScaleX(scale);
//                    left.setScaleY(scale);
//                }


                if (middle != null) {
                    float scale;
//                    if (positionOffset < 0.2F) {
//                        scale = 1 - (1 - SCALE_DST) * (0.2F - positionOffset);
//                    } else if (positionOffset < 0.8F)
//                        scale = 1 - (1 - SCALE_DST) * (positionOffset - 0.2F);
//                    } else {
//                        s
//                    }
                    if (positionOffset < 0.8F) {
                        scale = 1 - (1 - SCALE_DST) * (positionOffset + 0.2F);
                    } else {
                        scale = 1 - (1 - SCALE_DST) * (1 - (positionOffset - 0.8F));
                    }
//                    float scale = 1 - (1 - SCALE_DST)*positionOffset;
                    middle.setScaleX(scale);
                    middle.setScaleY(scale);
                    ((CardView) middle).setDim((1 - scale)*4);

//                    ImageView imageView = (ImageView) middle;
//                    imageView.getDrawable().setColorFilter(Color.argb((int) (255 * (1 - scale)), 0, 0, 0), PorterDuff.Mode.SRC_ATOP);
//                    imageView.invalidate();
                }
                if (right != null) {
                    float scale;
                    if (positionOffset <= 0.8F) {
                        scale = SCALE_DST + (1 - SCALE_DST) * (positionOffset + 0.2F);
                    } else {
                        scale = SCALE_DST + (1 - SCALE_DST) * (1 - (positionOffset - 0.8F));
                    }

                    right.setScaleX(scale);
                    right.setScaleY(scale);
                    ((CardView) right).setDim((1-scale)*4);
//                    ImageView imageView = (ImageView) right;
//                    imageView.getDrawable().setColorFilter(Color.argb((int) (255 * (1 - scale)), 0, 0, 0), PorterDuff.Mode.SRC_ATOP);
//                    imageView.invalidate();
                }
            }

            @Override
            public void onPageSelected(int position) {
//                View currrentPage = viewPager.findViewWithTag(position);
//                currrentPage.setScaleX(1);
//                currrentPage.setScaleY(1);
//                Toast.makeText(MainActivity.this, position+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOffscreenPageLimit(20);
    }

    private class BeautyPagerAdapter extends PagerAdapter implements View.OnClickListener {

        private String[] datas = {"1", "2", "3", "4", "5"};
        private int[] resId = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j, R.drawable.k, R.drawable.l, R.drawable.m};
        private int[] colors = {Color.parseColor("#80e91e63"), Color.parseColor("#809c27b0"), Color.parseColor("#805677fc"), Color.parseColor("#8000bcd4"), Color.parseColor("#80009688")};
        private List<View> views;

//        final ViewPager.LayoutParams params = new ViewPager.LayoutParams();
//        params.width = ViewPager.LayoutParams.MATCH_PARENT;
//        params.height = ViewPager.LayoutParams.MATCH_PARENT;
//        params.gravity = Gravity.CENTER_HORIZONTAL;

        public BeautyPagerAdapter() {
            super();
            views = new ArrayList<>();
            for (int position = 0; position < resId.length; position++) {

//                final RoundedImageView imageView = new RoundedImageView(MainActivity.this);
//                final ImageView imageView = new ImageView(MainActivity.this);
//                imageView.setImageResource(resId[position]);
                final CardView cardView = new CardView(MainActivity.this);
                cardView.setImage(resId[position]);

                cardView.getImageView().setScaleType(ImageView.ScaleType.CENTER_CROP);
//                imageView.setCornerRadius(20);

                cardView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

//            textView.setText(datas[position]);
//            textView.setBackgroundColor(colors[position]);
//            textView.setGravity(Gravity.CENTER);

//                imageView.getDrawable().setColorFilter(Color.argb((int) (255 * (1 - 0.5)), 0, 0, 0), PorterDuff.Mode.SRC_ATOP);

                if (position != 0) {
                    cardView.setScaleX(SCALE_DST);
                    cardView.setScaleY(SCALE_DST);
                    cardView.setDim((1-SCALE_DST)*4);
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
                cardView.setTag(position);
                cardView.setOnClickListener(this);
                views.add(cardView);
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
//            return super.instantiateItem(container, position);
//            final TextView textView = new TextView(container.getContext());

            View imageView = views.get(position);

            if (imageView.getParent() != null) {
                ((ViewGroup) imageView.getParent()).removeView(imageView);
            }

            container.addView(imageView);

            return imageView;
//            textView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//                @Override
//                public boolean onPreDraw() {
//                    textView.setPivotX(textView.getWidth()*0.5F);
//                    textView.setPivotY(textView.getHeight()*0.5F);
//                    if (position != 0) {
//                        textView.setScaleX(SCALE_DST);
//                        textView.setScaleY(SCALE_DST);
//                    }
//                    textView.getViewTreeObserver().removeOnPreDrawListener(this);
//                    return true;
//                }
//            });

//            textView.setTag(position);

//            container.addView(textView, params);

//            return textView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public float getPageWidth(int position) {
            return 0.7F;
//            if (position == 0 || position == datas.length-1) {
//                return 0.1F;
//            } else {
//                return 0.8F;
//            }
        }

        @Override
        public int getCount() {
            return resId.length;
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
