package com.example.rent_module.service.impl;

import com.example.rent_module.model.entity.AddressEntity;
import com.example.rent_module.model.entity.ApartmentEntity;
import com.example.rent_module.repository.ApartmentRepository;
import com.example.rent_module.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ApartmentRepository apartmentRepository;

    @Override
    public String getReport() {
        List<ApartmentEntity> apartments = apartmentRepository.findAll();
        File file = new File("C:\\Users\\Lucy\\IdeaProjects\\rent_apartement_app\\template.xlsx");
//        if (file.length() == 0) {
//            throw new IllegalArgumentException("The provided file is empty");
//        }
        try(FileInputStream fileInputStream = new FileInputStream(file);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            int rowNumber = 1;

            for (ApartmentEntity apart: apartments) {
                Row row = sheet.createRow(rowNumber++);
                row.createCell(0).setCellValue(getFullAddress(apart));
                row.createCell(1).setCellValue(apart.getPricePerDay());
                row.createCell(2).setCellValue(LocalDateTime.now());
            }

            workbook.write(fileOutputStream);
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Файл для выгрузки отчета не найден");
        } catch (IOException e) {
            throw new RuntimeException("Проблема с выгрузкой отчета");
        }
        return null;
    }

    private String getFullAddress(ApartmentEntity apart) {
        AddressEntity address = apart.getAddress();
        return address.getCity() + " " + address.getStreet() + " " + address.getNumberOfApartment();
    }
}
