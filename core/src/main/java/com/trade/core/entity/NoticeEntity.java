package com.trade.core.entity;
import com.alibaba.fastjson.annotation.JSONField;
import com.trade.core.Enum.NoticeTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

import java.util.Date;

/**
 * 公告表(notice)
 * 
 * @author lx
 * @since 2018-07-03 17:03:44
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "notice")
public class NoticeEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -5547350011736600306L;

    /** 公告id */
    @Id
	@GeneratedValue
    @Column(name = "id", unique = true, nullable = false, length = 18)
	@JSONField(name = "id", ordinal = 10)
    private String id;

    /** 公告类型[0=系统公告（默认），1=精彩活动] */
    @Column(name = "notice_type", length = 10)
	@JSONField(name = "notice_type", ordinal = 20)
    private Integer noticeType;

    /** 标题 */
    @Column(name = "title", length = 100)
	@JSONField(name = "title", ordinal = 30)
    private String title;

    /** 内容（支持富文本） */
    @Column(name = "content", length = 65535)
	@JSONField(name = "content", ordinal = 40)
    private String content;

    /** 图片路径 */
    @Column(name = "img_path", length = 100)
	@JSONField(name = "img_path", ordinal = 50)
    private String imgPath;

    /** 附件路径 */
    @Column(name = "file_path", length = 100)
	@JSONField(name = "file_path", ordinal = 60)
    private String filePath;

    /** 发起人id */
    @Column(name = "author_account_id", length = 18)
	@JSONField(name = "author_account_id", ordinal = 70)
    private String authorAccountId;

    /** 发起人姓名 */
    @Column(name = "author_real_name", length = 20)
	@JSONField(name = "author_real_name", ordinal = 80)
    private String authorRealName;

    /** 跳转关键字 */
    @Column(name = "jump_word", length = 20)
	@JSONField(name = "jump_word", ordinal = 90)
    private String jumpWord;

    /** 跳转链接 */
    @Column(name = "jump_link", length = 100)
	@JSONField(name = "jump_link", ordinal = 100)
    private String jumpLink;

    /** 创建时间 */
    @Column(name = "create_time", length = 26)
	@JSONField(name = "create_time", format = "yyyy-MM-dd HH:mm:ss", ordinal = 110)
    private Date createTime;

    /**
     * 初始化
     */
    public NoticeEntity init(String id,AccountEntity account) {
        this.id = id;
        this.noticeType = NoticeTypeEnum.系统公告.getCode();
        this.authorAccountId = account.getId();
        this.authorRealName = account.getRealName();
        this.createTime = new Date();
        return this;
    }
}