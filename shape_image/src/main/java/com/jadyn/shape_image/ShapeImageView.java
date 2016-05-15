package com.jadyn.shape_image;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.jadyn.shape_image.path.PathParser;

/**
 * @author 景通
 *         <p>
 *         自定义ImageView，圆形、心形等. 可以使用BitmapUtils直接使用
 */
public class ShapeImageView extends ImageView {

    /**
     *
     */
    public static final String TRIANGLE = "triangle";

    /**
     * 心形
     */
    public static final String HEART = "heart";

    /**
     * 三角形
     */
    public static final String COMMOA = "commoa";

    /**
     * 星形
     */
    public static final String STAR = "star";

    /**
     * 圆形
     */
    public static final String ROUND = "round";

    public static final String SIX_FLOWER = "sixflower";

    private Context mContext;

    private int border_size = 0;            // 边框厚度

    private int in_border_color = 0;            // 内圆边框颜色

    private int out_border_color = 0;            // 外圆边框颜色

    private int defColor = 0xFFFFFFFF;    // 默认颜色

    private int width = 0;            // 控件的宽度

    private int height = 0;            // 控件的高度

    private String shape_type;                            // 形状的类型

    private Bitmap mBitmap;

    private Double mMu = 3.0;//图形的大小

    public ShapeImageView(Context context, double mu) {

        super(context);
        this.mContext = context;
        mMu = mu;
    }

    public ShapeImageView(Context context, AttributeSet attrs) {

        super(context, attrs);
        this.mContext = context;
        setAttributes(attrs);
    }

    public ShapeImageView(Context context, AttributeSet attrs,
                          int defStyle) {

        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        this.mContext = context;
        setAttributes(attrs);
    }

    /**
     * 获得自定义属性
     *
     * @param attrs
     */
    private void setAttributes(AttributeSet attrs) {
        // TODO Auto-generated method stub
        TypedArray mArray = mContext.obtainStyledAttributes(attrs,
                R.styleable.shapeimageview);
        // 得到边框厚度，否则返回0
        border_size = mArray.getDimensionPixelSize(
                R.styleable.shapeimageview_border_size, 0);
        // 得到内边框颜色，否则返回默认颜色
        in_border_color = mArray.getColor(
                R.styleable.shapeimageview_in_border_color, defColor);
        // 得到外边框颜色，否则返回默认颜色
        out_border_color = mArray.getColor(
                R.styleable.shapeimageview_out_border_color,
                defColor);

        //自定义属性设置图形大小
        mMuNum = Double.valueOf(mArray.getString(R.styleable.shapeimageview_mu_num));
        // 得到形状的类型
        shape_type = mArray
                .getString(R.styleable.shapeimageview_shape_type);

        mArray.recycle();// 回收mArray
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas); 必须去掉该行或注释掉，否则会出现两张图片
        // 得到传入的图片
        if (mBitmap == null) {
            return;
        }
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        this.measure(0, 0);

        Bitmap cpBitmap = mBitmap.copy(Config.ARGB_8888, true);
        // 得到画布宽高
        width = getWidth();
        height = getHeight();

        int radius = 0;//
        // 判断是否是圆形
        if (ROUND.equals(shape_type)) {
            // 如果内圆边框和外圆边框的颜色不等于默认颜色，则说明该圆有两个边框
            if (in_border_color != defColor
                    && out_border_color != defColor) {
                // 计算出半径
                radius = (width < height ? width : height) / 2
                        - 2 * border_size;
                // 画内圆边框
                drawCircleBorder(canvas, radius + border_size / 2,
                        in_border_color);
                // 画外圆边框
                drawCircleBorder(canvas,
                        radius + border_size + border_size / 2,
                        out_border_color);
            } // 如果内圆边框颜色不等于默认颜色，则说明该圆有一个边框
            else if (in_border_color != defColor
                    && out_border_color == defColor) {
                radius = (width < height ? width : height) / 2
                        - border_size;

                drawCircleBorder(canvas, radius + border_size / 2,
                        in_border_color);
            } // 如果外圆边框颜色不等于默认颜色，则说明该圆有一个边框
            else if (in_border_color == defColor
                    && out_border_color != defColor) {
                radius = (width < height ? width : height) / 2
                        - border_size;

                drawCircleBorder(canvas, radius + border_size / 2,
                        out_border_color);
            } else {// 没有边框
                radius = (width < height ? width : height) / 2;
            }
        } else {
            radius = (width < height ? width : height) / 2;
        }

        Bitmap shapeBitmap = drawShapeBitmap(cpBitmap, radius);
        canvas.drawBitmap(shapeBitmap, width / 2 - radius,
                height / 2 - radius, null);
    }

    /**
     * 画出指定形状的图片
     *
     * @param radius
     * @return
     */
    private Bitmap drawShapeBitmap(Bitmap bmp, int radius) {

        Bitmap squareBitmap;// 根据传入的位图截取合适的正方形位图
        Bitmap scaledBitmap;// 根据diameter对截取的正方形位图进行缩放
        int diameter = radius * 2;
        // 传入位图的宽高
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        // 为了防止宽高不相等，造成圆形图片变形，因此截取长方形中处于中间位置最大的正方形图片
        int squarewidth = 0, squareheight = 0;// 矩形的宽高
        int x = 0, y = 0;
        if (h > w) {// 如果高>宽
            squarewidth = squareheight = w;
            x = 0;
            y = (h - w) / 2;
            // 截取正方形图片
            squareBitmap = Bitmap.createBitmap(bmp, x, y, squarewidth,
                    squareheight);
        } else if (h < w) {// 如果宽>高
            squarewidth = squareheight = h;
            x = (w - h) / 2;
            y = 0;
            squareBitmap = Bitmap.createBitmap(bmp, x, y, squarewidth,
                    squareheight);
        } else {
            squareBitmap = bmp;
        }
        // 对squareBitmap进行缩放为diameter边长的正方形位图
        if (squareBitmap.getWidth() != diameter
                || squareBitmap.getHeight() != diameter) {
            scaledBitmap = Bitmap.createScaledBitmap(squareBitmap,
                    diameter, diameter, true);
        } else {
            scaledBitmap = squareBitmap;
        }

        Bitmap outputbmp = Bitmap.createBitmap(
                scaledBitmap.getWidth(), scaledBitmap.getHeight(),
                Config.ARGB_8888);
        Canvas canvas = new Canvas(outputbmp);// 创建一个相同大小的画布
        Paint paint = new Paint();// 定义画笔
        paint.setAntiAlias(true);// 设置抗锯齿
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);

        if (STAR.equals(shape_type)) {// 如果绘制的形状为五角星形

            String s = "m 95.292133,36.490047 c -0.821062,-2.428382 -2.928552,-4.206358 -5.46584,-4.592346 L 65" +
                    ".147008,28.135077 54.419854,5.2979624 c -1.12933,-2.3979885 -3.545089,-3.9328227 -6.200942,-3" +
					".9328227 -2.655851,0 -5.07161,1.5348342 -6.200941,3.9328227 L 31.290818,28.135077 6.608583,31" +
					".897701 c -2.5343234,0.385988 -4.6418134,2.163964 -5.4599108,4.592346 -0.81513327,2.434459 -0" +
					".19859612,5.1151 1.591733,6.944745 L 20.827472,61.950059 16.63324,87.634977 c -0.426834,2.586423 " +
					"0.666928,5.181965 2.809987,6.695524 1.176755,0.829723 2.561,1.252182 3.945245,1.252182 1.138222,0" +
					" 2.279409,-0.285692 3.313887,-0.854037 L 48.218912,82.851157 69.738431,94.728646 c 1.034493,0" +
					".568345 2.175679,0.854037 3.310938,0.854037 1.384244,0 2.768489,-0.422459 3.948209,-1.252182 C 79" +
					".140637,92.816942 80.22847,90.2214 79.8046,87.634977 L 75.607406,61.950059 93.7004,43.434792 c 1" +
					".787365,-1.832684 2.400937,-4.513325 1.591733,-6.944745 z";
            Path path = new Path();
            PathParser.parse(s, path, mMuNum == 0 ? mMu : mMuNum);
            canvas.drawPath(path, paint);
        } else if (COMMOA.equals(shape_type)) {// 如果绘制的形状为逗号

            String s = "m 39.013454,37.47982 c 2.31581,1.090455 -0.327373,3.659657 -1.812408,3.849028 -4.024353,0" +
					".513182 -6.340615,-4.044733 -5.885647,-7.473844 0.813831,-6.133875 7.540507,-9.237984 13.13528,-7" +
					".922267 8.210555,1.930869 12.197995,11.075309 9.958886,18.796716 C 51.425185,55.020875 39.786701," +
					"59.912756 29.951413,56.724958 17.574706,52.713441 11.770001,38.548729 15.919289,26.60537 20" +
					".944632,12.140363 37.652102,5.4184882 51.700313,10.536627 68.255643,16.568176 75.897278,35.828022" +
					" 69.805676,51.979087 62.772679,70.62613 40.954364,79.189214 22.70178,72.121069 1.9620332,64" +
					".089794 -7.5236637,39.708902 0.523178,19.355737 9.5505183,-3.477438 36.496888,-13.886589 58" +
					".949946,-4.8594838 83.877096,5.162314 95.210329,34.676288 85.201786,59.22872";
            Path path = new Path();
            PathParser.parse(s, path, mMuNum == 0 ? mMu : mMuNum);
            canvas.drawPath(path, paint);
        } else if (TRIANGLE.equals(shape_type)) {// 如果绘制的形状为三角形
            Path path = new Path();

            path.moveTo(0, 0);// 此点为多边形的起点
            path.lineTo(diameter / 2, diameter);
            path.lineTo(diameter, 0);

            path.close(); // 使这些点构成封闭的多边形
            canvas.drawPath(path, paint);
        } else if (HEART.equals(shape_type)) {// 如果绘制的形状为心形
            String s = "m 26.43068,2.9403681 c -13.22904,0 -23.965642,11.3451439 -23.965642,25.3239859 0,28.437889 27" +
					".14772,35.89407 45.647065,64.008073 17.489783,-27.941509 45.647075,-36.478063 45.647075,-64" +
					".008073 0,-13.978841 -10.736591,-25.3239883 -23.965652,-25.3239859 -9.595825,0 -17.854726,5" +
					".9871011 -21.681423,14.6008629 C 44.285427,8.9274692 36.026526,2.9403657 26.43068,2.9403681 z";
            Path path = new Path();
            PathParser.parse(s, path, mMuNum == 0 ? mMu : mMuNum);
            canvas.drawPath(path, paint);

        } else if (SIX_FLOWER.equals(shape_type)) {// 如果绘制的形状为六叶草

            String s = "M 42.190849,0.5573364 C 40.910652,0.6117313 39.547784,0.74487738 38.258366,1.0647525 27" +
					".942925,3.6253777 21.735207,14.032254 24.323697,24.236758 c 0.396367,1.562545 1.060856,2.956843 1" +
					".795266,4.313039 -1.501676,-0.494183 -3.048765,-0.818416 -4.701883,-0.930264 -0.663175,-0.04492 " +
					"-1.401032,-0.01894 -2.05173,0 C 9.6051012,27.942846 1.4947446,35.538743 0.81428125,45.3791 0" +
					".08848831,55.875443 8.1564976,64.957706 18.766926,65.675747 c 1.583226,0.106895 3.122023,0.0017 4" +
					".616394,-0.253574 -0.912528,1.238042 -1.703292,2.571721 -2.308196,4.059331 -3.969169,9.760793 0" +
					".819215,21.021534 10.686095,24.947962 9.866882,3.926508 21.16453,-0.979599 25.133701,-10.74031 0" +
					".605642,-1.489367 0.899094,-2.963255 1.111352,-4.482176 0.910722,1.247244 1.998085,2.432567 3" +
					".248574,3.467345 8.157461,6.750474 20.361512,5.701788 27.185434,-2.367942 6.82392,-8.069704 5" +
					".763668,-20.05796 -2.393684,-26.808489 -1.236377,-1.023087 -2.633442,-1.870161 -4.017977,-2.53708" +
					" 1.482308,-0.470532 2.980183,-1.089282 4.359929,-1.945097 9.010655,-5.588749 11.719209,-17.47184 " +
					"6.069704,-26.385641 -3.884007,-6.128234 -10.703526,-9.246639 -17.525201,-8.795215 -3.100768,0" +
					".205403 -6.245988,1.128927 -9.061811,2.87536 -1.376302,0.853623 -2.601047,1.853218 -3.676017,2" +
					".959927 0.0026,-1.534994 -0.117358,-3.176857 -0.512933,-4.735884 C 59.41729,6.0052813 51.152289,0" +
					".17546168 42.190849,0.5574717 z";
            Path path = new Path();
            // 数据较小，全部乘以3
//			PathParser.parse(s, path, 3);
            //如果通过构造函数传入参数，就使用构造函数的参数，如果没有就是3
            PathParser.parse(s, path, mMuNum == 0 ? mMu : mMuNum);
            canvas.drawPath(path, paint);

        } else {// 这是默认形状，圆形
            canvas.drawCircle(scaledBitmap.getWidth() / 2,
                    scaledBitmap.getHeight() / 2,
                    scaledBitmap.getWidth() / 2, paint);// 绘制圆形
        }
        // 设置Xfermode的Mode
        paint.setXfermode(
                new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(scaledBitmap, 0, 0, paint);

        bmp = null;
        squareBitmap = null;
        scaledBitmap = null;
        return outputbmp;

    }

    /**
     * 角度转弧度公式
     *
     * @param degree
     * @return
     */
    private float degree2Radian(int degree) {
        // TODO Auto-generated method stub
        return (float) (Math.PI * degree / 180);
    }

    /**
     * 如果图片为圆形，这该方法为画出圆形图片的有色边框
     *
     * @param canvas
     * @param radius
     * @param color
     */
    private void drawCircleBorder(Canvas canvas, int radius,
                                  int color) {
        // TODO Auto-generated method stub
        Paint paint = new Paint();

        paint.setAntiAlias(true);// 抗锯齿
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setColor(color);// 设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);// 设置画笔的style为STROKE：空心
        paint.setStrokeWidth(border_size);// 设置画笔的宽度
        // 画出空心圆，也就是边框
        canvas.drawCircle(width / 2, height / 2, radius, paint);
    }

    /*
     * 对外提供的设置ImageView的样式
     */
    public void setImageType(String type) {

        this.shape_type = type;
    }

    /*
     * BitmapUtils内部调用此方法来实现display方法的
     */
    @Override
    public void setImageDrawable(Drawable drawable) {

        super.setImageDrawable(drawable);
        mBitmap = getBitmapFromDrawable(drawable);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {

        super.setImageBitmap(bm);
        mBitmap = bm;
    }

    private static final Config BITMAP_CONFIG = Config.ARGB_8888;

    private static final int COLORDRAWABLE_DIMENSION = 2;

    private Double mMuNum;

    private Bitmap getBitmapFromDrawable(Drawable drawable) {

        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable)
                    .getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION,
                        COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(),
                    canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
