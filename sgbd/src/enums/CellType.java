package enums;

import entities.cells.*;

public enum CellType {

    MEMORY_TABLE ("memory"),
    CSV_TABLE    ("csv"),
    FYI_TABLE    ("fyi"),
    JDBC_TABLE   ("jdbc"),
    OPERATION    ("operation");

    public final String id;

    CellType(String id) {
        this.id = id;
    }

    public static CellType fromTableCell(Cell tableCell) {

        return switch (tableCell){

          case FYITableCell ignored -> FYI_TABLE;

          case CSVTableCell ignored -> CSV_TABLE;

          case OperationCell ignored -> OPERATION;

          case JDBCTableCell ignored -> JDBC_TABLE;

          case MemoryTableCell ignored -> MEMORY_TABLE;

        };

    }
}
