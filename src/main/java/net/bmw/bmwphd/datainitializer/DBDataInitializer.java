package net.bmw.bmwphd.datainitializer;

import net.bmw.bmwphd.dao.ChangeRequestDao;
import net.bmw.bmwphd.dao.HorseDao;
import net.bmw.bmwphd.domain.ChangeRequest;
import net.bmw.bmwphd.domain.Horse;
import net.bmw.bmwphd.domain.User;
import net.bmw.bmwphd.service.HorseService;
import net.bmw.bmwphd.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class DBDataInitializer implements CommandLineRunner {
    private HorseDao horseDao;
    private HorseService horseService;
    private UserService userService;
    private ChangeRequestDao changeRequestDao;
    ArrayList<String[]> horses = new ArrayList<>();
    //ArrayList<ArrayList<String>> horses = new ArrayList<>();
    public DBDataInitializer(HorseDao horseDao, HorseService horseService, UserService userService, ChangeRequestDao changeRequestDao) {
        this.horseDao = horseDao;
        this.horseService = horseService;
        this.userService = userService;
        this.changeRequestDao = changeRequestDao;
    }



    @Override
    public void run(String... args) throws Exception {
        String line = "";
        String splitBy = ",";
        int i = 0;
        //parsing a CSV file into BufferedReader class constructor
        BufferedReader br = null;
        try {
           // br = new BufferedReader(new FileReader("horses.csv"));
            br = new BufferedReader(new FileReader("horses_2009-2022.csv"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        while (true)   //returns a Boolean value
        {
            try {
                if (!((line = br.readLine()) != null)) break;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //ArrayList<String> horse = Arrays.asList(line.split(splitBy));
            String[] horse = line.split(splitBy);    // use comma as separator
//            for (String emp: employee) {
//                //emp.toLowerCase();
//                emp = emp.substring(0,1).toUpperCase() + emp.substring(1).toLowerCase();
//            }
            horses.add(horse);
            //horses[i] = employee;
           // System.out.println(horses.get(i));
            //System.out.println("Name: " + horses.get(0)[1]);
            //System.out.println(horses[0][i]);
            i++;
//            System.out.println("Len: " + employee.length);
//            for(String emp : employee){
//                System.out.println(emp);
//            }

            //System.out.println("Employee [First Name=" + employee[0] + ", Last Name=" + employee[1] + ", Designation=" + employee[2] + ", Contact=" + employee[3] + ", Salary= " + employee[4] + ", City= " + employee[5] +"]");
        }


//        Horse h1 = new Horse();
//        h1.setId(horses.get(0)[0]);
//        h1.setName(horses.get(0)[1]);
//        h1.setSire(horses.get(0)[2]);
//        h1.setDam(horses.get(0)[3]);
        int j = 0;
        for(int k = 1; k < horses.size(); k++){
            j++;
            horseDao.save(new Horse(Integer.toString(j), horses.get(k)[0], horses.get(k)[1], horses.get(k)[2], horses.get(k)[3], horses.get(k)[4], horses.get(k)[5], horses.get(k)[6]));
        }

//        Horse h2 = new Horse();
//        h2.setId(horses.get(1)[0]);
//        h2.setName(horses.get(1)[1]);
//        h2.setSire(horses.get(1)[2]);
//        h2.setDam(horses.get(1)[3]);
////        h2.setId("kopokppo");
////        h2.setName("Chirayu");
////        h2.setDam("CM");
////        h2.setSire("CD");
//        Horse h3 = new Horse();
//        h3.setId(horses.get(2)[0]);
//        h3.setName(horses.get(2)[1]);
//        h3.setSire(horses.get(1)[2]);
//        h3.setDam(horses.get(1)[3]);
//
//        Horse h4 = new Horse();
//        h4.setId(horses.get(1)[0]);
//        h4.setName(horses.get(1)[1]);
//        h4.setSire(horses.get(1)[2]);
//        h4.setDam(horses.get(1)[3]);
//
//        Horse h5 = new Horse();
//        h5.setId(horses.get(1)[0]);
//        h5.setName(horses.get(1)[1]);
//        h5.setSire(horses.get(1)[2]);
//        h5.setDam(horses.get(1)[3]);
//
//        horseDao.save(h1);
//        horseDao.save(h2);
        User u4 = new User();
        u4.setPassword("123");
        u4.setName("Chirayu");
        u4.setEmail("chirayu@tcu.edu");
        u4.setRole("Judge, Admin");

        User u6 = new User();
        u6.setPassword("123");
        u6.setName("Madison");
        u6.setEmail("madison@tcu.edu");
        u6.setRole("Fan");

        userService.save(u4);
        userService.save(u6);

        ChangeRequest changeRequest1 = new ChangeRequest();
        changeRequest1.setAttribute("Dam");
        changeRequest1.setSuggestedChange("New Dam");
        changeRequest1.setStatus("Pending");
        changeRequest1.setOwnerId(u4.getId());

        ChangeRequest changeRequest2 = new ChangeRequest();
        changeRequest2.setAttribute("Sire");
        changeRequest2.setSuggestedChange("New Sire");
        changeRequest2.setStatus("Pending");
        changeRequest2.setOwnerId(u4.getId());

        changeRequestDao.save(changeRequest1);
        changeRequestDao.save(changeRequest2);
    }
}
