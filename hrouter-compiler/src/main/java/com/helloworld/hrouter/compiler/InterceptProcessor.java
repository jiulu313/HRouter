package com.helloworld.hrouter.compiler;

import com.google.auto.service.AutoService;
import com.helloworld.hrouter.annotation.Intercept;
import com.helloworld.hrouter.annotation.Router;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashSet;
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


/**
 * 处理 Intercept 注解
 */
@AutoService(Processor.class)
public class InterceptProcessor extends AbstractProcessor {
    private static final String CORE_PACKAGE_NAME = "com.helloworld.hrouter.core";
    private static final String GENERATE_CODE_PACKAGE_NAME = "com.helloworld.hrouter.internal.intercept";

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    //支持的注解
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> sets = new HashSet<>();
        sets.add(Intercept.class.getCanonicalName());
        return sets;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Intercept.class);
        if (elements != null && !elements.isEmpty()) {
            parseInterceptAnnotation(elements);
            return true;
        }
        return false;
    }

    private void parseInterceptAnnotation(Set<? extends Element> elements) {
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            Name qualifiedName = typeElement.getQualifiedName(); //类的全名

            ClassName className = ClassName.get(CORE_PACKAGE_NAME,"IAddIntercept");

            //生成类
            TypeSpec.Builder typeBuilder = TypeSpec.classBuilder("InterceptUtil" + System.currentTimeMillis())
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(className);

            MethodSpec.Builder addActivityBuilder = MethodSpec.methodBuilder("addActivity")
                    .addAnnotation(ClassName.get(Override.class))
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.VOID);

            //addIntercept 方法
            MethodSpec.Builder addIntercept = MethodSpec.methodBuilder("addIntercept")
                    .addAnnotation(ClassName.get(Override.class))
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.VOID);

            String s = String.format("$T.getInstance().addIntercept(\"%s\")",qualifiedName);
            addIntercept.addStatement(s,ClassName.get(CORE_PACKAGE_NAME,"HRouter"));

            TypeSpec activityUtil = typeBuilder.addMethod(addIntercept.build()).build();
            JavaFile file = JavaFile.builder(GENERATE_CODE_PACKAGE_NAME, activityUtil).build();

            try {
                file.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}









