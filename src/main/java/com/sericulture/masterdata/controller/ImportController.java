package com.sericulture.masterdata.controller;

import com.sericulture.masterdata.model.entity.*;
import com.sericulture.masterdata.repository.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@RestController
@RequestMapping("/v1/import")
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

    @PostMapping("/import-excel-data")
    public String readExcelData(@RequestParam MultipartFile file) throws Exception{
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

            // Getting the Sheet at index i
            Sheet sheet = workbook.getSheetAt(0);
            System.out.println("=> " + sheet.getSheetName());
            // Create a DataFormatter to format and get each cell's value as String
            DataFormatter dataFormatter = new DataFormatter();
            // 1. You can obtain a rowIterator and columnIterator and iterate over them
            System.out.println("Iterating over Rows and Columns using Iterator");
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                Village updateVillage = new Village();
                long stateId = 0, districtId = 0, talukId = 0, hobliId = 0, villageId = 0;
                Row row = rowIterator.next();
                // Get the row number
                int rowNumber = row.getRowNum();
                // Now let's iterate over the columns of the current row
                if(rowNumber>0) {
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        int cellIndex = cell.getColumnIndex();
                        String cellValue = dataFormatter.formatCellValue(cell);

                        switch (cellIndex) {
                            case 0:
                                //state
                                System.out.print("State:" +cellValue + "\t");
                                if(!cellValue.equals("") && cellValue != null) {
                                    State state = stateRepository.findByStateNameAndActive(cellValue, true);
                                    if (state == null) {
                                        State state1 = new State();
                                        state1.setStateName(cellValue);
                                        State state2 = stateRepository.save(state1);
                                        stateId = state2.getStateId();
                                    } else {
                                        stateId = state.getStateId();
                                    }
                                }
                                break;
                            case 1:
                                //district
                                System.out.print("district:" +cellValue + "\t");
                                if(!cellValue.equals("") && cellValue != null) {
                                    District district = districtRepository.findByDistrictNameAndStateIdAndActive(cellValue, stateId, true);
                                    if (district == null) {
                                        District district1 = new District();
                                        district1.setDistrictName(cellValue);
                                        district1.setStateId(stateId);
                                        District district2 = districtRepository.save(district1);
                                        districtId = district2.getDistrictId();
                                    } else {
                                        districtId = district.getDistrictId();
                                    }
                                }
                                break;
                            case 2:
                                //taluk
                                System.out.print("taluk:" +cellValue + "\t");
                                if(!cellValue.equals("") && cellValue != null) {
                                    Taluk taluk = talukRepository.findByTalukNameAndDistrictIdAndActive(cellValue, districtId, true);
                                    if (taluk == null) {
                                        Taluk taluk1 = new Taluk();
                                        taluk1.setTalukName(cellValue);
                                        taluk1.setStateId(stateId);
                                        taluk1.setDistrictId(districtId);
                                        Taluk taluk2 = talukRepository.save(taluk1);
                                        talukId = taluk2.getTalukId();
                                    } else {
                                        talukId = taluk.getTalukId();
                                    }
                                }
                                break;
                            case 3:
                                //hobli
                                System.out.print("hobli:" +cellValue + "\t");
                                if(!cellValue.equals("") && cellValue != null) {
                                    Hobli hobli = hobliRepository.findByHobliNameAndTalukIdAndActive(cellValue, talukId, true);
                                    if (hobli == null) {
                                        Hobli hobli1 = new Hobli();
                                        hobli1.setHobliName(cellValue);
                                        hobli1.setStateId(stateId);
                                        hobli1.setDistrictId(districtId);
                                        hobli1.setTalukId(talukId);
                                        Hobli hobli2 = hobliRepository.save(hobli1);
                                        hobliId = hobli2.getHobliId();
                                    } else {
                                        hobliId = hobli.getHobliId();
                                    }
                                }
                                break;
                            case 4:
                                //villageEng
                                System.out.print("villageEng:" +cellValue + "\t");
                                if(!cellValue.equals("") && cellValue != null) {
                                    Village village = villageRepository.findByVillageNameAndActive(cellValue, true);
                                    if (village == null) {
                                        Village village1 = new Village();
                                        village1.setVillageName(cellValue);
                                        village1.setStateId(stateId);
                                        village1.setDistrictId(districtId);
                                        village1.setTalukId(talukId);
                                        village1.setHobliId(hobliId);
                                        Village village2 = villageRepository.save(village1);
                                        villageId = village2.getVillageId();
                                        updateVillage = village2;
                                    } else {
                                        villageId = village.getVillageId();
                                        updateVillage = village;
                                    }
                                }
                                break;
                            case 5:
                                //villageKan
                                System.out.print("villageKan:" +cellValue + "\t");
                                if(!cellValue.equals("") && cellValue != null) {
                                    if (villageId != 0) {
                                        updateVillage.setVillageNameInKannada(cellValue);
                                        villageRepository.save(updateVillage);
                                    }
                                }
                                break;
                        }
                    }
                    // Check if this is the last cell in the row
                    if (row.getLastCellNum() > 0 && row.getLastCellNum() == row.getPhysicalNumberOfCells()) {
                        System.out.println("\nEnd of Row " + rowNumber);
                    }
                }
                System.out.println();
            }

        return "OK";
    }
}