package ynu.jackielinn.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ynu.jackielinn.auth.entity.Account;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
}
