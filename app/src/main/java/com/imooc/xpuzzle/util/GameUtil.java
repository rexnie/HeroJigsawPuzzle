package com.imooc.xpuzzle.util;

import com.imooc.xpuzzle.activity.PuzzleMain;
import com.imooc.xpuzzle.bean.ItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 拼图工具类：实现拼图的交换与生成算法
 *
 * @author xys
 */
public class GameUtil {

    // 游戏信息单元格Bean
    public static List<ItemBean> mItemBeans = new ArrayList<>();
    // 空格单元格
    public static ItemBean mBlankItemBean = new ItemBean();

    /**
     * 判断点击的Item是否可移动
     *
     * @param position position
     * @return 能否移动
     */
    public static boolean isMoveable(int position) {
        int type = PuzzleMain.TYPE;
        // 获取空格Item
        int blankId = GameUtil.mBlankItemBean.getItemId() - 1;
        // 不同行 相差为type, 上下行
        if (Math.abs(blankId - position) == type) {
            return true;
        }
        // 相同行 相差为1，前后的位置
        if ((blankId / type == position / type) &&
                Math.abs(blankId - position) == 1) {
            return true;
        }
        return false;
    }

    /**
     * 交换空格与点击Item的位置
     *
     * @param from  交换图
     * @param blank 空白图
     */
    public static void swapItems(ItemBean from, ItemBean blank) {
        ItemBean tempItemBean = new ItemBean();
        // 交换BitmapId
        tempItemBean.setBitmapId(from.getBitmapId());
        from.setBitmapId(blank.getBitmapId());
        blank.setBitmapId(tempItemBean.getBitmapId());
        // 交换Bitmap
        tempItemBean.setBitmap(from.getBitmap());
        from.setBitmap(blank.getBitmap());
        blank.setBitmap(tempItemBean.getBitmap());
        // 设置新的Blank
        GameUtil.mBlankItemBean = from;
    }

    /**
     * 生成随机的Item
     */
    public static void getPuzzleGenerator() {
        int index = 0;
        // 随机打乱顺序
        for (int i = 0; i < mItemBeans.size(); i++) {
            index = (int) (Math.random() *
                    PuzzleMain.TYPE * PuzzleMain.TYPE);
            swapItems(mItemBeans.get(index), GameUtil.mBlankItemBean);
        }
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < mItemBeans.size(); i++) {
            data.add(mItemBeans.get(i).getBitmapId());
        }
        // 判断生成是否有解
        if (canSolve(data)) {
            return;
        } else {
            getPuzzleGenerator();
        }
    }

    /**
     * 是否拼图成功
     *
     * @return 是否拼图成功
     */
    public static boolean isSuccess() {
        for (ItemBean tempBean : GameUtil.mItemBeans) {
            if (tempBean.getBitmapId() != 0 && // 1 -- (N*N-1)的bitmap
                    (tempBean.getItemId()) == tempBean.getBitmapId()) {
                // itemId是有序的，如果itemId==bitmapId, 即表示已排序
                continue;
            } else if (tempBean.getBitmapId() == 0 &&  // 空白bitmap
                    tempBean.getItemId() == PuzzleMain.TYPE * PuzzleMain.TYPE) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 该数据是否有解

     N puzzle问题的解
     假设是3x3的序列A：A0, A1, … A7，
     其中有一个空白X;
     序列A的倒置和为SumA
     1. 如果序列的元素个数(此处为9)是奇数的，那么,
        SumA为偶数，则有解。SumA为奇数则无解
     2. 如果序列的元素个数(此处为9)是偶数的，那么,
        当X位于从下往上数的奇数行中，SumA为偶数则有解
        当X位于从下往上数的偶数行中，SumA为奇数则有解
     TODO：验证此算法
     * @param data 拼图数组数据
     * @return 该数据是否有解
     */
    public static boolean canSolve(List<Integer> data) {
        // 获取空格Id
        int blankId = GameUtil.mBlankItemBean.getItemId();
        // 可行性原则
        if (data.size() % 2 == 1) {
            return getInversions(data) % 2 == 0;
        } else {
            // 从底往上数,空格位于奇数行
            if (((blankId - 1) / PuzzleMain.TYPE) % 2 == 1) {
                return getInversions(data) % 2 == 0;
            } else {
                // 从底往上数,空位位于偶数行
                return getInversions(data) % 2 == 1;
            }
        }
    }

    /**
     * 计算倒置和算法:

     假设是3x3的序列A：A0, A1, … A7，
     其中有一个空白X;
     倒置和算法是这样的：
     A0的倒置和T0=A1, A2, …A7中，比A0小的数的个数，不包括X
     A1的倒置和T1=A2, A3, …A7中，比A1小的数的个数，不包括X
     A2的倒置和T2=A3, A4, …A7中，比A2小的数的个数，不包括X
     ...
     A7的倒置和T7=0
     X的位置的倒置也不算。
     序列A的倒置和SumA=T0+T1+…+T7

     *
     * @param data 拼图数组数据
     * @return 该序列的倒置和
     */
    public static int getInversions(List<Integer> data) {
        int inversions = 0;
        int inversionCount = 0;
        for (int i = 0; i < data.size(); i++) {
            for (int j = i + 1; j < data.size(); j++) {
                int index = data.get(i);
                if (data.get(j) != 0 && data.get(j) < index) {
                    inversionCount++;
                }
            }
            inversions += inversionCount;
            inversionCount = 0;
        }
        return inversions;
    }
}
