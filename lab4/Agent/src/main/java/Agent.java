import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import java.lang.instrument.Instrumentation;

public class Agent {
    public static void premain(String agentArgument, Instrumentation instrumentation) throws NotFoundException {
        System.out.println("Agent Counter");
        instrumentation.addTransformer(new ClassTransformer());
    }
}
