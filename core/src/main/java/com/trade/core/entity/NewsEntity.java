package com.trade.core.entity;
import com.alibaba.fastjson.annotation.JSONField;
import com.trade.core.Enum.NewsStateEnum;
import com.trade.core.Enum.NewsTypeEnum;
import com.trade.core.Enum.SysStateEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

import java.util.Date;

/**
 * 消息表(news)
 * 
 * @author lx
 * @since 2018-07-05 15:00:42
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "news")
public class NewsEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 8947837052668019229L;

    /** 消息id */
    @Id
	@GeneratedValue
    @Column(name = "id", unique = true, nullable = false, length = 18)
	@JSONField(name = "id", ordinal = 10)
    private String id;

    /** 状态[0=未激活，1=正常（默认），2=关闭，3=冻结，4=禁用，5=异常，6=失效，7=已删除] */
    @Column(name = "sys_state", length = 10)
	@JSONField(name = "sys_state", ordinal = 20)
    private Integer sysState;

    /** 消息类型[0=系统消息（默认)] */
    @Column(name = "news_type", length = 10)
	@JSONField(name = "news_type", ordinal = 30)
    private Integer newsType;

    /** 接收人id */
    @Column(name = "accepter_account_id", length = 18)
	@JSONField(name = "accepter_account_id", ordinal = 40)
    private String accepterAccountId;

    /** 消息状态[0=未读，1=已读，2=清除] */
    @Column(name = "news_state", length = 10)
	@JSONField(name = "news_state", ordinal = 50)
    private Integer newsState;

    /** 标题 */
    @Column(name = "title", length = 100)
	@JSONField(name = "title", ordinal = 60)
    private String title;

    /** 内容（支持富文本） */
    @Column(name = "content", length = 65535)
	@JSONField(name = "content", ordinal = 70)
    private String content;

    /** 跳转关键字 */
    @Column(name = "jump_word", length = 20)
	@JSONField(name = "jump_word", ordinal = 80)
    private String jumpWord;

    /** 跳转链接 */
    @Column(name = "jump_link", length = 100)
	@JSONField(name = "jump_link", ordinal = 90)
    private String jumpLink;

    /** 创建时间 */
    @Column(name = "create_time", length = 26)
	@JSONField(name = "create_time", format = "yyyy-MM-dd HH:mm:ss", ordinal = 100)
    private Date createTime;

    public NewsEntity(String id, String accepterAccountId, String title, String content, String jumpWord, String jumpLink) {
        this.sysState = SysStateEnum.正常.getCode();
        this.newsType = NewsTypeEnum.系统消息.getCode();
        this.newsState = NewsStateEnum.未读.getCode();
        this.id = id;
        this.accepterAccountId = accepterAccountId;//接收人id
        this.title = title;
        this.content = content;
        this.jumpWord = jumpWord;
        this.jumpLink = jumpLink;
        this.createTime = new Date();
    }
}