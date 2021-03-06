package com.quinn.framework.controller;

import com.quinn.framework.component.KeyValueMultiService;
import com.quinn.util.base.model.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 通用主数据对外开放接口
 *
 * @author Qunhua.Liao
 * @since 2020-03-31
 */
@RestController
@RequestMapping("/core/key-value/*")
@Api(tags = {"0ZY010数据：通用主数据"})
public class KeyValueController extends AbstractController {

    @PostMapping(value = {"list", "list-basic"})
    @ApiOperation("综合获取通用主数据列表")
    public BaseResult list(
            @ApiParam(name = "condition", value = "Json格式条件", required = true)
            @RequestBody Map<String, Object> condition
    ) {
        return KeyValueMultiService.selectByMap(condition);
    }

    @PostMapping(value = {"page", "page-basic"})
    @ApiOperation("分页获取通用主数据列表")
    public BaseResult page(
            @ApiParam(name = "condition", value = "Json格式条件", required = true)
            @RequestBody Map<String, Object> condition
    ) {
        return KeyValueMultiService.pageByMap(condition);
    }

    @GetMapping(value = {"show", "show-basic"})
    @ApiOperation("显示：支持多个多个")
    public BaseResult show(
            @ApiParam(name = "dataType", value = "数据类型", required = true)
            @RequestParam(name = "dataType") String dataType,

            @ApiParam(name = "dataKeys", value = "数据编码", required = true)
            @RequestParam(name = "dataKeys") String dataKeys
    ) {
        return KeyValueMultiService.show(dataType, dataKeys);
    }

}
