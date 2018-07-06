package com.trade.core.util;

import org.springframework.beans.BeanUtils;
import util.LogUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 类型转换工具类
 *
 * @author lx
 * @since 2018-06-09 17:45:24
 */
public class O2OUtil {
    /**
     * 实体转换
     *
     * @param source      源对象
     * @param targetClass 目标对象类型
     * @return target 目标对象
     */
    public static <S, T> T copyObject(S source, Class<T> targetClass) {
        try {
            T target = targetClass.newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            LogUtil.error("【实体转换】", e);
            return null;
        }
    }

    /**
     * 集合转换
     *
     * @param poList  po集合
     * @param voClass vo类型
     * @return List<?> vo集合
     */
    public static <S, T> List<T> copyList(List<S> poList, Class<T> voClass) {
        return poList.stream().map(p -> copyObject(p, voClass)).collect(Collectors.toList());
    }

//    public static void main(String[] args) {
//        List<AccountEntity> list_entity = new ArrayList<>();
//        AccountEntity entity = new AccountEntity();
//        entity.setSysState(SysStateEnum.正常.getCode());
//        entity.setAuthType(AuthTypeEnum.猪场.toString());
//        entity.setAuthState(AuthStateEnum.未认证.getCode());
//        entity.setSex(SexEnum.保密.getCode());
//        entity.setCardType(CardTypeEnum.身份证.getCode());
//        entity.setRoleType(RoleTypeEnum.普通权限.getCode());
//        list_entity.add(entity);
//        AccountEntity entity2 = new AccountEntity();
//        entity2.setSysState(SysStateEnum.正常.getCode());
//        entity2.setAuthType(AuthTypeEnum.贸易商.toString());
//        entity2.setAuthState(AuthStateEnum.个人认证.getCode());
//        entity2.setSex(SexEnum.女.getCode());
//        entity2.setCardType(CardTypeEnum.身份证.getCode());
//        entity2.setRoleType(RoleTypeEnum.管理员权限.getCode());
//        entity2.setAuthState(1);
//        list_entity.add(entity2);
//        O2OUtil.copyList(list_entity, AccountVO.class).forEach(vo -> System.out.println(JSON.toJSONString(vo)));
//    }

}
