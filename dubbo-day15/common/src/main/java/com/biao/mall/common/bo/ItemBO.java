package com.biao.mall.common.bo;

import lombok.Data;
import lombok.ToString;

/**
 * @Classname itemBO
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-15 20:01
 * @Version 1.0
 **/
@Data
@ToString
public class ItemBO {
        private String itemId;
        private String name;
        private Integer quantity;

        public String getItemId() {
                return itemId;
        }

        public void setItemId(String itemId) {
                this.itemId = itemId;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public Integer getQuantity() {
                return quantity;
        }

        public void setQuantity(Integer quantity) {
                this.quantity = quantity;
        }
}
