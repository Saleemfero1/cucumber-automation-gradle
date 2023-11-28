package com.dcsg.oso.rlmcucumber.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Store implements Serializable {
    private String storeName;
    private String fromDate;
    private String toDate;
    private String dispenseStoreNo;
}




