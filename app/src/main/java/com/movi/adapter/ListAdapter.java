package com.movi.adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.movi.R;
import com.movi.model.Response;
import com.movi.ui.DetailsActivity;
import com.movi.widgets.TextViewLightSpace;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;


public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int DEFAULT_VIEW_TYPE = 1;
    private static final int NATIVE_AD_VIEW_TYPE = 2;
    private final Context mContext;
    private List<Response> mItems = new ArrayList<>();
    private final RequestManager requestManager;
    private NativeAd nativeAd;


    public MoviesAdapter(Context context, List<Response> movies, RequestManager requestManager) {
        mContext = context;
        mItems = movies;
        this.requestManager = requestManager;

    }

    @Override
    public int getItemViewType(int position) {
        if (position > 1 && position % 5 == 0) {
            return NATIVE_AD_VIEW_TYPE;
        }
        return DEFAULT_VIEW_TYPE;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        switch (viewType) {
            default:
                view = layoutInflater.inflate(R.layout.home_row, parent, false);
                return new MyViewHolder(view);

            case NATIVE_AD_VIEW_TYPE:
                view = layoutInflater.inflate(R.layout.list_item_native_ad, parent, false);
                return new NativeAdViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (!(holder instanceof MyViewHolder)) {
            return;
        }

        final Response model = mItems.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;


        myViewHolder.tvTitle.setText("" + model.getName());
        myViewHolder.tvTitle.setSelected(true);

        requestManager.load("http://img.youtube.com/vi/" + model.getDetails() + "/0.jpg")
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(myViewHolder.ivMainImage);


        myViewHolder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, DetailsActivity.class);
                mIntent.putExtra("yID", model.getDetails());
                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        int position;
        @BindView(R.id.ivMainImage)
        ImageView ivMainImage;
        @BindView(R.id.tvTitle)
        TextViewLightSpace tvTitle;
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.viewAnimation)
        View viewAnimation;
        @BindView(R.id.row)
        LinearLayout row;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            animationImage(viewAnimation);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
        }
    }

    public class NativeAdViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout adContainer;
        CardView card_view;

        public NativeAdViewHolder(View itemView) {
            super(itemView);


            adContainer = (LinearLayout) itemView.findViewById(R.id.native_ad_container);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
            showNativeAd(adContainer,card_view);
        }
    }

    private void showNativeAd(final LinearLayout nativeAdContainer, final CardView card_view) {
        nativeAd = new NativeAd(mContext, mContext.getString(R.string.fb_native_ads));
        nativeAd.setAdListener(new AdListener() {

            @Override
            public void onError(Ad ad, AdError error) {
                // Ad error callback
                card_view.setVisibility(View.GONE);

                Log.e(TAG, "onError: " + error.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e(TAG, "onAdLoaded: " + ad.getPlacementId() + "Placeid");
                card_view.setVisibility(View.VISIBLE);
                LayoutInflater inflater = LayoutInflater.from(mContext);
                LinearLayout adView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout, nativeAdContainer, false);
                nativeAdContainer.addView(adView);

                // Create native UI using the ad metadata.
                ImageView nativeAdIcon = (ImageView) adView.findViewById(R.id.native_ad_icon);
                TextView nativeAdTitle = (TextView) adView.findViewById(R.id.native_ad_title);
                MediaView nativeAdMedia = (MediaView) adView.findViewById(R.id.native_ad_media);
                TextView nativeAdSocialContext = (TextView) adView.findViewById(R.id.native_ad_social_context);
                TextView nativeAdBody = (TextView) adView.findViewById(R.id.native_ad_body);
                Button nativeAdCallToAction = (Button) adView.findViewById(R.id.native_ad_call_to_action);

                // Set the Text.
                nativeAdTitle.setText(nativeAd.getAdTitle());
                nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
                nativeAdBody.setText(nativeAd.getAdBody());
                nativeAdCallToAction.setText(nativeAd.getAdCallToAction());

                // Download and display the ad icon.
                NativeAd.Image adIcon = nativeAd.getAdIcon();
                NativeAd.downloadAndDisplayImage(adIcon, nativeAdIcon);

                // Download and display the cover image.
                nativeAdMedia.setNativeAd(nativeAd);

                // Add the AdChoices icon
                LinearLayout adChoicesContainer = (LinearLayout) adView.findViewById(R.id.ad_choices_container);
                AdChoicesView adChoicesView = new AdChoicesView(mContext, nativeAd, true);
                adChoicesContainer.addView(adChoicesView);

                // Register the Title and CTA button to listen for clicks.
                List<View> clickableViews = new ArrayList<>();
                clickableViews.add(nativeAdTitle);
                clickableViews.add(nativeAdCallToAction);
                nativeAd.registerViewForInteraction(nativeAdContainer, clickableViews);
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // On logging impression callback
            }
        });

        // Request an ad
        nativeAd.loadAd();
    }

    // color change animation when download images
    private void animationImage(final View holder) {
        final float[] hsv;
        final int[] runColor = new int[1];
        int hue = 0;
        hsv = new float[3]; // transition color
        hsv[1] = 1;
        hsv[2] = 1;

        ValueAnimator anim = ValueAnimator.ofInt(0, 100);
        anim.setDuration(10000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                hsv[0] = 360 * animation.getAnimatedFraction();
                runColor[0] = Color.HSVToColor(hsv);
                holder.setBackgroundColor(runColor[0]);
            }
        });

        anim.setRepeatCount(Animation.INFINITE);
        anim.start();
    }
}