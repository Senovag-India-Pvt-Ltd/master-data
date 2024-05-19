package com.sericulture.masterdata.controller;

import com.sericulture.masterdata.model.entity.*;
import com.sericulture.masterdata.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@RestController
@RequestMapping("/v1/import")
@Slf4j
public class ImportController {

    @Autowired
    StateRepository stateRepository;

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    TalukRepository talukRepository;

    @Autowired
    HobliRepository hobliRepository;

    @Autowired
    VillageRepository villageRepository;

    @PostMapping("/import-district-and-taluk")
    public String readExcelDataForDistrictAndTaluk(@RequestParam MultipartFile file) throws Exception{
        try {
            log.info("Multipart file uploaded ", file.getOriginalFilename());
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

            // Getting the Sheet at index i
            Sheet sheet = workbook.getSheetAt(0);
            System.out.println("=> " + sheet.getSheetName());
            // Create a DataFormatter to format and get each cell's value as String
            DataFormatter dataFormatter = new DataFormatter();
            // 1. You can obtain a rowIterator and columnIterator and iterate over them
            System.out.println("Iterating over Rows and Columns using Iterator");
            log.info("Iterating over Rows and Columns using Iterator");
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                log.info("Inside row iterator");
                long stateId = 2, districtId = 0;
                Row row = rowIterator.next();
                // Get the row number
                int rowNumber = row.getRowNum();
                // Now let's iterate over the columns of the current row
                if (rowNumber > 0) {
                    Iterator<Cell> cellIterator = row.cellIterator();
                    String distName = "";
                    String distNameInKan = "";
                    String lgDist = "";
                    String talukName = "";
                    String talukNameInKan = "";
                    String lgTaluk = "";
                    while (cellIterator.hasNext()) {
                        log.info("Inside cell iterator");
                        Cell cell = cellIterator.next();
                        int cellIndex = cell.getColumnIndex();
                        String cellValue = dataFormatter.formatCellValue(cell);

                        switch (cellIndex) {
                            case 0:
                                //district name
                                System.out.print("District name eng:" + cellValue + "\t");
                                log.info("District name eng:" + cellValue + "\t");
                                if (!cellValue.equals("") && cellValue != null) {
                                    distName = cellValue;
                                }
                                break;
                            case 1:
                                //district kan
                                System.out.print("district Kan:" + cellValue + "\t");
                                log.info("District name kan:" + cellValue + "\t");
                                if (!cellValue.equals("") && cellValue != null) {
                                    distNameInKan = cellValue;
                                }
                                break;
                            case 2:
                                //district lg;
                                System.out.print("District lg:" + cellValue + "\t");
                                log.info("District lg:" + cellValue + "\t");
                                if (!cellValue.equals("") && cellValue != null) {
                                    lgDist = cellValue;
                                }
                                break;
                            case 3:
                                //taluk eng
                                System.out.print("taluk:" + cellValue + "\t");
                                log.info("Taluk eng:" + cellValue + "\t");
                                if (!cellValue.equals("") && cellValue != null) {
                                    talukName = cellValue;
                                }
                                break;
                            case 4:
                                //taluk kan
                                System.out.print("talukKan:" + cellValue + "\t");
                                log.info("Taluk kan:" + cellValue + "\t");
                                if (!cellValue.equals("") && cellValue != null) {
                                    talukNameInKan = cellValue;
                                }
                                break;
                            case 5:
                                //taluk kan
                                System.out.print("lgTaluk:" + cellValue + "\t");
                                log.info("LgTaluk :" + cellValue + "\t");
                                if (!cellValue.equals("") && cellValue != null) {
                                    lgTaluk = cellValue;
                                }
                                break;
                        }
                    }
                    // Check if this is the last cell in the row
                    if (row.getLastCellNum() > 0 && row.getLastCellNum() == row.getPhysicalNumberOfCells()) {
                        log.info("\nEnd of Row " + rowNumber);
                        System.out.println("\nEnd of Row " + rowNumber);
                        District district = districtRepository.findByLgDistrict(lgDist);
                        if (district == null) {
                            District district1 = new District();
                            district1.setLgDistrict(lgDist);
                            district1.setDistrictName(distName);
                            district1.setDistrictNameInKannada(distNameInKan);
                            district1.setStateId(stateId);
                            log.info("\nSave district here. Entity: " + district1);
                            District district2 = districtRepository.save(district1);
                            districtId = district2.getDistrictId();
                        } else {
                            districtId = district.getDistrictId();
                        }

                        Taluk taluk = talukRepository.findByLgTaluk(lgTaluk);
                        if (taluk == null) {
                            Taluk taluk1 = new Taluk();
                            taluk1.setLgTaluk(lgTaluk);
                            taluk1.setTalukName(talukName);
                            taluk1.setTalukNameInKannada(talukNameInKan);
                            taluk1.setStateId(stateId);
                            taluk1.setDistrictId(districtId);
                            log.info("\nSave taluk here. Entity: " + taluk1);
                            talukRepository.save(taluk1);
                        }
                    }
                }
                System.out.println();
            }
        }catch (Exception ex){
            log.debug("error:" + ex);
            ex.printStackTrace();
        }

        return "OK";
    }

    @PostMapping("/import-excel-data")
    public String readExcelData(@RequestParam MultipartFile file) throws Exception{
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            log.info("\nUpload villages file name: " + file.getOriginalFilename());
            // Getting the Sheet at index i
            Sheet sheet = workbook.getSheetAt(0);
            System.out.println("=> " + sheet.getSheetName());
            // Create a DataFormatter to format and get each cell's value as String
            DataFormatter dataFormatter = new DataFormatter();
            // 1. You can obtain a rowIterator and columnIterator and iterate over them
            System.out.println("Iterating over Rows and Columns using Iterator");
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                log.info("Inside row iterator");
                long stateId = 2, districtId = 0, talukId = 0, hobliId = 0;
                String hobliName = "";
                String hobliNameInKan = "";
                String villageName = "";
                String villageNameInKan = "";
                String lgVillage = "";
                Row row = rowIterator.next();
                // Get the row number
                int rowNumber = row.getRowNum();
                // Now let's iterate over the columns of the current row
                if (rowNumber > 0) {
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        log.info("Inside cell iterator");
                        Cell cell = cellIterator.next();
                        int cellIndex = cell.getColumnIndex();
                        String cellValue = dataFormatter.formatCellValue(cell);

                        switch (cellIndex) {
                            case 0:
                                //district lg
                                System.out.print("lgDistrict:" + cellValue + "\t");
                                log.info("\nlgDist: " + cellValue);
                                if (!cellValue.equals("") && cellValue != null) {
                                    District district = districtRepository.findByLgDistrict(cellValue);
                                    if (district != null) {
                                        districtId = district.getDistrictId();
                                    }
                                }
                                break;
                            case 1:
                                //talukLg
                                System.out.print("taluk:" + cellValue + "\t");
                                log.info("\nlgTaluk: " + cellValue);
                                if (!cellValue.equals("") && cellValue != null) {
                                    Taluk taluk = talukRepository.findByLgTaluk(cellValue);
                                    if (taluk != null) {
                                        talukId = taluk.getTalukId();
                                    }
                                }
                                break;
                            case 2:
                                //hobli
                                System.out.print("hobli:" + cellValue + "\t");
                                log.info("\nHobli: " + cellValue);
                                if (!cellValue.equals("") && cellValue != null) {
                                    hobliName = cellValue;
                                }
                                break;
                            case 3:
                                //hobli kan
                                System.out.print("hobliKan:" + cellValue + "\t");
                                log.info("\nHobliKan: " + cellValue);
                                if (!cellValue.equals("") && cellValue != null) {
                                    hobliNameInKan = cellValue;
                                }
                                break;
                            case 4:
                                //villageLg
                                System.out.print("villageLg:" + cellValue + "\t");
                                log.info("\nlgVillage: " + cellValue);
                                if (!cellValue.equals("") && cellValue != null) {
                                    lgVillage = cellValue;
                                }
                                break;
                            case 5:
                                //villageEng
                                System.out.print("villageEng:" + cellValue + "\t");
                                log.info("\nvillageEng: " + cellValue);
                                if (!cellValue.equals("") && cellValue != null) {
                                    villageName = cellValue;
                                }
                            case 6:
                                //villageKan
                                System.out.print("villageKan:" + cellValue + "\t");
                                log.info("\nVillageKan: " + cellValue);
                                if (!cellValue.equals("") && cellValue != null) {
                                    villageNameInKan = cellValue;
                                }
                                break;
                        }
                    }
                    // Check if this is the last cell in the row
                    if (row.getLastCellNum() > 0 && row.getLastCellNum() == row.getPhysicalNumberOfCells()) {
                        System.out.println("\nEnd of Row " + rowNumber);
                        log.info("\nEnd of Row " + rowNumber);
                        Hobli hobli = hobliRepository.findByHobliNameAndTalukIdAndDistrictIdAndActive(hobliName, talukId, districtId, true);
                        if (hobli == null) {
                            Hobli hobli1 = new Hobli();
                            hobli1.setHobliName(hobliName);
                            hobli1.setHobliNameInKannada(hobliNameInKan);
                            hobli1.setStateId(stateId);
                            hobli1.setDistrictId(districtId);
                            hobli1.setTalukId(talukId);
                            Hobli hobli2 = hobliRepository.save(hobli1);
                            log.info("\nSave hobli entity: " + hobli1);
                            hobliId = hobli2.getHobliId();
                        } else {
                            hobliId = hobli.getHobliId();
                        }

                        Village village = villageRepository.findByLgVillage(lgVillage);
                        if (village == null) {
                            Village village1 = new Village();
                            village1.setLgVillage(lgVillage);
                            village1.setVillageName(villageName);
                            village1.setVillageNameInKannada(villageNameInKan);
                            village1.setStateId(stateId);
                            village1.setDistrictId(districtId);
                            village1.setTalukId(talukId);
                            village1.setHobliId(hobliId);
                            log.info("\nSave village entity: " + village1);
                            villageRepository.save(village1);
                        }
                    }
                }
                System.out.println();
            }
        }catch (Exception ex){
            log.debug("Error:"+ex);
            ex.printStackTrace();
        }

        return "OK";
    }
}