# YD-master
娱乐类APP
一款娱乐类APP，包含今日资讯、最热视频、图片浏览、电影速递四个模块，每天自动更新主题。
- 1.整个项目使用MVP架构，UI遵循Material Design设计风格。
- 2.项目内大量使用了Fragment，所以封装了三个Fragment抽象类，避免了布局资源过多增加APK体积。同时所有的模块根据样式区别继承不同的抽象类，后期子模块的增加或者去除十分简便。
- 3.使用Fragment+FragmentPageAdapter，在Fragment可见时加载数据，节省资源。
- 4.将网络拉取数据作为一个单独的Module分离出来，使用Retrofit+RxJava+OkHttp技术实现。这样做的好处是，数据和UI分离，在后期项目成型后，界面更改或者接口更改仍然十分方便。
- 5.实现了有网络时从网络获取数据，无网络连接时从缓存读取数据。
- 6.封装了RecycleView，对外增加Item点击事件、长按事件以及下拉刷新和上拉加载的监听方法。优化了适配器类和ViewHolder类，使用更加简洁。
- 7.列表页使用RecycleView+CardView实现，并使用ItemTouchHelper实现长按拖拽。其中图片浏览模块使用瀑布流形式展现。
- 8.图片详情界面实现单点/多点触摸缩放图片，并且提供长按可下载功能。
- 9.使用了Glide加载网络图片以及动图。每天更换主题使用Palette从图片取色，从而实现主题动态切换。
- 10.使用ToolBar进行全版本状态栏适配。
- 11.FloatingActionButton自定义动作。

![GIF](https://github.com/JadynAi/YD-master/blob/master/GIF.gif)

5月初进行了一次项目重构

