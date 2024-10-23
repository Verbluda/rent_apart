package com.example.architect_module.service.impl;

import com.example.architect_module.model.ArchitectRequestDto;
import com.example.architect_module.service.ArchitectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArchitectServiceImpl implements ArchitectService {
    @Override
    public void createMigrationFile(ArchitectRequestDto architectRequestDto) {
        String operation = architectRequestDto.getOperation();

        //создать файл с версией, операцией и именем таблицы в названии
        switch (operation) {
            case "create":
                // вызвать функцию для записи скрипта по созданию
                System.out.println("create");
                break;
            case "update":
                // вызвать функцию для записи скрипта по апдейту
                System.out.println("update");
                break;
            case "drop":
                // вызвать функцию для записи скрипта по удалению
                System.out.println("drop");
                break;
            default:
                // выбросить исключение
                System.out.println("неизвестная операция с базой данных");
        }
    }

    private int calculateVersionOfScript() {
        //вычисление версии скрипта с помощью  JDBCTemplate, отдельная функция без параметров
        return 1;
    }
}
