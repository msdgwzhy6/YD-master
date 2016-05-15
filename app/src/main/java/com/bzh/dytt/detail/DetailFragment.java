package com.bzh.dytt.detail;

import android.graphics.Bitmap;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bzh.common.utils.UIUtils;
import com.bzh.data.film.DetailEntity;
import com.bzh.dytt.R;
import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.FragmentArgs;
import com.bzh.dytt.base.basic.FragmentContainerActivity;
import com.bzh.dytt.base.basic_pageswitch.PageFragment;
import com.bzh.dytt.base.basic_pageswitch.PagePresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailFragment extends PageFragment implements IDetailView {

    public static final String FILM_URL = "FILM_URL";

    public static void launch(BaseActivity from, String url) {
        FragmentArgs fragmentArgs = new FragmentArgs();
        fragmentArgs.add(FILM_URL, url);
        FragmentContainerActivity.launch(from, DetailFragment.class, fragmentArgs);
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.film_poster)
    ImageView filmPoster;

    @BindView(R.id.appbar)
    AppBarLayout appbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    // 片名
    @BindView(R.id.layout_name)
    LinearLayout layout_name;
    @BindView(R.id.name)
    TextView name;

    // 译名
    @BindView(R.id.layout_translationName)
    LinearLayout layout_translationName;
    @BindView(R.id.translationName)
    TextView translationName;

    // 年代
    @BindView(R.id.layout_years)
    LinearLayout layout_years;
    @BindView(R.id.years)
    TextView years;

    // 国家
    @BindView(R.id.layout_country)
    LinearLayout layout_country;
    @BindView(R.id.country)
    TextView country;

    // 类型
    @BindView(R.id.layout_category)
    LinearLayout layout_category;
    @BindView(R.id.category)
    TextView category;

    // 语言
    @BindView(R.id.layout_language)
    LinearLayout layout_language;
    @BindView(R.id.language)
    TextView language;

    // 字幕
    @BindView(R.id.layout_subtitle)
    LinearLayout layout_subtitle;
    @BindView(R.id.subtitle)
    TextView subtitle;

    // 字幕
    @BindView(R.id.layout_showTime)
    LinearLayout layout_showTime;
    @BindView(R.id.showTime)
    TextView showTime;

    // 集数
    @BindView(R.id.layout_episodeNumber)
    LinearLayout layout_episodeNumber;
    @BindView(R.id.episodeNumber)
    TextView episodeNumber;

    // 来源
    @BindView(R.id.layout_source)
    LinearLayout layout_source;
    @BindView(R.id.source)
    TextView source;

    // IMDB评分
    @BindView(R.id.layout_imdb)
    LinearLayout layout_imdb;
    @BindView(R.id.imdb)
    TextView imdb;

    // 发布时间
    @BindView(R.id.layout_publishTime)
    LinearLayout layout_publishTime;
    @BindView(R.id.publishTime)
    TextView publishTime;

    // 上映时间
    @BindView(R.id.layout_playtime)
    LinearLayout layout_playtime;
    @BindView(R.id.playtime)
    TextView playtime;

    // 视频格式
    @BindView(R.id.layout_fileFormat)
    LinearLayout layout_fileFormat;
    @BindView(R.id.fileFormat)
    TextView fileFormat;

    // 视频尺寸
    @BindView(R.id.layout_videoSize)
    LinearLayout layout_videoSize;
    @BindView(R.id.videoSize)
    TextView videoSize;

    // 文件大小
    @BindView(R.id.layout_fileSize)
    LinearLayout layout_fileSize;
    @BindView(R.id.fileSize)
    TextView fileSize;

    // 导演
    @BindView(R.id.layout_director)
    LinearLayout layout_director;
    @BindView(R.id.director)
    TextView director;

    // 编辑
    @BindView(R.id.layout_screenWriters)
    LinearLayout layout_screenWriters;
    @BindView(R.id.screenWriters)
    TextView screenWriters;

    // 主演
    @BindView(R.id.layout_leadingPlayers)
    LinearLayout layout_leadingPlayers;
    @BindView(R.id.leadingPlayers)
    TextView leadingPlayers;

    // 描述
    @BindView(R.id.layout_description)
    LinearLayout layout_description;
    @BindView(R.id.description)
    TextView description;

    // 预览
    @BindView(R.id.layout_previewImage)
    LinearLayout layout_previewImage;
    @BindView(R.id.previewImage)
    ImageView previewImage;

    private DetailPresenter detailPresenter;

    @Override
    protected PagePresenter initPresenter() {
        detailPresenter = new DetailPresenter(getBaseActivity(), this, this);
        return detailPresenter;
    }

    @Override
    public void initFab() {
        fab.setOnClickListener(detailPresenter);
    }

    public void setText(TextView textView, LinearLayout layout, String str) {
        textView.setText(TextUtils.isEmpty(str) ? "" : str);
        layout.setVisibility(TextUtils.isEmpty(str) ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setFilmDetail(DetailEntity detailEntity) {
        collapsingToolbar.setTitle(detailEntity.getTranslationName());

        setText(name, layout_name, detailEntity.getName());                  // 1. 名字
        setText(translationName, layout_translationName, detailEntity.getTitle());     // 2. 译名
        setText(years, layout_years, detailEntity.getYears());                // 3. 年代
        setText(country, layout_country, detailEntity.getCountry());            // 4. 国家
        setText(category, layout_category, detailEntity.getCategory());          // 5．类型
        setText(language, layout_language, detailEntity.getLanguage());          // 6. 语言
        setText(showTime, layout_showTime, detailEntity.getShowTime());          // 7. 片长
        setText(publishTime, layout_publishTime, detailEntity.getPublishTime());    // 8. 发布时间
        setText(playtime, layout_playtime, detailEntity.getPlaytime());          // 9. 上映时间
        setText(subtitle, layout_subtitle, detailEntity.getSubtitle());          // 10. 字母
        setText(fileFormat, layout_fileFormat, detailEntity.getFileFormat());      // 11. 文件格式
        setText(videoSize, layout_videoSize, detailEntity.getVideoSize());        // 12.　视频尺寸
        setText(fileSize, layout_fileSize, detailEntity.getFileSize());        // 12.　文件大小
        setText(imdb, layout_imdb, detailEntity.getImdb());                  // 13. 评分
        setText(episodeNumber, layout_episodeNumber, detailEntity.getEpisodeNumber());// 14 集数
        setText(source, layout_source, detailEntity.getSource());               // 15. 来源
        if (detailEntity.getDirectors() != null && detailEntity.getDirectors().size() > 0) {
            setText(director, layout_director, detailEntity.getDirectors().get(0));// 16. 导演
        }else {
            layout_director.setVisibility(View.GONE);
        }
        if (detailEntity.getScreenWriters() != null && detailEntity.getScreenWriters().size() > 0) {
            setText(screenWriters, layout_screenWriters, detailEntity.getScreenWriters().get(0));// 17. 编辑
        }else {
            layout_screenWriters.setVisibility(View.GONE);
        }
        if (detailEntity.getLeadingPlayers() != null && detailEntity.getLeadingPlayers().size() > 0) {
            setText(leadingPlayers, layout_leadingPlayers, detailEntity.getLeadingPlayers().get(0));// 18. 主演
        }else {
            layout_leadingPlayers.setVisibility(View.GONE);
        }

        setText(description, layout_description, detailEntity.getDescription());// 19. 描述

        Glide.with(this)
                .load(detailEntity.getCoverUrl())
                .into(filmPoster);

        if (TextUtils.isEmpty(detailEntity.getPreviewImage())) {
            layout_previewImage.setVisibility(View.GONE);
        } else {
            layout_previewImage.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(detailEntity.getPreviewImage())
                    .asBitmap()
                    .into(new BitmapImageViewTarget(previewImage) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            super.setResource(resource);
                            int width = resource.getWidth();
                            int height = resource.getHeight();
                            float ratio = width * 1.0F / height;
                            float targetHeight = UIUtils.getScreenWidth() * 1.0F / ratio;

                            ViewGroup.LayoutParams params = previewImage.getLayoutParams();
                            params.height = (int) targetHeight;
                            previewImage.setLayoutParams(params);

                            previewImage.setImageBitmap(resource);
                        }
                    });
        }
    }


    @Override
    public void initToolbar() {
        getBaseActivity().setSupportActionBar(toolbar);
        if (getBaseActivity().getSupportActionBar() != null) {
            getBaseActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getBaseActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_film_detail;
    }

    @OnClick(R.id.fab)
    public void onClickFab(View v) {
        detailPresenter.onClick(v);
    }
}
