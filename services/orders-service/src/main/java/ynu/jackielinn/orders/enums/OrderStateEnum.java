package ynu.jackielinn.orders.enums;

public enum OrderStateEnum {
    UNPAID(0, "未支付"),
    PAID(1, "已支付/待接单"),
    ACCEPTED(2, "已接单"),
    FINISHED(3, "已完成"),
    CANCELED(4, "已取消");

    private final int code;
    private final String desc;

    OrderStateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() { return code; }
    public String getDesc() { return desc; }

    public static OrderStateEnum fromCode(int code) {
        for (OrderStateEnum state : values()) {
            if (state.code == code) return state;
        }
        return null;
    }
} 