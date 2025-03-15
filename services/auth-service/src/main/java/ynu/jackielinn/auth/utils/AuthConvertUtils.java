package ynu.jackielinn.auth.utils;

import ynu.jackielinn.auth.dto.response.AccountVO;
import ynu.jackielinn.auth.entity.Account;

/**
 * VO 对象转换器
 */
public class AuthConvertUtils {

    /**
     * 将 Account 实体类转换为 AccountVO
     * @param account 实体类对象
     * @return 转换后的 VO 对象
     */
    public static AccountVO account2VO(Account account) {
        if (account == null) {
            return null;
        }
        return account.asViewObject(AccountVO.class, vo -> {
            vo.setUserId(account.getUserId());
            vo.setUserName(account.getUserName());
            vo.setUserSex(account.getUserSex());
            vo.setEmail(account.getEmail());
            vo.setUserImg(account.getUserImg());
        });
    }
}
