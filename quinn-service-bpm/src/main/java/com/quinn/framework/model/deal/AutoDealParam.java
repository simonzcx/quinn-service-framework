package com.quinn.framework.model.deal;

import com.quinn.framework.api.BpmDealParamSupplier;
import com.quinn.framework.util.enums.BpmDealTypeEnum;
import com.quinn.framework.util.enums.BpmTodoTypeEnum;
import com.quinn.util.base.model.BaseResult;
import com.quinn.util.constant.NumberConstant;
import com.quinn.util.constant.StringConstant;
import com.quinn.util.constant.enums.UrgentLevelEnum;

/**
 * 系统自动处理
 *
 * @author Qunhua.Liao
 * @since 2020-05-03
 */
public class AutoDealParam extends AbstractBpmDealParam {

    {
        setDealType(BpmDealTypeEnum.AUTO.name());
        setTodoType(BpmTodoTypeEnum.AUTO.name());
        setUrgentLevel(UrgentLevelEnum.LOW.name());
        setTaskId(NumberConstant.TOP_OF_DATA_ID);
        setNodeCode(StringConstant.TOP_OF_DATA);
        setDealUser(StringConstant.NONE_OF_DATA);
    }

    @Override
    protected BaseResult subValidate() {
        return BaseResult.SUCCESS;
    }

    /**
     * 自动处理参数提供者
     *
     * @author Qunhua.Liao
     * @since 2020-07-02
     */
    public static class AutoDealParamSupplier implements BpmDealParamSupplier<AutoDealParam> {

        @Override
        public AutoDealParam supply() {
            return new AutoDealParam();
        }

        @Override
        public String[] getDealTypes() {
            return new String[] {BpmDealTypeEnum.AUTO.name()};
        }
    }

}
