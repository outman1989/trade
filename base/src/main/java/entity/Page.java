package entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 分页实体类
 *
 * @author lx
 * @since 2018-5-31 16:59:16
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class Page<E> implements Serializable {
    @JSONField(name = "total", ordinal = 1)
    private Integer total;// 总条数
    @JSONField(name = "list", ordinal = 2)
    private List<E> list;// 数据集合
}
