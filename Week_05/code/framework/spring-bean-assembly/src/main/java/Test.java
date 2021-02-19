import com.liquid.annotation.CustomConfig;
import com.liquid.entity.Klass;
import com.liquid.entity.School;
import com.liquid.entity.Student;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * 测试类
 *
 * @author Liquid
 */
public class Test {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        //===1.在xml文件中进行显示配置,采用bean标签配置===
        Student student100 = (Student) context.getBean("student100");
        System.out.println("name: " + student100.getName());

        Klass class1 = (Klass) context.getBean("class1");
        List<Student> students = class1.getStudents();
        System.out.println(students);

        //===2.使用Java显示配置,采用@Configuration与@Bean搭配使用===

        //自动扫描通过xml配置
        // Student student200 = (Student) context.getBean("student200");
        // System.out.println(student200.getName());
        //
        // Klass class2 = (Klass) context.getBean("class2");
        // List<Student> students2 = class2.getStudents();
        // System.out.println(students2);

        //自动扫描通过注解配置
        AnnotationConfigApplicationContext annotationContext = new AnnotationConfigApplicationContext(CustomConfig.class);
        Student student200 = (Student) annotationContext.getBean("student200");
        System.out.println(student200.getName());

        Klass class2 = (Klass) annotationContext.getBean("class2");
        List<Student> students2 = class2.getStudents();
        System.out.println(students2);

        //===3.使用注解隐式配置,采用@Component注解===
        //@Autowired注解按byType注入,required属性默认为true,一定要找到匹配的Bean，否则抛异常(spring)
        //可以配合@Qualifier使用

        //@Resource注解默认按byName注入,可以通过name和type属性进行选择性注入(j2ee)
        School school = (School) annotationContext.getBean("school");
        System.out.println(school.getClassx().getStudents().toString());

    }
}
