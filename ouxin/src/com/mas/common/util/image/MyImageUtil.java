/**    
 * 文件名：MyImageUtil.java    
 *    
 * 版本信息：    
 * 日期：2017-11-20    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.common.util.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

import com.mas.web.util.ServletUtil;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-11-20  
 */
public class MyImageUtil {
    
    private static final  int maxWidth = 200; // 这是画板的宽高
    private static final  int maxHeight = 200; // 这是画板的高度
    
    /**
     * 生成组合头像
     * 
     * @param paths 用户图像
     * @param uploadPath   路径
     * @param defaultImage 本地图片
     * @return
     * @throws IOException
     */
    public static boolean getCombinationOfhead(List<String> paths, String uploadPath, String defaultImage) throws IOException {
        int mGap = 2; //宫格间距
        //初始化图片信息
        int[] gridParam = calculateGridParam(paths.size());
        int mRowCount = gridParam[0]; //行数
        int mColumnCount = gridParam[1];//列数
        int targetImageSize = (maxWidth - (mColumnCount + 1) * mGap) / (mColumnCount == 1 ? 2 : mColumnCount);//图片尺寸
        
        
        List<BufferedImage> bufferedImages = new ArrayList<BufferedImage>();
        // 压缩图片所有的图片生成尺寸
        for (int i = 0; i < paths.size(); i++) {
            String path = paths.get(i);
            if(StringUtils.isEmpty(path))
                path = defaultImage;
            BufferedImage resize2 = resize2(path, targetImageSize, targetImageSize, true);
            if(null == resize2){
                resize2 = ImageIO.read(new File(defaultImage));
            }
            bufferedImages.add(resize2);
        }

        // BufferedImage.TYPE_INT_RGB可以自己定义可查看API
        BufferedImage outImage = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_RGB);

        // 生成画布
        Graphics g = outImage.getGraphics();
        Graphics2D canvas = (Graphics2D) g;
        // 设置背景色
        canvas.setBackground(new Color(231, 231, 231));

        // 通过使用当前绘图表面的背景色进行填充来清除指定的矩形。
        canvas.clearRect(0, 0, maxWidth, maxHeight);

        // 开始拼凑 根据图片的数量判断该生成那种样式的组合头像目前为4中
        int size = paths.size();
        int t_center = (maxHeight + mGap) / 2;//中间位置以下的顶点（有宫格间距）
        int b_center = (maxHeight - mGap) / 2;//中间位置以上的底部（有宫格间距）
        int l_center = (maxWidth + mGap) / 2;//中间位置以右的左部（有宫格间距）
        int r_center = (maxWidth - mGap) / 2;//中间位置以左的右部（有宫格间距）
        int center = (maxHeight - targetImageSize) / 2;//中间位置以上顶部（无宫格间距）
        for (int i = 0; i < size; i++) {
            int rowNum = i / mColumnCount;//当前行数
            int columnNum = i % mColumnCount;//当前列数

            int left = ((int) (targetImageSize * (mColumnCount == 1 ? columnNum + 0.5 : columnNum) + mGap * (columnNum + 1)));
            int top = ((int) (targetImageSize * (mColumnCount == 1 ? rowNum + 0.5 : rowNum) + mGap * (rowNum + 1)));
            int right = left + targetImageSize;
            int bottom = top + targetImageSize;

            BufferedImage bitmap = bufferedImages.get(i);
            if (size == 1) {
                drawBitmapAtPosition(canvas, left, top, targetImageSize, targetImageSize, bitmap);
            } else if (size == 2) {
                drawBitmapAtPosition(canvas, left, center, targetImageSize, targetImageSize, bitmap);
            } else if (size == 3) {
                if (i == 0) {
                    drawBitmapAtPosition(canvas, center, top, targetImageSize, targetImageSize, bitmap);
                } else {
                    drawBitmapAtPosition(canvas, mGap * i + targetImageSize * (i - 1), t_center, targetImageSize, targetImageSize, bitmap);
                }
            } else if (size == 4) {
                drawBitmapAtPosition(canvas, left, top, targetImageSize, targetImageSize, bitmap);
            } else if (size == 5) {
                if (i == 0) {
                    drawBitmapAtPosition(canvas, r_center - targetImageSize, r_center - targetImageSize, targetImageSize, targetImageSize, bitmap);
                } else if (i == 1) {
                    drawBitmapAtPosition(canvas, l_center, r_center - targetImageSize,  targetImageSize, targetImageSize, bitmap);
                } else {
                    drawBitmapAtPosition(canvas, mGap * (i - 1) + targetImageSize * (i - 2), t_center, targetImageSize, 
                            targetImageSize, bitmap);
                }
            } else if (size == 6) {
                if (i < 3) {
                    drawBitmapAtPosition(canvas, mGap * (i + 1) + targetImageSize * i, b_center - targetImageSize, targetImageSize, targetImageSize, bitmap);
                } else {
                    drawBitmapAtPosition(canvas, mGap * (i - 2) + targetImageSize * (i - 3), t_center,targetImageSize, 
                            targetImageSize, bitmap);
                }
            } else if (size == 7) {
                if (i == 0) {
                    drawBitmapAtPosition(canvas, center, mGap, targetImageSize, targetImageSize, bitmap);
                } else if (i > 0 && i < 4) {
                    drawBitmapAtPosition(canvas, mGap * i + targetImageSize * (i - 1), center, targetImageSize,targetImageSize, bitmap);
                } else {
                    drawBitmapAtPosition(canvas, mGap * (i - 3) + targetImageSize * (i - 4), t_center + targetImageSize / 2, targetImageSize, targetImageSize, bitmap);
                }
            } else if (size == 8) {
                if (i == 0) {
                    drawBitmapAtPosition(canvas, r_center - targetImageSize, mGap, targetImageSize, targetImageSize, bitmap);
                } else if (i == 1) {
                    drawBitmapAtPosition(canvas, l_center, mGap, targetImageSize, targetImageSize, bitmap);
                } else if (i > 1 && i < 5) {
                    drawBitmapAtPosition(canvas, mGap * (i - 1) + targetImageSize * (i - 2), center, targetImageSize, targetImageSize, bitmap);
                } else {
                    drawBitmapAtPosition(canvas, mGap * (i - 4) + targetImageSize * (i - 5), t_center + targetImageSize / 2, targetImageSize, targetImageSize, bitmap);
                }
            } else if (size == 9) {
                drawBitmapAtPosition(canvas, left, top, targetImageSize, targetImageSize, bitmap);
            }
        }
       

        try {  
            //输出到文件流  
            /*FileOutputStream newimage = new FileOutputStream(uploadPath);  
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);  
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(outImage);  
            // 压缩质量   
            jep.setQuality(0.9f, true);  
            encoder.encode(outImage, jep);  
            // 近JPEG编码  
            newimage.close();  */
            return true;  
        } catch (Exception e) {  
            return false;  
        }  
    }

    /**
     * 根据坐标画图
     *
     * @param canvas
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param bitmap
     */
    public static void drawBitmapAtPosition(Graphics2D g2d, int left, int top, int right, int bottom, BufferedImage bitmap) {
        if(null == bitmap){
          //图片为空用默认图片
            
        }
        g2d.drawImage(bitmap,left, top, right, bottom,null);
    }
    
    public static void main(String[] args) throws IOException {
        ArrayList<String> picUrls = new ArrayList<String>();
        // 1. 根据用户ID 获取用户头像(集合)
        // 获取群主头像
        picUrls.add("http://www.zhlzw.com/UploadFiles/Article_UploadFiles/201204/20120412123914329.jpg");
        picUrls.add("http://pic.58pic.com/58pic/15/14/14/18e58PICMwt_1024.jpg");
        picUrls.add("http://dynamic-image.yesky.com/740x-/uploadImages/2014/289/01/IGS09651F94M.jpg");
        picUrls.add("http://pic.58pic.com/58pic/13/61/00/61a58PICtPr_1024.jpg");
        picUrls.add("http://pic.58pic.com/58pic/15/35/96/9.jpg");
        picUrls.add("http://pic.58pic.com/58pic/13/61/00/61a58PICtPr_1024.jpg");
        picUrls.add("http://pic.58pic.com/58pic/15/35/96/97j58PICUhD_1024.jpg");
        // 当静态资源与应用分离时,可以获取网络资源进行图片合成
        // boolean flag=ImageUtil.getCombinationOfhead(picUrls,dir,groupId);
        // 当静态资源和应用程序在同一服务器上时,直接从本地磁盘读取文件
        /*for (int i = 0; i < picUrls.size(); i++) {
            String url = picUrls.get(i); // 获取当前头像
            picUrls.set(i, dir + url.replace(urlImg, ""));// 设置为网络路径头像
        }*/
        // 2 调用工具类 生成九宫格 并保存在已有路径
       // boolean flag = new MyImageUtil().getCombinationOfhead(picUrls, "D://image", "2ff22");
      
    }
    
    /**
     * 图片缩放
     * 
     * @param filePath
     *            图片路径
     * @param height
     *            高度
     * @param width
     *            宽度
     * @param bb
     *            比例不对时是否需要补白
     */
    public static BufferedImage resize2(String filePath, int height, int width, boolean bb) {
        try {
            double ratio = 0; // 缩放比例
            // System.out.println("图片缩放"+filePath);
            BufferedImage bi = null;
            if (filePath.indexOf("http://") == 0) {
                bi = ImageIO.read(new URL(filePath));
            } else {
                bi = ImageIO.read(new File(ServletUtil.realPath() + filePath));
            }
            
            Image itemp = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (new Integer(height)).doubleValue() / bi.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            }
            if (bb) {
                // copyimg(filePath, "D:\\img");
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null),
                            itemp.getHeight(null), Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null),
                            itemp.getHeight(null), Color.white, null);
                g.dispose();
                itemp = image;
            }
            return (BufferedImage) itemp;
        } catch (IOException e) {
            System.out.println("=======MyImageUtil=======");
            //e.printStackTrace();
        }
        return null;
    }
    
    
    /**
     * 设置宫格参数
     *
     * @param imagesSize 图片数量
     * @return 宫格参数 gridParam[0] 宫格行数 gridParam[1] 宫格列数
     */
    protected static int[] calculateGridParam(int imagesSize) {
        int[] gridParam = new int[2];
        if (imagesSize < 3) {
            gridParam[0] = 1;
            gridParam[1] = imagesSize;
        } else if (imagesSize <= 4) {
            gridParam[0] = 2;
            gridParam[1] = 2;
        } else {
            gridParam[0] = imagesSize / 3 + (imagesSize % 3 == 0 ? 0 : 1);
            gridParam[1] = 3;
        }
        return gridParam;
    }

    
}

/*int j = 1;
for (int i = 1; i <= bufferedImages.size(); i++) {
    if (bufferedImages.size() == 9) {
        if (i <= 3) {
            g2d.drawImage(bufferedImages.get(i - 1), 30 * i + 3 * i - 31, 0, null);
        } else if (i <= 6) {
            g2d.drawImage(bufferedImages.get(i - 1), 27 * j + 5 * j - 30, 36, null);
            j++;
        } else {
            g2d.drawImage(bufferedImages.get(i - 1), 27 * j + 5 * j - 125, 77, null);
            j++;
        }
    } else if (bufferedImages.size() == 8) {
        if (i <= 3) {
            g2d.drawImage(bufferedImages.get(i - 1), 30 * i + 3 * i - 31, 0, null);
        } else if (i <= 6) {
            g2d.drawImage(bufferedImages.get(i - 1), 27 * j + 5 * j - 30, 36, null);
            j++;
        } else {
            g2d.drawImage(bufferedImages.get(i - 1), 21 * j + 9 * j - 100, 77, null);
            j++;
        }

    }

    else if (bufferedImages.size() == 7) {
        if (i <= 3) {
            g2d.drawImage(bufferedImages.get(i - 1), 30 * i + 3 * i - 31, 0, null);
        } else if (i <= 6) {
            g2d.drawImage(bufferedImages.get(i - 1), 27 * j + 5 * j - 30, 36, null);
            j++;
        } else {
            g2d.drawImage(bufferedImages.get(i - 1), 3, 75, null);
        }

    }

    else if (bufferedImages.size() == 6) {
        if (i <= 3) {
            g2d.drawImage(bufferedImages.get(i - 1), 30 * i + 3 * i - 31, 10, null);
        } else {
            g2d.drawImage(bufferedImages.get(i - 1), 31 * j + 4 * j - 33, 60, null);
            j++;
        }
    } else if (bufferedImages.size() == 5) {
        if (i <= 3) {
            g2d.drawImage(bufferedImages.get(i - 1), 30 * i + 3 * i - 28, 10, null);
        } else {
            g2d.drawImage(bufferedImages.get(i - 1), 48 * j + 8 * j - 49, 60, null);
            j++;
        }

    } else if (bufferedImages.size() == 4) {
        if (i <= 2) {
            g2d.drawImage(bufferedImages.get(i - 1), 50 * i + 4 * i - 50, 4, null);
        } else {
            g2d.drawImage(bufferedImages.get(i - 1), 50 * j + 4 * j - 50, 58, null);
            j++;
        }
    } else if (bufferedImages.size() == 3) {
        if (i <= 1) {

            g2d.drawImage(bufferedImages.get(i - 1), 31, 4, null);

        } else {

            g2d.drawImage(bufferedImages.get(i - 1), 50 * j + 4 * j - 50, 58, null);

            j++;
        }

    } else if (bufferedImages.size() == 2) {

        g2d.drawImage(bufferedImages.get(i - 1), 50 * i + 4 * i - 50, 31, null);

    } else if (bufferedImages.size() == 1) {

        g2d.drawImage(bufferedImages.get(i - 1), 31, 31, null);

    }

    // 需要改变颜色的话在这里绘上颜色。可能会用到AlphaComposite类
}
*/