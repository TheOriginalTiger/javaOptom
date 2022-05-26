import javassist.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.Arrays;

public class ClassTransformer implements ClassFileTransformer {

    private static int count = 0;

    @Override
    public byte[] transform(ClassLoader loader,String className,Class<?> classBeingRedefined,ProtectionDomain protectionDomain,byte[] classfileBuffer) {
        System.out.println(String.format("loaded %s classes", ++count));
        ClassPool classPool = ClassPool.getDefault();
        CtClass current;
        try {
            current = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
            CtConstructor[] constructors = current.getConstructors();
            Arrays.stream(constructors).forEach(x -> {
                try {
                    x.insertAfter("System.out.print(\" constructing \"); System.out.println(\""+className+"\");");
                }
                catch (CannotCompileException e){
                    throw new RuntimeException(e);
                }
            });

            if (className.contains("TransactionProcessor")){
                CtMethod[] methods = current.getDeclaredMethods();
                CtField field = new CtField(CtClass.longType, "start_time", current);
                field.setModifiers(Modifier.STATIC);
                current.addField(field);
                Arrays.stream(methods).forEach(method -> {
                    try {
                        method.insertBefore("start_time = System.currentTimeMillis();");
                        method.insertAfter("""
                                    long finish = System.currentTimeMillis();
                                    long timeElapsed = finish - start_time;
                                    System.out.println("INSERTED: Elapsed Time: " + timeElapsed);""");
                    } catch (CannotCompileException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                });
            }
            return current.toBytecode();
        } catch (IOException | CannotCompileException e) {
            e.printStackTrace();
        }
        return classfileBuffer;
    }
}