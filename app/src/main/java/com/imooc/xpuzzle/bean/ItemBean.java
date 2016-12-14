package com.imooc.xpuzzle.bean;

import android.graphics.Bitmap;

/**
 * 拼图Item逻辑实体类：封装逻辑相关属性
 *
 * @author xys
 */
public class ItemBean {

    // Item的Id,此ItemBean在N*N网格的序号
    // 在交换位置时，此值不交换
    private int mItemId;
    // bitmap的Id, 每张小的Bitmap一个Id, (1,2,3,...N*N)
    // 在交换位置时，此值交换
    private int mBitmapId;
    // 一个小的Bitmap, 一张大的Bitmap划分成N*N 个小Bitmap
    // 在交换位置时，此值交换
    private Bitmap mBitmap;

    public ItemBean() {
    }

    public ItemBean(int mItemId, int mBitmapId, Bitmap mBitmap) {
        this.mItemId = mItemId;
        this.mBitmapId = mBitmapId;
        this.mBitmap = mBitmap;
    }

    public int getItemId() {
        return mItemId;
    }

    public void setItemId(int mItemId) {
        this.mItemId = mItemId;
    }

    public int getBitmapId() {
        return mBitmapId;
    }

    public void setBitmapId(int mBitmapId) {
        this.mBitmapId = mBitmapId;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

}
