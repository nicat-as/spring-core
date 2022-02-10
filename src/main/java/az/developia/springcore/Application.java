package az.developia.springcore;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;

public class Application {
    private ApplicationContext context;

    public Application() {
        this.context = loadContext();
    }

    public static void main(String[] args) {
        Application application = new Application();
        application.displayStudentBean();
        application.displayEmployeeBean();
    }

    private ApplicationContext loadContext() {
        // create generic context for adding multiple context
        var generic = new GenericApplicationContext();

        // define xml beans and load
        new XmlBeanDefinitionReader(generic).loadBeanDefinitions("bean.xml");

        // define class path beans (annotation based)
        new ClassPathBeanDefinitionScanner(generic).scan("az.developia.springcore");

        // refresh context for registering new beans
        generic.refresh();

        return generic;
    }

    void displayStudentBean() {
        var context = (GenericApplicationContext) this.context;

        // get student object from context
        var student = context.getBean(StudentBean.class);

        System.out.println("=".repeat(5) + "Student info" + "=".repeat(5));
        System.out.println(student);

        // student bean definition object
        var studentBeanDefinition = context.getBeanDefinition("studentBean");

        System.out.println("=".repeat(5) + "Student constructors" + "=".repeat(5));
        studentBeanDefinition
                .getConstructorArgumentValues()
                .getGenericArgumentValues()
                .forEach(v -> System.out.println(v.getName()));

        System.out.println("=".repeat(5) + "Student bean scope" + "=".repeat(5));
        System.out.println(studentBeanDefinition.getScope());
    }

    void displayEmployeeBean() {
        var context = (GenericApplicationContext) this.context;

        System.out.println("=".repeat(5) + "All Beans" + "=".repeat(5));
        // print all bean names
        context.getBeanFactory()
                .getBeanNamesIterator()
                .forEachRemaining(System.out::println);

        System.out.println("=".repeat(5) + "Employee info" + "=".repeat(5));
        // get employee object from context
        var employee = context.getBean(Employee.class);
        System.out.println(employee);
    }
}
