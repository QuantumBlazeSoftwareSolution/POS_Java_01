package com.qb.app.system;

import static com.qb.app.database_crud.ProductStatusCRUD.createProductStatus;
import static com.qb.app.database_crud.ProductStatusCRUD.getProductStatus;

/**
 *
 * @author Vihanga
 */
public class PrepareDatabase {

    public static void prepareDatabaseTables() {
        // Prepare Product Status
        if (getProductStatus().isEmpty()) {
            createProductStatus();
        }
    }
}
