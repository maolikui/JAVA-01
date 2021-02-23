import com.liquid.annotation.CustomConfig;
import com.liquid.annotation.ImportDemoConfig;
import com.liquid.aopdefination.DemoComponent;
import com.liquid.entity.*;
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
        //显式

        //=======1.在xml文件中进行配置,采用bean标签配置=======
        //1)通过类的全类名
        Student student100 = (Student) context.getBean("student100");
        System.out.println("name: " + student100.getName());

        Klass class1 = (Klass) context.getBean("class1");
        List<Student> students = class1.getStudents();
        System.out.println(students);

        //2)通过工厂方法
        MobilePhone phone = (MobilePhone) context.getBean("mobilePhone");
        System.out.println("brand: " + phone.getBrand());

        //3)通过FactoryBean方式(扫描亦可)
        MobilePhone phone2 = (MobilePhone) context.getBean("mobilePhone2");
        System.out.println("brand: " + phone2.getBrand());


        //========2.使用Java配置,采用@Configuration与@Bean搭配使用=========
        //注解需要开启自动扫描并指定扫描范围
        //自动扫描通过xml配置
        // Student student200 = (Student) context.getBean("student200");
        // System.out.println(student200.getName());
        //
        // Klass class2 = (Klass) context.getBean("class2");
        // List<Student> students2 = class2.getStudents();
        // System.out.println(students2);

        //自动扫描通过注解配置
        AnnotationConfigApplicationContext annotationContext = new AnnotationConfigApplicationContext(CustomConfig.class, ImportDemoConfig.class);
        Student student200 = (Student) annotationContext.getBean("student200");
        System.out.println(student200.getName());

        Klass class2 = (Klass) annotationContext.getBean("class2");
        List<Student> students2 = class2.getStudents();
        System.out.println(students2);

        // 隐式

        //===============3.使用@Component等注解=====================
        //@Autowired注解按byType注入,required属性默认为true,一定要找到匹配的Bean，否则抛异常(spring)
        //可以配合@Qualifier使用

        //@Resource注解默认按byName注入,可以通过name和type属性进行选择性注入(j2ee)
        School school = (School) annotationContext.getBean("school");
        System.out.println(school.getClassx().getStudents().toString());

        //================4.使用@import注解========================
        //@Import(类全限定名)
        ImportDemo importDemo = (ImportDemo) annotationContext.getBean("com.liquid.entity.ImportDemo");
        System.out.println(importDemo);
        //ImportSelector方式
        ImportDemo2 importDemo2 = (ImportDemo2) annotationContext.getBean("com.liquid.entity.ImportDemo2");
        System.out.println(importDemo2);
        //ImportBeanDefinitionRegistrar
        ImportDemo3 importDemo3 = (ImportDemo3) annotationContext.getBean("importDemo3");
        System.out.println(importDemo3);
        //在Import注解的配置类下显式配置
        Student student007 = (Student) annotationContext.getBean("student007");
        System.out.println(student007);

        //=================5.使用AOP，BeanDefination动态注入================
        DemoComponent demoComponent = (DemoComponent) annotationContext.getBean("demoComponent");
        demoComponent.demo();
    }
}
