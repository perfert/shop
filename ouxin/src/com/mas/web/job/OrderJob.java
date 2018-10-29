package com.mas.web.job;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.mas.common.file.util.PathUtil;
import com.mas.common.util.DateTimeUtil;
import com.mas.user.domain.entity.rp.RedPacket;
import com.mas.user.service.image.ImageService;
import com.mas.user.service.redpacket.RedPacketService;
import com.mas.user.service.wallet.WealthService;

/**
 * Job - 订单
 */
@Component("orderJob")
@Lazy(false)
public class OrderJob {

	@Autowired private RedPacketService redPacketService;
    @Autowired private WealthService wealthService;
    
    private static ConcurrentMap<String, RedPacket> concurrentMapWordCounts = new ConcurrentHashMap<String, RedPacket>();
	
	public static void main(String[] args) {
        BigDecimal a = new BigDecimal(5);
        BigDecimal b = new BigDecimal(4);
        System.out.println(a.compareTo(b) > 0);
        
        List<RedPacket> packList = new ArrayList<RedPacket>();
        for (int i = 0; i < 50; i++) {
            RedPacket rp = new RedPacket();
            packList.add(rp);
        }
                
        for (int i = 0; i < 50; i++) {
            if(i % 2 == 0){
                RedPacket rp = packList.get(i);
                packList.remove(rp);
            }
        }
    }
	
	public static void increase(RedPacket rp) {  
	    if(null != rp && null != rp.getId())
	        concurrentMapWordCounts.put(rp.getId().toString(), rp);
    }  
	
	//每30分钟执行一次
	@Scheduled(cron = "0 0/30 * * * ?")//0 0/30 * * * ?1445   
	public void releaseStock() {
        Date date = new Date();
        for(String rpId : concurrentMapWordCounts.keySet()){
            RedPacket rp = concurrentMapWordCounts.get(rpId);
            if(null != rp && DateTimeUtil.addMinutes(rp.getCreateDate(), 1445).before(date)){
                try {
                    RedPacket real = redPacketService.get(rp.getId());
                    if(real.getNum() != real.getReceiveNum() && real.getCash().compareTo(real.getReceiveCash()) > 0 && real.getHasBack() != 1){
                        BigDecimal backCash = real.getCash().subtract(real.getReceiveCash());
                        wealthService.rpBack(real.getId().toString(),real.getMemberId().toString(),real.getType().toString(),backCash);
                    }
                    concurrentMapWordCounts.remove(rpId);
                } catch (Exception e) {
                    System.out.println("===红包退款失败==" + rp.getId());
                }
            }
        }
	}

	//每天凌晨1点执行一次,删除三天前的二维码
	@Scheduled(cron = "0 0 1 * * ?")
	public void deleteQrcode() {
        final StringBuffer qrcodePath = new StringBuffer(ImageService.QRCODE_PATH);
        final StringBuffer recordPath = new StringBuffer(ImageService.MSG_RECROD_PATH);
        
        Date before = DateTimeUtil.addDays(new Date(), -3);
        String folder = DateTimeUtil.format(DateTimeUtil.YYYYMMDD,before);
        qrcodePath.append(folder);
        
        Date beforeRecord = DateTimeUtil.addDays(new Date(), -15);
        String folderRecord = DateTimeUtil.format(DateTimeUtil.YYYYMMDD,beforeRecord);
        recordPath.append(folderRecord);
        try {
            String rootPath = PathUtil.getTomcatWebRootPath();
            ReadFile.deletefile(rootPath + qrcodePath.toString());
            ReadFile.deletefile(rootPath + recordPath.toString());
        } catch (Exception e) {
            System.out.println("===deleteQrcode===删除二维码失败==" + folder);
        }
    }
}