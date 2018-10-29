/**    
 * 文件名：ImageService.java    
 *    
 * 版本信息：    
 * 日期：2017-11-20    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.service.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Resource;
import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mas.common.file.util.FileUtil;
import com.mas.common.util.DateTimeUtil;
import com.mas.common.util.image.MyImageUtil;
import com.mas.common.util.qrcode.LogoConfig;
import com.mas.common.util.qrcode.ZXingCodeUtil;
import com.mas.common.util.qrcode.ZXingConfig;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.util.FileTypeUtil;
import com.mas.web.util.ServletUtil;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-11-20  
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class ImageService {
    
    public static final String USER_QRCODE_PATH = "upload/user/qrcode/";
    public static final String QRCODE_PATH = "upload/address/qrcode/";
    public static final String MSG_RECROD_PATH = "upload/msg/record/";
    public static final String SHOPS_APPLY_PATH = "upload/shops/certifate/";
    
    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;
    
    public String uploadGroupAvatar(final List<String> images) {
        String fileName = UUID.randomUUID().toString() + ".png";
        final StringBuffer outPath = new StringBuffer("upload/group");
        FileUtil.mkdir(ServletUtil.realPath() + outPath.toString());
        outPath.append("/").append(fileName);
        
        try {
            taskExecutor.execute(new Runnable() {
                public void run() {
                    try {
                        MyImageUtil.getCombinationOfhead(images, ServletUtil.realPath() + outPath.toString(), ServletUtil.realPath() + JsonCtr.LOGO);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outPath.toString();
       
    }

    /**
     * 更新群组图像
     * @param images
     * @param avatar 原图像
     * @return
     */
    public String updateGroupAvatar(final List<String> images,final String groupId,final String avatar) {
        String fileName = FileTypeUtil.getUUID() + ".png";
        final StringBuffer outPath = new StringBuffer("upload/group");
        FileUtil.mkdir(ServletUtil.realPath() + outPath.toString());
        outPath.append("/").append(fileName);
        
        try {
            taskExecutor.execute(new Runnable() {
                public void run() {
                    try {
                        FileUtil.remove(avatar);
                        MyImageUtil.getCombinationOfhead(images, ServletUtil.realPath() + outPath.toString(), ServletUtil.realPath() + JsonCtr.LOGO);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outPath.toString();
    }

    /**
     * 生成我的二维码
     * @param userName
     * @param jsonString
     * @param logo
     * @return
     */
    public String uploadMineQrcode(String userName,final String jsonString,final String logo) {
        String fileName =  FileTypeUtil.getUUID() + ".png";
        final StringBuffer outPath = new StringBuffer(USER_QRCODE_PATH);
        FileUtil.mkdir(ServletUtil.realPath() + outPath.toString());
        outPath.append(fileName);
        
        try {
            taskExecutor.execute(new Runnable() {
                public void run() {
                    try {
                        // 生成二维码
                        File file = new File(ServletUtil.realPath() + outPath.toString());
                        ZXingCodeUtil zp = new ZXingCodeUtil(); // 实例化二维码工具
                        ZXingConfig zxingconfig = new ZXingConfig();    // 实例化二维码配置参数
                        zxingconfig.setHints(zp.getDecodeHintType());   // 设置二维码的格式参数
                        zxingconfig.setContent(jsonString);// 设置二维码生成内容
                        zxingconfig.setLogoPath(ServletUtil.realPath() + logo); // 设置Logo图片
                        zxingconfig.setLogoConfig(new LogoConfig());    // Logo图片参数设置   
                        zxingconfig.setLogoFlg(true);   // 设置生成Logo图片
                        BufferedImage bim = zp.getQR_CODEBufferedImage(zxingconfig);// 生成二维码
                        ImageIO.write(bim, "png", file);    // 图片写出
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outPath.toString();
    }

    /**
     * 生成收款二维码
     * @param username
     * @param content
     * @param avatar
     * @return
     */
    public String uploadReceiveQrCode(final String memberId,final String content,final String avatar) {
        String fileName = "receive.png";
        final StringBuffer outPath = new StringBuffer("upload/" + memberId + "/qrcode");
        FileUtil.mkdir(ServletUtil.realPath() + outPath.toString());
        outPath.append("/").append(fileName);
        
        try {
            taskExecutor.execute(new Runnable() {
                public void run() {
                    try {
                        // 生成二维码
                        File file = new File(ServletUtil.realPath() + outPath.toString());
                        ZXingCodeUtil zp = new ZXingCodeUtil(); // 实例化二维码工具
                        ZXingConfig zxingconfig = new ZXingConfig();    // 实例化二维码配置参数
                        zxingconfig.setHints(zp.getDecodeHintType());   // 设置二维码的格式参数
                        zxingconfig.setContent(content);// 设置二维码生成内容
                        zxingconfig.setLogoPath(ServletUtil.realPath() + avatar); // 设置Logo图片
                        zxingconfig.setLogoConfig(new LogoConfig());    // Logo图片参数设置   
                        zxingconfig.setLogoFlg(true);   // 设置生成Logo图片
                        BufferedImage bim = zp.getQR_CODEBufferedImage(zxingconfig);// 生成二维码
                        ImageIO.write(bim, "png", file);    // 图片写出
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outPath.toString();
    }
    
    /**６
     * 生成收款二维码带数量(定时删除)
     * @param username
     * @param content
     * @param avatar
     * @return
     */
    public String uploadReceiveQrCodeByNum(final String content,final String avatar) {
        String fileName = FileTypeUtil.getUUID() + new Random().nextInt(1000) + ".png";
        final StringBuffer outPath = new StringBuffer(QRCODE_PATH);
        String folder = DateTimeUtil.format(DateTimeUtil.YYYYMMDD, new Date());
        outPath.append(folder);
        FileUtil.mkdir(ServletUtil.realPath() + outPath.toString());
        outPath.append("/").append(fileName);
        
        try {
            taskExecutor.execute(new Runnable() {
                public void run() {
                    try {
                        // 生成二维码
                        File file = new File(ServletUtil.realPath() + outPath.toString());
                        ZXingCodeUtil zp = new ZXingCodeUtil(); // 实例化二维码工具
                        ZXingConfig zxingconfig = new ZXingConfig();    // 实例化二维码配置参数
                        zxingconfig.setHints(zp.getDecodeHintType());   // 设置二维码的格式参数
                        zxingconfig.setContent(content);// 设置二维码生成内容
                        zxingconfig.setLogoPath(ServletUtil.realPath() + avatar); // 设置Logo图片
                        zxingconfig.setLogoConfig(new LogoConfig());    // Logo图片参数设置   
                        zxingconfig.setLogoFlg(true);   // 设置生成Logo图片
                        BufferedImage bim = zp.getQR_CODEBufferedImage(zxingconfig);// 生成二维码
                        ImageIO.write(bim, "png", file);    // 图片写出
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outPath.toString();
    }
    
    /**
     * 保存图片
     * @param ip
     * @param outFile
     * @param format
     * @throws IOException
     */
    public void saveImage(InputStream ip,File outFile,float quality,String format) throws IOException{
        Thumbnails.of(ip)
        .scale(1f).outputQuality(quality)
        .outputFormat(format)
        .toFile(outFile);
    }
}
