package com.steven.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.widget.Toast;

import com.SkyEyesLive.LiiiFE.BaseApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by SkyEyes9 on 2015/12/10.
 * 图片合成水印，文字工具类
 */
public class WatermarkUtils {
    public WatermarkUtils(){

    }
    /**
     * 1.得到一张资源图片
     * 2.解析为Bitmap
     * 3.合成两张图片
     * @param pictureBitmap  源图片
     * @param watermark  水印图片
     * @return 合成图片
     * */
    public static Bitmap addPicture(Bitmap pictureBitmap, Bitmap watermark, int width, int height) {
        int w=pictureBitmap.getWidth();
        int h=pictureBitmap.getHeight();
        Bitmap newb = Bitmap.createBitmap( w, h, Bitmap.Config.ARGB_8888);//创建一个新的和SRC
        Canvas cv = new Canvas(newb);
        cv.drawBitmap(pictureBitmap, 0, 0, null );//在 0，0坐标开始画入src
        cv.drawBitmap( watermark, width, height, null );
        cv.save( Canvas.ALL_SAVE_FLAG );//保存
        cv.restore();//存储
        return newb;
    }

    /**
     * 1.得到一张资源图片
     * 2.解析为Bitmap
     * 3.合成两张图片
     * @param pictureBitmap  源图片
     * @param watermark  水印图片
     * @return 合成图片
     * */
    public static Bitmap addWatermark(Bitmap pictureBitmap, Bitmap watermark) {
//        Bitmap  pictureBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.beauty);
//        Bitmap  watermark=BitmapFactory.decodeResource(this.getResources(), R.drawable.life_watermark_small);

        int w=pictureBitmap.getWidth();
        int h=pictureBitmap.getHeight();

        int ww=watermark.getWidth();
        int wh=watermark.getHeight();

        Bitmap newb = Bitmap.createBitmap( w, h, Bitmap.Config.ARGB_8888);//创建一个新的和SRC

        Canvas cv = new Canvas(newb);
        //draw src into
        cv.drawBitmap(pictureBitmap, 0, 0, null );//在 0，0坐标开始画入src
        //draw watermark into
//      cv.drawBitmap( watermark, w - ww + 5, h - wh + 5, null );//在src的右下角画入水印
        cv.drawBitmap( watermark, 20, h - wh-20, null );//在src的右下角画入水印
        //save all clip
        cv.save( Canvas.ALL_SAVE_FLAG );//保存
        //store
        cv.restore();//存储

        return newb;
    }
    /**
     * 图片上动态添加文字
     * @param pictureBitmap 源图片
     * @param text 你要添加的文字
     * @return 合成图片
     * */
    public static Bitmap drawTextToBitmap(Bitmap pictureBitmap, String text,
                                          int x, int y) {
        //Bitmap  pictureBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.beauty);
        //临时标记图
        Bitmap imgTemp = Bitmap.createBitmap(pictureBitmap.getWidth(), pictureBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(imgTemp);
        Paint paint=new Paint();
        paint.setDither(true);//获取跟清晰的图像采样
        paint.setFilterBitmap(true);//过滤一些

        Rect src = new Rect(0, 0, pictureBitmap.getWidth(), pictureBitmap.getHeight());//创建一个指定的新矩形的坐标
        Rect dst = new Rect(0, 0, pictureBitmap.getWidth(), pictureBitmap.getHeight());//创建一个指定的新矩形的坐标

        canvas.drawBitmap(pictureBitmap, src, dst, paint);//将pictureBitmap缩放或则扩大到dst使用的填充区photoPaint
        Paint textPaint = new Paint();//设置画笔
        textPaint.setDither(true);
        textPaint.setAlpha(20);//设置透明度
        //textPaint.setStrokeWidth();
        textPaint.setTextSize(20.0f);//
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);//采用默认的宽度
        //textPaint.setShadowLayer(3f, 1, 1,this.getResources().getColor(android.R.color.background_dark));//阴影的设置
        textPaint.setColor(Color.WHITE);
        textPaint.setAlpha(255);
        //写个算法自动换行
        int i=20;//控制行距离
        int j=30;//控制列的字数
        if (text.length()<j){
            canvas.drawText(text, x, y,textPaint);//绘制上去字，开始未知x,y采用那只笔绘制(控制位置)
        }else if (text.length()>=j&&text.length()<j*2){
            canvas.drawText(text.substring(0,j), x, y,textPaint);
            canvas.drawText(text.substring(j), x, y+i,textPaint);
        }else if (text.length()>=j*2&&text.length()<j*3){
            canvas.drawText(text.substring(0,j), x, y,textPaint);
            canvas.drawText(text.substring(j,j*2), x, y+i,textPaint);
            canvas.drawText(text.substring(j*2), x, y+2*i,textPaint);
        }else if (text.length()>=j*3&&text.length()<j*4){
            canvas.drawText(text.substring(0,j), x, y,textPaint);
            canvas.drawText(text.substring(j,j*2), x, y+i,textPaint);
            canvas.drawText(text.substring(j*2,j*3), x, y+2*i,textPaint);
            canvas.drawText(text.substring(j*3), x, y+3*i,textPaint);
        }else if (text.length()>=j*4&&text.length()<j*5){
            canvas.drawText(text.substring(0,j), x, y,textPaint);
            canvas.drawText(text.substring(j,j*2), x, y+i,textPaint);
            canvas.drawText(text.substring(j*2,j*3), x, y+2*i,textPaint);
            canvas.drawText(text.substring(j*3,j*4), x, y+3*i,textPaint);
            canvas.drawText(text.substring(j*4), x, y+4*i,textPaint);
        }else if (text.length()>=j*5&&text.length()<j*6){//180个字符，6行
            canvas.drawText(text.substring(0,j), x, y,textPaint);
            canvas.drawText(text.substring(j,j*2), x, y+i,textPaint);
            canvas.drawText(text.substring(j*2,j*3), x, y+2*i,textPaint);
            canvas.drawText(text.substring(j*3,j*4), x, y+3*i,textPaint);
            canvas.drawText(text.substring(j*5,j*6), x, y+4*i,textPaint);
            canvas.drawText(text.substring(j*6), x, y+5*i,textPaint);
        }
        //canvas.drawText(text, x, y,textPaint);//绘制上去字，开始未知x,y采用那只笔绘制(控制位置)
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return imgTemp;
    }
    /**保存Bitmap到本地
     * @param bm 图片
     *@param path 路径
     * @param name 文件名
     * */
    public void saveBitmap(Bitmap bm, String path, String name) {
        File f=new File(path, name);
        if (f.exists()){
            f.delete();
        }
        try{
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(BaseApplication.mInstance,"保存成功！", Toast.LENGTH_SHORT).show();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
