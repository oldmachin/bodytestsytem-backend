package org.example.initializer;

import com.opencsv.CSVReader;
import jakarta.transaction.Transactional;
import org.example.model.entity.department.Major;
import org.example.model.entity.department.School;
import org.example.repository.MajorRepository;
import org.example.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.time.Year;

@Component
@Order(1)
public class DepartmentDataInitializer implements CommandLineRunner {
    @Autowired
    private MajorRepository majorRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (schoolRepository.count() == 0 && majorRepository.count() == 0) {
            System.out.println("No data found. Starting initialization.");

            try (CSVReader reader = new CSVReader(new InputStreamReader(getClass().getResourceAsStream("/initial_university_data.csv")))) {
                reader.skip(1);
                String[] nextLine;
                School school = null;
                String schoolName = "";

                while((nextLine = reader.readNext()) != null) {
                    if (nextLine.length < 9) continue;
                    for (String line : nextLine) {
                        System.out.println(line);
                    }
                    String majorName = nextLine[1];
                    String majorCode = nextLine[2];
                    String academicField = nextLine[3];
                    String majorCategory = nextLine[4];
                    String degreeCategory = nextLine[5];
                    Year approveYear = Year.of(Integer.parseInt(nextLine[6].replace("年", "")));
                    boolean enrollmentStatus = nextLine[7].equals("在招");
                    if (!nextLine[8].isBlank() && !schoolRepository.existsByName(nextLine[8])) {
                        schoolName = nextLine[8];
                        school = new School(schoolName);
                        schoolRepository.save(school);
                    }
                    Major major = new Major(majorCode, majorName, majorCategory, academicField, degreeCategory, approveYear, enrollmentStatus, school);
                    majorRepository.save(major);
                }
            }
        } else {
            System.out.println("Data already exists. Skipping initialization.");
        }
    }
}
