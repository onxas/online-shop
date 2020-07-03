package com.project.shop.model.dto.order;

import com.project.shop.model.entity.Order;
import com.project.shop.model.entity.OrderedItem;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO для получения заказа
 *
 * @author Алексей Климов
 */
@Builder
@Data
public class GetOrderDTO {

    private Long id;

    private Long orderDateAndTime;

    private List<GetOrderedItemDTO> orderedItems;

    /**
     * Вовзращает DTO из сущности
     *
     * @param order
     * @return
     */
    public static GetOrderDTO createFromOrder(Order order) {
        return GetOrderDTO.builder()
                .id(order.getId())
                .orderDateAndTime(order.getOrderDateAndTime().getTime())
                .orderedItems(order.getOrderedItems().stream()
                        .map(GetOrderedItemDTO::createFromOrderedItem).collect(Collectors.toList()))
                .build();
    }

    public static GetOrderDTO createFromOrder(Order order, Pageable pageable) {
        PagedListHolder<OrderedItem> page = new PagedListHolder<>(order.getOrderedItems());
        page.setPage(pageable.getPageNumber());
        page.setPageSize(pageable.getPageSize());
        return GetOrderDTO.builder()
                .id(order.getId())
                .orderDateAndTime(order.getOrderDateAndTime().getTime())
                .orderedItems(page.getPageList().stream()
                        .map(GetOrderedItemDTO::createFromOrderedItem)
                        .collect(Collectors.toList()))
                .build();
    }
}
