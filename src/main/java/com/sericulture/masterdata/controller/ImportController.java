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

                        List<Taluk> taluk = talukRepository.findByLgTaluk(lgTaluk);
                        if (taluk.size() ==0) {
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

    @PostMapping("/import-lg-district-and-lg-taluk")
    public String readExcelDataForDistrictAndTalukLg(@RequestParam MultipartFile file) throws Exception{
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
                    String lgDist = "";
                    String talukName = "";
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
                                //district lg;
                                System.out.print("District lg:" + cellValue + "\t");
                                log.info("District lg:" + cellValue + "\t");
                                if (!cellValue.equals("") && cellValue != null) {
                                    lgDist = cellValue;
                                }
                                break;
                            case 2:
                                //taluk eng
                                System.out.print("taluk:" + cellValue + "\t");
                                log.info("Taluk eng:" + cellValue + "\t");
                                if (!cellValue.equals("") && cellValue != null) {
                                    talukName = cellValue;
                                }
                                break;
                            case 3:
                                //taluk lg
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
                        List<District> district = districtRepository.findByDistrictName(distName);
                        if (district.size()>0) {
                            district.get(0).setLgDistrict(lgDist);
                            districtId = district.get(0).getDistrictId();
                            log.info("\nSave district here. Entity: " + district.get(0));
                            districtRepository.save(district.get(0));
                        }
                        List<Taluk> taluk = talukRepository.findByTalukNameAndDistrictId(talukName, districtId);
                        if (taluk.size()>0) {
                            taluk.get(0).setLgTaluk(lgTaluk);
                            log.info("\nSave taluk here. Entity: " + taluk.get(0));
                            talukRepository.save(taluk.get(0));
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
                                    List<Taluk> taluk = talukRepository.findByLgTaluk(cellValue);
                                    if (taluk.size()>0) {
                                        talukId = taluk.get(0).getTalukId();
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

    @PostMapping("/import-codes")
    public String importCodes(@RequestParam MultipartFile file) throws Exception{
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
                long districtId = 0, talukId = 0, hobliId = 0, villageId = 0;
                String distCode = "";
                String talukCode = "";
                String hobliCode = "";
                String villageCode = "";
                District districtObj = new District();
                Taluk talukObj = new Taluk();
                Hobli hobliObj = new Hobli();
                Village villageObj = new Village();
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
                                //district name
                                System.out.print("distname:" + cellValue + "\t");
                                log.info("\ndistname: " + cellValue);
                                if (!cellValue.equals("") && cellValue != null) {
                                    List<District> district = districtRepository.findByDistrictName(cellValue);
                                    if (district.size()>0) {
                                        districtId = district.get(0).getDistrictId();
                                        districtObj = district.get(0);
                                    }
                                }
                                break;
                            case 1:
                                //dist code
                                System.out.print("distcode:" + cellValue + "\t");
                                log.info("\ndistcode: " + cellValue);
                                if (!cellValue.equals("") && cellValue != null) {
                                    distCode = cellValue;
                                }
                                break;
                            case 2:
                                //taluk name
                                System.out.print("talukname:" + cellValue + "\t");
                                log.info("\ntalukname: " + cellValue);
                                if (!cellValue.equals("") && cellValue != null) {
                                    List<Taluk> talukList = talukRepository.findByTalukNameAndDistrictId(cellValue, districtId);
                                    if (talukList.size()>0) {
                                        talukId = talukList.get(0).getTalukId();
                                        talukObj = talukList.get(0);
                                    }
                                }
                                break;
                            case 3:
                                //taluk code
                                System.out.print("talukcode:" + cellValue + "\t");
                                log.info("\ntalukcode: " + cellValue);
                                if (!cellValue.equals("") && cellValue != null) {
                                    talukCode = cellValue;
                                }
                                break;
                            case 4:
                                //hobli name
                                System.out.print("hobliname:" + cellValue + "\t");
                                log.info("\nhobliname: " + cellValue);
                                if (!cellValue.equals("") && cellValue != null) {
                                    List<Hobli> hobliList = hobliRepository.findByHobliNameAndTalukIdAndDistrictId(cellValue, talukId, districtId);
                                    if (hobliList.size()>0) {
                                        hobliId = hobliList.get(0).getHobliId();
                                        hobliObj = hobliList.get(0);
                                    }
                                }
                                break;
                            case 5:
                                //hobli code
                                System.out.print("hoblicode:" + cellValue + "\t");
                                log.info("\nhoblicode: " + cellValue);
                                if (!cellValue.equals("") && cellValue != null) {
                                    hobliCode = cellValue;
                                }
                            case 6:
                                //village name
                                System.out.print("villagename:" + cellValue + "\t");
                                log.info("\nVillagename: " + cellValue);
                                if (!cellValue.equals("") && cellValue != null) {
                                    List<Village> villages = villageRepository.findByVillageNameAndDistrictIdAndTalukIdAndHobliId(cellValue, districtId, talukId, hobliId);
                                    if (villages.size()>0) {
                                        villageId = villages.get(0).getVillageId();
                                        villageObj = villages.get(0);
                                    }
                                }
                                break;
                            case 7:
                                //village code
                                System.out.print("villagecode:" + cellValue + "\t");
                                log.info("\nVillagecode: " + cellValue);
                                if (!cellValue.equals("") && cellValue != null) {
                                    villageCode = cellValue;
                                }
                                break;
                        }
                    }
                    // Check if this is the last cell in the row
                    if (row.getLastCellNum() > 0 && row.getLastCellNum() == row.getPhysicalNumberOfCells()) {
                        System.out.println("\nEnd of Row " + rowNumber);
                        log.info("\nEnd of Row " + rowNumber);

                        if(districtId>0){
                            districtObj.setDistrictCode(distCode);
                            districtRepository.save(districtObj);
                        }
                        if(talukId>0){
                            talukObj.setTalukCode(talukCode);
                            talukRepository.save(talukObj);
                        }
                        if(hobliId>0){
                            hobliObj.setHobliCode(hobliCode);
                            hobliRepository.save(hobliObj);
                        }
                        if(villageId>0){
                            villageObj.setVillageCode(villageCode);
                            villageRepository.save(villageObj);
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

    @PostMapping("/re-import-excel-data")
    public String reReadExcelData(@RequestParam MultipartFile file) throws Exception{
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
                                //district name
                                System.out.print("District name:" + cellValue + "\t");
                                log.info("\nDist name: " + cellValue);
                                if (!cellValue.equals("") && cellValue != null) {
                                    List<District> district = districtRepository.findByDistrictName(cellValue);
                                    if (district.size()>0) {
                                        districtId = district.get(0).getDistrictId();
                                    }
                                }
                                break;
                            case 1:
                                //taluk name
                                System.out.print("taluk name:" + cellValue + "\t");
                                log.info("\nTaluk name: " + cellValue);
                                if (!cellValue.equals("") && cellValue != null) {
                                    List<Taluk> taluk = talukRepository.findByTalukNameAndDistrictId(cellValue, districtId);
                                    if (taluk.size()>0) {
                                        talukId = taluk.get(0).getTalukId();
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

                        List<Village> village = villageRepository.findByVillageNameAndDistrictIdAndTalukIdAndHobliId(villageName, districtId, talukId, hobliId);
                        if (village.size() == 0) {
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