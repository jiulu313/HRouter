package com.helloworld.hrouter.compiler;

import com.google.auto.service.AutoService;
import com.helloworld.hrouter.annotation.Router;
import com.helloworld.hrouter.annotation.Intercept;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class RouterProcessor extends AbstractProcessor {
    private static final String CORE_ROUTER_PACKAGE_NAME = "com.helloworld.hrouter.core";
    private static final String GENERATE_CODE_PACKAGE_NAME = "com.helloworld.hrouter.internal.util";

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> sets = new HashSet<>();
        sets.add(Router.class.getCanonicalName());
        return sets;
    }

    /**
     *
     * @param set
     * @param roundEnvironment
     * @return true: 自己已经处理，不会被其它注解处理 ， false:会发给其它注解处理器处理
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Router.class);
        if (elements != null && !elements.isEmpty()) {
            parseRouterAnnotation(elements);
            return true;
        }
        return false;
    }

    /**
     * 解析 Router 注解
     */
    private void parseRouterAnnotation(Set<? extends Element> elements) {
        //1. 获取到所有使用了Route注解的activity，并存入表中
        Map<String,String> map = new HashMap<>();
        for (Element element : elements ) {
            TypeElement typeElement = (TypeElement) element;
            Name qualifiedName = typeElement.getQualifiedName();

            Router annotation = typeElement.getAnnotation(Router.class);
            String value = annotation.value();
            map.put(value,qualifiedName.toString() + ".class");
        }

        //写文件
        if (!map.isEmpty()) {
            ClassName className = ClassName.get(CORE_ROUTER_PACKAGE_NAME,"IRouter");
            TypeSpec.Builder typeBuilder = TypeSpec.classBuilder("ActivityUtil" + System.currentTimeMillis())
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(className);

            MethodSpec.Builder addActivityBuilder = MethodSpec.methodBuilder("addActivity")
                    .addAnnotation(ClassName.get(Override.class))
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.VOID);
            for (String key : map.keySet()) {
                String value = map.get(key);
                String s = String.format("$T.getInstance().addActivity(\"%s\",%s)",key,value);
                addActivityBuilder.addStatement(s,ClassName.get(CORE_ROUTER_PACKAGE_NAME,"HRouter"));
            }

            TypeSpec activityUtil = typeBuilder.addMethod(addActivityBuilder.build()).build();
            JavaFile file = JavaFile.builder(GENERATE_CODE_PACKAGE_NAME, activityUtil).build();

            try {
                file.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
