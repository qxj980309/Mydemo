package com.example.demo.mock.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.demo.mock.common.Handler.FieldParseListTypeHandler;
import com.example.demo.mock.common.Handler.PretreatmentPOTypeHandler;
import com.example.demo.mock.common.Handler.SignaturePOTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@Setter
@ApiModel
public class InterfaceOperationPO {
    @ApiModelProperty(value ="逻辑主键")
    private Long id;

    @ApiModelProperty(value ="接口id")
    @NotNull(message ="接口不能为空")
    private Long interfaceId;

    @ApiModelProperty(value ="请求报文预处理")
    @Valid
    @TableField(value = "pretreatment",typeHandler = PretreatmentPOTypeHandler.class)
    private PretreatmentPO pretreatment;

    @ApiModelProperty(value ="请求报文字段解析")
    @Valid
    @TableField(value = "field_parse", typeHandler = FieldParseListTypeHandler.class)
    private List<FieldParsePO> fieldParseList;

    @ApiModelProperty(value ="响应报文生成签名")
    @Valid
    @TableField(value = "signature", typeHandler = SignaturePOTypeHandler.class)
    private SignaturePO signature;


}
