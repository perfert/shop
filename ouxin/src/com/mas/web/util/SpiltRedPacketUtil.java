/**    
 * 文件名：SpiltRedPacketUtil.java    
 *    
 * 版本信息：    
 * 日期：2017-12-6    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.web.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 类名称： 创建人：yixuan
 * 
 * @version v1.00 2017-12-6
 */
public class SpiltRedPacketUtil {


    
    
    
    
    public static void main(String[] args) {
        /*
         * for (int i = 0; i < 10; i++) { List<BigDecimal> moneys =
         * mathList(BigDecimal.valueOf(1), 3); if (moneys != null) { BigDecimal
         * b = new BigDecimal(0); for (BigDecimal bigDecimal : moneys) {
         * System.out.print(bigDecimal + "元    "); b = b.add(bigDecimal); }
         * System.out.print("   总额：" + b+"元 "); System.out.println(); } }
         */

        /*
         * ConcurrentLinkedQueue<BigDecimal> queue = math(BigDecimal.valueOf(1),
         * 2); BigDecimal b = new BigDecimal(0); while (!queue.isEmpty()) {
         * BigDecimal bigDecimal = queue.poll(); b = b.add(bigDecimal);
         * System.out.print(bigDecimal + "元    "); } System.out.print("   总额：" +
         * b+"元 ");
         */

        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        BigDecimal all = BigDecimal.ZERO;
                        List<BigDecimal> allHotPacket = com.mas.web.util.RedPacketUtils.getAllHotPacket(0.1d, 10d, 3d, 1d);
                        int size = allHotPacket.size();
                        BigDecimal max = BigDecimal.ZERO;
                        int maxIndex = 0;
                        for (int i = 0; i < size; i++) {
                            BigDecimal amout = allHotPacket.get(i);
                            System.out.println("第" + (i + 1) + "随机的红包金额大小：" + amout);
                            if (amout.compareTo(max) > 0) {
                                max = amout;
                                maxIndex = i + 1;
                            }
                            all = all.add(amout);
                        }
                        System.out.println("所有红包金额为红包：" + all);
                        System.out.println("手气最佳为：第" + maxIndex + "个红包，金额为：" + max);
                    } catch (Exception ex) {
                    }
                }
            };
            thread.start();
        }
    }

    /**
     * 计算每人获得红包金额;最小每人0.01元
     * 
     * @param mmm
     *            红包总额
     * @param number
     *            人数
     * @return
     */
    public static ConcurrentLinkedQueue<BigDecimal> math(BigDecimal mmm, int number) {
        if (mmm.doubleValue() < number * 0.01) {
            return null;
        }
        Random random = new Random();
        // 金钱，按分计算 10块等于 1000分
        int money = mmm.multiply(BigDecimal.valueOf(100)).intValue();
        // 随机数总额
        double count = 0;
        // 每人获得随机点数
        double[] arrRandom = new double[number];
        // 每人获得钱数
        ConcurrentLinkedQueue<BigDecimal> queue = new ConcurrentLinkedQueue<BigDecimal>();
        // 循环人数 随机点
        for (int i = 0; i < arrRandom.length; i++) {
            int r = random.nextInt((number) * 99) + 1;
            count += r;
            arrRandom[i] = r;
        }
        // 计算每人拆红包获得金额
        int c = 0;
        for (int i = 0; i < arrRandom.length; i++) {
            // 每人获得随机数相加 计算每人占百分比
            Double x = new Double(arrRandom[i] / count);
            // 每人通过百分比获得金额
            int m = (int) Math.floor(x * money);
            // 如果获得 0 金额，则设置最小值 1分钱
            if (m == 0) {
                m = 1;
            }
            // 计算获得总额
            c += m;
            // 如果不是最后一个人则正常计算
            if (i < arrRandom.length - 1) {
                queue.add(new BigDecimal(m).divide(new BigDecimal(100)));
            } else {
                // 如果是最后一个人，则把剩余的钱数给最后一个人
                queue.add(new BigDecimal(money - c + m).divide(new BigDecimal(100)));
            }
        }
        return queue;
    }

    /**
     * 计算每人获得红包金额;最小每人0.01元
     * 
     * @param mmm
     *            红包总额
     * @param number
     *            人数
     * @return
     */
    public static List<BigDecimal> mathList(BigDecimal mmm, int number) {
        if (mmm.doubleValue() < number * 0.01) {
            return null;
        }
        Random random = new Random();
        // 金钱，按分计算 10块等于 1000分
        int money = mmm.multiply(BigDecimal.valueOf(100)).intValue();
        // 随机数总额
        double count = 0;
        // 每人获得随机点数
        double[] arrRandom = new double[number];
        // 每人获得钱数
        List<BigDecimal> arrMoney = new ArrayList<BigDecimal>(number);
        // 循环人数 随机点
        for (int i = 0; i < arrRandom.length; i++) {
            int r = random.nextInt((number) * 99) + 1;
            count += r;
            arrRandom[i] = r;
        }
        // 计算每人拆红包获得金额
        int c = 0;
        for (int i = 0; i < arrRandom.length; i++) {
            // 每人获得随机数相加 计算每人占百分比
            Double x = new Double(arrRandom[i] / count);
            // 每人通过百分比获得金额
            int m = (int) Math.floor(x * money);
            // 如果获得 0 金额，则设置最小值 1分钱
            if (m == 0) {
                m = 1;
            }
            // 计算获得总额
            c += m;
            // 如果不是最后一个人则正常计算
            if (i < arrRandom.length - 1) {
                arrMoney.add(new BigDecimal(m).divide(new BigDecimal(100)));
            } else {
                // 如果是最后一个人，则把剩余的钱数给最后一个人
                arrMoney.add(new BigDecimal(money - c + m).divide(new BigDecimal(100)));
            }
        }
        // 随机打乱每人获得金额
        Collections.shuffle(arrMoney);
        return arrMoney;
    }
}
