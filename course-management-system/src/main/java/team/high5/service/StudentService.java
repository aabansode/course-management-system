package team.high5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.high5.domain.entities.Course;
import team.high5.domain.entities.CourseOffering;
import team.high5.domain.entities.Enrolment;
import team.high5.domain.user.Student;
import team.high5.repository.StudentRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * course-management-system
 *
 * @Author : Charles Ma
 * @Date : 09-05-2018
 * @Time : 15:04
 * @Description :
 */
@Service
public class StudentService extends UserService<Student> {

    private final StudentRepo studentRepo;
    private final ScheduleService scheduleService;
    private final CourseOfferingService offeringService;


    @Autowired
    public StudentService(StudentRepo studentRepo,
                          ScheduleService scheduleService,
                          CourseOfferingService offeringService) {
        super(studentRepo);
        this.studentRepo = studentRepo;
        this.scheduleService = scheduleService;
        this.offeringService = offeringService;
    }

    public boolean enrol(Student student, CourseOffering offering) {
        boolean res = offering.getSchedule().equals(scheduleService.findCurrentSchedule());
        if (!res) {
            throw new IllegalArgumentException("The offering is not for this semester.");
        }
        // TODO: 2018/5/9 0009 To be finished.
        return false;
    }

    public List<CourseOffering> viewCourseOffering(Student student) {
        List<CourseOffering> res = new ArrayList<>();
        List<CourseOffering> currOfferings = offeringService.findOfferingsInCurrentSemester();
        // TODO: 2018/5/9 0009 To be finished. filter those course offerings the student can enrol.
        for (CourseOffering offering : currOfferings) {
            for (Course course : offering.getCourse().getPrerequisites()) {
                for (Enrolment enrolment : student.getPerformance()) {
                    if (enrolment.getOffering().getCourse().getCode().equals(course.getCode())) {
                        res.add(offering);
                    }
                }
            }
        }
        return res;
    }

}
