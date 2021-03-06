package com.quinn.framework.util;

/**
 * 定时任务参数名称
 *
 * @author Qunhua.Liao
 * @since 2020-05-31
 */
public interface ScheduleParamName {

    /**
     * 上次成功时间
     */
    String PARAM_KEY_LAST_SUCCESS_DATETIME = "lastSuccessDateTime";

    /**
     * 上次失败时间
     */
    String PARAM_KEY_LAST_FAIL_DATETIME = "lastFailDateTime";

    /**
     * 上次执行时间
     */
    String PARAM_KEY_LAST_EXEC_DATETIME = "lastExecDateTime";

    /**
     * 任务模型编码
     */
    String PARAM_KEY_SCHEDULE_KEY = "scheduleKey";

}
