package com.dcsg.oso.rlmcucumber.config;

import com.dcsg.oso.rlmcucumber.common.Utility;
import com.dcsg.oso.rlmcucumber.dto.Store;
import com.dcsg.oso.rlmcucumber.utility.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.*;


/**
 * Class used to convert DataTable to Java Object using Jackson (Json library).
 *
 * <p><b>NOTE:</b> This class assumes that you will use field names match the data table column
 * headers.
 */
@Configurable
public class CucumberTypeRegistryConfigurer {

  @Autowired Utility utility;

//  @DataTableType
//  public Store storeData(DataTable dataTable,String path) throws JsonProcessingException {
//
//    List<Map<String, String>> dataTables = dataTable.asMaps();
//    dataTables = utility.convertDataTableValues(dataTables, path);
//
//    Store store =
//            JsonUtil.convertToObject(JsonUtil.convert(dataTables.get(0)), Store.class);
//    return store;
//  }


}
