package com.example.architect_module.service.impl;

import com.example.architect_module.model.ArchitectRequestDto;
import com.example.architect_module.service.ArchitectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ArchitectServiceImpl implements ArchitectService {
    @Override
    public void createMigrationFile(ArchitectRequestDto architectRequestDto) {
        String operation = architectRequestDto.getOperation();
        String name = architectRequestDto.getTableName();
        Map<String, String> values = architectRequestDto.getValues();

        System.out.println(operation + " " + name);

        //создать файл в src/main/resources/db/migration с версией, операцией и именем таблицы в названии

        switch (operation) {
            case "create":
                writeInFileCreateScript(name, values);
                System.out.println("create");
                break;
            case "update":
                writeInFileUpdateScript(name, values);
                System.out.println("update");
                break;
            case "delete":
                writeInFileDeleteScript(name);
                System.out.println("delete");
                break;
            default:
                System.out.println("неизвестная операция с базой данных");
                throw new RuntimeException();
        }
    }

    private int calculateVersionOfScript() {

        //вычисление версии скрипта с помощью  JDBCTemplate, отдельная функция без параметров
        return 1;
    }

    private void writeInFileCreateScript(String tableName, Map<String, String> value) {

    }

    private void writeInFileUpdateScript(String tableName, Map<String, String> value) {

    }

    private void writeInFileDeleteScript(String tableName) {

    }
}
