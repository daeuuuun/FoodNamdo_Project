package org.zerock.foodnamdo.customDTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class OCRResponseDTO {

    public static class StoreInfo {
        private String name;
        private String bizNum;
        private String tel;
        private String address;

        // Getters and Setters
    }

    public static class Item {
        private String name;
        private String count;
        private String price;

        // Getters and Setters
    }

    public static class SubTotal {
        private String taxPrice;
        private String discountPrice;

        // Getters and Setters
    }

    private StoreInfo storeInfo;
    private List<Item> items;
    private SubTotal subTotal;

    // Getters and Setters
}



