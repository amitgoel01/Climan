/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.crm.Utils;


import com.crm.database.entity.EmployeeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DataGenerator {


    private static final String[] FIRST = new String[]{
            "empName", "empId", "businessUnit", "emailId", "country", "city", "state", "designation", "jobtype", "rpManager", "phNumber", "doj", "dob", "tenure"};

    public static List<EmployeeEntity> generateProducts() {
        List<EmployeeEntity> employeeList = new ArrayList<>(2);
        Random rnd = new Random();
        for (int i = 0; i < FIRST.length; i++) {
            for (int j = 0; j < 2; j++) {
                EmployeeEntity employee = new EmployeeEntity();
                employee.setEmpName(FIRST[i]+ Integer.toString(i));
                employee.setEmpId(FIRST[i]+ Integer.toString(i));
                employee.setBusinessUnit(FIRST[i]+ Integer.toString(i));
                employee.setEmailId(FIRST[i]+ Integer.toString(i));
                employee.setCountry(FIRST[i]+ Integer.toString(i));
                employee.setCity(FIRST[i]+ Integer.toString(i));
                employee.setState(FIRST[i]+ Integer.toString(i));
                employee.setDesignation(FIRST[i]+ Integer.toString(i));
                employee.setJobType(FIRST[i]+ Integer.toString(i));
                employee.setRpManager(FIRST[i]+ Integer.toString(i));
                employee.setPhNumber(FIRST[i]+ Integer.toString(i));
                employee.setDoj(FIRST[i]+ Integer.toString(i));
                employee.setDob(FIRST[i]+ Integer.toString(i));
                employee.setTenure(FIRST[i]+ Integer.toString(i));
                employeeList.add(employee);
            }
        }
        return employeeList;
    }
}
