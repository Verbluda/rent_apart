package com.example.architect_module.service.impl;

import com.example.architect_module.model.ArchitectRequestDto;
import com.example.architect_module.service.ArchitectService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ArchitectServiceImpl implements ArchitectService {

    public static final String FILENAME_TEMPLATE = "V%d__%s_%s.sql";
    public static final String PATH_OF_MIGRATION_FILE = "C:\\Users\\Lucy\\IdeaProjects\\rent_apartement_app\\architect_module\\src\\main\\resources\\db\\migration\\%s";
    public static final String CREATE_SCRIPT_START = "CREATE TABLE IF NOT EXISTS %s (\n    id int8 PRIMARY KEY";
    public static final String COLUMNS = ",\n    %s %s";
    public static final String CREATE_SCRIPT_END = ");\n\nCREATE SEQUENCE %s_sequence START 1 INCREMENT 1;";
    public static final String UPDATE_SCRIPT_START = "ALTER TABLE IF EXISTS {";
    public static final String UPDATE_SCRIPT_ADD = "\nADD %s IF NOT EXISTS %s";
    public static final String UPDATE_SCRIPT_DELETE = "\nDROP %s IF EXISTS";
    public static final String UPDATE_SCRIPT_UPDATE_TYPE = "\nALTER %s %s";
    public static final String UPDATE_SCRIPT_END = "\n};";
    public static final String DELETE_SCRIPT = "DROP TABLE IF EXIST %s;\n DROP SEQUENCE IF EXIST %s_sequence";
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void createMigrationFile(ArchitectRequestDto architectRequestDto) {
        String operation = architectRequestDto.getOperation();
        System.out.println(operation);
        String name = architectRequestDto.getTableName();
        Map<String, String> newColumns = architectRequestDto.getValues();

        int version = calculateVersionOfScript();

        String filename = String.format(FILENAME_TEMPLATE, version, operation, name);
        String filePath = String.format(PATH_OF_MIGRATION_FILE, filename);

        Path path = Paths.get(filePath);
        try {
            StringBuilder script = new StringBuilder();
            switch (operation) {
                case "create":
                    writeInFileCreateScript(script, name, newColumns);
                    break;
                case "update":
                    writeInFileUpdateScript(script, name, newColumns);
                    break;
                case "delete":
                    System.out.println(operation);
                    writeInFileDeleteScript(script, name);
                    break;
                default:
                    throw new RuntimeException("неизвестная операция с базой данных");
            }

            byte[] bs = script.toString().getBytes();
            Files.write(path, bs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int calculateVersionOfScript() {
        String query = "SELECT MAX(version) FROM  flyway_schema_history";
        return jdbcTemplate.queryForObject(query, Integer.class) + 1;
    }

    private void writeInFileCreateScript(StringBuilder script, String tableName, Map<String, String> newColumns) {
        script.append(String.format(CREATE_SCRIPT_START, tableName));
        for (Map.Entry<String, String> column : newColumns.entrySet()) {
            script.append(String.format(COLUMNS, column.getKey(), column.getValue()));
        }
        script.append(String.format(CREATE_SCRIPT_END, tableName));
    }

    private void writeInFileUpdateScript(StringBuilder script, String tableName, Map<String, String> newColumns) {
        script.append(String.format(UPDATE_SCRIPT_START, tableName));

        List<String> columnsToDelete = new ArrayList<>();
        Map<String, String> columnsToUpdateType = new HashMap<>();
        Map<String, String> columnsToAdd = new HashMap<>();

        String query = "SELECT COLUMN_NAME, UDT_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '%s'";
        List<Map<String, Object>> oldColumns = jdbcTemplate.queryForList(String.format(query, "address"));
        List<String> oldColumnsNameList = new ArrayList<>();

        for (Map<String, Object> oldColumn : oldColumns) {
            String oldColumnName = oldColumn.get("column_name").toString();
            oldColumnsNameList.add(oldColumnName);
            if (!newColumns.keySet().contains(oldColumnName)) {
                columnsToDelete.add(oldColumnName);
            } else {
                String oldColumnType = oldColumn.get("udt_name").toString();
                String newColumnType = newColumns.get(oldColumnName);
                if (!newColumnType.equals(oldColumnType)) {
                    columnsToUpdateType.put(oldColumnName, newColumnType);
                }
            }
        }
        for (String newColumnName : newColumns.keySet()) {
            if (!oldColumnsNameList.contains(newColumnName)) {
                columnsToAdd.put(newColumnName, newColumns.get(newColumnName));
            }
        }

        if (!columnsToDelete.isEmpty()) {
            for (int i = 0; i < columnsToDelete.size(); i++) {
                script.append(String.format(UPDATE_SCRIPT_DELETE, columnsToDelete.get(i)));
                if (i != columnsToDelete.size() - 1) {
                    script.append(" | ");
                }
            }
        }
        if (!columnsToAdd.isEmpty()) {
            if (!columnsToDelete.isEmpty()) {
                script.append(" | ");
            }
            for (Map.Entry<String, String> columnToAdd : columnsToAdd.entrySet()) {
                script.append(String.format(UPDATE_SCRIPT_ADD, columnToAdd.getKey(), columnToAdd.getValue()));
                script.append(" | ");
            }
        }
        if (!columnsToUpdateType.isEmpty()) {
            for (Map.Entry<String, String> columnToUpdateType : columnsToUpdateType.entrySet()) {
                script.append(String.format(UPDATE_SCRIPT_UPDATE_TYPE, columnToUpdateType.getKey(), columnToUpdateType.getValue()));
                script.append(" | ");
            }
        }
        if (!columnsToAdd.isEmpty() || !columnsToUpdateType.isEmpty()) {
            script.delete(script.length() - 3, script.length() - 1);
        }
        script.append(UPDATE_SCRIPT_END);
    }

    private void writeInFileDeleteScript(StringBuilder script, String tableName) {
        script.append(String.format(DELETE_SCRIPT, tableName, tableName));
    }
}
