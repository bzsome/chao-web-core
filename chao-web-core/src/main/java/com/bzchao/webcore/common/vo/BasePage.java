package com.bzchao.webcore.common.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
@ApiModel(value = "分页查询对象", description = "分页查询对象")
public class BasePage<T> {
    @ApiModelProperty(value = "当前页码")
    private Integer pageNum;

    @ApiModelProperty(value = "页码大小")
    private Integer pageSize;

    @ApiModelProperty(value = "倒序字段")
    private List<String> descs;

    @ApiModelProperty(value = "升序字段")
    private List<String> ascs;

    public IPage<T> toIPage() {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;

        Page<T> objectPage = new Page<>(pageNum, pageSize);
        List<OrderItem> orderItems = new ArrayList<>();
        if (!CollectionUtils.isEmpty(descs)) {
            List<OrderItem> descItems = descs.stream().map(OrderItem::desc).collect(Collectors.toList());
            orderItems.addAll(descItems);
        }
        if (!CollectionUtils.isEmpty(ascs)) {
            List<OrderItem> ascItems = ascs.stream().map(OrderItem::asc).collect(Collectors.toList());
            orderItems.addAll(ascItems);
        }
        objectPage.setOrders(orderItems);
        return objectPage;
    }

}
