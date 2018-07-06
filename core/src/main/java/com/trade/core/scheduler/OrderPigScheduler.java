package com.trade.core.scheduler;

import com.alibaba.fastjson.JSON;
import com.trade.core.Enum.OrderStateEnum;
import com.trade.core.entity.OrderLogEntity;
import com.trade.core.entity.OrderPigEntity;
import com.trade.core.mapper.IOrderLogMapper;
import com.trade.core.mapper.IOrderPigMapper;
import com.trade.core.util.SnowflakeIdUtil;
import constants.SqlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import util.LogUtil;

import java.util.List;

/**
 * 【任务调度】--订单
 *
 * @author lx
 * @since 2018-6-27 15:32:36
 */
@Component
@EnableScheduling
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class OrderPigScheduler {
    private static final String FN = "【任务调度】--订单";
    private static final int OVER_TIME = 1440;//订单超时取消时间（单位：分钟）
    @Autowired
    private IOrderPigMapper mapper;
    @Autowired
    private SnowflakeIdUtil snowflakeIdUtil;
    @Autowired
    private IOrderLogMapper orderLogMapper;

//    @Scheduled(fixedRate = 5000)//每隔5秒执行一次
//    @Scheduled(cron = "0/10 * * * * ?") //每10秒执行一次
//    @Scheduled(cron = "0 0/1 * * * ?") // 每分钟执行一次
//    @Scheduled(cron = "0 0 0/1 * * ? ") // 每一小时执行一次

    /**
     * 【任务调度】--订单--取消订单
     * 业务逻辑：订单状态==0等待买家支付 && 下单>1天（1440分钟）
     * 执行间隔：每1（分钟）执行一次
     */
//    @Scheduled(cron = "0/5 * * * * ?")//5秒
//    @Scheduled(cron = "0 0/1 * * * ?")//一分钟
    @Scheduled(cron = "0 0 0/1 * * ?")//一小时
    public void cancelOrder() {
        try {
            //1、查询待取消订单
            String where = String.format(" WHERE order_state = %s AND (date_sub(now(),interval %s minute) > create_time) ", OrderStateEnum.等待买家付款.getCode(), OVER_TIME);
            List<OrderPigEntity> list = mapper.getBySQL(" id ", where + SqlConfig.DEFAULT_QUERY_LIMIT);
            //2、批量取消订单
            int res = mapper.updateBySQL(String.format(" SET order_state = %s %s", OrderStateEnum.订单取消.getCode(), where));//2、执行取消订单
            //LogUtil.info(String.format(FN + "--取消订单，数量：%s 个，订单号：%s ", res, JSON.toJSONString(list)));//记录文件日志
            //3、记录订单日志
            list.forEach(o -> orderLogMapper.add(new OrderLogEntity(snowflakeIdUtil.nextId(), o.getId(), "取消订单")));
        } catch (Exception e) {
            LogUtil.error(FN + "-取消订单", e);
        }
    }

}
