package junit;

import com.google.common.reflect.ClassPath;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;

public class SimpleJUnit {

    public static void main(String[] args) throws Exception {
        // Достает все классы
        Set<? extends Class<?>> classes = ClassPath.from(ClassLoader.getSystemClassLoader())
                .getAllClasses()
                .stream()
                .filter(classInfo -> classInfo.getPackageName().contains("guru.qa"))
                .map(ClassPath.ClassInfo::load)
                .collect(Collectors.toSet());

        for (Class<?> aClass : classes) {
            Method[] methods = aClass.getDeclaredMethods();
            for (Method method : methods) {
                // Смотрит в метаинформации классов нет ли там @Test
                Test testAnnotation = method.getAnnotation(Test.class);
                if (testAnnotation != null) {
                    // Если есть, то запускает этот метод
                    method.setAccessible(true);
                    try {
                        method.invoke(aClass.getDeclaredConstructor().newInstance());
                    } catch (Throwable t) {
                        // Если метод выбросил исключение с типом AssertionError - желтый тест
                        if (AssertionError.class.isAssignableFrom(t.getCause().getClass())) {
                            System.out.println("Тест :" + method.getName() + " желтый");
                        } else {
                            // Если метод выбросил исключение с любым другим - красный тест
                            System.out.println("Тест :" + method.getName() + " красный");
                        }
                        continue;
                    }
                    // Если метод успешно выполнился - зеленый тест
                    System.out.println("Тест :" + method.getName() + " зеленый");
                }
            }
        }
    }
}
