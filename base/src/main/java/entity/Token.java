package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Token implements Serializable{
    private int accountId;//账号Id
    private int companyId;//公司Id
    private String loginName;//账号登录名
    private Date validay;//有效期
}
