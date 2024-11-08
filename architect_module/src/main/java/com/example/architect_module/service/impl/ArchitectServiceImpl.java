package com.example.architect_module.service.impl;

import com.example.architect_module.model.ArchitectRequestDto;
import com.example.architect_module.service.ArchitectService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ArchitectServiceImpl implements ArchitectService {

    public static final String FILENAME_TEMPLATE = "V%d__%s_%s.sql";
    public static final String PATH_OF_MIGRATION_FILE = "C:\\Users\\Lucy\\IdeaProjects\\rent_apartement_app\\architect_module\\src\\main\\resources\\db\\migration\\%s";
    public static final String CREATE_SCRIPT_START = "CREATE TABLE IF NOT EXISTS %s (\n    id int8 PRIMARY KEY";
    public static final String COLUMNS = ",\n    %s %s";
    public static final String CREATE_SCRIPT_END = ");\n\nCREATE SEQUENCE %s_sequence START 1 INCREMENT 1;";
    public static final String UPDATE_SCRIPT_START = "ALTER TABLE ";
    public static final String DELETE_SCRIPT = "DROP TABLE IF EXIST %s;\n DROP SEQUENCE IF EXIST %s_sequence";
    private final JdbcTemplate jdbcTemplate;
    @Override
    public void createMigrationFile(ArchitectRequestDto architectRequestDto) {
        String operation = architectRequestDto.getOperation();
        System.out.println(operation);
        String name = architectRequestDto.getTableName();
        Map<String, String> columns = architectRequestDto.getValues();

        int version = calculateVersionOfScript();

        String filename = String.format(FILENAME_TEMPLATE, version, operation, name);
        String filePath = String.format(PATH_OF_MIGRATION_FILE, filename);

        Path path = Paths.get(filePath);
        try {
            StringBuilder script = new StringBuilder();
            switch (operation) {
                case "create":
                    writeInFileCreateScript(script, name, columns);
                    break;
                case "update":
                    writeInFileUpdateScript(script, name, columns);
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

    private void writeInFileCreateScript(StringBuilder script, String tableName, Map<String, String> columns) {
        script.append(String.format(CREATE_SCRIPT_START, tableName));
        for (Map.Entry<String, String> column : columns.entrySet()) {
            script.append(String.format(COLUMNS, column.getKey(), column.getValue()));
        }
        script.append(String.format(CREATE_SCRIPT_END, tableName));
    }

    private void writeInFileUpdateScript(StringBuilder script, String tableName, Map<String, String> newColumns) {
        script.append(String.format(UPDATE_SCRIPT_START, tableName));
        String query = "SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = \"%s\"";
        Map<String, Object> oldColumns = jdbcTemplate.queryForMap(String.format(query, tableName));
        for (Map.Entry<String, Object> oldColumn : oldColumns.entrySet()) {
            System.out.println(oldColumn.getKey() + oldColumn.getValue());
        }
    }

    private void writeInFileDeleteScript(StringBuilder script, String tableName) {
        script.append(String.format(DELETE_SCRIPT, tableName, tableName));
    }
}
